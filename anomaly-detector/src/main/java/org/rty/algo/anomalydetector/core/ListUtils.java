package org.rty.algo.anomalydetector.core;

import java.util.List;

public class ListUtils {
	public static List<?> extractTail(List<?> list, int tailSize) {
		if (list.size() < tailSize) {
			return list;
		}
		return extractFrom(list, list.size() - tailSize, tailSize);
	}

	private static List<?> extractFrom(List<?> list, int from, int size) {
		return list.subList(from, from + size);
	}
}
