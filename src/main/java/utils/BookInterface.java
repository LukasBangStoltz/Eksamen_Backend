/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import dto.BookDTO;
import dto.LoanDTO;
import java.util.List;

/**
 *
 * @author lukas
 */
public interface BookInterface {

    public abstract List<BookDTO> getAllBooks();

    public abstract BookDTO getBookByIsbn(long isbn);

    public abstract LoanDTO loanABook(long isbn);

}
