/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import facades.BookFacade;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author lukas
 */
public class Tester {
    public static void main(String[] args) {
         EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu");
        EntityManager em = emf.createEntityManager();
        BookFacade bookFacade = BookFacade.getBookFacade(emf);
        
        
        
        
        Book book = new Book("testbog","author" , "gyldendal", "1999", true);
        
        User user = new User("nuel", "andersen");
        
        Loan loan = new Loan("i dag", "en uge", "test");
        Loan loan2 = new Loan("i dag", "to uge", "test");
        
        Book book2 = new Book("tittle", "authgors", "publishergyld", "29123", true);
        
        
        book.addLoans(loan);
        book2.addLoans(loan2);
        user.addLoans(loan);
        user.addLoans(loan2);
        book2.setIsAvalible(false);
        book.setIsAvalible(false);
        
        em.getTransaction().begin();
        em.persist(user);
        em.persist(book);
        em.persist(book2);
        em.getTransaction().commit();
        
        System.out.println(bookFacade.getAllBooks());
    }
}
