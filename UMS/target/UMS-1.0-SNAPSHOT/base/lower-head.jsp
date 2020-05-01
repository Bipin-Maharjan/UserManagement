</head>
<body>
  <div class="contaner-fluid">
    <nav class="navbar navbar-expand-lg navbar-light bg-light b_shadow">
      <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbar">
        <span class="navbar-toggler-icon"></span>
      </button>
      <a class="navbar-brand navbar-toggler b_fix" href="#">
        <img src="https://getbootstrap.com/docs/4.3/assets/brand/bootstrap-solid.svg" width="30" height="30" class="d-inline-block align-top" alt="" />
        <span>Navbar</span>
      </a>
      <div class="collapse navbar-collapse flex-column" id="navbar">
        <ul class="navbar-nav nav w-100 align-items-center">
          <li class="nav-item">
            <a class="navbar-brand d-none d-lg-inline-block" href="#">
              <img src="https://getbootstrap.com/docs/4.3/assets/brand/bootstrap-solid.svg" width="30" height="30" class="d-inline-block align-top" alt="" />
              Navbar
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
              <span class="align-middle">User</span>
              <img src="https://getbootstrap.com/docs/4.3/assets/brand/bootstrap-solid.svg" width="30" height="30" class="d-inline-block align-top rounded-circle" alt="" />
              <i class="fas fa-caret-down align-middle"></i>
            </a>
            <div class="dropdown-menu" aria-labelledby="navbarDropdown" style="left: unset; right: 0;">
              <a class="dropdown-item" href="#">History</a>
              <div class="dropdown-divider"></div>
              <a class="dropdown-item" href="#">Logout</a>
            </div>
          </li>
        </ul>
        <ul class="navbar-nav nav w-100 pl-lg-5" style="font-size: 14px;">
          <li class="nav-item">
            <a href="#" class="nav-link active b_modified_navlink">Dashboard</a>
          </li>
          <li class="nav-item">
            <a href="#" class="nav-link b_modified_navlink">Profile</a>
          </li>
          <li class="nav-item">
            <a href="#" class="nav-link b_modified_navlink">Admin</a>
          </li>
        </ul>
      </div>
    </nav>