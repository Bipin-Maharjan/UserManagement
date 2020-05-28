<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="base/upper-head.jsp" %>
<!--add CSS for this page below-->
<%@include file="base/lower-head.jsp" %>
<Script>
    document.title = "Add User";
</Script>
<%
    List errors = (List) request.getAttribute("errors");
    String success = (String) request.getAttribute("success");
%>
<div class="p-3" style="background-color: rgb(128, 146, 153);">
  <div class="bg-white">
    <div class="container">
      <div class="mx-5 px-3 bd">
        <div class="row border-bottom border-dark">
          <div class="col-12 text-center py-1">
            <span class="h3">Add User</span>
          </div>
        </div>
        <div class="row justify-content-center">
          <div class="col-8 py-4">
            <%  for (Object error : errors) {%>
            <p class="text-center text-danger">"<%= error%>"</p>
            <%} errors.removeAll(errors);%>
            <% if ( !success.isBlank() ){%>
            <div class="alert alert-success alert-dismissible fade show text-center auto-dismis" role="alert">
              <%= success%>
              <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
              </button>
            </div>
             <%}%>
            <form action="" method="POST">
              <div class="row">
                <div class="form-group col-6">
                  <label for="firstname">First Name*:</label>
                  <input type="text" class="form-control rounded-0" id="firstname" required name="first_name" placeholder="First Name" />
                </div>
                <div class="form-group col-6">
                  <label for="lastname">Last Name*:</label>
                  <input type="text" class="form-control rounded-0" id="lastname" required name="last_name" placeholder="Last Name" />
                </div>
              </div>
              <div class="row">
                <div class="form-group col-6">
                  <label for="email">Email*:</label>
                  <input type="email" class="form-control rounded-0" id="email" required name="email" placeholder="Email" />
                </div>
                <div class="form-group col-6">
                  <label for="username">Username*:</label>
                  <input type="text" class="form-control rounded-0" id="username" required name="username" placeholder="Username" />
                </div>
              </div>
              <div class="row">
                <div class="form-group col-6">
                  <label for="contactnum">Contact Number*:</label>
                  <input type="text" class="form-control rounded-0" id="contactnum" required name="phone_number" placeholder="Contact Number" />
                </div>
                <div class="form-group col-6">
                  <label for="role">Role*:</label>
                  <select class="form-control rounded-0" id="role" required name="role">
                    <option disabled selected>Select Role:</option>
                    <option value="client">Client</option>
                    <option value="admin">Admin</option>
                  </select>
                </div>
              </div>
              <div class="row">
                <div class="form-group col-6">
                  <label for="password">Password*:</label>
                  <input type="password" class="form-control rounded-0" id="password" required name="password" placeholder="Password" />
                </div>
                <div class="form-group col-6">
                  <label for="confirmpass">Confirm Password*:</label>
                  <input type="password" class="form-control rounded-0" id="inputPassword4" required name="confirmpass" placeholder="Confirm Password" />
                </div>
              </div>
              <div class="row">
                <div class="form-group col-6">
                  <label for="dateofbirth">Date of Birth*:</label>
                  <input type="date" class="form-control rounded-0" id="dateofbirth" required name="date_of_birth" />
                </div>
                <div class="form-group col-6">
                  <label for="gender">Gender*:</label>
                  <select class="form-control rounded-0" id="role" required name="gender">
                    <option disabled selected>Select Gender:</option>
                    <option value="male">Male</option>
                    <option value="admin">Female</option>
                  </select>
                </div>
              </div>
              <div class="row">
                <div class="form-group col-12">
                  <label for="securityquestion1">Security Question 1*:</label>
                  <select class="form-control rounded-0" name="question1" id="question1" required name="question1">
                    <option value="What is the first name of your best friend in high school?">What is the first name of your best friend in high school?</option>
                    <option value="What was the name of your first pet?">What was the name of your first pet?</option>
                    <option value="What was the first thing you learned to cook?">What was the first thing you learned to cook?</option>
                    <option value="What was the first film you saw in the theater?">What was the first film you saw in the theater?</option>
                    <option value="Where did you go the first time you flew on a plane?">Where did you go the first time you flew on a plane?</option>
                    <option value="What is the last name of your favorite elementary school teacher?">What is the last name of your favorite elementary school teacher?</option>
                  </select>
                  <input type="text" class="form-control rounded-0 mt-2" id="securityanswer1" required name="answer1" placeholder="Answer" />
                </div>
              </div>
              <div class="row">
                <div class="form-group col-12">
                  <label for="securityquestion2">Security Question 2*:</label>
                  <select class="form-control rounded-0" name="question2" id="question2" required name="question2">
                    <option value="What is your dream job?">What is your dream job?</option>
                    <option value="What is your favorite children’s book?">What is your favorite children’s book?</option>
                    <option value="What was the model of your first car?">What was the model of your first car?</option>
                    <option value="What was your childhood nickname?">What was your childhood nickname?</option>
                    <option value="Who was your favorite film star or character in school?">Who was your favorite film star or character in school?</option>
                    <option value="Who was your favorite singer or band in high school?">Who was your favorite singer or band in high school?</option>
                  </select>
                  <input type="text" class="form-control rounded-0 mt-2" id="securityanswer2" required name="answer2" placeholder="Answer" />
                </div>
              </div>
              <div class="row">
                <div class="col-12 text-center">
                  <button type="submit" class="btn btn-secondary px-3"><i class="fas fa-user-plus"></i> Add User</button>
                </div>
              </div>
            </form>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>
<%@include file="base/footer.jsp" %>
<!--add js for this page below-->
