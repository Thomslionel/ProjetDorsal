package ProjetDorsal.projetDorsal.Service;

import ProjetDorsal.projetDorsal.Dto.CorrectionDto;
import ProjetDorsal.projetDorsal.Dto.UserDto;
import ProjetDorsal.projetDorsal.Entity.CorrectionEntity;
import ProjetDorsal.projetDorsal.Entity.UserEntity;
import ProjetDorsal.projetDorsal.Repository.CorrectionRepository;
import ProjetDorsal.projetDorsal.Repository.UserRepository;
import ProjetDorsal.projetDorsal.Utilitaire.CorrectionMappage;
import ProjetDorsal.projetDorsal.Utilitaire.UserMappage;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CorrectionService {

    private JwtService jwtService;
    private UserService userService;
    private UserMappage userMappage;
    private CorrectionMappage correctionMappage;
    private CorrectionRepository correctionRepository;
    private UserRepository userRepository;

    public void saveCorrection(@NotNull String token, CorrectionDto correctionDto) {
        Optional<UserEntity> userEntity = findUserFromToken(token);
        UserDto userDto = new UserDto();

        if (userEntity.isPresent()) {
            userDto = userMappage.entityToDto(userEntity.get());
            correctionDto.setUserDto(userDto);
            correctionDto.setSaisiday(LocalDate.now());
            correctionDto.setSaisinext(LocalDate.now().plusDays(1));


            List<CorrectionEntity> existingCorrections = correctionRepository.findByUserentity(userEntity.get());
            for (CorrectionEntity existingCorrection : existingCorrections) {
                if (Objects.equals(existingCorrection.getSaisiday(), LocalDate.now())) {
                    throw new RuntimeException("Vous avez déjà saisi la correction pour aujourd'hui " + existingCorrection.getSaisiday() + ". Revenez demain " + existingCorrection.getSaisinext() + " .");
                }
            }
            correctionRepository.save(correctionMappage.dtoToEntity(correctionDto));

        } else {
            throw new RuntimeException("Utilisateur introuvable.");
        }
    }

    public List<CorrectionDto> listeCorrectionPerUser(String token) {
        Optional<UserEntity> userEntity = findUserFromToken(token);
        if (userEntity.isPresent()) {
            return correctionMappage.listeentityToDto(correctionRepository.findByUserentity(userEntity.get()));
        } else {
            throw new UsernameNotFoundException("Utilisateur non défini");
        }
    }


    private Optional<UserEntity> findUserFromToken(String token) {
        UserDto userDto = new UserDto();

        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        Optional<UserEntity> userEntity = userRepository.findByUsername(jwtService.extractUsername(token));
        return userEntity;
    }

    public List<CorrectionDto> listeCorrectionAll(String token) {

        Optional<UserEntity> userEntity = findUserFromToken(token);
        if (userEntity.isPresent()) {
            return correctionMappage.listeentityToDto(correctionRepository.findAll());
        } else {
            throw new UsernameNotFoundException("Réservez à l'Admin");
        }
    }

    public void modifierCorrection(Long idCorrection, CorrectionDto correctionDto, String token) {

        Optional<CorrectionEntity> correctionEntity = correctionRepository.findById(idCorrection);

        Optional<UserEntity> userEntity = findUserFromToken(token);

        List<CorrectionEntity> correctionEntity2 = correctionRepository.findByUserentity(userEntity.get());
        if (correctionEntity.isPresent()) {
            boolean elementTrouve = false;

            for (CorrectionEntity correction : correctionEntity2) {
                if (correction.getUserentity() == correctionEntity.get().getUserentity()) {
                    CorrectionEntity correctionEntity1 = correctionEntity.get();
                    correctionEntity1.setCorrectionvalue(correctionDto.getCorrectionvalue());
                    correctionEntity1.setSaisiday(LocalDate.now());
                    correctionEntity1.setSaisinext(LocalDate.now().plusDays(1));
                    correctionRepository.save(correctionEntity1);
                    elementTrouve = true;
                    break;
                }
            }

            if (!elementTrouve) {
                throw new RuntimeException("Vous ne devez pas modifier cet élément");
            }
        } else {
            throw new RuntimeException("Correction Non disponible");
        }



    }

    public void supprimerCorrection(Long idCorrection, String token) {
        Optional<CorrectionEntity> correctionEntity = correctionRepository.findById(idCorrection);
        Optional<UserEntity> userEntity = findUserFromToken(token);

        List<CorrectionEntity> correctionEntity2 = correctionRepository.findByUserentity(userEntity.get());

        if (correctionEntity.isPresent()) {
            boolean elementTrouve = false;

            for (CorrectionEntity correction : correctionEntity2) {
                if (correction.getUserentity() == correctionEntity.get().getUserentity()) {
                    correctionRepository.deleteById(idCorrection);
                    elementTrouve = true;
                    break;
                }
            }

            if (!elementTrouve) {
                throw new RuntimeException("Vous ne devez pas supprimer cet élément");
            }
        } else {
            throw new RuntimeException("Correction Non disponible");
        }
    }
}

