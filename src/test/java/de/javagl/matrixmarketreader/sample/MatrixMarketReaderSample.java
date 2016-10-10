package de.javagl.matrixmarketreader.sample;

import java.io.File;
import java.io.FileInputStream;
import java.util.Arrays;

import de.javagl.matrixmarketreader.CSR;
import de.javagl.matrixmarketreader.MatrixDescription;
import de.javagl.matrixmarketreader.MatrixMarketCSR;
import de.javagl.matrixmarketreader.MatrixMarketReader;
import de.javagl.matrixmarketreader.Callback;
import de.javagl.matrixmarketreader.PrintingCallback;

/**
 * A basic sample/test for the matrix market reader 
 */
@SuppressWarnings("javadoc")
public class MatrixMarketReaderSample
{
    public static void main(String[] args) throws Exception
    {
        String path = "src/test/resources/";
        String fileName = null;
        //fileName = "gr_900_900_crg.mtx";
        //fileName = "lap2D_5pt_n100.mtx";
        fileName = "lap3D_7pt_n20.mtx";
        FileInputStream inputStream =
            new FileInputStream(new File(path + fileName));
        Callback matrixMarketReaderCallback = new PrintingCallback();
        MatrixMarketReader.read(inputStream, matrixMarketReaderCallback);

        MatrixDescription m = matrixMarketReaderCallback.getMatrixDescription();
        System.out.println("Finished " + m);

        CSR csr = MatrixMarketCSR.readCSR(
            new FileInputStream(new File(path + fileName)));
        System.out.println("Done");
        System.out.println("Column indices: " + 
            Arrays.toString(csr.columnIndices));
        System.out.println("Row pointers: " + 
            Arrays.toString(csr.rowPointers));
        System.out.println("Values: " + 
            Arrays.toString(csr.values));
    }
    

}
