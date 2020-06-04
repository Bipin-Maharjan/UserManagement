<%@page import="com.novice.ums.model.History"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="base/upper-head.jsp" %>
<!--add CSS for this page below-->
<%@include file="base/lower-head.jsp" %>
<Script>
    document.title = "Dashboard";
    $(document).ready(function(){
       $('#bdashboard').addClass("active"); 
    });
</Script>
  <%
      List<History> lastLogins = (List) request.getAttribute("lastLogins");
      List<History> userUsage = (List) request.getAttribute("userUsage");
  %>
<div class="p-3" style="background-color: rgb(128, 146, 153);">
  <div class="bg-white">
    <div class="container py-4">
      <u class="h3">DashBoard</u>
      <div class="row justify-content-center mt-3">
        <div class="col-6">
          <div class="bd">
            <div style="background-color: #cdcdcd;">
              <p class="h5 m-0 py-1 px-3">Login History</p>
            </div>
            <div style="border-top: 1px solid black; min-height: 312px;">
              <table class="table table-striped text-center">
                <thead>
                  <tr>
                    <th scope="col">Login Date</th>
                    <th scope="col">Login Time</th>
                    <th scope="col">IP address</th>
                  </tr>
                </thead>
                <tbody>
                  <% for (History history : lastLogins) {%>
                  <tr>
                    <td><%= history.getDate_time().split(" ")[0]%></td>
                    <td><%= history.getDate_time().split(" ")[1]%></td>
                    <td><%= history.getIp_address()%></td>
                  </tr>
                  <%}%>
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
        var label = [], data = [];
  <% for (History history : userUsage) {%>
        label.push("<%= history.getExtra_info2()%>");
        data.push(<%= history.getExtra_info()%>);
  <%}%>
        var textcolor = "rgb(231,231,231)";
        new Chart(document.getElementById("myChart"), {
            type: "bar",
            data: {
                labels: label,
                datasets: [
                    {
                        label: "Usage Graph",
                        data: data,
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
    };</script>

<%@include file="base/footer.jsp" %>
<!--add js for this page below-->
<script src="https://cdn.jsdelivr.net/npm/chart.js@2.8.0"></script>
