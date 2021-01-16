/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 *
 * @author lukas
 */
@Entity
public class Book implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long isbn;
    private String title;
    
    private String authors;
    private String publisher;
    private String publishYear;
    private boolean isAvalible;
    
    
    @OneToMany(mappedBy = "book")
    private List<Loan> loans;

    public Book(String title, String authors, String publisher, String publishYear  ) {
        this.title = title;
        this.authors = authors;
        this.publisher = publisher;
        this.publishYear = publishYear;
        this.loans = new ArrayList<>();
        this.isAvalible = true;
    }

    public Book() {
    }

    public Long getIsbn() {
        return isbn;
    }

    public void setIsbn(Long isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getPublishYear() {
        return publishYear;
    }

    public void setPublishYear(String publishYear) {
        this.publishYear = publishYear;
    }

    public List<Loan> getLoans() {
        return loans;
    }

    public void addLoans(Loan loan) {
        if(loan!=null){
            this.loans.add(loan);
            loan.setBook(this);
        }
        
        
    }

    public boolean isIsAvalible() {
        return isAvalible;
    }

    public void setIsAvalible(boolean isAvalible) {
        this.isAvalible = isAvalible;
    }
    
    
}
