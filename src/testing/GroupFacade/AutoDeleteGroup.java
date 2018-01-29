package GroupFacade;

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

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

// TODO: Add comments for all tests

/**
 * Created by liangchun on 21.01.18.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(GroupFacade.class)
public class AutoDeleteGroup {
    @Before
    public void setUp() throws Exception {
        PowerMockito.mockStatic(DriverManager.class);
    }

    @Test
    public final void autoDeleteNone() {
        String GET_GROUPS_WITH_ONE_MEMBER = QueryConstants.GroupQueries.GET_GROUP_NAMES_WITH_ONE_MEMBER;
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

            when(stubCon.prepareStatement(GET_GROUPS_WITH_ONE_MEMBER)).thenReturn(ps);
            when(ps.executeQuery()).thenReturn(rs);

            when(rs.next()).thenReturn(false);

            int check = GroupFacade.getInstance().autoDeleteGroups();

            verify(stubCon, times(1)).prepareStatement(GET_GROUPS_WITH_ONE_MEMBER);
            verify(ps, times(1)).executeQuery();
            verify(rs, times(1)).next();

            assertTrue(check == 0);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public final void autoDeleteOne() {
        String GET_GROUPS_WITH_ONE_MEMBER = QueryConstants.GroupQueries.GET_GROUP_NAMES_WITH_ONE_MEMBER;
        String REMOVE_GROUP = QueryConstants.GroupQueries.REMOVE_GROUP_BY_GROUP_NAME;
        String REMOVE_MEMBERS = QueryConstants.GroupQueries.REMOVE_ALL_GROUP_MEMBERS_BY_GROUP_NAME;
        String GROUP_NAME_1 = "SAMPLE_GROUP_1";
        try {
            // Setting up return values for connection and statements
            Connection stubCon = mock(Connection.class);

            PreparedStatement ps = mock(PreparedStatement.class);
            ResultSet rs = mock(ResultSet.class);

            PreparedStatement ps2 = mock(PreparedStatement.class);
            PreparedStatement ps3 = mock(PreparedStatement.class);

            PowerMockito.when(
                    DriverManager.getConnection(
                            "jdbc:" + Configuration.getType() + "://"
                                    + Configuration.getServer() + ":"
                                    + Configuration.getPort() + "/"
                                    + Configuration.getDatabase(),
                            Configuration.getUser(),
                            Configuration.getPassword())).thenReturn(stubCon);

            when(stubCon.prepareStatement(GET_GROUPS_WITH_ONE_MEMBER)).thenReturn(ps);
            when(ps.executeQuery()).thenReturn(rs);

            when(stubCon.prepareStatement(REMOVE_GROUP)).thenReturn(ps2);
            when(ps2.executeUpdate()).thenReturn(1);
            when(stubCon.prepareStatement(REMOVE_MEMBERS)).thenReturn(ps3);
            when(ps3.executeUpdate()).thenReturn(1);

            when(rs.next()).thenReturn(true).thenReturn(false);
            when(rs.getString(1)).thenReturn(GROUP_NAME_1);


            int check = GroupFacade.getInstance().autoDeleteGroups();

            verify(stubCon, times(1)).prepareStatement(GET_GROUPS_WITH_ONE_MEMBER);
            verify(ps, times(1)).executeQuery();
            verify(rs, times(2)).next();
            verify(stubCon, times(1)).prepareStatement(REMOVE_GROUP);
            verify(ps2, times(1)).executeUpdate();
            verify(stubCon, times(1)).prepareStatement(REMOVE_MEMBERS);
            verify(ps3, times(1)).executeUpdate();


            assertTrue(check == 1);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public final void autoDeleteTwo() {
        String GET_GROUPS_WITH_ONE_MEMBER = QueryConstants.GroupQueries.GET_GROUP_NAMES_WITH_ONE_MEMBER;
        String REMOVE_GROUP = QueryConstants.GroupQueries.REMOVE_GROUP_BY_GROUP_NAME;
        String REMOVE_MEMBERS = QueryConstants.GroupQueries.REMOVE_ALL_GROUP_MEMBERS_BY_GROUP_NAME;
        String GROUP_NAME_1 = "SAMPLE_GROUP_1";
        String GROUP_NAME_2 = "SAMPLE_GROUP_2";
        try {
            // Setting up return values for connection and statements
            Connection stubCon = mock(Connection.class);

            PreparedStatement ps = mock(PreparedStatement.class);
            ResultSet rs = mock(ResultSet.class);

            PreparedStatement ps2 = mock(PreparedStatement.class);
            PreparedStatement ps3 = mock(PreparedStatement.class);

            PowerMockito.when(
                    DriverManager.getConnection(
                            "jdbc:" + Configuration.getType() + "://"
                                    + Configuration.getServer() + ":"
                                    + Configuration.getPort() + "/"
                                    + Configuration.getDatabase(),
                            Configuration.getUser(),
                            Configuration.getPassword())).thenReturn(stubCon);

            when(stubCon.prepareStatement(GET_GROUPS_WITH_ONE_MEMBER)).thenReturn(ps);
            when(ps.executeQuery()).thenReturn(rs);

            when(stubCon.prepareStatement(REMOVE_GROUP)).thenReturn(ps2);
            when(ps2.executeUpdate()).thenReturn(1);
            when(stubCon.prepareStatement(REMOVE_MEMBERS)).thenReturn(ps3);
            when(ps3.executeUpdate()).thenReturn(1);

            when(rs.next()).thenReturn(true).thenReturn(true).thenReturn(false);
            when(rs.getString(1)).thenReturn(GROUP_NAME_1).thenReturn(GROUP_NAME_2);

            int check = GroupFacade.getInstance().autoDeleteGroups();

            verify(stubCon, times(1)).prepareStatement(GET_GROUPS_WITH_ONE_MEMBER);
            verify(ps, times(1)).executeQuery();
            verify(rs, times(3)).next();
            verify(stubCon, times(2)).prepareStatement(REMOVE_GROUP);
            verify(ps2, times(2)).executeUpdate();
            verify(stubCon, times(2)).prepareStatement(REMOVE_MEMBERS);
            verify(ps3, times(2)).executeUpdate();


            assertTrue(check == 1);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public final void autoDeleteOneFail() {
        String GET_GROUPS_WITH_ONE_MEMBER = QueryConstants.GroupQueries.GET_GROUP_NAMES_WITH_ONE_MEMBER;
        String REMOVE_GROUP = QueryConstants.GroupQueries.REMOVE_GROUP_BY_GROUP_NAME;
        String GROUP_NAME_1 = "SAMPLE_GROUP_1";
        try {
            // Setting up return values for connection and statements
            Connection stubCon = mock(Connection.class);

            PreparedStatement ps = mock(PreparedStatement.class);
            ResultSet rs = mock(ResultSet.class);

            PreparedStatement ps2 = mock(PreparedStatement.class);

            PowerMockito.when(
                    DriverManager.getConnection(
                            "jdbc:" + Configuration.getType() + "://"
                                    + Configuration.getServer() + ":"
                                    + Configuration.getPort() + "/"
                                    + Configuration.getDatabase(),
                            Configuration.getUser(),
                            Configuration.getPassword())).thenReturn(stubCon);

            when(stubCon.prepareStatement(GET_GROUPS_WITH_ONE_MEMBER)).thenReturn(ps);
            when(ps.executeQuery()).thenReturn(rs);

            when(stubCon.prepareStatement(REMOVE_GROUP)).thenReturn(ps2);
            when(ps2.executeUpdate()).thenReturn(0);


            when(rs.next()).thenReturn(true).thenReturn(false);
            when(rs.getString(1)).thenReturn(GROUP_NAME_1);


            int check = GroupFacade.getInstance().autoDeleteGroups();

            verify(stubCon, times(1)).prepareStatement(GET_GROUPS_WITH_ONE_MEMBER);
            verify(ps, times(1)).executeQuery();
            verify(rs, times(2)).next();
            verify(stubCon, times(1)).prepareStatement(REMOVE_GROUP);
            verify(ps2, times(1)).executeUpdate();

            assertTrue(check == -1);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
