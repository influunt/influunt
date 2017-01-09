'use strict';

describe('Directive: InfluuntTabelaHoraria', function () {
  var scope,
      element,
      $compile,
      $injectedRootScope;

  var somenteVisualizacao = false,
      objeto,
      podeInicializar = true,
      podeVisualizarPlanos = false;

  var startDirectiveElement = function(obj) {
    scope = $injectedRootScope.$new();
    scope.somenteVisualizacao = somenteVisualizacao;
    scope.podeInicializar = podeInicializar;
    scope.podeVisualizarPlanos = podeVisualizarPlanos;
    scope.objeto = obj;
    element = angular.element('<influunt-tabela-horaria read-only="somenteVisualizacao" objeto="objeto" errors="errors" pode-inicializar="podeInicializar" pode-visualizar-planos="false" current-versao-tabela-horaria="currentVersaoTabelaHoraria"></influunt-tabela-horaria>');
    element = $compile(element)(scope);
  };

  beforeEach(inject(function ($rootScope, _$compile_) {
    $compile = _$compile_;
    $injectedRootScope = $rootScope;

    objeto = {
      eventos: [],
      tabelasHorarias: [],
      versoesTabelasHorarias: []
    };
    startDirectiveElement(objeto);
  }));

  describe('erros ao salvar', function () {
    beforeEach(inject(function(handleValidations) {
      scope.objeto = {
        versoesTabelasHorarias: [
          {
            idJson: 'vth1',
            tabelaHoraria: {idJson: 'th1'},
            statusVersao: 'CONFIGURADO'
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
          {idJson: 'ev1', tabelaHoraria: {idJson: 'th1'}, posicao: 1, horario: '00:00:00', tipo: 'NORMAL'},
          {idJson: 'ev2', tabelaHoraria: {idJson: 'th1'}, posicao: 2, horario: '00:00:00', tipo: 'NORMAL'},
          {idJson: 'ev3', tabelaHoraria: {idJson: 'th1'}, posicao: 1, horario: '00:00:00', tipo: 'ESPECIAL_RECORRENTE'},
          {idJson: 'ev4', tabelaHoraria: {idJson: 'th1'}, posicao: 2, horario: '00:00:00', tipo: 'ESPECIAL_RECORRENTE'},
          {idJson: 'ev5', tabelaHoraria: {idJson: 'th1'}, posicao: 1, horario: '00:00:00', tipo: 'ESPECIAL_NAO_RECORRENTE'}
        ]
      };
      var error = [{'root':'Controlador','message':'O plano selecionado não está configurado em todos os anéis.','path':'versoesTabelasHorarias[0].tabelaHoraria.eventos[2].planosConfigurados'},
                   {'root':'Controlador','message':'O plano selecionado não está configurado em todos os anéis.','path':'versoesTabelasHorarias[0].tabelaHoraria.eventos[1].planosConfigurados'},
                   {'root':'Controlador','message':'O plano selecionado não está configurado em todos os anéis.','path':'versoesTabelasHorarias[0].tabelaHoraria.eventos[4].planosConfigurados'}];
      scope.errors = handleValidations.buildValidationMessages(error, objeto);
      scope.$apply();
    }));

    it('Badge de erro deve aparecer na posição correta', function() {
      var isolateScope = element.isolateScope();

      expect(isolateScope.currentErrosEventos[0]).not.toBeDefined();
      expect(isolateScope.currentErrosEventos[1]).toBeDefined();

      isolateScope.selecionaTipoEvento(1);
      expect(isolateScope.currentErrosEventos[0]).toBeDefined();
      expect(isolateScope.currentErrosEventos[1]).not.toBeDefined();

      isolateScope.selecionaTipoEvento(2);
      expect(isolateScope.currentErrosEventos[0]).toBeDefined();
    });

    it('Não deve ter erro na tabela horária', function() {
      var isolateScope = element.isolateScope();
      expect(isolateScope.getErrosTabelaHoraria().length).toBe(0);
    });

    it('Deve apresentar alertas de erros nas tabs com eventos não válidos', inject(function(handleValidations) {
      var isolateScope = element.isolateScope();
      expect(isolateScope.tabTemErro(0)).toBeTruthy();
      expect(isolateScope.tabTemErro(1)).toBeTruthy();
      expect(isolateScope.tabTemErro(2)).toBeTruthy();

      var error = [{'root':'Controlador','message':'O plano selecionado não está configurado em todos os anéis.','path':'versoesTabelasHorarias[0].tabelaHoraria.eventos[2].planosConfigurados'}];
      scope.errors = handleValidations.buildValidationMessages(error, scope.objeto);
      scope.$apply();

      expect(isolateScope.tabTemErro(0)).toBeFalsy();
      expect(isolateScope.tabTemErro(1)).toBeTruthy();
      expect(isolateScope.tabTemErro(2)).toBeFalsy();
    }));
  });

  describe('não existe erros ao salvar', function () {
    beforeEach(inject(function(handleValidations) {
      scope.objeto = {
        versoesTabelasHorarias: [
          {
            idJson: 'vth1',
            tabelaHoraria: {idJson: 'th1'},
            statusVersao: 'CONFIGURADO'
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
          {idJson: 'ev1', tabelaHoraria: {idJson: 'th1'}, posicao: 1, horario: '00:00:00', tipo: 'NORMAL'},
          {idJson: 'ev2', tabelaHoraria: {idJson: 'th1'}, posicao: 2, horario: '00:00:00', tipo: 'NORMAL'},
          {idJson: 'ev3', tabelaHoraria: {idJson: 'th1'}, posicao: 1, horario: '00:00:00', tipo: 'ESPECIAL_RECORRENTE'},
          {idJson: 'ev4', tabelaHoraria: {idJson: 'th1'}, posicao: 2, horario: '00:00:00', tipo: 'ESPECIAL_RECORRENTE'},
          {idJson: 'ev5', tabelaHoraria: {idJson: 'th1'}, posicao: 1, horario: '00:00:00', tipo: 'ESPECIAL_NAO_RECORRENTE'}
        ]
      };
      var error = [];
      scope.errors = handleValidations.buildValidationMessages(error, scope.objeto);
      scope.$apply();
    }));

    it('Badge de erro deve aparecer na posição correta', function() {
      var isolateScope = element.isolateScope();
      isolateScope.selecionaTipoEvento(0);
      expect(isolateScope.currentErrosEventos[0]).not.toBeDefined();
      expect(isolateScope.currentErrosEventos[1]).not.toBeDefined();

      isolateScope.selecionaTipoEvento(1);
      expect(isolateScope.currentErrosEventos[0]).not.toBeDefined();
      expect(isolateScope.currentErrosEventos[1]).not.toBeDefined();

      isolateScope.selecionaTipoEvento(2);
      expect(isolateScope.currentErrosEventos[0]).not.toBeDefined();
    });

    it('Não deve ter erro na tabela horária', function() {
      var isolateScope = element.isolateScope();
      expect(isolateScope.getErrosTabelaHoraria().length).toBe(0);
    });
  });

  describe('editar eventos.', function () {
    beforeEach(function() {
      scope.objeto = ControladorComPlanos.get();
      scope.$apply();
    });

    it('Deve ter um novoEvento para cada tipo', function() {
      var isolateScope = element.isolateScope();
      expect(isolateScope.novosEventos.length).toBe(3);
      expect(isolateScope.currentNovoEvento.tipo).toBe('NORMAL');
      isolateScope.selecionaTipoEvento(1);
      expect(isolateScope.currentNovoEvento.tipo).toBe('ESPECIAL_RECORRENTE');
      isolateScope.selecionaTipoEvento(2);
      expect(isolateScope.currentNovoEvento.tipo).toBe('ESPECIAL_NAO_RECORRENTE');
    });

    it('Adiciona um novo evento normal ao objeto', function() {
      var isolateScope = element.isolateScope();
      expect(isolateScope.objeto.eventos.length).toBe(0);
      var evento = {idJson: 'ev1', diaDaSemana: 'TODOS_OS_DIAS', hora: '17', minuto: '0', segundo: '0', posicaoPlano: '1', tipo: 'NORMAL', tabelaHoraria: {idJson: isolateScope.currentTabelaHoraria.idJson}};
      isolateScope.verificaAtualizacaoDeEventos(evento);

      expect(isolateScope.objeto.eventos.length).toBe(1);
      expect(isolateScope.novosEventos.length).toBe(3);

      evento.posicaoPlano = '5';
      isolateScope.verificaAtualizacaoDeEventos(evento);

      expect(isolateScope.objeto.eventos.length).toBe(1);
      expect(isolateScope.novosEventos.length).toBe(3);
    });

    it('Adiciona um novo evento especial ao objeto', function() {
      var isolateScope = element.isolateScope();
      isolateScope.selecionaTipoEvento(1);
      expect(isolateScope.objeto.eventos.length).toBe(0);
      var evento = {idJson: 'ev2', data: '25/12/2016', nome: 'NATAL', hora: '17', minuto: '0', segundo: '0', posicaoPlano: '1', tipo: 'ESPECIAL_RECORRENTE', tabelaHoraria: {idJson: isolateScope.currentTabelaHoraria.idJson}};
      isolateScope.verificaAtualizacaoDeEventos(evento);

      expect(isolateScope.objeto.eventos.length).toBe(1);
      expect(isolateScope.novosEventos.length).toBe(3);

      evento.posicaoPlano = '5';
      isolateScope.verificaAtualizacaoDeEventos(evento);

      expect(isolateScope.objeto.eventos.length).toBe(1);
      expect(isolateScope.novosEventos.length).toBe(3);
    });

    it('Adiciona um novo evento nao finalizado', function() {
      var isolateScope = element.isolateScope();
      expect(isolateScope.objeto.eventos.length).toBe(0);
      var evento = {idJson: 'ev1', diaDaSemana: 'TODOS_OS_DIAS', hora: '17', minuto: '0', segundo: '0', tipo: 'NORMAL', tabelaHoraria: {idJson: isolateScope.currentTabelaHoraria.idJson}};
      isolateScope.verificaAtualizacaoDeEventos(evento);

      expect(isolateScope.objeto.eventos.length).toBe(0);
    });
  });

  describe('remover eventos', function () {
    beforeEach(function() {
      scope.objeto = ControladorComPlanos.get();
      scope.$apply();
    });

    describe('remover eventos não sincronizados à api', function () {
      beforeEach(function() {
        var isolateScope = element.isolateScope();
        isolateScope.objeto.tabelasHorarias[0].eventos = [ {idJson: 'E1'}, {idJson: 'E2'} ];
        isolateScope.objeto.eventos = [
          {idJson: 'E1', tipo: 'NORMAL', tabelaHoraria: {idJson: isolateScope.objeto.tabelasHorarias[0].idJson}},
          {idJson: 'E2', tipo: 'NORMAL', tabelaHoraria: {idJson: isolateScope.objeto.tabelasHorarias[0].idJson}}
        ];
        var evento = isolateScope.objeto.eventos[0];

        isolateScope.removerEvento(evento);
        isolateScope.$apply();
      });

      it('O elemento deve ser removido da coleção, das referências em Tabelas horárias e dos currentEventos', function() {
        var isolateScope = element.isolateScope();
        expect(_.find(isolateScope.objeto.tabelasHorarias[0].eventos, {idJson: 'E1'})).not.toBeDefined();
        expect(_.find(isolateScope.objeto.eventos, {idJson: 'E1'})).not.toBeDefined();
        expect(_.find(isolateScope.currentEventos, {idJson: 'E1'})).not.toBeDefined();
      });
    });

    describe('remover eventos sincronizados à api', function () {
      beforeEach(function() {
        var isolateScope = element.isolateScope();
        isolateScope.objeto.tabelasHorarias[0].eventos = [ {idJson: 'E1'}, {idJson: 'E2'} ];
        isolateScope.objeto.eventos = [
          {id: 1, idJson: 'E1', tipo: 'NORMAL', tabelaHoraria: {idJson: isolateScope.objeto.tabelasHorarias[0].idJson}},
          {id: 2, idJson: 'E2', tipo: 'NORMAL', tabelaHoraria: {idJson: isolateScope.objeto.tabelasHorarias[0].idJson}}
        ];
        var evento = isolateScope.objeto.eventos[0];

        isolateScope.removerEvento(evento);
        isolateScope.$apply();
      });

      it('O elemento deve ser removido dos currentEventos, mas permanecer nas demais coleções', function() {
        var isolateScope = element.isolateScope();
        expect(_.find(isolateScope.objeto.tabelasHorarias[0].eventos, {idJson: 'E1'})).toBeDefined();
        expect(_.find(isolateScope.objeto.eventos, {idJson: 'E1'})).toBeDefined();
        expect(_.find(isolateScope.currentEventos, {idJson: 'E1'})).not.toBeDefined();
      });

      it('O evento deve ser marcado como _destroy na coleção dos eventos', function() {
        var isolateScope = element.isolateScope();
        expect(_.find(isolateScope.objeto.eventos, {idJson: 'E1'})._destroy).toBeTruthy();
      });
    });
  });
});
