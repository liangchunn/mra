<#include "header.ftl">

<h2>Add User To Group</h2>
<form method="POST" action="user?action=addUserToGroup">
    <fieldset>
        <div>
            <label>Group Name</label>
            <input type="text" name="groupName" id="groupName"/>
        </div>
        <div>
            <label>User ID</label>
            <input type="text" name="userId" id="userId"/>
        </div>
    </fieldset>
    <button type="submit" name="addUserToGroup">Submit</button>
</form>

<#include "footer.ftl">