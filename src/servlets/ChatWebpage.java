package servlets;

import application.MRAApplication;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Cody's Original Chat Implementation on 14.01.18.
 */
public class ChatWebpage extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        String action = (request.getParameter("action") == null) ? "" : request.getParameter("action");
        if (action.equals("chatLogin")) {
            request.setAttribute("pagetitle", "Chat Login");
            request.setAttribute("hid", request.getParameter("hid"));
            try {
                request.getRequestDispatcher("/templates/chatLogin.ftl").forward(request, response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            request.setAttribute("pagetitle", "Chat Login");
            request.setAttribute("hid", request.getParameter("hid"));
            try {
                request.getRequestDispatcher("/templates/chatLogin.ftl").forward(request, response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("navtype", "guest");
        if (request.getParameter("action").equals("Send Messages")) {
            String shared_group = request.getParameter("groupName");
            Integer shared_user = Integer.parseInt(request.getParameter("userId"));
            try {
                boolean success = MRAApplication.getInstance().chatLogin(
                        shared_group,
                        shared_user
                );
                System.out.println(success);
                if (success) {
                    request.setAttribute("sharedGroup", shared_group); // add to request
                    request.setAttribute("sharedUser", shared_user); // add to request
                    request.getSession().setAttribute("sharedGroup", shared_group); // add to session
                    request.getSession().setAttribute("sharedUser", shared_user); // add to session
                    this.getServletConfig().getServletContext().setAttribute("sharedGroup", shared_group); // add to application context
                    this.getServletConfig().getServletContext().setAttribute("sharedUser", shared_user); // add to application context
                    request.setAttribute("pagetitle", "Send Message to the group");
                    try {
                        request.getRequestDispatcher("/templates/sendMessages.ftl").forward(request, response);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                request.setAttribute("pagetitle", "Error");
                request.setAttribute("message", "Something went wrong!");
                try {
                    request.getRequestDispatcher("/templates/failInfoRepresentation.ftl").forward(request, response);
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                e.printStackTrace();
            }
        } else if (request.getParameter("action").equals("saveMessage")) {
            String shared_group = request.getSession().getAttribute("sharedGroup").toString();
            Integer shared_user = Integer.parseInt(request.getSession().getAttribute("sharedUser").toString());
            try {
                boolean success = MRAApplication.getInstance().sendMessages(
                        shared_group,
                        shared_user,
                        request.getParameter("message")
                );
                System.out.println(success);
                if (success) {
                    request.setAttribute("pagetitle", "Send Message to the group");
                    request.setAttribute("message", "Message sent successfully.");
                    try {
                        request.getRequestDispatcher("/templates/okRepresentation.ftl").forward(request, response);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    request.setAttribute("pagetitle", "Sending Error");
                    request.setAttribute("message", "Failed to send the message.");
                    try {
                        request.getRequestDispatcher("/templates/failInfoRepresentation.ftl").forward(request, response);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                request.setAttribute("pagetitle", "Error");
                request.setAttribute("message", "Something went wrong!");
                try {
                    request.getRequestDispatcher("/templates/failInfoRepresentation.ftl").forward(request, response);
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                e.printStackTrace();
            }
        }
    }
}

