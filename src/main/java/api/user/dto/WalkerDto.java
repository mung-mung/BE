package api.user.dto;

import api.user.owner.Owner;
import api.user.walker.Walker;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class WalkerDto extends UserDtoAbstractClass {
    // 추후에 Walker 전용 필드 추가 예정
    public WalkerDto(Walker walker) {
        super(walker);
    }
}
