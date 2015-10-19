package org.yuzhakov.histology.gui.cell;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.AbstractTableModel;

import org.yuzhakov.histology.Util;
import org.yuzhakov.histology.model.m2D.Base;
import org.yuzhakov.histology.model.m2D.Topology;

import net.miginfocom.swing.MigLayout;

public class LayerEditor extends JPanel {
	private JSpinner sizeSpinner;
	protected LayerTableModel tableModel;
	protected JTable table;
	private Base base;

	public LayerEditor(String name, Base base) {
		super(new MigLayout());
		this.base = base;
		
		this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		JTextField name_TextField = new JTextField();
		
		
		sizeSpinner = new JSpinner(new SpinnerNumberModel(3, 3, 50, 1));
		sizeSpinner.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent event) {
				JSpinner spinner = (JSpinner) event.getSource();
				int size = (int) spinner.getValue();
				tableModel.setRowCount(size);
			}
		});
		sizeSpinner.setPreferredSize(new Dimension(50, sizeSpinner.getHeight()));
		
		JButton deleteLayerButton = new JButton("Удалить");
		deleteLayerButton.setPreferredSize(new Dimension(100, deleteLayerButton.getHeight()));

		tableModel = new LayerTableModel(base);
		table = new JTable(tableModel);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setRowSelectionAllowed(false);
		JScrollPane tablePane = new JScrollPane(table);
		tablePane.setPreferredSize(new Dimension(300, 150));
		
		this.add(new JLabel(name));
		this.add(deleteLayerButton, "wrap");
		this.add(new JLabel("Количество вершин"));
		this.add(sizeSpinner, "wrap");
		this.add(tablePane, "span 2");
		
		initialize();
	}
	
	private void initialize(){
		sizeSpinner.setValue(base.size());
	}

	public static class LayerTableModel extends AbstractTableModel {
		private int size;
		private Base base;

		public LayerTableModel(Base base) {
			super();
			this.size = base.size();
			this.base = base;
		}

		@Override
		public boolean isCellEditable(int rowIndex, int columnIndex) {
			if (columnIndex == 0)
				return false;
			return true;
		}

		@Override
		public int getColumnCount() {
			return 4;
		}

		@Override
		public int getRowCount() {
			return size;
		}
		
		public void setRowCount(int s) {
			size = s;
			this.fireTableDataChanged();
		}

		int[][] data = new int[][] { { 11, 12, 13, 14 }, { 21, 22, 23, 24 },
				{ 31, 32, 33, 34 }, { 41, 42, 43, 44 } };

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			switch (columnIndex) {
			case 0:
				return "Вершина "+(rowIndex+1);
			case 1:
				double X = base.getAllVertices().get(rowIndex).X;
				return String.valueOf(Util.round(X));
			case 2:
				double Y = base.getAllVertices().get(rowIndex).Y;
				return String.valueOf(Util.round(Y));
			case 3:
				return String.valueOf(rowIndex+1);
			default:
				return null;
			}
		}

		@Override
		public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
//			data[columnIndex][rowIndex] = Integer.parseInt((String) aValue);
			super.setValueAt(aValue, rowIndex, columnIndex);
		}
		
		@Override
		public String getColumnName(int column) {
			switch (column) {
			case 0:
				return "Вершина";
			case 1:
				return "X";
			case 2:
				return "Y";
			case 3:
				return "Ребра";
			default:
				return null;
			}
		}

	}
}
