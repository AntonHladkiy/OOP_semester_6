package bets.entity;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
@Data
@Entity
@Table(name = "bets", schema = "public")
@NoArgsConstructor
public class Bet {
    @Id
    @GeneratedValue(generator = "bets_id_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "bets_id_seq", sequenceName = "bets_id_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "race_id")
    private Race race;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "runner_id")
    private Runner runner_id;

    @Column(name = "bet")
    private Double bet;

    @Column(name = "win")
    private Double win;
}
