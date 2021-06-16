package bets.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
@Data
@Entity
@Table(name = "users", schema = "public")
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(generator = "runners_id_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "runners_id_seq", sequenceName = "runners_id_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "username")
    private String username;
}
