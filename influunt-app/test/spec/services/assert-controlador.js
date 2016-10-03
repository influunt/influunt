'use strict';

describe('Service: assertControlador', function () {

  // load the service's module
  // beforeEach(module('influuntApp'));

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
        estagios: [{idJson: 1}]
      }],
      estagios: [{idJson: 1}]
    };
  }));

  describe('hasAneis', function() {
    it('Verifica se o controlador passado por parametro possui ao menos um anel', function() {
      var result = assertControlador.hasAneis(controlador);
      expect(result).toBeTruthy();
    });
  });

  describe('hasEstagios', function() {
    it('o controlador é invalido se não houver estagios relacionados nos aneis.', function() {
      controlador.aneis[0].estagios = null;
      var result = assertControlador.hasEstagios(controlador);
      expect(result).not.toBeTruthy();
    });

    it('O controlador será inválido se não houver dados dos estagios na raiz do objeto', function() {
      controlador.estagios = [];
      var result = assertControlador.hasEstagios(controlador);
      expect(result).not.toBeTruthy();
    });

    it('O controlador deve ter ao menos um estagio em aneis, e este estagio deve existir no controlador', function() {
        var result = assertControlador.hasEstagios(controlador);
        expect(result).toBeTruthy();
      }
    );
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
