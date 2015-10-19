package org.yuzhakov.histology.gui;

import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class SwingUtils {
	public static JButton makeNavigationButton(String imageName, String actionCommand,
			String toolTipText) {
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
