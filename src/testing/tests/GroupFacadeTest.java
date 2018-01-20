package tests;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import datatypes.ChatData;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import dbadapter.Configuration;
import dbadapter.GroupFacade;


/**
 * Created by Cody on 19.01.2018 at 20:28.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(GroupFacade.class)
public class GroupFacadeTest {

    @Before
    public void setUp() throws Exception {
        PowerMockito.mockStatic(DriverManager.class);
    }

    @Test
    public final void testSaveMessage() {
        String sqlQuery = "INSERT INTO ChatDatabase (groupName, creatorName, message) VALUES (?, ?, ?);";
        String sqlQueryB = "SELECT userName FROM users WHERE userId = ?;";
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

//            String checkUserName = GroupFacade.getInstance().getUserName(1);
//            verify(stubCon, times(2)).prepareStatement(sqlQueryB);
//            verify(psB, times(2)).executeQuery();

            // Verify return values
            assertTrue(check == true);
//            System.out.println(checkUserName);
//            assertEquals("Soliman", checkUserName);
            // ...

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public final void testShowMessages() {
        String sqlQuery = "SELECT message,creatorName, creationTime FROM ChatDatabase WHERE groupName = ?;";
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
            assertTrue(messages.get(0).getCreatorName() == "Soliman");
            assertTrue(messages.get(0).getCreationTime() == stubCreationTime);
            assertTrue(messages.get(0).getMessageText() == "Hallo Welt!! :D");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}