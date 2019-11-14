package org.rty.algo.anomalydetector;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.rty.algo.anomalydetector.core.maths.AnomalyCalculator;

public class AnomalyDetector {
	private static final int ENTIRE_PERIOD = 365;

	public static void main(String[] args) {
		final String[] distribution = {
				"D", "D", "D", "D", "D", "D", "D", "D", "D", "E",
				"D", "D", "D", "D", "D", "D", "D", "D", "D", "E" };

		Random r = new Random();

		List<String> events = new ArrayList<>();
		int totalAlerts = 0;
		int dataPresentEvents = 0;
		int dataMissingEvents = 0;
		for (int i = 0; i < ENTIRE_PERIOD; i++) {
			String lastEvent = distribution[r.nextInt(distribution.length)];
			events.add(lastEvent);

			if ("E".equals(lastEvent)) {
				dataMissingEvents++;
			} else {
				dataPresentEvents++;
			}


			double chance = AnomalyCalculator.proprobabilityOfAnomaly(events);

			if (Double.compare(chance, 0.5D) > 0) {
				totalAlerts++;
				System.out.println("---------------------------------------------");
				System.out.println(events);
				System.out.println("Chance: " + chance);
			}
		}

		System.out.println("===================================================");
		System.out.println("Total alerts: " + totalAlerts);
		System.out.println("Data was missed: " + dataMissingEvents);
		System.out.println("Data was present: " + dataPresentEvents);
	}

}
