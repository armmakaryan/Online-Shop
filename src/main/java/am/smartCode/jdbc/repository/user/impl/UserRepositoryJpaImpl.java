package am.smartCode.jdbc.repository.user.impl;

import am.smartCode.jdbc.model.User;
import am.smartCode.jdbc.repository.user.UserRepository;
import am.smartCode.jdbc.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;

import java.sql.SQLException;
import java.util.List;

public class UserRepositoryJpaImpl implements UserRepository {

    SessionFactory sessionFactory = HibernateUtil.getSessionFactory();


    @Override
    public void create(User user) throws SQLException {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(user);
        transaction.commit();
        session.close();
    }

    @Override
    public void update(User user) throws SQLException {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.update(user);
        transaction.commit();
        session.close();
    }

    @Override
    public User get(Long id) throws SQLException {
        Session session = sessionFactory.openSession();
        User user = session.get(User.class, id);
        session.close();
        return user;
    }

    @Override
    public User get(String email) throws SQLException {
        Session session = sessionFactory.openSession();
        User user = session.get(User.class, email);
        session.close();
        return user;
    }

    @Override
    public List<User> getAll() throws SQLException {
        Session session = sessionFactory.openSession();
        NativeQuery<User> nativeQuery = session.createNativeQuery(
                "SELECT * FROM users");
        return nativeQuery.getResultList();
    }

    @Override
    public List<User> findUsersByName(String name) throws SQLException {
        Session session = sessionFactory.openSession();
        NativeQuery<User> nativeQuery = session.createNativeQuery(
                "SELECT * FROM users WHERE lower(name) LIKE lower(concat('%',:name,'%'));",
                User.class);
        nativeQuery.setParameter("name", name);
        return nativeQuery.getResultList();
    }

    @Override
    public void delete(long id) throws SQLException {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(get(id));
        transaction.commit();
        session.close();
    }
}
