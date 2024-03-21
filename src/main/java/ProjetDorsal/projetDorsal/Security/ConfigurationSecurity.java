package ProjetDorsal.projetDorsal.Security;

import ProjetDorsal.projetDorsal.Service.JwtFilter;
import ProjetDorsal.projetDorsal.Service.UserService;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.http.HttpMethod.*;

@Configuration
@AllArgsConstructor
@EnableWebSecurity
public class ConfigurationSecurity {

private UserService userService;

private final JwtFilter jwtFilter;

@Bean
    public SecurityFilterChain securityFilterChain(@NotNull HttpSecurity httpSecurity) throws Exception {
        return
                httpSecurity
                        .csrf(AbstractHttpConfigurer::disable)
                        .authorizeHttpRequests(
                                authorize-> authorize
                                        .requestMatchers(GET, "/findall").hasRole("ADMIN")
                                        .requestMatchers(POST,"/inscription").permitAll()
                                        .requestMatchers(POST,"/connexion").permitAll()
                                        .requestMatchers(POST, "/correction").hasAnyRole("ADMIN", "USER")
                                        .requestMatchers(GET, "/listeCorrection").hasAnyRole("ADMIN", "USER")
                                        .requestMatchers(GET, "/listeAll").hasRole("ADMIN")
                                        .requestMatchers(PUT, "/modifier/{idCorrection}").hasAnyRole("ADMIN", "USER")
                                        .requestMatchers(DELETE, "supprimer/{idCorrection}").hasAnyRole("ADMIN", "USER")
                                        .requestMatchers(DELETE, "/periodique").hasAnyRole("ADMIN", "USER")


                                        .anyRequest().authenticated()

                        ).sessionManagement(httpSecuritySessionManagementConfigurer ->
                                httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                        )
                        .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                        .build();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, BCryptPasswordEncoder bCryptPasswordEncoder) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder);
        return authenticationManagerBuilder.build();
    }

}
