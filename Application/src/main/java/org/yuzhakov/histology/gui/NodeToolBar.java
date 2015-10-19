package org.yuzhakov.histology.gui;

import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JToolBar;

public class NodeToolBar extends JToolBar{
	public NodeToolBar() {
		super();
		addButtons(this);
		
	}
	
	protected void addButtons(JToolBar toolBar) {
	    JButton button = null;

	    //first button
	    button = makeNavigationButton("Back24", "PREVIOUS",
	                                  "Back to previous something-or-other",
	                                  "Previous");
	    toolBar.add(button);

	    //second button
	    button = makeNavigationButton("Up24", "UP",
	                                  "Up to something-or-other",
	                                  "Up");
	    toolBar.add(button);
	}

	protected JButton makeNavigationButton(String imageName,
	                                       String actionCommand,
	                                       String toolTipText,
	                                       String altText) {
	    //Look for the image.
	    String imgLocation = "res/toolbarButtonGraphics/navigation/"
	                         + imageName
	                         + ".gif";

	    //Create and initialize the button.
	    JButton button = new JButton();
	    button.setActionCommand(actionCommand);
	    button.setToolTipText(toolTipText);
	    
	    ImageIcon icon = new ImageIcon(imgLocation, altText);
	    button.setIcon(icon);

	    return button;
	}
}
