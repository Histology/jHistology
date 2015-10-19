package org.yuzhakov.histology.gui.jreality;

import static de.jreality.shader.CommonAttributes.DIFFUSE_COLOR;
import static de.jreality.shader.CommonAttributes.EDGE_DRAW;
import static de.jreality.shader.CommonAttributes.POLYGON_SHADER;
import static de.jreality.shader.CommonAttributes.VERTEX_DRAW;

import java.util.ArrayList;
import java.util.List;

import org.yuzhakov.histology.model.Vertex;
import org.yuzhakov.histology.model.cut.CellCut;

import de.jreality.geometry.IndexedFaceSetFactory;
import de.jreality.scene.Appearance;
import de.jreality.scene.IndexedFaceSet;
import de.jreality.scene.SceneGraphComponent;
import de.jreality.shader.Color;
import de.jreality.util.SceneGraphUtility;

public class JrCellCut {
	private CellCut cellCut;
	private SceneGraphComponent sceneGraphComponent;
	
	public JrCellCut(CellCut cellCut){
		this.cellCut = cellCut;
		sceneGraphComponent = SceneGraphUtility.createFullSceneGraphComponent();
		setCommonAppearance(sceneGraphComponent.getAppearance());
		setAppearanceColor(sceneGraphComponent.getAppearance(), cellCut.getCell().getColor());
		update();
	}
	
	public CellCut getCellCut() {
		return cellCut;
	}

	public SceneGraphComponent getSceneGraphComponent() {
		return sceneGraphComponent;
	}

	public void update(){
		sceneGraphComponent.setGeometry(getIndexedFaceSet(cellCut.getCut()));
	}
	
	private static IndexedFaceSet getIndexedFaceSet(List<Vertex[]> c){
		if (c.size() == 0)
			return null;
		ArrayList<double[]> vertexes = new ArrayList<>();
		ArrayList<int[]> indices = new ArrayList<>();

		int i = 0;
		for (Vertex[] polygon : c){
			if (polygon.length < 3)
				continue;
			int[] face = new int[polygon.length];
			indices.add(face);
			for (int k = 0; k < polygon.length; ++k){
				vertexes.add(polygon[k].getCoordinates());
				face[k] = i;
				++i;
			}
		}
		
		IndexedFaceSetFactory ifsf = new IndexedFaceSetFactory();
		ifsf.setVertexCount(vertexes.size());
		ifsf.setVertexCoordinates(vertexes.toArray(new double[0][0]));
		ifsf.setFaceCount(indices.size());
		ifsf.setFaceIndices(indices.toArray(new int[0][0]));
		ifsf.setGenerateFaceNormals(true);
		ifsf.update();
		
		return ifsf.getIndexedFaceSet();
	}
	
	private static void setCommonAppearance(Appearance appearance){
		appearance.setAttribute(EDGE_DRAW, false);
		appearance.setAttribute(VERTEX_DRAW, false);
	}
	
	private static void setAppearanceColor(Appearance appearance, Color color){
		appearance.setAttribute(POLYGON_SHADER+"."+DIFFUSE_COLOR, color);
	}
}
