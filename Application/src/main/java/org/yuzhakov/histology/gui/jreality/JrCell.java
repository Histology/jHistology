package org.yuzhakov.histology.gui.jreality;

import static de.jreality.shader.CommonAttributes.*;

import java.util.ArrayList;
import java.util.List;

import org.yuzhakov.histology.model.Vertex;
import org.yuzhakov.histology.model.m2D.Base;
import org.yuzhakov.histology.model.m3D.Cell;

import de.jreality.geometry.FrameFieldType;
import de.jreality.geometry.IndexedFaceSetFactory;
import de.jreality.scene.Appearance;
import de.jreality.scene.IndexedFaceSet;
import de.jreality.scene.SceneGraphComponent;
import de.jreality.shader.Color;
import de.jreality.util.SceneGraphUtility;

public class JrCell {
	private Cell cell;
	private SceneGraphComponent sceneGraphComponent;
	
	public JrCell(Cell cell){
		this.cell = cell;
		double[][] coordinates = getVertexCoordinates(cell);
		int[][] faceIndices = getFaceIndices(cell);
		int[][] edges = getEdges(cell);
		
		sceneGraphComponent = SceneGraphUtility.createFullSceneGraphComponent();
		sceneGraphComponent.setGeometry(getIndexedFaceSet(coordinates, faceIndices, edges));
		setCommonAppearance(sceneGraphComponent.getAppearance());
		setAppearanceColor(sceneGraphComponent.getAppearance(), cell.getColor());
	}
	
	public Cell getCell() {
		return cell;
	}

	public SceneGraphComponent getSceneGraphComponent() {
		return sceneGraphComponent;
	}


	private static double[][] getVertexCoordinates(Cell cell){
		ArrayList<double[]> coordinates = new ArrayList<>();
		for (Vertex[] topologyCoordinates : cell.getAllVerticesByLayer()){
			for (Vertex v:topologyCoordinates){
				coordinates.add(v.getCoordinates());
			}
		}
		
		return coordinates.toArray(new double[0][0]);
	}
	private static int[][] getFaceIndices(Cell cell){
		return cell.getPrototype().getFaceIndices().toArray(new int[0][0]);
	}
	
	private static int[][] getEdges(Cell cell){
		ArrayList<int[]> edgesList = new ArrayList<>();
		for (int i = 0; i < cell.getNumberOfBases();++i){
			edgesList.addAll(getBaseEdges(cell, i));
		}
		for (int i = 0; i < cell.getNumberOfBases() - 1;++i){
			edgesList.addAll(getSideEdges(cell, i));
		}
		return edgesList.toArray(new int[0][0]);
	}
	
	private static List<int[]> getBaseEdges(Cell cell, int level){
		List<int[]> topologyEdges = new ArrayList<>();
		int offset = cell.getNumberOfVertexes(level);
		Base base = cell.getPrototype().getBases().get(level);
		List<Integer> verticesIndexes = base.getTopologyVerticesIndex();
		int prev_index = verticesIndexes.get(verticesIndexes.size() - 1); // last
		for (Integer curr_index : verticesIndexes){
			int[] edge = new int[2];
			edge[0] = prev_index+offset;
			edge[1] = curr_index+offset;
			topologyEdges.add(edge);
			prev_index = curr_index;
		}	
		return topologyEdges;
	}
	
	private static List<int[]> getSideEdges(Cell cell, int layer){
		int offsetBottom = cell.getNumberOfVertexes(layer);
		int offsetTop = cell.getNumberOfVertexes(layer+1);
		int[][] layerMapping = cell.getPrototype().getMappings().get(layer);
		List<int[]> sideEdges = new ArrayList<>();
		
		int f = 0;
		for (; f < layerMapping.length; ++f){
			int[] map = layerMapping[f];
			int[] edge = new int[2];
			edge[0] = offsetBottom+map[0];
			edge[1] = offsetTop+map[1];
			sideEdges.add(edge);
		}
		
		return sideEdges;
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
