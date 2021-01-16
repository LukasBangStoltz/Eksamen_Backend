/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import dto.BookDTO;
import dto.LoanBookDTO;
import dto.LoanDTO;
import java.util.List;

/**
 *
 * @author lukas
 */
public interface BookInterface {

    public abstract List<BookDTO> getAllBooks();

    public abstract BookDTO getBookByTitle(String title)throws Exception;

    public abstract LoanDTO loanABook(LoanBookDTO loanBookDTO) throws Exception;
    
    public abstract BookDTO addBook(BookDTO bookDTO);
    
    public abstract BookDTO removeBook(BookDTO bookDTO);
   
    public abstract List<LoanDTO> getAllLoans(String userName);

}
