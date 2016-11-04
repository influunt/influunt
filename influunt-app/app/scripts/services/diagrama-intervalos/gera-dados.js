'use strict';

/**
 * @ngdoc service
 * @name influuntApp.diagramaIntervalos/geraDados
 * @description
 * # diagramaIntervalos/geraDados
 * Factory in the influuntApp.
 */
angular.module('influuntApp')
  .factory('geraDadosDiagramaIntervalo', function geraDadosDiagramaIntervalo() {
    // Service logic
    // ...

    var plano = {};

    // Public API here
    return {
      gerar: function (currentPlano, currentAnel, currentGruposSemaforicos, objeto) {
        currentPlano = _.find(objeto.planos, {idJson: currentPlano.idJson});
        plano = _.cloneDeep(currentPlano);
        var anel = currentAnel;
        var gruposSemaforicos = currentGruposSemaforicos;
        var controlador = objeto;

        plano.estagiosPlanos = [];
        plano.gruposSemaforicosPlanos = [];
        plano.quantidadeGruposSemaforicos = anel.gruposSemaforicos.length;
        plano.posicaoGruposSemaforicos = {};
        plano.labelsGruposSemaforicos = {};
        plano.verdesConflitantesPlano = [];

        gruposSemaforicos.forEach(function (grupo, index) {
          plano.posicaoGruposSemaforicos['G' + grupo.posicao] = index;
          plano.labelsGruposSemaforicos[index] = 'G' + grupo.posicao;
        });

        currentPlano.gruposSemaforicosPlanos.forEach(function (gp){
          var grupoPlano = _.cloneDeep(_.find(controlador.gruposSemaforicosPlanos, {idJson: gp.idJson}));
          var grupo = _.cloneDeep(_.find(controlador.gruposSemaforicos, {idJson: grupoPlano.grupoSemaforico.idJson}));
          grupoPlano.grupoSemaforico.posicao = grupo.posicao;
          plano.gruposSemaforicosPlanos.push(grupoPlano);

          var verdeConflitante = _.filter(controlador.verdesConflitantes, { origem: { idJson: grupoPlano.grupoSemaforico.idJson } }) ||
                                 _.filter(controlador.verdesConflitantes, { destino: { idJson: grupoPlano.grupoSemaforico.idJson } });
          plano.verdesConflitantesPlano.push(verdeConflitante);
        });
        plano.verdesConflitantesPlano = _.flatten(plano.verdesConflitantesPlano);

        plano.gruposSemaforicosPlanos = _.orderBy(plano.gruposSemaforicosPlanos, 'grupoSemaforico.posicao');

        currentPlano.estagiosPlanos.forEach(function(ep){
          var estagioPlano = _.cloneDeep(_.find(controlador.estagiosPlanos, {idJson: ep.idJson}));
          var estagio = _.find(controlador.estagios, {idJson: estagioPlano.estagio.idJson});
          var novoEstagio = _.cloneDeep(estagio);

          novoEstagio.gruposSemaforicos = [];
          estagio.estagiosGruposSemaforicos.forEach(function(egs){
            var estagioGrupoSemaforico = _.find(controlador.estagiosGruposSemaforicos, {idJson: egs.idJson});
            var grupoSemaforico = _.find(controlador.gruposSemaforicos, {idJson: estagioGrupoSemaforico.grupoSemaforico.idJson});
            var novoGrupoSemaforico = _.cloneDeep(grupoSemaforico);

            novoGrupoSemaforico.tabelasEntreVerdes = [];
            grupoSemaforico.tabelasEntreVerdes.forEach(function(tev){
              var tabelaEntreVerde = _.cloneDeep(_.find(controlador.tabelasEntreVerdes, {idJson: tev.idJson}));
              novoGrupoSemaforico.tabelasEntreVerdes.push(tabelaEntreVerde);
            });

            novoGrupoSemaforico.transicoes = [];
            grupoSemaforico.transicoes.forEach(function(t){
              var transicao = _.find(controlador.transicoes, {idJson: t.idJson});
              var novaTransicao = _.cloneDeep(transicao);
              novaTransicao.tabelaEntreVerdesTransicoes = [];
              transicao.tabelaEntreVerdesTransicoes.forEach(function(tevt){
                var tabelaEntreVerdeTransicao = _.cloneDeep(_.find(controlador.tabelasEntreVerdesTransicoes, {idJson: tevt.idJson}));
                novaTransicao.tabelaEntreVerdesTransicoes.push(tabelaEntreVerdeTransicao);
              });
              novoGrupoSemaforico.transicoes.push(novaTransicao);
            });

            novoGrupoSemaforico.transicoesComGanhoDePassagem = [];
            grupoSemaforico.transicoesComGanhoDePassagem.forEach(function(t){
              var transicao = _.find(controlador.transicoesComGanhoDePassagem, {idJson: t.idJson});
              var novaTransicao = _.cloneDeep(transicao);
              novoGrupoSemaforico.transicoesComGanhoDePassagem.push(novaTransicao);
            });

            novoEstagio.gruposSemaforicos.push(novoGrupoSemaforico);
          });

          estagioPlano.estagio = novoEstagio;
          plano.estagiosPlanos.push(estagioPlano);
        });
        plano.estagiosPlanos = _.orderBy(plano.estagiosPlanos, ['posicao']);

        return plano;
      }
    };
  });
