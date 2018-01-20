package dbadapter;

import datatypes.ChatData;
import interfaces.*;
import extraClasses.*;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by liangchun on 14.01.18.
 */
public class GroupFacade implements ICheckIfGroupNameExists, IAddUserToGroup, ICreateGroup, IChatLogin, IReceiveMessages, ISendMessages, ILeaveGroup {
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
     *
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
            String sqlQuery = "UPDATE GroupMembers SET groupName= ? AND memberId=?";
            try (Connection connection = DriverManager.getConnection(
                    "jdbc:" + Configuration.getType() + "://"
                            + Configuration.getServer() + ":"
                            + Configuration.getPort() + "/"
                            + Configuration.getDatabase(), Configuration.getUser(),
                    Configuration.getPassword())) {
                try (PreparedStatement ps = connection.prepareStatement(sqlQuery)) {
                    ps.setInt(2, userId);
                    ps.setString(1, groupName);
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
            String sqlQuery = "INSERT INTO GroupDatabase (groupName, adminId) VALUES (?, ?);";
            try (Connection connection = DriverManager.getConnection(
                    "jdbc:" + Configuration.getType() + "://"
                            + Configuration.getServer() + ":"
                            + Configuration.getPort() + "/"
                            + Configuration.getDatabase(), Configuration.getUser(),
                    Configuration.getPassword())) {
                try (PreparedStatement ps = connection.prepareStatement(sqlQuery)) {
                    ps.setString(1, groupName);
                    ps.setInt(2, adminId);
                    try {
                        int rows = ps.executeUpdate();
                        sqlQuery = "INSERT INTO GroupMembers (groupName, memberId) VALUES (?, ?);";
                        try (PreparedStatement ps1 = connection.prepareStatement(sqlQuery)) {
                            for (Integer memberId :
                                    memberIds) {
                                ps1.setString(1, groupName);
                                ps1.setInt(2, memberId);
                                try {
                                    rows = ps1.executeUpdate();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                            if (rows > 0) {
                                return true;
                            } else {
                                return false;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
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

    @Override
    public boolean chatLogin(String groupName, Integer userId) {
        String sqlQuery = "SELECT * FROM GroupMembers WHERE groupName = ? AND memberId = ?;";
        String sqlQueryB = "SELECT * FROM GroupDatabase WHERE groupName = ? AND adminId = ?;";
        try (Connection connection = DriverManager.getConnection(
                "jdbc:" + Configuration.getType() + "://"
                        + Configuration.getServer() + ":"
                        + Configuration.getPort() + "/"
                        + Configuration.getDatabase(), Configuration.getUser(),
                Configuration.getPassword())) {
            try (PreparedStatement ps = connection.prepareStatement(sqlQuery)) {
                ps.setString(1, groupName);
                ps.setInt(2, userId);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next())
                        return true;
                    else {
                        try (PreparedStatement psB = connection.prepareStatement(sqlQueryB)) {
                            psB.setString(1, groupName);
                            psB.setInt(2, userId);
                            try (ResultSet rsB = psB.executeQuery()) {
                                if (rsB.next())
                                    return true;
                                else
                                    return false;
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

    public String getUserName(Integer userId) {
        String sqlQuery = "SELECT userName FROM users WHERE userId = ?;";
        try (Connection connection = DriverManager.getConnection(
                "jdbc:" + Configuration.getType() + "://"
                        + Configuration.getServer() + ":"
                        + Configuration.getPort() + "/"
                        + Configuration.getDatabase(), Configuration.getUser(),
                Configuration.getPassword())) {
            try (PreparedStatement ps = connection.prepareStatement(sqlQuery)) {
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
        String sqlQuery = "INSERT INTO ChatDatabase (groupName, creatorName, message) VALUES (?, ?, ?);";
        try (Connection connection = DriverManager.getConnection(
                "jdbc:" + Configuration.getType() + "://"
                        + Configuration.getServer() + ":"
                        + Configuration.getPort() + "/"
                        + Configuration.getDatabase(), Configuration.getUser(),
                Configuration.getPassword())) {
            try (PreparedStatement ps = connection.prepareStatement(sqlQuery)) {
                ps.setString(1, groupName);
                ps.setString(2, creatorName);
                ps.setString(3, message);
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
        return false;
    }

    @Override
    public ArrayList<ChatData> showMessages(String groupName) {
        String sqlQuery = "SELECT message,creatorName, creationTime FROM ChatDatabase WHERE groupName = ?;";
        ArrayList<ChatData> res = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(
                "jdbc:" + Configuration.getType() + "://"
                        + Configuration.getServer() + ":"
                        + Configuration.getPort() + "/"
                        + Configuration.getDatabase(), Configuration.getUser(),
                Configuration.getPassword())) {
            try (PreparedStatement ps = connection.prepareStatement(sqlQuery)) {
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

    private boolean deleteGroup(String groupName) {
        String sqlQueryA = "DELETE FROM groupdatabase WHERE groupName = ?;";
        String sqlQueryB = "DELETE FROM groupmembers WHERE groupName = ?;";
        try (Connection connection = DriverManager.getConnection(
                "jdbc:" + Configuration.getType() + "://"
                        + Configuration.getServer() + ":"
                        + Configuration.getPort() + "/"
                        + Configuration.getDatabase(), Configuration.getUser(),
                Configuration.getPassword())) {
            try (PreparedStatement psA = connection.prepareStatement(sqlQueryA)) {
                psA.setString(1, groupName);
                try {
                    int rows = psA.executeUpdate();
                    if (rows > 0) {
                        try (PreparedStatement psB = connection.prepareStatement(sqlQueryB)) {
                            psB.setString(1, groupName);
                            rows = psB.executeUpdate();
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

    private boolean removeUser(String groupName, Integer userId) {
        String sqlQueryA = "DELETE FROM groupmembers WHERE groupName = ? AND memberId = ?;";
        try (Connection connection = DriverManager.getConnection(
                "jdbc:" + Configuration.getType() + "://"
                        + Configuration.getServer() + ":"
                        + Configuration.getPort() + "/"
                        + Configuration.getDatabase(), Configuration.getUser(),
                Configuration.getPassword())) {
            try (PreparedStatement psA = connection.prepareStatement(sqlQueryA)) {
                psA.setString(1, groupName);
                psA.setInt(2, userId);
                try {
                    int rows = psA.executeUpdate();
                    if (rows > 0)
                        return true;
                    else
                        return false;
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
        String sqlQuery = "SELECT adminId FROM groupdatabase WHERE groupName = ?;";
        try (Connection connection = DriverManager.getConnection(
                "jdbc:" + Configuration.getType() + "://"
                        + Configuration.getServer() + ":"
                        + Configuration.getPort() + "/"
                        + Configuration.getDatabase(), Configuration.getUser(),
                Configuration.getPassword())) {
            try (PreparedStatement ps = connection.prepareStatement(sqlQuery)) {
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
}
