package api.user.dto;

import api.user.owner.Owner;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class OwnerDto extends UserDtoAbstractClass {
    // 추후에 Owner 전용 필드 추가 예정
    public OwnerDto(Owner owner) {
        super(owner);
    }
}
