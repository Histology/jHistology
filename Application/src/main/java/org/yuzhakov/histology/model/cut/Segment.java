package org.yuzhakov.histology.model.cut;

import org.yuzhakov.histology.model.Vertex;

public class Segment {
	protected Vertex start;
	protected Vertex end;
	protected Vertex vector;
	
	public Segment(Vertex a, Vertex b){
		start = new Vertex(a);
		end = new Vertex(b);
		vector = new Vertex(
				b.X-a.X,
				b.Y-a.Y,
				b.Z-a.Z);
	}
	

	public Vertex getStart() {
		return start;
	}

	public Vertex getEnd() {
		return end;
	}

	public Vertex getVector() {
		return vector;
	}
	
}
