'use strict';

/**
 * @ngdoc function
 * @name influuntApp.controller:ControladoresAneisCtrl
 * @description
 * # ControladoresAneisCtrl
 * Controller of the influuntApp
 */
angular.module('influuntApp')
  .controller('ControladoresAneisCtrl', ['$scope', '$state', '$controller', '$q', '$filter', 'assertControlador', 'influuntAlert',
    function ($scope, $state, $controller, $q, $filter, assertControlador, influuntAlert) {
      $controller('ControladoresCtrl', {$scope: $scope});

      // Métodos privados.
      var ativaPrimeiroAnel, inicializaEnderecos, atualizarAneisAtivos,
          registrarWatcherEndereco, atualizaCurrentEnderecos,
          setDadosBasicos, setandoEnderecoByAnel;

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
            $scope.objeto.aneis = _.orderBy($scope.objeto.aneis, ['posicao']);
            $scope.aneis = $scope.objeto.aneis;
            $scope.currentAnelIndex = 0;
            $scope.currentAnel = $scope.aneis[0];

            ativaPrimeiroAnel($scope.objeto);
            atualizarAneisAtivos();
            inicializaEnderecos();
            registrarWatcherEndereco();
            setDadosBasicos();
            $scope.$broadcast('influuntWizard.dropzoneOk');
          }
        });
      };

      /**
       * Endereços são sempre validados, logo precisam de ser removidos dos anéis não ativos.
       */
      $scope.beforeSubmitForm = function() {
        $scope.aneis.forEach(function(anel) {
          if (!anel.ativo) {
            delete anel.enderecos;
          }
        });
      };

      $scope.adicionarEstagio = function(upload, imagem) {
        var anel = $scope.currentAnel;
        anel.estagios = anel.estagios || [];
        $scope.objeto.estagios = $scope.objeto.estagios || [];
        $scope.objeto.imagens = $scope.objeto.imagens || [];

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

      $scope.removerEstagio = function(img) {
        var anel = $scope.currentAnel;

        var imagemIndex = _.findIndex($scope.objeto.imagens, { id: img.id });
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

      $scope.adicionarCroqui = function(imagem) {
        $scope.currentAnel.croqui = imagem;
      };

      $scope.ativarProximoAnel = function() {
        $scope.selecionaAnel(_.findIndex($scope.aneis, { ativo: false }));
        $scope.currentAnel.ativo = true;
        inicializaEnderecos();
        atualizarAneisAtivos();
      };

      $scope.desativarUltimoAnel = function() {
        var ultimoAnelAtivoIndex = _.findLastIndex($scope.aneis, { ativo: true });
        if (ultimoAnelAtivoIndex === 0) {
          return false;
        }

        $scope.aneis[ultimoAnelAtivoIndex].ativo = false;
        delete $scope.aneis[ultimoAnelAtivoIndex].enderecos;
        atualizarAneisAtivos();
      };

      $scope.deletarUltimoAnelAtivo = function(data) {
        var tabWasAdded = !!$(data.tab).data('newtab');
        if (tabWasAdded) {
          return $q.resolve(true);
        } else {
          var title = $filter('translate')('controladores.aneis.titulo_msg_apagar_anel'),
              text = $filter('translate')('controladores.aneis.corpo_msg_apagar_anel');
          return influuntAlert.confirm(title, text).then(function(deveApagarAnel) {
            if (deveApagarAnel) {
              var ultimoAnelAtivoIndex = _.findLastIndex($scope.aneis, { ativo: true });
              $scope.aneis[ultimoAnelAtivoIndex].ativo = false;
              $scope.aneis[ultimoAnelAtivoIndex]['_destroy'] = true;
              $scope.submitForm({$valid: true}, 'aneis', 'app.wizard_controladores.aneis');
            }
            return deveApagarAnel;
          });
        }
      };

      setDadosBasicos = function() {
        $scope.dadosBasicos = {
          numeroSMEE: $scope.objeto.numeroSMEE || '-',
        };
      };

      /**
       * Ao iniciar um novo controlador, este método garante que ao menos o primeiro
       * anel esteja ativo.
       *
       * Este método inicializa também a validação local dos aneis.
       *
       * @param      {<type>}  controlador  The controlador
       * @return     {<type>}  { description_of_the_return_value }
       */
      ativaPrimeiroAnel = function(controlador) {
        controlador.aneis.forEach(function(anel) { anel.valid = {form: true}; });
        controlador.aneis[0].ativo = true;
        return controlador.aneis;
      };

      atualizarAneisAtivos = function() {
        $scope.aneisAtivos = _.filter($scope.aneis, { ativo: true });
      };

      inicializaEnderecos = function() {
        _.each($scope.aneis, function(anel) {
          if (!angular.isDefined(anel.enderecos) || anel.enderecos.length === 0) {
            setandoEnderecoByAnel(anel);
          }
        });
      };

      registrarWatcherEndereco = function() {
        $scope.$watch('currentAnel', function(anel) {
          atualizaCurrentEnderecos();
          if (_.isArray($scope.currentEnderecos) && $scope.currentEnderecos.length >= 2 && $scope.currentEnderecos[0].localizacao && $scope.currentEnderecos[1].localizacao) {
            anel.localizacao = $scope.currentEnderecos[0].localizacao + ' com ' + $scope.currentEnderecos[1].localizacao;
            anel.latitude = $scope.currentEnderecos[0].latitude;
            anel.longitude = $scope.currentEnderecos[0].longitude;
          } else {
            anel.localizacao = '';
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

      setandoEnderecoByAnel = function (anel) {
        var enderecoDadosBasicos = [$scope.objeto.enderecos[0], $scope.objeto.enderecos[1]];
        var enderecoGenerateIdJson = [{ idJson: UUID.generate() }, { idJson: UUID.generate() }];
        var enderecos = anel.posicao === 1 ? enderecoDadosBasicos : enderecoGenerateIdJson;
        anel.enderecos = enderecos;
        $scope.objeto.todosEnderecos = $scope.objeto.todosEnderecos || [];
        if (anel.posicao === 1 ) {
          $scope.objeto.todosEnderecos.push(enderecos);
        } else {
          $scope.objeto.todosEnderecos = _.concat($scope.objeto.todosEnderecos, enderecos);
        }
      };
  }]);
