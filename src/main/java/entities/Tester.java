/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import dto.LoanDTO;
import facades.BookFacade;
import java.util.ArrayList;
import static java.util.Calendar.HOUR;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author lukas
 */
public class Tester {

    public static void main(String[] args) throws Exception {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu");
        EntityManager em = emf.createEntityManager();
        BookFacade bookFacade = BookFacade.getBookFacade(emf);

        Book book = new Book("testbog", "author", "gyldendal", "1999");
        Book book2 = new Book("tittle", "authgors", "publishergyld", "29123");
        Book book3 = new Book("testtest", "autrhorr", "gyldendal", "123123");

        User user = new User("user1", "andersen");

        User user2 = new User("user2", "larsen");

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
        em.getTransaction().commit();

        LoanDTO loanTest = bookFacade.loanABook(3, user.getUserName());
        LoanDTO loanTest2 = bookFacade.loanABook(1, user2.getUserName());

    }
}
