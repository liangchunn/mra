package GroupFacade;

import datatypes.ChatData;
import dbadapter.Configuration;
import dbadapter.GroupFacade;
import dbadapter.QueryConstants;
import extraClasses.MyResult;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

// TODO: Add comments for all tests

/**
 * Created by Cody on 19.01.2018 at 20:28.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(GroupFacade.class)
public class Chat {

    @Before
    public void setUp() throws Exception {
        PowerMockito.mockStatic(DriverManager.class);
    }

    @Test
    public final void testSaveMessage() {
        String sqlQuery = QueryConstants.GroupQueries.SAVE_CHAT_MESSAGE;
        String sqlQueryB = QueryConstants.GroupQueries.GET_USERNAME_BY_USER_ID;
        try {
            // Setting up return values for connection and statements
            Connection stubCon = mock(Connection.class);

            PreparedStatement ps = mock(PreparedStatement.class);
            ResultSet rs = mock(ResultSet.class);
            PreparedStatement psB = mock(PreparedStatement.class);
            ResultSet rsB = mock(ResultSet.class);

            PowerMockito.when(
                    DriverManager.getConnection(
                            "jdbc:" + Configuration.getType() + "://"
                                    + Configuration.getServer() + ":"
                                    + Configuration.getPort() + "/"
                                    + Configuration.getDatabase(),
                            Configuration.getUser(),
                            Configuration.getPassword())).thenReturn(stubCon);

            when(stubCon.prepareStatement(sqlQuery)).thenReturn(ps);
            when(ps.executeUpdate()).thenReturn(1);
            when(stubCon.prepareStatement(sqlQueryB)).thenReturn(psB);
            when(psB.executeQuery()).thenReturn(rsB);


            // Setting up return values for methods
            when(rs.next()).thenReturn(true).thenReturn(false);
            when(rs.getString(1)).thenReturn("Hulk");
            when(rs.getInt(2)).thenReturn(2);
            when(rs.getString(3)).thenReturn("Hello World!");

            when(rsB.next()).thenReturn(true).thenReturn(false);
            when(rsB.getString("userName")).thenReturn("Soliman");

            boolean check = GroupFacade.getInstance()
                    .saveMessage("Hulk", 2, "Hello World!");


            // Verify how often a method has been called
            verify(stubCon, times(1)).prepareStatement(sqlQuery);
            verify(ps, times(1)).executeUpdate();
            verify(stubCon, times(1)).prepareStatement(sqlQueryB);
            verify(psB, times(1)).executeQuery();

            // Verify return values
            assertTrue(check);
            // ...

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public final void testGetUsername() {
        String sqlQuery = QueryConstants.GroupQueries.GET_USERNAME_BY_USER_ID;
        try {
            // Setting up return values for connection and statements
            Connection stubCon = mock(Connection.class);
            PreparedStatement ps = mock(PreparedStatement.class);
            ResultSet rs = mock(ResultSet.class);
            PowerMockito.when(
                    DriverManager.getConnection(
                            "jdbc:" + Configuration.getType() + "://"
                                    + Configuration.getServer() + ":"
                                    + Configuration.getPort() + "/"
                                    + Configuration.getDatabase(),
                            Configuration.getUser(),
                            Configuration.getPassword())).thenReturn(stubCon);

            when(stubCon.prepareStatement(sqlQuery)).thenReturn(ps);
            when(ps.executeQuery()).thenReturn(rs);

            // Setting up return values for methods
            when(rs.next()).thenReturn(true).thenReturn(false);
            when(rs.getString("userName")).thenReturn("Soliman");

            // Verify how often a method has been called
            String checkUserName = GroupFacade.getInstance().getUserName(2);
            verify(stubCon, times(1)).prepareStatement(sqlQuery);
            verify(ps, times(1)).executeQuery();

            // Verify return values
            assertEquals("Soliman", checkUserName);
            // ...

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public final void testShowMessages() {
        String sqlQuery = QueryConstants.GroupQueries.GET_ALL_MESSAGES_BY_GROUP_NAME;
        Timestamp stubCreationTime;
        try {
            // Setting up return values for connection and statements
            Connection stubCon = mock(Connection.class);
            PreparedStatement ps = mock(PreparedStatement.class);
            ResultSet rs = mock(ResultSet.class);
            PowerMockito.when(
                    DriverManager.getConnection(
                            "jdbc:" + Configuration.getType() + "://"
                                    + Configuration.getServer() + ":"
                                    + Configuration.getPort() + "/"
                                    + Configuration.getDatabase(),
                            Configuration.getUser(),
                            Configuration.getPassword())).thenReturn(stubCon);

            when(stubCon.prepareStatement(sqlQuery)).thenReturn(ps);
            when(ps.executeQuery()).thenReturn(rs);

            DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
            Date date = dateFormat.parse("17/01/2018");
            long time = date.getTime();
            stubCreationTime = new Timestamp(time);


            // Setting up return values for methods
            when(rs.next()).thenReturn(true).thenReturn(false);
            when(rs.getString("creatorName")).thenReturn("Soliman");
            when(rs.getTimestamp("creationTime")).thenReturn(stubCreationTime);
            when(rs.getString("message")).thenReturn("Hallo Welt!! :D");

            ArrayList<ChatData> messages = GroupFacade.getInstance()
                    .showMessages("Hulk");


            // Verify how often a method has been called
            verify(stubCon, times(1)).prepareStatement(sqlQuery);
            verify(ps, times(1)).executeQuery();


            // Verify return values
            assertTrue(messages.size() == 1);
            assertTrue(Objects.equals(messages.get(0).getCreatorName(), "Soliman"));
            assertTrue(messages.get(0).getCreationTime() == stubCreationTime);
            assertTrue(Objects.equals(messages.get(0).getMessageText(), "Hallo Welt!! :D"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public final void testLeaveGroupAdmin() {
        String sqlQuery = QueryConstants.GroupQueries.SELECT_ADMIN_ID_FROM_GROUP_NAME;
        String REMOVE_GROUP = QueryConstants.GroupQueries.REMOVE_GROUP_BY_GROUP_NAME;
        String REMOVE_MEMBERS = QueryConstants.GroupQueries.REMOVE_ALL_GROUP_MEMBERS_BY_GROUP_NAME;
        try {
            Connection stubCon = mock(Connection.class);
            PreparedStatement ps = mock(PreparedStatement.class);
            PreparedStatement ps2 = mock(PreparedStatement.class);
            PreparedStatement ps3 = mock(PreparedStatement.class);
            ResultSet rs = mock(ResultSet.class);
            PowerMockito.when(
                    DriverManager.getConnection(
                            "jdbc:" + Configuration.getType() + "://"
                                    + Configuration.getServer() + ":"
                                    + Configuration.getPort() + "/"
                                    + Configuration.getDatabase(),
                            Configuration.getUser(),
                            Configuration.getPassword())).thenReturn(stubCon);

            when(stubCon.prepareStatement(sqlQuery)).thenReturn(ps);
            when(stubCon.prepareStatement(REMOVE_GROUP)).thenReturn(ps2);
            when(stubCon.prepareStatement(REMOVE_MEMBERS)).thenReturn(ps3);
            when(ps2.executeUpdate()).thenReturn(1);
            when(ps3.executeUpdate()).thenReturn(1);
            when(ps.executeQuery()).thenReturn(rs);

            // Setting up return values for methods
            when(rs.next()).thenReturn(true).thenReturn(false);
            when(rs.getString("adminId")).thenReturn("6");

            MyResult check = GroupFacade.getInstance()
                    .leaveGroup("Hulk", 6);

            // Verify how often a method has been called
            verify(stubCon, times(1)).prepareStatement(sqlQuery);
            verify(ps, times(1)).executeQuery();


            // Verify return values
            assertTrue(check.getFirst());
            assertTrue(check.getSecond() == 1);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public final void testLeaveGroupUser() {
        String GROUP_NAME = "Hulk";
        String sqlQuery = QueryConstants.GroupQueries.SELECT_ADMIN_ID_FROM_GROUP_NAME;
        String REMOVE_MEMBER = QueryConstants.GroupQueries.REMOVE_MEMBER_FROM_GROUP;
        try {
            Connection stubCon = mock(Connection.class);
            PreparedStatement ps = mock(PreparedStatement.class);
            PreparedStatement ps2 = mock(PreparedStatement.class);
            ResultSet rs = mock(ResultSet.class);
            PowerMockito.when(
                    DriverManager.getConnection(
                            "jdbc:" + Configuration.getType() + "://"
                                    + Configuration.getServer() + ":"
                                    + Configuration.getPort() + "/"
                                    + Configuration.getDatabase(),
                            Configuration.getUser(),
                            Configuration.getPassword())).thenReturn(stubCon);

            when(stubCon.prepareStatement(sqlQuery)).thenReturn(ps);
            when(stubCon.prepareStatement(REMOVE_MEMBER)).thenReturn(ps2);
            when(ps2.executeUpdate()).thenReturn(1);
            when(ps.executeQuery()).thenReturn(rs);

            // Setting up return values for methods
            when(rs.next()).thenReturn(true).thenReturn(false);
            when(rs.getString("adminId")).thenReturn("6");

            MyResult check = GroupFacade.getInstance()
                    .leaveGroup(GROUP_NAME, 7);

            // Verify how often a method has been called
            verify(stubCon, times(1)).prepareStatement(sqlQuery);
            verify(ps, times(1)).executeQuery();


            // Verify return values
            assertTrue(check.getFirst());
            assertTrue(check.getSecond() == 2);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}