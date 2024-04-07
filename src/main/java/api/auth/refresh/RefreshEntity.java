package api.auth.refresh;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="refresh")
public class RefreshEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NAME", nullable = false)
    private String email;

    @Column(name = "REFRESH", nullable = false)
    private String refresh;

    @Column(name = "EXPIRATION", nullable = false)
    private String expiration;
}
