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

import java.io.IOException;
import java.io.InputStream;

/**
 * Methods to read {@link CSR} data from a MatrixMarket input stream
 */
public class MatrixMarketCSR
{
    /**
     * Read the given MatrixMarket input stream, and return the result
     * as a {@link CSR}. Note that this will internally create a
     * dense matrix from the read data. All this was never intended
     * to be used for more than some basic tests...
     * 
     * @param inputStream The input stream
     * @return The {@link CSR}
     * @throws IOException If an IO-error occurs
     */
    public static CSR readCSR(InputStream inputStream) throws IOException
    {
        DefaultCallback c = new DefaultCallback();
        MatrixMarketReader.read(inputStream, c);
        final double epsilon = 1e-8;
        return CSRs.createCSR(
            c.getMatrixDescription(), c.getArray(), epsilon);
    }
    
    /**
     * Private constructor to prevent instantiation
     */
    private MatrixMarketCSR()
    {
        // Private constructor to prevent instantiation
    }
}
