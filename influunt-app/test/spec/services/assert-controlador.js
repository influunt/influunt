'use strict';

describe('Service: assertControlador', function () {
  // instantiate service
  var assertControlador, controlador;
  beforeEach(inject(function (_assertControlador_) {
    assertControlador = _assertControlador_;
    controlador = {
      aneis: [{
        idJson: 1,
        estagios: [{idJson: 'e1'}],
        gruposSemaforicos: [{
          idJson: 'gs1',
          transicoes: [{
            idJson: 't1',
            tabelaEntreVerdesTransicoes: [{}]
          }]
        }],
        verdesConflitantes: [{idJson: 'vc1'}],
        estagiosGruposSemaforicos: [{idJson: 'egs1'}]
      }],
      estagios: [{idJson: 'e1'}],
      gruposSemaforicos: [{idJson: 'gs1', transicoes: [{idJson: 't1'}]}],
      verdesConflitantes: [{idJson: 'vc1'}],
      estagiosGruposSemaforicos: [{idJson: 'egs1'}],
      transicoes: [{idJson: 't1'}],
      atrasosDeGrupo: [{idJson: 'adg1'}],
      tabelasEntreVerdesTransicoes: [{idJson: 'tevt1', tempoAmarelo: 1}]
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

  describe('assertSteps', function () {
    var controlador;

    beforeEach(function() {
      controlador = {};
    });

    describe('assertStepAneis', function() {
      it('deve invalidar o controlador que não tem ao menos um anel', function() {
        expect(assertControlador.assertStepAneis(controlador)).toBeFalsy();
      });

      it('deve validar o controlador se houver ao menos um anel', function() {
        controlador.aneis = [{idJson: 1}];
        expect(assertControlador.assertStepAneis(controlador)).toBeTruthy();
      });
    });

    describe('assertStepConfiguracaoGrupos', function() {
      beforeEach(function() {
        controlador = {
          aneis: [{
            idJson: 1,
            ativo: true,
            estagios: [{idJson: 'e1'}, {idJson: 'e2'}]
          }],
          estagios: [{idJson: 'e1'}, {idJson: 'e2'}]
        };
      });

      it('deve invalidar o controlador se não for válido para o step anterior', function() {
        controlador.aneis = [];
        expect(assertControlador.assertStepConfiguracaoGrupos(controlador)).toBeFalsy();
      });

      it('Deve invalidar o controlador se não houver estágios', function() {
        controlador.estagios = [];
        expect(assertControlador.assertStepConfiguracaoGrupos(controlador)).toBeFalsy();
      });

      it('Deve invalidar um controlador se não houver ao menos dois estagios configurados por anel ativo.', function() {
        controlador.estagios = [{idJson: 'e1'}];
        expect(assertControlador.assertStepConfiguracaoGrupos(controlador)).toBeFalsy();
      });

      it('Deve validar um controlador completo no step', function() {
        expect(assertControlador.assertStepConfiguracaoGrupos(controlador)).toBeTruthy();
      });
    });

    describe('assertStepVerdesConflitantes', function() {
      beforeEach(function() {
        controlador = {
          aneis: [{
            idJson: 1,
            estagios: [{idJson: 'e1'}],
            gruposSemaforicos: [{idJson: 'gs1'}]
          }],
          estagios: [{idJson: 'e1'}],
          gruposSemaforicos: [{idJson: 'gs1'}]
        };
      });

      it('deve invalidar o controlador se não for válido para o step anterior', function() {
        controlador.aneis = [];
        expect(assertControlador.assertStepVerdesConflitantes(controlador)).toBeFalsy();
      });

      it('Deve invalidar o controlador se não houver grupos semafóricos', function() {
        controlador.gruposSemaforicos = [];
        expect(assertControlador.assertStepVerdesConflitantes(controlador)).toBeFalsy();
      });

      it('Deve validar um controlador completo no step', function() {
        expect(assertControlador.assertStepVerdesConflitantes(controlador)).toBeTruthy();
      });
    });

    describe('assertStepAssociacao', function() {
      beforeEach(function() {
        controlador = {
          aneis: [{
            idJson: 1,
            estagios: [{idJson: 'e1'}],
            gruposSemaforicos: [{idJson: 'gs1'}],
            verdesConflitantes: [{idJson: 'vc1'}]
          }],
          estagios: [{idJson: 'e1'}],
          gruposSemaforicos: [{idJson: 'gs1'}],
          verdesConflitantes: [{idJson: 'vc1'}]
        };
      });

      it('deve invalidar o controlador se não for válido para o step anterior', function() {
        controlador.aneis = [];
        expect(assertControlador.assertStepAssociacao(controlador)).toBeFalsy();
      });

      it('Deve invalidar o controlador se não houver verdes conflitantes', function() {
        controlador.verdesConflitantes = [];
        expect(assertControlador.assertStepAssociacao(controlador)).toBeFalsy();
      });

      it('Deve validar um controlador completo no step', function() {
        expect(assertControlador.assertStepAssociacao(controlador)).toBeTruthy();
      });
    });

    describe('assertStepTransicoesProibidas', function() {
      beforeEach(function() {
        controlador = {
          aneis: [{
            idJson: 1,
            estagios: [{idJson: 'e1'}],
            gruposSemaforicos: [{idJson: 'gs1'}],
            verdesConflitantes: [{idJson: 'vc1'}],
            estagiosGruposSemaforicos: [{idJson: 'egs1'}]
          }],
          estagios: [{idJson: 'e1'}],
          gruposSemaforicos: [{idJson: 'gs1'}],
          verdesConflitantes: [{idJson: 'vc1'}],
          estagiosGruposSemaforicos: [{idJson: 'egs1'}]
        };
      });

      it('deve invalidar o controlador se não for válido para o step anterior', function() {
        controlador.aneis = [];
        expect(assertControlador.assertStepTransicoesProibidas(controlador)).toBeFalsy();
      });

      it('Deve invalidar o controlador se não houver associação de estagios e grupos semafóricos', function() {
        controlador.estagiosGruposSemaforicos = [];
        expect(assertControlador.assertStepTransicoesProibidas(controlador)).toBeFalsy();
      });

      it('Deve validar um controlador completo no step', function() {
        expect(assertControlador.assertStepTransicoesProibidas(controlador)).toBeTruthy();
      });
    });

    describe('assertStepEntreVerdes', function() {
      beforeEach(function() {
        controlador = {
          aneis: [{
            idJson: 1,
            estagios: [{idJson: 'e1'}],
            gruposSemaforicos: [{idJson: 'gs1'}],
            verdesConflitantes: [{idJson: 'vc1'}],
            estagiosGruposSemaforicos: [{idJson: 'egs1'}]
          }],
          estagios: [{idJson: 'e1'}],
          gruposSemaforicos: [{idJson: 'gs1', transicoes: [{idJson: 't1'}]}],
          verdesConflitantes: [{idJson: 'vc1'}],
          estagiosGruposSemaforicos: [{idJson: 'egs1'}],
          transicoes: [{idJson: 't1'}]
        };
      });

      it('deve invalidar o controlador se não for válido para o step anterior', function() {
        controlador.aneis = [];
        expect(assertControlador.assertStepEntreVerdes(controlador)).toBeFalsy();
      });

      it('Deve invalidar o controlador se não houver transições', function() {
        controlador.transicoes = [];
        _.each(controlador.gruposSemaforicos, function(i) { i.transicoes = []; });
        expect(assertControlador.assertStepEntreVerdes(controlador)).toBeFalsy();
      });

      it('Deve validar um controlador completo no step', function() {
        expect(assertControlador.assertStepEntreVerdes(controlador)).toBeTruthy();
      });
    });

    describe('assertStepAtrasoDeGrupo', function() {
      beforeEach(function() {
        controlador = {
          aneis: [{
            idJson: 1,
            estagios: [{idJson: 'e1'}],
            gruposSemaforicos: [{idJson: 'gs1'}],
            verdesConflitantes: [{idJson: 'vc1'}],
            estagiosGruposSemaforicos: [{idJson: 'egs1'}]
          }],
          estagios: [{idJson: 'e1'}],
          gruposSemaforicos: [{idJson: 'gs1', transicoes: [{idJson: 't1'}]}],
          verdesConflitantes: [{idJson: 'vc1'}],
          estagiosGruposSemaforicos: [{idJson: 'egs1'}],
          transicoes: [{idJson: 't1'}],
          tabelasEntreVerdesTransicoes: [{idJson: 'tevt1', tempoAmarelo: 1}]
        };
      });

      it('deve invalidar o controlador se não for válido para o step anterior', function() {
        controlador.aneis = [];
        expect(assertControlador.assertStepAtrasoDeGrupo(controlador)).toBeFalsy();
      });

      it('Deve invalidar o controlador se não houver dados de tabelas entreverdes', function() {
        controlador.tabelasEntreVerdesTransicoes = [];
        expect(assertControlador.assertStepAtrasoDeGrupo(controlador)).toBeFalsy();
      });

      it('Deve validar um controlador completo no step', function() {
        expect(assertControlador.assertStepAtrasoDeGrupo(controlador)).toBeTruthy();
      });
    });

    describe('assertStepAssociacaoDetectores', function() {
      beforeEach(function() {
        controlador = {
          aneis: [{
            idJson: 1,
            estagios: [{idJson: 'e1'}],
            gruposSemaforicos: [{idJson: 'gs1'}],
            verdesConflitantes: [{idJson: 'vc1'}],
            estagiosGruposSemaforicos: [{idJson: 'egs1'}]
          }],
          estagios: [{idJson: 'e1'}],
          gruposSemaforicos: [{idJson: 'gs1', transicoes: [{idJson: 't1'}]}],
          verdesConflitantes: [{idJson: 'vc1'}],
          estagiosGruposSemaforicos: [{idJson: 'egs1'}],
          transicoes: [{idJson: 't1'}],
          atrasosDeGrupo: [{idJson: 'adg1'}],
          tabelasEntreVerdesTransicoes: [{idJson: 'tevt1', tempoAmarelo: 1}]
        };
      });

      it('deve invalidar o controlador se não for válido para o step anterior', function() {
        controlador.aneis = [];
        expect(assertControlador.assertStepAssociacaoDetectores(controlador)).toBeFalsy();
      });

      it('Deve invalidar o controlador se não houver atraso de grupo.', function() {
        controlador.atrasosDeGrupo = [];
        expect(assertControlador.assertStepAssociacaoDetectores(controlador)).toBeFalsy();
      });

      it('Deve validar um controlador completo no step', function() {
        expect(assertControlador.assertStepAssociacaoDetectores(controlador)).toBeTruthy();
      });
    });
  });
});
