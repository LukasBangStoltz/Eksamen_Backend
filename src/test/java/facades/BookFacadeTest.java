package facades;

import utils.EMF_Creator;
import entities.Role;
import entities.User;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import security.errorhandling.AuthenticationException;

//Uncomment the line below, to temporarily disable this test
@Disabled
public class BookFacadeTest {

    private static EntityManagerFactory emf;
    private static BookFacade facade;
    private static User user;
    private static User admin;
    private static User both;

    public BookFacadeTest() {
    }

    @BeforeAll
    public static void setUpClass() {
       emf = EMF_Creator.createEntityManagerFactoryForTest();
       facade = BookFacade.getBookFacade(emf);
    }

    @AfterAll
    public static void tearDownClass() {
//        Clean up database after test is done or use a persistence unit with drop-and-create to start up clean on every test
    }

    // Setup the DataBase in a known state BEFORE EACH TEST
    //TODO -- Make sure to change the code below to use YOUR OWN entity class
    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            //Delete existing users and roles to get a "fresh" database

            Role userRole = new Role("user");
            Role adminRole = new Role("admin");
            user = new User("user", "test");
            user.addRole(userRole);
            admin = new User("admin", "test");
            admin.addRole(adminRole);
            both = new User("user_admin", "test");
            both.addRole(userRole);
            both.addRole(adminRole);
            em.persist(userRole);
            em.persist(adminRole);
            em.persist(user);
            em.persist(admin);
            em.persist(both);
            //System.out.println("Saved test data to database");
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
    @AfterEach
    public void tearDown() {
//        Remove any data after each test was run
    }

    // TODO: Delete or change this method 
    @Test
    public void testVerifyUser() throws AuthenticationException {
        User user = facade.getVeryfiedUser("admin", "test");
        assertEquals("admin", admin.getUserName());
    }

}
