'use strict';

/**
 * @ngdoc directive
 * @name influuntApp.directive:influuntTabs
 * @description
 * # influuntTabs
 */
angular.module('influuntApp')
  .directive('influuntTabs', ['$templateCache', '$interpolate', '$compile', '$filter', function ($templateCache, $interpolate, $compile, $filter) {
    return {
      restrict: 'E',
      scope: {
        onAdd: '&',
        onRemove: '&',
        onActivate: '&',
        errorCheck: '&',
        aneisAtivos: '=',
        maxTabs: '=',
        canAddTabs: '=',
        nameTabs: '@'
      },
      template: '<ul class="nav nav-tabs"></ul>',
      link: function (scope, element) {
        //TODO: Verificar I18n
        scope.nameTabs = scope.nameTabs || 'Anel';
        var tabs;
        var tabTemplate = $templateCache.get('views/directives/influunt-tabs/_tab.html');
        var initializing = true;

        var hideAddButton = function() {
          element.find('li[role="tab"].addTab').hide();
        };

        var showAddButton = function() {
          element.find('li[role="tab"].addTab').show();
        };

        var checkTabLimit = function() {
          if (scope.maxTabs) {
            var totalTabs = element.find('li[role="tab"]:not(.addTab)').length;
            if (totalTabs >= scope.maxTabs) {
              hideAddButton();
            } else {
              showAddButton();
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
          $(data.tab).find('a.closable').html(scope.nameTabs + ' ' + (data.index + 1));
        };

        var tabRemoved = function(event, data) {
          checkTabLimit();
          scope.onRemove()(data);
          toggleCloseButton();
        };

        var tabActivated = function(event, data) {
          scope.$apply(function() {
            if (data.newTab.find('a:first').html() !== 'New tab') {
              var elements = $(event.target).find('li[role="tab"]');
              var tabIndex = elements.index(data.newTab);
              scope.onActivate()(tabIndex);
            }
          });
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
              addTab: !!scope.canAddTabs,
              add: tabAdded,
              remove: tabRemoved,
              activate: tabActivated }).tabs('overflowResize');

            $compile(element.contents())(scope);
          }
        });
      }
    };
  }]);
