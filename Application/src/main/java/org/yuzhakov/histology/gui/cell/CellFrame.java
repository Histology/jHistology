package org.yuzhakov.histology.gui.cell;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;

import org.yuzhakov.histology.gui.EditorFrame;
import org.yuzhakov.histology.gui.JRealityComponent;
import org.yuzhakov.histology.model.m3D.CellPrototype;
import org.yuzhakov.histology.model.samples.Gekko;

import net.miginfocom.swing.MigLayout;
import de.jreality.math.MatrixBuilder;
import de.jreality.scene.SceneGraphComponent;
import de.jreality.util.CameraUtility;

public class CellFrame extends EditorFrame{
	private JPanel mainPanel;
		
	public CellFrame(CellPrototype cellPrototype, SceneGraphComponent sceneGraphComponent, String caption) {
		super(sceneGraphComponent, new CellPrototypeEditor(cellPrototype), caption);
	}
	
}
