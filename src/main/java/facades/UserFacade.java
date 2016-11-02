package facades;

import entity.Role;
import security.IUserFacade;
import entity.User;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import security.IUser;
import security.PasswordStorage;

public class UserFacade implements IUserFacade {

    EntityManagerFactory emf;

    public UserFacade(EntityManagerFactory emf) {
        this.emf = emf;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    @Override
    public IUser getUserByUserId(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(User.class, id);
        } finally {
            em.close();
        }
    }

    /*
  Return the Roles if users could be authenticated, otherwise null
     */
    @Override
    public List<String> authenticateUser(String userName, String password) {
        IUser user = getUserByUserId(userName);
        try {
            return user != null && PasswordStorage.verifyPassword(password, user.getPassword()) ? user.getRolesAsStrings() : null;
        } catch (PasswordStorage.CannotPerformOperationException | PasswordStorage.InvalidHashException ex) {
            Logger.getLogger(UserFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public String createNewUser(User u) {
        EntityManager em = getEntityManager();
        try {
            Role userRole = new Role("User");
            u.addRole(userRole);
            //hashes the password as the Gson rest bypasses the constructor!
            u.setPassword(u.getPassword());
            em.getTransaction().begin();
            em.persist(u);
            em.getTransaction().commit();
            return u.getUserName();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            em.close();
        }
    }

    @Override
    public List<User> getAllUsers() {
        EntityManager em = getEntityManager();
        try {
            List<User> users;

            em.getTransaction().begin();
            Query q = em.createQuery("SELECT u FROM SEED_USER u", User.class);
            users = q.getResultList();
            em.getTransaction().commit();

            return users;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            em.close();
        }
    }

    @Override
    public boolean deleteUser(String id) {
        EntityManager em = getEntityManager();
        try {

            em.getTransaction().begin();
            em.remove(em.find(User.class, id));
            em.getTransaction().commit();

            return true;
        } catch (Exception e) {
            em.getTransaction().rollback();
            return false;
        } finally {
            em.close();
        }
    }

}
