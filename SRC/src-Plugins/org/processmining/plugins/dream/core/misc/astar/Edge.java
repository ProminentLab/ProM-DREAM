package org.processmining.plugins.dream.core.misc.astar;

public class Edge {
	public final double cost;
	public final Node target;

	public Edge(Node targetNode, double costVal) {
		target = targetNode;
		cost = costVal;
	}
}