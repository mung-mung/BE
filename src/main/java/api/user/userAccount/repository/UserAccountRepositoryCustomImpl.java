package api.user.userAccount.repository;

import api.user.userAccount.dto.UserAccountDto;
import api.user.enums.Gender;
import api.user.enums.Role;
import api.user.userAccount.QUserAccount;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.querydsl.core.types.Projections.fields;

public class UserAccountRepositoryCustomImpl implements UserAccountRepositoryCustom{
    private final JPAQueryFactory queryFactory;

    public UserAccountRepositoryCustomImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }
    @Override
    public List<UserAccountDto> findUsersByAllCriteria(Integer id, String email, String userName, Role role, String contact, Gender gender, LocalDate birthday) {
        QUserAccount userAccount = QUserAccount.userAccount;
        BooleanBuilder whereClause = new BooleanBuilder();
        Optional.ofNullable(id).ifPresent(value -> whereClause.and(userAccount.id.eq(value)));
        Optional.ofNullable(email).ifPresent(value -> whereClause.and(userAccount.email.eq(value)));
        Optional.ofNullable(userName).ifPresent(value -> whereClause.and(userAccount.userName.eq(value)));
        Optional.ofNullable(role).ifPresent(value -> whereClause.and(userAccount.role.eq(value)));
        Optional.ofNullable(contact).ifPresent(value -> whereClause.and(userAccount.contact.eq(value)));
        Optional.ofNullable(gender).ifPresent(value -> whereClause.and(userAccount.gender.eq(value)));
        Optional.ofNullable(birthday).ifPresent(value -> whereClause.and(userAccount.birthday.eq(value)));
        return queryFactory.select(fields(UserAccountDto.class,
                        userAccount.id,
                        userAccount.email,
                        userAccount.userName,
                        userAccount.role,
                        userAccount.avatarUrl,
                        userAccount.contact,
                        userAccount.gender,
                        userAccount.birthday))
                .from(userAccount)
                .where(whereClause)
                .fetch();
    }
}
