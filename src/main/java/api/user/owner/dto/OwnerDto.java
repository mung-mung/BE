package api.user.owner.dto;

import api.user.dto.UserDtoAbstractClass;
import api.user.owner.Owner;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class OwnerDto extends UserDtoAbstractClass {
    public OwnerDto(){}
    // 추후에 Owner 전용 필드 추가 예정
    public OwnerDto(Owner owner) {
        super(owner);
    }
}
