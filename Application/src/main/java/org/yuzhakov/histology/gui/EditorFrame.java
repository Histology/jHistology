package org.yuzhakov.histology.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;

import org.yuzhakov.histology.model.samples.Gekko;

import net.miginfocom.swing.MigLayout;
import de.jreality.math.MatrixBuilder;
import de.jreality.scene.SceneGraphComponent;
import de.jreality.util.CameraUtility;

public abstract class EditorFrame extends JFrame{
	private JPanel mainPanel;
		
	protected EditorFrame(SceneGraphComponent sceneGraphComponent, Component editor, String caption) {
		super(caption);
		
		JRealityComponent jRealityComponent = new JRealityComponent(sceneGraphComponent);		
		mainPanel = new JPanel(new MigLayout("","[grow][grow]","[grow]"));
		mainPanel.add(jRealityComponent,"grow");
		
		JPanel cellPropertiesPanel = new JPanel(new BorderLayout());
		JScrollPane scrollPane = new JScrollPane(editor);
		cellPropertiesPanel.add(scrollPane);
		mainPanel.add(scrollPane,"grow");
			
		add(mainPanel);
    	setExtendedState(Frame.MAXIMIZED_BOTH);
    	validate();
    	pack();
    	setVisible(true);
	}
	
}
