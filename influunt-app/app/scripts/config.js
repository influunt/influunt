'use strict';

/**
 * @ngdoc overview
 * @name influuntApp
 * @description
 * # influuntApp
 *
 * Main module of the application.
 */
angular
  .module('influuntApp')
  .config(['$stateProvider', '$urlRouterProvider',
    function ($stateProvider, $urlRouterProvider) {

      $urlRouterProvider.otherwise('/login');

      $stateProvider
        .state('login', {
          url: '/login',
          templateUrl: 'views/login/signin.html',
          controller: 'LoginCtrl',
          controllerAs: 'login',
          data: {
            title: 'geral.dashboard',
          }
        })
        .state('app', {
          abstract: true,
          url: '/app',
          templateUrl: 'views/common/content_top_navigation.html',
          controller: 'MainCtrl',
          controllerAs: 'main',
          data: {
            title: 'geral.dashboard',
            redirectTo: 'app.main'
          }
        })
        .state('app.main', {
          url: '/main',
          templateUrl: 'views/main.html'
        })

        .state('app.cidades', {
          url: '/cidades',
          templateUrl: 'views/cidades/index.html',
          controller: 'CidadesCtrl',
          controllerAs: 'cidades',
          data: {
            title: 'cidades.titulo'
          }
        })

        .state('app.cidades_new', {
          url: '/cidades/new',
          templateUrl: 'views/cidades/new.html',
          controller: 'CidadesCtrl',
          controllerAs: 'cidades',
          data: {
            title: 'cidades.titulo'
          }
        })

        .state('app.cidades_edit', {
          url: '/cidades/:id/edit',
          templateUrl: 'views/cidades/edit.html',
          controller: 'CidadesCtrl',
          controllerAs: 'cidades',
          data: {
            title: 'cidades.titulo'
          }
        })

        .state('app.cidades_show', {
          url: '/cidades/:id',
          templateUrl: 'views/cidades/show.html',
          controller: 'CidadesCtrl',
          controllerAs: 'cidades',
          data: {
            title: 'cidades.titulo'
          }
        })

        // Crud areas.
        .state('app.areas', {
          url: '/areas',
          templateUrl: 'views/areas/index.html',
          controller: 'AreasCtrl',
          controllerAs: 'areas',
          data: {
            title: 'areas.titulo'
          }
        })

        .state('app.areas_new', {
          url: '/areas/new',
          templateUrl: 'views/areas/new.html',
          controller: 'AreasCtrl',
          controllerAs: 'areas',
          data: {
            title: 'areas.titulo'
          }
        })

        .state('app.areas_edit', {
          url: '/areas/:id/edit',
          templateUrl: 'views/areas/edit.html',
          controller: 'AreasCtrl',
          controllerAs: 'areas',
          data: {
            title: 'areas.titulo'
          }
        })

        .state('app.areas_show', {
          url: '/areas/:id',
          templateUrl: 'views/areas/show.html',
          controller: 'AreasCtrl',
          controllerAs: 'areas',
          data: {
            title: 'areas.titulo'
          }
        })

        // Crud controladores.
        .state('app.controladores', {
          url: '/controladores',
          templateUrl: 'views/controladores/index.html',
          controller: 'ControladoresCtrl',
          controllerAs: 'controladores',
          data: {
            title: 'controladores.titulo'
          }
        })

        .state('app.controladores_show', {
          url: '/controladores/:id',
          templateUrl: 'views/controladores/show.html',
          controller: 'ControladoresCtrl',
          controllerAs: 'controladores',
          data: {
            title: 'controladores.titulo'
          }
        })

        // Crud tipos detectores.
        .state('app.tipos_detectores', {
          url: '/tipos_detectores',
          templateUrl: 'views/tipos_detectores/index.html',
          controller: 'TiposDetectoresCtrl',
          controllerAs: 'tipos_detectores',
          data: {
            title: 'tipos_detectores.titulo'
          }
        })

        .state('app.tipos_detectores_new', {
          url: '/tipos_detectores/new',
          templateUrl: 'views/tipos_detectores/new.html',
          controller: 'TiposDetectoresCtrl',
          controllerAs: 'tipos_detectores',
          data: {
            title: 'tipos_detectores.titulo'
          }
        })

        .state('app.tipos_detectores_edit', {
          url: '/tipos_detectores/:id/edit',
          templateUrl: 'views/tipos_detectores/edit.html',
          controller: 'TiposDetectoresCtrl',
          controllerAs: 'tipos_detectores',
          data: {
            title: 'tipos_detectores.titulo'
          }
        })

        .state('app.tipos_detectores_show', {
          url: '/tipos_detectores/:id',
          templateUrl: 'views/tipos_detectores/show.html',
          controller: 'TiposDetectoresCtrl',
          controllerAs: 'tipos_detectores',
          data: {
            title: 'tipos_detectores.titulo'
          }
        })

        // Crud Fabricantes.
        .state('app.fabricantes', {
          url: '/fabricantes',
          templateUrl: 'views/fabricantes/index.html',
          controller: 'FabricantesCtrl',
          controllerAs: 'fabricantes',
          data: {
            title: 'fabricantes.titulo'
          }
        })

        .state('app.fabricantes_new', {
          url: '/fabricantes/new',
          templateUrl: 'views/fabricantes/new.html',
          controller: 'FabricantesCtrl',
          controllerAs: 'fabricantes',
          data: {
            title: 'fabricantes.titulo'
          }
        })

        .state('app.fabricantes_edit', {
          url: '/fabricantes/:id/edit',
          templateUrl: 'views/fabricantes/edit.html',
          controller: 'FabricantesCtrl',
          controllerAs: 'fabricantes',
          data: {
            title: 'fabricantes.titulo'
          }
        })

        .state('app.fabricantes_show', {
          url: '/fabricantes/:id',
          templateUrl: 'views/fabricantes/show.html',
          controller: 'FabricantesCtrl',
          controllerAs: 'fabricantes',
          data: {
            title: 'fabricantes.titulo'
          }
        })

        // Crud Configuracao Controladores.
        .state('app.configuracoes_controladores', {
          url: '/configuracoes_controladores',
          templateUrl: 'views/configuracoes_controladores/index.html',
          controller: 'ConfiguracaoControladoresCtrl',
          controllerAs: 'configuracoes_controladores',
          data: {
            title: 'configuracoes_controladores.titulo'
          }
        })

        .state('app.configuracoes_controladores_new', {
          url: '/configuracoes_controladores/new',
          templateUrl: 'views/configuracoes_controladores/new.html',
          controller: 'ConfiguracaoControladoresCtrl',
          controllerAs: 'configuracoes_controladores',
          data: {
            title: 'configuracoes_controladores.titulo'
          }
        })

        .state('app.configuracoes_controladores_edit', {
          url: '/configuracoes_controladores/:id/edit',
          templateUrl: 'views/configuracoes_controladores/edit.html',
          controller: 'ConfiguracaoControladoresCtrl',
          controllerAs: 'configuracoes_controladores',
          data: {
            title: 'configuracoes_controladores.titulo'
          }
        })

        .state('app.configuracoes_controladores_show', {
          url: '/configuracoes_controladores/:id',
          templateUrl: 'views/configuracoes_controladores/show.html',
          controller: 'ConfiguracaoControladoresCtrl',
          controllerAs: 'configuracoes_controladores',
          data: {
            title: 'configuracoes_controladores.titulo'
          }
        })

        // Crud Modelo Controladores.
        .state('app.modelos_controladores', {
          url: '/modelos_controladores',
          templateUrl: 'views/modelos_controladores/index.html',
          controller: 'ModelosControladoresCtrl',
          controllerAs: 'modelos_controladores',
          data: {
            title: 'modelos_controladores.titulo'
          }
        })

        .state('app.modelos_controladores_new', {
          url: '/modelos_controladores/new',
          templateUrl: 'views/modelos_controladores/new.html',
          controller: 'ModelosControladoresCtrl',
          controllerAs: 'modelos_controladores',
          data: {
            title: 'modelos_controladores.titulo'
          }
        })

        .state('app.modelos_controladores_edit', {
          url: '/modelos_controladores/:id/edit',
          templateUrl: 'views/modelos_controladores/edit.html',
          controller: 'ModelosControladoresCtrl',
          controllerAs: 'modelos_controladores',
          data: {
            title: 'modelos_controladores.titulo'
          }
        })

        .state('app.modelos_controladores_show', {
          url: '/modelos_controladores/:id',
          templateUrl: 'views/modelos_controladores/show.html',
          controller: 'ModelosControladoresCtrl',
          controllerAs: 'modelos_controladores',
          data: {
            title: 'modelos_controladores.titulo'
          }
        })

        .state('app.wizard_controladores', {
          url: '/wizard-controladores',
          templateUrl: 'views/controladores/wizard/wizard.html',
          controller: 'ControladoresCtrl',
          data: {
            title: 'controladores.titulo'
          }
        })

        .state('app.wizard_controladores.dados_basicos', {
          url: '/dados-basicos/:id',
          templateUrl: 'views/controladores/wizard/dados-basicos.html',
          data: {
            title: 'controladores.titulo'
          }
        })

        .state('app.wizard_controladores.aneis', {
          url: '/aneis/:id',
          templateUrl: 'views/controladores/wizard/aneis.html',
          data: {
            title: 'controladores.titulo'
          }
        })

        .state('app.wizard_controladores.associacao', {
          url: '/associacao/:id',
          templateUrl: 'views/controladores/wizard/associacao.html',
          data: {
            title: 'controladores.titulo'
          }
        })

        .state('app.wizard_controladores.estagios', {
          url: '/estagios/:id',
          templateUrl: 'views/controladores/wizard/estagios.html',
          data: {
            title: 'controladores.titulo'
          }
        })

        .state('app.wizard_controladores.associacao_detector', {
          url: '/associacao-detector/:id',
          templateUrl: 'views/controladores/wizard/associacao-detector.html',
          data: {
            title: 'controladores.titulo'
          }
        })
        .state('app.wizard_controladores.entre_verdes', {
          url: '/entre-verdes/:id',
          templateUrl: 'views/controladores/wizard/entre-verdes.html',
          data: {
            title: 'controladores.titulo'
          }
        })

        .state('app.wizard_controladores.verdes_conflitantes', {
          url: '/verdes-conflitantes/:id',
          templateUrl: 'views/controladores/wizard/verdes-conflitantes.html',
          data: {
            title: 'controladores.titulo'
          }
        })

        // Crud perfis.
        .state('app.perfis', {
          url: '/perfis',
          templateUrl: 'views/perfis/index.html',
          controller: 'PerfisCtrl',
          controllerAs: 'perfis',
          data: {
            title: 'perfis.titulo'
          }
        })

        .state('app.perfis_new', {
          url: '/perfis/new',
          templateUrl: 'views/perfis/new.html',
          controller: 'PerfisCtrl',
          controllerAs: 'perfis',
          data: {
            title: 'perfis.titulo'
          }
        })

        .state('app.perfis_edit', {
          url: '/perfis/:id/edit',
          templateUrl: 'views/perfis/edit.html',
          controller: 'PerfisCtrl',
          controllerAs: 'perfis',
          data: {
            title: 'perfis.titulo'
          }
        })

        .state('app.perfis_show', {
          url: '/perfis/:id',
          templateUrl: 'views/perfis/show.html',
          controller: 'PerfisCtrl',
          controllerAs: 'perfis',
          data: {
            title: 'perfis.titulo'
          }
        })

        .state('app.perfis_permissoes', {
          url: '/perfis/:id/permissoes',
          templateUrl: 'views/perfis/permissoes.html',
          controller: 'PerfisCtrl',
          controllerAs: 'perfis',
          data: {
            title: 'perfis.titulo'
          }
        })

        // Crud permissoes.
        .state('app.permissoes', {
          url: '/permissoes',
          templateUrl: 'views/permissoes/index.html',
          controller: 'PermissoesCtrl',
          controllerAs: 'permissoes',
          data: {
            title: 'permissoes.titulo'
          }
        })

        .state('app.permissoes_new', {
          url: '/permissoes/new',
          templateUrl: 'views/permissoes/new.html',
          controller: 'PermissoesCtrl',
          controllerAs: 'permissoes',
          data: {
            title: 'permissoes.titulo'
          }
        })

        .state('app.permissoes_edit', {
          url: '/permissoes/:id/edit',
          templateUrl: 'views/permissoes/edit.html',
          controller: 'PermissoesCtrl',
          controllerAs: 'permissoes',
          data: {
            title: 'permissoes.titulo'
          }
        })

        .state('app.permissoes_show', {
          url: '/permissoes/:id',
          templateUrl: 'views/permissoes/show.html',
          controller: 'PermissoesCtrl',
          controllerAs: 'permissoes',
          data: {
            title: 'permissoes.titulo'
          }
        })

        // Crud usuarios.
        .state('app.usuarios', {
          url: '/usuarios',
          templateUrl: 'views/usuarios/index.html',
          controller: 'UsuariosCtrl',
          controllerAs: 'usuarios',
          data: {
            title: 'usuarios.titulo'
          }
        })

        .state('app.usuarios_new', {
          url: '/usuarios/new',
          templateUrl: 'views/usuarios/new.html',
          controller: 'UsuariosCtrl',
          controllerAs: 'usuarios',
          data: {
            title: 'usuarios.titulo'
          }
        })

        .state('app.usuarios_edit', {
          url: '/usuarios/:id/edit',
          templateUrl: 'views/usuarios/edit.html',
          controller: 'UsuariosCtrl',
          controllerAs: 'usuarios',
          data: {
            title: 'usuarios.titulo'
          }
        })

        .state('app.usuarios_show', {
          url: '/usuarios/:id',
          templateUrl: 'views/usuarios/show.html',
          controller: 'UsuariosCtrl',
          controllerAs: 'usuarios',
          data: {
            title: 'usuarios.titulo'
          }
        })
      ;
    }])

    .run(['$rootScope', '$state', '$timeout',
      function($rootScope, $state, $timeout) {

        $rootScope.isAuthenticated = function() {
          return !!localStorage.token;
        };

        $rootScope.$on('$stateChangeStart', function(ev, toState) {
          $timeout(function() {
            if (!$rootScope.isAuthenticated() && toState.name !== 'login') {
                $state.go('login');
            }
          });
        });

      }]);
