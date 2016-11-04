/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import facades.UserFacade;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import org.junit.After;
import org.junit.AfterClass;
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
    public void userTests() {

        System.out.println("Starting User Test no. 1.1 - login with user 'user' ...");

    }
}
