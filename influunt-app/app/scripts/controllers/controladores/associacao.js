'use strict';

/**
 * @ngdoc function
 * @name influuntApp.controller:ControladoresAssociacaoCtrl
 * @description
 * # ControladoresAssociacaoCtrl
 * Controller of the influuntApp
 */
angular.module('influuntApp')
  .controller('ControladoresAssociacaoCtrl', ['$scope', '$state', '$controller', 'assertControlador',
    function ($scope, $state, $controller, assertControlador) {
      $controller('ControladoresCtrl', {$scope: $scope});

      /**
       * Pré-condições para acesso à tela de associações: Somente será possível acessar esta
       * tela se o objeto possuir estágios. Os estágios são informados no passo anterior, o
       * passo de aneis.
       *
       * @return     {boolean}  { description_of_the_return_value }
       */
      $scope.assertAssociacoes = function() {
        var valid = assertControlador.hasAneis($scope.objeto) && assertControlador.hasEstagios($scope.objeto);
        if (!valid) {
          $state.go('app.wizard_controladores.configuracao_grupo', {id: $scope.objeto.id});
        }

        return valid;
      };

      $scope.inicializaAssociacao = function() {
        return $scope.inicializaWizard().then(function() {
          if ($scope.assertAssociacoes()) {
            $scope.objeto.aneis = _.orderBy($scope.objeto.aneis, ['posicao'], ['asc']);
            $scope.aneis = _.filter($scope.objeto.aneis, {ativo: true});
            _.each($scope.aneis, function(anel) {
              anel.gruposSemaforicos = _.orderBy(anel.gruposSemaforicos, ['posicao'], ['asc']);
              _.each(anel.gruposSemaforicos, function(grupo) {
                grupo.label = 'G' + (grupo.posicao);
                grupo.ativo = false;

                // Cria o objeto helper para marcar os grupos ativos em cada estagio da tela.
                grupo.estagiosRelacionados = {};
                grupo.estagiosAtivados = {};
                grupo.estagioGrupoSemaforicos.forEach(function(estagioGrupo) {
                  grupo.estagiosRelacionados[estagioGrupo.estagio.id] = true;
                  grupo.estagiosAtivados[estagioGrupo.estagio.id] = estagioGrupo.ativo;
                });
              });

              // Inicializa o tempoMaximoPermanenciaAtivo true e posicao.
              _.each(anel.estagios, function(estagio, index) {
                estagio.posicao = estagio.posicao || (index + 1);
                estagio.tempoMaximoPermanenciaAtivo = !!estagio.tempoMaximoPermanencia;
              });

            });

            $scope.aneis = _.orderBy($scope.aneis, ['posicao'], ['asc']);
            $scope.selecionaAnel(0);
            $scope.selecionaEstagio(0);
          }
        });
      };

      /**
       * Limpa o tempo máximo de permanência do estágio caso o usuário uncheck o
       * checkbox de tempo máximo de permanência.
       *
       * @param      {<type>}  estagio  The estagio
       */
      $scope.limpaTempoPermanencia = function(estagio) {
        estagio.tempoMaximoPermanencia = null;
      };

      $scope.toggleEstagioAtivado = function(grupo, estagio) {
        var estagioId = estagio.id;
        var estagioGrupoSemaforico = _.find(grupo.estagioGrupoSemaforicos, {estagio: {id: estagioId}});

        if (!!estagioGrupoSemaforico) {
          estagioGrupoSemaforico.ativo = !estagioGrupoSemaforico.ativo;
          grupo.estagiosAtivados[estagioId] = estagioGrupoSemaforico.ativo;
          $scope.$apply();
        }
      };

      $scope.associaEstagiosGrupoSemaforico = function(grupo, estagio) {
        var obj = {
          grupoSemaforico: { id: grupo.id },
          estagio: { id: estagio.id },
        };

        var filter = {grupoSemaforico: {id: obj.grupoSemaforico.id}, estagio: {id: obj.estagio.id}};
        var index = _.findIndex(grupo.estagioGrupoSemaforicos, filter);
        if (index >= 0) {
          grupo.estagioGrupoSemaforicos.splice(index, 1);
          estagio.estagiosGruposSemaforicos.splice(index, 1);
        } else {
          grupo.estagioGrupoSemaforicos.push(obj);
          estagio.estagiosGruposSemaforicos.push(obj);
        }

        $scope.toggleEstagioAtivado(grupo, estagio);
        return $scope.currentEstagio && $scope.atualizaGruposSemaforicosSelecionados();
      };

      $scope.estagioTemErro = function(indiceAnel, indiceEstagio) {
        var errors = _.get($scope.errors, 'aneis[' + indiceAnel + '].estagios[' + indiceEstagio + ']');
        return _.isObject(errors) && Object.keys(errors).length > 0;
      };

      $scope.sortableOptions = {
        stop: function() {
          $scope.currentAnel.estagios.forEach(function(estagio, index) {
            estagio.posicao = index + 1;
          });

          $scope.$apply();
        }
      };

    }]);
