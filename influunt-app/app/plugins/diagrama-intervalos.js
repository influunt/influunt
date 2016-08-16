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
      function DiagramaIntervalos(plano, valoresMinimos) {
        this.plano = plano;
        this.valoresMinimos = valoresMinimos;
      }
      DiagramaIntervalos.prototype.calcula = function () {
        var plano = this.plano;
        var valoresMinimos = this.valoresMinimos;
        var diagrama = createArray(plano.quantidadeGruposSemaforicos, plano.tempoCiclo || 0);
        var estagios = [];
        for(var i = 0; i < diagrama.length; i++){
          for(var j = 0; j < diagrama[i].length; j++){
            diagrama[i][j] = 3;
          }
        }
        var tempoCiclo = 0;
        var instante = 0;
        for(var i = 0; i < plano.estagiosPlanos.length; i++){
          var estagioPlanoAtual = plano.estagiosPlanos[i];
          var estagioAtual = estagioPlanoAtual.estagio;
          var tempoVerde = plano.modoOperacao === 'ATUADO' ? estagioPlanoAtual.tempoVerdeMinimo || valoresMinimos.verdeMinimoMin : estagioPlanoAtual.tempoVerde || valoresMinimos.verdeMin;
          var estagioAnterior = this.estagioAnterior(plano.estagiosPlanos,i).estagio;
          if(estagioAtual.idJson !== estagioAnterior.idJson){
            for(var j = 0; j < estagioAnterior.gruposSemaforicos.length; j++){
              var grupo = estagioAnterior.gruposSemaforicos[j];
              var tabelaEntreVerde = _.find(grupo.tabelasEntreVerdes, {"posicao": plano.posicaoTabelaEntreVerde});
              var transicao = _.find(grupo.transicoes, {"origem": {"idJson": estagioAnterior.idJson}, "destino": {"idJson": estagioAtual.idJson}})
              var tabelaEntreVerdesTransicao = _.find(transicao.tabelaEntreVerdesTransicoes, {"tabelaEntreVerdes": {"idJson": tabelaEntreVerde.idJson}})
              var tempoAmarelo = !_.isUndefined(tabelaEntreVerdesTransicao.tempoAmarelo) ? parseInt(tabelaEntreVerdesTransicao.tempoAmarelo) : 0;
              var tempoVermelhoIntermitente = !_.isUndefined(tabelaEntreVerdesTransicao.tempoVermelhoIntermitente) ? parseInt(tabelaEntreVerdesTransicao.tempoVermelhoIntermitente) : 0;
              var tempoAtrasoGrupo = !_.isUndefined(tabelaEntreVerdesTransicao.tempoAtrasoGrupo) ? parseInt(tabelaEntreVerdesTransicao.tempoAtrasoGrupo) : 0;
              var tempoVermelhoLimpeza = !_.isUndefined(tabelaEntreVerdesTransicao.tempoVermelhoLimpeza) ? parseInt(tabelaEntreVerdesTransicao.tempoVermelhoLimpeza) : 0;

              var tempoAmareloOuVermelhoIntermitente = grupo.tipo == 'VEICULAR' ? tempoAmarelo : tempoVermelhoIntermitente;
              var tempoEntreVerde = tempoAmareloOuVermelhoIntermitente + tempoVermelhoLimpeza;
              var posicao = grupo.posicao - 1;
              if(!_.find(estagioAtual.gruposSemaforicos, {"id": grupo.id})){
                if(tempoAtrasoGrupo > 0){
                  for(var t = tempoCiclo; t < tempoCiclo + tempoAtrasoGrupo; t++){
                    diagrama[posicao][t] = 1;
                  }
                }
                for(var t = tempoCiclo + tempoAtrasoGrupo; t < tempoCiclo + tempoAmareloOuVermelhoIntermitente + tempoAtrasoGrupo; t++){
                  diagrama[posicao][t] = grupo.tipo == 'VEICULAR' ? 2 : 4;
                }
                for(var t = tempoCiclo + tempoAmareloOuVermelhoIntermitente + tempoAtrasoGrupo; t < tempoCiclo + tempoEntreVerde + tempoAtrasoGrupo; t++){
                  diagrama[posicao][t] = 6;
                }
              }else {
                for(var t = tempoCiclo; t < tempoCiclo + tempoEntreVerde + tempoVerde; t++){
                  diagrama[posicao][t] = 1;
                }
              }
              instante = Math.max(instante, tempoEntreVerde);
            }
          }
          for(var j = 0; j < estagioAtual.gruposSemaforicos.length; j++){
             var grupo = estagioAtual.gruposSemaforicos[j];
             for(var t = instante + tempoCiclo; t < tempoCiclo + instante + tempoVerde; t++){
               diagrama[grupo.posicao - 1][t] = 1;
             }
          }
          tempoCiclo += instante + (tempoVerde || 0);

          estagios.push({id: UUID.generate(), posicao: estagioAtual.posicao, duracao: instante + tempoVerde})
          instante = 0
        }
        return this.gerarDiagramaIntervalo(diagrama, estagios, plano.tempoCiclo);
      };
      DiagramaIntervalos.prototype.gerarDiagramaIntervalo = function (diagrama, estagios, tempoCiclo) {
        // setta modo "vermelho" para o restante tempo dos grupos em relação ao grupo de maior tempo
        // para o modo atuado.
        // if (this.plano.modoOperacao === 'ATUADO') {
          var size = _.chain(diagrama).map(function(i) { return i.length; }).max().value();

          diagrama.forEach(function(grupo) {
            for (i = grupo.length; i < size; i++) {
              grupo[i] = 3;
            }
          });
        var resultado = {gruposSemaforicos: [], estagios: estagios, erros: []};
        for(var i = 0; i < diagrama.length; i++){
          resultado.gruposSemaforicos[i] = resultado.gruposSemaforicos[i] || {};
          resultado.gruposSemaforicos[i].posicao = (i + 1);
          resultado.gruposSemaforicos[i].intervalos = resultado.gruposSemaforicos[i].intervalos || [];
          intervalos = resultado.gruposSemaforicos[i].intervalos;
          var status = diagrama[i][0];
          var duracao = 1;

          if(diagrama[i].length > tempoCiclo){
            return {erros: ["Tempo de Ciclo é menor que os tempos dos estágios."]};
          }

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
