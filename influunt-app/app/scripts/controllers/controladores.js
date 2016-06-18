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

      var loadWizardData = function(obj) {
        $scope.getHelpersControlador();
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
          _.each($scope.aneis, function(anel) {
            _.each(anel.gruposSemaforicos, function(grupo, index) {
              grupo.label = 'G' + (index+1);
            });
          });

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

      $scope.selecionaAnel = function(index) {
        $scope.currentAnelId = index;
        $scope.currentAnel = $scope.aneis[$scope.currentAnelId];
      };

      $scope.selecionaMovimento = function(index) {
        $scope.currentMovimentoId = index;
        $scope.currentMovimento = $scope.currentAnel.movimentos[index];
        $scope.atualizaGruposSemaforicosSelecionados();
      };

      $scope.submitFormAneis = function(nextStep) {
        var stepResource = 'aneis';
        if (validacoesAneis.valida($scope.objeto.aneis, $scope.objeto)) {
          $scope.submitForm({$valid: true}, stepResource, nextStep);
        } else {
          $scope.validacoes.alerts = validacoesAneis.retornaMensagensValidacao($scope.objeto.aneis);
        }
      };

      $scope.closeAlert = function() {
        $scope.validacoes.alerts = [];
      };

      $scope.getHelpersControlador = function() {
        Restangular.all('helpers').all('controlador').customGET().then(function(res) {
          $scope.data = res;
          $scope.helpers = {cidade: $scope.data.cidades[0]};
          if (!$scope.objeto.area) {
            $scope.objeto = {area: $scope.helpers.cidade.areas[0]};
          }

          // // tela associacao.
          // $scope.objeto = {"id": 1,"dataCriacao": "17/06/2016 18:21:47","dataAtualizacao": "17/06/2016 18:21:47","descricao": "Teste","numeroSMEE": "1234","numeroSMEEConjugado1": null,"numeroSMEEConjugado2": null,"numeroSMEEConjugado3": null,"firmware": "1235","latitude": 1.0,"longitude": 2.0,"modelo": {"id": "8efe6ec9-6551-40ae-a90b-c1fcfa4fd42b","configuracao": {"id": "e61b92fe-fa98-4687-8455-c8518256856b","limiteEstagio": 16,"limiteGrupoSemaforico": 16,"limiteAnel": 4,"limiteDetectorPedestre": 4,"limiteDetectorVeicular": 8,"dataCriacao": "17/06/2016 18:21:47","dataAtualizacao": "17/06/2016 18:21:47"},"descricao": null,"dataCriacao": "17/06/2016 18:21:47","dataAtualizacao": "17/06/2016 18:21:47"},"area": {"id": "6488d82b-62bb-4f4f-b57e-8bcb60ce6d67","descricao": 1,"limitesGeograficos": [],"dataCriacao": "17/06/2016 18:21:47","dataAtualizacao": "17/06/2016 18:21:47"},"aneis": [{"id": "52a5d32d-b899-4fa1-b6da-d22f745b0e18","ativo": false,"descricao": null,"posicao": null,"numeroSMEE": null,"latitude": null,"longitude": null,"quantidadeGrupoPedestre": 0,"quantidadeGrupoVeicular": 0,"quantidadeDetectorPedestre": 0,"quantidadeDetectorVeicular": 0,"detectores": [],"gruposSemaforicos": [],"movimentos": [],"dataCriacao": "17/06/2016 18:21:47","dataAtualizacao": "17/06/2016 18:21:47","idAnel": "1.000.0001-null","quantidadeGrupoSemaforico": 0,"estagios": []},{"id": "ef39739f-4075-48a7-a064-eb5b3f9db520","ativo": false,"descricao": null,"posicao": null,"numeroSMEE": null,"latitude": null,"longitude": null,"quantidadeGrupoPedestre": 0,"quantidadeGrupoVeicular": 0,"quantidadeDetectorPedestre": 0,"quantidadeDetectorVeicular": 0,"detectores": [],"gruposSemaforicos": [],"movimentos": [],"dataCriacao": "17/06/2016 18:21:47","dataAtualizacao": "17/06/2016 18:21:47","idAnel": "1.000.0001-null","quantidadeGrupoSemaforico": 0,"estagios": []},{"id": "a361c9c5-5138-4fc9-94ee-254e40c1c742","ativo": true,"descricao": null,"posicao": 1,"numeroSMEE": null,"latitude": 1.0,"longitude": 2.0,"quantidadeGrupoPedestre": 0,"quantidadeGrupoVeicular": 8,"quantidadeDetectorPedestre": 2,"quantidadeDetectorVeicular": 0,"detectores": [{"id": "9ac750a9-fbb9-4996-b590-08c0222ad8cd","tipo": "PEDESTRE","anel": "a361c9c5-5138-4fc9-94ee-254e40c1c742","dataCriacao": "17/06/2016 18:21:47","dataAtualizacao": "17/06/2016 18:21:47"},{"id": "c0abe989-e882-4994-bca7-8df11620e3b3","tipo": "PEDESTRE","anel": "a361c9c5-5138-4fc9-94ee-254e40c1c742","dataCriacao": "17/06/2016 18:21:47","dataAtualizacao": "17/06/2016 18:21:47"}],"gruposSemaforicos": [{"id": "8859deb3-6e8b-4960-98db-befcf074d811","tipo": null,"anel": "a361c9c5-5138-4fc9-94ee-254e40c1c742","estagioGrupoSemaforicos": [],"grupoConflito": null,"verdesConflitantes": [],"dataCriacao": "17/06/2016 18:21:47","dataAtualizacao": "17/06/2016 18:21:47"},{"id": "020f6104-3314-4d52-a4a0-f2cfb20acc1e","tipo": null,"anel": "a361c9c5-5138-4fc9-94ee-254e40c1c742","estagioGrupoSemaforicos": [],"grupoConflito": null,"verdesConflitantes": [],"dataCriacao": "17/06/2016 18:21:47","dataAtualizacao": "17/06/2016 18:21:47"},{"id": "7a373d24-7dd0-4cd5-9c6d-c21d8e1949ee","tipo": null,"anel": "a361c9c5-5138-4fc9-94ee-254e40c1c742","estagioGrupoSemaforicos": [],"grupoConflito": null,"verdesConflitantes": [],"dataCriacao": "17/06/2016 18:21:47","dataAtualizacao": "17/06/2016 18:21:47"},{"id": "fcc83b72-8518-4adb-a38c-4011e9b015a4","tipo": null,"anel": "a361c9c5-5138-4fc9-94ee-254e40c1c742","estagioGrupoSemaforicos": [],"grupoConflito": null,"verdesConflitantes": [],"dataCriacao": "17/06/2016 18:21:47","dataAtualizacao": "17/06/2016 18:21:47"},{"id": "77a8af95-a34f-4d3c-ab33-4a230024a848","tipo": null,"anel": "a361c9c5-5138-4fc9-94ee-254e40c1c742","estagioGrupoSemaforicos": [],"grupoConflito": null,"verdesConflitantes": [],"dataCriacao": "17/06/2016 18:21:47","dataAtualizacao": "17/06/2016 18:21:47"},{"id": "3a699549-5fa6-44b2-bf6c-5ae26290153d","tipo": null,"anel": "a361c9c5-5138-4fc9-94ee-254e40c1c742","estagioGrupoSemaforicos": [],"grupoConflito": null,"verdesConflitantes": [],"dataCriacao": "17/06/2016 18:21:47","dataAtualizacao": "17/06/2016 18:21:47"},{"id": "a8d702e2-fbf8-40fa-96e4-37d8b1bc9dfb","tipo": null,"anel": "a361c9c5-5138-4fc9-94ee-254e40c1c742","estagioGrupoSemaforicos": [],"grupoConflito": null,"verdesConflitantes": [],"dataCriacao": "17/06/2016 18:21:47","dataAtualizacao": "17/06/2016 18:21:47"},{"id": "1f319ef7-989a-4330-9ea8-0f91f0c9446c","tipo": null,"anel": "a361c9c5-5138-4fc9-94ee-254e40c1c742","estagioGrupoSemaforicos": [],"grupoConflito": null,"verdesConflitantes": [],"dataCriacao": "17/06/2016 18:21:47","dataAtualizacao": "17/06/2016 18:21:47"}],"movimentos": [{"id": "348b4ea4-10e7-4826-9731-3d230d5e31e0","descricao": null,"imagem": null,"anel": "a361c9c5-5138-4fc9-94ee-254e40c1c742","estagio": {"id": "c34d22cf-0de5-46f3-a1eb-9ab1fc2921c4","imagem": null,"descricao": null,"tempoMaximoPermanencia": null,"demandaPrioritaria": false,"estagiosGruposSemaforicos": [],"dataCriacao": "17/06/2016 18:21:47","dataAtualizacao": "17/06/2016 18:21:47"},"dataCriacao": "17/06/2016 18:21:47","dataAtualizacao": "17/06/2016 18:21:47"},{"id": "f00ed86a-5518-47dd-aa01-e64cae59f68f","descricao": null,"imagem": null,"anel": "a361c9c5-5138-4fc9-94ee-254e40c1c742","estagio": {"id": "3e812884-1ea4-4add-aee7-3a86ea6acdf4","imagem": null,"descricao": null,"tempoMaximoPermanencia": null,"demandaPrioritaria": false,"estagiosGruposSemaforicos": [],"dataCriacao": "17/06/2016 18:21:47","dataAtualizacao": "17/06/2016 18:21:47"},"dataCriacao": "17/06/2016 18:21:47","dataAtualizacao": "17/06/2016 18:21:47"}],"dataCriacao": "17/06/2016 18:21:47","dataAtualizacao": "17/06/2016 18:21:47","idAnel": "1.000.0001-1","quantidadeGrupoSemaforico": 8,"estagios": ["c34d22cf-0de5-46f3-a1eb-9ab1fc2921c4","3e812884-1ea4-4add-aee7-3a86ea6acdf4"]},{"id": "86ec090d-3fbc-46b5-abdf-f7f043dcc6ef","ativo": true,"descricao": null,"posicao": 1,"numeroSMEE": null,"latitude": 1.0,"longitude": 2.0,"quantidadeGrupoPedestre": 0,"quantidadeGrupoVeicular": 2,"quantidadeDetectorPedestre": 0,"quantidadeDetectorVeicular": 2,"detectores": [{"id": "431dece5-4d01-4481-990a-176cf877d61c","tipo": "VEICULAR","anel": "86ec090d-3fbc-46b5-abdf-f7f043dcc6ef","dataCriacao": "17/06/2016 18:21:47","dataAtualizacao": "17/06/2016 18:21:47"},{"id": "824e117d-0fac-42cf-bdea-ff85b32a8cd9","tipo": "VEICULAR","anel": "86ec090d-3fbc-46b5-abdf-f7f043dcc6ef","dataCriacao": "17/06/2016 18:21:47","dataAtualizacao": "17/06/2016 18:21:47"}],"gruposSemaforicos": [{"id": "1bbb4505-9e7d-46d2-ae09-c04dd57d6d2b","tipo": null,"anel": "86ec090d-3fbc-46b5-abdf-f7f043dcc6ef","estagioGrupoSemaforicos": [],"grupoConflito": null,"verdesConflitantes": [],"dataCriacao": "17/06/2016 18:21:47","dataAtualizacao": "17/06/2016 18:21:47"},{"id": "ce3065d4-2327-4275-af0b-b78fccd3f3f5","tipo": null,"anel": "86ec090d-3fbc-46b5-abdf-f7f043dcc6ef","estagioGrupoSemaforicos": [],"grupoConflito": null,"verdesConflitantes": [],"dataCriacao": "17/06/2016 18:21:47","dataAtualizacao": "17/06/2016 18:21:47"}],"movimentos": [{"id": "57709a2f-7b72-4273-b65f-ce82e06a0977","descricao": null,"imagem": null,"anel": "86ec090d-3fbc-46b5-abdf-f7f043dcc6ef","estagio": {"id": "e9117e5d-cf09-40da-a7ec-248c572eb21b","imagem": null,"descricao": null,"tempoMaximoPermanencia": null,"demandaPrioritaria": false,"estagiosGruposSemaforicos": [],"dataCriacao": "17/06/2016 18:21:47","dataAtualizacao": "17/06/2016 18:21:47"},"dataCriacao": "17/06/2016 18:21:47","dataAtualizacao": "17/06/2016 18:21:47"},{"id": "49506052-f751-4aad-b66b-bbad976bee09","descricao": null,"imagem": null,"anel": "86ec090d-3fbc-46b5-abdf-f7f043dcc6ef","estagio": {"id": "fd47aa6f-9361-47d0-b8da-a88ee8405bbd","imagem": null,"descricao": null,"tempoMaximoPermanencia": null,"demandaPrioritaria": false,"estagiosGruposSemaforicos": [],"dataCriacao": "17/06/2016 18:21:47","dataAtualizacao": "17/06/2016 18:21:47"},"dataCriacao": "17/06/2016 18:21:47","dataAtualizacao": "17/06/2016 18:21:47"}],"dataCriacao": "17/06/2016 18:21:47","dataAtualizacao": "17/06/2016 18:21:47","idAnel": "1.000.0001-1","quantidadeGrupoSemaforico": 2,"estagios": ["e9117e5d-cf09-40da-a7ec-248c572eb21b","fd47aa6f-9361-47d0-b8da-a88ee8405bbd"]}],"gruposSemaforicos": ["8859deb3-6e8b-4960-98db-befcf074d811","020f6104-3314-4d52-a4a0-f2cfb20acc1e","7a373d24-7dd0-4cd5-9c6d-c21d8e1949ee","fcc83b72-8518-4adb-a38c-4011e9b015a4","77a8af95-a34f-4d3c-ab33-4a230024a848","3a699549-5fa6-44b2-bf6c-5ae26290153d","a8d702e2-fbf8-40fa-96e4-37d8b1bc9dfb","1f319ef7-989a-4330-9ea8-0f91f0c9446c","1bbb4505-9e7d-46d2-ae09-c04dd57d6d2b","ce3065d4-2327-4275-af0b-b78fccd3f3f5"],"detectores": [{"id": "9ac750a9-fbb9-4996-b590-08c0222ad8cd","tipo": "PEDESTRE","anel": "a361c9c5-5138-4fc9-94ee-254e40c1c742","dataCriacao": "17/06/2016 18:21:47","dataAtualizacao": "17/06/2016 18:21:47"},{"id": "c0abe989-e882-4994-bca7-8df11620e3b3","tipo": "PEDESTRE","anel": "a361c9c5-5138-4fc9-94ee-254e40c1c742","dataCriacao": "17/06/2016 18:21:47","dataAtualizacao": "17/06/2016 18:21:47"},{"id": "431dece5-4d01-4481-990a-176cf877d61c","tipo": "VEICULAR","anel": "86ec090d-3fbc-46b5-abdf-f7f043dcc6ef","dataCriacao": "17/06/2016 18:21:47","dataAtualizacao": "17/06/2016 18:21:47"},{"id": "824e117d-0fac-42cf-bdea-ff85b32a8cd9","tipo": "VEICULAR","anel": "86ec090d-3fbc-46b5-abdf-f7f043dcc6ef","dataCriacao": "17/06/2016 18:21:47","dataAtualizacao": "17/06/2016 18:21:47"}],"idControlador": "1.000.0001"};
        });
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

        $scope.atualizaGruposSemaforicosSelecionados();
      };

      $scope.atualizaGruposSemaforicosSelecionados = function() {
        var estagioId = $scope.currentMovimento.estagio.id;
        $scope.gruposSelecionados = $scope.currentAnel.gruposSemaforicos.filter(function(grupo) {
          return !!_.filter(grupo.estagioGrupoSemaforicos, {estagio: {id: estagioId}}).length;
        });
      };

      $scope.toggleEstagioAtivado = function(grupo, movimento) {
        var estagioId = movimento.estagio.id;
        var estagio = _.find(grupo.estagioGrupoSemaforicos, {estagio: {id: estagioId}});
        estagio.ativo = !estagio.ativo;
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

    }]);
