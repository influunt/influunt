'use strict';

describe('Controller: TabelaHorariosCtrl', function () {
  var TabelaHorariosCtrl,
      scope,
      $httpBackend,
      $q,
      $state,
      $controller,
      controlador,
      timeline,
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
    $state.go('app.tabela_horarios', {id: controladorId});
    scope.$apply();

    TabelaHorariosCtrl = $controller('TabelaHorariosCtrl', { $scope: scope });

    timeline = [];
    $httpBackend.expectGET('/controladores/' + controladorId).respond(controlador);
    // $httpBackend.expectGET('/tabela_horarios/' + anelId + '/timeline').respond(timeline);
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
});
