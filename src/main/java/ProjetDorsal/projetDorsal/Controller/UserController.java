package ProjetDorsal.projetDorsal.Controller;

import ProjetDorsal.projetDorsal.Dto.ErrorDto;
import ProjetDorsal.projetDorsal.Dto.ExceptionCustom;
import ProjetDorsal.projetDorsal.Dto.UserDto;
import ProjetDorsal.projetDorsal.Entity.UserEntity;

import ProjetDorsal.projetDorsal.Service.JwtService;
import ProjetDorsal.projetDorsal.Service.UserService;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@RestController
@RequestMapping()
public class UserController {

    private UserService userService;

    private AuthenticationManager authenticationManager;

    private BCryptPasswordEncoder bCryptPasswordEncoder;



    private JwtService jwtService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/inscription")
    public void inscription(@RequestBody  UserDto userDto)
    {
        this.userService.inscription(userDto);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping("/connexion")
    public Map<String, String> authenticateUser(@RequestBody UserDto userDto) {
        final Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userDto.getUsername(), userDto.getPassword())
        );

        if(authenticate.isAuthenticated()) {
            return this.jwtService.generate(userDto.getUsername());
        }
        return null;
    }



    @GetMapping("/findall")
    public List<UserDto> listeUser(@RequestHeader("Authorization") String token) {
        return this.userService.searchAll(token);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({RuntimeException.class, UsernameNotFoundException.class, HttpClientErrorException.Forbidden.class, ExceptionCustom.class})
    public ErrorDto gestionErreur(@NotNull Exception exception) {
        return new ErrorDto(null, exception.getMessage());
    }


    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler({HttpClientErrorException.Forbidden.class, ExceptionCustom.class})
    public ErrorDto gestiondinterdi(@NotNull Exception exception) {
        return new ErrorDto(null, exception.getMessage());
    }

}
