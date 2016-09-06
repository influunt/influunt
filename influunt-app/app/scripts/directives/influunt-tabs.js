'use strict';

/**
 * @ngdoc directive
 * @name influuntApp.directive:influuntTabs
 * @description
 * # influuntTabs
 */
angular.module('influuntApp')
  .directive('influuntTabs', ['$templateCache', '$interpolate', '$compile', '$timeout', function ($templateCache, $interpolate, $compile, $timeout) {
    return {
      restrict: 'E',
      scope: {
        onAdd: '&',
        onRemove: '&',
        onActivate: '&',
        beforeRemove: '&', // deve retornar uma Promise
        errorCheck: '&',
        aneisAtivos: '=',
        maxTabs: '=',
        canAddTabs: '=',
        canRemoveTabs: '=',
        nameTabs: '=?'
      },
      template: '<ul class="nav nav-tabs"></ul>',
      link: function (scope, element) {

        var initializing = true;

        var inicializaNameTabs = function() {
          if (_.isArray(scope.nameTabs) &&
              _.isArray(scope.aneisAtivos) &&
              scope.nameTabs.length !== scope.aneisAtivos.length) {
            throw new Error('A lista de nomes das tabs deve ter a mesma quantidade de itens das tabs ativas.');
          }
        };

        scope.$watch('nameTabs', inicializaNameTabs);

        var getNomeTabs = function(index) {
          if (_.isArray(scope.nameTabs)) {
            return scope.nameTabs[index];
          } else {
            return (scope.nameTabs || 'Anel') + ' ' + (index + 1);
          }
        };

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
          if (!!scope.canRemoveTabs) {
            $(element).find('span.ui-icon-circle-close').hide();
            $(element).find('span.ui-icon-circle-close:not(:first):last').show();
          }
        };

        var tabAdded = function(event, data) {
          if (!initializing) {
            scope.$apply(function() {
              checkTabLimit();
              toggleCloseButton();
              $(data.tab).find('a.closable').html(getNomeTabs(data.index));
              if (angular.isFunction(scope.onAdd())) {
                scope.onAdd()(data);
              }
            });
          }
        };

        var _beforeRemove = function(event, data) {
          if (angular.isFunction(scope.beforeRemove())) {
            scope.beforeRemove()(data).then(function(result) {
              if (result) {
                var tabs = $(element).tabs('instance');
                tabs.remove(data.index, true);
              }else{
                var el = $(data.tab.children).first();
                el.css('position', 'absolute');
                $timeout(function (){
                  el.attr('style', null);
                });
              }
            });
            return false;
          }
          return true;
        };

        var tabRemoved = function(event, data) {
          
          $timeout(function() {
            checkTabLimit();
            toggleCloseButton();
            scope.onRemove()(data);
          });
        };

        var tabActivated = function(event, data) {
          if (!initializing) {
            $timeout(function() {
              if (data.newTab.find('a:first').html() !== 'New tab') {
                var elements = $(event.target).find('li[role="tab"]');
                var tabIndex = elements.index(data.newTab);
                scope.onActivate()(tabIndex);
              }
            });
          }
        };

        var createInitialTabs = function() {
          var tabs = $(element).tabs('instance');
          _.forEach(scope.aneisAtivos, function(anel, index) {
            // tabs.add(scope.nameTabs + ' ' + (index+1), !!scope.canAddTabs);
            tabs.add(getNomeTabs(index), !!scope.canAddTabs);
          });
          tabs.activate(0);
          toggleCloseButton();
        };

        scope.tabHasError = function(tabIndex) {
          if (angular.isFunction(scope.errorCheck())) {
            return scope.errorCheck()(tabIndex);
          }
          return false;
        };

        scope.$watch('aneisAtivos', function(value) {
          if (value) {
            $(element).tabs({
              closable: !!scope.canRemoveTabs,
              addTab: !!scope.canAddTabs,
              add: tabAdded,
              remove: tabRemoved,
              beforeRemove: _beforeRemove,
              activate: tabActivated }).tabs('overflowResize');

            if (initializing) {
              createInitialTabs();
              initializing = false;
            }

            $compile(element.contents())(scope);
          }
        });
      }
    };
  }]);
