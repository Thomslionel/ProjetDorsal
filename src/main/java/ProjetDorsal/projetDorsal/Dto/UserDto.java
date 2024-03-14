package ProjetDorsal.projetDorsal.Dto;


import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class UserDto {

    private Long id;

    private String username;

    private String password;
}
