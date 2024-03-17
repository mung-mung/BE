package api.dog;

import api.user.User;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

public class DogRepository { //JpaRepository 확장하기?

    private final EntityManager em;
    public DogRepository(EntityManager em){
        this.em = em;
    }
    public Dog save(Dog dog){
        em.persist(dog);
        return dog;
    }

    public Optional<Dog> findById(Long id) {
        Dog dog = em.find(Dog.class, id);
        return Optional.ofNullable((dog));
    }

    public List<Dog> findByOwner(User owner) {
        return em.createQuery("select d from Dog where d.owner = :owner", Dog.class)
                .setParameter("owner", owner)
                .getResultList();
    }

    public List<Dog> findByWalker(User walker) {
        return em.createQuery("select d from Dog where d.walker = :walker", Dog.class)
                .setParameter("walker", walker)
                .getResultList();
    }

    public List<Dog> findAll(){
        return em.createQuery("select d from Dog d", Dog.class)
                .getResultList();
    }
}
