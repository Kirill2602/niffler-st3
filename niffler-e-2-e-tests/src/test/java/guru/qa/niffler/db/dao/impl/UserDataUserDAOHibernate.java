package guru.qa.niffler.db.dao.impl;

import guru.qa.niffler.db.ServiceDB;
import guru.qa.niffler.db.dao.UserDataUserDAO;
import guru.qa.niffler.db.jpa.EntityManagerFactoryProvider;
import guru.qa.niffler.db.jpa.JpaService;
import guru.qa.niffler.db.model.userdata.UserDataEntity;

public class UserDataUserDAOHibernate extends JpaService implements UserDataUserDAO {
    public UserDataUserDAOHibernate() {
        super(EntityManagerFactoryProvider.INSTANCE.getDataSource(ServiceDB.USERDATA).createEntityManager());
    }

    @Override
    public UserDataEntity createUserInUserData(UserDataEntity user) {
        persist(user);
        return user;
    }

    @Override
    public UserDataEntity getUserFromUserDataByUsername(String username) {
        return em.createQuery("select u from UserDataEntity u where u.username=:username", UserDataEntity.class)
                .setParameter("username", username)
                .getSingleResult();
    }

    @Override
    public void updateUserInUserData(UserDataEntity user) {
        merge(user);
    }

    @Override
    public void deleteUserByUsernameInUserData(String username) {
        UserDataEntity user = getUserFromUserDataByUsername(username);
        remove(user);
    }
}
