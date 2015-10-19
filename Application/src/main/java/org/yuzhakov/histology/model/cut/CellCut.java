package org.yuzhakov.histology.model.cut;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.yuzhakov.histology.Settings;
import org.yuzhakov.histology.Tetgen;
import org.yuzhakov.histology.model.Vertex;
import org.yuzhakov.histology.model.m3D.Cell;
import org.yuzhakov.histology.tetrahedralization.Tetrahedralization;

public class CellCut {
	private CutPlane cutPlane;
	private List<Tetrahedron> tetrahedrons;
	private Cell cell;
	
	public CellCut(CutPlane cutPlane, Cell cell){
		this.cutPlane = cutPlane;
		this.cell = cell;
		this.tetrahedrons = Tetrahedralization.createTetrahedralization(cell);
	}
		
	public CutPlane getCutPlane() {
		return cutPlane;
	}
	public void setCutPlane(CutPlane cutPlane) {
		this.cutPlane = cutPlane;
	}
	public List<Tetrahedron> getTetrahedrons() {
		return tetrahedrons;
	}
	public void setTetrahedrons(List<Tetrahedron> tetrahedrons) {
		this.tetrahedrons = tetrahedrons;
	}
	
	public Cell getCell() {
		return cell;
	}

	public List<Vertex[]> getCut(){
		ArrayList<Vertex[]> cuts = new ArrayList<>();
		for (Tetrahedron tetrahedron : tetrahedrons){
			List<Vertex> cut = cutTetrahedron(tetrahedron);
			if (cut.size() < 3)
				continue;
			if (cut.size() > 3)
				cut = orderVertexes(cut);
			cuts.add(cut.toArray(new Vertex[0]));
		}
		return cuts;
	}
	
	private List<Vertex> cutTetrahedron(Tetrahedron tetrahedron){
		ArrayList<Vertex> intersections = new ArrayList<>();
		for (Segment segment : tetrahedron.getSegments()){
			for (Vertex v : cutSegment(segment)){
				if (!intersections.contains(v))
					intersections.add(v);
			}
		}
		return intersections;
	}
	
	private List<Vertex> cutSegment(Segment segment){
		Vertex normal = cutPlane.getAxisZ();
		Vertex offset = cutPlane.getOffset();
		ArrayList<Vertex> intersections = new ArrayList<>();
		double A = (normal.X*segment.vector.X + normal.Y*segment.vector.Y + normal.Z*segment.vector.Z);
		double f = (normal.X*segment.start.X + normal.Y*segment.start.Y + normal.Z*segment.start.Z);
		double g = (normal.X*offset.X + normal.Y*offset.Y + normal.Z*offset.Z);
		double B = g-f;
		if (Math.abs(A) < Settings.DEFAULT_PRECISION){
			if (Math.abs(B) >= Settings.DEFAULT_PRECISION)
				return intersections;
			
			intersections.add(new Vertex(segment.start));
			intersections.add(new Vertex(segment.end));
			return intersections;
		}
		double R = B/A;
		if (R > 1 || R < 0)
			return intersections;
		
		Vertex add = new Vertex(segment.vector);
		add.multiply(R);
		
		Vertex result = Vertex.getSumm(
				segment.start,
				add);
		intersections.add(result);
		return intersections;
	}
	
	private static final Comparator<Object[]> atan2Comparator = new Comparator<Object[]>() {
		
		@Override
		public int compare(Object[] o1, Object[] o2) {
			double a1 = (double) o1[0];
			double a2 = (double) o2[0];
			return Double.compare(a1, a2);
		}
	};
	
	private List<Vertex> orderVertexes(Collection<Vertex> vertexs){
		ArrayList<Object[]> list = new ArrayList<>();
		Vertex relCenter = new Vertex();
		for (Vertex absVertex : vertexs){
			Vertex relVertex = cutPlane.getRelativeCoordinates(absVertex);
			relCenter.X += relVertex.X;
			relCenter.Y += relVertex.Y;
			list.add(new Object[]{0.0, absVertex, relVertex});
		}
		relCenter.X = relCenter.X/vertexs.size();
		relCenter.Y = relCenter.Y/vertexs.size();
		for (Object[] o:list){
			Vertex relVertex = (Vertex) o[2];
			Double atan2 = Math.atan2(relVertex.Y - relCenter.Y, relVertex.X - relCenter.X);
			o[0] = atan2;
		}		
		Collections.sort(list, atan2Comparator);
		ArrayList<Vertex> result = new ArrayList<>();
		for (Object[] o:list){
			result.add((Vertex) o[1]);
		}
		return result;
	}
	
	private List<Tetrahedron> createTetrahedrons(Cell cell){
		Tetgen tetgen = new Tetgen(cell);
		tetgen.tetrahedralize(null);
		return tetgen.getTetrahedronList();
	}
	
}
