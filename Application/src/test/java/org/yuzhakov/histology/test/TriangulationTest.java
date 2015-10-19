package org.yuzhakov.histology.test;

import org.junit.Test;
import org.yuzhakov.histology.model.Vertex;
import org.yuzhakov.histology.triangulation.Triangulation;

import java.util.ArrayList;
import java.util.List;

public class TriangulationTest {

	@Test
	public void test() {
		List<Vertex> vertexs = new ArrayList<>();
		vertexs.add(new Vertex(1,1,0));
		vertexs.add(new Vertex(-1,1,0));
		vertexs.add(new Vertex(-1,-1,0));
		vertexs.add(new Vertex(1,-1,0));
		List<int[]> triangles = Triangulation.triangulate(vertexs);
		System.out.println("done");
	}

}
