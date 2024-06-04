package api.dog.repository;

import api.dog.QDog;
import api.dog.dto.DogDto;
import api.dog.enums.Sex;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.querydsl.core.types.Projections.fields;

public class DogRepositoryCustomImpl implements DogRepositoryCustom{
    private final JPAQueryFactory queryFactory;
    public DogRepositoryCustomImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public List<DogDto> findDogsByAllCriteria(Integer id, String name, LocalDate birthday, String breed, Float weight, Sex sex) {
        QDog dog = QDog.dog;
        BooleanBuilder whereClause = new BooleanBuilder();
        Optional.ofNullable(id).ifPresent(value -> whereClause.and(dog.id.eq(value)));
        Optional.ofNullable(name).ifPresent(value -> whereClause.and(dog.name.eq(value)));
        Optional.ofNullable(birthday).ifPresent(value -> whereClause.and(dog.birthday.eq(value)));
        Optional.ofNullable(breed).ifPresent(value -> whereClause.and(dog.breed.eq(value)));
        Optional.ofNullable(weight).ifPresent(value -> whereClause.and(dog.weight.eq(value)));
        Optional.ofNullable(sex).ifPresent(value -> whereClause.and(dog.sex.eq(value)));


        return queryFactory.select(fields(DogDto.class,
                        dog.id,
                        dog.name,
                        dog.birthday,
                        dog.breed,
                        dog.weight,
                        dog.sex))
                .from(dog)
                .where(whereClause)
                .fetch();
    }
}
