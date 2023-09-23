package guru.qa.niffler.db.dao.impl;

import guru.qa.niffler.db.ServiceDB;
import guru.qa.niffler.db.dao.AuthUserDAO;
import guru.qa.niffler.db.jpa.EntityManagerFactoryProvider;
import guru.qa.niffler.db.jpa.JpaService;
import guru.qa.niffler.db.model.auth.AuthUserEntity;

import java.util.UUID;

public class AuthUserDAOHibernate extends JpaService implements AuthUserDAO {
    public AuthUserDAOHibernate() {
        super(EntityManagerFactoryProvider.INSTANCE.getDataSource(ServiceDB.AUTH).createEntityManager());
    }

    @Override
    public AuthUserEntity createUser(AuthUserEntity user) {
        user.setPassword(pe.encode(user.getPassword()));
        persist(user);
        return user;
    }

    @Override
    public AuthUserEntity getUserFromAuthUserById(UUID userId) {
        return em.createQuery("select u from AuthUserEntity u where u.id=:userId", AuthUserEntity.class)
                .setParameter("userId", userId)
                .getSingleResult();
    }

    @Override
    public AuthUserEntity getUserFromAuthByUsername(String username) {
        return em.createQuery("select u from AuthUserEntity u where u.username=:username", AuthUserEntity.class)
                .setParameter("username", username)
                .getSingleResult();
    }


    @Override
    public void updateUser(AuthUserEntity user) {
        merge(user);
    }

    @Override
    public void deleteUserByIdInAuth(UUID userId) {
        AuthUserEntity user = getUserFromAuthUserById(userId);
        remove(user);
    }

    @Override
    public void deleteUserByUsername(String username) {
        AuthUserEntity user = getUserFromAuthByUsername(username);
        remove(user);
    }
}
