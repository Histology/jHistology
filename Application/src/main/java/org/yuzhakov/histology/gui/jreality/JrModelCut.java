package org.yuzhakov.histology.gui.jreality;

import java.util.ArrayList;
import java.util.List;

import org.yuzhakov.histology.model.cut.CellCut;
import org.yuzhakov.histology.model.cut.ModelCut;

import de.jreality.scene.SceneGraphComponent;
import de.jreality.util.SceneGraphUtility;

public class JrModelCut {
	private ModelCut modelCut;
	private List<JrCellCut> jrCellCuts;
	private SceneGraphComponent sceneGraphComponent;
	
	public JrModelCut(ModelCut modelCut){
		this.modelCut = modelCut;
		this.jrCellCuts = createCellCuts(modelCut);
		
		sceneGraphComponent = SceneGraphUtility.createFullSceneGraphComponent();
		for (JrCellCut jrCellCut : jrCellCuts){
			sceneGraphComponent.addChild(jrCellCut.getSceneGraphComponent());
		}
		update();
	}
	
	
	public SceneGraphComponent getSceneGraphComponent() {
		return sceneGraphComponent;
	}

	public void update(){
		for (JrCellCut jrCellCut : jrCellCuts){
			jrCellCut.update();
		}
	}
	
	private List<JrCellCut> createCellCuts(ModelCut modelCut){
		List<JrCellCut> cellCuts = new ArrayList<>();
		for (CellCut cellCut : modelCut.getCuts()){
			JrCellCut jrCellCut = new JrCellCut(cellCut);
			cellCuts.add(jrCellCut);
		}
		return cellCuts;
	}
	
	
}
