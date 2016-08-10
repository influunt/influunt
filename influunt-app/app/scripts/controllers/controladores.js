'use strict';

/**
 * @ngdoc function
 * @name influuntApp.controller:ControladoresCtrl
 * @description
 * # ControladoresCtrl
 * Controller of the influuntApp
 */
angular.module('influuntApp')
  .controller('ControladoresCtrl', ['$controller', '$scope', '$state', '$filter', 'Restangular', '$q', 'handleValidations', 'APP_ROOT', 'influuntBlockui',
    function ($controller, $scope, $state, $filter, Restangular, $q, handleValidations, APP_ROOT, influuntBlockui) {


      // Herda todo o comportamento do crud basico.
      $controller('CrudCtrl', {$scope: $scope});
      $scope.inicializaNovoCrud('controladores');
      $scope.hideRemoveCoordenada = true;

      // Seta URL para salvar imagens
      $scope.dados = {
        imagensUrl: APP_ROOT + '/imagens'
      };

      /**
       * Inicializa os dados da tela de index e os objetos requeridos para o filtro.
       */
      $scope.inicializaIndex = function(){
        $scope.filtros = {};
        $scope.filtroLateral = {};
        $scope.index();
      };

      /**
       * Carrega as listas de dependencias dos controladores. Atua na tela de crud.
       */
      $scope.beforeShow = function() {
        Restangular.all('areas').getList().then(function(res) {
          $scope.areas = res;
        });
      };

      /**
       * Carrega os dados de fabricas e cidades, que não estão diretamente relacionados ao contolador.
       */
      var getHelpersControlador = function() {
        return Restangular.one('helpers', 'controlador').get().then(function(res) {
          $scope.data = res;
          $scope.helpers = {};

          if ($scope.objeto.area) {
            var idJson = $scope.objeto.area.idJson;
            var area = _.find($scope.objeto.areas, {idJson: idJson});
            var cidade = _.find($scope.objeto.cidades, {idJson: area.cidade.idJson});

            cidade.areas = cidade.areas.map(function(area) {
              return _.find($scope.objeto.areas, {idJson: area.idJson});
            });

            $scope.helpers.cidade = cidade;
          } else {
            $scope.helpers.cidade = $scope.data.cidades[0];
            $scope.objeto.cidades = $scope.data.cidades;
            $scope.objeto.areas = _.chain($scope.data.cidades).map('areas').flatten().value();
          }

          if ($scope.objeto.modelo) {
            var modelos = _.chain($scope.data.fabricantes).map('modelos').flatten().uniq().value();
            var modelo = _.find(modelos, {id: $scope.objeto.modelo.id});

            var fabricante = _.find($scope.data.fabricantes, {id: modelo.fabricante.id});
            $scope.helpers.fornecedor = fabricante;
          }
        });
      };

      var loadWizardData = function(obj) {
        $scope.objeto = obj;
        getHelpersControlador();
        $scope.validacoes = {
          alerts: []
        };
      };

      /**
       * Função compartilhada por todos os passos do index. Deve carregar os dados
       * do controlador, caso este tenha sido definido.
       *
       * @return     {<type>}  { description_of_the_return_value }
       */
      $scope.inicializaWizard = function() {
        influuntBlockui.block();
        var defer = $q.defer();

        var id = $state.params.id;
        if (id) {
          Restangular.one('controladores', id).get().then(function(res) {
            loadWizardData(res);

            influuntBlockui.unblock();
            defer.resolve(res);
          });
        } else {
          var todosEnderecos = [{idJson: UUID.generate(), localizacao: '', latitude: null, longitude: null}, {idJson: UUID.generate(), localizacao: '', latitude: null, longitude: null}];
          var enderecos = _.map(todosEnderecos, function(i) {return {idJson: i.idJson};});
          loadWizardData({
            limiteEstagio: 16,
            limiteGrupoSemaforico: 16,
            limiteAnel: 4,
            limiteDetectorPedestre: 4,
            limiteDetectorVeicular: 8,
            limiteTabelasEntreVerdes: 2,
            todosEnderecos: todosEnderecos,
            enderecos: enderecos
          });

          influuntBlockui.unblock();
          defer.resolve({});
        }

        return defer.promise;
      };

      $scope.submitForm = function(form, stepResource, nextStep) {
        influuntBlockui.block();
        if (angular.isFunction($scope.beforeSubmitForm)) {
          $scope.beforeSubmitForm();
        }
        if (form.$valid) {
          Restangular
            .all('controladores')
            .all(stepResource)
            .post($scope.objeto)
            .then(function(res) {
              $scope.objeto = res;

              $scope.errors = {};
              $state.go(nextStep, {id: $scope.objeto.id});
              influuntBlockui.unblock();
            })
            .catch(function(res) {
              influuntBlockui.unblock();
              if (res.status === 422) {
                if (angular.isFunction($scope.afterSubmitFormOnValidationError)) {
                  $scope.afterSubmitFormOnValidationError();
                }
                $scope.buildValidationMessages(res.data);
              } else {
                console.error(res);
              }
            });
        }
      };

      /**
       * Seleciona um anel atraves do index da lista de aneis do controlador.
       *
       * @param      {<type>}  index   The index
       */
      $scope.selecionaAnel = function(index) {
        $scope.currentAnelIndex = index;
        $scope.currentAnel = $scope.aneis[$scope.currentAnelIndex];
      };

      /**
       * Seleciona um estágio do anel atual atraves do indice.
       *
       * @param      {<type>}  index   The index
       *
       * @todo verificar se este metodo continua sendo utilizado para algo.
       */
      // $scope.selecionaEstagio = function(index) {
      //   $scope.currentEstagioId = index;
      //   $scope.currentEstagio = $scope.currentAnel.estagios[index];
      //   $scope.atualizaGruposSemaforicosSelecionados();
      // };

      // /**
      //  * @todo verificar se este metodo continua sendo utilizado de forma efetiva.
      //  */
      // $scope.atualizaGruposSemaforicosSelecionados = function() {
      //   var estagioId = $scope.currentEstagio.id;
      //   $scope.gruposSelecionados = $scope.currentAnel.gruposSemaforicos.filter(function(grupo) {
      //     return !!_.filter(grupo.estagioGrupoSemaforicos, {estagio: {id: estagioId}}).length;
      //   });
      // };

      /**
       * Seleciona um grupo semafórico do anel atual atraves do índice.
       *
       * @param      {int}  index   The index
       */
      $scope.selecionaGrupoSemaforico = function(gs, index) {
        $scope.currentGrupoSemaforicoIndex = index;
        $scope.currentGrupoSemaforico = gs;
        $scope.currentGrupoSemaforicoIdentifier = $scope.currentAnelIndex.toString() + index.toString();

        if (angular.isDefined($scope.isTabelaEntreVerdes) && $scope.isTabelaEntreVerdes) {
          $scope.atualizaTabelaEntreVerdes();
          $scope.selecionaTabelaEntreVerdes($scope.currentTabelasEntreVerdes[0], 0);
        }
      };

      $scope.atualizaTabelaEntreVerdes = function() {
        var ids = _.map($scope.currentGrupoSemaforico.tabelasEntreVerdes, 'idJson');

        $scope.currentTabelasEntreVerdes = _
          .chain($scope.objeto.tabelasEntreVerdes)
          .filter(function(tev) {
            return ids.indexOf(tev.idJson) >= 0;
          })
          .orderBy(['posicao'])
          .value();

        return $scope.currentTabelasEntreVerdes;
      };

      /**
       * Seleciona uma tabela entre-verdes do grupo semaforico atual atraves do índice.
       *
       * @param      {int}  index   The index
       */
      $scope.selecionaTabelaEntreVerdes = function(tev, index) {
        $scope.currentTabelaEntreVerdesIndex = index;
        $scope.currentTabelaEntreVerdes = tev;
        $scope.tabelasEntreVerdesTransicoes = _.chain($scope.currentGrupoSemaforico.transicoes)
                                     .map(function(transicao) { return _.find($scope.objeto.transicoes, {idJson: transicao.idJson}); })
                                     .map(function(transicao) { return transicao.tabelaEntreVerdesTransicoes; })
                                     .flatten()
                                     .map(function(tevt) { return _.find($scope.objeto.tabelasEntreVerdesTransicoes, {idJson: tevt.idJson}); })
                                     .filter(function(tevt) {
                                        return _.find($scope.objeto.tabelasEntreVerdes, {idJson: tevt.tabelaEntreVerdes.idJson}).posicao === index + 1;
                                      })
                                     .value();

        $scope.currentTabelaOrigensEDestinos = {};
        $scope.currentGrupoSemaforico.transicoes.forEach(function(t) {
          var transicao = _.find($scope.objeto.transicoes, {idJson: t.idJson});
          $scope.currentTabelaOrigensEDestinos[t.idJson] = {
            origem: _.find($scope.objeto.estagios, {idJson: transicao.origem.idJson}),
            destino: _.find($scope.objeto.estagios, {idJson: transicao.destino.idJson}),
          };
        });

        $scope.atualizaTabelasEntreVerdesTransicoes();
      };

      $scope.atualizaTabelasEntreVerdesTransicoes = function() {
        var ids = _.map($scope.currentTabelaEntreVerdes.tabelaEntreVerdesTransicoes, 'idJson');


        $scope.currentTabelasEntreVerdesTransicoes = _
          .chain($scope.objeto.tabelasEntreVerdesTransicoes)
          .filter(function(tevt) { return ids.indexOf(tevt.idJson) >= 0; })
          .value();

        return $scope.currentTabelasEntreVerdesTransicoes;
      };

      /**
       * Deleta a lista de mensagens de validações globais. Recebe o parametro shownGroup,
       * que deve conter o nome do grupo apresentado nas mensagens.
       *
       * @param      {int}  index       The index
       * @param      {<type>}  shownGroup  The shown group
       */
      $scope.closeAlert = function(index, shownGroup) {
        shownGroup = shownGroup || 'general';
        if ($scope.errors && $scope.errors.aneis[index]) {
          delete $scope.errors.aneis[index][shownGroup];
        }
      };

      $scope.buildValidationMessages = function(errors) {
        $scope.errors = handleValidations.handle(errors);

        if ($scope.errors && _.isArray($scope.errors.aneis)) {
          for (var i = 0; i < $scope.errors.aneis.length; i++) {
            $scope.errors.aneis[i] = $scope.errors.aneis[i] || {};
          }
        }

        $scope.getErrosVerdes();
      };

      $scope.getErrosVerdes = function() {
        $scope.messages = {aneis: []};
        _.each($scope.errors.aneis, function(anel, anelIndex) {
          _.each(anel.gruposSemaforicos, function(gs, gsIndex) {
            var grupoSemaforicoIdJson = $scope.objeto.aneis[anelIndex].gruposSemaforicos[gsIndex].idJson;
            var grupoSemaforico = _.find($scope.objeto.gruposSemaforicos, {idJson: grupoSemaforicoIdJson});
            var posicao = grupoSemaforico.posicao;
            var nomeGS = 'G' + grupoSemaforico.posicao;
            _.each(gs, function(mgs) {
              _.map(mgs, function(msg) {
                $scope.messages.aneis[anelIndex] = $scope.messages.aneis[anelIndex] || [];
                $scope.messages.aneis[anelIndex].push({posicao: posicao, texto: nomeGS + ': ' + msg});
              });
            });
          });
        });
      };

      /**
       * Deve informar que determinado anel possui erros caso haja uma lista de
       * erros para determinado anel.
       *
       * @param      {<type>}  indice  The indice
       * @return     {<type>}  { description_of_the_return_value }
       */
      $scope.anelTemErro = function(indice) {
        var errors = _.get($scope.errors, 'aneis[' + indice + ']');
        return _.isObject(errors) && Object.keys(errors).length > 0;
      };

      /**
       * Deve informar que determinado anel possui erros caso haja uma lista de
       * erros para determinado anel.
       *
       * @param      {int}  anelIndex  The indice of the anel
       * @param      {int}  index  The indice of the grupo semaforico
       * @return     {boolean}  { true se grupo semaforico tem erro }
       */
      $scope.grupoSemaforicoTemErro = function(anelIndex, index) {
        var errors = _.get($scope.errors, 'aneis[' + anelIndex + '].gruposSemaforicos['+index+']');
        return _.isObject(errors) && Object.keys(errors).length > 0;
      };

      $scope.atualizaGruposSemaforicos = function() {
        var ids = _.map($scope.currentAnel.gruposSemaforicos, 'idJson');
        $scope.currentGruposSemaforicos = _
          .chain($scope.objeto.gruposSemaforicos)
          .filter(function(gs) {
            return ids.indexOf(gs.idJson) >= 0;
          })
          .value();

          return $scope.currentGruposSemaforicos;
      };

      $scope.atualizaEstagios = function() {
        var ids = _.map($scope.currentAnel.estagios, 'idJson');
        $scope.currentEstagios = _
          .chain($scope.objeto.estagios)
          .filter(function(e) {
            return ids.indexOf(e.idJson) >= 0;
          })
          .orderBy(['posicao'])
          .value();

          return $scope.currentEstagios;
      };

      $scope.getImagemDeEstagio = function(estagio) {
        var imagem = _.find($scope.objeto.imagens, {idJson: estagio.imagem.idJson});
        return imagem && $filter('imageSource')(imagem.id,'thumb');
      };

    }]);
