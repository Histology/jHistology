package org.yuzhakov.histology.model.m3D;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.yuzhakov.histology.model.Vertex;
import org.yuzhakov.histology.model.m2D.Base;

public class Slice {	
	private List<Vertex> vertices;
	private List<Integer[]> faces;
	private List<int[]> edges;
			
	public Slice(List<Vertex> vertices, List<Integer[]> faces, List<int[]> edges) {
		super();
		this.vertices = vertices;
		this.faces = faces;
		this.edges = edges;
	}
	public List<Vertex> getVertices() {
		return vertices;
	}
	public void setVertices(List<Vertex> vertices) {
		this.vertices = vertices;
	}
	public List<Integer[]> getFaces() {
		return faces;
	}
	public void setFaces(List<Integer[]> faces) {
		this.faces = faces;
	}
	public List<int[]> getEdges() {
		return edges;
	}
	public void setEdges(List<int[]> edges) {
		this.edges = edges;
	}

	public static class Builder{
		private List<Vertex> allTopVertices;
		private List<Vertex> allBottomVertices;
		private List<Integer> topCentralVertices;
		private List<Integer> bottomCentralVertices;
		private int[][] mapping;
		
		private Integer[] topIndexList;
		private Integer[] bottomIndexList;
		private List<Vertex> newIndexList;
		private List<Integer[]> faces;
		private List<int[]> edges;
		
		public static Slice buildSlice(List<Vertex> allTopVertices,
				List<Vertex> allBottomVertices,
				List<Integer> topCentralVertices,
				List<Integer> bottomCentralVertices, int[][] mapping){
			
			return new Builder(allTopVertices, allBottomVertices, topCentralVertices, bottomCentralVertices, mapping).buildSlice();
		}
		
		public Builder(List<Vertex> allTopVertices,
				List<Vertex> allBottomVertices,
				List<Integer> topCentralVertices,
				List<Integer> bottomCentralVertices, int[][] mapping) {
			super();
			this.allTopVertices = allTopVertices;
			this.allBottomVertices = allBottomVertices;
			this.topCentralVertices = topCentralVertices;
			this.bottomCentralVertices = bottomCentralVertices;
			this.mapping = mapping;
		}
		
		public Slice buildSlice(){
			topIndexList = new Integer[allTopVertices.size()];
			bottomIndexList = new Integer[allBottomVertices.size()];
			newIndexList = new LinkedList<>();
			faces = new LinkedList<>();
			edges = new LinkedList<>();
			
			int f = 0;
			for (; f < mapping.length - 1; ++f){
				int[] map1 = mapping[f];
				int[] map2 = mapping[f+1];
				addSideFace(map1[0], map2[0], map1[1], map2[1]);
			}		
			int[] map1 = mapping[f];
			int[] map2 = mapping[0];
			addSideFace(map1[0], map2[0], map1[1], map2[1]);
			
			addTopFace(topCentralVertices);
			addBottomFace(bottomCentralVertices);
			
			return new Slice(new ArrayList<>(this.newIndexList),
					new ArrayList<>(this.faces),
					new ArrayList<>(this.edges));
		}

		private void addSideFace(int bottom1, int bottom2, int top1, int top2){
			List<Integer> face = new ArrayList<>(4);
			
			face.add(getIndexVertexBottom(bottom1));
			if (bottom2 != bottom1)
				face.add(getIndexVertexBottom(bottom2));
			face.add(getIndexVertexTop(top2));
			if (top1 != top2)
				face.add(getIndexVertexTop(top1));
			
			if (face.size() < 3)
				return; //Not a face
			faces.add(face.toArray(new Integer[0]));
			addFaceEdges(face);
		}
		
		private void addTopFace(List<Integer> top){
			List<Integer> face = new ArrayList<>(top.size());
			
			for (Integer index : top)
				face.add(getIndexVertexTop(index));
			
			if (face.size() < 3)
				return; //Not a face
			faces.add(face.toArray(new Integer[0]));
		}
		
		private void addBottomFace(List<Integer> bottom){
			List<Integer> face = new ArrayList<>(bottom.size());
			
			for (Integer index : bottom)
				face.add(getIndexVertexBottom(index));
			
			if (face.size() < 3)
				return; //Not a face
			faces.add(face.toArray(new Integer[0]));
		}
		
		private void addFaceEdges(List<Integer> face){
			if (face.size() < 2)
				return;
			int v_last = face.get(face.size() - 1);
			int i, v0, v1;
			v1 = v_last;
			for (i = 0; i < face.size(); ++i){
				v0 = v1;
				v1 = face.get(i);
				edges.add(new int[]{v0,v1});
			}
		}
		
		private void addTopEdges(List<Integer> top){
			if (top.size() < 2)
				return;
			int v_first = getIndexVertexTop(top.get(0));
			int i, v0, v1;
			v1 = v_first;
			for (i = 1; i < top.size(); ++i){
				v0 = v1;
				v1 = getIndexVertexTop(top.get(i));
				edges.add(new int[]{v0,v1});
			}
			edges.add(new int[]{v1,v_first});
		}
		
		private void addBottomEdges(List<Integer> bottom){
			if (bottom.size() < 2)
				return;
			int v_first = getIndexVertexBottom(bottom.get(0));
			int i, v0, v1;
			v1 = v_first;
			for (i = 1; i < bottom.size(); ++i){
				v0 = v1;
				v1 = getIndexVertexBottom(bottom.get(i));
				edges.add(new int[]{v0,v1});
			}
			edges.add(new int[]{v1,v_first});
		}

		private int getIndexVertexTop(int index){
			Integer newIndex = topIndexList[index];
			if (newIndex == null){
				newIndex =  newIndexList.size();
				topIndexList[index] = newIndex;
				Vertex vertex = allTopVertices.get(index);
				newIndexList.add(newIndex, vertex);				
			}
			return newIndex;
		}
		
		private int getIndexVertexBottom(int index){
			Integer newIndex = bottomIndexList[index];
			if (newIndex == null){
				newIndex =  newIndexList.size();
				bottomIndexList[index] = newIndex;
				Vertex vertex = allBottomVertices.get(index);
				newIndexList.add(newIndex, vertex);				
			}
			return newIndex;
		}
		
	}
	
}
