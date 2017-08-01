<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html lang="ko" ng-app="app">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Lm-Macro</title>

    <script src="<c:url value="/assets/lib/jquery/jquery-3.1.1.js"/>"></script>
    <script src="<c:url value="/assets/lib/angular/angular.min.js"/>"></script>
    <script src="<c:url value="/assets/lib/angular/ui-bootstrap-tpls-2.5.0.min.js"/>"></script>
    <link rel="stylesheet" href="<c:url value="/assets/lib/bootstrap/bootstrap.css"/>">
    <script src="<c:url value="/assets/lib/bootstrap/bootstrap.js"/>"></script>

    <link rel="stylesheet" href="<c:url value="/assets/lib/cropper/cropper.css"/>">
    <script src="<c:url value="/assets/lib/cropper/cropper.js"/>"></script>

    <script src="<c:url value="/assets/lib/socket/sockjs.js"/>"></script>
    <script src="<c:url value="/assets/lib/socket/stomp.js"/>"></script>
    <script src="<c:url value="/assets/lib/socket/underscore-min.js"/>"></script>

    <script src="<c:url value="/assets/js/app.js"/>"></script>
    <script src="<c:url value="/assets/js/service.js"/>"></script>
    <script src="<c:url value="/assets/js/controller.js"/>"></script>

    <link rel="stylesheet" href="<c:url value="/assets/css/style.css"/>">
</head>
<body>
<div id="page">
    <div class="container-fluid navigation">
        <nav class="navbar navbar-inverse navbar-fixed-top">
            <div class="container-fluid">
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar"
                            aria-expanded="false" aria-controls="navbar">
                        <span class="sr-only">Toggle navigation</span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                    <a class="navbar-brand" href="/">Lm-Macro</a>
                </div>
            </div>
            <div id="navbar" class="navbar-collapse collapse">
                <%@ include file="navMenu.jsp" %>
            </div>
        </nav>
    </div>
    <div class="container-fluid">
        <div class="row">
            <div class="col-sm-3 col-md-2 sidebar admin-menu">
                <div class="nav-menu">
                    <%@ include file="navMenu.jsp" %>
                </div>
            </div>
            <div class="col-sm-9  col-md-9 col-sm-offset-3 col-md-offset-2" id="page-body">