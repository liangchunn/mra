<#include "header.ftl">

<h2>Create Group</h2>
<form method="POST" action="user?action=createGroup">
    <fieldset>
        <div>
            <label>Group Name</label>
            <input type="text" name="groupName" id="groupName"/>
        </div>
        <div>
            <label>Your username</label>
            <input type="text" name="adminUserName" id="adminUserName"/>
        </div>
        <div>
            <label>Member names (csv)</label>
            <input type="text" name="memberUserNames" id="memberUserNames"/>
        </div>
    </fieldset>
    <button type="submit" name="createGroup" id="createGroup">Submit</button>
</form>

<#include "footer.ftl">