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
    var adicionar = function(controlador, anel, posicao) {
      var plano = _.find(
        controlador.planos,
        {posicao: posicao, anel: {idJson: anel.idJson}}
      );

      if (plano) {
        plano.configurado = true;
      } else {
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
          configurado: posicao === 1,
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

        anel.estagios.forEach(function (e){
          var estagio =  _.find(controlador.estagios, {idJson: e.idJson});
          var estagioPlano = {
            idJson: UUID.generate(),
            estagio: {
              idJson: estagio.idJson
            },
            plano: {
              idJson: plano.idJson
            },
            posicao: estagio.posicao,
            tempoVerde: controlador.verdeMin,
            dispensavel: false
          };

          controlador.estagiosPlanos = controlador.estagiosPlanos || [];
          controlador.estagiosPlanos.push(estagioPlano);
          plano.estagiosPlanos.push({idJson: estagioPlano.idJson});
        });


        controlador.planos = controlador.planos || [];
        controlador.planos.push(plano);

        anel.planos = anel.planos || [];
        anel.planos.push({idJson: plano.idJson});
      }

      return controlador;
    };

    return {
      adicionar: adicionar
    };
  });
