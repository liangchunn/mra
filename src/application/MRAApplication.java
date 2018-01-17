package application;

import java.sql.Timestamp;
import java.util.ArrayList;

import datatypes.ChatData;
import dbadapter.GroupFacade;
import interfaces.UCmds;


public class MRAApplication implements UCmds {

	private static MRAApplication instance;

	public static MRAApplication getInstance() {
		if (instance == null) {
			instance = new MRAApplication();
		}

		return instance;
	}

	@Override
	public boolean createGroup(String groupName, Integer adminId, ArrayList<Integer> memberIds) {
		return GroupFacade.getInstance().createGroup(groupName, adminId, memberIds);
	}

	@Override
	public boolean addUserToGroup(String groupName, Integer userId) {
		return GroupFacade.getInstance().addUserToGroup(groupName, userId);
	}

	@Override
	public boolean chatLogin(String groupName, Integer userId) {
		return GroupFacade.getInstance().chatLogin(groupName, userId);
	}

	@Override
	public boolean sendMessages(String groupName, Integer userId, String message) {
		return GroupFacade.getInstance().sendMessages(groupName, userId, message);
	}

	@Override
	public ArrayList<ChatData> receiveMessages(String groupName) {
		return GroupFacade.getInstance().receiveMessages(groupName);
	}

}
