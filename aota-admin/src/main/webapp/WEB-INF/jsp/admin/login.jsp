<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en" ng-app="aotaApp">
<head>
    <base href="/">
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <!-- <link rel="icon" href="../../favicon.ico"> -->

    <title>Dashboard</title>
    
    <!-- Bootstrap core CSS -->
    <link href="bower_components/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="bower_components/font-awesome/css/font-awesome.min.css" rel="stylesheet">
    <!-- Custom styles for this page -->
    <link href="styles/bootstrap-reset.css" rel="stylesheet">
    <link href="styles/base.css" rel="stylesheet">
    <link href="styles/dashboard.css" rel="stylesheet">
</head>

<body>

    <nav class="navbar navbar-default" role="navigation">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#"><img alt="TCL" src="images/logo.gif">
            </a>
        </div>
        <div id="navbar" class="collapse navbar-collapse">
            <ul class="nav navbar-nav navbar-main">
                <li class="active"><a href="admin/login#/" >Dashboard</a>
                </li>
                <li><a href="admin/login#/about">Statist</a>
                </li>
                <li><a>App Management</a>
                </li>
                <li><a>Package Management</a>
                </li>
                <li><a>Recycle Bin</a>
                </li>
            </ul>
            <div class="pointer"></div>
            <ul class="nav navbar-nav navbar-right">
                <li class="user-info">
                    <div class="media">
                        <a class="media-left" href="#">
                            <img src="images/user.gif">
                        </a>
                        <div class="media-body">
                            <p class="media-heading">Welcome</p>
                            <p class="user-email">admin@tcloud.com</p>
                        </div>
                    </div>
                </li>
                <li><a id="log-out" href="#"><i class="fa fa-sign-out"></i> Log out</a>
                </li>
            </ul>
        </div>
        <!--/.nav-collapse -->
    </nav>

    <div id="main-container">
        <div id="main-content">
            <div id="main-content-header">
                <h5>Dashboard Chart</h5>
            </div>
            <div id="main-content-body" class="clearfix" ng-view>
                <section class="chart-container col-md-6">
                    <canvas id="d-h" width="560" height="214"></canvas>
                </section>
                <section class="chart-container col-md-6">
                    <canvas id="u-h" width="560" height="214"></canvas>
                </section>
                <section class="chart-container col-md-6">
                    <canvas id="d-d" width="560" height="214"></canvas>
                </section>
                <section class="chart-container col-md-6">
                    <canvas id="u-d" width="560" height="214"></canvas>
                </section>
            </div>
        </div>
    </div>

    <!--footer-->
    <footer>
        <a href="#">Go to Dashboard chart</a>
    </footer>

    <!--angular-->
    <script src="bower_components/angular/angular.js"></script>
    <script src="bower_components/angular-cookies/angular-cookies.js"></script>
    <script src="bower_components/angular-resource/angular-resource.js"></script>
    <script src="bower_components/angular-sanitize/angular-sanitize.js"></script>
    <script src="bower_components/angular-animate/angular-animate.js"></script>
    <script src="bower_components/angular-route/angular-route.js"></script>
    <!-- Bootstrap core JavaScript-->
    <script src="bower_components/jquery/jquery.min.js"></script>
    <script src="bower_components/bootstrap/js/bootstrap.min.js"></script>
    <script src="bower_components/chart-js/Chart.Core.js"></script>
    <script src="bower_components/chart-js/Chart.Line.js"></script>
    <!-- endbower -->
    <!-- endbuild -->
    <script src="scripts/app.js"></script>
    <script src="scripts/common.js"></script>
        <!-- endbuild -->
</body>

</html>
