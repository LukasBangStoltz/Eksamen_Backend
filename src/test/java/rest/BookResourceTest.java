package rest;

import entities.Book;
import entities.User;
import utils.EMF_Creator;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import io.restassured.parsing.Parser;
import java.net.URI;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.UriBuilder;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import static org.hamcrest.Matchers.equalTo;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
//Uncomment the line below, to temporarily disable this test


public class BookResourceTest {

    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";

    static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();
    private static HttpServer httpServer;
    private static EntityManagerFactory emf;

    static HttpServer startServer() {
        ResourceConfig rc = ResourceConfig.forApplication(new ApplicationConfig());
        return GrizzlyHttpServerFactory.createHttpServer(BASE_URI, rc);
    }

    @BeforeAll
    public static void setUpClass() {
        //This method must be called before you request the EntityManagerFactory
        EMF_Creator.startREST_TestWithDB();
        emf = EMF_Creator.createEntityManagerFactoryForTest();

        httpServer = startServer();
        //Setup RestAssured
        RestAssured.baseURI = SERVER_URL;
        RestAssured.port = SERVER_PORT;
        RestAssured.defaultParser = Parser.JSON;
        EntityManager em = emf.createEntityManager();
        
        em.getTransaction().begin();
        em.createNativeQuery("DELETE FROM user_roles").executeUpdate();
        em.createNativeQuery("DELETE FROM roles").executeUpdate();
        em.createNativeQuery("DELETE FROM LOAN").executeUpdate();
        em.createNativeQuery("DELETE FROM BOOK").executeUpdate();
        em.createNativeQuery("DELETE FROM users").executeUpdate();
        em.getTransaction().commit();
        
        Book book = new Book(1, "testbog", "author", "gyldendal", "1999");
        Book book2 = new Book(2, "tittle", "authgors", "publishergyld", "29123");
        Book book3 = new Book(3, "testtest", "autrhorr", "gyldendal", "123123");
        Book book4 = new Book(4, "lånmig", "jaja", "gylden", "12312");

        User user = new User("user1", "andersen");

        User user2 = new User("user2", "larsen");
        long test = 1;
//        book.addLoans(loan);
//        book2.addLoans(loan2);
//        user.addLoans(loan);
//        user.addLoans(loan2);
//        book2.setIsAvalible(false);
//        book.setIsAvalible(false);
        em.getTransaction().begin();
        em.persist(user);
        em.persist(user2);
        em.persist(book);
        em.persist(book2);
        em.persist(book3);
        em.persist(book4);
        em.getTransaction().commit();
        
        
    }

    @AfterAll
    public static void closeTestServer() {
        //System.in.read();

        //Don't forget this, if you called its counterpart in @BeforeAll
        EMF_Creator.endREST_TestWithDB();
        httpServer.shutdownNow();
    }

    // Setup the DataBase (used by the test-server and this test) in a known state BEFORE EACH TEST
    //TODO -- Make sure to change the EntityClass used below to use YOUR OWN (renamed) Entity class
//    @BeforeEach
//    public void setUp() {
//        EntityManager em = emf.createEntityManager();
//        r1 = new RenameMe("Some txt", "More text");
//        r2 = new RenameMe("aaa", "bbb");
//        try {
//            em.getTransaction().begin();
//            em.createNamedQuery("RenameMe.deleteAllRows").executeUpdate();
//            em.persist(r1);
//            em.persist(r2);
//            em.getTransaction().commit();
//        } finally {
//            em.close();
//        }
//    }

    @Test
    public void testServerIsUp() {
        given().when().get("/books").then().statusCode(200);
    }

    //This test assumes the database contains two rows
    @Test
    public void testDummyMsg() throws Exception {
        given()
                .contentType("application/json")
                .get("/books/").then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("msg", equalTo("Hello anonymous"));
    }

    
    
    
    
    
    
    
    
}
