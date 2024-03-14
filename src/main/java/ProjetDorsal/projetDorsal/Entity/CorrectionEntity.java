package ProjetDorsal.projetDorsal.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import static jakarta.persistence.CascadeType.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "correction")
public class CorrectionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Float correctionvalue;

    private LocalDate saisiday;
    private LocalDate saisinext;

    @ManyToOne(fetch = FetchType.LAZY,cascade = MERGE)
    @JoinColumn(name = "user_id")
    private  UserEntity userentity;
}
