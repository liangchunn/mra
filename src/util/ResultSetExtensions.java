package util;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

/**
 * Created by liangchun on 18.01.18.
 */
public class ResultSetExtensions {
    /**
     * Print a result set to system out.
     *
     * @param  rs           The ResultSet to print
     * @throws SQLException If there is a problem reading the ResultSet
     */
    final public static void print(ResultSet rs) throws SQLException
    {
        ResultSetMetaData rsmd = rs.getMetaData();
        int columnsNumber = rsmd.getColumnCount();
        while (rs.next()) {
            for (int i = 1; i <= columnsNumber; i++) {
                if (i > 1) System.out.print(" | ");
                System.out.print(rs.getString(i));
            }
            System.out.println("");
        }
    }
}
