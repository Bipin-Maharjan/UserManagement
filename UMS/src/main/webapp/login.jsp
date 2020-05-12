<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="base/upper-head.jsp" %>
<!--add CSS for this page below-->
<%@include file="base/lower-head-nologin.jsp" %>
<script>
    document.title = "Login";
</script>
<%
    List errors = (List) request.getAttribute("errors");
%>
<div class="contaner-fluid">
  <div class="mx-3">
    <div class="row my-2">
      <div class="col-xl-2 col-12 text-center">
        <a href="register" class="btn btn-info mr-2">Sign up</a>
        <a href="login" class="btn btn-info ml-3">Login</a>
      </div>
      <div class="w-100"></div>
    </div>
    <div class="row">
      <div class="col-12 text-center align-content-center">
        <span class="mb-5 mt-1 d-block font-weight-bold" style="font-size: 28px;">Login with User Management System Account</span>
      </div>
    </div>
    <div class="container mt-lg-3 pr-lg-5 pl-lg-2">
      <div class="b_formWidth" style="width: 50%; margin-right: auto; margin-left: auto;">
        <form action="login/" method="POST" class="py-4 pr-lg-5 px-4 border rounded shadow-lg" style="background-color: #e1e2e2;">
          <%  for (Object error : errors) {%>
          <p class="text-center text-danger">"<%= error%>"</p>
          <%} errors.removeAll(errors); %>
          <div class="row form-group justify-content-center mt-5">
            <div class="col-lg-4 col-12 text-lg-right align-self-end">
              <label for="username">Username:</label>
            </div>
            <div class="col-lg-8 col-12 text-lg-right">
              <input type="text" class="form-control rounded-0" id="username" name="username" placeholder="Enter Username" required />
            </div>
          </div>
          <div class="row form-group justify-content-center">
            <div class="col-lg-4 col-12 text-lg-right align-self-end">
              <label for="password">Password:</label>
            </div>
            <div class="col-lg-8 col-12">
              <input type="password" class="form-control rounded-0" id="password" name="password" placeholder="Enter Password" required />
              <i class="fas fa-eye position-absolute" id="seepassword" style="top: 12px; right: 22px; cursor: pointer;"> </i>
              <script>
                  $(document).ready(function () {
                      $("#seepassword").click(function () {
                          if ($("#password").attr("type") === "password") {
                              $("#password").attr("type", "text");
                              $("#seepassword").removeClass("fa-eye");
                              $("#seepassword").addClass("fa-eye-slash");
                          } else {
                              $("#password").attr("type", "password");
                              $("#seepassword").removeClass("fa-eye-slash");
                              $("#seepassword").addClass("fa-eye");
                          }
                      });
                  });
              </script>
            </div>
          </div>
          <div class="row form-group justify-content-center">
            <a href="<%= request.getContextPath()+"/account/forgetpassword" %>" class="d-block">Forgot Password?</a>
          </div>
          <div class="row form-group justify-content-center mt-2">
            <button type="submit" class="btn btn-info px-5">Login</button>
          </div>
        </form>
      </div>
    </div>
  </div>
</div>

<%@include file="base/footer.jsp" %>
<!--add js for this page below-->