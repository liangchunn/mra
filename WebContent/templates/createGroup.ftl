<#include "header.ftl">

<h2>Create Group</h2>
<form method="POST" action="user?action=createGroup">
    <fieldset>
        <div>
            <label>Group Name</label>
            <input type="text" name="groupName" id="groupName"/>
        </div>
        <div>
            <label>Your UserID (number)</label>
            <input type="text" name="adminId" id="adminId"/>
        </div>
        <div>
            <label>Member IDs (csv)</label>
            <input type="text" name="memberIds" id="memberIds"/>
        </div>
    </fieldset>
    <button type="submit" name="createGroup">Submit</button>
</form>

<#include "footer.ftl">