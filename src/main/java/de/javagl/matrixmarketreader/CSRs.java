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

/**
 * Methods to create {@link CSR} instances
 */
class CSRs
{
    /**
     * Create a new {@link CSR} from the given data
     * 
     * @param matrixDescription The {@link MatrixDescription}
     * @param data The matrix data
     * @param epsilon The epsilon - only values that are greater than
     * or equal to this value will be considered as non-zero
     * @return The {@link CSR}
     */
    static CSR createCSR(
        MatrixDescription matrixDescription, double data[], double epsilon)
    {
        // Can't use the numNonZeros of matrix, because it may be symmetric....
        int numNonZeros = 0;
        for (int i=0; i<data.length; i++)
        {
            if (Math.abs(data[i]) >= epsilon)
            {
                numNonZeros++;
            }
        }
        int numRows = matrixDescription.getNumRows();
        int numCols = matrixDescription.getNumCols();
        CSR csr = new CSR();
        csr.numRows = numRows;
        csr.numCols = numCols;
        csr.values = new double[numNonZeros];
        csr.columnIndices = new int[numNonZeros];
        csr.rowPointers = new int[numRows + 1];
        csr.rowPointers[csr.rowPointers.length - 1] = numNonZeros;
        int index = 0;
        for (int r = 0; r < numRows; r++)
        {
            boolean firstColumn = true;
            for (int c = 0; c < numCols; c++)
            {
                double value = data[r + c * numRows];
                if (Math.abs(value) >= epsilon)
                {
                    if (firstColumn)
                    {
                        csr.rowPointers[r] = index;
                        firstColumn = false;
                    }
                    csr.values[index] = value;
                    csr.columnIndices[index] = c;
                    index++;
                }
            }
        }
        return csr;
    }
}
