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

      var atualizaPosicaoEstagios;

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
            $scope.objeto.gruposSemaforicos = _.orderBy($scope.objeto.gruposSemaforicos, ['posicao']);
            $scope.aneis = _.filter($scope.objeto.aneis, {ativo: true});
            atualizaPosicaoEstagios();
            $scope.objeto.estagios = _.orderBy($scope.objeto.estagios, ['posicao']);
            $scope.selecionaAnelAssociacao(0);

              _.each($scope.objeto.gruposSemaforicos, function(grupo) {
                grupo.label = 'G' + (grupo.posicao);
                grupo.ativo = false;

                // Cria o objeto helper para marcar os grupos ativos em cada estagio da tela.
                grupo.estagiosRelacionados = {};
                grupo.estagiosAtivados = {};
                grupo.estagiosGruposSemaforicos.forEach(function(eg) {
                  var estagioGrupo = _.find($scope.objeto.estagiosGruposSemaforicos, {idJson: eg.idJson});
                  var estagio = _.find($scope.objeto.estagios, {idJson: estagioGrupo.estagio.idJson});
                  grupo.estagiosRelacionados[estagio.id] = true;
                });
              });

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
        var estagioGrupoSemaforico = _.find(grupo.estagiosGruposSemaforicos, {estagio: {id: estagioId}});

        if (!!estagioGrupoSemaforico) {
          estagioGrupoSemaforico.ativo = !estagioGrupoSemaforico.ativo;
          grupo.estagiosAtivados[estagioId] = estagioGrupoSemaforico.ativo;
          $scope.$apply();
        }
      };

      $scope.associaEstagiosGrupoSemaforico = function(grupo, estagio) {
        var busca = {
          grupoSemaforico: {idJson: grupo.idJson},
          estagio: {idJson: estagio.idJson}
        };
        var index = _.findIndex($scope.objeto.estagiosGruposSemaforicos, busca);
        if (index >= 0) {
          var estagioGrupoSemaforico = $scope.objeto.estagiosGruposSemaforicos[index];
          $scope.objeto.estagiosGruposSemaforicos.splice(index, 1);
          index = _.findIndex(grupo.estagiosGruposSemaforicos, {idJson: estagioGrupoSemaforico.idJson});
          grupo.estagiosGruposSemaforicos.splice(index, 1);

          index = _.findIndex(estagio.estagiosGruposSemaforicos, {idJson: estagioGrupoSemaforico.idJson});
          estagio.estagiosGruposSemaforicos.splice(index, 1);
        } else {
          var obj = {
            idJson: UUID.generate(),
            grupoSemaforico: { idJson: grupo.idJson },
            estagio: { idJson: estagio.idJson },
          };

          $scope.objeto.estagiosGruposSemaforicos.push(obj);
          grupo.estagiosGruposSemaforicos.push({idJson: obj.idJson});
          estagio.estagiosGruposSemaforicos.push({idJson: obj.idJson});
        }

        $scope.toggleEstagioAtivado(grupo, estagio);
        // @todo verificar se "atualizaGruposSemaforicosSelecionados" tem funcao efetiva.
        // return $scope.currentEstagio && $scope.atualizaGruposSemaforicosSelecionados();
      };

      $scope.estagioTemErro = function(indiceAnel, indiceEstagio) {
        var errors = _.get($scope.errors, 'aneis[' + indiceAnel + '].estagios[' + indiceEstagio + ']');
        return _.isObject(errors) && Object.keys(errors).length > 0;
      };

      $scope.selecionaAnelAssociacao = function(index) {
        $scope.selecionaAnel(index);
        $scope.atualizaGruposSemaforicos();
        $scope.atualizaEstagios();
      };

      atualizaPosicaoEstagios = function() {
        var posicao = {};
        _.chain($scope.aneis)
          .map('estagios')
          .flatten()
          .map('idJson')
          .each(function(idJson) {
            var obj = _.find($scope.objeto.estagios, {idJson: idJson});
            posicao[obj.anel.idJson] = posicao[obj.anel.idJson] || 0;
            var p = ++posicao[obj.anel.idJson];
            obj.posicao = obj.posicao || p;
          })
          .value();
      };

      $scope.sortableOptions = {
        handle: ".title-stages",
        stop: function() {
          $scope.$apply(function() {
            $scope.currentEstagios.forEach(function(estagio, index) {
              estagio.posicao = index + 1;
            });

            $scope.objeto.estagios = _.orderBy($scope.objeto.estagios, ['posicao']);
          });
        }
      };

    }]);
