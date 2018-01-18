package view;

import datatypes.ChatData;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

public class receiveMessagesView {

    public static void viewMessages(ArrayList<ChatData> messages, HttpServletResponse response) {
        PrintWriter out = null;
        try {
            out = response.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }
        out.println("<!DOCTYPE HTML>\n" +
                "<html lang='de' dir='ltr'>\n" +
                "<head>\n" +
                "\t<meta charset=\"utf-8\" />\n" +
                "\t<title>Movie Rating App - ${pagetitle}</title>\n" +
                "\t<link type=\"text/css\" href=\"css/style.css\" rel=\"stylesheet\" media=\"screen\" />\n" +
                "\t<link rel=\"stylesheet\" href=\"//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css\" />\n" +
                "  \t<script src=\"//code.jquery.com/jquery-1.10.2.js\"></script>\n" +
                "  \t<script src=\"//code.jquery.com/ui/1.11.4/jquery-ui.js\"></script>\n" +
                "  \t<script>\n" +
                "  \t\t$(function() {\n" +
                "    \t\t$( \"#datepicker2\" ).datepicker(\n" +
                "    \t\t{\n" +
                "    \t\t\tminDate:'today',\n" +
                "    \t\t\t\n" +
                "    \t\t});\n" +
                " \n" +
                "  \t\t\t$(\"#datepicker1\").datepicker({\n" +
                "  \t\t\t\tminDate:'today',\n" +
                "    \t\t\tonSelect: function (dateValue, inst) {\n" +
                "        \t\t\t$(\"#datepicker2\").datepicker(\"option\", \"minDate\", dateValue)\n" +
                "    \t\t\t}\n" +
                "\t\t\t});\n" +
                "\t\t});\n" +
                "  \t</script>\n" +
                "</head>\n" +
                "<body>\n" +
                "<div id=\"wrapper\">\n" +
                "\t<div id=\"logo\">Movie Rating App</div>\n" +
                "    <ul id=\"navigation\">\n" +
                "    \t<li><a href=\"index\" title=\"Index\">Home</a></li>\n" +
                "\n" +
                "\t\t<li><a href=\"user\" title=\"User Actions\">User</a></li>\n" +
                "    </ul>\n" +
                "\t<div id=\"content\">");
        SimpleDateFormat formattedDate = new SimpleDateFormat("DD/MM/yyyy hh:mm:ss");
        for (ChatData message : messages) {
            try {
                out = response.getWriter();
            } catch (IOException e) {
                e.printStackTrace();
            }
            response.setContentType("text/html;charset=UTF-8");
            out.println("<table style=\"margin-bottom: 10px; width:100%;\" border=\"0\">\n" +
                    "\t\t\t\t<tr>\n" +
                    "\t\t\t\t\t<td>" + message.getMessageText() + "</td>\n" +
                    "\t\t\t\t</tr>\n" +
                    "                <tr>\n" +
                    "                    <td style=\"border-top:0; text-align; right;\"><i>Time of creation:</i>" +
                    formattedDate.format(new Date(message.getCreationTime().getTime())) +
                    "    <i>Created By:</i> " + message.getCreatorName() + "</td>\n" +
                    "                </tr>\n" +
                    "\t\t\t</table>");
        }

        out.println("\t</div>\n" +
                "\t<div id=\"copyright\">\n" +
                "        &copy; 2018 <a href=\"http://swe.uni-due.de\" title=\"SWE Uni Due\">Software Engineering - University of\n" +
                "        Duisburg-Essen</a>\n" +
                "\t</div>\n" +
                "</div>\n" +
                "</body>\n" +
                "</html>");
    }
}
