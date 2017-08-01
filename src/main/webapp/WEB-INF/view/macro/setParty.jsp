<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../../include/header.jsp" %>

<div ng-controller="PartyController" class="modal-progress">
    <h1 class="page-header">파티 설정</h1>
    <div class="row">
        <div class="col-lg-3">
            <h5>연결된 디바이스 목록</h5>
            <ul class="list-group">
                <li ng-repeat="device in connectedDeviceList" class="list-group-item" style="overflow: hidden"
                    ng-class="{'active':currentDevice == device}">
                    <p>[{{device.adbProcess.emulatorName}}] {{device.adbProcess.title }}</p>
                    <button type="button" class="btn btn-default btn-xs" ng-click="settingParty(device)">파티설정
                    </button>
                </li>
            </ul>
        </div>
        <div class="col-lg-9">
            <h4>파티에 추가할 디바이스 목록</h4>
            <ul class="list-group">
                <li ng-repeat="partyDevice in partyDeviceList" class="list-group-item">
                    {{partyDevice.adbProcess.title}}
                    <button class="btn btn-default btn-xs" ng-click="addParty(partyDevice)">추가</button>
                </li>
            </ul>
        </div>
    </div>
</div>

<%@ include file="../../include/footer.jsp" %>
