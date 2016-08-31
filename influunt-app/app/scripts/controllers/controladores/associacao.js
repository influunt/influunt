'use strict';

/**
 * @ngdoc function
 * @name influuntApp.controller:ControladoresAssociacaoCtrl
 * @description
 * # ControladoresAssociacaoCtrl
 * Controller of the influuntApp
 */
angular.module('influuntApp')
  .controller('ControladoresAssociacaoCtrl', ['$scope', '$state', '$controller', 'assertControlador', 'utilEstagios',
    function ($scope, $state, $controller, assertControlador, utilEstagios) {
      $controller('ControladoresCtrl', {$scope: $scope});

      var atualizaPosicaoEstagios, onSortableStop;

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
            $scope.selecionaAnelAssociacao(0);

            _.each($scope.objeto.gruposSemaforicos, function(grupo) {
              grupo.label = 'G' + (grupo.posicao);
              grupo.ativo = false;

              // Cria o objeto helper para marcar os grupos ativos em cada estagio da tela.
              grupo.estagiosRelacionados = {};
              grupo.estagiosAtivados = {};
              return _.isArray(grupo.estagiosGruposSemaforicos) &&
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

      $scope.associaEstagiosGrupoSemaforico = function(grupo, estagio) {
        var busca = {
          grupoSemaforico: {idJson: grupo.idJson},
          estagio: {idJson: estagio.idJson}
        };

        $scope.objeto.estagiosGruposSemaforicos = $scope.objeto.estagiosGruposSemaforicos || [];
        grupo.estagiosGruposSemaforicos = grupo.estagiosGruposSemaforicos || [];
        estagio.estagiosGruposSemaforicos = estagio.estagiosGruposSemaforicos || [];

        // var index = _.findIndex($scope.objeto.estagiosGruposSemaforicos, busca);
        var estagioGrupoSemaforico = _.find($scope.objeto.estagiosGruposSemaforicos, busca);
        if (typeof estagioGrupoSemaforico === 'undefined') {
          var obj = {
            idJson: UUID.generate(),
            grupoSemaforico: { idJson: grupo.idJson },
            estagio: { idJson: estagio.idJson },
          };

          $scope.objeto.estagiosGruposSemaforicos.push(obj);
          grupo.estagiosGruposSemaforicos.push({idJson: obj.idJson});
          estagio.estagiosGruposSemaforicos.push({idJson: obj.idJson});

        } else if (estagioGrupoSemaforico._destroy) {
          delete estagioGrupoSemaforico._destroy;
        } else if (estagioGrupoSemaforico.id) {
          estagioGrupoSemaforico._destroy = true;
        } else {
          var index = _.findIndex($scope.objeto.estagiosGruposSemaforicos, { idJson: estagioGrupoSemaforico.idJson });
          $scope.objeto.estagiosGruposSemaforicos.splice(index, 1);
          index = _.findIndex(grupo.estagiosGruposSemaforicos, {idJson: estagioGrupoSemaforico.idJson});
          grupo.estagiosGruposSemaforicos.splice(index, 1);

          index = _.findIndex(estagio.estagiosGruposSemaforicos, {idJson: estagioGrupoSemaforico.idJson});
          estagio.estagiosGruposSemaforicos.splice(index, 1);
        }
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
          .map(function(estagio) { return _.find($scope.objeto.estagios, { idJson: estagio.idJson }); })
          .orderBy(['posicao'])
          .each(function(estagio) {
            posicao[estagio.anel.idJson] = posicao[estagio.anel.idJson] || 0;
            var p = ++posicao[estagio.anel.idJson];
            estagio.posicao = estagio.posicao || p;
          })
          .value();
      };

      $scope.leftEstagio = function(posicaoAtual) {
        var estagioAtual = $scope.currentEstagios[posicaoAtual];
        var estagioAnterior = utilEstagios.getEstagioAnterior($scope.currentEstagios, posicaoAtual);
        var indexAnterior = _.findIndex($scope.currentEstagios, estagioAnterior);

        $scope.currentEstagios.splice(indexAnterior, 1);
        var indexAtual = _.findIndex($scope.currentEstagios, estagioAtual);
        $scope.currentEstagios.splice(indexAtual+1, 0, estagioAnterior);

        atualizaPosicaoEstagios();
        onSortableStop();
      };

      $scope.rightEstagio = function(posicaoAtual) {
        var estagioAtual = $scope.currentEstagios[posicaoAtual];
        var proximoEstagio = utilEstagios.getProximoEstagio($scope.currentEstagios, posicaoAtual);

        $scope.currentEstagios.splice(posicaoAtual, 1);
        var posicaoProximoEstagio = _.findIndex($scope.currentEstagios, proximoEstagio);
        $scope.currentEstagios.splice(posicaoProximoEstagio + 1, 0, estagioAtual);

        atualizaPosicaoEstagios();
        onSortableStop();
      };

      onSortableStop = function() {
        $scope.currentEstagios.forEach(function(estagio, index) {
          estagio.posicao = index + 1;
        });

        $scope.objeto.estagios = _.orderBy($scope.objeto.estagios, ['posicao']);
        $scope.currentAnel.estagios = _.map($scope.currentEstagios, function(estagio) {
          return {idJson: estagio.idJson};
        });
      };

      $scope.sortableOptions = {
        handle: '.title-stages',
        stop: function() {
          $scope.$apply(onSortableStop);
        }
      };
    }]);
