import java.util.ArrayList;


public class PiParallel extends Thread {

	private long begin;
	private long end;
	private long numSteps;
	private double subtotal;

	public PiParallel(long begin, long end, long numSteps) {
		this.begin = begin;
		this.end = end;
		this.numSteps = numSteps;
	}

	public double getSubtotal() {
		return this.subtotal;
	}

	public void run() {
		this.subtotal = 0.0;
		for (long i = this.begin; i < this.end; ++i) {
			double x = (i + 0.5) / this.numSteps;
			this.subtotal += 4.0 / (1.0 + x * x);
		}
		this.subtotal /= this.numSteps;
	}

	public static void main(String[] args) {
		int numThreads = Runtime.getRuntime().availableProcessors();
		long numSteps = 100000000;
		if (args.length > 1) {
			numThreads = Integer.parseInt(args[1]);
		}
		if (args.length > 0) {
			numSteps = Long.parseLong(args[0]);
		}

		System.out.println("Calculating pi using " + numThreads + " threads in " + numSteps + " steps...");

		long startTime = System.currentTimeMillis();

		ArrayList<PiParallel> threads = new ArrayList<PiParallel>();
		long begin, end = 0;
		for (int i = 0; i < numThreads; ++i) {
			begin = end;
			end = (i + 1) * numSteps / numThreads;
			threads.add(new PiParallel(begin, end, numSteps));
			threads.get(i).start();
		}

		double total = 0.0;
		for (int i = 0; i < numThreads; ++i) {
			try {
				threads.get(i).join();
			} catch (Exception e) {
				System.err.println("Exception caught: " + e);
			}
			total += threads.get(i).getSubtotal();
		}

		long stopTime = System.currentTimeMillis();
		
		System.out.println("==> pi = " + total);
		System.out.println("Calculation took " + (stopTime - startTime) + "ms");
	}
}