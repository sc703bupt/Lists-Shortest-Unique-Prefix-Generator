package com.shortesetuniqueprefix;

import java.util.Set;

/**
 * virtual node for implementing "in memory" finding
 * 
 * @author shenchen
 *
 */
class Node {
	public Node(Set<Integer> indexSet, int level, String sequenceItemStr) {
		this.indexSet = indexSet;
		this.level = level;
		this.sequenceItemStr = sequenceItemStr;
	}
	Set<Integer> indexSet;
	int level;
	String sequenceItemStr;
}