package org.yuzhakov.histology.model;

import org.yuzhakov.histology.Settings;
import org.yuzhakov.histology.Util;

public class Vertex {
	public double X;
	public double Y;
	public double Z;
	
	public Vertex(){
		super();
	}
	
	public Vertex(double[] coordinates){
		super();
		X = coordinates[0];
		Y = coordinates[1];
		Z = coordinates[2];
	}

	public Vertex(double x, double y, double z) {
		this();
		X = x;
		Y = y;
		Z = z;
	}
	
	public Vertex(double x, double y) {
		this(x, y, 0.0);
	}
	
	public Vertex(Vertex v) {
		super();
		this.X = v.X;
		this.Y = v.Y;
		this.Z = v.Z;
	}
	
	public double[] getCoordinates(){
		return new double[]{
				X, Y, Z
		};
	}
	
	public double getNorm(){
		return Math.sqrt((X*X)+(Y*Y)+(Z*Z));
	}
	
	public void multiply(double d){
		this.X = (this.X*d);
		this.Y = (this.Y*d);
		this.Z = (this.Z*d);
	}
	
	public static double getDistance(Vertex v1, Vertex v2){
		double dX = v1.X-v2.X;
		double dY = v1.Y-v2.Y;
		double dZ = v1.Z-v2.Z;
		return Math.sqrt((dX*dX)+(dY*dY)+(dZ*dZ));
	}
	
	public static Vertex getSumm(Vertex v1, Vertex v2){
		Vertex v = new Vertex();
		v.X = v1.X+v2.X;
		v.Y = v1.Y+v2.Y;
		v.Z = v1.Z+v2.Z;
		return v;
	}
	
	public static Vertex rotate(Vertex vertex, double angle){
		Vertex v = new Vertex();
		double radians = angle * Math.PI / 180;
		v.X = vertex.X*Math.cos(radians) - vertex.Y*Math.sin(radians);
		v.Y = vertex.X*Math.sin(radians) + vertex.Y*Math.cos(radians);
		v.Z = vertex.Z;
		return v;
	}
	
	public static Vertex getDifference(Vertex v1, Vertex v2){
		Vertex v = new Vertex();
		v.X = v1.X-v2.X;
		v.Y = v1.Y-v2.Y;
		v.Z = v1.Z-v2.Z;
		return v;
	}
	
	public static double getInnerProduct(Vertex v1, Vertex v2){
		double pX = v1.X*v2.X;
		double pY = v1.Y*v2.Y;
		double pZ = v1.Z*v2.Z;
		return pX+pY+pZ;
	}
	
	public static double getAngle(Vertex v1, Vertex v2, Vertex v3){
		Vertex vector1 = Vertex.getDifference(v1, v2);
		Vertex vector2 = Vertex.getDifference(v3, v2);
		double innerProduct = Vertex.getInnerProduct(vector1, vector2);
		return innerProduct / (vector1.getNorm() * vector2.getNorm());
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vertex other = (Vertex) obj;
		if (getDistance(this, other) > Settings.DEFAULT_PRECISION)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "["+X+", "+Y+", "+Z+"]";
	}
	
	
	
}
