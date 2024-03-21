package ProjetDorsal.projetDorsal.Controller;

import ProjetDorsal.projetDorsal.Dto.CorrectionDto;
import ProjetDorsal.projetDorsal.Dto.ErrorDto;
import ProjetDorsal.projetDorsal.Dto.ExceptionCustom;
import ProjetDorsal.projetDorsal.Dto.UserDto;
import ProjetDorsal.projetDorsal.Service.CorrectionService;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.nio.file.AccessDeniedException;
import java.time.LocalDate;
import java.util.List;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;


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

    @ResponseStatus(HttpStatus.ACCEPTED)
    @GetMapping("/listeCorrection")
    public List<CorrectionDto> listeCorrectionPerUser(@RequestHeader("Authorization") String token)
    {
        return this.correctionService.listeCorrectionPerUser(token);
    }



    @ResponseStatus(HttpStatus.ACCEPTED)
    @GetMapping("/periodique")
    public List<CorrectionDto> periodicite(@RequestHeader("Authorization") String token, @RequestParam("periode") LocalDate localDate)
    {
        return this.correctionService.listeParPeriodicite(token, localDate);
    }


    @ResponseStatus(HttpStatus.ACCEPTED)
    @GetMapping("/listeAll")
    public ResponseEntity<List<CorrectionDto>> listeAll(@RequestHeader("Authorization") String token) {
        List<CorrectionDto> correctionList = this.correctionService.listeCorrectionAll(token);
        return ResponseEntity.accepted().body(correctionList);
    }


    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping(path = "modifier/{idCorrection}", consumes = APPLICATION_JSON_VALUE)
    public void modifier(@PathVariable Long idCorrection, @RequestBody CorrectionDto correctionDto, @RequestHeader("Authorization") String token)
    {
        this.correctionService.modifierCorrection(idCorrection, correctionDto, token);
    }



    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping(path = "supprimer/{idCorrection}", consumes = APPLICATION_JSON_VALUE)
    public void supprimer(@PathVariable Long idCorrection, @RequestHeader("Authorization") String token)
    {
        this.correctionService.supprimerCorrection(idCorrection, token);
    }



    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(AccessDeniedException.class)
    public ErrorDto handleForbiddenException(AccessDeniedException exception) {
        return new ErrorDto(null, "Access forbidden: " + exception.getMessage());
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
