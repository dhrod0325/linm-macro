<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../../include/header.jsp" %>
<style>
    #editorImageContainer {
        width: 800px;
        height: 480px;
        border: 1px solid #dedede;
    }

    #editorImageContainer img {
        width: 100%;
        height: 100%;
    }
</style>
<div ng-controller="EditorController" class="modal-progress">
    <h1 class="page-header">에디터</h1>
    <div class="row">
        <div class="col-lg-3">
            <h5>연결된 디바이스 목록
                <button class="btn btn-primary btn-xs" ng-click="refreshConnectDeviceList()">새로고침</button>
            </h5>
            <ul class="list-group">
                <li ng-repeat="device in connectedDeviceList" class="list-group-item" style="overflow: hidden"
                    ng-class="{'active':currentDevice == device}">
                    <p>[{{device.adbProcess.emulatorName}}] {{device.adbProcess.title }}</p>
                    <button type="button" class="btn btn-default btn-xs" ng-click="getScreenDevice(device)">화면가저오기
                    </button>
                </li>
            </ul>
        </div>
        <div class="col-lg-9">
            <h5>스크린 에디터</h5>
            <p style="overflow:hidden;">[CROP BOX INFO ] X : {{cropBoxData.left}} , Y : {{cropBoxData.top}} , WIDTH :
                {{cropBoxData.width}}
                HEIGHT : {{cropBoxData.height}}
                <button style="float: right;" class="btn btn-primary" ng-click="getCropImage()">이미지 가저오기</button>
            </p>
            <div id="editorImageContainer">
                <img id="editorImage">
            </div>
        </div>
    </div>
</div>
<%@ include file="../../include/footer.jsp" %>