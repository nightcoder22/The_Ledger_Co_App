package org.ledger;

import org.ledger.reader.FileProcessor;

import java.io.File;

public class App {
    public static void main ( String[] args ) {
        if ( args.length == 1 ) {
            File file = new File ( args[ 0 ] );
            FileProcessor fileReader = new FileProcessor ( );
            fileReader.processFile ( file );
        } else {
            System.out.println ( "Usage: java -jar jar_file_name input_file" );
        }
    }
}
