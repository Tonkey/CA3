package test;

import com.google.gson.Gson;
import entity.Role;
import entity.User;
import facades.UserFacade;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

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
        em = Persistence.createEntityManagerFactory("PU_TEST").createEntityManager();
        properties = new HashMap();
        Persistence.generateSchema("PU_TEST", properties);
        facade = new UserFacade(Persistence.createEntityManagerFactory("PU_TEST"));
        setUpDatabase();
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void MultibleUserTests() {

        System.out.println("Starting Multible-Users - Test no. 1 ...");
        System.out.println("Starting Single-User - Test no. 1.1 - creating 3 new user - 'testuser1' - 'testuser2' - 'testuser3' ...");

        String name1 = "test1";
        String password1 = "test1";
        String name2 = "test2";
        String password2 = "test2";
        String name3 = "test3";
        String password3 = "test3";

        User testUser1 = new Gson().fromJson("{'userName': " + name1 + ", 'passwordHash': " + password1 + "}", User.class);
        User testUser2 = new Gson().fromJson("{'userName': " + name2 + ", 'passwordHash': " + password2 + "}", User.class);
        User testUser3 = new Gson().fromJson("{'userName': " + name3 + ", 'passwordHash': " + password3 + "}", User.class);
        assertEquals(facade.createNewUser(testUser1), "test1");
        assertEquals(facade.createNewUser(testUser2), "test2");
        assertEquals(facade.createNewUser(testUser3), "test3");

        System.out.println("Starting Single-User - Test no. 1.2 - 'Duplicate key value' for createUser in Database ...");
        assertEquals(facade.createNewUser(testUser1), null);

        System.out.println("Starting Single-User - Test no. 1.3 - 'getAllUsers' - from Database conmparing first name in arrayList ...");
        assertEquals(facade.getAllUsers().get(0).getUserName(), name1);

        System.out.println("Test no. 1 -  'Multible-Users' - finished");
    }

    @Test
    public void singleUserTests() {

        System.out.println("Starting Single-User - Test no. 2 ...");
        System.out.println("Starting Single-User - Test no. 2.1 - creating a new user - 'testuser' ...");

        String name = "test";
        String password = "test";

        User testUser = new Gson().fromJson("{'userName': " + name + ", 'passwordHash': " + password + "}", User.class);
        assertEquals(facade.createNewUser(testUser), "test");

        System.out.println("Starting Single-User - Test no. 2.2 - Login with 'testuser' ...");
        List<String> userList = new ArrayList();
        userList.add("User");
        assertEquals(facade.authenticateUser(name, password), userList);

        System.out.println("Starting Single-User - Test no. 2.3 - Get user by ID ...");
        assertEquals(name, facade.getUserByUserId(name).getUserName());

        System.out.println("Starting Single-User - Test no. 2.4 - delete testUser ...");
        assertTrue(facade.deleteUser(name));

        System.out.println("Single-User - Test no. 2 done.");
    }

    public static void setUpDatabase() {
        try {
            em.getTransaction().begin();
            Role admin = new Role("Admin");
            Role user = new Role("User");
            em.persist(admin);
            em.persist(user);
            em.getTransaction().commit();
        } catch (Exception e) {} finally {
            em.close();
        }
    }
}
