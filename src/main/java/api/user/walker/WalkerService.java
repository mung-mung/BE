package api.user.walker;

import api.user.dto.WalkerDto;
import api.user.enums.Gender;
import api.user.enums.Role;
import api.user.walker.repository.WalkerRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
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
    public List<WalkerDto> findWalkersByAllCriteria(Integer id, String email, String userName, String contact, Gender gender, LocalDate birthday) {
        return walkerRepository.findWalkersByAllCriteria(id, email, userName, contact, gender, birthday);
    }


    // @Transactional
    // updateWalkerById 구현

}
