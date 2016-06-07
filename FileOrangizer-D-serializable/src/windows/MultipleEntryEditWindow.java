package windows;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

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

import main.FOWindow;
import main.Organization;

public class MultipleEntryEditWindow extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField tagsTextField;
	boolean changed = false;

	/**
	 * Create the dialog.
	 */
	
	
	public MultipleEntryEditWindow(int[] indicesInDirectory, int indexOfDirectoryInPaths) {
		
		setResizable(false);
		setTitle("Add Tags");
		setBounds(100, 100, 633, 327);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(20, 39, 261, 207);
		contentPanel.add(scrollPane);
		
		DefaultListModel DLMselectedFilesList = new DefaultListModel();
		JList selectedFilesList = new JList(DLMselectedFilesList);
		scrollPane.setViewportView(selectedFilesList);
		
		for (int index : indicesInDirectory) {
			DLMselectedFilesList.addElement(FOWindow.paths.get(indexOfDirectoryInPaths).getEntryAt(index).getFileName());
		}
		
		JLabel lblNewLabel = new JLabel("Selected files to add tags to:");
		lblNewLabel.setBounds(20, 14, 221, 14);
		contentPanel.add(lblNewLabel);
		
		tagsTextField = new JTextField();
		tagsTextField.setBounds(288, 39, 319, 20);
		contentPanel.add(tagsTextField);
		tagsTextField.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Enter tags to add separated by commas:");
		lblNewLabel_1.setBounds(288, 11, 210, 14);
		contentPanel.add(lblNewLabel_1);
		
		JButton btnAddTags = new JButton("Add tags");
		btnAddTags.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				boolean tagsAdded = false;
				for (String tag : Organization.parseSearch(tagsTextField.getText())) {
					for (int index : indicesInDirectory) {
						if(!FOWindow.paths.get(indexOfDirectoryInPaths).getEntryAt(index).getTags().contains(tag)) {
							FOWindow.paths.get(indexOfDirectoryInPaths).getEntryAt(index).addTag(tag);
							changed   = true;
							tagsAdded = true;
						}
					}
				}
				
				if (tagsAdded)
					JOptionPane.showMessageDialog(null,"Tags added.");
			}
		});
		btnAddTags.setBounds(291, 70, 89, 23);
		contentPanel.add(btnAddTags);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Save");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						
						try {
							FOWindow.savePaths();
						} catch (IOException e) {
							JOptionPane.showMessageDialog(null,"UNABLE TO SAVE.");
							e.printStackTrace();
						}
						dispose();
						
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Done");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if (changed) {
							if (JOptionPane.showConfirmDialog(null, "You have made changes but you haven't saved. Would you like to save?", "WARNING",
							        JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
							    try {
									FOWindow.savePaths();
								} catch (IOException e1) {
									JOptionPane.showMessageDialog(null,"UNABLE TO SAVE.");
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
