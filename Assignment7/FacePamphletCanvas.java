/*
 * File: FacePamphletCanvas.java
 * -----------------------------
 * This class represents the canvas on which the profiles in the social
 * network are displayed.  NOTE: This class does NOT need to update the
 * display when the window is resized.
 */

import acm.graphics.*;

import java.awt.*;
import java.util.*;

public class FacePamphletCanvas extends GCanvas implements
		FacePamphletConstants {

	/**
	 * Constructor This method takes care of any initialization needed for the
	 * display
	 */
	public FacePamphletCanvas() {
	}

	/**
	 * This method displays a message string near the bottom of the canvas.
	 * Every time this method is called, the previously displayed message (if
	 * any) is replaced by the new message text passed in.
	 */
	public void showMessage(String msg) {
		if (message == null)
			createMessage(msg);
		remove(message);
		createMessage(msg);
	}

	/*
	 * This method creates message to display;
	 */
	private void createMessage(String msg) {
		message = new GLabel(msg);
		message.setFont(MESSAGE_FONT);
		add(message, getWidth() / 2 - message.getWidth() / 2, getHeight()
				- BOTTOM_MESSAGE_MARGIN - message.getHeight());
	}

	/**
	 * This method displays the given profile on the canvas. The canvas is first
	 * cleared of all existing items (including messages displayed near the
	 * bottom of the screen) and then the given profile is displayed. The
	 * profile display includes the name of the user from the profile, the
	 * corresponding image (or an indication that an image does not exist), the
	 * status of the user, and a list of the user's friends in the social
	 * network.
	 */
	public void displayProfile(FacePamphletProfile profile) {
		removeAll();
		displayName(profile.getName());
		displayImage(profile.getImage());
		displayStatus(profile.getStatus());
		displayFriends(profile.getFriends());

	}

	/*
	 * This method adds friends of profile on canvas;
	 */
	private void displayFriends(Iterator<String> friends) {
		double x = getWidth() / 2;
		double y = nameY + IMAGE_MARGIN;
		GLabel profileFriends = new GLabel("Friends:");
		profileFriends.setFont(PROFILE_FRIEND_LABEL_FONT);
		add(profileFriends, x, y);
		Iterator<String> it = friends;
		double listX = getWidth() / 2;
		double listY = y;
		while (it.hasNext()) {
			String friendName = it.next();
			GLabel friendList = new GLabel(friendName);
			friendList.setFont(PROFILE_FRIEND_FONT);
			listY = listY + friendList.getAscent();
			add(friendList, listX, listY);
		}

	}

	/*
	 * This method adds status of profile on canvas;
	 */
	private void displayStatus(String status) {
		if (!status.equals("")) {
			double x = LEFT_MARGIN;
			double y = nameY + IMAGE_MARGIN + IMAGE_HEIGHT + STATUS_MARGIN;
			GLabel pstatus = new GLabel(status);
			pstatus.setFont(PROFILE_STATUS_FONT);
			if (getElementAt(x, y) != null) {
				remove(getElementAt(x, y));
			}
			add(pstatus, x, y);
		}
	}

	/*
	 * This method adds photo of profile on canvas or adds no image photo;
	 */
	private void displayImage(GImage image) {
		if (image != null) {
			image.setBounds(LEFT_MARGIN, nameY + IMAGE_MARGIN, IMAGE_WIDTH,
					IMAGE_HEIGHT);
			image.setSize(IMAGE_WIDTH, IMAGE_HEIGHT);
			add(image);
		} else {
			displayNoImage();
		}

	}

	/*
	 * This method draws no image rectangle on canvas;
	 */
	private void displayNoImage() {
		GRect imageRect = new GRect(LEFT_MARGIN, nameY + IMAGE_MARGIN,
				IMAGE_WIDTH, IMAGE_HEIGHT);
		add(imageRect);
		GLabel noImage = new GLabel("No Image");
		noImage.setFont(PROFILE_IMAGE_FONT);
		double labelWidth = LEFT_MARGIN + IMAGE_WIDTH / 2 - noImage.getWidth()
				/ 2;
		double labelHeight = nameY + IMAGE_MARGIN + IMAGE_HEIGHT / 2;
		add(noImage, labelWidth, labelHeight);
	}

	/*
	 * This method adds name of profile on canvas;
	 */
	private void displayName(String name) {
		GLabel profileName = new GLabel(name);
		profileName.setFont(PROFILE_NAME_FONT);
		profileName.setColor(Color.BLUE);
		add(profileName, LEFT_MARGIN, TOP_MARGIN + profileName.getHeight());
		nameY = profileName.getY();
	}

	/* Instance variables */
	private GLabel message;
	private double nameY;

}
