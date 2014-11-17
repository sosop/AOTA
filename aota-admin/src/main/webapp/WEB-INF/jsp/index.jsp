<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en" ng-app="aotaApp">
<head>
    <%
        String contextPath = request.getContextPath();
    %>
    <base href="/">
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <!-- <link rel="icon" href="../../favicon.ico"> -->

    <title>{{ title }}</title>
    
    <!-- Bootstrap core CSS -->
    <link href="<%=contextPath%>/bower_components/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="<%=contextPath%>/bower_components/font-awesome/css/font-awesome.min.css" rel="stylesheet">
    <!-- Custom styles for this page -->
    <link href="<%=contextPath%>/styles/bootstrap-reset.min.css" rel="stylesheet">
    <link href="<%=contextPath%>/styles/base.min.css" rel="stylesheet">
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
            <a class="navbar-brand" href="#"><img alt="TCL" src="<%=contextPath%>/images/logo.gif">
            </a>
        </div>
        <div id="navbar" class="collapse navbar-collapse">
            <ul class="nav navbar-nav navbar-main">
                <li class="active dashboard"><a href="<%=contextPath%>/admin/#/dashboard" >Dashboard</a>
                </li>
                <li class="statist"><a href="<%=contextPath%>/admin/#/statist">Statist</a>
                </li>
                <li class="app_management"><a href="<%=contextPath%>/admin/#/app_manage">App Management</a>
                </li>
                <li class="package_management"><a href="<%=contextPath%>/admin/#/pkg_manage">Package Management</a>
                </li>
                <li class="recycle_bin"><a href="<%=contextPath%>/admin/#/recycle_bin">Recycle Bin</a>
                </li>
            </ul>
            <div class="pointer"></div>
            <ul class="nav navbar-nav navbar-right">
                <li class="user-info">
                    <div class="media">
                        <a class="media-left">
                            <img src="<%=contextPath%>/images/user.gif">
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
            <header class="main-content-header">
                <h5>{{ header }}</h5>
            </header>
            <div id="main-content-body">
            	<div class="route-view clearfix" ng-view>
            		<!-- route view -->
            	</div>
            </div>
        </div>
    </div>

    <!--footer-->
    <footer>
        <a href="<%=contextPath%>/admin/#/dashboard">Go to Dashboard chart</a>
    </footer>
    <!-- Bootstrap core JavaScript-->
    <script src="<%=contextPath%>/bower_components/jquery/jquery.min.js"></script>
    <script src="<%=contextPath%>/bower_components/bootstrap/js/bootstrap.min.js"></script>
    <script src="<%=contextPath%>/scripts/common.min.js"></script>

  	<!--angular-->
    <script src="<%=contextPath%>/bower_components/angular/angular.min.js"></script>
    <script src="<%=contextPath%>/bower_components/angular-resource/angular-resource.min.js"></script>
    <script src="<%=contextPath%>/bower_components/angular-route/angular-route.min.js"></script>

    <!-- endbower -->
	    <!-- endbuild -->
	    <script src="<%=contextPath%>/scripts/app.min.js"></script>
	    <script src="<%=contextPath%>/scripts/controllers.min.js"></script>
	    <script src="<%=contextPath%>/scripts/directives.min.js"></script>
	    <script src="<%=contextPath%>/scripts/services.min.js"></script>
	    <script src="<%=contextPath%>/scripts/filters.min.js"></script>
	    <!-- endbuild -->
</body>

</html>
