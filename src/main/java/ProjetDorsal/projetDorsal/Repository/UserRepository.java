package ProjetDorsal.projetDorsal.Repository;

import ProjetDorsal.projetDorsal.Entity.UserEntity;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByUsername (String userName);
    @NotNull List<UserEntity> findAll();
}
