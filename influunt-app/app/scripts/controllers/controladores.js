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

      /**
       * Pré-condições para acesso à tela de aneis: Somente será possível acessar esta
       * tela se o objeto já possuir aneis (os aneis são gerados pela API assim que o
       * usuário informar os dados básicos).
       *
       * @return     {boolean}  { description_of_the_return_value }
       */
      $scope.assertAneis = function() {
        var condition = ($scope.objeto.aneis && $scope.objeto.aneis.length > 0);
        if (!condition) {
          $state.go('app.wizard_controladores.dados_basicos', {id: $scope.objeto.id});
          return false;
        }

        return true;
      };

      /**
       * Pré-condições para acesso à tela de associações: Somente será possível acessar esta
       * tela se o objeto possuir estágios. Os estágios são informados no passo anterior, o
       * passo de aneis.
       *
       * @return     {boolean}  { description_of_the_return_value }
       */
      $scope.assertAssociacoes = function() {
        var condition = $scope.objeto.aneis && $scope.objeto.aneis.length;
        condition = condition && _.chain($scope.objeto.aneis).map('estagios').flatten().compact().value().length > 0;
        if (!condition) {
          $state.go('app.wizard_controladores.aneis', {id: $scope.objeto.id});
          return false;
        }

        return true;
      };

      /**
       * Pré-condições para acesso à tela de verdes conflitantes: Somente será possível acessar
       * esta tela se o objeto possuir grupos semafóricos relacionados. Isto é feito no passo anterior,
       * na tela de associações.
       *
       * @return     {boolean}  { description_of_the_return_value }
       */
      $scope.assertVerdesConflitantes = function() {
        var condition = ($scope.objeto.gruposSemaforicos && $scope.objeto.gruposSemaforicos.length > 0);
        if (!condition) {
          $state.go('app.wizard_controladores.associacao', {id: $scope.objeto.id});
          return false;
        }

        return true;
      };

      $scope.assertEstagiosProibidos = function() {
        console.warn('---------------> ASSERT ESTAGIOS PROIBIDOS AINDA NÃO IMPLEMENTADO!!!');
        return true;
      };

      $scope.inicializaAneis = function() {
        return $scope.inicializaWizard().then(function() {
          if ($scope.assertAneis()) {
            $scope.currentAnelIndex = 0;
            $scope.criaAneis($scope.objeto);
            $scope.aneis = _.orderBy($scope.objeto.aneis, ['posicao'], ['asc']);
            $scope.currentAnel = $scope.objeto.aneis[$scope.currentAnelIndex];
          }
        });
      };

      $scope.inicializaAssociacao = function() {
        return $scope.inicializaWizard().then(function() {
          if ($scope.assertAssociacoes()) {
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
          }
        });
      };

      $scope.inicializaVerdesConflitantes = function() {
        return $scope.inicializaWizard().then(function() {
          if ($scope.assertVerdesConflitantes()) {
            $scope.objeto.aneis = _.orderBy($scope.objeto.aneis, ['posicao'], ['asc']);

            $scope.objeto.aneis.forEach(function(anel) {
              anel.gruposSemaforicos.forEach(function(gs) {
                gs.anel = {
                  id: anel.id
                };
              });
            });

            $scope.grupoIds = _.chain($scope.objeto.gruposSemaforicos).orderBy(['posicao'], ['asc']).map('id').value();
            var totalGrupos = $scope.objeto.modelo.configuracao.limiteGrupoSemaforico;
            $scope.grupos = _.times(totalGrupos, function(i) {return 'G' + (i+1);});

            buildIntervaloAneis();
            buildMatrizVerdesConflitantes();

            return $scope.objeto.gruposSemaforicos && $scope.objeto.gruposSemaforicos.forEach(function(gs) {
              return gs.verdesConflitantes && gs.verdesConflitantes.forEach(function(vc) {
                if (!$scope.verdesConflitantes[gs.posicao - 1][vc.posicao - 1]) {
                  $scope.toggleVerdeConflitante(gs.posicao - 1, vc.posicao - 1);
                }
              });
            });
          }
        });
      };

      /**
       * Inicializa a tela de estagios proibidos: Carrega os dados necessários, ordena os aneis e estágios a partir
       * das posições.
       *
       * @return     {<type>}  { description_of_the_return_value }
       */
      $scope.inicializaEstagiosProibidos = function() {
        return $scope.inicializaWizard().then(function() {
          if ($scope.assertEstagiosProibidos()) {
            $scope.objeto.aneis = _.orderBy($scope.objeto.aneis, ['posicao'], ['asc']);
            $scope.aneis = _.filter($scope.objeto.aneis, {ativo: true});
            $scope.aneis.forEach(function(anel) {
              anel.transicoesProibidas =  {};
              anel.estagios.forEach(function(estagio, index) {
                estagio.posicao = index + 1;
              });
            });

            $scope.selecionaAnel(0);
            $scope.selecionaEstagio(0);
          }
        });
      };

      var ativarTransicaoProibida = function(estagio1, estagio2) {
        var transicaoProibida = {
          origem: {id: estagio1.id},
          destino: {id: estagio2.id}
        };

        estagio1.origemDeTransicoesProibidas = estagio1.origemDeTransicoesProibidas || [];
        estagio1.origemDeTransicoesProibidas.push(transicaoProibida);

        estagio2.destinoDeTransicoesProibidas = estagio2.destinoDeTransicoesProibidas || [];
        estagio2.destinoDeTransicoesProibidas.push(transicaoProibida);

        var transicao = 'E' + estagio1.posicao + '-E' + estagio2.posicao
        $scope.currentAnel.transicoesProibidas[transicao] = {origem: estagio1, destino: estagio2};
      };

      var desativarTransicaoProibida = function(estagio1, estagio2) {
        var transicao = 'E' + estagio1.posicao + '-E' + estagio2.posicao
        delete $scope.currentAnel.transicoesProibidas[transicao];
        var idx1 = _.findIndex(estagio1.origemDeTransicoesProibidas, {destino: {id: estagio2.id}});
        var idx2 = _.findIndex(estagio2.destinoDeTransicoesProibidas, {origem: {id: estagio1.id}});
        estagio1.origemDeTransicoesProibidas.splice(idx1, 1);
        estagio2.destinoDeTransicoesProibidas.splice(idx2, 1);
      };

      $scope.toggleTransicaoProibida = function(estagio1, estagio2, disabled) {
        if (disabled) {
          return false;
        }

        var transicao = 'E' + estagio1.posicao + '-E' + estagio2.posicao
        if ($scope.currentAnel.transicoesProibidas.hasOwnProperty(transicao)) {
          desativarTransicaoProibida(estagio1, estagio2);
        } else {
          ativarTransicaoProibida(estagio1, estagio2);
        }
      };

      $scope.marcarTransicaoAlternativa = function(transicao) {
        var t = _.find(transicao.origem.origemDeTransicoesProibidas, {destino: {id: transicao.destino.id}});

        if (transicao.alternativa) {
          t.alternativa = {id: transicao.alternativa.id};
          transicao.alternativa.alternativaDeTransicoesProibidas = transicao.alternativa.alternativaDeTransicoesProibidas || [];
          transicao.alternativa.alternativaDeTransicoesProibidas.push(t);
        } else {
          var estagioAlternativa = _.find($scope.currentAnel.estagios, t.alternativa);
          delete t.alternativa;
          var query = {origem: {id: transicao.origem.id},destino: {id: transicao.destino.id}};
          var index = _.findIndex(estagioAlternativa.alternativaDeTransicoesProibidas, query);
          return index >= 0 && estagioAlternativa.alternativaDeTransicoesProibidas.splice(index, 1);
        }
      };

      $scope.filterEstagiosAlternativos = function(origem, destino) {
        return function(item) {
          return item.id !== origem.id && item.id !== destino.id;
        };
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

        var gruposAneis = _.chain($scope.objeto.aneis).map('gruposSemaforicos').flatten().value();
        var grupoX = _.find(gruposAneis, {id: $scope.grupoIds[x]});
        var grupoY = _.find(gruposAneis, {id: $scope.grupoIds[y]});

        if ($scope.verdesConflitantes[x][y]) {
          var indexX = _.findIndex(grupoX.verdesConflitantes, {id: $scope.grupoIds[y]});
          grupoX.verdesConflitantes.splice(indexX, 1);

          var indexY = _.findIndex(grupoY.verdesConflitantes, {id: $scope.grupoIds[x]});
          grupoY.verdesConflitantes.splice(indexY, 1);
        } else {
          grupoX.verdesConflitantes = grupoX.verdesConflitantes || [];
          grupoY.verdesConflitantes = grupoY.verdesConflitantes || [];
          grupoX.verdesConflitantes.push({
            id: $scope.grupoIds[y],
            anel: {
              id: grupoY.anel.id
            }
          });
          grupoY.verdesConflitantes.push({
            id: $scope.grupoIds[x],
            anel: {
              id: grupoX.anel.id
            }
          });
        }

        // Deve marcar/desmarcar os coordenadas (x, y) e (y, x) simultaneamente.
        $scope.verdesConflitantes[x][y] = !$scope.verdesConflitantes[x][y];
        $scope.verdesConflitantes[y][x] = !$scope.verdesConflitantes[y][x];
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

          $scope.errors.aneis = _.compact($scope.errors.aneis);
          $scope.getErrosVerdes();
        }
      };

      $scope.getErrosVerdes = function() {
        $scope.messages = [];
        _.each($scope.errors.aneis, function(anel, anelIndex) {
          _.each(anel.gruposSemaforicos, function(gs, gsIndex) {
            var nomeGS = 'G' + $scope.objeto.aneis[anelIndex].gruposSemaforicos[gsIndex].posicao;
            _.each(gs, function(mgs) {
              _.map(mgs, function(msg) {
                $scope.messages.push(nomeGS + ': ' + msg);
              });
            });
          });
        });

        $scope.messages = _.uniq($scope.messages);
      };

      $scope.closeMensagensVerdes = function() {
        $scope.messages = [];
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
      };

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
