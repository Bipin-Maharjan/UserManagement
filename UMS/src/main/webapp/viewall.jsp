<%@page import="java.util.Arrays"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="base/upper-head.jsp" %>
<!--add CSS for this page below-->
<%@include file="base/lower-head.jsp" %>
<%
    List<User> report_users = (List) request.getAttribute("report_users");
    String[] column = (String[]) request.getAttribute("column");
    int pageno = (int) request.getAttribute("pageno");
    String report_page = (String) request.getAttribute("data");
%>
<script>
    document.title = "Report | <%= report_page%>";</script>
<div class="" style="background-color: rgb(157, 163, 165);">
  <div class="container b_container">
    <div class="container b_container">
      <div class="bg-white container b_container">
        <div class="row bd p-2">
          <div class="col-md-4 col-sm-5">
            <span class="h3 mb-0">List of <%= report_page == "client" ? "Clients" : (report_page == "admin" ? "Admins" : "Online Users")%></span>
          </div>
          <div class="col-sm-4 col-md-5"></div>
          <div class="col-md-3 col-sm-3">
            <form action="" class="mt-1 mb-0 row form-group">
              <div class="col-6 text-lg-right align-self-end">
                <label for="sort">Sort:</label>
              </div>
              <div class="col-6 p-0 pl-2">
                <div class="d-inline-block pl-2"><input type="radio" name="sorttype" checked="checked" /> <span>A-Z</span></div>
                <div class="d-inline-block pl-2"><input type="radio" name="sorttype" /> <span>Z-A</span></div>
              </div>
            </form>
          </div>
        </div>
        <div class="row bd px-3 px-md-0">
          <table class="table table-striped table-hover">
            <thead class="thead-light">
              <tr>
                <th scope="col" style="width:25%"><%= column[0]%></th>
                <th scope="col" style="width:25%"><%= column[1]%></th>
                <th scope="col" style="width:25%"><%= column[2]%></th>
                <th scope="col" style="width:25%"><%= column[3]%></th>
              </tr>
            </thead>
            <tbody>
              <% for (User user : report_users) {%>
              <tr>
                <td><%= user.getFirst_name().substring(0, 1).toUpperCase() + user.getFirst_name().substring(1) + " "
                        + user.getLast_name().substring(0, 1).toUpperCase() + user.getLast_name().substring(1)%></td>
                <td><a href="<%= request.getContextPath() + "/profile?user=" + user.getUsername()%>"><%= user.getUsername()%></a></td>
                <td><%= user.getEmail()%></td>
                <% if (report_page == "online") {%>
                <td><%= user.getExtra_info()%></td>
                <% } else {%>
                <td><%= user.getPhone_number()%></td>
                <%}%>
              </tr>
              <%}%>
            </tbody>
          </table>
        </div>
        <div class="row justify-content-center">
          <nav aria-label="Page navigation">
            <ul class="pagination mb-0" id="start-pagination">
              <li class="page-item">
                <a class="page-link" href="#" aria-label="Previous">
                  <span aria-hidden="true">&laquo;</span>
                </a>
              </li>
              <% for (int count = 0; count < pageno; count++) {%>
              <li class="page-item"><a class="page-link" href="#"><%= count + 1%></a></li>
                <% }%>
              <li class="page-item">
                <a class="page-link" href="#" aria-label="Next">
                  <span aria-hidden="true">&raquo;</span>
                </a>
              </li>
            </ul>
          </nav>
          <script>
              var activeli = $("#start-pagination li")[<%= request.getParameter("page") != null ? request.getParameter("page") : 1%>];
              $(activeli).addClass("active");
              
              $(document).ready(function () {
                  $("#start-pagination li").click(function (event) {
                      event.preventDefault();
                      var pageno = $(this).text(); //taking clicked tag value
                      var url = new URL(window.location.href); // converting url into url object
                      if (pageno.trim() === "«") {
                          pageno = getActivetText();
                          if (pageno < 1) {
                              pageno = 1;

                          } else if (pageno == 1) {
                              return;
                          } else {
                              pageno -= 1;
                          }
                      } else if (pageno.trim() === "»") {
                          pageno = getActivetText();
                          if ($("#start-pagination li").length - 2 > pageno) {
                              pageno += 1;
                          } else if ($("#start-pagination li").length - 2 == pageno) {
                              return
                          } else if ($("#start-pagination li").length - 2 < pageno) {
                              pageno = $("#start-pagination li").length - 2;
                          }
                      }

                      if (url.search === "") {
                          url.search = "page=" + pageno;
                      } else {
                          let match = url.search.match(/((\?|\&)page=[0-9]+)#?$/gm); //checking if  page number is already there with regex.
                          if (match != null) {
                              url.search = url.search.replace(match[0], "");
                          }
                          if (url.search === "") {
                              url.search = "page=" + pageno;
                          } else {
                              url.search += "&page=" + pageno;
                          }
                      }
                      changeActiveClass(pageno);
                      window.location.href = url.href; // send request to server with modified url.
                  });
              });
              function changeActiveClass(pageno) {

                  for (li of $("#start-pagination li")) {
                      if ($(li).text() == pageno) {
                          $("#start-pagination li").removeClass("active");
                          $(li).addClass("active");
                      }
                  }
              }
              function getActivetText() {
                  for (li of $("#start-pagination li")) {
                      if ($(li).hasClass("active")) {
                          return parseInt($(li).text());
                      }
                  }
              }
          </script>
        </div>
      </div>
    </div>
  </div>
</div>

<%@include file="base/footer.jsp" %>
<!--add js for this page below-->