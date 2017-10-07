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
import javax.swing.*;

public class FacePamphlet extends Program implements FacePamphletConstants {

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
		picField.addActionListener(this);
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
		picField = new JTextField(TEXT_FIELD_SIZE);
		add(picField, WEST);
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
		}
		if (e.getActionCommand().equals("Delete")) {
			delete(str);
		}
		if (e.getActionCommand().equals("Lookup")) {
			lookUp(str);
		}
		if (e.getSource() == statusField
				|| e.getActionCommand().equals("Change Status")) {
			statusField();
		}
		if (e.getSource() == picField
				|| e.getActionCommand().equals("Change Picture")) {
			pictureField();
		}
		if (e.getSource() == friendField
				|| e.getActionCommand().equals("Add Friend")) {
			friendField();
		}
	}

	/*
	 * This method adds friend in profile friends' list. Also displays messages
	 * for user;
	 */
	private void friendField() {
		if (friendField.getText().length() > 0)
			if (currentProfile != null) {
				if (dat.containsProfile(friendField.getText())
						&& currentProfile != dat.getProfile(friendField
								.getText())) {
					currentProfile.addFriend(friendField.getText());
					dat.getProfile(friendField.getText()).addFriend(
							currentProfile.getName());
					canvas.displayProfile(currentProfile);
					canvas.showMessage(friendField.getText()
							+ " added as a friend");
				} else {
					canvas.showMessage(currentProfile.getName()
							+ " already has " + friendField.getText()
							+ " as a friend.");
				}
			} else {
				canvas.showMessage("Please select a profile to add friend");
			}
	}

	/*
	 * This method set image on profile using file location written in field.
	 * Also displays messages for user;
	 */
	private void pictureField() {
		if (picField.getText().length() > 0)
			if (currentProfile != null) {
				GImage image = null;
				try {
					if (picField.getText().length() > 0) {
						image = new GImage(picField.getText());
						currentProfile.setImage(image);
						canvas.displayProfile(currentProfile);
						canvas.showMessage("Picture updated");
					}
				} catch (ErrorException ex) {
					canvas.showMessage("Unable to open image file: "
							+ picField.getText());
				}
			} else {
				canvas.showMessage("Please select a profile to change picture");
			}
	}

	/*
	 * This method adds status for profile. Also displays messages for user;
	 */
	private void statusField() {
		if (statusField.getText().length() > 0)
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

	/*
	 * This method adds profile in database. Also displays messages for user;
	 */
	private void add(String str) {
		if (nameField.getText().length() > 0)
			if (dat.containsProfile(str)) {
				canvas.displayProfile(dat.getProfile(str));
				canvas.showMessage("A profile with the name "
						+ dat.getProfile(str).getName() + " already exists");
				currentProfile = dat.getProfile(str);
			} else {
				FacePamphletProfile profile = new FacePamphletProfile(str);
				dat.addProfile(profile);
				currentProfile = profile;
				currentProfile.setStatus("No current Status");
				canvas.displayProfile(currentProfile);
				canvas.showMessage("New profile created");
			}
	}

	/*
	 * This method deletes profile in database. Also displays messages for user;
	 */
	private void delete(String str) {
		if (nameField.getText().length() > 0)
			if (dat.containsProfile(str)) {
				canvas.removeAll();
				canvas.showMessage("Profile of "
						+ dat.getProfile(str).getName() + " deleted");
				dat.deleteProfile(str);
				currentProfile = null;
			} else {
				canvas.showMessage("A profile with the name "
						+ dat.getProfile(str).getName() + " already exists*");
			}
	}

	/*
	 * This method looks for name of profile in database and displays if
	 * possible. Also displays messages for user;
	 */
	private void lookUp(String str) {
		if (nameField.getText().length() > 0)
			if (dat.containsProfile(str)) {
				currentProfile = dat.getProfile(str);
				canvas.displayProfile(currentProfile);
				canvas.showMessage("Displaying " + currentProfile.getName());
			} else {
				currentProfile = null;
				canvas.removeAll();
				canvas.showMessage("A profile with the name " + str
						+ " does not exist");
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
	private JTextField picField;
	private JTextField friendField;
	private FacePamphletDatabase dat;
	private FacePamphletProfile currentProfile;
	private FacePamphletCanvas canvas;

}
