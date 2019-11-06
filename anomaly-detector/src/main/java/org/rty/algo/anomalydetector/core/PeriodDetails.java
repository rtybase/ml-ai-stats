package org.rty.algo.anomalydetector.core;

public class PeriodDetails {
	public final int period;
	public final double frequency;
	public final double lastEventFrequency;
	public final double confidence;

	public PeriodDetails(int period, double frequency, double lastEventFrequency, double confidence) {
		this.period = period;
		this.frequency = frequency;
		this.lastEventFrequency = lastEventFrequency;
		this.confidence = confidence;
	}

	@Override
	public String toString() {
		return "period = " + period
				+ "; periodFrequency = " + frequency
				+ "; confidence = " + confidence
				+ "; lastEventFrequency = " + lastEventFrequency;
	}
}
