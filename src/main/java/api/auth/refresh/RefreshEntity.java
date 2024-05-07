package api.auth.refresh;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
@Table(name="refresh")
public class RefreshEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "NAME", nullable = false)
    private String email;

    @Column(name = "REFRESH", nullable = false)
    private String refresh;

    @Column(name = "EXPIRATION", nullable = false)
    private LocalDateTime expiration;

    public RefreshEntity(String email, String refresh, LocalDateTime expiration) {
        this.email = email;
        this.refresh = refresh;
        this.expiration = expiration;
    }
}
