<%@ page import="lm.macro.auto.common.LmCommon" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>LmMacro 로그인</title>

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
<style>
    #loginForm {
        width: 300px;
        position: absolute;
        top: 30%;
        left: 50%;
        margin-left: -150px;
    }

    #loginForm h3 {
        font-size: 30px;
        font-weight: bold;

    }
</style>
<form:form commandName="user" action="/loginProcess">
    <div id="loginForm">
        <h3>Lm-Macro Login</h3>
        <hr>
        <table class="table table-bordered">
            <colgroup>
                <col style="width:80px;">
            </colgroup>
            <tr>
                <th>아이디</th>
                <td>
                    <form:input path="id" cssClass="form-control"/>
                    <form:errors path="id"/>
                </td>
            </tr>
            <tr>
                <th>비밀번호</th>
                <td>
                    <form:password path="pw" cssClass="form-control"/>
                    <form:errors path="pw"/>
                </td>
            </tr>
        </table>

        <p>
            <input type="checkbox" name="remember-me" id="remember-me" value="1"/>
            <label for="remember-me">아이디 저장</label>

            <a href="<%=LmCommon.WEB_SERVER_URL%>/bbs/register.php" target="_blank"
               style="float: right;display: inline-block">회원가입</a>
        </p>

        <div class="text-right">
            <input type="submit" class="btn btn-default" value="로그인"/>
        </div>
    </div>
</form:form>

</body>
</html>