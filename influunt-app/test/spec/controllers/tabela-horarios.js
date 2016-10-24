'use strict';

describe('Controller: TabelaHorariosCtrl', function () {
  var TabelaHorariosCtrl,
      scope,
      $httpBackend,
      $q,
      $state,
      $controller,
      controlador,
      controladorId;

  beforeEach(inject(function (_$controller_, $rootScope, _$httpBackend_, _$q_, _$state_) {
    $httpBackend = _$httpBackend_;
    $q = _$q_;
    $state = _$state_;
    scope = $rootScope.$new();
    $controller = _$controller_;
    TabelaHorariosCtrl = $controller('TabelaHorariosCtrl', { $scope: scope });
  }));

  var beforeEachFn = function(objControlador) {
    controladorId = objControlador.getControladorId();
    controlador = objControlador.get();

    // Inicializando, por padrão, o planos em modo de visualizacao. Isto é necessário para acessar os dados
    // em `data`, nas rotas.
    $state.go('app.tabelas_horarias', {id: controladorId});
    scope.$apply();
    TabelaHorariosCtrl = $controller('TabelaHorariosCtrl', { $scope: scope });

    $httpBackend.expectGET('/controladores/' + controladorId).respond(controlador);
    scope.init();
    $httpBackend.flush();
    scope.$apply();
  };

  describe('erros ao salvar', function () {
    beforeEach(inject(function(handleValidations) {
      scope.objeto = {
        versoesTabelasHorarias: [
          {
            idJson: 'vth1',
            tabelaHoraria: {idJson: 'th1'}
          }
        ],
        tabelasHorarias: [
          {
            idJson: 'th1',
            versaoTabelaHoraria: {idJson: 'vth1'},
            eventos: [
              {idJson: 'ev1'},
              {idJson: 'ev2'},
              {idJson: 'ev3'},
              {idJson: 'ev4'},
              {idJson: 'ev5'}
            ]
          }
        ],
        eventos: [
          {idJson: 'ev1', tabelaHoraria: {idJson: 'th1'}, posicao: 1, tipo: 'NORMAL'},
          {idJson: 'ev2', tabelaHoraria: {idJson: 'th1'}, posicao: 2, tipo: 'NORMAL'},
          {idJson: 'ev3', tabelaHoraria: {idJson: 'th1'}, posicao: 1, tipo: 'ESPECIAL_RECORRENTE'},
          {idJson: 'ev4', tabelaHoraria: {idJson: 'th1'}, posicao: 2, tipo: 'ESPECIAL_RECORRENTE'},
          {idJson: 'ev5', tabelaHoraria: {idJson: 'th1'}, posicao: 1, tipo: 'ESPECIAL_NAO_RECORRENTE'}
        ]
      };
      var error = [{'root':'Controlador','message':'O plano selecionado não está configurado em todos os anéis.','path':'versoesTabelasHorarias[0].tabelaHoraria.eventos[2].planosConfigurados'},
      {'root':'Controlador','message':'O plano selecionado não está configurado em todos os anéis.','path':'versoesTabelasHorarias[0].tabelaHoraria.eventos[1].planosConfigurados'},
      {'root':'Controlador','message':'O plano selecionado não está configurado em todos os anéis.','path':'versoesTabelasHorarias[0].tabelaHoraria.eventos[4].planosConfigurados'}];
      scope.currentTabelaHoraria = _.find(scope.objeto.tabelasHorarias, {idJson: 'th1'});
      scope.currentVersaoTabelaHorariaIndex = 0;
      scope.currentVersaoTabelaHoraria = scope.objeto.versoesTabelasHorarias[scope.currentVersaoTabelaHorariaIndex];
      scope.selecionaTipoEvento(0);

      scope.errors = handleValidations.buildValidationMessages(error, scope.objeto);
      scope.$apply();
    }));

    it('Badge de erro deve aparecer na posição correta', function() {
      expect(scope.currentErrosEventos[0]).not.toBeDefined();
      expect(scope.currentErrosEventos[1]).toBeDefined();

      scope.selecionaTipoEvento(1);
      expect(scope.currentErrosEventos[0]).toBeDefined();
      expect(scope.currentErrosEventos[1]).not.toBeDefined();

      scope.selecionaTipoEvento(2);
      expect(scope.currentErrosEventos[0]).toBeDefined();
    });

    it('Não deve ter erro na tabela horária', function() {
      expect(scope.getErrosTabelaHoraria().length).toBe(0);
    });

    it('Deve apresentar alertas de erros nas tabs com eventos não válidos', inject(function(handleValidations) {
      expect(scope.tabTemErro(0)).toBeTruthy();
      expect(scope.tabTemErro(1)).toBeTruthy();
      expect(scope.tabTemErro(2)).toBeTruthy();

      var error = [{'root':'Controlador','message':'O plano selecionado não está configurado em todos os anéis.','path':'versoesTabelasHorarias[0].tabelaHoraria.eventos[2].planosConfigurados'}];
      scope.errors = handleValidations.buildValidationMessages(error, scope.objeto);
      scope.$apply();

      expect(scope.tabTemErro(0)).toBeFalsy();
      expect(scope.tabTemErro(1)).toBeTruthy();
      expect(scope.tabTemErro(2)).toBeFalsy();
    }));
  });

  describe('não existe erros ao salvar', function () {
    beforeEach(inject(function(handleValidations) {
      scope.objeto = {
        versoesTabelasHorarias: [
          {
            idJson: 'vth1',
            tabelaHoraria: {idJson: 'th1'}
          }
        ],
        tabelasHorarias: [
          {
            idJson: 'th1',
            versaoTabelaHoraria: {idJson: 'vth1'},
            eventos: [
              {idJson: 'ev1'},
              {idJson: 'ev2'},
              {idJson: 'ev3'},
              {idJson: 'ev4'},
              {idJson: 'ev5'}
            ]
          }
        ],
        eventos: [
          {idJson: 'ev1', tabelaHoraria: {idJson: 'th1'}, posicao: 1, tipo: 'NORMAL'},
          {idJson: 'ev2', tabelaHoraria: {idJson: 'th1'}, posicao: 2, tipo: 'NORMAL'},
          {idJson: 'ev3', tabelaHoraria: {idJson: 'th1'}, posicao: 1, tipo: 'ESPECIAL_RECORRENTE'},
          {idJson: 'ev4', tabelaHoraria: {idJson: 'th1'}, posicao: 2, tipo: 'ESPECIAL_RECORRENTE'},
          {idJson: 'ev5', tabelaHoraria: {idJson: 'th1'}, posicao: 1, tipo: 'ESPECIAL_NAO_RECORRENTE'}
        ]
      };
      var error = [];
      scope.errors = handleValidations.buildValidationMessages(error, scope.objeto);
      scope.currentTabelaHoraria = _.find(scope.objeto.tabelasHorarias, {idJson: 'th1'});
      scope.currentVersaoTabelaHorariaIndex = 0;
      scope.currentVersaoTabelaHoraria = scope.objeto.versoesTabelasHorarias[scope.currentVersaoTabelaHorariaIndex];
    }));

    it('Badge de erro deve aparecer na posição correta', function() {
      scope.selecionaTipoEvento(0);
      expect(scope.currentErrosEventos[0]).not.toBeDefined();
      expect(scope.currentErrosEventos[1]).not.toBeDefined();

      scope.selecionaTipoEvento(1);
      expect(scope.currentErrosEventos[0]).not.toBeDefined();
      expect(scope.currentErrosEventos[1]).not.toBeDefined();

      scope.selecionaTipoEvento(2);
      expect(scope.currentErrosEventos[0]).not.toBeDefined();
    });

    it('Não deve ter erro na tabela horária', function() {
      expect(scope.getErrosTabelaHoraria().length).toBe(0);
    });
  });

  describe('editarTabelaHoraria - função de edição de tabela horaria não ATIVA', function () {
    beforeEach(function() {
      beforeEachFn(ControladorComPlanos);
    });

    it('Deve redirecionar à tela de edicao de tabela horária', function() {
      $httpBackend.expectGET('/controladores/' + controladorId + '/pode_editar').respond(200, null);
      scope.editarTabelaHoraria(controladorId);
      $httpBackend.flush();
      expect($state.current.name).toBe('app.tabelas_horarias_edit');
    });

    it('O usuário deve ser alertado que não pode editar o plano, se for o caso', inject(function(toast, influuntAlert) {
      spyOn(toast, 'clear');
      spyOn(influuntAlert, 'alert');

      $httpBackend.expectGET('/controladores/' + controladorId + '/pode_editar').respond(403, [{message: 'teste'}]);
      scope.editarTabelaHoraria(controladorId);
      $httpBackend.flush();

      expect(toast.clear).toHaveBeenCalled();
      expect(influuntAlert.alert).toHaveBeenCalled();
    }));

    it('Os demais erros devem ser tratados via toast.error', inject(function(toast, influuntAlert) {
      spyOn(toast, 'clear');
      spyOn(influuntAlert, 'alert');
      $httpBackend.expectGET('/controladores/' + controladorId + '/pode_editar').respond(403, [{message: 'teste'}]);
      scope.editarTabelaHoraria(controladorId);
      $httpBackend.flush();

      expect(toast.clear).toHaveBeenCalled();
      expect(influuntAlert.alert).toHaveBeenCalled();
    }));
  });

  describe('clonarTabelaHoraria - função de edição de tabela horaria ATIVA', function () {
    beforeEach(function() { beforeEachFn(ControladorComPlanos); });

    it('Deve redirecionar à tela de edicao de tabela horaria', function() {
      $httpBackend.expectGET('/controladores/' + controladorId + '/pode_editar').respond(200, null);
      $httpBackend.expectGET('/controladores/' + controladorId + '/editar_tabelas_horarias').respond(controlador);
      scope.clonarTabelaHoraria(controladorId);
      $httpBackend.flush();
      expect($state.current.name).toBe('app.tabelas_horarias_edit');
    });

    it('O usuário deve ser alertado que não pode editar a tabela horaria, se for o caso', inject(function(influuntAlert) {
      spyOn(influuntAlert, 'alert');

      $httpBackend.expectGET('/controladores/' + controladorId + '/pode_editar').respond(403, [{message: 'teste'}]);
      scope.clonarTabelaHoraria(controladorId);
      $httpBackend.flush();

      expect(influuntAlert.alert).toHaveBeenCalled();
    }));

    it('Os demais erros devem ser tratados via toast.error', inject(function(toast) {
      spyOn(toast, 'error');

      $httpBackend.expectGET('/controladores/' + controladorId + '/pode_editar').respond(403);
      scope.clonarTabelaHoraria(controladorId);
      $httpBackend.flush();

      expect(toast.error).toHaveBeenCalled();
    }));
  });

  describe('cancelarEdicao', function () {
    var deferred, Restangular;
    beforeEach(inject(function(influuntAlert, $q, _Restangular_) {
      Restangular = _Restangular_;
      deferred = $q.defer();
      beforeEachFn(ControladorComPlanos);
      spyOn(influuntAlert, 'delete').and.returnValue(deferred.promise);
    }));

    it('Não deve executar nada se o usuário não confirmar o cancelamento', function() {
      spyOn(Restangular, 'one');
      scope.cancelarEdicao();
      deferred.resolve(false);
      scope.$apply();
      expect(Restangular.one).not.toHaveBeenCalled();
    });

    it('Deve excluir a copia local de plano se este não for sincronizado à API', function() {
      scope.objeto.tabelasHorarias = [{}];
      spyOn(Restangular, 'one');
      scope.cancelarEdicao();
      deferred.resolve(true);
      scope.$apply();
      expect(Restangular.one).not.toHaveBeenCalled();
      expect(scope.$state.current.name).toBe('app.controladores');
    });

    it('Deve enviar à API uma request de cancelamento de edição se uma versão já sincronizada for encontrada', inject(function(toast) {
      spyOn(toast, 'success');
      var tabelaHoraria = _.chain(scope.objeto.tabelasHorarias).filter('id').last().value();
      $httpBackend.expectDELETE('/tabelas_horarias/' + tabelaHoraria.id + '/cancelar_edicao').respond({});
      scope.cancelarEdicao();
      deferred.resolve(true);
      $httpBackend.flush();
      scope.$apply();
      expect(toast.success).toHaveBeenCalled();
      expect(scope.$state.current.name).toBe('app.controladores');
    }));
  });

  describe('submitForm', function () {
    var Restangular;
    beforeEach(inject(function(_Restangular_) {
      Restangular = _Restangular_;
      beforeEachFn(ControladorComPlanos);
      scope.submitForm();
    }));

    it('Deve atualizar o controlador com a resposta da API em caso de sucesso.', function() {
      var resposta = {id: 'controlador_id'};
      $httpBackend.expectPOST('/tabelas_horarias').respond(200, resposta);
      $httpBackend.flush();
      scope.$apply();

      expect(scope.objeto.id).toEqual(resposta.id);
    });

    it('Deve exibir mensagens de validação em caso de inconsistencia.', inject(function(handleValidations) {
      spyOn(handleValidations, 'buildValidationMessages');
      $httpBackend.expectPOST('/tabelas_horarias').respond(422, {});
      $httpBackend.flush();
      scope.$apply();

      expect(handleValidations.buildValidationMessages).toHaveBeenCalled();
    }));

    it('Deve tratar os demais erros que não sejam validações da API', function() {
      spyOn(console, 'error');
      $httpBackend.expectPOST('/tabelas_horarias').respond(500, {});
      $httpBackend.flush();
      scope.$apply();

      expect(console.error).toHaveBeenCalled();
    });
  });

  describe('editar eventos.', function () {
    beforeEach(function() {
      beforeEachFn(ControladorComPlanos);
    });

    it('Deve ter um novoEvento para cada tipo', function() {
      expect(scope.novosEventos.length).toBe(3);
      expect(scope.currentNovoEvento.tipo).toBe('NORMAL');
      scope.selecionaTipoEvento(1);
      expect(scope.currentNovoEvento.tipo).toBe('ESPECIAL_RECORRENTE');
      scope.selecionaTipoEvento(2);
      expect(scope.currentNovoEvento.tipo).toBe('ESPECIAL_NAO_RECORRENTE');
    });

    it('Adiciona um novo evento normal ao objeto', function() {
      expect(scope.objeto.eventos.length).toBe(0);
      var evento = {idJson: 'ev1', diaDaSemana: 'TODOS_OS_DIAS', hora: '17', minuto: '0', segundo: '0', posicaoPlano: '1', tipo: 'NORMAL', tabelaHoraria: {idJson: scope.currentTabelaHoraria.idJson}};
      scope.verificaAtualizacaoDeEventos(evento);

      expect(scope.objeto.eventos.length).toBe(1);
      expect(scope.novosEventos.length).toBe(3);

      evento.posicaoPlano = '5';
      scope.verificaAtualizacaoDeEventos(evento);

      expect(scope.objeto.eventos.length).toBe(1);
      expect(scope.novosEventos.length).toBe(3);
    });

    it('Adiciona um novo evento especial ao objeto', function() {
      scope.selecionaTipoEvento(1);
      expect(scope.objeto.eventos.length).toBe(0);
      var evento = {idJson: 'ev2', data: '25/12/2016', nome: 'NATAL', hora: '17', minuto: '0', segundo: '0', posicaoPlano: '1', tipo: 'ESPECIAL_RECORRENTE', tabelaHoraria: {idJson: scope.currentTabelaHoraria.idJson}};
      scope.verificaAtualizacaoDeEventos(evento);

      expect(scope.objeto.eventos.length).toBe(1);
      expect(scope.novosEventos.length).toBe(3);

      evento.posicaoPlano = '5';
      scope.verificaAtualizacaoDeEventos(evento);

      expect(scope.objeto.eventos.length).toBe(1);
      expect(scope.novosEventos.length).toBe(3);
    });

    it('Adiciona um novo evento nao finalizado', function() {
      expect(scope.objeto.eventos.length).toBe(0);
      var evento = {idJson: 'ev1', diaDaSemana: 'TODOS_OS_DIAS', hora: '17', minuto: '0', segundo: '0', tipo: 'NORMAL', tabelaHoraria: {idJson: scope.currentTabelaHoraria.idJson}};
      scope.verificaAtualizacaoDeEventos(evento);

      expect(scope.objeto.eventos.length).toBe(0);
    });
  });

  describe('remover eventos', function () {
    beforeEach(function() {
      beforeEachFn(ControladorComPlanos);
    });

    describe('remover eventos não sincronizados à api', function () {
      beforeEach(function() {
        scope.objeto.tabelasHorarias[0].eventos = [ {idJson: 'E1'}, {idJson: 'E2'} ];
        scope.objeto.eventos = [
          {idJson: 'E1', tipo: 'NORMAL', tabelaHoraria: {idJson: scope.objeto.tabelasHorarias[0].idJson}},
          {idJson: 'E2', tipo: 'NORMAL', tabelaHoraria: {idJson: scope.objeto.tabelasHorarias[0].idJson}}
        ];
        var evento = scope.objeto.eventos[0];

        scope.removerEvento(evento);
        scope.$apply();
      });

      it('O elemento deve ser removido da coleção, das referências em Tabelas horarias e dos currentEventos', function() {
        expect(_.find(scope.objeto.tabelasHorarias[0].eventos, {idJson: 'E1'})).not.toBeDefined();
        expect(_.find(scope.objeto.eventos, {idJson: 'E1'})).not.toBeDefined();
        expect(_.find(scope.currentEventos, {idJson: 'E1'})).not.toBeDefined();
      });
    });

    describe('remover eventos sincronizados à api', function () {
      beforeEach(function() {
        scope.objeto.tabelasHorarias[0].eventos = [ {idJson: 'E1'}, {idJson: 'E2'} ];
        scope.objeto.eventos = [
          {id: 1, idJson: 'E1', tipo: 'NORMAL', tabelaHoraria: {idJson: scope.objeto.tabelasHorarias[0].idJson}},
          {id: 2, idJson: 'E2', tipo: 'NORMAL', tabelaHoraria: {idJson: scope.objeto.tabelasHorarias[0].idJson}}
        ];
        var evento = scope.objeto.eventos[0];

        scope.removerEvento(evento);
        scope.$apply();
      });

      it('O elemento deve ser removido dos currentEventos, mas permanecer nas demais coleções', function() {
        expect(_.find(scope.objeto.tabelasHorarias[0].eventos, {idJson: 'E1'})).toBeDefined();
        expect(_.find(scope.objeto.eventos, {idJson: 'E1'})).toBeDefined();
        expect(_.find(scope.currentEventos, {idJson: 'E1'})).not.toBeDefined();
      });

      it('O evento deve ser marcado como _destroy na coleção dos eventos', function() {
        expect(_.find(scope.objeto.eventos, {idJson: 'E1'})._destroy).toBeTruthy();
      });
    });
  });
});
