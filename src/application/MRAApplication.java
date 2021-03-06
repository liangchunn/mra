package application;

import java.util.ArrayList;

import datatypes.ChatData;
import dbadapter.GroupFacade;
import extraClasses.MyResult;
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
	public boolean createGroup(String groupName, String adminUserName, ArrayList<String> memberUserNames) {
		return GroupFacade.getInstance().createGroup(groupName, adminUserName, memberUserNames);
	}

	@Override
	public boolean addUserToGroup(String groupName, String userName) {
		return GroupFacade.getInstance().addUserToGroup(groupName, userName);
	}

	@Override
	public boolean chatLogin(String groupName, Integer userId) {
		return GroupFacade.getInstance().chatLogin(groupName, userId);
	}

	@Override
	public boolean sendMessages(String groupName, Integer userId, String message) {
		return GroupFacade.getInstance().saveMessage(groupName, userId, message);
	}

	@Override
	public ArrayList<ChatData> receiveMessages(String groupName) {
		return GroupFacade.getInstance().showMessages(groupName);
	}

	@Override
	public MyResult leaveGroup(String groupName, Integer userId) {
		return GroupFacade.getInstance().leaveGroup(groupName, userId);
	}

}
