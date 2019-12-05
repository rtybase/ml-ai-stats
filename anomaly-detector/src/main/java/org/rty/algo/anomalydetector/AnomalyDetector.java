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
		int totalDataPresentEventsAlerts = 0;
		int totalDataMissingEventsAlerts = 0;
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
				if ("E".equals(lastEvent)) {
					totalDataMissingEventsAlerts++;
				} else {
					totalDataPresentEventsAlerts++;
				}
				System.out.println("---------------------------------------------");
				System.out.println(events);
				System.out.println("Chance: " + chance);
			}
		}

		System.out.println("===================================================");
		System.out.println("Total alerts: " + (totalDataPresentEventsAlerts + totalDataMissingEventsAlerts));
		System.out.println("Total alerts when data was missing (TP): " + totalDataMissingEventsAlerts);
		System.out.println("Total alerts when data was present (FP): " + totalDataPresentEventsAlerts);
		System.out.println("Total missed events (FN): " + (dataMissingEvents - totalDataMissingEventsAlerts));
		System.out.println("Data was missed: " + dataMissingEvents);
		System.out.println("Data was present: " + dataPresentEvents);

		double precision = (1.0D * totalDataMissingEventsAlerts) / (totalDataMissingEventsAlerts + totalDataPresentEventsAlerts);
		double recall = (1.0D * totalDataMissingEventsAlerts) / dataMissingEvents;
		double f1 = 2.0D * (precision * recall) / (precision + recall);
		System.out.println("Precision: " + precision);
		System.out.println("Recall: " + recall);
		System.out.println("F1 score: " + f1);
	}

}
