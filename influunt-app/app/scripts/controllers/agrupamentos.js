'use strict';

/**
 * @ngdoc function
 * @name influuntApp:CorredoresCtrl
 * @description
 * # CidadesCtrl
 * Controller of the influuntApp
 */
angular.module('influuntApp')
  .controller('AgrupamentosCtrl', ['$scope', '$controller', '$timeout', 'Restangular', 'HorariosService', 'influuntBlockui', 'influuntAlert', '$filter',
    function ($scope, $controller, $timeout, Restangular, HorariosService, influuntBlockui, influuntAlert, $filter) {

      // funções privadas
      var inicializaAneisHandle, inicializaControladoresHandle, adicionarAnel, removerAnel, findControladorByAnelId,
          setPlanosSelectOptions, podeConstruirHorario, registrarWatcherAneis, atualizaHandles, checkControlador,
          loadControladores, loadSubareas, registrarWatcherSubareasHandle, inicializaSubareasHandle, registrarWatcherControladoresSelecionadosHandle,
          addControladorNaView, removeControladorNaView, reorderControladoresNaView;

      var objetoInitialized = false, controladoresInitialized = false;

      // Herda todo o comportamento do crud basico.
      $controller('CrudCtrl', { $scope: $scope });
      $scope.inicializaNovoCrud('agrupamentos');

      $scope.controladoresNaView = [];
      $scope.controladoresSelecionados = { controladores: [] };
      $scope.subareasSelecionadas = { subareas: [] };
      $scope.controladoresDisponiveisParaEscolha = [];

      $scope.pesquisa = {
        campos: [
          {
            nome: 'nome',
            label: 'agrupamentos.nome',
            tipo: 'texto'
          },
          {
            nome: 'numero',
            label: 'agrupamentos.numero',
            tipo: 'texto'
          },
          {
            nome: 'descricao',
            label: 'agrupamentos.descricao',
            tipo: 'texto'
          },
          {
            nome: 'tipo',
            label: 'agrupamentos.tipo',
            tipo: 'texto'
          }
        ]
      };

      $scope.planosSelectOptions = [];
      $scope.dias = HorariosService.getDias();
      $scope.horarios = HorariosService.getHoras();
      $scope.minutos = HorariosService.getMinutos();
      $scope.segundos = HorariosService.getSegundos();
      $scope.planos = HorariosService.getPlanos();

      loadControladores = function() {
        Restangular.all('controladores').customGET('agrupamentos', { statusControlador: 1 })
          .then(function(res) {
            $scope.controladores = [];
            res.data.forEach(function(controlador) {
              controlador.aneisAtivos = _
                .chain(controlador.aneis)
                .filter('ativo')
                .orderBy('posicao')
                .value();
              $scope.controladores.push(controlador);
              $scope.controladoresDisponiveisParaEscolha.push(controlador);
            });
            setPlanosSelectOptions();
            inicializaControladoresHandle();
            inicializaAneisHandle();

            controladoresInitialized = true;
            if (objetoInitialized) {
              atualizaHandles();
              registrarWatcherAneis();
              registrarWatcherControladoresSelecionadosHandle();
            }
          }).finally(influuntBlockui.unblock);
      };

      loadSubareas = function() {
        Restangular.all('subareas').customGET()
          .then(function(res) {
            if (res.data) {
              $scope.subareas = res.data;
              inicializaSubareasHandle();
              registrarWatcherSubareasHandle();
            }
          }).finally(influuntBlockui.unblock);
      };


      addControladorNaView = function(controlador) {
        var foundControlador = _.map(_.flatten($scope.controladoresNaView), 'id').indexOf(controlador.id) > -1;
        if (!foundControlador) {
          if ($scope.controladoresNaView.length === 0) {
            $scope.controladoresNaView.push([controlador]);
          } else {
            var last = $scope.controladoresNaView.length - 1;
            var lastArrayLength = $scope.controladoresNaView[last].length;
            if (lastArrayLength < 3) {
              $scope.controladoresNaView[last].push(controlador);
            } else {
              $scope.controladoresNaView.push([controlador]);
            }
          }
        }
      };

      removeControladorNaView = function(controladorId) {
        var indexToRemove = -1,
            lastArray = $scope.controladoresNaView.length - 1,
            controladorFoundAt = -1;
        _.forEach($scope.controladoresNaView, function(controladores, index) {
          var controladorIsHere = _.findIndex(controladores, { id: controladorId }) > -1;
          if (controladorIsHere) {
            if (controladores.length === 1) {
              indexToRemove = index;
            } else {
              controladorFoundAt = index;
              _.remove(controladores, { id: controladorId });
            }
          }
        });
        if (indexToRemove > -1) {
          $scope.controladoresNaView.splice(indexToRemove, 1);
        } else if (controladorFoundAt !== lastArray) {
          reorderControladoresNaView();
        }
      };

      reorderControladoresNaView = function() {
        var controladoresCopy = _.cloneDeep($scope.controladoresNaView);
        $scope.controladoresNaView = [];
        _.forEach(controladoresCopy, function(controladores) {
          _.forEach(controladores, addControladorNaView);
        });
      };

      registrarWatcherSubareasHandle = function() {
        $scope.$watch('subareasHandle', function(subareas) {

          _.forEach(subareas, function(selected, subareaId) {
            var subarea = _.find($scope.subareas, { id: subareaId });
            if (selected) {
              _.forEach(subarea.controladores, function(controlador) {
                var c = _.find($scope.controladores, { id: controlador.id });
                addControladorNaView(c);
                _.remove($scope.controladoresDisponiveisParaEscolha, { id: controlador.id });
              });
            } else {
              _.forEach(subarea.controladores, function(controlador) {
                var c = _.find($scope.controladores, { id: controlador.id });
                removeControladorNaView(controlador.id);
                if (c) {
                  $scope.controladoresDisponiveisParaEscolha.push(c);
                }
              });
            }
          });

        }, true);
      };

      registrarWatcherControladoresSelecionadosHandle = function() {
        $scope.$watch('controladoresSelecionadosHandle', function(controladores) {

          _.forEach(controladores, function(selected, controladorId) {
            var controlador = _.find($scope.controladoresDisponiveisParaEscolha, { id: controladorId });
            if (controlador) {
              if (selected) {
                addControladorNaView(controlador);
              } else {
                removeControladorNaView(controladorId);
              }
            }

          });
        }, true);
      };

      $scope.watcherSubareasSelecionadas = function() {
        _.forEach($scope.subareasHandle, function(selected, subareaId, subareasHandle) {
          var index = _.findIndex($scope.subareasSelecionadas.subareas, { id: subareaId });
          subareasHandle[subareaId] = index > -1;
        });
      };

      $scope.watcherControladoresSelecionados = function() {
        _.forEach($scope.controladoresSelecionadosHandle, function(selected, controladorId, controladoresSelecionadosHandle) {
          var index = _.findIndex($scope.controladoresSelecionados.controladores, { id: controladorId });
          controladoresSelecionadosHandle[controladorId] = index > -1;
        });
      };

      inicializaSubareasHandle = function() {
        $scope.subareasHandle = {};

        _.forEach($scope.subareas, function(subarea) {
          $scope.subareasHandle[subarea.id] = false;
        });
      };

      /**
       * Recupera a lista de controladores que podem ser relacionadas ao agrupamento.
       */
      $scope.beforeShow = function() {
        loadControladores();
        loadSubareas();
      };

      $scope.afterShow = function() {
        if ($scope.objeto.horario) {
          var horarioSplitted = $scope.objeto.horario.split(':');
          $scope.objeto.hora = parseInt(horarioSplitted[0]) + '';
          $scope.objeto.minuto = parseInt(horarioSplitted[1]) + '';
          $scope.objeto.segundo = parseInt(horarioSplitted[2]) + '';
        }
        if ($scope.objeto.numero) {
          $scope.objeto.numero = parseInt($scope.objeto.numero);
        }
        objetoInitialized = true;
        if (controladoresInitialized) {
          atualizaHandles();
          registrarWatcherAneis();
          registrarWatcherControladoresSelecionadosHandle();
        }
        $scope.objeto.segundo = $scope.objeto.segundo || '0';
      };

      atualizaHandles = function() {
        _.forEach($scope.controladores, function(controlador) {
          _.forEach(controlador.aneis, function(anel) {
            if (anel.ativo) {
              var isAnelSelected = _.findIndex($scope.objeto.aneis, { id: anel.id }) > -1;
              $scope.aneisHandle[anel.id] = isAnelSelected;
              if (isAnelSelected) {
                if (_.findIndex($scope.controladoresSelecionados.controladores, { id: controlador.id }) === -1) {
                  $scope.controladoresSelecionados.controladores.push(controlador);
                  $scope.controladoresSelecionadosHandle[controlador.id] = true;
                }
              }
            }
          });
          checkControlador(controlador.id);
        });
      };

      setPlanosSelectOptions = function() {
        $scope.planosSelectOptions = [];
        var limitePlanos;
        if ($scope.controladores.length > 0) {
          limitePlanos = $scope.controladores[0].limitePlanos;
        } else {
          limitePlanos = 16;
        }
        _.times(limitePlanos, function(i) {
          $scope.planosSelectOptions.push({ numero: i+1, nome: "Plano "+(i+1) });
        });
      };

      inicializaControladoresHandle = function() {
        $scope.controladoresHandle = {};
        $scope.controladoresSelecionadosHandle = {};
        _.forEach($scope.controladores, function(controlador) {
          $scope.controladoresHandle[controlador.id] = false;
          $scope.controladoresSelecionadosHandle[controlador.id] = false;
        });
      };

      inicializaAneisHandle = function() {
        $scope.aneisHandle = {};
        _.forEach($scope.controladores, function(controlador) {
          _.forEach(controlador.aneis, function(anel) {
            if (anel.ativo) {
              $scope.aneisHandle[anel.id] = false;
            }
          });
        });
      };

      findControladorByAnelId = function(anelId) {
        return _.find($scope.controladores, function(controlador) {
          return _.find(controlador.aneis, { id: anelId });
        });
      };

      adicionarAnel = function(anelId) {
        if (!angular.isArray($scope.objeto.aneis)) {
          $scope.objeto.aneis = [];
        }
        var controlador = _.find($scope.controladores, function(c) { return _.findIndex(c.aneis, { id: anelId }) > -1; })
        $scope.objeto.aneis.push({ id: anelId, controlador: { id: controlador.id } });
      };

      removerAnel = function(anelId) {
        _.remove($scope.objeto.aneis, function(anel) {
          return anel.id === anelId;
        });
      };

      $scope.setTodosAneis = function(controladorId, value) {
        var controlador = _.find($scope.controladores, { id: controladorId });
        _.forEach(controlador.aneis, function(anel) {
          if (anel.ativo) {
            $scope.aneisHandle[anel.id] = !!value;
          }
        });
      };

      registrarWatcherAneis = function() {
        $scope.$watch('aneisHandle', function(aneisHandle) {
          if (aneisHandle) {
            _.forEach(aneisHandle, function(checked, anelId) {
              if (checked) {
                var index = _.findIndex($scope.objeto.aneis, { id: anelId });
                if (index === -1) {
                  adicionarAnel(anelId);
                }
              } else {
                removerAnel(anelId);
              }
            });
          }
        }, true);
      };

      $scope.uncheckControlador = function(anelId) {
        var controlador = findControladorByAnelId(anelId);
        $scope.controladoresHandle[controlador.id] = false;
      };

      $scope.checkControladorByAnelId = function(anelId) {
        var controlador = findControladorByAnelId(anelId);
        checkControlador(controlador.id);
      };

      checkControlador = function(controladorId) {
        var controlador = _.find($scope.controladores, { id: controladorId });
        var allChecked = true;
        _.forEach(controlador.aneis, function(anel) {
          if (anel.ativo && !$scope.aneisHandle[anel.id]) {
            allChecked = false;
            return;
          }
        });

        if (allChecked) {
          $scope.controladoresHandle[controlador.id] = true;
        }
      };

      podeConstruirHorario = function() {
        return isFinite(parseInt($scope.objeto.hora)) &&
               isFinite(parseInt($scope.objeto.minuto)) &&
               isFinite(parseInt($scope.objeto.segundo));
      };

      $scope.atualizaHorario = function() {
        if(podeConstruirHorario()) {
          $scope.objeto.horario = ($scope.objeto.hora.length < 2 ? '0'+$scope.objeto.hora : $scope.objeto.hora) + ':' +
          ($scope.objeto.minuto.length < 2 ? '0'+$scope.objeto.minuto : $scope.objeto.minuto) + ':' +
          ($scope.objeto.segundo.length < 2 ? '0'+$scope.objeto.segundo : $scope.objeto.segundo);
        }
      };

      $scope.salvar = function(formValido) {
        var title = $filter('translate')('agrupamentos.eventosPopup.title'),
            text = $filter('translate')('agrupamentos.eventosPopup.text');

        return influuntAlert.alert(title, text)
          .then(function(criarEventos) {
            $scope.criarEventos = criarEventos;
            $scope.save(formValido);
          });
      }

      $scope.create = function() {
        return Restangular
          .service('agrupamentos')
          .post($scope.objeto, { criarEventos: $scope.criarEventos })
          .finally(influuntBlockui.unblock);
      };

      $scope.update = function() {
        return $scope
          .objeto
          .save({ criarEventos: $scope.criarEventos })
          .finally(influuntBlockui.unblock);
      };

    }]);
