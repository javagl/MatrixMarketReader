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
 * Default implementation of a {@link Callback}, storing the data in
 * a dense, 1-dimensional, column-major double array
 */
class DefaultCallback implements Callback
{
    /**
     * The {@link MatrixDescription}
     */
    private MatrixDescription matrixDescription;
    
    /**
     * The number of rows in the matrix
     */
    private int numRows;
    
    /**
     * Whether complex data is read
     */
    private boolean complex;
    
    /**
     * The actual data array
     */
    private double array[];

    @Override
    public void startMatrix(MatrixDescription matrixDescription)
    {
        this.matrixDescription = matrixDescription;
        numRows = matrixDescription.getNumRows();
        int numCols = matrixDescription.getNumCols();
        int length = numRows * numCols;
        if (matrixDescription.getField() == Field.COMPLEX)
        {
            complex = true;
            length *= 2;
        }
        array = new double[length];
    }

    @Override
    public void setMatrixElement(
        int rowIndexZeroBased,
        int columnIndexZeroBased, 
        double value0, double value1)
    {
        set(rowIndexZeroBased, columnIndexZeroBased, value0, value1);
        if (matrixDescription.getSymmetry() == Symmetry.SYMMETRIC)
        {
            set(columnIndexZeroBased, rowIndexZeroBased, value0, value1);
        }
        else if (matrixDescription.getSymmetry() == Symmetry.SKEW_SYMMETRIC)
        {
            set(columnIndexZeroBased, rowIndexZeroBased, -value0, value1);
        }
        else if (matrixDescription.getSymmetry() == Symmetry.HERMITIAN)
        {
            set(columnIndexZeroBased, rowIndexZeroBased, value0, -value1);
        }
    }
    
    /**
     * Set the value with the given row and column indices
     * 
     * @param r The row
     * @param c The column
     * @param v0 The (real) value
     * @param v1 The imaginary value
     */
    private void set(int r, int c, double v0, double v1)
    {
        int index = r + c * numRows;
        if (complex)
        {
            index += index;
            array[index + 0] = v0;
            array[index + 1] = v1;
        }
        else
        {
            array[index] = v0;
        }
    }

    @Override
    public void finishMatrix()
    {
        // Nothing to do here
    }
    
    @Override
    public MatrixDescription getMatrixDescription()
    {
        return matrixDescription;
    }
    
    /**
     * Returns a <i>reference</i> to the data array, or <code>null</code>
     * if this callback did not yet receive the appropriate calls
     *  
     * @return The data array
     */
    double[] getArray()
    {
        return array;
    }

}
