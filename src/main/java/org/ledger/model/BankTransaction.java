package org.ledger.model;

import java.util.LinkedList;

/**
 * Bank Transaction model , representation of loan component
 */
public class BankTransaction {
    private String bank;
    private String borrower;
    private int principleAmount;
    private int period;
    private int interest;
    private double emiAmount;
    private LinkedList<Emi> emis;

    public BankTransaction ( String bank, String borrower, int principleAmount, int period, int interest ) {
        this.bank = bank;
        this.borrower = borrower;
        this.principleAmount = principleAmount;
        this.period = period;
        this.interest = interest;
        double totalAmount = principleAmount + ( ( principleAmount * period * interest ) / 100 );
        int noOfEmis = period * 12;
        this.emiAmount = Math.ceil ( totalAmount / noOfEmis );
        this.emis = new LinkedList<> ( );
        this.generateList ( );
    }

    /**
     * generate emi list for borrower.
     */
    private void generateList ( ) {
        int noOfEmis = this.period * 12;
        for ( int i = 0; i <= noOfEmis; i++ ) {
            Emi emi = new Emi ( this.emiAmount );
            if ( i == 0 ){
                emi.setEmiAmount ( 0 );
            }
            emi.setNoOfEmiLeft ( noOfEmis - i );
            this.getEmis ( ).add ( emi );
        }
    }

    /**
     * this method will modify emi list of borrower when lump sum payment is received.
     * @param index emi number on which lump sum payment was made
     */
    public void modifyList ( int index ) {
        Emi emi = this.getEmis ( ).get ( index );
        double lumpSumAmount = emi.getLumpSumAmount ( );
        double noOfEmi = Math.floor ( lumpSumAmount / this.emiAmount );
        double lastAmount = Math.ceil ( lumpSumAmount % this.emiAmount );
        for ( int i = 0; i < noOfEmi; i++ ) {
            this.getEmis ( ).removeLast ( );
        }
        if ( lastAmount > 0 ) {
            Emi newEmi = this.getEmis ( ).removeLast ( );
            newEmi.setEmiAmount ( newEmi.getEmiAmount ( ) - lastAmount );
            this.getEmis ( ).add ( newEmi );
        }
        for ( int j = index; j < this.getEmis ( ).size ( ); j++ ) {
            this.getEmis ( ).get ( j ).setNoOfEmiLeft ( this.getEmis ( ).size ( ) - j - 1 );
        }
    }

    /**
     * this method will calculate total amount paid by borrower till the given emi no (incl lump sum amount).
     * @param emiNo emi no till which total has to be calculated.
     * @return total of amount paid
     */
    public double amountPaid ( int emiNo ) {
        double total = 0;
        for ( int i = 0; i <= emiNo; i++ ) {
            Emi emi = this.getEmis ( ).get ( i );
            total = total + emi.getEmiAmount ( ) + emi.getLumpSumAmount ( );
        }
        return total;
    }

    /**
     * this method will calculate total no of emi left for the given emi no.
     * @param emiNo emi no till which emi left has to be calculated.
     * @return no of emi left
     */
    public int emisLeft ( int emiNo ) {
        return this.getEmis ( ).get ( emiNo ).getNoOfEmiLeft ( );
    }

    public String getBank ( ) {
        return bank;
    }

    public void setBank ( String bank ) {
        this.bank = bank;
    }

    public int getPrincipleAmount ( ) {
        return principleAmount;
    }

    public void setPrincipleAmount ( int principleAmount ) {
        this.principleAmount = principleAmount;
    }

    public int getPeriod ( ) {
        return period;
    }

    public void setPeriod ( int period ) {
        this.period = period;
    }

    public int getInterest ( ) {
        return interest;
    }

    public void setInterest ( int interest ) {
        this.interest = interest;
    }

    public String getBorrower ( ) {
        return borrower;
    }

    public void setBorrower ( String borrower ) {
        this.borrower = borrower;
    }

    public double getEmiAmount ( ) {
        return emiAmount;
    }

    public void setEmiAmount ( int emiAmount ) {
        this.emiAmount = emiAmount;
    }

    public void setEmiAmount ( double emiAmount ) {
        this.emiAmount = emiAmount;
    }

    public LinkedList<Emi> getEmis ( ) {
        return emis;
    }

    public void setEmis ( LinkedList<Emi> emis ) {
        this.emis = emis;
    }

    @Override
    public String toString ( ) {
        return "BankTransaction{" +
                "bank='" + bank + '\'' +
                ", borrower='" + borrower + '\'' +
                ", principleAmount=" + principleAmount +
                ", period=" + period +
                ", interest=" + interest +
                ", emiAmount=" + emiAmount +
                '}';
    }
}
