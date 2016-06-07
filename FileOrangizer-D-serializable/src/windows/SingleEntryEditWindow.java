package windows;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Collections;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import main.Entry;
import main.FOWindow;
import main.Organization;

public class SingleEntryEditWindow extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField textField;
	private int indexOfDirectoryInPaths;
	private int indexOfEntryInDirectory;
	private boolean changed;
	/**
	 * Create the dialog.
	 */
	public SingleEntryEditWindow(int indexOfDirectoryInPaths, int indexOfEntryInDirectory) {
		this.indexOfDirectoryInPaths = indexOfDirectoryInPaths;
		this.indexOfEntryInDirectory = indexOfEntryInDirectory;
		this.changed = false;
		
		Entry currentEntry = FOWindow.paths.get(indexOfDirectoryInPaths).getEntryAt(indexOfEntryInDirectory);
		
		setTitle("Edit Tags");
		setResizable(false);
		setBounds(100, 100, 516, 387);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 40, 248, 274);
		contentPanel.add(scrollPane);
		
		DefaultListModel DLMlist = new DefaultListModel();
		JList list = new JList(DLMlist);
		scrollPane.setViewportView(list);
		for (String tag : FOWindow.paths.get(indexOfDirectoryInPaths).getEntryAt(indexOfEntryInDirectory).getTags()) {
			DLMlist.addElement(tag);
		}
		
		JLabel lblTagsForSelected = new JLabel("Tags for selected file \"" + Organization.pathToFileName(FOWindow.paths.get(indexOfDirectoryInPaths).getEntryAt(indexOfEntryInDirectory).getPath()) + "\"");
		
		lblTagsForSelected.setBounds(10, 11, 224, 14);
		contentPanel.add(lblTagsForSelected);
		
		textField = new JTextField();
		textField.setBounds(271, 39, 230, 20);
		contentPanel.add(textField);
		textField.setColumns(10);
		
		JLabel lblEnterTagsTo = new JLabel("Enter tags to add separated by commas");
		lblEnterTagsTo.setBounds(271, 11, 216, 14);
		contentPanel.add(lblEnterTagsTo);
		
		JButton btnNewButton = new JButton("Add tags");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(textField.getText().length() != 0) {
					changed = true;
					DLMlist.clear();
					String[] input = Organization.parseSearch(textField.getText());
					for (String tag : input) {
						if(!FOWindow.paths.get(indexOfDirectoryInPaths).getEntryAt(indexOfEntryInDirectory).getTags().contains(tag))
							FOWindow.paths.get(indexOfDirectoryInPaths).getEntryAt(indexOfEntryInDirectory).addTag(tag);
					}
					Collections.sort( FOWindow.paths.get(indexOfDirectoryInPaths).getEntryAt(indexOfEntryInDirectory).tags);
					for (String tag : FOWindow.paths.get(indexOfDirectoryInPaths).getEntryAt(indexOfEntryInDirectory).tags) {
						DLMlist.addElement(tag);
					}
				}
			}
		});
		
		btnNewButton.setBounds(271, 70, 97, 23);
		contentPanel.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Remove Tags");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				for (int i = list.getSelectedIndices().length - 1; i >= 0; i--) {
					FOWindow.paths.get(indexOfDirectoryInPaths).getEntryAt(indexOfEntryInDirectory).removeTag(list.getSelectedIndices()[i]);
				}
				
				DLMlist.clear();
				
				for (String tag : FOWindow.paths.get(indexOfDirectoryInPaths).getEntryAt(indexOfEntryInDirectory).tags) {
					DLMlist.addElement(tag);
				}
			}
		});
		btnNewButton_1.setBounds(271, 104, 97, 23);
		contentPanel.add(btnNewButton_1);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Save");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						
						try {
							FOWindow.savePaths();
						} catch (IOException e1) {
							e1.printStackTrace();
						} finally {
							changed = false;
						}
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Close");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						
						if (changed) {
							if (JOptionPane.showConfirmDialog(null, "You have made changes but you haven't saved. Would you like to save?", "WARNING",
							        JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
							    try {
									FOWindow.savePaths();
								} catch (IOException e1) {
									e1.printStackTrace();
								}
							    
							    dispose();
							} else {
								dispose();
							}
						} else {
							dispose();
						}
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
}
