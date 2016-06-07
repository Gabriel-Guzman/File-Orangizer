package main;

import java.util.LinkedList;

import javax.swing.DefaultListModel;

public class SelectedDirectory extends DefaultListModel {
	LinkedList<Directory> elements = new LinkedList<Directory>();
	
	public void addDirectory (Directory file) {
		elements.add(file);
		this.addElement(file.getPath());
	}
	
	public void removeDirectory (int index) {
		elements.remove(index);
		this.remove(index);
	}
	
	public Directory getDirectory(int index) {
		return this.elements.get(index);
	}
}
