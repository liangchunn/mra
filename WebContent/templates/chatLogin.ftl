<#include "header.ftl">

<h2>Login to Group Chat</h2>
<form id="chatLoginForm" method="POST">
    <fieldset>
        <div>
            <label>Group Name</label>
            <input type="text" name="groupName" id="groupName"/>
        </div>
        <div>
            <label>UserId</label>
            <input type="text" name="userId" id="userId"/>
        </div>
    </fieldset>
    <input type="submit" name="action" value="Send Messages" id="sendMessage"/>
    <input type="submit" name="action" value="Receive Messages" id="receiveMessage"/>
</form>

<#include "footer.ftl">