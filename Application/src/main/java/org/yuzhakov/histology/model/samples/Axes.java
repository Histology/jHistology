package org.yuzhakov.histology.model.samples;

import org.la4j.matrix.Matrix;
import org.la4j.matrix.dense.Basic2DMatrix;
import org.la4j.vector.dense.BasicVector;
import org.yuzhakov.histology.model.Vertex;

import de.jreality.geometry.IndexedLineSetFactory;
import de.jreality.scene.IndexedLineSet;
import de.jreality.scene.SceneGraphComponent;
import de.jreality.shader.Color;
import de.jreality.util.SceneGraphUtility;

public class Axes {
	private double A;
	private double B;
	private SceneGraphComponent sceneGraphComponent;
	
	public Axes(){
		sceneGraphComponent = SceneGraphUtility.createFullSceneGraphComponent();
	}
	
	public Vertex update(){
		double[][] c = new double[][]{
				{0,0,0},
				{1,0,0},
				{0,1,0},
				{0,0,1},
		};
		
		Matrix coordinates = new Basic2DMatrix(new double[][]{
				{0,0,0},
				{1,0,0},
				{0,1,0},
				{0,0,1}
				});
		Matrix rotA = new Basic2DMatrix(new double[][]{
				{Math.cos(A),0, Math.sin(A)},
				{0,1,0},
				{-Math.sin(A),0,Math.cos(A)},
				});
		
		Matrix rotB = new Basic2DMatrix(new double[][]{
				{1,0,0},
				{0,Math.cos(B),-Math.sin(B)},
				{0,Math.sin(B),Math.cos(B)},
				});
		
		Basic2DMatrix rot = (Basic2DMatrix) rotA.multiply(rotB);
		
		Basic2DMatrix res = (Basic2DMatrix) coordinates.multiply(rot);
		
		Basic2DMatrix transRot = (Basic2DMatrix) rot.transpose();
		
		BasicVector zAx = (BasicVector) res.getRow(2);
		
		BasicVector planar = (BasicVector) zAx.multiply(transRot);
		
		sceneGraphComponent.setGeometry(getIndexedLineSet(res.toArray()));
		
		
		return new Vertex(
				res.getRow(3).get(0),
				res.getRow(3).get(1),
				res.getRow(3).get(2));
	}
	
	public double getA() {
		return A;
	}

	public void setA(double a) {
		A = a;
	}

	public double getB() {
		return B;
	}

	public void setB(double b) {
		B = b;
	}

	public SceneGraphComponent getSceneGraphComponent() {
		return sceneGraphComponent;
	}

	private static IndexedLineSet getIndexedLineSet(double[][] coordinates){
		int[][] edges = new int[][]{
				{0,1},
				{0,2},
				{0,3}
		};
		IndexedLineSetFactory ilsf = new IndexedLineSetFactory();
	    
	    ilsf.setVertexCount( coordinates.length );
	    ilsf.setVertexCoordinates( coordinates );
	    ilsf.setEdgeCount(edges.length);
	    ilsf.setEdgeIndices(edges);
	    ilsf.setEdgeColors(new Color[]{
	    		Color.RED,
	    		Color.GREEN,
	    		Color.BLACK
	    });
	    ilsf.update();
		
		return ilsf.getIndexedLineSet();
	}
	
	
}
