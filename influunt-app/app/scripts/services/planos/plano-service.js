'use strict';

/**
* @ngdoc service
* @name influuntApp.planos/planoService
* @description
* # planos/planoService
* Factory in the influuntApp.
*/
angular.module('influuntApp')
.factory('planoService', function planoService() {

    var verdeMinimoDoEstagio = function(controlador, estagio){
      var tempoMax = controlador.verdeMin;
      var veicular = false;
      _.each(estagio.estagiosGruposSemaforicos, function(gs){
        var egs = _.find(controlador.estagiosGruposSemaforicos, {idJson: gs.idJson});
        var grupo = _.find(controlador.gruposSemaforicos, {idJson: egs.grupoSemaforico.idJson});
        tempoMax = _.max([grupo.tempoVerdeSeguranca, tempoMax]);
        veicular = grupo.tipo === 'VEICULAR' ? true : veicular;
      });
      estagio.verdeMinimoEstagio = tempoMax;
      estagio.isVeicular = veicular;
      return tempoMax;
    };

    var adicionar = function(controlador, anel, posicao) {
      var plano = _.find(controlador.planos, {posicao: posicao, anel: {idJson: anel.idJson}});
      if (plano) {
        plano.configurado = true;
      }else {
        plano = {
          idJson: UUID.generate(),
          anel: { idJson: anel.idJson },
          descricao: 'PLANO ' + posicao,
          posicao: posicao,
          modoOperacao: 'TEMPO_FIXO_ISOLADO',
          posicaoTabelaEntreVerde: 1,
          gruposSemaforicosPlanos: [],
          estagiosPlanos: [],
          tempoCiclo: controlador.cicloMin,
          configurado: posicao === 1 ? true : false,
          versaoPlano: {idJson: anel.versaoPlano.idJson}
        };

        var versaoPlano = _.find(controlador.versoesPlanos, {idJson: anel.versaoPlano.idJson});
        versaoPlano.planos = versaoPlano.planos || [];
        versaoPlano.planos.push({idJson: plano.idJson});


        controlador.gruposSemaforicosPlanos = controlador.gruposSemaforicosPlanos || [];
        anel.gruposSemaforicos.forEach(function (g){
          var grupo =  _.find(controlador.gruposSemaforicos, {idJson: g.idJson});
          var grupoPlano = {
            idJson: UUID.generate(),
            ativado: true,
            grupoSemaforico: {
              idJson: grupo.idJson
            },
            plano: {
              idJson: plano.idJson
            }
          };

          controlador.gruposSemaforicosPlanos.push(grupoPlano);
          plano.gruposSemaforicosPlanos.push({idJson: grupoPlano.idJson});
        });

        controlador.estagiosPlanos = controlador.estagiosPlanos || [];
        anel.estagios.forEach(function (e){
          var estagio =  _.find(controlador.estagios, {idJson: e.idJson});
          if(!estagio.demandaPrioritaria){
            var estagioPlano = {
              idJson: UUID.generate(),
              estagio: {
                idJson: estagio.idJson
              },
              plano: {
                idJson: plano.idJson
              },
              posicao: estagio.posicao,
              tempoVerde: verdeMinimoDoEstagio(controlador, estagio),
              dispensavel: false
            };

            controlador.estagiosPlanos.push(estagioPlano);
            plano.estagiosPlanos.push({idJson: estagioPlano.idJson});
          }
        });

        controlador.planos = controlador.planos || [];
        controlador.planos.push(plano);

        anel.planos = anel.planos || [];
        anel.planos.push({idJson: plano.idJson});
      }
    return controlador;
  };

  return {
    adicionar: adicionar,
    verdeMinimoDoEstagio: verdeMinimoDoEstagio
  };
});
