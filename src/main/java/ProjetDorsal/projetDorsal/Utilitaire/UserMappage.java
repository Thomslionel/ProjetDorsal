package ProjetDorsal.projetDorsal.Utilitaire;

import ProjetDorsal.projetDorsal.Dto.UserDto;
import ProjetDorsal.projetDorsal.Entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class UserMappage {

    //Mappage dtoToEntity
    public UserEntity dtoToEntity(@NotNull UserDto userDto)
    {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(userDto.getUsername());
        if (userDto.getUsername().contains("@thoms.com"))
        {
            userEntity.setTypeRole(TypeRole.ADMIN);
        }else
        {
            userEntity.setTypeRole(TypeRole.USER);
        }
        userEntity.setPassword(bCryptPasswordEncoder.encode((userDto.getPassword())));
        return userEntity;
    }


    public UserDto entityToDto(@NotNull UserEntity userEntity)
    {
        UserDto userDto = new UserDto();
        userDto.setId(userEntity.getId());
        userDto.setUsername(userEntity.getUsername());
        userDto.setPassword(userEntity.getPassword());
        return userDto;
    }

    public List<UserDto> listeentityToDto(@NotNull List<UserEntity> userEntities)
    {
        List<UserDto> userDtos = new ArrayList<>();

        UserDto userDto;
        for (UserEntity userEntity : userEntities)
        {
            userDto = entityToDto(userEntity);
            userDtos.add(userDto);
        }
        return userDtos;
    }


    //bCryptPasswordEncoder chemin sans retour
    public UserEntity dtoToEntityforCorrection(@NotNull UserDto userDto)
    {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(userDto.getUsername());
        userEntity.setId(userDto.getId());
        if (userDto.getUsername().contains("@thoms.com"))
        {
            userEntity.setTypeRole(TypeRole.ADMIN);
        }else
        {
            userEntity.setTypeRole(TypeRole.USER);
        }
        userEntity.setPassword(userDto.getPassword());
        return userEntity;
    }

}
