# MatrixMarketReader

A simple implementation of a reader for 
[MatrixMarket files](http://math.nist.gov/MatrixMarket/formats.html#MMformat).

## NOTE

This should **not**  be considered as a robust, stable, universal library.
It was only created as a little helper for the 
[JCuda samples](https://github.com/jcuda/jcuda-samples), particularly
the JCusolver samples, to quickly and easily read the example matrices.
It may change arbitrarily in the future.

Limitations:

* The library only supports the "coordinate" MatrixMarket format
* The library reads the data as a *dense* matrix (which then may be converted 
into the CSR format). So it is not appropriate for loading large, sparse 
matrices, because of the memory requirements for the internal creation of 
the dense matrix. It might be updated in the future to support reading matrices 
directly in CSR format, but this is not the intended use case right now.



