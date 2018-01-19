<#include "header.ftl">

<h2>Leave Group</h2>
<form id="groupLeaveForm" method="POST" action="user?action=leaveGroup">
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
    <button type="submit" name="leaveButton">Leave Group</button>
</form>

<#include "footer.ftl">