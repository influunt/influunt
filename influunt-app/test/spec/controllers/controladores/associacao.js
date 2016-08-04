'use strict';

xdescribe('Controller: ControladoresAssociacaoCtrl', function () {

  // load the controller's module
  beforeEach(module('influuntApp', function(RestangularProvider) {
    RestangularProvider.setBaseUrl('');
  }));

  var ControladoresAssociacaoCtrl,
    scope,
    $httpBackend,
    $state,
    $q;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope, _$q_, _$httpBackend_, _$state_) {
    scope = $rootScope.$new();
    ControladoresAssociacaoCtrl = $controller('ControladoresAssociacaoCtrl', {
      $scope: scope
      // place here mocked dependencies
    });

    $httpBackend = _$httpBackend_;
    $q = _$q_;
    $state = _$state_;
  }));

  describe('Wizard para novo controlador', function() {
    var helpers;
    beforeEach(function() {
      helpers = {cidades:[{},{}],fabricantes:[{},{},{}]};
      $httpBackend.expectGET('/helpers/controlador').respond(helpers);
      scope.inicializaWizard();
      $httpBackend.flush();
    });

    describe('InicializaAssociacao', function() {
      beforeEach(function() {
        var objeto = {id: 'ba485d0d-2fe7-4137-8a95-180de014d38d',localizacao: 'test geral',numeroSMEE: 'teste',numeroSMEEConjugado1: '123',numeroSMEEConjugado2: '123',numeroSMEEConjugado3: '123',firmware: '123',latitude: -23.552224894823986,longitude: -46.66601657867431,dataCriacao: '12/07/2016 10:34:48',dataAtualizacao: '12/07/2016 10:35:35',CLC: '51.000.0999',area: {id: '3a480786-47a2-11e6-9695-1936c437c22c',descricao: 51,dataCriacao: '11/07/2016 17:01:16',dataAtualizacao: '11/07/2016 17:01:16',cidade: {id: '3a3aaa46-47a2-11e6-9695-1936c437c22c',areas: []},limites: []},modelo: {id: '3a59313c-47a2-11e6-9695-1936c437c22c',descricao: 'Desc modelo',dataCriacao: '11/07/2016 17:01:17',dataAtualizacao: '11/07/2016 17:01:17',fabricante: {id: '3a4951fe-47a2-11e6-9695-1936c437c22c',nome: 'Raro Labs',dataCriacao: '11/07/2016 17:01:16',dataAtualizacao: '11/07/2016 17:01:16',modelos: [{id: '3a59313c-47a2-11e6-9695-1936c437c22c',descricao: 'Desc modelo',dataCriacao: '11/07/2016 17:01:17',dataAtualizacao: '11/07/2016 17:01:17',configuracao: {id: '3a50ebd0-47a2-11e6-9695-1936c437c22c',descricao: 'DESC',limiteEstagio: 5,limiteGrupoSemaforico: 5,limiteAnel: 5,limiteDetectorPedestre: 5,limiteDetectorVeicular: 5,dataCriacao: '11/07/2016 17:01:17',dataAtualizacao: '11/07/2016 17:01:17'}}]},configuracao: {id: '3a50ebd0-47a2-11e6-9695-1936c437c22c',descricao: 'DESC',limiteEstagio: 5,limiteGrupoSemaforico: 5,limiteAnel: 5,limiteDetectorPedestre: 5,limiteDetectorVeicular: 5,dataCriacao: '11/07/2016 17:01:17',dataAtualizacao: '11/07/2016 17:01:17'}},aneis: [{id: '0d26ebb2-cf20-4a0b-b80e-f07f4e26b75b',ativo: false,posicao: 5,quantidadeGrupoPedestre: 0,quantidadeGrupoVeicular: 0,quantidadeDetectorPedestre: 0,quantidadeDetectorVeicular: 0,dataCriacao: '12/07/2016 10:34:48',dataAtualizacao: '12/07/2016 10:35:35',estagios: [],gruposSemaforicos: []},{id: '62886abb-7f5d-4bff-abab-6d49d3dbe0f5',numeroSMEE: 'tsete',ativo: true,posicao: 1,latitude: -23.55080863515256,longitude: -46.66460037231445,quantidadeGrupoPedestre: 2,quantidadeGrupoVeicular: 2,quantidadeDetectorPedestre: 2,quantidadeDetectorVeicular: 2,dataCriacao: '12/07/2016 10:34:48',dataAtualizacao: '12/07/2016 10:35:35',estagios: [{id: '3eebfc8a-9570-4882-ab3a-1ec34454d092',imagem: {id: 'caca21aa-cea2-4fca-b6dc-6a69b92e53cb',filename: 'Screen Shot 2016-07-07 at 16.12.27.png',contentType: 'image/png',dataCriacao: '12/07/2016 10:35:18',dataAtualizacao: '12/07/2016 10:35:18'},demandaPrioritaria: false,dataCriacao: '12/07/2016 10:35:35',dataAtualizacao: '12/07/2016 10:35:35',origemDeTransicoesProibidas: [],destinoDeTransicoesProibidas: [],alternativaDeTransicoesProibidas: [],estagiosGruposSemaforicos: []},{id: '7d5f3052-1d2a-4495-a185-74d2b3567475',imagem: {id: 'b2ae23b2-36c8-47e2-ba2a-3ade102545ef',filename: 'Screen Shot 2016-07-04 at 15.16.24.png',contentType: 'image/png',dataCriacao: '12/07/2016 10:35:18',dataAtualizacao: '12/07/2016 10:35:18'},demandaPrioritaria: false,dataCriacao: '12/07/2016 10:35:35',dataAtualizacao: '12/07/2016 10:35:35',origemDeTransicoesProibidas: [],destinoDeTransicoesProibidas: [],alternativaDeTransicoesProibidas: [],estagiosGruposSemaforicos: []},{id: 'eb82ec77-2a6f-48a9-8df8-0e3fda621712',imagem: {id: '59a81939-3021-4e1e-b2dd-b88c6579fc16',filename: '13406967_1003062549749489_543993289383831132_n.jpg',contentType: 'image/jpeg',dataCriacao: '12/07/2016 10:35:18',dataAtualizacao: '12/07/2016 10:35:18'},demandaPrioritaria: false,dataCriacao: '12/07/2016 10:35:35',dataAtualizacao: '12/07/2016 10:35:35',origemDeTransicoesProibidas: [],destinoDeTransicoesProibidas: [],alternativaDeTransicoesProibidas: [],estagiosGruposSemaforicos: []},{id: 'fd232a7a-ff58-445e-a492-fa2f70c85ef1',imagem: {id: '039061a5-3704-42bb-8d27-773acee98346',filename: 'Screen Shot 2016-06-24 at 18.47.14.png',contentType: 'image/png',dataCriacao: '12/07/2016 10:35:18',dataAtualizacao: '12/07/2016 10:35:18'},demandaPrioritaria: false,dataCriacao: '12/07/2016 10:35:35',dataAtualizacao: '12/07/2016 10:35:35',origemDeTransicoesProibidas: [],destinoDeTransicoesProibidas: [],alternativaDeTransicoesProibidas: [],estagiosGruposSemaforicos: []}],gruposSemaforicos: [{id: '2bdec225-1b9e-43c4-804a-88940d8a51a2',dataCriacao: '12/07/2016 10:35:35',dataAtualizacao: '12/07/2016 10:35:35',posicao: 1,anel: {id: '62886abb-7f5d-4bff-abab-6d49d3dbe0f5',numeroSMEE: 'tsete',ativo: true,posicao: 1,latitude: -23.55080863515256,longitude: -46.66460037231445,quantidadeGrupoPedestre: 2,quantidadeGrupoVeicular: 2,quantidadeDetectorPedestre: 2,quantidadeDetectorVeicular: 2,dataCriacao: '12/07/2016 10:34:48',dataAtualizacao: '12/07/2016 10:35:35',estagios: [{id: '3eebfc8a-9570-4882-ab3a-1ec34454d092',imagem: {id: 'caca21aa-cea2-4fca-b6dc-6a69b92e53cb',filename: 'Screen Shot 2016-07-07 at 16.12.27.png',contentType: 'image/png',dataCriacao: '12/07/2016 10:35:18',dataAtualizacao: '12/07/2016 10:35:18'},demandaPrioritaria: false,dataCriacao: '12/07/2016 10:35:35',dataAtualizacao: '12/07/2016 10:35:35',origemDeTransicoesProibidas: [],destinoDeTransicoesProibidas: [],alternativaDeTransicoesProibidas: [],estagiosGruposSemaforicos: []},{id: '7d5f3052-1d2a-4495-a185-74d2b3567475',imagem: {id: 'b2ae23b2-36c8-47e2-ba2a-3ade102545ef',filename: 'Screen Shot 2016-07-04 at 15.16.24.png',contentType: 'image/png',dataCriacao: '12/07/2016 10:35:18',dataAtualizacao: '12/07/2016 10:35:18'},demandaPrioritaria: false,dataCriacao: '12/07/2016 10:35:35',dataAtualizacao: '12/07/2016 10:35:35',origemDeTransicoesProibidas: [],destinoDeTransicoesProibidas: [],alternativaDeTransicoesProibidas: [],estagiosGruposSemaforicos: []},{id: 'eb82ec77-2a6f-48a9-8df8-0e3fda621712',imagem: {id: '59a81939-3021-4e1e-b2dd-b88c6579fc16',filename: '13406967_1003062549749489_543993289383831132_n.jpg',contentType: 'image/jpeg',dataCriacao: '12/07/2016 10:35:18',dataAtualizacao: '12/07/2016 10:35:18'},demandaPrioritaria: false,dataCriacao: '12/07/2016 10:35:35',dataAtualizacao: '12/07/2016 10:35:35',origemDeTransicoesProibidas: [],destinoDeTransicoesProibidas: [],alternativaDeTransicoesProibidas: [],estagiosGruposSemaforicos: []},{id: 'fd232a7a-ff58-445e-a492-fa2f70c85ef1',imagem: {id: '039061a5-3704-42bb-8d27-773acee98346',filename: 'Screen Shot 2016-06-24 at 18.47.14.png',contentType: 'image/png',dataCriacao: '12/07/2016 10:35:18',dataAtualizacao: '12/07/2016 10:35:18'},demandaPrioritaria: false,dataCriacao: '12/07/2016 10:35:35',dataAtualizacao: '12/07/2016 10:35:35',origemDeTransicoesProibidas: [],destinoDeTransicoesProibidas: [],alternativaDeTransicoesProibidas: [],estagiosGruposSemaforicos: []}],gruposSemaforicos: []},verdesConflitantesOrigem: [],verdesConflitantesDestino: [],estagioGrupoSemaforicos: [],transicoes: [],tabelasEntreVerdes: [{id: 'a8ff9877-158d-472a-8c27-9373c1bf6154',dataCriacao: '12/07/2016 10:35:35',dataAtualizacao: '12/07/2016 10:35:35',descricao: 'PADRÃO'}]},{id: '81d3f687-7440-45f3-b600-a47e06a82170',dataCriacao: '12/07/2016 10:35:35',dataAtualizacao: '12/07/2016 10:35:35',posicao: 3,anel: {id: '62886abb-7f5d-4bff-abab-6d49d3dbe0f5',numeroSMEE: 'tsete',ativo: true,posicao: 1,latitude: -23.55080863515256,longitude: -46.66460037231445,quantidadeGrupoPedestre: 2,quantidadeGrupoVeicular: 2,quantidadeDetectorPedestre: 2,quantidadeDetectorVeicular: 2,dataCriacao: '12/07/2016 10:34:48',dataAtualizacao: '12/07/2016 10:35:35',estagios: [{id: '3eebfc8a-9570-4882-ab3a-1ec34454d092',imagem: {id: 'caca21aa-cea2-4fca-b6dc-6a69b92e53cb',filename: 'Screen Shot 2016-07-07 at 16.12.27.png',contentType: 'image/png',dataCriacao: '12/07/2016 10:35:18',dataAtualizacao: '12/07/2016 10:35:18'},demandaPrioritaria: false,dataCriacao: '12/07/2016 10:35:35',dataAtualizacao: '12/07/2016 10:35:35',origemDeTransicoesProibidas: [],destinoDeTransicoesProibidas: [],alternativaDeTransicoesProibidas: [],estagiosGruposSemaforicos: []},{id: '7d5f3052-1d2a-4495-a185-74d2b3567475',imagem: {id: 'b2ae23b2-36c8-47e2-ba2a-3ade102545ef',filename: 'Screen Shot 2016-07-04 at 15.16.24.png',contentType: 'image/png',dataCriacao: '12/07/2016 10:35:18',dataAtualizacao: '12/07/2016 10:35:18'},demandaPrioritaria: false,dataCriacao: '12/07/2016 10:35:35',dataAtualizacao: '12/07/2016 10:35:35',origemDeTransicoesProibidas: [],destinoDeTransicoesProibidas: [],alternativaDeTransicoesProibidas: [],estagiosGruposSemaforicos: []},{id: 'eb82ec77-2a6f-48a9-8df8-0e3fda621712',imagem: {id: '59a81939-3021-4e1e-b2dd-b88c6579fc16',filename: '13406967_1003062549749489_543993289383831132_n.jpg',contentType: 'image/jpeg',dataCriacao: '12/07/2016 10:35:18',dataAtualizacao: '12/07/2016 10:35:18'},demandaPrioritaria: false,dataCriacao: '12/07/2016 10:35:35',dataAtualizacao: '12/07/2016 10:35:35',origemDeTransicoesProibidas: [],destinoDeTransicoesProibidas: [],alternativaDeTransicoesProibidas: [],estagiosGruposSemaforicos: []},{id: 'fd232a7a-ff58-445e-a492-fa2f70c85ef1',imagem: {id: '039061a5-3704-42bb-8d27-773acee98346',filename: 'Screen Shot 2016-06-24 at 18.47.14.png',contentType: 'image/png',dataCriacao: '12/07/2016 10:35:18',dataAtualizacao: '12/07/2016 10:35:18'},demandaPrioritaria: false,dataCriacao: '12/07/2016 10:35:35',dataAtualizacao: '12/07/2016 10:35:35',origemDeTransicoesProibidas: [],destinoDeTransicoesProibidas: [],alternativaDeTransicoesProibidas: [],estagiosGruposSemaforicos: []}],gruposSemaforicos: []},verdesConflitantesOrigem: [],verdesConflitantesDestino: [],estagioGrupoSemaforicos: [],transicoes: [],tabelasEntreVerdes: [{id: 'b190fbde-e030-4be9-bf67-7fe90b687306',dataCriacao: '12/07/2016 10:35:35',dataAtualizacao: '12/07/2016 10:35:35',descricao: 'PADRÃO'}]},{id: '8647d58f-83a5-4d2d-a67a-3ffb02b7531b',dataCriacao: '12/07/2016 10:35:35',dataAtualizacao: '12/07/2016 10:35:35',posicao: 2,anel: {id: '62886abb-7f5d-4bff-abab-6d49d3dbe0f5',numeroSMEE: 'tsete',ativo: true,posicao: 1,latitude: -23.55080863515256,longitude: -46.66460037231445,quantidadeGrupoPedestre: 2,quantidadeGrupoVeicular: 2,quantidadeDetectorPedestre: 2,quantidadeDetectorVeicular: 2,dataCriacao: '12/07/2016 10:34:48',dataAtualizacao: '12/07/2016 10:35:35',estagios: [{id: '3eebfc8a-9570-4882-ab3a-1ec34454d092',imagem: {id: 'caca21aa-cea2-4fca-b6dc-6a69b92e53cb',filename: 'Screen Shot 2016-07-07 at 16.12.27.png',contentType: 'image/png',dataCriacao: '12/07/2016 10:35:18',dataAtualizacao: '12/07/2016 10:35:18'},demandaPrioritaria: false,dataCriacao: '12/07/2016 10:35:35',dataAtualizacao: '12/07/2016 10:35:35',origemDeTransicoesProibidas: [],destinoDeTransicoesProibidas: [],alternativaDeTransicoesProibidas: [],estagiosGruposSemaforicos: []},{id: '7d5f3052-1d2a-4495-a185-74d2b3567475',imagem: {id: 'b2ae23b2-36c8-47e2-ba2a-3ade102545ef',filename: 'Screen Shot 2016-07-04 at 15.16.24.png',contentType: 'image/png',dataCriacao: '12/07/2016 10:35:18',dataAtualizacao: '12/07/2016 10:35:18'},demandaPrioritaria: false,dataCriacao: '12/07/2016 10:35:35',dataAtualizacao: '12/07/2016 10:35:35',origemDeTransicoesProibidas: [],destinoDeTransicoesProibidas: [],alternativaDeTransicoesProibidas: [],estagiosGruposSemaforicos: []},{id: 'eb82ec77-2a6f-48a9-8df8-0e3fda621712',imagem: {id: '59a81939-3021-4e1e-b2dd-b88c6579fc16',filename: '13406967_1003062549749489_543993289383831132_n.jpg',contentType: 'image/jpeg',dataCriacao: '12/07/2016 10:35:18',dataAtualizacao: '12/07/2016 10:35:18'},demandaPrioritaria: false,dataCriacao: '12/07/2016 10:35:35',dataAtualizacao: '12/07/2016 10:35:35',origemDeTransicoesProibidas: [],destinoDeTransicoesProibidas: [],alternativaDeTransicoesProibidas: [],estagiosGruposSemaforicos: []},{id: 'fd232a7a-ff58-445e-a492-fa2f70c85ef1',imagem: {id: '039061a5-3704-42bb-8d27-773acee98346',filename: 'Screen Shot 2016-06-24 at 18.47.14.png',contentType: 'image/png',dataCriacao: '12/07/2016 10:35:18',dataAtualizacao: '12/07/2016 10:35:18'},demandaPrioritaria: false,dataCriacao: '12/07/2016 10:35:35',dataAtualizacao: '12/07/2016 10:35:35',origemDeTransicoesProibidas: [],destinoDeTransicoesProibidas: [],alternativaDeTransicoesProibidas: [],estagiosGruposSemaforicos: []}],gruposSemaforicos: []},verdesConflitantesOrigem: [],verdesConflitantesDestino: [],estagioGrupoSemaforicos: [],transicoes: [],tabelasEntreVerdes: [{id: '870cb452-23e5-4dde-b023-d8fee48ec3c5',dataCriacao: '12/07/2016 10:35:35',dataAtualizacao: '12/07/2016 10:35:35',descricao: 'PADRÃO'}]},{id: 'a2c40ef1-6160-4f93-aaca-694cdeab7720',dataCriacao: '12/07/2016 10:35:35',dataAtualizacao: '12/07/2016 10:35:35',posicao: 4,anel: {id: '62886abb-7f5d-4bff-abab-6d49d3dbe0f5',numeroSMEE: 'tsete',ativo: true,posicao: 1,latitude: -23.55080863515256,longitude: -46.66460037231445,quantidadeGrupoPedestre: 2,quantidadeGrupoVeicular: 2,quantidadeDetectorPedestre: 2,quantidadeDetectorVeicular: 2,dataCriacao: '12/07/2016 10:34:48',dataAtualizacao: '12/07/2016 10:35:35',estagios: [{id: '3eebfc8a-9570-4882-ab3a-1ec34454d092',imagem: {id: 'caca21aa-cea2-4fca-b6dc-6a69b92e53cb',filename: 'Screen Shot 2016-07-07 at 16.12.27.png',contentType: 'image/png',dataCriacao: '12/07/2016 10:35:18',dataAtualizacao: '12/07/2016 10:35:18'},demandaPrioritaria: false,dataCriacao: '12/07/2016 10:35:35',dataAtualizacao: '12/07/2016 10:35:35',origemDeTransicoesProibidas: [],destinoDeTransicoesProibidas: [],alternativaDeTransicoesProibidas: [],estagiosGruposSemaforicos: []},{id: '7d5f3052-1d2a-4495-a185-74d2b3567475',imagem: {id: 'b2ae23b2-36c8-47e2-ba2a-3ade102545ef',filename: 'Screen Shot 2016-07-04 at 15.16.24.png',contentType: 'image/png',dataCriacao: '12/07/2016 10:35:18',dataAtualizacao: '12/07/2016 10:35:18'},demandaPrioritaria: false,dataCriacao: '12/07/2016 10:35:35',dataAtualizacao: '12/07/2016 10:35:35',origemDeTransicoesProibidas: [],destinoDeTransicoesProibidas: [],alternativaDeTransicoesProibidas: [],estagiosGruposSemaforicos: []},{id: 'eb82ec77-2a6f-48a9-8df8-0e3fda621712',imagem: {id: '59a81939-3021-4e1e-b2dd-b88c6579fc16',filename: '13406967_1003062549749489_543993289383831132_n.jpg',contentType: 'image/jpeg',dataCriacao: '12/07/2016 10:35:18',dataAtualizacao: '12/07/2016 10:35:18'},demandaPrioritaria: false,dataCriacao: '12/07/2016 10:35:35',dataAtualizacao: '12/07/2016 10:35:35',origemDeTransicoesProibidas: [],destinoDeTransicoesProibidas: [],alternativaDeTransicoesProibidas: [],estagiosGruposSemaforicos: []},{id: 'fd232a7a-ff58-445e-a492-fa2f70c85ef1',imagem: {id: '039061a5-3704-42bb-8d27-773acee98346',filename: 'Screen Shot 2016-06-24 at 18.47.14.png',contentType: 'image/png',dataCriacao: '12/07/2016 10:35:18',dataAtualizacao: '12/07/2016 10:35:18'},demandaPrioritaria: false,dataCriacao: '12/07/2016 10:35:35',dataAtualizacao: '12/07/2016 10:35:35',origemDeTransicoesProibidas: [],destinoDeTransicoesProibidas: [],alternativaDeTransicoesProibidas: [],estagiosGruposSemaforicos: []}],gruposSemaforicos: []},verdesConflitantesOrigem: [],verdesConflitantesDestino: [],estagioGrupoSemaforicos: [],transicoes: [],tabelasEntreVerdes: [{id: '8b6d8fb5-399a-4082-a636-11d5b6431596',dataCriacao: '12/07/2016 10:35:35',dataAtualizacao: '12/07/2016 10:35:35',descricao: 'PADRÃO'}]}]},{id: '90f71926-9e8d-47c7-87c6-4dc0bbd3f9bd',ativo: false,posicao: 3,quantidadeGrupoPedestre: 0,quantidadeGrupoVeicular: 0,quantidadeDetectorPedestre: 0,quantidadeDetectorVeicular: 0,dataCriacao: '12/07/2016 10:34:48',dataAtualizacao: '12/07/2016 10:35:35',estagios: [],gruposSemaforicos: []},{id: 'cf3d5296-36b6-466c-82a0-e83f6e09151b',ativo: false,posicao: 2,quantidadeGrupoPedestre: 0,quantidadeGrupoVeicular: 0,quantidadeDetectorPedestre: 0,quantidadeDetectorVeicular: 0,dataCriacao: '12/07/2016 10:34:48',dataAtualizacao: '12/07/2016 10:35:35',estagios: [],gruposSemaforicos: []},{id: 'd985ac80-0059-4248-9cb9-849b86de3d26',ativo: false,posicao: 4,quantidadeGrupoPedestre: 0,quantidadeGrupoVeicular: 0,quantidadeDetectorPedestre: 0,quantidadeDetectorVeicular: 0,dataCriacao: '12/07/2016 10:34:48',dataAtualizacao: '12/07/2016 10:35:35',estagios: [],gruposSemaforicos: []}]};
        WizardControladores.fakeInicializaWizard(scope, $q, objeto, scope.inicializaAssociacao);
      });

      it('Deve carregar o scope.aneis com a lista de aneis ativos', function() {
        expect(scope.aneis.length).toBe(1);
      });

      it('Não deve acessar a tela de associações caso não haja ao menos um estágio declarado', function () {
          scope.objeto = {idControlador: '1234567', aneis: [{}], modelo: {configuracao: {limiteAnel: 4}}};
          scope.inicializaAssociacao();
          scope.$apply();

          expect($state.current.name).not.toBe('app.wizard_controladores.associacao');
        });

      it('Os grupos semaforicos deverão estar ordenados por posicao', function() {
        expect(scope.aneis[0].gruposSemaforicos[0].posicao).toBe(1);
        expect(scope.aneis[0].gruposSemaforicos[1].posicao).toBe(2);
      });
    });
  });

  describe('Funções auxiliares', function () {
    describe('toggleEstagioAtivado', function () {
      var grupo, estagio;

      beforeEach(function() {
        estagio = {id: '123'};
        grupo = {
          estagioGrupoSemaforicos: [{estagio: {id: '123'}}],
          estagiosAtivados: {'123': true}
        };
      });

      it('Deve desativar o estagio do grupo semaforicos caso este estaja ativo', function () {
        grupo.estagioGrupoSemaforicos[0].ativo = true;
        scope.toggleEstagioAtivado(grupo, estagio);
        expect(grupo.estagioGrupoSemaforicos[0].ativo).not.toBeTruthy();
      });

      it('Deve ativar o estagio do grupo semaforicos caso este não estaja ativo', function () {
        grupo.estagioGrupoSemaforicos[0].ativo = true;
        scope.toggleEstagioAtivado(grupo, estagio);
        expect(grupo.estagioGrupoSemaforicos[0].ativo).not.toBeTruthy();
      });

      it('Não deve alterar o status de nenhum estagio caso um estagio inexistente seja apresentado', function () {
        var grupoOriginal = _.cloneDeep(grupo);
        scope.toggleEstagioAtivado(grupo, {id: 456});
        expect(grupoOriginal).toEqual(grupo);
      });
    });

    describe('limpaTempoPermanencia', function () {
      it('quando o usuário desmarca o checkbox para "desativação de tempo máximo", ' +
        'o tempo de permanecia do estágio deverá ser excluido.', function () {
          var estagio = {tempoMaximoPermanencia: 1};
          scope.limpaTempoPermanencia(estagio);
          expect(estagio.tempoMaximoPermanencia).toBeNull();
        });
    });
  });

});
