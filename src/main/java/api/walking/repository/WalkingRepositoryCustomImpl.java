package api.walking.repository;

import api.walking.QWalking;
import api.walking.dto.WalkingDto;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;

import java.util.List;
import java.util.Optional;

import static com.querydsl.core.types.Projections.constructor;


public class WalkingRepositoryCustomImpl implements WalkingRepositoryCustom {
    private final JPAQueryFactory queryFactory;
    public WalkingRepositoryCustomImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }
    QWalking walking = QWalking.walking;
    BooleanBuilder whereClause = new BooleanBuilder();
    @Override
    public List<WalkingDto> findWalkingsByAllCriteria(Integer id, Integer walkerId, Integer dogId) {
        Optional.ofNullable(id).ifPresent(value -> whereClause.and(walking.id.eq(value)));
        Optional.ofNullable(walkerId).ifPresent(value -> whereClause.and(walking.walker.id.eq(value)));
        Optional.ofNullable(dogId).ifPresent(value -> whereClause.and(walking.dog.id.eq(value)));
        return queryFactory.select(constructor(WalkingDto.class,
                        walking.id,
                        walking.walker.id,
                        walking.dog.id))
                .from(walking)
                .where(whereClause)
                .fetch();
    }


}
