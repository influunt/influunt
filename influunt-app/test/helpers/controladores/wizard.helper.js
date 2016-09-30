'use strict';

/**
 * Conjunto de funções auxiliares utilizadas pelos testes do wizard de controladores.
 *
 * @type       {<type>}
 */
var WizardControladores = {
  /**
   * Simula a inicializacao de todos os passos do wizard.
   *
   * @param      {<type>}    scope   The scope
   * @param      {<type>}    $q      The quarter
   * @param      {<type>}    objeto  The objeto
   * @param      {Function}  fn      The function
   */
  fakeInicializaWizard: function(scope, $q, objeto, fn) {
    spyOn(scope, 'inicializaWizard').and.callFake(function() {
      var deferred = $q.defer();
      deferred.resolve({});
      return deferred.promise;
    });

    scope.objeto = objeto;
    fn();
    scope.$apply();
  }
};
