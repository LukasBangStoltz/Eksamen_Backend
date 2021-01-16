/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import entities.Loan;
import java.util.Date;

/**
 *
 * @author lukas
 */
public class LoanDTO {

    public Date checkoutDate;
    public Date dueDate;
    public String returnedDate;
    
    
    public LoanDTO(Loan loan){
        this.checkoutDate = loan.getCheckoutDate();
        this.dueDate = loan.getDueDate();
        this.returnedDate = loan.getReturnedDate();
    }
}
