package org.yuzhakov.histology.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import de.jreality.math.MatrixBuilder;
import de.jreality.plugin.JRViewer;
import de.jreality.scene.Appearance;
import de.jreality.scene.SceneGraphComponent;
import de.jreality.scene.Viewer;
import de.jreality.shader.Color;
import de.jreality.shader.CommonAttributes;
import de.jreality.util.CameraUtility;

public class JRealityComponent extends JPanel{
	private static final long serialVersionUID = 5745189816553846673L;
	private JRViewer viewer;
	private Component viewingComponent;
	private SceneGraphComponent cameraNode;

	private JButton plus;
	private JButton minus;
	
	public JRealityComponent(SceneGraphComponent component) {
		super(new BorderLayout());
			
		viewer = JRViewer.createJRViewer(component);
		viewer.startupLocal();
		Viewer v = viewer.getViewer();
		viewingComponent = (Component) v.getViewingComponent();
		cameraNode = initCameraNode();
		this.add(viewingComponent, BorderLayout.CENTER);
		this.add(createToolBar(), BorderLayout.NORTH);
		initToolBarListeners();
		this.setVisible(true);
		
		Appearance appearance = viewer.getViewer().getSceneRoot().getAppearance();
		appearance.setAttribute(CommonAttributes.BACKGROUND_COLOR, Color.WHITE);
		
	}
	

	public JRViewer getJRViewer() {
		return viewer;
	}
	
	public Component getViewingComponent(){
		return viewingComponent;
	}
	
	private JToolBar createToolBar(){
		JToolBar toolBar = new JToolBar();
		toolBar.setRollover(true);
		toolBar.setFloatable(false);
		plus = SwingUtils.makeNavigationButton("ZoomIn24", "ZOOM_IN", "Увеличить");
		toolBar.add(plus);
		minus = SwingUtils.makeNavigationButton("ZoomOut24", "ZOOM_OUT", "Уменьшить");
		toolBar.add(minus);
		return toolBar;
	}
	
	private SceneGraphComponent initCameraNode(){
		SceneGraphComponent cameraNode = CameraUtility.getCameraNode(viewer.getViewer());
		MatrixBuilder.euclidean().translate(0,0,20).assignTo(cameraNode);
		return cameraNode;
	}
	
	private void initToolBarListeners(){
		minus.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				MatrixBuilder.euclidean(cameraNode).translate(0,0,1).assignTo(cameraNode);
			}
		});
		
		plus.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				MatrixBuilder.euclidean(cameraNode).translate(0,0,-1).assignTo(cameraNode);
			}
		});
	}
}
