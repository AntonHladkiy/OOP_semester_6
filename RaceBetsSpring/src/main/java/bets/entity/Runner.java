package bets.entity;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
@Data
@Entity
@Table(name = "runners", schema = "public")
@NoArgsConstructor
public class Runner {
    @Id
    @GeneratedValue(generator = "runners_id_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "runners_id_seq", sequenceName = "runners_id_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "runner_name")
    private String runner_name;
}
