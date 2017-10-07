/* 
 * File: FacePamphlet.java
 * -----------------------
 * When it is finished, this program will implement a basic social network
 * management system.
 */

import acm.program.*;
import acm.graphics.*;
import acm.util.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileFilter;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class FacePamphletExtension extends Program implements
		FacePamphletConstants {

	/**
	 * This method has the responsibility for initializing the interactors in
	 * the application, and taking care of any other initialization that needs
	 * to be performed.
	 */
	public void init() {
		dat = new FacePamphletDatabase();
		southSide();
		westSide();
		statusField.addActionListener(this);
		friendField.addActionListener(this);
		addActionListeners();
		canvas = new FacePamphletCanvas();
		add(canvas);
	}

	/*
	 * This method adds interactors on south side of canvas.
	 */
	public void southSide() {
		name = new JLabel("Name");
		add(name, NORTH);
		nameField = new JTextField(TEXT_FIELD_SIZE);
		add(nameField, NORTH);
		add = new JButton("Add");
		add(add, NORTH);
		delete = new JButton("Delete");
		add(delete, NORTH);
		lookUp = new JButton("Lookup");
		add(lookUp, NORTH);
	}

	/*
	 * This method adds interactors on west side of canvas.
	 */
	public void westSide() {
		statusField = new JTextField(TEXT_FIELD_SIZE);
		add(statusField, WEST);
		changestat = new JButton("Change Status");
		add(changestat, WEST);
		add(new JLabel(EMPTY_LABEL_TEXT), WEST);
		changepic = new JButton("Change Picture");
		add(changepic, WEST);
		add(new JLabel(EMPTY_LABEL_TEXT), WEST);
		friendField = new JTextField(TEXT_FIELD_SIZE);
		add(friendField, WEST);
		addFriend = new JButton("Add Friend");
		add(addFriend, WEST);
	}

	/**
	 * This class is responsible for detecting when the buttons are clicked or
	 * interactors are used, so you will have to add code to respond to these
	 * actions.
	 */
	public void actionPerformed(ActionEvent e) {
		String str = nameField.getText();
		if (e.getActionCommand().equals("Add")) {
			add(str);
			nameField.setText("");
		}
		if (e.getActionCommand().equals("Delete")) {
			delete(str);
			nameField.setText("");
		}
		if (e.getActionCommand().equals("Lookup")) {
			lookUp(str);
			nameField.setText("");
		}
		if (e.getSource() == statusField
				|| e.getActionCommand().equals("Change Status")) {
			statusField();
			statusField.setText("");
		}
		if (e.getActionCommand().equals("Change Picture")) {
			pictureField();
		}
		if (e.getSource() == friendField
				|| e.getActionCommand().equals("Add Friend")) {
			friendField();
			friendField.setText("");
		}
	}

	private void friendField() {
		if (currentProfile != null) {
			if (dat.containsProfile(friendField.getText())
					&& currentProfile != dat.getProfile(friendField.getText())) {
				currentProfile.addFriend(friendField.getText());
				dat.getProfile(friendField.getText()).addFriend(
						currentProfile.getName());
				canvas.displayProfile(currentProfile);
				canvas.showMessage(friendField.getText() + " added as a friend");
			} else {
				canvas.showMessage(currentProfile.getName() + " already has "
						+ friendField.getText() + " as a friend.");
			}
		} else {
			canvas.showMessage("Please select a profile to add friend");
		}
	}

	private void pictureField() {
		if (currentProfile != null) {
			GImage image = null;
			// create JFileChooser object;
			JFileChooser fileChooser = new JFileChooser();
			// open dialog to choose file;
			int result = fileChooser.showOpenDialog(this);
			// filter extension of file;
			FileNameExtensionFilter filter = new FileNameExtensionFilter("png",
					"jpg", "gif");
			// set filter on file chooser;
			fileChooser.setFileFilter(filter);
			// get chosen files;
			File selectedFile = fileChooser.getSelectedFile();
			if (result == JFileChooser.APPROVE_OPTION
					&& filter.accept(selectedFile)) {
				image = new GImage(selectedFile.getAbsolutePath());
				currentProfile.setImage(image);
				canvas.displayProfile(currentProfile);
				canvas.showMessage("Picture updated");
			} else {
				canvas.showMessage("Unable to open image file");
			}
		} else {
			canvas.showMessage("Please select a profile to change picture");
		}
	}

	private void statusField() {
		if (currentProfile != null) {
			currentProfile.setStatus(statusField.getText());
			dat.getProfile(currentProfile.getName());
			currentProfile.setStatus(currentProfile.getName() + " is "
					+ statusField.getText());
			canvas.displayProfile(currentProfile);
			canvas.showMessage("Status updated to " + statusField.getText());
		} else {
			canvas.showMessage("Please select a profile to change status");
		}
	}

	private void add(String str) {
		if (dat.containsProfile(str)) {
			canvas.displayProfile(dat.getProfile(str));
			canvas.showMessage("A profile with the name "
					+ dat.getProfile(str).getName() + " already exists");
			currentProfile = dat.getProfile(str);
		} else {
			FacePamphletProfile profile = new FacePamphletProfile(str);
			dat.addProfile(profile);
			currentProfile = profile;
			canvas.displayProfile(currentProfile);
			canvas.showMessage("New profile created");
		}
	}

	private void delete(String str) {
		if (dat.containsProfile(str)) {
			canvas.removeAll();
			canvas.showMessage("Profile of " + dat.getProfile(str).getName()
					+ " deleted");
			dat.deleteProfile(str);
			currentProfile = null;
		} else {
			canvas.showMessage("A profile with the name "
					+ dat.getProfile(str).getName() + " already exists*");
		}
	}

	private void lookUp(String str) {
		if (dat.containsProfile(str)) {
			currentProfile = dat.getProfile(str);
			canvas.displayProfile(currentProfile);
			canvas.showMessage("Displaying " + currentProfile.getName());
		} else {
			currentProfile = null;
			canvas.removeAll();
			canvas.showMessage("A profile with the name " + str
					+ " already exists");
		}
	}

	/* Private instance variables */
	private JLabel name;
	private JButton add;
	private JButton delete;
	private JButton lookUp;
	private JButton changestat;
	private JButton changepic;
	private JButton addFriend;
	private JTextField nameField;
	private JTextField statusField;
	private JTextField friendField;
	private FacePamphletDatabase dat;
	private FacePamphletProfile currentProfile;
	private FacePamphletCanvas canvas;

}
