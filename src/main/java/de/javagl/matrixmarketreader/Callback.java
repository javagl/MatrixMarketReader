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
 * Interface for classes that may receive the data that is read by
 * a {@link MatrixMarketReader}
 */
public interface Callback
{
    /**
     * Will be called when the parsing process started for a matrix
     * with the given {@link MatrixDescription}
     * 
     * @param matrixDescription The {@link MatrixDescription}
     */
    void startMatrix(MatrixDescription matrixDescription);
   
    /**
     * Returns the {@link MatrixDescription} that was previously passed
     * to {@link #startMatrix(MatrixDescription)}, or <code>null</code>
     * if no matrix was started yet.
     * 
     * @return The {@link MatrixDescription}
     */
    MatrixDescription getMatrixDescription();
    
    
    /**
     * Set the specified element of the matrix
     * 
     * @param rowIndexZeroBased The (zero-based) row index
     * @param columnIndexZeroBased The (zero-based) column index
     * @param value0 The real value
     * @param value1 The imaginary value. If the matrix is a 
     * real matrix, then this will be <code>NaN</code>.
     */
    void setMatrixElement(
        int rowIndexZeroBased, int columnIndexZeroBased, 
        double value0, double value1);
    
    /**
     * Will be called when reading the matrix finished
     */
    void finishMatrix();
    
}