<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../../include/header.jsp" %>

<div ng-controller="LogController" class="modal-progress">
    <h1 class="page-header">로그</h1>
    <div class="row">
        <div class="col-lg-12">
            <p class="text-right">
                <button class="btn btn-default" ng-click="clearLog()">로그 비우기
                </button>

                <button class="btn btn-primary" ng-click="toggleAutoScroll()">자동 스크롤 {{ useAutoScroll ? '끄기':'켜기'}}
                </button>
            </p>

            <div class="chattingBox" id="chattingBox">
                <ul>
                    <li ng-repeat="message in messages track by $index">
                        <p class="type-{{message.message.type }} port-{{message.message.port }}">
                            <span class="type">
                            [ TYPE : {{message.message.type }} ]
                                </span>
                            <span class="port">
                            [ PORT : {{message.message.device.port}} ]
                                </span>
                            <span class="message">
                            {{message.message.message}}
                                </span>
                        </p>
                    </li>
                </ul>
            </div>

        </div>
    </div>
</div>

<%@ include file="../../include/footer.jsp" %>