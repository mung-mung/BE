package api.user.owner;

import api.user.dto.OwnerDto;
import api.user.enums.Gender;
import api.user.owner.repository.OwnerRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OwnerService {
    private final OwnerRepository ownerRepository;


    @Autowired
    public OwnerService(OwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;

    }

    @Transactional(readOnly = true)
    public List<OwnerDto> findWalkersByAllCriteria(Integer id, String email, String userName, String contact, Gender gender, LocalDate birthday) {
        return ownerRepository.findOwnersByAllCriteria(id, email, userName, contact, gender, birthday);
    }

    @Transactional(readOnly = true)
    public OwnerDto findOwnerById(Integer id) {
        Optional<Owner> optionalOwner =  ownerRepository.findById(id);
        if (optionalOwner.isPresent()) {
            Owner owner = optionalOwner.get();
            OwnerDto ownerDto = new OwnerDto(owner);
            return ownerDto;
        }else{
            return null;
        }
    }

    // @Transactional
    // updateOwnerById 구현

}
