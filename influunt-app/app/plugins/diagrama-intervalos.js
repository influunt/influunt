function createArray(length) {
    var arr = new Array(length || 0),
        i = length;

    if (arguments.length > 1) {
        var args = Array.prototype.slice.call(arguments, 1);
        while(i--) arr[length-1 - i] = createArray.apply(this, args);
    }
    

    return arr;
}


var influunt;
(function (influunt) {
  var components;
  (function (components) {
    var DiagramaIntervalos = (function () {
      function DiagramaIntervalos() {
      }
      DiagramaIntervalos.prototype.calcula = function (plano) {
        var diagrama = createArray(plano.quantidadeGruposSemaforicos,plano.tempoCiclo);
        var estagios = [];
        for(var i = 0; i < diagrama.length; i++){
          for(var j = 0; j < diagrama[i].length; j++){
            diagrama[i][j] = 3;
          }
        }
        var tempoCiclo = 0;
        var instante = 0;
        for(var i = 0; i < plano.sequenciaEstagios.length; i++){
          var estagioAtual = plano.sequenciaEstagios[i];
          var estagioAnterior = this.estagioAnterior(plano.sequenciaEstagios,i);
          for(var j = 0; j < estagioAnterior.gruposSemaforicos.length; j++){
            var grupo = estagioAnterior.gruposSemaforicos[j];
            var tabelaEntreVerde = _.find(grupo.tabelaEntreVerdes, {"posicao": plano.posicao});
            var transicao = _.find(grupo.transicoes, {"origem": {"id": estagioAnterior.id}, "destino": {"id": estagioAtual.id}})
            var tabelaEntreVerdesTransicao = _.find(transicao.tabelaEntreVerdesTransicoes, {"tabelaEntreVerdes": {"id": tabelaEntreVerde.id}})
            var tempo = grupo.tipo == 'VEICULAR' ? tabelaEntreVerdesTransicao.tempoAmarelo : tabelaEntreVerdesTransicao.tempoVermelhoIntermitente;
            var tempoAtrasoGrupo = tabelaEntreVerdesTransicao.tempoAtrasoGrupo == null ? 0 : tabelaEntreVerdesTransicao.tempoAtrasoGrupo
            var posicao = grupo.posicao - 1;
            if(!_.find(estagioAtual.gruposSemaforicos, {"id": grupo.id})){
              if(tempoAtrasoGrupo > 0){
                for(var t = tempoCiclo; t < tempoCiclo + tempoAtrasoGrupo; t++){
                  diagrama[posicao][t] = 1;
                }
              }
              for(var t = tempoCiclo + tempoAtrasoGrupo; t < tempoCiclo + tempo + tempoAtrasoGrupo; t++){
                diagrama[posicao][t] = grupo.tipo == 'VEICULAR' ? 2 : 4;
              }
              for(var t = tempoCiclo + tempo + tempoAtrasoGrupo; t < tempoCiclo +tempo + tabelaEntreVerdesTransicao.tempoVermelhoLimpeza + tempoAtrasoGrupo; t++){
                diagrama[posicao][t] = 3;
              }
            }else {
              for(var t = tempoCiclo; t < tempoCiclo +tempo + tabelaEntreVerdesTransicao.tempoVermelhoLimpeza + estagioAtual.tempoVerde; t++){
                diagrama[posicao][t] = 1;
              }
            }
            instante = Math.max(instante, tempo + tabelaEntreVerdesTransicao.tempoVermelhoLimpeza);
          }          
          for(var j = 0; j < estagioAtual.gruposSemaforicos.length; j++){
             var grupo = estagioAtual.gruposSemaforicos[j];
             for(var t = instante + tempoCiclo; t < tempoCiclo + instante + estagioAtual.tempoVerde; t++){
               diagrama[grupo.posicao - 1][t] = 1;
             }
          }
          tempoCiclo += instante + estagioAtual.tempoVerde;
          estagios.push({posicao: i, duracao: instante + estagioAtual.tempoVerde})
          instante = 0
        }
        return this.gerarDiagramaIntervalo(diagrama, estagios);
      };
      DiagramaIntervalos.prototype.gerarDiagramaIntervalo = function (diagrama, estagios) {
        var resultado = {gruposSemaforicos: [], estagios: estagios, erros: []}; 
        for(var i = 0; i < diagrama.length; i++){
          resultado.gruposSemaforicos[i] = resultado.gruposSemaforicos[i] || {};
          resultado.gruposSemaforicos[i].intervalos = resultado.gruposSemaforicos[i].intervalos || [];
          intervalos = resultado.gruposSemaforicos[i].intervalos;
          var status = diagrama[i][0];
          var duracao = 1;
          for(var j = 0; j < diagrama[i].length; j++){
            if(j+1 >= diagrama[i].length){
              intervalos.push({status: status == null ? 3 : status ,duracao:duracao });
            }else if(diagrama[i][j] != diagrama[i][j+1]){
              intervalos.push({status: status == null ? 3 : status ,duracao:duracao });
              duracao = 1;
              status = diagrama[i][j+1];
            }else{
              duracao++;
            }
          }
        }
        return resultado;
        
      };
      DiagramaIntervalos.prototype.estagioSeguinte = function (lista,atual) {
        if (atual >= lista.length || atual < 0) {return null;}

        var posicao = (atual + 1) % lista.length;
        return lista[posicao];

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
