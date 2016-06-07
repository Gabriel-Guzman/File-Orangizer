package main;

import java.io.Serializable;
import java.util.LinkedList;

public class Directory implements Serializable {
	String path;
	LinkedList<Entry> entries;
	
	public Directory (String path, LinkedList<Entry> entries) {
		this.path = path;
		this.entries = entries;
	}
	
	public Directory (String path) {
		this.path = path;
		this.entries = new LinkedList<Entry>();
	}
	
	public String getPath() {
		return this.path;
	}
	
	public LinkedList<Entry> getEntries() {
		return this.entries;
	}
	
	public Entry getEntryAt(int index) {
		return entries.get(index);
	}
	
	public void addEntry (Entry entry) {
		this.entries.add(entry);
	}
	
	public void removeEntry (int index) {
		this.entries.remove(index);
	}
}
