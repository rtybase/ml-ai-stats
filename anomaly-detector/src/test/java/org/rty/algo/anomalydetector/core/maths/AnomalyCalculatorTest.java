package org.rty.algo.anomalydetector.core.maths;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Test;

public class AnomalyCalculatorTest {
	@Test
	public void testForPeriodicSequence() {
		final String[] events = {
				"D", "D", "D", "D", "D", "D", "D", "D", "D", "E",
				"D", "D", "D", "D", "D", "D", "D", "D", "D", "E",
				"D", "D", "D", "D", "D", "D", "D", "D", "D", "E",
				"D", "D", "D", "D", "D", "D", "D", "D", "D", "E" };

		double result = AnomalyCalculator.proprobabilityOfAnomaly(Arrays.asList(events));
		assertEquals(result, 0.0D, 0.0001D);
	}

	@Test
	public void testForAlmostPeriodicSequence() {
		final String[] events = {
				"D", "E",
				"D", "D", "D", "D", "D", "D", "E",
				"D", "D", "D", "D", "D", "D", "E",
				"D", "D", "D", "D", "D", "D", "E",
				"D", "D", "D", "D", "D", "D", "E",
				"E", "D", "D", "D", "D", "D", "E",
				"E", "E", "E", "E", "E", "E", "E",
				"E", "D", "D", "D", "D", "D", "E",
				"E", "D", "D", "D", "D", "D", "E",
				"D", "D", "D", "D", "D", "D", "E",
				"E", "D", "D", "D", "D", "D", "E",
				"E", "D", "D", "D", "D", "D", "E",
				"E", "D", "D", "D" };

		double result = AnomalyCalculator.proprobabilityOfAnomaly(Arrays.asList(events));
		assertEquals(result, 0.2060519D, 0.0001D);
	}

	@Test
	public void testForSequenceWithAnomaly() {
		final String[] events = {
				"D", "E",
				"D", "E",
				"D", "E",
				"D", "E",
				"D", "E",
				"D", "E",
				"D", "E",
				"D", "D" };

		double result = AnomalyCalculator.proprobabilityOfAnomaly(Arrays.asList(events));
		assertEquals(result, 0.883925D, 0.0001D);
	}
}
