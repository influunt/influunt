'use strict';

describe('Directive: customBackground', function () {

  // load the directive's module
  beforeEach(module('influuntApp'));

  var element,
    scope;

  beforeEach(inject(function ($rootScope) {
    scope = $rootScope.$new();
  }));

  it('Deve modificar algumas propriedades de css do elemento quando uma url for adicionada ao customBackground',
    inject(function($compile) {

      scope.test = {
        url: 'my-url'
      };

      element = angular.element('<div custom-background="test.url"></div>');
      element = $compile(element)(scope);
      scope.$apply();

      var background = $(element).css('background');
      expect(background).toMatch(/my-url/);
    }));

  it('não deverá modificar nada caso não haja nenhum valor para custom-background', inject(function($compile) {
    element = angular.element('<div custom-background=""></div>');
    element = $compile(element)(scope);
    scope.$apply();

    var background = $(element).css('background');
    expect(background).toBe('');
  }));

});
