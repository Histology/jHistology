package org.yuzhakov.histology.model.m3D;

import java.util.ArrayList;
import java.util.List;

import org.yuzhakov.histology.Util;
import org.yuzhakov.histology.model.Vertex;
import org.yuzhakov.histology.model.m2D.Base;
import org.yuzhakov.histology.model.m2D.Topology;
import org.yuzhakov.histology.triangulation.Triangulation;

import de.jreality.shader.Color;

public class CellPrototype {
	private List<Base> bases = new ArrayList<>();
	private List<int[][]> mappings = new ArrayList<>();
	private Color color;
	
	public CellPrototype(){
		color = Util.getRandomColor();
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
	
	public List<Base> getBases() {
		return bases;
	}

	public Base getBottomBase(){
		return bases.get(0);
	}
	
	public Base getTopBase(){
		return bases.get(bases.size() - 1);
	}

	public List<int[][]> getMappings() {
		return mappings;
	}
	
	public int getNumberOfVertexes(int till_level){
		int s = 0;
		for (int i = 0; i < till_level;++i){
			s += bases.get(i).size();
		}
		return s;
	}
	
	public int getNumberOfVertexes(){
		return getNumberOfVertexes(bases.size());
	}
	
	public List<Vertex> getVertices(){
		List<Vertex> vertices = new ArrayList<>();
		for (Base base : bases){
			for (Vertex vertex : base.getAllVertices()){
				vertices.add(new Vertex(vertex));
			}
		}
		return vertices;
	}
	
	public List<int[]> getFaceIndices(){
		ArrayList<int[]> facesList = new ArrayList<>();
		facesList.addAll(getBottomFaceIndices());
		facesList.addAll(getTopFaceIndices());
		int numberOfLayers = mappings.size();
		for (int i = 0; i < numberOfLayers; ++i){
			facesList.addAll(getSideFaceIndices(i));
		}
		return facesList;
	}
	
	private List<int[]> getBottomFaceIndices(){
		return getBottomBase().getTriangulation();
	}
	
	private List<int[]> getTopFaceIndices(){
		int offset = getNumberOfVertexes(bases.size() - 1);
		List<int[]> topFaceIndices = getTopBase().getTriangulation();
		for (int[] face : topFaceIndices){
			for (int i = 0; i < face.length;++i){
				int index = face[i];
				face[i] = index + offset;
			}
		}
		return topFaceIndices;
	}
	
	private List<int[]> getSideFaceIndices(int layer){
		int offsetBottom = getNumberOfVertexes(layer);
		int offsetTop = getNumberOfVertexes(layer+1);
		int[][] layerMapping = mappings.get(layer);
		List<int[]> sideFaceIndices = new ArrayList<>();
		
		int f = 0;
		for (; f < layerMapping.length - 1; ++f){
			int[] map1 = layerMapping[f];
			int[] map2 = layerMapping[f+1];
			int[] face = new int[4];
			face[0] = offsetBottom+map1[0];
			face[1] = offsetBottom+map2[0];
			face[2] = offsetTop+map2[1];
			face[3] = offsetTop+map1[1];
			sideFaceIndices.add(face);
		}
		
		int[] map1 = layerMapping[f];
		int[] map2 = layerMapping[0];
		int[] face = new int[4];
		face[0] = offsetBottom+map1[0];
		face[1] = offsetBottom+map2[0];
		face[2] = offsetTop+map2[1];
		face[3] = offsetTop+map1[1];
		sideFaceIndices.add(face);
		
		return sideFaceIndices;
	}
	
}
