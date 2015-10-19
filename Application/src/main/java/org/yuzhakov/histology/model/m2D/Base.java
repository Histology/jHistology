package org.yuzhakov.histology.model.m2D;

import java.util.ArrayList;
import java.util.List;

import org.yuzhakov.histology.model.Vertex;
import org.yuzhakov.histology.triangulation.Triangulation;

public class Base{
	private List<Vertex> topologyVertices = new ArrayList<>();
	private List<Vertex> intermediateVertices = new ArrayList<>();
	private List<Vertex> peripheralVertices = new ArrayList<>();
	private List<Vertex> allVertices = new ArrayList<>();
	private List<Integer> topologyVerticesIndex = new ArrayList<>();
	private List<Integer> centralVerticesIndex = new ArrayList<>();	//topology+auxilary
	
	public Base(){
		super();
	}
	
	public Base(Topology topology){
		this();
		for (Vertex vertex : topology.getCoordinates()){
			addTopologyVertex(vertex);
		}
	}
	
	public List<Vertex> getTopologyVertices() {
		return topologyVertices;
	}
	public void setTopologyVertices(List<Vertex> topologyVertices) {
		this.topologyVertices = topologyVertices;
	}
	
	public List<Vertex> getIntermediateVertices() {
		return intermediateVertices;
	}

	public void setIntermediateVertices(List<Vertex> intermediateVertices) {
		this.intermediateVertices = intermediateVertices;
	}

	public List<Vertex> getPeripheralVertices() {
		return peripheralVertices;
	}

	public void setPeripheralVertices(List<Vertex> peripheralVertices) {
		this.peripheralVertices = peripheralVertices;
	}

	public List<Vertex> getAllVertices() {
		return allVertices;
	}
	public void setAllVertices(List<Vertex> allVertices) {
		this.allVertices = allVertices;
	}
	
	public List<Integer> getTopologyVerticesIndex() {
		return topologyVerticesIndex;
	}
	public void setTopologyVerticesIndex(List<Integer> topologyVerticesIndex) {
		this.topologyVerticesIndex = topologyVerticesIndex;
	}
	public List<Integer> getCentralVerticesIndex() {
		return centralVerticesIndex;
	}
	public void setCentralVerticesIndex(List<Integer> centralVerticesIndex) {
		this.centralVerticesIndex = centralVerticesIndex;
	}
	public int addTopologyVertex(Vertex vertex){
		allVertices.add(vertex);
		int index = allVertices.size()-1;
		topologyVertices.add(vertex);
		topologyVerticesIndex.add(index);
		centralVerticesIndex.add(index);
		return index;
	}
	public int addIntermediateVertex(Vertex vertex){
		allVertices.add(vertex);
		int index = allVertices.size()-1;
		intermediateVertices.add(vertex);
		topologyVerticesIndex.add(index);
		centralVerticesIndex.add(index);
		return index;
	}
	public int addPeripheralVertex(Vertex vertex){
		allVertices.add(vertex);
		peripheralVertices.add(vertex);
		return allVertices.size()-1;
	}
	public int size(){
		return allVertices.size();
	}
	public List<int[]> getTriangulation(){
		return Triangulation.triangulate(
				allVertices,
				topologyVerticesIndex);
	}
	
}
