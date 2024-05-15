package api.board;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<post, Integer> {
    List<post> findByWriterId(Integer writerId);
}
