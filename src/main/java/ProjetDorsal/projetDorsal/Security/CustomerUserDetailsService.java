//package ProjetDorsal.projetDorsal.Security;
//
//import ProjetDorsal.projetDorsal.Entity.UserEntity;
//import ProjetDorsal.projetDorsal.Repository.UserRepository;
//import ProjetDorsal.projetDorsal.Service.UserService;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//@AllArgsConstructor
//@Getter
//@Setter
//@Service
//public class CustomerUserDetailsService implements UserDetailsService {
//
//    private UserRepository userRepository;
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        Optional<UserEntity> userEntityOptional = userRepository.findByUsername(username);
//
//        if (userEntityOptional.isPresent()) {
//            UserEntity userEntity = userEntityOptional.get();
//            return new User(
//                    userEntity.getUsername(),
//                    userEntity.getPassword(),
//                    getGrantedAuthorities(userEntity.getTypeRole().toString())
//            );
//        } else {
//            throw new UsernameNotFoundException("Utilisateur non trouvé pour le nom d'utilisateur : " + username);
//        }
//    }
//
//
//    private List<GrantedAuthority> getGrantedAuthorities(String role) {
//        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
//        authorities.add(new SimpleGrantedAuthority(role));
//        return authorities;
//    }
//}
