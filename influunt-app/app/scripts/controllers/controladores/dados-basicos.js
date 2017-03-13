'use strict';

/**
 * @ngdoc function
 * @name influuntApp.controller:ControladoresDadosBasicosCtrl
 * @description
 * # ControladoresDadosBasicosCtrl
 * Controller of the influuntApp
 */
angular.module('influuntApp')
  .controller('ControladoresDadosBasicosCtrl', ['$scope', '$controller', '$filter', 'influuntBlockui', 'influuntAlert',
                                                'Restangular', 'toast', 'PermissionsService', 'PermissionStrategies',
                                                'breadcrumbs', 'ROOT_API_SMEE', 'CTELocalizacaoService',
    function ($scope, $controller, $filter, influuntBlockui, influuntAlert,
              Restangular, toast, PermissionsService, PermissionStrategies,
              breadcrumbs, ROOT_API_SMEE, CTELocalizacaoService) {
      $controller('ControladoresCtrl', {$scope: $scope});

      var deletarCroquiNoServidor, inicializaObjetoCroqui, setarAreaControlador, updateBreadcrumbs, confirmaEnderecoSMEE,
          alertaSmeeNaoEncontrado, setEnderecoControladorIndex;
      $scope.PermissionStrategies = PermissionStrategies;

      $scope.inicializaWizardDadosBasicos = function() {
        return $scope.inicializaWizard().then(function (){
          inicializaObjetoCroqui();
          setarAreaControlador();
          updateBreadcrumbs();
          setEnderecoControladorIndex();
        });
      };

      setEnderecoControladorIndex = function() {
        var enderecoIdJson = _.get($scope.objeto, 'endereco.idJson');
        if (enderecoIdJson) {
          $scope.enderecoControladorIndex = _.findIndex($scope.objeto.todosEnderecos, { idJson: enderecoIdJson });
        } else {
          $scope.enderecoControladorIndex = 0;
        }
      };

      $scope.consultaNumeroSMEE = function(field) {
        var numero = $scope.objeto[field];

        $scope.checkingSMEE = $scope.checkingSMEE || {};
        if (numero) {
          $scope.checkingSMEE[field] = true;
          return Restangular
            .oneUrl('api_smee', ROOT_API_SMEE)
            .one('local', numero)
            .get()
            .then(function(res) {
              if (res.IdLocal !== null && res.IdLocal !== undefined) {
                confirmaEnderecoSMEE(res.Descricao);
              } else {
                alertaSmeeNaoEncontrado();
              }
            })
            .catch(function(res) {
              // bad request.
              if (res.status === 400) {
                alertaSmeeNaoEncontrado();
              }
            })
            .finally(function() {
              $scope.checkingSMEE[field] = false;
            });
        }
      };

      alertaSmeeNaoEncontrado = function() {
        influuntAlert.alert(
          $filter('translate')('controladores.confirmacaoEnderecoTitulo'),
          $filter('translate')('controladores.validacaoNumeroSMEE')
        )
        .then(function() {
          $scope.objeto.numeroSMEE = null;
        });
      };

      confirmaEnderecoSMEE = function(descricao) {
        influuntAlert.confirm(
          $filter('translate')('controladores.confirmacaoEnderecoTitulo'),
          $filter('translate')('controladores.confirmacaoEndereco', {descricao: descricao})
        )
        .then(function(resposta) {
          if (!resposta) {
            $scope.objeto.numeroSMEE = null;
          }
        });
      };

      $scope.$watch('currentEndereco', function(currentVal, prevVal) {
        CTELocalizacaoService.atualizaLatLngPorEndereco(currentVal, prevVal);
      }, true);

      $scope.$watch('objeto.todosEnderecos', function(todosEnderecos) {
        if (!_.isArray(todosEnderecos)) { return false; }
        var endereco = $scope.objeto.todosEnderecos[$scope.enderecoControladorIndex];
        $scope.currentEndereco = endereco;

        $scope.objeto.nomeEndereco = $filter('nomeEndereco')(endereco);
      }, true);

      $scope.$watchGroup(['objeto.area.idJson', 'helpers.cidade.areas'], function (){
        $scope.currentSubareas = [];
        if ($scope.objeto && $scope.helpers && $scope.objeto.area && $scope.objeto.area.idJson){
          var area = _.find($scope.helpers.cidade.areas, {idJson: $scope.objeto.area.idJson});
          $scope.currentSubareas = area.subareas.map(function(s) {
            s.area = {idJson: area.idJson};
            return s;
          });

          if ($scope.objeto.subarea) {
            $scope.objeto.subarea = _.find($scope.currentSubareas, {idJson: $scope.objeto.subarea.idJson});
          }
        }
        return $scope.currentSubareas;
      }, true);

      $scope.$watch('objeto.modelo', function(modelo) {
        $scope.modeloControlador = modelo;
      });

      $scope.adicionarCroqui = function(upload, imagem) {
        var _imagem = { id: imagem.id, filename: imagem.filename, idJson: imagem.idJson };

        $scope.objeto.croqui = {id: _imagem.id, idJson: _imagem.idJson};
        $scope.objeto.imagens = $scope.objeto.imagens || [];
        $scope.objeto.imagens.push(_imagem);
      };

      $scope.removerCroquiLocal = function() {
        var imagemIndex = _.findIndex($scope.objeto.imagens, { id: $scope.objeto.croqui.id });
        delete $scope.objeto.croqui;
        $scope.objeto.imagens.splice(imagemIndex, 1);
      };

      $scope.removerCroqui = function(imagemIdJson) {
        return influuntAlert.delete().then(function(confirmado) {
          if (confirmado) {
            return deletarCroquiNoServidor(imagemIdJson)
              .then(function() {
                $scope.removerCroquiLocal(imagemIdJson);
                return true;
              })
              .catch(function() {
                toast.error($filter('translate')('geral.erro_deletar_croqui'));
                return false;
              })
              .finally(influuntBlockui.unblock);
          }
          return confirmado;
        });
      };

      deletarCroquiNoServidor = function(imagemIdJson) {
        var imagem = _.find($scope.objeto.imagens, { idJson: imagemIdJson });
        return Restangular.one('imagens', imagem.id).customDELETE('croqui');
      };

      inicializaObjetoCroqui = function() {
        var croqui = $scope.objeto.croqui;
        if (!!croqui) {
          $scope.imagemCroqui = {
            idJson: croqui.idJson,
            url: $filter('imageSource')(croqui.id),
            nomeImagem: croqui.filename
          };
        }
      };

      setarAreaControlador = function() {
        if (!PermissionsService.podeVisualizarTodasAreas() && !PermissionsService.isUsuarioRoot()) {
          $scope.objeto.area = PermissionsService.getUsuario().area;
        }
      };

      updateBreadcrumbs = function() {
        if ($scope.objeto.aneis) {
          var aneis = _
            .chain($scope.objeto.aneis)
            .filter('ativo')
            .orderBy('posicao')
            .value();
          if (aneis.length > 0) {
            var idJsonEndereco = _.get(aneis[0].endereco, 'idJson');
            var currentEndereco = _.find($scope.objeto.todosEnderecos, {idJson: idJsonEndereco });
            breadcrumbs.setNomeEndereco($filter('nomeEndereco')(currentEndereco));
          }
        }
      };

    }]);
