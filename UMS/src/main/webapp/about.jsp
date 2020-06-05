<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="base/upper-head.jsp" %>
<!--add CSS for this page below-->
<%@include file="base/lower-head-nologin.jsp" %>

<div class="mx-3">
  <div class="row" style="background-color: rgb(157, 163, 165);">
    <div class="col-xl-2 col-12 text-center my-2">
      <a href="<%= request.getContextPath()+"/account/register" %>" class="btn btn-info mr-2 border border-light">Sign up</a>
      <a href="<%= request.getContextPath()+"/account/login" %>" class="btn btn-info ml-3 border border-light">Login</a>
    </div>
    <div class="w-100"></div>
    <div class="container bg-light mt-2 mb-4">
      <div class="row justify-content-center my-3">
        <div class="col-10">
          <p class="h3 text-center">About</p>
          <p class="h5 font-weight-bold">
            <u>About Developers:</u>
          </p>
          <div class="py-2 px-3">
            <p class="text-justify">
              In our fourth semester bachelor, the Novice Team was formed to complete our coursework. We had the same vision and a goal of completing the coursework on time. With this mindset,
              we began to develop a User Management System for our coursework.
            </p>
            <p>
              The Novice Team consist of 5 team members and they are:-
            </p>
            <ul>
              <li>
                <a href="https://www.linkedin.com/in/bipin-maharjan-36999916b/" data-toggle="tooltip" data-placement="right" title="Linkedin Profile">
                  Bipin Maharjan
                </a>
              </li>
              <li>
                <a href="#" data-toggle="tooltip" data-placement="right" title="No Link">
                  Kisan Rai
                </a>
              </li>
              <li>
                <a href="#" data-toggle="tooltip" data-placement="right" title="No Link">
                  Ishwor Pokhrel
                </a>
              </li>
              <li>
                <a href="#" data-toggle="tooltip" data-placement="right" title="No Link">
                  Dipak Rai
                </a>
              </li>
              <li>
                <a href="#" data-toggle="tooltip" data-placement="right" title="No Link">
                  Luza Maharjan
                </a>
              </li>
            </ul>
          </div>
        </div>
      </div>
      <div class="row justify-content-center mb-3">
        <div class="col-10">
          <p class="h5 font-weight-bold">
            <u>Privacy Policy for Novice :</u>
          </p>
          <div class="py-2 px-3">
            <div>
              <p>
                At UMS, one of our main priorities is the privacy of our visitors. This Privacy Policy document contains types of information that is collected and recorded by UMS and how we use
                it.
              </p>

              <p>
                This Privacy Policy applies only to our online activities and is valid for visitors to our website with regards to the information that they shared and/or collect in UMS. This
                policy is not applicable to any information collected offline or via channels other than this website.
              </p>
            </div>

            <div>
              <u><h4>Consent</h4></u>

              <p>By using our website, you hereby consent to our Privacy Policy and agree to its terms.</p>

              <div>
                <u><h4>Information we collect</h4></u>

                <p>
                  The personal information that you are asked to provide, and the reasons why you are asked to provide it, will be made clear to you at the point we ask you to provide your
                  personal information.
                </p>
                <p>
                  If you contact us directly, we may receive additional information about you such as your name, email address, phone number, the contents of the message and/or attachments you
                  may send us, and any other information you may choose to provide.
                </p>
                <p>When you register for an Account, we may ask for your contact information, including items such as name, email address, and telephone number.</p>
              </div>

              <div>
                <u><h4>How we use your information</h4></u>

                <p>We use the information we collect in various ways, including to:</p>

                <ul>
                  <li>Provide, operate, and maintain our website</li>
                  <li>Improve, personalize, and expand our website</li>
                  <li>Understand and analyze how you use our website</li>
                  <li>Develop new products, services, features, and functionality</li>
                  <li>Send you emails</li>
                  <li>Find and prevent fraud</li>
                </ul>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>
<script>
    $("ul li>a").tooltip();
</script>

<%@include file="base/footer.jsp" %>
<!--add js for this page below-->
