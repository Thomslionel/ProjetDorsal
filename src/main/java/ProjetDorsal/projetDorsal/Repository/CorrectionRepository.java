package ProjetDorsal.projetDorsal.Repository;

import ProjetDorsal.projetDorsal.Entity.CorrectionEntity;
import ProjetDorsal.projetDorsal.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CorrectionRepository extends JpaRepository<CorrectionEntity, Long> {

    List<CorrectionEntity> findByUserentity(UserEntity userEntity);

    List<CorrectionEntity> findBySaisidayAfterAndUserentity(LocalDate localDate, UserEntity userEntity);



}
