package dbadapter;

import datatypes.ChatData;
import extraClasses.MyResult;
import interfaces.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * Created by liangchun on 14.01.18.
 */
public class GroupFacade implements ICheckIfGroupNameExists, IAddUserToGroup, ICreateGroup, IChatLogin, IReceiveMessages, ISendMessages, ILeaveGroup, ITimer {
    private static GroupFacade instance;

    protected GroupFacade() {
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

    /**
     * Gets a user's id by its username
     * @param userName username to find
     * @return the user id
     */
    public Integer getUserId(String userName) {
        if (userName.length() <= 0) {
            System.out.println("Username cannot be empty or null");
            return null;
        }
        try (Connection connection = DriverManager.getConnection(
                "jdbc:" + Configuration.getType() + "://"
                        + Configuration.getServer() + ":"
                        + Configuration.getPort() + "/"
                        + Configuration.getDatabase(), Configuration.getUser(),
                Configuration.getPassword())) {
            try (PreparedStatement ps = connection.prepareStatement(QueryConstants.GroupQueries.GET_USER_ID_BY_USERNAME)) {
                ps.setString(1, userName);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.first()) {
                        return rs.getInt(1);
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
                    return groupCount > 0;
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
            if (userName.length() <= 0) {
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
                            for (String memberUserName : memberUserNames) {
                                addUserToGroup(groupName, memberUserName);
                            }

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
        }
        return false;
    }

    @Override
    public boolean chatLogin(String groupName, Integer userId) {
        try (Connection connection = DriverManager.getConnection(
                "jdbc:" + Configuration.getType() + "://"
                        + Configuration.getServer() + ":"
                        + Configuration.getPort() + "/"
                        + Configuration.getDatabase(), Configuration.getUser(),
                Configuration.getPassword())) {
            try (PreparedStatement ps = connection.prepareStatement(QueryConstants.GroupQueries.CHECK_CHAT_ELIGIBILITY)) {
                ps.setString(1, groupName);
                ps.setInt(2, userId);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next())
                        return true;
                    else {
                        try (PreparedStatement psB = connection.prepareStatement(QueryConstants.GroupQueries.CHECK_CHAT_ELIGIBILITY_ADMIN)) {
                            psB.setString(1, groupName);
                            psB.setInt(2, userId);
                            try (ResultSet rsB = psB.executeQuery()) {
                                return rsB.next();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
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

    /**
     * Gets the user's username by id
     * @param userId user id to check
     * @return username from userid
     */
    public String getUserName(Integer userId) {
        try (Connection connection = DriverManager.getConnection(
                "jdbc:" + Configuration.getType() + "://"
                        + Configuration.getServer() + ":"
                        + Configuration.getPort() + "/"
                        + Configuration.getDatabase(), Configuration.getUser(),
                Configuration.getPassword())) {
            try (PreparedStatement ps = connection.prepareStatement(QueryConstants.GroupQueries.GET_USERNAME_BY_USER_ID)) {
                ps.setInt(1, userId);
                try {
                    ResultSet res = ps.executeQuery();
                    if (res.next()) {
                        return res.getString("userName");
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

    @Override
    public boolean saveMessage(String groupName, Integer userId, String message) {
        String creatorName = getUserName(userId);
        try (Connection connection = DriverManager.getConnection(
                "jdbc:" + Configuration.getType() + "://"
                        + Configuration.getServer() + ":"
                        + Configuration.getPort() + "/"
                        + Configuration.getDatabase(), Configuration.getUser(),
                Configuration.getPassword())) {
            try (PreparedStatement ps = connection.prepareStatement(QueryConstants.GroupQueries.SAVE_CHAT_MESSAGE)) {
                ps.setString(1, groupName);
                ps.setString(2, creatorName);
                ps.setString(3, message);
                try {
                    int rows = ps.executeUpdate();
                    return rows > 0;
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
    public ArrayList<ChatData> showMessages(String groupName) {
        ArrayList<ChatData> res = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(
                "jdbc:" + Configuration.getType() + "://"
                        + Configuration.getServer() + ":"
                        + Configuration.getPort() + "/"
                        + Configuration.getDatabase(), Configuration.getUser(),
                Configuration.getPassword())) {
            try (PreparedStatement ps = connection.prepareStatement(QueryConstants.GroupQueries.GET_ALL_MESSAGES_BY_GROUP_NAME)) {
                ps.setString(1, groupName);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next())
                        res.add(new ChatData(rs.getString("creatorName"), rs.getTimestamp("creationTime"), rs.getString("message")));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * Deletes a group by its name
     * @param groupName group name to delete
     * @return if the group was successfully deleted
     */
    public boolean deleteGroup(String groupName) {
        try (Connection connection = DriverManager.getConnection(
                "jdbc:" + Configuration.getType() + "://"
                        + Configuration.getServer() + ":"
                        + Configuration.getPort() + "/"
                        + Configuration.getDatabase(), Configuration.getUser(),
                Configuration.getPassword())) {
            try (PreparedStatement psA = connection.prepareStatement(QueryConstants.GroupQueries.REMOVE_GROUP_BY_GROUP_NAME)) {
                psA.setString(1, groupName);
                try {
                    int rows = psA.executeUpdate();
                    if (rows > 0) {
                        try (PreparedStatement psB = connection.prepareStatement(QueryConstants.GroupQueries.REMOVE_ALL_GROUP_MEMBERS_BY_GROUP_NAME)) {
                            psB.setString(1, groupName);
                            psB.executeUpdate();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return true;
                    } else
                        return false;
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

    /**
     * Removes a user by it's userId from a group
     * @param groupName groupn ame to find
     * @param userId user to delete
     * @return if the user was successfully remove from the group
     */
    private boolean removeUser(String groupName, Integer userId) {
        try (Connection connection = DriverManager.getConnection(
                "jdbc:" + Configuration.getType() + "://"
                        + Configuration.getServer() + ":"
                        + Configuration.getPort() + "/"
                        + Configuration.getDatabase(), Configuration.getUser(),
                Configuration.getPassword())) {
            try (PreparedStatement psA = connection.prepareStatement(QueryConstants.GroupQueries.REMOVE_MEMBER_FROM_GROUP)) {
                psA.setString(1, groupName);
                psA.setInt(2, userId);
                try {
                    int rows = psA.executeUpdate();
                    return rows > 0;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public MyResult leaveGroup(String groupName, Integer userId) {
        try (Connection connection = DriverManager.getConnection(
                "jdbc:" + Configuration.getType() + "://"
                        + Configuration.getServer() + ":"
                        + Configuration.getPort() + "/"
                        + Configuration.getDatabase(), Configuration.getUser(),
                Configuration.getPassword())) {
            try (PreparedStatement ps = connection.prepareStatement(QueryConstants.GroupQueries.SELECT_ADMIN_ID_FROM_GROUP_NAME)) {
                ps.setString(1, groupName);
                try {
                    ResultSet res = ps.executeQuery();
                    if (res.next() && Integer.parseInt(res.getString("adminId")) == userId)
                        return new MyResult(deleteGroup(groupName), 1);
                    else
                        return new MyResult(removeUser(groupName, userId), 2);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new MyResult(false, 0);
    }

    /**
     * Cleans up all groups which have 1 member by removing them
     * @return 1 if groups was deleted, 0 if nothing deleted, -1 if an error has occurred
     */
    public int autoDeleteGroups() {
        System.out.println("[AUTO] Started 'autoDelete' job...");
        try (Connection connection = DriverManager.getConnection(
                "jdbc:" + Configuration.getType() + "://"
                        + Configuration.getServer() + ":"
                        + Configuration.getPort() + "/"
                        + Configuration.getDatabase(), Configuration.getUser(),
                Configuration.getPassword())) {
            try (PreparedStatement ps = connection.prepareStatement(QueryConstants.GroupQueries.GET_GROUP_NAMES_WITH_ONE_MEMBER)) {
                try (ResultSet rs = ps.executeQuery()) {
                    ArrayList<Boolean> allSuccess = new ArrayList<>();
                    while(rs.next()) {
                        String groupName = rs.getString(1);
                        Boolean success = deleteGroup(groupName);
                        if (success) {
                            System.out.println("[AUTO] Successfully deleted group " + groupName);
                        } else {
                            System.out.println("[AUTO] Failed to delete group " + groupName);
                        }
                        allSuccess.add(success);
                    }
                    if (allSuccess.size() == 0) {
                        System.out.println("[AUTO] No groups to delete\n[AUTO] Job 'autoDelete' completed with status 0");
                        return 0;
                    }
                    for (Boolean b : allSuccess) {
                        if (!b) {
                            System.out.println("[AUTO] Delete of some group failed!");
                            return -1;
                        }
                    }
                    System.out.println("[AUTO] Job 'autoDelete' completed with status 1");
                    return 1;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("[AUTO] Job 'autoDelete' failed with status -1!");
        return -1;
    }

}
