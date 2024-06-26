package api.owning;


import api.common.util.auth.loggedInUser.LoggedInUser;
import api.owning.dto.OwningDto;
import api.owning.repository.OwningRepository;
import api.user.userAccount.dto.UserAccountDto;
import api.user.enums.Role;
import api.user.owner.Owner;
import api.user.owner.repository.OwnerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Optional;

public class OwningService {
    private final OwningRepository owningRepository;
    private final OwnerRepository ownerRepository;
    public OwningService(OwningRepository owningRepository, OwnerRepository ownerRepository){
        this.owningRepository = owningRepository;
        this.ownerRepository = ownerRepository;
    }
    @Transactional(readOnly = true)
    public List<OwningDto> findOwningsByAllCriteria(Integer id, Integer ownerId, Integer dogId){
        return owningRepository.findOwningsByAllCriteria(id, ownerId, dogId);
    }

    @Transactional
    public void deleteOwningById(Integer owningId) throws AccessDeniedException {
        // 현재 로그인된 사용자 정보 가져오기
        UserAccountDto loggedInUserAccountDto = LoggedInUser.getLoggedInUserAccountDto();

        // 사용자 정보가 없거나 Role이 OWNER 아니면 AccessDeniedException 발생
        if (loggedInUserAccountDto == null || !Role.OWNER.equals(loggedInUserAccountDto.getRole())) {
            throw new AccessDeniedException("Only owners can delete owning records.");
        }

        // UserAccountDto에서 Owner 객체 조회
        Optional<Owner> optionalOwner = ownerRepository.findByEmail(loggedInUserAccountDto.getEmail());
        if (optionalOwner.isEmpty()) {
            throw new AccessDeniedException("Owner not found for the logged in user.");
        }
        Owner owner = optionalOwner.get();

        // Owning 객체 조회
        Optional<Owning> optionalOwning = owningRepository.findById(owningId);
        if (optionalOwning.isEmpty()) {
            throw new EntityNotFoundException("Owning not found with ID: " + owningId);
        }
        Owning owning = optionalOwning.get();

        // Owner와 Owning 관계 확인
        if (!owning.getOwner().equals(owner)) {
            throw new AccessDeniedException("The logged-in user is not the owner of this owning record.");
        }

        // Owning 삭제
        owningRepository.deleteById(owningId);
    }
}
