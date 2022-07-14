package org.ledger.reader;

import org.ledger.exception.InputParserException;
import org.ledger.exception.UserNotFoundException;
import org.ledger.model.Balance;
import org.ledger.model.BankTransaction;
import org.ledger.model.Emi;
import org.ledger.model.Payment;
import org.ledger.writer.ConsoleWriter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.function.Predicate;

/**
 * Class responsible for handling input related events and processing input
 */
public class FileProcessor {

    //In memory hash map to store bank transaction.
    private final HashMap<String, ArrayList<BankTransaction>> transactions = new HashMap<> ( );
    private static final String LOAN = "LOAN";
    private static final String PAYMENT = "PAYMENT";
    private static final String BALANCE = "BALANCE";

    /**
     * Reads file line by line.
     * @param file object
     */
    public void processFile ( File file ) {
        try {
            FileInputStream fileInputStream = new FileInputStream ( file );
            Scanner scanner = new Scanner ( fileInputStream );
            while ( scanner.hasNextLine ( ) ) {
                processLine ( scanner.nextLine ( ) );
            }
        } catch ( FileNotFoundException | InputParserException | UserNotFoundException e ) {
            throw new RuntimeException ( e );
        }

    }

    /**
     * Process line
     * @param line string
     * @throws InputParserException
     * @throws UserNotFoundException
     */
    private void processLine ( String line ) throws InputParserException, UserNotFoundException {
        String[] strings = line.split ( " " );
        switch ( strings[ 0 ] ) {
            case LOAN:
                processLoan ( getBankTransaction ( strings ) );
                break;
            case PAYMENT:
                processPayment ( getPayment ( strings ) );
                break;
            case BALANCE:
                processBalance ( getBalance ( strings ) );
                break;
            default:
                throw new InputParserException ( "Invalid operation" );
        }
    }

    /**
     * Process LOAN command and add loan related details for the borrower.
     * @param bankTransaction object
     */
    private void processLoan ( BankTransaction bankTransaction ) {
        String borrower = bankTransaction.getBorrower ( );
        ArrayList<BankTransaction> list;
        if ( transactions.containsKey ( borrower ) ) {
            list = transactions.get ( borrower );
        } else {
            list = new ArrayList<> ( );
        }
        list.add ( bankTransaction );
        transactions.put ( borrower, list );
    }

    /**
     * Process PAYMENT command and modify emi list of borrower
     * @param payment object
     * @throws UserNotFoundException
     */
    private void processPayment ( Payment payment ) throws UserNotFoundException {
        BankTransaction bankTransaction = getBankTransactionForBorrower ( payment.getBorrower ( ), payment.getBankName ( ) );
        Emi emi = new Emi ( bankTransaction.getEmiAmount ( ) );
        if ( payment.getEmiNo ( ) == 0 ){
            emi.setEmiAmount ( 0 );
        }
        emi.setLumpSumAmount ( payment.getLumpSumAmount ( ) );
        bankTransaction.getEmis ( ).set ( payment.getEmiNo ( ), emi );
        bankTransaction.modifyList ( payment.getEmiNo ( ) );
    }

    /**
     * Process BALANCE command and prints output
     * @param balance object
     * @throws UserNotFoundException
     */
    private void processBalance ( Balance balance ) throws UserNotFoundException {
        BankTransaction bankTransaction = getBankTransactionForBorrower ( balance.getBorrower ( ), balance.getBankName ( ) );
        double amountPaid = bankTransaction.amountPaid ( balance.getEmiNo ( ) ), emiLeft = bankTransaction.emisLeft ( balance.getEmiNo ( ) );
        ConsoleWriter.writeToConsole ( balance, amountPaid, emiLeft );
    }

    /**
     * Validate and return if borrower and bank name are present.
     * @param borrower borrower name
     * @param bankName bank name
     * @return Bank Transaction object
     * @throws UserNotFoundException
     */
    private BankTransaction getBankTransactionForBorrower ( String borrower, final String bankName ) throws UserNotFoundException {
        ArrayList<BankTransaction> bankTransactions = transactions.get ( borrower );
        if ( bankTransactions == null ) {
            throw new UserNotFoundException ( borrower + " not found." );
        }
        BankTransaction bankTransaction = bankTransactions.stream ( ).filter ( new Predicate<BankTransaction> ( ) {
            @Override
            public boolean test ( BankTransaction bankTransaction1 ) {
                return bankTransaction1.getBank ( ).equals ( bankName );
            }
        } ).findAny ( ).orElse ( null );
        if ( bankTransaction == null ) {
            throw new UserNotFoundException ( "Bank transaction not found for " + borrower );
        }
        return bankTransaction;
    }

    /**
     * generate Bank transaction object from line
     * @param strings line
     * @return Bank transaction object
     * @throws InputParserException
     */
    private BankTransaction getBankTransaction ( String[] strings ) throws InputParserException {
        if ( strings.length != 6 ) {
            throw new InputParserException ( "Found invalid LOAN record in file." );
        }
        return new BankTransaction ( strings[ 1 ], strings[ 2 ], Integer.parseInt ( strings[ 3 ] ), Integer.parseInt ( strings[ 4 ] ), Integer.parseInt ( strings[ 5 ] ) );
    }

    /**
     * generate Payment object from line
     * @param strings line
     * @return Payment object
     * @throws InputParserException
     */
    private Payment getPayment ( String[] strings ) throws InputParserException {
        if ( strings.length != 5 ) {
            throw new InputParserException ( "Found invalid PAYMENT record in file." );
        }
        return new Payment ( strings[ 1 ], strings[ 2 ], Integer.parseInt ( strings[ 3 ] ), Integer.parseInt ( strings[ 4 ] ) );
    }

    /**
     * generate Balance object from line
     * @param strings line
     * @return Balance object
     * @throws InputParserException
     */
    private Balance getBalance ( String[] strings ) throws InputParserException {
        if ( strings.length != 4 ) {
            throw new InputParserException ( "Found invalid BALANCE record in file." );
        }
        return new Balance ( strings[ 1 ], strings[ 2 ], Integer.parseInt ( strings[ 3 ] ) );
    }

}
