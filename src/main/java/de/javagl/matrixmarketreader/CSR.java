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
 * Simple representation of a matrix in CSR format. This is only intended
 * as the return type of {@link MatrixMarketCSR#readCSR(java.io.InputStream)},
 * and thus, does not offer any form of encapsulation. Instances of this
 * class are <b>not</b> supposed to be used for anything except for 
 * using the fields to create instances of matrices from a proper
 * matrix library. 
 */
public class CSR
{
    /**
     * The number of rows
     */
    public int numRows;
    
    /**
     * The number of columns
     */
    public int numCols;
    
    /**
     * The values
     */
    public double values[];
    
    /**
     * The column indices
     */
    public int columnIndices[];
    
    /**
     * The row pointer indices
     */
    public int rowPointers[];
}