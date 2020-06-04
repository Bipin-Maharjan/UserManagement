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
    $(document).ready(function(){
       $('#bprofile').addClass("active"); 
    });
</script>
<div class="p-3" style="background-color: rgb(128, 146, 153);">
  <div class="bg-white">
    <div class="container py-4">
      <!-- history button -->
      <div class="row">
        <% String success = (String) request.getAttribute("success"); %>
        <% if (!success.isBlank()) {%>
        <div class="col-12">
          <div class="row justify-content-center">
            <div class="col-md-8 col-12">
              <div class="alert alert-success alert-dismissible fade show text-center auto-dismis" role="alert">
                <%= success%>
                <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                  <span aria-hidden="true">&times;</span>
                </button>
              </div>
            </div>
          </div>
        </div>
        <% } %>
        <% User user = (User) session.getAttribute("user"); %>
        <div class="col-12 text-center mb-2" id="error">
          <% List errors = (List) request.getAttribute("errors");%>
          <%  for (Object error : errors) {%>
          <span class="text-danger">"<%= error%>"<%= errors.size() == 1 ? "" : ","%></span>
          <%}
              errors.removeAll(errors);%>
        </div>
      </div>
      <!-- history button end -->
      <!-- profile picture -->
      <div class="row justify-content-center">
        <div class="col-lg-4 col-12 mb-2 text-center">
          <div id="b_profil_container">
            <% if (user.getProfile_picture() != null) {%>
            <img id="b_profilepic" src="${pageContext.request.contextPath}/resources/profile/<%= user.getProfile_picture()%>" alt="" class="image-fluid" style="width:300px;" />
            <%} else {%>
            <img id="b_profilepic" src="${pageContext.request.contextPath}/resources/profile/noimage.jpg" alt="" class="image-fluid" style="width:300px;" />
            <%}%>
            <i id="b_addicon" class="fas fa-plus-circle"></i>
          </div>
          <form method="post" id="b_uploadprofile" action="profile/uploadprofilepicture" enctype="multipart/form-data">
            <input id="b_uploadprofilepic" type="file" name="profile_picture" accept="image/jpeg,image/png">
          </form>
          <p class="h4"><%= user.getFirst_name().substring(0, 1).toUpperCase() + user.getFirst_name().substring(1) + " " + user.getLast_name().substring(0, 1).toUpperCase() + user.getLast_name().substring(1)%></p>
          <p class="h6">@<%= user.getUsername()%></p>
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
                <a href="profile/editprofile" class="text-dark"><i class="fas fa-pencil-alt"></i></a>
              </div>
            </div>
            <!-- heading bar ends-->
            <!-- details -->
            <div class="p-3">
              <dl class="row">
                <dt class="col-2 text-right">Name:</dt>
                <dd class="col-4 text-wrap text-break"><%= user.getFirst_name().substring(0, 1).toUpperCase() + user.getFirst_name().substring(1) + " " + user.getLast_name().substring(0, 1).toUpperCase() + user.getLast_name().substring(1)%></dd>

                <dt class="col-3 text-right">Username:</dt>
                <dd class="col-3 text-wrap text-break"><%= user.getUsername()%></dd>
              </dl>
              <dl class="row">
                <dt class="col-2 text-right">Email:</dt>
                <dd class="col-4 text-wrap text-break"><%= user.getEmail()%></dd>

                <dt class="col-3 text-right">Contact No.:</dt>
                <dd class="col-3 text-wrap text-break"><%= user.getPhone_number()%></dd>
              </dl>
              <dl class="row">
                <dt class="col-2 text-right">Gender:</dt>
                <dd class="col-4 text-wrap text-break"><%= user.getGender().substring(0, 1).toUpperCase() + user.getGender().substring(1)%></dd>

                <dt class="col-3 text-right">Date of Birth:</dt>
                <dd class="col-3 text-wrap text-break"><%= user.getDate_of_birth()%></dd>
              </dl>
              <dl class="row mb-0">
                <dt class="col-2 text-right">Bio:</dt>
                <dd class="col-10">
                  <div class="border border-secondary px-2 text-justify">
                    <%= (user.getBio() != null) ? user.getBio() : "<br>"%>
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