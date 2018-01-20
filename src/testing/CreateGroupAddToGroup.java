package testing;

import dbadapter.Configuration;
import dbadapter.GroupFacade;
import dbadapter.QueryConstants;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by liangchun on 20.01.18.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(GroupFacade.class)
public class CreateGroupAddToGroup {

    @Before
    public void setUp() throws Exception {
        PowerMockito.mockStatic(DriverManager.class);
    }

    @Test
    public final void getUserIdValid() {
        final String SAMPLE_USER_NAME = "SAMPLE_USERNAME";
        final String SQL_SELECT_USER_ID = QueryConstants.GroupQueries.GET_USER_ID;
        try {
            Connection stubConnection = mock(Connection.class);
            PreparedStatement ps = mock(PreparedStatement.class);
            ResultSet rs = mock(ResultSet.class);
            PowerMockito.when(
                    DriverManager.getConnection(
                            "jdbc:" + Configuration.getType() + "://"
                                    + Configuration.getServer() + ":"
                                    + Configuration.getPort() + "/"
                                    + Configuration.getDatabase(),
                            Configuration.getUser(),
                            Configuration.getPassword())).thenReturn(stubConnection);
            when(stubConnection.prepareStatement(SQL_SELECT_USER_ID)).thenReturn(ps);
            when(ps.executeQuery()).thenReturn(rs);

            when(rs.first()).thenReturn(true);
            when(rs.getInt(1)).thenReturn(2);

            assertTrue(GroupFacade.getInstance().getUserId(SAMPLE_USER_NAME) != null);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public final void getUserIdInvalid() {
        final String SAMPLE_USER_NAME = "SAMPLE_USERNAME";
        final String SQL_SELECT_USER_ID = QueryConstants.GroupQueries.GET_USER_ID;
        try {
            Connection stubConnection = mock(Connection.class);
            PreparedStatement ps = mock(PreparedStatement.class);
            ResultSet rs = mock(ResultSet.class);
            PowerMockito.when(
                    DriverManager.getConnection(
                            "jdbc:" + Configuration.getType() + "://"
                                    + Configuration.getServer() + ":"
                                    + Configuration.getPort() + "/"
                                    + Configuration.getDatabase(),
                            Configuration.getUser(),
                            Configuration.getPassword())).thenReturn(stubConnection);
            when(stubConnection.prepareStatement(SQL_SELECT_USER_ID)).thenReturn(ps);
            when(ps.executeQuery()).thenReturn(rs);

            when(rs.first()).thenReturn(false);

            assertTrue(GroupFacade.getInstance().getUserId(SAMPLE_USER_NAME) == null);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public final void checkGroupNameExists() {
        final String SAMPLE_GROUP_NAME = "SAMPLE_GROUP";
        final String SAMPLE_SELECT_GROUP = QueryConstants.GroupQueries.CHECK_GROUP_EXISTENCE;
        try {
            Connection stubConnection = mock(Connection.class);
            PreparedStatement ps = mock(PreparedStatement.class);
            ResultSet rs = mock(ResultSet.class);
            PowerMockito.when(
                    DriverManager.getConnection(
                            "jdbc:" + Configuration.getType() + "://"
                                    + Configuration.getServer() + ":"
                                    + Configuration.getPort() + "/"
                                    + Configuration.getDatabase(),
                            Configuration.getUser(),
                            Configuration.getPassword())).thenReturn(stubConnection);
            when(stubConnection.prepareStatement(SAMPLE_SELECT_GROUP)).thenReturn(ps);
            when(ps.executeQuery()).thenReturn(rs);

            when(rs.first()).thenReturn(true);
            when(rs.getInt(1)).thenReturn(1);

            assertTrue(GroupFacade.getInstance().checkIfGroupNameExists(SAMPLE_GROUP_NAME) == true);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public final void checkGroupNameNotExists() {
        final String SAMPLE_GROUP_NAME = "SAMPLE_GROUP";
        final String SAMPLE_SELECT_GROUP = QueryConstants.GroupQueries.CHECK_GROUP_EXISTENCE;
        try {
            Connection stubConnection = mock(Connection.class);
            PreparedStatement ps = mock(PreparedStatement.class);
            ResultSet rs = mock(ResultSet.class);
            PowerMockito.when(
                    DriverManager.getConnection(
                            "jdbc:" + Configuration.getType() + "://"
                                    + Configuration.getServer() + ":"
                                    + Configuration.getPort() + "/"
                                    + Configuration.getDatabase(),
                            Configuration.getUser(),
                            Configuration.getPassword())).thenReturn(stubConnection);
            when(stubConnection.prepareStatement(SAMPLE_SELECT_GROUP)).thenReturn(ps);
            when(ps.executeQuery()).thenReturn(rs);

            when(rs.first()).thenReturn(true);
            when(rs.getInt(1)).thenReturn(0);

            assertTrue(GroupFacade.getInstance().checkIfGroupNameExists(SAMPLE_GROUP_NAME) == false);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public final void addUserToGroupValid() {
        final String SAMPLE_GROUP_NAME = "SAMPLE_GROUP";
        final String SAMPLE_USER_NAME = "SAMPLE_USERNAME";
        final String SAMPLE_SELECT_GROUP = QueryConstants.GroupQueries.CHECK_GROUP_EXISTENCE;
        final String SAMPLE_INSERT_USER_INTO_GROUP = QueryConstants.GroupQueries.ADD_TO_GROUP;
        final String SQL_SELECT_USER_ID = QueryConstants.GroupQueries.GET_USER_ID;
        try {
            Connection stubConnection = mock(Connection.class);
            PreparedStatement ps = mock(PreparedStatement.class);
            ResultSet rs = mock(ResultSet.class);
            PreparedStatement ps2 = mock(PreparedStatement.class);
            PreparedStatement ps3 = mock(PreparedStatement.class);
            ResultSet rs3 = mock(ResultSet.class);

            PowerMockito.when(
                    DriverManager.getConnection(
                            "jdbc:" + Configuration.getType() + "://"
                                    + Configuration.getServer() + ":"
                                    + Configuration.getPort() + "/"
                                    + Configuration.getDatabase(),
                            Configuration.getUser(),
                            Configuration.getPassword())).thenReturn(stubConnection);

            when(stubConnection.prepareStatement(SAMPLE_SELECT_GROUP)).thenReturn(ps);
            when(ps.executeQuery()).thenReturn(rs);
            when(stubConnection.prepareStatement(SAMPLE_INSERT_USER_INTO_GROUP)).thenReturn(ps2);
            when(ps2.executeUpdate()).thenReturn(1);
            when(stubConnection.prepareStatement(SQL_SELECT_USER_ID)).thenReturn(ps3);
            when(ps3.executeQuery()).thenReturn(rs3);

            when(rs.first()).thenReturn(true);
            when(rs.getInt(1)).thenReturn(1);

            when(rs3.first()).thenReturn(true);
            when(rs3.getInt(1)).thenReturn(2);

            assertTrue(GroupFacade.getInstance().addUserToGroup(SAMPLE_GROUP_NAME, SAMPLE_USER_NAME) == true);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public final void addUserToGroupInvalid() {
        final String SAMPLE_GROUP_NAME = "SAMPLE_GROUP";
        final String SAMPLE_USER_NAME = "SAMPLE_USERNAME";
        final String SAMPLE_SELECT_GROUP = QueryConstants.GroupQueries.CHECK_GROUP_EXISTENCE;
        final String SAMPLE_INSERT_USER_INTO_GROUP = QueryConstants.GroupQueries.ADD_TO_GROUP;
        final String SQL_SELECT_USER_ID = QueryConstants.GroupQueries.GET_USER_ID;
        try {
            Connection stubConnection = mock(Connection.class);
            PreparedStatement ps = mock(PreparedStatement.class);
            ResultSet rs = mock(ResultSet.class);
            PreparedStatement ps2 = mock(PreparedStatement.class);
            PreparedStatement ps3 = mock(PreparedStatement.class);
            ResultSet rs3 = mock(ResultSet.class);

            PowerMockito.when(
                    DriverManager.getConnection(
                            "jdbc:" + Configuration.getType() + "://"
                                    + Configuration.getServer() + ":"
                                    + Configuration.getPort() + "/"
                                    + Configuration.getDatabase(),
                            Configuration.getUser(),
                            Configuration.getPassword())).thenReturn(stubConnection);

            when(stubConnection.prepareStatement(SAMPLE_SELECT_GROUP)).thenReturn(ps);
            when(ps.executeQuery()).thenReturn(rs);
            when(stubConnection.prepareStatement(SAMPLE_INSERT_USER_INTO_GROUP)).thenReturn(ps2);
            when(ps2.executeUpdate()).thenReturn(0);
            when(stubConnection.prepareStatement(SQL_SELECT_USER_ID)).thenReturn(ps3);
            when(ps3.executeQuery()).thenReturn(rs3);

            when(rs.first()).thenReturn(true);
            when(rs.getInt(1)).thenReturn(1);

            when(rs3.first()).thenReturn(true);
            when(rs3.getInt(1)).thenReturn(2);

            assertTrue(GroupFacade.getInstance().addUserToGroup(SAMPLE_GROUP_NAME, SAMPLE_USER_NAME) == false);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public final void createGroupWithValidDetails() {
        final String SAMPLE_GROUP_NAME = "SAMPLE_GROUP";
        final String SAMPLE_ADMIN_NAME = "SAMPLE_ADMIN";
        final ArrayList<String> SAMPLE_MEMBER_NAMES = new ArrayList<String>() {{
            add("SAMPLE_MEMBER_1");
            add("SAMPLE_MEMBER_2");
            add("SAMPLE_MEMBER_3");
        }};

        String SQL_CREATE_GROUP = QueryConstants.GroupQueries.CREATE_GROUP;
        String SQL_SELECT_ADMIN_ID = QueryConstants.GroupQueries.GET_USER_ID;
        String SQL_ADD_USER_TO_GROUP = QueryConstants.GroupQueries.ADD_TO_GROUP;
        String SQL_CHECK_GROUP_NAME_EXISTS = QueryConstants.GroupQueries.CHECK_GROUP_EXISTENCE;

        try {
            Connection stubConnection = mock(Connection.class);
            // Adding into GroupDatabase stub
            PreparedStatement ps = mock(PreparedStatement.class);
            ResultSet rs = mock(ResultSet.class);
            // Selecting from adminId stub
            PreparedStatement ps2 = mock(PreparedStatement.class);
            ResultSet rs2 = mock(ResultSet.class);
            // Adding user into DB stud
            PreparedStatement ps3 = mock(PreparedStatement.class);
            ResultSet rs3 = mock(ResultSet.class);
            // Check if group exists
            PreparedStatement ps4 = mock(PreparedStatement.class);
            ResultSet rs4 = mock(ResultSet.class);

            PowerMockito.when(
                    DriverManager.getConnection(
                            "jdbc:" + Configuration.getType() + "://"
                                    + Configuration.getServer() + ":"
                                    + Configuration.getPort() + "/"
                                    + Configuration.getDatabase(),
                            Configuration.getUser(),
                            Configuration.getPassword())).thenReturn(stubConnection);

            when(stubConnection.prepareStatement(SQL_CREATE_GROUP)).thenReturn(ps);
            when(ps.executeUpdate()).thenReturn(1);
            when(stubConnection.prepareStatement(SQL_SELECT_ADMIN_ID)).thenReturn(ps2);
            when(ps2.executeQuery()).thenReturn(rs2);
            when(stubConnection.prepareStatement(SQL_ADD_USER_TO_GROUP)).thenReturn(ps3);
            when(ps3.executeUpdate()).thenReturn(1);
            when(stubConnection.prepareStatement(SQL_CHECK_GROUP_NAME_EXISTS)).thenReturn(ps4);
            when(ps4.executeQuery()).thenReturn(rs4);

            when(rs.next()).thenReturn(true).thenReturn(false);
            when(rs.getString(1)).thenReturn(SAMPLE_GROUP_NAME);
            when(rs.getInt(2)).thenReturn(2);

            when(rs2.first()).thenReturn(true);
            when(rs2.getInt(1)).thenReturn(2);

            when(rs3.next()).thenReturn(true).thenReturn(false);
            when(rs3.getString(1)).thenReturn(SAMPLE_GROUP_NAME);
            when(rs3.getInt(2)).thenReturn(2);

            when(rs4.first()).thenReturn(true);
            when(rs4.getInt(1)).thenReturn(0);

            boolean check = GroupFacade.getInstance().createGroup(SAMPLE_GROUP_NAME, SAMPLE_ADMIN_NAME, SAMPLE_MEMBER_NAMES);

            assertTrue(check == true);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public final void createGroupWithInvalidDetails() {
        final String SAMPLE_GROUP_NAME = "SAMPLE_GROUP";
        final String SAMPLE_ADMIN_NAME = "SAMPLE_ADMIN";
        final ArrayList<String> SAMPLE_MEMBER_NAMES = new ArrayList<String>() {{
            add("SAMPLE_MEMBER_1");
            add("SAMPLE_MEMBER_2");
            add("SAMPLE_MEMBER_3");
        }};

        String SQL_CREATE_GROUP = QueryConstants.GroupQueries.CREATE_GROUP;
        String SQL_SELECT_ADMIN_ID = QueryConstants.GroupQueries.GET_USER_ID;
        String SQL_ADD_USER_TO_GROUP = QueryConstants.GroupQueries.ADD_TO_GROUP;
        String SQL_CHECK_GROUP_NAME_EXISTS = QueryConstants.GroupQueries.CHECK_GROUP_EXISTENCE;

        try {
            Connection stubConnection = mock(Connection.class);
            // Adding into GroupDatabase stub
            PreparedStatement ps = mock(PreparedStatement.class);
            ResultSet rs = mock(ResultSet.class);
            // Selecting from adminId stub
            PreparedStatement ps2 = mock(PreparedStatement.class);
            ResultSet rs2 = mock(ResultSet.class);
            // Adding user into DB stud
            PreparedStatement ps3 = mock(PreparedStatement.class);
            ResultSet rs3 = mock(ResultSet.class);
            // Check if group exists
            PreparedStatement ps4 = mock(PreparedStatement.class);
            ResultSet rs4 = mock(ResultSet.class);

            PowerMockito.when(
                    DriverManager.getConnection(
                            "jdbc:" + Configuration.getType() + "://"
                                    + Configuration.getServer() + ":"
                                    + Configuration.getPort() + "/"
                                    + Configuration.getDatabase(),
                            Configuration.getUser(),
                            Configuration.getPassword())).thenReturn(stubConnection);

            when(stubConnection.prepareStatement(SQL_CREATE_GROUP)).thenReturn(ps);
            when(ps.executeUpdate()).thenReturn(0);
            when(stubConnection.prepareStatement(SQL_SELECT_ADMIN_ID)).thenReturn(ps2);
            when(ps2.executeQuery()).thenReturn(rs2);
            when(stubConnection.prepareStatement(SQL_ADD_USER_TO_GROUP)).thenReturn(ps3);
            when(ps3.executeUpdate()).thenReturn(1);
            when(stubConnection.prepareStatement(SQL_CHECK_GROUP_NAME_EXISTS)).thenReturn(ps4);
            when(ps4.executeQuery()).thenReturn(rs4);

            when(rs.next()).thenReturn(true).thenReturn(false);
            when(rs.getString(1)).thenReturn(SAMPLE_GROUP_NAME);
            when(rs.getInt(2)).thenReturn(2);

            when(rs2.first()).thenReturn(true);
            when(rs2.getInt(1)).thenReturn(2);

            when(rs3.next()).thenReturn(true).thenReturn(false);
            when(rs3.getString(1)).thenReturn(SAMPLE_GROUP_NAME);
            when(rs3.getInt(2)).thenReturn(2);

            when(rs4.first()).thenReturn(true);
            when(rs4.getInt(1)).thenReturn(0);

            boolean check = GroupFacade.getInstance().createGroup(SAMPLE_GROUP_NAME, SAMPLE_ADMIN_NAME, SAMPLE_MEMBER_NAMES);

            assertTrue(check == false);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
