/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import entities.Book;

/**
 *
 * @author lukas
 */
public class BookDTO {

    public long isbn;
    public String title;
    public String authors;
    public String publisher;
    public String publishYear;
    public boolean isAvalible;

    public BookDTO(Book book) {
        this.isbn = book.getIsbn();
        this.title = book.getTitle();
        this.authors = book.getAuthors();
        this.publisher = book.getPublisher();
        this.publishYear = book.getPublishYear();
        this.isAvalible = book.isIsAvalible();
    }

}
