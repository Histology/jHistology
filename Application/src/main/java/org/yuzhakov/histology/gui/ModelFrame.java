package org.yuzhakov.histology.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JToolBar;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import de.jreality.math.MatrixBuilder;
import de.jreality.scene.SceneGraphComponent;
import de.jreality.util.CameraUtility;

public class ModelFrame extends JFrame{
	private JPanel mainPanel;
	
	private JSlider sliderA;
	private JSlider sliderB;
	private JSlider sliderZ;	
	
	public ModelFrame(SceneGraphComponent sceneGraphComponent, String modelName) {
		super(modelName);
		
		setJMenuBar(new MainMenuBar());
		
		JRealityComponent jRealityComponent = new JRealityComponent(sceneGraphComponent);
		final SceneGraphComponent cameraNode = CameraUtility.getCameraNode(jRealityComponent.getJRViewer().getViewer());
		MatrixBuilder.euclidean(cameraNode).translate(0,0,10).assignTo(cameraNode);
		
		
		mainPanel = new JPanel(new BorderLayout());
		mainPanel.add(jRealityComponent, BorderLayout.CENTER);
		mainPanel.add(getSliderPanel(), BorderLayout.EAST);
		
		
		final ChangeListener slidersListener = new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent event) {
				JSlider slider = (JSlider) event.getSource();
				if (slider.getValueIsAdjusting())
					return;
				double A = (double) sliderA.getValue() * Math.PI / 180;
				double B = (double) sliderB.getValue() * Math.PI / 180;
				double Z = (double) sliderZ.getValue() / 100;
				sliderStateChanged(A, B, Z);
			}
		};

		sliderA.addChangeListener(slidersListener);
		sliderB.addChangeListener(slidersListener);
		sliderZ.addChangeListener(slidersListener);
		
		add(mainPanel);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	setExtendedState(Frame.MAXIMIZED_BOTH);
    	setMinimumSize(new Dimension(300, 300));
    	validate();
    	pack();
    	setVisible(true);
	}
	
	public void sliderStateChanged(double A, double B, double Z){
		
	}
	
	private JPanel getSliderPanel(){
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		sliderA = new JSlider(-90, 90, 0);
		sliderA.setName("Parameter A");
		panel.add(sliderA);
		sliderB = new JSlider(-90, 90, 0);
		sliderB.setName("Parameter B");
		panel.add(sliderB);
		sliderZ = new JSlider(0, 100, 0);
		sliderZ.setName("Parameter Z");
		panel.add(sliderZ);
		
		return panel;
	}
}
