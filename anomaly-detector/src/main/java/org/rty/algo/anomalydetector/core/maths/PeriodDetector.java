package org.rty.algo.anomalydetector.core.maths;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.rty.algo.anomalydetector.core.PeriodDetails;

public class PeriodDetector {
	public static PeriodDetails calculatePeriod(List<?> events) {
		Map.Entry<Integer, Double> possiblePeriods = calculateBestPeriod(events);
		int period = possiblePeriods.getKey();
		double lastEventFrequency = calculateLastEventFrequency(events, period);

		double confidence = 1.0D - (1.0D * period) / events.size();
		return new PeriodDetails(period, possiblePeriods.getValue(), lastEventFrequency, confidence);
	}

	private static double calculateLastEventFrequency(List<?> events, int period) {
		if (period <= 0) {
			return 0.0D;
		}

		int totalCount = 0;
		int lastEventMatchCount = 0;
		Object lastEvent = events.get(events.size() - 1);
		for (int i = events.size() - 1; i >= 0; i -= period) {
			totalCount++;
			if (lastEvent.equals(events.get(i))) {
				lastEventMatchCount++;
			}
		}
		return (1.0D * lastEventMatchCount) / totalCount;
	}

	private static Map.Entry<Integer, Double> calculateBestPeriod(List<?> events) {
		double maxFrequency = 0.0D;
		int maxPeriod = 0;
		int maxCount = 0;

		HashMap<Integer, Integer> cache = new HashMap<>();
		for (int i = 1; i < events.size() - 1; i++) {
			int count1 = countMatches(cache, events, i - 1);
			double frequency1 = calculateMatchingFrequency(count1, events.size(), i - 1);

			int count2 = countMatches(cache, events, i);
			double frequency2 = calculateMatchingFrequency(count2, events.size(), i);

			int count3 = countMatches(cache, events, i + 1);
			double frequency3 = calculateMatchingFrequency(count3, events.size(), i + 1);

			if (Double.compare(frequency2, 0.0D) > 0
					&& isSpike(frequency1, frequency2, frequency3)
					&& (count2 > maxCount)) {
				maxCount = count2;
				maxPeriod = i;
				maxFrequency = frequency2;
			}
		}
		return new AbstractMap.SimpleEntry<>(maxPeriod, maxFrequency);
	}

	private static boolean isSpike(double frequency1, double frequency2, double frequency3) {
		int diff1 = Double.compare(frequency2, frequency1);
		int diff2 = Double.compare(frequency2, frequency3);
		return (diff1 > 0 && diff2 >= 0) || (diff1 >= 0 && diff2 > 0);
	}

	private static double calculateMatchingFrequency(int count, int eventsListSize, int period) {
		return (1.0D * count) / (eventsListSize - period);
	}

	private static int countMatches(HashMap<Integer, Integer> cache, List<?> events, int period) {
		return cache.computeIfAbsent(period, p -> {
			int count = 0;
			for (int i = 0; i + period < events.size(); i++) {
				if (events.get(i).equals(events.get(i + period))) {
					count++;
				}
			}
			return count;
		});
	}
}
