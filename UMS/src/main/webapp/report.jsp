<%@page import="java.util.ArrayList"%>
<%@page import="com.novice.ums.model.History"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="base/upper-head.jsp" %>
<!--add CSS for this page below-->
<%@include file="base/lower-head.jsp" %>
<%
    List<History> histories = (List) request.getAttribute("histories");
    List<User> clientUsers = (List) request.getAttribute("clientUsers");
    List<User> adminUsers = (List) request.getAttribute("adminUsers");
    List<User> onlineUsers = (List) request.getAttribute("onlineUsers");
    List<User> usersBlocked;
    List<User> usersCreated;

    if (request.getAttribute("usersBlocked") != null) {
        if (request.getAttribute("usersBlocked").getClass() == "".getClass()) {
            usersBlocked = new ArrayList();
        } else {
            usersBlocked = (List) request.getAttribute("usersBlocked");
        }
    } else {
        usersBlocked = null;
    }

    if (request.getAttribute("usersCreated") != null) {
        if (request.getAttribute("usersCreated").getClass() == "".getClass()) {
            usersCreated = new ArrayList();
        } else {
            usersCreated = (List) request.getAttribute("usersCreated");
        }
    } else {
        usersCreated = null;
    }

    String data = "";
    int link = 0;
    List<String> tableHead = new ArrayList();
    if (histories != null) {
        data = "histories";
        link = 1;
        tableHead.add("Username");
        tableHead.add("Date and Time");
        tableHead.add("Remark");

    } else if (clientUsers != null) {
        data = "clientUsers";
        link = 2;
        tableHead.add("Name");
        tableHead.add("Username");
        tableHead.add("Email");
        tableHead.add("Contact No");
    } else if (adminUsers != null) {
        data = "adminUsers";
        link = 3;
        tableHead.add("Name");
        tableHead.add("Username");
        tableHead.add("Email");
        tableHead.add("Contact No");
    } else if (usersCreated != null) {
        data = "usersCreated";
        link = 4;
        tableHead.add("Name");
        tableHead.add("Username");
        tableHead.add("Email");
        tableHead.add("Joined Date");
    } else if (usersBlocked != null) {
        data = "usersBlocked";
        link = 5;
        tableHead.add("Name");
        tableHead.add("Username");
        tableHead.add("Email");
        tableHead.add("Blocked date");
    } else if (onlineUsers != null) {
        data = "onlineUsers";
        link = 6;
        tableHead.add("Name");
        tableHead.add("Username");
        tableHead.add("Email");
        tableHead.add("Login Time");
    }

%>
<script>
    document.title = "Report";
</script>
<div style="background-color: rgb(157, 163, 165);">
  <div class="container" style="background-color: white;">
    <div class="row">
      <div class="col-3 p-0 border" style="border-color: #dfdfdf;">
        <div class="nav flex-column nav-pills list-group-flush" id="v-pills-tab" role="tablist" aria-orientation="vertical">
          <span class="list-group-item text-dark font-weight-bold" style="background-color: #b3b5b7; text-decoration: underline;"> Navigations: </span>
          <a class="nav-link list-group-item rounded-0" href="<%= request.getContextPath() + "/report/history"%>"> History </a>
          <a class="nav-link list-group-item rounded-0" href="<%= request.getContextPath() + "/report/clients"%>"> Clients </a>
          <a class="nav-link list-group-item rounded-0" href="<%= request.getContextPath() + "/report/admins"%>"> Admins </a>
          <a class="nav-link list-group-item rounded-0" href="<%= request.getContextPath() + "/report/usercreated"%>"> User Created </a>
          <a class="nav-link list-group-item rounded-0" href="<%= request.getContextPath() + "/report/userblocked"%>"> User Blocked </a>
          <a class="nav-link list-group-item rounded-0" href="<%= request.getContextPath() + "/report/online"%>"> Online Clients </a>
        </div>
        <script>
            var link = <%= link%>;
            var item = $(".list-group-item")[link];
            $(item).addClass("active bg-info border-info");
        </script>
      </div>
      <div class="col-9 p-0">
        <div class="tab-content" id="v-pills-tabContent">
          <div class="<%= data != "usersCreated" && data != "usersBlocked" ? "px-0" : "px-3"%>">
            <% if (data == "usersCreated" || data == "usersBlocked") {%>
            <div>
              <p class="my-2 font-weight-bold">Select Date Range</p>
              <div class="alert alert-dark rounded-0 mb-0 py-1 px-3">Custom Date</div>
              <form action="<%= data == "usersCreated" ? request.getContextPath() + "/report/usercreated" : request.getContextPath() + "/report/userblocked"%>" method="GET">
                <div class="form-group row px-5 py-2">
                  <div class="col-4">
                    <label for="startdate">Start Date:</label>
                    <input type="date" class="form-control" id="startdate" name="start" required value="<%= request.getParameter("start")%>" />
                  </div>
                  <div class="col-1"></div>
                  <div class="col-4">
                    <label for="enddate">End Date:</label>
                    <input type="date" class="form-control" id="enddate" name="end" required value="<%= request.getParameter("end")%>" />
                  </div>
                  <div class="col-3 align-self-end text-right">
                    <button class="btn btn-primary" type="submit">Get Records</button>
                  </div>
                </div>
              </form>
              <div class="alert alert-dark rounded-0 mb-0 py-1 px-3">User <%= data == "usersCreated" ? "Created" : "Blocked"%> Records</div>
            </div>
            <% } %>
            <% if (!data.isBlank()) {%>
            <div <%= data != "usersCreated" && data != "usersBlocked" ? "class=\"mt-0\" style=\" min-height:85vh \"" : "class=\"mt-2\" style=\" min-height:53vh \""%>>
              <table class="table table-striped table-hover">
                <thead class="thead-light">
                  <tr>
                    <th scope="col"><%= tableHead.get(0)%></th>
                    <th scope="col"><%= tableHead.get(1)%></th>
                    <th scope="col" style="<%= data != "histories" ? "width:25%" : "width:32%"%>"><%= tableHead.get(2)%></th>
                      <%if (data != "histories") {%>
                    <th scope="col" style="width:25%"><%= tableHead.get(3)%></th>
                      <%}%>
                  </tr>
                </thead>
                <tbody>
                  <% if (data == "histories") { %>
                  <% for (History history : histories) {%>
                  <tr>
                    <td><a href="<%= request.getContextPath() + "/profile?user=" + history.getUsername()%>"><%= history.getUsername()%></a></td>
                    <td><%= history.getDate_time()%></td>
                    <td><%= history.getRemark()%></td>
                  </tr>
                  <%}%>

                  <%} else if (data == "clientUsers") { %>
                  <% for (User user : clientUsers) {%>
                  <tr>
                      <td><%= user.getFirst_name().substring(0, 1).toUpperCase() + user.getFirst_name().substring(1) + " "
                            + user.getLast_name().substring(0, 1).toUpperCase() + user.getLast_name().substring(1)%></td>
                    <td><a href="<%= request.getContextPath() + "/profile?user=" + user.getUsername()%>"><%= user.getUsername()%></a></td>
                    <td><%= user.getEmail()%></td>
                    <td><%= user.getPhone_number()%></td>
                  </tr>
                  <%}%> 

                  <%} else if (data == "adminUsers") { %>
                  <% for (User user : adminUsers) {%>
                  <tr>
                      <td><%= user.getFirst_name().substring(0, 1).toUpperCase() + user.getFirst_name().substring(1) + " "
                            + user.getLast_name().substring(0, 1).toUpperCase() + user.getLast_name().substring(1)%></td>
                    <td><a href="<%= request.getContextPath() + "/profile?user=" + user.getUsername()%>"><%= user.getUsername()%></a></td>
                    <td><%= user.getEmail()%></td>
                    <td><%= user.getPhone_number()%></td>
                  </tr>
                  <%}%>  

                  <%} else if (data == "usersCreated") { %>
                  <% for (User user : usersCreated) {%>
                  <tr>
                      <td><%= user.getFirst_name().substring(0, 1).toUpperCase() + user.getFirst_name().substring(1) + " "
                            + user.getLast_name().substring(0, 1).toUpperCase() + user.getLast_name().substring(1)%></td>
                    <td><a href="<%= request.getContextPath() + "/profile?user=" + user.getUsername()%>"><%= user.getUsername()%></a></td>
                    <td><%= user.getEmail()%></td>
                    <td><%= user.getExtra_info()%></td>
                  </tr>
                  <%}%>

                  <%} else if (data == "usersBlocked") { %>
                  <% for (User user : usersBlocked) {%>
                  <tr>
                      <td><%= user.getFirst_name().substring(0, 1).toUpperCase() + user.getFirst_name().substring(1) + " "
                            + user.getLast_name().substring(0, 1).toUpperCase() + user.getLast_name().substring(1)%></td>
                    <td><a href="<%= request.getContextPath() + "/profile?user=" + user.getUsername()%>"><%= user.getUsername()%></a></td>
                    <td><%= user.getEmail()%></td>
                    <td><%= user.getExtra_info()%></td>
                  </tr>
                  <%}%>

                  <%} else if (data == "onlineUsers") { %>
                  <% for (User user : onlineUsers) {%>
                  <tr>
                      <td><%= user.getFirst_name().substring(0, 1).toUpperCase() + user.getFirst_name().substring(1) + " "
                            + user.getLast_name().substring(0, 1).toUpperCase() + user.getLast_name().substring(1)%></td>
                    <td><a href="<%= request.getContextPath() + "/profile?user=" + user.getUsername()%>"><%= user.getUsername()%></a></td>
                    <td><%= user.getEmail()%></td>
                    <td><%= user.getExtra_info()%></td>
                  </tr>
                  <%}%>     
                  <%}%>
                </tbody>
              </table>
              <% if (data != "usersCreated" && data != "usersBlocked") {%>
              <div class="border p-1 text-center" style="border-color: #dfdfdf;">
                <% if (data == "histories") { %>
                    <a href="<%= request.getContextPath()+"/history/all" %>" class="btn btn-info px-3">View More</a>
                <%} else if (data == "clientUsers") {%>
                    <a href="<%= request.getContextPath()+"/report/clients/all" %>" class="btn btn-info px-3">View More</a>
                <%} else if (data == "adminUsers") {%>
                    <a href="<%= request.getContextPath()+"/report/admins/all" %>" class="btn btn-info px-3">View More</a>
                <%} else if (data == "onlineUsers") {%>
                    <a href="<%= request.getContextPath()+"/report/online/all" %>" class="btn btn-info px-3">View More</a>
                <%}%>
              </div>
              <%}%>
            </div>
            <% } else { %>
            <div style="height:85vh;"></div>
            <%}%>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>

<%@include file="base/footer.jsp" %>
<!--add js for this page below-->