package org.yuzhakov.histology.gui.cell;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.Border;

import org.yuzhakov.histology.model.m2D.Base;
import org.yuzhakov.histology.model.m2D.Topology;
import org.yuzhakov.histology.model.m3D.CellPrototype;
import org.yuzhakov.histology.model.samples.Gekko;

import net.miginfocom.swing.MigLayout;

public class CellPrototypeEditor extends JPanel {
	public CellPrototypeEditor(CellPrototype prototype) {
		super(new MigLayout());
		
		Border border = BorderFactory.createLineBorder(Color.BLACK);

		JPanel headerPanel = new JPanel(new MigLayout("","[][grow]",""));
		headerPanel.setBorder(border);
		headerPanel.add(new JLabel("Название типа"));
		JTextField name_TextField = new JTextField();
		headerPanel.add(name_TextField, "grow, span 2, wrap");
		headerPanel.add(new JLabel("Количество уровней"));
		JSpinner modelSize_Spinner = new JSpinner(new SpinnerNumberModel(prototype.getBases().size(), 2, 50, 1));
		modelSize_Spinner.setPreferredSize(new Dimension(50, modelSize_Spinner.getHeight()));
		headerPanel.add(modelSize_Spinner);
		JButton addLayerButton = new JButton("Добавить");
		addLayerButton.setPreferredSize(new Dimension(100, addLayerButton.getHeight()));
		headerPanel.add(addLayerButton, "align right");
		
		
		this.add(headerPanel, "wrap, grow");
		int i = 1;
		for (Base base : prototype.getBases()){
			this.add(new LayerEditor("Уровень "+ i++, base), "wrap, grow");
		}
	}
}
