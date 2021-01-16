package facades;

import dto.BookDTO;
import dto.LoanDTO;
import entities.Book;
import entities.User;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import security.errorhandling.AuthenticationException;
import utils.BookInterface;

/**
 * @author lam@cphbusiness.dk
 */
public class BookFacade implements BookInterface {

    private static EntityManagerFactory emf;
    private static BookFacade instance;

    private BookFacade() {
    }

    /**
     *
     * @param _emf
     * @return the instance of this facade.
     */
    public static BookFacade getBookFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new BookFacade();
        }
        return instance;
    }

    public User getVeryfiedUser(String username, String password) throws AuthenticationException {
        EntityManager em = emf.createEntityManager();
        User user;
        try {
            user = em.find(User.class, username);
            if (user == null || !user.verifyPassword(password)) {
                throw new AuthenticationException("Invalid user name or password");
            }
        } finally {
            em.close();
        }
        return user;
    }

    @Override
    public List<BookDTO> getAllBooks() {
        EntityManager em = emf.createEntityManager();
        List<Book> bookList;
        List<BookDTO> bookListDTO = new ArrayList();
        try {
            Query query = em.createQuery("SELECT b FROM Book b");
            bookList = query.getResultList();

            for (Book book : bookList) {
                bookListDTO.add(new BookDTO(book));
            }

        } finally {
            em.close();
        }
        return bookListDTO;
    }

    @Override
    public BookDTO getBookByIsbn(long isbn) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public LoanDTO loanABook(long isbn) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
