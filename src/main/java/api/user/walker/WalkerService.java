package api.user.walker;

import api.dog.DogRepository;
import api.owning.OwningRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class WalkerService {
    private final WalkerRepository walkerRepository;

    @Autowired
    public WalkerService(WalkerRepository walkerRepository) {
        this.walkerRepository = walkerRepository;
    }

    @Transactional(readOnly = true)
    public List<Walker> findAllWalkers() {
        return walkerRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Walker> findWalkerById(Integer id) {
        return walkerRepository.findById(id);
    }

    // @Transactional
    // updateWalkerById 구현

}
