'use strict';

describe('Service: assertControlador', function () {

  // load the service's module
  beforeEach(module('influuntApp'));

  // instantiate service
  var assertControlador, controlador;
  beforeEach(inject(function (_assertControlador_) {
    assertControlador = _assertControlador_;
    controlador = {
      aneis: [{
        gruposSemaforicos: [{
          transicoes: [{
            tabelaEntreVerdesTransicoes: [{}]
          }]
        }],
        estagios: [{}]
      }]
    };
  }));

  describe('hasAneis', function() {
    it('Verifica se o controlador passado por parametro possui ao menos um anel', function() {
      var result = assertControlador.hasAneis(controlador);
      expect(result).toBeTruthy();
    });
  });

  describe('hasEstagios', function() {
    it('Verifica se o controlador passado por parametro possui ao menos um estagio entre os aneis', function() {
      var result = assertControlador.hasEstagios(controlador);
      expect(result).toBeTruthy();
    });
  });

  describe('hasTransicoes', function() {
    it('Deve conter ao menos um anel', function() {
      var result = assertControlador.hasTransicoes({});
      expect(result).not.toBeTruthy();
    });

    it('Verifica se o controlador possui ao menos uma transição por grupo semafórico', function() {
      var result = assertControlador.hasTransicoes(controlador);
      expect(result).toBeTruthy();
    });
  });

  describe('hasTabelasEntreVerdes', function() {
    it('Deve conter transicoes', function() {
      var result = assertControlador.hasTabelasEntreVerdes({});
      expect(result).not.toBeTruthy();
    });

    it('Verifica se o controlador possui ao menos uma tabela entre-verdes por transição', function() {
      var result = assertControlador.hasTabelasEntreVerdes(controlador);
      expect(result).toBeTruthy();
    });

  });
});
