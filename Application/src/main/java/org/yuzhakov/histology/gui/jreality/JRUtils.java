package org.yuzhakov.histology.gui.jreality;

import java.util.ArrayList;
import java.util.List;

import org.yuzhakov.histology.model.Vertex;

import de.jreality.geometry.IndexedFaceSetFactory;
import de.jreality.scene.IndexedFaceSet;

public class JRUtils {
	public static IndexedFaceSet getIndexedFaceSet(List<Vertex[]> c){
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
}
