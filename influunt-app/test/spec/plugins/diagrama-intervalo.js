(function() {
  'use strict';

  describe('Diagrama de Intervalos', function () {
    describe('Configuração de planos', function() {
      var plano, valoresMinimos, resposta;
      beforeEach(function() {
        plano = {
          posicao: 1,
          tempoCiclo: 60,
          posicaoTabelaEntreVerde: 1,
          defasagem: 0,
          quantidadeGruposSemaforicos: 5,
          modoOperacao: 'TEMPO_FIXO_ISOLADO',
          estagiosPlanos: [
            {
              id: 'EP1',
              idJson: 'EP1-JSON',
              tempoVerde: 20,
              tempoVerdeMinimo: null,
              tempoVerdeMaximo: null,
              tempoVerdeIntermediario: null,
              extensaoVerde: null,
              estagio: {
                id: 'E1',
                idJson: 'E1-JSON',
                gruposSemaforicos: [{
                  id: 'G1',
                  idJson: 'G1-JSON',
                  tipo: 'VEICULAR',
                  posicao: 1,
                  tabelasEntreVerdes: [{
                    id: 'tabela-entre-verdes-1',
                    idJson: 'tabela-entre-verdes-1-JSON',
                    posicao: 1
                  }, {
                    id: 'tabela-entre-verdes-1-2',
                    idJson: 'tabela-entre-verdes-1-2-JSON',
                    posicao: 2
                  }, ],
                  transicoes: [{
                    id: 'transicao-1',
                    idJson: 'transicao-1-JSON',
                    origem: {
                      id: 'E1',
                      idJson: 'E1-JSON'
                    },
                    destino: {
                      id: 'E2',
                      idJson: 'E2-JSON'
                    },
                    tabelaEntreVerdesTransicoes: [{
                      tabelaEntreVerdes: {
                        id: 'tabela-entre-verdes-1',
                        idJson: 'tabela-entre-verdes-1-JSON'
                      },
                      tempoAmarelo: 3,
                      tempoVermelhoLimpeza: 1
                    }]
                  }],
                  transicoesComGanhoDePassagem: [{
                    id: 'transicao-perda-1',
                    idJson: 'transicao-perda-1-JSON',
                    origem: {
                      id: 'E3',
                      idJson: 'E3-JSON'
                    },
                    destino: {
                      id: 'E1',
                      idJson: 'E1-JSON'
                    },
                    tempoAtrasoGrupo: 2
                  }]
                }, {
                  id: 'G5',
                  idJson: 'G5-JSON',
                  tipo: 'PEDESTRE',
                  posicao: 5,
                  tabelasEntreVerdes: [{
                    id: 'tabela-entre-verdes-2',
                    idJson: 'tabela-entre-verdes-2-JSON',
                    posicao: 1
                  }],
                  transicoes: [{
                    id: 'transicao-3',
                    idJson: 'transicao-3-JSON',
                    origem: {
                      id: 'E1',
                      idJson: 'E1-JSON'
                    },
                    destino: {
                      id: 'E2',
                      idJson: 'E2-JSON'
                    },
                    tabelaEntreVerdesTransicoes: [{
                      tabelaEntreVerdes: {
                        id: 'tabela-entre-verdes-2',
                        idJson: 'tabela-entre-verdes-2-JSON'
                      },
                      tempoVermelhoIntermitente: 3,
                      tempoVermelhoLimpeza: 3
                    }]
                  }, {
                    id: 'transicao-13',
                    idJson: 'transicao-13-JSON',
                    origem: {
                      id: 'E2',
                      idJson: 'E2-JSON'
                    },
                    destino: {
                      id: 'E3',
                      idJson: 'E3-JSON'
                    },
                    tabelaEntreVerdesTransicoes: [{
                      tabelaEntreVerdes: {
                        id: 'tabela-entre-verdes-2',
                        idJson: 'tabela-entre-verdes-2-JSON'
                      },
                      tempoVermelhoIntermitente: 3,
                      tempoVermelhoLimpeza: 3
                    }]
                  }],
                  transicoesComGanhoDePassagem: [{
                    id: 'transicao-perda-2',
                    idJson: 'transicao-perda-2-JSON',
                    origem: {
                      id: 'E3',
                      idJson: 'E3-JSON'
                    },
                    destino: {
                      id: 'E1',
                      idJson: 'E1-JSON'
                    },
                    tempoAtrasoGrupo: 0
                  }]
                }, ]
              }
            }, {
              id: 'EP2',
              idJson: 'EP2-JSON',
              tempoVerde: 10,
              tempoVerdeMinimo: null,
              tempoVerdeMaximo: null,
              tempoVerdeIntermediario: null,
              extensaoVerde: null,
              estagio: {
                id: 'E2',
                idJson: 'E2-JSON',
                gruposSemaforicos: [{
                  id: 'G3',
                  idJson: 'G3-JSON',
                  tipo: 'PEDESTRE',
                  posicao: 3,
                  tabelasEntreVerdes: [{
                    id: 'tabela-entre-verdes-3',
                    idJson: 'tabela-entre-verdes-3-JSON',
                    posicao: 1
                  }],
                  transicoes: [{
                    id: 'transicao-4',
                    idJson: 'transicao-4-JSON',
                    origem: {
                      id: 'E2',
                      idJson: 'E2-JSON'
                    },
                    destino: {
                      id: 'E3',
                      idJson: 'E3-JSON'
                    },
                    tabelaEntreVerdesTransicoes: [{
                      tabelaEntreVerdes: {
                        id: 'tabela-entre-verdes-3',
                        idJson: 'tabela-entre-verdes-3-JSON'
                      },
                      tempoVermelhoIntermitente: 3,
                      tempoVermelhoLimpeza: 3
                    }]
                  }, {
                    id: 'transicao-5',
                    idJson: UUID.generate(),
                    origem: {
                      id: 'E1',
                      idJson: 'E1-JSON'
                    },
                    destino: {
                      id: 'E3',
                      idJSON: 'E3-JSON'
                    },
                    tabelaEntreVerdesTransicoes: [{
                      tabelaEntreVerdes: {
                        id: 'tabela-entre-verdes-3',
                        idJson: 'tabela-entre-verdes-3-JSON'
                      },
                      tempoVermelhoIntermitente: 3,
                      tempoVermelhoLimpeza: 3
                    }]
                  }, ],
                  transicoesComGanhoDePassagem: [{
                    id: 'transicao-perda-3',
                    idJson: 'transicao-perda-3-JSON',
                    origem: {
                      id: 'E1',
                      idJson: 'E1-JSON'
                    },
                    destino: {
                      id: 'E2',
                      idJson: 'E2-JSON'
                    },
                    tempoAtrasoGrupo: 0
                  }]
                }, {
                  id: 'G5',
                  idJson: 'G5-JSON',
                  tipo: 'PEDESTRE',
                  posicao: 5,
                  tabelasEntreVerdes: [{
                    id: 'tabela-entre-verdes-2',
                    idJson: 'tabela-entre-verdes-2-JSON',
                    posicao: 1
                  }],
                  transicoes: [{
                    id: 'transicao-3',
                    idJson: 'transicao-3-JSON',
                    origem: {
                      id: 'E1',
                      idJson: 'E1-JSON'
                    },
                    destino: {
                      id: 'E2',
                      idJson: 'E2-JSON'
                    },
                    tabelaEntreVerdesTransicoes: [{
                      tabelaEntreVerdes: {
                        id: 'tabela-entre-verdes-2',
                        idJson: 'tabela-entre-verdes-2-JSON'
                      },
                      tempoVermelhoIntermitente: 3,
                      tempoVermelhoLimpeza: 3
                    }]
                  }, {
                    id: 'transicao-13',
                    idJson: 'transicao-13-JSON',
                    origem: {
                      id: 'E2',
                      idJson: 'E2-JSON'
                    },
                    destino: {
                      id: 'E3',
                      idJson: 'E3-JSON'
                    },
                    tabelaEntreVerdesTransicoes: [{
                      tabelaEntreVerdes: {
                        id: 'tabela-entre-verdes-2',
                        idJson: 'tabela-entre-verdes-2-JSON'
                      },
                      tempoVermelhoIntermitente: 3,
                      tempoVermelhoLimpeza: 3
                    }]
                  }],
                  transicoesComGanhoDePassagem: [{
                    id: 'transicao-perda-4',
                    idJson: 'transicao-perda-4-JSON',
                    origem: {
                      id: 'E1',
                      idJson: 'E1-JSON'
                    },
                    destino: {
                      id: 'E2',
                      idJson: 'E2-JSON'
                    },
                    tempoAtrasoGrupo: 0
                  }]
                }, {
                  id: 'G4',
                  idJson: 'G4-JSON',
                  tipo: 'PEDESTRE',
                  posicao: 4,
                  tabelasEntreVerdes: [{
                    id: 'tabela-entre-verdes-5',
                    idJson: 'tabela-entre-verdes-5-JSON',
                    posicao: 1
                  }],
                  transicoes: [{
                    id: 'transicao-6',
                    idJson: 'transicao-6-JSON',
                    origem: {
                      id: 'E2',
                      idJson: 'E2-JSON'
                    },
                    destino: {
                      id: 'E3',
                      idJson: 'E3-JSON'
                    },
                    tabelaEntreVerdesTransicoes: [{
                      tabelaEntreVerdes: {
                        id: 'tabela-entre-verdes-5',
                        idJson: 'tabela-entre-verdes-5-JSON'
                      },
                      tempoVermelhoIntermitente: 5,
                      tempoVermelhoLimpeza: 3
                    }]
                  }, {
                    id: 'transicao-7',
                    idJson: 'transicao-7-JSON',
                    origem: {
                      id: 'E3',
                      idJson: 'E3-JSON'
                    },
                    destino: {
                      id: 'E1',
                      idJson: 'E1-JSON'
                    },
                    tabelaEntreVerdesTransicoes: [{
                      tabelaEntreVerdes: {
                        id: 'tabela-entre-verdes-5',
                        idJson: 'tabela-entre-verdes-5-JSON'
                      },
                      tempoVermelhoIntermitente: 5,
                      tempoVermelhoLimpeza: 3
                    }]
                  }, ],
                  transicoesComGanhoDePassagem: [{
                    id: 'transicao-perda-5',
                    idJson: 'transicao-perda-5-JSON',
                    origem: {
                      id: 'E1',
                      idJson: 'E1-JSON'
                    },
                    destino: {
                      id: 'E2',
                      idJson: 'E2-JSON'
                    },
                    tempoAtrasoGrupo: 0
                  }]
                }]
              }
            }, {
              id: 'EP3',
              idJson: 'EP3-JSON',
              tempoVerde: 12,
              tempoVerdeMinimo: null,
              tempoVerdeMaximo: null,
              tempoVerdeIntermediario: null,
              extensaoVerde: null,
              estagio: {
                id: 'E3',
                idJson: 'E3-JSON',
                gruposSemaforicos: [{
                  id: 'G2',
                  idJson: 'G2-JSON',
                  tipo: 'VEICULAR',
                  posicao: 2,
                  tabelasEntreVerdes: [{
                    id: 'tabela-entre-verdes-6',
                    idJson: 'tabela-entre-verdes-6-JSON',
                    posicao: 1
                  }],
                  transicoes: [{
                    id: 'transicao-8',
                    idJson: 'transicao-8-JSON',
                    origem: {
                      id: 'E3',
                      idJson: 'E3-JSON'
                    },
                    destino: {
                      id: 'E1',
                      idJson: 'E1-JSON'
                    },
                    tabelaEntreVerdesTransicoes: [{
                      tabelaEntreVerdes: {
                        id: 'tabela-entre-verdes-6',
                        idJson: 'tabela-entre-verdes-6-JSON'
                      },
                      tempoAmarelo: 3,
                      tempoVermelhoLimpeza: 3,
                      tempoAtrasoGrupo: 2
                    }]
                  }],
                  transicoesComGanhoDePassagem: [{
                    id: 'transicao-perda-6',
                    idJson: 'transicao-perda-6-JSON',
                    origem: {
                      id: 'E2',
                      idJson: 'E2-JSON'
                    },
                    destino: {
                      id: 'E3',
                      idJson: 'E3-JSON'
                    },
                    tempoAtrasoGrupo: 0
                  }]
                }, {
                  id: 'G4',
                  idJson: 'G4-JSON',
                  tipo: 'PEDESTRE',
                  posicao: 4,
                  tabelasEntreVerdes: [{
                    id: 'tabela-entre-verdes-5',
                    idJson: 'tabela-entre-verdes-5-JSON',
                    posicao: 1
                  }],
                  transicoes: [{
                    id: 'transicao-6',
                    idJson: 'transicao-6-JSON',
                    origem: {
                      id: 'E2',
                      idJson: 'E2-JSON'
                    },
                    destino: {
                      id: 'E3',
                      idJson: 'E3-JSON'
                    },
                    tabelaEntreVerdesTransicoes: [{
                      tabelaEntreVerdes: {
                        id: 'tabela-entre-verdes-5',
                        idJson: 'tabela-entre-verdes-5-JSON'
                      },
                      tempoVermelhoIntermitente: 5,
                      tempoVermelhoLimpeza: 3
                    }]
                  }, {
                    id: 'transicao-7',
                    idJson: 'transicao-7-JSON',
                    origem: {
                      id: 'E3',
                      idJson: 'E3-JSON'
                    },
                    destino: {
                      id: 'E1',
                      idJson: 'E1-JSON'
                    },
                    tabelaEntreVerdesTransicoes: [{
                      tabelaEntreVerdes: {
                        id: 'tabela-entre-verdes-5',
                        idJson: 'tabela-entre-verdes-5-JSON'
                      },
                      tempoVermelhoIntermitente: 5,
                      tempoVermelhoLimpeza: 3
                    }]
                  }, ],
                  transicoesComGanhoDePassagem: [{
                    id: 'transicao-perda-7',
                    idJson: 'transicao-perda-7-JSON',
                    origem: {
                      id: 'E2',
                      idJson: 'E2-JSON'
                    },
                    destino: {
                      id: 'E3',
                      idJson: 'E3-JSON'
                    },
                    tempoAtrasoGrupo: 0
                  }]
                }, ]
              }
            }
          ],
          posicaoGruposSemaforicos: {
            G1: 0,
            G2: 1,
            G3: 2,
            G4: 3,
            G5: 4
          },
          labelsGruposSemaforicos: {
            0: 'G1',
            1: 'G2',
            2: 'G3',
            3: 'G4',
            4: 'G5'
          },
          gruposSemaforicosPlanos: [
            {
              grupoSemaforico: {
                posicao: 1
              }
            }, {
              grupoSemaforico: {
                posicao: 2
              }
            }, {
              grupoSemaforico: {
                posicao: 3
              }
            }, {
              grupoSemaforico: {
                posicao: 4
              }
            }, {
              grupoSemaforico: {
                posicao: 5
              }
            }
          ]
        };
        valoresMinimos = {
          verdeMin: 1,
          verdeMinimoMin: 1
        };
      });
      describe('Descricao do plano', function() {
        it('Deve existir um objeto de plano', function() {
          expect(plano).toBeDefined();
        });

        it('O plano deve conter uma sequencia de estagios', function() {
          expect(plano.estagiosPlanos).toBeDefined();
        });

        it('A sequencia de estágios deve conter 3 estagios', function() {
          expect(plano.estagiosPlanos.length).toBe(3);
        });

        it('O E1 deve possuir 2 grupos semafóricos', function() {
          expect(plano.estagiosPlanos[0].estagio.gruposSemaforicos.length).toBe(2);
        });

        it('O E2 deve possuir 3 grupos semafóricos', function() {
          expect(plano.estagiosPlanos[1].estagio.gruposSemaforicos.length).toBe(3);
        });

        it('O E3 deve possuir 2 grupos semafóricos', function() {
          expect(plano.estagiosPlanos[2].estagio.gruposSemaforicos.length).toBe(2);
        });

        it('O G1 deve ter 1 transicao', function() {
          expect(plano.estagiosPlanos[0].estagio.gruposSemaforicos[0].transicoes.length).toBe(1);
        });

        it('O G2 deve ter 1 transicao', function() {
          expect(plano.estagiosPlanos[2].estagio.gruposSemaforicos[0].transicoes.length).toBe(1);
        });

        it('O G3 deve ter 2 transicoes', function() {
          expect(plano.estagiosPlanos[1].estagio.gruposSemaforicos[0].transicoes.length).toBe(2);
        });

        it('O G4 deve ter 2 transicoes', function() {
          expect(plano.estagiosPlanos[1].estagio.gruposSemaforicos[2].transicoes.length).toBe(2);
          expect(plano.estagiosPlanos[2].estagio.gruposSemaforicos[1].transicoes.length).toBe(2);
        });

        it('O G5 deve ter 2 transicoes', function() {
          expect(plano.estagiosPlanos[0].estagio.gruposSemaforicos[1].transicoes.length).toBe(2);
          expect(plano.estagiosPlanos[1].estagio.gruposSemaforicos[1].transicoes.length).toBe(2);
        });
      });

      describe('descricao da resposta', function() {
        beforeEach(function() {
          var diagramaIntervaloBuilder = new influunt.components.DiagramaIntervalos(plano, valoresMinimos);
          resposta = diagramaIntervaloBuilder.calcula();
        });

        it('Deve conter uma lista de erros vazia', function() {
          expect(resposta.erros.length).toBe(0);
        });

        it('Deve possuir uma lista de gruposSemaforicos contendo 5 grupos', function() {
          expect(resposta.gruposSemaforicos.length).toBe(5);
        });

        it('O G1 deve ter 5 intervalos', function() {
          expect(resposta.gruposSemaforicos[0].intervalos.length).toBe(5);
        });

        it('O G2 deve ter 5 intervalos', function() {
          expect(resposta.gruposSemaforicos[1].intervalos.length).toBe(5);
        });

        it('O G3 deve ter 5 intervalos', function() {
          expect(resposta.gruposSemaforicos[2].intervalos.length).toBe(5);
        });

        it('O G4 deve ter 4 intervalos', function() {
          expect(resposta.gruposSemaforicos[3].intervalos.length).toBe(4);
        });

        it('O G5 deve ter 5 intervalos', function() {
          expect(resposta.gruposSemaforicos[4].intervalos.length).toBe(5);
        });

        it('O label do grupos semaforicos devem estar corretos', function() {
          expect(resposta.gruposSemaforicos[0].labelPosicao).toBe('G1');
          expect(resposta.gruposSemaforicos[1].labelPosicao).toBe('G2');
          expect(resposta.gruposSemaforicos[2].labelPosicao).toBe('G3');
          expect(resposta.gruposSemaforicos[3].labelPosicao).toBe('G4');
          expect(resposta.gruposSemaforicos[4].labelPosicao).toBe('G5');
        });

        it('Os intervalos do G1 devem durar 6(vermelho) , 22(verde), 3(amarelo), 1(vermelho limpeza) e 28(vermelho) segundos', function() {
          expect(resposta.gruposSemaforicos[0].intervalos[0].duracao).toBe(6);
          expect(resposta.gruposSemaforicos[0].intervalos[1].duracao).toBe(22);
          expect(resposta.gruposSemaforicos[0].intervalos[2].duracao).toBe(3);
          expect(resposta.gruposSemaforicos[0].intervalos[3].duracao).toBe(1);
          expect(resposta.gruposSemaforicos[0].intervalos[4].duracao).toBe(28);

          expect(resposta.gruposSemaforicos[0].intervalos[0].status).toBe(3);
          expect(resposta.gruposSemaforicos[0].intervalos[1].status).toBe(1);
          expect(resposta.gruposSemaforicos[0].intervalos[2].status).toBe(2);
          expect(resposta.gruposSemaforicos[0].intervalos[3].status).toBe(6);
          expect(resposta.gruposSemaforicos[0].intervalos[4].status).toBe(3);
        });

        it('Os intervalos do G2 devem durar 2(verde) , 3(amarelo), 3(vermelho limpeza), 40(vermelho) e 12(verde) segundos', function() {
          expect(resposta.gruposSemaforicos[1].intervalos[0].duracao).toBe(2);
          expect(resposta.gruposSemaforicos[1].intervalos[1].duracao).toBe(3);
          expect(resposta.gruposSemaforicos[1].intervalos[2].duracao).toBe(3);
          expect(resposta.gruposSemaforicos[1].intervalos[3].duracao).toBe(40);
          expect(resposta.gruposSemaforicos[1].intervalos[4].duracao).toBe(12);

          expect(resposta.gruposSemaforicos[1].intervalos[0].status).toBe(1);
          expect(resposta.gruposSemaforicos[1].intervalos[1].status).toBe(2);
          expect(resposta.gruposSemaforicos[1].intervalos[2].status).toBe(6);
          expect(resposta.gruposSemaforicos[1].intervalos[3].status).toBe(3);
          expect(resposta.gruposSemaforicos[1].intervalos[4].status).toBe(1);
        });

        it('Os intervalos do G3 devem durar 32(vermelho) , 10(verde), 3(vermelhoIntermitente), 3(vermelho limpeza) e 12(vermelho) segundos', function() {
          expect(resposta.gruposSemaforicos[2].intervalos[0].duracao).toBe(32);
          expect(resposta.gruposSemaforicos[2].intervalos[1].duracao).toBe(10);
          expect(resposta.gruposSemaforicos[2].intervalos[2].duracao).toBe(3);
          expect(resposta.gruposSemaforicos[2].intervalos[3].duracao).toBe(3);
          expect(resposta.gruposSemaforicos[2].intervalos[4].duracao).toBe(12);

          expect(resposta.gruposSemaforicos[2].intervalos[0].status).toBe(3);
          expect(resposta.gruposSemaforicos[2].intervalos[1].status).toBe(1);
          expect(resposta.gruposSemaforicos[2].intervalos[2].status).toBe(4);
          expect(resposta.gruposSemaforicos[2].intervalos[3].status).toBe(6);
          expect(resposta.gruposSemaforicos[2].intervalos[4].status).toBe(3);
        });

        it('Os intervalos do G4 devem durar 5(vermelhoIntermitente), 3(vermelho limpeza), 24(vermelho), 28(verde) segundos', function() {
          expect(resposta.gruposSemaforicos[3].intervalos[0].duracao).toBe(5);
          expect(resposta.gruposSemaforicos[3].intervalos[1].duracao).toBe(3);
          expect(resposta.gruposSemaforicos[3].intervalos[2].duracao).toBe(24);
          expect(resposta.gruposSemaforicos[3].intervalos[3].duracao).toBe(28);

          expect(resposta.gruposSemaforicos[3].intervalos[0].status).toBe(4);
          expect(resposta.gruposSemaforicos[3].intervalos[1].status).toBe(6);
          expect(resposta.gruposSemaforicos[3].intervalos[2].status).toBe(3);
          expect(resposta.gruposSemaforicos[3].intervalos[3].status).toBe(1);
        });

        it('Os intervalos do G5 devem durar 8(vermelho), 34(verde), 3(vermelhoIntermitente), 3(vermelho limpeza) e 12(vermelho) segundos', function() {
          expect(resposta.gruposSemaforicos[4].intervalos[0].duracao).toBe(8);
          expect(resposta.gruposSemaforicos[4].intervalos[1].duracao).toBe(34);
          expect(resposta.gruposSemaforicos[4].intervalos[2].duracao).toBe(3);
          expect(resposta.gruposSemaforicos[4].intervalos[3].duracao).toBe(3);
          expect(resposta.gruposSemaforicos[4].intervalos[4].duracao).toBe(12);

          expect(resposta.gruposSemaforicos[4].intervalos[0].status).toBe(3);
          expect(resposta.gruposSemaforicos[4].intervalos[1].status).toBe(1);
          expect(resposta.gruposSemaforicos[4].intervalos[2].status).toBe(4);
          expect(resposta.gruposSemaforicos[4].intervalos[3].status).toBe(6);
          expect(resposta.gruposSemaforicos[4].intervalos[4].status).toBe(3);
        });

        it('A resposta deve ter uma lista de 3 estágios', function() {
          expect(resposta.estagios.length).toBe(3);
        });

        it('Os estágios devem ter as durações de E1: 28, E2: 14, E3: 18', function() {
          expect(resposta.estagios[0].duracao).toBe(28);
          expect(resposta.estagios[1].duracao).toBe(14);
          expect(resposta.estagios[2].duracao).toBe(18);
        });
      });

      describe('valida os erros', function() {
        it('Erro de tempo de ciclo menor', function() {
          plano.tempoCiclo = 40;
          var diagramaIntervaloBuilder = new influunt.components.DiagramaIntervalos(plano, valoresMinimos);
          resposta = diagramaIntervaloBuilder.calcula();
          expect('Tempo de Ciclo é diferente da soma dos tempos dos estágios.').toBe(resposta.erros[0]);
        });

        it('Erro de tempo de ciclo menor', function() {
          delete plano.tempoCiclo;
          var diagramaIntervaloBuilder = new influunt.components.DiagramaIntervalos(plano, valoresMinimos);
          resposta = diagramaIntervaloBuilder.calcula();
          expect('Tempo de Ciclo é diferente da soma dos tempos dos estágios.').toBe(resposta.erros[0]);
        });
      });
    });


    describe('Configuração de planos com estágio duplo', function() {
      var plano, valoresMinimos, resposta;
      beforeEach(function() {
        plano = {
          posicao: 1,
          tempoCiclo: 60,
          posicaoTabelaEntreVerde: 1,
          defasagem: 0,
          quantidadeGruposSemaforicos: 4,
          modoOperacao: 'TEMPO_FIXO_ISOLADO',
          estagiosPlanos: [{
            id: 'EP1',
            idJson: 'EP1-JSON',
            tempoVerde: 20,
            tempoVerdeMinimo: null,
            tempoVerdeMaximo: null,
            tempoVerdeIntermediario: null,
            extensaoVerde: null,
            estagio: {
              id: 'E1',
              idJson: 'E1-JSON',
              gruposSemaforicos: [{
                id: 'G5',
                idJson: 'G5-JSON',
                tipo: 'VEICULAR',
                posicao: 5,
                tabelasEntreVerdes: [{
                  id: 'tabela-entre-verdes-1',
                  idJson: 'tabela-entre-verdes-1-JSON',
                  posicao: 1
                }, {
                  id: 'tabela-entre-verdes-1-2',
                  idJson: 'tabela-entre-verdes-1-2-JSON',
                  posicao: 2
                }, ],
                transicoes: [{
                  id: 'transicao-1',
                  idJson: 'transicao-1-JSON',
                  origem: {
                    id: 'E1',
                    idJson: 'E1-JSON'
                  },
                  destino: {
                    id: 'E2',
                    idJson: 'E2-JSON'
                  },
                  tabelaEntreVerdesTransicoes: [{
                    tabelaEntreVerdes: {
                      id: 'tabela-entre-verdes-1',
                      idJson: 'tabela-entre-verdes-1-JSON'
                    },
                    tempoAmarelo: 3,
                    tempoVermelhoLimpeza: 1
                  }]
                }],
                transicoesComGanhoDePassagem: [{
                  id: 'transicao-perda-1',
                  idJson: 'transicao-perda-1-JSON',
                  origem: {
                    id: 'E2',
                    idJson: 'E2-JSON'
                  },
                  destino: {
                    id: 'E1',
                    idJson: 'E1-JSON'
                  },
                  tempoAtrasoGrupo: 2
                }]
              }, {
                id: 'G8',
                idJson: 'G8-JSON',
                tipo: 'PEDESTRE',
                posicao: 8,
                tabelasEntreVerdes: [{
                  id: 'tabela-entre-verdes-2',
                  idJson: 'tabela-entre-verdes-2-JSON',
                  posicao: 1
                }],
                transicoes: [{
                  id: 'transicao-3',
                  idJson: 'transicao-3-JSON',
                  origem: {
                    id: 'E1',
                    idJson: 'E1-JSON'
                  },
                  destino: {
                    id: 'E2',
                    idJson: 'E2-JSON'
                  },
                  tabelaEntreVerdesTransicoes: [{
                    tabelaEntreVerdes: {
                      id: 'tabela-entre-verdes-2',
                      idJson: 'tabela-entre-verdes-2-JSON'
                    },
                    tempoVermelhoIntermitente: 3,
                    tempoVermelhoLimpeza: 3
                  }]
                }],
                transicoesComGanhoDePassagem: [{
                  id: 'transicao-perda-2',
                  idJson: 'transicao-perda-2-JSON',
                  origem: {
                    id: 'E3',
                    idJson: 'E3-JSON'
                  },
                  destino: {
                    id: 'E1',
                    idJson: 'E1-JSON'
                  },
                  tempoAtrasoGrupo: 0
                }]
              }, ]
            }
          }, {
            id: 'EP2',
            idJson: 'EP2-JSON',
            tempoVerde: 10,
            tempoVerdeMinimo: null,
            tempoVerdeMaximo: null,
            tempoVerdeIntermediario: null,
            extensaoVerde: null,
            estagio: {
              id: 'E2',
              idJson: 'E2-JSON',
              gruposSemaforicos: [{
                id: 'G6',
                idJson: 'G6-JSON',
                tipo: 'PEDESTRE',
                posicao: 6,
                tabelasEntreVerdes: [{
                  id: 'tabela-entre-verdes-3',
                  idJson: 'tabela-entre-verdes-3-JSON',
                  posicao: 1
                }],
                transicoes: [{
                  id: 'transicao-4',
                  idJson: 'transicao-4-JSON',
                  origem: {
                    id: 'E2',
                    idJson: 'E2-JSON'
                  },
                  destino: {
                    id: 'E1',
                    idJson: 'E1-JSON'
                  },
                  tabelaEntreVerdesTransicoes: [{
                    tabelaEntreVerdes: {
                      id: 'tabela-entre-verdes-3',
                      idJson: 'tabela-entre-verdes-3-JSON'
                    },
                    tempoVermelhoIntermitente: 3,
                    tempoVermelhoLimpeza: 3
                  }]
                }],
                transicoesComGanhoDePassagem: [{
                  id: 'transicao-perda-3',
                  idJson: 'transicao-perda-3-JSON',
                  origem: {
                    id: 'E1',
                    idJson: 'E1-JSON'
                  },
                  destino: {
                    id: 'E2',
                    idJson: 'E2-JSON'
                  },
                  tempoAtrasoGrupo: 0
                }]
              }, {
                id: 'G8',
                idJson: 'G8-JSON',
                tipo: 'PEDESTRE',
                posicao: 8,
                tabelasEntreVerdes: [{
                  id: 'tabela-entre-verdes-2',
                  idJson: 'tabela-entre-verdes-2-JSON',
                  posicao: 1
                }],
                transicoes: [{
                  id: 'transicao-3',
                  idJson: 'transicao-3-JSON',
                  origem: {
                    id: 'E1',
                    idJson: 'E1-JSON'
                  },
                  destino: {
                    id: 'E2',
                    idJson: 'E2-JSON'
                  },
                  tabelaEntreVerdesTransicoes: [{
                    tabelaEntreVerdes: {
                      id: 'tabela-entre-verdes-2',
                      idJson: 'tabela-entre-verdes-2-JSON'
                    },
                    tempoVermelhoIntermitente: 3,
                    tempoVermelhoLimpeza: 3
                  }]
                }, {
                  id: 'transicao-13',
                  idJson: 'transicao-13-JSON',
                  origem: {
                    id: 'E2',
                    idJson: 'E2-JSON'
                  },
                  destino: {
                    id: 'E1',
                    idJson: 'E1-JSON'
                  },
                  tabelaEntreVerdesTransicoes: [{
                    tabelaEntreVerdes: {
                      id: 'tabela-entre-verdes-2',
                      idJson: 'tabela-entre-verdes-2-JSON'
                    },
                    tempoVermelhoIntermitente: 3,
                    tempoVermelhoLimpeza: 3
                  }]
                }],
                transicoesComGanhoDePassagem: [{
                  id: 'transicao-perda-4',
                  idJson: 'transicao-perda-4-JSON',
                  origem: {
                    id: 'E1',
                    idJson: 'E1-JSON'
                  },
                  destino: {
                    id: 'E2',
                    idJson: 'E2-JSON'
                  },
                  tempoAtrasoGrupo: 0
                }]
              }, {
                id: 'G7',
                idJson: 'G7-JSON',
                tipo: 'PEDESTRE',
                posicao: 7,
                tabelasEntreVerdes: [{
                  id: 'tabela-entre-verdes-5',
                  idJson: 'tabela-entre-verdes-5-JSON',
                  posicao: 1
                }],
                transicoes: [{
                  id: 'transicao-6',
                  idJson: 'transicao-6-JSON',
                  origem: {
                    id: 'E2',
                    idJson: 'E2-JSON'
                  },
                  destino: {
                    id: 'E1',
                    idJson: 'E1-JSON'
                  },
                  tabelaEntreVerdesTransicoes: [{
                    tabelaEntreVerdes: {
                      id: 'tabela-entre-verdes-5',
                      idJson: 'tabela-entre-verdes-5-JSON'
                    },
                    tempoVermelhoIntermitente: 3,
                    tempoVermelhoLimpeza: 3
                  }]
                }],
                transicoesComGanhoDePassagem: [{
                  id: 'transicao-perda-5',
                  idJson: 'transicao-perda-5-JSON',
                  origem: {
                    id: 'E1',
                    idJson: 'E1-JSON'
                  },
                  destino: {
                    id: 'E2',
                    idJson: 'E2-JSON'
                  },
                  tempoAtrasoGrupo: 0
                }]
              }]
            }
          }, {
            id: 'EP3',
            idJson: 'EP3-JSON',
            tempoVerde: 20,
            tempoVerdeMinimo: null,
            tempoVerdeMaximo: null,
            tempoVerdeIntermediario: null,
            extensaoVerde: null,
            estagio: {
              id: 'E1',
              idJson: 'E1-JSON',
              gruposSemaforicos: [{
                id: 'G5',
                idJson: 'G5-JSON',
                tipo: 'VEICULAR',
                posicao: 5,
                tabelasEntreVerdes: [{
                  id: 'tabela-entre-verdes-1',
                  idJson: 'tabela-entre-verdes-1-JSON',
                  posicao: 1
                }, {
                  id: 'tabela-entre-verdes-1-2',
                  idJson: 'tabela-entre-verdes-1-2-JSON',
                  posicao: 2
                }, ],
                transicoes: [{
                  id: 'transicao-1',
                  idJson: 'transicao-1-JSON',
                  origem: {
                    id: 'E1',
                    idJson: 'E1-JSON'
                  },
                  destino: {
                    id: 'E2',
                    idJson: 'E2-JSON'
                  },
                  tabelaEntreVerdesTransicoes: [{
                    tabelaEntreVerdes: {
                      id: 'tabela-entre-verdes-1',
                      idJson: 'tabela-entre-verdes-1-JSON'
                    },
                    tempoAmarelo: 3,
                    tempoVermelhoLimpeza: 1
                  }]
                }],
                transicoesComGanhoDePassagem: [{
                  id: 'transicao-perda-1',
                  idJson: 'transicao-perda-1-JSON',
                  origem: {
                    id: 'E3',
                    idJson: 'E3-JSON'
                  },
                  destino: {
                    id: 'E1',
                    idJson: 'E1-JSON'
                  },
                  tempoAtrasoGrupo: 2
                }]
              }, {
                id: 'G8',
                idJson: 'G8-JSON',
                tipo: 'PEDESTRE',
                posicao: 8,
                tabelasEntreVerdes: [{
                  id: 'tabela-entre-verdes-2',
                  idJson: 'tabela-entre-verdes-2-JSON',
                  posicao: 1
                }],
                transicoes: [{
                  id: 'transicao-3',
                  idJson: 'transicao-3-JSON',
                  origem: {
                    id: 'E1',
                    idJson: 'E1-JSON'
                  },
                  destino: {
                    id: 'E2',
                    idJson: 'E2-JSON'
                  },
                  tabelaEntreVerdesTransicoes: [{
                    tabelaEntreVerdes: {
                      id: 'tabela-entre-verdes-2',
                      idJson: 'tabela-entre-verdes-2-JSON'
                    },
                    tempoVermelhoIntermitente: 3,
                    tempoVermelhoLimpeza: 3
                  }]
                }, {
                  id: 'transicao-13',
                  idJson: 'transicao-13-JSON',
                  origem: {
                    id: 'E2',
                    idJson: 'E2-JSON'
                  },
                  destino: {
                    id: 'E1',
                    idJson: 'E1-JSON'
                  },
                  tabelaEntreVerdesTransicoes: [{
                    tabelaEntreVerdes: {
                      id: 'tabela-entre-verdes-2',
                      idJson: 'tabela-entre-verdes-2-JSON'
                    },
                    tempoVermelhoIntermitente: 3,
                    tempoVermelhoLimpeza: 3
                  }]
                }],
                transicoesComGanhoDePassagem: [{
                  id: 'transicao-perda-2',
                  idJson: 'transicao-perda-2-JSON',
                  origem: {
                    id: 'E3',
                    idJson: 'E3-JSON'
                  },
                  destino: {
                    id: 'E1',
                    idJson: 'E1-JSON'
                  },
                  tempoAtrasoGrupo: 0
                }]
              }, ]
            }
          }, ],
          posicaoGruposSemaforicos: {
            G5: 0,
            G6: 1,
            G7: 2,
            G8: 3
          },
          labelsGruposSemaforicos: {
            0: 'G5',
            1: 'G6',
            2: 'G7',
            3: 'G8'
          },
          gruposSemaforicosPlanos: [
            {
              grupoSemaforico: {
                posicao: 1
              }
            }, {
              grupoSemaforico: {
                posicao: 2
              }
            }, {
              grupoSemaforico: {
                posicao: 3
              }
            }, {
              grupoSemaforico: {
                posicao: 4
              }
            }
          ]
        };
        valoresMinimos = {
          verdeMin: 1,
          verdeMinimoMin: 1
        };
      });
      describe('descricao da resposta', function() {
        beforeEach(function() {
          var diagramaIntervaloBuilder = new influunt.components.DiagramaIntervalos(plano, valoresMinimos);
          resposta = diagramaIntervaloBuilder.calcula();
        });

        it('Deve conter uma lista de erros vazia', function() {
          expect(resposta.erros.length).toBe(0);
        });

        it('Deve possuir uma lista de gruposSemaforicos contendo 4 grupos', function() {
          expect(resposta.gruposSemaforicos.length).toBe(4);
        });

        it('O G5 deve ter 5 intervalos', function() {
          expect(resposta.gruposSemaforicos[0].intervalos.length).toBe(5);
        });

        it('O G6 deve ter 5 intervalos', function() {
          expect(resposta.gruposSemaforicos[1].intervalos.length).toBe(5);
        });

        it('O G7 deve ter 5 intervalos', function() {
          expect(resposta.gruposSemaforicos[2].intervalos.length).toBe(5);
        });

        it('O G8 deve ter 5 intervalos', function() {
          expect(resposta.gruposSemaforicos[3].intervalos.length).toBe(1);
        });

        it('O label do grupos semaforicos devem estar corretos', function() {
          expect(resposta.gruposSemaforicos[0].labelPosicao).toBe('G5');
          expect(resposta.gruposSemaforicos[1].labelPosicao).toBe('G6');
          expect(resposta.gruposSemaforicos[2].labelPosicao).toBe('G7');
          expect(resposta.gruposSemaforicos[3].labelPosicao).toBe('G8');
        });

        it('Os intervalos do G5 devem durar 20(verde) , 3(amarelo), 1(vermelho limpeza), 16(vermelho) e 20(verde) segundos', function() {
          expect(resposta.gruposSemaforicos[0].intervalos[0].duracao).toBe(20);
          expect(resposta.gruposSemaforicos[0].intervalos[1].duracao).toBe(3);
          expect(resposta.gruposSemaforicos[0].intervalos[2].duracao).toBe(1);
          expect(resposta.gruposSemaforicos[0].intervalos[3].duracao).toBe(16);
          expect(resposta.gruposSemaforicos[0].intervalos[4].duracao).toBe(20);

          expect(resposta.gruposSemaforicos[0].intervalos[0].status).toBe(1);
          expect(resposta.gruposSemaforicos[0].intervalos[1].status).toBe(2);
          expect(resposta.gruposSemaforicos[0].intervalos[2].status).toBe(6);
          expect(resposta.gruposSemaforicos[0].intervalos[3].status).toBe(3);
          expect(resposta.gruposSemaforicos[0].intervalos[4].status).toBe(1);
        });

        it('Os intervalos do G6 devem durar 24(vermelho) , 10(verde), 3(vermelhoIntermitente), 3(vermelho limpeza) e 20(vermelho) segundos', function() {
          expect(resposta.gruposSemaforicos[1].intervalos[0].duracao).toBe(24);
          expect(resposta.gruposSemaforicos[1].intervalos[1].duracao).toBe(10);
          expect(resposta.gruposSemaforicos[1].intervalos[2].duracao).toBe(3);
          expect(resposta.gruposSemaforicos[1].intervalos[3].duracao).toBe(3);
          expect(resposta.gruposSemaforicos[1].intervalos[4].duracao).toBe(20);

          expect(resposta.gruposSemaforicos[1].intervalos[0].status).toBe(3);
          expect(resposta.gruposSemaforicos[1].intervalos[1].status).toBe(1);
          expect(resposta.gruposSemaforicos[1].intervalos[2].status).toBe(4);
          expect(resposta.gruposSemaforicos[1].intervalos[3].status).toBe(6);
          expect(resposta.gruposSemaforicos[1].intervalos[4].status).toBe(3);
        });

        it('Os intervalos do G7 devem durar 24(vermelho) , 10(verde), 3(vermelhoIntermitente), 3(vermelho limpeza) e 20(vermelho) segundos', function() {
          expect(resposta.gruposSemaforicos[2].intervalos[0].duracao).toBe(24);
          expect(resposta.gruposSemaforicos[2].intervalos[1].duracao).toBe(10);
          expect(resposta.gruposSemaforicos[2].intervalos[2].duracao).toBe(3);
          expect(resposta.gruposSemaforicos[2].intervalos[3].duracao).toBe(3);
          expect(resposta.gruposSemaforicos[2].intervalos[4].duracao).toBe(20);

          expect(resposta.gruposSemaforicos[2].intervalos[0].status).toBe(3);
          expect(resposta.gruposSemaforicos[2].intervalos[1].status).toBe(1);
          expect(resposta.gruposSemaforicos[2].intervalos[2].status).toBe(4);
          expect(resposta.gruposSemaforicos[2].intervalos[3].status).toBe(6);
          expect(resposta.gruposSemaforicos[2].intervalos[4].status).toBe(3);
        });

        it('Os intervalos do G8 devem durar 62(verde)', function() {
          expect(resposta.gruposSemaforicos[3].intervalos[0].duracao).toBe(60);

          expect(resposta.gruposSemaforicos[3].intervalos[0].status).toBe(1);
        });

        it('A resposta deve ter uma lista de 3 estágios', function() {
          expect(resposta.estagios.length).toBe(3);
        });

        it('Os estágios devem ter as durações de E1: 20, E2: 14, E3: 26', function() {
          expect(resposta.estagios[0].duracao).toBe(20);
          expect(resposta.estagios[1].duracao).toBe(14);
          expect(resposta.estagios[2].duracao).toBe(26);
        });
      });
    });

    describe('atraso de grupo automatico', function () {
      var controlador, plano, anel, grupos, resposta, valoresMinimos, geraDadosDiagramaIntervalo;
      beforeEach(inject(function(_geraDadosDiagramaIntervalo_) {
        geraDadosDiagramaIntervalo = _geraDadosDiagramaIntervalo_;
        controlador = ControladorAtrasoGrupoAutomatico.get();
        anel = _.find(controlador.aneis, {posicao: 1});
        plano = _
          .chain(anel.planos)
          .map(function(p) { return _.find(controlador.planos, {idJson: p.idJson}); })
          .find({posicao: 1})
          .value();

        grupos = _
          .chain(anel.gruposSemaforicos)
          .map(function(g) { return _.find(controlador.gruposSemaforicos, {idJson: g.idJson}); })
          .orderBy('posicao')
          .value();

        valoresMinimos = {
          verdeMin: controlador.verdeMin,
          verdeMinimoMin: controlador.verdeMinimoMin
        };
      }));

      describe('Atraso automático em G4-G5', function() {
        beforeEach(function() {
          var planoDiagrama = geraDadosDiagramaIntervalo.gerar(plano, anel, grupos, controlador);
          var diagramaIntervaloBuilder = new influunt.components.DiagramaIntervalos(planoDiagrama, valoresMinimos);
          resposta = diagramaIntervaloBuilder.calcula();
        });

        it('Deve conter uma lista de erros vazia', function() {
          expect(resposta.erros.length).toBe(0);
        });

        it('Deve possuir 5 grupos semafóricos e 3 estágios', function() {
          expect(resposta.gruposSemaforicos.length).toBe(5);
          expect(resposta.estagios.length).toBe(3);
        });

        it('Os intervalos do G1 devem durar 5(vermelho), 40(verde), 3(amarelo), 2(vermelho limpeza) e 50(vermelho)', function() {
          expect(resposta.gruposSemaforicos[0].intervalos.length).toBe(5);

          expect(resposta.gruposSemaforicos[0].intervalos[0].duracao).toBe(5);
          expect(resposta.gruposSemaforicos[0].intervalos[1].duracao).toBe(40);
          expect(resposta.gruposSemaforicos[0].intervalos[2].duracao).toBe(3);
          expect(resposta.gruposSemaforicos[0].intervalos[3].duracao).toBe(2);
          expect(resposta.gruposSemaforicos[0].intervalos[4].duracao).toBe(50);

          expect(resposta.gruposSemaforicos[0].intervalos[0].status).toBe(3);
          expect(resposta.gruposSemaforicos[0].intervalos[1].status).toBe(1);
          expect(resposta.gruposSemaforicos[0].intervalos[2].status).toBe(2);
          expect(resposta.gruposSemaforicos[0].intervalos[3].status).toBe(6);
          expect(resposta.gruposSemaforicos[0].intervalos[4].status).toBe(3);
        });

        it('Os intervalos do G2 devem durar 50(vermelho), 20(verde), 3(amarelo), 2(vermelho limpeza) e 25(vermelho)', function() {
          expect(resposta.gruposSemaforicos[1].intervalos.length).toBe(5);

          expect(resposta.gruposSemaforicos[1].intervalos[0].duracao).toBe(50);
          expect(resposta.gruposSemaforicos[1].intervalos[1].duracao).toBe(20);
          expect(resposta.gruposSemaforicos[1].intervalos[2].duracao).toBe(3);
          expect(resposta.gruposSemaforicos[1].intervalos[3].duracao).toBe(2);
          expect(resposta.gruposSemaforicos[1].intervalos[4].duracao).toBe(25);

          expect(resposta.gruposSemaforicos[1].intervalos[0].status).toBe(3);
          expect(resposta.gruposSemaforicos[1].intervalos[1].status).toBe(1);
          expect(resposta.gruposSemaforicos[1].intervalos[2].status).toBe(2);
          expect(resposta.gruposSemaforicos[1].intervalos[3].status).toBe(6);
          expect(resposta.gruposSemaforicos[1].intervalos[4].status).toBe(3);
        });

        it('Os intervalos do G3 devem durar 3(amarelo), 2(vermelho limpeza) e 75(vermelho) 20(verde)', function() {
          expect(resposta.gruposSemaforicos[2].intervalos.length).toBe(4);

          expect(resposta.gruposSemaforicos[2].intervalos[0].duracao).toBe(3);
          expect(resposta.gruposSemaforicos[2].intervalos[1].duracao).toBe(2);
          expect(resposta.gruposSemaforicos[2].intervalos[2].duracao).toBe(75);
          expect(resposta.gruposSemaforicos[2].intervalos[3].duracao).toBe(20);

          expect(resposta.gruposSemaforicos[2].intervalos[0].status).toBe(2);
          expect(resposta.gruposSemaforicos[2].intervalos[1].status).toBe(6);
          expect(resposta.gruposSemaforicos[2].intervalos[2].status).toBe(3);
          expect(resposta.gruposSemaforicos[2].intervalos[3].status).toBe(1);
        });

        it('Os intervalos do G4 devem durar 55(vermelho), 20(verde), 9(vermelho intermitente), 1(vermelho limpeza) e 15(vermelho)', function() {
          expect(resposta.gruposSemaforicos[3].intervalos.length).toBe(5);

          expect(resposta.gruposSemaforicos[3].intervalos[0].duracao).toBe(55);
          expect(resposta.gruposSemaforicos[3].intervalos[1].duracao).toBe(20);
          expect(resposta.gruposSemaforicos[3].intervalos[2].duracao).toBe(9);
          expect(resposta.gruposSemaforicos[3].intervalos[3].duracao).toBe(1);
          expect(resposta.gruposSemaforicos[3].intervalos[4].duracao).toBe(15);

          expect(resposta.gruposSemaforicos[3].intervalos[0].status).toBe(3);
          expect(resposta.gruposSemaforicos[3].intervalos[1].status).toBe(1);
          expect(resposta.gruposSemaforicos[3].intervalos[2].status).toBe(4);
          expect(resposta.gruposSemaforicos[3].intervalos[3].status).toBe(6);
          expect(resposta.gruposSemaforicos[3].intervalos[4].status).toBe(3);
        });

        it('Os intervalos do G5 devem durar 50(verde), 3(amarelo), 2(vermelho limpeza), 30(vermelho) e 15(verde)', function() {
          expect(resposta.gruposSemaforicos[4].intervalos.length).toBe(5);

          expect(resposta.gruposSemaforicos[4].intervalos[0].duracao).toBe(50);
          expect(resposta.gruposSemaforicos[4].intervalos[1].duracao).toBe(3);
          expect(resposta.gruposSemaforicos[4].intervalos[2].duracao).toBe(2);
          expect(resposta.gruposSemaforicos[4].intervalos[3].duracao).toBe(30);
          expect(resposta.gruposSemaforicos[4].intervalos[4].duracao).toBe(15);

          expect(resposta.gruposSemaforicos[4].intervalos[0].status).toBe(1);
          expect(resposta.gruposSemaforicos[4].intervalos[1].status).toBe(2);
          expect(resposta.gruposSemaforicos[4].intervalos[2].status).toBe(6);
          expect(resposta.gruposSemaforicos[4].intervalos[3].status).toBe(3);
          expect(resposta.gruposSemaforicos[4].intervalos[4].status).toBe(1);
        });
      });

      describe('Atraso automático em G4-G5', function () {
        beforeEach(function() {
          var ids = _.map(plano.estagiosPlanos, 'idJson');
          var estagiosPlanos = controlador.estagiosPlanos.filter(function(ep) { return ids.indexOf(ep.idJson) >= 0; });
          var ep1Original = _.find(estagiosPlanos, {posicao: 1});
          var ep2Original = _.find(estagiosPlanos, {posicao: 2});
          var ep3Original = _.find(estagiosPlanos, {posicao: 3});

          ep3Original.posicao = 1;
          ep1Original.posicao = 2;
          ep2Original.posicao = 3;

          plano.estagiosPlanos = _
            .chain(estagiosPlanos)
            .orderBy('posicao')
            .map(function(e) { return {idJson: e.idJson}; })
            .value();

          var planoDiagrama = geraDadosDiagramaIntervalo.gerar(plano, anel, grupos, controlador);
          var diagramaIntervaloBuilder = new influunt.components.DiagramaIntervalos(planoDiagrama, valoresMinimos);
          resposta = diagramaIntervaloBuilder.calcula();
        });

        it('Deve conter uma lista de erros vazia', function() {
          expect(resposta.erros.length).toBe(0);
        });

        it('Deve possuir 5 grupos semafóricos e 3 estágios', function() {
          expect(resposta.gruposSemaforicos.length).toBe(5);
          expect(resposta.estagios.length).toBe(3);
        });

        it('Os intervalos do G1 devem durar 35(vermelho), 40(verde), 3(amarelo), 2(vermelho limpeza) e 20(vermelho)', function() {
          expect(resposta.gruposSemaforicos[0].intervalos.length).toBe(5);

          expect(resposta.gruposSemaforicos[0].intervalos[0].duracao).toBe(35);
          expect(resposta.gruposSemaforicos[0].intervalos[1].duracao).toBe(40);
          expect(resposta.gruposSemaforicos[0].intervalos[2].duracao).toBe(3);
          expect(resposta.gruposSemaforicos[0].intervalos[3].duracao).toBe(2);
          expect(resposta.gruposSemaforicos[0].intervalos[4].duracao).toBe(20);

          expect(resposta.gruposSemaforicos[0].intervalos[0].status).toBe(3);
          expect(resposta.gruposSemaforicos[0].intervalos[1].status).toBe(1);
          expect(resposta.gruposSemaforicos[0].intervalos[2].status).toBe(2);
          expect(resposta.gruposSemaforicos[0].intervalos[3].status).toBe(6);
          expect(resposta.gruposSemaforicos[0].intervalos[4].status).toBe(3);
        });

        it('Os intervalos do G2 devem durar 3(amarelo), 2(vermelho limpeza), 75(vermelho), 20(verde)', function() {
          expect(resposta.gruposSemaforicos[1].intervalos.length).toBe(4);

          expect(resposta.gruposSemaforicos[1].intervalos[0].duracao).toBe(3);
          expect(resposta.gruposSemaforicos[1].intervalos[1].duracao).toBe(2);
          expect(resposta.gruposSemaforicos[1].intervalos[2].duracao).toBe(75);
          expect(resposta.gruposSemaforicos[1].intervalos[3].duracao).toBe(20);

          expect(resposta.gruposSemaforicos[1].intervalos[0].status).toBe(2);
          expect(resposta.gruposSemaforicos[1].intervalos[1].status).toBe(6);
          expect(resposta.gruposSemaforicos[1].intervalos[2].status).toBe(3);
          expect(resposta.gruposSemaforicos[1].intervalos[3].status).toBe(1);
        });

        it('Os intervalos do G3 devem durar 10(vermelho), 20(verde) 3(amarelo) 2(vermelho limpeza) e 65(vermelho)', function() {
          expect(resposta.gruposSemaforicos[2].intervalos.length).toBe(5);

          expect(resposta.gruposSemaforicos[2].intervalos[0].duracao).toBe(10);
          expect(resposta.gruposSemaforicos[2].intervalos[1].duracao).toBe(20);
          expect(resposta.gruposSemaforicos[2].intervalos[2].duracao).toBe(3);
          expect(resposta.gruposSemaforicos[2].intervalos[3].duracao).toBe(2);
          expect(resposta.gruposSemaforicos[2].intervalos[4].duracao).toBe(65);

          expect(resposta.gruposSemaforicos[2].intervalos[0].status).toBe(3);
          expect(resposta.gruposSemaforicos[2].intervalos[1].status).toBe(1);
          expect(resposta.gruposSemaforicos[2].intervalos[2].status).toBe(2);
          expect(resposta.gruposSemaforicos[2].intervalos[3].status).toBe(6);
          expect(resposta.gruposSemaforicos[2].intervalos[4].status).toBe(3);
        });

        it('Os intervalos do G4 devem durar 5(verde), 9(vermelho intermitente), 1(vermelho limpeza), 70(vermelho) e 15(verde)', function() {
          expect(resposta.gruposSemaforicos[3].intervalos.length).toBe(5);

          expect(resposta.gruposSemaforicos[3].intervalos[0].duracao).toBe(5);
          expect(resposta.gruposSemaforicos[3].intervalos[1].duracao).toBe(9);
          expect(resposta.gruposSemaforicos[3].intervalos[2].duracao).toBe(1);
          expect(resposta.gruposSemaforicos[3].intervalos[3].duracao).toBe(70);
          expect(resposta.gruposSemaforicos[3].intervalos[4].duracao).toBe(15);

          expect(resposta.gruposSemaforicos[3].intervalos[0].status).toBe(1);
          expect(resposta.gruposSemaforicos[3].intervalos[1].status).toBe(4);
          expect(resposta.gruposSemaforicos[3].intervalos[2].status).toBe(6);
          expect(resposta.gruposSemaforicos[3].intervalos[3].status).toBe(3);
          expect(resposta.gruposSemaforicos[3].intervalos[4].status).toBe(1);
        });

        it('Os intervalos do G5 devem durar 15(vermelho), 65(verde), 3(amarelo), 2(vermelho limpeza) e 15(vermelho)', function() {
          expect(resposta.gruposSemaforicos[4].intervalos.length).toBe(5);

          expect(resposta.gruposSemaforicos[4].intervalos[0].duracao).toBe(15);
          expect(resposta.gruposSemaforicos[4].intervalos[1].duracao).toBe(65);
          expect(resposta.gruposSemaforicos[4].intervalos[2].duracao).toBe(3);
          expect(resposta.gruposSemaforicos[4].intervalos[3].duracao).toBe(2);
          expect(resposta.gruposSemaforicos[4].intervalos[4].duracao).toBe(15);

          expect(resposta.gruposSemaforicos[4].intervalos[0].status).toBe(3);
          expect(resposta.gruposSemaforicos[4].intervalos[1].status).toBe(1);
          expect(resposta.gruposSemaforicos[4].intervalos[2].status).toBe(2);
          expect(resposta.gruposSemaforicos[4].intervalos[3].status).toBe(6);
          expect(resposta.gruposSemaforicos[4].intervalos[4].status).toBe(3);
        });
      });
    });

  });

})();
