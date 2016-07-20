'use strict';

/**
 * @ngdoc function
 * @name influuntApp.controller:ControladoresCtrl
 * @description
 * # ControladoresCtrl
 * Controller of the influuntApp
 */
angular.module('influuntApp')
  .controller('ControladoresCtrl', ['$controller', '$scope', '$state','Restangular', '$q', 'handleValidations', 'APP_ROOT',
    function ($controller, $scope, $state, Restangular, $q, handleValidations, APP_ROOT) {

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
        Restangular.one('helpers', 'controlador').get().then(function(res) {
          $scope.data = res;
          $scope.helpers = {};

          if ($scope.objeto.area) {
            $scope.helpers.cidade = $scope.objeto.area.cidade;
          } else {
            $scope.helpers.cidade = $scope.data.cidades[0];
          }

          if ($scope.objeto.modelo) {
            $scope.helpers.fornecedor = $scope.objeto.modelo.fabricante;
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
        var defer = $q.defer();

        var id = $state.params.id;
        if (id) {
          Restangular.one('controladores', id).get().then(function(res) {
            loadWizardData(res);
            defer.resolve(res);
          });
        } else {
          loadWizardData({});
          defer.resolve({});
        }

        return defer.promise;
      };

      $scope.submitForm = function(form, stepResource, nextStep) {
        $.blockUI({message: '<h1>Aguarde...</h1>'});
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
              $.unblockUI();
            })
            .catch(function(res) {
              $.unblockUI();
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

        if (angular.isDefined($scope.currentEstagioId)) {
          $scope.selecionaEstagio($scope.currentEstagioId);
        }
      };

      /**
       * Seleciona um estágio do anel atual atraves do indice.
       *
       * @param      {<type>}  index   The index
       */
      $scope.selecionaEstagio = function(index) {
        $scope.currentEstagioId = index;
        $scope.currentEstagio = $scope.currentAnel.estagios[index];
        $scope.atualizaGruposSemaforicosSelecionados();
      };

      $scope.atualizaGruposSemaforicosSelecionados = function() {
        var estagioId = $scope.currentEstagio.id;
        $scope.gruposSelecionados = $scope.currentAnel.gruposSemaforicos.filter(function(grupo) {
          return !!_.filter(grupo.estagioGrupoSemaforicos, {estagio: {id: estagioId}}).length;
        });
      };

      /**
       * Seleciona um grupo semafórico do anel atual atraves do índice.
       *
       * @param      {int}  index   The index
       */
      $scope.selecionaGrupoSemaforico = function(index) {
        $scope.currentGrupoSemaforicoIndex = index;
        $scope.currentGrupoSemaforico = $scope.currentAnel.gruposSemaforicos[index];
        $scope.currentGrupoSemaforicoIdentifier = $scope.currentAnelIndex.toString() + index.toString();

        if (angular.isDefined($scope.isTabelaEntreVerdes) && $scope.isTabelaEntreVerdes) {
          $scope.selecionaTabelaEntreVerdes(0);
        }
      };

      /**
       * Seleciona uma tabela entre-verdes do grupo semaforico atual atraves do índice.
       *
       * @param      {int}  index   The index
       */
      $scope.selecionaTabelaEntreVerdes = function(index) {
        $scope.currentTabelaEntreVerdesIndex = index;
        $scope.currentTabelaEntreVerdes = $scope.currentGrupoSemaforico.tabelasEntreVerdes[index];
        $scope.tabelasEntreVerdesTransicoes = _.chain($scope.currentGrupoSemaforico.transicoes)
                                     .map(function(transicao) { return transicao.tabelaEntreVerdesTransicoes; })
                                     .flatten()
                                     .filter(function(tevt) { return tevt.tabelaEntreVerdes.posicao === index + 1; })
                                     .value();
        $scope.tabelasEntreVerdesTransicoes.forEach(function(tevTransicao) {
          tevTransicao.tabelaEntreVerdes = $scope.currentTabelaEntreVerdes;
        });
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
        $scope.errors.aneis = _.compact($scope.errors.aneis);
        console.log('$scope.errors: ', $scope.errors);
        $scope.getErrosVerdes();
      };

      $scope.getErrosVerdes = function() {
        $scope.messages = [];
        _.each($scope.errors.aneis, function(anel, anelIndex) {
          _.each(anel.gruposSemaforicos, function(gs, gsIndex) {
            var nomeGS = 'G' + $scope.objeto.aneis[anelIndex].gruposSemaforicos[gsIndex].posicao;
            _.each(gs, function(mgs) {
              _.map(mgs, function(msg) {
                $scope.messages.push(nomeGS + ': ' + msg);
              });
            });
          });
        });

        $scope.messages = _.uniq($scope.messages);
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

    }]);
