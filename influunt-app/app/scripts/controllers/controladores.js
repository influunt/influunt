'use strict';

/**
 * @ngdoc function
 * @name influuntApp.controller:ControladoresCtrl
 * @description
 * # ControladoresCtrl
 * Controller of the influuntApp
 */
angular.module('influuntApp')
  .controller('ControladoresCtrl', ['$controller', '$scope', '$state','Restangular', 'validacoesAneis', '$q', 'APP_ROOT',
    function ($controller, $scope, $state, Restangular, validacoesAneis, $q, APP_ROOT) {

      // Herda todo o comportamento do crud basico.
      $controller('CrudCtrl', {$scope: $scope});
      $scope.inicializaNovoCrud('controladores');
      $scope.hideRemoveCoordenada = true;

      // Seta URL para salvar imagens
      $scope.dados = {
        imagensUrl: APP_ROOT + "/imagens"
      };

      /**
       * Filtra controladores baseado nos checkboxes
       * nos filtros à esquerda.
       */
      $scope.filtrarControlador = function(controlador) {
        if ($scope.filtroLateral[controlador.id]) {
          return true;
        }

        for (var controlador_id in $scope.filtroLateral) {
          if ($scope.filtroLateral[controlador_id]) {
            return false;
          }
        }
        return true;
      };

      /**
       * Carrega as listas de dependencias dos controladores. Atua na tela de crud.
       */
      $scope.beforeShow = function() {
        Restangular.all('areas').getList().then(function(res) {
          $scope.areas = res;
        });
      };

      /**
       * Inicializa o objeto de coordenadas do controlador, caso este ainda não
       * tenha sido definido. Atua na tela de crud.
       */
      $scope.afterShow = function() {
        var coordenadaDefault = {
          latitude: null,
          longitude: null
        };

        $scope.objeto.coordenada = $scope.objeto.coordenada || coordenadaDefault;
        $scope.coordenada = $scope.objeto.coordenada;
      };

      var getHelpersControlador = function() {
        Restangular.all('helpers').all('controlador').customGET().then(function(res) {
          $scope.data = res;
          $scope.helpers = {cidade: $scope.data.cidades[0]};
          if (!$scope.objeto.area) {
            $scope.objeto = {area: $scope.helpers.cidade.areas[0]};
          }
        });
      };

      var loadWizardData = function(obj) {
        getHelpersControlador();
        $scope.objeto = obj;
        $scope.validacoes = {
          alerts: []
        };
        $scope.helpers = {};
      };

      $scope.inicializaWizard = function() {
        var defer = $q.defer();

        var id = $state.params.id;
        if (id) {
          Restangular.one('controladores', id).customGET().then(function(res) {
            loadWizardData(res);
            defer.resolve(res);
          });
        } else {
          loadWizardData({});
          defer.resolve({});
        }

        return defer.promise;
      };

      $scope.inicializaDadosBasicos = function() {
        return $scope.inicializaWizard();
      };

      $scope.inicializaAneis = function() {
        return $scope.inicializaWizard().then(function() {
          $scope.currentAnelId = 0;
          $scope.criaAneis($scope.objeto);
          $scope.aneis = $scope.objeto.aneis;
          $scope.currentAnel = $scope.objeto.aneis[$scope.currentAnelId];
        });
      };

      $scope.inicializaAssociacao = function() {
        return $scope.inicializaWizard().then(function() {
          $scope.aneis = _.filter($scope.objeto.aneis, {ativo: true});
          console.log($scope.aneis);
          _.each($scope.aneis, function(anel) {
            _.each(anel.gruposSemaforicos, function(grupo, index) {
              grupo.label = 'G' + (index+1);
              grupo.ativo = false;

              // Cria o objeto helper para marcar os grupos ativos em cada estagio da tela.
              grupo.estagiosRelacionados = {};
              grupo.estagiosAtivados = {};
              grupo.estagioGrupoSemaforicos.forEach(function(estagioGrupo) {
                grupo.estagiosRelacionados[estagioGrupo.estagio.id] = true;
                grupo.estagiosAtivados[estagioGrupo.estagio.id] = estagioGrupo.ativo;
              });
            });
          });

          $scope.aneis = _.orderBy($scope.aneis, ['posicao'], ['asc']);
          $scope.selecionaAnel(0);
          $scope.selecionaMovimento(0);
        });
      };

      $scope.inicializaVerdesConflitantes = function() {
        return $scope.inicializaWizard().then(function() {
          $scope.grupos = _.times(16, function(i) {return 'G' + (i+1);});

          var aneis = _.filter($scope.objeto.aneis, {ativo: true});
          var somador = 0;
          $scope.intervalosAneis = aneis.map(function(anel) {
            somador += anel.grupos_semaforicos.length;
            return somador;
          });
          $scope.intervalosAneis.unshift(0);
          $scope.gruposUtilizados = $scope.intervalosAneis[$scope.intervalosAneis.length - 1];

          $scope.verdesConflitantes = [];
          for (var i = 0; i < $scope.grupos.length; i++) {
            for (var j = 0; j < $scope.grupos.length; j++) {
              $scope.verdesConflitantes[i] = $scope.verdesConflitantes[i] || [];
              $scope.verdesConflitantes[i][j] = false;
            }
          }
        });
      };

      $scope.submitForm = function(form, stepResource, nextStep) {
        $scope.submited = true;
        if (form.$valid) {
          Restangular
            .all('controladores')
            .all(stepResource)
            .post($scope.objeto)
            .then(function(res) {
              $scope.objeto = res;
              $scope.submited = false;

              $state.go(nextStep, {id: $scope.objeto.id});
            })
            .catch(function(res) {
              $scope.mensagemValidacaoForm(res);
            });
        }
      };

      $scope.submitFormAneis = function(nextStep) {
        var stepResource = 'aneis';
        if (validacoesAneis.valida($scope.objeto.aneis, $scope.objeto)) {
          $scope.submitForm({$valid: true}, stepResource, nextStep);
        } else {
          $scope.validacoes.alerts = validacoesAneis.retornaMensagensValidacao($scope.objeto.aneis);
        }
      };

      $scope.selecionaAnel = function(index) {
        $scope.currentAnelId = index;
        $scope.currentAnel = $scope.aneis[$scope.currentAnelId];

        if (angular.isDefined($scope.currentMovimentoId)) {
          console.log($scope.currentMovimentoId);
          $scope.selecionaMovimento($scope.currentMovimentoId);
        }
      };

      $scope.selecionaMovimento = function(index) {
        $scope.currentMovimentoId = index;
        $scope.currentMovimento = $scope.currentAnel.movimentos[index];
        $scope.atualizaGruposSemaforicosSelecionados();
      };

      $scope.closeAlert = function() {
        $scope.validacoes.alerts = [];
      };

      $scope.inicializa_index = function(){
        $scope.filtros = {};
        $scope.filtroLateral = {};
        $scope.index();
      };

      $scope.toggleVerdeConflitante = function(x, y, disabled) {
        if (disabled) {
          return false;
        }

        $scope.verdesConflitantes[x][y] = !$scope.verdesConflitantes[x][y];
      };

      $scope.toggleEstagioAtivado = function(grupo, movimento) {
        var estagioId = movimento.estagio.id;
        var estagio = _.find(grupo.estagioGrupoSemaforicos, {estagio: {id: estagioId}});

        if (!!estagio) {
          estagio.ativo = !estagio.ativo;
          grupo.estagiosAtivados[movimento.estagio.id] = estagio.ativo;
          $scope.$apply();
        }
      };

      $scope.associaEstagiosGrupoSemaforico = function(grupo, movimento) {
        var obj = {
          grupoSemaforico: grupo.id,
          estagio: movimento.estagio
        };

        var index = _.findIndex(grupo.estagioGrupoSemaforicos, obj);
        if (index >= 0) {
          grupo.estagioGrupoSemaforicos.splice(index, 1);
        } else {
          grupo.estagioGrupoSemaforicos.push(obj);
        }

        $scope.toggleEstagioAtivado(grupo, movimento);
        $scope.atualizaGruposSemaforicosSelecionados();
      };

      $scope.atualizaGruposSemaforicosSelecionados = function() {
        var estagioId = $scope.currentMovimento.estagio.id;
        $scope.gruposSelecionados = $scope.currentAnel.gruposSemaforicos.filter(function(grupo) {
          return !!_.filter(grupo.estagioGrupoSemaforicos, {estagio: {id: estagioId}}).length;
        });
      };

      $scope.mensagemValidacaoForm = function(res) {
        var messages = res.data.map(function(a) {
          return {
            msg: 'validacoesAPI.' + _.lowerCase(a.root) + '.' + _.camelCase(a.message),
            params: {
              CAMPO: a.path
            }
          };
        });

        $scope.validacoes.alerts = messages;
      };

      $scope.criaAneis = function(controlador) {
        if (controlador.aneis) {
          var idControlador = controlador.idControlador;
          controlador.aneis = _.times(controlador.modelo.configuracao.limiteAnel)
            .map(function(value, key) {
              return {
                ativo: key === 0,
                id_anel: idControlador + '-' + (key + 1),
                posicao: key + 1,
                quantidadeGrupoPedestre: null,
                quantidadeGrupoVeicular: null,
                quantidadeDetectorPedestre: null,
                descricao: null,
                numero_smee: null,
                latitude: null,
                longitude: null,
                valid: {
                  form: true,
                  required: {}
                }
              };
            });
        }

        return controlador.aneis;
      };

      $scope.associaImagemAoMovimento = function(upload, imagem) {
        var anel = $scope.currentAnel;
        if (!('movimentos' in anel)) {
          anel.movimentos = [];
        }

        anel.movimentos.push({ imagem: { id: imagem.id } });
      };

      $scope.relacionaImagemAoEstagio = function(movimento, upload, imagem) {
        movimento.estagio.imagem = imagem;
        $scope.$apply();
        console.log($scope.aneis);
      };

    }]);
