package utils;


import entities.Book;
import entities.Loan;
import entities.Role;
import entities.User;
import facades.BookFacade;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class SetupTestUsers {

  public static void setUpUsers() {

    EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
    EntityManager em = emf.createEntityManager();
      BookFacade bf = facades.BookFacade.getBookFacade(emf);
    // IMPORTAAAAAAAAAANT!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    // This breaks one of the MOST fundamental security rules in that it ships with default users and passwords
    // CHANGE the three passwords below, before you uncomment and execute the code below
    // Also, either delete this file, when users are created or rename and add to .gitignore
    // Whatever you do DO NOT COMMIT and PUSH with the real passwords

    User user = new User("user", "testuser");
    User admin = new User("admin", "testadmin");
    User both = new User("user_admin", "testuseradmin");
    Book book1 = new Book("BogTitle", "Jens", "gyldenlal", "1992");
    Book book2 = new Book("BogTitle2", "Jens2", "gyldenlal2", "19922");
    Book book3 = new Book("BogTitle3", "Jens3", "gyldenlal3", "19923");

    Loan loan1 = new Loan("Dummy1");
    Loan loan2 = new Loan("Dummy2");
    Loan loan3 = new Loan("Dummy3");
    
    if(admin.getUserPass().equals("test")||user.getUserPass().equals("test")||both.getUserPass().equals("test"))
      throw new UnsupportedOperationException("You have not changed the passwords");

    book1.addLoans(loan1);
    book2.addLoans(loan2);
    book3.addLoans(loan3);
    
    user.addLoans(loan1);
    admin.addLoans(loan2);
    both.addLoans(loan3);
    
    em.getTransaction().begin();
    Role userRole = new Role("user");
    Role adminRole = new Role("admin");
    user.addRole(userRole);
    admin.addRole(adminRole);
    both.addRole(userRole);
    both.addRole(adminRole);
    em.persist(userRole);
    em.persist(adminRole);
    em.persist(user);
    em.persist(admin);
    em.persist(both);
    em.persist(book1);
    em.persist(book2);
    em.persist(book3);
    em.getTransaction().commit();
    System.out.println("PW: " + user.getUserPass());
    System.out.println("Testing user with OK password: " + user.verifyPassword("test"));
    System.out.println("Testing user with wrong password: " + user.verifyPassword("test1"));
    System.out.println("Created TEST Users");

   
  }

}
