'use strict';

describe('Directive: influuntMap', function () {

  // load the directive's module
  // beforeEach(module('influuntApp'));

  var element,
    scope,
    $compile;

  beforeEach(inject(function ($rootScope, _$compile_) {
    $compile = _$compile_;
    scope = $rootScope.$new();
    scope.data = {
      markers: [{
        latitude: -23.55191017177127,
        longitude: -46.64923667907714,
        options: {
          id: 1,
          icon: 'icon'
        }
      }],
      areas: [
        {
          label: 'label 1',
          points: [
            {latitude: -23.50873193, longitude: -46.65517744},
            {latitude: -23.52258755, longitude: -46.63316917},
            {latitude: -23.53968749, longitude: -46.63952957}
          ]
        }
      ],
      agrupamentos: [
        {
          points: [
            {latitude: -23.50873193, longitude: -46.65517744},
            {latitude: -23.52258755, longitude: -46.63316917},
            {latitude: -23.53968749, longitude: -46.63952957}
          ]
        }
      ],
      options: {scrollWheelZoom: false},
      onClickMarker: function(){}
    };

    element = '<div influunt-map markers="data.markers" areas="data.areas" agrupamentos="data.agrupamentos" options="data.options" on-click-marker="data.onClickMarker($markerData)"></div>';
    element = $compile(element)(scope);
    scope.$digest();
  }));

  // aguarda 1 segundo, para que o mapa possa reederizar os markers.
  beforeEach(function(done) {setTimeout(done, 1000);});

  it('Deve criar um objeto de mapa', function() {
    expect(element.find('.leaflet-map-pane').length).toBe(1);
  });

  describe('markers', function () {
    it('Deve construir um objeto de marker.', function() {
      expect(element.find('.leaflet-marker-pane').length).toBe(1);
      expect(element.find('.leaflet-marker-icon').length).toBe(scope.data.markers.length);
    });

    it('Deve executar o método declarado em "onClickMarker" ao clicar um marker.', function() {
      spyOn(scope.data, 'onClickMarker');
      element.find('.leaflet-marker-icon').trigger('click');
      scope.$apply();
      expect(scope.data.onClickMarker).toHaveBeenCalled();
    });
  });

  describe('areas', function () {
    it('Deve construir um objeto de área', function () {
      expect(element.find('path.influunt-area').length).toBe(1);
    });

    it('Esta área deverá ter uma label', function() {
      expect(element.find('.leaflet-label-overlay').text()).toMatch(scope.data.areas[0].label);
    });
  });

  describe('agrupamentos', function () {
    it('Deve construir um objeto de área', function () {
      expect(element.find('path.influunt-agrupamento').length).toBe(1);
    });
  });
});
