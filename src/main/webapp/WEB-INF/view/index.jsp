<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../include/header.jsp" %>

<h1 class="page-header">매크로 정보</h1>

<div class="row">
    <div class="col-lg-4">
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
                <th>남은사용기간</th>
                <td></td>
            </tr>
        </table>
    </div>
    <div class="col-lg-4">
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