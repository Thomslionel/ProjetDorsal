package ProjetDorsal.projetDorsal.Dto;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class ExceptionCustom extends RuntimeException{

    public ExceptionCustom(String message) {
        super(message);
    }

}
