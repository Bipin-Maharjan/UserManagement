<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="base/upper-head.jsp" %>
<!--add CSS for this page below-->
<%@include file="base/lower-head.jsp" %>
<%
    User user = (User) session.getAttribute("user");
    List<User> searchedUsers = (List) session.getAttribute("searchedUsers");
    int totalpage = (int) session.getAttribute("totalpageno");
%>
<script>
    document.title = "Search | <%= request.getParameter("search")%>";</script>
<div class="p-3" style="background-color: rgb(128, 146, 153);">
  <div class="bg-white">
    <div class="container">
      <div class="container bd">
        <!-- heading section -->
        <div class="row">
          <div class="col-3 bd text-center py-2">
            <span class="h4">Filters</span>
          </div>
          <div class="col-9 bd text-center py-2">
            <span class="h4"><i class="fas fa-search"></i> Search Result</span>
          </div>
        </div>
        <!-- heading section end -->
        <div class="row">
          <!-- Filter section -->
          <div class="col-3 bd pt-2">
            <span class="font-weight-bold">Filter by Role</span>
            <form action="<%= request.getContextPath()%>/search"  id="filtersearch" method="GET" class="pl-3 pt-2">
              <input type="hidden" name="search" value="<%= request.getParameter("search")%>" >
              <div class="form-check">
                <input class="form-check-input" type="radio" name="role" id="allrole" value="all" />
                <label class="form-check-label" for="allrole">
                  All
                </label>
              </div>
              <div class="form-check">
                <input class="form-check-input" type="radio" name="role" id="admin" value="admin" />
                <label class="form-check-label" for="admin">
                  Admin
                </label>
              </div>
              <div class="form-check">
                <input class="form-check-input" type="radio" name="role" id="client" value="client" />
                <label class="form-check-label" for="client">
                  Client
                </label>
              </div>
            </form>
            <script>
                $("input[type='radio'][name='role'][value='<%= request.getParameter("role") != null
                        ? (request.getParameter("role").equals("client") ? "client" : (request.getParameter("role").equals("admin") ? "admin" : "all")) : "all"%>']").attr("checked", "")
                $(document).ready(function () {
                    $("input[type='radio'][name='role']").change(function () {
                        if ($(this).val() === "all") {
                            $(this).prop("disabled", true);
                            $("#filtersearch").submit();
                        } else {
                            $("#filtersearch").submit();
                        }
                    })
                });
            </script>
          </div>

          <!-- search result section -->
          <div class="col-9 bd">
            <div style="min-height: 63vh;">
              <% for (User searchedUser : searchedUsers) {%>
              <% if (!searchedUser.getUsername().equalsIgnoreCase(user.getUsername())) {%>
              <a href="<%= request.getContextPath()%>/profile?user=<%= searchedUser.getUsername()%>">
                <div class="row mx-2 py-3 mt-3 border">
                  <div class="col-2 text-center">
                    <% if (user.getProfile_picture() != null) {%>
                    <img
                        src="${pageContext.request.contextPath}/resources/profile/<%= searchedUser.getProfile_picture()%>"
                        alt="profile picture"
                        class="rounded-circle"
                        style="width: 60px; height: 60px;"
                        />
                    <%} else {%>
                    <img
                        src="${pageContext.request.contextPath}/resources/profile/noimage.jpg"
                        alt="profile picture"
                        class="rounded-circle border border-dark"
                        style="width: 60px; height: 60px;"
                        />
                    <%}%>
                  </div>
                  <div class="col-10 p-0">
                    <div class="row no-gutters">
                      <div class="col-12 pb-1">
                        <span class="font-weight-bold"><%= searchedUser.getFirst_name().substring(0, 1).toUpperCase() + searchedUser.getFirst_name().substring(1) + " " + searchedUser.getLast_name().substring(0, 1).toUpperCase() + searchedUser.getLast_name().substring(1)%>
                          (<%= searchedUser.getUsername()%>)</span>
                      </div>
                      <div class="col-4"><span>Role: <%= searchedUser.getRole()%> </span></div>
                      <div class="w-100"></div>
                    </div>
                  </div>
                </div>
              </a>
              <%}%>
              <%}%>
              <% if (request.getParameter("page") == null) { %>
              <% if (totalpage == 1) {%>
              <!-- end result -->
              <div class="text-center">
                <hr class="border mb-1" />
                <span class="text-secondary" style="font-size: 1.1rem;">End of Result</span>
              </div>
              <%}%>
              <%} else {%>
              <% if (String.valueOf(totalpage).equalsIgnoreCase(request.getParameter("page"))) {%>
              <!-- end result -->
              <div class="text-center">
                <hr class="border mb-1" />
                <span class="text-secondary" style="font-size: 1.1rem;">End of Result</span>
              </div>
              <%}%>
              <%}%>
            </div>
            <!-- pagination -->
            <div class="row justify-content-center py-1">
              <nav aria-label="Page navigation">
                <ul class="pagination mb-0" id="start-pagination">
                  <li class="page-item">
                    <a class="page-link" href="#" aria-label="Previous">
                      <span aria-hidden="true">&laquo;</span>
                    </a>
                  </li>
                  <% for (int count = 0; count < totalpage; count++) {%>
                  <li class="page-item active"><a class="page-link" href="#"><%= count + 1%></a></li>
                    <%}%>
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
  </div>
</div>

<%@include file="base/footer.jsp" %>
<!--add js for this page below-->