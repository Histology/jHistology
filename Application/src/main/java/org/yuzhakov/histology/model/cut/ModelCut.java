package org.yuzhakov.histology.model.cut;

import java.util.ArrayList;
import java.util.List;

import org.yuzhakov.histology.Tetgen;
import org.yuzhakov.histology.model.m3D.Cell;

public class ModelCut {
	private List<Cell> cells;
	private CutPlane cutPlane;
	private List<CellCut> cuts;
	
	public ModelCut(List<Cell> cells, CutPlane cutPlane) {
		super();
		this.cells = cells;
		this.cutPlane = cutPlane;
		this.cuts = createCellCuts(cells, cutPlane);
	}
	
	public List<Cell> getCells() {
		return cells;
	}

	public CutPlane getCutPlane() {
		return cutPlane;
	}

	public List<CellCut> getCuts() {
		return cuts;
	}

	private List<CellCut> createCellCuts(List<Cell> cells, CutPlane cutPlane){
		List<CellCut> cellCuts = new ArrayList<>();
		for (Cell cell : cells){			
			CellCut cellCut = new CellCut(cutPlane, cell);
			cellCuts.add(cellCut);
		}		
		return cellCuts;		
	}
	
}
