<%-- 
    Document   : profile
    Created on : May 1, 2020, 9:07:34 PM
    Author     : HP
    VIEW       : this is a jsp page and is only for UI page
--%>
<%@page import="java.util.List"%>
<%@page import="com.novice.ums.model.User"%>
<%@page import="java.util.Hashtable"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="base/upper-head.jsp" %>
<%@include file="base/lower-head.jsp" %>
<script>
    document.title = "Profile";
</script>
<div class="p-3" style="background-color: rgb(128, 146, 153);">
  <div class="bg-white">
    <div class="container py-4">
      <!-- history button -->
      <div class="row">
        <% User user = (User) session.getAttribute("user"); %>
        <% if(user != null && user.getRole() == "admin"){ %>
        <div class="col-12 text-center">
          <% if(user.getStatus()=="blocked"){ %>
          <p class="h6 font-weight-bold text-danger text-center d-md-inline-block"> User is blocked </p>
          <%}%>
          <button class="btn btn-info py-1 d-md-inline-block float-md-right mr-2 mb-md-0 mb-2">View History</button>
        </div>
        <% } %>
        <div class="col-12 text-center mb-2" id="error">
          <% List errors = (List) request.getAttribute("errors");%>
          <%  for(Object error: errors){ %>
          <span class="text-danger">"<%= error %>", </span>
          <%} errors.removeAll(errors);%>
        </div>
      </div>
      <!-- history button end -->
      <!-- profile picture -->
      <div class="row justify-content-center">
        <div class="col-lg-4 col-12 mb-2 text-center">
          <div id="b_profil_container">
            <% if(user.getProfile_picture() != null){ %>
              <img id="b_profilepic" src="${pageContext.request.contextPath}/resources/profile/<%= user.getProfile_picture()%>" alt="" class="image-fluid" style="width:300px;" />
            <%} else {%>
              <img id="b_profilepic" src="${pageContext.request.contextPath}/resources/profile/noimage.jpg" alt="" class="image-fluid" style="width:300px;" />
            <%}%>
            <i id="b_addicon" class="fas fa-plus-circle"></i>
          </div>
          <form method="post" id="b_uploadprofile" action="profile" enctype="multipart/form-data">
            <input id="b_uploadprofilepic" type="file" name="profile_picture" accept="image/jpeg,image/png">
          </form>
          <p class="h4"><%= user.getFirst_name()+" "+user.getLast_name() %></p>
          <p class="h6">@<%= user.getUsername() %></p>
        </div>
      </div>
      <!-- profile picture end -->
      <!-- About Me -->
      <div class="row justify-content-center">
        <div class="col-lg-8 col-xl-7 col-12">
          <div class="border border-dark rounded">
            <!-- heading bar -->
            <div class="border border-dark rounded text-center">
              <p class="m-0 d-inline-block font-weight-bold" style="font-size: 1rem;">About Me</p>
              <div class="d-inline-block float-right mr-2">
                <a href="#" class="text-dark"><i class="fas fa-pencil-alt"></i></a>
              </div>
            </div>
            <!-- heading bar ends-->
            <!-- details -->
            <div class="p-3">
              <dl class="row">
                <dt class="col-2 text-right">Name:</dt>
                <dd class="col-4 text-wrap text-break"><%= user.getFirst_name()+" "+user.getLast_name() %></dd>

                <dt class="col-3 text-right">Username:</dt>
                <dd class="col-3 text-wrap text-break"><%= user.getUsername() %></dd>
              </dl>
              <dl class="row">
                <dt class="col-2 text-right">Email:</dt>
                <dd class="col-4 text-wrap text-break"><%= user.getEmail() %></dd>

                <dt class="col-3 text-right">Contact No.:</dt>
                <dd class="col-3 text-wrap text-break"><%= user.getPhone_number() %></dd>
              </dl>
              <dl class="row">
                <dt class="col-2 text-right">Gender:</dt>
                <dd class="col-4 text-wrap text-break"><%= user.getGender() %></dd>

                <dt class="col-3 text-right">Date of Birth:</dt>
                <dd class="col-3 text-wrap text-break"><%= user.getDate_of_birth() %></dd>
              </dl>
              <dl class="row mb-0">
                <dt class="col-2 text-right">Bio:</dt>
                <dd class="col-10">
                  <div class="border border-secondary px-2 text-justify">
                    <%= (user.getBio()!=null)?user.getBio():"<br>" %>
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