'use strict';

describe('Directive: influuntFaixaValores', function () {
  var element, scope;

  beforeEach(inject(function ($rootScope) {
    scope = $rootScope.$new();
  }));

  it('Teste name do input', inject(function ($compile) {
    element = angular.element('<influunt-faixa-valores name="\'faixa_de_valores.teste1\'" min="0" max="10" unidade="segundo"></influunt-faixa-valores>');
    element = $compile(element)(scope);
    scope.$apply();

    expect($(element).find('input[name="teste1Min"]')[0]).toBeDefined();

    expect($(element).find('input[name="teste1Max"]')[0]).toBeDefined();
  }));
});