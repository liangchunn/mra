package FTL;

import net.sourceforge.jwebunit.junit.WebTester;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.Random;

// TODO: Add comments for all tests

/**
 * Used to test all of the interactive parts of the web components, including
 * all phenomenons such as failure, success, etc.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FTLWebTester {
    private WebTester tester;

    /**
     * Generates a random string with A-Z and 0-9 with the length
     * @param length the length of the generated string
     * @return a random string
     */
    static protected String getSaltString(int length) {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < length) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;

    }

    private static String GROUP_NAME = getSaltString(8);
    private static String CHAT_MESSAGE_1 = "This is the chat message 1";
    private static String CHAT_MESSAGE_2 = "This is the chat message 2";

    /**
     * Prepares the WebTester and sets the base URL
     */
    @Before
    public void prepare() {
        tester = new WebTester();
        tester.setBaseUrl("http://localhost:8080/");
    }

    /**
     * Tests if the links are visible on UserGUI
     */
    @Test
    public void testA_UserGui() {
        tester.beginAt("user");
        tester.assertTitleEquals("Movie Rating App - User Actions");
        tester.assertLinkPresent("createGroup");
        tester.assertLinkPresent("addUserToGroup");
        tester.assertLinkPresent("leaveGroup");

    }

    /**
     * Creates a test group with the generated group name, and adding 2 users to the group
     */
    @Test
    public void testB_CreateGroup() {

        tester.beginAt("user?action=createGroup");
        tester.assertTitleEquals("Movie Rating App - Create Group");
        tester.assertTextPresent("Create Group");
        tester.assertTextPresent("Group Name");
        tester.assertTextPresent("Your username");
        tester.assertTextPresent("Member names (csv)");
        tester.assertFormPresent();
        tester.assertFormElementPresent("groupName");
        tester.assertFormElementPresent("adminUserName");
        tester.assertFormElementPresent("memberUserNames");
        tester.assertFormElementPresent("createGroup");

        // Submit form with group data
        tester.setTextField("groupName", GROUP_NAME);
        tester.setTextField("adminUserName", "dbuser");
        tester.setTextField("memberUserNames", "tom,jerry");

        tester.clickButton("createGroup");
        tester.assertTextPresent("Successfully created group.");
    }

    /**
     * Creates a group with an existing group name, the operation should fail
     */
    @Test
    public void testC_CreateExistingGroup() {
        tester.beginAt("user?action=createGroup");
        tester.assertTitleEquals("Movie Rating App - Create Group");
        tester.assertTextPresent("Create Group");
        tester.assertTextPresent("Group Name");
        tester.assertTextPresent("Your username");
        tester.assertTextPresent("Member names (csv)");
        tester.assertFormPresent();
        tester.assertFormElementPresent("groupName");
        tester.assertFormElementPresent("adminUserName");
        tester.assertFormElementPresent("memberUserNames");
        tester.assertFormElementPresent("createGroup");

        // Submit form with group data
        tester.setTextField("groupName", GROUP_NAME);
        tester.setTextField("adminUserName", "dbuser");
        tester.setTextField("memberUserNames", "jim");

        tester.clickButton("createGroup");
        tester.assertTextPresent("Failed to created group.");
    }

    /**
     * Test adding a user to an existing group
     */
    @Test
    public void testD_AddUserToGroup() {
        tester.beginAt("user?action=addUserToGroup");
        tester.assertTitleEquals("Movie Rating App - Add User To Group");
        tester.assertTextPresent("Add User To Group");
        tester.assertTextPresent("Group Name");
        tester.assertTextPresent("Username");
        tester.assertFormPresent();
        tester.assertFormElementPresent("groupName");
        tester.assertFormElementPresent("userName");
        tester.assertFormElementPresent("addUserToGroup");

        // Submit form with group data
        tester.setTextField("groupName", GROUP_NAME);
        tester.setTextField("userName", "jim");

        tester.clickButton("addUserToGroup");
        tester.assertTextPresent("Successfully added user to group.");
    }

    /**
     * Test adding a non-existent user to group.
     */
    @Test
    public void testE_AddNonExistentUserToGroup() {
        tester.beginAt("user?action=addUserToGroup");
        tester.assertTitleEquals("Movie Rating App - Add User To Group");
        tester.assertTextPresent("Add User To Group");
        tester.assertTextPresent("Group Name");
        tester.assertTextPresent("Username");
        tester.assertFormPresent();
        tester.assertFormElementPresent("groupName");
        tester.assertFormElementPresent("userName");
        tester.assertFormElementPresent("addUserToGroup");

        // Submit form with group data
        tester.setTextField("groupName", GROUP_NAME);
        tester.setTextField("userName", "asdasd");

        tester.clickButton("addUserToGroup");
        tester.assertTextPresent("Failed to add user to group.");
    }

    /**
     * Submits a message to the created group, this should pass.
     */
    @Test
    public void testF_SubmitChatMessageUser_1() {
        tester.beginAt("chat");
        tester.assertTitleEquals("Movie Rating App - Chat Login");
        tester.assertTextPresent("Login to Group Chat");
        tester.assertFormPresent();
        tester.assertFormElementPresent("groupName");
        tester.assertFormElementPresent("userId");
        tester.assertFormElementPresent("action");

        tester.setTextField("groupName", GROUP_NAME);
        tester.setTextField("userId", "1");

        tester.clickButton("sendMessage");

        tester.assertTextPresent("Send Messages");
        tester.assertFormPresent();
        tester.assertFormElementPresent("message");
        tester.assertFormElementPresent("saveMessage");

        tester.setTextField("message", CHAT_MESSAGE_1);
        tester.clickButton("saveMessage");

        tester.assertTextPresent("Message sent successfully.");
    }

    /**
     * Test submitting another test message to the existing group. This should pass.
     */
    @Test
    public void testF_SubmitChatMessageUser_2() {
        tester.beginAt("chat");
        tester.assertTitleEquals("Movie Rating App - Chat Login");
        tester.assertTextPresent("Login to Group Chat");
        tester.assertFormPresent();
        tester.assertFormElementPresent("groupName");
        tester.assertFormElementPresent("userId");
        tester.assertFormElementPresent("action");

        tester.setTextField("groupName", GROUP_NAME);
        tester.setTextField("userId", "2");

        tester.clickButton("sendMessage");

        tester.assertTextPresent("Send Messages");
        tester.assertFormPresent();
        tester.assertFormElementPresent("message");
        tester.assertFormElementPresent("saveMessage");

        tester.setTextField("message", CHAT_MESSAGE_2);
        tester.clickButton("saveMessage");
        tester.assertTextPresent("Message sent successfully.");
    }

    /**
     * Test submitting a chat to a non-existing group. This should fail.
     */
    @Test
    public void testG_SubmitChatMessageFailNonExistentGroup() {
        tester.beginAt("chat");
        tester.assertTitleEquals("Movie Rating App - Chat Login");
        tester.assertTextPresent("Login to Group Chat");
        tester.assertFormPresent();
        tester.assertFormElementPresent("groupName");
        tester.assertFormElementPresent("userId");
        tester.assertFormElementPresent("action");

        tester.setTextField("groupName", "asdasdafffffff");
        tester.setTextField("userId", "2");

        tester.clickButton("sendMessage");

        tester.assertTextPresent("Group does not exist or you do not have sufficient permissions for this group");
    }

    /**
     * Test submitting a chat to an existing group but with a member which is not in a group. This should fail.
     */
    @Test
    public void testG_SubmitChatMessageFailNonExistentUser() {
        tester.beginAt("chat");
        tester.assertTitleEquals("Movie Rating App - Chat Login");
        tester.assertTextPresent("Login to Group Chat");
        tester.assertFormPresent();
        tester.assertFormElementPresent("groupName");
        tester.assertFormElementPresent("userId");
        tester.assertFormElementPresent("action");

        tester.setTextField("groupName", GROUP_NAME);
        tester.setTextField("userId", "29");

        tester.clickButton("sendMessage");

        tester.assertTextPresent("Group does not exist or you do not have sufficient permissions for this group");
    }

    /**
     * Test getting all chat messages as an existing user in the group
     */
    @Test
    public void testH_GetChatMessage() {
        tester.beginAt("chat");
        tester.assertTitleEquals("Movie Rating App - Chat Login");
        tester.assertTextPresent("Login to Group Chat");
        tester.assertFormPresent();
        tester.assertFormElementPresent("groupName");
        tester.assertFormElementPresent("userId");
        tester.assertFormElementPresent("action");

        tester.setTextField("groupName", GROUP_NAME);
        tester.setTextField("userId", "2");

        tester.clickButton("receiveMessage");
        tester.assertTextPresent(CHAT_MESSAGE_1);
        tester.assertTextPresent(CHAT_MESSAGE_2);

    }

    /**
     * Test getting all chat messages in a non-existent group
     */
    @Test
    public void testH_GetChatMessageNonExistentGroup() {
        tester.beginAt("chat");
        tester.assertTitleEquals("Movie Rating App - Chat Login");
        tester.assertTextPresent("Login to Group Chat");
        tester.assertFormPresent();
        tester.assertFormElementPresent("groupName");
        tester.assertFormElementPresent("userId");
        tester.assertFormElementPresent("action");

        tester.setTextField("groupName", "adasdasdasddsd");
        tester.setTextField("userId", "1");

        tester.clickButton("receiveMessage");

        tester.assertTextPresent("Group does not exist or you do not have sufficient permissions for this group");

    }

    /**
     * Test getting a message from a group with a user which is not in the group
     */
    @Test
    public void testH_GetChatMessageNoPrivileges() {
        tester.beginAt("chat");
        tester.assertTitleEquals("Movie Rating App - Chat Login");
        tester.assertTextPresent("Login to Group Chat");
        tester.assertFormPresent();
        tester.assertFormElementPresent("groupName");
        tester.assertFormElementPresent("userId");
        tester.assertFormElementPresent("action");

        tester.setTextField("groupName", GROUP_NAME);
        tester.setTextField("userId", "29");

        tester.clickButton("receiveMessage");

        tester.assertTextPresent("Group does not exist or you do not have sufficient permissions for this group");
    }


    /**
     * Test leaving a group
     */
    @Test
    public void testI_UserLeaveGroup() {
        tester.beginAt("user?action=leaveGroup");
        tester.assertTitleEquals("Movie Rating App - Leave Group");
        tester.assertTextPresent("Leave Group");
        tester.assertFormPresent();
        tester.assertFormElementPresent("groupName");
        tester.assertFormElementPresent("userId");
        tester.assertFormElementPresent("leaveButton");

        tester.setTextField("groupName", GROUP_NAME);
        tester.setTextField("userId", "2");

        tester.clickButton("leaveButton");
        tester.assertTextPresent("User 2 successfully left Group " + GROUP_NAME + ".");
    }

    /**
     * Test admin leaving a group. The group should be deleted.
     */
    @Test
    public void testJ_AdminLeaveGroup() {
        tester.beginAt("user?action=leaveGroup");
        tester.assertTitleEquals("Movie Rating App - Leave Group");
        tester.assertTextPresent("Leave Group");
        tester.assertFormPresent();
        tester.assertFormElementPresent("groupName");
        tester.assertFormElementPresent("userId");
        tester.assertFormElementPresent("leaveButton");

        tester.setTextField("groupName", GROUP_NAME);
        tester.setTextField("userId", "1");

        tester.clickButton("leaveButton");
        tester.assertTextPresent("User 1 is an admin of " + GROUP_NAME +".");
        tester.assertTextPresent("The group is successfully deleted.");
    }

    /**
     * Test leaving a non-existent group. This should fail.
     */
    @Test
    public void testK_NonExistentLeaveGroup () {
        tester.beginAt("user?action=leaveGroup");
        tester.assertTitleEquals("Movie Rating App - Leave Group");
        tester.assertTextPresent("Leave Group");
        tester.assertFormPresent();
        tester.assertFormElementPresent("groupName");
        tester.assertFormElementPresent("userId");
        tester.assertFormElementPresent("leaveButton");

        tester.setTextField("groupName", "asdasd");
        tester.setTextField("userId", "23");

        tester.clickButton("leaveButton");
        tester.assertTextPresent("Failed to remove user from the group.");
    }


}