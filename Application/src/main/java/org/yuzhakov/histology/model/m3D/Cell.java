package org.yuzhakov.histology.model.m3D;

import de.jreality.shader.Color;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.yuzhakov.histology.model.Vertex;
import org.yuzhakov.histology.model.m2D.Base;
import org.yuzhakov.histology.model.m2D.Topology;

public class Cell {
	private CellPrototype prototype;
	private List<Double> heights;
	private Vertex offset;
	private double angle;
	
	public Cell(CellPrototype prototype) {
		this.prototype = prototype;
		this.offset = new Vertex();
		this.angle = 0;
		this.heights = new ArrayList<>();
		int size = prototype.getBases().size();
		for (int i = 0; i < size; ++i){
			this.heights.add( (double) i );
		}
	}
	
	public Cell(CellPrototype prototype, List<Double> heights, double angle, Vertex offset) {
		this.prototype = prototype;
		this.heights = new ArrayList<>(heights);
		this.offset = offset;
		this.angle = angle;
	}
	
	public List<Vertex[]> getAllVerticesByLayer(){
		List<Vertex[]> vertices = new ArrayList<>();
		int level = 0;
		for (Base base : prototype.getBases()){
			double height = heights.get(level);
			Vertex[] layer = copyWithOffsetRotationHeight(base.getAllVertices(),offset,angle,height);
			vertices.add(layer);
			++level;
		}
		return vertices;
	}
	
	public List<Slice> getSlices(){
		List<Vertex[]> allVertices = getAllVerticesByLayer();
		int numberOfSlices = getNumberOfBases() - 1;
		List<Slice> slices = new ArrayList<>(numberOfSlices);
		for (int i = 0; i < numberOfSlices; ++i){
			slices.add(Slice.Builder.buildSlice(
					Arrays.asList(allVertices.get(i+1)),
					Arrays.asList(allVertices.get(i)),
					prototype.getBases().get(i+1).getCentralVerticesIndex(),
					prototype.getBases().get(i).getCentralVerticesIndex(),
					prototype.getMappings().get(i)));
		}
		return slices;
	}
	
	public Color getColor(){
		return prototype.getColor();
	}

	public CellPrototype getPrototype() {
		return prototype;
	}

	public List<Double> getHeights() {
		return heights;
	}

	public Vertex getOffset() {
		return offset;
	}

	public double getAngle() {
		return angle;
	}
	
	public int getNumberOfBases(){
		return prototype.getBases().size();
	}
	
	public int getNumberOfVertexes(int till_level){
		return prototype.getNumberOfVertexes(till_level);
	}
	
	public int getNumberOfVertexes(){
		return prototype.getNumberOfVertexes();
	}
	
	private static Vertex[] copyWithOffsetRotationHeight(List<Vertex> v, Vertex offset, double angle, double Z){
		Vertex[] result = new Vertex[v.size()];
		for (int i = 0; i < result.length; ++i){
			result[i] = Vertex.getSumm(offset, Vertex.rotate(v.get(i), angle));
			result[i].Z = Z;
		}
		return result;
	}
	
}
