package BE.repository;

import BE.domain.User;
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
        User user = em.find(User.class, email);
        return Optional.ofNullable(user);
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