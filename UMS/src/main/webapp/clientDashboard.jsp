<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="base/upper-head.jsp" %>
<!--add CSS for this page below-->
<%@include file="base/lower-head.jsp" %>
<Script>
    document.title = "Dashboard";
</Script>

<div class="p-3" style="background-color: rgb(128, 146, 153);">
  <div class="bg-white">
    <div class="container py-4">
      <u class="h3">DashBoard</u>
      <div class="row justify-content-center mt-3">
        <div class="col-6">
          <div class="bd">
            <div style="background-color: #cdcdcd;">
              <p class="h5 m-0 py-1 px-3">Last Visitor</p>
            </div>
            <div style="border-top: 1px solid black; min-height: 312px;">
              <table class="table table-striped text-center">
                <thead>
                  <tr>
                    <th scope="col">Last Visit</th>
                    <th scope="col">Time Spent</th>
                    <th scope="col">IP address</th>
                  </tr>
                </thead>
                <tbody>
                  <tr>
                    <td>Mark</td>
                    <td>Otto</td>
                    <td>@mdo</td>
                  </tr>
                  <tr>
                    <td>Jacob</td>
                    <td>Thornton</td>
                    <td>@fat</td>
                  </tr>
                  <tr>
                    <td>Larry</td>
                    <td>the Bird</td>
                    <td>@twitter</td>
                  </tr>
                  <tr>
                    <td>Mark</td>
                    <td>Otto</td>
                    <td>@mdo</td>
                  </tr>
                  <tr>
                    <td>Mark</td>
                    <td>Otto</td>
                    <td>@mdo</td>
                  </tr>
                </tbody>
              </table>
            </div>
          </div>
        </div>
      </div>
      <div class="row justify-content-center mt-4">
        <div class="col-6">
          <canvas id="myChart" style="background-color: #32373a; height: 300px; width: 100%;"></canvas>
        </div>
      </div>
    </div>
  </div>
</div>

<script>
  window.onload = function () {
    var textcolor = "rgb(231,231,231)";
    new Chart(document.getElementById("myChart"), {
      type: "bar",
      data: {
        labels: ["Sunday", "Monday", "Tuesday", "Wednesday", "Thrusday", "Friday", "Saturday"],
        datasets: [
          {
            label: "Usage Graph",
            data: [65, 59, 80, 81, 56, 55, 20],
            backgroundColor: ["rgb(109,120,173)", "rgb(81,205,160)", "rgb(223,121,112)", "rgb(76,156,160)", "rgb(174,125,153)", "rgb(201,212,92)", "rgb(85,146,173)"],
            fill: false,
            borderWidth: 0,
          },
        ],
      },
      options: {
        title: {
          display: true,
          text: "Usage Graph",
          fontColor: textcolor,
          fontSize: 18,
        },
        legend: {
          display: false,
        },
        tooltips: {
          enabled: true,
        },
        scales: {
          xAxes: [
            {
              ticks: {
                fontColor: textcolor,
              },
              gridLines: {
                display: false,
              },
            },
          ],
          yAxes: [
            {
              gridLines: {
                color: "rgb(133,133,133)",
              },
              ticks: {
                fontColor: textcolor,
                beginAtZero: true,
              },
              scaleLabel: {
                display: true,
                labelString: "Hours",
                fontColor: textcolor,
                fontSize: 16,
              },
            },
          ],
        },
        layout: {
          padding: {
            left: 0,
            right: 0,
            top: 0,
            bottom: 0,
          },
        },
      },
    });
  };
</script>

<%@include file="base/footer.jsp" %>
<!--add js for this page below-->
<script src="https://cdn.jsdelivr.net/npm/chart.js@2.8.0"></script>
