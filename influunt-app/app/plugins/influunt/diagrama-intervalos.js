'use strict';

function createArray(tam) {
  var arr = new Array(tam || 0), i = tam;

  if (arguments.length > 1) {
      var args = Array.prototype.slice.call(arguments, 1);
      while (i--) {
        arr[tam - 1 - i] = createArray.apply(this, args);
      }
  }

  return arr;
}


var influunt;
(function (influunt) {
  var components;
  (function (components) {
    var DiagramaIntervalos = (function () {
      function DiagramaIntervalos(plano, valoresMinimos) {
        this.plano = plano;
        this.valoresMinimos = valoresMinimos;
      }
      DiagramaIntervalos.prototype.calcula = function () {
        var plano = this.plano;
        var valoresMinimos = this.valoresMinimos;
        var diagrama = createArray(plano.quantidadeGruposSemaforicos, plano.tempoCiclo);
        var estagios = [];
        var i = 0;
        var j = 0;
        var t = 0;
        var estagioPlanoAtual, estagioAtual, tempoVerde, estagioAnterior, grupo, tabelaEntreVerde, transicao, tabelaEntreVerdesTransicao, tempoAmarelo, tempoVermelhoIntermitente, tempoAtrasoGrupo, tempoVermelhoLimpeza, tempoAmareloOuVermelhoIntermitente, tempoEntreVerde, posicao;
        for(i = 0; i < diagrama.length; i++){
          for(j = 0; j < diagrama[i].length; j++){
            diagrama[i][j] = -1;
          }
        }
        var tempoCiclo = 0;
        var instante = 0;
        for(i = 0; i < plano.estagiosPlanos.length; i++){
          estagioPlanoAtual = plano.estagiosPlanos[i];
          estagioAtual = estagioPlanoAtual.estagio;
          tempoVerde = estagioPlanoAtual.tempoVerde || valoresMinimos.verdeMin;
          estagioAnterior = this.estagioAnterior(plano.estagiosPlanos,i).estagio;

          if(estagioAtual.idJson !== estagioAnterior.idJson){
            for(j = 0; j < estagioAnterior.gruposSemaforicos.length; j++){
              grupo = estagioAnterior.gruposSemaforicos[j];
              if(!_.find(estagioAtual.gruposSemaforicos, {'id': grupo.id})){
                tabelaEntreVerde = _.find(grupo.tabelasEntreVerdes, {'posicao': plano.posicaoTabelaEntreVerde});
                transicao = _.find(grupo.transicoes, {'origem': {'idJson': estagioAnterior.idJson}, 'destino': {'idJson': estagioAtual.idJson}});
                tabelaEntreVerdesTransicao = _.find(transicao.tabelaEntreVerdesTransicoes, {'tabelaEntreVerdes': {'idJson': tabelaEntreVerde.idJson}});
                tempoAmarelo = tabelaEntreVerdesTransicao.tempoAmarelo ? parseInt(tabelaEntreVerdesTransicao.tempoAmarelo) : 0;
                tempoVermelhoIntermitente = tabelaEntreVerdesTransicao.tempoVermelhoIntermitente ? parseInt(tabelaEntreVerdesTransicao.tempoVermelhoIntermitente) : 0;
                tempoAtrasoGrupo = tabelaEntreVerdesTransicao.tempoAtrasoGrupo ? parseInt(tabelaEntreVerdesTransicao.tempoAtrasoGrupo) : 0;
                tempoVermelhoLimpeza = tabelaEntreVerdesTransicao.tempoVermelhoLimpeza ? parseInt(tabelaEntreVerdesTransicao.tempoVermelhoLimpeza) : 0;

                tempoAmareloOuVermelhoIntermitente = grupo.tipo === 'VEICULAR' ? tempoAmarelo : tempoVermelhoIntermitente;
                tempoEntreVerde = tempoAmareloOuVermelhoIntermitente + tempoVermelhoLimpeza;
                posicao = plano.posicaoGruposSemaforicos['G' + grupo.posicao];

                if(tempoAtrasoGrupo > 0){
                  for(t = tempoCiclo; t < tempoCiclo + tempoAtrasoGrupo; t++){
                    diagrama[posicao][t] = 1;
                  }
                }
                for(t = tempoCiclo + tempoAtrasoGrupo; t < tempoCiclo + tempoAmareloOuVermelhoIntermitente + tempoAtrasoGrupo; t++){
                  diagrama[posicao][t] = grupo.tipo === 'VEICULAR' ? 2 : 4;
                }
                for(t = tempoCiclo + tempoAmareloOuVermelhoIntermitente + tempoAtrasoGrupo; t < tempoCiclo + tempoEntreVerde + tempoAtrasoGrupo; t++){
                  diagrama[posicao][t] = 6;
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
               diagrama[plano.posicaoGruposSemaforicos['G' + grupo.posicao]][t] = 1;
             }
          }
          tempoCiclo += instante + (tempoVerde || 0);

          estagios.push({id: UUID.generate(), posicao: estagioAtual.posicao, duracao: instante + tempoVerde});
          instante = 0;
        }
        //Inserindo o vermelho
        for(i = 0; i < diagrama.length; i++){
          for(j = 0; j < tempoCiclo; j++){
            if(diagrama[i][j] === -1){
              diagrama[i][j] = 3;
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
            grupo[i] = 3;
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
              intervalos.push({status: status === null ? 3 : status ,duracao:duracao });
            }else if(diagrama[i][j] !== diagrama[i][j+1]){
              intervalos.push({status: status === null ? 3 : status ,duracao:duracao });
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
