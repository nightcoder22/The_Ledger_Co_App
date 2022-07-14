package org.ledger.writer;

import org.ledger.model.Balance;

/**
 * Class responsible for handling output related events
 */
public class ConsoleWriter {

    /**
     * @param balance object
     * @param amountPaid amount paid by borrower till date
     * @param emiLeft no of emi's left
     */
    public static void writeToConsole( Balance balance, double amountPaid, double emiLeft ){
        System.out.println ( balance.displayBalance ( amountPaid, emiLeft ) );
    }

}
