package dbadapter;

/**
 * Created by liangchun on 20.01.18.
 */

/**
 * Class where query constants are defined.
 * This is used to share consistent MySQL queries with the test files as well.
 */
public final class QueryConstants {
    private QueryConstants() {}
    public static final class GroupQueries {
        public static final String CREATE_GROUP = "INSERT INTO GroupDatabase (groupName, adminId) VALUES (?, ?)";
        public static final String ADD_TO_GROUP = "INSERT INTO GroupMembers(groupName, memberId) VALUES (?, ?)";
        public static final String CHECK_GROUP_EXISTENCE = "SELECT count(*) FROM GroupDatabase WHERE groupName=?;";
        public static final String GET_USER_ID_BY_USERNAME = "SELECT userId FROM Users WHERE userName=?";
        public static final String SELECT_ADMIN_ID_FROM_GROUP_NAME = "SELECT adminId FROM GroupDatabase WHERE groupName = ?;";
        public static final String REMOVE_MEMBER_FROM_GROUP = "DELETE FROM GroupMembers WHERE groupName = ? AND memberId = ?;";
        public static final String REMOVE_GROUP_BY_GROUP_NAME = "DELETE FROM GroupDatabase WHERE groupName = ?;";
        public static final String REMOVE_ALL_GROUP_MEMBERS_BY_GROUP_NAME = "DELETE FROM GroupMembers WHERE groupName = ?;";
        public static final String GET_ALL_MESSAGES_BY_GROUP_NAME = "SELECT message,creatorName, creationTime FROM ChatDatabase WHERE groupName = ?;";
        public static final String SAVE_CHAT_MESSAGE = "INSERT INTO ChatDatabase (groupName, creatorName, message) VALUES (?, ?, ?);";
        public static final String GET_USERNAME_BY_USER_ID = "SELECT userName FROM users WHERE userId = ?;";
        public static final String CHECK_CHAT_ELIGIBILITY = "SELECT * FROM GroupMembers WHERE groupName = ? AND memberId = ?;";
        public static final String CHECK_CHAT_ELIGIBILITY_ADMIN = "SELECT * FROM GroupDatabase WHERE groupName = ? AND adminId = ?;";
        public static final String GET_GROUP_NAMES_WITH_ONE_MEMBER = "SELECT groupName FROM (SELECT groupName, COUNT(*) as count FROM GroupMembers GROUP BY groupName) as CI WHERE CI.count = 1";
        private GroupQueries() {
        }
    }
}