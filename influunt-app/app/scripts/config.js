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

        .state('app.main_info', {
          url: '/main-info',
          templateUrl: 'views/main-info.html'
        })

        .state('app.status', {
          url: '/status',
          templateUrl: 'views/status/index.html',
          controller: 'StatusCtrl',
          controllerAs: 'status',
          data: {
            title: 'status.titulo'
          }
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

        .state('app.horarios', {
          url: '/horarios',
          templateUrl: 'views/horarios/index.html',
          controller: 'HorariosCtrl',
          controllerAs: 'horarios',
          data: {
            title: 'horarios.titulo'
          }
        })

        .state('app.cidades_new', {
          url: '/cidades/new',
          templateUrl: 'views/cidades/new.html',
          controller: 'CidadesCtrl',
          controllerAs: 'cidades',
          data: {
            title: 'cidades.titulo',
            breadcrumb: 'cidades.novo'
          }
        })

        .state('app.cidades_edit', {
          url: '/cidades/:id/edit',
          templateUrl: 'views/cidades/edit.html',
          controller: 'CidadesCtrl',
          controllerAs: 'cidades',
          data: {
            title: 'cidades.titulo',
            breadcrumb: 'cidades.editar'
          }
        })

        .state('app.cidades_show', {
          url: '/cidades/:id',
          templateUrl: 'views/cidades/show.html',
          controller: 'CidadesCtrl',
          controllerAs: 'cidades',
          data: {
            title: 'cidades.titulo',
            breadcrumb: 'cidades.mostrar'
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
            title: 'areas.titulo',
            breadcrumb: 'areas.novo'
          }
        })

        .state('app.areas_edit', {
          url: '/areas/:id/edit',
          templateUrl: 'views/areas/edit.html',
          controller: 'AreasCtrl',
          controllerAs: 'areas',
          data: {
            title: 'areas.titulo',
            breadcrumb: 'areas.editar'
          }
        })

        .state('app.areas_show', {
          url: '/areas/:id',
          templateUrl: 'views/areas/show.html',
          controller: 'AreasCtrl',
          controllerAs: 'areas',
          data: {
            title: 'areas.titulo',
            breadcrumb: 'areas.mostrar'
          }
        })

        // Crud subareas.
        .state('app.subareas', {
          url: '/subareas',
          templateUrl: 'views/subareas/index.html',
          controller: 'SubAreasCtrl',
          controllerAs: 'subareas',
          data: {
            title: 'subareas.titulo'
          }
        })

        .state('app.subareas_new', {
          url: '/subareas/new',
          templateUrl: 'views/subareas/new.html',
          controller: 'SubAreasCtrl',
          controllerAs: 'subareas',
          data: {
            title: 'subareas.titulo',
            breadcrumb: 'subareas.novo'
          }
        })

        .state('app.subareas_edit', {
          url: '/subareas/:id/edit',
          templateUrl: 'views/subareas/edit.html',
          controller: 'SubAreasCtrl',
          controllerAs: 'subareas',
          data: {
            title: 'subareas.titulo',
            breadcrumb: 'subareas.editar'
          }
        })

        .state('app.subareas_show', {
          url: '/subareas/:id',
          templateUrl: 'views/subareas/show.html',
          controller: 'SubAreasCtrl',
          controllerAs: 'subareas',
          data: {
            title: 'subareas.titulo',
            breadcrumb: 'subareas.mostrar'
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
          controller: 'ControladoresRevisaoCtrl',
          controllerAs: 'controladores',
          data: {
            title: 'controladores.titulo'
          }
        })

        //Timeline
        .state('app.controladores_timeline', {
          url: '/controladores/:id/timeline',
          templateUrl: 'views/controladores/timeline.html',
          controller: 'ControladoresCtrl',
          controllerAs: 'controladores',
          data: {
            title: 'timeline.titulo'
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
            title: 'fabricantes.titulo',
            breadcrumb: 'fabricantes.novo'
          }
        })

        .state('app.fabricantes_edit', {
          url: '/fabricantes/:id/edit',
          templateUrl: 'views/fabricantes/edit.html',
          controller: 'FabricantesCtrl',
          controllerAs: 'fabricantes',
          data: {
            title: 'fabricantes.titulo',
            breadcrumb: 'fabricantes.editar'
          }
        })

        .state('app.fabricantes_show', {
          url: '/fabricantes/:id',
          templateUrl: 'views/fabricantes/show.html',
          controller: 'FabricantesCtrl',
          controllerAs: 'fabricantes',
          data: {
            title: 'fabricantes.titulo',
            breadcrumb: 'fabricantes.mostrar'
          }
        })


        // Crud Modelos Contolador.
        .state('app.modelos', {
          url: '/modelos',
          templateUrl: 'views/modelos/index.html',
          controller: 'ModelosCtrl',
          controllerAs: 'modelos',
          data: {
            title: 'modelos.titulo'
          }
        })

        .state('app.modelos_new', {
          url: '/modelos/new',
          templateUrl: 'views/modelos/new.html',
          controller: 'ModelosCtrl',
          controllerAs: 'modelos',
          data: {
            title: 'modelos.titulo',
            breadcrumb: 'modelos.novo'
          }
        })

        .state('app.modelos_edit', {
          url: '/modelos/:id/edit',
          templateUrl: 'views/modelos/edit.html',
          controller: 'ModelosCtrl',
          controllerAs: 'modelos',
          data: {
            title: 'modelos.titulo',
            breadcrumb: 'modelos.editar'
          }
        })

        .state('app.modelos_show', {
          url: '/modelos/:id',
          templateUrl: 'views/modelos/show.html',
          controller: 'ModelosCtrl',
          controllerAs: 'modelos',
          data: {
            title: 'modelos.titulo',
            breadcrumb: 'modelos.mostrar'
          }
        })



        .state('app.wizard_controladores', {
          url: '/wizard-controladores',
          templateUrl: 'views/controladores/wizard/wizard.html',
          controller: 'ControladoresCtrl',
          abstract: true,
          data: {
            title: 'controladores.titulo',
            redirectTo: 'app.controladores'
          }
        })

        .state('app.wizard_controladores.dados_basicos', {
          url: '/dados-basicos/:id',
          templateUrl: 'views/controladores/wizard/dados-basicos.html',
          controller: 'ControladoresDadosBasicosCtrl',
          data: {
            title: 'controladores.dadosBasicos.titulo'
          }
        })

        .state('app.wizard_controladores.aneis', {
          url: '/aneis/:id',
          templateUrl: 'views/controladores/wizard/aneis.html',
          controller: 'ControladoresAneisCtrl',
          data: {
            title: 'controladores.aneis.titulo'
          }
        })

        .state('app.wizard_controladores.configuracao_grupo', {
          url: '/configuracao-grupo/:id',
          templateUrl: 'views/controladores/wizard/configuracao-grupo.html',
          controller: 'ControladoresConfiguracaoGruposCtrl',
          data: {
            title: 'controladores.gruposSemaforicos.titulo'
          }
        })

        .state('app.wizard_controladores.associacao', {
          url: '/associacao/:id',
          templateUrl: 'views/controladores/wizard/associacao.html',
          controller: 'ControladoresAssociacaoCtrl',
          data: {
            title: 'controladores.associacao.titulo'
          }
        })

        .state('app.wizard_controladores.verdes_conflitantes', {
          url: '/verdes-conflitantes/:id',
          templateUrl: 'views/controladores/wizard/verdes-conflitantes.html',
          controller: 'ControladoresVerdesConflitantesCtrl',
          data: {
            title: 'controladores.verdesConflitantes.titulo'
          }
        })

        .state('app.wizard_controladores.transicoes_proibidas', {
          url: '/transicoes-proibidas/:id',
          templateUrl: 'views/controladores/wizard/transicoes-proibidas.html',
          controller: 'ControladoresTransicoesProibidasCtrl',
          data: {
            title: 'controladores.transicoesProibidas.titulo'
          }
        })

        .state('app.wizard_controladores.atraso_de_grupo', {
          url: '/atraso-de-grupo/:id',
          templateUrl: 'views/controladores/wizard/atraso-de-grupo.html',
          controller: 'ControladoresAtrasoDeGrupoCtrl',
          data: {
            title: 'controladores.atrasoDeGrupo.titulo'
          }
        })

        .state('app.wizard_controladores.entre_verdes', {
          url: '/entre-verdes/:id',
          templateUrl: 'views/controladores/wizard/entre-verdes.html',
          controller: 'ControladoresEntreVerdesCtrl',
          data: {
            title: 'controladores.entreVerdes.titulo'
          }
        })

        .state('app.wizard_controladores.associacao_detectores', {
          url: '/associacao-detectores/:id',
          controller: 'ControladoresAssociacaoDetectoresCtrl',
          templateUrl: 'views/controladores/wizard/associacao-detectores.html',
          data: {
            title: 'controladores.associacaoDetectores.titulo'
          }
        })

        .state('app.wizard_controladores.revisao', {
          url: '/revisao/:id',
          controller: 'ControladoresRevisaoCtrl',
          templateUrl: 'views/controladores/wizard/revisao.html',
          data: {
            title: 'controladores.revisao.titulo'
          }
        })

        .state('app.mapa_controladores', {
          url: '/mapa-controladores',
          templateUrl: 'views/controladores/mapa.html',
          controller: 'ControladoresMapaCtrl',
          data: {
            title: 'controladores.mapa'
          }
        })

        .state('app.planos', {
          url: '/planos/:id',
          templateUrl: 'views/planos/criacao-planos.html',
          controller: 'PlanosCtrl',
          data: {
            title: 'planos.titulo',
            somenteVisualizacao: true
          }
        })

        .state('app.planos_edit', {
          url: '/planos/:id/edit',
          templateUrl: 'views/planos/criacao-planos.html',
          controller: 'PlanosCtrl',
          data: {
            title: 'planos.titulo',
            somenteVisualizacao: false
          }
        })

        .state('app.tabela_horarios', {
          url: '/tabela_horarios/:id',
          templateUrl: 'views/tabela_horarios/configuracao.html',
          controller: 'TabelaHorariosCtrl',
          data: {
            title: 'tabelaHorarios.titulo',
            somenteVisualizacao: true
          }
        })

        .state('app.tabela_horarios_edit', {
          url: '/tabela_horarios/:id/edit',
          templateUrl: 'views/tabela_horarios/configuracao.html',
          controller: 'TabelaHorariosCtrl',
          data: {
            title: 'tabelaHorarios.titulo',
            somenteVisualizacao: false
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
            title: 'perfis.titulo',
            breadcrumb: 'perfis.novo'
          }
        })

        .state('app.perfis_edit', {
          url: '/perfis/:id/edit',
          templateUrl: 'views/perfis/edit.html',
          controller: 'PerfisCtrl',
          controllerAs: 'perfis',
          data: {
            title: 'perfis.titulo',
            breadcrumb: 'perfis.editar'
          }
        })

        .state('app.perfis_show', {
          url: '/perfis/:id',
          templateUrl: 'views/perfis/show.html',
          controller: 'PerfisCtrl',
          controllerAs: 'perfis',
          data: {
            title: 'perfis.titulo',
            breadcrumb: 'perfis.mostrar'
          }
        })

        .state('app.perfis_permissoes', {
          url: '/perfis/:id/permissoes',
          templateUrl: 'views/perfis/permissoes.html',
          controller: 'PerfisCtrl',
          controllerAs: 'perfis',
          data: {
            title: 'perfis.titulo',
            breadcrumb: 'perfis.definir_permissoes'
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
            title: 'permissoes.titulo',
            breadcrumb: 'permissoes.novo'
          }
        })

        .state('app.permissoes_edit', {
          url: '/permissoes/:id/edit',
          templateUrl: 'views/permissoes/edit.html',
          controller: 'PermissoesCtrl',
          controllerAs: 'permissoes',
          data: {
            title: 'permissoes.titulo',
            breadcrumb: 'permissoes.editar'
          }
        })

        .state('app.permissoes_show', {
          url: '/permissoes/:id',
          templateUrl: 'views/permissoes/show.html',
          controller: 'PermissoesCtrl',
          controllerAs: 'permissoes',
          data: {
            title: 'permissoes.titulo',
            breadcrumb: 'permissoes.mostrar'
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
            title: 'usuarios.titulo',
            breadcrumb: 'usuarios.novo'
          }
        })

        .state('app.usuarios_edit', {
          url: '/usuarios/:id/edit',
          templateUrl: 'views/usuarios/edit.html',
          controller: 'UsuariosCtrl',
          controllerAs: 'usuarios',
          data: {
            title: 'usuarios.titulo',
            breadcrumb: 'usuarios.editar'
          }
        })

        .state('app.usuarios_show', {
          url: '/usuarios/:id',
          templateUrl: 'views/usuarios/show.html',
          controller: 'UsuariosCtrl',
          controllerAs: 'usuarios',
          data: {
            title: 'usuarios.titulo',
            breadcrumb: 'usuarios.mostrar'
          }
        })

        .state('app.usuarios_access_log', {
          url: '/usuarios/:id/access_log',
          templateUrl: 'views/usuarios/access_log.html',
          controller: 'UsuariosCtrl',
          controllerAs: 'usuarios',
          data: {
            title: 'usuarios.access_log',
            breadcrumb: 'usuarios.access_log'
          }
        })

        // CRUD Agrupamentos
        .state('app.agrupamentos', {
          url: '/agrupamentos',
          templateUrl: 'views/agrupamentos/index.html',
          controller: 'AgrupamentosCtrl',
          controllerAs: 'agrupamentos',
          data: {
            title: 'agrupamentos.titulo'
          }
        })

        .state('app.agrupamentos_new', {
          url: '/agrupamentos/new',
          templateUrl: 'views/agrupamentos/new.html',
          controller: 'AgrupamentosCtrl',
          controllerAs: 'agrupamentos',
          data: {
            title: 'agrupamentos.titulo',
            breadcrumb: 'agrupamentos.novo'
          }
        })

        .state('app.agrupamentos_edit', {
          url: '/agrupamentos/:id/edit',
          templateUrl: 'views/agrupamentos/edit.html',
          controller: 'AgrupamentosCtrl',
          controllerAs: 'agrupamentos',
          data: {
            title: 'agrupamentos.titulo',
            breadcrumb: 'agrupamentos.editar'
          }
        })

        .state('app.agrupamentos_show', {
          url: '/agrupamentos/:id',
          templateUrl: 'views/agrupamentos/show.html',
          controller: 'AgrupamentosCtrl',
          controllerAs: 'agrupamentos',
          data: {
            title: 'agrupamentos.titulo',
            breadcrumb: 'agrupamentos.mostrar'
          }
        })

        // AUDITORIA
        .state('app.auditorias', {
          url: '/auditorias',
          templateUrl: 'views/auditorias/index.html',
          controller: 'AuditoriasCtrl',
          controllerAs: 'auditorias',
          data: {
            title: 'auditorias.titulo',
          }
        })
        .state('app.auditorias_show', {
          url: '/auditorias/:id',
          templateUrl: 'views/auditorias/show.html',
          controller: 'AuditoriasCtrl',
          controllerAs: 'auditorias',
          data: {
            title: 'auditorias.titulo',
            breadcrumb: 'auditorias.mostrar'
          }
        })
      ;
    }])

    .run(['$rootScope', '$state', '$timeout',
      function($rootScope, $state, $timeout) {

        $rootScope.$state = $state;

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
