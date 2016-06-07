package main;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileFilter;

import windows.DirectoryRestrictedFileSystemView;

public class FOWindow extends JFrame {

	private JPanel contentPane;
	private static String dataFolderPath;
	public static File directoriesFile;
	public static LinkedList<Directory> paths;
	private static JTable table;
	private JTextField searchTextField;
	public static File crashLog = new File("crashlog.txt");
	private static JDialog editWindow;
	private static LinkedList<Entry> entriesToSearch;
	
	

	/**
	 * Launch the application.
	 * @throws URISyntaxException 
	 * @throws IOException 
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void main(String[] args) throws URISyntaxException, IOException {
		String currentRunningPath = FOWindow.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
		
		//Getting path of running jar file
		String currentPath = currentRunningPath.substring(0, currentRunningPath.lastIndexOf("/") + 1);
		dataFolderPath = currentPath + "\\FileOrganism";
		directoriesFile = new File(dataFolderPath + "\\directories.ser");
		

		
		//Creating data file directory
		File directory = new File(dataFolderPath);
		if (!directory.exists()) {
			if (!directory.mkdir()) {
				printError("Failed to make directory");
				System.exit(1);
			}
		}
		
		if (!(directoriesFile.exists())) { //Directory file doesn't exist
			//Create new empty data file
			OutputStream file = new FileOutputStream(directoriesFile);
			OutputStream buffer = new BufferedOutputStream(file);
			ObjectOutput output = new ObjectOutputStream(buffer);
		    output.writeObject(new LinkedList<Directory>());
		    output.close();
		    paths = new LinkedList<Directory>();
		    
			
		} else {
			//Directory file does exist
			
			try{
			      //use buffering
				  InputStream file = new FileInputStream(directoriesFile);
				  InputStream buffer = new BufferedInputStream(file);
				  ObjectInput input = new ObjectInputStream (buffer);
				  try{
				    //Deserialize the list inside the paths file
				    paths = (LinkedList<Directory>)input.readObject();
				    
				  } catch (ClassNotFoundException e) {
					printError("Failed to deserialize directoriesFile");
					e.printStackTrace();
					
				input.close();
				} catch (Exception e) {
					printError("Failed to create inptustream/objectinput for directoriesFile");
					e.printStackTrace();
				}
			
			//Time to compare files in each directory with the files in the serialized directories
			for (Directory path : paths) {
				
				LinkedList<String> localPaths = FileOperations.getPaths(path.getPath());
				for (Entry entry : path.getEntries()) {
					if (localPaths.contains(entry.getPath())) {
						localPaths.remove(localPaths.indexOf(entry.getPath()));
					} else {
						path.removeEntry(path.getEntries().indexOf(entry));
					}
				}
				
				for (String pathToAdd : localPaths) {
					path.addEntry(new Entry(pathToAdd));
				}
			}
			
			savePaths();
			
			} catch (Exception e) {
				printError("Failed to reserialize fixed entries");
				e.printStackTrace();
			}
		}
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FOWindow frame = new FOWindow();
					frame.setVisible(true);
					frame.setResizable(false);
				} catch (Exception e) {
					try {
						printError("Failed to make window");
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public FOWindow() {
		try {
			changeLaf(this);
		} catch ( Exception e) {
			e.printStackTrace();
		}
		
		entriesToSearch = new LinkedList<Entry>();
		
		setTitle("FileOrangizer");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 876, 518);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//This chooser is used for the "add directory" function
		JFileChooser chooser = new windows.ThumbnailFileChooser();
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 102, 520, 366);
		contentPane.add(scrollPane_1);
		
		JScrollPane scrollPane = new JScrollPane();
	
		scrollPane.setBounds(556, 171, 294, 241);
		contentPane.add(scrollPane);

		
		SelectedDirectory DLMDirectoriesToInclude = new SelectedDirectory();
		JList list = new JList(DLMDirectoriesToInclude);
		
		//Creating popup menu for directories to use
		JPopupMenu DTUPopupMenu = new JPopupMenu ( "Menu" );
		JMenuItem DTURemove = new JMenuItem( "Remove" );
		DTURemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int dialogButton = JOptionPane.YES_NO_OPTION;
				int dialogResult = JOptionPane.showConfirmDialog (null, "Are you sure you want to remove this directory? Cannot be undone","Warning",dialogButton);
				if(dialogResult == JOptionPane.YES_OPTION){
				  
					paths.remove(list.getSelectedIndex());
					
					try {
						OutputStream file 	= new FileOutputStream(directoriesFile);
						OutputStream buffer = new BufferedOutputStream(file);
						ObjectOutput output = new ObjectOutputStream(buffer);
						output.writeObject(paths);
						output.close();
					} catch (Exception e) {
						
						e.printStackTrace();
					}
					
					DLMDirectoriesToInclude.remove(list.getSelectedIndex());
					for (Directory directory : paths) {
						System.out.println(directory.getPath());
					}
				}
			}
			
		});
		DTUPopupMenu.add(DTURemove);
		contentPane.add(DTUPopupMenu);
		
		list.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent arg0) {
				if ( SwingUtilities.isRightMouseButton(arg0) ) {
					list.setSelectedIndex(list.locationToIndex(arg0.getPoint()));
					DTUPopupMenu.show(arg0.getComponent(), arg0.getX(), arg0.getY());
				}
			}
		});
		scrollPane.setViewportView(list);
		for (Object directory : paths) {
			DLMDirectoriesToInclude.addDirectory((Directory) directory);
		}
		
		table = new JTable();
		table.getTableHeader().setReorderingAllowed(false);
		table.setSelectionModel(new ForcedListSelectionModel());
		TableResultsModel tableDLM = new TableResultsModel(
				new Object[][] {
				},
				new String[] {
					"File Name", "Parent Folder", "Tags"
				}
		);
		
		table.setModel(tableDLM);
		scrollPane_1.setViewportView(table);
		
		searchTextField = new JTextField();
		searchTextField.setColumns(10);
		searchTextField.setBounds(10, 42, 395, 26);
		contentPane.add(searchTextField);
		
		tableDLM.addRow(new Object[]{"HEY", "man"});
		
		JButton searchButton = new JButton("Search tag(s)");
		
		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Emptying the results
				tableDLM.empty();
				
				//Takes in the text from
				LinkedList<String> queryList = new LinkedList<String>(Arrays.asList(searchTextField.getText().split(",")));
				for (int i = 0; i < queryList.size(); i ++) {
					queryList.set(i, queryList.get(i).trim());
				}
				
				LinkedList<Entry> entryResults = new LinkedList<Entry>();
				
				int[] selection = list.getSelectedIndices();
				for (String query : queryList) {
					for (int index : selection) {
						for (Entry entry : DLMDirectoriesToInclude.getDirectory(index).getEntries()) {
							if (entry.getTags().contains(query) && !entryResults.contains(entry)) 
								entryResults.add(entry);
						}
					}
				}
				
				for (Entry result : entryResults) {
					tableDLM.addRowEntry(new TableEntry (result));
				}
				
			}
		});
		searchButton.setBounds(415, 41, 115, 29);
		contentPane.add(searchButton);
		
		JLabel labelEnterSearchTerms = new JLabel("Enter search term(s) separated by commas");
		labelEnterSearchTerms.setBounds(10, 11, 357, 20);
		contentPane.add(labelEnterSearchTerms);
		
		JButton btnEditTags = new JButton("Edit tags");
		btnEditTags.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if (list.getSelectedIndices().length == 1) {
					Directory selectedDirectory = DLMDirectoriesToInclude.getDirectory(list.getSelectedIndex());
					DirectoryRestrictedFileSystemView DRFSW = new DirectoryRestrictedFileSystemView(new File(selectedDirectory.getPath()));
					JFileChooser chooser = new windows.ThumbnailFileChooser(DRFSW);
					chooser.setCurrentDirectory(new File(selectedDirectory.getPath()));
					chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
					chooser.setMultiSelectionEnabled(true);
					
					
					int returnVal = chooser.showOpenDialog(getParent());
					if (returnVal == JFileChooser.APPROVE_OPTION) {
						File[] files = chooser.getSelectedFiles();
						if ( files.length > 1 ) { //If the user selects multiple files, only the option to add tags is available
							
							int[] indices = new int[files.length];
							for (int i = 0; i < files.length; i++) {
								indices[i] = Organization.findEntry(files[i].getAbsolutePath(), selectedDirectory);
							}
							
							editWindow = new windows.MultipleEntryEditWindow(indices, list.getSelectedIndex());
							editWindow.setModal(true);
							editWindow.setVisible(true);
							
						}
						
						else if ( files.length == 1 ) {
							int indexOfDirectoryInPaths = list.getSelectedIndex();
							int indexOfEntryInDirectory = Organization.findEntry(files[0].getAbsolutePath(), paths.get(indexOfDirectoryInPaths));
							editWindow = new windows.SingleEntryEditWindow(indexOfDirectoryInPaths, indexOfEntryInDirectory);
							editWindow.setModal(true);
							editWindow.setVisible(true);
							
						}
					}
				}
			}
		});
		btnEditTags.setBounds(556, 74, 115, 29);
		contentPane.add(btnEditTags);
		
		JButton btnLocate = new JButton("Locate");
		btnLocate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (tableDLM.rows.size() > 0)
					Runtime.getRuntime().exec("explorer.exe " + tableDLM.getRow(table.getSelectedRow()).getAbsolutePath());
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		btnLocate.setBounds(556, 114, 115, 29);
		contentPane.add(btnLocate);
		
		JLabel lblIncludeTheFollowing = new JLabel("Include the following directories:");
		lblIncludeTheFollowing.setBounds(556, 154, 265, 14);
		contentPane.add(lblIncludeTheFollowing);
		
		JButton folderSelectorButton = new JButton("Add directory");
		folderSelectorButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				int returnVal = chooser.showOpenDialog(getParent());
				if(returnVal == JFileChooser.APPROVE_OPTION) {
				   File directoryToAdd = chooser.getSelectedFile();
				   Directory D = new Directory(directoryToAdd.getAbsolutePath());
				   boolean isIn = false;
				   
				   for ( Directory directory : paths ) {
					   if (directory.getPath().equals(D.getPath())) {
					   		isIn = true;
					   }
				   }
				   
				   if (!isIn && directoryToAdd.exists()) {
					   
					   for (Object path : FileOperations.getPaths(directoryToAdd.getAbsolutePath())) {
						   D.addEntry(new Entry(path.toString()));
					   }
						  
					   paths.add(D);
					   
					   //Rewrite dataFile with new directory
					   try {
						   
						savePaths();
						
						DLMDirectoriesToInclude.clear();
						for (Object element : paths) {
							DLMDirectoriesToInclude.addDirectory((Directory) element);
						}
						
					   } catch (IOException e) {
						   e.printStackTrace();
					   }
				   } else {
					   JOptionPane.showMessageDialog(null, "Directory already added", "Error", JOptionPane.ERROR_MESSAGE);
				   }
				}
			}
		});
		folderSelectorButton.setBounds(556, 423, 115, 23);
		contentPane.add(folderSelectorButton);
		
	}
	
	public Entry addTagsToEntry(Entry entry, String[] tagsToAdd) {
		Entry tempEntry = entry;
		for (String tag : tagsToAdd) {
			tempEntry.addTag(tag);
		}
		LinkedList<String> tagsToSort = tempEntry.getTags();
		Collections.sort(tagsToSort);
		
		tempEntry.setTags(tagsToSort);
		return tempEntry;
	}
	
	public Entry removeTagsFromEntry(Entry entry, int index) {
		Entry tempEntry = entry;
		tempEntry.removeTag(index);
		return tempEntry;
	}
	
	static void printError(String message) throws IOException {
		BufferedWriter troubleBuffer = new BufferedWriter(new FileWriter(crashLog));
		troubleBuffer.write(message);
		troubleBuffer.close();
	}
	
	public static void changeLaf(JFrame frame) throws InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException  e) {
            e.printStackTrace();
        }
        SwingUtilities.updateComponentTreeUI(frame);
    }
	
	public static void savePaths() throws FileNotFoundException, IOException{
		OutputStream file = new FileOutputStream(directoriesFile);
		OutputStream buffer = new BufferedOutputStream(file);
		ObjectOutput output = new ObjectOutputStream(buffer);
	    output.writeObject(paths);
	    output.close();
	}
}
