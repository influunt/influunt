'use strict';

describe('Directive: wizardSteps', function () {
  // beforeEach(module('influuntApp'));

  var element, scope, $compile;

  beforeEach(inject(function ($rootScope, _$compile_) {
    scope = $rootScope.$new();
    $compile = _$compile_;

    element = angular.element('<ul wizard-steps><li></li><li class="active"></li><li></li></ul>');
    element = $compile(element)(scope);
    scope.$apply();
  }));

  it('Os passos anteriores ao "active" devem estar "completed"', function() {
    var res = $(element).children('li:nth-child(1)').hasClass('completed');
    expect(res).toBeTruthy();
  });

  it('Os passos depois de "active" devem estar "disabled"', function() {
    var res = $(element).children('li:nth-child(3)').hasClass('disabled');
    expect(res).toBeTruthy();
  });

  it('Deve desabilitar todos os passos se não houver um ativo', function() {
    element = angular.element('<ul wizard-steps><li></li><li></li><li></li></ul>');
    element = $compile(element)(scope);
    scope.$apply();

    expect($(element).children('li.disabled').length).toBe(3);
  });

});
