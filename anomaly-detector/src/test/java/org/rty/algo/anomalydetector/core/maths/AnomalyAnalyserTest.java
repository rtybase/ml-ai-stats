package org.rty.algo.anomalydetector.core.maths;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Test;

public class AnomalyAnalyserTest {
	private static final double ALLOWED_ERROR = 0.000000001D;

	private static final double _4D_PROBABILITY = 0.978757028D;
	private static final double _3D_PROBABILITY = 0.976101656D;
	private static final double _2D_PROBABILITY = 0.973114363D;

	@Test
	public void testWithEmptyList() {
		assertEquals(
				AnomalyAnalyser.probabilityOfAnomalyFromTail(Collections.emptyList(), 4),
				0.0D, ALLOWED_ERROR);
	}

	@Test
	public void testWithExceedingTailSize() {
		assertEquals(
				AnomalyAnalyser.probabilityOfAnomalyFromTail(Arrays.asList("D"), 4),
				0.0D, ALLOWED_ERROR);
	}
	
	@Test
	public void testWithSmallList() {
		assertEquals(
				AnomalyAnalyser.probabilityOfAnomalyFromTail(Arrays.asList("D"), 1),
				0.0D, ALLOWED_ERROR);
		assertEquals(
				AnomalyAnalyser.probabilityOfAnomalyFromTail(Arrays.asList("D", "D"), 1),
				0.0D, ALLOWED_ERROR);
	}

	@Test
	public void testWithNonPeriodicData() {
		final String[] events = { "D", "D", "D", "D", "D", "D", "D", "D", "D", "E", "D" };

		assertEquals(
				AnomalyAnalyser.probabilityOfAnomalyFromTail(Arrays.asList(events), 4),
				0.969313919D, ALLOWED_ERROR);
		assertEquals(
				AnomalyAnalyser.probabilityOfAnomalyFromTail(Arrays.asList(events), 3),
				0.965478159D, ALLOWED_ERROR);
		assertEquals(
				AnomalyAnalyser.probabilityOfAnomalyFromTail(Arrays.asList(events), 2),
				0.689303439D, ALLOWED_ERROR);
	}

	@Test
	public void testWithPeriodicData() {
		final String[] events = {
				"D", "D", "D", "D", "D", "D", "D", "D", "D", "E",
				"D", "D", "D", "D", "D", "D", "D", "D", "D", "E",
				"D", "D", "D", "D", "D", "D", "D", "D", "D", "E",
				"D", "D", "D", "D", "D", "D", "D", "D", "D", "E" };

		assertEquals(
				AnomalyAnalyser.probabilityOfAnomalyFromTail(Arrays.asList(events), 4),
				_4D_PROBABILITY, ALLOWED_ERROR);
		assertEquals(
				AnomalyAnalyser.probabilityOfAnomalyFromTail(Arrays.asList(events), 3),
				_3D_PROBABILITY, ALLOWED_ERROR);
		assertEquals(AnomalyAnalyser.probabilityOfAnomalyFromTail(Arrays.asList(events), 2),
				_2D_PROBABILITY, ALLOWED_ERROR);
	}
	
	@Test
	public void testWithPeriodicEmpty() {
		final String[] events = {
				"E", "E", "E", "E", "E", "E", "E", "E", "E", "D",
				"E", "E", "E", "E", "E", "E", "E", "E", "E", "D",
				"E", "E", "E", "E", "E", "E", "E", "E", "E", "D",
				"E", "E", "E", "E", "E", "E", "E", "E", "E", "D",
				"E", "E", "E", "E", "E", "E", "E", "E", "E", "D",
				"E", "E", "E", "E", "E", "E", "E", "E", "E", "D",
				"E", "E", "E", "E", "E", "E", "E", "E", "E", "D" };

		assertEquals(
				AnomalyAnalyser.probabilityOfAnomalyFromTail(Arrays.asList(events), 4),
				_4D_PROBABILITY, ALLOWED_ERROR);
		assertEquals(
				AnomalyAnalyser.probabilityOfAnomalyFromTail(Arrays.asList(events), 3),
				_3D_PROBABILITY, ALLOWED_ERROR);
		assertEquals(
				AnomalyAnalyser.probabilityOfAnomalyFromTail(Arrays.asList(events), 2),
				_2D_PROBABILITY, ALLOWED_ERROR);
	}
}