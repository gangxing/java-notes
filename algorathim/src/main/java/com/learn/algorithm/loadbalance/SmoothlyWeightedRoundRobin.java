package com.learn.algorithm.loadbalance;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @description: Nginx 平滑权重轮询
 * @author: XingGang
 * @create: 2021-03-02 15:32
 * @see <href>https://tenfy.cn/2018/11/12/smooth-weighted-round-robin/</href>
 */
public class SmoothlyWeightedRoundRobin {
	
	private List<Node> nodes;
	
	private int weightSum;
	
	public SmoothlyWeightedRoundRobin(
		List<Node> nodes) {
		this.nodes = nodes;
		for (Node node : nodes) {
			this.weightSum += node.weight;
		}
	}
	
	/**
	 * 1. 每个节点，用它们的当前值加上它们自己的权重。
	 * 2. 选择当前值最大的节点为选中节点，并把它的当前值减去所有节点的权重总和。
	 */
	public Node select() {
		add();
		sort();
		deduct();
		return this.nodes.get(0);
	}
	
	private void add() {
		for (Node node : this.nodes) {
			node.current += node.weight;
		}
	}
	
	private void sort() {
		Collections.sort(this.nodes);
	}
	
	private void deduct() {
		this.nodes.get(0).current -= this.weightSum;
	}
	
	private static class Node implements Comparable<Node> {
		
		private String name;
		private int weight;
		private int current;
		
		public Node(String name, int weight) {
			this.name = name;
			this.weight = weight;
		}
		
		@Override
		public String toString() {
			return "Node{" +
				"name='" + name + '\'' +
				", weight=" + weight +
				'}';
		}
		
		@Override
		public int compareTo(Node o) {
			return this.current - o.current;
		}
	}
	
	public static void main(String[] args) {
		List<Node> nodes = new ArrayList<>();
		nodes.add(new Node("1", 1));
		nodes.add(new Node("2", 2));
		nodes.add(new Node("3", 3));
		nodes.add(new Node("4", 4));
		SmoothlyWeightedRoundRobin selector = new SmoothlyWeightedRoundRobin(nodes);
		for (int i = 0; i < 100; i++) {
			Node node=selector.select();
			System.err.println(node);
		}
	}
	
	
}
