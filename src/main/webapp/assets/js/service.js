services.service('ProgressService', function ($uibModal, $document) {
    var mod = {};

    var modalInstance = null;

    var elem = angular.element($document[0].querySelector('.modal-progress'));

    return {
        show: function () {
            modalInstance = $uibModal.open({
                animation: false,
                ariaLabelledBy: 'modal-title',
                ariaDescribedBy: 'modal-body',
                templateUrl: '/statics/progress.jsp',
                appendTo: elem
            });
        },
        close: function () {
            modalInstance.close();
        }
    }
});

services.service('DeviceService', function ($http, ProgressService) {
    var service = {};

    service.getAdbProcessList = function (callback) {
        ProgressService.show();

        $http.get('/common/getAdbProcessList').success(function (response) {
            callback(response);
            ProgressService.close();
        });
    };

    service.connectDevice = function (process, callback) {
        $http.post('/common/connectDevice', process).success(function (response) {
            callback(response);
        });
    };

    service.disConnectDevice = function (port, callback) {
        $http.post('/common/disConnectDevice', {port: port}).success(function (response) {
            callback(response);
        });
    };

    service.getConnectedDeviceList = function (callback) {
        $http.get('/common/getConnectedDeviceList').success(function (response) {
            callback(response);
        });
    };

    service.refreshConnectDeviceList = function (callback) {
        $http.get('/common/getConnectedDeviceList').success(function (response) {
            callback(response);
        });
    };

    service.findAutoPatternListByDevicePort = function (port, callback) {
        $http.post('/autoPattern/findByDevicePort', {
            devicePort: port
        }).success(function (response) {
            callback(response);
        });
    };

    return service;
});

services.service('SocketService', function ($q, $timeout) {
    var listenTypes = [];

    var service = {}, listener = $q.defer(), socket = {
        client: null,
        stomp: null
    }, sendQueue = [], readyState = false;

    service.RECONNECT_TIMEOUT = 30000;
    service.SOCKET_URL = "/web-socket";

    service.receive = function () {
        return listener.promise;
    };

    service.listen = function (listenType, callback) {
        if (!_.contains(listenTypes, listenType)) {
            listenTypes.push(listenType);
        }

        if (_.contains(listenTypes, listenType)) {
            service.receive().then(null, null, function (data) {
                if (data.listenType === listenType) {
                    var type = data.listenType;

                    delete data['listenType'];

                    callback(data, type);
                }
            });
        } else {
            console.warn('등록되지 않은 listenType 입니다.');
        }
    };

    service.send = function (broker, data) {
        data = data || {};

        if (!readyState) {
            sendQueue.push({broker: broker, data: data});
        } else {
            socket.stomp.send(broker, {
                priority: 9
            }, JSON.stringify(data));
        }
    };

    var getMessage = function (type, data) {
        var obj = JSON.parse(data);
        obj.listenType = type;

        return obj;
    };

    var startListener = function () {
        _.each(listenTypes, function (item) {
            socket.stomp.subscribe(item, function (data) {
                listener.notify(getMessage(item, data.body));
            });
        });

        readyState = true;

        _.each(sendQueue, function (q) {
            service.send(q.broker, q.data);
        });
    };

    var initialize = function () {
        socket.client = new SockJS(service.SOCKET_URL);
        socket.stomp = Stomp.over(socket.client);
        socket.stomp.connect({}, startListener);
        socket.stomp.onclose = reconnect;
        socket.stomp.debug = null;
    };

    var reconnect = function () {
        $timeout(function () {
            initialize();
        }, this.RECONNECT_TIMEOUT);
    };

    initialize();

    return service;
});