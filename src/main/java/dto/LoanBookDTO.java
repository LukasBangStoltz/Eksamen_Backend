/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

/**
 *
 * @author lukas
 */
public class LoanBookDTO {

    public long isbn;
    public String userName;

    public LoanBookDTO(long isbn, String userName) {
        this.isbn = isbn;
        this.userName = userName;
    }

}
