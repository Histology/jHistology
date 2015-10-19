package org.yuzhakov.histology;

import java.util.ArrayList;
import java.util.List;

import org.yuzhakov.histology.model.Vertex;
import org.yuzhakov.histology.model.cut.Tetrahedron;
import org.yuzhakov.histology.model.m2D.Base;
import org.yuzhakov.histology.model.m3D.Cell;
import org.yuzhakov.histology.model.m3D.CellPrototype;
import org.yuzhakov.histology.model.m3D.Slice;

import de.jreality.shader.Color;

public class Tetgen {
	private double[] vertexes;
	private int numberOfVertexes;
	
	private int[] faces;
	private int[] facesSizes;
	private int numberOfFaces;
	
	private int[] tetrahedrons;
	private int numberOfTetrahedrons;
	
	static {
		try {
			System.loadLibrary("tetgen");
		} catch (UnsatisfiedLinkError e) {
			String path = JniLoader.loadDllFromJar("tetgen");
			System.load(path);
		}
	}
	
	public Tetgen(List<Vertex> vertexList, List<int[]> facesList){
		//vertexes
		numberOfVertexes = vertexList.size();
		vertexes = new double[numberOfVertexes*3];
		int i = 0;
		for (Vertex v : vertexList) {
			vertexes[i] = v.X;
			vertexes[i + 1] = v.Y;
			vertexes[i + 2] = v.Z;
			i += 3;
		}
		
		//faces
		numberOfFaces = facesList.size();
		facesSizes = new int[numberOfFaces];
		int sum = 0;
		i = 0;
		for (int[] face : facesList){
			int faceSize = face.length;
			facesSizes[i] = faceSize;
			sum += faceSize;
			++i;
		}
		
		faces = new int[sum];
		i = 0;
		for (int[] face : facesList){
			for (int v : face){
				faces[i] = v;
				++i;
			}
		}
	}
	
	public Tetgen(Slice slice){
		//vertexes
		numberOfVertexes = slice.getVertices().size();
		vertexes = new double[numberOfVertexes*3];
		int i = 0;
		int j = 0;
		for (Vertex v : slice.getVertices()){
			vertexes[i] = v.X;
			vertexes[i+1] = v.Y;
			vertexes[i+2] = v.Z;
			i+=3;
		}
		
		//faces
		List<Integer[]> facesList = slice.getFaces();
		numberOfFaces = facesList.size();
		facesSizes = new int[numberOfFaces];
		int sum = 0;
		i = 0;
		for (; i < numberOfFaces; ++i){
			int s = facesList.get(i).length;
			facesSizes[i] = s;
			sum += s;
		}
		faces = new int[sum];
		i = 0;
		j = 0;
		for (; i < numberOfFaces; ++i){
			Integer[] f = facesList.get(i);
			for (int k = 0; k < f.length; ++k){
				faces[j] = f[k];
				++j;
			}
		}		
	}
	
	public Tetgen(Cell cell){
		CellPrototype cellPrototype = cell.getPrototype();
		//vertexes
		numberOfVertexes = cellPrototype.getNumberOfVertexes();
		vertexes = new double[numberOfVertexes*3];
		int i = 0;
		int j = 0;
		int layer = 0;
		for (Vertex[] topologyCoordinates : cell.getAllVerticesByLayer()){
			for (Vertex v:topologyCoordinates){
				vertexes[i] = v.X;
				vertexes[i+1] = v.Y;
				vertexes[i+2] = v.Z;
				i+=3;
			}
			++layer;
		}
		
		//faces
		int[][] nodeFaces = cellPrototype.getFaceIndices().toArray(new int[0][0]);
		numberOfFaces = nodeFaces.length;
		facesSizes = new int[numberOfFaces];
		int sum = 0;
		i = 0;
		for (; i < numberOfFaces; ++i){
			int s = nodeFaces[i].length;
			facesSizes[i] = s;
			sum += s;
		}
		faces = new int[sum];
		i = 0;
		j = 0;
		for (; i < numberOfFaces; ++i){
			int[] f = nodeFaces[i];
			for (int k = 0; k < f.length; ++k){
				faces[j] = f[k];
				++j;
			}
		}
		
		
	}
	
	public Tetgen(CellPrototype cellPrototype){
		//vertexes
		numberOfVertexes = cellPrototype.getNumberOfVertexes();
		vertexes = new double[numberOfVertexes*3];
		int i = 0;
		int j = 0;
		int layer = 0;
		for (Base base : cellPrototype.getBases()){
			for (Vertex v : base.getAllVertices()){
				vertexes[i] = v.X;
				vertexes[i+1] = v.Y;
				vertexes[i+2] = layer;
				i+=3;
			}
			++layer;
		}
		
		//faces
		int[][] nodeFaces = cellPrototype.getFaceIndices().toArray(new int[0][0]);
		numberOfFaces = nodeFaces.length;
		facesSizes = new int[numberOfFaces];
		int sum = 0;
		i = 0;
		for (; i < numberOfFaces; ++i){
			int s = nodeFaces[i].length;
			facesSizes[i] = s;
			sum += s;
		}
		faces = new int[sum];
		i = 0;
		j = 0;
		for (; i < numberOfFaces; ++i){
			int[] f = nodeFaces[i];
			for (int k = 0; k < f.length; ++k){
				faces[j] = f[k];
				++j;
			}
		}
		
		
	}
	
	public native void tetrahedralize(String params);
	
	public void tetrahedralize(){
		tetrahedralize(null);
	}

	public double[] getVertexes() {
		return vertexes;
	}

	public int getNumberOfVertexes() {
		return numberOfVertexes;
	}

	public int[] getFaces() {
		return faces;
	}

	public int[] getFacesSizes() {
		return facesSizes;
	}

	public int getNumberOfFaces() {
		return numberOfFaces;
	}

	public int[] getTetrahedrons() {
		return tetrahedrons;
	}

	public int getNumberOfTetrahedrons() {
		return numberOfTetrahedrons;
	}
	
	public List<Tetrahedron> getTetrahedronList(){
		ArrayList<Tetrahedron> tetraList = new ArrayList<>();
		for (int i = 0; i < numberOfTetrahedrons*4;i+=4){
			Vertex[] tetrahedron = new Vertex[]{
					getVertex(tetrahedrons[i]),
					getVertex(tetrahedrons[i+1]),
					getVertex(tetrahedrons[i+2]),
					getVertex(tetrahedrons[i+3]),
			};
			tetraList.add(new Tetrahedron(tetrahedron));
		}
		return tetraList;
	}
	
	private Vertex getVertex(int index){
		return new Vertex(
				vertexes[index*3],
				vertexes[index*3 + 1],
				vertexes[index*3 + 2]
				);
	}

	//For native executing: methods looks better in JNI
	private void setVertexes(double[] vertexes) {
		this.vertexes = vertexes;
	}

	private void setNumberOfVertexes(int numberOfVertexes) {
		this.numberOfVertexes = numberOfVertexes;
	}

	private void setFaces(int[] faces) {
		this.faces = faces;
	}

	private void setFacesSizes(int[] facesSizes) {
		this.facesSizes = facesSizes;
	}

	private void setNumberOfFaces(int numberOfFaces) {
		this.numberOfFaces = numberOfFaces;
	}

	private void setTetrahedrons(int[] tetrahedrons) {
		this.tetrahedrons = tetrahedrons;
	}

	private void setNumberOfTetrahedrons(int numberOfTetrahedrons) {
		this.numberOfTetrahedrons = numberOfTetrahedrons;
	}
	
	
	
	
}
