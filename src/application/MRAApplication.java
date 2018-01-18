package application;

import java.util.ArrayList;

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
	public boolean createGroup(String groupName, String adminUserName, ArrayList<String> memberUserNames) {
		return GroupFacade.getInstance().createGroup(groupName, adminUserName, memberUserNames);
	}

	@Override
	public boolean addUserToGroup(String groupName, String userName) {
		return GroupFacade.getInstance().addUserToGroup(groupName, userName);
	}
}
