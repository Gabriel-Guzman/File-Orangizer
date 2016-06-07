package main;

import java.io.Serializable;
import java.util.LinkedList;

public class Entry implements Serializable {
	String path;
	public LinkedList<String> tags;
	
	public Entry (String path, LinkedList<String> tags) {
		this.path = path;
		this.tags = tags;
	}
	
	public Entry (String path) {
		this.path = path;
		this.tags = new LinkedList<String>();
	}
	
	public void removeTag(String element) {
		tags.remove(tags.indexOf(element));
	}
	
	public void removeTag(int i) {
		tags.remove(i);
	}
	
	public void addTag(String element) {
		tags.add(element);
	}
	
	public String getFileName() {
		return this.path.substring(this.path.lastIndexOf("\\"));
	}
	
	public String getParentFolder() {
		return this.path.substring(0 , this.path.lastIndexOf("\\") + 1);
	}
	
	public LinkedList<String> getTags(){
		return tags;
	}
	
	public void setTags(LinkedList<String> tagsToAdd) { //Will delete all current tags! Careful.
		this.tags = tagsToAdd;
	}
	
	public String getPath(){
		return path;
	}

}
