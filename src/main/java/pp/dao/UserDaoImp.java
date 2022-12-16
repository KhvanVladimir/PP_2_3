package pp.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import pp.model.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import java.util.List;

@Repository
@EnableTransactionManagement
public class UserDaoImp implements UserDao {

    @PersistenceContext
    private EntityManager entityManagerFactory;

    @Override
    public void add(User user) {
        entityManagerFactory.persist(user);
    }

    @Override
    public void update(User user) {
        entityManagerFactory.merge(user);
    }

    @Override
    public List<User> listUsers() {
        return entityManagerFactory.createNativeQuery("SELECT * FROM test1.users",User.class).getResultList();
    }

    @Override
    public User getUser(int id) {
        return entityManagerFactory.find(User.class, (long) id);
    }

    @Override
    public void delete(int id) {
        entityManagerFactory.remove(getUser(id));
    }
}