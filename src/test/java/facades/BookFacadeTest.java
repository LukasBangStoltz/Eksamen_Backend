package facades;

import dto.BookDTO;
import dto.LoanBookDTO;
import dto.LoanDTO;
import entities.Book;
import entities.Loan;
import utils.EMF_Creator;
import entities.Role;
import entities.User;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import security.errorhandling.AuthenticationException;

//Uncomment the line below, to temporarily disable this test
public class BookFacadeTest {

    private static EntityManagerFactory emf;
    private static BookFacade facade;
    private static User user;
    private static User admin;
    private static User both;
    private static Book book;
    private static Book book2;
    private static Book book3;
    private static Book book4;

    public BookFacadeTest() {
    }

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = BookFacade.getBookFacade(emf);
        EntityManager em = emf.createEntityManager();
        try {
            //Delete existing users and roles to get a "fresh" database
            em.getTransaction().begin();
            em.createNativeQuery("DELETE FROM user_roles").executeUpdate();
            em.createNativeQuery("DELETE FROM roles").executeUpdate();
            em.createNativeQuery("DELETE FROM LOAN").executeUpdate();
            em.createNativeQuery("DELETE FROM BOOK").executeUpdate();
            em.createNativeQuery("DELETE FROM users").executeUpdate();
            em.getTransaction().commit();
            book = new Book(1, "testbog", "author", "gyldendal", "1999");
            book2 = new Book(2, "tittle", "authgors", "publishergyld", "29123");
            book3 = new Book(3, "testtest", "autrhorr", "gyldendal", "123123");
            book4 = new Book(4, "lånmig", "jaja", "gylden", "12312");

            User user = new User("user1", "andersen");

            User user2 = new User("user2", "larsen");

            Loan loan1 = new Loan("DUMMY1");
            Loan loan2 = new Loan("DUMMY2");
            Loan loan3 = new Loan("DUMMY3");

            book.addLoans(loan1);
            user.addLoans(loan1);

            em.getTransaction().begin();
            em.persist(user);
            em.persist(user2);
            em.persist(book);
            em.persist(book2);
            em.persist(book3);
            em.persist(book4);
            em.getTransaction().commit();
            //System.out.println("Saved test data to database");
        } finally {
            em.close();
        }
    }

    @AfterAll
    public static void tearDownClass() {
//        Clean up database after test is done or use a persistence unit with drop-and-create to start up clean on every test
    }

    // Setup the DataBase in a known state BEFORE EACH TEST
    //TODO -- Make sure to change the code below to use YOUR OWN entity class
    @BeforeEach
    public void setUp() {

    }

    @AfterEach
    public void tearDown() {
//        Remove any data after each test was run
    }

    // TODO: Delete or change this method 
    @Test
    public void testVerifyUser() throws AuthenticationException {
        User user = facade.getVeryfiedUser("user1", "andersen");
        assertEquals("user1", user.getUserName());
    }

    @Test
    public void testAddBook() {

        Book book = new Book(10, "title", "Authors", "publisher", "year");
        BookDTO bookDTO = new BookDTO(book);

        BookDTO newBook = facade.addBook(bookDTO);

        assertEquals("title", newBook.title);

    }

    @Test
    public void testRemoveBook() {
        BookDTO book = facade.removeBook(2);

        assertEquals(book.title, "tittle");

    }

    @Test
    public void testGetAllBooks() {
        List<BookDTO> bookList = facade.getAllBooks();

        assertEquals(5, bookList.size());
    }

    @Test
    public void testGetBookByTitle() throws Exception {
        BookDTO book = facade.getBookByTitle("testbog");

        assertEquals("testbog", book.title);
    }

    @Test
    public void testLoanABook() throws Exception {

        LoanBookDTO loanBook = new LoanBookDTO(3, "user1");

        LoanDTO loan = facade.loanABook(loanBook);

        assertEquals("DATODUMMY", loan.returnedDate);
    }

    @Test
    public void testGetAllLoans() {
        List<LoanDTO> loans = facade.getAllLoans("user1");

        assertEquals(2, loans.size());
    }
}
