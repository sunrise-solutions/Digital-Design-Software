import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

public class TableModel extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int columnCount = 2;
	private ArrayList<String[]> data;
	
	public TableModel() {
		data = new ArrayList<String []>();
		for(int i = 0; i < data.size(); i++) {
			data.add(new String[getColumnCount()]);
		}
	}
	
	public void addData(String[] row) {
		String[] rowTable = new String[getColumnCount()];
		rowTable = row;
		data.add(rowTable);
	}
	
	public void addFirstColumn(String ident) {
		String[] rowTable = {ident, ""};
		data.add(rowTable);
	}
	
	@Override
	public int getRowCount() {
		return data.size();
	}

	@Override
	public int getColumnCount() {
		return columnCount;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return data.get(rowIndex)[columnIndex];
	}
	
	@Override
	public String getColumnName(int columnIndex) {
		switch (columnIndex) {
			case 0: return "Original names";
			case 1: return "Result names";
		}
		return "";
	}
	
	public void deleteData() {
	    data.clear();
	}
}
