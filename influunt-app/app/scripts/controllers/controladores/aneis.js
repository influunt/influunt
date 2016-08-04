'use strict';

/**
 * @ngdoc function
 * @name influuntApp.controller:ControladoresAneisCtrl
 * @description
 * # ControladoresAneisCtrl
 * Controller of the influuntApp
 */
angular.module('influuntApp')
  .controller('ControladoresAneisCtrl', ['$scope', '$state', '$controller', 'assertControlador',
    function ($scope, $state, $controller, assertControlador) {
      $controller('ControladoresCtrl', {$scope: $scope});

      // Métodos privados.
      var criaAneis, inicializaEnderecos, atualizarAneisAtivos, registrarWatcherEndereco, atualizaCurrentEnderecos;

      /**
       * Pré-condições para acesso à tela de aneis: Somente será possível acessar esta
       * tela se o objeto já possuir aneis (os aneis são gerados pela API assim que o
       * usuário informar os dados básicos).
       *
       * @return     {boolean}  { description_of_the_return_value }
       */
      $scope.assertAneis = function () {
        var valid = assertControlador.hasAneis($scope.objeto);
        if (!valid) {
          $state.go('app.wizard_controladores.dados_basicos');
        }

        return valid;
      };


      $scope.inicializaAneis = function() {
        return $scope.inicializaWizard().then(function() {
          if ($scope.assertAneis()) {
            $scope.currentAnelIndex = 0;
            criaAneis($scope.objeto);
            $scope.aneis = _.orderBy($scope.objeto.aneis, ['posicao'], ['asc']);
            $scope.currentAnel = $scope.objeto.aneis[$scope.currentAnelIndex];
            atualizarAneisAtivos();
            inicializaEnderecos();
            registrarWatcherEndereco();
            $scope.$broadcast('influuntWizard.dropzoneOk');
          }
        });
      };

      // Endereços são sempre validados, logo precisam de ser
      // removidos dos anéis não ativos.
      $scope.beforeSubmitForm = function() {
        $scope.aneis.forEach(function(anel) {
          if (!anel.ativo) {
            delete anel.enderecos;
          }
        });
      };

      /**
       * Desativa todos os aneis após o anel corrente, caso o anel atual seja
       * desativado.
       *
       * @param      {<type>}  currentAnel  The current anel
       */
      $scope.desativaProximosAneis = function(currentAnel) {
        $scope.aneis.forEach(function(anel) {
          if (anel.posicao > currentAnel.posicao) {
            anel.ativo = false;
          }
        });
      };

      criaAneis = function(controlador) {
        controlador.aneis = _.orderBy(controlador.aneis, ['posicao'], ['asc']).map(function(anel, key) {
          anel.idAnel = controlador.CLC + '-' + (key + 1);
          anel.posicao = anel.posicao || (key + 1);
          anel.valid = {
            form: true
          };

          return anel;
        });

        // Garantia de que o primeiro anel será sempre null.
        controlador.aneis[0].ativo = true;
        return controlador.aneis;
      };

      $scope.associaImagemAoEstagio = function(upload, imagem) {
        var anel = $scope.currentAnel;
        anel.estagios = anel.estagios || [];
        $scope.objeto.estagios = $scope.objeto.estagios || [];

        var uuid = UUID.generate();
        var _imagem = { id: imagem.id, filename: imagem.filename, idJson: imagem.idJson };
        var estagio = {
          imagem: { idJson: imagem.idJson },
          idJson: uuid
        };

        anel.estagios.push({idJson: uuid});
        $scope.objeto.estagios.push(estagio);
        $scope.objeto.imagens.push(_imagem);
      };

      $scope.removeImagemDoEstagio = function(img) {
        var anel = $scope.currentAnel;

        var imagemIndex = _.findIndex($scope.objeto.imagens, {id: img.id});
        var imagem = $scope.objeto.imagens[imagemIndex];
        var estagioIndex = _.findIndex($scope.objeto.estagios, function(estagio) {
          return estagio.imagem.idJson === imagem.idJson;
        });
        var estagio = $scope.objeto.estagios[estagioIndex];
        var estagioAnelIndex = _.findIndex(anel.estagios, {idJson: estagio.idJson});

        $scope.objeto.imagens.splice(imagemIndex, 1);
        $scope.objeto.estagios.splice(estagioIndex, 1);
        anel.estagios.splice(estagioAnelIndex, 1);
      };

      $scope.associaImagemAoCurrentAnel = function(imagem) {
        $scope.currentAnel.croqui = imagem;
      };

      atualizarAneisAtivos = function() {
        $scope.aneisAtivos = _.filter($scope.aneis, { ativo: true });
      };

      $scope.ativarProximoAnel = function() {
        $scope.$apply(function() {
          $scope.selecionaAnel(_.findIndex($scope.aneis, { ativo: false }));
          $scope.currentAnel.ativo = true;
          inicializaEnderecos();
          atualizarAneisAtivos();
        });
      };

      $scope.desativarUltimoAnel = function() {
        $scope.$apply(function() {
          var ultimoAnelAtivoIndex = _.findLastIndex($scope.aneis, { ativo: true });
          $scope.aneis[ultimoAnelAtivoIndex].ativo = false;
          atualizarAneisAtivos();
        });
      };

      inicializaEnderecos = function() {
        _.each($scope.aneis, function(anel) {
          if (!angular.isDefined(anel.enderecos) || anel.enderecos.length === 0) {
            var enderecos = [{ idJson: UUID.generate() }, { idJson: UUID.generate() }];
            anel.enderecos = enderecos;
            $scope.objeto.todosEnderecos = _.concat($scope.objeto.todosEnderecos, enderecos);
          }
        });
      };

      registrarWatcherEndereco = function() {
        $scope.$watch('currentAnel', function(anel) {
          atualizaCurrentEnderecos();
          if (angular.isArray($scope.currentEnderecos) && $scope.currentEnderecos[0].localizacao && $scope.currentEnderecos[1].localizacao) {
            anel.localizacao = $scope.currentEnderecos[0].localizacao + ' com ' + $scope.currentEnderecos[1].localizacao;
          }

        }, true);
      };

      atualizaCurrentEnderecos = function() {
        var ids = _.map($scope.currentAnel.enderecos, 'idJson');
        $scope.currentEnderecos = _
          .chain($scope.objeto.todosEnderecos)
          .filter(function(e) { return ids.indexOf(e.idJson) >= 0; })
          .value();

        return $scope.currentEnderecos;
      };

    }]);
