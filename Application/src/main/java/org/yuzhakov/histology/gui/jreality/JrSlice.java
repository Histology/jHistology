package org.yuzhakov.histology.gui.jreality;

import static de.jreality.shader.CommonAttributes.DIFFUSE_COLOR;
import static de.jreality.shader.CommonAttributes.LINE_SHADER;
import static de.jreality.shader.CommonAttributes.POLYGON_SHADER;
import static de.jreality.shader.CommonAttributes.TUBE_RADIUS;
import static de.jreality.shader.CommonAttributes.VERTEX_DRAW;

import java.util.ArrayList;
import java.util.List;

import org.yuzhakov.histology.Util;
import org.yuzhakov.histology.model.Vertex;
import org.yuzhakov.histology.model.m3D.Slice;

import de.jreality.geometry.IndexedFaceSetFactory;
import de.jreality.scene.Appearance;
import de.jreality.scene.IndexedFaceSet;
import de.jreality.scene.SceneGraphComponent;
import de.jreality.shader.Color;
import de.jreality.util.SceneGraphUtility;

public class JrSlice {
	private Slice slice;
	private SceneGraphComponent sceneGraphComponent;
	
	public JrSlice(Slice slice){
		this.slice = slice;
		double[][] coordinates = getVertexCoordinates(slice);
		int[][] faceIndices = getFaceIndices(slice);
		int[][] edges = getEdges(slice);
		
		sceneGraphComponent = SceneGraphUtility.createFullSceneGraphComponent();
		sceneGraphComponent.setGeometry(getIndexedFaceSet(coordinates, faceIndices, edges));
		setCommonAppearance(sceneGraphComponent.getAppearance());
		setAppearanceColor(sceneGraphComponent.getAppearance(), Util.getRandomColor());
	}
	
	public Slice getSlice() {
		return slice;
	}

	public SceneGraphComponent getSceneGraphComponent() {
		return sceneGraphComponent;
	}


	private static double[][] getVertexCoordinates(Slice slice){
		ArrayList<double[]> coordinates = new ArrayList<>();
		for (Vertex v : slice.getVertices()) {
			coordinates.add(v.getCoordinates());
		}
		
		return coordinates.toArray(new double[0][0]);
	}
	private static int[][] getFaceIndices(Slice slice){
		List<Integer[]> facesList = slice.getFaces();
		int numberOfFaces = facesList.size();
		int[][] faces = new int[numberOfFaces][];
		for (int i = 0; i < numberOfFaces; ++i){
			Integer[] face = facesList.get(i);
			faces[i] = new int[face.length];
			for (int j = 0; j < face.length; ++j){
				faces[i][j] = face[j];
			}
		}
		return faces;
	}
	
	private static int[][] getEdges(Slice slice){
		return slice.getEdges().toArray(new int[0][0]);
	}
	
	private static void setCommonAppearance(Appearance appearance){
		appearance.setAttribute(LINE_SHADER+"."+DIFFUSE_COLOR, Color.BLACK);
		appearance.setAttribute(LINE_SHADER+"."+TUBE_RADIUS, 0.01);
		appearance.setAttribute(VERTEX_DRAW, false);
	}
	
	private static void setAppearanceColor(Appearance appearance, Color color){
		appearance.setAttribute(POLYGON_SHADER+"."+DIFFUSE_COLOR, color);
	}
	
	private static IndexedFaceSet getIndexedFaceSet(double[][] coordinates, int[][] faceIndices, int[][] edges){
		IndexedFaceSetFactory ifsf = new IndexedFaceSetFactory();
		ifsf.setVertexCount(coordinates.length);
		ifsf.setFaceCount(faceIndices.length);
		ifsf.setEdgeCount(edges.length);
		ifsf.setVertexCoordinates(coordinates);
		ifsf.setFaceIndices(faceIndices);
		ifsf.setEdgeIndices(edges);
		ifsf.setGenerateFaceNormals(true);
		ifsf.update();
		
		return ifsf.getIndexedFaceSet();
	}
	
}
