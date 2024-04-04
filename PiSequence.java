public class PiSequence {

	public static void main(String[] args) {
		long numSteps = 100000000;
		
		long startTime = System.currentTimeMillis();
		
		double total = 0.0;
		for (long i = 0; i < numSteps; ++i) {
			double x = (i + 0.5) / numSteps;
			total += 4.0 / (1.0 + x * x);
		}
		total /= numSteps;
			
		long stopTime = System.currentTimeMillis();
		System.out.println("==> pi = " + total);		
		System.out.println("Calculation took " + (stopTime - startTime) + "ms");
	}
}