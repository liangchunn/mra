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

    /**
     * Checks if a group already existing in the database
     * @param groupName
     * @return
     */
    @Override
    public boolean checkIfGroupNameExists(String groupName) {
        String sqlCount = "SELECT count(*) FROM GroupDatabase WHERE groupName=?;";
        try (Connection connection = DriverManager.getConnection(
                "jdbc:" + Configuration.getType() + "://"
                        + Configuration.getServer() + ":"
                        + Configuration.getPort() + "/"
                        + Configuration.getDatabase(), Configuration.getUser(),
                Configuration.getPassword())) {
            try (PreparedStatement ps = connection.prepareStatement(sqlCount)) {
                ps.setString(1, groupName);
                try (ResultSet rs = ps.executeQuery()) {
                    rs.first();
                    Integer groupCount = rs.getInt(1);
                    if (groupCount > 0) {
                        System.out.println("groupName " + groupName + " exists");
                        return true;
                    } else {
                        System.out.println("groupName " + groupName + " does not exist");
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

    private String getCurrentGroupMembers(String groupName) {
        Boolean exists = checkIfGroupNameExists(groupName);
        if (exists) {
            String sqlQuery = "SELECT memberIds FROM GroupDatabase WHERE groupName=?";
            try (Connection connection = DriverManager.getConnection(
                    "jdbc:" + Configuration.getType() + "://"
                            + Configuration.getServer() + ":"
                            + Configuration.getPort() + "/"
                            + Configuration.getDatabase(), Configuration.getUser(),
                    Configuration.getPassword())) {
                try (PreparedStatement ps = connection.prepareStatement(sqlQuery)) {
                    ps.setString(1, groupName);
                    try (ResultSet rs = ps.executeQuery()) {
                        rs.first();
                        return rs.getString(1);
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
        return null;
    }

    @Override
    public boolean addUserToGroup(String groupName, Integer userId) {
        Boolean exists = checkIfGroupNameExists(groupName);
        if (exists) {
            String sqlQuery = "UPDATE GroupDatabase SET memberIds=? WHERE groupName=?";
            try (Connection connection = DriverManager.getConnection(
                    "jdbc:" + Configuration.getType() + "://"
                            + Configuration.getServer() + ":"
                            + Configuration.getPort() + "/"
                            + Configuration.getDatabase(), Configuration.getUser(),
                    Configuration.getPassword())) {
                String prevMemberIds = getCurrentGroupMembers(groupName);
                Integer prevMemberIdLength = (prevMemberIds != null) ? prevMemberIds.length() : 0;
                try (PreparedStatement ps = connection.prepareStatement(sqlQuery)) {
                    if (prevMemberIds != null || prevMemberIdLength > 0) {
                        ps.setString(1, prevMemberIds + "," + Integer.toString(userId));
                    } else {
                        ps.setString(1, Integer.toString(userId));
                    }
                    ps.setString(2, groupName);
                    try {
                        int row = ps.executeUpdate();
                        if (row > 0) return true;
                        else return false;
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

    private String convertToString(ArrayList<Integer> input) {
        String res = "";
        for (Integer i = 0; i < input.size(); i++) {
            res += input.get(i).toString();
            if (i < input.size() - 1) {
                res += ", ";
            }
        }
        return res;
    }

    @Override
    public boolean createGroup(String groupName, Integer adminId, ArrayList<Integer> memberIds) {
        Boolean exists = checkIfGroupNameExists(groupName);
        if (!exists) {
            String sqlQuery = "INSERT INTO GroupDatabase (groupName, adminId, memberIds) VALUES (?, ?, ?);";
            try (Connection connection = DriverManager.getConnection(
                    "jdbc:" + Configuration.getType() + "://"
                            + Configuration.getServer() + ":"
                            + Configuration.getPort() + "/"
                            + Configuration.getDatabase(), Configuration.getUser(),
                    Configuration.getPassword())) {
                try (PreparedStatement ps = connection.prepareStatement(sqlQuery)) {
                    ps.setString(1, groupName);
                    ps.setInt(2, adminId);
                    ps.setString(3, convertToString(memberIds));
                    try {
                        int rows = ps.executeUpdate();
                        if (rows > 0) {
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
}
