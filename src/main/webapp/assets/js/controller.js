controllers.controller('MacroController', function ($scope, $http, $uibModal, $interval, SocketService, ProgressService, DeviceService) {
    $scope.selectedTab = 'pattern';
    $scope.autoPattern = {};
    $scope.huntSetting = defaultHuntSetting();

    function defaultHuntSetting() {
        return {
            huntMapList: []
        };
    }

    $scope.selectTab = function (tab) {
        $scope.selectedTab = tab;
    };

    $scope.getAdbProcessList = function () {
        DeviceService.getAdbProcessList(function (response) {
            $scope.processList = response;
            $scope.getConnectedDeviceList();
        });
    };

    $scope.getAdbProcessList();

    $scope.connectDevice = function (process) {
        DeviceService.connectDevice(process, function (response) {
            $scope.connectedDeviceList = response;
            $scope.processList = _.without($scope.processList, _.findWhere($scope.processList, process));
        });
    };

    $scope.disConnectDevice = function (device) {

        DeviceService.disConnectDevice(device.device.port, function (response) {
            $scope.connectedDeviceList = response;

            if ($scope.currentDevice === device) {
                $scope.currentDevice = null;
                $scope.autoPatternList = null;
            }

            $scope.getAdbProcessList();
        });
    };

    $scope.getConnectedDeviceList = function () {
        DeviceService.getConnectedDeviceList(function (response) {
            $scope.connectedDeviceList = response;
            $scope.currentDevice = null;
            $scope.autoPatternList = null;
        });
    };

    $scope.refreshConnectDeviceList = function () {
        DeviceService.getConnectedDeviceList(function (response) {
            $scope.connectedDeviceList = response;
            $scope.currentDevice = null;
            $scope.autoPatternList = null;
        });
    };

    $scope.findAutoPatternListByDevicePort = function (port) {
        $http.post('/autoPattern/findByDevicePort', {
            devicePort: port
        }).success(function (response) {
            $scope.autoPatternList = response;
        });
    };

    $scope.settingDevice = function (device) {
        $scope.currentDevice = device;
        $scope.huntSetting = $scope.currentDevice.pcInstance.huntSetting || defaultHuntSetting();
        $scope.buyItems = createBuyItems($scope.currentDevice.pcInstance.buyItems);

        $scope.findAutoPatternListByDevicePort(device.device.port);
    };

    function createBuyItems(list) {
        var result = {};

        _.each(list, function (item, i) {
            result[item.name] = item.buyCount;
        });

        return result;
    }

    $scope.addAutoPattern = function (e) {
        var data = _.extend($scope.autoPattern, {
            devicePort: $scope.currentDevice.device.port
        });

        $http.post('/autoPattern/save', data).success(function (response) {
            $scope.findAutoPatternListByDevicePort(data.devicePort);
        });

        $scope.autoPattern = {};
        e.currentTarget.reset();
    };

    $scope.removePattern = function (pattern) {
        $http.post('/autoPattern/remove', {id: pattern.id}).success(function (response) {
            $scope.findAutoPatternListByDevicePort(pattern.devicePort);
            $scope.autoPattern = {};
        });
    };

    $scope.editPattern = function (pattern) {
        $scope.autoPattern = pattern;
    };

    $scope.cancelEditPattern = function (e) {
        $(e.currentTarget).parent('form')[0].reset();
        $scope.autoPattern = {};
    };


    $scope.startMacro = function () {
        $http.post('/macro/startMacro', {
            port: $scope.currentDevice.device.port
        }).success(function (response) {
            console.log($scope.currentDevice);
            $scope.currentDevice.pcInstance.state = 'PLAY';
        });
    };

    $scope.stopMacro = function () {
        $http.post('/macro/stopMacro', {
            port: $scope.currentDevice.device.port
        }).success(function (response) {
            $scope.currentDevice.pcInstance.state = 'STOP';
        });
    };

    $scope.patternControl = function (pattern, type) {
        if (type === 'stop') {
            $http.post('/autoPattern/stopAutoPattern', {
                id: pattern.id
            }).success(function () {
                pattern.start = false;
            });
        } else if (type === 'start') {
            $http.post('/autoPattern/startAutoPattern', {
                id: pattern.id
            }).success(function () {
                pattern.start = true;
            });
        }
    };

    $scope.saveHunt = function (callback) {
        var data = _.extend({
            deviceSerial: $scope.currentDevice.device.serial,
        }, $scope.huntSetting);

        data.huntMapList = JSON.stringify(data.huntMapList);

        $http.post('/hunt/save', data).success(function () {
            alert('설정저장완료');

            if (callback) {
                callback();
            }
        });
    };

    $scope.saveBuyItems = function () {
        $scope.saveHunt(function () {
            var data = _.extend($scope.buyItems, {
                'deviceSerial': $scope.currentDevice.device.serial
            });

            $http.post('/buyItem/save', data).success(function (response) {
                alert('설정 저장 완료');
            });
        });
    };

    $scope.saveDeleteItems = function () {
        var data = _.extend($scope.deleteItems, {
            'deviceSerial': $scope.currentDevice.device.serial
        });

        $http.post('/deleteItem/save', data).success(function (response) {
            alert('설정 저장 완료');
        });
    };

    $scope.addHuntMap = function () {
        $scope.huntSetting.huntMapList.push({});
    };

    $scope.addJoystick = function (huntMap) {
        huntMap.huntJoysticks = huntMap.huntJoysticks || [];
        huntMap.huntJoysticks.push({});
    };

    $scope.deleteHuntMap = function (huntMap) {
        $scope.huntSetting.huntMapList = _.without($scope.huntSetting.huntMapList, _.findWhere($scope.huntSetting.huntMapList, huntMap));
    };

    $scope.removeJoystick = function (huntMap, joystick) {
        huntMap.huntJoysticks = _.without(huntMap.huntJoysticks, _.findWhere(huntMap.huntJoysticks, joystick));
    }
});

controllers.controller('PartyController', function ($scope, DeviceService) {
    $scope.partyDeviceList = [];

    DeviceService.getConnectedDeviceList(function (response) {
        $scope.connectedDeviceList = response;
    });

    $scope.settingParty = function (device) {
        $scope.partyDeviceList = _.without($scope.connectedDeviceList, _.findWhere($scope.connectedDeviceList, device));
    };
});

controllers.controller('MonitorController', function ($scope, SocketService) {
    SocketService.listen('/socket/devices', function (devices) {
        $scope.devices = devices;
        console.log(devices);
    });
});

controllers.controller('LogController', function ($scope, $interval, SocketService) {
    $scope.useAutoScroll = true;
    var scrollDownInterval = null;
    var messageStorageKey = 'logMessage';

    $scope.clearLog = function () {
        localStorage.removeItem(messageStorageKey);
        $scope.messages = [];
    };

    $scope.messages = function getMessages() {
        if (typeof(localStorage) !== "undefined") {
            var messages = localStorage.getItem(messageStorageKey);
            return messages ? JSON.parse(messages) : [];
        } else {
            return [];
        }
    }();

    SocketService.listen('/socket/triggerLog', function (data) {
        $scope.messages.push(data);

        if (typeof(localStorage) !== "undefined") {
            localStorage.setItem(messageStorageKey, JSON.stringify($scope.messages));
        }
    });

    $scope.toggleAutoScroll = function () {
        $scope.useAutoScroll = !$scope.useAutoScroll;

        if (!$scope.useAutoScroll) {
            $interval.cancel(scrollDownInterval);
        } else {
            autoScroll();
        }
    };

    function autoScroll() {
        scrollDownInterval = $interval(function () {
            $('#chattingBox').scrollTop($('#chattingBox')[0].scrollHeight);
        }, 10);
    }

    autoScroll();
});

controllers.controller('EditorController', function ($scope, $http) {
    $scope.getConnectedDeviceList = function () {
        $http.get('/common/getConnectedDeviceList').success(function (response) {
            $scope.connectedDeviceList = response;
            $scope.currentDevice = null;
            $scope.autoPatternList = null;
        });
    };

    $scope.refreshConnectDeviceList = function () {
        $http.get('/common/getConnectedDeviceList').success(function (response) {
            $scope.connectedDeviceList = response;
            $scope.currentDevice = null;
            $scope.autoPatternList = null;
        });
    };

    $scope.getConnectedDeviceList();

    var image = document.querySelector('#editorImage');
    var minAspectRatio = 0.5;
    var maxAspectRatio = 1.5;

    var cropper = new Cropper(image, {
        ready: function () {
            var cropper = this.cropper;
            $scope.cropBoxData = cropper.getCropBoxData();
            $scope.$apply();
        },
        cropmove: function () {
            var cropper = this.cropper;
            $scope.cropBoxData = cropper.getCropBoxData();
            $scope.$apply();
        }
    });

    $scope.getScreenDevice = function (device) {
        $scope.currentDevice = device;

        var t = '/common/getDeviceScreen?port=' + device.device.port + "&time=" + new Date().getTime();
        $('#editorImage').attr(t);
        cropper.replace(t)
    };

    $scope.getCropImage = function () {
        console.log($scope.cropBoxData);

        var x = Math.ceil($scope.cropBoxData.left);
        var y = Math.ceil($scope.cropBoxData.top);
        var w = Math.ceil($scope.cropBoxData.width);
        var h = Math.ceil($scope.cropBoxData.height);
        var port = $scope.currentDevice.device.port;

        var queryString = $.param({x: x, y: y, w: w, h: h, port: port});

        window.open('/common/getDeviceScreenCrop?' + queryString);
    };
});

controllers.controller("SnsController", function ($scope) {

})
