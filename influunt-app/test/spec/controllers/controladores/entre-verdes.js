'use strict';

describe('Controller: ControladoresEntreVerdesCtrl', function () {

  var ControladoresEntreVerdesCtrl,
    scope,
    $q,
    $httpBackend,
    $state,
    helpers;

  beforeEach(inject(function ($controller, $rootScope, _$httpBackend_, _$state_, _$q_) {
    scope = $rootScope.$new();
    ControladoresEntreVerdesCtrl = $controller('ControladoresEntreVerdesCtrl', {$scope: scope});

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

  describe('assertEntreVerdes', function () {
    it('O controlador deve ter ao menos um estagio Grupo Semaforico.', function() {
      scope.objeto = {
        aneis: [
          {
            idJson: 1,
            gruposSemaforicos: [{idJson: 'gs1'}],
            verdesConflitantes: [{idJson: 'vc1'}],
            estagios: [{idJson: 'e1'}],
            estagiosGruposSemaforicos: [{idJson: 'egs1'}, {idJson: 'egs2'}]
          }
        ],
        estagios: [
          {idJson: 'e1', id: 'e1', anel: {idJson: 1}},
          {idJson: 'e2', id: 'e2', anel: {idJson: 1}}
        ],
        gruposSemaforicos: [
          {idJson: 'gs1', tipo: 'VEICULAR', transicoes: [{idJson: 't1'}]},
          {idJson: 'gs2', tipo: 'PEDESTRE', transicoes: [{idJson: 't2'}]}
        ],
        verdesConflitantes: [{idJson: 'vc1'}],
        estagiosGruposSemaforicos: [{idJson: 'egs1'}, {idJson: 'egs2'}],
        transicoes: [
          {idJson: 't1', origem: {idJson: 'e1'}, destino: {idJson: 'e2'}}, 
          {idJson: 't2', origem: {idJson: 'e1'}, destino: {idJson: 'e2'}},
        ]
      };

      var result = scope.assertEntreVerdes();
      expect(result).toBeTruthy();
    });

    it('Um controlador que não tenha ao menos um estagio grupo semaforico', function() {
      scope.objeto = {aneis: []};
      var result = scope.assertEntreVerdes();
      expect(result).not.toBeTruthy();
    });
  });

  describe('inicializaEntreVerdes', function () {
    describe('Configuração inválida', function () {
      it('Deve interromper o "inicializaEntreVerdes" se o controlador estiver inválido', function() {
        var objeto = {};
        WizardControladores.fakeInicializaWizard(scope, $q, objeto, scope.inicializaEntreVerdes);
        expect(scope.currentAnelIndex).not.toBeDefined();
      });
    });
  });

  describe('Deve montar o entreverdes', function () {
    var deferred;
    beforeEach(inject(function(influuntAlert) {
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
          {idJson: 'gs2', tipo: 'PEDESTRE', transicoes: [{idJson: 't2'}, {idJson: 't5'}], tabelasEntreVerdes: [{idJson: 'tev2'}], posicao: 2},
          {idJson: 'gs1', tipo: 'VEICULAR', transicoes: [{idJson: 't1'}, {idJson: 't4'}], tabelasEntreVerdes: [{idJson: 'tev1'}], posicao: 1},
          {idJson: 'gs3', tipo: 'VEICULAR', transicoes: [{idJson: 't3'}], tabelasEntreVerdes: [{idJson: 'tev3'}], posicao: 3}
        ],
        verdesConflitantes: [{idJson: 'vc1'}],
        estagiosGruposSemaforicos: [{idJson: 'egs1'}, {idJson: 'egs3'}],
        transicoes: [
          {idJson: 't1', origem: {idJson: 'e1'}, destino: {idJson: 'e2'}, tabelaEntreVerdesTransicoes: [{idJson: 'tevt1'}], grupoSemaforico: {idJson: 'gs1'} }, 
          {idJson: 't2', origem: {idJson: 'e2'}, destino: {idJson: 'e1'}, tabelaEntreVerdesTransicoes: [{idJson: 'tevt2'}], grupoSemaforico: {idJson: 'gs2'} }, 
          {idJson: 't3', origem: {idJson: 'e3'}, destino: {idJson: 'e4'}, tabelaEntreVerdesTransicoes: [{idJson: 'tevt3'}], grupoSemaforico: {idJson: 'gs3'} },
          {idJson: 't4', origem: {idJson: 'e2'}, destino: {idJson: 'e3'}, tabelaEntreVerdesTransicoes: [{idJson: 'tevt4'}], grupoSemaforico: {idJson: 'gs1'} },
          {idJson: 't5', origem: {idJson: 'e2'}, destino: {idJson: 'e3'}, tabelaEntreVerdesTransicoes: [{idJson: 'tevt5'}], grupoSemaforico: {idJson: 'gs2'} } 
        ],
        tabelasEntreVerdes: [
          {
            idJson: 'tev1',
            posicao: 1,
            grupoSemaforico: {idJson: 'gs1'},
            tabelaEntreVerdesTransicoes: [{idJson: 'tevt1'}]
          },
          {
            idJson: 'tev2',
            posicao: 1,
            grupoSemaforico: {idJson: 'gs2'},
            tabelaEntreVerdesTransicoes: [{idJson: 'tevt2'}]
          },
          {
            idJson: 'tev3',
            posicao: 1,
            grupoSemaforico: {idJson: 'gs3'},
            tabelaEntreVerdesTransicoes: [{idJson: 'tevt3'}]
          }
        ],
        tabelasEntreVerdesTransicoes: [
          {
            idJson: 'tevt1',
            tabelaEntreVerdes: {idJson: 'tev1'},
          },
          {
            idJson: 'tevt2',
            tabelaEntreVerdes: {idJson: 'tev2'},
          },
          {
            idJson: 'tevt3',
            tabelaEntreVerdes: {idJson: 'tev3'},
          }
        ],
        vermelhoIntermitenteMin: 10,
        vermelhoIntermitenteMax: 20,
        vermelhoLimpezaPedestreMin: 0,
        vermelhoLimpezaPedestreMax: 10,
        vermelhoLimpezaVeicularMin: 4,
        vermelhoLimpezaVeicularMax: 7,
        atrasoGrupoMin: 0,
        atrasoGrupoMax: 5,
        amareloMin: 3,
        amareloMax: 10,
        limiteTabelasEntreVerdes: 2
      };
      deferred = $q.defer();
      spyOn(influuntAlert, 'delete').and.returnValue(deferred.promise);
      WizardControladores.fakeInicializaWizard(scope, $q, objeto, scope.inicializaEntreVerdes);
    }));
    
    describe('inicializa os entreverdes', function () {
      it('Deve montar e selecionar os itens', function() {
        expect(scope.currentAnel.posicao).toBe(1);
        expect(scope.currentGruposSemaforicos.length).toBe(2);
        expect(scope.currentGrupoSemaforico.posicao).toBe(1);
        expect(scope.currentTabelasEntreVerdes.length).toBe(1);
        expect(scope.currentTabelaEntreVerdes.posicao).toBe(1);

        expect(scope.limites(scope.currentGruposSemaforicos[0]).tempoVermelhoIntermitente.min).toBe(10);
        expect(scope.limites(scope.currentGruposSemaforicos[0]).tempoVermelhoIntermitente.max).toBe(20);
        expect(scope.limites(scope.currentGruposSemaforicos[0]).tempoVermelhoLimpeza.min).toBe(4);
        expect(scope.limites(scope.currentGruposSemaforicos[0]).tempoVermelhoLimpeza.max).toBe(7);
        expect(scope.limites(scope.currentGruposSemaforicos[0]).tempoAtrasoGrupo.min).toBe(0);
        expect(scope.limites(scope.currentGruposSemaforicos[0]).tempoAtrasoGrupo.max).toBe(5);
        expect(scope.limites(scope.currentGruposSemaforicos[0]).tempoAmarelo.min).toBe(3);
        expect(scope.limites(scope.currentGruposSemaforicos[0]).tempoAmarelo.max).toBe(10);

        expect(scope.limites(scope.currentGruposSemaforicos[1]).tempoVermelhoLimpeza.min).toBe(0);
        expect(scope.limites(scope.currentGruposSemaforicos[1]).tempoVermelhoLimpeza.max).toBe(10);

        expect(scope.limiteTabelasEntreVerdes).toBe(2);
      });

      it('Deve retorna o tempo (amarelo ou vermelho intermitente) de acordo com o tipo do grupo selecionado', function() {
        expect(scope.amareloOuVermelho(scope.currentGrupoSemaforico, false)).toBe('Amarelo');
        expect(scope.amareloOuVermelho(scope.currentGrupoSemaforico, true)).toBe('tempoAmarelo');
        expect(scope.amareloOuVermelhoColor(scope.currentGrupoSemaforico)).toBe('amarelo');

        scope.selecionaGrupoSemaforico(scope.currentGruposSemaforicos[1], 1);

        expect(scope.amareloOuVermelho(scope.currentGrupoSemaforico, false)).toBe('Vermelho Intermitente');
        expect(scope.amareloOuVermelho(scope.currentGrupoSemaforico, true)).toBe('tempoVermelhoIntermitente');
        expect(scope.amareloOuVermelhoColor(scope.currentGrupoSemaforico)).toBe('vermelho-intermitente');
      });
    });

    describe('adicionar e remover novas tabelas', function () {
      it('Deve adicionar uma nova tabela até o limite permitido', function() {
        expect(scope.currentTabelasEntreVerdes.length).toBe(1);

        scope.adicionarTabelaEntreVerdes();
        expect(scope.currentTabelasEntreVerdes.length).toBe(2);
        expect(scope.currentTabelaEntreVerdes.posicao).toBe(2);

        scope.adicionarTabelaEntreVerdes();
        expect(scope.currentTabelasEntreVerdes.length).toBe(2);
        expect(scope.currentTabelaEntreVerdes.posicao).toBe(2);

        scope.removerTabelaEntreVerdes(scope.currentTabelaEntreVerdes);
        deferred.resolve(true);
        scope.$apply();

        expect(scope.currentTabelasEntreVerdes.length).toBe(1);
        expect(scope.currentTabelaEntreVerdes.posicao).toBe(1);
      });
    });

    describe('verifica o check de não há nada a preencher', function () {
      it('deve identificar que algo foi preenchido', function() {
        expect(scope.possuiInformacoesPreenchidas()).not.toBeTruthy();
        expect(scope.podeSalvar()).not.toBeTruthy();

        scope.currentTabelasEntreVerdesTransicoes[0].tempoVermelhoLimpeza = 5;
        scope.$apply();
        expect(scope.possuiInformacoesPreenchidas()).toBeTruthy();
        expect(scope.podeSalvar()).not.toBeTruthy();

        scope.selecionaAnelEntreVerdes(1);
        expect(scope.possuiInformacoesPreenchidas()).not.toBeTruthy();
        expect(scope.podeSalvar()).not.toBeTruthy();
        
        scope.currentTabelasEntreVerdesTransicoes[0].tempoVermelhoLimpeza = 3;
        scope.$apply();

        expect(scope.possuiInformacoesPreenchidas()).toBeTruthy();
        expect(scope.podeSalvar()).not.toBeTruthy();

        scope.selecionaAnelEntreVerdes(0);
        scope.selecionaGrupoSemaforico(scope.currentGruposSemaforicos[1], 1);
        scope.currentTabelasEntreVerdesTransicoes[0].tempoVermelhoLimpeza = 4;
        scope.$apply();
        expect(scope.possuiInformacoesPreenchidas()).toBeTruthy();
        expect(scope.podeSalvar()).toBeTruthy();
      });
    });
    
    describe('Deve tratar o modo Intermitente ou Apagado', function () {
      it('Deve inicializar o modoIntermitenteOuApagado', function () {
        scope.inicializaModoIntermitenteOuApagado();
        expect(scope.modoIntermitenteOuApagado[1][1]).toBe('t1');
        expect(scope.modoIntermitenteOuApagado[1][2]).toBe('t4');
        expect(scope.modoIntermitenteOuApagado[2][2]).toBe('t2');
        expect(scope.modoIntermitenteOuApagado[3][1]).toBe('t3');

        expect(_.find(scope.objeto.transicoes, {idJson: 't1'}).modoIntermitenteOuApagado).toBeTruthy();
        expect(_.find(scope.objeto.transicoes, {idJson: 't4'}).modoIntermitenteOuApagado).toBeTruthy();

        expect(_.find(scope.objeto.transicoes, {idJson: 't2'}).modoIntermitenteOuApagado).toBeTruthy();
        expect(_.find(scope.objeto.transicoes, {idJson: 't5'}).modoIntermitenteOuApagado).toBeFalsy();

        expect(_.find(scope.objeto.transicoes, {idJson: 't3'}).modoIntermitenteOuApagado).toBeTruthy();
      });

      it('Deve alterar o modoIntermitenteOuApagado', function () {
        scope.inicializaModoIntermitenteOuApagado();
        scope.changeModoAmareloIntermitenteOuApagado('t5');
        scope.changeModoAmareloIntermitenteOuApagado('t2');
        
        expect(_.find(scope.objeto.transicoes, {idJson: 't5'}).modoIntermitenteOuApagado).toBeTruthy();
        expect(_.find(scope.objeto.transicoes, {idJson: 't2'}).modoIntermitenteOuApagado).toBeFalsy();

        scope.changeModoAmareloIntermitenteOuApagado('t5');
        scope.changeModoAmareloIntermitenteOuApagado('t2');

        expect(_.find(scope.objeto.transicoes, {idJson: 't2'}).modoIntermitenteOuApagado).toBeTruthy();
        expect(_.find(scope.objeto.transicoes, {idJson: 't5'}).modoIntermitenteOuApagado).toBeFalsy();
      });
    });
  });
});
