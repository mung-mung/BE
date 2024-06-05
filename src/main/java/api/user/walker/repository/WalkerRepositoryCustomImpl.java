package api.user.walker.repository;

import api.user.walker.dto.WalkerDto;
import api.user.enums.Gender;
import api.user.walker.QWalker;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.querydsl.core.types.Projections.fields;

public class WalkerRepositoryCustomImpl implements WalkerRepositoryCustom{
    private final JPAQueryFactory queryFactory;
    public WalkerRepositoryCustomImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public List<WalkerDto> findWalkersByAllCriteria(Integer id, String email, String userName, String contact, Gender gender, LocalDate birthday) {
        QWalker walker = QWalker.walker;
        BooleanBuilder whereClause = new BooleanBuilder();
        Optional.ofNullable(id).ifPresent(value -> whereClause.and(walker.id.eq(value)));
        Optional.ofNullable(email).ifPresent(value -> whereClause.and(walker.email.eq(value)));
        Optional.ofNullable(userName).ifPresent(value -> whereClause.and(walker.userName.eq(value)));
        Optional.ofNullable(contact).ifPresent(value -> whereClause.and(walker.contact.eq(value)));
        Optional.ofNullable(gender).ifPresent(value -> whereClause.and(walker.gender.eq(value)));
        Optional.ofNullable(birthday).ifPresent(value -> whereClause.and(walker.birthday.eq(value)));
        return queryFactory.select(fields(WalkerDto.class,
                        walker.id,
                        walker.email,
                        walker.userName,
                        walker.role,
                        walker.avatarUrl,
                        walker.contact,
                        walker.gender,
                        walker.birthday))
                .from(walker)
                .where(whereClause)
                .fetch();
    }
}
