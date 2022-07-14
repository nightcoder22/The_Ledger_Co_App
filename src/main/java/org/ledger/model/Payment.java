package org.ledger.model;

/**
 * Payment model
 */
public class Payment {
    private String bankName;
    private String borrower;
    private int lumpSumAmount;
    private int emiNo;

    public Payment ( String bankName, String borrower, int lumpSumAmount, int emiNo ) {
        this.bankName = bankName;
        this.borrower = borrower;
        this.lumpSumAmount = lumpSumAmount;
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

    public int getLumpSumAmount ( ) {
        return lumpSumAmount;
    }

    public void setLumpSumAmount ( int lumpSumAmount ) {
        this.lumpSumAmount = lumpSumAmount;
    }

    public int getEmiNo ( ) {
        return emiNo;
    }

    public void setEmiNo ( int emiNo ) {
        this.emiNo = emiNo;
    }

    @Override
    public String toString ( ) {
        return "Payment{" +
                "bankName='" + bankName + '\'' +
                ", borrower='" + borrower + '\'' +
                ", lumpSumAmount=" + lumpSumAmount +
                ", emiNo=" + emiNo +
                '}';
    }
}
