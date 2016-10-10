/*
 * MatrixMarketReader - a simple reader for MatrixMarket files
 *
 * Copyright (c) 2015-2015 Marco Hutter - http://www.javagl.de
 *
 * Permission is hereby granted, free of charge, to any person
 * obtaining a copy of this software and associated documentation
 * files (the "Software"), to deal in the Software without
 * restriction, including without limitation the rights to use,
 * copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following
 * conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 */
package de.javagl.matrixmarketreader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;

/**
 * Simple implementation of a reader for MatrixMarket files
 */
public class MatrixMarketReader
{
    /**
     * Read the MatrixMarket data from the given input stream, and notify
     * the given callback about the elements that are read.<br>
     * <br>
     * <br>
     * Only the {@link Format#COORDINATE} format is supported.
     * 
     * @param inputStream The input stream
     * @param callback The callback. May not be <code>null</code>
     * @throws IOException If an IO-error occurs
     * @throws NullPointerException If the given callback is <code>null</code>
     */
    public static void read(InputStream inputStream, Callback callback)
        throws IOException
    {
        BufferedReader br = 
            new BufferedReader(new InputStreamReader(inputStream));
        MatrixDescription matrixDescription = new MatrixDescription();
        
        String line = null;
        boolean firstLine = true;
        boolean foundSize = false;
        while (true)
        {
            line = br.readLine();
            if (line == null)
            {
                break;
            }
            // System.out.println("Read "+line);
            if (firstLine)
            {
                String tokens[] = line.split("\\s+");
                if (tokens.length != 5)
                {
                    throw new IOException(
                        "Expected 5 tokens in the first line, but found "
                        + tokens.length + ": " + line);
                }
                validateFirstToken(tokens[0]);
                validateObject(tokens[1]);
                matrixDescription.setFormat(parseFormat(tokens[2]));

                // TODO The ARRAY format should also be supported...
                if (matrixDescription.getFormat() != Format.COORDINATE)
                {
                    throw new IOException(
                        "Only COORDINATE format is supported");
                }

                matrixDescription.setField(parseField(tokens[3]));
                matrixDescription.setSymmetry(parseSymmetry(tokens[4]));
                firstLine = false;
                continue;
            }
            
            line = line.trim();
            if (line.isEmpty())
            {
                continue;
            }
            if (line.startsWith("%"))
            {
                continue;
            }

            if (!foundSize)
            {
                initSize(matrixDescription, line);
                foundSize = true;
                callback.startMatrix(matrixDescription);
                continue;
            }
            
            switch (matrixDescription.getField())
            {
                case REAL:
                    processReal(br, line, callback);
                    break;
                case COMPLEX:
                    processComplex(br, line, callback);
                    break;
                case INTEGER:
                    processInteger(br, line, callback);
                    break;
                case PATTERN:
                    processPattern(br, line, callback);
                    break;

                default:
                    // Should never happen:
                    throw new AssertionError(
                        "Invalid field " + matrixDescription.getField());

            }
        }
    }
    
    /**
     * Process a {@link Field#REAL} value from the current line
     * 
     * @param br The buffered reader
     * @param currentLine The current line
     * @param callback The {@link Callback}
     * @throws IOException If an IO-error occurs
     */
    private static void processReal(
        BufferedReader br, String currentLine, 
        Callback callback) throws IOException
    {
        boolean firstLine = true;
        String line = null;
        while (true)
        {
            if (firstLine)
            {
                line = currentLine;
                firstLine = false;
            }
            else
            {
                line = br.readLine();
            }
            if (line == null)
            {
                break;
            }
            line = line.trim();
            if (line.isEmpty())
            {
                continue;
            }
            if (line.startsWith("%"))
            {
                continue;
            }
            
            String tokens[] = line.split("\\s+");
            if (tokens.length != 3)
            {
                System.out.println(Arrays.toString(tokens));
                throw new IOException("Expected matrix entry of the form "
                    + "\"rowIndex columnIndex value\", " 
                    + "but found " + line);
            }
            int row = parseInt(tokens[0]) - 1;
            int col = parseInt(tokens[1]) - 1;
            double value = parseDouble(tokens[2]);
            callback.setMatrixElement(row, col, value, Double.NaN);
        }
    }

    /**
     * Process a {@link Field#COMPLEX} value from the current line
     * 
     * @param br The buffered reader
     * @param currentLine The current line
     * @param callback The {@link Callback}
     * @throws IOException If an IO-error occurs
     */
    private static void processComplex(
        BufferedReader br, String currentLine, 
        Callback callback) throws IOException
    {
        boolean firstLine = true;
        String line = null;
        while (true)
        {
            if (firstLine)
            {
                line = currentLine;
                firstLine = false;
            }
            else
            {
                line = br.readLine();
            }
            if (line == null)
            {
                break;
            }
            line = line.trim();
            if (line.isEmpty())
            {
                continue;
            }
            if (line.startsWith("%"))
            {
                continue;
            }
            
            String tokens[] = line.split("\\s+");
            if (tokens.length != 4)
            {
                throw new IOException("Expected matrix entry of the form "
                    + "\"rowIndex columnIndex realValue imagValue\", "
                    + "but found " + line);
            }
            int row = parseInt(tokens[0]) - 1;
            int col = parseInt(tokens[1]) - 1;
            double value0 = parseDouble(tokens[2]);
            double value1 = parseDouble(tokens[3]);
            callback.setMatrixElement(row, col, value0, value1);
            line = br.readLine();
        }
    }
    
    
    /**
     * Process a {@link Field#INTEGER} value from the current line
     * 
     * @param br The buffered reader
     * @param currentLine The current line
     * @param callback The {@link Callback}
     * @throws IOException If an IO-error occurs
     */
    private static void processInteger(
        BufferedReader br, String currentLine, 
        Callback callback) throws IOException
    {
        boolean firstLine = true;
        String line = null;
        while (true)
        {
            if (firstLine)
            {
                line = currentLine;
                firstLine = false;
            }
            else
            {
                line = br.readLine();
            }
            if (line == null)
            {
                break;
            }
            line = line.trim();
            if (line.isEmpty())
            {
                continue;
            }
            if (line.startsWith("%"))
            {
                continue;
            }

            String tokens[] = line.split("\\s+");
            if (tokens.length != 3)
            {
                throw new IOException("Expected matrix entry of the form "
                    + "\"rowIndex columnIndex value\", " 
                    + "but found " + line);
            }
            int row = parseInt(tokens[0]) - 1;
            int col = parseInt(tokens[1]) - 1;
            int value = parseInt(tokens[2]);
            callback.setMatrixElement(row, col, value, 0.0);
            line = br.readLine();
        }
    }
    
    /**
     * Process a {@link Field#PATTERN} value from the current line
     * 
     * @param br The buffered reader
     * @param currentLine The current line
     * @param callback The {@link Callback}
     * @throws IOException If an IO-error occurs
     */
    private static void processPattern(
        BufferedReader br, String currentLine, 
        Callback callback) throws IOException
    {
        // What else should be done here?
        processReal(br, currentLine, callback);
    }
    
    
    /**
     * Initialize the size in the given {@link MatrixDescription} from
     * the given line
     * 
     * @param matrixDescription The {@link MatrixDescription}
     * @param line The line
     * @throws IOException If an IO-error occurs
     */
    private static void initSize(
        MatrixDescription matrixDescription, String line) throws IOException
    {
        String tokens[] = line.split("\\s+");
        if (matrixDescription.getFormat() == Format.COORDINATE)
        {
            if (tokens.length != 3)
            {
                throw new IOException(
                    "For COORDINATE format, size must be of the form"
                    + "\"numRows numCols numNonZeros\", but found " + line);
            }
            int numRows = parseInt(tokens[0]);
            int numCols = parseInt(tokens[1]);
            int numNonZeros = parseInt(tokens[2]);
            matrixDescription.setSize(numRows, numCols, numNonZeros);
        }
        else if (matrixDescription.getFormat() == Format.ARRAY)
        {
            if (tokens.length != 2)
            {
                throw new IOException(
                    "For COORDINATE format, size must be of the form"
                    + "\"numRows numCols\", but found " + line);
            }
            int numRows = parseInt(tokens[0]);
            int numCols = parseInt(tokens[1]);
            int numNonZeros = numRows * numCols;
            matrixDescription.setSize(numRows, numCols, numNonZeros);
        }
        else
        {
            // May never happen
            throw new IOException("No matrix format found");

        }
    }
    
    /**
     * Parse an int from the given string and return it
     * 
     * @param s The string
     * @return The result
     * @throws IOException If the string can not be parsed
     */
    private static int parseInt(String s) throws IOException
    {
        try
        {
            return Integer.parseInt(s);
        }
        catch (NumberFormatException e)
        {
            throw new IOException(e);
        }
    }
    
    /**
     * Parse a double from the given string and return it
     * 
     * @param s The string
     * @return The result
     * @throws IOException If the string can not be parsed
     */
    private static double parseDouble(String s) throws IOException
    {
        try
        {
            return Double.parseDouble(s);
        }
        catch (NumberFormatException e)
        {
            throw new IOException(e);
        }
    }

    /**
     * Validate that the given token is "%%MatrixMarket", ignoring the case
     * 
     * @param firstToken The token
     * @throws IOException If the token is not valid
     */
    private static void validateFirstToken(String firstToken) 
        throws IOException
    {
        if (!firstToken.equalsIgnoreCase("%%MatrixMarket"))
        {
            throw new IOException(
                "Expected \"%%MatrixMarket\", found " + firstToken);
        }
    }

    /**
     * Validate that the given string is "matrix", ignoring the case
     * 
     * @param objectString The token
     * @throws IOException If the token is not valid
     */
    private static void validateObject(String objectString) 
        throws IOException
    {
        if (!objectString.equalsIgnoreCase("matrix"))
        {
            throw new IOException(
                "Expected \"matrix\", found " + objectString);
        }
    }
    
    /**
     * Parse a {@link Format} from the given string
     * 
     * @param s The string
     * @return The {@link Format}
     * @throws IOException If the string can not be parsed
     */
    private static Format parseFormat(String s)
        throws IOException
    {
        try
        {
            return Format.valueOf(s.toUpperCase());
        }
        catch (IllegalArgumentException e)
        {

            throw new IOException("Expected one of "
                + Arrays.toString(Format.values()) + ", found " + s);
        }
    }
    
    /**
     * Parse a {@link Field} from the given string
     * 
     * @param s The string
     * @return The {@link Field}
     * @throws IOException If the string can not be parsed
     */
    private static Field parseField(String s)
        throws IOException
    {
        try
        {
            return Field.valueOf(s.toUpperCase());
        }
        catch (IllegalArgumentException e)
        {

            throw new IOException("Expected one of "
                + Arrays.toString(Field.values()) + ", found " + s);
        }
    }

    /**
     * Parse a {@link Symmetry} from the given string
     * 
     * @param s The string
     * @return The {@link Symmetry}
     * @throws IOException If the string can not be parsed
     */
    private static Symmetry parseSymmetry(String s)
        throws IOException
    {
        try
        {
            return Symmetry.valueOf(s.toUpperCase());
        }
        catch (IllegalArgumentException e)
        {

            throw new IOException("Expected one of "
                + Arrays.toString(Symmetry.values()) + ", found " + s);
        }
    }

    /**
     * Private constructor to prevent instantiation
     */
    private MatrixMarketReader()
    {
        // Private constructor to prevent instantiation
    }
}
