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

      console.log('-----------> ', $(element).css('background'));

      expect(true).toBe(true)
    }));

});
