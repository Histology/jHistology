package org.yuzhakov.histology.gui.model;

import java.util.List;

import javax.swing.JPanel;

import org.yuzhakov.histology.gui.EditorFrame;
import org.yuzhakov.histology.model.m3D.Cell;

import de.jreality.scene.SceneGraphComponent;

public class ModelFrame extends EditorFrame{
	private JPanel mainPanel;
		
	public ModelFrame(List<Cell> cellList, SceneGraphComponent sceneGraphComponent, String caption) {
		super(sceneGraphComponent, new ModelEditor(cellList), caption);
	}
	
}
