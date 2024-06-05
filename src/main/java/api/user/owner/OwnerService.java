package api.user.owner;

import api.common.util.auth.loggedInUser.LoggedInUser;
import api.user.enums.Role;
import api.user.owner.dto.OwnerDto;
import api.user.enums.Gender;
import api.user.owner.dto.OwningDogsDto;
import api.user.owner.repository.OwnerRepository;
import api.user.userAccount.dto.UserAccountDto;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.AccessDeniedException;
import java.time.LocalDate;
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
    public List<OwningDogsDto> findAllOwningDogs() throws AccessDeniedException {
        UserAccountDto loggedInUserAccountDto = LoggedInUser.getLoggedInUserAccountDto();
        if (loggedInUserAccountDto == null || !Role.OWNER.equals(loggedInUserAccountDto.getRole())) {
            throw new AccessDeniedException("Only owners can find their owning dogs.");
        }

        Optional<Owner> optionalOwner = ownerRepository.findByEmail(loggedInUserAccountDto.getEmail());
        if (optionalOwner.isEmpty()) {
            throw new AccessDeniedException("Invalid owner account");
        }
        Owner owner = optionalOwner.get();
        return ownerRepository.findAllOwningDogs(owner.getId());
    }

    // @Transactional
    // updateOwnerById 구현

}
