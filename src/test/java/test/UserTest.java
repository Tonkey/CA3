/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import entity.Role;
import entity.User;
import facades.UserFacade;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import security.IUserFacade;
import security.PasswordStorage;
import security.UserFacadeFactory;

/**
 *
 * @author Michael
 */
public class UserTest {

    private static EntityManager em;
    private static UserFacade facade;
    private static Map<String, String> properties;

    public UserTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        this.em = Persistence.createEntityManagerFactory("PU_TEST").createEntityManager();
        this.properties = new HashMap();
        Persistence.generateSchema("PU_TEST", properties);
        this.facade = new UserFacade(Persistence.createEntityManagerFactory("PU_TEST"));
        setupDerbyDataBase();
    }

    @After
    public void tearDown() {
    }

    @Test
    public void userTests() {
        
        System.out.println("Starting User Test no. 1.1 - login with user 'user' ...");
        
        
        
        System.out.println("Starting User Test no. 1.2 - login with user 'admin' ...");
        
        
        
        System.out.println("Starting User Test no. 1.3 - login with user 'user_admin' ...");

    }

    public void setupDerbyDataBase() {
        System.out.println("Setting up the Derby database 'CA3DBTEST' - adding: 'user' , 'admin' , 'user_admin' ...");
        try {
            em.getTransaction().begin();

            if (em.find(User.class, "user") == null) {

                Role userRole = new Role("User");
                Role adminRole = new Role("Admin");
                User user = new User("user", "test");
                user.addRole(userRole);
                User admin = new User("admin", "test");
                admin.addRole(adminRole);
                User both = new User("user_admin", "test");
                both.addRole(userRole);
                both.addRole(adminRole);

                em.persist(userRole);
                em.persist(adminRole);
                em.persist(user);
                em.persist(admin);
                em.persist(both);
            }
            em.getTransaction().commit();

        } catch (Exception ex) {
            Logger.getLogger(UserFacade.class.getName()).log(Level.SEVERE, null, ex);
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
        System.out.println("Derby database 'CA3DBTEST' is set up ...");
    }
}
