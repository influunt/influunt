'use strict';

describe('Controller: PlanosCtrl', function () {
  var PlanosCtrl,
      scope,
      $httpBackend,
      $timeout,
      $q,
      $state,
      $controller,
      controlador,
      controladorId,
      anelId;

  beforeEach(inject(function (_$controller_, $rootScope, _$httpBackend_, _$q_, _$state_, _$timeout_) {
    $httpBackend = _$httpBackend_;
    $q = _$q_;
    $state = _$state_;
    scope = $rootScope.$new();
    $controller = _$controller_;
    $timeout = _$timeout_;
  }));

  var beforeEachFn = function(objControlador) {
    controladorId = objControlador.getControladorId();
    anelId = objControlador.getAnelAtivoId();
    controlador = objControlador.get();

    // Inicializando, por padrão, o planos em modo de visualizacao. Isto é necessário para acessar os dados
    // em `data`, nas rotas.
    $state.go('app.planos', {id: controladorId});
    scope.$apply();

    PlanosCtrl = $controller('PlanosCtrl', { $scope: scope });

    $httpBackend.expectGET('/controladores/' + controladorId).respond(controlador);
    scope.init();
    $httpBackend.flush();
  };

  describe('init - controlador mínimo', function () {
    beforeEach(function() { beforeEachFn(ControladorBasico); });

    it('Deve salvar o plano em objeto', function() {
      expect(scope.objeto).toBeDefined();
    });

    it('Deve montar os valores minimos para o diagrama de intervalos', function() {
      expect(scope.valoresMinimos).toBeDefined();
    });

    it('Deve criar um objeto de versão planos para cada anel ativo', function() {
      var aneis = _.filter(scope.objeto.aneis, 'ativo');
      aneis.forEach(function(anel) {
        expect(anel.versaoPlano).toBeDefined();
      });

      expect(scope.objeto.versoesPlanos.length).toBe(aneis.length);
    });

    it('Deve criar n planos por anel, sendo n igual ao limite de planos do controlador', function() {
      var aneis = _.filter(scope.objeto.aneis, 'ativo');
      aneis.forEach(function(anel) {
        expect(anel.planos).toBeDefined();
        expect(anel.planos.length).toBe(scope.objeto.limitePlanos);
      });
    });
  });

  describe('init - controlador mínimo', function () {
    beforeEach(function() { 
      beforeEachFn(ControladorComVariosAneis); 
    });
    it('O plano 1 deve estar ativo e ser coordenado', function() {
      var aneis = _.filter(scope.objeto.aneis, 'ativo');
      aneis.forEach(function(anel) {
        var plano = _.find(scope.objeto.planos, {anel: {idJson: anel.idJson}, posicao: 1});
        expect(plano.configurado).toBeTruthy();
        expect(plano.modoOperacao).toBe('TEMPO_FIXO_COORDENADO');
      });
    });
    
    it('Se o plano 2 estiver selecionado, ao trocar de anel o mesmo numero deve continuar selecionado e ser ativo', function() {
      var planoAnel1 = _.cloneDeep(scope.currentPlano);
      scope.selecionaAnelPlanos(1);
      var planoAnel2 = _.cloneDeep(scope.currentPlano);
      expect(planoAnel1.configurado).toBeTruthy();
      expect(planoAnel1.posicao).toBe(1);
      
      expect(planoAnel2.configurado).toBeTruthy();
      expect(planoAnel2.posicao).toBe(1);
    });
  });

  describe('init - controlador com planos cadastrados', function () {
    beforeEach(function() { beforeEachFn(ControladorComPlanos); });

    it('Deverá preencher os n planos por anel, mantendo aqueles que já haviam sido configurados', function() {
      var aneis = _.filter(scope.objeto.aneis, 'ativo');
      aneis.forEach(function(anel) {
        var planosConfigurados = _.filter(scope.objeto.planos, 'configurado');

        expect(anel.planos).toBeDefined();
        expect(anel.planos.length).toBe(scope.objeto.limitePlanos);
        expect(planosConfigurados.length).toBe(controlador.planos.length);
        expect(_.map(planosConfigurados, 'id')).toEqual(_.map(controlador.planos, 'id'));
      });
    });
  });

  describe('Planos', function () {
    describe('editarPlano - função de edição de planos não ATIVOS', function () {
      beforeEach(function() {
        beforeEachFn(ControladorComPlanos);
      });

      it('Deve redirecionar à tela de edicao de planos', function() {
        $httpBackend.expectGET('/controladores/' + controladorId + '/pode_editar').respond(200, null);
        scope.editarPlano(controladorId);
        $httpBackend.flush();
        expect($state.current.name).toBe('app.planos_edit');
      });

      it('O usuário deve ser alertado que não pode editar o plano, se for o caso', inject(function(toast, influuntAlert) {
        spyOn(toast, 'clear');
        spyOn(influuntAlert, 'alert');

        $httpBackend.expectGET('/controladores/' + controladorId + '/pode_editar').respond(403, [{message: 'teste'}]);
        scope.editarPlano(controladorId);
        $httpBackend.flush();

        expect(toast.clear).toHaveBeenCalled();
        expect(influuntAlert.alert).toHaveBeenCalled();
      }));
    });

    describe('clonarPlanos - função de edição de planos ATIVOS', function () {
      beforeEach(function() { beforeEachFn(ControladorComPlanos); });

      it('Deve redirecionar à tela de edicao de planos', function() {
        $httpBackend.expectGET('/controladores/' + controladorId + '/pode_editar').respond(200, null);
        $httpBackend.expectGET('/controladores/' + controladorId + '/editar_planos').respond(controlador);
        scope.clonarPlanos(controladorId);
        $httpBackend.flush();
        expect($state.current.name).toBe('app.planos_edit');
      });

      it('O usuário deve ser alertado que não pode editar o plano, se for o caso', inject(function(toast, influuntAlert) {
        spyOn(toast, 'clear');
        spyOn(influuntAlert, 'alert');

        $httpBackend.expectGET('/controladores/' + controladorId + '/pode_editar').respond(403, [{message: 'teste'}]);
        scope.clonarPlanos(controladorId);
        $httpBackend.flush();

        expect(toast.clear).toHaveBeenCalled();
        expect(influuntAlert.alert).toHaveBeenCalled();
      }));

      it('Os demais erros devem ser tratados via toast.error', inject(function(toast) {
        spyOn(toast, 'error');

        $httpBackend.expectGET('/controladores/' + controladorId + '/pode_editar').respond(403);
        scope.clonarPlanos(controladorId);
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
        expect(scope.$state.current.name).toBe('app.planos');
      });

      it('Deve excluir a copia local de plano se este não for sincronizado à API', function() {
        scope.objeto.planos = [{}];
        spyOn(Restangular, 'one');
        scope.cancelarEdicao();
        deferred.resolve(true);
        scope.$apply();
        expect(Restangular.one).not.toHaveBeenCalled();
        expect(scope.$state.current.name).toBe('app.controladores');
      });

      it('Deve enviar à API uma request de cancelamento de edição se uma versão já sincronizada for encontrada', inject(function(toast) {
        spyOn(toast, 'success');
        var plano = _.chain(scope.objeto.planos).filter('id').last().value();
        $httpBackend.expectDELETE('/planos/' + plano.id + '/cancelar_edicao').respond({});
        scope.cancelarEdicao();
        deferred.resolve(true);
        $httpBackend.flush();
        scope.$apply();
        expect(toast.success).toHaveBeenCalled();
        expect(scope.$state.current.name).toBe('app.controladores');
      }));
    });

    describe('copiarPlano', function () {
      var plano, tempoCicloTeste, planosCopiados, versaoPlanoOriginal;
      beforeEach(function() {
        tempoCicloTeste = 42;
        beforeEachFn(ControladorComPlanos);
        plano = _.find(scope.objeto.planos, {posicao: 1});
        plano.tempoCiclo = tempoCicloTeste;
        versaoPlanoOriginal = _.cloneDeep(scope.objeto.versoesPlanos[0]);

        scope.copiarPlano(plano);
        // Pega 3 planos não configurados para testes.
        scope.planosDestino = _.chain(scope.objeto.planos).reject('configurado').take(3).value();
        scope.confirmacaoCopiarPlano();
        scope.$apply();

        // Filtra todos os planos destino exceto o plano de origem.
        planosCopiados = scope.objeto.planos.filter(function(p) {
          return _.find(scope.planosDestino, {posicao: p.posicao});
        });
      });

      it('Deve copiar a configuração completa do plano origem para os planos destino, mantendo a posição', function() {
        planosCopiados.forEach(function(dest) {
          expect(dest.configurado).toBeTruthy();
          expect(dest.tempoCiclo).toBe(plano.tempoCiclo);
          expect(dest.id).not.toBe(plano.id);
          expect(dest.posicao).not.toBe(plano.posicao);
          expect(dest.descricao).toBe('PLANO ' + dest.posicao);

          expect(dest.gruposSemaforicosPlanos.length).toBe(plano.gruposSemaforicosPlanos.length);
          expect(dest.estagiosPlanos.length).toBe(plano.estagiosPlanos.length);
        });
      });

      it('O plano deve ser atualizado no objeto de versões planos.', function() {
        scope.planosDestino.forEach(function(dest) {
          var planoEmVersao = _.find(scope.objeto.versoesPlanos[0].planos, {idJson: dest.idJson});
          expect(planoEmVersao).not.toBeDefined();
        });

        planosCopiados.forEach(function(dest) {
          var planoEmVersao = _.find(scope.objeto.versoesPlanos[0].planos, {idJson: dest.idJson});
          expect(planoEmVersao).toBeDefined();
        });
      });

      it('A quantidade de planos da versão não deve ser alterada', function() {
        expect(scope.objeto.versoesPlanos[0].planos.length).toBe(versaoPlanoOriginal.planos.length);
      });

      it('Os planos copiados devem sustituir os planos antigos nas listas de planos', function() {
        scope.planosDestino.forEach(function(dest) {
          expect(_.find(scope.objeto.planos, {idJson: dest.idJson})).not.toBeDefined();
          expect(_.find(scope.currentPlanos, {idJson: dest.idJson})).not.toBeDefined();
        });

        planosCopiados = scope.objeto.planos.filter(function(p) {
          return _.find(scope.planosDestino, {posicao: p.posicao});
        });

        planosCopiados.forEach(function(copy) {
          expect(_.find(scope.objeto.planos, {idJson: copy.idJson})).toBeDefined();
          expect(_.find(scope.currentPlanos, {idJson: copy.idJson})).toBeDefined();
        });
      });

      it('Se o plano origem for adicionado aos planos destino, ele não deverá ser alterado', function() {
        scope.copiarPlano(plano);
        // Pega 3 planos não configurados para testes.
        scope.planosDestino = _.chain(scope.objeto.planos).reject('configurado').take(3).value();
        scope.planosDestino.push(plano);
        scope.confirmacaoCopiarPlano();
        scope.$apply();

        // O idJson do plano origem não deve ser alterado, ou seja, o objeto não foi
        // copiado para ele mesmo.
        expect(_.find(scope.objeto.planos, {idJson: plano.idJson})).toBeDefined();
        expect(_.find(scope.currentPlanos, {idJson: plano.idJson})).toBeDefined();

        scope.planosDestino
          .filter(function(p) { return p.posicao !== plano.posicao; })
          .forEach(function(p) {
            expect(_.find(scope.objeto.planos, {idJson: p.idJson})).not.toBeDefined();
            expect(_.find(scope.currentPlanos, {idJson: p.idJson})).not.toBeDefined();
          });
      });
    });

    describe('onChangeModoOperacao', function () {
      var inicializaTesteModoOperacao = function(modo) {
        beforeEachFn(ControladorComPlanos);
        var index = _.findIndex(scope.objeto.planos, {posicao: 1});
        scope.selecionaPlano(scope.objeto.planos[index], index);
        scope.currentPlano.modoOperacao = modo;
        scope.onChangeModoOperacao();
        scope.$apply();
        $timeout.flush();
      };

      var isEstagioVeicular = function(controlador, estagioPlano) {
        var veicular = false;
        var estagio = _.find(controlador.estagios, {idJson: estagioPlano.estagio.idJson});
        _.each(estagio.estagiosGruposSemaforicos, function(gs){
          var egs = _.find(controlador.estagiosGruposSemaforicos, {idJson: gs.idJson});
          var grupo = _.find(controlador.gruposSemaforicos, {idJson: egs.grupoSemaforico.idJson});
          veicular = grupo.tipo === 'VEICULAR' ? true : veicular;
        });
        return veicular;
      };

      it('Deve ajustar o conteúdo para o plano em modo ATUADO', function() {
        inicializaTesteModoOperacao('ATUADO');
        var plano = scope.currentPlano;

        expect(plano.tempoCiclo).toBe(null);
        expect(plano.defasagem).toBe(null);
        plano.estagiosPlanos.forEach(function(e) {
          var estagio = _.find(scope.objeto.estagiosPlanos, {idJson: e.idJson});
          expect(estagio.tempoVerde).toBe(null);
          expect(estagio.tempoVerdeMinimo).toBe(10);
          expect(estagio.tempoVerdeMaximo).toBe(scope.objeto.verdeMaximoMin);
          expect(estagio.tempoVerdeIntermediario).toBe(scope.objeto.verdeIntermediarioMin);
          expect(estagio.tempoExtensaoVerde).toBe(scope.objeto.extensaoVerdeMin);
        });
      });

      it('Deve ajustar o conteúdo para o plano em modo COORDENADO', function() {
        inicializaTesteModoOperacao('TEMPO_FIXO_COORDENADO');
        var plano = scope.currentPlano;

        expect(plano.tempoCiclo).toBe(scope.objeto.cicloMin);
        expect(plano.defasagem).toBe(scope.objeto.defasagemMin);
        plano.estagiosPlanos.forEach(function(e) {
          var estagio = _.find(scope.objeto.estagiosPlanos, {idJson: e.idJson});
          if (isEstagioVeicular(scope.objeto, estagio)) {
            expect(estagio.tempoVerde).toBe(10);
          } else {
            expect(estagio.tempoVerde).toBe(4);
          }
          expect(estagio.tempoVerdeMinimo).toBe(null);
          expect(estagio.tempoVerdeMaximo).toBe(null);
          expect(estagio.tempoVerdeIntermediario).toBe(null);
          expect(estagio.tempoExtensaoVerde).toBe(null);
        });
      });

      it('Deve ajustar o conteúdo para o plano em modo ISOLADO', function() {
        inicializaTesteModoOperacao('TEMPO_FIXO_ISOLADO');
        var plano = scope.currentPlano;

        expect(plano.tempoCiclo).toBe(scope.objeto.cicloMin);
        expect(plano.defasagem).toBe(null);
        plano.estagiosPlanos.forEach(function(e) {
          var estagio = _.find(scope.objeto.estagiosPlanos, {idJson: e.idJson});
          if (isEstagioVeicular(scope.objeto, estagio)) {
            expect(estagio.tempoVerde).toBe(10);
          } else {
            expect(estagio.tempoVerde).toBe(4);
          }
          expect(estagio.tempoVerdeMinimo).toBe(null);
          expect(estagio.tempoVerdeMaximo).toBe(null);
          expect(estagio.tempoVerdeIntermediario).toBe(null);
          expect(estagio.tempoExtensaoVerde).toBe(null);
        });
      });

      it('Deve ajustar o conteúdo para o plano em modo INTERMITENTE', function() {
        inicializaTesteModoOperacao('INTERMITENTE');
        var plano = scope.currentPlano;

        expect(plano.tempoCiclo).toBe(scope.objeto.cicloMin);
        expect(plano.defasagem).toBe(null);
        plano.estagiosPlanos.forEach(function(e) {
          var estagio = _.find(scope.objeto.estagiosPlanos, {idJson: e.idJson});
          if (isEstagioVeicular(scope.objeto, estagio)) {
            expect(estagio.tempoVerde).toBe(10);
          } else {
            expect(estagio.tempoVerde).toBe(4);
          }
          expect(estagio.tempoVerdeMinimo).toBe(null);
          expect(estagio.tempoVerdeMaximo).toBe(null);
          expect(estagio.tempoVerdeIntermediario).toBe(null);
          expect(estagio.tempoExtensaoVerde).toBe(null);
        });
      });

      it('Deve ajustar o conteúdo para o plano em modo APAGADO', function() {
        inicializaTesteModoOperacao('APAGADO');
        var plano = scope.currentPlano;

        expect(plano.tempoCiclo).toBe(scope.objeto.cicloMin);
        expect(plano.defasagem).toBe(null);
        plano.estagiosPlanos.forEach(function(e) {
          var estagio = _.find(scope.objeto.estagiosPlanos, {idJson: e.idJson});
          if (isEstagioVeicular(scope.objeto, estagio)) {
            expect(estagio.tempoVerde).toBe(10);
          } else {
            expect(estagio.tempoVerde).toBe(4);
          }
          expect(estagio.tempoVerdeMinimo).toBe(null);
          expect(estagio.tempoVerdeMaximo).toBe(null);
          expect(estagio.tempoVerdeIntermediario).toBe(null);
          expect(estagio.tempoExtensaoVerde).toBe(null);
        });
      });
    });

    describe('resetarPlano', function () {
      var deferred, planoRemovido;

      beforeEach(inject(function(influuntAlert, $q) {
        beforeEachFn(ControladorComPlanos);
        var plano = _.find(scope.objeto.planos, {posicao: 1});

        scope.copiarPlano(plano);
        scope.planosDestino = _.chain(scope.objeto.planos).reject('configurado').take(1).value();
        scope.confirmacaoCopiarPlano();
        scope.$apply();

        deferred = $q.defer();
        spyOn(influuntAlert, 'confirm').and.returnValue(deferred.promise);
        var index = _.findIndex(scope.objeto.planos, {posicao: scope.planosDestino[0].posicao});
        planoRemovido = scope.objeto.planos[index];
        scope.resetarPlano(planoRemovido, index);
      }));

      it('O plano não deve ser resetado se o usuario marcar "nao" no popup de confirmação', function() {
        deferred.resolve(false);
        scope.$apply();

        var p = _.find(scope.objeto.planos, {posicao: planoRemovido.posicao});
        expect(p.configurado).toBeTruthy();
      });

      it('Se for um plano ainda não salvo na API, deve remover o plano localmente', function() {
        deferred.resolve(true);
        scope.$apply();
        var p = _.find(scope.objeto.planos, {posicao: planoRemovido.posicao});
        expect(p.configurado).toBeFalsy();
        expect(_.find(scope.currentAnel.planos, {idJson: p.idJson})).toBeDefined();
      });

      it('Se for um plano salvo na API, deve remover na API e localmente.', function() {
        $httpBackend.expectDELETE('/planos/123').respond({});
        planoRemovido.id = '123';
        deferred.resolve(true);
        $httpBackend.flush();
        scope.$apply();
        var p = _.find(scope.objeto.planos, {posicao: planoRemovido.posicao});
        expect(p.configurado).toBeFalsy();
        expect(_.find(scope.currentAnel.planos, {idJson: p.idJson})).toBeDefined();
      });

      it('Deve informar o usuário que houve um erro caso a API não responda com codigo de sucesso',
        inject(function(toast) {
          spyOn(toast, 'error');
          $httpBackend.expectDELETE('/planos/123').respond(500, {});
          planoRemovido.id = '123';
          deferred.resolve(true);
          $httpBackend.flush();
          scope.$apply();

          expect(toast.error).toHaveBeenCalled();

          var p = _.find(scope.objeto.planos, {posicao: planoRemovido.posicao});
          expect(p.configurado).toBeTruthy();
          expect(_.find(scope.currentAnel.planos, {idJson: p.idJson})).toBeDefined();
        }));
    });

    describe('renomearPlano', function () {
      var planoRenomeado, nomeAnteriorPlano, deferred;
      beforeEach(inject(function(influuntAlert, $q) {
        beforeEachFn(ControladorComPlanos);
        planoRenomeado = _.chain(scope.objeto.planos).filter('configurado').first().value();
        nomeAnteriorPlano = planoRenomeado.descricao;

        deferred = $q.defer();
        spyOn(influuntAlert, 'prompt').and.returnValue(deferred.promise);
      }));

      it('Deve manter o nome do plano se o usuário cancelar ou não passar um nome no popup', function() {
        scope.renomearPlano(planoRenomeado);
        deferred.resolve(null);
        scope.$apply();
        expect(planoRenomeado.descricao).toBe(nomeAnteriorPlano);
      });

      it('Deve alterar o nome do plano', function() {
        scope.renomearPlano(planoRenomeado);
        deferred.resolve('novo nome');
        scope.$apply();
        expect(planoRenomeado.descricao).toBe('novo nome');
      });
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
        $httpBackend.expectPOST('/planos').respond(200, resposta);
        $httpBackend.flush();
        scope.$apply();

        expect(scope.objeto.id).toEqual(resposta.id);
      });

      it('Deve exibir mensagens de validação em caso de inconsistencia.', inject(function(handleValidations) {
        spyOn(handleValidations, 'buildValidationMessages');
        $httpBackend.expectPOST('/planos').respond(422, {});
        $httpBackend.flush();
        scope.$apply();

        expect(handleValidations.buildValidationMessages).toHaveBeenCalled();
      }));
    });

    describe('verificaVerdeMinimoDoEstagio', function () {
      var influuntAlert, deferred;
      beforeEach(inject(function(planoService, _influuntAlert_) {
        beforeEachFn(ControladorComPlanos);
        influuntAlert = _influuntAlert_;
        scope.currentEstagioPlanoIndex = 0;
        scope.currentEstagiosPlanos = [{idJson: 'ep1', estagio: {idJson: 'e1'}}];
        scope.objeto = {
          estagios: [{idJson: 'e1'}]
        };

        deferred = $q.defer();
        spyOn(planoService, 'verdeMinimoDoEstagio').and.returnValue(10);
        spyOn(influuntAlert, 'alert');
        spyOn(influuntAlert, 'confirm').and.returnValue(deferred.promise);
      }));

      it('Deve atualizar o valor de tempo de verde do estágio se o tempo informado for maior que o minimo válido', function() {
        var tempoVerde = 11;
        scope.currentEstagiosPlanos[scope.currentEstagioPlanoIndex].tempoVerde = tempoVerde;
        scope.verificaVerdeMinimoDoEstagio(null, tempoVerde);
        scope.$apply();

        expect(influuntAlert.alert).not.toHaveBeenCalled();
        expect(influuntAlert.confirm).not.toHaveBeenCalled();
        expect(scope.currentEstagiosPlanos[scope.currentEstagioPlanoIndex].tempoVerde).toBe(tempoVerde);
      });

      it('Deve manter o valor antigo se for um estágio de pedestres e valor mínimo for maior que o valor informado', function() {
        var tempoVerdeAnterior = 15;
        var tempoVerde = 5;
        scope.objeto.estagios[0].isVeicular = false;
        scope.currentEstagiosPlanos[scope.currentEstagioPlanoIndex].tempoVerde = tempoVerde;
        scope.verificaVerdeMinimoDoEstagio(tempoVerdeAnterior, tempoVerde);
        scope.$apply();

        expect(influuntAlert.alert).toHaveBeenCalled();
        expect(influuntAlert.confirm).not.toHaveBeenCalled();
        expect(scope.currentEstagiosPlanos[scope.currentEstagioPlanoIndex].tempoVerde).toBe(tempoVerdeAnterior);
      });

      it('O estagio veicular poderá ter um tempo de verde menor que o minimo se o usuario confirmar que isto está correto', function() {
        var tempoVerdeAnterior = 15;
        var tempoVerde = 5;
        scope.objeto.estagios[0].isVeicular = true;
        scope.currentEstagiosPlanos[scope.currentEstagioPlanoIndex].tempoVerde = tempoVerde;

        scope.verificaVerdeMinimoDoEstagio(tempoVerdeAnterior, tempoVerde);
        deferred.resolve(true);
        scope.$apply();

        expect(influuntAlert.alert).not.toHaveBeenCalled();
        expect(influuntAlert.confirm).toHaveBeenCalled();
        expect(scope.currentEstagiosPlanos[scope.currentEstagioPlanoIndex].tempoVerde).toBe(tempoVerde);
      });

      it('O estagio veicular não poderá ter um tempo de verde menor que o minimo se o usuario não confirmar que isto está correto', function() {
        var tempoVerdeAnterior = 15;
        var tempoVerde = 5;
        scope.objeto.estagios[0].isVeicular = true;
        scope.currentEstagiosPlanos[scope.currentEstagioPlanoIndex].tempoVerde = tempoVerde;

        scope.verificaVerdeMinimoDoEstagio(tempoVerdeAnterior, tempoVerde);
        deferred.resolve(false);
        scope.$apply();

        expect(influuntAlert.alert).not.toHaveBeenCalled();
        expect(influuntAlert.confirm).toHaveBeenCalled();
        expect(scope.currentEstagiosPlanos[scope.currentEstagioPlanoIndex].tempoVerde).toBe(tempoVerdeAnterior);
      });
    });
  });

  describe('EstagiosPlanos', function () {
    var sequenciaOriginal;
    beforeEach(function() {
      beforeEachFn(ControladorComPlanos);
      var plano = _.find(scope.objeto.planos, {configurado: true, posicao: 1});
      scope.selecionaPlano(plano, 0);
      scope.$apply();

      sequenciaOriginal = _.clone(scope.currentEstagiosPlanos);
    });

    describe('adicionarEstagio', function () {
      it('Adiciona um estagio plano à sequencia de estágios do plano na ultima posicao', function() {
        var estagioPlano = scope.currentEstagiosPlanos[0];
        scope.adicionarEstagio(estagioPlano.estagio);
        scope.$apply();

        var objeto = _.last(scope.currentEstagiosPlanos);
        expect(objeto.posicao).toBe(sequenciaOriginal.length + 1);
        expect(scope.currentEstagiosPlanos.length).toBe(sequenciaOriginal.length + 1);
        expect(_.find(scope.currentEstagiosPlanos, {idJson: objeto.idJson})).toBeDefined();
        expect(_.find(scope.objeto.estagiosPlanos, {idJson: objeto.idJson})).toBeDefined();
        expect(_.find(scope.currentPlano.estagiosPlanos, {idJson: objeto.idJson})).toBeDefined();
      });
    });

    describe('removerEstagioPlano', function () {
      var deferred, estagioPlano;
      beforeEach(inject(function(influuntAlert) {
        deferred = $q.defer();
        spyOn(influuntAlert, 'delete').and.returnValue(deferred.promise);
        estagioPlano = scope.currentEstagiosPlanos[0];
        scope.removerEstagioPlano(estagioPlano);
      }));

      it('Nao deve remover o estagio da lista de estagios do plano se o usuario nao confirmar', function() {
        deferred.resolve(false);
        scope.$apply();
        expect(scope.currentEstagiosPlanos.length).toBe(sequenciaOriginal.length);
      });

      it('Remove o estagio da sequencia do plano', function() {
        deferred.resolve(true);
        scope.$apply();
        expect(scope.currentEstagiosPlanos.length).toBe(sequenciaOriginal.length - 1);
        expect(_.find(scope.currentEstagiosPlanos, {idJson: estagioPlano.idJson})).not.toBeDefined();
        expect(_.find(scope.objeto.estagiosPlanos, {idJson: estagioPlano.idJson})).not.toBeDefined();
        expect(_.find(scope.currentPlano.estagiosPlanos, {idJson: estagioPlano.idJson})).not.toBeDefined();
        expect(sequenciaOriginal[1].posicao).toBe(1);
      });
    });

    describe('selecionaEstagioPlano', function() {
      it('Deve carregar o estagio atual e a lista de estágios alternativos para estágio dispensável', function() {
        var estagioPlano = sequenciaOriginal[1];
        var opcoesDisponiveis = [sequenciaOriginal[0], sequenciaOriginal[2]].map(function(ep) {
          return ep.idJson;
        });
        scope.selecionaEstagioPlano(estagioPlano, 1);
        scope.$apply();

        expect(scope.currentEstagioPlanoIndex).toBe(1);
        expect(scope.currentEstagioPlano).toEqual(estagioPlano);
        expect(_.map(scope.opcoesEstagiosDisponiveis, 'idJson')).toEqual(opcoesDisponiveis);
      });

      it('Deve apresentar somente um estágio caso anterior e próximo ao atual dispensável sejam o mesmo', function() {
        var primeiroEstagio = _.cloneDeep(scope.currentEstagiosPlanos[0]);
        primeiroEstagio.id = UUID.generate();
        primeiroEstagio.idJson = UUID.generate();
        primeiroEstagio.posicao = 3;
        scope.currentEstagiosPlanos.splice(2, 1, primeiroEstagio);

        scope.selecionaEstagioPlano(scope.currentEstagiosPlanos[1], 1);
        scope.$apply();

        expect(scope.opcoesEstagiosDisponiveis.length).toBe(1);
        expect(scope.opcoesEstagiosDisponiveis[0].posicaoEstagio).toBe(1);


        scope.currentEstagiosPlanos.splice(2, 1);
        scope.selecionaEstagioPlano(scope.currentEstagiosPlanos[1], 1);
        scope.$apply();

        expect(scope.opcoesEstagiosDisponiveis.length).toBe(1);
        expect(scope.opcoesEstagiosDisponiveis[0].posicaoEstagio).toBe(1);
      });
    });

    describe('leftEstagio', function () {
      it('Nao permite o elemento na posicao 0 trocar de posicao à esquerda', function() {
        expect(scope.leftEstagio(0)).toBeFalsy();
      });

      it('Deve trocar a posicao do estagio plano atual com o anterior na lista', function() {
        scope.leftEstagio(1);
        scope.$apply();

        expect(sequenciaOriginal[0].idJson).toBe(scope.currentEstagiosPlanos[1].idJson);
        expect(sequenciaOriginal[1].idJson).toBe(scope.currentEstagiosPlanos[0].idJson);
        expect(scope.currentEstagiosPlanos[0].posicao).toBe(1);
        expect(scope.currentEstagiosPlanos[1].posicao).toBe(2);
      });
    });

    describe('rightEstagio', function () {
      it('Nao permite o elemento última posição troque de lugar à direita', function() {
        var ultimaPosicao = scope.currentEstagiosPlanos.length - 1;
        expect(scope.rightEstagio(ultimaPosicao)).toBeFalsy();
      });

      it('Deve trocar a posicao do estagio plano atual com o proximo na lista', function() {
        scope.rightEstagio(1);
        scope.$apply();

        expect(sequenciaOriginal[1].idJson).toBe(scope.currentEstagiosPlanos[2].idJson);
        expect(sequenciaOriginal[2].idJson).toBe(scope.currentEstagiosPlanos[1].idJson);
        expect(scope.currentEstagiosPlanos[1].posicao).toBe(2);
        expect(scope.currentEstagiosPlanos[2].posicao).toBe(3);
      });
    });

    describe('getEstagio', function () {
      it('Retorna um estagio de um estagio plano', function() {
        var estagioPlano = sequenciaOriginal[0];
        expect(scope.getEstagio(estagioPlano)).toBeDefined();
      });

      it('Não retorna o estágio se o estágio plano nao existir', function() {
        expect(scope.getEstagio()).not.toBeDefined();
      });

      it('Não retorna o estagio se o estagio plano referenciar um estágio não existente', function() {
        expect(scope.getEstagio({idJson: 'nao_existente'})).not.toBeDefined();
      });
    });
  });

  describe('utils', function () {
    describe('onChangeCheckboxGrupo', function () {
      var grupo;
      beforeEach(function() {
        grupo = {posicao: 1, intervalos: []};
        beforeEachFn(ControladorComPlanos);
      });

      it('Deve ativar/desativar o grupo semafórico no plano', function() {
        scope.onChangeCheckboxGrupo(grupo, true);
        scope.$apply();
        var grupoSemaforico = _.find(scope.objeto.gruposSemaforicos, {posicao: grupo.posicao});
        var grupoSemaforioPlano = _.find(
          scope.objeto.gruposSemaforicosPlanos,
          {plano: {idJson: scope.currentPlano.idJson}, grupoSemaforico: {idJson: grupoSemaforico.idJson}}
        );
        expect(grupoSemaforioPlano.ativado).toBeTruthy();

        scope.onChangeCheckboxGrupo(grupo, false);
        scope.$apply();
        grupoSemaforico = _.find(scope.objeto.gruposSemaforicos, {posicao: grupo.posicao});
        grupoSemaforioPlano = _.find(
          scope.objeto.gruposSemaforicosPlanos,
          {plano: {idJson: scope.currentPlano.idJson}, grupoSemaforico: {idJson: grupoSemaforico.idJson}}
        );
        expect(grupoSemaforioPlano.ativado).toBeFalsy();
      });

      it('Deve ativar/desativar o grupo semafórico no plano no diagrama', inject(function(modoOperacaoService) {
        scope.onChangeCheckboxGrupo(grupo, false);
        scope.$apply();
        expect(grupo.intervalos[0]).toBeDefined();
        expect(grupo.intervalos[0].status).toBe(modoOperacaoService.get('APAGADO'));

        scope.onChangeCheckboxGrupo(grupo, true);
        scope.$apply();
        expect(grupo.intervalos[0]).not.toBeDefined();
      }));
    });
  });

  describe('bugs', function () {
    var deferred;
    beforeEach(inject(function(influuntAlert, $q) {
      beforeEachFn(ControladorComPlanos);
      // Mocks a call for confirm method and calls the promise function.
      deferred = $q.defer();
      spyOn(influuntAlert, 'delete').and.returnValue(deferred.promise);
    }));

    it('Adicionar, remover e trocar modo de operação', function() {
      var estagioPlano = _.find(scope.objeto.estagiosPlanos, {idJson: scope.currentPlano.estagiosPlanos[0].idJson});
      expect(scope.currentPlano.estagiosPlanos.length).toBe(3);

      scope.adicionarEstagio(estagioPlano.estagio);
      expect(scope.currentPlano.estagiosPlanos.length).toBe(4);

      estagioPlano = _.find(scope.objeto.estagiosPlanos, {idJson: scope.currentPlano.estagiosPlanos[0].idJson});
      scope.removerEstagioPlano(estagioPlano);
      deferred.resolve(true);
      scope.$apply();
      expect(scope.currentPlano.estagiosPlanos.length).toBe(3);

      scope.currentPlano.modoOperacao = 'ATUADO';
      scope.onChangeModoOperacao();

      expect(scope.currentPlano.estagiosPlanos.length).toBe(3);
    });

    describe('erros ao salvar', function () {
      beforeEach(function() {
        beforeEachFn(ControladorComPlanos);
      });

      it('Badge de erro deve aparecer na posição correta', function() {
        scope.selecionaAnelPlanos(0);
        scope.errors = {'aneis':[{'versoesPlanos':[{'planos':[{'ultrapassaTempoCiclo':['A soma dos tempos dos estágios ({temposEstagios}s) é diferente do tempo de ciclo ({tempoCiclo}s).']},null,{'ultrapassaTempoCiclo':['A soma dos tempos dos estágios ({temposEstagios}s) é diferente do tempo de ciclo ({tempoCiclo}s).']}]}],'all':[{'planos':[{'ultrapassaTempoCiclo':['A soma dos tempos dos estágios ({temposEstagios}s) é diferente do tempo de ciclo ({tempoCiclo}s).']},null,{'ultrapassaTempoCiclo':['A soma dos tempos dos estágios ({temposEstagios}s) é diferente do tempo de ciclo ({tempoCiclo}s).']}]}]}]};

        expect(scope.planoTemErro(0)).toBe(true);
        expect(scope.planoTemErro(1)).toBe(false);
        expect(scope.planoTemErro(2)).toBe(true);
      });
    });

    describe('erros ao salvar anel com plano exclusivo', function () {
      beforeEach(function() {
        beforeEachFn(ControladorComPlanoExclusivo);
      });

      it('Badge de erro deve aparecer na posição correta', function() {
        scope.selecionaAnelPlanos(0);
        scope.errors = {'aneis':[{'versoesPlanos':[{'planos':[null, {'ultrapassaTempoCiclo':['A soma dos tempos dos estágios ({temposEstagios}s) é diferente do tempo de ciclo ({tempoCiclo}s).']},null,{'ultrapassaTempoCiclo':['A soma dos tempos dos estágios ({temposEstagios}s) é diferente do tempo de ciclo ({tempoCiclo}s).']}]}],'all':[{'planos':[{'ultrapassaTempoCiclo':['A soma dos tempos dos estágios ({temposEstagios}s) é diferente do tempo de ciclo ({tempoCiclo}s).']},null,{'ultrapassaTempoCiclo':['A soma dos tempos dos estágios ({temposEstagios}s) é diferente do tempo de ciclo ({tempoCiclo}s).']}]}]}]};

        expect(scope.planoTemErro(0)).toBe(false);
        expect(scope.planoTemErro(1)).toBe(true);
        expect(scope.planoTemErro(2)).toBe(false);
        expect(scope.planoTemErro(3)).toBe(true);
      });

      it('Deve pegar mensagem de erro de forma correta', function() {
        scope.selecionaAnelPlanos(0);
        scope.errors = {'aneis':[{'versoesPlanos':[{'planos':[null, {'numeroEstagiosEmModoManualOk':['Este plano deve ter a mesma quantidade de estágios que os outros planos em modo manual exclusivo.']},null,{'ultrapassaTempoCiclo':['A soma dos tempos dos estágios ({temposEstagios}s) é diferente do tempo de ciclo ({tempoCiclo}s).']}]}],'all':[{'planos':[{'ultrapassaTempoCiclo':['A soma dos tempos dos estágios ({temposEstagios}s) é diferente do tempo de ciclo ({tempoCiclo}s).']},null,{'ultrapassaTempoCiclo':['A soma dos tempos dos estágios ({temposEstagios}s) é diferente do tempo de ciclo ({tempoCiclo}s).']}]}]}]};
        var erros = scope.getErrosPlanos(_.get(scope.errors, 'aneis[0].versoesPlanos[0]'));
        expect(erros.length).toBe(1);
        expect(erros[0]).toBe('Este plano deve ter a mesma quantidade de estágios que os outros planos em modo manual exclusivo.');
      });
    });

    describe('testar limpar plano de um anel com plano exclusivo', function () {
      var deferred;
      beforeEach(inject(function(influuntAlert, $q) {
        beforeEachFn(ControladorComPlanoExclusivo);
        deferred = $q.defer();
        spyOn(influuntAlert, 'confirm').and.returnValue(deferred.promise);
      }));

      it('Confirmando - Deve permanecer com a numeracao correta', function() {
        scope.selecionaAnelPlanos(0);

        expect(scope.currentAnel.aceitaModoManual).toBeTruthy();
        expect(scope.currentPlanos.length).toBe(17);
        var plano = scope.currentPlanos[6];
        plano.configurado = true;
        scope.selecionaPlano(plano, 6);
        plano.configurado = false;

        expect(plano.posicao).toBe(6);
        expect(scope.currentPlano.posicao).toBe(6);
        scope.resetarPlano(plano, 6);
        deferred.resolve(true);
        scope.$apply();

        expect(scope.currentPlanos[0].posicao).toBe(0);
        expect(scope.currentPlanos[1].posicao).toBe(1);
        expect(scope.currentPlanos[6].posicao).toBe(6);
        expect(scope.currentPlanos[16].posicao).toBe(16);
        expect(scope.currentPlano.posicao).toBe(6);

        expect(scope.currentPlanos.length).toBe(17);

        _.each(scope.currentPlanos, function(plano, index){
          expect(plano.posicao).toBe(index);
        });
      });

      it('Não Confirmando - Deve permanecer com a numeracao correta', function() {
        scope.selecionaAnelPlanos(0);

        expect(scope.currentAnel.aceitaModoManual).toBeTruthy();
        expect(scope.currentPlanos.length).toBe(17);
        var plano = scope.currentPlanos[6];
        plano.configurado = true;
        scope.selecionaPlano(plano, 6);
        plano.configurado = false;

        expect(plano.posicao).toBe(6);
        expect(scope.currentPlano.posicao).toBe(6);
        scope.resetarPlano(plano, 6);
        deferred.resolve(false);
        scope.$apply();

        expect(scope.currentPlano.configurado).toBeTruthy();

        expect(scope.currentPlanos[0].posicao).toBe(0);
        expect(scope.currentPlanos[1].posicao).toBe(1);
        expect(scope.currentPlanos[6].posicao).toBe(6);
        expect(scope.currentPlanos[16].posicao).toBe(16);
        expect(scope.currentPlano.posicao).toBe(6);

        expect(scope.currentPlanos.length).toBe(17);

        _.each(scope.currentPlanos, function(plano, index){
          expect(plano.posicao).toBe(index);
        });
      });
    });

    describe('testar copiar plano de um anel com plano exclusivo', function () {
      var deferred;
      beforeEach(inject(function(influuntAlert, $q) {
        beforeEachFn(ControladorComPlanoExclusivo);
        deferred = $q.defer();
        spyOn(influuntAlert, 'confirm').and.returnValue(deferred.promise);
      }));

      it('Deve permanecer com a numeracao correta', function() {
        scope.selecionaAnelPlanos(0);

        expect(scope.currentAnel.aceitaModoManual).toBeTruthy();
        expect(scope.currentPlanos.length).toBe(17);
        var plano = scope.currentPlanos[1];
        scope.planoCopiado = plano;
        scope.planosDestino = [scope.currentPlanos[6], scope.currentPlanos[12], scope.currentPlanos[16]];
        scope.confirmacaoCopiarPlano();

        expect(scope.currentPlanos.length).toBe(17);

        _.each(scope.currentPlanos, function(plano, index){
          expect(plano.posicao).toBe(index);
        });
      });
    });

    describe('testar limpar plano de um anel sem plano exclusivo', function () {
      var deferred;
      beforeEach(inject(function(influuntAlert, $q) {
        beforeEachFn(ControladorComPlanos);
        deferred = $q.defer();
        spyOn(influuntAlert, 'confirm').and.returnValue(deferred.promise);
      }));

      it('Confirmando - Deve permanecer com a numeracao correta', function() {
        scope.selecionaAnelPlanos(0);

        expect(scope.currentAnel.aceitaModoManual).toBeFalsy();
        expect(scope.currentPlanos.length).toBe(16);
        var plano = scope.currentPlanos[10];
        plano.configurado = true;
        scope.selecionaPlano(plano, 10);
        plano.configurado = false;

        expect(plano.posicao).toBe(11);
        expect(scope.currentPlano.posicao).toBe(11);
        scope.resetarPlano(plano, 10);
        deferred.resolve(true);
        scope.$apply();

        expect(scope.currentPlanos[0].posicao).toBe(1);
        expect(scope.currentPlanos[1].posicao).toBe(2);
        expect(scope.currentPlanos[10].posicao).toBe(11);
        expect(scope.currentPlanos[15].posicao).toBe(16);
        expect(scope.currentPlano.posicao).toBe(11);

        expect(scope.currentPlanos.length).toBe(16);

        _.each(scope.currentPlanos, function(plano, index){
          expect(plano.posicao).toBe(index+1);
        });
      });

      it('Não Confirmando - Deve permanecer com a numeracao correta', function() {
        scope.selecionaAnelPlanos(0);

        expect(scope.currentAnel.aceitaManual).toBeFalsy();
        expect(scope.currentPlanos.length).toBe(16);
        var plano = scope.currentPlanos[6];
        plano.configurado = true;
        scope.selecionaPlano(plano, 6);
        plano.configurado = false;

        expect(plano.posicao).toBe(7);
        expect(scope.currentPlano.posicao).toBe(7);
        scope.resetarPlano(plano, 6);
        deferred.resolve(false);
        scope.$apply();

        expect(scope.currentPlano.configurado).toBeTruthy();

        expect(scope.currentPlanos[0].posicao).toBe(1);
        expect(scope.currentPlanos[1].posicao).toBe(2);
        expect(scope.currentPlanos[6].posicao).toBe(7);
        expect(scope.currentPlanos[15].posicao).toBe(16);
        expect(scope.currentPlano.posicao).toBe(7);

        expect(scope.currentPlanos.length).toBe(16);

        _.each(scope.currentPlanos, function(plano, index){
          expect(plano.posicao).toBe(index+1);
        });
      });
    });

    describe('testar copiar plano de um anel sem plano exclusivo', function () {
      var deferred;
      beforeEach(inject(function(influuntAlert, $q) {
        beforeEachFn(ControladorComPlanos);
        deferred = $q.defer();
        spyOn(influuntAlert, 'confirm').and.returnValue(deferred.promise);
      }));

      it('Deve permanecer com a numeracao correta', function() {
        scope.selecionaAnelPlanos(0);

        expect(scope.currentAnel.aceitaModoManual).toBeFalsy();
        expect(scope.currentPlanos.length).toBe(16);
        var plano = scope.currentPlanos[1];
        scope.planoCopiado = plano;
        scope.planosDestino = [scope.currentPlanos[6], scope.currentPlanos[12], scope.currentPlanos[15]];
        scope.confirmacaoCopiarPlano();

        expect(scope.currentPlanos.length).toBe(16);

        _.each(scope.currentPlanos, function(plano, index){
          expect(plano.posicao).toBe(index+1);
        });
      });
    });

    describe('para a badge de erro aparecer corretamente', function () {
      beforeEach(function() {
        beforeEachFn(ControladorComPlanos);
        $httpBackend.expectPOST('/planos');
      });

      it('os planos devem ser ordenados antes de enviados para o servidor', function() {
        var orderedIdJson = _
          .chain(scope.objeto.planos)
          .orderBy('posicao')
          .map('idJson')
          .value();
        var orderedIdJsonVersoes = _.map(scope.objeto.versoesPlanos[0].planos, 'idJson');
        expect(orderedIdJson).not.toEqual(orderedIdJsonVersoes);

        scope.submitForm();

        orderedIdJson = _
          .chain(scope.objeto.planos)
          .orderBy('posicao')
          .map('idJson')
          .value();
        orderedIdJsonVersoes = _.map(scope.objeto.versoesPlanos[0].planos, 'idJson');
        expect(orderedIdJson).toEqual(orderedIdJsonVersoes);
      });
    });
  });

  describe('getErros', function () {
    beforeEach(inject(function($timeout, handleValidations) {
      beforeEachFn(ControladorComPlanos);
      var errors = [
        {
          "root": "Controlador",
          "message": "O tempo de verde intermediário deve estar entre os valores de verde mínimo e verde máximo.",
          "path": "aneis[0].versoesPlanos[0].planos[0].estagiosPlanos[0].tempoVerdeIntermediarioFieldEntreMinimoMaximo"
        },
        {
          "root": "Controlador",
          "message": "O estágio precisa estar associado a um detector para ser dispensável.",
          "path": "aneis[0].versoesPlanos[0].planos[0].estagiosPlanos[2].podeSerEstagioDispensavel"
        },
        {
          "root": "Controlador",
          "message": "O estágio que recebe o tempo do estágio dispensável não pode ficar em branco.",
          "path": "aneis[0].versoesPlanos[0].planos[1].estagiosPlanos[1].estagioQueRecebeEstagioDispensavel"
        },
        {
          "root": "Controlador",
          "message": "O tempo de verde mínimo deve ser maior ou igual ao verde de segurança e menor que o verde máximo.",
          "path": "aneis[0].versoesPlanos[0].planos[1].estagiosPlanos[1].tempoVerdeMinimoFieldMenorMaximo"
        },
        {
          "root": "Controlador",
          "message": "O tempo de verde intermediário deve estar entre os valores de verde mínimo e verde máximo.",
          "path": "aneis[0].versoesPlanos[0].planos[1].estagiosPlanos[1].tempoVerdeIntermediarioFieldEntreMinimoMaximo"
        },
        {
          "root": "Controlador",
          "message": "O tempo de verde mínimo deve ser maior ou igual ao verde de segurança e menor que o verde máximo.",
          "path": "aneis[0].versoesPlanos[0].planos[1].estagiosPlanos[0].tempoVerdeMinimoFieldMenorMaximo"
        },
        {
          "root": "Controlador",
          "message": "Defasagem deve estar entre {min} e o tempo de ciclo",
          "path": "aneis[0].versoesPlanos[0].planos[1].defasagem"
        },
        {
          "root": "Controlador",
          "message": "O tempo de verde está menor que o tempo de segurança configurado.",
          "path": "aneis[0].versoesPlanos[0].planos[3].gruposSemaforicosPlanos[4].respeitaVerdesDeSeguranca"
        },
        {
          "root": "Controlador",
          "message": "A soma dos tempos dos estágios ({temposEstagios}s) é diferente do tempo de ciclo ({tempoCiclo}s).",
          "path": "aneis[0].versoesPlanos[0].planos[3].ultrapassaTempoCiclo"
        }
      ];

      scope.submitForm();
      $httpBackend.expectPOST('/planos').respond(422, errors);
      $httpBackend.flush();
      scope.selecionaAnelPlanos(0);
      scope.$apply();
      $timeout.flush();
    }));

    it('Deve ter erro o anel 0 e não ter erro no anel 1', function() {
      expect(scope.anelTemErro(0)).toBeTruthy();
      expect(scope.anelTemErro(1)).toBeFalsy();
    });

    it('Deve ter erro para os planos 0 e 1 e não ter erro para o plano 2', function() {
      expect(scope.planoTemErro(0)).toBeTruthy();
      expect(scope.planoTemErro(1)).toBeTruthy();
      expect(scope.planoTemErro(2)).toBeFalsy();
    });

    it('Deve existir um erro de defasagem no plano 1 e não existir no plano 0', function() {
      expect(scope.erroDefasagem()).toBeFalsy();
      scope.selecionaPlano(scope.currentPlanos[1], 1);
      expect(scope.erroDefasagem()).toBeTruthy();
      expect(scope.erroDefasagem()[0]).toBe("Defasagem deve estar entre 0 e o tempo de ciclo");
    });

    it('Deve existir erros em estagios planos', function() {
      scope.selecionaPlano(scope.currentPlanos[0], 0);
      expect(scope.getErrosEstagiosPlanos(0)).toBeTruthy();
      expect(scope.getErrosEstagiosPlanos(0).tempoVerdeIntermediarioFieldEntreMinimoMaximo[0]).toBe('O tempo de verde intermediário deve estar entre os valores de verde mínimo e verde máximo.');
      scope.selecionaPlano(scope.currentPlanos[1], 1);
      expect(scope.getErrosEstagiosPlanos(1)).toBeTruthy();
      expect(scope.getErrosEstagiosPlanos(1).estagioQueRecebeEstagioDispensavel[0]).toBe('O estágio que recebe o tempo do estágio dispensável não pode ficar em branco.');
      expect(scope.getErrosEstagiosPlanos(1).tempoVerdeMinimoFieldMenorMaximo[0]).toBe('O tempo de verde mínimo deve ser maior ou igual ao verde de segurança e menor que o verde máximo.');
      expect(scope.getErrosEstagiosPlanos(1).tempoVerdeIntermediarioFieldEntreMinimoMaximo[0]).toBe('O tempo de verde intermediário deve estar entre os valores de verde mínimo e verde máximo.');
      expect(scope.getErrosEstagiosPlanos(2)).toBeFalsy();
    });

    it('Deve existir erros no plano', function() {
      scope.selecionaPlano(scope.currentPlanos[3], 3);
      var erros = scope.errors.aneis[0].versoesPlanos[0];
      expect(scope.getErrosPlanos(erros).length).toBe(2);
      expect(scope.getErrosPlanos(erros)[0]).toBe('G4 - O tempo de verde está menor que o tempo de segurança configurado.');
      expect(scope.getErrosPlanos(erros)[1]).toBe('A soma dos tempos dos estágios (0s) é diferente do tempo de ciclo (30s).');

      var estagioPlano = scope.currentEstagiosPlanos[0];
      estagioPlano.tempoEstagio = 15;
      scope.currentPlano.tempoCiclo = 45;
      expect(scope.getErrosPlanos(erros)[1]).toBe('A soma dos tempos dos estágios (0s) é diferente do tempo de ciclo (30s).');
    });

  });
});
