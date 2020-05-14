<%@page import="com.novice.ums.model.User"%>
</head>
<body>
  <% User sessionUser = (User) session.getAttribute("user");%>
  <div class="contaner-fluid">
    <nav class="navbar navbar-expand-lg navbar-light bg-light b_shadow">
      <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbar">
        <span class="navbar-toggler-icon"></span>
      </button>
      <a class="navbar-brand navbar-toggler b_fix" href="<%= request.getContextPath() %>">
        <img src="${pageContext.request.contextPath}/resources/logo/um-logo.png" width="30" height="30" class="d-inline-block align-top" alt="" />
        <span>User Management System</span>
      </a>
      <div class="collapse navbar-collapse flex-column" id="navbar">
        <ul class="navbar-nav nav w-100 align-items-center">
          <li class="nav-item">
            <a class="navbar-brand d-none d-lg-inline-block" href="<%= request.getContextPath() %>">
              <img src="${pageContext.request.contextPath}/resources/logo/um-logo.png" width="30" height="30" class="d-inline-block align-top" alt="" />
              User Management System
            </a>
          </li>
          <li class="nav-item flex-grow-1 my-2">
            <form class="form-inline justify-content-center">
              <input class="form-control mr-sm-2 b_navinputheight" type="search" placeholder="Search" aria-label="Search" />
              <button class="btn btn-outline-success my-2 my-sm-0 b_navinputheight" style="padding: unset; padding: 0px 8px;" type="submit">Search</button>
            </form>
          </li>
          <li class="nav-item text-right my-2 b_width_100_xs_lg dropdown" style="flex-grow: 0.7;">
            <a class="navbar-brand mr-0" style="font-size: 1rem;" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
              <span class="align-middle"><%= sessionUser.getUsername() %></span>
              <% if (sessionUser.getProfile_picture() != null) {%>
              <img src="${pageContext.request.contextPath}/resources/profile/<%= sessionUser.getProfile_picture()%>" width="30" height="30" class="d-inline-block align-top rounded-circle border border-secondary" alt="" />
              <%} else {%>
              <img src="${pageContext.request.contextPath}/resources/profile/noimage.jpg" width="30" height="30" class="d-inline-block align-top rounded-circle border border-secondary" alt="" />
              <%}%>
              <i class="fas fa-caret-down align-middle"></i>
            </a>
            <div class="dropdown-menu" aria-labelledby="navbarDropdown" style="left: unset; right: 0;">
              <a class="dropdown-item" href="<%= request.getContextPath()+"/history" %>">History</a>
              <div class="dropdown-divider"></div>
              <a class="dropdown-item" href="<%= request.getContextPath()+"/account/logout" %>">Logout</a>
            </div>
          </li>
        </ul>
        <ul class="navbar-nav nav w-100 pl-lg-5" style="font-size: 14px;">
          <li class="nav-item">
            <a href="#" class="nav-link active b_modified_navlink">Dashboard</a>
          </li>
          <li class="nav-item">
            <a href="<%= request.getContextPath()+"/profile" %>" class="nav-link b_modified_navlink">Profile</a>
          </li>
          <li class="nav-item">
            <a href="#" class="nav-link b_modified_navlink">Report</a>
          </li>
        </ul>
      </div>
    </nav>