'use strict';

/**
 * @ngdoc function
 * @name influuntApp.controller:ControladoresRevisaoCtrl
 * @description
 * # ControladoresRevisaoCtrl
 * Controller of the influuntApp
 */
angular.module('influuntApp')
 .controller('ControladoresRevisaoCtrl', ['$scope', '$state', '$controller', '$filter',
                                          'assertControlador', 'influuntAlert', 'Restangular', 'toast',
                                          'influuntBlockui',
    function ($scope, $state, $controller, $filter,
              assertControlador, influuntAlert, Restangular, toast, influuntBlockui) {
      $controller('ControladoresCtrl', {$scope: $scope});

      var setDadosBasicosControlador, setDadosCurrentAnel, getNumGruposSemaforicosAnel,
          getNumDetectoresAnel, setDadosCurrentGruposSemaforicos, setDadosCurrentEstagios,
          setDadosCurrentVerdesConflitantes, setDadosCurrentTransicoesProibidas, setDadosCurrentTabelasEntreVerdes,
          setDadosCurrentDetectores, setCurrentAtrasosDeGrupo;

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
        return $scope.inicializaWizard()
          .then(function() {
            setDadosBasicosControlador();

            $scope.objeto.aneis = _.orderBy($scope.objeto.aneis, ['posicao']);
            $scope.aneis = _.filter($scope.objeto.aneis, 'ativo');
            $scope.selecionaAnelRevisao(0);

            $scope.markerEnderecoControlador = _.clone($scope.objeto.todosEnderecos[0]);
            $scope.markerEnderecoControlador.options = {draggable: false};
          })
          .finally(influuntBlockui.unblock);
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
        setCurrentAtrasosDeGrupo();

        $scope.gsTabelasEntreVerdes = $scope.currentGruposSemaforicos[0];
        $scope.gsAtrasoGrupo = $scope.currentGruposSemaforicos[0];
      };

      $scope.commitMessage = function() {
        var titulo = $filter('translate')('controladores.revisao.submitPopup.titulo');
        var texto = $filter('translate')('controladores.revisao.submitPopup.texto');
        return influuntAlert
          .prompt(titulo, texto)
          .then(function(texto) {
            if (texto) {
              return Restangular.one('controladores', $scope.objeto.id)
                .all('atualizar_descricao')
                .customPUT({descricao: texto})
                .then(function() {
                  $state.go('app.controladores');
                })
                .catch(function(err) {
                  toast.clear();
                  influuntAlert.alert('Controlador', err.data[0].message);
                })
                .finally(influuntBlockui.unblock);
            }
          });
      };

      setDadosBasicosControlador = function() {
        var area;
        if($scope.objeto && $scope.objeto.area){
          area = _.find($scope.objeto.areas, {idJson: $scope.objeto.area.idJson});
        }
        $scope.dadosBasicos = {
          area: area,
          subarea: $scope.objeto.subarea,
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
          endereco: $scope.objeto.nomeEndereco,
          croqui: $scope.getImagemDeCroqui($scope.objeto),
          exclusivoParaTeste: $scope.objeto.exclusivoParaTeste
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
            numDetectoresVeicular: detectoresCount.totalVeicular,
            aceitaModoManual: $scope.currentAnel.aceitaModoManual,
            croqui: $scope.getImagemDeCroqui($scope.currentAnel)
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
              faseVermelha: grupoSemaforico.faseVermelhaApagadaAmareloIntermitente ? 'Colocar em amarelo intermitente' : 'Não colocar em amarelo intermitente',
              tempoVerdeSeguranca: grupoSemaforico.tempoVerdeSeguranca,
              tabelaEntreVerdes: grupoSemaforico.tabelasEntreVerdes
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
          _.forEach($scope.currentEstagios, function(estagio, index) {
            var ids = _.map(estagio.estagiosGruposSemaforicos, 'idJson');
            var estagioGrupos = _.chain($scope.objeto.estagiosGruposSemaforicos)
              .filter(function(e) {
                return ids.indexOf(e.idJson) >= 0;
              })
              .value();

            ids = _.map(estagioGrupos, function(egs) { return egs.grupoSemaforico.idJson; });
            var gruposStr = _
              .chain($scope.objeto.gruposSemaforicos)
              .filter(function(e) {
                return ids.indexOf(e.idJson) >= 0;
              })
              .orderBy(['posicao'])
              .map(function(grupoSemaforico) { return 'G' + grupoSemaforico.posicao; })
              .join(', ')
              .value();

            var dadosEstagio = {
              posicao: estagio.posicao || (index + 1),
              gruposSemaforicosStr: gruposStr,
              demandaPrioritaria: estagio.demandaPrioritaria,
              tempoMaximoPermanenciaAtivado: estagio.tempoMaximoPermanenciaAtivado,
              tempoMaximoPermanencia: estagio.tempoMaximoPermanencia,
              tempoVerdeDemandaPrioritaria: estagio.tempoVerdeDemandaPrioritaria,
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
          $scope.dadosCurrentTransicoesProibidas1 = [];
          $scope.dadosCurrentTransicoesProibidas2 = [];

          _.forEach(dadosCurrentTransicoesProibidas, function(transicaoProibida, index) {
            if (index % 2 === 0) {
              $scope.dadosCurrentTransicoesProibidas1.push(transicaoProibida);
            } else {
              $scope.dadosCurrentTransicoesProibidas2.push(transicaoProibida);
            }
          });
        }
      };

      setCurrentAtrasosDeGrupo = function(){
        $scope.currentAtrasosdeGrupo = $scope.objeto.atrasosDeGrupo;
      };

      setDadosCurrentTabelasEntreVerdes = function() {
        if ($scope.currentGruposSemaforicos) {
          var gs = _.map($scope.currentGruposSemaforicos, 'idJson');
          $scope.dadosCurrentTabelaEntreVerdes = _
            .chain($scope.objeto.tabelasEntreVerdes)
            .filter(function(tev) {return gs.indexOf(tev.grupoSemaforico.idJson) >= 0;})
            .map(function(tev) {
              var obj = {
                descricao: tev.descricao,
                posicao: tev.posicao,
                grupoSemaforicoIdJson: tev.grupoSemaforico.idJson
              };

              obj.transicoes = _.map(tev.tabelaEntreVerdesTransicoes, function(el) {
                var tevt = _.find($scope.objeto.tabelasEntreVerdesTransicoes, {idJson: el.idJson});
                var transicao = _.find($scope.objeto.transicoes, {idJson: tevt.transicao.idJson});
                var origem = _.find($scope.objeto.estagios, {idJson: transicao.origem.idJson});
                var destino = _.find($scope.objeto.estagios, {idJson: transicao.destino.idJson});

                return {
                  tempoAmarelo: tevt.tempoAmarelo,
                  tempoVermelhoIntermitente: tevt.tempoVermelhoIntermitente,
                  tempoAtrasoGrupo: tevt.tempoAtrasoGrupo,
                  tempoVermelhoLimpeza: tevt.tempoVermelhoLimpeza,
                  tipo: transicao.tipo,
                  modoIntermitenteOuApagado: transicao.modoIntermitenteOuApagado,
                  label: 'E' + origem.posicao + '-' + 'E' + destino.posicao
                };
              });

              return obj;
            })
            .orderBy(['posicao'])
            .value();
          $scope.atualizaTabelasEntreVerdes($scope.currentGruposSemaforicos[0]);
        }
      };

      $scope.atualizaTabelasEntreVerdes = function(grupo){
        if(grupo){
          $scope.currentTabelasEntreVerdes = _.filter($scope.dadosCurrentTabelaEntreVerdes, {grupoSemaforicoIdJson: grupo.idJson});
          $scope.tabelaEntreVerdeSelecionada = $scope.currentTabelasEntreVerdes[0];
        }
      };

      $scope.atualizaTabelaDeAtrasoDeGrupo = function(grupo){
        if(grupo){
          $scope.gsAtrasoGrupo = grupo;
          $scope.currentAtrasosDeGrupoPerdaPassagem = _
          .chain($scope.objeto.transicoes)
          .filter(function (transicao) { return transicao.grupoSemaforico.idJson === grupo.idJson;})
          .map(function(transicao) {
            var origem = _.find($scope.objeto.estagios, {idJson: transicao.origem.idJson});
            var destino = _.find($scope.objeto.estagios, {idJson: transicao.destino.idJson});
            return {
              label: 'E' + origem.posicao + '-' + 'E' + destino.posicao,
              tempoAtrasoGrupo: transicao.tempoAtrasoGrupo
            };
          })
          .value();

          $scope.currentAtrasosDeGrupoGanhoPassagem = _
          .chain($scope.objeto.transicoesComGanhoDePassagem)
          .filter(function (transicao) { return transicao.grupoSemaforico.idJson === grupo.idJson;})
          .map(function(transicao) {
            var origem = _.find($scope.objeto.estagios, {idJson: transicao.origem.idJson});
            var destino = _.find($scope.objeto.estagios, {idJson: transicao.destino.idJson});
            return {
              label: 'E' + origem.posicao + '-' + 'E' + destino.posicao,
              tempoAtrasoGrupo: transicao.tempoAtrasoGrupo
            };
          })
          .value();
        }
      };

      setDadosCurrentDetectores = function() {
        $scope.dadosCurrentDetectores = [];
        if ($scope.currentEstagios) {
          var ids = _.map($scope.currentEstagios, 'idJson');
          var detectores = _.filter($scope.objeto.detectores, function(detector) {
            return ids.indexOf(detector.estagio.idJson) >= 0;
          });

          detectores = _.orderBy(detectores, ['tipo', 'posicao']);

          _.forEach(detectores, function(detector) {
            $scope.dadosCurrentDetectores.push({
              nome: detector.tipo === 'PEDESTRE' ? 'DP'+detector.posicao : 'DV'+detector.posicao,
              estagio: _.find($scope.objeto.estagios, { idJson: detector.estagio.idJson }),
              tempoAusenciaDeteccao: detector.tempoAusenciaDeteccao || 0,
              tempoDeteccaoPermanente: detector.tempoDeteccaoPermanente || 0,
              monitorado: detector.monitorado
            });
          });
        }
      };
    }]);
