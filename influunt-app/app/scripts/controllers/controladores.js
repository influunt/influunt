'use strict';

/**
 * @ngdoc function
 * @name influuntApp.controller:ControladoresCtrl
 * @description
 * # ControladoresCtrl
 * Controller of the influuntApp
 */
angular.module('influuntApp')
  .controller('ControladoresCtrl', ['$controller', '$scope', '$state','Restangular', '$q', 'APP_ROOT',
    function ($controller, $scope, $state, Restangular, $q, APP_ROOT) {

      // Herda todo o comportamento do crud basico.
      $controller('CrudCtrl', {$scope: $scope});
      $scope.inicializaNovoCrud('controladores');
      $scope.hideRemoveCoordenada = true;

      // Seta URL para salvar imagens
      $scope.dados = {
        imagensUrl: APP_ROOT + '/imagens'
      };

      /**
       * Inicializa os dados da tela de index e os objetos requeridos para o filtro.
       */
      $scope.inicializaIndex = function(){
        $scope.filtros = {};
        $scope.filtroLateral = {};
        $scope.index();
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
       * Carrega os dados de fabricas e cidades, que não estão diretamente relacionados ao contolador.
       */
      var getHelpersControlador = function() {
        Restangular.one('helpers', 'controlador').get().then(function(res) {
          $scope.data = res;
          $scope.helpers = {};

          if ($scope.objeto.area) {
            $scope.helpers.cidade = $scope.objeto.area.cidade;
          } else {
            $scope.helpers.cidade = $scope.data.cidades[0];
          }

          if ($scope.objeto.modelo) {
            $scope.helpers.fornecedor = $scope.objeto.modelo.fabricante;
          }
        });
      };

      var loadWizardData = function(obj) {
        $scope.objeto = obj;
        getHelpersControlador();
        $scope.validacoes = {
          alerts: []
        };
      };

      /**
       * Função compartilhada por todos os passos do index. Deve carregar os dados
       * do controlador, caso este tenha sido definido.
       *
       * @return     {<type>}  { description_of_the_return_value }
       */
      $scope.inicializaWizard = function() {
        var defer = $q.defer();

        var id = $state.params.id;
        if (id) {
          Restangular.one('controladores', id).get().then(function(res) {
            loadWizardData(res);
            defer.resolve(res);
          });
        } else {
          loadWizardData({});
          defer.resolve({});
        }

        return defer.promise;
      };

      $scope.inicializaAneis = function() {
        return $scope.inicializaWizard().then(function() {
          $scope.currentAnelId = 0;
          $scope.criaAneis($scope.objeto);
          $scope.aneis = _.orderBy($scope.objeto.aneis, ['posicao'], ['asc']);
          $scope.currentAnel = $scope.objeto.aneis[$scope.currentAnelId];
        });
      };

      $scope.inicializaAssociacao = function() {
        return $scope.inicializaWizard().then(function() {
          $scope.aneis = _.filter($scope.objeto.aneis, {ativo: true});
          _.each($scope.aneis, function(anel) {
            anel.gruposSemaforicos = _.orderBy(anel.gruposSemaforicos, ['posicao'], ['asc']);
            _.each(anel.gruposSemaforicos, function(grupo) {
              grupo.label = 'G' + (grupo.posicao);
              grupo.ativo = false;

              // Cria o objeto helper para marcar os grupos ativos em cada estagio da tela.
              grupo.estagiosRelacionados = {};
              grupo.estagiosAtivados = {};
              grupo.estagioGrupoSemaforicos.forEach(function(estagioGrupo) {
                grupo.estagiosRelacionados[estagioGrupo.estagio.id] = true;
                grupo.estagiosAtivados[estagioGrupo.estagio.id] = estagioGrupo.ativo;
              });
            });

            // Inicializa o tempoMaximoPermanenciaAtivo true para os casos onde este
            // já está preenchido.
            _.each(anel.estagios, function(estagio) {
              estagio.tempoMaximoPermanenciaAtivo = !!estagio.tempoMaximoPermanencia;
            });

          });

          $scope.aneis = _.orderBy($scope.aneis, ['posicao'], ['asc']);
          $scope.selecionaAnel(0);
          $scope.selecionaEstagio(0);
        });
      };

      $scope.inicializaVerdesConflitantes = function() {
        return $scope.inicializaWizard().then(function() {
          $scope.grupoIds = _.chain($scope.objeto.gruposSemaforicos).orderBy(['posicao'], ['asc']).map('id').value();

          var totalGrupos = $scope.objeto.modelo.configuracao.limiteGrupoSemaforico;
          $scope.grupos = _.times(totalGrupos, function(i) {return 'G' + (i+1);});

          $scope.estagios = _.chain($scope.objeto.aneis).map('estagios').flatten().value();

          var aneis = _.filter($scope.objeto.aneis, {ativo: true});
          var somador = 0;
          $scope.intervalosAneis = aneis.map(function(anel) {
            somador += anel.gruposSemaforicos.length;
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

      $scope.selecionaAnel = function(index) {
        $scope.currentAnelId = index;
        $scope.currentAnel = $scope.aneis[$scope.currentAnelId];

        if (angular.isDefined($scope.currentEstagioId)) {
          $scope.selecionaEstagio($scope.currentEstagioId);
        }
      };

      $scope.selecionaEstagio = function(index) {
        $scope.currentEstagioId = index;
        $scope.currentEstagio = $scope.currentAnel.estagios[index];
        $scope.atualizaGruposSemaforicosSelecionados();
      };

      $scope.closeAlert = function() {
        $scope.validacoes.alerts = [];
      };

      /**
       * Limpa o tempo máximo de permanência do estágio caso o usuário uncheck o
       * checkbox de tempo máximo de permanência.
       *
       * @param      {<type>}  estagio  The estagio
       */
      $scope.toggleTempoPermanencia = function(estagio) {
        estagio.tempoMaximoPermanencia = null;
      };

      $scope.toggleVerdeConflitante = function(x, y, disabled) {
        if (disabled) {
          return false;
        }

        var grupo = _.find($scope.objeto.gruposSemaforicos, {id: $scope.grupoIds[x]});
        grupo.verdesConflitantes = grupo.verdesConflitantes || [];

        if ($scope.verdesConflitantes[x][y]) {
          var index = _.findIndex(grupo.verdesConflitantes, {id: $scope.grupoIds[y]});
          grupo.verdesConflitantes.splice(index, 1);
        } else {
          grupo.verdesConflitantes.push({id: $scope.grupoIds[y]});
        }

        $scope.verdesConflitantes[x][y] = !$scope.verdesConflitantes[x][y];
      };

      $scope.toggleEstagioAtivado = function(grupo, estagio) {
        var estagioId = estagio.id;
        estagio = _.find(grupo.estagioGrupoSemaforicos, {estagio: {id: estagioId}});

        if (!!estagio) {
          estagio.ativo = !estagio.ativo;
          grupo.estagiosAtivados[estagio.id] = estagio.ativo;
          $scope.$apply();
        }
      };

      $scope.associaEstagiosGrupoSemaforico = function(grupo, estagio) {
        var obj = {
          grupoSemaforico: { id: grupo.id },
          estagio: estagio
        };

        var index = _.findIndex(grupo.estagioGrupoSemaforicos, obj);
        if (index >= 0) {
          grupo.estagioGrupoSemaforicos.splice(index, 1);
        } else {
          grupo.estagioGrupoSemaforicos.push(obj);
        }

        $scope.toggleEstagioAtivado(grupo, estagio);
        $scope.atualizaGruposSemaforicosSelecionados();
      };

      $scope.atualizaGruposSemaforicosSelecionados = function() {
        var estagioId = $scope.currentEstagio.id;
        $scope.gruposSelecionados = $scope.currentAnel.gruposSemaforicos.filter(function(grupo) {
          return !!_.filter(grupo.estagioGrupoSemaforicos, {estagio: {id: estagioId}}).length;
        });
      };

      $scope.mensagemValidacaoForm = function(res) {
        var messages = res && res.data && res.data.map(function(a) {
          return {
            // msg: 'validacoesAPI.' + _.lowerCase(a.root) + '.' + _.camelCase(a.message),
            msg: a.message,
            params: {
              CAMPO: a.path
            }
          };
        });

        $scope.validacoes.alerts = messages;
      };

      $scope.criaAneis = function(controlador) {
        controlador.aneis = _.orderBy(controlador.aneis, ['posicao'], ['asc']).map(function(anel, key) {
          anel.idAnel = controlador.CLC + '-' + (key + 1);
          anel.posicao = anel.posicao || (key + 1);
          anel.valid = {
            form: true
          };

          return anel;
        });

        // Garantia de que o primeiro anel será sempre null.
        controlador.aneis[0].ativo = true;
        return controlador.aneis;
      };

      $scope.associaImagemAoEstagio = function(upload, imagem) {
        var anel = $scope.currentAnel;
        if (!('estagios' in anel)) {
          anel.estagios = [];
        }

        anel.estagios.push({ imagem: { id: imagem.id } });
      };

      $scope.relacionaImagemAoEstagio = function(estagio, upload, imagem) {
        estagio.imagem = imagem;
        $scope.$apply();
      };

      /**
       * Desativa todos os aneis após o anel corrente, caso o anel atual seja
       * desativado.
       *
       * @param      {<type>}  currentAnel  The current anel
       */
      $scope.desativaProximosAneis = function(currentAnel) {
        $scope.aneis.forEach(function(anel) {
          if (anel.posicao > currentAnel.posicao) {
            anel.ativo = false;
          }
        });
      };
    }]);
