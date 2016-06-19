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

      var visitaRota = function() {
        var step = _.camelCase("app.wizard_controladores.dados_basicos".split('.').reverse()[0]);
        if ($scope.wizard.stepVisited.hasOwnProperty(step)) {
          $scope.wizard.stepVisited[step] = true;
        }
      };

      var loadWizardData = function(obj) {
        getHelpersControlador();
        $scope.objeto = obj;
        $scope.wizard = $scope.wizard || {
          stepVisited: {
            dadosBasicos: true,
            aneis: false,
            associacao: false,
            verdesConflitantes: false
          }
        };
        visitaRota();

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
          });

          $scope.aneis = _.orderBy($scope.aneis, ['posicao'], ['asc']);
          $scope.selecionaAnel(0);
          $scope.selecionaMovimento(0);
        });
      };

      $scope.inicializaVerdesConflitantes = function() {
        return $scope.inicializaWizard().then(function() {
          console.warn('still mocking data.');
          $scope.objeto = {"id": 4,"dataCriacao": "19/06/2016 12:21:49","dataAtualizacao": "19/06/2016 12:22:28","descricao": "teste","numeroSMEE": "sadf","numeroSMEEConjugado1": "asdf","numeroSMEEConjugado2": "asfd","numeroSMEEConjugado3": "asfd","firmware": "asdf","latitude": -23.55017918151177,"longitude": -46.66395664215088,"modelo": {"id": "9c31bb11-0802-4ce8-87b4-5dd6b07b9de0","configuracao": {"id": "52da8b31-cfaa-4268-b4ed-0e35c29b3318","descricao": "opa 1","limiteEstagio": 8,"limiteGrupoSemaforico": 8,"limiteAnel": 8,"limiteDetectorPedestre": 8,"limiteDetectorVeicular": 16,"dataCriacao": "19/06/2016 12:05:11","dataAtualizacao": "19/06/2016 12:05:11"},"descricao": "opa 1","dataCriacao": "19/06/2016 12:05:25","dataAtualizacao": "19/06/2016 12:05:25","nomeFabricante": "fab 1"},"area": {"id": "a2d34109-2d36-4dfd-abf3-0d1c397803f3","descricao": 1,"limitesGeograficos": [],"dataCriacao": "19/06/2016 12:04:54","dataAtualizacao": "19/06/2016 12:04:54"},"aneis": [{"id": "44dc47b7-3944-4b3a-b163-22ae93f6f61f","ativo": false,"descricao": null,"posicao": 8,"numeroSMEE": null,"latitude": null,"longitude": null,"quantidadeGrupoPedestre": 0,"quantidadeGrupoVeicular": 0,"quantidadeDetectorPedestre": 0,"quantidadeDetectorVeicular": 0,"detectores": [],"gruposSemaforicos": [],"movimentos": [],"dataCriacao": "19/06/2016 12:22:28","dataAtualizacao": "19/06/2016 12:22:28","idAnel": "1.000.0004-8","quantidadeGrupoSemaforico": 0,"estagios": []},{"id": "591c16d0-c98a-4b90-96ae-c359f7a76ef0","ativo": true,"descricao": "111","posicao": 1,"numeroSMEE": null,"latitude": -23.55080863515256,"longitude": -46.66434288024902,"quantidadeGrupoPedestre": 1,"quantidadeGrupoVeicular": 1,"quantidadeDetectorPedestre": 0,"quantidadeDetectorVeicular": 0,"detectores": [],"gruposSemaforicos": [{"id": "7efa53af-3e9a-4938-a70d-435c21dbd19e","tipo": "VEICULAR","anel": "591c16d0-c98a-4b90-96ae-c359f7a76ef0","estagioGrupoSemaforicos": [{"grupoSemaforico": "7efa53af-3e9a-4938-a70d-435c21dbd19e","estagio": {"id": "d37ab1dd-4b4b-425a-989e-ee94de505060","imagem": null,"descricao": null,"tempoMaximoPermanencia": 12,"demandaPrioritaria": true,"estagiosGruposSemaforicos": [],"dataCriacao": "19/06/2016 12:22:28","dataAtualizacao": "19/06/2016 12:22:28"},"ativo": true}],"grupoConflito": null,"verdesConflitantes": [],"posicao": 1,"dataCriacao": "19/06/2016 12:22:28","dataAtualizacao": "19/06/2016 12:22:28","label": "G1","ativo": false,"estagiosRelacionados": {"d37ab1dd-4b4b-425a-989e-ee94de505060": true},"estagiosAtivados": {"d37ab1dd-4b4b-425a-989e-ee94de505060": true},"descricao": "veicular_"},{"id": "60b5304f-b51a-4214-a2a0-6b0753ef522a","tipo": "PEDESTRE","anel": "591c16d0-c98a-4b90-96ae-c359f7a76ef0","estagioGrupoSemaforicos": [{"grupoSemaforico": "60b5304f-b51a-4214-a2a0-6b0753ef522a","estagio": {"id": "0f9eb2f2-afc0-4482-b75f-e8f6474f70db","imagem": null,"descricao": null,"tempoMaximoPermanencia": 10,"demandaPrioritaria": false,"estagiosGruposSemaforicos": [],"dataCriacao": "19/06/2016 12:22:28","dataAtualizacao": "19/06/2016 12:22:28"},"ativo": true}],"grupoConflito": null,"verdesConflitantes": [],"posicao": 2,"dataCriacao": "19/06/2016 12:22:28","dataAtualizacao": "19/06/2016 12:22:28","label": "G2","ativo": false,"estagiosRelacionados": {"0f9eb2f2-afc0-4482-b75f-e8f6474f70db": true},"estagiosAtivados": {"0f9eb2f2-afc0-4482-b75f-e8f6474f70db": true},"descricao": "pedestre_"}],"movimentos": [{"id": "0699d9f1-a1eb-472f-a502-931464bfcee9","descricao": null,"imagem": {"id": "80030800-858b-4e17-93c1-08c7043949fd","filename": "Screen Shot 2016-06-13 at 23.45.32.png","contentType": "image/png","dataCriacao": "19/06/2016 12:21:59","dataAtualizacao": "19/06/2016 12:21:59"},"anel": "591c16d0-c98a-4b90-96ae-c359f7a76ef0","estagio": {"id": "d37ab1dd-4b4b-425a-989e-ee94de505060","imagem": null,"descricao": null,"tempoMaximoPermanencia": 12,"demandaPrioritaria": true,"estagiosGruposSemaforicos": [],"dataCriacao": "19/06/2016 12:22:28","dataAtualizacao": "19/06/2016 12:22:28"},"dataCriacao": "19/06/2016 12:22:28","dataAtualizacao": "19/06/2016 12:22:28"},{"id": "e4da01c4-d2aa-42f0-a56a-3c0165bec984","descricao": null,"imagem": {"id": "69f72f6d-ee51-4d5b-b749-1eeb6e3a46b8","filename": "Screen Shot 2016-06-18 at 12.44.55.png","contentType": "image/png","dataCriacao": "19/06/2016 12:21:59","dataAtualizacao": "19/06/2016 12:21:59"},"anel": "591c16d0-c98a-4b90-96ae-c359f7a76ef0","estagio": {"id": "0f9eb2f2-afc0-4482-b75f-e8f6474f70db","imagem": null,"descricao": null,"tempoMaximoPermanencia": 10,"demandaPrioritaria": false,"estagiosGruposSemaforicos": [],"dataCriacao": "19/06/2016 12:22:28","dataAtualizacao": "19/06/2016 12:22:28"},"dataCriacao": "19/06/2016 12:22:28","dataAtualizacao": "19/06/2016 12:22:28"}],"dataCriacao": "19/06/2016 12:22:28","dataAtualizacao": "19/06/2016 12:22:28","idAnel": "1.000.0004-1","quantidadeGrupoSemaforico": 2,"estagios": ["d37ab1dd-4b4b-425a-989e-ee94de505060","0f9eb2f2-afc0-4482-b75f-e8f6474f70db"]},{"id": "638b5e21-4293-4bda-bfc7-91d0067e812e","ativo": false,"descricao": null,"posicao": 4,"numeroSMEE": null,"latitude": null,"longitude": null,"quantidadeGrupoPedestre": 0,"quantidadeGrupoVeicular": 0,"quantidadeDetectorPedestre": 0,"quantidadeDetectorVeicular": 0,"detectores": [],"gruposSemaforicos": [],"movimentos": [],"dataCriacao": "19/06/2016 12:22:28","dataAtualizacao": "19/06/2016 12:22:28","idAnel": "1.000.0004-4","quantidadeGrupoSemaforico": 0,"estagios": []},{"id": "7f16a0b9-5947-47e7-8353-963ee546eda5","ativo": false,"descricao": null,"posicao": 7,"numeroSMEE": null,"latitude": null,"longitude": null,"quantidadeGrupoPedestre": 0,"quantidadeGrupoVeicular": 0,"quantidadeDetectorPedestre": 0,"quantidadeDetectorVeicular": 0,"detectores": [],"gruposSemaforicos": [],"movimentos": [],"dataCriacao": "19/06/2016 12:22:28","dataAtualizacao": "19/06/2016 12:22:28","idAnel": "1.000.0004-7","quantidadeGrupoSemaforico": 0,"estagios": []},{"id": "7f65f1e2-0673-4ecc-babd-c4399a864e2a","ativo": false,"descricao": null,"posicao": 3,"numeroSMEE": null,"latitude": null,"longitude": null,"quantidadeGrupoPedestre": 0,"quantidadeGrupoVeicular": 0,"quantidadeDetectorPedestre": 0,"quantidadeDetectorVeicular": 0,"detectores": [],"gruposSemaforicos": [],"movimentos": [],"dataCriacao": "19/06/2016 12:22:28","dataAtualizacao": "19/06/2016 12:22:28","idAnel": "1.000.0004-3","quantidadeGrupoSemaforico": 0,"estagios": []},{"id": "a575ed1f-63e3-451c-8d12-bc27fb246abc","ativo": false,"descricao": null,"posicao": 5,"numeroSMEE": null,"latitude": null,"longitude": null,"quantidadeGrupoPedestre": 0,"quantidadeGrupoVeicular": 0,"quantidadeDetectorPedestre": 0,"quantidadeDetectorVeicular": 0,"detectores": [],"gruposSemaforicos": [],"movimentos": [],"dataCriacao": "19/06/2016 12:22:28","dataAtualizacao": "19/06/2016 12:22:28","idAnel": "1.000.0004-5","quantidadeGrupoSemaforico": 0,"estagios": []},{"id": "c012e997-e3d1-4b04-b8d1-7f7865b1ee5a","ativo": true,"descricao": "asdf","posicao": 2,"numeroSMEE": null,"latitude": -23.548408826987984,"longitude": -46.66224002838135,"quantidadeGrupoPedestre": 1,"quantidadeGrupoVeicular": 1,"quantidadeDetectorPedestre": 0,"quantidadeDetectorVeicular": 0,"detectores": [],"gruposSemaforicos": [{"id": "20de42a2-0826-46f8-b514-e894f7edc33f","tipo": "PEDESTRE","anel": "c012e997-e3d1-4b04-b8d1-7f7865b1ee5a","estagioGrupoSemaforicos": [{"grupoSemaforico": "20de42a2-0826-46f8-b514-e894f7edc33f","estagio": {"id": "cef56580-cd93-44d4-b570-7552dd53ce7d","imagem": null,"descricao": null,"tempoMaximoPermanencia": 20,"demandaPrioritaria": true,"estagiosGruposSemaforicos": [],"dataCriacao": "19/06/2016 12:22:28","dataAtualizacao": "19/06/2016 12:22:28"},"ativo": true}],"grupoConflito": null,"verdesConflitantes": [],"posicao": 3,"dataCriacao": "19/06/2016 12:22:28","dataAtualizacao": "19/06/2016 12:22:28","label": "G3","ativo": false,"estagiosRelacionados": {"cef56580-cd93-44d4-b570-7552dd53ce7d": true},"estagiosAtivados": {"cef56580-cd93-44d4-b570-7552dd53ce7d": true},"descricao": "_pedestre"},{"id": "a300dbfc-264f-4f87-a293-124935921b3b","tipo": "VEICULAR","anel": "c012e997-e3d1-4b04-b8d1-7f7865b1ee5a","estagioGrupoSemaforicos": [{"grupoSemaforico": "a300dbfc-264f-4f87-a293-124935921b3b","estagio": {"id": "1fd61f90-6b0d-4605-8f63-96592f2efb1a","imagem": null,"descricao": null,"tempoMaximoPermanencia": 30,"demandaPrioritaria": false,"estagiosGruposSemaforicos": [],"dataCriacao": "19/06/2016 12:22:28","dataAtualizacao": "19/06/2016 12:22:28"},"ativo": true}],"grupoConflito": null,"verdesConflitantes": [],"posicao": 4,"dataCriacao": "19/06/2016 12:22:28","dataAtualizacao": "19/06/2016 12:22:28","label": "G4","ativo": false,"estagiosRelacionados": {"1fd61f90-6b0d-4605-8f63-96592f2efb1a": true},"estagiosAtivados": {"1fd61f90-6b0d-4605-8f63-96592f2efb1a": true},"descricao": "_veicular"}],"movimentos": [{"id": "66606f91-e4b8-4b24-ac37-ccd499ba93de","descricao": null,"imagem": {"id": "17df0d7e-bae8-4e81-add3-e6589dc13367","filename": "12963901_10154615163486840_8455352346796368737_n.jpg","contentType": "image/jpeg","dataCriacao": "19/06/2016 12:22:20","dataAtualizacao": "19/06/2016 12:22:20"},"anel": "c012e997-e3d1-4b04-b8d1-7f7865b1ee5a","estagio": {"id": "cef56580-cd93-44d4-b570-7552dd53ce7d","imagem": null,"descricao": null,"tempoMaximoPermanencia": 20,"demandaPrioritaria": true,"estagiosGruposSemaforicos": [],"dataCriacao": "19/06/2016 12:22:28","dataAtualizacao": "19/06/2016 12:22:28"},"dataCriacao": "19/06/2016 12:22:28","dataAtualizacao": "19/06/2016 12:22:28"},{"id": "97a3e644-b2f1-422a-a9cc-6425151d64ab","descricao": null,"imagem": {"id": "d4e3a681-e7b9-4229-8100-ec341bdd0a39","filename": "12321359_986438248070903_1173574894423312875_n.jpg","contentType": "image/jpeg","dataCriacao": "19/06/2016 12:22:20","dataAtualizacao": "19/06/2016 12:22:20"},"anel": "c012e997-e3d1-4b04-b8d1-7f7865b1ee5a","estagio": {"id": "1fd61f90-6b0d-4605-8f63-96592f2efb1a","imagem": null,"descricao": null,"tempoMaximoPermanencia": 30,"demandaPrioritaria": false,"estagiosGruposSemaforicos": [],"dataCriacao": "19/06/2016 12:22:28","dataAtualizacao": "19/06/2016 12:22:28"},"dataCriacao": "19/06/2016 12:22:28","dataAtualizacao": "19/06/2016 12:22:28"}],"dataCriacao": "19/06/2016 12:22:28","dataAtualizacao": "19/06/2016 12:22:28","idAnel": "1.000.0004-2","quantidadeGrupoSemaforico": 2,"estagios": ["cef56580-cd93-44d4-b570-7552dd53ce7d","1fd61f90-6b0d-4605-8f63-96592f2efb1a"]},{"id": "dad372f3-09a4-4b23-a663-39c9359566eb","ativo": false,"descricao": null,"posicao": 6,"numeroSMEE": null,"latitude": null,"longitude": null,"quantidadeGrupoPedestre": 0,"quantidadeGrupoVeicular": 0,"quantidadeDetectorPedestre": 0,"quantidadeDetectorVeicular": 0,"detectores": [],"gruposSemaforicos": [],"movimentos": [],"dataCriacao": "19/06/2016 12:22:28","dataAtualizacao": "19/06/2016 12:22:28","idAnel": "1.000.0004-6","quantidadeGrupoSemaforico": 0,"estagios": []}],"gruposSemaforicos": ["20de42a2-0826-46f8-b514-e894f7edc33f","60b5304f-b51a-4214-a2a0-6b0753ef522a","7efa53af-3e9a-4938-a70d-435c21dbd19e","a300dbfc-264f-4f87-a293-124935921b3b"],"detectores": [],"movimentos": [],"idControlador": "1.000.0004"};
          $scope.grupos = _.times(16, function(i) {return 'G' + (i+1);});

          $scope.movimentos = _.chain($scope.objeto.aneis).map('movimentos').flatten().value();

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
        var messages = res && res.data && res.data.map(function(a) {
          return {
            // msg: 'validacoesAPI.' + _.lowerCase(a.root) + '.' + _.camelCase(a.message),
            msg: a.message,
            params: {
              CAMPO: a.path
            }
          };
        });

        console.log(res);

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

      $scope.$watch('objeto.endereco', function(value) {
        if (value && value.geometry && location) {
          $scope.objeto.latitude = value.geometry.location.lat();
          $scope.objeto.longitude = value.geometry.location.lng();
        }
      });

      $scope.$watch('currentAnel.endereco', function(value) {
        if (value && value.geometry && location) {
          $scope.currentAnel.latitude = value.geometry.location.lat();
          $scope.currentAnel.longitude = value.geometry.location.lng();
        }
      });

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
