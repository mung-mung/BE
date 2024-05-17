package api.user.owner;

import api.dog.DogRepository;
import api.owning.OwningRepository;
import api.user.dto.OwnerDto;
import api.user.dto.UserAccountDto;
import api.user.userAccount.UserAccount;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

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
    public List<OwnerDto> findAllOwners() {
        List<Owner> allOwners =  ownerRepository.findAll();
        List<OwnerDto> ownerDtos = new ArrayList<>();
        for(Owner owner : allOwners){
            OwnerDto ownerDto = new OwnerDto(owner);
            ownerDtos.add(ownerDto);
        }
        return ownerDtos;
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
