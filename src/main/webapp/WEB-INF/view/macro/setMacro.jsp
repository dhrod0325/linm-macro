<%@ page import="lm.macro.auto.data.model.setting.LmHuntSetting" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../../include/header.jsp" %>
<div ng-controller="MacroController" class="modal-progress">
    <h1 class="page-header">매크로설정</h1>
    <div class="row">
        <div class="col-lg-3">
            <h5>미연결 디바이스목록
                <button class="btn btn-primary btn-xs" ng-click="getAdbProcessList()">새로고침</button>
            </h5>
            <ul class="list-group">
                <li ng-repeat="device in processList" class="list-group-item" style="overflow: hidden">
                    <span>[{{device.emulatorName}}] {{device.title}}</span>
                    <button type="button" class="btn btn-default" style="float: right;"
                            ng-click="connectDevice(device)">연결
                    </button>
                </li>
            </ul>

            <hr>

            <h5>연결된 디바이스 목록
                <button class="btn btn-primary btn-xs" ng-click="refreshConnectDeviceList()">새로고침</button>
            </h5>
            <ul class="list-group">
                <li ng-repeat="device in connectedDeviceList" class="list-group-item" style="overflow: hidden"
                    ng-class="{'active':currentDevice == device}">
                    <p>[{{device.adbProcess.emulatorName}}] {{device.adbProcess.title }}</p>
                    <button type="button" class="btn btn-default btn-xs" ng-click="settingDevice(device)">설정
                    </button>
                    <button type="button" class="btn btn-default btn-xs" ng-click="disConnectDevice(device)">연결끊기
                    </button>
                </li>
            </ul>
        </div>

        <div class="col-lg-9">
            <div ng-show="currentDevice">
                <h4>[{{currentDevice.adbProcess.emulatorName}}] {{currentDevice.adbProcess.title }} 설정 .. PORT :
                    {{currentDevice.adbProcess.hostPort}}</h4>

                <hr>

                <p class="text-right">
                    <span style="float: left">매크로 동작상태 : {{currentDevice.pcInstance.state}}</span>
                    <button class="btn btn-default"
                            ng-class="{'btn-primary':currentDevice.pcInstance.state!='STOP','btn-default':currentDevice.pcInstance.state=='STOP' }"
                            ng-click="startMacro()">
                        매크로 시작
                    </button>
                    <button class="btn btn-default"
                            ng-class="{'btn-primary':currentDevice.pcInstance.state=='STOP','btn-default':currentDevice.pcInstance.state!='STOP' }"
                            ng-click="stopMacro()">
                        매크로 중지
                    </button>
                </p>

                <ul class="nav nav-tabs">
                    <li ng-class="{active:selectedTab == 'pattern'}" ng-click="selectTab('pattern')"><a
                            href="#">패턴설정</a>
                    </li>
                    <li ng-class="{active:selectedTab == 'hunt'}" ng-click="selectTab('hunt')"><a href="#">사냥설정</a></li>
                    <li ng-class="{active:selectedTab == 'shop'}" ng-click="selectTab('shop')"><a href="#">구매설정</a></li>
                    <li ng-class="{active:selectedTab == 'delItem'}" ng-click="selectTab('delItem')"><a
                            href="#">삭제설정</a>
                    </li>
                    <li ng-class="{active:selectedTab == 'warehouse'}" ng-click="selectTab('warehouse')"><a href="#">창고설정</a>
                    <li ng-class="{active:selectedTab == 'etc'}" ng-click="selectTab('etc')"><a href="#">기타설정</a>
                    </li>
                </ul>

                <div ng-show="selectedTab == 'pattern'">
                    <div class="row">
                        <div class="col-lg-6">
                            <h5>설정</h5>
                            <form class="form-horizontal" ng-submit="addAutoPattern($event)">
                                <div class="form-group">
                                    <div class="col-lg-12">
                                        <p>명칭은 아무것이나 해도 되지만 목록중 고유해야 합니다.</p>
                                        <input type="text" ng-model="autoPattern.name" class="form-control"/>
                                    </div>
                                </div>

                                <h6>HP 설정</h6>

                                <div class="form-group">
                                    <div class="col-lg-12">
                                        <select ng-model="autoPattern.type1" class="form-control"
                                                ng-init="autoPattern.type1 = 'HP'">
                                            <option value="HP">HP</option>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <div class="col-lg-6">
                                        <label class="control-label">%이상</label>
                                        <input type="text" ng-model="autoPattern.above1" placeholder="% 이상"
                                               class="form-control">
                                    </div>
                                    <div class="col-lg-6">
                                        <label class="control-label">%이하</label>
                                        <input type="text" ng-model="autoPattern.below1" placeholder="% 이하"
                                               class="form-control">
                                    </div>
                                </div>

                                <hr>

                                <h6>MP 설정</h6>
                                <div class="form-group">
                                    <div class="col-lg-12">
                                        <select ng-model="autoPattern.type2" class="form-control"
                                                ng-init="autoPattern.type2 = 'MP'">
                                            <option value="MP">MP</option>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <div class="col-lg-6">
                                        <label class="control-label">%이상</label>
                                        <input type="text" ng-model="autoPattern.above2" placeholder="% 이상"
                                               class="form-control">
                                    </div>
                                    <div class="col-lg-6">
                                        <label class="control-label">%이하</label>
                                        <input type="text" ng-model="autoPattern.below2" placeholder="% 이하"
                                               class="form-control">
                                    </div>
                                </div>

                                <hr>

                                <div class="form-group">
                                    <div class="col-lg-12">
                                        <p>스킬을 사용하고 난 후 매크로 대기시간 (초단위로 입력)</p>
                                        <input type="text" ng-model="autoPattern.delay" placeholder="초 단위로 입력"
                                               class="form-control"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <div class="col-lg-12">
                                        <p class=" text-left">
                                            {{autoPattern.type1}}가 {{autoPattern.above1}}% 이상이고 {{autoPattern.below1}}%
                                            이하 이면서
                                            {{autoPattern.type2}}가 {{autoPattern.above2}}% 이상이고 {{autoPattern.below2}}%
                                            이하 이면
                                        </p>
                                        <select ng-model="autoPattern.motion" class="form-control">
                                            <c:forEach items="${motionNames}" var="name">
                                                <option value="${name}">${name}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>

                                <input type="submit" class="btn btn-default" value="저장">
                                <input type="button" class="btn btn-default" value="취소"
                                       ng-click="cancelEditPattern($event)">
                            </form>
                        </div>
                        <div class="col-lg-6">
                            <h5>패턴 목록</h5>
                            <ul class="list-group">
                                <li ng-repeat="pattern in autoPatternList"
                                    class="list-group-item">
                                    <h6>{{pattern.name}}</h6>
                                    <p>
                                        {{pattern.type1}}가 {{pattern.above1}}% 이상이고 {{pattern.below1}}%
                                        이하 이면서 <br>
                                        {{pattern.type2}}가 {{pattern.above2}}% 이상이고 {{pattern.below2}}%
                                        이하 이면
                                        {{pattern.motion}}</p>
                                    <button class="btn btn-xs "
                                            ng-class="{'btn-primary':pattern.start,'btn-default':!pattern.start }"
                                            ng-click="patternControl(pattern,'start')">
                                        시작
                                    </button>
                                    <button class="btn btn-xs"
                                            ng-class="{'btn-primary':!pattern.start,'btn-default':pattern.start }"
                                            ng-click="patternControl(pattern,'stop')">
                                        정지
                                    </button>

                                    <button class="btn btn-xs btn-default" ng-click="removePattern(pattern)"
                                            style="float: right">
                                        삭제
                                    </button>
                                    <button class="btn btn-xs btn-default" ng-click="editPattern(pattern)"
                                            style="float: right;margin-right:5px;">
                                        수정
                                    </button>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>

                <div ng-show="selectedTab == 'hunt'">
                    <h5>사냥터 설정</h5>

                    <div class="text-right" style="margin-bottom:10px;">
                        <button class="btn btn-primary" ng-click="saveHunt();">저장</button>
                    </div>

                    <table class="table table-bordered">
                        <tr>
                            <th><label for="huntSetting.damagedMotion">유저에게 공격당했을때 처리할 행동</label></th>
                            <td>
                                <select ng-model="huntSetting.damagedMotion" id="huntSetting.damagedMotion"
                                        class="form-control">
                                    <option value="">아무행동안함</option>
                                    <option value="<%= LmHuntSetting.DAMAGED_MOTION_TELEPORT%>">순간이동사용(슬롯7에 순간이어야함)
                                    </option>
                                    <option value="<%= LmHuntSetting.DAMAGED_MOTION_HOME%>">귀환주문서사용</option>

                                    <option value="<%= LmHuntSetting.DAMAGED_MOTION_ATTACK%>">반격</option>
                                </select>
                            </td>
                        </tr>
                        <tr ng-show="huntSetting.damagedMotion == '<%=LmHuntSetting.DAMAGED_MOTION_ATTACK%>'">
                            <th>
                                반격행동패턴
                            </th>
                            <td>

                            </td>
                        </tr>
                        <tr>
                            <td>

                                <p>
                                    <label for="huntSetting.useSelfCheck">
                                        <input type="checkbox" ng-model="huntSetting.useSelfCheck"
                                               id="huntSetting.useSelfCheck">
                                        셀프 자동 켜기 사용
                                    </label>
                                </p>
                                <p ng-show="huntSetting.useSelfCheck">
                                    <label for="huntSetting.selfCheckTime">
                                        감지시간(초 단위로 입력하세요.)
                                        <input type="number" ng-model="huntSetting.selfCheckTime"
                                               id="huntSetting.selfCheckTime">
                                    </label>
                                </p>
                            </td>
                            <td>
                                <p>
                                    <label for="huntSetting.useAutoCheck">
                                        <input type="checkbox" ng-model="huntSetting.useAutoCheck"
                                               id="huntSetting.useAutoCheck">
                                        오토 자동 켜기 사용
                                    </label>
                                </p>
                                <p ng-show="huntSetting.useAutoCheck">
                                    <label for="huntSetting.autoCheckTime">
                                        감지시간(초 단위로 입력하세요.)
                                        <input type="number" ng-model="huntSetting.autoCheckTime"
                                               id="huntSetting.autoCheckTime">
                                    </label>
                                </p>
                            </td>
                        </tr>
                    </table>

                    <table class="table table-bordered">
                        <tr>
                            <td>
                                <input type="checkbox" ng-model="huntSetting.useHuntTeleport"
                                       id="huntSetting.useHuntTeleport">
                                <label for="huntSetting.useHuntTeleport">사냥터 이동기능 사용</label>
                            </td>
                            <td><input type="checkbox" ng-model="huntSetting.homeInStopMacro"
                                       id="huntSetting.homeInStopMacro">
                                <label for="huntSetting.homeInStopMacro">귀환시 매크로 정지</label></td>
                        </tr>
                        <tr ng-show="huntSetting.useHuntTeleport">
                            <th>사냥터 사용할 곳</th>

                            <td>
                                <p class="text-right">
                                    <button class="btn btn-default" ng-click="addHuntMap();">사냥터 추가</button>
                                </p>
                                <p class="text-right">
                                    사냥터를 여러개 추가하시면 귀환후 렌덤으로 사냥터 복귀합니다.
                                </p>
                                <div ng-repeat="huntMap in huntSetting.huntMapList" class="form-horizontal">
                                    <p class="text-right">
                                        <button class="btn btn-warning" ng-click="deleteHuntMap(huntMap)">사냥터 삭제
                                        </button>
                                        <button class="btn btn-default" ng-click="addJoystick(huntMap)">조이스틱 추가</button>
                                    </p>

                                    <div class="form-group">
                                        <label for="hunt-{{huntMap.id}}" class="col-sm-4 control-label">사냥터</label>
                                        <div class="col-sm-8">
                                            <select ng-model="huntMap.name" id="hunt-{{huntMap.id}}"
                                                    class="form-control">
                                                <c:forEach items="${teleportGraphics}" var="teleport">
                                                    <option value="${teleport.name}">${teleport.name}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <input type="checkbox" ng-model="huntMap.noDungeon"
                                               id="hunt-{{huntMap.id}}-nodungeon"/>
                                        <label for="hunt-{{huntMap.id}}-nodungeon"
                                               class="col-sm-4 control-label">던전입장안함</label>
                                    </div>

                                    <div ng-repeat="joystick in huntMap.huntJoysticks">
                                        <div class="form-group">
                                            <label for="hunt-{{huntMap.id}}-joystickLoc" class="col-sm-2 control-label">위치</label>
                                            <div class="col-sm-2">
                                                <select ng-model="joystick.joystickLoc" class="form-control"
                                                        id="hunt-{{huntMap.id}}-joystickLoc">
                                                    <c:forEach items="${joystickLocations}" var="item">
                                                        <option value="${item}">${item}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                            <div class="col-sm-5">
                                                <input type="number" ng-model="joystick.joystickDelay"
                                                       class="form-control" placeholder="초"/>
                                            </div>
                                            <div class="col-sm-3">
                                                <button class="btn btn-warning"
                                                        ng-click="removeJoystick(huntMap,joystick)">
                                                    삭제
                                                </button>
                                            </div>
                                        </div>
                                    </div>

                                    <hr/>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td><input type="checkbox" ng-model="huntSetting.huntStartInMap"
                                       id="huntSetting.huntStartInMap">
                                <label for="huntSetting.huntStartInMap">사냥터에서 매크로시작</label>
                            </td>
                            <td>
                                <label for="huntSetting.poisonUseSlot">독에 걸렸을때 사용할 스킬</label>
                                <select ng-model="huntSetting.poisonUseSlot" class="form-control"
                                        id="huntSetting.poisonUseSlot">
                                    <option value="">사용안함</option>
                                    <c:forEach items="${motionNames}" var="name">
                                        <option value="${name}">${name}</option>
                                    </c:forEach>
                                </select>
                            </td>
                        </tr>
                    </table>

                    <h5>사냥터 귀환 옵션</h5>

                    <table class="table table-bordered" style="margin-top:10px;">
                        <tr>
                            <td>
                                <input type="checkbox"
                                       ng-model="huntSetting.redPotionEmptyGoHome"
                                       id="huntSetting.redPotionEmptyGoHome"/>
                                <label for="huntSetting.redPotionEmptyGoHome">빨간물약이 떨어지면 자동귀환</label>

                                <div ng-show="huntSetting.redPotionEmptyGoHome">
                                    <input type="checkbox"
                                           ng-model="huntSetting.redPotionEmptyStopMacro"
                                           id="huntSetting.redPotionEmptyStopMacro"/>
                                    <label for="huntSetting.redPotionEmptyStopMacro">빨간물약이 떨어지면 자동귀환 후 매크로 정지</label>
                                </div>
                            </td>
                            <td>

                            </td>
                        </tr>
                        <tr>
                            <td>
                                <p>
                                    <label for="huntSetting.hpGoHome">
                                        <input type="checkbox" ng-model="huntSetting.hpGoHome"
                                               id="huntSetting.hpGoHome"/> 체력상태 체크 귀환 사용
                                    </label>
                                </p>
                                <p ng-show="huntSetting.hpGoHome">
                                    <label for="huntSetting.hpGoHomeValue">
                                        체력이 <input type="text" ng-model="huntSetting.hpGoHomeValue"
                                                   id="huntSetting.hpGoHomeValue"/>% 이하로 떨어지면 자동귀환
                                    </label>
                                </p>
                            </td>
                            <td>
                                <p>
                                    <label for="huntSetting.arrowGoHome">
                                        <input type="checkbox" ng-model="huntSetting.arrowGoHome"
                                               id="huntSetting.arrowGoHome"/> 화살 체크 귀환 사용
                                    </label>
                                </p>
                            </td>
                        </tr>
                    </table>
                    <div class="text-right">
                        <button class="btn btn-primary" ng-click="saveHunt();">저장</button>
                    </div>
                </div>

                <div ng-show="selectedTab == 'shop'">
                    <h5>상점 구매 설정</h5>

                    <p>
                        <label for="huntSetting.useShopping">
                            <input type="checkbox" ng-model="huntSetting.useShopping"
                                   id="huntSetting.useShopping"/> 상점 구매기능 이용여부
                        </label>
                    </p>
                    <%--<p ng-show="!huntSetting.useShopping">사냥설정에서 상점구매기능을 켜고 저장버튼을 누르세요</p>--%>

                    <div ng-show="huntSetting.useShopping">
                        <p class="text-right">
                            구입하고 싶으신 수량만큼 입력하세요.
                            <button class="btn btn-default" ng-click="saveBuyItems()">저장</button>
                        </p>

                        <table class="table table-bordered">
                            <thead>
                            <tr>
                                <th>아이템명</th>
                                <th>수량</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${shopGraphics}" var="item">
                                <tr>
                                    <th>${item.name}</th>
                                    <td>
                                        <input type="text" class="form-control" ng-model="buyItems['${item.name}']"/>

                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                        <p class="text-right">
                            <button class="btn btn-default" ng-click="saveBuyItems()">저장</button>
                        </p>
                    </div>
                </div>

                <div ng-show="selectedTab == 'delItem'">
                    <h5>아이템 삭제 설정</h5>

                    <p class="text-right">
                        <button class="btn btn-default" ng-click="saveDeleteItems()">저장</button>
                    </p>

                    <table class="table table-bordered">
                        <thead>
                        <tr>
                            <th>아이템명</th>
                            <th>수량</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${deleteGraphics}" var="item">
                            <tr>
                                <th>${item.name}</th>
                                <td>
                                    <input type="text" class="form-control" ng-model="deleteItems['${item.name}']"/>

                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                    <p class="text-right">
                        <button class="btn btn-default" ng-click="saveDeleteItems()">저장</button>
                    </p>
                </div>

                <div ng-show="selectedTab == 'etc'">
                    <h5>기타 설정</h5>

                    <table class="table-bordered table">
                        <tr>
                            <td>
                                <div class="col-lg-12">
                                    <label for="huntSetting.useCheckLetter">
                                        <input type="checkbox" ng-model="huntSetting.useCheckLetter"
                                               id="huntSetting.useCheckLetter"/>
                                        30분마다 우편함 체크
                                    </label>
                                </div>
                            </td>
                        </tr>
                    </table>

                    <div class="text-right">
                        <button class="btn btn-primary" ng-click="saveHunt();">저장</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<%@ include file="../../include/footer.jsp" %>