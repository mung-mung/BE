package api.user.owner.repository;

import api.dog.Dog;
import api.dog.enums.Sex;
import api.dog.repository.DogRepository;
import api.owning.Owning;
import api.owning.repository.OwningRepository;
import api.user.enums.Gender;
import api.user.enums.Role;
import api.user.owner.Owner;
import api.user.owner.dto.OwnerDto;
import api.user.owner.dto.OwningDogsDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@Import(OwnerRepositoryTest.TestConfig.class)
public class OwnerRepositoryTest {

    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private DogRepository dogRepository;

    @Autowired
    private OwningRepository owningRepository;

    @TestConfiguration
    static class TestConfig {

        @Bean
        public JPAQueryFactory jpaQueryFactory(EntityManager entityManager) {
            return new JPAQueryFactory(entityManager);
        }

        @Bean
        public OwnerRepositoryCustomImpl ownerRepositoryCustom(JPAQueryFactory jpaQueryFactory) {
            return new OwnerRepositoryCustomImpl(jpaQueryFactory);
        }
    }

    @Test
    @DisplayName("이메일로 owner 검색")
    public void testFindByEmail() {
        // given
        Owner owner = new Owner("test@example.com", "testowner", Role.OWNER, "password", "123-456-7890", Gender.MALE, LocalDate.of(1990, 1, 1));
        ownerRepository.save(owner);

        // when
        Optional<Owner> foundOwner = ownerRepository.findByEmail("test@example.com");

        // then
        assertThat(foundOwner).isPresent();
        assertThat(foundOwner.get().getEmail()).isEqualTo("test@example.com");
    }

    @Test
    @DisplayName("모든 조건으로 owner 검색")
    public void testFindOwnersByAllCriteria() {
        // given
        Owner owner = new Owner("test@example.com", "testowner", Role.OWNER, "password", "123-456-7890", Gender.MALE, LocalDate.of(1990, 1, 1));
        ownerRepository.save(owner);

        // when
        List<OwnerDto> foundOwners = ownerRepository.findOwnersByAllCriteria(null, "test@example.com", "testowner", "123-456-7890", Gender.MALE, LocalDate.of(1990, 1, 1));

        // then
        assertThat(foundOwners).isNotEmpty();
        assertThat(foundOwners.get(0).getEmail()).isEqualTo("test@example.com");
        assertThat(foundOwners.get(0).getUserName()).isEqualTo("testowner");
        assertThat(foundOwners.get(0).getContact()).isEqualTo("123-456-7890");
        assertThat(foundOwners.get(0).getGender()).isEqualTo(Gender.MALE);
        assertThat(foundOwners.get(0).getBirthday()).isEqualTo(LocalDate.of(1990, 1, 1));
    }

    @Test
    @DisplayName("userName으로 owner 검색")
    public void testFindByUserName() {
        // given
        Owner owner = new Owner("test2@example.com", "testowner2", Role.OWNER, "password", "123-456-7891", Gender.FEMALE, LocalDate.of(1991, 2, 2));
        ownerRepository.save(owner);

        // when
        List<OwnerDto> foundOwners = ownerRepository.findOwnersByAllCriteria(null, null, "testowner2", null, null, null);

        // then
        assertThat(foundOwners).isNotEmpty();
        assertThat(foundOwners.get(0).getUserName()).isEqualTo("testowner2");
    }

    @Test
    @DisplayName("모든 소유 개 정보 검색")
    public void testFindAllOwningDogs() {
        // given
        Owner owner = new Owner("test3@example.com", "testowner3", Role.OWNER, "password", "123-456-7892", Gender.MALE, LocalDate.of(1992, 3, 3));
        ownerRepository.save(owner);

        Dog dog = new Dog("Buddy", LocalDate.of(2020, 5, 10), "Labrador", 25.0f, Sex.MALE);
        dogRepository.save(dog);

        Owning owning = new Owning(owner, dog);
        owningRepository.save(owning);

        // when
        List<OwningDogsDto> owningDogs = ownerRepository.findAllOwningDogs(owner.getId());

        // then
        assertThat(owningDogs).isNotEmpty();
        assertThat(owningDogs.get(0).getOwnerId()).isEqualTo(owner.getId());
        assertThat(owningDogs.get(0).getOwningDogs()).isNotEmpty();
        assertThat(owningDogs.get(0).getOwningDogs().get(0).getName()).isEqualTo("Buddy");
    }
}