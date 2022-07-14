package org.ledger.model;

/**
 * Balance model
 */
public class Balance {
    private String bankName;
    private String borrower;
    private int emiNo;

    public Balance ( String bankName, String borrower, int emiNo ) {
        this.bankName = bankName;
        this.borrower = borrower;
        this.emiNo = emiNo;
    }

    public String getBankName ( ) {
        return bankName;
    }

    public void setBankName ( String bankName ) {
        this.bankName = bankName;
    }

    public String getBorrower ( ) {
        return borrower;
    }

    public void setBorrower ( String borrower ) {
        this.borrower = borrower;
    }

    public int getEmiNo ( ) {
        return emiNo;
    }

    public void setEmiNo ( int emiNo ) {
        this.emiNo = emiNo;
    }

    public String displayBalance(double amountPaid, double yearLeft){
        return bankName + " " + borrower + " " + (int) amountPaid + " " + (int) yearLeft;
    }

    @Override
    public String toString ( ) {
        return "Balance{" +
                "bankName='" + bankName + '\'' +
                ", borrower='" + borrower + '\'' +
                ", emiNo=" + emiNo +
                '}';
    }
}
