package org.yuzhakov.histology.gui.model;

import java.awt.Color;
import java.awt.Dimension;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.Border;
import javax.swing.table.AbstractTableModel;

import org.yuzhakov.histology.Util;
import org.yuzhakov.histology.gui.cell.LayerEditor.LayerTableModel;
import org.yuzhakov.histology.model.m2D.Topology;
import org.yuzhakov.histology.model.m3D.Cell;
import org.yuzhakov.histology.model.m3D.CellPrototype;
import org.yuzhakov.histology.model.samples.Gekko;

import net.miginfocom.swing.MigLayout;

public class ModelEditor extends JPanel {
	public ModelEditor(List<Cell> cellList) {
		super(new MigLayout());

		JPanel namePanel = new JPanel(new MigLayout("","[][grow]",""));
		namePanel.add(new JLabel("Название модели"));
		JTextField name_TextField = new JTextField();
		namePanel.add(name_TextField, "grow, wrap");
		this.add(namePanel, "wrap, grow");
		
		JPanel meshPanel = new JPanel(new MigLayout("","[][grow]",""));
		this.add(meshPanel, "wrap");
		meshPanel.add(new JLabel("Параметры сетки"), "wrap, grow");
		meshPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		meshPanel.add(new JLabel("Тип сетки"));
		JComboBox<String> mesh_ComboBox = new JComboBox<>(new String[]{"Прямоугольная"});
		meshPanel.add(mesh_ComboBox,"wrap");
		
		AttributesTableModel meshTableModel = new AttributesTableModel(4);
		JTable meshTable = new JTable(meshTableModel);
		meshTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		meshTable.setRowSelectionAllowed(false);
		JScrollPane meshTablePane = new JScrollPane(meshTable);
		meshTablePane.setPreferredSize(new Dimension(300, 200));
		
		meshPanel.add(meshTablePane, "wrap, span 2");
		
		JPanel histionPanel = new JPanel(new MigLayout());
		histionPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		this.add(histionPanel, "wrap");
		
		histionPanel.add(new JLabel("Гистион"), "");
		JButton editButton = new JButton("Редактировать");
		editButton.setPreferredSize(new Dimension(100, editButton.getHeight()));
		histionPanel.add(editButton, "wrap, align right");
		histionPanel.add(new JLabel("Количество слоёв"), "");
		JSpinner modelHeight_Spinner = new JSpinner(new SpinnerNumberModel(5, 2, 50, 1));
		modelHeight_Spinner.setPreferredSize(new Dimension(50, modelHeight_Spinner.getHeight()));
		histionPanel.add(modelHeight_Spinner, "wrap, align right");
		AttributesTableModel histionTableModel = new AttributesTableModel(6);
		JTable histionTable = new JTable(histionTableModel);
		histionTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		histionTable.setRowSelectionAllowed(true);
		JScrollPane histionTablePane = new JScrollPane(histionTable);
		histionTablePane.setPreferredSize(new Dimension(300, 150));
		histionPanel.add(histionTablePane, "span 2");		
	}
	
	
	public static class AttributesTableModel extends AbstractTableModel {
		private int size;
		private Object[][] data;

		public AttributesTableModel(int size) {
			super();
			this.size = size;
			data = new Object[size][2];
			data[0][0] = "Ширина";
			data[0][1] = Gekko.GISTEON_WIDTH;
			data[1][0] = "Высота";
			data[1][1] = Gekko.GISTEON_HEIGHT;
		}

		@Override
		public boolean isCellEditable(int rowIndex, int columnIndex) {
			return true;
		}

		@Override
		public int getColumnCount() {
			return 2;
		}

		@Override
		public int getRowCount() {
			return size;
		}

		public void setRowCount(int s) {
			size = s;
			this.fireTableDataChanged();
		}


		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			return data[rowIndex][columnIndex];
		}

		@Override
		public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
			data[rowIndex][columnIndex] = aValue;
		}

		@Override
		public String getColumnName(int column) {
			switch (column) {
			case 0:
				return "Параметр";
			case 1:
				return "Значение";
			default:
				return null;
			}
		}

	}
}
