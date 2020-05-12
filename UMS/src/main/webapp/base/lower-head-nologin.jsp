<!--this page is only for only for login page, sign up, forget password and guest page-->
</head>
<body>
  <div class="contaner-fluid">
    <nav class="navbar navbar-expand-lg navbar-light bg-light b_shadow">
      <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbar">
        <span class="navbar-toggler-icon"></span>
      </button>
      <a class="navbar-brand navbar-toggler b_fix" href="<%= request.getContextPath() %>">
        <img src="${pageContext.request.contextPath}/resources/logo/um-logo.png" width="30" height="30" class="d-inline-block align-top" alt="" />
        User Management System
      </a>
      <div class="collapse navbar-collapse flex-column" id="navbar">
        <ul class="navbar-nav nav w-100 align-items-center">
          <li class="nav-item">
            <a class="navbar-brand d-none d-lg-inline-block" href="<%= request.getContextPath() %>">
              <img src="${pageContext.request.contextPath}/resources/logo/um-logo.png" width="30" height="30" class="d-inline-block align-top" alt="" />
              User Management System
            </a>
          </li>
          <li class="nav-item text-right my-2 b_width_100_xs_lg" style="flex-grow: 1;">
            <a class="navbar-brand mr-0" style="font-size: 1rem;" href="#">
              <span class="align-middle btn btn-primary">About Us</span>
            </a>
          </li>
        </ul>
      </div>
    </nav>