<%@page import="com.novice.ums.model.History"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="base/upper-head.jsp" %>
<!--add CSS for this page below-->
<%@include file="base/lower-head.jsp" %>
<Script>
    document.title = "Dashboard";
    $(document).ready(function(){
       $('#bdashboard').addClass("active"); 
    });
</Script>
<%
    int mostActiveUserLimit = 10;
    int totalAdmins = (int) request.getAttribute("totalAdmins");
    int totalClients = (int)request.getAttribute("totalClients");
    int newUser = (int)request.getAttribute("newUser");
    int onlineUser = (int)request.getAttribute("onlineUser");
    List<History> lastVisitors = (List) request.getAttribute("lastVisitors");
    List<History> mostActiveUser = (List) request.getAttribute("mostActiveUser");
            
%>
<div class="p-3" style="background-color: rgb(128, 146, 153);">
  <div class="bg-white">
    <div class="container py-3">
      <u class="h3">DashBoard</u>
      <div class="row mt-4 justify-content-between">
        <div class="card text-white bg-dark mb-3" style="max-width: 18rem;">
          <div class="px-3 pt-4"><i class="fas fa-user-plus" style="font-size: 40px;"></i></div>
          <div class="card-body pt-0">
            <h2 class="card-title text-center mb-3"><%= newUser %></h2>
            <p class="card-text" style="font-size: 20px;">New User this Week</p>
          </div>
        </div>

        <div class="card text-white bg-dark mb-3" style="max-width: 18rem; min-width: 216px;">
          <div class="px-3 pt-4"><i class="fas fa-users" style="font-size: 40px;"></i></div>
          <div class="card-body pt-0">
            <h2 class="card-title text-center mb-3"><%= onlineUser %></h2>
            <p class="card-text text-center" style="font-size: 20px;">Online Users</p>
          </div>
        </div>

        <a href="<%= request.getContextPath() + "/adduser"%>">
          <div class="card text-white bg-dark mb-3" style="max-width: 18rem; min-width: 216px;">
            <div class="px-3 pt-5 pt-4 text-center"><i class="fas fa-plus-circle" style="font-size: 40px;"></i></div>
            <div class="card-body">
              <p class="card-text text-center" style="font-size: 20px;">Add User</p>
            </div>
          </div>
        </a>
      </div>
      <div class="row mt-4 mb-3">
        <div class="col-6">
          <div class="bd">
            <div style="background-color: #cdcdcd;">
              <p class="h5 m-0 py-1 px-3">Last Visitor</p>
            </div>
            <div style="border-top: 1px solid black; min-height: 312px;">
              <table class="table table-striped">
                <thead>
                  <tr>
                    <th scope="col">Name</th>
                    <th scope="col">Last Visit</th>
                    <th scope="col">Time Spent</th>
                    <th scope="col">IP address</th>
                  </tr>
                </thead>
                <tbody>
                  <% for(History history : lastVisitors ){%>
                  <tr>
                    <th><%= history.getExtra_info2() %></th>
                    <td><%= history.getDate_time()%></td>
                    <td><%= history.getExtra_info() %></td>
                    <td><%= history.getIp_address()%></td>
                  </tr>
                  <%}%>
                </tbody>
              </table>
            </div>
          </div>
          <div class="row justify-content-between px-3 py-4">
            <div class="card text-white bg-dark mb-3" style="max-width: 18rem; min-height: 168px; min-width: 216px;">
              <div class="card-body pt-4">
                <p class="card-text text-center" style="font-size: 22px;">Total Users</p>
                <h2 class="card-title text-center mb-3"><%= totalClients %></h2>
              </div>
            </div>
            <div class="card text-white bg-dark mb-3" style="max-width: 18rem; min-height: 168px; min-width: 216px;">
              <div class="card-body pt-4">
                <p class="card-text text-center" style="font-size: 22px;">Total Admins</p>
                <h2 class="card-title text-center mb-3"><%= totalAdmins %></h2>
              </div>
            </div>
          </div>
        </div>
        <div class="col-6">
          <div class="bd" style="max-height: 540px; min-height: 540px; overflow-y: scroll;">
            <div style="background-color: #cdcdcd;"><p class="h5 m-0 py-1 px-3">Most Active Users</p></div>
            <div style="border-top: 1px solid black;">
              <table class="table table-striped">
                <thead>
                  <tr>
                    <th scope="col">Name</th>
                    <th scope="col">Username</th>
                    <th scope="col">Last visit</th>
                    <th scope="col">Logins</th>
                  </tr>
                </thead>
                <tbody class="text-left">
                  <% int i=1;
                      for(History history: mostActiveUser){
                      if(i==mostActiveUserLimit){break;} 
                      i+=1; %>
                  <tr>
                    <th><%= history.getExtra_info2() %></th>
                    <td><a href="<%= request.getContextPath()+"/profile?user=" + history.getUsername() %>"><%= history.getUsername()%></a></td>
                    <td><%= history.getDate_time()%></td>
                    <td style="text-align: center;"><%= history.getExtra_info() %></td>
                  </tr>
                  <%}%>
                </tbody>
              </table>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>
<%@include file="base/footer.jsp" %>
<!--add js for this page below-->
