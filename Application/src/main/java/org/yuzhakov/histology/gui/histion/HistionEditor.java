package org.yuzhakov.histology.gui.histion;

import java.awt.Color;
import java.awt.Dimension;
import java.util.List;

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
import javax.swing.border.Border;
import javax.swing.table.AbstractTableModel;

import org.yuzhakov.histology.Util;
import org.yuzhakov.histology.gui.cell.LayerEditor.LayerTableModel;
import org.yuzhakov.histology.model.m2D.Topology;
import org.yuzhakov.histology.model.m3D.Cell;
import org.yuzhakov.histology.model.m3D.CellPrototype;
import org.yuzhakov.histology.model.samples.Gekko;

import net.miginfocom.swing.MigLayout;

public class HistionEditor extends JPanel {
	public HistionEditor(List<Cell> cellList) {
		super(new MigLayout());
		
		Border border = BorderFactory.createLineBorder(Color.BLACK);

		JPanel headerPanel = new JPanel(new MigLayout("","[][grow]",""));
		headerPanel.setBorder(border);
		headerPanel.add(new JLabel("Название гистиона"));
		JTextField name_TextField = new JTextField();
		headerPanel.add(name_TextField, "grow, span 2, wrap");
		headerPanel.add(new JLabel("Количество клеток"));
		JSpinner modelSize_Spinner = new JSpinner(new SpinnerNumberModel(cellList.size(), 2, 50, 1));
		modelSize_Spinner.setPreferredSize(new Dimension(50, modelSize_Spinner.getHeight()));
		headerPanel.add(modelSize_Spinner,"wrap");
		
		
		this.add(headerPanel, "wrap, grow");
		
		CellsTableModel cellsTableModel = new CellsTableModel(cellList);
		JTable cellsTable = new JTable(cellsTableModel);
		cellsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		cellsTable.setRowSelectionAllowed(false);
		JScrollPane cellsTablePane = new JScrollPane(cellsTable);
		cellsTablePane.setPreferredSize(new Dimension(600, 300));
		
		this.add(cellsTablePane, "wrap");
		
		JPanel libraryPanel = new JPanel(new MigLayout());
		this.add(libraryPanel, "wrap");
		
		libraryPanel.add(new JLabel("Библиотека типов"), "wrap");
		PrototypesTableModel protosTableModel = new PrototypesTableModel(6);
		JTable prototypesTable = new JTable(protosTableModel);
		prototypesTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		prototypesTable.setRowSelectionAllowed(true);
		JScrollPane prototypesTablePane = new JScrollPane(prototypesTable);
		prototypesTablePane.setPreferredSize(new Dimension(300, 150));
		prototypesTable.setDefaultRenderer(Color.class, new ColorRenderer(true));
		prototypesTable.setDefaultEditor(Color.class,
              new ColorEditor());
		libraryPanel.add(prototypesTablePane, "span 1 5");
		
		JButton editButton = new JButton("Изменить");
		editButton.setPreferredSize(new Dimension(100, editButton.getHeight()));
		libraryPanel.add(editButton, "wrap");
		JButton copyButton = new JButton("Копировать");
		copyButton.setPreferredSize(new Dimension(100, copyButton.getHeight()));
		libraryPanel.add(copyButton, "wrap");
		JButton deleteButton = new JButton("Удалить");
		deleteButton.setPreferredSize(new Dimension(100, deleteButton.getHeight()));
		libraryPanel.add(deleteButton, "wrap");
		JButton addButton = new JButton("Добавить");
		addButton.setPreferredSize(new Dimension(100, addButton.getHeight()));
		libraryPanel.add(addButton, "wrap");
	}
	
	public static class PrototypesTableModel extends AbstractTableModel {
		private int size;
		private Object[][] data;

		public PrototypesTableModel(int size) {
			super();
			this.size = size;
			data = new Object[size][2];
			for (int i = 0; i < size; ++i){
				data[i][1] = Color.BLACK;
			}
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
				return "Тип";
			case 1:
				return "Цвет";
			default:
				return null;
			}
		}
		
		 @Override
		public Class<?> getColumnClass(int columnIndex) {
			if (columnIndex == 1)
				return Color.class;
			return super.getColumnClass(columnIndex);
		}

	}
	public static class CellsTableModel extends AbstractTableModel {
		private int size;
		private Object[][] data;

		public CellsTableModel(List<Cell> cells) {
			super();
			this.size = cells.size();
			data = new Object[size][6];
			int i = 0;
			for (Cell cell : cells){
				data[i][0] = "";
				data[i][1] = cell.getColor();
				data[i][2] = cell.getOffset().X;
				data[i][3] = cell.getOffset().Y;
				data[i][4] = cell.getAngle();
				data[i][5] = "";
				++i;
			}
		}

		@Override
		public boolean isCellEditable(int rowIndex, int columnIndex) {
			return true;
		}

		@Override
		public int getColumnCount() {
			return 6;
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
				return "Имя клетки";
			case 1:
				return "Тип";
			case 2:
				return "Координата X";
			case 3:
				return "Координата Y";
			case 4:
				return "Угол поворота";
			case 5:
				return "Начальный уровень";
			default:
				return null;
			}
		}

	}
}
