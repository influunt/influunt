'use strict';

describe('Controller: ControladoresAtrasoDeGrupoCtrl', function () {

  var ControladoresAssociacaoCtrl,
    scope,
    $q,
    $httpBackend,
    $state,
    helpers;

  beforeEach(inject(function ($controller, $rootScope, _$httpBackend_, _$state_, _$q_) {
    scope = $rootScope.$new();
    ControladoresAssociacaoCtrl = $controller('ControladoresAtrasoDeGrupoCtrl', {$scope: scope});

    $httpBackend = _$httpBackend_;
    $state = _$state_;
    $q = _$q_;

    helpers = {cidades:[{},{}],fabricantes:[{},{},{}]};
    $httpBackend.expectGET('/helpers/controlador').respond(helpers);
    scope.inicializaAtrasoDeGrupo();
    $httpBackend.flush();
  }));

  it('Deve conter as definições de funções do ControladorCtrl', function() {
    expect(scope.inicializaWizard).toBeDefined();
  });

  describe('assertAtrasoDeGrupo', function () {
    it('O controlador deve ter ao menos um atraso de grupo', function() {
      scope.objeto = {
        todosEnderecos: [
          {idJson: 'end1', nomeEndereco: 'Rua Paul Bouthilier'}
        ],
        aneis: [
          {
            idJson: 1,
            ativo: true,
            endereco: {idJson: 'end1'},
            estagios: [
              {idJson: 'e1'},
              {idJson: 'e2'}
            ],
            gruposSemaforicos: [{idJson: 'gs2'}, {idJson: 'gs1'}],
            verdesConflitantes: [{idJson: 'vc1'}],
            estagiosGruposSemaforicos: [{idJson: 'egs1'}]
          }
        ],
        estagios: [
          {idJson: 'e1', id: 'e1', posicao: 1, anel: {idJson: 1}},
          {idJson: 'e2', id: 'e2', posicao: 2, anel: {idJson: 1}}
        ],
        gruposSemaforicos: [
          {idJson: 'gs2', transicoes: [{idJson: 't2'}], posicao: 2},
          {idJson: 'gs1', transicoes: [{idJson: 't1'}], posicao: 1}
        ],
        verdesConflitantes: [{idJson: 'vc1'}],
        estagiosGruposSemaforicos: [{idJson: 'egs1'}, {idJson: 'egs3'}],
        transicoes: [
          {idJson: 't1', origem: {idJson: 'e1'}, destino: {idJson: 'e2'}}, 
          {idJson: 't2', origem: {idJson: 'e2'}, destino: {idJson: 'e1'}}, 
          {idJson: 't3', origem: {idJson: 'e3'}, destino: {idJson: 'e4'}}
        ],
        tabelasEntreVerdesTransicoes: [{idJson: 'tevt1', tempoAmarelo: 1}],
      };

      var result = scope.assertAtrasoDeGrupo();
      expect(result).toBeTruthy();
    });

    it('Um controlador que não tenha ao menos um atraso de grupo', function() {
      scope.objeto = {atrasosDeGrupo: []};
      var result = scope.assertAtrasoDeGrupo();
      expect(result).not.toBeTruthy();
    });
  });

  describe('inicializaAtrasoDeGrupo', function () {
    describe('Configuração inválida', function () {
      it('Deve interromper o "inicializaAtrasoDeGrupo" se o controlador estiver inválido', function() {
        var objeto = {};
        WizardControladores.fakeInicializaWizard(scope, $q, objeto, function(){scope.inicializaAtrasoDeGrupo(0)});
        expect(scope.currentAnelIndex).not.toBeDefined();
      });
    });

    describe('Configuração inicializada', function () {
      beforeEach(function() {
        var objeto = {
          todosEnderecos: [
            {idJson: 'end1', nomeEndereco: 'Rua Paul Bouthilier'}
          ],
          aneis: [
            {
              idJson: 1,
              posicao: 1,
              ativo: true,
              endereco: {idJson: 'end1'},
              estagios: [
                {idJson: 'e1'},
                {idJson: 'e2'}
              ],
              gruposSemaforicos: [{idJson: 'gs2'}, {idJson: 'gs1'}],
              verdesConflitantes: [{idJson: 'vc1'}],
              estagiosGruposSemaforicos: [{idJson: 'egs1'}]
            },
            {
              idJson: 2,
              posicao: 2,
              ativo: true,
              endereco: {idJson: 'end1'},
              estagios: [
                {idJson: 'e3'},
                {idJson: 'e4'}
              ],
              gruposSemaforicos: [{idJson: 'gs3'}],
              verdesConflitantes: [{idJson: 'vc1'}],
              estagiosGruposSemaforicos: [{idJson: 'egs3'}]
            }
          ],
          estagios: [
            {idJson: 'e1', id: 'e1', posicao: 1, anel: {idJson: 1}},
            {idJson: 'e2', id: 'e2', posicao: 2, anel: {idJson: 1}},
            {idJson: 'e3', id: 'e3', posicao: 1, anel: {idJson: 2}},
            {idJson: 'e4', id: 'e4', posicao: 2, anel: {idJson: 2}}
          ],
          gruposSemaforicos: [
            {idJson: 'gs2', transicoes: [{idJson: 't2'}], posicao: 2},
            {idJson: 'gs1', transicoes: [{idJson: 't1'}], posicao: 1},
            {idJson: 'gs3', transicoes: [{idJson: 't3'}], posicao: 3}
          ],
          verdesConflitantes: [{idJson: 'vc1'}],
          estagiosGruposSemaforicos: [{idJson: 'egs1'}, {idJson: 'egs3'}],
          transicoes: [
            {idJson: 't1', origem: {idJson: 'e1'}, destino: {idJson: 'e2'}}, 
            {idJson: 't2', origem: {idJson: 'e2'}, destino: {idJson: 'e1'}}, 
            {idJson: 't3', origem: {idJson: 'e3'}, destino: {idJson: 'e4'}}
          ],
          tabelasEntreVerdesTransicoes: [{idJson: 'tevt1', tempoAmarelo: 1}],
          atrasoGrupoMin: 0,
          atrasoGrupoMax: 30
        };

        WizardControladores.fakeInicializaWizard(scope, $q, objeto, function(){scope.inicializaAtrasoDeGrupo(0)});
      });

      it('Deve inicializaros atrasosDeGrupo para cada transição existente', function() {
        expect(scope.objeto.atrasosDeGrupo.length).toBe(3);
        expect(scope.objeto.atrasosDeGrupo[0].atrasoDeGrupo).toBe(0);
      });

      it('Deve definir os valores minimos e maximos', function() {
        expect(scope.atrasoGrupoMin).toBe(0);
        expect(scope.atrasoGrupoMax).toBe(30);
      });

      it('Os dados devem estar ordenados e selecionados', function() {
        expect(scope.objeto.gruposSemaforicos[0].idJson).toBe('gs1');
        expect(scope.currentAnel.idJson).toBe(1);
        expect(scope.currentGrupoSemaforico.idJson).toBe('gs1');
        expect(scope.currentGruposSemaforicos.length).toBe(2);

        expect(scope.possuiInformacoesPreenchidas()).not.toBeTruthy();
      });

      it('Deve alterar as informações após trocar de anel', function() {
        scope.selecionaAnelAtrasoDeGrupo(1);
        expect(scope.objeto.gruposSemaforicos[0].idJson).toBe('gs1');
        expect(scope.currentAnel.idJson).toBe(2);
        expect(scope.currentGrupoSemaforico.idJson).toBe('gs3');
        expect(scope.currentGruposSemaforicos.length).toBe(1);

        expect(scope.possuiInformacoesPreenchidas()).not.toBeTruthy();
      });

      it('Deve liberar o avanço dessa etapa', function() {
        expect(scope.possuiInformacoesPreenchidas()).not.toBeTruthy();

        scope.currentTransicoes[0].atrasoDeGrupo.atrasoDeGrupo = 5;
        scope.$apply();

        expect(scope.possuiInformacoesPreenchidas()).toBeTruthy();
        scope.currentTransicoes[0].atrasoDeGrupo.atrasoDeGrupo = 0;
        scope.$apply();

        expect(scope.possuiInformacoesPreenchidas()).not.toBeTruthy();
        expect(scope.podeSalvar()).not.toBeTruthy();

        scope.currentTransicoes[0].atrasoDeGrupo.atrasoDeGrupo = 5;
        scope.$apply();

        expect(scope.possuiInformacoesPreenchidas()).toBeTruthy();
        expect(scope.podeSalvar()).not.toBeTruthy();

        scope.selecionaGrupoSemaforico(scope.currentGruposSemaforicos[1], 1);
        expect(scope.possuiInformacoesPreenchidas()).not.toBeTruthy();

        scope.selecionaGrupoSemaforico(scope.currentGruposSemaforicos[0], 0);
        scope.selecionaAnelAtrasoDeGrupo(1);
        scope.currentTransicoes[0].atrasoDeGrupo.atrasoDeGrupo = 2;
        scope.$apply();

        expect(scope.podeSalvar()).toBeTruthy();
      });
    });
  });

});
