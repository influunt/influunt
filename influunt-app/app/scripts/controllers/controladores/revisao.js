'use strict';

/**
 * @ngdoc function
 * @name influuntApp.controller:ControladoresRevisaoCtrl
 * @description
 * # ControladoresRevisaoCtrl
 * Controller of the influuntApp
 */
angular.module('influuntApp')
 .controller('ControladoresRevisaoCtrl', ['$scope', '$state', '$controller', 'assertControlador',
    function ($scope, $state, $controller, assertControlador) {
      $controller('ControladoresCtrl', {$scope: $scope});

      var setDadosBasicosControlador, setDadosCurrentAnel, getNumGruposSemaforicosAnel,
          getNumDetectoresAnel, setDadosCurrentGruposSemaforicos, setDadosCurrentEstagios,
          setDadosCurrentVerdesConflitantes, setDadosCurrentTransicoesProibidas, setDadosCurrentTabelasEntreVerdes,
          setDadosCurrentDetectores;


      /**
       * Pré-condições para acesso à tela de revisao: Somente será possível acessar esta
       * tela se o objeto possuir estágios. Os estágios são informados no passo anterior, o
       * passo de aneis.
       *
       * @return     {boolean}  { description_of_the_return_value }
       */
      $scope.assertRevisao = function() {
        var valid = assertControlador.hasAneis($scope.objeto) && assertControlador.hasEstagios($scope.objeto);
        if (!valid) {
          $state.go('app.wizard_controladores.revisao', {id: $scope.objeto.id});
        }
        return valid;
      };

      $scope.inicializaRevisao = function() {
        return $scope.inicializaWizard().then(function() {
          setDadosBasicosControlador();
          $scope.objeto.aneis = _.orderBy($scope.objeto.aneis, ['posicao']);
          $scope.aneis = _.filter($scope.objeto.aneis, { ativo: true });
          $scope.selecionaAnelRevisao(0);
        });
      };

      $scope.selecionaAnelRevisao = function(index) {
        $scope.selecionaAnel(index);
        $scope.atualizaGruposSemaforicos();
        $scope.atualizaEstagios();
        setDadosCurrentAnel();
        setDadosCurrentGruposSemaforicos();
        setDadosCurrentEstagios();
        setDadosCurrentVerdesConflitantes();
        setDadosCurrentTransicoesProibidas();
        setDadosCurrentTabelasEntreVerdes();
        setDadosCurrentDetectores();
      };

      setDadosBasicosControlador = function() {
        var area = _.find($scope.objeto.areas, {idJson: $scope.objeto.area.idJson});
        $scope.dadosBasicos = {
          area: area,
          cidade: _.find($scope.objeto.cidades, { idJson: area.cidade.idJson }),
          numeroSMEE: $scope.objeto.numeroSMEE || '-',
          numeroSMEEConjugado1: $scope.objeto.numeroSMEEConjugado1 || '-',
          numeroSMEEConjugado2: $scope.objeto.numeroSMEEConjugado2 || '-',
          numeroSMEEConjugado3: $scope.objeto.numeroSMEEConjugado3 || '-',
          CLC: $scope.objeto.CLC,
          modelo: $scope.objeto.modelo,
          fabricante: $scope.objeto.modelo.fabricante,
          numEstagios: $scope.objeto.estagios.length,
          numAneisAtivos: _.filter($scope.objeto.aneis, { ativo: true }).length,
          numDetectoresVeicular: _.filter($scope.objeto.detectores, {tipo : 'VEICULAR'}).length,
          numDetectoresPedestre: _.filter($scope.objeto.detectores, {tipo : 'PEDESTRE'}).length,
          endereco: $scope.objeto.nomeEndereco
        };
      };

      setDadosCurrentAnel = function() {
        if ($scope.aneis.length > 0) {
          var gruposSemaforicosCount = getNumGruposSemaforicosAnel($scope.currentAnel);
          var detectoresCount = getNumDetectoresAnel($scope.currentAnel);
          $scope.dadosCurrentAnel = {
            CLA: $scope.currentAnel.CLA,
            localizacao: $scope.currentAnel.localizacao,
            numeroSMEE: $scope.currentAnel.numeroSMEE || '-',
            numGruposPedestre: gruposSemaforicosCount.totalPedestre,
            numGruposVeicular: gruposSemaforicosCount.totalVeicular,
            numDetectoresPedestre: detectoresCount.totalPedestre,
            numDetectoresVeicular: detectoresCount.totalVeicular
          };
        }
      };

      getNumGruposSemaforicosAnel = function(anel) {
        var result = { totalVeicular: 0, totalPedestre: 0 };
        _.forEach(anel.gruposSemaforicos, function(gs) {
          var grupoSemaforico = _.find($scope.objeto.gruposSemaforicos, { idJson: gs.idJson });
          if (grupoSemaforico.tipo === 'VEICULAR') {
            result.totalVeicular += 1;
          } else {
            result.totalPedestre += 1;
          }
        });

        return result;
      };

      getNumDetectoresAnel = function(anel) {
        var result = { totalVeicular: 0, totalPedestre: 0 };
        _.forEach(anel.detectores, function(d) {
          var detector = _.find($scope.objeto.detectores, { idJson: d.idJson });
          if (detector.tipo === 'VEICULAR') {
            result.totalVeicular += 1;
          } else {
            result.totalPedestre += 1;
          }
        });

        return result;
      };

      setDadosCurrentGruposSemaforicos = function() {
        $scope.dadosCurrentGruposSemaforicos = [];
        if ($scope.currentGruposSemaforicos) {
          var grupos = [];
          _.forEach($scope.currentGruposSemaforicos, function(grupoSemaforico, index) {
            if (index > 0 && index % 3 === 0) {
              $scope.dadosCurrentGruposSemaforicos.push(grupos);
              grupos = [];
            }

            var dadosGrupo = {
              posicao: grupoSemaforico.posicao,
              descricao: grupoSemaforico.descricao,
              tipo: grupoSemaforico.tipo,
              faseVermelha: grupoSemaforico.faseVermelhaApagadaAmareloIntermitente ? 'Colocar em amarelo intermitente' : 'Não colocar em amarelo intermitente'
            };

            grupos.push(dadosGrupo);

            if (index === $scope.currentGruposSemaforicos.length - 1) {
              $scope.dadosCurrentGruposSemaforicos.push(grupos);
            }
          });
        }
      };

      setDadosCurrentEstagios = function() {
        $scope.dadosCurrentEstagios = [];
        if ($scope.currentEstagios) {
          _.forEach($scope.currentEstagios, function(estagio) {
            var ids = _.map(estagio.estagiosGruposSemaforicos, 'idJson');
            var estagioGrupos = _.chain($scope.objeto.estagiosGruposSemaforicos)
              .filter(function(e) {
                return ids.indexOf(e.idJson) >= 0;
              })
              .value();

            ids = _.map(estagioGrupos, function(egs) { return egs.grupoSemaforico.idJson; });
            var gruposStr = _.chain($scope.objeto.gruposSemaforicos)
              .filter(function(e) {
                return ids.indexOf(e.idJson) >= 0;
              })
              .orderBy(['posicao'])
              .map(function(grupoSemaforico) { return 'G' + grupoSemaforico.posicao; })
              .join(', ')
              .value();

            var dadosEstagio = {
              posicao: estagio.posicao,
              gruposSemaforicosStr: gruposStr,
              demandaPrioritaria: estagio.demandaPrioritaria,
              tempoMaximoPermanenciaAtivado: estagio.tempoMaximoPermanenciaAtivado,
              tempoMaximoPermanencia: estagio.tempoMaximoPermanencia,
              imagem: $scope.getImagemDeEstagio(estagio)
            };

            $scope.dadosCurrentEstagios.push(dadosEstagio);
          });
        }
      };

      setDadosCurrentVerdesConflitantes = function() {
        $scope.dadosCurrentVerdesConflitantes = [];
        if ($scope.currentGruposSemaforicos) {
          var ids = _.map($scope.currentGruposSemaforicos, 'idJson');
          var verdesConflitantes = _.filter($scope.objeto.verdesConflitantes, function(vc) {
            return ids.indexOf(vc.origem.idJson) >= 0 || ids.indexOf(vc.destino.idJson) >= 0;
          });

          _.forEach(verdesConflitantes, function(verdeConflitante) {
            $scope.dadosCurrentVerdesConflitantes.push({
              origem: _.find($scope.objeto.gruposSemaforicos, { idJson: verdeConflitante.origem.idJson }),
              destino: _.find($scope.objeto.gruposSemaforicos, { idJson: verdeConflitante.destino.idJson })
            });
          });

          $scope.dadosCurrentVerdesConflitantes = _.orderBy($scope.dadosCurrentVerdesConflitantes, ['origem.posicao', 'destino.posicao']);
        }
      };

      setDadosCurrentTransicoesProibidas = function() {
        var dadosCurrentTransicoesProibidas = [];
        if ($scope.currentEstagios) {
          var ids = _.map($scope.currentEstagios, 'idJson');
          var transicoesProibidas = _.filter($scope.objeto.transicoesProibidas, function(tp) {
            return ids.indexOf(tp.origem.idJson) >= 0 || ids.indexOf(tp.destino.idJson) >= 0 || ids.indexOf(tp.alternativo.idJson) >= 0;
          });

          _.forEach(transicoesProibidas, function(transicaoProibida) {
            dadosCurrentTransicoesProibidas.push({
              origem: _.find($scope.objeto.estagios, { idJson: transicaoProibida.origem.idJson }),
              destino: _.find($scope.objeto.estagios, { idJson: transicaoProibida.destino.idJson }),
              alternativo: _.find($scope.objeto.estagios, { idJson: transicaoProibida.alternativo.idJson })
            });
          });

          dadosCurrentTransicoesProibidas = _.orderBy(dadosCurrentTransicoesProibidas, ['origem.posicao', 'destino.posicao', 'alternativo.posicao']);
          $scope.dadosCurrentTRansicoesProibidas1 = [];
          $scope.dadosCurrentTRansicoesProibidas2 = [];

          _.forEach(dadosCurrentTransicoesProibidas, function(transicaoProibida, index) {
            if (index % 2 === 0) {
              $scope.dadosCurrentTRansicoesProibidas1.push(transicaoProibida);
            } else {
              $scope.dadosCurrentTRansicoesProibidas2.push(transicaoProibida);
            }
          });
        }
      };

      setDadosCurrentTabelasEntreVerdes = function() {
        $scope.dadosCurrentTabelasentreVerdesPadrao = [];
        $scope.dadosCurrentTabelasentreVerdesOutra = [];
        if ($scope.currentEstagios) {
          var ids = _.map($scope.currentEstagios, 'idJson');
          var transicoes = _.filter($scope.objeto.transicoes, function(t) {
            return ids.indexOf(t.origem.idJson) >= 0 || ids.indexOf(t.destino.idJson) >= 0;
          });

          _.forEach(transicoes, function(transicao) {
            var tevtPadrao = _.find($scope.objeto.tabelasEntreVerdesTransicoes, { idJson: transicao.tabelaEntreVerdesTransicoes[0].idJson });
            $scope.dadosCurrentTabelasentreVerdesPadrao.push({
              origem: _.find($scope.objeto.estagios, { idJson: transicao.origem.idJson }),
              destino: _.find($scope.objeto.estagios, { idJson: transicao.destino.idJson }),
              tempoAmarelo: tevtPadrao.tempoAmarelo,
              tempoVermelhoLimpeza: tevtPadrao.tempoVermelhoLimpeza,
              tempoVermelhoIntermitente: tevtPadrao.tempoVermelhoIntermitente
            });

            var tevtOutra = _.get(transicao, 'tabelaEntreVerdesTransicoes[1]');
            if (typeof tevtOutra !== 'undefined') {
              tevtOutra = _.find($scope.objeto.tabelasEntreVerdesTransicoes, { idJson: tevtOutra.idJson });
              var tevOutra = _.find($scope.tabelasEntreVerdes, { idJson: tevtOutra.tabelaEntreVerdes.idJson });
              $scope.dadosCurrentTabelasentreVerdesOutra.push({
                origem: _.find($scope.objeto.estagios, { idJson: transicao.origem.idJson }),
                destino: _.find($scope.objeto.estagios, { idJson: transicao.destino.idJson }),
                tempoAmarelo: tevtOutra.tempoAmarelo,
                tempoVermelhoLimpeza: tevtOutra.tempoVermelhoLimpeza,
                tempoVermelhoIntermitente: tevtOutra.tempoVermelhoIntermitente,
                nome: _.get(tevOutra, 'descricao') || 'NOVA'
              });
            }
          });
        }
      };

      setDadosCurrentDetectores = function() {
        $scope.dadosCurrentDetectores = [];
        if ($scope.currentEstagios) {
          var ids = _.map($scope.currentEstagios, 'idJson');
          var detectores = _.filter($scope.objeto.detectores, function(detector) {
            return ids.indexOf(detector.estagio.idJson) >= 0;
          });

          _.forEach(detectores, function(detector) {
            $scope.dadosCurrentDetectores.push({
              nome: detector.tipo === 'PEDESTRE' ? 'DP'+detector.posicao : 'DV'+detector.posicao,
              estagio: _.find($scope.objeto.estagios, { idJson: detector.estagio.idJson }),
              tempoAusenciaDeteccaoMinima: detector.tempoAusenciaDeteccaoMinima || 0,
              tempoAusenciaDeteccaoMaxima: detector.tempoAusenciaDeteccaoMaxima || 0,
              tempoDeteccaoPermanenteMinima: detector.tempoDeteccaoPermanenteMinima || 0,
              tempoDeteccaoPermanenteMaxima: detector.tempoDeteccaoPermanenteMaxima || 0
            });
          });
        }
      };
    }]);
