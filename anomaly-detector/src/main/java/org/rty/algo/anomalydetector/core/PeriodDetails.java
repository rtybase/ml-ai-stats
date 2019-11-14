package org.rty.algo.anomalydetector.core;

public class PeriodDetails {
	public final int period;
	public final double frequency;
	public final double lastEventFrequency;

	public PeriodDetails(int period, double frequency, double lastEventFrequency) {
		this.period = period;
		this.frequency = frequency;
		this.lastEventFrequency = lastEventFrequency;
	}

	@Override
	public String toString() {
		return "period = " + period
				+ "; periodFrequency = " + frequency
				+ "; lastEventFrequency = " + lastEventFrequency;
	}
}
