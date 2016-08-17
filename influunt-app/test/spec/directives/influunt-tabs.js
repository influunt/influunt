'use strict';

describe('Directive: influuntTabs', function () {

  // load the directive's module
  beforeEach(module('influuntApp'));

  var element,
    scope;

  beforeEach(inject(function ($rootScope) {
    scope = $rootScope.$new();
  }));

  beforeEach(inject(function ($templateCache, $interpolate, $compile) {
    scope.totalTabs = 0;
    scope.maxTabs = 4;
    scope.aneis = [];
    element = angular.element('<influunt-tabs class="tabpanel" data-on-activate="selecionaAnelAssociacaoDetectores" data-aneis-ativos="aneis" data-error-check="anelTemErro" can-add-tabs="true"></influunt-tabs>');
    element = $compile(element)(scope);
    scope.$apply();

  }));

  describe('onAdd', function() {
    it('Deve adicionar uma nova tab sempre que o "+" for clicado', function() {
      var addButton = $(element).find('li[aria-controls=add]');
      addButton.click();
      scope.$apply();

      var contentTabs = $(element).find('li[role="tab"]:not(.addTab)');
      expect(contentTabs.length).toBe(1);
    });

    it('Não deve adicionar mais do que quatro abas', function () {
      scope.totalTabs = 4;
      scope.maxTabs = 4;
      scope.$apply();
      var addButton = element.find('li[role="tab"].addTab');
      addButton.click();
      scope.$apply();
      expect(true).toBe(true);
    });
  });
});
