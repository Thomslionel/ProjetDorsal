package ProjetDorsal.projetDorsal.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CorrectionDto {

    private Long id;
    private Float correctionvalue;

    private LocalDate saisiday;

    private LocalDate saisinext;


    private UserDto userDto;
}
