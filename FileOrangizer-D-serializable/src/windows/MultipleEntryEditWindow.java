package windows;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class MultipleEntryEditWindow extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField tagsTextField;

	/**
	 * Create the dialog.
	 */
	
	public void writeToList() {
		
	}
	
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
		btnAddTags.setBounds(291, 70, 89, 23);
		contentPanel.add(btnAddTags);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Done");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						
						
						
						dispose();
						
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
}
