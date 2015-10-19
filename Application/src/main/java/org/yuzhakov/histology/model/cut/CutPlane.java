package org.yuzhakov.histology.model.cut;

import java.util.ArrayList;
import java.util.List;

import org.la4j.matrix.Matrix;
import org.la4j.matrix.dense.Basic2DMatrix;
import org.la4j.vector.Vector;
import org.la4j.vector.dense.BasicVector;
import org.yuzhakov.histology.model.Vertex;

public class CutPlane {
	private double A;
	private double B;
	private double Z;
	
	private Vertex offset;
	
	private BasicVector axisX;
	private BasicVector axisY;
	private BasicVector axisZ;
	
	private Matrix rotationMatrix;
	private Matrix invRotationMatrix;
	private Vector offsetVector;	
	
	public void construct(){
		offset = new Vertex(0,0,Z);
		offsetVector = new BasicVector(offset.getCoordinates());
		
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
		
		rotationMatrix = (Basic2DMatrix) rotA.multiply(rotB);
		invRotationMatrix = (Basic2DMatrix) rotationMatrix.transpose();
		
		axisX = (BasicVector) rotationMatrix.getRow(0);
		axisY = (BasicVector) rotationMatrix.getRow(1);
		axisZ = (BasicVector) rotationMatrix.getRow(2);	
	}
	
	public Vertex getRelativeCoordinates(Vertex v){
		Vector absV = new BasicVector(v.getCoordinates());
		BasicVector relative = (BasicVector) absV.subtract(offsetVector).multiply(invRotationMatrix);
		return new Vertex(relative.toArray());
	}
	
	public List<Vertex> getPlanePoints(double X, double Y, double Z){
//		Vertex rest = getRelativeCoordinates(new Vertex(X,Y,Z));
//		double restX = rest.X;
//		double restY = rest.Y;
		
		double restX = X;
		double restY = Y;
		
		BasicVector v1 = (BasicVector) offsetVector.add(axisX.multiply(restX));
		BasicVector v2 = (BasicVector) offsetVector.add(axisY.multiply(restY));
		BasicVector v3 = (BasicVector) offsetVector.add(axisX.multiply(-restX));
		BasicVector v4 = (BasicVector) offsetVector.add(axisY.multiply(-restY));
		
		ArrayList<Vertex> result = new ArrayList<>();
		result.add(new Vertex(v1.toArray()));
		result.add(new Vertex(v2.toArray()));
		result.add(new Vertex(v3.toArray()));
		result.add(new Vertex(v4.toArray()));
		
		return result;
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
	public double getZ() {
		return Z;
	}
	public void setZ(double z) {
		Z = z;
	}

	public Vertex getAxisX() {
		return new Vertex(axisX.toArray());
	}

	public Vertex getAxisY() {
		return new Vertex(axisY.toArray());
	}

	public Vertex getAxisZ() {
		return new Vertex(axisZ.toArray());
	}

	public Vertex getOffset() {
		return offset;
	}
	
	
}
