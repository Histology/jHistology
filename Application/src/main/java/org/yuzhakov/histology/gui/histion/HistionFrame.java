package org.yuzhakov.histology.gui.histion;

import java.util.List;

import javax.swing.JPanel;

import org.yuzhakov.histology.gui.EditorFrame;
import org.yuzhakov.histology.model.m3D.Cell;

import de.jreality.scene.SceneGraphComponent;

public class HistionFrame extends EditorFrame{
	private JPanel mainPanel;
		
	public HistionFrame(List<Cell> cellList, SceneGraphComponent sceneGraphComponent, String caption) {
		super(sceneGraphComponent, new HistionEditor(cellList), caption);
	}
	
}
