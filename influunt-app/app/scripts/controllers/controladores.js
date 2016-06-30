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

      // funcoes privadas.
      var buildIntervaloAneis, buildMatrizVerdesConflitantes;

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
          $scope.currentAnelIndex = 0;
          $scope.criaAneis($scope.objeto);
          $scope.aneis = _.orderBy($scope.objeto.aneis, ['posicao'], ['asc']);
          $scope.currentAnel = $scope.objeto.aneis[$scope.currentAnelIndex];
        });
      };

      $scope.inicializaAssociacao = function() {
        return $scope.inicializaWizard().then(function() {
          $scope.objeto.aneis = _.orderBy($scope.objeto.aneis, ['posicao'], ['asc']);
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

          buildIntervaloAneis();
          buildMatrizVerdesConflitantes();
        });
      };

      $scope.submitForm = function(form, stepResource, nextStep) {
        if (form.$valid) {
          Restangular
            .all('controladores')
            .all(stepResource)
            .post($scope.objeto)
            .then(function(res) {
              $scope.objeto = res;

              $scope.errors = {};
              $state.go(nextStep, {id: $scope.objeto.id});
            })
            .catch(function(res) {
              if (res.status === 422) {
                $scope.buildValidationMessages(res.data);
              } else {
                console.error(res);
              }
            });
        }
      };

      $scope.selecionaAnel = function(index) {
        $scope.currentAnelIndex = index;
        $scope.currentAnel = $scope.aneis[$scope.currentAnelIndex];

        if (angular.isDefined($scope.currentEstagioId)) {
          $scope.selecionaEstagio($scope.currentEstagioId);
        }
      };

      $scope.selecionaEstagio = function(index) {
        $scope.currentEstagioId = index;
        $scope.currentEstagio = $scope.currentAnel.estagios[index];
        $scope.atualizaGruposSemaforicosSelecionados();
      };

      /**
       * Deleta a lista de mensagens de validações globais exibidas para determinado
       * anel.
       *
       * @param      {<type>}  index       The index
       * @param      {<type>}  shownGroup  The shown group
       */
      $scope.closeAlertAnel = function(index, shownGroup) {
        shownGroup = shownGroup || 'general';
        if ($scope.errors && $scope.errors.aneis[index]) {
          delete $scope.errors.aneis[index][shownGroup];
        }
      };

      /**
       * Limpa o tempo máximo de permanência do estágio caso o usuário uncheck o
       * checkbox de tempo máximo de permanência.
       *
       * @param      {<type>}  estagio  The estagio
       */
      $scope.limpaTempoPermanencia = function(estagio) {
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
        var estagioGrupoSemaforico = _.find(grupo.estagioGrupoSemaforicos, {estagio: {id: estagioId}});

        if (!!estagioGrupoSemaforico) {
          estagioGrupoSemaforico.ativo = !estagioGrupoSemaforico.ativo;
          grupo.estagiosAtivados[estagioId] = estagioGrupoSemaforico.ativo;
          $scope.$apply();
        }
      };

      $scope.associaEstagiosGrupoSemaforico = function(grupo, estagio) {
        var obj = {
          grupoSemaforico: { id: grupo.id },
          estagio: estagio
        };

        var filter = {grupoSemaforico: {id: obj.grupoSemaforico.id}, estagio: {id: obj.estagio.id}};
        var index = _.findIndex(grupo.estagioGrupoSemaforicos, filter);
        if (index >= 0) {
          grupo.estagioGrupoSemaforicos.splice(index, 1);
        } else {
          grupo.estagioGrupoSemaforicos.push(obj);
        }

        $scope.toggleEstagioAtivado(grupo, estagio);
        $scope.atualizaGruposSemaforicosSelecionados();
      };

      $scope.atualizaGruposSemaforicosSelecionados = function() {
        if (!$scope.currentEstagio) {
          return false;
        }

        var estagioId = $scope.currentEstagio.id;
        $scope.gruposSelecionados = $scope.currentAnel.gruposSemaforicos.filter(function(grupo) {
          return !!_.filter(grupo.estagioGrupoSemaforicos, {estagio: {id: estagioId}}).length;
        });
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

      $scope.buildValidationMessages = function(errors) {
        $scope.validations = {};
        if (angular.isArray(errors)) {
          errors.forEach(function(err) {
            var path = err.path.match(/\d+\]$/) ? err.path + '.general' : err.path;
            if (!path) {
              path = 'general';
            }

            $scope.validations[path] = $scope.validations[path] || [];
            $scope.validations[path].push(err.message);
          });

          $scope.errors = {};
          _.each($scope.validations, function(val, key) {
            _.update($scope.errors, key, _.constant(val));
          });

          // Específicos para as validações em escopo de anel.
          _.each($scope.errors.aneis, function(anel) {
            if (anel) {
              anel.all = _.chain(anel).values().flatten().uniq().value();
            }
          });

          console.log($scope.errors);
        }
      };

      /**
       * Deve informar que determinado anel possui erros caso haja uma lista de
       * erros para determinado anel.
       *
       * @param      {<type>}  indice  The indice
       * @return     {<type>}  { description_of_the_return_value }
       */
      $scope.anelTemErro = function(indice) {
        var errors = _.get($scope.errors, 'aneis[' + indice + ']');
        return _.isObject(errors) && Object.keys(errors).length > 0;
      };

      $scope.estagioTemErro = function(indiceAnel, indiceEstagio) {
        var errors = _.get($scope.errors, 'aneis[' + indiceAnel + '].estagios[' + indiceEstagio + ']');
        return _.isObject(errors) && Object.keys(errors).length > 0;
      }

      /**
       * Retorna um array com os intervalos de grupos para cada anel. Utilizado
       * somente para desenhar os limites dos aneis pelos grupos.
       */
      buildIntervaloAneis = function() {
        var aneis = _.filter($scope.objeto.aneis, {ativo: true});
        var somador = 0;
        $scope.intervalosAneis = aneis.map(function(anel) {
          somador += anel.gruposSemaforicos.length;
          return somador;
        });
        $scope.intervalosAneis.unshift(0);
        $scope.gruposUtilizados = $scope.intervalosAneis[$scope.intervalosAneis.length - 1];
      };

      /**
       * Constroi uma matriz quadrada para representação gráfica da tabela de
       * verdes conflitantes.
       */
      buildMatrizVerdesConflitantes = function() {
        $scope.verdesConflitantes = [];
        for (var i = 0; i < $scope.grupos.length; i++) {
          for (var j = 0; j < $scope.grupos.length; j++) {
            $scope.verdesConflitantes[i] = $scope.verdesConflitantes[i] || [];
            $scope.verdesConflitantes[i][j] = false;
          }
        }
      };

    }]);
