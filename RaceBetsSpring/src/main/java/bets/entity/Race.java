package bets.entity;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name = "races", schema = "public")
@NoArgsConstructor
public class Race {
    @Id
    @GeneratedValue(generator = "races_id_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "races_id_seq", sequenceName = "races_id_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "coef1")
    private Double coef1;

    @Column(name = "coef2")
    private Double coef2;

    @Column(name = "finished")
    private Boolean finished;

    @ManyToOne
    @JoinColumn(name = "bookmaker_id")
    private User bookmaker;

    @ManyToOne
    @JoinColumn(name = "runner_id1")
    private Runner runner_1;

    @ManyToOne
    @JoinColumn(name = "runner_id2")
    private Runner runner_2;
}
