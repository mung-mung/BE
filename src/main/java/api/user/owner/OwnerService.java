package api.user.owner;

import api.dog.DogRepository;
import api.owning.OwningRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

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
    public List<Owner> findAllOwners() {
        return ownerRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Owner> findOwnerById(Integer id) {
        return ownerRepository.findById(id);
    }

    // @Transactional
    // updateOwnerById 구현

}
