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
		double _4DayProbability = AnomalyAnalyser.probabilityOfAnomalyFromTail(events, 4);
		double _3DayProbability = AnomalyAnalyser.probabilityOfAnomalyFromTail(events, 3);
		double _2DayProbability = AnomalyAnalyser.probabilityOfAnomalyFromTail(events, 2);

		return (_2DayProbability / 4 + _3DayProbability / 4 + _4DayProbability / 2);
	}
}
