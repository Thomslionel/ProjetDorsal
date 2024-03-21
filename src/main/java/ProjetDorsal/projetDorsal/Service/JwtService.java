package ProjetDorsal.projetDorsal.Service;


import ProjetDorsal.projetDorsal.Entity.UserEntity;
import ProjetDorsal.projetDorsal.Repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

@AllArgsConstructor
@Service
public class JwtService {


    private UserService userService;

    public Map<String, String> generate(String username) {
        UserEntity userEntity = this.userService.loadUserByUsername(username);
        return this.generateJwt(userEntity);
    }

    public String extractUsername(String token) {
        return this.getClaim(token, Claims::getSubject);
    }

    public boolean isTokenExpired(String token) {
        Date expirationDate = getExpirationDateFromToken(token);
        return expirationDate.before(new Date());
    }

    private Date getExpirationDateFromToken(String token) {
        return this.getClaim(token, Claims::getExpiration);
    }

    private <T> T getClaim(String token, Function<Claims, T> function) {
        Claims claims = getAllClaims(token);
        return function.apply(claims);
    }

    private Claims getAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(this.getKey())
                .parseClaimsJws(token)
                .getBody();
    }

    private Map<String, String> generateJwt(UserEntity utilisateur) {
        final long currentTime = System.currentTimeMillis();
        final long expirationTime = currentTime + 30 * 60 * 1000;

        final Map<String, Object> claims = Map.of(
                "nom", utilisateur.getUsername(),
                "role", utilisateur.getTypeRole(),
                "password", utilisateur.getPassword(),
                Claims.EXPIRATION, new Date(expirationTime),
                Claims.SUBJECT, utilisateur.getUsername()
        );

        final String bearer = Jwts.builder()
                .setIssuedAt(new Date(currentTime))
                .setExpiration(new Date(expirationTime))
                .setSubject(utilisateur.getUsername())
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS256, getKey())
                .compact();
        return Map.of("Jetons", bearer);
    }

    private @NotNull Key getKey() {
        String ENCRIPTION_KEY = "608f36e92dc66d97d5933f0e6371493cb4fc05b1aa8f8de64014732472303a7c";
        byte[] decoder = Base64.getDecoder().decode(ENCRIPTION_KEY);
        return Keys.hmacShaKeyFor(decoder);
    }
    }


