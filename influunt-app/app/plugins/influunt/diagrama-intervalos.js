'use strict';

var influunt;
(function (influunt) {
  var components;
  (function (components) {
    var DiagramaIntervalos = (function () {
      var VERDE = 1;
      var AMARELO = 2;
      var VERMELHO = 3;
      var VERMELHO_INTERMITENTE = 4;
      var VERMELHO_LIMPEZA = 6;
      var INDEFINIDO = -1;
      var ENTREVERDES = [AMARELO, VERMELHO_INTERMITENTE, VERMELHO_LIMPEZA];

      function DiagramaIntervalos(plano, valoresMinimos) {
        this.plano = plano;
        this.valoresMinimos = valoresMinimos;

      }
      DiagramaIntervalos.prototype.calcula = function () {
        var plano = this.plano;
        var valoresMinimos = this.valoresMinimos;
        var estagios = [];
        var i = 0;
        var j = 0;
        var t = 0;
        var estagioPlanoAtual, estagioAtual, tempoVerde, estagioAnterior, grupo, tabelaEntreVerde, transicao, tabelaEntreVerdesTransicao, tempoAmarelo, tempoVermelhoIntermitente, tempoAtrasoGrupo, tempoVermelhoLimpeza, tempoAmareloOuVermelhoIntermitente, tempoEntreVerde, posicao;

        // cria uma matriz de tamanho quantidadeGruposSemaforicos x tempoCiclo, inicializada com -1 em todos os campos.
        var diagrama = _.times(plano.quantidadeGruposSemaforicos, function() { return _.times(plano.tempoCiclo, _.constant(-1)); });
        var tempoCiclo = 0;
        var instante = 0;
        for (i = 0; i < plano.estagiosPlanos.length; i++) {
          estagioPlanoAtual = plano.estagiosPlanos[i];
          estagioAtual = estagioPlanoAtual.estagio;
          tempoVerde = estagioPlanoAtual.tempoVerde || valoresMinimos.verdeMin;
          estagioAnterior = this.estagioAnterior(plano.estagiosPlanos,i).estagio;

          if (estagioAtual.idJson !== estagioAnterior.idJson) {
            for(j = 0; j < estagioAnterior.gruposSemaforicos.length; j++){
              grupo = estagioAnterior.gruposSemaforicos[j];
              if(!_.find(estagioAtual.gruposSemaforicos, {'id': grupo.id})){
                tabelaEntreVerde = _.find(grupo.tabelasEntreVerdes, {'posicao': plano.posicaoTabelaEntreVerde});
                transicao = _.find(grupo.transicoes, {'origem': {'idJson': estagioAnterior.idJson}, 'destino': {'idJson': estagioAtual.idJson}});
                tabelaEntreVerdesTransicao = _.find(transicao.tabelaEntreVerdesTransicoes, {'tabelaEntreVerdes': {'idJson': tabelaEntreVerde.idJson}});
                tempoAmarelo = tabelaEntreVerdesTransicao.tempoAmarelo ? parseInt(tabelaEntreVerdesTransicao.tempoAmarelo) : 0;
                tempoVermelhoIntermitente = tabelaEntreVerdesTransicao.tempoVermelhoIntermitente ? parseInt(tabelaEntreVerdesTransicao.tempoVermelhoIntermitente) : 0;

                // ### PONTO DE INTERESSE ###
                tempoAtrasoGrupo = parseInt(tabelaEntreVerdesTransicao.tempoAtrasoGrupo) || 0;
                tempoVermelhoLimpeza = parseInt(tabelaEntreVerdesTransicao.tempoVermelhoLimpeza) || 0;

                tempoAmareloOuVermelhoIntermitente = grupo.tipo === 'VEICULAR' ? tempoAmarelo : tempoVermelhoIntermitente;
                tempoEntreVerde = tempoAmareloOuVermelhoIntermitente + tempoVermelhoLimpeza;
                posicao = plano.posicaoGruposSemaforicos['G' + grupo.posicao];

                // ### PONTO DE INTERESSE ###
                if(tempoAtrasoGrupo > 0){
                  for(t = tempoCiclo; t < tempoCiclo + tempoAtrasoGrupo; t++){
                    diagrama[posicao][t] = VERDE;
                  }
                }
                for(t = tempoCiclo + tempoAtrasoGrupo; t < tempoCiclo + tempoAmareloOuVermelhoIntermitente + tempoAtrasoGrupo; t++){
                  diagrama[posicao][t] = grupo.tipo === 'VEICULAR' ? AMARELO : VERMELHO_INTERMITENTE;
                }
                for(t = tempoCiclo + tempoAmareloOuVermelhoIntermitente + tempoAtrasoGrupo; t < tempoCiclo + tempoEntreVerde + tempoAtrasoGrupo; t++){
                  diagrama[posicao][t] = VERMELHO_LIMPEZA;
                }
                instante = Math.max(instante, tempoEntreVerde);
              }
            }
          }

          for(j = 0; j < estagioAtual.gruposSemaforicos.length; j++){
             grupo = estagioAtual.gruposSemaforicos[j];
             var inicio;
             if(!_.find(estagioAnterior.gruposSemaforicos, {'id': grupo.id})){
               transicao = _.find(grupo.transicoesComGanhoDePassagem, {'origem': {'idJson': estagioAnterior.idJson}, 'destino': {'idJson': estagioAtual.idJson}});
               tempoAtrasoGrupo = transicao && transicao.tempoAtrasoGrupo ? parseInt(transicao.tempoAtrasoGrupo) : 0;
               inicio = instante + tempoCiclo - tempoAtrasoGrupo;
             }else{
               inicio = tempoCiclo;
             }
             for(t = inicio; t < tempoCiclo + instante + tempoVerde; t++){
               diagrama[plano.posicaoGruposSemaforicos['G' + grupo.posicao]][t] = VERDE;
             }
          }

          tempoCiclo += instante + (tempoVerde || 0);

          estagios.push({id: UUID.generate(), posicao: estagioAtual.posicao, duracao: instante + tempoVerde, gruposSemaforicos: estagioAtual.gruposSemaforicos});
          instante = 0;
        }

        var possuiConflito = function(g1, g2, verdesConflitantes) {
          var verdeConflitante = _.find(verdesConflitantes, { origem: { idJson: g1.idJson }, destino: { idJson: g2.idJson } }) ||
                                 _.find(verdesConflitantes, { origem: { idJson: g2.idJson }, destino: { idJson: g1.idJson } });
          return !!verdeConflitante;
        };

        var buscaVerdes = function(instante, numGrupos) {
          var verdes = [];
          for (var i = 0; i < numGrupos; i++) {
            if (diagrama[grupo][instante] === VERDE) {
              verdes.push(grupo);
            }
          }
          return verdes;
        };

        var getEstagioIndex = function(instanteNoCiclo) {
          var estagio = null;
          var duracaoTotal = 0;
          _.some(estagios, function(e, i) {
            duracaoTotal += e.duracao;
            if (instanteNoCiclo < duracaoTotal) {
              estagio = i;
              return true;
            }
          });
          return estagio;
        };

        // var getProximoEstagio = function(estagioAnteriorIndex) {
        //   if (_.indexOf(estagios, estagioAnterior) === estagios.length - 1)

        // }

        // ### PONTO DE INTERESSE ###
        var tempoTotalEstagiosAteAgora = 0;
        for (var estagioIndex = 0; estagioIndex < estagios.length; estagioIndex++) {
          var estagioAtual = estagios[estagioIndex];
          var proximoEstagio = estagios[(estagioIndex + 1) % estagios.length];
          console.log('estagioAtual: ', estagioAtual)
          for (var instanteNoCiclo = tempoTotalEstagiosAteAgora; instanteNoCiclo < tempoTotalEstagiosAteAgora + estagioAtual.duracao; instanteNoCiclo++) {
            console.log('instanteNoCiclo: ', instanteNoCiclo);
            console.log('duracao: ', tempoTotalEstagiosAteAgora + estagioAtual.duracao)

            var verdes = [],
                entreverdes = [];

            for(grupo = 0; grupo < diagrama.length; grupo++) {
              if (diagrama[grupo][instanteNoCiclo] === VERDE) {
                verdes.push(grupo);
              }
              else if (ENTREVERDES.indexOf(diagrama[grupo][instanteNoCiclo]) > -1) {
                entreverdes.push(grupo);
              }
              else if (diagrama[grupo][instanteNoCiclo] === INDEFINIDO && diagrama[grupo].length > tempoCiclo) {
                // diagrama[grupo].splice(instanteNoCiclo, diagrama[grupo].length - tempoCiclo);
              }
            }

            if (instanteNoCiclo === 75)
              debugger

            for (var k = 0; k < verdes.length; k++) {
              var gVerde = this.plano.gruposSemaforicosPlanos[verdes[k]].grupoSemaforico;

              for (j = 0; j < entreverdes.length; j++) {
                var gEntreverde = this.plano.gruposSemaforicosPlanos[entreverdes[j]].grupoSemaforico;
                var conflitante = possuiConflito(gVerde, gEntreverde, this.plano.verdesConflitantesPlano);
                if (conflitante) {
                  // debugger;
                  if (_.findIndex(estagioAtual.gruposSemaforicos, { posicao: verdes[k]+1 }) > -1 && _.findIndex(proximoEstagio.gruposSemaforicos, { posicao: verdes[k]+1 }) > -1) {
                    // não é perda de passagem
                    diagrama[verdes[k]].splice(instanteNoCiclo, 1, INDEFINIDO);
                  } else {
                    // perda de passagem
                    diagrama[verdes[k]].splice(instanteNoCiclo, 0, INDEFINIDO);
                  }

                }
              }
            }

          }
          tempoTotalEstagiosAteAgora += estagioAtual.duracao;
        }


        // for(var instanteNoCiclo = 0; instanteNoCiclo < tempoCiclo; instanteNoCiclo++) {
        //   var verdes = [],
        //       entreverdes = [];

        //   for(grupo = 0; grupo < diagrama.length; grupo++) {
        //     if (diagrama[grupo][instanteNoCiclo] === VERDE) {
        //       verdes.push(grupo);
        //     }
        //     else if (ENTREVERDES.indexOf(diagrama[grupo][instanteNoCiclo]) > -1) {
        //       entreverdes.push(grupo);
        //     }
        //     else if (diagrama[grupo][instanteNoCiclo] === INDEFINIDO && diagrama[grupo].length > tempoCiclo) {
        //       // diagrama[grupo].splice(instanteNoCiclo, diagrama[grupo].length - tempoCiclo);
        //     }
        //   }
        //   for (var k = 0; k < verdes.length; k++) {
        //     var gVerde = this.plano.gruposSemaforicosPlanos[verdes[k]].grupoSemaforico;

        //     for (j = 0; j < entreverdes.length; j++) {
        //       var gEntreverde = this.plano.gruposSemaforicosPlanos[entreverdes[j]].grupoSemaforico;
        //       var conflitante = possuiConflito(gVerde, gEntreverde, this.plano.verdesConflitantesPlano);
        //       if (conflitante) {
        //         debugger;
        //         diagrama[verdes[k]].splice(instanteNoCiclo, 0, INDEFINIDO);
        //       }
        //     }
        //   }
        // }

        console.log(diagrama);
        debugger

        _.each(diagrama, function(grupo, grupoindex) {
          if (grupo.length > tempoCiclo) {
            _.eachRight(grupo, function(instanteNoCiclo, i) {
              var estagioIndex = getEstagioIndex(instanteNoCiclo);
              var estagio = estagios[estagioIndex];
              var proximoEstagioIndex = estagioIndex + 1 === estagios.length ? 0 : estagioIndex + 1;
              var proximoEstagio = estagios[proximoEstagioIndex];
              if (instanteNoCiclo === INDEFINIDO && grupo.length > tempoCiclo) {
                grupo.splice(i, 1);
              }
            });
          }
        });

        //Inserindo o vermelho
        for(i = 0; i < diagrama.length; i++){
          for(j = 0; j < tempoCiclo; j++){
            if(diagrama[i][j] === -1){
              diagrama[i][j] = VERMELHO;
            }
          }
        }

        return this.gerarDiagramaIntervalo(diagrama, estagios, plano.tempoCiclo);
      };
      DiagramaIntervalos.prototype.gerarDiagramaIntervalo = function (diagrama, estagios, tempoCiclo) {
        var plano = this.plano;
        var size = _.chain(diagrama).map(function(i) { return i.length; }).max().value();
        var i, j;
        var intervalos;
        diagrama.forEach(function(grupo) {
          for (i = grupo.length; i < size; i++) {
            grupo[i] = VERMELHO;
          }
        });
        var resultado = {gruposSemaforicos: [], estagios: estagios, erros: []};
        for(i = 0; i < diagrama.length; i++){
          resultado.gruposSemaforicos[i] = resultado.gruposSemaforicos[i] || {};
          resultado.gruposSemaforicos[i].posicao = (i + 1);
          resultado.gruposSemaforicos[i].labelPosicao = plano.labelsGruposSemaforicos[i];
          resultado.gruposSemaforicos[i].intervalos = resultado.gruposSemaforicos[i].intervalos || [];
          intervalos = resultado.gruposSemaforicos[i].intervalos;
          var status = diagrama[i][0];
          var duracao = 1;

          if(!tempoCiclo || diagrama[i].length > tempoCiclo){
            return {erros: ['Tempo de Ciclo é diferente da soma dos tempos dos estágios.']};
          }

          for(j = 0; j < diagrama[i].length; j++){
            if(j+1 >= diagrama[i].length){
              intervalos.push({status: status === null ? 3 : status, duracao: duracao });
            }else if(diagrama[i][j] !== diagrama[i][j+1]){
              intervalos.push({status: status === null ? 3 : status, duracao: duracao });
              duracao = 1;
              status = diagrama[i][j+1];
            }else{
              duracao++;
            }
          }
        }

        return resultado;

      };

      DiagramaIntervalos.prototype.estagioAnterior = function (lista,atual) {
        if (atual >= lista.length || atual < 0) {return null;}
        var posicao = ((atual -1) + lista.length) % lista.length;
        return lista[posicao];

      };

      return DiagramaIntervalos;
    }());
    components.DiagramaIntervalos = DiagramaIntervalos;
  })(components = influunt.components || (influunt.components = {}));
})(influunt || (influunt = {}));
