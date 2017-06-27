'use strict';

/**
 * @ngdoc directive
 * @name influuntApp.directive:wizardFooter
 * @description
 * # wizardFooter
 */
angular.module('influuntApp')
  .directive('wizardFooter', ['$filter', '$state', function ($filter, $state) {
    return {
      templateUrl: 'views/directives/wizard-footer.html',
      restrict: 'E',
      link: function(scope, el, attrs) {
        scope.url = attrs.url;
        scope.currentStep = attrs.currentStep;
        scope.nextStep = attrs.nextStep;
        scope.previousStep = attrs.previousStep;
        scope.requireAssertion = attrs.requireAssertion;

        scope.getStepTitle = function(step) {
          return $filter('translate')($state.get(step).data.title);
        };

        scope.translateBotaoSalvar = function(step) {
          var stepEntreVerdes = 'app.wizard_controladores.entre_verdes';
          var botaoEntreVerdes = 'geral.tooltip.naoPodeSalvarSemConfirmacaoEntreVerdes';
          var botaoDefault = 'geral.tooltip.naoPodeSalvarSemConfirmacao';

          if (step === stepEntreVerdes){
            return $filter('translate')(botaoEntreVerdes);
          } else {
            return $filter('translate')(botaoDefault);
          }
        };
      }
    };
  }])
  .directive('popupRevisao', ['mapaProvider', '$timeout', function (mapaProvider, $timeout) {
    return {
      templateUrl: 'views/controladores/wizard/common/popup-revisao.html',
      restrict: 'E',
      link: function(scope, el, attrs) {
        $(document).ready(function() {
          $('#myModal').on('show.bs.modal', function() {
            $timeout(function() {
              var mapa = mapaProvider.getMap('popup-revisao');
              mapa.resetView();
              mapa.setViewForMarkers();
            }, 500);
          });
        });
      }
    };
  }])
  ;
