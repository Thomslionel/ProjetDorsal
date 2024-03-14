package ProjetDorsal.projetDorsal.Repository;

import ProjetDorsal.projetDorsal.Entity.CorrectionEntity;
import ProjetDorsal.projetDorsal.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CorrectionRepository extends JpaRepository<CorrectionEntity, Long> {

    Optional<CorrectionEntity> findByUserentity(UserEntity userEntity);
}
