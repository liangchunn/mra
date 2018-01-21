package servlets;

import application.MRAApplication;
import dbadapter.GroupFacade;
import extraClasses.MyResult;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

/**
 * Created by liangchun on 14.01.18.
 */
public class UserGUI extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        String action = (request.getParameter("action") == null) ? "" : request.getParameter("action");
        switch (action) {
            case "clean":
                int result = GroupFacade.getInstance().autoDeleteGroups();
                if (result == 0) {
                    request.setAttribute("pagetitle", "No groups to delete");
                    request.setAttribute("message", "No groups found with 1 members.");
                    try {
                        request.getRequestDispatcher("/templates/okRepresentation.ftl").forward(request, response);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else if (result == 1) {
                    request.setAttribute("pagetitle", "Delete Groups Successful");
                    request.setAttribute("message", "Successfully deleted groups with 1 members.");
                    try {
                        request.getRequestDispatcher("/templates/okRepresentation.ftl").forward(request, response);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else {
                    request.setAttribute("pagetitle", "Delete Groups Failed");
                    request.setAttribute("message", "Error encountered when deleting groups with 1 members!");
                    try {
                        request.getRequestDispatcher("/templates/failInfoRepresentation.ftl").forward(request, response);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            case "createGroup":
                request.setAttribute("pagetitle", "Create Group");
                request.setAttribute("hid", request.getParameter("hid"));
                try {
                    request.getRequestDispatcher("/templates/createGroup.ftl").forward(request, response);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "addUserToGroup":
                request.setAttribute("pagetitle", "Add User To Group");
                request.setAttribute("hid", request.getParameter("hid"));
                try {
                    request.getRequestDispatcher("/templates/addUserToGroup.ftl").forward(request, response);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "leaveGroup":
                request.setAttribute("pagetitle", "Leave Group");
                request.setAttribute("hid", request.getParameter("hid"));
                try {
                    request.getRequestDispatcher("/templates/leaveGroup.ftl").forward(request, response);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            default:
                request.setAttribute("pagetitle", "User Actions");
                request.setAttribute("hid", request.getParameter("hid"));
                try {
                    request.getRequestDispatcher("/templates/user.ftl").forward(request, response);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }
    protected void doPost (HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("navtype", "guest");
        if (request.getParameter("action").equals("createGroup")) {
            try {
                ArrayList<String> splitUserNames = new ArrayList<>();
                String[] splitUserNamesIntermediate = request.getParameter("memberUserNames").split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
                for (String aSplitUserNamesIntermediate : splitUserNamesIntermediate) {
                    splitUserNames.add(aSplitUserNamesIntermediate);
                }
                boolean success = MRAApplication.getInstance().createGroup(
                        request.getParameter("groupName"),
                        request.getParameter("adminUserName"),
                        splitUserNames
                );
                if (success) {
                    request.setAttribute("pagetitle", "Create Group Successful");
                    request.setAttribute("message", "Successfully created group.");
                    try {
                        request.getRequestDispatcher("/templates/okRepresentation.ftl").forward(request, response);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    request.setAttribute("pagetitle", "Create Group Failed");
                    request.setAttribute("message", "Failed to created group.");
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
        } else if (request.getParameter("action").equals("addUserToGroup")) {
            try {
                String groupName = request.getParameter("groupName");
                String userName = request.getParameter("userName");
                boolean success = MRAApplication.getInstance().addUserToGroup(groupName, userName);
                if (success) {
                    request.setAttribute("pagetitle", "Add User Successful");
                    request.setAttribute("message", "Successfully added user to group.");
                    try {
                        request.getRequestDispatcher("/templates/okRepresentation.ftl").forward(request, response);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    request.setAttribute("pagetitle", "Add User Failed");
                    request.setAttribute("message", "Failed to add user to group.");
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
        } else if (request.getParameter("action").equals("leaveGroup")) {
            try {
                String groupName = request.getParameter("groupName");
                Integer userId = Integer.parseInt(request.getParameter("userId"));
                MyResult result = MRAApplication.getInstance().leaveGroup(groupName, userId);
                if (result.getFirst()) {
                    if (result.getSecond() == 1) {
                        request.setAttribute("pagetitle", "Leave & Delete Group Successful");
                        request.setAttribute("message", "User " + userId + " is an admin of " + groupName + "." + "<br />The group is successfully deleted.");
                    } else if (result.getSecond() == 2) {
                        request.setAttribute("pagetitle", "Leave Group Successful");
                        request.setAttribute("message", "User " + userId + " successfully left Group " + groupName + ".");
                    }
                    try {
                        request.getRequestDispatcher("/templates/okRepresentation.ftl").forward(request, response);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    request.setAttribute("pagetitle", "Remove User Failed");
                    request.setAttribute("message", "Failed to remove user from the group.");
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
