package org.rty.algo.anomalydetector.core.maths;

import java.util.ArrayList;
import java.util.List;

import org.rty.algo.anomalydetector.core.ListUtils;

import com.google.common.math.Stats;

public class AnomalyAnalyser {
	public static double probabilityOfAnomalyFromTail(List<?> events, int tailSize) {
		if (events.size() == 0 || events.size() < tailSize) {
			return 0.0D;
		}

		List<?> tail = ListUtils.extractTail(events, tailSize);
		double probabilityOfGap = probabilityOfGap(events);
		double probabilityOfTailEvents = probabilityOfTailEvents(events, tail);
		return 1.0D - probabilityOfGap * probabilityOfTailEvents;
	}

	private static double probabilityOfTailEvents(List<?> allEvents, List<?> subEvents) {
		if (allEvents.size() < 2 || subEvents.size() < 2) {
			return 1.0D;
		}

		MarkovChainTransitions transitions = new MarkovChainTransitions();
		for (int i = 1; i < allEvents.size(); i++) {
			transitions.addPath(allEvents.get(i - 1), allEvents.get(i));
		}

		double probability = 1.0D;
		for (int i = 1; i < subEvents.size(); i++) {
			double transitionProbability = transitions.getTransitionProbability(subEvents.get(i - 1), subEvents.get(i));
			probability *= transitionProbability;
		}
		return probability;
	}

	private static double probabilityOfGap(List<?> allEvents) {
		List<Integer> continuousPeriodsWithoutGaps = new ArrayList<>();
		int continuousPeriod = 0;
		Object lastEvent = allEvents.get(allEvents.size() - 1);
		for (Object event : allEvents) {
			if (lastEvent.equals(event)) {
				continuousPeriodsWithoutGaps.add(continuousPeriod);
				continuousPeriod = 0;
			} else {
				continuousPeriod++;
			}
		}

		if (continuousPeriodsWithoutGaps.size() <= 1) {
			return 1.0D;
		}

		Stats stats = Stats.of(continuousPeriodsWithoutGaps);
		double std = stats.populationStandardDeviation();
		if (Double.compare(std, 0.000001) < 0) {
			return 1.0D;
		}

		double lastEntry = 1.0D * continuousPeriodsWithoutGaps.get(continuousPeriodsWithoutGaps.size() - 1);
		double normalisedValue = (lastEntry - stats.mean()) / std;
		return Math.exp((normalisedValue * normalisedValue) / -2.0D) / Math.sqrt(2 * Math.PI);
	}
}
