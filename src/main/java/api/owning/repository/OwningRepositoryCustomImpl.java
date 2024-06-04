package api.owning.repository;

import api.owning.QOwning;
import api.owning.dto.OwningDto;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;

import java.util.List;
import java.util.Optional;

import static com.querydsl.core.types.Projections.fields;


public class OwningRepositoryCustomImpl implements OwningRepositoryCustom {
    private final JPAQueryFactory queryFactory;
    public OwningRepositoryCustomImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }
    QOwning owning = QOwning.owning;
    BooleanBuilder whereClause = new BooleanBuilder();
    @Override
    public List<OwningDto> findOwningsByAllCriteria(Integer id, Integer ownerId, Integer dogId) {
        Optional.ofNullable(id).ifPresent(value -> whereClause.and(owning.id.eq(value)));
        Optional.ofNullable(ownerId).ifPresent(value -> whereClause.and(owning.owner.id.eq(value)));
        Optional.ofNullable(dogId).ifPresent(value -> whereClause.and(owning.dog.id.eq(value)));
        return queryFactory.select(fields(OwningDto.class,
                        owning.id,
                        owning.owner.id,
                        owning.dog.id))
                .from(owning)
                .where(whereClause)
                .fetch();
    }


}
