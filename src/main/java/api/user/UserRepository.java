package api.user;

import api.dog.Dog;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

public class UserRepository {
    private final EntityManager em;
    public UserRepository(EntityManager em) {
        this.em = em;
    }
    public User save(User user) {
        em.persist(user);
        return user;
    }
    public Optional<User> findById(Long id) {
        User user = em.find(User.class, id);
        return Optional.ofNullable(user);
    }
    public Optional<User> findByEmail(String email) {
        // 이메일을 사용하여 사용자를 찾기 위해 JPQL 쿼리를 사용합니다.
        return em.createQuery("select u from User u where u.email = :email", User.class)
                .setParameter("email", email)
                .getResultList()
                .stream()
                .findFirst();
    }

    public List<User> findAll(){
        return em.createQuery("select u from User u", User.class)
                .getResultList();
    }

    public List<User> findAllWalker(){
        return em.createQuery("select u from User u where u.userType = :userType", User.class)
                .setParameter("userType", "walker")
                .getResultList();
    }

    public List<User> findAllOwner(){
        return em.createQuery("select u from User u where u.userType = :userType", User.class)
                .setParameter("userType", "owner")
                .getResultList();
    }


}