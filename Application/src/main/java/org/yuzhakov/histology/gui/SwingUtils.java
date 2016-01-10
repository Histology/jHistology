package org.yuzhakov.histology.gui;

import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Paths;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class SwingUtils {
	public static JButton makeNavigationButton(String imageName, String actionCommand,
			String toolTipText) {
		URL url = SwingUtils.class.getResource("/icons/" + imageName + ".gif");
//		URL[] urls = ((URLClassLoader) ClassLoader.getSystemClassLoader()).getURLs()
		// Look for the image.
		Image image = Toolkit.getDefaultToolkit().getImage(SwingUtils.class.getResource("/icons/" + imageName + ".gif"));

		// Create and initialize the button.
		JButton button = new JButton();
		button.setActionCommand(actionCommand);
		button.setToolTipText(toolTipText);
		button.setSize(24, 24);

		button.setIcon(new ImageIcon(image));

		return button;
	}
}
