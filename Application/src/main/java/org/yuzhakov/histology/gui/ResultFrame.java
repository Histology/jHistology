package org.yuzhakov.histology.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import de.jreality.math.MatrixBuilder;
import de.jreality.scene.SceneGraphComponent;
import de.jreality.util.CameraUtility;

public class ResultFrame extends JFrame{
	private JPanel mainPanel;
	
	
	public ResultFrame(SceneGraphComponent sceneGraphComponent, String caption) {
		super(caption);
		
		JRealityComponent jRealityComponent = new JRealityComponent(sceneGraphComponent);
		
		
		mainPanel = new JPanel(new BorderLayout());
		mainPanel.add(jRealityComponent, BorderLayout.CENTER);
		
		add(mainPanel);
//    	setExtendedState(Frame.MAXIMIZED_BOTH);
    	setMinimumSize(new Dimension(300, 300));
    	setAlwaysOnTop(true);
    	validate();
    	pack();
    	setVisible(true);
	}
}
