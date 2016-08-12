'use strict';

/**
 * @ngdoc function
 * @name influuntApp.controller:TabelaHorariosCtrl
 * @description
 * # HorariosCtrl
 * Controller of the influuntApp
 */
angular.module('influuntApp')
  .controller('TabelaHorariosCtrl', ['$scope', '$state', '$timeout', 'Restangular', '$filter',
                           'validaTransicao', 'utilEstagios', 'toast', 'modoOperacaoService',
                           'influuntAlert', 'influuntBlockui',
    function ($scope, $state, $timeout, Restangular, $filter,
              validaTransicao, utilEstagios, toast, modoOperacaoService,
              influuntAlert, influuntBlockui) {

      var adicionaTabelaHorario, adicionaEvento, selecionaAnel, atualizaPlanos, atualizaEventos, inicializa, getTrocas, passada, intervalos, comparePrograma;
      var diagramaDebouncer = null;
      /**
       * Inicializa a tela de planos. Carrega os dados básicos da tela.
       */
      $scope.init = function() {
        var id = $state.params.id;
        Restangular.one('controladores', id).get().then(function(res) {
          $scope.objeto = res;
          $scope.agenda = {};
          $scope.intervalos = {};
          $scope.diasDaSemana = ['dom','seg','ter','qua','qui','sex','sab'];
          $scope.dias = [
            {
              label: "Todos",
              value: 'TODOS_OS_DIAS',
              dias: ['dom','seg','ter','qua','qui','sex','sab'],
              prioridade:11
            },
            {
              label: "Domingo",
              value: 'DOMINGO',
              dias: ['dom'],
              prioridade:7                
            },
            {
              label: "Segunda",
              value: 'SEGUNDA',
              dias: ['seg'] ,
              prioridade:6,                       
            },
            {
              label: "Terça",
              value: 'TERCA',
              dias: ['ter'],
              prioridade:5                          
            },
            {
              label: "Quarta",
              value: 'QUARTA',
              dias: ['qua'],
              prioridade:4,                          
            },
            {
              label: "Quinta",
              value: 'QUINTA',
              dias: ['qui'],
              prioridade:3                
            },
            {
              label: "Sexta",
              value: 'SEXTA',
              dias: ['sex'],
              prioridade:2                
            },
            {
              label: "Sábado",
              value: 'SABADO',
              dias: ['sab'],
              prioridade:1          
            },
            {
              label: "Sábado e Domingo",
              value: 'SABADO_A_DOMINGO',
              dias: ['dom','sab'],
              prioridade:8                
            },

            {
              label: "Segunda a Sexta",
              value: 'SEGUNDA_A_SEXTA',
              dias: ['seg','ter','qua','qui','sex'],
              prioridade:9                
            },
            {
              label: "Segunda a Sábado",
              value: 'SEGUNDA_A_SABADO',
              dias: ['seg','ter','qua','qui','sex','sab'],
              prioridade:10                
            }
          ]
          $scope.horarios = $scope.getTimes(24);

          $scope.tipoEventos = [
            {}
          ];

          $scope.objeto.aneis = _.orderBy($scope.objeto.aneis, ['posicao']);
          $scope.aneis = _.filter($scope.objeto.aneis, {ativo: true});
          $scope.aneis.forEach(function(anel) {
            if(!anel.tabelaHorario) {
              adicionaTabelaHorario(anel);
            }
          });
          $scope.selecionaAnelTabelaHorarios(0);
        });
      };
      
      $scope.selecionaAnelTabelaHorarios = function(index) {
        selecionaAnel(index);
        $scope.currentTabelaHorario = _.find($scope.objeto.tabelaHorarios, {idJson: $scope.currentAnel.tabelaHorario.idJson});
        atualizaEventos();
      };
      
      $scope.selecionaTipoEvento = function(index) {
        $scope.currentTipoEventoIndex = 0;
      };
      
      $scope.selecionaEvento = function(evento){
        $scope.currentEvento = evento;
      };
      
      $scope.getTimes = function(quantidade){
        return new Array(quantidade);
      }

      adicionaTabelaHorario = function(anel) {
        var tabelaHorario = {
          idJson: UUID.generate(),
          anel: { idJson: anel.idJson }
        };
        $scope.objeto.tabelaHorarios = $scope.objeto.tabelaHorarios || [];
        $scope.objeto.tabelaHorarios.push(tabelaHorario);
        
        anel.tabelaHorario = anel.tabelaHorario || {};
        anel.tabelaHorario.idJson = tabelaHorario.idJson;
        adicionaEvento(tabelaHorario);
        return tabelaHorario;
      };

      adicionaEvento = function(tabelaHorario){
        tabelaHorario.eventos = tabelaHorario.eventos || []
        var posicao = tabelaHorario.eventos.length + 1;
        var evento = {
          idJson: UUID.generate(),
          tabelaHorario: { idJson: tabelaHorario.idJson },
          posicao: posicao
        };
        $scope.objeto.eventos = $scope.objeto.eventos || []
        $scope.objeto.eventos.push(evento);

        tabelaHorario.eventos.push({idJson: evento.idJson});
        return evento;
      };
        
      selecionaAnel = function(index) {
        $scope.currentAnelIndex = index;
        $scope.currentAnel = $scope.aneis[$scope.currentAnelIndex];
        atualizaPlanos();
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

      atualizaEventos = function() {
        var ids = _.map($scope.currentTabelaHorario.eventos, 'idJson');
        $scope.currentEventos = _
          .chain($scope.objeto.eventos)
          .filter(function(e) {
            return ids.indexOf(e.idJson) >= 0;
          })
          .orderBy(['posicao'])
          .value();

          return $scope.currentEventos;
      };
      
      $scope.verificaAtualizacaoDeEventos = function(evento){
        //Adiciona novo evento caso todos os dados estejam preenchidos
        var indexUltimoEvento = $scope.currentEventos.length - 1;
        var index = _.findIndex($scope.currentEventos, {idJson: evento.idJson});
        if(evento.hora && evento.minuto){
          evento.horario = evento.hora + ':' + evento.minuto;
        }
        if(index === indexUltimoEvento && evento.diaDaSemana && evento.horario && evento.plano){
          adicionaEvento($scope.currentTabelaHorario);
          atualizaEventos();
        }
      };
      
      inicializa = function(){
        for(var minuto = 0; minuto < 4; minuto++){
          for(var hora = 0; hora < 24; hora++ ){
            $scope.diasDaSemana.forEach(function(dia){
              if($scope.agenda[dia] == undefined){
                $scope.agenda[dia] =  new Array(24);
                for(var i = 0; i < 24; i++){
                  $scope.agenda[dia][i] = new Array(4);
                }
              }
              $scope.agenda[dia][hora][minuto] = {state: 'unset'}
            })
          }
        }
      };
      
      getTrocas = function(programas){
        var hash = {};
        programas.forEach(function(programa){
          var hora = parseInt(programa.hora);
          var minuto = parseInt(programa.minuto / 15);
          var dia = _.find($scope.dias, {value: programa.dia});
          dia.dias.forEach(function(dia){
            hash[dia] = hash[dia] || {}
            hash[dia][hora] = hash[dia][hora] || {}
            if(hash[dia][hora][minuto]){
              if(comparePrograma(programa,hash[dia][hora][minuto]) < 0){
                hash[dia][hora][minuto] = programa
              }
            }else{
              hash[dia][hora][minuto] = programa
            }
          
          })
        })
        return hash;
      };
      
      passada = function(trocas,ultimo){
        if(Object.keys(trocas).length > 0){
          $scope.diasDaSemana.forEach(function(dia){
            for(var i = 0; i < 96; i++){
              var hora = Math.floor(i / 4);
              var minuto = i % 4;
              var slot = $scope.agenda[dia][hora][minuto];
        
              if(slot.state == 'unset'){
                if(trocas[dia] && trocas[dia][hora] && trocas[dia][hora][minuto]){
                  slot.state = trocas[dia][hora][minuto].class;
                  slot.index = trocas[dia][hora][minuto].index;
                  ultimo = slot.state;
                }else if(ultimo!=undefined){
                  slot.state = ultimo;
                }
              }
            }
          });
        }
        return ultimo;
      };
      
      intervalos = function(){
        $scope.intervalo = {};
      
        if($scope.agenda['dom'][0][0].state == 'unset'){
          return;
        }
      
        var currentIndex = undefined;
      
        $scope.diasDaSemana.forEach(function(dia){
            for(var i = 0; i < 96; i++){
              var hora = Math.floor(i / 4);
              var minuto = i % 4;
              var slotIndex = $scope.agenda[dia][hora][minuto].index;
              if(slotIndex == undefined){
                slotIndex = currentIndex;
              }

              if(currentIndex==undefined){
                currentIndex = slotIndex;
                $scope.intervalo[currentIndex] = $scope.intervalo[currentIndex] || [];
                $scope.intervalo[currentIndex].push([[dia,hora,minuto],[dia,hora,minuto]])
              }else{
                if(currentIndex != slotIndex){
                  currentIndex = slotIndex;
                  $scope.intervalo[currentIndex] = $scope.intervalo[currentIndex] || [];
                  $scope.intervalo[currentIndex].push([[dia,hora,minuto],[dia,hora,minuto]])
                }else{
                  var last = $scope.intervalo[currentIndex][$scope.intervalo[currentIndex].length - 1];
                  last[1][0]= dia;
                  last[1][1]= hora;
                  last[1][2]= minuto; 
                }
              }
            }
          })
      };
      
      comparePrograma = function (a, b) {
        var pa = JSON.parse(a.dia).prioridade;
        var pb = JSON.parse(b.dia).prioridade;
        var ha = parseInt(a.hora);
        var hb = parseInt(b.hora);
        var ma = parseInt(a.minuto);
        var mb = parseInt(b.minuto);
  
        if (pa < pb ) {
          return -1;
        }else if(pa > pb){
          return 0;
        }else{
          if(ha < hb){
            return -1;
          }else if(ha < hb){
            return 1
          }else{
            if(ma < mb){
              return -1;
            }else if(ma > mb){
              return -1;
            }
          }
        }
        return 0;
      };

      $scope.getTableCell = function(v,i){
        return v[Math.floor(i / 4)][i % 4].state
      }
      
      $scope.$watch('currentEventos',function(newObj,oldObj){
        if($scope.currentEventos && newObj){

          inicializa();
        
          var prs = []
          var index = 0;
          $scope.currentEventos.forEach(function(evento){
            var programa = _.cloneDeep(evento);
            if(programa.diaDaSemana && programa.horario){
              programa.hora = programa.horario.split(':')[0];
              programa.minuto = programa.minuto.split(':')[0];
              programa.dia = programa.diaDaSemana;
              programa.class = 'horarioColor' + (index+1);
              programa.index = index++;
              prs.push(programa);
            }
          });
          console.log(prs);
          var trocas = getTrocas(prs)
          var ultimo = undefined;
          ultimo = passada(trocas,ultimo);
          passada(trocas,ultimo);
          intervalos();
          console.log("Intervalos:" + JSON.stringify($scope.intervalo));
        }
      },true);
    }
  ]);
