# Time-average-for-atoms

Parse the data from Molecular Dynamics simulations. A.	The data consists of the position vector (3 columns), velocity vector (3 columns), force vector (3 columns) and other information (3 – 6 columns) for each atom at each timestep from the simulation. The dataset size varies with the total number of timestep and dump frequency for the simulation, usually from 100 MB to 20 GB.

First using hadoop ecosystem to parse the atom data to calculate time averages. Writting mapper and reducer in python and perform mapreduce jobs in hadoop server. Works fine for data size smaller than 2GB and fails with filer is larger due to size limit in the server.

Then using spark local mode to parse the atom data to calculate time averages. Works fine for any data up to 20GB.
