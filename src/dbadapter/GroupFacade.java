package dbadapter;

import interfaces.IAddUserToGroup;
import interfaces.ICheckIfGroupNameExists;
import interfaces.ICreateGroup;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * Created by liangchun on 14.01.18.
 */
public class GroupFacade implements ICheckIfGroupNameExists, IAddUserToGroup, ICreateGroup{
    private static GroupFacade instance;

    private GroupFacade() {
        try {
            Class.forName("com." + Configuration.getType() + ".jdbc.Driver")
                    .newInstance();
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static GroupFacade getInstance() {
        if (instance == null) {
            instance = new GroupFacade();
        }
        return instance;
    }

    public Integer getUserId(String userName) {
        if (userName.length() <= 0 || userName == null) {
            System.out.println("Username cannot be empty or null");
            return null;
        }
        try (Connection connection = DriverManager.getConnection(
                "jdbc:" + Configuration.getType() + "://"
                        + Configuration.getServer() + ":"
                        + Configuration.getPort() + "/"
                        + Configuration.getDatabase(), Configuration.getUser(),
                Configuration.getPassword())) {
            try (PreparedStatement ps = connection.prepareStatement(QueryConstants.GroupQueries.GET_USER_ID)) {
                ps.setString(1, userName);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.first()) {
                        Integer fetchedUserId = rs.getInt(1);
                        return fetchedUserId;
                    } else {
                        return null;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Checks if a group already existing in the database
     * @param groupName
     * @return
     */
    @Override
    public boolean checkIfGroupNameExists(String groupName) {
        try (Connection connection = DriverManager.getConnection(
                "jdbc:" + Configuration.getType() + "://"
                        + Configuration.getServer() + ":"
                        + Configuration.getPort() + "/"
                        + Configuration.getDatabase(), Configuration.getUser(),
                Configuration.getPassword())) {
            try (PreparedStatement ps = connection.prepareStatement(QueryConstants.GroupQueries.CHECK_GROUP_EXISTENCE)) {
                ps.setString(1, groupName);
                try (ResultSet rs = ps.executeQuery()) {
                    rs.first();
                    Integer groupCount = rs.getInt(1);
                    if (groupCount > 0) {
                        return true;
                    } else {
                        return false;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean addUserToGroup(String groupName, String userName) {
        Boolean exists = checkIfGroupNameExists(groupName);
        if (exists) {
            // Check if userName is not empty or empty
            if (userName.length() <= 0 || userName == null) {
                System.out.println("Username cannot be empty or null");
                return false;
            }
            Integer userId = getUserId(userName);
            if (userId == null) {
                System.out.println("User " + userName + " not found!");
                return false;
            }
            try (Connection connection = DriverManager.getConnection(
                    "jdbc:" + Configuration.getType() + "://"
                            + Configuration.getServer() + ":"
                            + Configuration.getPort() + "/"
                            + Configuration.getDatabase(), Configuration.getUser(),
                    Configuration.getPassword())) {
                try (PreparedStatement ps = connection.prepareStatement(QueryConstants.GroupQueries.ADD_TO_GROUP)) {
                    ps.setString(1, groupName);
                    ps.setInt(2, userId);
                    try {
                        int row = ps.executeUpdate();
                        if (row > 0) {
                            System.out.println("Added " + userName + " to group " + groupName);
                            return true;
                        } else {
                            System.out.println("Failed to add  " + userName + " to group " + groupName);
                            return false;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Group " + groupName + " does not exist!");
            return false;
        }
        return false;
    }

    @Override
    public boolean createGroup(String groupName, String adminUserName, ArrayList<String> memberUserNames) {
        System.out.println("memberUserNames is " + memberUserNames + " ; adminUserName is " + adminUserName);
        Boolean exists = checkIfGroupNameExists(groupName);
        Integer adminId = getUserId(adminUserName);
        if (adminId == null) {
            System.out.println("Admin User " + adminUserName + " not found!");
            return false;
        }
        if (!exists) {
            try (Connection connection = DriverManager.getConnection(
                    "jdbc:" + Configuration.getType() + "://"
                            + Configuration.getServer() + ":"
                            + Configuration.getPort() + "/"
                            + Configuration.getDatabase(), Configuration.getUser(),
                    Configuration.getPassword())) {
                try (PreparedStatement ps = connection.prepareStatement(QueryConstants.GroupQueries.CREATE_GROUP)) {
                    ps.setString(1, groupName);
                    ps.setInt(2, adminId);
                    try {
                        // Create group
                        int rows = ps.executeUpdate();
                        if (rows > 0) {
                            // Add admin as a group member
                            addUserToGroup(groupName, adminUserName);
                            // Add other people as group members
                            for (int i = 0; i < memberUserNames.size(); i++) {
                                addUserToGroup(groupName, memberUserNames.get(i));
                            }

                            return true;

                            /* The code block below is used to check if a all users are successfully added to the group
                            // Add the admin first
                            addMembersSuccess.add(addUserToGroup(groupName, adminUserName));
                            // Now add users
                            for (int i = 0; i < memberUserNames.size(); i++) {
                                addMembersSuccess.add(addUserToGroup(groupName, memberUserNames.get(i)));
                            }
                            // Then check if the length is the same
                            if (addMembersSuccess.size() == memberUserNames.size()) {
                                // now check if all are successful
                                for (int i = 0; i < addMembersSuccess.size(); i++) {
                                    if (addMembersSuccess.get(i) && addMembersSuccess.get(i) != null) {
                                        continue;
                                    } else {
                                        return false;
                                    }
                                }
                                return true;
                            }*/
                        } else {
                            return false;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
