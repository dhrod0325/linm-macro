<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ include file="../include/header.jsp" %>

<h1 class="page-header">매크로 정보</h1>

<div class="row">
    <div class="col-lg-6">
        <h3>사용자 정보</h3>
        <table class="table table-bordered">
            <tr>
                <th>사용자 ID</th>
                <td>${user.id}</td>
            </tr>
            <tr>
                <th>사용자명</th>
                <td>${user.name}</td>
            </tr>
            <tr>
                <th>정보</th>
                <td>
                    <ul class="list-group">
                        <c:forEach items="${user.macroUseAbleDataList}" var="item">
                            <li class="list-group-item">
                                <fmt:formatDate pattern="yyyy-MM-dd" value="${item.endDate}"/>일 까지, 실행가능수
                                : ${item.macroCount}
                            </li>
                        </c:forEach>
                        <li class="list-group-item">현재 실행 가능 매크로 수 : ${user.totalUseAbleCount}</li>
                    </ul>

                </td>
            </tr>
        </table>
    </div>
    <div class="col-lg-6">
        <h3>공지사항</h3>

        <table class="table table-bordered">
            <tr>
                <th>제목</th>
                <th>내용</th>
            </tr>
            <c:forEach items="${noticeList}" var="notice">
                <tr>
                    <td>${notice.wr_subject}</td>
                    <td>
                        <a href="${notice.href}" target="_blank">
                                ${notice.contentShort}
                        </a>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </div>
</div>

<%@ include file="../include/footer.jsp" %>