'use strict';

/**
 * @ngdoc function
 * @name influuntApp.controller:TabelaHorariosCtrl
 * @description
 * # HorariosCtrl
 * Controller of the influuntApp
 */
angular.module('influuntApp')
  .controller('TabelaHorariosDiffCtrl', ['$controller', '$scope', '$stateParams', 'Restangular', '$filter',
                                         'influuntBlockui', 'TabelaHorariaService', 'HorariosService',
    function ($controller, $scope, $stateParams, Restangular, $filter,
              influuntBlockui, TabelaHorariaService, HorariosService) {


      $controller('HistoricoCtrl', {$scope: $scope});
      $scope.inicializaResourceHistorico('tabelas_horarias');

      var getTabela, findEvento, removeEvento, findEventoNormal, removeEventoNormal, findEventoRecorrente,
          removeEventoRecorrente, findEventoNaoRecorrente, removeEventoNaoRecorrente, makeDiff;

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
        var id = $stateParams.id;
        $scope.versaoBaseIdJson = $stateParams.versaoIdJson;

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

            $scope.selecionaTipoEvento(0);
          })
          .finally(influuntBlockui.unblock);
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
        makeDiff();
      };

      getTabela = function(tabelaIdJson) {
        var tabela = _.cloneDeep(_.find($scope.objeto.tabelasHorarias, { idJson: tabelaIdJson }));
        tabela.eventos = _
          .chain(tabela.eventos)
          .map(function(e) { return _.find($scope.objeto.eventos, { idJson: e.idJson }); })
          .filter({ tipo: $scope.currentTipoEvento })
          .orderBy('posicao')
          .value();
        return _.cloneDeep(tabela);
      };

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
          case ESPECIAL_NAO_RECORRENTE:
            return findEventoNaoRecorrente(e, eventos);
          default:
            return findEventoNormal(e, eventos);
        }
      };

      removeEvento = function(e, eventos) {
        switch($scope.currentTipoEvento) {
          case ESPECIAL_RECORRENTE:
            return removeEventoRecorrente(e, eventos);
          case ESPECIAL_NAO_RECORRENTE:
            return removeEventoNaoRecorrente(e, eventos);
          default:
            return removeEventoNormal(e, eventos);
        }
      };

      makeDiff = function() {
        $scope.versaoBase = _.find($scope.objeto.versoesTabelasHorarias, { idJson: $scope.versaoBaseIdJson });
        $scope.tabelaBase = getTabela($scope.versaoBase.tabelaHoraria.idJson);
        $scope.tabelaOrigem = getTabela($scope.versaoBase.tabelaHorariaOrigem.idJson);
        $scope.versaoOrigem = _.find($scope.objeto.versoesTabelasHorarias, { idJson: $scope.tabelaOrigem.versaoTabelaHoraria.idJson });

        var removidos = _.cloneDeep(_.filter($scope.tabelaOrigem.eventos, { tipo: $scope.currentTipoEvento }));
        var adicionados = _.cloneDeep(_.filter($scope.tabelaBase.eventos, { tipo: $scope.currentTipoEvento }));

        // Os eventos que estão nas duas tabelas permaneceram inalterados,
        // e são removidos das lista dos removidos e dos adicionados.
        _.each($scope.tabelaOrigem.eventos, function(e1) {
          if (!!findEvento(e1, $scope.tabelaBase.eventos)) {
            removeEvento(e1, removidos);
            removeEvento(e1, adicionados);
          }
        });

        $scope.eventosOrigem = [];
        $scope.eventosBase = [];

        // os eventos da versão origem que sobraram na lista dos removidos foram
        // removidos (ou modificados) na versão seguinte.
        _.each($scope.tabelaOrigem.eventos, function(e) {
          e.status = !!findEvento(e, removidos) ? 'removido' : 'inalterado';
          $scope.eventosOrigem.push(e);
        });

        // os eventos da versão base que sobraram na lista dos adicionados foram
        // adicionados (ou modificados) na versão na versão anterior.
        _.each($scope.tabelaBase.eventos, function(e) {
          e.status = !!findEvento(e, adicionados) ? 'adicionado' : 'inalterado';
          $scope.eventosBase.push(e);
        });
      };
    }]);
