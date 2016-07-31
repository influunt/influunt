'use strict';

/**
 * @ngdoc directive
 * @name influuntApp.directive:influuntTabs
 * @description
 * # influuntTabs
 */
angular.module('influuntApp')
  .directive('influuntTabs', ['$templateCache', '$interpolate', '$compile', function ($templateCache, $interpolate, $compile) {
    return {
      restrict: 'E',
      scope: {
        onAdd: '&',
        onRemove: '&',
        onActivate: '&',
        errorCheck: '&',
        aneisAtivos: '=',
        maxTabs: '='
      },
      template: '<ul class="nav nav-tabs"></ul>',
      link: function (scope, element) {

        var tabs;
        var tabTemplate = $templateCache.get('views/directives/influunt-tabs/_tab.html');
        var initializing = true;

        var checkTabLimit = function() {
          if (scope.maxTabs) {
            var totalTabs = element.find('li[role="tab"]:not(.addTab)').length;
            if (totalTabs >= scope.maxTabs) {
              element.find('li[role="tab"].addTab').hide();
            } else {
              element.find('li[role="tab"].addTab').show();
            }
          }
        };

        var toggleCloseButton = function() {
          $(element).find('span.ui-icon-circle-close').hide();
          $(element).find('span.ui-icon-circle-close:last').show();
        };

        var tabAdded = function(event, data) {
          checkTabLimit();
          scope.onAdd()(data);
          toggleCloseButton();
          $(data.tab).find('a.closable').html('Anel ' + (data.index + 1));
        };

        var tabRemoved = function(event, data) {
          checkTabLimit();
          scope.onRemove()(data);
          toggleCloseButton();
        };

        var tabActivated = function(event, data) {
          if (data.newTab.find('a:first').html() !== 'New tab') {
            var tabIndex = data.newTab.index('li[role="tab"]');
            scope.onActivate()(tabIndex);
          }
        };

        var createInitialTabs = function(element) {
          var ul = element.find('ul');
          scope.aneisAtivos.forEach(function(anel) {
            scope.anel = anel;
            var template = $interpolate(tabTemplate)(scope);
            ul.append(template);
          });
          delete scope.anel;
        };

        scope.tabHasError = function(tabIndex) {
          if (angular.isFunction(scope.errorCheck())) {
            return scope.errorCheck()(tabIndex);
          } else {
            return false;
          }
        };

        scope.$watch('aneisAtivos', function(value) {
          if (value) {

            if (initializing) {
              createInitialTabs(element);
              initializing = false;
            }

            tabs = $(element).tabs({
              closable: true,
              addTab: true,
              add: tabAdded,
              remove: tabRemoved,
              activate: tabActivated }).tabs('overflowResize');

            $compile(element.contents())(scope);
          }
        });
      }
    };
  }]);
