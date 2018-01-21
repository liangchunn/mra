package servlets;

import application.MRAApplication;
import datatypes.ChatData;
import view.receiveMessagesView;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

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

    private boolean checkLoginCredentials(HttpServletRequest request, HttpServletResponse response) {
        String shared_group = request.getParameter("groupName");
        Integer shared_user = Integer.parseInt(request.getParameter("userId"));
        try {
            boolean success = MRAApplication.getInstance().chatLogin(
                    shared_group,
                    shared_user
            );
            if (success) {
                request.setAttribute("sharedGroup", shared_group);
                request.setAttribute("sharedUser", shared_user);
                request.getSession().setAttribute("sharedGroup", shared_group);
                request.getSession().setAttribute("sharedUser", shared_user);
                this.getServletConfig().getServletContext().setAttribute("sharedGroup", shared_group);
                this.getServletConfig().getServletContext().setAttribute("sharedUser", shared_user);
                return true;
            } else {
                return false;
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
        return false;
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("navtype", "guest");

        if (request.getParameter("action").equals("Send Messages")) {
            if (checkLoginCredentials(request, response)) {
                request.setAttribute("pagetitle", "Send Message to the group");
                try {
                    request.getRequestDispatcher("/templates/sendMessages.ftl").forward(request, response);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        } else if (request.getParameter("action").equals("Receive Messages")) {
            if (checkLoginCredentials(request, response)) {
                try {
                    ArrayList<ChatData> messages = MRAApplication.getInstance().receiveMessages(request.getParameter("groupName"));
                    receiveMessagesView.viewMessages(messages, response);
                    if (!messages.isEmpty()) {
                        request.setAttribute("pagetitle", "Showing Messages of " + request.getParameter("groupName") + " Group");
                        try {
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
        } else if (request.getParameter("action").equals("saveMessage")) {
            String shared_group = request.getSession().getAttribute("sharedGroup").toString();
            Integer shared_user = Integer.parseInt(request.getSession().getAttribute("sharedUser").toString());
            try {
                boolean success = MRAApplication.getInstance().sendMessages(
                        shared_group,
                        shared_user,
                        request.getParameter("message")
                );
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

