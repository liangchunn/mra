<#include "header.ftl">

<h2>Send Messages</h2>
<form method="POST" action="chat?action=saveMessage">
    <fieldset>
        <div>
            <label>Message</label>
            <textarea id="messageText" class="text" cols="86" rows="20" name="message"></textarea>
        </div>
    </fieldset>
    <button type="submit" name="saveMessage">Send</button>
</form>

<#list message as user>
  <div>
      ${user}<#sep>, </#sep>
  </div>
</#list>



<#include "footer.ftl">