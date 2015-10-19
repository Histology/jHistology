package org.yuzhakov.histology.model.samples;

import org.yuzhakov.histology.model.Vertex;
import org.yuzhakov.histology.model.m2D.Base;
import org.yuzhakov.histology.model.m2D.Topology;
import org.yuzhakov.histology.model.m3D.CellPrototype;

public class Samples {
	
	public static CellPrototype hexagonPrismNode(){
		Base base = new Base();
		base.addTopologyVertex(new Vertex(0.8660254037844387, 0.5, 0.0));
		base.addTopologyVertex(new Vertex(0, 1.0, 0.0));
		base.addTopologyVertex(new Vertex(-0.8660254037844385, 0.5, 0.0));
		base.addTopologyVertex(new Vertex(-0.8660254037844388, -0.5, 0.0));
		base.addTopologyVertex(new Vertex(0, -1.0, 0.0));
		base.addTopologyVertex(new Vertex(0.8660254037844388, -0.5, 0.0));
		
		
		int[][] mapping = new int[][]{
				{0,0},
				{1,1},
				{2,2},
				{3,3},
				{4,4},
				{5,5},
		};

		CellPrototype hexagonPrism = new CellPrototype();
		hexagonPrism.getBases().add(base);
		hexagonPrism.getBases().add(base);
		hexagonPrism.getMappings().add(mapping);
		
		return hexagonPrism;
	}
	
	
	public static Base star4Base(){
		Base star4 = new Base();
		star4.addTopologyVertex(new Vertex(1, 0, 0));
		star4.addTopologyVertex(new Vertex(0.25, 0.25, 0));
		star4.addTopologyVertex(new Vertex(0, 1, 0));
		star4.addTopologyVertex(new Vertex(-0.25, 0.25, 0));
		star4.addTopologyVertex(new Vertex(-1, 0, 0));
		star4.addTopologyVertex(new Vertex(-0.25, -0.25, 0));
		star4.addTopologyVertex(new Vertex(0, -1, 0));
		star4.addTopologyVertex(new Vertex(0.25, -0.25, 0));
		return star4;
	}
	
	public static CellPrototype Star4(int layers){		
		Base base = star4Base();
		
		int[][] mapping = new int[][]{
				{0,0},
				{1,1},
				{2,2},
				{3,3},
				{4,4},
				{5,5},
				{6,6},
				{7,7}
		};

		CellPrototype star4 = new CellPrototype();
		star4.getBases().add(base);
		for (int i = 0; i < layers; ++i){
			star4.getBases().add(base);
			star4.getMappings().add(mapping);
		}
		
		return star4;
	}
}
