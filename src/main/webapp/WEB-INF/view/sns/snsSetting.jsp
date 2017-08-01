<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../../include/header.jsp" %>
<div ng-controller="SnsController" class="modal-progress">
    <h1 class="page-header">SNS설정</h1>
    <div class="row">
        <div class="col-lg-12">
            <table class="table table-bordered">
                <thead>
                <tr>
                    <th>SNS 타입</th>
                    <th>토큰값</th>
                    <th>상태</th>
                    <th>관리</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td>카카오톡</td>
                    <td>
                        ${kakaoToken.accessToken}
                    </td>
                    <td>
                        <c:if test="${!kakaoToken.expired}">
                            유효함
                        </c:if>
                        <c:if test="${kakaoToken.expired}">
                            유효하지않음
                        </c:if>
                    </td>
                    <td>
                        <c:if test="${empty kakaoToken.accessToken}">
                            <a href="${kakaoAuthorizationUrl}">연결</a>
                        </c:if>
                        <c:if test="${!empty kakaoToken.accessToken}">
                            <a href="${kakaoAuthorizationUrl}">재연결</a>
                        </c:if>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>
</div>

<%@ include file="../../include/footer.jsp" %>