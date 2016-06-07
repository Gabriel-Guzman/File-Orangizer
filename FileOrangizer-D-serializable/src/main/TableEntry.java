package main;

import java.io.File;
import java.util.LinkedList;

public class TableEntry extends File {

	public String fileName;
	public File parentFolder;
	public LinkedList<String> tags;
	public String path;
	
	public TableEntry(File parent, String child) {
		super(parent, child);
	}
	
	public TableEntry(String path) {
		super(path);
		this.fileName 		= path.substring(path.lastIndexOf("\\"));
		this.parentFolder 	= new File(path.substring(0, path.lastIndexOf("\\")));
	}
	public TableEntry(Entry entry) {
		super(entry.getPath());
		this.parentFolder	= new File(entry.getParentFolder());
		this.tags 			= entry.getTags();
		this.path 			= entry.getPath();
		this.fileName 		= entry.getFileName();
	}
	
	public TableEntry(String path, LinkedList<String> tags) {
		super(path);
		this.fileName 		= path.substring(path.lastIndexOf("\\"));
		this.parentFolder 	= new File(path.substring(0, path.lastIndexOf("\\")));
		this.tags 			= tags;
	}
	
	
	
	public LinkedList<String> getTags() {
		return this.tags;
	}
	
	public String getFileName() {
		return this.fileName;
	}
	
	public File getParentFolder() {
		return this.parentFolder;
	}
	
}
