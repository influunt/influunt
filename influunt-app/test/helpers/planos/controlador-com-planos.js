'use strict';

/**
 * Conjunto de funções auxiliares utilizadas pelos testes do wizard de controladores.
 *
 * @type       {<type>}
 */
var ControladorComPlanos = {
  obj: {
    id: '63b103d3-c0ee-453f-8da5-1bd4539b815b',
    versoesTabelasHorarias: [
      {
        id: '45b5c7a8-2cd8-47ac-bd71-08228ede44b3',
        idJson: '0ad750a9-1aca-4be5-9136-1e9152c6855a',
        statusVersao: 'ATIVO',
        tabelaHoraria: {
          idJson: '28d4e817-4b93-4011-8281-be48bca1cbf4'
        }
      }
    ],
    sequencia: 1,
    limiteEstagio: 16,
    limiteGrupoSemaforico: 16,
    limiteAnel: 4,
    limiteDetectorPedestre: 4,
    limiteDetectorVeicular: 8,
    limiteTabelasEntreVerdes: 2,
    limitePlanos: 16,
    nomeEndereco: 'Rua Itápolis',
    dataCriacao: '22/09/2016 22:44:21',
    dataAtualizacao: '26/09/2016 20:09:44',
    CLC: '3.000.0001',
    verdeMin: '1',
    verdeMax: '255',
    verdeMinimoMin: '10',
    verdeMinimoMax: '255',
    verdeMaximoMin: '10',
    verdeMaximoMax: '255',
    extensaVerdeMin: '1.0',
    extensaVerdeMax: '10.0',
    verdeIntermediarioMin: '10',
    verdeIntermediarioMax: '255',
    defasagemMin: '0',
    defasagemMax: '255',
    amareloMin: '3',
    amareloMax: '5',
    vermelhoIntermitenteMin: '3',
    vermelhoIntermitenteMax: '32',
    vermelhoLimpezaVeicularMin: '0',
    vermelhoLimpezaVeicularMax: '7',
    vermelhoLimpezaPedestreMin: '0',
    vermelhoLimpezaPedestreMax: '5',
    atrasoGrupoMin: '0',
    atrasoGrupoMax: '60',
    verdeSegurancaVeicularMin: '10',
    verdeSegurancaVeicularMax: '30',
    verdeSegurancaPedestreMin: '4',
    verdeSegurancaPedestreMax: '10',
    maximoPermanenciaEstagioMin: '60',
    maximoPermanenciaEstagioMax: '255',
    cicloMin: '30',
    cicloMax: '255',
    ausenciaDeteccaoMin: '0',
    ausenciaDeteccaoMax: '4320',
    deteccaoPermanenteMin: '0',
    deteccaoPermanenteMax: '1440',
    statusControlador: 'ATIVO',
    area: {
      idJson: '3ace69be-bf58-49b7-8cef-ed337c4669ac'
    },
    endereco: {
      idJson: '47a57c97-f958-425d-9edf-e5f88f1a2a06'
    },
    modelo: {
      id: 'ed4a113e-7c3c-11e6-ab15-0401fa4eb401',
      idJson: 'ec1bf5fa-e4fe-40b8-8ebe-b13b17303e52',
      descricao: 'Modelo Básico',
      fabricante: {
        id: 'ed49e845-7c3c-11e6-ab15-0401fa4eb401',
        nome: 'Raro Labs'
      }
    },
    aneis: [
      {
        id: '5f001d05-b51a-459a-a6c8-8c0b78af156e',
        idJson: '10d38a4f-49e2-4ab7-93fe-fcf27401bbea',
        ativo: false,
        posicao: 2,
        CLA: '3.000.0001.2',
        estagios: [],
        gruposSemaforicos: [],
        detectores: [],
        planos: []
      },
      {
        id: '77cbf85d-9ab8-4cca-9d1d-8d06df2a133f',
        idJson: '49f4f339-0008-43f1-8479-7b9618a72700',
        numeroSMEE: '-',
        ativo: true,
        posicao: 1,
        CLA: '3.000.0001.1',
        versaoPlano: {
          idJson: 'f8e14d25-6086-4fb4-86c3-0193eb165e97'
        },
        estagios: [
          {
            idJson: '26da7371-6761-4873-8bb3-5c36cbae5b3f'
          },
          {
            idJson: '20e31857-f96a-478a-b779-c2feb61d143a'
          },
          {
            idJson: '23a44791-2dbf-40e0-a7ff-d88882700944'
          }
        ],
        gruposSemaforicos: [
          {
            idJson: 'a86b3310-42d2-4e5f-a69a-8328c516c271'
          },
          {
            idJson: '0ec897d5-d38a-4432-8e26-4c61bd6778ff'
          },
          {
            idJson: '09cef9ad-bfac-42fe-9f47-c9d0d1354c03'
          },
          {
            idJson: '289b82dc-51a2-448a-9340-904472aa906c'
          },
          {
            idJson: '965023e7-b51b-4e56-8e45-78f762367c9b'
          }
        ],
        detectores: [
          {
            idJson: 'd165d7e5-62ee-4767-ac27-387a0b475c77'
          }
        ],
        planos: [
          {
            idJson: 'fafcc914-8646-440e-af8f-887f73819598'
          },
          {
            idJson: '493b769a-7376-4eee-b71b-22a4bb6050fe'
          }
        ],
        endereco: {
          idJson: '120c05e0-ab35-4367-87f6-6ad24896e224'
        }
      },
      {
        id: 'acb2d736-50d2-44ca-aebd-c86532471799',
        idJson: 'caf9ed33-af93-4030-88c1-c9e9b47c2472',
        ativo: false,
        posicao: 3,
        CLA: '3.000.0001.3',
        estagios: [],
        gruposSemaforicos: [],
        detectores: [],
        planos: []
      },
      {
        id: 'd62be576-7892-4e7f-9c15-cebffb4f9303',
        idJson: '8b4f6cbb-f5a5-4b91-883e-18e81295e973',
        ativo: false,
        posicao: 4,
        CLA: '3.000.0001.4',
        estagios: [],
        gruposSemaforicos: [],
        detectores: [],
        planos: []
      }
    ],
    estagios: [
      {
        id: '7f994d49-4686-4dea-925c-7559f560e2ac',
        idJson: '26da7371-6761-4873-8bb3-5c36cbae5b3f',
        tempoMaximoPermanencia: 60,
        tempoMaximoPermanenciaAtivado: true,
        demandaPrioritaria: false,
        posicao: 1,
        anel: {
          idJson: '49f4f339-0008-43f1-8479-7b9618a72700'
        },
        imagem: {
          idJson: '6ba03b50-dfe6-46b1-9e25-c949610d4f17'
        },
        origemDeTransicoesProibidas: [],
        destinoDeTransicoesProibidas: [
          {
            idJson: 'dabe72b5-c995-47c3-9a9b-15789d32383e'
          }
        ],
        alternativaDeTransicoesProibidas: [],
        estagiosGruposSemaforicos: [
          {
            idJson: 'a2b4f7ad-7461-408f-bc50-9738fea5bb0a'
          },
          {
            idJson: '9a8ffef7-4eaa-48fe-bed5-45fbf0336fdc'
          }
        ]
      },
      {
        id: '8a7c9a7f-9fbf-4e01-8be3-8dfb8bd25bbc',
        idJson: '20e31857-f96a-478a-b779-c2feb61d143a',
        tempoMaximoPermanencia: 60,
        tempoMaximoPermanenciaAtivado: true,
        demandaPrioritaria: false,
        posicao: 3,
        anel: {
          idJson: '49f4f339-0008-43f1-8479-7b9618a72700'
        },
        imagem: {
          idJson: '4adc1df8-a512-492a-b9c6-747d0822df84'
        },
        origemDeTransicoesProibidas: [
          {
            idJson: 'dabe72b5-c995-47c3-9a9b-15789d32383e'
          }
        ],
        destinoDeTransicoesProibidas: [],
        alternativaDeTransicoesProibidas: [],
        estagiosGruposSemaforicos: [
          {
            idJson: '03b74ebb-3e7d-4dce-864d-eab23c43d465'
          },
          {
            idJson: '2a322b9b-60a6-4abd-afa6-feced80d961b'
          }
        ]
      },
      {
        id: 'f01d8bf4-9231-4826-8669-b32037ac2310',
        idJson: '23a44791-2dbf-40e0-a7ff-d88882700944',
        tempoMaximoPermanencia: 60,
        tempoMaximoPermanenciaAtivado: true,
        demandaPrioritaria: false,
        posicao: 2,
        anel: {
          idJson: '49f4f339-0008-43f1-8479-7b9618a72700'
        },
        imagem: {
          idJson: '24e2da4e-8a0e-4c5d-aea6-c5860e77669e'
        },
        origemDeTransicoesProibidas: [],
        destinoDeTransicoesProibidas: [],
        alternativaDeTransicoesProibidas: [
          {
            idJson: 'dabe72b5-c995-47c3-9a9b-15789d32383e'
          }
        ],
        estagiosGruposSemaforicos: [
          {
            idJson: '51d723af-759e-4ba0-b337-03d25d9a1853'
          },
          {
            idJson: '3d27a597-ac40-44ea-9183-fedb32bad56b'
          },
          {
            idJson: 'd927db01-5f2d-4060-89fd-04e0e4aefba9'
          }
        ],
        detector: {
          idJson: 'd165d7e5-62ee-4767-ac27-387a0b475c77'
        }
      }
    ],
    gruposSemaforicos: [
      {
        id: 'b2e32d98-dfa1-4a22-bb64-7207235506d1',
        idJson: '289b82dc-51a2-448a-9340-904472aa906c',
        tipo: 'VEICULAR',
        posicao: 2,
        faseVermelhaApagadaAmareloIntermitente: true,
        tempoVerdeSeguranca: 10,
        anel: {
          idJson: '49f4f339-0008-43f1-8479-7b9618a72700'
        },
        verdesConflitantesOrigem: [
          {
            idJson: '57e35e98-51af-41bc-882d-29f753a25238'
          },
          {
            idJson: '19698f88-2c25-4c60-9641-67667fe180b9'
          }
        ],
        verdesConflitantesDestino: [
          {
            idJson: 'e1ef6596-b78f-4ef2-ac0a-0563119e74cc'
          }
        ],
        estagiosGruposSemaforicos: [
          {
            idJson: '03b74ebb-3e7d-4dce-864d-eab23c43d465'
          }
        ],
        transicoes: [
          {
            idJson: 'b11d057b-a680-42ff-8baa-cd36379b80dc'
          }
        ],
        transicoesComGanhoDePassagem: [
          {
            idJson: '1889843e-8ffe-45f7-8bac-b9c1a93e8f3a'
          },
          {
            idJson: 'e3529c3a-6c95-4deb-bede-2a1ccba89568'
          }
        ],
        tabelasEntreVerdes: [
          {
            idJson: '0ce731a9-bbad-440c-861f-27df0e49a050'
          }
        ]
      },
      {
        id: '5a0f757a-d577-418b-afae-4f1c6c0d88eb',
        idJson: '09cef9ad-bfac-42fe-9f47-c9d0d1354c03',
        tipo: 'PEDESTRE',
        posicao: 3,
        faseVermelhaApagadaAmareloIntermitente: false,
        tempoVerdeSeguranca: 4,
        anel: {
          idJson: '49f4f339-0008-43f1-8479-7b9618a72700'
        },
        verdesConflitantesOrigem: [],
        verdesConflitantesDestino: [
          {
            idJson: '41b1bae4-a4f8-40a7-88c2-1783bfa5da2c'
          },
          {
            idJson: '19698f88-2c25-4c60-9641-67667fe180b9'
          }
        ],
        estagiosGruposSemaforicos: [
          {
            idJson: 'd927db01-5f2d-4060-89fd-04e0e4aefba9'
          }
        ],
        transicoes: [
          {
            idJson: '76f11084-6815-4b1b-8371-3f6cbe259623'
          },
          {
            idJson: '6c14a739-9b10-4cc0-acc0-b74941e6591c'
          }
        ],
        transicoesComGanhoDePassagem: [
          {
            idJson: '35edb941-7325-48c2-b343-ceca04fc8408'
          },
          {
            idJson: '9779e807-06df-430f-a55e-938467b219d2'
          }
        ],
        tabelasEntreVerdes: [
          {
            idJson: 'ee5f4139-5b2f-42fe-af03-4bcfd9a0187c'
          }
        ]
      },
      {
        id: '2fba87af-9923-4504-9058-2fed209b1421',
        idJson: '0ec897d5-d38a-4432-8e26-4c61bd6778ff',
        tipo: 'PEDESTRE',
        posicao: 5,
        faseVermelhaApagadaAmareloIntermitente: false,
        tempoVerdeSeguranca: 4,
        anel: {
          idJson: '49f4f339-0008-43f1-8479-7b9618a72700'
        },
        verdesConflitantesOrigem: [],
        verdesConflitantesDestino: [
          {
            idJson: '57e35e98-51af-41bc-882d-29f753a25238'
          }
        ],
        estagiosGruposSemaforicos: [
          {
            idJson: '51d723af-759e-4ba0-b337-03d25d9a1853'
          },
          {
            idJson: 'a2b4f7ad-7461-408f-bc50-9738fea5bb0a'
          }
        ],
        transicoes: [
          {
            idJson: '3bc8d9ad-b414-42bd-8cad-3977f1f35c9f'
          },
          {
            idJson: 'b788dda9-e0ee-45af-9510-7590b1249367'
          },
          {
            idJson: 'eb95968f-a68f-4167-a7e0-aaa9a0f180ee'
          },
          {
            idJson: '20f82117-072f-41e6-bc08-81cf30019bf3'
          }
        ],
        transicoesComGanhoDePassagem: [
          {
            idJson: '89bb547c-f386-4763-8eea-797700607970'
          },
          {
            idJson: '7db2764b-9a2d-4bdf-a7ee-334b54373cc6'
          },
          {
            idJson: 'f9c4ce91-0af2-4ff6-8914-886763707111'
          }
        ],
        tabelasEntreVerdes: [
          {
            idJson: '19916be5-59cd-41a4-9763-c328eb70cc74'
          }
        ]
      },
      {
        id: 'cc210b18-cff6-422b-bc69-418a787126c9',
        idJson: '965023e7-b51b-4e56-8e45-78f762367c9b',
        tipo: 'PEDESTRE',
        posicao: 4,
        faseVermelhaApagadaAmareloIntermitente: false,
        tempoVerdeSeguranca: 4,
        anel: {
          idJson: '49f4f339-0008-43f1-8479-7b9618a72700'
        },
        verdesConflitantesOrigem: [],
        verdesConflitantesDestino: [
          {
            idJson: '3581dc43-767d-45eb-b580-c19072629018'
          }
        ],
        estagiosGruposSemaforicos: [
          {
            idJson: '3d27a597-ac40-44ea-9183-fedb32bad56b'
          },
          {
            idJson: '2a322b9b-60a6-4abd-afa6-feced80d961b'
          }
        ],
        transicoes: [
          {
            idJson: 'e801033a-cb3e-4635-82e2-3635df7d604a'
          },
          {
            idJson: 'cc500061-dd4f-4aa0-a4c0-f7f49d8b9e8f'
          },
          {
            idJson: '1f97cbaa-454b-4911-a17b-fb2c08f9aac0'
          }
        ],
        transicoesComGanhoDePassagem: [
          {
            idJson: '662d217d-cb23-4011-8e30-81b162f15a90'
          },
          {
            idJson: '3b79582c-a26c-4236-9e41-3fb2094fce64'
          },
          {
            idJson: 'ae54dea4-5346-4722-8486-656426e8549a'
          },
          {
            idJson: '20bac7ef-2c1d-46d7-b734-71d6fefdf274'
          }
        ],
        tabelasEntreVerdes: [
          {
            idJson: 'b125e184-882e-431b-ae0c-b4aaadace0ed'
          }
        ]
      },
      {
        id: '2b3d31df-2e3b-4e79-adc0-02a1282e3488',
        idJson: 'a86b3310-42d2-4e5f-a69a-8328c516c271',
        tipo: 'VEICULAR',
        posicao: 1,
        faseVermelhaApagadaAmareloIntermitente: true,
        tempoVerdeSeguranca: 10,
        anel: {
          idJson: '49f4f339-0008-43f1-8479-7b9618a72700'
        },
        verdesConflitantesOrigem: [
          {
            idJson: 'e1ef6596-b78f-4ef2-ac0a-0563119e74cc'
          },
          {
            idJson: '3581dc43-767d-45eb-b580-c19072629018'
          },
          {
            idJson: '41b1bae4-a4f8-40a7-88c2-1783bfa5da2c'
          }
        ],
        verdesConflitantesDestino: [],
        estagiosGruposSemaforicos: [
          {
            idJson: '9a8ffef7-4eaa-48fe-bed5-45fbf0336fdc'
          }
        ],
        transicoes: [
          {
            idJson: '2cf13d9f-556a-4ce2-a651-827c16ec1aa8'
          },
          {
            idJson: 'bb6973c1-94ea-4c6b-a396-8dc338aeff10'
          }
        ],
        transicoesComGanhoDePassagem: [
          {
            idJson: '4cd3f4be-5acc-49ff-8e8e-ce06f924ebe5'
          }
        ],
        tabelasEntreVerdes: [
          {
            idJson: '29773b83-fb3c-4253-a676-eef9f4f1cd84'
          }
        ]
      }
    ],
    detectores: [
      {
        id: '6b1b2e08-e6ea-48ad-9260-b0e88a14d7fa',
        idJson: 'd165d7e5-62ee-4767-ac27-387a0b475c77',
        tipo: 'PEDESTRE',
        posicao: 1,
        monitorado: false,
        tempoAusenciaDeteccao: 0,
        tempoDeteccaoPermanente: 0,
        anel: {
          idJson: '49f4f339-0008-43f1-8479-7b9618a72700'
        },
        estagio: {
          idJson: '23a44791-2dbf-40e0-a7ff-d88882700944'
        }
      }
    ],
    transicoesProibidas: [
      {
        id: '577e5d74-b9ce-48aa-aad3-143511b2e8e5',
        idJson: 'dabe72b5-c995-47c3-9a9b-15789d32383e',
        origem: {
          idJson: '20e31857-f96a-478a-b779-c2feb61d143a'
        },
        destino: {
          idJson: '26da7371-6761-4873-8bb3-5c36cbae5b3f'
        },
        alternativo: {
          idJson: '23a44791-2dbf-40e0-a7ff-d88882700944'
        }
      }
    ],
    estagiosGruposSemaforicos: [
      {
        id: 'ed00ba2a-71af-4b11-9e3f-8834e1b85712',
        idJson: 'd927db01-5f2d-4060-89fd-04e0e4aefba9',
        estagio: {
          idJson: '23a44791-2dbf-40e0-a7ff-d88882700944'
        },
        grupoSemaforico: {
          idJson: '09cef9ad-bfac-42fe-9f47-c9d0d1354c03'
        }
      },
      {
        id: 'db5a5d32-4e54-45bb-8342-ce1b266bb1a3',
        idJson: '2a322b9b-60a6-4abd-afa6-feced80d961b',
        estagio: {
          idJson: '20e31857-f96a-478a-b779-c2feb61d143a'
        },
        grupoSemaforico: {
          idJson: '965023e7-b51b-4e56-8e45-78f762367c9b'
        }
      },
      {
        id: 'c5852982-2dcc-4d36-b91a-3db64ca1af41',
        idJson: '9a8ffef7-4eaa-48fe-bed5-45fbf0336fdc',
        estagio: {
          idJson: '26da7371-6761-4873-8bb3-5c36cbae5b3f'
        },
        grupoSemaforico: {
          idJson: 'a86b3310-42d2-4e5f-a69a-8328c516c271'
        }
      },
      {
        id: '93b6d7d9-4a75-4f8c-bd16-c969b38b8a60',
        idJson: 'a2b4f7ad-7461-408f-bc50-9738fea5bb0a',
        estagio: {
          idJson: '26da7371-6761-4873-8bb3-5c36cbae5b3f'
        },
        grupoSemaforico: {
          idJson: '0ec897d5-d38a-4432-8e26-4c61bd6778ff'
        }
      },
      {
        id: '2daad87b-3151-449a-a67e-0ffba8dc92fe',
        idJson: '51d723af-759e-4ba0-b337-03d25d9a1853',
        estagio: {
          idJson: '23a44791-2dbf-40e0-a7ff-d88882700944'
        },
        grupoSemaforico: {
          idJson: '0ec897d5-d38a-4432-8e26-4c61bd6778ff'
        }
      },
      {
        id: 'a34344f3-b2f8-414e-aeed-dc306fcb09c1',
        idJson: '03b74ebb-3e7d-4dce-864d-eab23c43d465',
        estagio: {
          idJson: '20e31857-f96a-478a-b779-c2feb61d143a'
        },
        grupoSemaforico: {
          idJson: '289b82dc-51a2-448a-9340-904472aa906c'
        }
      },
      {
        id: '5ece57f1-4d2c-484c-8503-6453f9e0be3f',
        idJson: '3d27a597-ac40-44ea-9183-fedb32bad56b',
        estagio: {
          idJson: '23a44791-2dbf-40e0-a7ff-d88882700944'
        },
        grupoSemaforico: {
          idJson: '965023e7-b51b-4e56-8e45-78f762367c9b'
        }
      }
    ],
    verdesConflitantes: [
      {
        id: 'a755ca24-28c9-4b01-b73c-d18195eda6c4',
        idJson: '57e35e98-51af-41bc-882d-29f753a25238',
        origem: {
          idJson: '289b82dc-51a2-448a-9340-904472aa906c'
        },
        destino: {
          idJson: '0ec897d5-d38a-4432-8e26-4c61bd6778ff'
        }
      },
      {
        id: '6b5164ae-25c8-43cc-adff-02891a1431c9',
        idJson: 'e1ef6596-b78f-4ef2-ac0a-0563119e74cc',
        origem: {
          idJson: 'a86b3310-42d2-4e5f-a69a-8328c516c271'
        },
        destino: {
          idJson: '289b82dc-51a2-448a-9340-904472aa906c'
        }
      },
      {
        id: '6d44b71c-6850-4ae2-8233-ca93fe083d55',
        idJson: '3581dc43-767d-45eb-b580-c19072629018',
        origem: {
          idJson: 'a86b3310-42d2-4e5f-a69a-8328c516c271'
        },
        destino: {
          idJson: '965023e7-b51b-4e56-8e45-78f762367c9b'
        }
      },
      {
        id: '778f2ad5-2027-4fd5-ab21-e1d50c275945',
        idJson: '41b1bae4-a4f8-40a7-88c2-1783bfa5da2c',
        origem: {
          idJson: 'a86b3310-42d2-4e5f-a69a-8328c516c271'
        },
        destino: {
          idJson: '09cef9ad-bfac-42fe-9f47-c9d0d1354c03'
        }
      },
      {
        id: 'e79d6b22-b040-4aef-9a39-60ddb943857e',
        idJson: '19698f88-2c25-4c60-9641-67667fe180b9',
        origem: {
          idJson: '289b82dc-51a2-448a-9340-904472aa906c'
        },
        destino: {
          idJson: '09cef9ad-bfac-42fe-9f47-c9d0d1354c03'
        }
      }
    ],
    transicoes: [
      {
        id: '93ed443a-1ec4-4528-b4fc-5d2252df5e42',
        idJson: '76f11084-6815-4b1b-8371-3f6cbe259623',
        origem: {
          idJson: '23a44791-2dbf-40e0-a7ff-d88882700944'
        },
        destino: {
          idJson: '26da7371-6761-4873-8bb3-5c36cbae5b3f'
        },
        tabelaEntreVerdesTransicoes: [
          {
            idJson: 'ae4f4a6f-3d78-4cf1-bcae-08386b18a572'
          }
        ],
        grupoSemaforico: {
          idJson: '09cef9ad-bfac-42fe-9f47-c9d0d1354c03'
        },
        tipo: 'GANHO_DE_PASSAGEM',
        tempoAtrasoGrupo: '0',
        atrasoDeGrupo: {
          idJson: 'e777742b-c587-43a8-9956-53fbfcc3c6a4'
        }
      },
      {
        id: 'ea38ab5d-b7b6-4c3b-ae49-7a4fc8894347',
        idJson: 'cc500061-dd4f-4aa0-a4c0-f7f49d8b9e8f',
        origem: {
          idJson: '23a44791-2dbf-40e0-a7ff-d88882700944'
        },
        destino: {
          idJson: '26da7371-6761-4873-8bb3-5c36cbae5b3f'
        },
        tabelaEntreVerdesTransicoes: [
          {
            idJson: '77a12a06-0b95-4519-a5bc-18ca430901d8'
          }
        ],
        grupoSemaforico: {
          idJson: '965023e7-b51b-4e56-8e45-78f762367c9b'
        },
        tipo: 'GANHO_DE_PASSAGEM',
        tempoAtrasoGrupo: '0',
        atrasoDeGrupo: {
          idJson: '73817a44-7653-4a7e-baa2-44d05b0e0153'
        }
      },
      {
        id: 'd95b92ec-4005-4c4a-b6e8-75dd3a6ef884',
        idJson: '2cf13d9f-556a-4ce2-a651-827c16ec1aa8',
        origem: {
          idJson: '26da7371-6761-4873-8bb3-5c36cbae5b3f'
        },
        destino: {
          idJson: '20e31857-f96a-478a-b779-c2feb61d143a'
        },
        tabelaEntreVerdesTransicoes: [
          {
            idJson: '1e6d9f0d-6001-48c9-b524-559927535577'
          }
        ],
        grupoSemaforico: {
          idJson: 'a86b3310-42d2-4e5f-a69a-8328c516c271'
        },
        tipo: 'GANHO_DE_PASSAGEM',
        tempoAtrasoGrupo: '0',
        atrasoDeGrupo: {
          idJson: 'eab30f66-1f1d-4deb-93a9-0353d8171ffa'
        }
      },
      {
        id: 'eb8a4c5a-8ef1-4dff-8c16-260d40982d05',
        idJson: '1f97cbaa-454b-4911-a17b-fb2c08f9aac0',
        origem: {
          idJson: '23a44791-2dbf-40e0-a7ff-d88882700944'
        },
        destino: {
          idJson: '20e31857-f96a-478a-b779-c2feb61d143a'
        },
        tabelaEntreVerdesTransicoes: [
          {
            idJson: 'be0096f3-0bec-4603-bf23-a454ecf60bc5'
          }
        ],
        grupoSemaforico: {
          idJson: '965023e7-b51b-4e56-8e45-78f762367c9b'
        },
        tipo: 'GANHO_DE_PASSAGEM',
        tempoAtrasoGrupo: '0',
        atrasoDeGrupo: {
          idJson: 'c9dc3d3b-ab33-466f-901c-6a2e182bd2bc'
        }
      },
      {
        id: 'ffbe6920-4c1d-40b2-afbf-ae7aba5b6876',
        idJson: '20f82117-072f-41e6-bc08-81cf30019bf3',
        origem: {
          idJson: '23a44791-2dbf-40e0-a7ff-d88882700944'
        },
        destino: {
          idJson: '20e31857-f96a-478a-b779-c2feb61d143a'
        },
        tabelaEntreVerdesTransicoes: [
          {
            idJson: 'b0361e01-3017-4cc6-8e89-fbe05d5dd4db'
          }
        ],
        grupoSemaforico: {
          idJson: '0ec897d5-d38a-4432-8e26-4c61bd6778ff'
        },
        tipo: 'GANHO_DE_PASSAGEM',
        tempoAtrasoGrupo: '0',
        atrasoDeGrupo: {
          idJson: '31870514-56f1-42dd-b1de-903a0f86a22f'
        }
      },
      {
        id: '6aad9c90-d617-4aba-a72c-228b9d53636b',
        idJson: 'b11d057b-a680-42ff-8baa-cd36379b80dc',
        origem: {
          idJson: '20e31857-f96a-478a-b779-c2feb61d143a'
        },
        destino: {
          idJson: '23a44791-2dbf-40e0-a7ff-d88882700944'
        },
        tabelaEntreVerdesTransicoes: [
          {
            idJson: 'b99c400f-162a-420e-bf77-4a0da278070b'
          }
        ],
        grupoSemaforico: {
          idJson: '289b82dc-51a2-448a-9340-904472aa906c'
        },
        tipo: 'GANHO_DE_PASSAGEM',
        tempoAtrasoGrupo: '0',
        atrasoDeGrupo: {
          idJson: '7e382d0d-fc7c-488f-a93a-539406682779'
        }
      },
      {
        id: 'fe7f3fc3-cea0-45f0-ba9b-64600ac0db72',
        idJson: '6c14a739-9b10-4cc0-acc0-b74941e6591c',
        origem: {
          idJson: '23a44791-2dbf-40e0-a7ff-d88882700944'
        },
        destino: {
          idJson: '20e31857-f96a-478a-b779-c2feb61d143a'
        },
        tabelaEntreVerdesTransicoes: [
          {
            idJson: '6fe735a6-69e6-4e82-ae17-11ae75a62f60'
          }
        ],
        grupoSemaforico: {
          idJson: '09cef9ad-bfac-42fe-9f47-c9d0d1354c03'
        },
        tipo: 'GANHO_DE_PASSAGEM',
        tempoAtrasoGrupo: '0',
        atrasoDeGrupo: {
          idJson: '86990e90-ab1a-4cc4-a8d7-9bc5ca812a13'
        }
      },
      {
        id: '825576e9-9c3e-4459-8fd9-8f06d7800c6c',
        idJson: 'e801033a-cb3e-4635-82e2-3635df7d604a',
        origem: {
          idJson: '20e31857-f96a-478a-b779-c2feb61d143a'
        },
        destino: {
          idJson: '23a44791-2dbf-40e0-a7ff-d88882700944'
        },
        tabelaEntreVerdesTransicoes: [
          {
            idJson: '6c30f0f5-dad9-451b-bb08-468f6449e8a6'
          }
        ],
        grupoSemaforico: {
          idJson: '965023e7-b51b-4e56-8e45-78f762367c9b'
        },
        tipo: 'GANHO_DE_PASSAGEM',
        tempoAtrasoGrupo: '0',
        atrasoDeGrupo: {
          idJson: '7eb228d4-7553-49dd-a3e0-6807608a5dca'
        }
      },
      {
        id: '91f4ba1a-4b3b-4989-83f4-a909813ea398',
        idJson: 'b788dda9-e0ee-45af-9510-7590b1249367',
        origem: {
          idJson: '23a44791-2dbf-40e0-a7ff-d88882700944'
        },
        destino: {
          idJson: '26da7371-6761-4873-8bb3-5c36cbae5b3f'
        },
        tabelaEntreVerdesTransicoes: [
          {
            idJson: 'c28a3831-3173-49a4-88d4-dcf39141877e'
          }
        ],
        grupoSemaforico: {
          idJson: '0ec897d5-d38a-4432-8e26-4c61bd6778ff'
        },
        tipo: 'GANHO_DE_PASSAGEM',
        tempoAtrasoGrupo: '0',
        atrasoDeGrupo: {
          idJson: 'f3800af4-b98b-4f26-b151-c8edd0d55b37'
        }
      },
      {
        id: '18043c5c-02af-4cbb-aaf6-c77757eea007',
        idJson: '3bc8d9ad-b414-42bd-8cad-3977f1f35c9f',
        origem: {
          idJson: '26da7371-6761-4873-8bb3-5c36cbae5b3f'
        },
        destino: {
          idJson: '20e31857-f96a-478a-b779-c2feb61d143a'
        },
        tabelaEntreVerdesTransicoes: [
          {
            idJson: '90e5f0d3-3404-4aff-a414-4a35bcc79e65'
          }
        ],
        grupoSemaforico: {
          idJson: '0ec897d5-d38a-4432-8e26-4c61bd6778ff'
        },
        tipo: 'GANHO_DE_PASSAGEM',
        tempoAtrasoGrupo: '0',
        atrasoDeGrupo: {
          idJson: 'edf0cce5-aca3-40bd-86c7-babc77634eb7'
        }
      },
      {
        id: 'd204e800-5308-4d22-afbb-ac7b3f3f62f1',
        idJson: 'eb95968f-a68f-4167-a7e0-aaa9a0f180ee',
        origem: {
          idJson: '26da7371-6761-4873-8bb3-5c36cbae5b3f'
        },
        destino: {
          idJson: '23a44791-2dbf-40e0-a7ff-d88882700944'
        },
        tabelaEntreVerdesTransicoes: [
          {
            idJson: '1f34e499-46b5-4489-9116-b067f06e9b4f'
          }
        ],
        grupoSemaforico: {
          idJson: '0ec897d5-d38a-4432-8e26-4c61bd6778ff'
        },
        tipo: 'GANHO_DE_PASSAGEM',
        tempoAtrasoGrupo: '0',
        atrasoDeGrupo: {
          idJson: '63df8c37-a285-414c-8370-8c59538565d8'
        }
      },
      {
        id: 'e94e1111-ab80-4b87-9ad2-4697c422d63a',
        idJson: 'bb6973c1-94ea-4c6b-a396-8dc338aeff10',
        origem: {
          idJson: '26da7371-6761-4873-8bb3-5c36cbae5b3f'
        },
        destino: {
          idJson: '23a44791-2dbf-40e0-a7ff-d88882700944'
        },
        tabelaEntreVerdesTransicoes: [
          {
            idJson: '29a43185-f5fd-47fc-a64a-7397952d1dac'
          }
        ],
        grupoSemaforico: {
          idJson: 'a86b3310-42d2-4e5f-a69a-8328c516c271'
        },
        tipo: 'GANHO_DE_PASSAGEM',
        tempoAtrasoGrupo: '0',
        atrasoDeGrupo: {
          idJson: 'e2f820e5-c627-4edc-a07a-677925b54efc'
        }
      }
    ],
    transicoesComGanhoDePassagem: [
      {
        id: '1ea1975e-48ff-4f27-a16d-260969d0d5aa',
        idJson: '1889843e-8ffe-45f7-8bac-b9c1a93e8f3a',
        origem: {
          idJson: '26da7371-6761-4873-8bb3-5c36cbae5b3f'
        },
        destino: {
          idJson: '20e31857-f96a-478a-b779-c2feb61d143a'
        },
        tabelaEntreVerdesTransicoes: [],
        grupoSemaforico: {
          idJson: '289b82dc-51a2-448a-9340-904472aa906c'
        },
        tipo: 'PERDA_DE_PASSAGEM',
        tempoAtrasoGrupo: '0',
        atrasoDeGrupo: {
          idJson: '86978524-c175-4755-8c45-e29474fa5813'
        }
      },
      {
        id: '0adefa01-9be9-4cb2-81ff-8fe413a1d897',
        idJson: '89bb547c-f386-4763-8eea-797700607970',
        origem: {
          idJson: '23a44791-2dbf-40e0-a7ff-d88882700944'
        },
        destino: {
          idJson: '26da7371-6761-4873-8bb3-5c36cbae5b3f'
        },
        tabelaEntreVerdesTransicoes: [],
        grupoSemaforico: {
          idJson: '0ec897d5-d38a-4432-8e26-4c61bd6778ff'
        },
        tipo: 'PERDA_DE_PASSAGEM',
        tempoAtrasoGrupo: '0',
        atrasoDeGrupo: {
          idJson: '9be62aaf-0ea9-4824-91d7-4ab444bd2b94'
        }
      },
      {
        id: 'cd96b7d4-3dc5-446c-bccf-460b7201b5ff',
        idJson: '9779e807-06df-430f-a55e-938467b219d2',
        origem: {
          idJson: '26da7371-6761-4873-8bb3-5c36cbae5b3f'
        },
        destino: {
          idJson: '23a44791-2dbf-40e0-a7ff-d88882700944'
        },
        tabelaEntreVerdesTransicoes: [],
        grupoSemaforico: {
          idJson: '09cef9ad-bfac-42fe-9f47-c9d0d1354c03'
        },
        tipo: 'PERDA_DE_PASSAGEM',
        tempoAtrasoGrupo: '0',
        atrasoDeGrupo: {
          idJson: '541cde08-2bce-47ec-ae77-4d7bc98000c6'
        }
      },
      {
        id: '07f534f2-9dc3-4e7d-a5d8-3b26fea254e0',
        idJson: '35edb941-7325-48c2-b343-ceca04fc8408',
        origem: {
          idJson: '20e31857-f96a-478a-b779-c2feb61d143a'
        },
        destino: {
          idJson: '23a44791-2dbf-40e0-a7ff-d88882700944'
        },
        tabelaEntreVerdesTransicoes: [],
        grupoSemaforico: {
          idJson: '09cef9ad-bfac-42fe-9f47-c9d0d1354c03'
        },
        tipo: 'PERDA_DE_PASSAGEM',
        tempoAtrasoGrupo: '0',
        atrasoDeGrupo: {
          idJson: '8090cad3-f814-4de6-9c42-eb6e23f1af0a'
        }
      },
      {
        id: '65e4e90f-6590-4721-a3d4-3234601c4049',
        idJson: '3b79582c-a26c-4236-9e41-3fb2094fce64',
        origem: {
          idJson: '26da7371-6761-4873-8bb3-5c36cbae5b3f'
        },
        destino: {
          idJson: '23a44791-2dbf-40e0-a7ff-d88882700944'
        },
        tabelaEntreVerdesTransicoes: [],
        grupoSemaforico: {
          idJson: '965023e7-b51b-4e56-8e45-78f762367c9b'
        },
        tipo: 'PERDA_DE_PASSAGEM',
        tempoAtrasoGrupo: '0',
        atrasoDeGrupo: {
          idJson: '9122d327-4cd5-4bd1-8962-22fe4942329c'
        }
      },
      {
        id: 'd496affa-64af-4e10-ab2e-e60438ee36dd',
        idJson: 'f9c4ce91-0af2-4ff6-8914-886763707111',
        origem: {
          idJson: '20e31857-f96a-478a-b779-c2feb61d143a'
        },
        destino: {
          idJson: '23a44791-2dbf-40e0-a7ff-d88882700944'
        },
        tabelaEntreVerdesTransicoes: [],
        grupoSemaforico: {
          idJson: '0ec897d5-d38a-4432-8e26-4c61bd6778ff'
        },
        tipo: 'PERDA_DE_PASSAGEM',
        tempoAtrasoGrupo: '0',
        atrasoDeGrupo: {
          idJson: 'f1642d9b-de44-43ef-b687-5ba3e3a58c0d'
        }
      },
      {
        id: '42ed801d-23ee-4a0d-b16f-be4d8ac3adf4',
        idJson: '4cd3f4be-5acc-49ff-8e8e-ce06f924ebe5',
        origem: {
          idJson: '23a44791-2dbf-40e0-a7ff-d88882700944'
        },
        destino: {
          idJson: '26da7371-6761-4873-8bb3-5c36cbae5b3f'
        },
        tabelaEntreVerdesTransicoes: [],
        grupoSemaforico: {
          idJson: 'a86b3310-42d2-4e5f-a69a-8328c516c271'
        },
        tipo: 'PERDA_DE_PASSAGEM',
        tempoAtrasoGrupo: '0',
        atrasoDeGrupo: {
          idJson: 'e2aff9a3-05e9-4c32-baa5-1393ae3f2e29'
        }
      },
      {
        id: '27fb79c5-c8c8-4b8c-99a6-2285b43a9de5',
        idJson: '662d217d-cb23-4011-8e30-81b162f15a90',
        origem: {
          idJson: '23a44791-2dbf-40e0-a7ff-d88882700944'
        },
        destino: {
          idJson: '20e31857-f96a-478a-b779-c2feb61d143a'
        },
        tabelaEntreVerdesTransicoes: [],
        grupoSemaforico: {
          idJson: '965023e7-b51b-4e56-8e45-78f762367c9b'
        },
        tipo: 'PERDA_DE_PASSAGEM',
        tempoAtrasoGrupo: '0',
        atrasoDeGrupo: {
          idJson: 'e7d0aa5c-f0f8-4d64-af53-fe041d5e1341'
        }
      },
      {
        id: 'f8e0d216-8429-4bbb-85a7-27ae9b7c29a8',
        idJson: 'e3529c3a-6c95-4deb-bede-2a1ccba89568',
        origem: {
          idJson: '23a44791-2dbf-40e0-a7ff-d88882700944'
        },
        destino: {
          idJson: '20e31857-f96a-478a-b779-c2feb61d143a'
        },
        tabelaEntreVerdesTransicoes: [],
        grupoSemaforico: {
          idJson: '289b82dc-51a2-448a-9340-904472aa906c'
        },
        tipo: 'PERDA_DE_PASSAGEM',
        tempoAtrasoGrupo: '0',
        atrasoDeGrupo: {
          idJson: '4bb542ae-01b2-4240-aeb7-89b9d1bb5b6a'
        }
      },
      {
        id: '790ab0be-9d4f-4fea-8bf1-0579f7da3a79',
        idJson: '7db2764b-9a2d-4bdf-a7ee-334b54373cc6',
        origem: {
          idJson: '26da7371-6761-4873-8bb3-5c36cbae5b3f'
        },
        destino: {
          idJson: '23a44791-2dbf-40e0-a7ff-d88882700944'
        },
        tabelaEntreVerdesTransicoes: [],
        grupoSemaforico: {
          idJson: '0ec897d5-d38a-4432-8e26-4c61bd6778ff'
        },
        tipo: 'PERDA_DE_PASSAGEM',
        tempoAtrasoGrupo: '0',
        atrasoDeGrupo: {
          idJson: 'fc4821d7-56bd-456f-aa85-aa7f45a73024'
        }
      },
      {
        id: '75664f55-dc52-49bb-8578-6e4fd2f7265b',
        idJson: 'ae54dea4-5346-4722-8486-656426e8549a',
        origem: {
          idJson: '20e31857-f96a-478a-b779-c2feb61d143a'
        },
        destino: {
          idJson: '23a44791-2dbf-40e0-a7ff-d88882700944'
        },
        tabelaEntreVerdesTransicoes: [],
        grupoSemaforico: {
          idJson: '965023e7-b51b-4e56-8e45-78f762367c9b'
        },
        tipo: 'PERDA_DE_PASSAGEM',
        tempoAtrasoGrupo: '0',
        atrasoDeGrupo: {
          idJson: '73056093-aed1-44b5-96cc-37f5f39c215c'
        }
      },
      {
        id: '98c9b29e-be20-46ee-a436-6ff2892218d7',
        idJson: '20bac7ef-2c1d-46d7-b734-71d6fefdf274',
        origem: {
          idJson: '26da7371-6761-4873-8bb3-5c36cbae5b3f'
        },
        destino: {
          idJson: '20e31857-f96a-478a-b779-c2feb61d143a'
        },
        tabelaEntreVerdesTransicoes: [],
        grupoSemaforico: {
          idJson: '965023e7-b51b-4e56-8e45-78f762367c9b'
        },
        tipo: 'PERDA_DE_PASSAGEM',
        tempoAtrasoGrupo: '0',
        atrasoDeGrupo: {
          idJson: 'b6ce11cb-29ad-4308-b36f-dc2b53a58cb8'
        }
      }
    ],
    tabelasEntreVerdes: [
      {
        id: '3ecce844-d32c-4fbc-907c-9b6912a98404',
        idJson: '19916be5-59cd-41a4-9763-c328eb70cc74',
        descricao: 'PADRÃO',
        posicao: 1,
        grupoSemaforico: {
          idJson: '0ec897d5-d38a-4432-8e26-4c61bd6778ff'
        },
        tabelaEntreVerdesTransicoes: [
          {
            idJson: 'b0361e01-3017-4cc6-8e89-fbe05d5dd4db'
          },
          {
            idJson: '1f34e499-46b5-4489-9116-b067f06e9b4f'
          },
          {
            idJson: 'c28a3831-3173-49a4-88d4-dcf39141877e'
          },
          {
            idJson: '90e5f0d3-3404-4aff-a414-4a35bcc79e65'
          }
        ]
      },
      {
        id: '63b296f1-1c25-471e-97ad-27350eb079a1',
        idJson: 'b125e184-882e-431b-ae0c-b4aaadace0ed',
        descricao: 'PADRÃO',
        posicao: 1,
        grupoSemaforico: {
          idJson: '965023e7-b51b-4e56-8e45-78f762367c9b'
        },
        tabelaEntreVerdesTransicoes: [
          {
            idJson: '77a12a06-0b95-4519-a5bc-18ca430901d8'
          },
          {
            idJson: 'be0096f3-0bec-4603-bf23-a454ecf60bc5'
          },
          {
            idJson: '6c30f0f5-dad9-451b-bb08-468f6449e8a6'
          }
        ]
      },
      {
        id: '4dad5bc1-4850-4adb-aa01-ac5b7c4144b4',
        idJson: '29773b83-fb3c-4253-a676-eef9f4f1cd84',
        descricao: 'PADRÃO',
        posicao: 1,
        grupoSemaforico: {
          idJson: 'a86b3310-42d2-4e5f-a69a-8328c516c271'
        },
        tabelaEntreVerdesTransicoes: [
          {
            idJson: '1e6d9f0d-6001-48c9-b524-559927535577'
          },
          {
            idJson: '29a43185-f5fd-47fc-a64a-7397952d1dac'
          }
        ]
      },
      {
        id: '45c0458c-716a-47a2-9bdc-2c0f8aa1ec25',
        idJson: 'ee5f4139-5b2f-42fe-af03-4bcfd9a0187c',
        descricao: 'PADRÃO',
        posicao: 1,
        grupoSemaforico: {
          idJson: '09cef9ad-bfac-42fe-9f47-c9d0d1354c03'
        },
        tabelaEntreVerdesTransicoes: [
          {
            idJson: 'ae4f4a6f-3d78-4cf1-bcae-08386b18a572'
          },
          {
            idJson: '6fe735a6-69e6-4e82-ae17-11ae75a62f60'
          }
        ]
      },
      {
        id: '2bc50c8a-2431-4109-ba80-c5f41dcc8d1d',
        idJson: '0ce731a9-bbad-440c-861f-27df0e49a050',
        descricao: 'PADRÃO',
        posicao: 1,
        grupoSemaforico: {
          idJson: '289b82dc-51a2-448a-9340-904472aa906c'
        },
        tabelaEntreVerdesTransicoes: [
          {
            idJson: 'b99c400f-162a-420e-bf77-4a0da278070b'
          }
        ]
      }
    ],
    tabelasEntreVerdesTransicoes: [
      {
        id: '27abfd99-32db-459c-b8fc-5e2f644ff3ff',
        idJson: '77a12a06-0b95-4519-a5bc-18ca430901d8',
        tempoVermelhoIntermitente: '3',
        tempoVermelhoLimpeza: '3',
        tempoAtrasoGrupo: '0',
        tabelaEntreVerdes: {
          idJson: 'b125e184-882e-431b-ae0c-b4aaadace0ed'
        },
        transicao: {
          idJson: 'cc500061-dd4f-4aa0-a4c0-f7f49d8b9e8f'
        }
      },
      {
        id: 'b01f0a29-aec5-46b2-880d-272990109a57',
        idJson: 'c28a3831-3173-49a4-88d4-dcf39141877e',
        tempoVermelhoIntermitente: '3',
        tempoVermelhoLimpeza: '3',
        tempoAtrasoGrupo: '0',
        tabelaEntreVerdes: {
          idJson: '19916be5-59cd-41a4-9763-c328eb70cc74'
        },
        transicao: {
          idJson: 'b788dda9-e0ee-45af-9510-7590b1249367'
        }
      },
      {
        id: '73fda5cb-9d3f-4d8e-b5c5-a9d95e025931',
        idJson: 'b99c400f-162a-420e-bf77-4a0da278070b',
        tempoAmarelo: '3',
        tempoVermelhoLimpeza: '3',
        tempoAtrasoGrupo: '0',
        tabelaEntreVerdes: {
          idJson: '0ce731a9-bbad-440c-861f-27df0e49a050'
        },
        transicao: {
          idJson: 'b11d057b-a680-42ff-8baa-cd36379b80dc'
        }
      },
      {
        id: '6d244e17-6739-4c81-9ce2-0c4c3dd2c612',
        idJson: 'be0096f3-0bec-4603-bf23-a454ecf60bc5',
        tempoVermelhoIntermitente: '3',
        tempoVermelhoLimpeza: '3',
        tempoAtrasoGrupo: '0',
        tabelaEntreVerdes: {
          idJson: 'b125e184-882e-431b-ae0c-b4aaadace0ed'
        },
        transicao: {
          idJson: '1f97cbaa-454b-4911-a17b-fb2c08f9aac0'
        }
      },
      {
        id: '98de4097-42f5-40da-9051-96c399b49d83',
        idJson: 'ae4f4a6f-3d78-4cf1-bcae-08386b18a572',
        tempoVermelhoIntermitente: '3',
        tempoVermelhoLimpeza: '3',
        tempoAtrasoGrupo: '0',
        tabelaEntreVerdes: {
          idJson: 'ee5f4139-5b2f-42fe-af03-4bcfd9a0187c'
        },
        transicao: {
          idJson: '76f11084-6815-4b1b-8371-3f6cbe259623'
        }
      },
      {
        id: '9da6d050-9a67-473f-8614-9a98c277e2f8',
        idJson: '6fe735a6-69e6-4e82-ae17-11ae75a62f60',
        tempoVermelhoIntermitente: '3',
        tempoVermelhoLimpeza: '3',
        tempoAtrasoGrupo: '0',
        tabelaEntreVerdes: {
          idJson: 'ee5f4139-5b2f-42fe-af03-4bcfd9a0187c'
        },
        transicao: {
          idJson: '6c14a739-9b10-4cc0-acc0-b74941e6591c'
        }
      },
      {
        id: 'e8c03eb0-3c5a-4c40-b4de-503ff38a4a31',
        idJson: '90e5f0d3-3404-4aff-a414-4a35bcc79e65',
        tempoVermelhoIntermitente: '3',
        tempoVermelhoLimpeza: '3',
        tempoAtrasoGrupo: '0',
        tabelaEntreVerdes: {
          idJson: '19916be5-59cd-41a4-9763-c328eb70cc74'
        },
        transicao: {
          idJson: '3bc8d9ad-b414-42bd-8cad-3977f1f35c9f'
        }
      },
      {
        id: 'b2199deb-ed16-4b09-b5e5-44e4da58cb14',
        idJson: '1e6d9f0d-6001-48c9-b524-559927535577',
        tempoAmarelo: '3',
        tempoVermelhoLimpeza: '3',
        tempoAtrasoGrupo: '0',
        tabelaEntreVerdes: {
          idJson: '29773b83-fb3c-4253-a676-eef9f4f1cd84'
        },
        transicao: {
          idJson: '2cf13d9f-556a-4ce2-a651-827c16ec1aa8'
        }
      },
      {
        id: '572bda9d-f7a1-4c9b-a826-8d79b5975949',
        idJson: '1f34e499-46b5-4489-9116-b067f06e9b4f',
        tempoVermelhoIntermitente: '3',
        tempoVermelhoLimpeza: '3',
        tempoAtrasoGrupo: '0',
        tabelaEntreVerdes: {
          idJson: '19916be5-59cd-41a4-9763-c328eb70cc74'
        },
        transicao: {
          idJson: 'eb95968f-a68f-4167-a7e0-aaa9a0f180ee'
        }
      },
      {
        id: '35fd45d9-4420-47e0-ad9f-122a157a8892',
        idJson: 'b0361e01-3017-4cc6-8e89-fbe05d5dd4db',
        tempoVermelhoIntermitente: '3',
        tempoVermelhoLimpeza: '3',
        tempoAtrasoGrupo: '0',
        tabelaEntreVerdes: {
          idJson: '19916be5-59cd-41a4-9763-c328eb70cc74'
        },
        transicao: {
          idJson: '20f82117-072f-41e6-bc08-81cf30019bf3'
        }
      },
      {
        id: '7f447180-7ad6-4069-8ece-327ed7d5589c',
        idJson: '6c30f0f5-dad9-451b-bb08-468f6449e8a6',
        tempoVermelhoIntermitente: '3',
        tempoVermelhoLimpeza: '3',
        tempoAtrasoGrupo: '0',
        tabelaEntreVerdes: {
          idJson: 'b125e184-882e-431b-ae0c-b4aaadace0ed'
        },
        transicao: {
          idJson: 'e801033a-cb3e-4635-82e2-3635df7d604a'
        }
      },
      {
        id: 'e429d5b5-b657-4e55-b61b-b81f81e9ac04',
        idJson: '29a43185-f5fd-47fc-a64a-7397952d1dac',
        tempoAmarelo: '3',
        tempoVermelhoLimpeza: '3',
        tempoAtrasoGrupo: '0',
        tabelaEntreVerdes: {
          idJson: '29773b83-fb3c-4253-a676-eef9f4f1cd84'
        },
        transicao: {
          idJson: 'bb6973c1-94ea-4c6b-a396-8dc338aeff10'
        }
      }
    ],
    planos: [
      {
        id: '2566ca02-1569-4f7f-9278-f93da6b4f8b4',
        idJson: 'fafcc914-8646-440e-af8f-887f73819598',
        posicao: 7,
        descricao: 'PLANO 7',
        tempoCiclo: 60,
        defasagem: 0,
        posicaoTabelaEntreVerde: 1,
        modoOperacao: 'TEMPO_FIXO_ISOLADO',
        dataCriacao: '22/09/2016 22:49:32',
        dataAtualizacao: '22/09/2016 22:51:24',
        anel: {
          idJson: '49f4f339-0008-43f1-8479-7b9618a72700'
        },
        versaoPlano: {
          idJson: 'f8e14d25-6086-4fb4-86c3-0193eb165e97'
        },
        estagiosPlanos: [
          {
            idJson: '13510e46-2b24-4117-86af-cc75699340b0'
          },
          {
            idJson: 'c99454fc-8773-4fc7-b6b6-d32da445af29'
          },
          {
            idJson: '405f4115-6604-44d4-a333-69c42472eaae'
          }
        ],
        gruposSemaforicosPlanos: [
          {
            idJson: 'bbef3949-307d-47ec-baf5-39499ac15103'
          },
          {
            idJson: '1e73b1aa-452d-4c89-99af-b5b2b2b21a13'
          },
          {
            idJson: 'b1233c73-fbf1-4e22-a8e2-5a6e26df76eb'
          },
          {
            idJson: 'b097a963-a9da-4230-ab16-34d2a70b0d82'
          },
          {
            idJson: '3f11c132-0db1-43a7-84a6-fc78f394a60b'
          }
        ]
      },
      {
        id: 'ddcfa70a-1bae-4441-8be3-ccd526fc9d38',
        idJson: '493b769a-7376-4eee-b71b-22a4bb6050fe',
        posicao: 1,
        descricao: 'PLANO 1',
        tempoCiclo: 60,
        defasagem: 0,
        posicaoTabelaEntreVerde: 1,
        modoOperacao: 'TEMPO_FIXO_ISOLADO',
        dataCriacao: '22/09/2016 22:49:31',
        dataAtualizacao: '22/09/2016 22:51:24',
        anel: {
          idJson: '49f4f339-0008-43f1-8479-7b9618a72700'
        },
        versaoPlano: {
          idJson: 'f8e14d25-6086-4fb4-86c3-0193eb165e97'
        },
        estagiosPlanos: [
          {
            idJson: '450c48a7-0ba5-4c63-b109-2d5a171b4554'
          },
          {
            idJson: 'fe27d1b0-29ca-4ebe-80a6-b7fa35306a23'
          },
          {
            idJson: 'b8633697-c4dc-4f3a-b9c6-ae661be4f7bb'
          }
        ],
        gruposSemaforicosPlanos: [
          {
            idJson: '56e46a93-9218-4243-abce-81c7abb52f8f'
          },
          {
            idJson: '6cb58627-c8c2-4f74-b1fb-8fc107884424'
          },
          {
            idJson: '68b08e14-b7b1-4352-8721-0622dbd3693b'
          },
          {
            idJson: 'a586356f-accd-4e1d-865c-15239fb209e0'
          },
          {
            idJson: 'abaf89ea-2084-4f75-8d68-17948d5bb9bc'
          }
        ]
      }
    ],
    gruposSemaforicosPlanos: [
      {
        id: '7e4f3907-e301-46ce-b2a0-d6053e50d981',
        idJson: '68b08e14-b7b1-4352-8721-0622dbd3693b',
        plano: {
          idJson: '493b769a-7376-4eee-b71b-22a4bb6050fe'
        },
        grupoSemaforico: {
          idJson: '0ec897d5-d38a-4432-8e26-4c61bd6778ff'
        },
        ativado: true
      },
      {
        id: 'd71c927a-7c9d-4567-bbf0-6f2851e48482',
        idJson: 'abaf89ea-2084-4f75-8d68-17948d5bb9bc',
        plano: {
          idJson: '493b769a-7376-4eee-b71b-22a4bb6050fe'
        },
        grupoSemaforico: {
          idJson: '09cef9ad-bfac-42fe-9f47-c9d0d1354c03'
        },
        ativado: true
      },
      {
        id: '6e9327e7-c04f-4f29-b54b-144951968e4b',
        idJson: 'bbef3949-307d-47ec-baf5-39499ac15103',
        plano: {
          idJson: 'fafcc914-8646-440e-af8f-887f73819598'
        },
        grupoSemaforico: {
          idJson: '0ec897d5-d38a-4432-8e26-4c61bd6778ff'
        },
        ativado: true
      },
      {
        id: '9e51dd71-d3f9-438c-b6d1-0b38d08afcda',
        idJson: 'b097a963-a9da-4230-ab16-34d2a70b0d82',
        plano: {
          idJson: 'fafcc914-8646-440e-af8f-887f73819598'
        },
        grupoSemaforico: {
          idJson: '289b82dc-51a2-448a-9340-904472aa906c'
        },
        ativado: true
      },
      {
        id: '853c2de1-4cb9-49da-8e45-ce15379fecee',
        idJson: 'a586356f-accd-4e1d-865c-15239fb209e0',
        plano: {
          idJson: '493b769a-7376-4eee-b71b-22a4bb6050fe'
        },
        grupoSemaforico: {
          idJson: '965023e7-b51b-4e56-8e45-78f762367c9b'
        },
        ativado: true
      },
      {
        id: '24db52db-8b89-4d29-ab66-a23ecf6d541c',
        idJson: '56e46a93-9218-4243-abce-81c7abb52f8f',
        plano: {
          idJson: '493b769a-7376-4eee-b71b-22a4bb6050fe'
        },
        grupoSemaforico: {
          idJson: 'a86b3310-42d2-4e5f-a69a-8328c516c271'
        },
        ativado: true
      },
      {
        id: '3607263f-5585-47a6-b80c-ffc97f71209b',
        idJson: '6cb58627-c8c2-4f74-b1fb-8fc107884424',
        plano: {
          idJson: '493b769a-7376-4eee-b71b-22a4bb6050fe'
        },
        grupoSemaforico: {
          idJson: '289b82dc-51a2-448a-9340-904472aa906c'
        },
        ativado: true
      },
      {
        id: 'e2a8f045-4dc1-4ed8-8555-adc5f43d3f1c',
        idJson: '3f11c132-0db1-43a7-84a6-fc78f394a60b',
        plano: {
          idJson: 'fafcc914-8646-440e-af8f-887f73819598'
        },
        grupoSemaforico: {
          idJson: '965023e7-b51b-4e56-8e45-78f762367c9b'
        },
        ativado: true
      },
      {
        id: '93f6d244-0473-428e-a4c1-f618d5aedfa5',
        idJson: '1e73b1aa-452d-4c89-99af-b5b2b2b21a13',
        plano: {
          idJson: 'fafcc914-8646-440e-af8f-887f73819598'
        },
        grupoSemaforico: {
          idJson: '09cef9ad-bfac-42fe-9f47-c9d0d1354c03'
        },
        ativado: true
      },
      {
        id: '9427889c-7ff6-4c54-ae5b-6c51aa78ba22',
        idJson: 'b1233c73-fbf1-4e22-a8e2-5a6e26df76eb',
        plano: {
          idJson: 'fafcc914-8646-440e-af8f-887f73819598'
        },
        grupoSemaforico: {
          idJson: 'a86b3310-42d2-4e5f-a69a-8328c516c271'
        },
        ativado: true
      }
    ],
    estagiosPlanos: [
      {
        id: '996e00d1-a67f-4ccf-845d-7a19ed35617b',
        idJson: 'fe27d1b0-29ca-4ebe-80a6-b7fa35306a23',
        posicao: 1,
        tempoVerde: 11,
        dispensavel: false,
        plano: {
          idJson: '493b769a-7376-4eee-b71b-22a4bb6050fe'
        },
        estagio: {
          idJson: '26da7371-6761-4873-8bb3-5c36cbae5b3f'
        }
      },
      {
        id: 'bdac979c-367a-4550-90e1-3d2e2bebec7c',
        idJson: 'b8633697-c4dc-4f3a-b9c6-ae661be4f7bb',
        posicao: 3,
        tempoVerde: 11,
        dispensavel: false,
        plano: {
          idJson: '493b769a-7376-4eee-b71b-22a4bb6050fe'
        },
        estagio: {
          idJson: '23a44791-2dbf-40e0-a7ff-d88882700944'
        }
      },
      {
        id: '91b3cad0-8a56-4a75-9b1b-4cbc6785736c',
        idJson: '13510e46-2b24-4117-86af-cc75699340b0',
        posicao: 2,
        tempoVerde: 20,
        dispensavel: false,
        plano: {
          idJson: 'fafcc914-8646-440e-af8f-887f73819598'
        },
        estagio: {
          idJson: '20e31857-f96a-478a-b779-c2feb61d143a'
        }
      },
      {
        id: 'dd0fff34-a9ca-4765-8489-9e8af25e8b89',
        idJson: 'c99454fc-8773-4fc7-b6b6-d32da445af29',
        posicao: 3,
        tempoVerde: 11,
        dispensavel: false,
        plano: {
          idJson: 'fafcc914-8646-440e-af8f-887f73819598'
        },
        estagio: {
          idJson: '23a44791-2dbf-40e0-a7ff-d88882700944'
        }
      },
      {
        id: '77ce87bb-dc98-4163-b9e5-6f97a98932dd',
        idJson: '450c48a7-0ba5-4c63-b109-2d5a171b4554',
        posicao: 2,
        tempoVerde: 20,
        dispensavel: false,
        plano: {
          idJson: '493b769a-7376-4eee-b71b-22a4bb6050fe'
        },
        estagio: {
          idJson: '20e31857-f96a-478a-b779-c2feb61d143a'
        }
      },
      {
        id: 'f05c4579-6062-4845-a233-271f45de1777',
        idJson: '405f4115-6604-44d4-a333-69c42472eaae',
        posicao: 1,
        tempoVerde: 11,
        dispensavel: false,
        plano: {
          idJson: 'fafcc914-8646-440e-af8f-887f73819598'
        },
        estagio: {
          idJson: '26da7371-6761-4873-8bb3-5c36cbae5b3f'
        }
      }
    ],
    cidades: [
      {
        id: 'ed499634-7c3c-11e6-ab15-0401fa4eb401',
        idJson: 'ed49cb6d-7c3c-11e6-ab15-0401fa4eb401',
        nome: 'São Paulo',
        areas: [
          {
            idJson: '4b3028d8-4297-4da2-89b4-2c754c0e962b'
          },
          {
            idJson: '3ace69be-bf58-49b7-8cef-ed337c4669ac'
          },
          {
            idJson: 'ed49da25-7c3c-11e6-ab15-0401fa4eb401'
          }
        ]
      }
    ],
    areas: [
      {
        id: 'c7fdfc82-e27b-454e-a5db-35cc7e3bb8a7',
        idJson: '4b3028d8-4297-4da2-89b4-2c754c0e962b',
        descricao: 5,
        cidade: {
          idJson: 'ed49cb6d-7c3c-11e6-ab15-0401fa4eb401'
        },
        limites: [
          {
            idJson: '6d40d504-28b7-40d7-81dd-bc8c9f4c1444'
          },
          {
            idJson: 'f9fd9df5-7eef-414b-a8a6-2f90f9175b35'
          },
          {
            idJson: '5cd40411-805d-479a-a2ff-7258b25b2eab'
          },
          {
            idJson: 'b0947a78-da4c-45ed-af8b-c7dbbc67b938'
          },
          {
            idJson: '822bbb5e-06ad-4cc0-b235-ee8e705b565d'
          },
          {
            idJson: '6700c5af-cdf9-4212-b2a3-5ac81058602b'
          },
          {
            idJson: '58487ad9-06a1-4034-822b-996a337c7222'
          },
          {
            idJson: '96dc2133-a5aa-4a62-beda-1ca12f80214a'
          },
          {
            idJson: 'b89752e0-56f1-4db3-9ece-ea1e54d43717'
          },
          {
            idJson: '202f3acb-61b4-4ee2-a9d1-1c0939db5367'
          },
          {
            idJson: '56fb2d38-7353-4533-9e15-e1f2b130b0af'
          },
          {
            idJson: '20d30f58-3ea6-4f15-b0cb-d0eef82d7977'
          }
        ],
        subareas: [
          {
            id: '3e3be85e-d3d7-4a7a-9c51-4392d457b2f8',
            idJson: '78bd7736-4259-49d0-8ab7-0e503a17c708',
            nome: 'Consolação',
            numero: '12'
          },
          {
            id: 'f0595694-bda6-444d-8dcb-22ee3a53ba57',
            idJson: '54559dd5-df45-4e88-a18c-4cbcf4782de1',
            nome: 'Haddock Lobo',
            numero: '146'
          }
        ]
      },
      {
        id: 'e23e1607-715f-49ee-ad68-e866fb8f39e3',
        idJson: '3ace69be-bf58-49b7-8cef-ed337c4669ac',
        descricao: 3,
        cidade: {
          idJson: 'ed49cb6d-7c3c-11e6-ab15-0401fa4eb401'
        },
        limites: [
          {
            idJson: '96a665ad-c383-4d72-aa97-08664ea3b94d'
          },
          {
            idJson: '1ac9907b-b4cd-4b4c-a967-d62ce181542b'
          },
          {
            idJson: 'fbe41b18-3db5-46fc-a3e6-a1f73927236b'
          },
          {
            idJson: '0ae80934-696c-4f15-affb-7ccfe67cb65e'
          },
          {
            idJson: '6293b783-c83a-4317-9b24-168735511aca'
          },
          {
            idJson: '695da54d-cd05-4de8-a146-23a9bc7497a8'
          },
          {
            idJson: 'ae77d58a-e692-479e-abf2-94e86a858044'
          },
          {
            idJson: '1883df58-0db3-41de-92c2-8be518808816'
          },
          {
            idJson: '6c468c47-3ae6-4cdd-ba45-89178c70b414'
          },
          {
            idJson: '6037c1a7-4e1c-4005-a0f0-01de5d542c86'
          },
          {
            idJson: '77b643d2-52cb-4bdb-ad6e-4ea46753dc76'
          },
          {
            idJson: '36621840-eb45-45c1-bce4-12b439f459d8'
          },
          {
            idJson: '8d06db7a-57dd-4145-8168-a7af87cda516'
          },
          {
            idJson: 'ce0dd404-9aab-4c56-9f09-699b606db365'
          },
          {
            idJson: 'cb622dfe-a51f-44f1-9e64-3ab3ac2359c3'
          },
          {
            idJson: 'f8268a85-f3ae-448a-870c-7069ef30576a'
          },
          {
            idJson: '36fb7258-bbe4-4b06-9524-8806b001dbed'
          },
          {
            idJson: '2e54c58d-d7a5-46ff-b284-82d856abd2ff'
          },
          {
            idJson: 'f3986f28-a80d-4bcd-90f4-b216404c00c3'
          },
          {
            idJson: 'a22a34a8-f0f2-4bec-8bab-773f5cc7527d'
          },
          {
            idJson: 'f704a46a-4d21-4507-9e21-6522d2e27272'
          },
          {
            idJson: 'f114fc96-e19b-45ca-8bad-baab75fead7d'
          },
          {
            idJson: '8823ec95-e6ca-48b1-9de0-2769864d5197'
          },
          {
            idJson: 'e0997172-6bd7-44a3-b648-ee7b621905bf'
          },
          {
            idJson: '1e9bfdc6-2572-4fda-90eb-08e35f846823'
          },
          {
            idJson: '83202c39-5fb1-48b9-b40e-9c12820b0fdd'
          },
          {
            idJson: '05face2b-3dfd-41af-bed9-04809aa3cfb3'
          },
          {
            idJson: 'f86fcd5c-d234-4603-be3f-e4327875e6d7'
          },
          {
            idJson: '77904b16-20ca-42fc-92a8-565377f38760'
          },
          {
            idJson: '43611824-daa9-4d3b-ada9-e5dba3311775'
          },
          {
            idJson: '3e9f9f54-77b1-4ef6-a4f1-c93b412cb931'
          },
          {
            idJson: '37ade495-901c-40bb-ae60-e033fca5c924'
          },
          {
            idJson: '82b0006e-e730-4476-ba05-b5b86dac181d'
          },
          {
            idJson: 'f9c0df64-b0b6-406a-8d02-22ed08e0b40a'
          },
          {
            idJson: '23191d20-cd56-4d5a-8d23-36975f5d4dad'
          }
        ],
        subareas: []
      },
      {
        id: 'ed49da06-7c3c-11e6-ab15-0401fa4eb401',
        idJson: 'ed49da25-7c3c-11e6-ab15-0401fa4eb401',
        descricao: 1,
        cidade: {
          idJson: 'ed49cb6d-7c3c-11e6-ab15-0401fa4eb401'
        },
        limites: [
          {
            idJson: 'ad66f851-5de7-4fc0-8882-7ea8b0f2d6d1'
          },
          {
            idJson: 'dbf51681-0f2c-4931-8c72-bc5099123b49'
          },
          {
            idJson: '0911dc5c-8457-4792-970d-536a4f441d74'
          },
          {
            idJson: '094c8ccf-7b97-4463-adf8-4cb94db03a73'
          },
          {
            idJson: '2f14cf21-3926-4cd6-a3ab-adb19188b54b'
          },
          {
            idJson: 'e4884238-d05b-41ee-8465-de3e40b15d66'
          },
          {
            idJson: 'efb5be31-5a40-43be-8f79-49a6efaa7edc'
          },
          {
            idJson: '22d0c107-0146-4baf-bddb-0eb7e5915f4e'
          },
          {
            idJson: '5bfb10b7-0492-43c7-933d-7f5ceb195ca8'
          },
          {
            idJson: 'b87c6be7-aade-40cb-95bf-ca746a92042d'
          },
          {
            idJson: '047028ab-2726-4958-9f3e-db6c05078692'
          },
          {
            idJson: '98e18d5b-b93b-49ee-87a7-081839e23dd1'
          },
          {
            idJson: '2ade715f-40d5-420b-bebf-c35fd5e30b41'
          }
        ],
        subareas: [
          {
            id: '3fae8987-9a53-4036-9078-e907ee7ebe9f',
            idJson: '243da367-d8a9-4b70-81b8-44195f89113d',
            nome: 'Augusta',
            numero: '142'
          }
        ]
      }
    ],
    limites: [
      {
        id: 'a7fa5a43-a715-496a-8f96-5b2576a06c4f',
        idJson: '822bbb5e-06ad-4cc0-b235-ee8e705b565d',
        latitude: -23.562226794262727,
        longitude: -46.67508244514465,
        posicao: 5
      },
      {
        id: 'c62a4ffd-4260-4f3a-a97e-239fb7113091',
        idJson: '83202c39-5fb1-48b9-b40e-9c12820b0fdd',
        latitude: -23.54891042985688,
        longitude: -46.65245532989502,
        posicao: 34
      },
      {
        id: 'e8670a49-f6a1-49c2-a3a9-6ff2593dd6cf',
        idJson: '82b0006e-e730-4476-ba05-b5b86dac181d',
        latitude: -23.54974643038533,
        longitude: -46.680264472961426,
        posicao: 3
      },
      {
        id: '98279784-d941-4daa-bc0b-1d775ec93cdd',
        idJson: '36fb7258-bbe4-4b06-9524-8806b001dbed',
        latitude: -23.547799032687486,
        longitude: -46.64249897003173,
        posicao: 31
      },
      {
        id: '8fafae0e-fc56-44c1-93da-65cd68a3456c',
        idJson: 'b0947a78-da4c-45ed-af8b-c7dbbc67b938',
        latitude: -23.557683252890925,
        longitude: -46.667550802230835,
        posicao: 7
      },
      {
        id: 'a6c34942-0fc3-45fb-9701-4b023c58d73f',
        idJson: '2e54c58d-d7a5-46ff-b284-82d856abd2ff',
        latitude: -23.535858297043994,
        longitude: -46.63365840911865,
        posicao: 26
      },
      {
        id: 'b3e9e264-6c8a-4bf5-8d4c-0afef16813ec',
        idJson: '8823ec95-e6ca-48b1-9de0-2769864d5197',
        latitude: -23.526060969666243,
        longitude: -46.68541431427001,
        posicao: 16
      },
      {
        id: 'f90ee9f7-bc7e-44b1-8acf-6f06ab9254af',
        idJson: '2ade715f-40d5-420b-bebf-c35fd5e30b41',
        latitude: -23.547031868098472,
        longitude: -46.646318435668945,
        posicao: 5
      },
      {
        id: 'b0d5ccb8-3c17-48b6-a92c-e0b7ec2037f9',
        idJson: '6700c5af-cdf9-4212-b2a3-5ac81058602b',
        latitude: -23.574165134541,
        longitude: -46.66714310646057,
        posicao: 1
      },
      {
        id: 'd7178064-4044-4560-a06e-9e5afa9d0568',
        idJson: '56fb2d38-7353-4533-9e15-e1f2b130b0af',
        latitude: -23.57924892524502,
        longitude: -46.6618537902832,
        posicao: 11
      },
      {
        id: 'a98d5768-a7ba-4e77-a25e-53c177dacdd6',
        idJson: 'f3986f28-a80d-4bcd-90f4-b216404c00c3',
        latitude: -23.5472285773942,
        longitude: -46.69000625610351,
        posicao: 6
      },
      {
        id: '78ae98be-bde2-43d7-88ad-dbae6355a310',
        idJson: 'e4884238-d05b-41ee-8465-de3e40b15d66',
        latitude: -23.559974712224648,
        longitude: -46.63970947265624,
        posicao: 10
      },
      {
        id: '35462331-c008-4e60-a378-a26cfad0ec2f',
        idJson: 'f9fd9df5-7eef-414b-a8a6-2f90f9175b35',
        latitude: -23.57720363273005,
        longitude: -46.66399955749512,
        posicao: 12
      },
      {
        id: 'c507bc66-f0f2-4dcc-b174-a4bf5d6705c6',
        idJson: 'b87c6be7-aade-40cb-95bf-ca746a92042d',
        latitude: -23.554545954065333,
        longitude: -46.63691997528076,
        posicao: 8
      },
      {
        id: 'e69c0d00-9c7f-4772-8ef6-eb10526de036',
        idJson: '98e18d5b-b93b-49ee-87a7-081839e23dd1',
        latitude: -23.558165151090375,
        longitude: -46.63850784301758,
        posicao: 9
      },
      {
        id: 'b1aab137-1503-4852-8060-07afe5ccf024',
        idJson: 'f114fc96-e19b-45ca-8bad-baab75fead7d',
        latitude: -23.5554507626657,
        longitude: -46.662840843200684,
        posicao: 35
      },
      {
        id: '522a8f12-6585-4c1d-93a0-17793d622871',
        idJson: '6037c1a7-4e1c-4005-a0f0-01de5d542c86',
        latitude: -23.53515008097375,
        longitude: -46.69785976409912,
        posicao: 10
      },
      {
        id: '557117a9-87f9-42e5-8c2d-e0c0a947426c',
        idJson: '77b643d2-52cb-4bdb-ad6e-4ea46753dc76',
        latitude: -23.5339697123839,
        longitude: -46.698074340820305,
        posicao: 11
      },
      {
        id: 'cee8645a-3eb5-4c98-a52a-c76b2382e7ce',
        idJson: '43611824-daa9-4d3b-ada9-e5dba3311775',
        latitude: -23.556168704187947,
        longitude: -46.666027307510376,
        posicao: 1
      },
      {
        id: '55de9db8-5902-4c39-bf9b-e82d428d3941',
        idJson: '5cd40411-805d-479a-a2ff-7258b25b2eab',
        latitude: -23.566858684435765,
        longitude: -46.674878597259514,
        posicao: 3
      },
      {
        id: 'df06b709-6996-4c71-8811-c19682f55718',
        idJson: '37ade495-901c-40bb-ae60-e033fca5c924',
        latitude: -23.521575202858227,
        longitude: -46.66841983795166,
        posicao: 19
      },
      {
        id: 'fa3410fa-6d66-443b-b4f2-b56ca915e759',
        idJson: '23191d20-cd56-4d5a-8d23-36975f5d4dad',
        latitude: -23.54712038731797,
        longitude: -46.64571762084961,
        posicao: 32
      },
      {
        id: '686b205c-809b-4693-9d1e-e64a73542c69',
        idJson: '36621840-eb45-45c1-bce4-12b439f459d8',
        latitude: -23.551241382782205,
        longitude: -46.67773246765137,
        posicao: 2
      },
      {
        id: 'e06f64ce-1e82-4cd5-900e-0019d369afbd',
        idJson: '20d30f58-3ea6-4f15-b0cb-d0eef82d7977',
        latitude: -23.574764973087337,
        longitude: -46.65644645690918,
        posicao: 10
      },
      {
        id: '12740eb5-af4f-477e-a752-2948e4c1c934',
        idJson: '0ae80934-696c-4f15-affb-7ccfe67cb65e',
        latitude: -23.52877596477722,
        longitude: -46.63149118423461,
        posicao: 25
      },
      {
        id: '8753d9ea-ccb4-42d6-8ed2-4c917c8f74d4',
        idJson: 'cb622dfe-a51f-44f1-9e64-3ab3ac2359c3',
        latitude: -23.52680858260393,
        longitude: -46.673011779785156,
        posicao: 18
      },
      {
        id: '6f91328d-f43c-457f-8c27-9fd4b95db567',
        idJson: '8d06db7a-57dd-4145-8168-a7af87cda516',
        latitude: -23.54160257538497,
        longitude: -46.63417339324951,
        posicao: 27
      },
      {
        id: 'c250ae87-3c5a-4f55-b698-d736b1532673',
        idJson: '1e9bfdc6-2572-4fda-90eb-08e35f846823',
        latitude: -23.526139665964944,
        longitude: -46.69095039367676,
        posicao: 15
      },
      {
        id: 'cfd9786f-f6dc-46b4-a766-759b46d948dc',
        idJson: 'b89752e0-56f1-4db3-9ece-ea1e54d43717',
        latitude: -23.56954333581838,
        longitude: -46.672024726867676,
        posicao: 2
      },
      {
        id: 'c6f385b1-7f63-492e-9f50-46c4eb84b1c2',
        idJson: '05face2b-3dfd-41af-bed9-04809aa3cfb3',
        latitude: -23.54954972485692,
        longitude: -46.684298515319824,
        posicao: 4
      },
      {
        id: 'd1098b06-6680-416e-8095-956a4e84fa19',
        idJson: '3e9f9f54-77b1-4ef6-a4f1-c93b412cb931',
        latitude: -23.51834850413711,
        longitude: -46.645545959472656,
        posicao: 22
      },
      {
        id: '0d6dbda6-f690-4e72-a831-5a5018e48aac',
        idJson: 'fbe41b18-3db5-46fc-a3e6-a1f73927236b',
        latitude: -23.54713022278312,
        longitude: -46.64724111557007,
        posicao: 33
      },
      {
        id: '4f8ffc9b-8b9b-482c-9bca-fa0b6639aa72',
        idJson: '0911dc5c-8457-4792-970d-536a4f441d74',
        latitude: -23.552421596349273,
        longitude: -46.637821197509766,
        posicao: 7
      },
      {
        id: 'f6918b0d-4cd7-4447-9a2f-6affb6f71724',
        idJson: 'f9c0df64-b0b6-406a-8d02-22ed08e0b40a',
        latitude: -23.520689836082397,
        longitude: -46.66786193847656,
        posicao: 20
      },
      {
        id: 'cc720681-5b81-4556-8b5c-c1bf8f925642',
        idJson: '96dc2133-a5aa-4a62-beda-1ca12f80214a',
        latitude: -23.5554507626657,
        longitude: -46.66292667388916,
        posicao: 8
      },
      {
        id: 'b1669899-a402-4fd6-a4dc-725f2ad3bc49',
        idJson: '58487ad9-06a1-4034-822b-996a337c7222',
        latitude: -23.560368091782326,
        longitude: -46.672089099884026,
        posicao: 6
      },
      {
        id: '475f0a70-e355-409c-b43b-191547ee080d',
        idJson: '1883df58-0db3-41de-92c2-8be518808816',
        latitude: -23.54309762038146,
        longitude: -46.69507026672363,
        posicao: 7
      },
      {
        id: 'aaadfee2-c024-478f-99be-d6baaf840954',
        idJson: 'a22a34a8-f0f2-4bec-8bab-773f5cc7527d',
        latitude: -23.547503969913787,
        longitude: -46.63773536682128,
        posicao: 29
      },
      {
        id: 'aab2b084-401e-430b-8f0e-81655592ab27',
        idJson: '22d0c107-0146-4baf-bddb-0eb7e5915f4e',
        latitude: -23.5554704323487,
        longitude: -46.662883758544915,
        posicao: 1
      },
      {
        id: 'ae200b08-335f-4241-bab5-6aa3d4114a54',
        idJson: 'f704a46a-4d21-4507-9e21-6522d2e27272',
        latitude: -23.543609079241413,
        longitude: -46.63546085357665,
        posicao: 28
      },
      {
        id: 'd4889917-e826-46a5-a38d-31b7626df411',
        idJson: '202f3acb-61b4-4ee2-a9d1-1c0939db5367',
        latitude: -23.56755197878468,
        longitude: -46.64888799190521,
        posicao: 9
      },
      {
        id: '04477596-dd25-43d1-a3ac-0bbc5b470a02',
        idJson: '96a665ad-c383-4d72-aa97-08664ea3b94d',
        latitude: -23.54974643038533,
        longitude: -46.64052486419677,
        posicao: 30
      },
      {
        id: '318f7176-5a05-497b-9c1c-98aba238ba9c',
        idJson: 'ae77d58a-e692-479e-abf2-94e86a858044',
        latitude: -23.519410962449264,
        longitude: -46.630096435546875,
        posicao: 23
      },
      {
        id: '111e407a-71a5-4b84-975a-e8765cef4277',
        idJson: 'ad66f851-5de7-4fc0-8882-7ea8b0f2d6d1',
        latitude: -23.558312670594056,
        longitude: -46.660308837890625,
        posicao: 13
      },
      {
        id: 'b5ac7d6e-ffc1-4db4-aaf2-b634216251ae',
        idJson: '5bfb10b7-0492-43c7-933d-7f5ceb195ca8',
        latitude: -23.550297204299,
        longitude: -46.655073165893555,
        posicao: 3
      },
      {
        id: '82d3143e-5a9e-4a01-87b5-3c887ad655b6',
        idJson: 'ce0dd404-9aab-4c56-9f09-699b606db365',
        latitude: -23.528185753213577,
        longitude: -46.690778732299805,
        posicao: 14
      },
      {
        id: '2539a9c3-e21a-4f90-b273-4eec5faae549',
        idJson: '6d40d504-28b7-40d7-81dd-bc8c9f4c1444',
        latitude: -23.56450834019336,
        longitude: -46.67870879173279,
        posicao: 4
      },
      {
        id: '4ab9277f-2e1a-4695-92d3-faf9e84818ac',
        idJson: '6c468c47-3ae6-4cdd-ba45-89178c70b414',
        latitude: -23.515830050185553,
        longitude: -46.667025089263916,
        posicao: 21
      },
      {
        id: '13acfc0a-4ab9-4f33-9114-3440605add33',
        idJson: '6293b783-c83a-4317-9b24-168735511aca',
        latitude: -23.528185753213577,
        longitude: -46.6994047164917,
        posicao: 13
      },
      {
        id: '67db0b60-5a1a-4f4b-8638-206556af1cab',
        idJson: '2f14cf21-3926-4cd6-a3ab-adb19188b54b',
        latitude: -23.548880923858743,
        longitude: -46.65249824523926,
        posicao: 4
      },
      {
        id: 'cd8efc93-d104-4eb8-b6bb-2c149c610c5e',
        idJson: 'f86fcd5c-d234-4603-be3f-e4327875e6d7',
        latitude: -23.547740020185714,
        longitude: -46.687517166137695,
        posicao: 5
      },
      {
        id: '29ed3730-d058-4338-88bb-e88d5ac138dd',
        idJson: '695da54d-cd05-4de8-a146-23a9bc7497a8',
        latitude: -23.5299170329606,
        longitude: -46.67910575866699,
        posicao: 17
      },
      {
        id: '8e9890a2-059f-4087-b941-49b2374708be',
        idJson: 'efb5be31-5a40-43be-8f79-49a6efaa7edc',
        latitude: -23.565639264220323,
        longitude: -46.640825271606445,
        posicao: 11
      },
      {
        id: '5d30bac0-c6db-4d60-8108-08c2dc82276f',
        idJson: '094c8ccf-7b97-4463-adf8-4cb94db03a73',
        latitude: -23.57303428392735,
        longitude: -46.64142608642578,
        posicao: 12
      },
      {
        id: '897179cd-0fec-4413-a79f-195b753e4eb8',
        idJson: 'f8268a85-f3ae-448a-870c-7069ef30576a',
        latitude: -23.524211593100432,
        longitude: -46.63052558898926,
        posicao: 24
      },
      {
        id: 'ce880fce-1701-4e01-8169-e039bbf77039',
        idJson: '77904b16-20ca-42fc-92a8-565377f38760',
        latitude: -23.532435217384155,
        longitude: -46.69914722442627,
        posicao: 12
      },
      {
        id: 'c72faf17-2829-4735-b68f-afa11de60978',
        idJson: '047028ab-2726-4958-9f3e-db6c05078692',
        latitude: -23.54785804516276,
        longitude: -46.64249897003173,
        posicao: 6
      },
      {
        id: '0d22d2ac-daaf-40f0-88e0-4806ac34d948',
        idJson: '1ac9907b-b4cd-4b4c-a967-d62ce181542b',
        latitude: -23.540697671472287,
        longitude: -46.69743061065674,
        posicao: 9
      },
      {
        id: 'bfb58f30-c367-4331-b162-18e98392a656',
        idJson: 'e0997172-6bd7-44a3-b648-ee7b621905bf',
        latitude: -23.54219272675611,
        longitude: -46.69657230377197,
        posicao: 8
      },
      {
        id: '40beb2fd-3bde-4a51-8c07-5b8e1bbae283',
        idJson: 'dbf51681-0f2c-4931-8c72-bc5099123b49',
        latitude: -23.55336575957312,
        longitude: -46.660308837890625,
        posicao: 2
      }
    ],
    todosEnderecos: [
      {
        id: 'e4286fd3-8a69-4fc1-a1c4-59c67c16dfc6',
        idJson: '47a57c97-f958-425d-9edf-e5f88f1a2a06',
        localizacao: 'Rua Itápolis',
        latitude: -23.551615118224955,
        longitude: -46.66404247283935,
        localizacao2: ''
      },
      {
        id: '1e4b00c5-84c1-4bf9-a444-8030b1d7d124',
        idJson: '120c05e0-ab35-4367-87f6-6ad24896e224',
        localizacao: 'Rua Itápolis',
        latitude: -23.551615118224955,
        longitude: -46.66404247283935,
        localizacao2: ''
      }
    ],
    imagens: [
      {
        id: 'a7c5a5a0-65ef-4906-b90b-114e78a9c976',
        idJson: '6ba03b50-dfe6-46b1-9e25-c949610d4f17',
        fileName: 'Screen Shot 2016-06-19 at 13.12.51.png',
        contentType: 'image/png'
      },
      {
        id: 'ec7dd2cd-614a-467b-9513-a29442eacd8c',
        idJson: '4adc1df8-a512-492a-b9c6-747d0822df84',
        fileName: 'Screen Shot 2016-06-19 at 13.13.05.png',
        contentType: 'image/png'
      },
      {
        id: '75dd7d67-5b32-47fc-9a43-a8b1bc83bf7a',
        idJson: '24e2da4e-8a0e-4c5d-aea6-c5860e77669e',
        fileName: 'Screen Shot 2016-06-19 at 13.12.55.png',
        contentType: 'image/png'
      }
    ],
    atrasosDeGrupo: [
      {
        id: '964b20c3-d30f-4ad6-b6f5-552a51b2b375',
        idJson: 'e2aff9a3-05e9-4c32-baa5-1393ae3f2e29',
        atrasoDeGrupo: 0
      },
      {
        id: 'a15fc5a7-f37a-422c-9503-53af70f18fbb',
        idJson: '7eb228d4-7553-49dd-a3e0-6807608a5dca',
        atrasoDeGrupo: 0
      },
      {
        id: 'd0e5e744-0aef-4c45-b8da-227d7fc5e9a2',
        idJson: 'c9dc3d3b-ab33-466f-901c-6a2e182bd2bc',
        atrasoDeGrupo: 0
      },
      {
        id: '1d76b5d5-99c4-4979-9d83-459f9820dbf8',
        idJson: '86990e90-ab1a-4cc4-a8d7-9bc5ca812a13',
        atrasoDeGrupo: 0
      },
      {
        id: '063af0d1-999c-4303-99e6-0c0d6e6d3b35',
        idJson: 'f3800af4-b98b-4f26-b151-c8edd0d55b37',
        atrasoDeGrupo: 0
      },
      {
        id: 'a5a97d99-0ac3-4861-9719-bdeb5514c547',
        idJson: 'edf0cce5-aca3-40bd-86c7-babc77634eb7',
        atrasoDeGrupo: 0
      },
      {
        id: '7c746da5-7593-4400-aed8-6e2fa0c00134',
        idJson: '73056093-aed1-44b5-96cc-37f5f39c215c',
        atrasoDeGrupo: 0
      },
      {
        id: '0549dd94-c5c1-4a7c-a447-8bd5bc7735e9',
        idJson: 'eab30f66-1f1d-4deb-93a9-0353d8171ffa',
        atrasoDeGrupo: 0
      },
      {
        id: '6a37eadf-626a-44cc-8030-53b2bc1ab6b7',
        idJson: 'e2f820e5-c627-4edc-a07a-677925b54efc',
        atrasoDeGrupo: 0
      },
      {
        id: '8fa8c17f-a281-4f1e-9698-0f7d6492514e',
        idJson: '73817a44-7653-4a7e-baa2-44d05b0e0153',
        atrasoDeGrupo: 0
      },
      {
        id: '7d471d0c-a757-4235-aff2-bbf5b61ce741',
        idJson: 'e777742b-c587-43a8-9956-53fbfcc3c6a4',
        atrasoDeGrupo: 0
      },
      {
        id: '7a13cdd3-5551-4873-a23e-f4edbce138d3',
        idJson: 'f1642d9b-de44-43ef-b687-5ba3e3a58c0d',
        atrasoDeGrupo: 0
      },
      {
        id: '65db5c4a-7eb4-4985-a5be-3a2eb2294eb6',
        idJson: '9122d327-4cd5-4bd1-8962-22fe4942329c',
        atrasoDeGrupo: 0
      },
      {
        id: '31b7fe31-1b6b-4be5-947c-e45695163d89',
        idJson: '31870514-56f1-42dd-b1de-903a0f86a22f',
        atrasoDeGrupo: 0
      },
      {
        id: '0fb4003b-07fb-4cc1-acd0-2ae9a7d5689b',
        idJson: '4bb542ae-01b2-4240-aeb7-89b9d1bb5b6a',
        atrasoDeGrupo: 0
      },
      {
        id: '2af6ee8d-860e-4c99-b438-14fb7ddb73a3',
        idJson: 'b6ce11cb-29ad-4308-b36f-dc2b53a58cb8',
        atrasoDeGrupo: 0
      },
      {
        id: 'c68f507f-428e-4b5b-a44b-89d4d153d89d',
        idJson: '7e382d0d-fc7c-488f-a93a-539406682779',
        atrasoDeGrupo: 0
      },
      {
        id: '562d041a-54c7-4aef-9195-7dc465c0ee45',
        idJson: '9be62aaf-0ea9-4824-91d7-4ab444bd2b94',
        atrasoDeGrupo: 0
      },
      {
        id: 'adc6cf68-16ca-43f1-9535-bee879f2b352',
        idJson: 'e7d0aa5c-f0f8-4d64-af53-fe041d5e1341',
        atrasoDeGrupo: 0
      },
      {
        id: '72cc95c0-8a5f-43c4-8953-64efaeda849f',
        idJson: '8090cad3-f814-4de6-9c42-eb6e23f1af0a',
        atrasoDeGrupo: 0
      },
      {
        id: '6f4d6569-7b42-4d0c-9663-c09c180a894f',
        idJson: 'fc4821d7-56bd-456f-aa85-aa7f45a73024',
        atrasoDeGrupo: 0
      },
      {
        id: 'c348a1c4-6b4e-4c79-8970-e55f9b440724',
        idJson: '63df8c37-a285-414c-8370-8c59538565d8',
        atrasoDeGrupo: 0
      },
      {
        id: 'f3821ade-a4bf-41bd-b811-3a7cefed160f',
        idJson: '86978524-c175-4755-8c45-e29474fa5813',
        atrasoDeGrupo: 0
      },
      {
        id: 'd824dc1a-6f78-4e93-ab14-8c4fe2c25fbb',
        idJson: '541cde08-2bce-47ec-ae77-4d7bc98000c6',
        atrasoDeGrupo: 0
      }
    ],
    statusVersao: 'ATIVO',
    versaoControlador: {
      id: '1d393b6c-400c-4ea7-bc44-9b7af27ca9e4',
      idJson: null,
      descricao: 'Configuração Inicial',
      usuario: {
        id: 'ed4a1df0-7c3c-11e6-ab15-0401fa4eb401',
        nome: 'Administrador Geral',
        login: 'root',
        email: 'root@influunt.com.br'
      }
    },
    versoesPlanos: [
      {
        id: '1ecb422d-12d3-425d-a26b-2db81fa7ded9',
        idJson: 'f8e14d25-6086-4fb4-86c3-0193eb165e97',
        statusVersao: 'ATIVO',
        anel: {
          idJson: '49f4f339-0008-43f1-8479-7b9618a72700'
        },
        planos: [
          {
            idJson: 'fafcc914-8646-440e-af8f-887f73819598'
          },
          {
            idJson: '493b769a-7376-4eee-b71b-22a4bb6050fe'
          }
        ]
      }
    ],
    tabelasHorarias: [
      {
        id: '73883fc8-4a85-4a8b-ada0-ea0dea2cbc97',
        idJson: '28d4e817-4b93-4011-8281-be48bca1cbf4',
        versaoTabelaHoraria: {
          idJson: '0ad750a9-1aca-4be5-9136-1e9152c6855a'
        },
        eventos: []
      }
    ],
    eventos: []
  },
  getControladorId: function() {
    return this.obj.id;
  },
  getAnelAtivoId: function() {
    return _.find(this.obj.aneis, 'ativo').id;
  },
  get: function() {
    return _.cloneDeep(this.obj);
  }
};
