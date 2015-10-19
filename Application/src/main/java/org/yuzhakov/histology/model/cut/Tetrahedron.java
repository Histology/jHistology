package org.yuzhakov.histology.model.cut;


import org.yuzhakov.histology.Util;
import org.yuzhakov.histology.model.Vertex;

import de.jreality.shader.Color;

public class Tetrahedron {
	private Vertex[] corners;
	private Color color;
	private Segment[] segments;
	
	public Tetrahedron(Vertex[] corners) {
		this.corners = corners;
		this.segments = new Segment[]{
				new Segment(corners[0], corners[1]),
				new Segment(corners[0], corners[2]),
				new Segment(corners[0], corners[3]),
				new Segment(corners[1], corners[2]),
				new Segment(corners[1], corners[3]),
				new Segment(corners[2], corners[3]),
		};
	}

	public Vertex[] getCorners() {
		return corners;
	}

	public Color getColor() {
		return color;
	}

	public Segment[] getSegments() {
		return segments;
	}
	
}
