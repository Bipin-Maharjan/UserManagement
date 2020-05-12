<%@page import="com.novice.ums.model.User"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="base/upper-head.jsp" %>
<!--add CSS for this page below-->
<%@include file="base/lower-head-nologin.jsp" %>
<script>
    document.title = "Forget Password";
</script>
<%
    List errors = (List) request.getAttribute("errors");
    User user = (User)session.getAttribute("recoverUser");
    session.removeAttribute("recoverUser");
    if(user == null){
        response.sendRedirect(request.getContextPath()+"/account/login");
        return;
    }
%>
<div class="mx-3">
  <div class="row my-2">
    <div class="col-xl-2 col-12 text-center">
      <a href="<%= request.getContextPath() %>/account/register" class="btn btn-info mr-2">Sign up</a>
      <a href="<%= request.getContextPath() %>/account/login" class="btn btn-info ml-3">Login</a>
    </div>
    <div class="w-100"></div>
  </div>
  <div class="row">
    <div class="col-12 text-center align-content-center">
      <span class="mb-5 mt-1 d-block font-weight-bold" style="font-size: 28px;">Set New Password for <%= user.getUsername() %></span>
      <span>Please do not refresh. Refresh will redirect you to login page.</span>  
    </div>
  </div>
  <div class="container mt-lg-3 pr-lg-5 pl-lg-2">
    <div class="b_formWidth" style="width: 50%; margin-right: auto; margin-left: auto;">
      <form action="<%= request.getContextPath() %>/account/setnewpassword/" method="POST" class="py-4 pr-lg-5 px-4 border rounded shadow-lg" style="background-color: #e1e2e2;">
        <%  for (Object error : errors) {%>
          <p class="text-center text-danger">"<%= error%>"</p>
          <%} errors.removeAll(errors); %>
        <div class="row form-group justify-content-center mt-5">
          <div class="col-lg-4 col-12 text-lg-right align-self-end">
            <label for="password">New Password:</label>
          </div>
          <div class="col-lg-8 col-12 text-lg-right">
            <input type="password" class="form-control rounded-0" id="password" name="password" placeholder="Enter New Password" required />
          </div>
        </div>
        <div class="row form-group justify-content-center">
          <div class="col-lg-4 col-12 text-lg-right align-self-end">
            <label for="password">Confirm Password:</label>
          </div>
          <div class="col-lg-8 col-12">
            <input type="password" class="form-control rounded-0" id="confpassword" name="confpassword" placeholder="Enter Confirm Password" required />
            <i class="fas fa-eye position-absolute" id="seepassword" style="top: 12px; right: 22px; cursor: pointer;"> </i>
            <script>
                $(document).ready(function () {
                    $("#seepassword").click(function () {
                        if ($("#password").attr("type") === "password") {
                            $("#password,#confpassword").attr("type", "text");
                            $("#seepassword").removeClass("fa-eye");
                            $("#seepassword").addClass("fa-eye-slash");
                        } else {
                            $("#password,#confpassword").attr("type", "password");
                            $("#seepassword").removeClass("fa-eye-slash");
                            $("#seepassword").addClass("fa-eye");
                        }
                    });
                });
            </script>
          </div>
        </div>
        <div class="row form-group justify-content-center mt-4">
          <input type="hidden" name="username" value="<%= user.getUsername() %>">
          <button type="submit" class="btn btn-info px-4">Change Password</button>
        </div>
      </form>
    </div>
  </div>
</div>

<%@include file="base/footer.jsp" %>
<!--add js for this page below-->