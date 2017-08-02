<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../../include/header.jsp" %>
<div ng-controller="MonitorController" class="modal-progress">
    <h1 class="page-header">모니터링</h1>
    
    <div class="row">
        <div class="col-lg-12">
            <div class="col-lg-4" ng-repeat="device in devices">
                <h3>{{device.adbProcess.title}}</h3>
                <table class="table table-bordered">
                    <tbody>
                    <tr>
                        <th>연결시간</th>
                        <td>{{device.pcInstance.macroStartTime | date:'yyyy-MM-dd HH:mm:ss'}}</td>
                    </tr>
                    <tr>
                        <th>HP</th>
                        <td>{{device.pcInstance.hp}}</td>
                    </tr>
                    <tr>
                        <th>MP</th>
                        <td>{{device.pcInstance.mp}}</td>
                    </tr>
                    <tr>
                        <th>사망횟수</th>
                        <td>{{device.pcInstance.dieRestart.dieCount}}</td>
                    </tr>
                    <tr>
                        <th>소지품</th>
                        <td>
                            <ul class="list-group">
                                <li ng-repeat="(key,value) in device.pcInstance.useItems" class="list-group-item">
                                    {{key}} : {{value}}
                                </li>
                            </ul>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>

<%@ include file="../../include/footer.jsp" %>