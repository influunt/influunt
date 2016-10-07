'use strict';

/**
 * @ngdoc function
 * @name influuntApp.controller:ControladoresDadosBasicosCtrl
 * @description
 * # ControladoresDadosBasicosCtrl
 * Controller of the influuntApp
 */
angular.module('influuntApp')
  .controller('ControladoresDadosBasicosCtrl', ['$scope', '$controller', '$filter', 'influuntBlockui', 'influuntAlert', 'Restangular', 'toast', 'PermissionsService', 'PermissionStrategies', 'breadcrumbs',
    function ($scope, $controller, $filter, influuntBlockui, influuntAlert, Restangular, toast, PermissionsService, PermissionStrategies, breadcrumbs) {
      $controller('ControladoresCtrl', {$scope: $scope});

      var deletarCroquiNoServidor, inicializaObjetoCroqui, setarAreaControlador, updateBreadcrumbs;

      $scope.PermissionStrategies = PermissionStrategies;

      $scope.inicializaWizardDadosBasicos = function() {
        return $scope.inicializaWizard().then(function (){
          inicializaObjetoCroqui();
          setarAreaControlador();
          updateBreadcrumbs();
        }).finally(influuntBlockui.unblock);
      };

      $scope.$watch('objeto.todosEnderecos', function(todosEnderecos) {
        if (!_.isArray(todosEnderecos)) { return false; }
        $scope.objeto.nomeEndereco = $filter('nomeEndereco')(todosEnderecos[0]);
      }, true);

      $scope.$watchGroup(['objeto.area.idJson', 'helpers.cidade.areas'], function (){
        $scope.currentSubareas = [];
        if ($scope.objeto && $scope.helpers && $scope.objeto.area && $scope.objeto.area.idJson){
          var area = _.find($scope.helpers.cidade.areas, {idJson: $scope.objeto.area.idJson});
          $scope.currentSubareas = area.subareas;
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
