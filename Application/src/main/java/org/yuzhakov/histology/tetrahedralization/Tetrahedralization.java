package org.yuzhakov.histology.tetrahedralization;

import java.util.ArrayList;
import java.util.List;

import org.yuzhakov.histology.Tetgen;
import org.yuzhakov.histology.model.cut.Tetrahedron;
import org.yuzhakov.histology.model.m3D.Cell;
import org.yuzhakov.histology.model.m3D.Slice;

public class Tetrahedralization {
	public static List<Tetrahedron> createTetrahedralization(Cell cell){
		List<Tetrahedron> tetrahedrons = new ArrayList<>();
		for (Slice slice : cell.getSlices()){
			Tetgen tetgen = new Tetgen(slice);
			tetgen.tetrahedralize(null);
			tetrahedrons.addAll(tetgen.getTetrahedronList());
		}
		return tetrahedrons;
	}
}
