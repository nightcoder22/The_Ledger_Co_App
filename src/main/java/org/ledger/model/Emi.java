package org.ledger.model;

/**
 * Emi model
 */
public class Emi {
    double emiAmount;
    double lumpSumAmount;
    int noOfEmiLeft;

    public Emi ( double emiAmount ) {
        this.emiAmount = emiAmount;
    }

    public double getEmiAmount ( ) {
        return emiAmount;
    }

    public void setEmiAmount ( double emiAmount ) {
        this.emiAmount = emiAmount;
    }

    public double getLumpSumAmount ( ) {
        return lumpSumAmount;
    }

    public void setLumpSumAmount ( double lumpSumAmount ) {
        this.lumpSumAmount = lumpSumAmount;
    }

    public int getNoOfEmiLeft ( ) {
        return noOfEmiLeft;
    }

    public void setNoOfEmiLeft ( int noOfEmiLeft ) {
        this.noOfEmiLeft = noOfEmiLeft;
    }
}
