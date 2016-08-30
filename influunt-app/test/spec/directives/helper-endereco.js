'use strict';

describe('Directive: helperEndereco', function () {
  beforeEach(module('influuntApp'));
  var mockEndereco = function() {
    return {
      geometry: {
        lat: function() {
          return 1;
        },
        lng: function() {
          return 1;
        }
      }
    };
  };

  var element,
    scope;

  beforeEach(inject(function ($rootScope) {
    scope = $rootScope.$new();
  }));

  // xit('Deve atualizar os campos de latitude e longitude', inject(function($compile) {
  //   scope.data = {
  //     endereco: null,
  //     latitude: null,
  //     longitude: null
  //   };

  //   element = angular.element(
  //     '<helper-endereco ng-model="data.endereco" latitude="data.latitude" longitude="data.longitude"></helper-endereco>'
  //   );
  //   element = $compile(element)(scope);
  //   scope.apply();
  //   scope.data.endereco.force_update = 1;

  //   expect(scope.data.latitude).toBe(1);
  //   expect(scope.data.longitude).toBe(1);

  // }));
});
