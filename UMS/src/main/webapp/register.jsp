<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="base/upper-head.jsp" %>
<!--add CSS for this page below-->
<%@include file="base/lower-head-nologin.jsp" %>
<script>
    document.title = "Register"
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
        <span class="mb-3 mt-1 d-block font-weight-bold" style="font-size: 28px;">Create an account for User Management System</span>
      </div>
    </div>
    <div class="container pr-lg-5 pl-lg-2">
      <div>
        <form action="register/" method="POST" class="p-lg-3 pl-4 pr-lg-5 pr-4 border rounded shadow-lg" style="background-color: #e1e2e2;">
          <%  for (Object error : errors) {%>
          <p class="text-center text-danger">"<%= error%>"</p>
          <%} errors.removeAll(errors);%>
          <div class="row form-group mt-5">
            <label for="firstname" class="col-lg-2 col-12 text-lg-right align-self-end">First Name:</label>
            <input type="text" class="form-control col-lg-4 col-12 rounded-0" id="firstname" name="fname" placeholder="Enter First Name" required />

            <label for="lastname" class="col-lg-2 col-12 text-lg-right align-self-end">Last Name:</label>
            <input type="text" class="form-control col-lg-4 col-12 rounded-0" id="lastname" name="lname" placeholder="Enter Last Name" required />
          </div>
          <div class="row form-group">
            <label for="email" class="col-lg-2 col-12 text-lg-right align-self-end">Email:</label>
            <input type="text" class="form-control col-lg-4 col-12 rounded-0" id="email" name="email" placeholder="Enter E-mail" required />

            <label for="username" class="col-lg-2 col-12 text-lg-right align-self-end">Username:</label>
            <input type="text" class="form-control col-lg-4 col-12 rounded-0" id="username" name="username" placeholder="Enter Username" required />
          </div>
          <div class="row form-group">
            <label for="password" class="col-lg-2 col-12 text-lg-right align-self-end">Password:</label>
            <input type="password" class="form-control col-lg-4 col-12 rounded-0" id="password" name="password" placeholder="Enter Password" required />

            <label for="confirmpassword" class="col-lg-2 col-12 text-lg-right align-self-end">Confirm Password:</label>
            <div class="col-lg-4 col-12 p-0">
              <input type="password" class="form-control rounded-0" id="confirmpassword" name="confpassword" placeholder="Enter Confirm Password" required />
              <i class="fas fa-eye position-absolute" id="seepassword" style="top: 12px; right: 7px; cursor: pointer;"></i>
              <script>
                  $(document).ready(function () {
                      $("#seepassword").click(function () {
                          if ($("#password").attr("type") === "password") {
                              $("#password,#confirmpassword").attr("type", "text");
                              $("#seepassword").removeClass("fa-eye");
                              $("#seepassword").addClass("fa-eye-slash");
                          } else {
                              $("#password,#confirmpassword").attr("type", "password");
                              $("#seepassword").removeClass("fa-eye-slash");
                              $("#seepassword").addClass("fa-eye");
                          }
                      });
                  });
              </script>
            </div>
          </div>
          <div class="row form-group">
            <label for="contactnumber" class="col-lg-2 col-12 text-lg-right align-self-end">Contact Number:</label>
            <input type="text" class="form-control col-lg-4 col-12 rounded-0" id="contactnumber" name="phonenumber" placeholder="Enter Contact Number" required />

            <label for="dateofbirth" class="col-lg-2 col-12 text-lg-right align-self-xl-end align-self-center">Date of Birth:</label>
            <input type="date" class="form-control col-lg-2 col-12 rounded-0" id="dateofbirth" name="dob" required />

            <label for="gender" class="col-lg-1 col-12 text-lg-left align-self-xl-end align-self-center">Gender:</label>
            <select class="form-control col-lg-1 col-12 rounded-0" name="gender" id="gender" required>
              <option value="Male">Male</option>
              <option value="Female">Female</option>
            </select>
          </div>
          <div class="row form-group">
            <label for="question1" class="col-lg-2 col-12 text-lg-right align-self-end">Security Question 1:</label>
            <select class="form-control col-lg-4 col-12 rounded-0" name="question1" id="question1" required>
              <option value="What is the first name of your best friend in high school?">What is the first name of your best friend in high school?</option>
              <option value="What was the name of your first pet?">What was the name of your first pet?</option>
              <option value="What was the first thing you learned to cook?">What was the first thing you learned to cook?</option>
              <option value="What was the first film you saw in the theater?">What was the first film you saw in the theater?</option>
              <option value="Where did you go the first time you flew on a plane?">Where did you go the first time you flew on a plane?</option>
              <option value="What is the last name of your favorite elementary school teacher?">What is the last name of your favorite elementary school teacher?</option>
            </select>

            <label for="question2" class="col-lg-2 col-12 text-lg-right align-self-end">Security Question 2:</label>
            <select class="form-control col-lg-4 col-12 rounded-0" name="question2" id="question2" required>
              <option value="What is your dream job?">What is your dream job?</option>
              <option value="What is your favorite children’s book?">What is your favorite children’s book?</option>
              <option value="What was the model of your first car?">What was the model of your first car?</option>
              <option value="What was your childhood nickname?">What was your childhood nickname?</option>
              <option value="Who was your favorite film star or character in school?">Who was your favorite film star or character in school?</option>
              <option value="Who was your favorite singer or band in high school?">Who was your favorite singer or band in high school?</option>
            </select>
          </div>
          <div class="row form-group">
            <label for="answer1" class="col-lg-2 col-12 text-lg-right align-self-end">Answer:</label>
            <input type="text" class="form-control col-lg-4 col-12 rounded-0" id="answer1" name="answer1" placeholder="Enter Answer" required />

            <label for="answer2" class="col-lg-2 col-12 text-lg-right align-self-end">Answer:</label>
            <input type="text" class="form-control col-lg-4 col-12 rounded-0" id="answer2" name="answer2" placeholder="Enter Answer" required />
          </div>
          <div class="row form-group justify-content-center mt-2">
            <button type="submit" class="btn btn-info px-4">Sign up</button>
          </div>
        </form>
      </div>
    </div>
  </div>
</div>

<%@include file="base/footer.jsp" %>
<!--add js for this page below-->