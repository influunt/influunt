(function () {
  'use strict';

  describe('Configuração de planos', function() {
    var plano, resposta;
    describe('Descricao do plano', function() {
      beforeEach(function() {
        plano = {
          posicao: 1,
          tempoCiclo: 120,
          posicaoTabelaEntreVerdes: 1,
          defasagem: 0,
          quantidadeGruposSemaforicos: 5,
          modoOperacao: 'TEMPO_FIXO_ISOLADO',
          sequenciaEstagios: [
            {
              id: 'E1',
              tempoVerde: 30,
              tempoVerdeMinimo: null,
              tempoVerdeMaximo: null,
              tempoVerdeIntermediario: null,
              extensaoVerde: null,
              gruposSemaforicos: [
                {
                  id: 'G1',
                  tipo: 'VEICULAR',
                  posicao: 1,
                  tabelaEntreVerdes: [
                    {
                      id: 'tabela-entre-verdes-1',
                      posicao: 1
                    },
                    {
                      id: 'tabela-entre-verdes-1-2',
                      posicao: 2
                    },
                  ],
                  transicoes: [
                    {
                      id: 'transicao-1',
                      origem: {id: 'E1'},
                      destino: {id: 'E2'},
                      tabelaEntreVerdesTransicoes: [
                        {
                          tabelaEntreVerdes: {
                            id: 'tabela-entre-verdes-1'
                          },
                          tempoAmarelo: 10,
                          tempoVermelhoLimpeza: 10
                        }
                      ]
                    }
                  ]
                },
                {
                  id: 'G5',
                  tipo: 'PEDESTRE',
                  posicao: 5,
                  tabelaEntreVerdes: [
                    {
                      id: 'tabela-entre-verdes-2',
                      posicao: 1
                    }
                  ],
                  transicoes: [
                    {
                      id: 'transicao-3',
                      origem: {id: 'E1'},
                      destino: {id: 'E2'},
                      tabelaEntreVerdesTransicoes: [
                        {
                          tabelaEntreVerdes: {
                            id: 'tabela-entre-verdes-2'
                          },
                          tempoVermelhoIntermitente: 10,
                          tempoVermelhoLimpeza: 10
                        }
                      ]
                    },
                    {
                      id: 'transicao-13',
                      origem: {id: 'E2'},
                      destino: {id: 'E3'},
                      tabelaEntreVerdesTransicoes: [
                        {
                          tabelaEntreVerdes: {
                            id: 'tabela-entre-verdes-2'
                          },
                          tempoVermelhoIntermitente: 10,
                          tempoVermelhoLimpeza: 10
                        }
                      ]
                    }
                  ]
                },
              ]
            },
            {
              id: 'E2',
              tempoVerde: 10,
              tempoVerdeMinimo: null,
              tempoVerdeMaximo: null,
              tempoVerdeIntermediario: null,
              extensaoVerde: null,
              gruposSemaforicos: [
                {
                  id: 'G3',
                  tipo: 'PEDESTRE',
                  posicao: 3,
                  tabelaEntreVerdes: [
                    {
                      id: 'tabela-entre-verdes-3',
                      posicao: 1
                    }
                  ],
                  transicoes: [
                    {
                      id: 'transicao-4',
                      origem: {id: 'E2'},
                      destino: {id: 'E3'},
                      tabelaEntreVerdesTransicoes: [
                        {
                          tabelaEntreVerdes: {
                            id: 'tabela-entre-verdes-3'
                          },
                          tempoVermelhoIntermitente: 10,
                          tempoVermelhoLimpeza: 10
                        }
                      ]
                    },
                    {
                      id: 'transicao-5',
                      origem: {id: 'E1'},
                      destino: {id: 'E3'},
                      tabelaEntreVerdesTransicoes: [
                        {
                          tabelaEntreVerdes: {
                            id: 'tabela-entre-verdes-3'
                          },
                          tempoVermelhoIntermitente: 5,
                          tempoVermelhoLimpeza: 5
                        }
                      ]
                    },
                  ]
                },
                {
                  id: 'G5',
                  tipo: 'PEDESTRE',
                  posicao: 5,
                  tabelaEntreVerdes: [
                    {
                      id: 'tabela-entre-verdes-2',
                      posicao: 1
                    }
                  ],
                  transicoes: [
                    {
                      id: 'transicao-3',
                      origem: {id: 'E1'},
                      destino: {id: 'E2'},
                      tabelaEntreVerdesTransicoes: [
                        {
                          tabelaEntreVerdes: {
                            id: 'tabela-entre-verdes-2'
                          },
                          tempoVermelhoIntermitente: 10,
                          tempoVermelhoLimpeza: 10
                        }
                      ]
                    },
                    {
                      id: 'transicao-13',
                      origem: {id: 'E2'},
                      destino: {id: 'E3'},
                      tabelaEntreVerdesTransicoes: [
                        {
                          tabelaEntreVerdes: {
                            id: 'tabela-entre-verdes-2'
                          },
                          tempoVermelhoIntermitente: 10,
                          tempoVermelhoLimpeza: 10
                        }
                      ]
                    }
                  ]
                },
                {
                  id: 'G4',
                  tipo: 'PEDESTRE',
                  posicao: 4,
                  tabelaEntreVerdes: [
                    {
                      id: 'tabela-entre-verdes-5',
                      posicao: 1
                    }
                  ],
                  transicoes: [
                    {
                      id: 'transicao-6',
                      origem: {id: 'E2'},
                      destino: {id: 'E3'},
                      tabelaEntreVerdesTransicoes: [
                        {
                          tabelaEntreVerdes: {
                            id: 'tabela-entre-verdes-5'
                          },
                          tempoVermelhoIntermitente: 5,
                          tempoVermelhoLimpeza: 5
                        }
                      ]
                    },
                    {
                      id: 'transicao-7',
                      origem: {id: 'E3'},
                      destino: {id: 'E1'},
                      tabelaEntreVerdesTransicoes: [
                        {
                          tabelaEntreVerdes: {
                            id: 'tabela-entre-verdes-5'
                          },
                          tempoVermelhoIntermitente: 20,
                          tempoVermelhoLimpeza: 10
                        }
                      ]
                    },
                  ]
                }
              ]
            },
            {
              id: 'E3',
              tempoVerde: 10,
              tempoVerdeMinimo: null,
              tempoVerdeMaximo: null,
              tempoVerdeIntermediario: null,
              extensaoVerde: null,
              gruposSemaforicos: [
                {
                  id: 'G2',
                  tipo: 'VEICULAR',
                  posicao: 2,
                  tabelaEntreVerdes: [
                    {
                      id: 'tabela-entre-verdes-6',
                      posicao: 1
                    }
                  ],
                  transicoes: [
                    {
                      id: 'transicao-8',
                      origem: {id: 'E3'},
                      destino: {id: 'E1'},
                      tabelaEntreVerdesTransicoes: [
                        {
                          tabelaEntreVerdes: {
                            id: 'tabela-entre-verdes-6'
                          },
                          tempoAmarelo: 10,
                          tempoVermelhoLimpeza: 10,
                          tempoAtrasoGrupo: 10
                        }
                      ]
                    }
                  ]
                },
                {
                  id: 'G4',
                  tipo: 'PEDESTRE',
                  posicao: 4,
                  tabelaEntreVerdes: [
                    {
                      id: 'tabela-entre-verdes-5',
                      posicao: 1
                    }
                  ],
                  transicoes: [
                    {
                      id: 'transicao-6',
                      origem: {id: 'E2'},
                      destino: {id: 'E3'},
                      tabelaEntreVerdesTransicoes: [
                        {
                          tabelaEntreVerdes: {
                            id: 'tabela-entre-verdes-5'
                          },
                          tempoVermelhoIntermitente: 5,
                          tempoVermelhoLimpeza: 5
                        }
                      ]
                    },
                    {
                      id: 'transicao-7',
                      origem: {id: 'E3'},
                      destino: {id: 'E1'},
                      tabelaEntreVerdesTransicoes: [
                        {
                          tabelaEntreVerdes: {
                            id: 'tabela-entre-verdes-5'
                          },
                          tempoVermelhoIntermitente: 20,
                          tempoVermelhoLimpeza: 10
                        }
                      ]
                    },
                  ]

                },
              ]
            },
          ]
        };
      });

      it('Deve existir um objeto de plano', function() {
        expect(plano).toBeDefined();
      });

      it('O plano deve conter uma sequencia de estagios', function() {
        expect(plano.sequenciaEstagios).toBeDefined();
      });

      it('A sequencia de estágios deve conter 3 estagios', function() {
        expect(plano.sequenciaEstagios.length).toBe(3);
      });

      it('O E1 deve possuir 2 grupos semafóricos', function() {
        expect(plano.sequenciaEstagios[0].gruposSemaforicos.length).toBe(2);
      });

      it('O E2 deve possuir 3 grupos semafóricos', function() {
        expect(plano.sequenciaEstagios[1].gruposSemaforicos.length).toBe(3);
      });

      it('O E3 deve possuir 2 grupos semafóricos', function() {
        expect(plano.sequenciaEstagios[2].gruposSemaforicos.length).toBe(2);
      });

      it('O G1 deve ter 1 transicao', function() {
        expect(plano.sequenciaEstagios[0].gruposSemaforicos[0].transicoes.length).toBe(1);
      });

      it('O G2 deve ter 1 transicao', function() {
        expect(plano.sequenciaEstagios[2].gruposSemaforicos[0].transicoes.length).toBe(1);
      });

      it('O G3 deve ter 2 transicoes', function() {
        expect(plano.sequenciaEstagios[1].gruposSemaforicos[0].transicoes.length).toBe(2);
      });

      it('O G4 deve ter 2 transicoes', function() {
        expect(plano.sequenciaEstagios[1].gruposSemaforicos[2].transicoes.length).toBe(2);
        expect(plano.sequenciaEstagios[2].gruposSemaforicos[1].transicoes.length).toBe(2);
      });

      it('O G5 deve ter 2 transicoes', function() {
        expect(plano.sequenciaEstagios[0].gruposSemaforicos[1].transicoes.length).toBe(2);
        expect(plano.sequenciaEstagios[1].gruposSemaforicos[1].transicoes.length).toBe(2);
      });
    });

    describe('descricao da resposta', function() {
      beforeEach(function() {
        var diagramaIntervaloBuilder = new influunt.components.DiagramaIntervalos();
        resposta = diagramaIntervaloBuilder.calcula(plano);
      });

      it('Deve conter uma lista de erros vazia', function() {
        expect(resposta.erros.length).toBe(0);
      });

      it('Deve possuir uma lista de gruposSemaforicos contendo 5 grupos', function() {
        expect(resposta.gruposSemaforicos.length).toBe(5);
      });

      it('O G1 deve ter 4 intervalos', function() {
        expect(resposta.gruposSemaforicos[0].intervalos.length).toBe(4);
      });

      it('O G2 deve ter 4 intervalos', function() {
        expect(resposta.gruposSemaforicos[1].intervalos.length).toBe(4);
      });

      it('O G3 deve ter 4 intervalos', function() {
        expect(resposta.gruposSemaforicos[2].intervalos.length).toBe(4);
      });

      it('O G4 deve ter 3 intervalos', function() {
        expect(resposta.gruposSemaforicos[3].intervalos.length).toBe(3);
      });

      it('O G5 deve ter 4 intervalos', function() {
        expect(resposta.gruposSemaforicos[4].intervalos.length).toBe(4);
      });

      it('Os intervalos do G1 devem durar 30(vermelho) , 30(verde), 10(amarelo) e 50(vermelho) segundos', function() {
        expect(resposta.gruposSemaforicos[0].intervalos[0].duracao).toBe(30);
        expect(resposta.gruposSemaforicos[0].intervalos[1].duracao).toBe(30);
        expect(resposta.gruposSemaforicos[0].intervalos[2].duracao).toBe(10);
        expect(resposta.gruposSemaforicos[0].intervalos[3].duracao).toBe(50);

        expect(resposta.gruposSemaforicos[0].intervalos[0].status).toBe(3);
        expect(resposta.gruposSemaforicos[0].intervalos[1].status).toBe(1);
        expect(resposta.gruposSemaforicos[0].intervalos[2].status).toBe(2);
        expect(resposta.gruposSemaforicos[0].intervalos[3].status).toBe(3);
      });

      it('Os intervalos do G2 devem durar 10(verde) , 10(amarelo), 90(vermelho) e 10(verde) segundos', function() {
        expect(resposta.gruposSemaforicos[1].intervalos[0].duracao).toBe(10);
        expect(resposta.gruposSemaforicos[1].intervalos[1].duracao).toBe(10);
        expect(resposta.gruposSemaforicos[1].intervalos[2].duracao).toBe(90);
        expect(resposta.gruposSemaforicos[1].intervalos[3].duracao).toBe(10);

        expect(resposta.gruposSemaforicos[1].intervalos[0].status).toBe(1);
        expect(resposta.gruposSemaforicos[1].intervalos[1].status).toBe(2);
        expect(resposta.gruposSemaforicos[1].intervalos[2].status).toBe(3);
        expect(resposta.gruposSemaforicos[1].intervalos[3].status).toBe(1);
      });

      it('Os intervalos do G3 devem durar 80(vermelho) , 10(verde), 10(vermelhoIntermitente) e 20(vermelho) segundos', function() {
        expect(resposta.gruposSemaforicos[2].intervalos[0].duracao).toBe(80);
        expect(resposta.gruposSemaforicos[2].intervalos[1].duracao).toBe(10);
        expect(resposta.gruposSemaforicos[2].intervalos[2].duracao).toBe(10);
        expect(resposta.gruposSemaforicos[2].intervalos[3].duracao).toBe(20);

        expect(resposta.gruposSemaforicos[2].intervalos[0].status).toBe(3);
        expect(resposta.gruposSemaforicos[2].intervalos[1].status).toBe(1);
        expect(resposta.gruposSemaforicos[2].intervalos[2].status).toBe(4);
        expect(resposta.gruposSemaforicos[2].intervalos[3].status).toBe(3);
      });

      it('Os intervalos do G4 devem durar 20(vermelhoIntermitente) , 60(vermelho), 40(verde) segundos', function() {
        expect(resposta.gruposSemaforicos[3].intervalos[0].duracao).toBe(20);
        expect(resposta.gruposSemaforicos[3].intervalos[1].duracao).toBe(60);
        expect(resposta.gruposSemaforicos[3].intervalos[2].duracao).toBe(40);

        expect(resposta.gruposSemaforicos[3].intervalos[0].status).toBe(4);
        expect(resposta.gruposSemaforicos[3].intervalos[1].status).toBe(3);
        expect(resposta.gruposSemaforicos[3].intervalos[2].status).toBe(1);
      });

      it('Os intervalos do G5 devem durar 30(vermelho) , 60(verde), 10(vermelhoIntermitente) e 20(vermelho) segundos', function() {
        expect(resposta.gruposSemaforicos[4].intervalos[0].duracao).toBe(30);
        expect(resposta.gruposSemaforicos[4].intervalos[1].duracao).toBe(60);
        expect(resposta.gruposSemaforicos[4].intervalos[2].duracao).toBe(10);
        expect(resposta.gruposSemaforicos[4].intervalos[3].duracao).toBe(20);

        expect(resposta.gruposSemaforicos[4].intervalos[0].status).toBe(3);
        expect(resposta.gruposSemaforicos[4].intervalos[1].status).toBe(1);
        expect(resposta.gruposSemaforicos[4].intervalos[2].status).toBe(4);
        expect(resposta.gruposSemaforicos[4].intervalos[3].status).toBe(3);
      });

      it('A resposta deve ter uma lista de 3 estágios', function() {
        expect(resposta.estagios.length).toBe(3);
      });

      it('Os estágios devem ter as durações de E1: 60, E2: 30, E3: 30', function() {
        expect(resposta.estagios[0].duracao).toBe(60);
        expect(resposta.estagios[1].duracao).toBe(30);
        expect(resposta.estagios[2].duracao).toBe(30);
      });

    });
  });
})();
