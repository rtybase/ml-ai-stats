package org.rty.algo.anomalydetector.core.maths;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.rty.algo.anomalydetector.core.PeriodDetails;

public class PeriodDetectorTest {
	@Test
	public void testPeriodWithDominantData() {
		final String[] events = {
				"D", "D", "D", "D", "D", "D", "D", "D", "D", "E",
				"D", "D", "D", "D", "D", "D", "D", "D", "D", "E",
				"D", "D", "D", "D", "D", "D", "D", "D", "D", "E",
				"D", "D", "D", "D", "D", "D", "D", "D", "D", "E" };

		PeriodDetails result = PeriodDetector.calculatePeriod(Arrays.asList(events));
		assertEquals(result.period, 10);
		assertEquals(result.frequency, 1.0D, 0.0001D);
		assertEquals(result.lastEventFrequency, 1.0D, 0.0001D);
	}

	@Test
	public void testPeriodWithDominantNoData() {
		final String[] events = {
				"E", "E", "E", "E", "E", "E", "E", "E", "E", "D",
				"E", "E", "E", "E", "E", "E", "E", "E", "E", "D",
				"E", "E", "E", "E", "E", "E", "E", "E", "E", "D",
				"E", "E", "E", "E", "E", "E", "E", "E", "E", "D",
				"E", "E", "E", "E", "E", "E", "E", "E", "E", "D",
				"E", "E", "E", "E", "E", "E", "E", "E", "E", "D",
				"E", "E", "E", "E", "E", "E", "E", "E", "E", "D" };

		PeriodDetails result = PeriodDetector.calculatePeriod(Arrays.asList(events));
		assertEquals(result.period, 10);
		assertEquals(result.frequency, 1.0D, 0.0001D);
		assertEquals(result.lastEventFrequency, 1.0D, 0.0001D);
	}

	@Test
	public void testPeriodWithLongPeriod() {
		final String[] events = {
				"D", "D", "D", "D", "D", "D", "D", "D", "D", "D", "D", "D", "D", "D", "D", "D", "D", "D", "D", "D",
				"E", "E", "E", "E", "E", "E", "E", "E", "E", "E", "E", "E", "E", "E", "E", "E", "E", "E", "E", "E",
				"D", "D", "D", "D", "D", "D", "D", "D", "D", "D", "D", "D", "D", "D", "D", "D", "D", "D", "D", "D" };

		PeriodDetails result = PeriodDetector.calculatePeriod(Arrays.asList(events));
		assertEquals(result.period, 40);
		assertEquals(result.frequency, 1.0D, 0.0001D);
		assertEquals(result.lastEventFrequency, 1.0D, 0.0001D);
	}

	@Test
	public void testPeriodWithShortPeriod() {
		final String[] events = {
				"D", "E", "D", "E", "D", "E", "D", "E", "D", "E", "D", "E", "D", "E", "D", "E", "D", "E", "D", "E",
				"D", "E", "D", "E", "D", "E", "D", "E", "D", "E", "D", "E", "D", "E", "D", "E", "D", "E", "D", "E",
				"D" };

		PeriodDetails result = PeriodDetector.calculatePeriod(Arrays.asList(events));
		assertEquals(result.period, 2);
		assertEquals(result.frequency, 1.0D, 0.0001D);
		assertEquals(result.lastEventFrequency, 1.0D, 0.0001D);
	}

	@Test
	public void testNoPeriod() {
		final String[] events = { "D", "E", "D" };

		PeriodDetails result = PeriodDetector.calculatePeriod(Arrays.asList(events));
		assertEquals(result.period, 0);
		assertEquals(result.frequency, 0.0D, 0.0001D);
		assertEquals(result.lastEventFrequency, 0.0D, 0.0001D);
	}

	@Test
	public void testOddPeriod() {
		final String[] events = {
				"D", "D", "D", "D", "D", "D", "E",
				"D", "D", "D", "D", "D", "D", "E",
				"D", "D", "D", "D", "D", "D", "E",
				"D", "D", "D", "D", "D", "D", "E",
				"D", "D", "D", "D", "D", "D", "E",
				"D", "D", "D", "D", "D", "D", "E",
				"D", "D", "D", "D", "D", "D", "E",
				"D", "D", "D", "D", "D", "D", "E",
				"D" };

		PeriodDetails result = PeriodDetector.calculatePeriod(Arrays.asList(events));
		assertEquals(result.period, 7);
		assertEquals(result.frequency, 1.0D, 0.0001D);
		assertEquals(result.lastEventFrequency, 1.0D, 0.0001D);
	}

	@Test
	public void testComplexPeriod() {
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
				"E", "D", "D" };
		
		final int[] expectedPeriods = {
				0, 0, 0, 0, 2, 2, 2, 2, 2, 2, 3, 2, 2, 2, 2,
				2, 2, 3, 2, 2, 2, 2, 7, 7, 7, 7, 7, 7, 7, 7,
				7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7,
				7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7,
				7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7 };

		assertEquals(events.length, expectedPeriods.length);
		for (int i = 1; i < events.length + 1; i++) {
			List<String> arrayOfEvents = Arrays.asList(Arrays.copyOf(events, i));
			PeriodDetails result = PeriodDetector.calculatePeriod(arrayOfEvents);
			System.out.println(arrayOfEvents);
			System.out.println(result);
			assertEquals(result.period, expectedPeriods[i - 1]);
		}
	}

}
