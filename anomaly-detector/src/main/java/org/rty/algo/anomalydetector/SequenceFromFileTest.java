package org.rty.algo.anomalydetector;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.rty.algo.anomalydetector.core.maths.AnomalyCalculator;

public class SequenceFromFileTest {
	public static void execute(String file) throws IOException {
		List<?> events = readEvents(file);

		for (int i = 1; i < events.size() + 1; i++) {
			printResult(events.subList(0, i));
		}
	}

	private static void printResult(List<?> events) {
		double chance = AnomalyCalculator.proprobabilityOfAnomaly(events);
		Object lastEvent = events.get(events.size() - 1);
		System.out.println(lastEvent + "," + chance);
	}

	private static List<?> readEvents(String file) throws IOException {
		List<String> events = new ArrayList<>();

		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			String line;
			while ((line = br.readLine()) != null) {
				String[] values = line.split("[,\\s]*");
				for (String value : values) {
					value = value.trim();
					if (!value.isEmpty()) {
						events.add(value);
					}
				}
			}
		}
		return events;
	}
}
