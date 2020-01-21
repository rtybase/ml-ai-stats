package org.rty.algo.anomalydetector;

public class AnomalyDetector {
	private static final String RAND_TEST_PARAM = "rand";

	public static void main(String[] args) throws Exception {
		if (args.length != 1) {
			printUsage();
		} else if (RAND_TEST_PARAM.equalsIgnoreCase(args[0])) {
			RandomTest.execute();
		} else {
			SequenceFromFileTest.execute(args[0]);
		}
	}

	private static void printUsage() {
		System.out.println("Usage:");
		System.out.println("java " + AnomalyDetector.class.getCanonicalName() + " <scope>");
		System.out.println("<scope>");
		System.out.println("\t" + RAND_TEST_PARAM + " - executes a random test.");
		System.out.println("\tpath to a file - reads the sequence from the file and analyses it.");
	}
}
