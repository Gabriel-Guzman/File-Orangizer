package main;

import java.util.LinkedList;
import java.util.List;

public class Organization {
	
	//Will return if two lists are different or not.
	public static boolean compareLists(List<String> listOne, List<String> listTwo){
		//NOTE: This is only for lists of entries
		
		boolean areEqual = true;
		
		if (listOne.size() != listTwo.size()) {
			areEqual = false;
		} else {
		
			for (int i = 0; i < listOne.size(); i++) {
				if (listOne.get(i) != listTwo.get(i)){
					areEqual = false;
				}
			}
		}
		
		return areEqual;
	}
	
	// Returns the index where listOne and listTwo become different
	public static int pointOfDivergence(List<String> listOne, List<String> listTwo) {
		int point = 0;
		if (listOne.size() == listTwo.size() || listOne.size() < listTwo.size()){
			for (int i = 0; i < listOne.size(); i++){
				if (!listOne.get(i).matches(listTwo.get(i))) {
					point = i;
					break;
				}
			}
		}
		else if (listOne.size() > listTwo.size()){
			for (int i = 0; i < listTwo.size(); i++){
				if (!listOne.get(i).matches(listTwo.get(i))) {
					point = i;
					break;
				}
			}
		}
		
		return point;
	}
	
	//Returns just the file name of a path to a file.
	public static String pathToFileName(String path) {
		return path.split("\\\\")[path.split("\\\\").length - 1];
	}
	
	//Will inject a string into a LinkedList<String> at nodePosition index. It will move whatever was
	//in that index forward one and replace that with node.
	public static LinkedList<String> insertNode(LinkedList<String> list, String node, int nodePosition) {
		LinkedList<String> fixedList = new LinkedList<String>();
		if (nodePosition > 0) {
		for (int i = 0; i < nodePosition; i++)
			fixedList.add(list.get(i));
		}
		fixedList.add(node);
		for (int i = nodePosition; i < list.size() - nodePosition; i++) {
			fixedList.add(list.get(i));
		}
		return fixedList;
		
	}
	
	public static LinkedList<String> alphabetic(LinkedList<String> list) {
		LinkedList<String> sorted = list;
		java.util.Collections.sort((List<String>)sorted);
		return sorted;
		
		
	}
	
	public static String generateDataFileName (String path) {
		return path.replace("\\", "-").replace(":", "-").toLowerCase();
	}
	
	public static int findEntry (String path, Directory directory) {
		int found = -1;
		
		for (int i = 0; i < directory.getEntries().size(); i++) {
			if (directory.getEntries().get(i).getPath().toLowerCase().equals(path.toLowerCase())) {
				found = i;
			}
		}
		
		return found;
	}
	
}
