package testing;

import datatypes.ChatData;
import org.junit.*;

import java.sql.Timestamp;

public class sendingMessageTest {

    ChatData message;

    @Before
    public void setUp() throws Exception {
        message = new ChatData("Hulk 2", "me", new Timestamp(System.currentTimeMillis()), "Testing");
    }

    @Test
    public final void testOverlap() {

    }

}
