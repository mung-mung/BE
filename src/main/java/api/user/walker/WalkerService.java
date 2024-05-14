package api.user.walker;

import api.dog.DogRepository;
import api.owning.OwningRepository;
import api.user.dto.WalkerDto;
import api.user.dto.UserAccountDto;
import api.user.userAccount.UserAccount;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
    public List<WalkerDto> findAllWalkers() {
        List<Walker> allWalkers =  walkerRepository.findAll();
        List<WalkerDto> walkerDtos = new ArrayList<>();
        for(Walker walker : allWalkers){
            WalkerDto walkerDto = new WalkerDto(walker);
            walkerDtos.add(walkerDto);
        }
        return walkerDtos;
    }

    @Transactional(readOnly = true)
    public WalkerDto findWalkerById(Integer id) {
        Optional<Walker> optionalWalker =  walkerRepository.findById(id);
        if (optionalWalker.isPresent()) {
            Walker walker = optionalWalker.get();
            WalkerDto walkerDto = new WalkerDto(walker);
            return walkerDto;
        }else{
            return null;
        }
    }

    // @Transactional
    // updateWalkerById 구현

}
