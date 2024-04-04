import mpi.MPI;

public class PiMPI{

	public static void main(String[] args) {
	
		MPI.Init(args);
		long startTime = System.currentTimeMillis();
		
		int rank = MPI.COMM_WORLD.Rank();
		int size = MPI.COMM_WORLD.Size();
		int nint = 100; // Intervals.
		double h = 1.0/(double)nint, sum = 0.0;
	
		for (int i=rank+1; i<=nint; i+=size) {
			double x = h * ((double)i - 0.5);
			sum += (4.0 / (1.0 + x * x));
		}
	
		double sBuf[] = { h * sum };
		double rBuf[] = new double[1];
		   
		MPI.COMM_WORLD.Reduce(sBuf, 0, rBuf, 0, 1, MPI.DOUBLE, MPI.SUM, 0);
	   
		if (rank == 0) {
	       System.out.println("PI: " + rBuf[0]);
	       }
		
		MPI.Finalize();
		
		long stopTime = System.currentTimeMillis();
		System.out.println("Rank [" + rank +"] Time Duration: " + (stopTime - startTime) + "ms");
	}
}