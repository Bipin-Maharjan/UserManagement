<%-- 
    Document   : profile
    Created on : May 1, 2020, 9:07:34 PM
    Author     : HP
    VIEW       : this is a jsp page and is only for UI page
--%>
<%@page import="java.util.List"%>
<%@page import="com.novice.ums.model.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="base/upper-head.jsp" %>
<%@include file="base/lower-head.jsp" %>
<% User user = (User) session.getAttribute("user"); User otherUser = (User) request.getAttribute("otherUser"); %>
<script>
    document.title = "<%= otherUser.getUsername() %>";
</script>
<div class="p-3" style="background-color: rgb(128, 146, 153);">
  <div class="bg-white">
    <div class="container py-4">
      <!-- history button -->
      <div class="row">
        <% if(user != null && user.getRole().equalsIgnoreCase("admin")){ %>
        <div class="col-12 text-center">
          <% if(otherUser.getStatus().equalsIgnoreCase("blocked")){ %>
          <p class="h6 font-weight-bold text-danger text-center d-md-inline-block"> User is blocked </p>
          <%}%>
          <button class="btn btn-info py-1 d-md-inline-block float-md-right mr-2 mb-md-0 mb-2">View History</button>
        </div>
        <% } %>
        <div class="col-12 text-center" id="error">
          <% List errors = (List)request.getAttribute("errors");%>
          <%  for(Object error: errors){ %>
          <span class="text-danger">"<%= error %>"<%= errors.size()==1?"":"," %> </span>
          <%} errors.removeAll(errors);%>
        </div>
      </div>
      <!-- history button end -->
      <!-- profile picture -->
      <div class="row justify-content-center">
        <div class="col-lg-4 col-12 mb-2 text-center">
          <div>
            <% if(user.getProfile_picture() != null){ %>
            <img src="${pageContext.request.contextPath}/resources/profile/<%= user.getProfile_picture()%>" alt="" class="image-fluid" style="width:300px;" />
            <%} else {%>
            <img src="${pageContext.request.contextPath}/resources/profile/noimage.jpg" alt="" class="image-fluid" style="width:300px;" />
            <%}%>
          </div>
          <p class="h4"><%= otherUser.getFirst_name()+" "+otherUser.getLast_name() %></p>
          <p class="h6">@<%= otherUser.getUsername() %></p>
        </div>
      </div>
      <!-- profile picture end -->
      <!-- About Me -->
      <div class="row justify-content-center">
        <div class="col-lg-8 col-xl-7 col-12">
          <div class="border border-dark rounded">
            <!-- heading bar -->
            <div class="border border-dark rounded text-center">
              <p class="m-0 d-inline-block font-weight-bold" style="font-size: 1rem;">About Me </p>
              <div class="d-inline-block float-right mr-2">
                <% if(user.getRole().equalsIgnoreCase("admin") && !user.getUsername().equalsIgnoreCase(otherUser.getUsername())){ %>
                <a href="#" class="text-dark" id="editDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                  <i class="fas fa-caret-down align-middle"></i>
                </a>
                <div class="dropdown-menu border-dark" aria-labelledby="editDropdown">
                  <a class="dropdown-item" href="profile?u=<%= otherUser.getStatus().equalsIgnoreCase("active")?otherUser.getUsername()+"/blockrequest":otherUser.getUsername()+"/activerequest" %>"><%= otherUser.getStatus().equalsIgnoreCase("active")?"Block User":"Activate User" %></a>
                  <div class="dropdown-divider border-dark"></div>
                  <a class="dropdown-item" href="profile?u=<%= otherUser.getUsername()+"/deleterequest"%>">Delete User</a>
                </div>
                <%}%>
              </div>
            </div>
            <!-- heading bar ends-->
            <!-- details -->
            <div class="p-3">
              <dl class="row">
                <dt class="col-2 text-right">Name:</dt>
                <dd class="col-4 text-wrap text-break"><%= otherUser.getFirst_name()+" "+otherUser.getLast_name() %></dd>

                <dt class="col-3 text-right">Username:</dt>
                <dd class="col-3 text-wrap text-break"><%= otherUser.getUsername() %></dd>
              </dl>
              <dl class="row">
                <dt class="col-2 text-right">Email:</dt>
                <dd class="col-4 text-wrap text-break"><%= otherUser.getEmail() %></dd>

                <dt class="col-3 text-right">Contact No.:</dt>
                <dd class="col-3 text-wrap text-break"><%= otherUser.getPhone_number() %></dd>
              </dl>
              <dl class="row">
                <dt class="col-2 text-right">Gender:</dt>
                <dd class="col-4 text-wrap text-break"><%= otherUser.getGender() %></dd>

                <dt class="col-3 text-right">Date of Birth:</dt>
                <dd class="col-3 text-wrap text-break"><%= otherUser.getDate_of_birth() %></dd>
              </dl>
              <dl class="row mb-0">
                <dt class="col-2 text-right">Bio:</dt>
                <dd class="col-10">
                  <div class="border border-secondary px-2 text-justify">
                    <%= (otherUser.getBio()!=null)?otherUser.getBio():"<br>" %>
                  </div>
                </dd>
              </dl>
            </div>
            <!-- details end -->
          </div>
        </div>
      </div> 
      <!-- About Me end -->
    </div>
  </div>
</div>
<%@include file="base/footer.jsp" %>