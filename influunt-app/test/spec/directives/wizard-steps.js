'use strict';

describe('Directive: wizardSteps', function () {

  // load the directive's module
  beforeEach(module('influuntApp'));

  var element,
    scope;

  beforeEach(inject(function ($rootScope, $compile) {
    scope = $rootScope.$new();
    element = '<ul wizard-steps><li class="current"></li><li></li><li></li><li></li><li></li></ul>';
    element = angular.element(element);
    element = $compile(element)(scope);
    scope.$apply();
  }));

  it('Se o primeiro for o elemento corrente, somente ele deverá estar ativo.', function() {
    var tabsInativas = element.children('.disabled');
    expect(tabsInativas.length).toBe(4);
  });

  it('Se o wizard avançar para o passo seguinte, o passo anterior deverá permanecer ativo', function() {
    var primeiraTab = element.children('li.current');
    var segundaTab = element.children('li:not(.current):first');

    primeiraTab.removeClass('current');
    segundaTab.addClass('current');
    scope.$apply();

    expect(element.children('.disabled').length).toBe(3);
    expect(primeiraTab.attr('class')).not.toMatch('disabled');
  });

  it('Se o wizard avançar um passo e voltar para o passo anterior, os passos já visitados deverão permanecer ativos',
    function() {
      var primeiraTab = element.children('li.current');
      var segundaTab = element.children('li:not(.current):first');

      // caminha um passo à frente.
      primeiraTab.removeClass('current');
      segundaTab.addClass('current');
      scope.$apply();

      // volta ao passo anterior.
      primeiraTab.removeClass('current');
      segundaTab.addClass('current');
      scope.$apply();

      expect(segundaTab.attr('class')).not.toMatch('disabled');
    });

  it('se não houver uma tab current, todas deverão permanecer desativadas', inject(function($compile) {
    element = '<ul wizard-steps><li></li><li></li><li></li><li></li><li></li></ul>';
    element = angular.element(element);
    element = $compile(element)(scope);
    scope.$apply();
    expect(element.children('li.disabled').length).toBe(5);
  }));

});
