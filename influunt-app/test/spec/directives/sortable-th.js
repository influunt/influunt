'use strict';

describe('Directive: sortableTh', function () {

  var element,
    scope;

  beforeEach(inject(function ($rootScope, $compile) {
    scope = $rootScope.$new();
    element = angular.element('<th sortable-th ng-model="pesquisa" label="teste" name="teste"></th>');
    element = $compile(element)(scope);
    scope.$apply();
  }));

  it('Deve criar um th com funções de ordenação', function () {
    expect(element).toBeDefined();
    expect($(element).find('a').length).toBe(1);
    expect($(element).find('span').length).toBe(2);
  });

  it('O th deve ter o texto igual ao campo "label"', function() {
    var link = $(element).find('a');
    expect(link.text()).toMatch('teste');
  });

  it('Quando o link dentro do th for clicado, o filtro deverá manter o campo de ordenação igual a "name"', function() {
    $(element).find('a').trigger('click');
    scope.$apply();
    expect(scope.pesquisa.orderField).toBe('teste');
    expect(scope.pesquisa.orderReverse).toBeTruthy();

    $(element).find('a').trigger('click');
    scope.$apply();
    expect(scope.pesquisa.orderField).toBe('teste');
    expect(scope.pesquisa.orderReverse).not.toBeTruthy();
  });
});
