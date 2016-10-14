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
                           'handleValidations', 'TabelaHorariaService', 'HorariosService', 'SimulacaoService',
    function ($scope, $state, $timeout, Restangular, $filter, toast,
              influuntAlert, influuntBlockui, geraDadosDiagramaIntervalo,
              handleValidations, TabelaHorariaService, HorariosService, SimulacaoService) {

      var adicionaTabelaHorario, adicionaEvento, atualizaPlanos, atualizaDiagramaIntervalo, atualizaGruposSemaforicos, atualizaEventos,
      atualizaEventosNormais, atualizaPosicaoEventosDoTipo, atualizaPosicaoEventos, atualizaQuantidadeEventos, removerEventoNoCliente,
      atualizaQuadroTabelaHoraria, atualizaErrosEventos, getErrosEvento;

      var qtdEventos, qtdEventosRecorrentes, qtdEventosNaoRecorrentes;
      var NORMAL = 'NORMAL';
      var ESPECIAL_RECORRENTE = 'ESPECIAL_RECORRENTE';
      var ESPECIAL_NAO_RECORRENTE = 'ESPECIAL_NAO_RECORRENTE';

      $scope.somenteVisualizacao = $state.current.data.somenteVisualizacao;
      /**
       * Inicializa a tela de tabela horario.
       */
      $scope.init = function() {
        var id = $state.params.id;
        Restangular.one('controladores', id).get()
          .then(function(res) {
            $scope.objeto = res;
            $scope.comCheckBoxGrupo = false;
            $scope.currentTipoEvento = NORMAL;

            $scope.dias = HorariosService.getDias();
            $scope.horarios = HorariosService.getHoras();
            $scope.minutos = HorariosService.getMinutos();
            $scope.segundos = HorariosService.getSegundos();
            $scope.planos = HorariosService.getPlanos();

            $scope.tipoEventos = [
              {posicao: ''},
              {posicao: 'Especiais Recorrentes'},
              {posicao: 'Especiais Não Recorrentes'}
            ];

            $scope.qtdEventos = 0;

            $scope.nomesTabs = [
              $filter('translate')('tabelaHorarios.eventos') + '<span class=\'badge badge-success m-l-xs\'>' + $scope.qtdEventos + '</span>',
              $filter('translate')('tabelaHorarios.eventosRecorrentes') + '<span class=\'badge badge-success m-l-xs\'>' + $scope.qtdEventosRecorrentes + '</span>',
              $filter('translate')('tabelaHorarios.eventosNaoRecorrentes') + '<span class=\'badge badge-success m-l-xs\'>' + $scope.qtdEventosNaoRecorrentes + '</span>'
            ];

            $scope.objeto.aneis = _.orderBy($scope.objeto.aneis, ['posicao']);
            $scope.aneis = _.filter($scope.objeto.aneis, {ativo: true});

            _.each($scope.objeto.eventos, function(evento){
              evento.hora = parseInt(evento.horario.split(':')[0]) + '';
              evento.minuto = parseInt(evento.horario.split(':')[1]) + '';
              evento.segundo = parseInt(evento.horario.split(':')[2]) + '';
              if(!!evento.diaDaSemana){
                evento.diaDaSemana = _.find($scope.dias, {label: evento.diaDaSemana}).value;
              }
              if(!!evento.data){
                evento.data = moment(evento.data, 'DD-MM-YYYY');
              }
            });

            $scope.currentVersaoTabelaHorariaIndex = _.findIndex($scope.objeto.versoesTabelasHorarias, {statusVersao: 'EDITANDO'});
            if ($scope.currentVersaoTabelaHorariaIndex === -1) {
             $scope.currentVersaoTabelaHorariaIndex = _.findIndex($scope.objeto.versoesTabelasHorarias, {statusVersao: 'CONFIGURADO'});
            }
            if ($scope.currentVersaoTabelaHorariaIndex === -1) {
             $scope.currentVersaoTabelaHorariaIndex = _.findIndex($scope.objeto.versoesTabelasHorarias, {statusVersao: 'ATIVO'});
            }

            $scope.currentVersaoTabelaHoraria = $scope.objeto.versoesTabelasHorarias[$scope.currentVersaoTabelaHorariaIndex];
            if($scope.objeto.tabelasHorarias.length === 0) {
              $scope.objeto.versoesTabelasHorarias = ($scope.objeto.versoesTabelasHorarias.length > 0) ? $scope.objeto.versoesTabelasHorarias : [{idJson: UUID.generate()}];
              $scope.currentVersaoTabelaHorariaIndex = 0;
              $scope.currentVersaoTabelaHoraria = $scope.objeto.versoesTabelasHorarias[$scope.currentVersaoTabelaHorariaIndex];
              adicionaTabelaHorario($scope.objeto);
            }

            $scope.currentTabelaHoraria = _.find(
              $scope.objeto.tabelasHorarias, {idJson: $scope.currentVersaoTabelaHoraria.tabelaHoraria.idJson}
            );

            adicionaEvento($scope.currentTabelaHoraria, NORMAL);
            adicionaEvento($scope.currentTabelaHoraria, ESPECIAL_RECORRENTE);
            adicionaEvento($scope.currentTabelaHoraria, ESPECIAL_NAO_RECORRENTE);
            $scope.selecionaTipoEvento(0);
          })
          .finally(influuntBlockui.unblock);
      };

      $scope.clonarTabelaHoraria = function(controladorId) {
        return Restangular.one('controladores', controladorId).all('editar_tabela_horaria').customGET()
          .then(function() {
            $state.go('app.tabela_horarios_edit', { id: controladorId });
          })
          .catch(function(err) {
            toast.error($filter('translate')('geral.mensagens.default_erro'));
            throw new Error(JSON.stringify(err));
          })
          .finally(influuntBlockui.unblock);
      };

      $scope.timeline = function() {
        return Restangular.one('tabela_horarios', $state.params.id).all('timeline').customGET()
          .then(function(res) {
            $scope.versoes = res;
          })
          .catch(function(err) {
            toast.error($filter('translate')('geral.mensagens.default_erro'));
            throw new Error(JSON.stringify(err));
          })
          .finally(influuntBlockui.unblock);
      };

      $scope.editarTabelaHoraria = function(controladorId) {
        return Restangular.one('controladores', controladorId).all("pode_editar").customGET()
          .then(function() {
            $state.go('app.tabela_horarios_edit', { id: controladorId });
          })
          .catch(function(err) {
            toast.clear();
            influuntAlert.alert('Controlador', err.data[0].message);
          })
          .finally(influuntBlockui.unblock);
      };

      $scope.cancelarEdicao = function(controladorId) {
        influuntAlert.delete().then(function(confirmado) {
          return confirmado && Restangular.one('tabela_horarios', controladorId).all('cancelar_edicao').customDELETE()
            .then(function() {
              toast.success($filter('translate')('geral.mensagens.removido_com_sucesso'));
              $state.go('app.controladores');
            })
            .catch(function(err) {
              toast.error($filter('translate')('geral.mensagens.default_erro'));
              throw new Error(err);
            })
            .finally(influuntBlockui.unblock);
        });
      };

      $scope.selecionaTipoEvento = function(index) {
        switch(index) {
          case 1:
            $scope.currentTipoEvento = ESPECIAL_RECORRENTE;
            break;
          case 2:
            $scope.currentTipoEvento = ESPECIAL_NAO_RECORRENTE;
            break;
          default:
            $scope.currentTipoEvento = NORMAL;
            break;
        }

        TabelaHorariaService.initialize($scope.currentTipoEvento);
        $scope.tabelaHorariaService = TabelaHorariaService;
        atualizaEventos();
      };

      $scope.selecionaAnel = function (index){
        $scope.currentAnelIndex = index;
        $scope.currentAnel = $scope.aneis[$scope.currentAnelIndex];
        atualizaGruposSemaforicos();
        atualizaPlanos();
        atualizaDiagramaIntervalo();
      };

      $scope.selecionaEvento = function(evento){
        $scope.currentEvento = evento;
      };

      $scope.verificaAtualizacaoDeEventos = function(evento) {
        if(evento.dataMoment) {
          evento.data = evento.dataMoment.format('DD-MM-YYYY');
        }

        if(evento.hora && evento.minuto && evento.segundo && evento.posicaoPlano){
          evento.horario = evento.hora + ':' + evento.minuto + ':' + evento.segundo;

          if ((($scope.currentTipoEvento !== NORMAL && evento.data && evento.nome) ||
             ($scope.currentTipoEvento === NORMAL && evento.diaDaSemana)) &&
             !evento.posicao) {
            //Salva Evento
            $scope.objeto.eventos = $scope.objeto.eventos || [];
            $scope.objeto.eventos.push(evento);
            $scope.currentTabelaHoraria.eventos = $scope.currentTabelaHoraria.eventos || [];
            $scope.currentTabelaHoraria.eventos.push({idJson: evento.idJson});
            adicionaEvento($scope.currentTabelaHoraria);
          }

          atualizaEventos();
        }
      };

      removerEventoNoCliente = function(evento) {
        var eventoObjetoIndex = _.findIndex($scope.objeto.eventos, {idJson: evento.idJson});
        var eventoIndex = _.findIndex($scope.currentTabelaHoraria.eventos, {idJson: evento.idJson});

        $scope.objeto.eventos.splice(eventoObjetoIndex, 1);
        $scope.currentTabelaHoraria.eventos.splice(eventoIndex, 1);
        atualizaEventos();
        atualizaEventosNormais();
      };

      $scope.removerEvento = function(evento) {
        if (angular.isUndefined(evento.id)) {
          removerEventoNoCliente(evento);
        } else {
          Restangular.one('eventos', evento.id).remove()
            .then(function() {
              removerEventoNoCliente(evento);
            }).catch(function() {
              toast.error($filter('translate')('controladores.eventos.msg_erro_apagar_evento'));
            })
            .finally(influuntBlockui.unblock);
        }
      };

      $scope.visualizarPlano = function(evento){
        $scope.selecionaEvento(evento);
        $scope.selecionaAnel(0);
        $('#modalDiagramaIntervalos').modal('show');

        return $scope.dadosDiagrama;
      };

      atualizaDiagramaIntervalo = function (){
        var posicaoPlano = parseInt($scope.currentEvento.posicaoPlano);
        var plano = _.find($scope.currentPlanos, {posicao: posicaoPlano});
        if(plano){
          $scope.currentPlano = plano;
          $scope.plano = geraDadosDiagramaIntervalo.gerar(
            plano, $scope.currentAnel, $scope.currentGruposSemaforicos, $scope.objeto
          );
          var diagramaBuilder = new influunt.components.DiagramaIntervalos($scope.plano, $scope.valoresMinimos);
          var result = diagramaBuilder.calcula();
          _.each(result.gruposSemaforicos, function(g) {
            g.ativo = true;
          });
          $scope.dadosDiagrama = result;
        }else{
          $scope.dadosDiagrama = {erros: [$filter('translate')('diagrama_intervalo.erros.nao_existe_plano', {NUMERO: posicaoPlano})]};
        }
      };

      adicionaTabelaHorario = function(controlador) {
        var tabelaHoraria = {
          idJson: UUID.generate(),
          controlador: { idJson: controlador.idJson },
          versaoTabelaHoraria: {idJson: $scope.currentVersaoTabelaHoraria.idJson},
          eventos: []
        };

        controlador.tabelasHorarias = controlador.tabelasHorarias || [];
        controlador.tabelasHorarias.push(tabelaHoraria);
        $scope.currentVersaoTabelaHoraria.tabelaHoraria = {idJson: tabelaHoraria.idJson};

        return tabelaHoraria;
      };

      adicionaEvento = function(tabelaHoraria, tipo){
        tipo = tipo || $scope.currentTipoEvento;
        $scope.currentEventos = $scope.currentEventos || [];
        var eventoIndex = null;
        if($scope.novosEventos){
          eventoIndex = _.findIndex($scope.novosEventos, {tabelaHoraria: {idJson: tabelaHoraria.idJson}, tipo: tipo});
        }

        var evento = {
          idJson: UUID.generate(),
          tabelaHoraria: { idJson: tabelaHoraria.idJson },
          tipo: tipo,
          dataMoment: moment(),
          segundo: '0'
        };

        $scope.novosEventos = $scope.novosEventos || [];
        if (eventoIndex >= 0) {
          $scope.novosEventos.splice(eventoIndex, 1, evento);
        } else {
          $scope.novosEventos.push(evento);
        }

        if(tipo === NORMAL){
          atualizaEventosNormais();
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
        atualizaPosicaoEventos();
        $scope.currentEventos = _
          .chain($scope.objeto.eventos)
          .filter(function(e){
            return e.tipo === $scope.currentTipoEvento && e.tabelaHoraria.idJson === $scope.currentTabelaHoraria.idJson;
          })
          .orderBy(['posicao'])
          .value();

        $scope.currentNovoEvento = _.find(
          $scope.novosEventos,
          {tabelaHoraria: {idJson: $scope.currentTabelaHoraria.idJson}, tipo: $scope.currentTipoEvento}
        );

        atualizaErrosEventos();
        return $scope.currentEventos;
      };

      atualizaPosicaoEventosDoTipo = function(tipo) {
        var index = 1;
        return _.chain($scope.objeto.eventos)
          .filter(function(e){
            return e.tipo === tipo;
          })
          .orderBy(['posicao'])
          .value()
          .forEach(function (evento){
            evento.posicao = index;
            index++;
          });
      };

      atualizaPosicaoEventos = function() {
        atualizaPosicaoEventosDoTipo(NORMAL);
        atualizaPosicaoEventosDoTipo(ESPECIAL_RECORRENTE);
        atualizaPosicaoEventosDoTipo(ESPECIAL_NAO_RECORRENTE);
        atualizaQuantidadeEventos();
      };

      atualizaQuantidadeEventos = function() {
        qtdEventos = _.filter($scope.objeto.eventos, {tipo: NORMAL}).length;
        qtdEventosRecorrentes = _.filter($scope.objeto.eventos, {tipo: ESPECIAL_RECORRENTE}).length;
        qtdEventosNaoRecorrentes = _.filter($scope.objeto.eventos, {tipo: ESPECIAL_NAO_RECORRENTE}).length;

        $scope.nomesTabs = [
          $filter('translate')('tabelaHorarios.eventos') + '<span class=\'badge badge-success m-l-xs\'>' + qtdEventos + '</span>',
          $filter('translate')('tabelaHorarios.eventosRecorrentes') + '<span class=\'badge badge-success m-l-xs\'>' + qtdEventosRecorrentes + '</span>',
          $filter('translate')('tabelaHorarios.eventosNaoRecorrentes') + '<span class=\'badge badge-success m-l-xs\'>' + qtdEventosNaoRecorrentes + '</span>'
        ];
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
        })
        .finally(influuntBlockui.unblock);
      };

      $scope.tipoEventoTemErro = function(indice) {
        var hasError = false;
        if($scope.errors && $scope.errors.tabelaHoraria && $scope.errors.tabelaHoraria.eventos){
          _.each($scope.errors.tabelaHoraria.eventos, function (eventoError, eventoIndex) {
            if($scope.currentTabelaHoraria.eventos[eventoIndex]) {
              var evento = _.find($scope.objeto.eventos, {idJson: $scope.currentTabelaHoraria.eventos[eventoIndex].idJson});

              hasError = hasError || ((evento.tipo === NORMAL && indice === 0) || (evento.tipo === ESPECIAL_RECORRENTE && indice === 1) || (evento.tipo === ESPECIAL_NAO_RECORRENTE && indice === 2));
            }
          });
        }
        return hasError;
      };

      $scope.getErrosTabelaHoraria = function() {
        if ($scope.errors && Object.keys($scope.errors).length > 0 && Object.keys($scope.errors.versoesTabelasHorarias[$scope.currentVersaoTabelaHorariaIndex]).length > 0) {
          return _
          .chain($scope.errors.versoesTabelasHorarias[$scope.currentVersaoTabelaHorariaIndex].tabelaHoraria.aoMenosUmEvento)
          .map()
          .flatten()
          .value();
        } else {
          return [];
        }
      };

      getErrosEvento = function(evento){
        var indexEvento = _.findIndex($scope.objeto.eventos, {idJson: evento.idJson});
        return $scope.errors.versoesTabelasHorarias[$scope.currentVersaoTabelaHorariaIndex].tabelaHoraria.eventos[indexEvento];
      };

      atualizaErrosEventos = function() {
        $scope.currentErrosEventos = {};
        if ($scope.errors && Object.keys($scope.errors).length > 0 && Object.keys($scope.errors.versoesTabelasHorarias[$scope.currentVersaoTabelaHorariaIndex]).length > 0 && Object.keys($scope.errors.versoesTabelasHorarias[$scope.currentVersaoTabelaHorariaIndex].tabelaHoraria.eventos).length > 0){
          _.each($scope.currentEventos, function(evento, index){
            $scope.currentErrosEventos[index] = getErrosEvento(evento, index);
          });
        }
        return $scope.currentErrosEventos;
      };

      $scope.$watch('errors',function(){
        atualizaErrosEventos();
      },true);

      //Métodos para colorir tabela
      $scope.getTableCell = function(v,i){
        return v[Math.floor(i)][0].state;
      };

      $scope.highlightEvento = function(v,i){
        var table = v[Math.floor(i)][0].state;
        var tr = angular.element('.evento .' + table).parent();
        tr.addClass('light_' + table);
      };

      $scope.leaveHighlightEvento = function(v,i){
        var table = v[Math.floor(i)][0].state;
        var tr = angular.element('.light_' + table);
        tr.removeClass('light_' + table);
      };

      $scope.$watch('eventosNormais',function(newObj){
        if($scope.eventosNormais && newObj){
          atualizaQuadroTabelaHoraria();
        }
      },true);

      atualizaEventosNormais = function(){
        $scope.eventosNormais = _.chain($scope.objeto.eventos)
        .filter(function(e){
          return e.tipo === NORMAL && e.tabelaHoraria.idJson === $scope.currentTabelaHoraria.idJson;
        })
        .orderBy(['posicao'])
        .value();
      };

      atualizaQuadroTabelaHoraria = function(){
        var quadroHorarioBuilder = new influunt.components.QuadroTabelaHorario($scope.dias, $scope.eventosNormais);
        $scope.agenda = quadroHorarioBuilder.calcula();
      };

      $scope.podeSimular = function(controlador) {
        return SimulacaoService.podeSimular(controlador);
      };
    }]);
