'use strict';

describe('Directive: influuntKnob', function () {

  // load the directive's module
  beforeEach(module('influuntApp'));

  var element,
    scope,
    $compile;

  beforeEach(inject(function ($rootScope, $timeout, _$compile_) {
    $compile = _$compile_;
    scope = $rootScope.$new();
    scope.teste = {
      min: 0,
      max: 100,
      knob: null,
      title: 'teste knob',
      label: 'SEG'
    };

    element = angular.element('<influunt-knob title="{{ teste.title }}" label="{{ teste.label }}" ng-model="teste.knob" max="teste.max" min="scope.min"></influunt-knob>');
    element = $compile(element)(scope);
    scope.$apply();
    $timeout.flush();
  }));

  it('Deve ter o titulo igual ao valor passado por parametro', function() {
    var title = $(element).find('.title').html();
    expect(title).toBe(scope.teste.title);
  });
  it('Deve ter a label igual ao valor passado por parametro', function() {
    var label = $(element).find('.knob-label').html();
    expect(label).toBe(scope.teste.label);
  });
  it('Se houver valor de ng-model, o componente deverá ter o valor atual igual ao ng-model', function() {
    scope.teste.knob = 42;
    element = angular.element('<influunt-knob title="{{ teste.title }}" label="{{ teste.label }}" ng-model="teste.knob" max="teste.max" min="scope.min"></influunt-knob>');
    element = $compile(element)(scope);
    scope.$apply();

    var value = parseInt($(element).find('.dial-input').val());
    expect(value).toBe(scope.teste.knob);
  });
  it('Se não houver valor de ng-model, o valor do componente será igual ao valor minimo.', function() {
    var value = parseInt($(element).find('.dial-input').val());
    expect(value).toBe(scope.teste.min);
  });
  it('Se houver atualizacao no valor de model, o valor do componente também deverá ser atualizado', function() {
    scope.teste.knob = 42;
    scope.$apply();
    var value = parseInt($(element).find('.dial-input').val());
    expect(value).toBe(scope.teste.knob);
  });
  it('Se não houver valor de ng-model, ele deverá ser atualizado para o valor mínimo', function() {
    expect(scope.teste.knob).toBe(scope.teste.min);
  });
  it('Se houver atualizacao no componente, o valor de ng-model também devera se atualizado', function() {
    var value = 42;
    $(element).find('.dial-input').val(value).trigger('change');
    scope.$apply();
    expect(value).toBe(parseInt(scope.teste.knob));
  });
});
