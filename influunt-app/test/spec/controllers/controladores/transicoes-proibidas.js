'use strict';

describe('Controller: ControladoresTransicoesProibidasCtrl', function () {

  var ControladoresTransicoesProibidasCtrl,
    scope,
    $q,
    $httpBackend,
    $state,
    helpers;

  beforeEach(inject(function ($controller, $rootScope, _$httpBackend_, _$state_, _$q_) {
    scope = $rootScope.$new();
    ControladoresTransicoesProibidasCtrl = $controller('ControladoresTransicoesProibidasCtrl', {$scope: scope});

    $httpBackend = _$httpBackend_;
    $state = _$state_;
    $q = _$q_;

    helpers = {cidades:[{},{}],fabricantes:[{},{},{}]};
    $httpBackend.expectGET('/helpers/controlador').respond(helpers);
    scope.inicializaWizard();
    $httpBackend.flush();
  }));

  it('Deve conter as definições de funções do ControladorCtrl', function() {
    expect(scope.inicializaWizard).toBeDefined();
  });

  describe('assertTransicoesProibidas', function () {
    it('O controlador deve ter ao menos as configuracoes minimas ate associacao.', function() {
      scope.objeto = {
        aneis: [{
          idJson: 1,
          estagios: [{idJson: 'e1'}],
          gruposSemaforicos: [{idJson: 'gs1'}],
          verdesConflitantes: [{idJson: 'vc1'}],
          estagiosGruposSemaforicos: [{idJson: 'egs1'}]

        }],
        estagios: [{idJson: 'e1'}],
        estagiosGruposSemaforicos: [{idJson: 'egs1'}],
        gruposSemaforicos: [{idJson: 'gs1', transicoes: [{idJson: 't1'}]}],
        verdesConflitantes: [{idJson: 'vc1'}]
      };
      var result = scope.assertTransicoesProibidas();
      expect(result).toBeTruthy();
    });

    it('Um controlador que não tenha ao menos um anel deve ser considerado inválido', function() {
      scope.objeto = {aneis: []};
      var result = scope.assertTransicoesProibidas();
      expect(result).not.toBeTruthy();
    });
  });

  describe('inicializaTransicoesProibidas', function () {
    describe('Configuração inválida', function () {
      it('Deve interromper o "inicializaTransicoesProibidas" se o controlador estiver inválido', function() {
        var objeto = {};
        WizardControladores.fakeInicializaWizard(scope, $q, objeto, scope.inicializaTransicoesProibidas);
        expect(scope.currentAnelIndex).not.toBeDefined();
      });
    });

    describe('Configuração inicializada', function () {
      beforeEach(function() {
        var objeto = {
          aneis: [
            {
              idJson: 1,
              ativo: true,
              estagios: [
                {idJson: 'e1'},
                {idJson: 'e2'},
                {idJson: 'e3'},
                {idJson: 'e4'}
              ],
              gruposSemaforicos: [{idJson: 'gs1'}],
              verdesConflitantes: [{idJson: 'vc1'}],
              estagiosGruposSemaforicos: [{idJson: 'egs1'}]
            }
          ],
          estagios: [
            {idJson: 'e1', id: 'e1', posicao: 1, anel: {idJson: 1}, origemDeTransicoesProibidas: [{idJson: 'tp1'}]},
            {idJson: 'e2', id: 'e2', posicao: 2, anel: {idJson: 1}},
            {idJson: 'e3', id: 'e3', posicao: 3, anel: {idJson: 1}},
            {idJson: 'e4', id: 'e4', posicao: 4, anel: {idJson: 1}}
          ],
          transicoesProibidas: [
            {idJson: 'tp1',origem: {idJson: 'e1'},destino: {idJson: 'e2'},alternativo: {idJson: 'e3'}}
          ],
          estagiosGruposSemaforicos: [{idJson: 'egs1'}],
          gruposSemaforicos: [{idJson: 'gs1', transicoes: [{idJson: 't1'}]}],
          verdesConflitantes: [{idJson: 'vc1'}]
        };

        WizardControladores.fakeInicializaWizard(scope, $q, objeto, scope.inicializaTransicoesProibidas);
      });

      it('Deve inicializar a estrutura auxiliar de "transicoesProibidas" nos aneis', function() {
        expect(scope.objeto.aneis[0].transicoesProibidas['E1-E2']).toBeDefined();
      });
    });
  });

  describe('toggleTransicaoProibida', function () {
    beforeEach(function() {
      var objeto = {
        aneis: [
          {
            idJson: 1,
            ativo: true,
            estagios: [
              {idJson: 'e1'},
              {idJson: 'e2'},
              {idJson: 'e3'},
              {idJson: 'e4'}
            ],
            gruposSemaforicos: [{idJson: 'gs1'}],
            verdesConflitantes: [{idJson: 'vc1'}],
            estagiosGruposSemaforicos: [{idJson: 'egs1'}]
          }
        ],
        estagios: [
          {idJson: 'e1', id: 'e1', posicao: 1, anel: {idJson: 1}},
          {idJson: 'e2', id: 'e2', posicao: 2, anel: {idJson: 1}},
          {idJson: 'e3', id: 'e3', posicao: 3, anel: {idJson: 1}},
          {idJson: 'e4', id: 'e4', posicao: 4, anel: {idJson: 1}}
        ],
        estagiosGruposSemaforicos: [{idJson: 'egs1'}],
        gruposSemaforicos: [{idJson: 'gs1'}],
        verdesConflitantes: [{idJson: 'vc1'}]
      };

      WizardControladores.fakeInicializaWizard(scope, $q, objeto, scope.inicializaTransicoesProibidas);
    });

    it('se a intercessão e1xe1 for clicada, o metodo deverá ser interrompido sem causar alterações', function() {
      var estagio = scope.objeto.estagios[0];
      scope.toggleTransicaoProibida(estagio, estagio);

      expect(estagio.origemDeTransicoesProibidas).not.toBeDefined();
      expect(estagio.destinoDeTransicoesProibidas).not.toBeDefined();
    });

    describe('Ativação/desativação da intercessão dos estágios E1-E2', function() {
      var estagio1, estagio2;
      beforeEach(function() {
        estagio1 = scope.objeto.estagios[0];
        estagio2 = scope.objeto.estagios[1];
        scope.toggleTransicaoProibida(estagio1, estagio2, false);
      });

      it('O estagio e1 será origem de uma transicao proibida para e2 em "origemDeTransicoesProibidas" do estagio1', function() {
        var transicaoProibida = _.find(
          scope.objeto.transicoesProibidas, {idJson: estagio1.origemDeTransicoesProibidas[0].idJson}
        );
        expect(transicaoProibida.origem.idJson).toBe(estagio1.idJson);
      });

      it('O estágio e1 será origem de uma transicao proibida para e2 em "destinoDeTransicoesProibidas" do estagio2', function() {
        var transicaoProibida = _.find(
          scope.objeto.transicoesProibidas, {idJson: estagio2.destinoDeTransicoesProibidas[0].idJson}
        );
        expect(transicaoProibida.origem.idJson).toBe(estagio1.idJson);
      });

      it('O estagio e2 será destino de uma transição proibida para e1 em "origemDeTransicoesProibidas do estagio1"', function() {
        var transicaoProibida = _.find(
          scope.objeto.transicoesProibidas, {idJson: estagio1.origemDeTransicoesProibidas[0].idJson}
        );
        expect(transicaoProibida.destino.idJson).toBe(estagio2.idJson);
      });

      it('O estagio e2 será destino de uma transição proibida para e1 em "destinoDeTransicoesProibidas" do estagio2', function() {
        var transicaoProibida = _.find(
          scope.objeto.transicoesProibidas, {idJson: estagio2.destinoDeTransicoesProibidas[0].idJson}
        );
        expect(transicaoProibida.destino.idJson).toBe(estagio2.idJson);
      });

      it('Quando o usuario desativa a intercessão E1-E2, os campos de origem e destino de ambos deverão ser removidos', function() {
        scope.toggleTransicaoProibida(estagio1, estagio2, false);
        expect(estagio1.origemDeTransicoesProibidas.length).toBe(0);
        expect(estagio2.destinoDeTransicoesProibidas.length).toBe(0);
        expect(scope.objeto.transicoesProibidas.length).toBe(0);
      });
    });
  });

  describe('marcarTransicaoAlternativa', function () {
    var estagio1, estagio2, estagio3, estagio4;
    describe('Dado que a transicacao E1-E2 receba o estágio E3 como alternativo', function () {
      beforeEach(function() {
        var objeto = {
          aneis: [
            {
              idJson: 1,
              ativo: true,
              estagios: [
                {idJson: 'e1'},
                {idJson: 'e2'},
                {idJson: 'e3'},
                {idJson: 'e4'}
              ],
              gruposSemaforicos: [{idJson: 'gs1'}],
              verdesConflitantes: [{idJson: 'vc1'}],
              estagiosGruposSemaforicos: [{idJson: 'egs1'}]
            }
          ],
          estagios: [
            {idJson: 'e1', id: 'e1', posicao: 1, anel: {idJson: 1}},
            {idJson: 'e2', id: 'e2', posicao: 2, anel: {idJson: 1}},
            {idJson: 'e3', id: 'e3', posicao: 3, anel: {idJson: 1}},
            {idJson: 'e4', id: 'e4', posicao: 4, anel: {idJson: 1}}
          ],
          estagiosGruposSemaforicos: [{idJson: 'egs1'}],
          gruposSemaforicos: [{idJson: 'gs1'}],
          verdesConflitantes: [{idJson: 'vc1'}]
        };

        WizardControladores.fakeInicializaWizard(scope, $q, objeto, scope.inicializaTransicoesProibidas);
        estagio1 = scope.objeto.estagios[0];
        estagio2 = scope.objeto.estagios[1];
        estagio3 = scope.objeto.estagios[2];
        estagio4 = scope.objeto.estagios[3];
        scope.toggleTransicaoProibida(estagio1, estagio2);
        scope.marcarTransicaoAlternativa({origem: estagio1,destino: estagio2,alternativo: estagio3});
      });

      describe('marcação de transição alternativa', function () {
        it('O estágio E1 deve ter um objeto em origemDeTransicoesProibidas, com origem = E1, destino = E2 e alternativo = E3',
          function() {
            var transicaoProibida = _.find(
              scope.objeto.transicoesProibidas, {idJson: estagio1.origemDeTransicoesProibidas[0].idJson}
            );

            expect(transicaoProibida.origem.idJson).toBe(estagio1.idJson);
            expect(transicaoProibida.destino.idJson).toBe(estagio2.idJson);
            expect(transicaoProibida.alternativo.idJson).toBe(estagio3.idJson);
          });
        it('O estágio E2 deve ter um objeto em destinoDeTransicoesProibidas, com origem = E1, destino = E2 e alternativo = E3',
          function() {
            var transicaoProibida = _.find(
              scope.objeto.transicoesProibidas, {idJson: estagio2.destinoDeTransicoesProibidas[0].idJson}
            );

            expect(transicaoProibida.origem.idJson).toBe(estagio1.idJson);
            expect(transicaoProibida.destino.idJson).toBe(estagio2.idJson);
            expect(transicaoProibida.alternativo.idJson).toBe(estagio3.idJson);
          });
        it('O estágio E3 deve ter um objeto em alternativaDeTransicoesProibidas, com origem = E1, destino = E2 e alternativo = E3',
          function() {
            var transicaoProibida = _.find(
              scope.objeto.transicoesProibidas, {idJson: estagio3.alternativaDeTransicoesProibidas[0].idJson}
            );

            expect(transicaoProibida.origem.idJson).toBe(estagio1.idJson);
            expect(transicaoProibida.destino.idJson).toBe(estagio2.idJson);
            expect(transicaoProibida.alternativo.idJson).toBe(estagio3.idJson);
          });
        it('O estagio E4 não deverá ter objetos em nenhum dos campos de transicoesProibidas',
          function() {
            expect(estagio4.origemDeTransicoesProibidas).not.toBeDefined();
            expect(estagio4.destinoDeTransicoesProibidas).not.toBeDefined();
            expect(estagio4.alternativaDeTransicoesProibidas).not.toBeDefined();
          });
      });

      describe('Dado que a alternativa de E1-E2 seja alterada para E4', function () {
        beforeEach(function() {
          scope.marcarTransicaoAlternativa({origem: estagio1, destino: estagio2, alternativo: estagio4});
        });

        it('O estágio E1 deve ter um objeto em origemDeTransicoesProibidas, com origem = E1, destino = E2 e alternativo = E4',
          function() {
            var transicaoProibida = _.find(
              scope.objeto.transicoesProibidas, {idJson: estagio1.origemDeTransicoesProibidas[0].idJson}
            );

            expect(transicaoProibida.origem.idJson).toBe(estagio1.idJson);
            expect(transicaoProibida.destino.idJson).toBe(estagio2.idJson);
            expect(transicaoProibida.alternativo.idJson).toBe(estagio4.idJson);
          });
        it('O estágio E2 deve ter um objeto em destinoDeTransicoesProibidas, com origem = E1, destino = E2 e alternativo = E4',
          function() {
            var transicaoProibida = _.find(
              scope.objeto.transicoesProibidas, {idJson: estagio2.destinoDeTransicoesProibidas[0].idJson}
            );

            expect(transicaoProibida.origem.idJson).toBe(estagio1.idJson);
            expect(transicaoProibida.destino.idJson).toBe(estagio2.idJson);
            expect(transicaoProibida.alternativo.idJson).toBe(estagio4.idJson);
          });
        it('O estagio E3 não deverá ter objetos em nenhum dos campos de transicoesProibidas',
          function() {
            expect(estagio3.origemDeTransicoesProibidas).not.toBeDefined();
            expect(estagio3.destinoDeTransicoesProibidas).not.toBeDefined();
            expect(estagio3.alternativaDeTransicoesProibidas[0]).not.toBeDefined();
          });
        it('O estágio E4 deve ter um objeto em alternativaDeTransicoesProibidas, com origem = E1, destino = E2 e alternativo = E4',
          function() {
            var transicaoProibida = _.find(
              scope.objeto.transicoesProibidas, {idJson: estagio4.alternativaDeTransicoesProibidas[0].idJson}
            );

            expect(transicaoProibida.origem.idJson).toBe(estagio1.idJson);
            expect(transicaoProibida.destino.idJson).toBe(estagio2.idJson);
            expect(transicaoProibida.alternativo.idJson).toBe(estagio4.idJson);
          });
      });

      describe('desativar transicao com estagio alternativo', function () {
        it('O estagio alternativo E3 deverá ter sua "alternativaDeTransicoesProibidas" removida', function() {
          scope.toggleTransicaoProibida(estagio1, estagio2);
          expect(estagio3.alternativaDeTransicoesProibidas.length).toBe(0);
        });
      });
    });
  });

  describe('getErrosEstagiosAlternativos', function () {
    beforeEach(inject(function(handleValidations) {
      scope.objeto = {
        aneis: [
          {
            idJson: 1,
            estagios: [
              {idJson: 'E1A1'},
              {idJson: 'E2A1'}
            ]
          },
          {
            idJson: 2,
            estagios: [
              {idJson: 'E1A2'},
              {idJson: 'E2A2'}
            ]
          }
        ],
        estagios: [
          {idJson: 'E1A1', anel: {idJson: 1}},
          {idJson: 'E2A1', anel: {idJson: 1}},
          {idJson: 'E1A2', anel: {idJson: 2}, origemDeTransicoesProibidas: [{idJson: 'TP1'}]},
          {idJson: 'E2A2', anel: {idJson: 2}}
        ],
        transicoesProibidas: [
          {idJson: 'TP1', origem: {idJson: 'E1A2'}, destino: {idJson: 'E2A2'}}
        ]
      };
      var error = [{'root':'Controlador','message':'não pode ficar em branco','path':'aneis[1].estagios[0].origemDeTransicoesProibidas[0].alternativo'}];
      scope.errors = handleValidations.buildValidationMessages(error, scope.objeto);
    }));

    it('Não deve ter erro para transição E1A1-E2A1', function() {
      var origem = {idJson: 'E1A1'};
      var destino = {idJson: 'E2A1'};
      var result = scope.getErrosEstagiosAlternativos(origem, destino);
      expect(result).not.toBeTruthy();
    });

    it('Deve ter erro para a transição E1A2-E2A2', function() {
      var origem = {idJson: 'E1A2'};
      var destino = {idJson: 'E2A2'};
      var result = scope.getErrosEstagiosAlternativos(origem, destino);
      expect(result).toBeTruthy();
    });
  });

  describe('getErrosEstagios', function () {
    beforeEach(inject(function($timeout, handleValidations) {
      scope.objeto = {
        aneis: [
          {
            idJson: 1,
            estagios: [
              {idJson: 'E1A1'},
              {idJson: 'E2A1'},
              {idJson: 'E3A1'},
              {idJson: 'E4A1'}
            ]
          },
          {
            idJson: 2,
            estagios: [
              {idJson: 'E1A2'},
              {idJson: 'E2A2'}
            ]
          }
        ],
        estagios: [
          {idJson: 'E1A1', anel: {idJson: 1}},
          {idJson: 'E2A1', anel: {idJson: 1}},
          {idJson: 'E3A1', anel: {idJson: 1}},
          {idJson: 'E4A1', anel: {idJson: 1}},
          {idJson: 'E1A2', anel: {idJson: 2}, demandaPrioritaria: true},
          {idJson: 'E2A2', anel: {idJson: 2}}
        ],
        transicoesProibidas: [
          {idJson: 'TP1', origem: {idJson: 'E1A2'}, destino: {idJson: 'E2A2'}}
        ]
      };
      var error = [{'root':'Controlador','message':'Esse estágio deve possuir ao menos uma transição válida para outro estágio.','path':'aneis[0].estagios[2].estagioPossuiAoMenosUmaTransicaoOrigemValida'},
                   {'root':'Controlador','message':'Pelo menos um estágio deve ter uma transição válida para esse estágio.','path':'aneis[0].estagios[2].estagioPossuiAoMenosUmaTransicaoDestinoValida'},
                   {'root':'Controlador','message':'Outro erro qualquer.','path':'aneis[0].estagios[2].estagioPossuiOutroErroQualquer'}];
      scope.errors = handleValidations.buildValidationMessages(error, scope.objeto);
      scope.selecionaAnel(0);
      scope.errosEstagios = [];
      scope.getErrosEstagios();
      scope.$apply();
      $timeout.flush();
    }));

    it('Não deve ter erro para o estágio 1', function() {
        expect(scope.errosEstagios[0].length).toBe(0);
    });

    it('Não deve ter erro para o estágio 2', function() {
      expect(scope.errosEstagios[1].length).toBe(0);
    });

    it('Deve ter erro para o estágio 3', function() {
      expect(scope.errosEstagios[2].length).toBe(2);
    });

    it('Não deve ter erro para o estágio 4', function() {
      expect(scope.errosEstagios[3].length).toBe(0);
    });
  });
});
