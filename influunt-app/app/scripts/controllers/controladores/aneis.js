'use strict';

/**
 * @ngdoc function
 * @name influuntApp.controller:ControladoresAneisCtrl
 * @description
 * # ControladoresAneisCtrl
 * Controller of the influuntApp
 */
angular.module('influuntApp')
  .controller('ControladoresAneisCtrl', ['$scope', '$state', '$controller', '$q', '$filter', 'assertControlador', 'influuntAlert', 'Restangular', 'toast',
    function ($scope, $state, $controller, $q, $filter, assertControlador, influuntAlert, Restangular, toast) {
      $controller('ControladoresCtrl', {$scope: $scope});

      // Métodos privados.
      var ativaPrimeiroAnel, inicializaEnderecos, atualizarAneisAtivos, registrarWatcherCurrentAnel, setDadosBasicos,
      setandoEnderecoByAnel, watcherEndereco, setarImagensEstagios, inicializaObjetoCroqui, deletarCroquiNoServidor;

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
            registrarWatcherCurrentAnel();
            setDadosBasicos();
            $scope.selecionaAnelLocal($scope.currentAnelIndex);
          }
        });
      };

      $scope.selecionaAnelLocal = function(index) {
        $scope.selecionaAnel(index);
        inicializaObjetoCroqui();
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

      $scope.adicionarCroqui = function(upload, imagem) {
        var _imagem = { id: imagem.id, filename: imagem.filename, idJson: imagem.idJson };

        $scope.currentAnel.croqui = {id: _imagem.id, idJson: _imagem.idJson};
        $scope.objeto.imagens = $scope.objeto.imagens || [];
        $scope.objeto.imagens.push(_imagem);
      };

      $scope.removerCroquiLocal = function(img) {
        var imagemIndex = _.findIndex($scope.objeto.imagens, { id: img.id });
        $scope.imagemCroqui = null;
        delete $scope.currentAnel.croqui;
        $scope.objeto.imagens.splice(imagemIndex, 1);
      };

      $scope.removerCroqui = function(imagemIdJson) {
        return influuntAlert.delete().then(function(confirmado) {
          if (confirmado) {
            return deletarCroquiNoServidor(imagemIdJson).then(function() {
              $scope.removerCroquiLocal(imagemIdJson);
              return true;
            }).catch(function() {
              toast.error($filter('translate')('geral.erro_deletar_croqui'));
              return false;
            });
          }
          return confirmado;
        });
      };

      deletarCroquiNoServidor = function(imagemIdJson) {
        var imagem = _.find($scope.objeto.imagens, { idJson: imagemIdJson });
        return Restangular.one('imagens', imagem.id).customDELETE('croqui');
      };

      $scope.deletarEstagio = function(estagioIdJson) {
        var title = $filter('translate')('controladores.estagios.titulo_msg_apagar_estagio'),
            text = $filter('translate')('controladores.estagios.corpo_msg_apagar_estagio');
        return influuntAlert.confirm(title, text).then(function(deveApagarEstagio) {
          if (deveApagarEstagio) {
            var estagio = _.find($scope.objeto.estagios, { idJson: estagioIdJson });
            return Restangular.one('estagios', estagio.id).remove()
              .then(function() {
                $scope.inicializaWizard().then(function() {
                  $scope.objeto.aneis = _.orderBy($scope.objeto.aneis, ['posicao']);
                  $scope.aneis = $scope.objeto.aneis;
                  $scope.currentAnel = $scope.aneis[$scope.currentAnelIndex];
                  setarImagensEstagios($scope.currentAnel);
                });
                return true;
              }).catch(function() {
                toast.error($filter('translate')('controladores.estagios.msg_erro_apagar_estagio'));
                return false;
              });
          }
          return deveApagarEstagio;
        });
      };

      $scope.ativarProximoAnel = function() {
        $scope.selecionaAnelLocal(_.findIndex($scope.aneis, { ativo: false }));
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
              $scope.aneis[ultimoAnelAtivoIndex]._destroy = true;
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
          if (!anel.endereco) {
            setandoEnderecoByAnel(anel);
          }
        });
      };

      registrarWatcherCurrentAnel = function() {
        $scope.$watch('currentAnel', function(anel) {
          watcherEndereco(anel);
          setarImagensEstagios(anel);
        });
      };

      watcherEndereco = function(anel) {
        $scope.currentEndereco = _.find($scope.objeto.todosEnderecos, {idJson: $scope.currentAnel.endereco.idJson});
        if ($scope.currentEndereco && $scope.currentEndereco.localizacao && ($scope.currentEndereco.localizacao2 || $scope.currentEndereco.alturaNumerica)) {
          anel.localizacao = $filter('nomeEndereco')($scope.currentEndereco);
          anel.latitude = $scope.currentEndereco.latitude;
          anel.longitude = $scope.currentEndereco.longitude;
        } else {
          anel.localizacao = '';
        }
      };

      setarImagensEstagios = function(anel) {
        var estagios = anel.estagios;
        if (!_.isArray(estagios)) {
          return false;
        }

        $scope.imagensDeEstagios = _
          .chain(estagios)
          .map(function(e) {
            var estagio = _.find($scope.objeto.estagios, {idJson: e.idJson});
            var imagem = _.find($scope.objeto.imagens, {idJson: estagio.imagem.idJson});
            var obj = {
              idJson: estagio.idJson,
              url: $filter('imageSource')(imagem.id),
              nomeImagem: imagem.filename
            };

            return obj;
          })
          .value();
      };

      setandoEnderecoByAnel = function (anel) {
        var endereco;
        if(anel.posicao === 1){
          endereco = _.cloneDeep($scope.objeto.todosEnderecos[0]);
          delete endereco.id;
          endereco.idJson = UUID.generate();
        }else{
          endereco = { idJson: UUID.generate() };
        }

        anel.endereco = endereco;
        $scope.objeto.todosEnderecos = $scope.objeto.todosEnderecos || [];
        $scope.objeto.todosEnderecos.push(endereco);
      };

      inicializaObjetoCroqui = function() {
        var croqui = $scope.currentAnel.croqui;
        if (!!croqui) {
          $scope.imagemCroqui = {
            idJson: $scope.currentAnel.croqui.idJson,
            url: $filter('imageSource')(croqui.id),
            nomeImagem: croqui.filename
          };
        }
      };
  }]);
