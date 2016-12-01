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

      $urlRouterProvider.otherwise(function($injector) {
        var $state = $injector.get('$state');
        $state.go('login');
      });

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

        .state('recuperar_senha', {
          url: '/recuperar_senha',
          templateUrl: 'views/login/recuperar_senha.html',
          controller: 'LoginCtrl',
          controllerAs: 'login',
          data: {
            title: 'geral.dashboard',
          }
        })

        .state('redefinir_senha', {
          url: '/redefinir_senha',
          templateUrl: 'views/login/redefinir_senha.html',
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
          templateUrl: 'views/main.html',
          onExit: ['pahoProvider', function(pahoProvider) {
            pahoProvider.disconnect();
          }]
        })

        .state('app.dashboard_detalhe_controlador', {
          url: '/main/controlador/:id',
          templateUrl: 'views/dashboard/detalhe_controlador.html',
          controller: 'MainCtrl',
          controllerAs: 'main',
          data: {
            title: 'geral.dashboard',
            breadcrumb: 'controladores.controlador',
            permissions: {
              only: 'visualizarDetalhesControlador',
              redirectTo: 'app.main'
            }
          }
        })

        // CRUD cidades
        .state('app.cidades', {
          url: '/cidades',
          templateUrl: 'views/cidades/index.html',
          controller: 'CidadesCtrl',
          controllerAs: 'cidades',
          data: {
            title: 'cidades.titulo',
            permissions: {
              only: 'listarCidades',
              redirectTo: 'app.main'
            }
          }
        })

        .state('app.cidades_new', {
          url: '/cidades/new',
          templateUrl: 'views/cidades/new.html',
          controller: 'CidadesCtrl',
          controllerAs: 'cidades',
          data: {
            title: 'cidades.titulo',
            breadcrumb: 'cidades.novo',
            permissions: {
              only: 'criarCidades',
              redirectTo: 'app.main'
            }
          }
        })

        .state('app.cidades_edit', {
          url: '/cidades/:id/edit',
          templateUrl: 'views/cidades/edit.html',
          controller: 'CidadesCtrl',
          controllerAs: 'cidades',
          data: {
            title: 'cidades.titulo',
            breadcrumb: 'cidades.editar',
            permissions: {
              only: 'editarCidades',
              redirectTo: 'app.main'
            }
          }
        })

        .state('app.cidades_show', {
          url: '/cidades/:id',
          templateUrl: 'views/cidades/show.html',
          controller: 'CidadesCtrl',
          controllerAs: 'cidades',
          data: {
            title: 'cidades.titulo',
            breadcrumb: 'cidades.mostrar',
            permissions: {
              only: 'visualizarCidades',
              redirectTo: 'app.main'
            }
          }
        })

        // Crud areas.
        .state('app.areas', {
          url: '/areas',
          templateUrl: 'views/areas/index.html',
          controller: 'AreasCtrl',
          controllerAs: 'areas',
          data: {
            title: 'areas.titulo',
            permissions: {
              only: 'listarAreas',
              redirectTo: 'app.main'
            }
          }
        })

        .state('app.areas_new', {
          url: '/areas/new',
          templateUrl: 'views/areas/new.html',
          controller: 'AreasCtrl',
          controllerAs: 'areas',
          data: {
            title: 'areas.titulo',
            breadcrumb: 'areas.novo',
            permissions: {
              only: 'criarAreas',
              redirectTo: 'app.main'
            }
          }
        })

        .state('app.areas_edit', {
          url: '/areas/:id/edit',
          templateUrl: 'views/areas/edit.html',
          controller: 'AreasCtrl',
          controllerAs: 'areas',
          data: {
            title: 'areas.titulo',
            breadcrumb: 'areas.editar',
            permissions: {
              only: 'editarAreas',
              redirectTo: 'app.main'
            }
          }
        })

        .state('app.areas_show', {
          url: '/areas/:id',
          templateUrl: 'views/areas/show.html',
          controller: 'AreasCtrl',
          controllerAs: 'areas',
          data: {
            title: 'areas.titulo',
            breadcrumb: 'areas.mostrar',
            permissions: {
              only: 'visualizarAreas',
              redirectTo: 'app.main'
            }
          }
        })

        // Crud subareas.
        .state('app.subareas', {
          url: '/subareas',
          templateUrl: 'views/subareas/index.html',
          controller: 'SubAreasCtrl',
          controllerAs: 'subareas',
          data: {
            title: 'subareas.titulo',
            permissions: {
              only: 'listarSubareas',
              redirectTo: 'app.main'
            }
          }
        })

        .state('app.subareas_new', {
          url: '/subareas/new',
          templateUrl: 'views/subareas/new.html',
          controller: 'SubAreasCtrl',
          controllerAs: 'subareas',
          data: {
            title: 'subareas.titulo',
            breadcrumb: 'subareas.novo',
            permissions: {
              only: 'criarSubareas',
              redirectTo: 'app.main'
            }
          }
        })

        .state('app.subareas_edit', {
          url: '/subareas/:id/edit',
          templateUrl: 'views/subareas/edit.html',
          controller: 'SubAreasCtrl',
          controllerAs: 'subareas',
          data: {
            title: 'subareas.titulo',
            breadcrumb: 'subareas.editar',
            permissions: {
              only: 'editarSubareas',
              redirectTo: 'app.main'
            }
          }
        })

        .state('app.subareas_show', {
          url: '/subareas/:id',
          templateUrl: 'views/subareas/show.html',
          controller: 'SubAreasCtrl',
          controllerAs: 'subareas',
          data: {
            title: 'subareas.titulo',
            breadcrumb: 'subareas.mostrar',
            permissions: {
              only: 'visualizarSubareas',
              redirectTo: 'app.main'
            }
          }
        })

        // Crud controladores.
        // TODO: verificar se essa rota é utilizada
        .state('app.controladores', {
          url: '/controladores',
          templateUrl: 'views/controladores/index.html',
          controller: 'ControladoresCtrl',
          controllerAs: 'controladores',
          data: {
            title: 'controladores.titulo',
            permissions: {
              only: 'listarControladores',
              redirectTo: 'app.main'
            }
          }
        })


        .state('app.controladores_show', {
          url: '/controladores/:id',
          templateUrl: 'views/controladores/show.html',
          controller: 'ControladoresRevisaoCtrl',
          controllerAs: 'controladores',
          data: {
            title: 'controladores.titulo',
            permissions: {
              only: 'verControlador',
              redirectTo: 'app.main'
            }
          }
        })


        //Timeline
        // TODO: verificar se essa rota é utilizada
        .state('app.controladores_timeline', {
          url: '/controladores/:id/timeline',
          templateUrl: 'views/controladores/timeline.html',
          controller: 'ControladoresCtrl',
          controllerAs: 'controladores',
          data: {
            title: 'timeline.titulo'
          }
        })



        // Crud Fabricantes.
        .state('app.fabricantes', {
          url: '/fabricantes',
          templateUrl: 'views/fabricantes/index.html',
          controller: 'FabricantesCtrl',
          controllerAs: 'fabricantes',
          data: {
            title: 'fabricantes.titulo',
            permissions: {
              only: 'listarFabricantes',
              redirectTo: 'app.main'
            }
          }
        })

        .state('app.fabricantes_new', {
          url: '/fabricantes/new',
          templateUrl: 'views/fabricantes/new.html',
          controller: 'FabricantesCtrl',
          controllerAs: 'fabricantes',
          data: {
            title: 'fabricantes.titulo',
            breadcrumb: 'fabricantes.novo',
            permissions: {
              only: 'criarFabricantes',
              redirectTo: 'app.main'
            }
          }
        })

        .state('app.fabricantes_edit', {
          url: '/fabricantes/:id/edit',
          templateUrl: 'views/fabricantes/edit.html',
          controller: 'FabricantesCtrl',
          controllerAs: 'fabricantes',
          data: {
            title: 'fabricantes.titulo',
            breadcrumb: 'fabricantes.editar',
            permissions: {
              only: 'editarFabricantes',
              redirectTo: 'app.main'
            }
          }
        })

        .state('app.fabricantes_show', {
          url: '/fabricantes/:id',
          templateUrl: 'views/fabricantes/show.html',
          controller: 'FabricantesCtrl',
          controllerAs: 'fabricantes',
          data: {
            title: 'fabricantes.titulo',
            breadcrumb: 'fabricantes.mostrar',
            permissions: {
              only: 'visualizarFabricantes',
              redirectTo: 'app.main'
            }
          }
        })


        // Crud Modelos Contolador.
        .state('app.modelos', {
          url: '/modelos',
          templateUrl: 'views/modelos/index.html',
          controller: 'ModelosCtrl',
          controllerAs: 'modelos',
          data: {
            title: 'modelos.titulo',
            permissions: {
              only: 'listarModelos',
              redirectTo: 'app.main'
            }
          }
        })

        .state('app.modelos_new', {
          url: '/modelos/new',
          templateUrl: 'views/modelos/new.html',
          controller: 'ModelosCtrl',
          controllerAs: 'modelos',
          data: {
            title: 'modelos.titulo',
            breadcrumb: 'modelos.novo',
            permissions: {
              only: 'criarModelos',
              redirectTo: 'app.main'
            }
          }
        })

        .state('app.modelos_edit', {
          url: '/modelos/:id/edit',
          templateUrl: 'views/modelos/edit.html',
          controller: 'ModelosCtrl',
          controllerAs: 'modelos',
          data: {
            title: 'modelos.titulo',
            breadcrumb: 'modelos.editar',
            permissions: {
              only: 'editarModelos',
              redirectTo: 'app.main'
            }
          }
        })

        .state('app.modelos_show', {
          url: '/modelos/:id',
          templateUrl: 'views/modelos/show.html',
          controller: 'ModelosCtrl',
          controllerAs: 'modelos',
          data: {
            title: 'modelos.titulo',
            breadcrumb: 'modelos.mostrar',
            permissions: {
              only: 'visualizarModelos',
              redirectTo: 'app.main'
            }
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
            title: 'controladores.dadosBasicos.titulo',
            permissions: {
              only: 'criarControlador',
              redirectTo: 'app.main'
            }
          }
        })

        .state('app.wizard_controladores.aneis', {
          url: '/aneis/:id',
          templateUrl: 'views/controladores/wizard/aneis.html',
          controller: 'ControladoresAneisCtrl',
          data: {
            title: 'controladores.aneis.titulo',
            permissions: {
              only: 'criarControlador',
              redirectTo: 'app.main'
            }
          }
        })

        .state('app.wizard_controladores.configuracao_grupo', {
          url: '/configuracao-grupo/:id',
          templateUrl: 'views/controladores/wizard/configuracao-grupo.html',
          controller: 'ControladoresConfiguracaoGruposCtrl',
          data: {
            title: 'controladores.gruposSemaforicos.titulo',
            permissions: {
              only: 'criarControlador',
              redirectTo: 'app.main'
            }
          }
        })

        .state('app.wizard_controladores.verdes_conflitantes', {
          url: '/verdes-conflitantes/:id',
          templateUrl: 'views/controladores/wizard/verdes-conflitantes.html',
          controller: 'ControladoresVerdesConflitantesCtrl',
          data: {
            title: 'controladores.verdesConflitantes.titulo',
            permissions: {
              only: 'criarControlador',
              redirectTo: 'app.main'
            }
          }
        })

        .state('app.wizard_controladores.associacao', {
          url: '/associacao/:id',
          templateUrl: 'views/controladores/wizard/associacao.html',
          controller: 'ControladoresAssociacaoCtrl',
          data: {
            title: 'controladores.associacao.titulo',
            permissions: {
              only: 'criarControlador',
              redirectTo: 'app.main'
            }
          }
        })

        .state('app.wizard_controladores.transicoes_proibidas', {
          url: '/transicoes-proibidas/:id',
          templateUrl: 'views/controladores/wizard/transicoes-proibidas.html',
          controller: 'ControladoresTransicoesProibidasCtrl',
          data: {
            title: 'controladores.transicoesProibidas.titulo',
            permissions: {
              only: 'criarControlador',
              redirectTo: 'app.main'
            }
          }
        })

        .state('app.wizard_controladores.entre_verdes', {
          url: '/entre-verdes/:id',
          templateUrl: 'views/controladores/wizard/entre-verdes.html',
          controller: 'ControladoresEntreVerdesCtrl',
          data: {
            title: 'controladores.entreVerdes.titulo',
            permissions: {
              only: 'criarControlador',
              redirectTo: 'app.main'
            }
          }
        })

        .state('app.wizard_controladores.atraso_de_grupo', {
          url: '/atraso-de-grupo/:id',
          templateUrl: 'views/controladores/wizard/atraso-de-grupo.html',
          controller: 'ControladoresAtrasoDeGrupoCtrl',
          data: {
            title: 'controladores.atrasoDeGrupo.titulo',
            permissions: {
              only: 'criarControlador',
              redirectTo: 'app.main'
            }
          }
        })

        .state('app.wizard_controladores.associacao_detectores', {
          url: '/associacao-detectores/:id',
          controller: 'ControladoresAssociacaoDetectoresCtrl',
          templateUrl: 'views/controladores/wizard/associacao-detectores.html',
          data: {
            title: 'controladores.associacaoDetectores.titulo',
            permissions: {
              only: 'criarControlador',
              redirectTo: 'app.main'
            }
          }
        })

        .state('app.wizard_controladores.revisao', {
          url: '/revisao/:id',
          controller: 'ControladoresRevisaoCtrl',
          templateUrl: 'views/controladores/wizard/revisao.html',
          data: {
            title: 'controladores.revisao.titulo',
            permissions: {
              only: 'criarControlador',
              redirectTo: 'app.main'
            }
          }
        })

        .state('app.mapa_controladores', {
          url: '/mapa-controladores',
          templateUrl: 'views/controladores/mapa.html',
          controller: 'ControladoresMapaCtrl',
          data: {
            title: 'controladores.mapa',
            permissions: {
              only: 'verNoMapa',
              redirectTo: 'app.main'
            }
          },
          onExit: ['pahoProvider', function(pahoProvider) {
            pahoProvider.disconnect();
          }]
        })

        .state('app.planos', {
          url: '/planos/:id',
          templateUrl: 'views/planos/criacao-planos.html',
          controller: 'PlanosCtrl',
          data: {
            title: 'planos.titulo',
            somenteVisualizacao: true,
            permissions: {
              only: 'verPlanos',
              redirectTo: 'app.main'
            }
          }
        })

        .state('app.planos_edit', {
          url: '/planos/:id/edit',
          templateUrl: 'views/planos/criacao-planos.html',
          controller: 'PlanosCtrl',
          data: {
            title: 'planos.titulo',
            somenteVisualizacao: false,
            permissions: {
              only: 'criarPlanos',
              redirectTo: 'app.main'
            }
          }
        })

        .state('app.tabelas_horarias', {
          url: '/tabela_horarios/:id',
          templateUrl: 'views/tabela_horarios/configuracao.html',
          controller: 'TabelaHorariosCtrl',
          data: {
            title: 'tabelaHorarios.titulo',
            somenteVisualizacao: true,
            permissions: {
              only: 'verTabelaHoraria',
              redirectTo: 'app.main'
            }
          }
        })

        .state('app.tabelas_horarias_edit', {
          url: '/tabela_horarios/:id/edit',
          templateUrl: 'views/tabela_horarios/configuracao.html',
          controller: 'TabelaHorariosCtrl',
          data: {
            title: 'tabelaHorarios.titulo',
            somenteVisualizacao: false,
            permissions: {
              only: 'criarTabelaHoraria',
              redirectTo: 'app.main'
            }
          }
        })

        .state('app.tabelas_horarias_diff', {
          url: '/tabela_horarios/:id/diff/:versaoIdJson',
          templateUrl: 'views/tabela_horarios/diff.html',
          controller: 'TabelaHorariosDiffCtrl',
          data: {
            title: 'tabelaHorarios.titulo'
          }
        })

        // Crud perfis.
        .state('app.perfis', {
          url: '/perfis',
          templateUrl: 'views/perfis/index.html',
          controller: 'PerfisCtrl',
          controllerAs: 'perfis',
          data: {
            title: 'perfis.titulo',
            permissions: {
              only: 'listarPerfis',
              redirectTo: 'app.main'
            }
          }
        })

        .state('app.perfis_new', {
          url: '/perfis/new',
          templateUrl: 'views/perfis/new.html',
          controller: 'PerfisCtrl',
          controllerAs: 'perfis',
          data: {
            title: 'perfis.titulo',
            breadcrumb: 'perfis.novo',
            permissions: {
              only: 'criarPerfis',
              redirectTo: 'app.main'
            }
          }
        })

        .state('app.perfis_edit', {
          url: '/perfis/:id/edit',
          templateUrl: 'views/perfis/edit.html',
          controller: 'PerfisCtrl',
          controllerAs: 'perfis',
          data: {
            title: 'perfis.titulo',
            breadcrumb: 'perfis.editar',
            permissions: {
              only: 'editarPerfis',
              redirectTo: 'app.main'
            }
          }
        })

        .state('app.perfis_show', {
          url: '/perfis/:id',
          templateUrl: 'views/perfis/show.html',
          controller: 'PerfisCtrl',
          controllerAs: 'perfis',
          data: {
            title: 'perfis.titulo',
            breadcrumb: 'perfis.mostrar',
            permissions: {
              only: 'visualizarPerfis',
              redirectTo: 'app.main'
            }
          }
        })

        .state('app.perfis_permissoes', {
          url: '/perfis/:id/permissoes',
          templateUrl: 'views/perfis/permissoes.html',
          controller: 'PerfisCtrl',
          controllerAs: 'perfis',
          data: {
            title: 'perfis.titulo',
            breadcrumb: 'perfis.definir_permissoes',
            permissions: {
              only: 'editarPerfis',
              redirectTo: 'app.main'
            }
          }
        })

        // Crud permissoes.
        .state('app.permissoes', {
          url: '/permissoes',
          templateUrl: 'views/permissoes/index.html',
          controller: 'PermissoesCtrl',
          controllerAs: 'permissoes',
          data: {
            title: 'permissoes.titulo',
            permissions: {
              only: 'listarPermissoes',
              redirectTo: 'app.main'
            }
          }
        })

        .state('app.permissoes_new', {
          url: '/permissoes/new',
          templateUrl: 'views/permissoes/new.html',
          controller: 'PermissoesCtrl',
          controllerAs: 'permissoes',
          data: {
            title: 'permissoes.titulo',
            breadcrumb: 'permissoes.novo',
            permissions: {
              only: 'criarPermissoes',
              redirectTo: 'app.main'
            }
          }
        })

        .state('app.permissoes_edit', {
          url: '/permissoes/:id/edit',
          templateUrl: 'views/permissoes/edit.html',
          controller: 'PermissoesCtrl',
          controllerAs: 'permissoes',
          data: {
            title: 'permissoes.titulo',
            breadcrumb: 'permissoes.editar',
            permissions: {
              only: 'editarPermissoes',
              redirectTo: 'app.main'
            }
          }
        })

        .state('app.permissoes_show', {
          url: '/permissoes/:id',
          templateUrl: 'views/permissoes/show.html',
          controller: 'PermissoesCtrl',
          controllerAs: 'permissoes',
          data: {
            title: 'permissoes.titulo',
            breadcrumb: 'permissoes.mostrar',
            permissions: {
              only: 'visualizarPermissoes',
              redirectTo: 'app.main'
            }
          }
        })

        // Crud usuarios.
        .state('app.usuarios', {
          url: '/usuarios',
          templateUrl: 'views/usuarios/index.html',
          controller: 'UsuariosCtrl',
          controllerAs: 'usuarios',
          data: {
            title: 'usuarios.titulo',
            permissions: {
              only: 'listarUsuarios',
              redirectTo: 'app.main'
            }
          }
        })

        .state('app.usuarios_new', {
          url: '/usuarios/new',
          templateUrl: 'views/usuarios/new.html',
          controller: 'UsuariosCtrl',
          controllerAs: 'usuarios',
          data: {
            title: 'usuarios.titulo',
            breadcrumb: 'usuarios.novo',
            permissions: {
              only: 'criarUsuarios',
              redirectTo: 'app.main'
            }
          }
        })

        .state('app.usuarios_edit', {
          url: '/usuarios/:id/edit',
          templateUrl: 'views/usuarios/edit.html',
          controller: 'UsuariosCtrl',
          controllerAs: 'usuarios',
          data: {
            title: 'usuarios.titulo',
            breadcrumb: 'usuarios.editar',
            permissions: {
              only: function(transitionProperties) {
                var usuarioLogado = JSON.parse(localStorage.usuario);
                var usuarioId = transitionProperties.toParams.id;
                if (usuarioLogado.id === usuarioId) {
                  return true;
                } else {
                  return 'editarUsuarios';
                }
              },
              redirectTo: 'app.main'
            }
          }
        })

        .state('app.usuarios_show', {
          url: '/usuarios/:id',
          templateUrl: 'views/usuarios/show.html',
          controller: 'UsuariosCtrl',
          controllerAs: 'usuarios',
          data: {
            title: 'usuarios.titulo',
            breadcrumb: 'usuarios.mostrar',
            permissions: {
              only: function(transitionProperties) {
                var usuarioLogado = JSON.parse(localStorage.usuario);
                var usuarioId = transitionProperties.toParams.id;
                if (usuarioLogado.id === usuarioId) {
                  return true;
                } else {
                  return 'visualizarUsuarios';
                }
              },
              redirectTo: 'app.main'
            }
          }
        })

        .state('app.usuarios_access_log', {
          url: '/usuarios/:id/access_log',
          templateUrl: 'views/usuarios/access_log.html',
          controller: 'UsuariosCtrl',
          controllerAs: 'usuarios',
          data: {
            title: 'usuarios.access_log',
            breadcrumb: 'usuarios.access_log',
            permissions: {
              only: 'verLogAcessoUsuarios',
              redirectTo: 'app.main'
            }
          }
        })

        .state('app.alarmes_e_falhas', {
          url: '/usuarios/:id/alarmes-e-falhas',
          templateUrl: 'views/usuarios/alarmes-e-falhas.html',
          controller: 'AlarmesEFalhasCtrl',
          data: {
            title: 'alarmesEFalhas.titulo',
            breadcrumb: 'alarmesEFalhas.titulo',
            permissions: {
              only: 'verLogAcessoUsuarios',
              redirectTo: 'app.main'
            }
          }
        })

        // CRUD Agrupamentos
        .state('app.agrupamentos', {
          url: '/agrupamentos',
          templateUrl: 'views/agrupamentos/index.html',
          controller: 'AgrupamentosCtrl',
          controllerAs: 'agrupamentos',
          data: {
            title: 'agrupamentos.titulo',
            permissions: {
              only: 'listarAgrupamentos',
              redirectTo: 'app.main'
            }
          }
        })

        .state('app.agrupamentos_new', {
          url: '/agrupamentos/new',
          templateUrl: 'views/agrupamentos/new.html',
          controller: 'AgrupamentosCtrl',
          controllerAs: 'agrupamentos',
          data: {
            title: 'agrupamentos.titulo',
            breadcrumb: 'agrupamentos.novo',
            permissions: {
              only: 'criarAgrupamentos',
              redirectTo: 'app.main'
            }
          }
        })

        .state('app.agrupamentos_edit', {
          url: '/agrupamentos/:id/edit',
          templateUrl: 'views/agrupamentos/edit.html',
          controller: 'AgrupamentosCtrl',
          controllerAs: 'agrupamentos',
          data: {
            title: 'agrupamentos.titulo',
            breadcrumb: 'agrupamentos.editar',
            permissions: {
              only: 'editarAgrupamentos',
              redirectTo: 'app.main'
            }
          }
        })

        .state('app.agrupamentos_show', {
          url: '/agrupamentos/:id',
          templateUrl: 'views/agrupamentos/show.html',
          controller: 'AgrupamentosCtrl',
          controllerAs: 'agrupamentos',
          data: {
            title: 'agrupamentos.titulo',
            breadcrumb: 'agrupamentos.mostrar',
            permissions: {
              only: 'visualizarAgrupamentos',
              redirectTo: 'app.main'
            }
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
            permissions: {
              only: 'listarAuditorias',
              redirectTo: 'app.main'
            }
          }
        })

        .state('app.auditorias_show', {
          url: '/auditorias/:id',
          templateUrl: 'views/auditorias/show.html',
          controller: 'AuditoriasCtrl',
          controllerAs: 'auditorias',
          data: {
            title: 'auditorias.titulo',
            breadcrumb: 'auditorias.mostrar',
            permissions: {
              only: 'visualizarAuditorias',
              redirectTo: 'app.main'
            }
          }
        })

        // SIMULACAO
        .state('app.simulacao', {
          url: '/simulacao/:id',
          templateUrl: 'views/simulacao/index.html',
          controller: 'SimulacaoCtrl',
          controllerAs: 'simulacao',
          data: {
            title: 'simulacao.titulo',
            permissions: {
              only: 'simularControlador',
              redirectTo: 'app.main'
            }
          }
        })

        // IMPOR CONFIGURAÇÃO
        .state('app.impor_config', {
          url: '/impor_config/',
          templateUrl: 'views/impor_config/index.html',
          controller: 'ImporConfigCtrl',
          controllerAs: 'imporConfig',
          data: {
            title: 'imporConfig.titulo',
          }
        })

        // FAIXAS DE VALORES
        .state('app.faixas_de_valores_edit', {
          url: '/faixas_de_valores/edit',
          templateUrl: 'views/faixas_de_valores/edit.html',
          controller: 'FaixasDeValoresCtrl',
          controllerAs: 'faixasDeValores',
          data: {
            title: 'faixas_de_valores.titulo',
            breadcrumb: 'faixas_de_valores.editar',
            permissions: {
              only: 'visualizarFaixasDeValores',
              redirectTo: 'app.main'
            }
          }
        })

        .state('app.relatorios_controladores_status', {
          url: '/relatorios/controladores_status',
          templateUrl: 'views/relatorios/status.html',
          controller: 'RelatoriosStatusCtrl',
          controllerAs: 'relatorios',
          data: {
            title: 'relatorios.controladoresStatus',
            permissions: {
              only: 'gerarRelatorioControladoresStatus',
              redirectTo: 'app.main'
            }
          }
        })

        .state('app.relatorios_planos', {
          url: '/relatorios/planos',
          templateUrl: 'views/relatorios/planos.html',
          controller: 'RelatoriosPlanosCtrl',
          controllerAs: 'relatorios',
          data: {
            title: 'relatorios.planos',
            permissions: {
              only: 'gerarRelatorioPlanos',
              redirectTo: 'app.main'
            }
          }
        })

        .state('app.relatorios_controladores_falhas', {
          url: '/relatorios/controladores_falhas',
          templateUrl: 'views/relatorios/falhas.html',
          controller: 'RelatoriosFalhasCtrl',
          controllerAs: 'relatorios',
          data: {
            title: 'relatorios.controladoresFalhas',
            permissions: {
              only: 'gerarRelatorioControladoresFalhas',
              redirectTo: 'app.main'
            }
          }
        })

        .state('app.relatorios_tabela_horaria', {
          url: '/relatorios/tabela_horaria',
          templateUrl: 'views/relatorios/tabelaHoraria.html',
          controller: 'RelatorioTabelaHorariaCtrl',
          controllerAs: 'relatorios',
          data: {
            title: 'relatorios.tabelaHoraria',
            permissions: {
              only: 'gerarRelatorioTabelaHoraria',
              redirectTo: 'app.main'
            }
          }
        })
      ;

      // Prevent router from automatic state resolving
      $urlRouterProvider.deferIntercept();
    }])

  .run(['$rootScope', '$state', '$timeout', 'TELAS_SEM_LOGIN',
    function($rootScope, $state, $timeout, TELAS_SEM_LOGIN) {

      $rootScope.$state = $state;

      $rootScope.isAuthenticated = function() {
        return !!localStorage.token;
      };

      var needLogin = function(stateName) {
        return !$rootScope.isAuthenticated() && TELAS_SEM_LOGIN.indexOf(stateName) < 0;
      };

      $rootScope.$on('$stateChangeStart', function(ev, toState) {
        $timeout(function() {
          if (needLogin(toState.name)) {
            $state.go('login');
          }
        });
      });

    }]);
