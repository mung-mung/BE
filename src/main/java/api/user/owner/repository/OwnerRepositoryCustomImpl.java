package api.user.owner.repository;

import api.dog.QDog;
import api.dog.dto.DogDto;
import api.owning.QOwning;
import api.user.owner.dto.OwnerDto;
import api.user.enums.Gender;
import api.user.owner.QOwner;
import api.user.owner.dto.OwningDogsDto;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.querydsl.core.types.Projections.fields;

public class OwnerRepositoryCustomImpl implements OwnerRepositoryCustom{
    private final JPAQueryFactory queryFactory;
    public OwnerRepositoryCustomImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public List<OwnerDto> findOwnersByAllCriteria(Integer id, String email, String userName, String contact, Gender gender, LocalDate birthday) {
        QOwner owner = QOwner.owner;
        BooleanBuilder whereClause = new BooleanBuilder();
        Optional.ofNullable(id).ifPresent(value -> whereClause.and(owner.id.eq(value)));
        Optional.ofNullable(email).ifPresent(value -> whereClause.and(owner.email.eq(value)));
        Optional.ofNullable(userName).ifPresent(value -> whereClause.and(owner.userName.eq(value)));
        Optional.ofNullable(contact).ifPresent(value -> whereClause.and(owner.contact.eq(value)));
        Optional.ofNullable(gender).ifPresent(value -> whereClause.and(owner.gender.eq(value)));
        Optional.ofNullable(birthday).ifPresent(value -> whereClause.and(owner.birthday.eq(value)));
        return queryFactory.select(fields(OwnerDto.class,
                        owner.id,
                        owner.email,
                        owner.userName,
                        owner.role,
                        owner.avatarUrl,
                        owner.contact,
                        owner.gender,
                        owner.birthday))
                .from(owner)
                .where(whereClause)
                .fetch();
    }

    @Override
    public List<OwningDogsDto> findAllOwningDogs(Integer ownerId) {
        QDog dog = QDog.dog;
        QOwning owning = QOwning.owning;

        List<DogDto> dogDtos = queryFactory.select(fields(DogDto.class,
                        dog.id,
                        dog.name,
                        dog.birthday,
                        dog.breed,
                        dog.weight,
                        dog.sex))
                .from(dog)
                .innerJoin(owning).on(dog.id.eq(owning.dog.id))
                .where(owning.owner.id.eq(ownerId))
                .fetch();

        return List.of(new OwningDogsDto(ownerId, dogDtos));
    }
}
