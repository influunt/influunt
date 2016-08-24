'use strict';

/**
 * @ngdoc function
 * @name influuntApp.controller:TabelaHorariosCtrl
 * @description
 * # HorariosCtrl
 * Controller of the influuntApp
 */
angular.module('influuntApp')
  .controller('TabelaHorariosCtrl', ['$scope', '$state', '$timeout', 'Restangular', '$filter', 'toast',
                           'influuntAlert', 'influuntBlockui', 'geraDadosDiagramaIntervalo',
                           'handleValidations', 'TabelaHorariaService',
    function ($scope, $state, $timeout, Restangular, $filter, toast,
              influuntAlert, influuntBlockui, geraDadosDiagramaIntervalo, handleValidations, TabelaHorariaService) {

      var adicionaTabelaHorario, adicionaEvento, atualizaPlanos, atualizaGruposSemaforicos, atualizaEventos, atualizaPosicaoEventos;
      /**
       * Inicializa a tela de tabela horario.
       */
      $scope.init = function() {
        var id = $state.params.id;
        Restangular.one('controladores', id).get().then(function(res) {
          $scope.objeto = res;
          $scope.comCheckBoxGrupo = false;
          $scope.currentTipoEvento = 'NORMAL';
          $scope.dias = [
            {
              label: 'Todos os dias da semana',
              value: 'TODOS_OS_DIAS',
              dias: ['dom','seg','ter','qua','qui','sex','sab'],
              prioridade:11
            },
            {
              label: 'Domingo',
              value: 'DOMINGO',
              dias: ['dom'],
              prioridade:7
            },
            {
              label: 'Segunda-feira',
              value: 'SEGUNDA',
              dias: ['seg'] ,
              prioridade:6,
            },
            {
              label: 'Terça-feira',
              value: 'TERCA',
              dias: ['ter'],
              prioridade:5
            },
            {
              label: 'Quarta-feira',
              value: 'QUARTA',
              dias: ['qua'],
              prioridade:4,
            },
            {
              label: 'Quinta-feira',
              value: 'QUINTA',
              dias: ['qui'],
              prioridade:3
            },
            {
              label: 'Sexta-feira',
              value: 'SEXTA',
              dias: ['sex'],
              prioridade:2
            },
            {
              label: 'Sábado',
              value: 'SABADO',
              dias: ['sab'],
              prioridade:1
            },
            {
              label: 'Sábado e Domingo',
              value: 'SABADO_A_DOMINGO',
              dias: ['dom','sab'],
              prioridade:8
            },

            {
              label: 'Segunda à Sexta',
              value: 'SEGUNDA_A_SEXTA',
              dias: ['seg','ter','qua','qui','sex'],
              prioridade:9
            },
            {
              label: 'Segunda à Sábado',
              value: 'SEGUNDA_A_SABADO',
              dias: ['seg','ter','qua','qui','sex','sab'],
              prioridade:10
            }
          ];
          $scope.horarios = $scope.getTimes(24);

          $scope.tipoEventos = [{posicao: ''}, {posicao: 'Especiais Recorrentes'}, {posicao: 'Especiais Não Recorrentes'}];

          _.each($scope.objeto.eventos, function(evento){
            evento.hora = parseInt(evento.horario.split(':')[0]) + '';
            evento.minuto = parseInt(evento.horario.split(':')[1]) + '';
            if(!!evento.diaDaSemana){
              evento.diaDaSemana = _.find($scope.dias, {label: evento.diaDaSemana}).value;
            }
            if(!!evento.data){
              evento.data = moment(evento.data, 'DD-MM-YYYY');
            }
          });


          if(!$scope.objeto.tabelaHorario) {
            adicionaTabelaHorario($scope.objeto);
          }
          adicionaEvento($scope.objeto.tabelaHorario, 'NORMAL');
          adicionaEvento($scope.objeto.tabelaHorario, 'ESPECIAL');
          adicionaEvento($scope.objeto.tabelaHorario, 'NAO_RECORRENTE');
          atualizaEventos();
          atualizaGruposSemaforicos();
          atualizaPlanos();

          // TODO - remover bloco de codigo
          // $scope.objeto.aneis = _.orderBy($scope.objeto.aneis, ['posicao']);
          // $scope.aneis = _.filter($scope.objeto.aneis, {ativo: true});
          // $scope.aneis.forEach(function(anel) {
          //   if(!anel.tabelaHorario) {
          //     adicionaTabelaHorario(anel);
          //   }
          //   adicionaEvento(anel.tabelaHorario, 'NORMAL');
          //   adicionaEvento(anel.tabelaHorario, 'ESPECIAL');
          //   adicionaEvento(anel.tabelaHorario, 'NAO_RECORRENTE');
          // });
          // $scope.selecionaAnelTabelaHorarios(0);

        });
      };

      $scope.selecionaAnelTabelaHorarios = function(index) {
        selecionaAnel(index);
        $scope.currentTabelaHorario = _.find($scope.objeto.tabelasHorarios, {idJson: $scope.currentAnel.tabelaHorario.idJson});
        atualizaEventos();
      };

      $scope.selecionaTipoEvento = function(index) {
        switch(index) {
          case 0:
            $scope.currentTipoEvento = 'NORMAL'
            break;
          case 1:
            $scope.currentTipoEvento = 'ESPECIAL';
            break;
          case 2:
            $scope.currentTipoEvento = 'NAO_RECORRENTE';
            break;
          default:
            $scope.currentTipoEvento = 'NORMAL'
            break;
        };

        TabelaHorariaService.initialize($scope.currentTipoEvento);
        $scope.TabelaHorariaService = TabelaHorariaService;

        atualizaEventos();
      };

      $scope.selecionaEvento = function(evento){
        $scope.currentEvento = evento;
      };

      $scope.getTimes = function(quantidade){
        return new Array(quantidade);
      };

      $scope.verificaAtualizacaoDeEventos = function(evento) {
        if(evento.dataMoment) {
          evento.data = evento.dataMoment.format("DD-MM-YYYY");
        }

        if(evento.hora && evento.minuto && evento.plano){
          evento.horario = evento.hora + ':' + evento.minuto;

          if(($scope.currentTipoEvento !== 'NORMAL' && evento.data && evento.nome) || ($scope.currentTipoEvento === 'NORMAL' && evento.diaDaSemana) && !evento.posicao){
            //Salva Evento
            $scope.objeto.eventos = $scope.objeto.eventos || [];
            $scope.objeto.eventos.push(evento);
            $scope.currentTabelaHorario.eventos = $scope.currentTabelaHorario.eventos || [];
            $scope.currentTabelaHorario.eventos.push({idJson: evento.idJson});
            adicionaEvento($scope.currentTabelaHorario);
          }

          atualizaEventos();
        }
      };

      $scope.removerEvento = function(evento) {
        var eventoObjetoIndex = _.findIndex($scope.objeto.eventos, {idJson: evento.idJson});
        var eventoIndex = _.findIndex($scope.currentTabelaHorario.eventos, {idJson: evento.idJson});

        $scope.objeto.eventos.splice(eventoObjetoIndex, 1);
        $scope.currentTabelaHorario.eventos.splice(eventoIndex, 1);
        atualizaEventos();
      };

      $scope.visualizarPlano = function(evento){
        $('#myModal').modal('show');
        $scope.plano = geraDadosDiagramaIntervalo.gerar(evento.plano, $scope.currentAnel, $scope.currentGruposSemaforicos, $scope.objeto);
        var diagramaBuilder = new influunt.components.DiagramaIntervalos($scope.plano, $scope.valoresMinimos);
        var result = diagramaBuilder.calcula();
        _.each(result.gruposSemaforicos, function(g) {
          g.ativo = true;
        });
        $scope.dadosDiagrama = result;
        return $scope.dadosDiagrama;
      };

      adicionaTabelaHorario = function(controlador) {
        var tabelaHorario = {
          idJson: UUID.generate(),
          controlador: { idJson: controlador.idJson },
          eventos: []
        };

        controlador.tabelaHorario = controlador.tabelaHorario || {};
        controlador.tabelaHorario.idJson = tabelaHorario.idJson;
        return tabelaHorario;
      };

      adicionaEvento = function(tabelaHorario, tipo){
        tipo = tipo || $scope.currentTipoEvento;
        $scope.currentEventos = $scope.currentEventos || [];
        var eventoIndex = null;
        if($scope.novosEventos){
          eventoIndex = _.findIndex($scope.novosEventos, {tabelaHorario: {idJson: tabelaHorario.idJson}, tipo: tipo});
        }
        var evento = {
          idJson: UUID.generate(),
          tabelaHorario: { idJson: tabelaHorario.idJson },
          tipo: tipo,
          dataMoment: moment()
        };
        $scope.novosEventos = $scope.novosEventos || [];
        if(eventoIndex >= 0){
          $scope.novosEventos.splice(eventoIndex, 1, evento);
        }else{
          $scope.novosEventos.push(evento);
        }
        return evento;
      };


      atualizaPlanos = function() {
        var ids = _.map($scope.currentAnel.planos, 'idJson');
        $scope.currentPlanos = _
          .chain($scope.objeto.planos)
          .filter(function(e) {
            return ids.indexOf(e.idJson) >= 0;
          })
          .orderBy(['posicao'])
          .value();

          return $scope.currentPlanos;
      };

      atualizaGruposSemaforicos = function(){
        var ids = _.map($scope.currentAnel.gruposSemaforicos, 'idJson');
        $scope.currentGruposSemaforicos = _
          .chain($scope.objeto.gruposSemaforicos)
          .filter(function(e) {
            return ids.indexOf(e.idJson) >= 0;
          })
          .orderBy(['posicao'])
          .value();

          return $scope.currentGruposSemaforicos;
      };
      atualizaEventos = function() {
        var ids = _.map($scope.currentTabelaHorario.eventos, 'idJson');
        $scope.currentEventosAnel = _
          .chain($scope.objeto.eventos)
          .filter(function(e) {
            return ids.indexOf(e.idJson) >= 0;
          })
          .value();

        $scope.currentEventos = _
          .chain($scope.currentEventosAnel)
          .filter(function(e){
            return e.tipo === $scope.currentTipoEvento;
          })
          .orderBy(['posicao'])
          .value();

        $scope.currentNovoEvento = _.find($scope.novosEventos, {tabelaHorario: {idJson: $scope.currentTabelaHorario.idJson}, tipo: $scope.currentTipoEvento});
        return atualizaPosicaoEventos();
      };

      atualizaPosicaoEventos = function() {
        $scope.currentEventos.forEach(function (evento, index){
          evento.posicao = index + 1;
        });
      };

      $scope.submitForm = function() {

        Restangular
        .all('tabela_horarios')
        .post($scope.objeto)
        .then(function(res) {
          $scope.objeto = res;

          $scope.errors = {};
          influuntBlockui.unblock();
          $state.go('app.controladores');
        })
        .catch(function(res) {
          influuntBlockui.unblock();
          if (res.status === 422) {
            $scope.errors = handleValidations.buildValidationMessages(res.data);
          } else {
            console.error(res);
          }
        });
      };

      $scope.anelTemErro = function(indice) {
        return handleValidations.anelTemErro($scope.errors, indice);
      };

      $scope.tipoEventoTemErro = function(indice) {
        var hasError = false;
        if ($scope.errors) {
          var errorsAnel = $scope.errors.aneis[$scope.currentAnelIndex];
          if (_.keys(errorsAnel).length > 0) {
            _.each(errorsAnel.tabelaHorario.eventos, function (eventoError, eventoIndex) {
              var anel = $scope.aneis[$scope.currentAnelIndex];
              var tabelaHorario = _.find($scope.objeto.tabelasHorarios, {idJson: anel.tabelaHorario.idJson});
              var evento = _.find($scope.objeto.eventos, {idJson: tabelaHorario.eventos[eventoIndex].idJson});

              hasError = hasError || ((evento.tipo === 'NORMAL' && indice === 0) || (evento.tipo === 'ESPECIAL' && indice === 1) || (evento.tipo === 'NAO_RECORRENTE' && indice === 2));
            });
          }
        }

        return hasError;
      };

      $scope.getErrosTabelaHorarios = function(listaErros) {
        if (listaErros && listaErros.tabelaHorario) {
          return _.chain(listaErros.tabelaHorario.aoMenosUmEvento).map().flatten().value();
        } else {
          return [];
        }
      };

      //Métodos para colorir tabela
      $scope.getTableCell = function(v,i){
        return v[Math.floor(i / 4)][i % 4].state;
      };

      $scope.highlightEvento = function(v,i){
        var table = v[Math.floor(i / 4)][i % 4].state;
        var tr = angular.element('.evento .' + table).parent();
        tr.addClass('light_' + table);
      };

      $scope.leaveHighlightEvento = function(v,i){
        var table = v[Math.floor(i / 4)][i % 4].state;
        var tr = angular.element('.light_' + table);
        tr.removeClass('light_' + table);
      };

      $scope.$watch('currentEventos',function(newObj){
        if($scope.currentEventos && newObj){
          var quadroHorarioBuilder = new influunt.components.QuadroTabelaHorario($scope.dias, $scope.currentEventosAnel);
          $scope.agenda = quadroHorarioBuilder.calcula();
        }
      },true);

    }
  ]);
