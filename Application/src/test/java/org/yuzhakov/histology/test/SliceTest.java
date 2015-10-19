package org.yuzhakov.histology.test;

import org.junit.Test;
import org.yuzhakov.histology.Tetgen;
import org.yuzhakov.histology.model.cut.Tetrahedron;
import org.yuzhakov.histology.model.m3D.Cell;
import org.yuzhakov.histology.model.m3D.Slice;
import org.yuzhakov.histology.model.samples.Gekko;

import java.util.ArrayList;
import java.util.List;

public class SliceTest {

	@SuppressWarnings("unused")
	@Test
	public void test() {
		Cell cell = new Cell(Gekko.cellTypeA());
		List<Slice> slices = cell.getSlices();
		List<Tetrahedron> tetrahedrons = new ArrayList<>();
		int i = 0;
		for (Slice slice : slices){
			Tetgen tetgen = new Tetgen(slice);
			tetgen.tetrahedralize();
			tetrahedrons.addAll(tetgen.getTetrahedronList());
			System.out.println(i++);
		}
		return;
	}

}
