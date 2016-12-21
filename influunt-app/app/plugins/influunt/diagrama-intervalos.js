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
        var estagioPlanoAtual, estagioAtual, tempoVerde, estagioAnterior, grupo, tabelaEntreVerde, transicao,
            tabelaEntreVerdesTransicao, tempoAmarelo, tempoVermelhoIntermitente, tempoAtrasoGrupo, tempoVermelhoLimpeza,
            tempoAmareloOuVermelhoIntermitente, tempoEntreVerde, posicao, grupoSemaforicoPlano;

        // cria uma matriz de tamanho quantidadeGruposSemaforicos x tempoCiclo, inicializada com -1 em todos os campos.
        var diagrama = _.times(plano.quantidadeGruposSemaforicos, function() { return _.times(plano.tempoCiclo, _.constant(-1)); });

        var tempoCiclo = 0;
        var entreverde = 0;
        var temposEntreverdes;
        var dados;
        for (i = 0; i < plano.estagiosPlanos.length; i++) {
          estagioPlanoAtual = plano.estagiosPlanos[i];
          estagioAtual = estagioPlanoAtual.estagio;
          tempoVerde = estagioPlanoAtual.tempoVerde || valoresMinimos.verdeMin;
          estagioAnterior = this.estagioAnterior(plano.estagiosPlanos,i).estagio;
          temposEntreverdes = {};
          if (estagioAtual.idJson !== estagioAnterior.idJson) {
            for(j = 0; j < estagioAnterior.gruposSemaforicos.length; j++){
              grupo = estagioAnterior.gruposSemaforicos[j];
              grupoSemaforicoPlano = _.find(plano.gruposSemaforicosPlanos, { grupoSemaforico: { idJson: grupo.idJson } });
              dados = {};
              if (grupoSemaforicoPlano.ativado !== false && !_.find(estagioAtual.gruposSemaforicos, {'id': grupo.id})){
                tabelaEntreVerde = _.find(grupo.tabelasEntreVerdes, {'posicao': plano.posicaoTabelaEntreVerde});
                transicao = _.find(grupo.transicoes, {'origem': {'idJson': estagioAnterior.idJson}, 'destino': {'idJson': estagioAtual.idJson}});
                tabelaEntreVerdesTransicao = _.find(transicao.tabelaEntreVerdesTransicoes, {'tabelaEntreVerdes': {'idJson': tabelaEntreVerde.idJson}});
                tempoAmarelo = parseInt(tabelaEntreVerdesTransicao.tempoAmarelo) || 0;
                tempoVermelhoIntermitente = parseInt(tabelaEntreVerdesTransicao.tempoVermelhoIntermitente) || 0;
                tempoVermelhoLimpeza = parseInt(tabelaEntreVerdesTransicao.tempoVermelhoLimpeza) || 0;


                dados.tempoAtrasoGrupo = parseInt(tabelaEntreVerdesTransicao.tempoAtrasoGrupo) || 0;
                dados.tempoAmareloOuVermelhoIntermitente = grupo.tipo === 'VEICULAR' ? tempoAmarelo : tempoVermelhoIntermitente;
                dados.tempoEntreVerde = dados.tempoAmareloOuVermelhoIntermitente + tempoVermelhoLimpeza;
                dados.posicao = plano.posicaoGruposSemaforicos['G' + grupo.posicao];
                entreverde = Math.max(entreverde, dados.tempoEntreVerde);
              }
              temposEntreverdes[grupo.posicao] = dados;
            }

            for(j = 0; j < estagioAnterior.gruposSemaforicos.length; j++){
              grupo = estagioAnterior.gruposSemaforicos[j];
              grupoSemaforicoPlano = _.find(plano.gruposSemaforicosPlanos, { grupoSemaforico: { idJson: grupo.idJson } });
              dados = temposEntreverdes[grupo.posicao];

              if (grupoSemaforicoPlano.ativado !== false && !_.find(estagioAtual.gruposSemaforicos, {'id': grupo.id})){
                if (dados.tempoEntreVerde < entreverde) {
                  dados.tempoAtrasoGrupo = Math.max(dados.tempoAtrasoGrupo, (entreverde - dados.tempoEntreVerde));
                }
                
                if(dados.tempoAtrasoGrupo > 0){
                  for(t = tempoCiclo; t < tempoCiclo + dados.tempoAtrasoGrupo; t++){
                    diagrama[dados.posicao][t] = VERDE;
                  }
                }
                for(t = tempoCiclo + dados.tempoAtrasoGrupo; t < tempoCiclo + dados.tempoAmareloOuVermelhoIntermitente + dados.tempoAtrasoGrupo; t++){
                  diagrama[dados.posicao][t] = grupo.tipo === 'VEICULAR' ? AMARELO : VERMELHO_INTERMITENTE;
                }
                for(t = tempoCiclo + dados.tempoAmareloOuVermelhoIntermitente + dados.tempoAtrasoGrupo; t < tempoCiclo + dados.tempoEntreVerde + dados.tempoAtrasoGrupo; t++){
                  diagrama[dados.posicao][t] = VERMELHO_LIMPEZA;
                }
              }
            }
          }

          for(j = 0; j < estagioAtual.gruposSemaforicos.length; j++){
            grupo = estagioAtual.gruposSemaforicos[j];
            var inicio;
            if (!_.find(estagioAnterior.gruposSemaforicos, {'id': grupo.id})){
              transicao = _.find(grupo.transicoesComGanhoDePassagem, {'origem': {'idJson': estagioAnterior.idJson}, 'destino': {'idJson': estagioAtual.idJson}});
              tempoAtrasoGrupo = parseInt(_.get(transicao, 'tempoAtrasoGrupo', 0));
              inicio = entreverde + tempoCiclo - tempoAtrasoGrupo;
            } else {
              inicio = tempoCiclo;
            }
            for (t = inicio; t < tempoCiclo + entreverde + tempoVerde; t++) {
              diagrama[plano.posicaoGruposSemaforicos['G' + grupo.posicao]][t] = VERDE;
            }
          }

          tempoCiclo += entreverde + (tempoVerde || 0);
          estagios.push({id: UUID.generate(), posicao: estagioAtual.posicao, duracao: entreverde + tempoVerde, gruposSemaforicos: estagioAtual.gruposSemaforicos});
          entreverde = 0;
        }

        var possuiConflito = function(g1, g2, verdesConflitantes) {
          var verdeConflitante = _.find(verdesConflitantes, { origem: { idJson: g1.idJson }, destino: { idJson: g2.idJson } }) ||
                                 _.find(verdesConflitantes, { origem: { idJson: g2.idJson }, destino: { idJson: g1.idJson } });
          return !!verdeConflitante;
        };

        var possuiGrupoSemaforico = function(estagio, grupo) {
          return _.findIndex(estagio.gruposSemaforicos, { posicao: grupo.posicao }) > -1;
        };

        var self = this;
        var atrasoDeGrupoAutomatico = function() {
          var houveConflito = false;

          var tempoTotalEstagiosAteAgora = 0;
          for (var estagioIndex = 0; estagioIndex < estagios.length; estagioIndex++) {
            estagioAtual = estagios[estagioIndex];
            var proximoEstagio = estagios[(estagioIndex + 1) % estagios.length];
            for (var instanteNoCiclo = tempoTotalEstagiosAteAgora; instanteNoCiclo < tempoTotalEstagiosAteAgora + estagioAtual.duracao; instanteNoCiclo++) {
              var conflitos = [];

              // Primeiro percorre todos os grupos semafóricos no mesmo instante no ciclo
              // coletando todos os grupos que estão em verde ou entreverde ao mesmo tempo.
              for(grupo = 0; grupo < diagrama.length; grupo++) {
                if (diagrama[grupo][instanteNoCiclo] === VERDE || ENTREVERDES.indexOf(diagrama[grupo][instanteNoCiclo]) > -1) {
                  conflitos.push(grupo);
                }
              }

              for (var k = 0; k < conflitos.length; k++) {
                var grupoA = self.plano.gruposSemaforicosPlanos[conflitos[k]].grupoSemaforico;
                for (j = k+1; j < conflitos.length; j++) {
                  var grupoB = self.plano.gruposSemaforicosPlanos[conflitos[j]].grupoSemaforico;

                  // Para cada par de grupos que podem estar em conflito, verifica se realmente
                  // há conflito entre eles.
                  var haGruposConflitantes = possuiConflito(grupoA, grupoB, self.plano.verdesConflitantesPlano);
                  houveConflito = houveConflito || haGruposConflitantes;

                  // Se o par de grupos semafóricos (A e B) são conflitantes, verifica se a próxima transição do grupo
                  // que está em verde é de perda de passagem ou não. Se não for perda de passagem (grupo continua
                  // verde no estágio seguinte), reduz o tempo de verde. Se for perda de passagem, "empurra" o verde,
                  // aumentando o tempo de vermelho (que nesse ponto ainda é INDEFINIDO).
                  if (haGruposConflitantes) {
                    if (possuiGrupoSemaforico(estagioAtual, grupoA)) {
                      // grupoA está verde
                      if (possuiGrupoSemaforico(proximoEstagio, grupoA)) {
                        // grupoA continua verde no estagio seguinte
                        diagrama[conflitos[k]].splice(instanteNoCiclo, 1, INDEFINIDO);
                      } else {
                        // grupoA passou p/ vermelho
                        diagrama[conflitos[k]].splice(instanteNoCiclo, 0, INDEFINIDO);
                      }
                    } else if (possuiGrupoSemaforico(estagioAtual, grupoB)) {
                      // grupoB está verde
                      if (possuiGrupoSemaforico(proximoEstagio, grupoB)) {
                        // grupoB continua verde no estagio seguinte
                        diagrama[conflitos[j]].splice(instanteNoCiclo, 1, INDEFINIDO);
                      } else {
                        // grupoB passou p/ vermelho
                        diagrama[conflitos[j]].splice(instanteNoCiclo, 0, INDEFINIDO);
                      }
                    }
                  }
                }
              }

            }
            tempoTotalEstagiosAteAgora += estagioAtual.duracao;
          }

          _.each(diagrama, function(grupo) {
            if (grupo.length > tempoCiclo) {
              for (var i = grupo.length - 1; i >= tempoCiclo; i--) {
                if (grupo[i] === INDEFINIDO) {
                  grupo.splice(i, 1);
                } else if (grupo[i] === VERDE) {
                  grupo.splice(0, 0, VERDE);
                  grupo.splice(grupo.length - 1, 1);
                }
              }
              _.eachRight(grupo, function(instanteNoCiclo, i) {
                if (instanteNoCiclo === INDEFINIDO && grupo.length > tempoCiclo) {
                  grupo.splice(i, 1);
                }
              });
            }
          });

          return houveConflito;
        };

        var houveConflito = true;
        for (i = 0; i <= estagios.length && houveConflito; i++) {
          houveConflito = atrasoDeGrupoAutomatico();
        }

        //Inserindo o vermelho
        for (i = 0; i < diagrama.length; i++) {
          for (j = 0; j < tempoCiclo; j++) {
            if (diagrama[i][j] === -1) {
              diagrama[i][j] = VERMELHO;
            }
          }
        }

        diagrama = _.map(diagrama, function(grupo) {
          for (var i = tempoCiclo; i < self.plano.tempoCiclo; i++) {
            grupo[i] = INDEFINIDO;
          }
          return grupo;
        });

        if (houveConflito) {
          return {erros: ['Existem conflitos de verdes que não podem ser resolvidos automaticamente.']};
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
        for (i = 0; i < diagrama.length; i++) {
          resultado.gruposSemaforicos[i] = resultado.gruposSemaforicos[i] || {};
          resultado.gruposSemaforicos[i].posicao = (i + 1);
          resultado.gruposSemaforicos[i].labelPosicao = plano.labelsGruposSemaforicos[i];
          resultado.gruposSemaforicos[i].intervalos = resultado.gruposSemaforicos[i].intervalos || [];
          intervalos = resultado.gruposSemaforicos[i].intervalos;
          var status = diagrama[i][0];
          var duracao = 1;

          if (!tempoCiclo || diagrama[i].length > tempoCiclo) {
            return {erros: ['Tempo de Ciclo é diferente da soma dos tempos dos estágios.']};
          }

          for (j = 0; j < diagrama[i].length; j++) {
            if (j+1 >= diagrama[i].length) {
              intervalos.push({status: status === null ? VERMELHO : status, duracao: duracao });
            } else if (diagrama[i][j] !== diagrama[i][j+1]) {
              intervalos.push({status: status === null ? VERMELHO : status, duracao: duracao });
              duracao = 1;
              status = diagrama[i][j+1];
            } else {
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
