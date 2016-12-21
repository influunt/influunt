'use strict';

describe('Directive: wizardFooter', function () {

  var element, scope, $compile, $state;

  beforeEach(inject(function ($rootScope, $filter, _$state_, _$compile_) {
    scope = $rootScope.$new();
    $state = _$state_;
    $compile = _$compile_;
  }));

  it('Deve carregar o footer', function() {
    element = angular.element('<wizard-footer url="entre_verdes" current-step="app.wizard_controladores.entre_verdes" next-step="app.wizard_controladores.atraso_de_grupo" previous-step="app.wizard_controladores.transicoes_proibidas" require-assertion="true"></wizard-footer>');
    scope.$apply();
    expect(element).toBeDefined();
  });
});
