<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="base/upper-head.jsp" %>
<!--add CSS for this page below-->
<%@include file="base/lower-head.jsp" %>
<script>
    document.title = "Edit Profile";
</script>
<%
    List errors = (List) request.getAttribute("errors");
    User user = (User) session.getAttribute("user");
%>
<div class="p-3" style="background-color: rgb(128, 146, 153);">
  <div class="bg-white">
    <div class="container">
      <div class="mx-5 px-3">
        <div class="row py-2 bd">
          <div class="col-12 text-center">
            <span class="h5">Edit User</span>
          </div>
        </div>
        <div class="row bd">
          <div class="col-12">
            <div class="mt-1 mb-3">
              <span class="font-weight-bold">
                <u>Personal Details</u>
              </span>
              <% if(!errors.isEmpty()){%>
                <% for (Object error : (List) errors.remove(0)) {%>
                <p class="text-danger text-center">"<%= error%>"</p>
              <%}} %>
            </div>
            <div>
              <form action="updatedetails" method="POST" class="pr-4 pl-2">
                <div class="row form-group">
                  <label for="firstname" class="col-lg-2 col-12 text-lg-right align-self-end">First Name:</label>
                  <input type="text" class="form-control col-lg-4 col-12 rounded-0" id="firstname" name="fname" placeholder="Enter First Name" value="<%= user.getFirst_name() %>"/>

                  <label for="lastname" class="col-lg-2 col-12 text-lg-right align-self-end">Last Name:</label>
                  <input type="text" class="form-control col-lg-4 col-12 rounded-0" id="lastname" name="lname" placeholder="Enter Last Name" value="<%= user.getLast_name() %>"/>
                </div>
                <div class="row form-group">
                  <label for="email" class="col-lg-2 col-12 text-lg-right align-self-end">Email:</label>
                  <input type="email" class="form-control col-lg-4 col-12 rounded-0" id="email" name="email" placeholder="Enter E-mail" value="<%= user.getEmail() %>"/>

                  <label for="username" class="col-lg-2 col-12 text-lg-right align-self-end">Username:</label>
                  <input type="text" class="form-control col-lg-4 col-12 rounded-0" id="username" name="username" placeholder="Enter Username" value="<%= user.getUsername() %>"/>
                </div>
                <div class="row form-group">
                  <label for="contactnumber" class="col-lg-2 col-12 text-lg-right align-self-end">Contact Number:</label>
                  <input type="text" class="form-control col-lg-4 col-12 rounded-0" id="contactnumber" name="contact" placeholder="Enter Contact Number" value="<%= user.getPhone_number() %>"/>

                  <label for="dateofbirth" class="col-lg-2 col-12 text-lg-right align-self-xl-end align-self-center">Date of Birth:</label>
                  <input type="date" class="form-control col-lg-4 col-12 rounded-0" id="dateofbirth" name="dob" value="<%= user.getDate_of_birth() %>"/>
                </div>
                <div class="row form-group">
                  <label for="bio" class="col-lg-2 col-12 text-lg-right align-self-center">Bio:</label>
                  <textarea class="form-control col-lg-8 col-12 rounded-0" id="bio" rows="1" name="bio"><%= (user.getBio() == null)?"": user.getBio() %></textarea>
                </div>
                <div class="row form-group justify-content-center">
                  <button type="submit" class="btn btn-secondary">Update Details</button>
                </div>
              </form>
            </div>
          </div>
        </div>
        <div class="row bd">
          <div class="col-12">
            <div class="mt-2 mb-4">
              <span class="font-weight-bold">
                <u>Change Password</u>
              </span>
              <% if(!errors.isEmpty()){%>
              <% for (Object error : (List) errors.remove(0)) {%>
              <p class="text-danger text-center">"<%= error%>"</p>
              <%}} %>
            </div>
            <div>
              <form action="changepassword" method="POST" class="pr-4 pl-2">
                <div class="row form-group">
                  <label for="currentpassword" class="col-lg-2 col-12 text-lg-right align-self-end">Current Password:</label>
                  <input type="password" class="form-control col-lg-4 col-12 rounded-0" id="currentpassword" name="currentpass" placeholder="Enter Current Password" />
                </div>
                <div class="row form-group">
                  <label for="newpassword" class="col-lg-2 col-12 text-lg-right align-self-end">New Password:</label>
                  <input type="password" class="form-control col-lg-4 col-12 rounded-0" id="newpassword" name="newpass" placeholder="Enter New Password" />

                  <label for="confirmpassword" class="col-lg-2 col-12 text-lg-right align-self-end">Confirm Password:</label>
                  <input type="password" class="form-control col-lg-4 col-12 rounded-0" id="confirmpassword" name="confirmpass" placeholder="Enter Confirm Password" />
                </div>
                <div class="row form-group justify-content-center">
                  <button type="submit" class="btn btn-secondary">Change Password</button>
                </div>
              </form>
            </div>
          </div>
        </div>
        <div class="row bd">
          <div class="col-12">
            <div class="mt-2 mb-4">
              <span class="font-weight-bold">
                <u>Change Security Question</u>
              </span>
              <% if(!errors.isEmpty()){%>
              <% for (Object error : (List) errors.remove(0)) {%>
              <p class="text-danger text-center">"<%= error%>"</p>
              <%}}%>
            </div>
            <div>
              <form action="changequestion" method="POST" class="pr-4 pl-2">
                <div class="row form-group">
                  <div class="col-lg-6 col-12">
                    <div>
                      <label for="answer1" class="align-self-end">Security Question 1:</label>
                      <input class="form-check-input float-right" type="checkbox" id="checkbox1" name="checkbox1" style="position: unset;" />
                    </div>
                    <select class="form-control rounded-0" name="question1" id="question1" disabled>
                      <option value="What is the first name of your best friend in high school?">What is the first name of your best friend in high school?</option>
                      <option value="What was the name of your first pet?">What was the name of your first pet?</option>
                      <option value="What was the first thing you learned to cook?">What was the first thing you learned to cook?</option>
                      <option value="What was the first film you saw in the theater?">What was the first film you saw in the theater?</option>
                      <option value="Where did you go the first time you flew on a plane?">Where did you go the first time you flew on a plane?</option>
                      <option value="What is the last name of your favorite elementary school teacher?">What is the last name of your favorite elementary school teacher?</option>
                    </select>
                    <input type="text" class="form-control rounded-0 mt-3" id="answer1" name="answer1" placeholder="Enter Answer" disabled />
                  </div>
                  <div class="col-lg-6 col-12">
                    <div>
                      <label for="answer2" class="align-self-end">Security Question 2:</label>
                      <input class="form-check-input float-right" type="checkbox" id="checkbox2" name="checkbox2" style="position: unset;" />
                    </div>
                    <select class="form-control rounded-0" name="question2" id="question2" disabled>
                      <option value="What is your dream job?">What is your dream job?</option>
                      <option value="What is your favorite children’s book?">What is your favorite children’s book?</option>
                      <option value="What was the model of your first car?">What was the model of your first car?</option>
                      <option value="What was your childhood nickname?">What was your childhood nickname?</option>
                      <option value="Who was your favorite film star or character in school?">Who was your favorite film star or character in school?</option>
                      <option value="Who was your favorite singer or band in high school?">Who was your favorite singer or band in high school?</option>
                    </select>
                    <input type="text" class="form-control rounded-0 mt-3" id="answer2" name="answer2" placeholder="Enter Answer" disabled />
                  </div>
                </div>
                <div class="row form-group justify-content-center">
                  <button type="submit" class="btn btn-secondary">Change Security Question</button>
                </div>
              </form>
              <script>
                  $("document").ready(function () {
                      $("#checkbox1").change(function () {
                          if ($(this).is(":checked")) {
                              $("#answer1,#question1").removeAttr("disabled");
                          } else {
                              $("#answer1,#question1").attr("disabled", "");
                          }
                      });
                      $("#checkbox2").change(function () {
                          if ($(this).is(":checked")) {
                              $("#answer2,#question2").removeAttr("disabled");
                          } else {
                              $("#answer2,#question2").attr("disabled", "");
                          }
                      });
                  });
              </script>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>

<%@include file="base/footer.jsp" %>
<!--add js for this page below-->
