'use strict';

describe('Controller: ControladoresAssociacaoCtrl', function () {

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
        var objeto = {'id':'13ae1e96-f66c-41e5-8649-70da9a36c3e7','descricao':'teste','numeroSMEE':'teste','numeroSMEEConjugado1':'123','numeroSMEEConjugado2':'123','numeroSMEEConjugado3':'132','firmware':'123','latitude':-19.9809899,'longitude':-44.03494549999999,'dataCriacao':'24/06/2016 14:18:15','dataAtualizacao':'24/06/2016 17:41:38','CLC':'1.000.0999','area':{'id':'41648354-f83a-4680-8571-621630e16008','descricao':1,'dataCriacao':'24/06/2016 11:38:32','dataAtualizacao':'24/06/2016 11:38:32','cidade':{'id':'15b83474-f4bf-499b-a41d-a12f04429cb4','nome':'Belo Horizonte','dataCriacao':'24/06/2016 11:38:24','dataAtualizacao':'24/06/2016 11:38:24','areas':[{'id':'41648354-f83a-4680-8571-621630e16008','descricao':1},{'id':'ed522abb-85c4-425d-a65d-d5947cd46888','descricao':2}]},'limites':[]},'modelo':{'id':'cb0489e1-a0ad-4145-a2cf-7cca7e19f8f2','descricao':'opa 2 fab2','dataCriacao':'24/06/2016 11:39:59','dataAtualizacao':'24/06/2016 11:39:59','fabricante':{'id':'b195beea-4f3b-4614-83f3-2da0f7d2874b','nome':'fabricante 2','dataCriacao':'24/06/2016 11:39:59','dataAtualizacao':'24/06/2016 11:39:59','modelos':[{'id':'cb0489e1-a0ad-4145-a2cf-7cca7e19f8f2','descricao':'opa 2 fab2','configuracao':{'id':'f3e4293d-e406-4f0d-a2cb-5ddae54e3a72','descricao':'opa 2','limiteEstagio':8,'limiteGrupoSemaforico':8,'limiteAnel':8,'limiteDetectorPedestre':8,'limiteDetectorVeicular':32,'dataCriacao':'24/06/2016 11:39:07','dataAtualizacao':'24/06/2016 11:39:07'}},{'id':'dd1b1175-a2ea-4dde-aa0d-5ec80ad886c7','descricao':'opa 1 fab2','configuracao':{'id':'f20a1b3a-c43b-43da-a9ba-6e2d7da4afc9','descricao':'opa 1','limiteEstagio':4,'limiteGrupoSemaforico':4,'limiteAnel':4,'limiteDetectorPedestre':4,'limiteDetectorVeicular':16,'dataCriacao':'24/06/2016 11:38:58','dataAtualizacao':'24/06/2016 11:38:58'}}]},'configuracao':{'id':'f3e4293d-e406-4f0d-a2cb-5ddae54e3a72','descricao':'opa 2','limiteEstagio':8,'limiteGrupoSemaforico':8,'limiteAnel':8,'limiteDetectorPedestre':8,'limiteDetectorVeicular':32,'dataCriacao':'24/06/2016 11:39:07','dataAtualizacao':'24/06/2016 11:39:07'}},'aneis':[{'id':'29e5bfb6-6e18-4208-ba17-8a627bda21ab','numeroSMEE':'laksfdj','descricao':'Estagio opa','ativo':true,'posicao':1,'latitude':-19.9431826,'longitude':-43.911083599999984,'quantidadeGrupoPedestre':2,'quantidadeGrupoVeicular':2,'quantidadeDetectorPedestre':2,'quantidadeDetectorVeicular':2,'dataCriacao':'24/06/2016 14:18:15','dataAtualizacao':'24/06/2016 17:41:38','estagios':[{'id':'4d77cf1c-536e-480b-85ae-cb95dc7de633','imagem':{'id':'b70cd222-e70e-41f8-9b5d-8d15082c60bc','filename':'12321359_986438248070903_1173574894423312875_n.jpg','contentType':'image/jpeg','dataCriacao':'24/06/2016 15:47:46','dataAtualizacao':'24/06/2016 15:47:46'},'tempoMaximoPermanencia':12,'demandaPrioritaria':true,'dataCriacao':'24/06/2016 17:41:38','dataAtualizacao':'24/06/2016 17:41:38'},{'id':'d11740e4-f6ae-4797-b839-46fdde078374','imagem':{'id':'1e988fa7-335f-40cd-bebf-fd9befe19c69','filename':'Screen Shot 2016-06-17 at 15.08.57.png','contentType':'image/png','dataCriacao':'24/06/2016 15:47:46','dataAtualizacao':'24/06/2016 15:47:46'},'demandaPrioritaria':false,'dataCriacao':'24/06/2016 17:41:38','dataAtualizacao':'24/06/2016 17:41:38'}],'gruposSemaforicos':[{'id':'05a01a08-9d1f-4fc2-8d2a-c5b21a022861','dataCriacao':'24/06/2016 15:50:59','dataAtualizacao':'24/06/2016 17:41:38','tipo':'PEDESTRE','posicao':3,'estagioGrupoSemaforicos':[{'id':'64e79cbe-096b-407e-9a56-b71ed7debd13','dataCriacao':'24/06/2016 17:41:38','dataAtualizacao':'24/06/2016 17:41:38','ativo':true,'estagio':{'id':'d11740e4-f6ae-4797-b839-46fdde078374','imagem':{'id':'1e988fa7-335f-40cd-bebf-fd9befe19c69','filename':'Screen Shot 2016-06-17 at 15.08.57.png','contentType':'image/png','dataCriacao':'24/06/2016 15:47:46','dataAtualizacao':'24/06/2016 15:47:46'},'demandaPrioritaria':false,'dataCriacao':'24/06/2016 17:41:38','dataAtualizacao':'24/06/2016 17:41:38'}}]},{'id':'ba6a03d0-c500-4edb-80a2-ecfda27850e9','dataCriacao':'24/06/2016 15:50:59','dataAtualizacao':'24/06/2016 17:41:38','tipo':'PEDESTRE','posicao':4,'estagioGrupoSemaforicos':[{'id':'0f68dc01-3463-4485-95e5-7bdbc669218f','dataCriacao':'24/06/2016 17:41:38','dataAtualizacao':'24/06/2016 17:41:38','ativo':true,'estagio':{'id':'4d77cf1c-536e-480b-85ae-cb95dc7de633','imagem':{'id':'b70cd222-e70e-41f8-9b5d-8d15082c60bc','filename':'12321359_986438248070903_1173574894423312875_n.jpg','contentType':'image/jpeg','dataCriacao':'24/06/2016 15:47:46','dataAtualizacao':'24/06/2016 15:47:46'},'tempoMaximoPermanencia':12,'demandaPrioritaria':true,'dataCriacao':'24/06/2016 17:41:38','dataAtualizacao':'24/06/2016 17:41:38'}}]},{'id':'c8a7f4c8-6f36-4ed6-a80a-4a6d77c1df5a','dataCriacao':'24/06/2016 15:50:59','dataAtualizacao':'24/06/2016 17:41:38','tipo':'VEICULAR','posicao':1,'estagioGrupoSemaforicos':[{'id':'962d5c95-9c8a-47f3-8ff2-24cfceafc77c','dataCriacao':'24/06/2016 17:41:38','dataAtualizacao':'24/06/2016 17:41:38','ativo':true,'estagio':{'id':'4d77cf1c-536e-480b-85ae-cb95dc7de633','imagem':{'id':'b70cd222-e70e-41f8-9b5d-8d15082c60bc','filename':'12321359_986438248070903_1173574894423312875_n.jpg','contentType':'image/jpeg','dataCriacao':'24/06/2016 15:47:46','dataAtualizacao':'24/06/2016 15:47:46'},'tempoMaximoPermanencia':12,'demandaPrioritaria':true,'dataCriacao':'24/06/2016 17:41:38','dataAtualizacao':'24/06/2016 17:41:38'}}]},{'id':'fc525f6c-6ba4-463c-84ef-5631126711e8','dataCriacao':'24/06/2016 15:50:59','dataAtualizacao':'24/06/2016 17:41:38','tipo':'VEICULAR','posicao':2,'estagioGrupoSemaforicos':[{'id':'17a2006b-b4ff-4ab4-a63f-fc7f65e0596f','dataCriacao':'24/06/2016 17:41:38','dataAtualizacao':'24/06/2016 17:41:38','ativo':true,'estagio':{'id':'d11740e4-f6ae-4797-b839-46fdde078374','imagem':{'id':'1e988fa7-335f-40cd-bebf-fd9befe19c69','filename':'Screen Shot 2016-06-17 at 15.08.57.png','contentType':'image/png','dataCriacao':'24/06/2016 15:47:46','dataAtualizacao':'24/06/2016 15:47:46'},'demandaPrioritaria':false,'dataCriacao':'24/06/2016 17:41:38','dataAtualizacao':'24/06/2016 17:41:38'}}]}]},{'id':'554a799a-5510-4ca5-bf18-37b7c8a778b9','ativo':false,'quantidadeGrupoPedestre':0,'quantidadeGrupoVeicular':0,'quantidadeDetectorPedestre':0,'quantidadeDetectorVeicular':0,'dataCriacao':'24/06/2016 17:41:38','dataAtualizacao':'24/06/2016 17:41:38','estagios':[],'gruposSemaforicos':[]},{'id':'61c6028e-7084-4a23-9dde-31f54f282d6c','ativo':false,'quantidadeGrupoPedestre':0,'quantidadeGrupoVeicular':0,'quantidadeDetectorPedestre':0,'quantidadeDetectorVeicular':0,'dataCriacao':'24/06/2016 17:41:38','dataAtualizacao':'24/06/2016 17:41:38','estagios':[],'gruposSemaforicos':[]},{'id':'6ce94531-6943-4b09-b476-3658ea153659','ativo':false,'quantidadeGrupoPedestre':0,'quantidadeGrupoVeicular':0,'quantidadeDetectorPedestre':0,'quantidadeDetectorVeicular':0,'dataCriacao':'24/06/2016 17:41:38','dataAtualizacao':'24/06/2016 17:41:38','estagios':[],'gruposSemaforicos':[]},{'id':'72094c61-72f5-41bc-b3b5-24f01c2e9a0a','ativo':false,'quantidadeGrupoPedestre':0,'quantidadeGrupoVeicular':0,'quantidadeDetectorPedestre':0,'quantidadeDetectorVeicular':0,'dataCriacao':'24/06/2016 17:41:38','dataAtualizacao':'24/06/2016 17:41:38','estagios':[],'gruposSemaforicos':[]},{'id':'bf5e48bc-1612-44ae-96ba-6ace83e37f8e','ativo':false,'quantidadeGrupoPedestre':0,'quantidadeGrupoVeicular':0,'quantidadeDetectorPedestre':0,'quantidadeDetectorVeicular':0,'dataCriacao':'24/06/2016 17:41:38','dataAtualizacao':'24/06/2016 17:41:38','estagios':[],'gruposSemaforicos':[]},{'id':'c6cb37cb-5f13-4913-ae7a-e5ba1f3ec260','ativo':false,'quantidadeGrupoPedestre':0,'quantidadeGrupoVeicular':0,'quantidadeDetectorPedestre':0,'quantidadeDetectorVeicular':0,'dataCriacao':'24/06/2016 17:41:38','dataAtualizacao':'24/06/2016 17:41:38','estagios':[],'gruposSemaforicos':[]},{'id':'cf11fe89-9f46-4620-94d5-557ad198777b','ativo':false,'quantidadeGrupoPedestre':0,'quantidadeGrupoVeicular':0,'quantidadeDetectorPedestre':0,'quantidadeDetectorVeicular':0,'dataCriacao':'24/06/2016 17:41:38','dataAtualizacao':'24/06/2016 17:41:38','estagios':[],'gruposSemaforicos':[]}],'gruposSemaforicos':[{'id':'05a01a08-9d1f-4fc2-8d2a-c5b21a022861','dataCriacao':'24/06/2016 15:50:59','dataAtualizacao':'24/06/2016 17:41:38','tipo':'PEDESTRE','posicao':3,'estagioGrupoSemaforicos':[{'id':'64e79cbe-096b-407e-9a56-b71ed7debd13','dataCriacao':'24/06/2016 17:41:38','dataAtualizacao':'24/06/2016 17:41:38','ativo':true,'estagio':{'id':'d11740e4-f6ae-4797-b839-46fdde078374','imagem':{'id':'1e988fa7-335f-40cd-bebf-fd9befe19c69','filename':'Screen Shot 2016-06-17 at 15.08.57.png','contentType':'image/png','dataCriacao':'24/06/2016 15:47:46','dataAtualizacao':'24/06/2016 15:47:46'},'demandaPrioritaria':false,'dataCriacao':'24/06/2016 17:41:38','dataAtualizacao':'24/06/2016 17:41:38'}}]},{'id':'337e7d90-e6aa-4d98-a97b-e16c8185f94a','dataCriacao':'24/06/2016 15:50:59','dataAtualizacao':'24/06/2016 17:41:38','tipo':'VEICULAR','posicao':5,'estagioGrupoSemaforicos':[{'id':'a4826476-d603-44ea-a885-296ede07432d','dataCriacao':'24/06/2016 17:41:38','dataAtualizacao':'24/06/2016 17:41:38','ativo':true,'estagio':{'id':'54ad4d85-ca9d-487a-8022-1e5e100b5a3a','imagem':{'id':'e974c74b-254b-4776-81c7-bb7a9874fac7','filename':'12963901_10154615163486840_8455352346796368737_n.jpg','contentType':'image/jpeg','dataCriacao':'24/06/2016 15:48:10','dataAtualizacao':'24/06/2016 15:48:10'},'tempoMaximoPermanencia':21,'demandaPrioritaria':true,'dataCriacao':'24/06/2016 17:41:38','dataAtualizacao':'24/06/2016 17:41:38'}}]},{'id':'34d4b6a9-56b3-43b6-9bad-9f456ed353df','dataCriacao':'24/06/2016 15:50:59','dataAtualizacao':'24/06/2016 17:41:38','tipo':'PEDESTRE','posicao':8,'estagioGrupoSemaforicos':[{'id':'56873032-81b3-4eed-b7dc-1233b4e5a04d','dataCriacao':'24/06/2016 17:41:38','dataAtualizacao':'24/06/2016 17:41:38','ativo':true,'estagio':{'id':'54ad4d85-ca9d-487a-8022-1e5e100b5a3a','imagem':{'id':'e974c74b-254b-4776-81c7-bb7a9874fac7','filename':'12963901_10154615163486840_8455352346796368737_n.jpg','contentType':'image/jpeg','dataCriacao':'24/06/2016 15:48:10','dataAtualizacao':'24/06/2016 15:48:10'},'tempoMaximoPermanencia':21,'demandaPrioritaria':true,'dataCriacao':'24/06/2016 17:41:38','dataAtualizacao':'24/06/2016 17:41:38'}}]},{'id':'ba6a03d0-c500-4edb-80a2-ecfda27850e9','dataCriacao':'24/06/2016 15:50:59','dataAtualizacao':'24/06/2016 17:41:38','tipo':'PEDESTRE','posicao':4,'estagioGrupoSemaforicos':[{'id':'0f68dc01-3463-4485-95e5-7bdbc669218f','dataCriacao':'24/06/2016 17:41:38','dataAtualizacao':'24/06/2016 17:41:38','ativo':true,'estagio':{'id':'4d77cf1c-536e-480b-85ae-cb95dc7de633','imagem':{'id':'b70cd222-e70e-41f8-9b5d-8d15082c60bc','filename':'12321359_986438248070903_1173574894423312875_n.jpg','contentType':'image/jpeg','dataCriacao':'24/06/2016 15:47:46','dataAtualizacao':'24/06/2016 15:47:46'},'tempoMaximoPermanencia':12,'demandaPrioritaria':true,'dataCriacao':'24/06/2016 17:41:38','dataAtualizacao':'24/06/2016 17:41:38'}}]},{'id':'c8a7f4c8-6f36-4ed6-a80a-4a6d77c1df5a','dataCriacao':'24/06/2016 15:50:59','dataAtualizacao':'24/06/2016 17:41:38','tipo':'VEICULAR','posicao':1,'estagioGrupoSemaforicos':[{'id':'962d5c95-9c8a-47f3-8ff2-24cfceafc77c','dataCriacao':'24/06/2016 17:41:38','dataAtualizacao':'24/06/2016 17:41:38','ativo':true,'estagio':{'id':'4d77cf1c-536e-480b-85ae-cb95dc7de633','imagem':{'id':'b70cd222-e70e-41f8-9b5d-8d15082c60bc','filename':'12321359_986438248070903_1173574894423312875_n.jpg','contentType':'image/jpeg','dataCriacao':'24/06/2016 15:47:46','dataAtualizacao':'24/06/2016 15:47:46'},'tempoMaximoPermanencia':12,'demandaPrioritaria':true,'dataCriacao':'24/06/2016 17:41:38','dataAtualizacao':'24/06/2016 17:41:38'}}]},{'id':'d2ced644-bcfa-4a23-9c67-0900479e4be2','dataCriacao':'24/06/2016 15:50:59','dataAtualizacao':'24/06/2016 17:41:38','tipo':'VEICULAR','posicao':6,'estagioGrupoSemaforicos':[{'id':'9ed8ed59-e311-4b07-9853-4a4d5764c0b4','dataCriacao':'24/06/2016 17:41:38','dataAtualizacao':'24/06/2016 17:41:38','ativo':true,'estagio':{'id':'986b4dbe-9358-4eea-a199-28ab94d30e6b','imagem':{'id':'b11a4ff2-e9c5-4d07-ac50-62a166ec8dab','filename':'Screen Shot 2016-06-13 at 23.45.32.png','contentType':'image/png','dataCriacao':'24/06/2016 15:48:10','dataAtualizacao':'24/06/2016 15:48:10'},'tempoMaximoPermanencia':12,'demandaPrioritaria':true,'dataCriacao':'24/06/2016 17:41:38','dataAtualizacao':'24/06/2016 17:41:38'}}]},{'id':'da50d895-5208-4da4-a0ec-2bb4c83667f4','dataCriacao':'24/06/2016 15:50:59','dataAtualizacao':'24/06/2016 17:41:38','tipo':'PEDESTRE','posicao':7,'estagioGrupoSemaforicos':[{'id':'ffa6bb61-52b5-45bb-ba9e-373dbbb50e9f','dataCriacao':'24/06/2016 17:41:38','dataAtualizacao':'24/06/2016 17:41:38','ativo':true,'estagio':{'id':'986b4dbe-9358-4eea-a199-28ab94d30e6b','imagem':{'id':'b11a4ff2-e9c5-4d07-ac50-62a166ec8dab','filename':'Screen Shot 2016-06-13 at 23.45.32.png','contentType':'image/png','dataCriacao':'24/06/2016 15:48:10','dataAtualizacao':'24/06/2016 15:48:10'},'tempoMaximoPermanencia':12,'demandaPrioritaria':true,'dataCriacao':'24/06/2016 17:41:38','dataAtualizacao':'24/06/2016 17:41:38'}}]},{'id':'fc525f6c-6ba4-463c-84ef-5631126711e8','dataCriacao':'24/06/2016 15:50:59','dataAtualizacao':'24/06/2016 17:41:38','tipo':'VEICULAR','posicao':2,'estagioGrupoSemaforicos':[{'id':'17a2006b-b4ff-4ab4-a63f-fc7f65e0596f','dataCriacao':'24/06/2016 17:41:38','dataAtualizacao':'24/06/2016 17:41:38','ativo':true,'estagio':{'id':'d11740e4-f6ae-4797-b839-46fdde078374','imagem':{'id':'1e988fa7-335f-40cd-bebf-fd9befe19c69','filename':'Screen Shot 2016-06-17 at 15.08.57.png','contentType':'image/png','dataCriacao':'24/06/2016 15:47:46','dataAtualizacao':'24/06/2016 15:47:46'},'demandaPrioritaria':false,'dataCriacao':'24/06/2016 17:41:38','dataAtualizacao':'24/06/2016 17:41:38'}}]}]};
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
