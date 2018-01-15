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
	public boolean createGroup(String groupName, Integer adminId, ArrayList<Integer> memberIds) {
		return GroupFacade.getInstance().createGroup(groupName, adminId, memberIds);
	}

	@Override
	public boolean addUserToGroup(String groupName, Integer userId) {
		return GroupFacade.getInstance().addUserToGroup(groupName, userId);
	}
}
