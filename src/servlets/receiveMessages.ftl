<#include "../../WebContent/templates/header.ftl">

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

<#list messages as message>
    ${message}
</#list>

<c:forEach var="message" items="messages">
    <c:out value="${message.creatorName}"/>
</c:forEach>

<#include "../../WebContent/templates/footer.ftl">