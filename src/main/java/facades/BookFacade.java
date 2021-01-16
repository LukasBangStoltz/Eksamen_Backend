package facades;

import dto.BookDTO;
import dto.LoanBookDTO;
import dto.LoanDTO;
import entities.Book;
import entities.Loan;
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
    public BookDTO getBookByIsbn(long isbn) throws Exception {
        EntityManager em = emf.createEntityManager();
        Book book;

        try {
            book = em.find(Book.class, isbn);

            if (book == null) {
                throw new Exception("Book not found");
            }

        } finally {
            em.close();
        }
        return new BookDTO(book);
    }

    @Override
    public LoanDTO loanABook(LoanBookDTO loanBookDTO) throws Exception {
        EntityManager em = emf.createEntityManager();

        Loan loan = new Loan("DATODUMMY");
        Book book;
        User user;
        try {
            book = em.find(Book.class, loanBookDTO.isbn);

            if (book == null) {
                throw new Exception("Book not found");
            }
            if (book.isIsAvalible() == false) {
                throw new Exception("Book not avalible");
            }

            user = em.find(User.class, loanBookDTO.userName);

            if (user == null) {
                throw new Exception("User not found");
            }
            book.addLoans(loan);
            user.addLoans(loan);
            book.setIsAvalible(false);
            em.getTransaction().begin();
            em.persist(loan);
            em.getTransaction().commit();

        } finally {
            em.close();
        }
        return new LoanDTO(loan);
    }

    @Override
    public BookDTO addBook(BookDTO bookDTO) {

        EntityManager em = emf.createEntityManager();
        Book book = new Book(bookDTO.isbn, bookDTO.title, bookDTO.authors, bookDTO.publisher, bookDTO.publishYear);
        try {

            em.getTransaction().begin();
            em.persist(book);

            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new BookDTO(book);
    }

    @Override
    public BookDTO removeBook(BookDTO bookDTO) {
        EntityManager em = emf.createEntityManager();
        Book book;
        try {
            book = em.find(Book.class, bookDTO.isbn);
            em.getTransaction().begin();

            for (Loan loan : book.getLoans()) {
                em.remove(loan);
            }

            em.remove(book);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new BookDTO(book);
    }

}
