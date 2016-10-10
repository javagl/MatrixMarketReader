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
 * The description of a matrix that is read by a {@link MatrixMarketReader}
 */
public final class MatrixDescription
{
    /**
     * The format
     */
    private Format format;
    
    /**
     * The field
     */
    private Field field;
    
    /**
     * The symmetry
     */
    private Symmetry symmetry;
    
    /**
     * The number of rows
     */
    private int numRows;
    
    /**
     * The number of columns
     */
    private int numCols;
    
    /**
     * The number of non-zero elements
     */
    private int numNonZeros;
    
    /**
     * Package-private constructor
     */
    MatrixDescription()
    {
        // Package-private constructor
    }
    
    /**
     * Set the format
     * 
     * @param format The format
     */
    void setFormat(Format format)
    {
        this.format = format;
    }
    
    /**
     * Set the field
     * 
     * @param field The field
     */
    void setField(Field field)
    {
        this.field = field;
    }

    /**
     * Set the symmetry
     * 
     * @param symmetry The symmetry
     */
    void setSymmetry(Symmetry symmetry)
    {
        this.symmetry = symmetry;
    }

    /**
     * Set the size
     * 
     * @param numRows The number of rows
     * @param numCols The number of columns
     * @param numNonZeros The number of non-zero elements
     */
    void setSize(int numRows, int numCols, int numNonZeros)
    {
        this.numRows = numRows;
        this.numCols = numCols;
        this.numNonZeros = numNonZeros;
    }

    /**
     * Returns the {@link Format} of the matrix
     * 
     * @return The {@link Format}
     */
    public Format getFormat()
    {
        return format;
    }

    /**
     * Returns the {@link Field} of the matrix
     * 
     * @return The {@link Field}
     */
    public Field getField()
    {
        return field;
    }

    /**
     * Returns the {@link Symmetry} of the matrix
     * 
     * @return The {@link Symmetry}
     */
    public Symmetry getSymmetry()
    {
        return symmetry;
    }

    /**
     * Returns the number of rows of the matrix
     * 
     * @return The number of rows
     */
    public int getNumRows()
    {
        return numRows;
    }
    
    /**
     * Returns the number of columns of the matrix
     * 
     * @return The number of columns
     */
    public int getNumCols()
    {
        return numCols;
    }

    /**
     * Returns the number of non-zero elements of the matrix
     * 
     * @return The number of non-zero elements
     */
    public int getNumNonZeros()
    {
        return numNonZeros;
    }
    
    @Override
    public String toString()
    {
        return "MatrixDescription [format=" + format + ", field=" + field
            + ", symmetry=" + symmetry + ", numRows=" + numRows + ", numCols="
            + numCols + ", numNonZeros=" + numNonZeros + "]";
    }
    
}