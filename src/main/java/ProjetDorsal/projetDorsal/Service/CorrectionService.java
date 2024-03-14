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
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
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
        UserDto userDto = new UserDto();

        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        Optional<UserEntity> userEntity = userRepository.findByUsername(jwtService.extractUsername(token));
        if (userEntity.isPresent()) {
            userDto = userMappage.entityToDto(userEntity.get());
            correctionDto.setUserDto(userDto);
            correctionDto.setSaisiday(LocalDate.now());
            correctionDto.setSaisinext(LocalDate.now().plusDays(1));


            Optional<CorrectionEntity> existingCorrection = correctionRepository.findByUserentity(userEntity.get());
            if (existingCorrection.isPresent() && Objects.equals(existingCorrection.get().getSaisiday(), LocalDate.now())) {
                throw new RuntimeException("Vous avez déjà saisi la correction pour aujourd'hui " +existingCorrection.get().getSaisiday()+ " Revenez demain " + existingCorrection.get().getSaisinext());
            } else {
                correctionRepository.save(correctionMappage.dtoToEntity(correctionDto));
            }
        } else {
            throw new RuntimeException("Utilisateur introuvable.");
        }
    }
}

