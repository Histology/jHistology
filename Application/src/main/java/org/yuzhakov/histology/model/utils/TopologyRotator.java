package org.yuzhakov.histology.model.utils;

import java.util.ArrayList;
import java.util.LinkedList;

import org.yuzhakov.histology.model.m2D.Topology;

public class TopologyRotator {
	/**
	 *  рутим пор€док ребер так, чтобы в начале располагалась уникальна€ последовательность.
	 */
	public static void rollEdges(Topology topology){
		LinkedList<Sequence> sequences = createSequences(topology);
	}
		
	private static LinkedList<Sequence> createSequences(Topology topology){
		double[] edges = topology.getEdges();
		int size = edges.length;
		//»щем размер самого большого ребра
		double maxValue = edges[0];
		for (int i = 1; i < size; ++i){
			if (edges[i] > maxValue)
				maxValue = edges[i];
		}
		
		//¬ыдел€ем конкурентные последовательности
		LinkedList<Sequence> sequences = new LinkedList<>();
		int seqStart = 0;
		int seqEnd;
		Sequence seq = new Sequence();
		sequences.add(seq);
		seq.put(0, edges[0]);
		for (int i = 1; i < size; ++i){
			double diff = edges[i] - edges[i-1];
			// ≈сли предыдущий был меньше максимума, а теперь максимум - нова€ последовательность
			if (edges[i] == maxValue && diff > 0){
				seq = new Sequence();
				sequences.add(seq);
			}
			seq.put(i, edges[i]);
		}
		//—ращиваем первую и последнюю последовательности, если надо.
		//Ќадо всегда, кроме случа€ когда между последним и первым есть возрастание и первый - максимум.
		double diff = edges[0] - edges[size-1];
		if (edges[0] != maxValue || diff <= 0){
			sequences.getLast().merge(sequences.getFirst());
			sequences.remove();//First
		}
		
		return sequences;
	}
	
	
	
	
	
	private static class Sequence{
		ArrayList<Integer> order = new ArrayList<>();
		ArrayList<Double> value = new ArrayList<>();
		
		public void put (int o, double v){
			order.add(o);
			value.add(v);
		}
		
		public void merge(Sequence other){
			order.addAll(other.order);
			value.addAll(other.value);
		}
		
		public int size(){
			return value.size();
		}
	}
}
