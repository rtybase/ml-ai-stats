package org.rty.algo.anomalydetector.core.maths;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.google.common.math.Stats;

public class AnomalyAnalyser {
	private static final double SQRT_OF_2_PI = Math.sqrt(2 * Math.PI);

	private final List<?> events;
	private final MarkovChainTransitions transitions = new MarkovChainTransitions();
	private final double probabilityOfGap;

	public AnomalyAnalyser(List<?> events) {
		this.events = new ArrayList<>(Objects.requireNonNull(events, "events must not be null!"));

		for (int i = 1; i < events.size(); i++) {
			transitions.addPath(events.get(i - 1), events.get(i));
		}

		this.probabilityOfGap = probabilityOfGap(events);
	}

	public double probabilityOfAnomalyFromTail(int tailSize) {
		if (events.size() == 0 || events.size() < tailSize) {
			return 0.0D;
		}

		double probabilityOfTailEvents = probabilityOfTailEvents(tailSize);
		return 1.0D - probabilityOfGap * probabilityOfTailEvents;
	}

	private double probabilityOfTailEvents(int tailSize) {
		if (events.size() < 2 || tailSize < 2) {
			return 1.0D;
		}

		double probability = 1.0D;
		for (int i = events.size() - tailSize + 1; i < events.size(); i++) {
			double transitionProbability = transitions.getTransitionProbability(events.get(i - 1), events.get(i));
			probability *= transitionProbability;
		}
		return probability;
	}

	private static double probabilityOfGap(List<?> allEvents) {
		if (allEvents.size() == 0) {
			return 0.0D;
		}

		List<Integer> continuousPeriodsWithoutGaps = computePeriodsWithoutGaps(allEvents);
		if (continuousPeriodsWithoutGaps.size() <= 1) {
			return 1.0D;
		}

		Stats stats = Stats.of(continuousPeriodsWithoutGaps);
		double std = stats.populationStandardDeviation();
		if (Double.compare(std, 0.000001) < 0) {
			return 1.0D;
		}

		return calculateNormalDistribution(
				continuousPeriodsWithoutGaps.get(continuousPeriodsWithoutGaps.size() - 1),
				stats.mean(),
				std);
	}

	private static List<Integer> computePeriodsWithoutGaps(List<?> allEvents) {
		List<Integer> continuousPeriodsWithoutGaps = new ArrayList<>();
		int continuousPeriod = 0;
		Object currentEvent = allEvents.get(0);
		for (Object event : allEvents) {
			if (currentEvent.equals(event)) {
				continuousPeriod++;
			} else {
				continuousPeriodsWithoutGaps.add(continuousPeriod);
				continuousPeriod = 1;
				currentEvent = event;
			}
		}

		continuousPeriodsWithoutGaps.add(continuousPeriod);
		return continuousPeriodsWithoutGaps;
	}

	private static double calculateNormalDistribution(int x, double mean, double std) {
		double normalisedValue = (x - mean) / std;
		return Math.exp((normalisedValue * normalisedValue) / -2.0D) / SQRT_OF_2_PI;
	}
}
