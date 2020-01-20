package org.rty.algo.anomalydetector.core.maths;

import java.util.List;

import org.rty.algo.anomalydetector.core.PeriodDetails;

public class AnomalyCalculator {

	public static double proprobabilityOfAnomaly(List<?> events) {
		PeriodDetails periodicDetails = PeriodDetector.calculatePeriod(events);
		// This is total probability rule!
		return (1.0D - periodicDetails.lastEventFrequency) * periodicDetails.frequency
				+ nonCycleProbability(events) * (1.0D - periodicDetails.frequency);
	}

	private static double nonCycleProbability(List<?> events) {
		AnomalyAnalyser analyser = new AnomalyAnalyser(events);
		double _4DayProbability = analyser.probabilityOfAnomalyFromTail(4);
		double _3DayProbability = analyser.probabilityOfAnomalyFromTail(3);
		double _2DayProbability = analyser.probabilityOfAnomalyFromTail(2);

		return (_2DayProbability / 12 + _3DayProbability / 7 + _4DayProbability / 3);
	}
}
