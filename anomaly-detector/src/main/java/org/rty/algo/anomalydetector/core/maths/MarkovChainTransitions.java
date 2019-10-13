package org.rty.algo.anomalydetector.core.maths;

import java.util.HashMap;
import java.util.Map;

public class MarkovChainTransitions {
	private final Map<Object, Integer> fromNodeTotal = new HashMap<>();
	private final Map<String, Integer> fromNodeToNode = new HashMap<>();

	public void addPath(Object fromNode, Object toNode) {
		Integer currentValue = fromNodeTotal.computeIfAbsent(fromNode, k -> new Integer(0));
		fromNodeTotal.put(fromNode, currentValue + 1);

		String key = computeKey(fromNode, toNode);
		currentValue = fromNodeToNode.computeIfAbsent(key, k -> new Integer(0));
		fromNodeToNode.put(key, currentValue + 1);
	}

	public double getTransitionProbability(Object fromNode, Object toNode) {
		String key = computeKey(fromNode, toNode);
		Integer transitionValue = fromNodeToNode.get(key);
		if (transitionValue == null) {
			return 0.0D;
		}

		Integer fromNodeValue = fromNodeTotal.get(fromNode);
		return (1.0D * transitionValue) / fromNodeValue;
	}

	private static String computeKey(Object fromNode, Object toNode) {
		return fromNode.toString() + "->" + toNode.toString();
	}
}
