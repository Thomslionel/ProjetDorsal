package ProjetDorsal.projetDorsal.Controller;

import ProjetDorsal.projetDorsal.Dto.CorrectionDto;
import ProjetDorsal.projetDorsal.Dto.ErrorDto;
import ProjetDorsal.projetDorsal.Dto.ExceptionCustom;
import ProjetDorsal.projetDorsal.Dto.UserDto;
import ProjetDorsal.projetDorsal.Service.CorrectionService;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;


@RestController
@AllArgsConstructor
public class CorrectionController {

    private CorrectionService correctionService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/correction")
    public void inscription(@RequestHeader("Authorization") String token, @RequestBody CorrectionDto correctionDto)
    {
        this.correctionService.saveCorrection(token, correctionDto);
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({RuntimeException.class, UsernameNotFoundException.class})
    public ErrorDto gestionErreur(@NotNull Exception exception) {
        return new ErrorDto(null, exception.getMessage());
    }


    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler({HttpClientErrorException.Forbidden.class, ExceptionCustom.class})
    public ErrorDto gestiondinterdi(@NotNull Exception exception) {
        return new ErrorDto(null, exception.getMessage());
    }

}
