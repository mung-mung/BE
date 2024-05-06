package api.board;

public class PostService {
    private final PostRepository boardRepository;

    public PostService(PostRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

}
