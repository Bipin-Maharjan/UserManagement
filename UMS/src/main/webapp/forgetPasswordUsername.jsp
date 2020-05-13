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
%>
<div class="contaner-fluid">
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
        <span class="mb-5 mt-1 d-block font-weight-bold" style="font-size: 28px;">Forget password recovery</span>
      </div>
    </div>
    <div class="container mt-lg-3 pr-lg-5 pl-lg-2">
      <div class="b_formWidth" style="width: 50%; margin-right: auto; margin-left: auto;">
        <form action="<%= request.getContextPath() %>/account/checkusername/" method="POST" class="py-4 pr-lg-5 px-4 border rounded shadow-lg" style="background-color: #c8cecd;">
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
          <div class="row form-group justify-content-center my-4">
            <button type="submit" class="btn btn-info px-5">Recover</button>
          </div>
        </form>
      </div>
    </div>
  </div>
</div>

<%@include file="base/footer.jsp" %>
<!--add js for this page below-->