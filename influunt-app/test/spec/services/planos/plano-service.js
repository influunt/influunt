'use strict';

describe('Service: planoService', function () {

  // instantiate service
  var planoService, controlador, anel;
  beforeEach(inject(function (_planoService_) {
    planoService = _planoService_;

    controlador = ControladorBasico.get();
    anel = _.find(controlador.aneis, 'ativo');
    var posicao = 1;

    controlador.versoesPlanos = [{idJson: 1}];
    anel.versaoPlano = {idJson: 1};

    controlador = planoService.adicionar(controlador, anel, posicao);
  }));

  describe('Novo plano', function () {
    it('O controlador deverá conter uma lista de planos', function() {
      expect(controlador.planos).toBeDefined();
      expect(controlador.planos.length).toBe(1);
    });

    it('Deve inicializar o plano com os valores default', function() {
      var plano = controlador.planos[0];

      expect(plano.idJson).toBeDefined();
      expect(plano.anel).toEqual({idJson: anel.idJson});
      expect(plano.descricao).toBe('PLANO 1');
      expect(plano.posicao).toBe(1);
      expect(plano.modoOperacao).toBe('TEMPO_FIXO_COORDENADO');
      expect(plano.posicaoTabelaEntreVerde).toBe(1);
      expect(plano.tempoCiclo).toBe(controlador.cicloMin);
      expect(plano.versaoPlano).toEqual({idJson: anel.versaoPlano.idJson});
    });

    it('Deve criar um array de planos em versaoPlano', function() {
      expect(controlador.versoesPlanos[0].planos.length).toBe(1);
    });

    it('Cada plano criado deverá ter uma associação com os grupos semaforicos do anel', function() {
      var gruposSemaforicos = anel.gruposSemaforicos;
      controlador.planos.forEach(function(plano) {
        expect(plano.gruposSemaforicosPlanos.length).toBe(gruposSemaforicos.length);
      });

      expect(controlador.gruposSemaforicosPlanos.length).toBe(controlador.planos.length * controlador.gruposSemaforicos.length);
    });

    it('Cada plano criado deverá ter uma associação com os estágios do anel', function() {
      var estagios = anel.estagios;
      controlador.planos.forEach(function(plano) {
        expect(plano.estagiosPlanos.length).toBe(estagios.length);
      });

      expect(controlador.estagiosPlanos.length).toBe(controlador.planos.length * controlador.estagios.length);
    });
  });

  describe('plano existente', function () {
    beforeEach(function() {
      var posicao = 1;
      var anel = controlador.aneis[0];
      controlador.planos = [{
        idJson: 'p1',
        anel: {idJson: anel.idJson},
        posicao: posicao
      }];

      controlador = planoService.adicionar(controlador, anel, posicao);
    });

    it('Deverá marcar o plano como "configurado"', function() {
      expect(controlador.planos[0].configurado).toBeTruthy();
    });
  });

  describe('isGrupoNemAssociadoNemDemandaPrioritaria()', function() {
    var anel, plano;
    beforeEach(function() {
      controlador = ControladorComVariosAneisEPlanos.get();
      anel = _.find(controlador.aneis, { posicao: 2 });
      plano = _.find(controlador.planos, { anel: { idJson: anel.idJson }, posicao: 1 });
    });

    it('deve retornar false para um grupo semafórico associado a estágio de demanda prioritária', function() {
      var estagioDP = _.find(controlador.estagios, { demandaPrioritaria: true, anel: { idJson: anel.idJson } });
      var egsDP = _.find(controlador.estagiosGruposSemaforicos, { estagio: { idJson: estagioDP.idJson } });
      var grupoDP = _.find(controlador.gruposSemaforicos, { idJson: egsDP.grupoSemaforico.idJson });
      expect(planoService.isGrupoNemAssociadoNemDemandaPrioritaria(controlador, anel, plano, grupoDP)).toBe(false);
    });

    it('deve retornar false para um grupo semafórico associado a estágio que esteja na sequência do plano', function() {
      var epAssociado = _.find(controlador.estagiosPlanos, { plano: { idJson: plano.idJson } });
      var estagioAssociado = _.find(controlador.estagios, { idJson: epAssociado.estagio.idJson });
      var egsAssociado = _.find(controlador.estagiosGruposSemaforicos, { estagio: { idJson: estagioAssociado.idJson } });
      var grupoAssociado = _.find(controlador.gruposSemaforicos, { idJson: egsAssociado.grupoSemaforico.idJson });
      expect(planoService.isGrupoNemAssociadoNemDemandaPrioritaria(controlador, anel, plano, grupoAssociado)).toBe(false);
    });

    it('deve retornar true para um grupo semafórico que não seja demanda prioritária e nem esteja na sequência do plano', function() {
      var estagiosIdJson = _.chain(controlador.estagios).filter({ anel: { idJson: anel.idJson } }).map('idJson').value();
      var estagiosAssociados = _.chain(controlador.estagiosPlanos).filter({ plano: { idJson: plano.idJson } }).map('estagio.idJson').uniq().value();
      var estagiosNaoAssociadosIdJson = _.difference(estagiosIdJson, estagiosAssociados);
      var estagioNaoAssociadoNemDP = _.find(controlador.estagios, function(e) {
        return estagiosNaoAssociadosIdJson.indexOf(e.idJson) > -1 && !e.demandaPrioritaria;
      });
      var egsNaoAssociadoNemDP = _.find(controlador.estagiosGruposSemaforicos, { estagio: { idJson: estagioNaoAssociadoNemDP.idJson } });
      var grupoNaoAssociadoNemDP = _.find(controlador.gruposSemaforicos, { idJson: egsNaoAssociadoNemDP.grupoSemaforico.idJson });
      expect(planoService.isGrupoNemAssociadoNemDemandaPrioritaria(controlador, anel, plano, grupoNaoAssociadoNemDP)).toBe(true);
    });
  });

});
