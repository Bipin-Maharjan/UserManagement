<%@page import="com.novice.ums.model.History"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="base/upper-head.jsp" %>
<!--add CSS for this page below-->
<%@include file="base/lower-head.jsp" %>
<%
    User user = (User) session.getAttribute("user");
    String username = (String) request.getAttribute("username");
    String all = (String) request.getAttribute("all");
    String mine = (String) request.getAttribute("mine");
    List<History> histories = (List) session.getAttribute("histories");
    int pageno = (int) session.getAttribute("pageno");
    List<String> types = (List) session.getAttribute("types");
%>
<script>
    document.title = "History<%= (username != null) ? " | " + username : ""%>";</script>

<div class="" style="background-color: rgb(157, 163, 165);">
  <div class="container b_container">
    <div class="container b_container">
      <div class="bg-white container b_container">
        <div class="row bd p-2">
          <div class="col-md-3 col-sm-4">
            <span class="h5 mb-0">
              <%= all != null ? "Client's History" : (mine != null ? "Your History" : ("History of " + username))%>
            </span>
          </div>
          <div class="col-sm-4 col-md-6">
            <% if (all != null) {%>
            <form action="" id="historysearch" class="mx-auto b_formWidth" style="width: 50%;">
              <div class="input-group">
                <input type="text" class="form-control" name="hsearch" placeholder="Username" id="input-search" 
                       aria-label="username" aria-describedby="button-search" value="<%= request.getParameter("hsearch") != null ? request.getParameter("hsearch") : ""%>" />
                <div class="input-group-append">
                  <button class="btn btn-outline-success" type="submit" id="button-search"><i class="fas fa-search form-control-feedback"></i></button>
                </div>
              </div>
            </form>
            <script>
                $(document).ready(function () {
                    $("#button-search").click(function (event) {
                        event.preventDefault();
                        if ($("#input-search").val().trim() === "") {
                            $("#input-search").prop("disabled", true);
                        }
                        $("#historysearch").submit();
                    });
                });
            </script>
            <%}%>
          </div>
          <div class="col-md-3 col-sm-4">
            <form action="" id="historyfilter" class="mt-1">
              <% if (request.getParameter("hsearch") != null) {%>
              <input type="hidden" name="hsearch" value="<%= request.getParameter("hsearch")%>">
              <%} else if (request.getParameter("user") != null) {%>
              <input type="hidden" name="user" value="<%= request.getParameter("user")%>">
              <%}%>
              <div class="input-group">
                <select class="form-control form-control-sm pl-3" name="type" id="type">
                  <option value="all">All</option>
                 <% for(String type : types){ %>
                 <option value="<%= type %>"><%= type.substring(0, 1).toUpperCase() + type.substring(1) %></option>
                <% }%>
                </select>
                <i class="fas fa-filter" style="font-size: 10px; position: absolute; top: 11px; left: 7px; z-index: 100;"> </i>
              </div>
            </form>
            <script>
                $('#type option[value="<%= request.getParameter("type") != null ? request.getParameter("type") : "all"%>"]').prop("selected", true);
                $(document).ready(function () {
                    $("#type").change(function () {
                        option = $("#type option:selected")[0];
                        if (option.value === "all"){
                            $("#type").prop("disabled",true);
                            $("#historyfilter").submit();
                        }
                        else{
                            $("#historyfilter").submit();
                        }
                    });
                });
            </script>
          </div>
        </div>
        <div class="row bd px-3 px-md-0">
          <table class="table table-striped table-hover">
            <thead class="thead-light">
              <tr>
                <th scope="col">#</th>
                <th scope="col">Username</th>
                <th scope="col">Date and Time</th>
                <th scope="col" id="b_remarkcol" style="width:32%">Remark</th>
              </tr>
            </thead>
            <tbody>
              <% for(int count=0; count<histories.size();count++){%>
              <% History history = histories.get(count); %>
              <tr>
                <th scope="row"><%= count+1 %></th>
                <td><a href="<%= request.getContextPath()+"/profile?user="+history.getUsername() %>"><%= history.getUsername() %></a></td>
                <td><%= history.getDate_time() %></td>
                <td><%= history.getRemark().trim() %></td>
              </tr>
              <%} %>
            </tbody>
          </table>
        </div>
        <div class="row justify-content-center">
          <nav aria-label="Page navigation">
            <ul class="pagination mb-0" id="pagination">
              <li class="page-item">
                <a class="page-link" href="#" aria-label="Previous">
                  <span aria-hidden="true">&laquo;</span>
                </a>
              </li>
              <% for(int count = 0; count<pageno; count++){ %>
              <li class="page-item"><a class="page-link" href="#"><%= count+1 %></a></li>
              <% } %>
              <li class="page-item">
                <a class="page-link" href="#" aria-label="Next">
                  <span aria-hidden="true">&raquo;</span>
                </a>
              </li>
            </ul>
          </nav>
          <script>
              activeli = $("#pagination li")[<%= request.getParameter("page") != null ? request.getParameter("page") : 1%>]
              $(activeli).addClass("active");
              
              $(document).ready(function () {
                  $("#pagination li").click(function (event) {
                      event.preventDefault();
                      var pageno = $(this).text();
                      var url = new URL(window.location.href);
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
                          if ($("#pagination li").length - 2 > pageno) {
                              pageno += 1;
                          } else if ($("#pagination li").length - 2 == pageno) {
                              return
                          } else if ($("#pagination li").length - 2 < pageno) {
                              pageno = $("#pagination li").length - 2;
                          }
                      }

                      if (url.search === "") {
                          url.search = "page=" + pageno;
                      } else {
                          let match = url.search.match(/((\?|\&)page=[0-9]+)#?$/gm);
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
                      window.location.href = url.href;
                  });
              });
              function changeActiveClass(pageno) {

                  for (li of $("#pagination li")) {
                      if ($(li).text() == pageno) {
                          $("#pagination li").removeClass("active");
                          $(li).addClass("active");
                      }
                  }
              }
              function getActivetText() {
                  for (li of $("#pagination li")) {
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