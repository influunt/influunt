'use strict';

/**
 * @ngdoc function
 * @name influuntApp.controller:TabelaHorariosCtrl
 * @description
 * # HorariosCtrl
 * Controller of the influuntApp
 */
angular.module('influuntApp')
  .controller('TabelaHorariosDiffCtrl', ['$controller', '$scope', '$state', '$timeout', 'Restangular', '$filter', 'toast',
                           'influuntAlert', 'influuntBlockui', 'geraDadosDiagramaIntervalo',
                           'handleValidations', 'TabelaHorariaService', 'HorariosService', 'planoService', 'SimulacaoService',
    function ($controller, $scope, $state, $timeout, Restangular, $filter, toast,
              influuntAlert, influuntBlockui, geraDadosDiagramaIntervalo,
              handleValidations, TabelaHorariaService, HorariosService, planoService, SimulacaoService) {


      $controller('HistoricoCtrl', {$scope: $scope});
      $scope.inicializaResourceHistorico('tabelas_horarias');

      var adicionaTabelaHorario, adicionaEvento, atualizaDiagramaIntervalo, atualizaEventos, atualizaEventosNormais,
          atualizaPosicaoEventosDoTipo, atualizaPosicaoEventos, atualizaQuantidadeEventos, removerEventoNoCliente,
          removerEventoRemoto, atualizaQuadroTabelaHoraria, atualizaErrosEventos, getErrosEvento;

      var qtdEventos, qtdEventosRecorrentes, qtdEventosNaoRecorrentes;
      var NORMAL = 'NORMAL';
      var ESPECIAL_RECORRENTE = 'ESPECIAL_RECORRENTE';
      var ESPECIAL_NAO_RECORRENTE = 'ESPECIAL_NAO_RECORRENTE';

      $scope.tipoEventos = [
        {posicao: ''},
        {posicao: 'Especiais Recorrentes'},
        {posicao: 'Especiais Não Recorrentes'}
      ];

      $scope.tiposTabs = [NORMAL, ESPECIAL_RECORRENTE, ESPECIAL_NAO_RECORRENTE];
      $scope.nomesTabs = [
        $filter('translate')('tabelaHorarios.eventos'),
        $filter('translate')('tabelaHorarios.eventosRecorrentes'),
        $filter('translate')('tabelaHorarios.eventosNaoRecorrentes')
      ];

      $scope.somenteVisualizacao = true;

      /**
       * Inicializa a tela de tabela horario.
       */
      $scope.init = function() {
        var id = $state.params.id;
        console.log('diff!')
        Restangular.one('controladores', id).get()
          .then(function(res) {
            $scope.objeto = res;

            $scope.dias = HorariosService.getDias();
            $scope.horarios = HorariosService.getHoras();
            $scope.minutos = HorariosService.getMinutos();
            $scope.segundos = HorariosService.getSegundos();
            $scope.planos = HorariosService.getPlanos();

            _.each($scope.objeto.versoesTabelasHorarias, function(versaoTabelaHoraria) {
              versaoTabelaHoraria.dataCriacaoMoment = moment(versaoTabelaHoraria.dataCriacao, 'DD/MM/YYYY HH:mm:ss');
            });
            $scope.objeto.versoesTabelasHorarias = _.orderBy($scope.objeto.versoesTabelasHorarias, ['dataCriacaoMoment']);

            _.each($scope.objeto.eventos, function(evento) {
              var horarioSplitted = evento.horario.split(':');
              evento.hora = parseInt(horarioSplitted[0]) + '';
              evento.minuto = parseInt(horarioSplitted[1]) + '';
              evento.segundo = parseInt(horarioSplitted[2]) + '';
              if(!!evento.diaDaSemana){
                evento.diaDaSemana = _.find($scope.dias, {label: evento.diaDaSemana}).value;
              }
              if(!!evento.data){
                evento.dataMoment = moment(evento.data, 'DD-MM-YYYY');
                evento.dataDiaMes = evento.dataMoment.date() + '' + (evento.dataMoment.month() + 1);
                evento.dataDiaMesAno = evento.dataMoment.date() + '' + (evento.dataMoment.month() + 1) + evento.dataMoment.year();
              }
            });

            console.log('objeto: ', $scope.objeto)

            // $scope.currentVersaoTabelaHorariaIndex = _.findIndex($scope.objeto.versoesTabelasHorarias, {statusVersao: 'EDITANDO'});
            // if ($scope.currentVersaoTabelaHorariaIndex === -1) {
            //  $scope.currentVersaoTabelaHorariaIndex = _.findIndex($scope.objeto.versoesTabelasHorarias, {statusVersao: 'CONFIGURADO'});
            // }
            // if ($scope.currentVersaoTabelaHorariaIndex === -1) {
            //  $scope.currentVersaoTabelaHorariaIndex = _.findIndex($scope.objeto.versoesTabelasHorarias, {statusVersao: 'ATIVO'});
            // }

            // if($scope.objeto.tabelasHorarias.length === 0) {
            //   $scope.objeto.versoesTabelasHorarias = ($scope.objeto.versoesTabelasHorarias.length > 0) ? $scope.objeto.versoesTabelasHorarias : [{idJson: UUID.generate()}];
            //   $scope.currentVersaoTabelaHorariaIndex = 0;
            //   $scope.currentVersaoTabelaHoraria = $scope.objeto.versoesTabelasHorarias[$scope.currentVersaoTabelaHorariaIndex];
            //   adicionaTabelaHorario($scope.objeto);
            // }



            // var versao1 = _.find($scope.objeto.versoesTabelasHorarias, { idJson: '96122f94-d82c-442e-9118-ccd3bfc1fc7f' });
            $scope.versao1IdJson = 'b7a1efab-c606-483a-b06a-24f485c201c3';
            $scope.versao2IdJson = 'e41e36e9-0b83-4676-a213-7fa526650d7a';



            // adicionaEvento($scope.currentTabelaHoraria, NORMAL);
            // adicionaEvento($scope.currentTabelaHoraria, ESPECIAL_RECORRENTE);
            // adicionaEvento($scope.currentTabelaHoraria, ESPECIAL_NAO_RECORRENTE);
            $scope.selecionaTipoEvento(0);
          })
          .finally(influuntBlockui.unblock);
      };

      // $scope.clonarTabelaHoraria = function(controladorId) {
      //   return $scope.clonar(controladorId).finally(influuntBlockui.unblock);
      // };

      // $scope.editarTabelaHoraria = function(controladorId) {
      //   return $scope.editar(controladorId).finally(influuntBlockui.unblock);
      // };

      // $scope.cancelarEdicao = function() {
      //   return $scope.cancelar($scope.currentTabelaHoraria);
      // };

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
        // atualizaEventos();

        // diff
        var versao1 = _.find($scope.objeto.versoesTabelasHorarias, { idJson: $scope.versao1IdJson });
        var versao2 = _.find($scope.objeto.versoesTabelasHorarias, { idJson: $scope.versao2IdJson });
        makeDiff(versao1.tabelaHoraria.idJson, versao2.tabelaHoraria.idJson);
      };

      // $scope.selecionaAnel = function (index){
      //   $scope.currentAnelIndex = index;
      //   $scope.currentAnel = $scope.aneis[$scope.currentAnelIndex];
      //   $scope.currentGruposSemaforicos = planoService.atualizaGruposSemaforicos($scope.objeto, $scope.currentAnel);
      //   $scope.currentPlanos = planoService.atualizaPlanos($scope.objeto, $scope.currentAnel);
      //   atualizaDiagramaIntervalo();
      // };

      // $scope.selecionaEvento = function(evento){
      //   $scope.currentEvento = evento;
      // };

      // $scope.verificaAtualizacaoDeEventos = function(evento) {
      //   if(evento.dataMoment) {
      //     evento.data = evento.dataMoment.format('DD-MM-YYYY');
      //   }

      //   if(evento.hora && evento.minuto && evento.segundo && evento.posicaoPlano){
      //     evento.horario = evento.hora + ':' + evento.minuto + ':' + evento.segundo;

      //     if ((($scope.currentTipoEvento !== NORMAL && evento.data && evento.nome) ||
      //        ($scope.currentTipoEvento === NORMAL && evento.diaDaSemana)) &&
      //        !evento.posicao) {
      //       //Salva Evento
      //       $scope.objeto.eventos = $scope.objeto.eventos || [];
      //       $scope.objeto.eventos.push(evento);
      //       $scope.currentTabelaHoraria.eventos = $scope.currentTabelaHoraria.eventos || [];
      //       $scope.currentTabelaHoraria.eventos.push({idJson: evento.idJson});
      //       adicionaEvento($scope.currentTabelaHoraria);
      //     }

      //     // atualizaEventos();
      //   }
      // };

      // $scope.podeEditarControlador = function() {
      //   return planoService.podeEditarControlador($scope.objeto);
      // };

      // removerEventoNoCliente = function(evento) {
      //   var eventoObjetoIndex = _.findIndex($scope.objeto.eventos, {idJson: evento.idJson});
      //   var eventoIndex = _.findIndex($scope.currentTabelaHoraria.eventos, {idJson: evento.idJson});

      //   $scope.objeto.eventos.splice(eventoObjetoIndex, 1);
      //   $scope.currentTabelaHoraria.eventos.splice(eventoIndex, 1);
      //   // atualizaEventos();
      //   atualizaEventosNormais();
      // };

      // removerEventoRemoto = function(evento) {
      //   evento._destroy = true;
      //   // atualizaEventos();
      //   atualizaEventosNormais();
      // };

      // $scope.removerEvento = function(evento) {
      //   return angular.isDefined(evento.id) ? removerEventoRemoto(evento) : removerEventoNoCliente(evento);
      // };

      // $scope.visualizarPlano = function(evento) {
      //   $scope.selecionaEvento(evento);
      //   $scope.selecionaAnel(0);

      //   if (!angular.isDefined($scope.plano)) {
      //     influuntAlert.alert(
      //       $filter('translate')('planos.planoNaoConfigurado.tituloAlert'),
      //       $filter('translate')('planos.planoNaoConfigurado.textoAlert')
      //     );

      //     return false;
      //   }

      //   if ($scope.plano.modoOperacao === 'ATUADO' || $scope.plano.modoOperacao === 'MANUAL') {
      //     influuntAlert.alert(
      //       $filter('translate')('planos.modoOperacaoSemDiagrama.tituloAlert'),
      //       $filter('translate')('planos.modoOperacaoSemDiagrama.textoAlert')
      //     );
      //   } else {
      //     $('#modalDiagramaIntervalos').modal('show');
      //     return $scope.dadosDiagrama;
      //   }
      // };

      // adicionaTabelaHorario = function(controlador) {
      //   var tabelaHoraria = {
      //     idJson: UUID.generate(),
      //     controlador: { idJson: controlador.idJson },
      //     versaoTabelaHoraria: {idJson: $scope.currentVersaoTabelaHoraria.idJson},
      //     eventos: []
      //   };

      //   controlador.tabelasHorarias = controlador.tabelasHorarias || [];
      //   controlador.tabelasHorarias.push(tabelaHoraria);
      //   $scope.currentVersaoTabelaHoraria.tabelaHoraria = {idJson: tabelaHoraria.idJson};

      //   return tabelaHoraria;
      // };

      // adicionaEvento = function(tabelaHoraria, tipo){
      //   tipo = tipo || $scope.currentTipoEvento;
      //   $scope.currentEventos = $scope.currentEventos || [];
      //   var eventoIndex = null;
      //   if ($scope.novosEventos) {
      //     eventoIndex = _.findIndex($scope.novosEventos, {tabelaHoraria: {idJson: tabelaHoraria.idJson}, tipo: tipo});
      //   }

      //   var evento = {
      //     idJson: UUID.generate(),
      //     tabelaHoraria: { idJson: tabelaHoraria.idJson },
      //     tipo: tipo,
      //     dataMoment: moment(),
      //     segundo: '0'
      //   };

      //   $scope.novosEventos = $scope.novosEventos || [];
      //   if (eventoIndex >= 0) {
      //     $scope.novosEventos.splice(eventoIndex, 1, evento);
      //   } else {
      //     $scope.novosEventos.push(evento);
      //   }

      //   if(tipo === NORMAL){
      //     atualizaEventosNormais();
      //   }

      //   return evento;
      // };

      // atualizaDiagramaIntervalo = function () {
      //   var posicaoPlano = parseInt($scope.currentEvento.posicaoPlano);
      //   $scope.plano = _.find($scope.currentPlanos, {posicao: posicaoPlano});
      //   // alias necessária para o vis.
      //   $scope.currentPlano = $scope.plano;
      //   if ($scope.plano) {
      //     var estagiosPlanos = planoService.atualizaEstagiosPlanos($scope.objeto, $scope.plano);
      //     var valoresMinimos = planoService.montaTabelaValoresMinimos($scope.objeto);

      //     $scope.dadosDiagrama = planoService.atualizaDiagramaIntervalos(
      //       $scope.objeto, $scope.currentAnel, $scope.currentGruposSemaforicos,
      //       estagiosPlanos, $scope.plano, valoresMinimos
      //     );
      //   } else {
      //     $scope.dadosDiagrama = {erros: [$filter('translate')('diagrama_intervalo.erros.nao_existe_plano', {NUMERO: posicaoPlano})]};
      //   }
      // };

      // atualizaEventos = function() {
      //   $scope.currentEventos = _
      //     .chain($scope.objeto.eventos)
      //     .filter(function(e){
      //       return e.tipo === $scope.currentTipoEvento && e.tabelaHoraria.idJson === $scope.currentTabelaHoraria.idJson;
      //     })
      //     .reject('_destroy')
      //     .orderBy('posicao')
      //     .value();

      //   $scope.currentNovoEvento = _.find(
      //     $scope.novosEventos,
      //     {tabelaHoraria: {idJson: $scope.currentTabelaHoraria.idJson}, tipo: $scope.currentTipoEvento}
      //   );

      //   atualizaPosicaoEventos();
      //   atualizaErrosEventos();
      //   return $scope.currentEventos;
      // };

      // atualizaPosicaoEventosDoTipo = function(tipo) {
      //   return _.chain($scope.currentEventos)
      //     .filter({tipo: tipo})
      //     .orderBy('posicao')
      //     .each(function(evento, index) { evento.posicao = index + 1; })
      //     .value();
      // };

      // atualizaPosicaoEventos = function() {
      //   atualizaPosicaoEventosDoTipo(NORMAL);
      //   atualizaPosicaoEventosDoTipo(ESPECIAL_RECORRENTE);
      //   atualizaPosicaoEventosDoTipo(ESPECIAL_NAO_RECORRENTE);
      //   atualizaQuantidadeEventos();
      // };

      // atualizaQuantidadeEventos = function() {
      //   var eventosVersao = _.find(
      //       $scope.objeto.tabelasHorarias,
      //       {idJson: $scope.currentVersaoTabelaHoraria.tabelaHoraria.idJson}
      //     )
      //     .eventos
      //     .map(function(ev) {
      //       return _.find($scope.objeto.eventos, {idJson: ev.idJson});
      //     });

      //   qtdEventos = _.chain(eventosVersao).filter({tipo: NORMAL}).reject('_destroy').value().length;
      //   qtdEventosRecorrentes = _.chain(eventosVersao).filter({tipo: ESPECIAL_RECORRENTE}).reject('_destroy').value().length;
      //   qtdEventosNaoRecorrentes = _.chain(eventosVersao).filter({tipo: ESPECIAL_NAO_RECORRENTE}).reject('_destroy').value().length;

      //   $scope.nomesTabs = [
      //     $filter('translate')('tabelaHorarios.eventos') + '<span class=\'badge badge-success m-l-xs\'>' + qtdEventos + '</span>',
      //     $filter('translate')('tabelaHorarios.eventosRecorrentes') + '<span class=\'badge badge-success m-l-xs\'>' + qtdEventosRecorrentes + '</span>',
      //     $filter('translate')('tabelaHorarios.eventosNaoRecorrentes') + '<span class=\'badge badge-success m-l-xs\'>' + qtdEventosNaoRecorrentes + '</span>'
      //   ];
      // };

      // atualizaEventosNormais = function(){
      //   $scope.eventosNormais = _.chain($scope.objeto.eventos)
      //   .filter(function(e){
      //     return e.tipo === NORMAL && e.tabelaHoraria.idJson === $scope.currentTabelaHoraria.idJson;
      //   })
      //   .orderBy('posicao')
      //   .reject('_destroy')
      //   .value();
      // };

      // atualizaQuadroTabelaHoraria = function(){
      //   var quadroHorarioBuilder = new influunt.components.QuadroTabelaHorario($scope.dias, $scope.eventosNormais);
      //   $scope.agenda = quadroHorarioBuilder.calcula();
      // };

      // $scope.submitForm = function() {
      //   return $scope
      //     .submit($scope.objeto)
      //     .then(function(res) { $scope.objeto = res; })
      //     .catch(function(err) { $scope.errors = err; })
      //     .finally(influuntBlockui.unblock);
      // };

      // $scope.getErrosTabelaHoraria = function() {
      //   if ($scope.errors && Object.keys($scope.errors).length > 0 && Object.keys($scope.errors.versoesTabelasHorarias[$scope.currentVersaoTabelaHorariaIndex]).length > 0) {
      //     return _
      //     .chain($scope.errors.versoesTabelasHorarias[$scope.currentVersaoTabelaHorariaIndex].tabelaHoraria.aoMenosUmEvento)
      //     .map()
      //     .flatten()
      //     .value();
      //   } else {
      //     return [];
      //   }
      // };

      // getErrosEvento = function(evento) {
      //   var indexEvento = _.findIndex($scope.currentTabelaHoraria.eventos, {idJson: evento.idJson});
      //   return $scope.errors.versoesTabelasHorarias[$scope.currentVersaoTabelaHorariaIndex].tabelaHoraria.eventos[indexEvento];
      // };

      // $scope.tabTemErro = function(indice) {
      //   return $scope.tabErrors[$scope.tiposTabs[indice]];
      // };

      // atualizaErrosEventos = function() {
      //   $scope.tabErrors = {};
      //   $scope.currentErrosEventos = {};
      //   if ($scope.errors && Object.keys($scope.errors).length > 0 &&
      //       $scope.errors.versoesTabelasHorarias &&
      //       Object.keys($scope.errors.versoesTabelasHorarias[$scope.currentVersaoTabelaHorariaIndex]).length > 0 &&
      //       $scope.errors.versoesTabelasHorarias[$scope.currentVersaoTabelaHorariaIndex].tabelaHoraria.eventos &&
      //       Object.keys($scope.errors.versoesTabelasHorarias[$scope.currentVersaoTabelaHorariaIndex].tabelaHoraria.eventos).length > 0){

      //     _.each($scope.currentEventos, function(evento, index) {
      //       $scope.currentErrosEventos[index] = getErrosEvento(evento, index);
      //     });

      //     $scope.errors.versoesTabelasHorarias[0].tabelaHoraria.eventos.forEach(function(v, k) {
      //       var evento = _.find($scope.objeto.eventos, {idJson: $scope.currentTabelaHoraria.eventos[k].idJson});
      //       $scope.tabErrors[evento.tipo] = $scope.tabErrors[evento.tipo] || !!v;
      //     });
      //   }

      //   return $scope.currentErrosEventos;
      // };

      // $scope.$watch('errors', atualizaErrosEventos ,true);

      //Métodos para colorir tabela
      // $scope.getTableCell = function(v,i){
      //   console.log('getTableCell('+v+', '+i+')')
      //   return v[Math.floor(i)][0].state;
      // };

      // $scope.highlightEvento = function(v,i){
      //   console.log('highlightEvento('+v+', '+i+')')
      //   var table = v[Math.floor(i)][0].state;
      //   var tr = angular.element('.evento .' + table).parent();
      //   tr.addClass('light_' + table);
      // };

      // $scope.leaveHighlightEvento = function(v,i){
      //   console.log('leaveHighlightEvento('+v+', '+i+')')
      //   var table = v[Math.floor(i)][0].state;
      //   var tr = angular.element('.light_' + table);
      //   tr.removeClass('light_' + table);
      // };

      // $scope.$watch('eventosNormais',function(newObj){
      //   if($scope.eventosNormais && newObj){
      //     atualizaQuadroTabelaHoraria();
      //   }
      // },true);

      // $scope.podeSimular = function(controlador) {
      //   return SimulacaoService.podeSimular(controlador);
      // };






      var getTabela, findEvento, removeEvento, findEventoNormal, removeEventoNormal, findEventoRecorrente,
          removeEventoRecorrente, findEventoNaoRecorrente, removeEventoNaoRecorrente, makeDiff;

      getTabela = function(tabelaIdJson) {
        var tabela = _.cloneDeep(_.find($scope.objeto.tabelasHorarias, { idJson: tabelaIdJson }));
        tabela.eventos = _
          .chain(tabela.eventos)
          .map(function(e) { return _.find($scope.objeto.eventos, { idJson: e.idJson }) })
          .filter({ tipo: $scope.currentTipoEvento })
          .orderBy('posicao')
          .value();
        return _.cloneDeep(tabela);
      }

      findEventoNormal = function(e, eventos) {
        return _.find(eventos, {
          posicao: e.posicao,
          hora: e.hora,
          minuto: e.minuto,
          segundo: e.segundo,
          posicaoPlano: e.posicaoPlano,
          diaDaSemana: e.diaDaSemana,
          tipo: e.tipo
        });
      };

      removeEventoNormal = function(e, eventos) {
        return _.remove(eventos, {
          posicao: e.posicao,
          hora: e.hora,
          minuto: e.minuto,
          segundo: e.segundo,
          posicaoPlano: e.posicaoPlano,
          diaDaSemana: e.diaDaSemana,
          tipo: e.tipo
        });
      };

      findEventoRecorrente = function(e, eventos) {
        return _.find(eventos, {
          posicao: e.posicao,
          dataDiaMes: e.dataDiaMes,
          hora: e.hora,
          minuto: e.minuto,
          segundo: e.segundo,
          posicaoPlano: e.posicaoPlano,
          tipo: e.tipo
        });
      };

      removeEventoRecorrente = function(e, eventos) {
        return _.remove(eventos, {
          posicao: e.posicao,
          dataDiaMes: e.dataDiaMes,
          hora: e.hora,
          minuto: e.minuto,
          segundo: e.segundo,
          posicaoPlano: e.posicaoPlano,
          tipo: e.tipo
        });
      };

      findEventoNaoRecorrente = function(e, eventos) {
        return _.find(eventos, {
          posicao: e.posicao,
          nome: e.nome,
          dataDiaMesAno: e.dataDiaMesAno,
          hora: e.hora,
          minuto: e.minuto,
          segundo: e.segundo,
          posicaoPlano: e.posicaoPlano,
          tipo: e.tipo
        });
      };

      removeEventoNaoRecorrente = function(e, eventos) {
        return _.remove(eventos, {
          posicao: e.posicao,
          nome: e.nome,
          dataDiaMesAno: e.dataDiaMesAno,
          hora: e.hora,
          minuto: e.minuto,
          segundo: e.segundo,
          posicaoPlano: e.posicaoPlano,
          tipo: e.tipo
        });
      };



      findEvento = function(e, eventos) {
        switch($scope.currentTipoEvento) {
          case ESPECIAL_RECORRENTE:
            return findEventoRecorrente(e, eventos);
            break;
          case ESPECIAL_NAO_RECORRENTE:
            return findEventoNaoRecorrente(e, eventos);
            break;
          default:
            return findEventoNormal(e, eventos);
            break;
        }
      };

      removeEvento = function(e, eventos) {
        switch($scope.currentTipoEvento) {
          case ESPECIAL_RECORRENTE:
            return removeEventoRecorrente(e, eventos);
            break;
          case ESPECIAL_NAO_RECORRENTE:
            return removeEventoNaoRecorrente(e, eventos);
            break;
          default:
            return removeEventoNormal(e, eventos);
            break;
        }
      };

      // tabela1: tabela base
      // tabela2: tabela a ser comparada
      makeDiff = function(tabela1IdJson, tabela2IdJson) {
        $scope.tabela1 = getTabela(tabela1IdJson);
        console.log('tabela1: ', $scope.tabela1)
        $scope.tabela2 = getTabela(tabela2IdJson);
        console.log('tabela2: ', $scope.tabela2)
        $scope.removidos = _.cloneDeep(_.filter($scope.tabela1.eventos, { tipo: $scope.currentTipoEvento }));
        $scope.adicionados = _.cloneDeep(_.filter($scope.tabela2.eventos, { tipo: $scope.currentTipoEvento }));


        _.each($scope.tabela1.eventos, function(e1) {
          if (!!findEvento(e1, $scope.tabela2.eventos)) {
            removeEvento(e1, $scope.removidos);
            removeEvento(e1, $scope.adicionados);
          }
        });

        $scope.eventos1 = [];
        $scope.eventos2 = [];

        _.each($scope.tabela1.eventos, function(e) {
          if (!!findEvento(e, $scope.removidos)) {
            e.status = 'removido';
          } else {
            e.status = 'inalterado';
          }
          $scope.eventos1.push(e);
        });

        _.each($scope.tabela2.eventos, function(e) {
          if (!!findEvento(e, $scope.adicionados)) {
            e.status = 'adicionado';
          } else {
            e.status = 'inalterado';
          }
          $scope.eventos2.push(e);
        });

        // debugger
      };
    }]);
