package bets.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name = "bookmaker_data", schema = "public")
@NoArgsConstructor
public class BookmakerData {
    @Id
    @GeneratedValue(generator = "bookmaker_data_id_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "bookmaker_data_id_seq", sequenceName = "bookmaker_data_id_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "bookmaker_id")
    private User bookmaker;

    @Column(name = "date")
    private java.sql.Date date;

    @Column(name = "income")
    private Double income;
}
