package ProjetDorsal.projetDorsal.Service;

import ProjetDorsal.projetDorsal.Dto.UserDto;
import ProjetDorsal.projetDorsal.Entity.UserEntity;
import ProjetDorsal.projetDorsal.Repository.UserRepository;
import ProjetDorsal.projetDorsal.Utilitaire.TypeRole;
import ProjetDorsal.projetDorsal.Utilitaire.UserMappage;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor

public class UserService  implements UserDetailsService {

    private final UserRepository userRepository;

    private final UserMappage userMappage ;


    public void inscription(@NotNull UserDto userDto)
    {

        System.out.println(userDto.getPassword());
        System.out.println(userDto.getUsername());
        if (!userDto.getUsername().contains("@") || !userDto.getUsername().contains("."))
        {
            throw new RuntimeException("Mail Invalide");
        }

        Optional<UserEntity> userOptionnal = this.userRepository.findByUsername(userDto.getUsername());

        if (userOptionnal.isPresent())
        {
            throw new RuntimeException("Votre mail est déjà utilisé");
        }else
        {
            UserEntity userEntity = userMappage.dtoToEntity(userDto);


            userRepository.save(userEntity);
        }

    }

    public List<UserDto> searchAll() {
        return  this.userMappage.listeentityToDto(userRepository.findAll());
    }

    @Override
    public UserEntity loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.userRepository
                .findByUsername(username)
                .orElseThrow(() -> new  UsernameNotFoundException("Aucun utilisateur ne corespond à cet identifiant"));
    }


//    public UserEntity rechercherByUsername(String username)
//    {
//        Optional<UserEntity> userEntity = userRepository.findByUserName(username);
//        if (userEntity.isPresent())
//        {
//            return userEntity.get();
//        }else
//        {
//            throw new EntityNotFoundException("L'utilisateur n'existe pas");
//        }
//    }
}
