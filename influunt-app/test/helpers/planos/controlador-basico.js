'use strict';

/**
 * Conjunto de funções auxiliares utilizadas pelos testes do wizard de controladores.
 *
 * @type       {<type>}
 */
var ControladorBasico = {
  obj: {
    id: '7a507327-5b15-451f-b8ce-1639df796d38',
    versoesTabelasHorarias: [],
    numeroSMEE: '123',
    numeroSMEEConjugado1: '123',
    numeroSMEEConjugado2: '123',
    numeroSMEEConjugado3: '132',
    firmware: '123',
    sequencia: 10,
    limiteEstagio: 16,
    limiteGrupoSemaforico: 16,
    limiteAnel: 4,
    limiteDetectorPedestre: 4,
    limiteDetectorVeicular: 8,
    limiteTabelasEntreVerdes: 2,
    limitePlanos: 16,
    nomeEndereco: 'R. Minas Gerais com R. Novo Horizonte',
    dataCriacao: '26/09/2016 17:14:45',
    dataAtualizacao: '26/09/2016 17:22:42',
    CLC: '1.142.0010',
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
    statusControlador: 'CONFIGURADO',
    area: {
      idJson: 'ed49da25-7c3c-11e6-ab15-0401fa4eb401'
    },
    subarea: {
      id: '3fae8987-9a53-4036-9078-e907ee7ebe9f',
      idJson: '243da367-d8a9-4b70-81b8-44195f89113d',
      nome: 'Augusta',
      numero: 142
    },
    endereco: {
      idJson: '6fa725d3-8906-42bd-ac34-98e1c4024dcb'
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
        id: '2abd1824-302a-4f16-b3eb-f9cf141dc35f',
        idJson: '429134ab-fb75-4093-8e33-b36fe7d22b53',
        ativo: false,
        posicao: 3,
        CLA: '1.142.0010.3',
        estagios: [],
        gruposSemaforicos: [],
        detectores: [],
      },
      {
        id: '5a06ea15-4962-4ce5-bc96-3c0747234cd1',
        idJson: '892a4fd4-c659-4bd1-bc65-5cf6868782d0',
        numeroSMEE: '123',
        ativo: true,
        posicao: 1,
        CLA: '1.142.0010.1',
        estagios: [
          {
            idJson: '72738e16-3258-4220-9037-677f501f3bb1'
          },
          {
            idJson: 'e683e2c4-2c93-4712-8d26-fef0be32bf16'
          }
        ],
        gruposSemaforicos: [
          {
            idJson: '9b8814da-5fc9-4ff4-867b-7dc5e16ba0b3'
          },
          {
            idJson: '9bbf5f4c-2767-4a32-bd96-4549141bfd80'
          }
        ],
        detectores: [],
        endereco: {
          idJson: 'fba1b1b3-6feb-4146-aea7-744e5d148d56'
        }
      },
      {
        id: 'c2a69118-5e69-49e8-9a7a-b1209039526e',
        idJson: '121e5bb5-c8af-462a-a46e-b916425df729',
        ativo: false,
        posicao: 4,
        CLA: '1.142.0010.4',
        estagios: [],
        gruposSemaforicos: [],
        detectores: [],
      },
      {
        id: 'd9ff9fdc-5783-462e-91a6-a1c127bb6e92',
        idJson: 'e70251f7-59ed-4fdc-b3df-0ca672c5c7d8',
        ativo: false,
        posicao: 2,
        CLA: '1.142.0010.2',
        estagios: [],
        gruposSemaforicos: [],
        detectores: [],
      }
    ],
    estagios: [
      {
        id: 'c22a8d53-11f8-4ba1-8f68-3b975318f36f',
        idJson: 'e683e2c4-2c93-4712-8d26-fef0be32bf16',
        tempoMaximoPermanencia: 60,
        tempoMaximoPermanenciaAtivado: true,
        demandaPrioritaria: false,
        posicao: 2,
        anel: {
          idJson: '892a4fd4-c659-4bd1-bc65-5cf6868782d0'
        },
        imagem: {
          idJson: 'b8440228-ffe6-4c35-bf01-237737eee418'
        },
        origemDeTransicoesProibidas: [],
        destinoDeTransicoesProibidas: [],
        alternativaDeTransicoesProibidas: [],
        estagiosGruposSemaforicos: [
          {
            idJson: '2dd8055f-4712-4772-a5fd-16cba796c2bf'
          }
        ]
      },
      {
        id: '08dd815f-0776-45aa-8545-b43a21d93485',
        idJson: '72738e16-3258-4220-9037-677f501f3bb1',
        tempoMaximoPermanencia: 60,
        tempoMaximoPermanenciaAtivado: true,
        demandaPrioritaria: false,
        posicao: 1,
        anel: {
          idJson: '892a4fd4-c659-4bd1-bc65-5cf6868782d0'
        },
        imagem: {
          idJson: '1056be86-c2e5-4b65-b3c3-f29ed467d518'
        },
        origemDeTransicoesProibidas: [],
        destinoDeTransicoesProibidas: [],
        alternativaDeTransicoesProibidas: [],
        estagiosGruposSemaforicos: [
          {
            idJson: 'e78904b9-623e-4495-9291-a641cb745568'
          }
        ]
      }
    ],
    gruposSemaforicos: [
      {
        id: '02c0cc6a-c33c-4834-81c4-7eedbab1b8d3',
        idJson: '9b8814da-5fc9-4ff4-867b-7dc5e16ba0b3',
        tipo: 'VEICULAR',
        posicao: 1,
        faseVermelhaApagadaAmareloIntermitente: true,
        tempoVerdeSeguranca: 10,
        anel: {
          idJson: '892a4fd4-c659-4bd1-bc65-5cf6868782d0'
        },
        verdesConflitantesOrigem: [
          {
            idJson: 'ecdd7c24-4422-430b-9eda-c7777ef9372b'
          }
        ],
        verdesConflitantesDestino: [],
        estagiosGruposSemaforicos: [
          {
            idJson: 'e78904b9-623e-4495-9291-a641cb745568'
          }
        ],
        transicoes: [
          {
            idJson: '3f043bde-886b-4f57-b7c5-fb6d7e0e7c8a'
          }
        ],
        transicoesComGanhoDePassagem: [
          {
            idJson: '8cb04751-3ad0-4ca9-8b4b-ba739d140da4'
          }
        ],
        tabelasEntreVerdes: [
          {
            idJson: 'b1ae3855-c310-4d6e-b6f5-9b140b4b458b'
          }
        ]
      },
      {
        id: '3e573437-c733-460f-a9c7-6d41c7783b6a',
        idJson: '9bbf5f4c-2767-4a32-bd96-4549141bfd80',
        tipo: 'VEICULAR',
        posicao: 2,
        faseVermelhaApagadaAmareloIntermitente: true,
        tempoVerdeSeguranca: 10,
        anel: {
          idJson: '892a4fd4-c659-4bd1-bc65-5cf6868782d0'
        },
        verdesConflitantesOrigem: [],
        verdesConflitantesDestino: [
          {
            idJson: 'ecdd7c24-4422-430b-9eda-c7777ef9372b'
          }
        ],
        estagiosGruposSemaforicos: [
          {
            idJson: '2dd8055f-4712-4772-a5fd-16cba796c2bf'
          }
        ],
        transicoes: [
          {
            idJson: 'ed395f5f-0a50-4301-aed5-785e91d5e1e6'
          }
        ],
        transicoesComGanhoDePassagem: [
          {
            idJson: '60863641-e3b8-42cd-8530-bdf61872a14a'
          }
        ],
        tabelasEntreVerdes: [
          {
            idJson: '0100ac0b-9f8f-4d54-91b4-ce572e300048'
          }
        ]
      }
    ],
    detectores: [],
    transicoesProibidas: [],
    estagiosGruposSemaforicos: [
      {
        id: 'a9371b25-0bfe-43aa-954b-f152e94c30b6',
        idJson: 'e78904b9-623e-4495-9291-a641cb745568',
        estagio: {
          idJson: '72738e16-3258-4220-9037-677f501f3bb1'
        },
        grupoSemaforico: {
          idJson: '9b8814da-5fc9-4ff4-867b-7dc5e16ba0b3'
        }
      },
      {
        id: '057709de-73be-431b-b759-d0f94afc98a7',
        idJson: '2dd8055f-4712-4772-a5fd-16cba796c2bf',
        estagio: {
          idJson: 'e683e2c4-2c93-4712-8d26-fef0be32bf16'
        },
        grupoSemaforico: {
          idJson: '9bbf5f4c-2767-4a32-bd96-4549141bfd80'
        }
      }
    ],
    verdesConflitantes: [
      {
        id: '4506f134-6f8f-465b-a68f-cefdde278d3f',
        idJson: 'ecdd7c24-4422-430b-9eda-c7777ef9372b',
        origem: {
          idJson: '9b8814da-5fc9-4ff4-867b-7dc5e16ba0b3'
        },
        destino: {
          idJson: '9bbf5f4c-2767-4a32-bd96-4549141bfd80'
        }
      }
    ],
    transicoes: [
      {
        id: '42774edc-920d-4429-98da-427132ab4f8b',
        idJson: 'ed395f5f-0a50-4301-aed5-785e91d5e1e6',
        origem: {
          idJson: 'e683e2c4-2c93-4712-8d26-fef0be32bf16'
        },
        destino: {
          idJson: '72738e16-3258-4220-9037-677f501f3bb1'
        },
        tabelaEntreVerdesTransicoes: [
          {
            idJson: '47082808-9bd9-41b4-90ca-d3eaa0ac92a0'
          }
        ],
        grupoSemaforico: {
          idJson: '9bbf5f4c-2767-4a32-bd96-4549141bfd80'
        },
        tipo: 'GANHO_DE_PASSAGEM',
        tempoAtrasoGrupo: '0',
        atrasoDeGrupo: {
          idJson: 'dd61cdbf-8ece-42a2-acfb-179526e00cf1'
        }
      },
      {
        id: '44baba8c-e420-4ca1-92bc-ac5fbeb9086b',
        idJson: '3f043bde-886b-4f57-b7c5-fb6d7e0e7c8a',
        origem: {
          idJson: '72738e16-3258-4220-9037-677f501f3bb1'
        },
        destino: {
          idJson: 'e683e2c4-2c93-4712-8d26-fef0be32bf16'
        },
        tabelaEntreVerdesTransicoes: [
          {
            idJson: '4b3c6ce5-d9fc-47a7-a51c-2291a2e6fd67'
          }
        ],
        grupoSemaforico: {
          idJson: '9b8814da-5fc9-4ff4-867b-7dc5e16ba0b3'
        },
        tipo: 'GANHO_DE_PASSAGEM',
        tempoAtrasoGrupo: '0',
        atrasoDeGrupo: {
          idJson: '49ddf198-7ea8-47e1-b3eb-744b7749eb68'
        }
      }
    ],
    transicoesComGanhoDePassagem: [
      {
        id: 'ac8704be-8542-458b-90fb-7e6bb288a270',
        idJson: '8cb04751-3ad0-4ca9-8b4b-ba739d140da4',
        origem: {
          idJson: 'e683e2c4-2c93-4712-8d26-fef0be32bf16'
        },
        destino: {
          idJson: '72738e16-3258-4220-9037-677f501f3bb1'
        },
        tabelaEntreVerdesTransicoes: [],
        grupoSemaforico: {
          idJson: '9b8814da-5fc9-4ff4-867b-7dc5e16ba0b3'
        },
        tipo: 'PERDA_DE_PASSAGEM',
        tempoAtrasoGrupo: '0',
        atrasoDeGrupo: {
          idJson: 'e169aa88-9e3a-44c4-bb07-8aa1a0235533'
        }
      },
      {
        id: '08e1d953-4994-4a8b-bd51-0c413f6c2392',
        idJson: '60863641-e3b8-42cd-8530-bdf61872a14a',
        origem: {
          idJson: '72738e16-3258-4220-9037-677f501f3bb1'
        },
        destino: {
          idJson: 'e683e2c4-2c93-4712-8d26-fef0be32bf16'
        },
        tabelaEntreVerdesTransicoes: [],
        grupoSemaforico: {
          idJson: '9bbf5f4c-2767-4a32-bd96-4549141bfd80'
        },
        tipo: 'PERDA_DE_PASSAGEM',
        tempoAtrasoGrupo: '0',
        atrasoDeGrupo: {
          idJson: '93b85a61-a01d-423b-9c67-fa702b7f9c2f'
        }
      }
    ],
    tabelasEntreVerdes: [
      {
        id: 'cdd8cb04-1603-4c5e-a807-9574d22a2e9f',
        idJson: '0100ac0b-9f8f-4d54-91b4-ce572e300048',
        descricao: 'PADRÃO',
        posicao: 1,
        grupoSemaforico: {
          idJson: '9bbf5f4c-2767-4a32-bd96-4549141bfd80'
        },
        tabelaEntreVerdesTransicoes: [
          {
            idJson: '47082808-9bd9-41b4-90ca-d3eaa0ac92a0'
          }
        ]
      },
      {
        id: '784727b4-2afb-49d3-8278-cefe6bf901b6',
        idJson: 'b1ae3855-c310-4d6e-b6f5-9b140b4b458b',
        descricao: 'PADRÃO',
        posicao: 1,
        grupoSemaforico: {
          idJson: '9b8814da-5fc9-4ff4-867b-7dc5e16ba0b3'
        },
        tabelaEntreVerdesTransicoes: [
          {
            idJson: '4b3c6ce5-d9fc-47a7-a51c-2291a2e6fd67'
          }
        ]
      }
    ],
    tabelasEntreVerdesTransicoes: [
      {
        id: 'cadf94d9-9798-413b-bf61-7c8a1190d4fe',
        idJson: '4b3c6ce5-d9fc-47a7-a51c-2291a2e6fd67',
        tempoAmarelo: '3',
        tempoVermelhoLimpeza: '0',
        tempoAtrasoGrupo: '0',
        tabelaEntreVerdes: {
          idJson: 'b1ae3855-c310-4d6e-b6f5-9b140b4b458b'
        },
        transicao: {
          idJson: '3f043bde-886b-4f57-b7c5-fb6d7e0e7c8a'
        }
      },
      {
        id: 'b21ad7eb-78c4-4d9e-9835-e5f9d6f2db12',
        idJson: '47082808-9bd9-41b4-90ca-d3eaa0ac92a0',
        tempoAmarelo: '3',
        tempoVermelhoLimpeza: '0',
        tempoAtrasoGrupo: '0',
        tabelaEntreVerdes: {
          idJson: '0100ac0b-9f8f-4d54-91b4-ce572e300048'
        },
        transicao: {
          idJson: 'ed395f5f-0a50-4301-aed5-785e91d5e1e6'
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
        id: '856029f6-56cb-4f3a-904b-3def84dd5137',
        idJson: 'fba1b1b3-6feb-4146-aea7-744e5d148d56',
        localizacao: 'R. Minas Gerais',
        latitude: -23.5533264195742,
        longitude: -46.66294813156128,
        localizacao2: 'R. Novo Horizonte'
      },
      {
        id: '972b9ae2-f01c-4f60-9931-d6e9c1fb804d',
        idJson: '6fa725d3-8906-42bd-ac34-98e1c4024dcb',
        localizacao: 'R. Minas Gerais',
        latitude: -23.5533264195742,
        longitude: -46.66294813156128,
        localizacao2: 'R. Novo Horizonte'
      }
    ],
    imagens: [
      {
        id: 'cbb6e9b2-1fe2-4607-ba9a-a1f881e1a426',
        idJson: 'b8440228-ffe6-4c35-bf01-237737eee418',
        fileName: '7c2c88b9-109b-4ea3-a04f-1d7313432c77',
        contentType: 'application/octet-stream'
      },
      {
        id: 'dcb4bdd6-0919-477c-9c35-4ccf2e0ca4c3',
        idJson: '1056be86-c2e5-4b65-b3c3-f29ed467d518',
        fileName: '557dde7f-11d8-4800-b7cf-f37855cb79ec',
        contentType: 'application/octet-stream'
      }
    ],
    atrasosDeGrupo: [
      {
        id: '808d59bb-2fd7-4820-8049-62805fec37f6',
        idJson: '49ddf198-7ea8-47e1-b3eb-744b7749eb68',
        atrasoDeGrupo: 0
      },
      {
        id: '8d8569af-661f-4735-b4f3-995238218eb9',
        idJson: '93b85a61-a01d-423b-9c67-fa702b7f9c2f',
        atrasoDeGrupo: 0
      },
      {
        id: '979b5b61-7af8-40db-8bb5-1952ef76b5e4',
        idJson: 'dd61cdbf-8ece-42a2-acfb-179526e00cf1',
        atrasoDeGrupo: 0
      },
      {
        id: '6fe9d9bf-009f-4dd5-b0cb-dffd112e79a3',
        idJson: 'e169aa88-9e3a-44c4-bb07-8aa1a0235533',
        atrasoDeGrupo: 0
      }
    ],
    statusVersao: 'EDITANDO',
    versaoControlador: {
      id: 'fdaf7e1e-9220-41e7-b748-43a7163ed13b',
      idJson: null,
      descricao: 'Controlador utilizado para testar se planos são criados na api',
      usuario: {
        id: 'ed4a1df0-7c3c-11e6-ab15-0401fa4eb401',
        nome: 'Administrador Geral',
        login: 'root',
        email: 'root@influunt.com.br'
      }
    },
    tabelasHorarias: [],
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
