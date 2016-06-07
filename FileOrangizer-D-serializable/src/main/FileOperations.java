package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

public class FileOperations {
	
	public static LinkedList<String> getPathsAsFileName(String currentPath){
		
		File dir = new File(currentPath);
		File[] directoryListing = dir.listFiles();
		LinkedList<String> listing = new LinkedList<String>();
		
		if (directoryListing != null) {
			for (File child : directoryListing) {
				if (!child.isDirectory())
					 listing.add(Organization.pathToFileName(child.getAbsolutePath()));
			}
		} else {
			// Handle the case where dir is not really a directory.
			// Checking dir.isDirectory() above would not be sufficient
			// to avoid race conditions with another process that deletes
			// directories.
		}
		
		return listing;
	}
	
	public static LinkedList<String> getPathsFromFile(String fileName) throws IOException {
		
		LinkedList<String> paths = new LinkedList<String>();
		String line = null;
		
		try {
			FileReader fileReader = new FileReader(fileName);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			
			while ((line = bufferedReader.readLine()) != null){
				paths.add(line.substring(line.indexOf("\\") + 1, line.length())); //Adds just the filename from the line
			}
			
			bufferedReader.close();
		
		} catch (FileNotFoundException ex) {	
			paths.add("failed");
		}
		
		return paths;
	}
	
public static LinkedList<String> getPaths(String currentPath){
		
		File dir = new File(currentPath);
		File[] directoryListing = dir.listFiles();
		LinkedList<String> listing = new LinkedList<String>();
		
		if (directoryListing != null) {
			for (File child : directoryListing) {
				if (!child.isDirectory())
					 listing.add(child.getAbsolutePath());
			}
		} else {
			// Handle the case where dir is not really a directory.
			// Checking dir.isDirectory() above would not be sufficient
			// to avoid race conditions with another process that deletes
			// directories.
		}
		
		return listing;
	}
	
	
	
	public static LinkedList<String> linesAsLinkedList(String fileName) throws IOException {
		LinkedList<String> paths = new LinkedList<String>();
		String line = null;
		
		try {
			FileReader fileReader = new FileReader(fileName);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			
			while ((line = bufferedReader.readLine()) != null){
				paths.add(line); //Adds just the filename from the line
			}
			
			bufferedReader.close();
		
		} catch (FileNotFoundException ex) {	
			paths.add("failed");
		}
		
		return paths;
	}
}
