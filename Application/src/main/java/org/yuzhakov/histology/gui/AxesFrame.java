package org.yuzhakov.histology.gui;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import net.miginfocom.swing.MigLayout;

import org.yuzhakov.histology.Tetgen;
import org.yuzhakov.histology.gui.jreality.JrCell;
import org.yuzhakov.histology.gui.jreality.JrCutPlane;
import org.yuzhakov.histology.model.Vertex;
import org.yuzhakov.histology.model.cut.CutPlane;
import org.yuzhakov.histology.model.cut.CellCut;
import org.yuzhakov.histology.model.m3D.Cell;
import org.yuzhakov.histology.model.m3D.CellPrototype;
import org.yuzhakov.histology.model.samples.Axes;
import org.yuzhakov.histology.model.samples.Samples;

import de.jreality.scene.SceneGraphComponent;
import de.jreality.util.SceneGraphUtility;

public class AxesFrame extends JFrame{
	private JPanel mainPanel;
	
	private JSlider sliderA;
	private JSlider sliderB;
	private JSlider sliderZ;
	
	
	public AxesFrame() {
		super("Histology 3D");
		
		final CutPlane cutPlane = new CutPlane();
		cutPlane.setA(Math.PI / 4);
		cutPlane.setB(Math.PI / 4);
		cutPlane.setZ(0.5);
		cutPlane.construct();
		
		final JrCutPlane jrCutPlane = new JrCutPlane(cutPlane);
		
//		final Axes axes = new Axes();
//		axes.setA(Math.PI / 4);
//		axes.setB(Math.PI / 4);
//		axes.update();
		SceneGraphComponent world = SceneGraphUtility.createFullSceneGraphComponent();
//		world.addChild(axes.getSceneGraphComponent());
		world.addChild(jrCutPlane.getSceneGraphComponent());
		
		setJMenuBar(new MainMenuBar());
		
		mainPanel = new JPanel(new BorderLayout());
		mainPanel.add(new JRealityComponent(world), BorderLayout.CENTER);
		mainPanel.add(getSliderPanel(), BorderLayout.EAST);
		
//		sliderA.addChangeListener(new ChangeListener() {
//			
//			@Override
//			public void stateChanged(ChangeEvent event) {
//				JSlider slider = (JSlider) event.getSource();
//				if (slider.getValueIsAdjusting())
//					return;
//				double A = (double)sliderA.getValue() * Math.PI / 90;
//				axes.setA(A);
//				Vertex v = axes.update();
//				modelCut.setNormal(v);
//				jrCutPlane.update();
//			}
//		});
//		
//		sliderB.addChangeListener(new ChangeListener() {
//			
//			@Override
//			public void stateChanged(ChangeEvent event) {
//				JSlider slider = (JSlider) event.getSource();
//				if (slider.getValueIsAdjusting())
//					return;
//				double B = (double)sliderB.getValue() * Math.PI / 90;
//				axes.setB(B);
//				Vertex v = axes.update();
//				modelCut.setNormal(v);
//				jrCutPlane.update();
//			}
//		});
//		
//		sliderZ.addChangeListener(new ChangeListener() {
//			
//			@Override
//			public void stateChanged(ChangeEvent event) {
//				JSlider slider = (JSlider) event.getSource();
//				if (slider.getValueIsAdjusting())
//					return;
//				double Z = (double)slider.getValue() * 2 / 100;
//				modelCut.setOffset(new Vertex(0,0,Z));
//				jrCutPlane.update();
//			}
//		});

		
		add(mainPanel);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	setExtendedState(Frame.MAXIMIZED_BOTH);
    	validate();
    	pack();
    	setVisible(true);
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
