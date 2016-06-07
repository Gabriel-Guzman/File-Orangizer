package main;

import java.io.File;
import java.util.LinkedList;

import javax.swing.table.DefaultTableModel;

public class TableResultsModel extends DefaultTableModel {
	LinkedList<TableEntry> rows = new LinkedList<TableEntry>();
	
	public TableResultsModel(Object rowData[][], Object columnNames[]) {
        super(rowData, columnNames);
    }
	
	public void empty() {
		this.setRowCount(0);
		rows.clear();
	}
	
	public void addRowEntry(TableEntry tableEntry) {
		this.rows.add(tableEntry);
		String tagsString = new String();
		for (String tag : tableEntry.getTags()) {
			tagsString += tag + ", ";
		}
		this.addRow(new Object[]{ tableEntry.getName(), tableEntry.getParentFolder(), tagsString});
	}
	
	
	public TableEntry getRow(int index) {
		return rows.get(index);
	}
	
	public String getFileName(int row) {
		return rows.get(row).getFileName();
	}
	
	public File getParentFolder(int row) {
		return rows.get(row).getParentFolder();
	}
	
	public LinkedList<String> getTags(int row) {
		return rows.get(row).getTags();
	}
	
	public void removeDataRow(int index) {
		this.removeRow(index);
		rows.remove(index);
	}
	
	public boolean isCellEditable(int row, int column){  
        return false;  
    }

}
