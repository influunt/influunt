'use strict';

/**
 * @ngdoc function
 * @name influuntApp.controller:ControladoresMapaCtrl
 * @description
 * # ControladoresMapaCtrl
 * Controller of the influuntApp
 */
angular.module('influuntApp')
  .controller('ControladoresMapaCtrl', ['$scope', '$controller',
    function ($scope, $controller) {
      $controller('ControladoresCtrl', {$scope: $scope});

      $scope.inicializaMapa = function() {
        return $scope.index().then(function() {
          _.each($scope.lista, function(controlador) {
            console.log(controlador);
          });
        });
      };

      var options = [
        {"latitude": -19.98110052,"longitude": -44.03627324,"popupText": "<strong>[-19.98110052, -44.03627324] <p>Mussum ipsum cacilds, vidis litro abertis. Consetis adipiscings elitis. Pra lá , depois divoltis porris, paradis.</p>","options": {"draggable": true,"icon": "images/leaflet/influunt-icons/traffic-light.png"}},
        {"latitude": -19.97911257,"longitude": -44.03591287,"popupText": "<strong>[-19.97911257, -44.03591287] <p>Paisis, filhis, espiritis santis. Mé faiz elementum girarzis, nisi eros vermeio, in elementis mé pra quem é amistosis quis leo.</p>","options": {"draggable": true,"icon": "images/leaflet/influunt-icons/traffic-light.png"}},
        {"latitude": -19.98248531,"longitude": -44.03903758,"popupText": "<strong>[-19.98248531, -44.03903758] <p>Manduma pindureta quium dia nois paga. Sapien in monti palavris qui num significa nadis i pareci latim.</p>","options": {"draggable": true,"icon": "images/leaflet/influunt-icons/traffic-light.png"}},
        {"latitude": -19.97872414,"longitude": -44.03513976,"popupText": "<strong>[-19.97872414, -44.03513976] <p>Interessantiss quisso pudia ce receita de bolis, mais bolis eu num gostis.</p>","options": {"draggable": true,"icon": "images/leaflet/influunt-icons/traffic-light.png"}},
        {"latitude": -19.9837117,"longitude": -44.03633619,"popupText": "<strong>[-19.9837117, -44.03633619] <p>Suco de cevadiss, é um leite divinis, qui tem lupuliz, matis, aguis e fermentis.</p>","options": {"draggable": true,"icon": "images/leaflet/influunt-icons/traffic-light.png"}},
        {"latitude": -19.9770247,"longitude": -44.03335148,"popupText": "<strong>[-19.9770247, -44.03335148] <p>Interagi no mé, cursus quis, vehicula ac nisi. Aenean vel dui dui. Nullam leo erat, aliquet quis tempus a, posuere ut mi.</p>","options": {"draggable": true,"icon": "images/leaflet/influunt-icons/traffic-light.png"}},
        {"latitude": -19.98164014,"longitude": -44.03237617,"popupText": "<strong>[-19.98164014, -44.03237617] <p>Ut scelerisque neque et turpis posuere pulvinar pellentesque nibh ullamcorper. Pharetra in mattis molestie, volutpat elementum justo. Aenean ut ante turpis. Pellentesque laoreet mé vel lectus scelerisque interdum cursus velit auctor. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Etiam ac mauris lectus, non scelerisque augue. Aenean justo massa.</p>","options": {"draggable": true,"icon": "images/leaflet/influunt-icons/traffic-light.png"}},
        {"latitude": -19.98027967,"longitude": -44.03438513,"popupText": "<strong>[-19.98027967, -44.03438513] <p>Casamentiss faiz malandris se pirulitá, Nam liber tempor cum soluta nobis eleifend option congue nihil imperdiet doming id quod mazim placerat facer possim assum.</p>","options": {"draggable": true,"icon": "images/leaflet/influunt-icons/traffic-light.png"}},
        {"latitude": -19.97819713,"longitude": -44.03203441,"popupText": "<strong>[-19.97819713, -44.03203441] <p>Cevadis im ampola pa arma uma pindureta. Nam varius eleifend orci, sed viverra nisl condimentum ut. Donec eget justis enim. </p>","options": {"draggable": true,"icon": "images/leaflet/influunt-icons/traffic-light.png"}},
        {"latitude": -19.9784306,"longitude": -44.03387337,"popupText": "<strong>[-19.9784306, -44.03387337] <p>Atirei o pau no gatis. Viva Forevis aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos.</p>","options": {"draggable": true,"icon": "images/leaflet/influunt-icons/traffic-light.png"}}
      ];
      $scope.randomPlaces = function(size) {
        $scope.markers = {"latitude": -19.98110052,"longitude": -44.03627324};
        return true;
        $scope.markers = _.times(size, function() {
          return options[Math.floor((Math.random() * 100) % options.length)];
        });
        $scope.markers = _.cloneDeep($scope.markers);

        $scope.randomPolygons(size);
      };

      $scope.randomPolygons = function(size) {
        var colors = ['#D32F2F', '#C2185B', '#7B1FA2', '#303F9F', '#388E3C', '#FFA000', '#00796B'];

        $scope.areas = [];
        var polygonSize = 2 + Math.ceil((Math.random() * 100) % 10); // at least 3 points.
        var area = {
          points: _.times(polygonSize, function() {
            return options[Math.floor((Math.random() * 100) % options.length)];
          }),
          options: {
            color: colors[Math.floor((Math.random() * 100) % colors.length)],
            fillColor: colors[Math.floor((Math.random() * 100) % colors.length)]
          }
        };

        $scope.areas.push(area);
      };

    }]);
