'use strict';

/**
 * Conjunto de funções auxiliares utilizadas pelos testes do wizard de controladores.
 *
 * @type       {<type>}
 */
var ControladorComVariosAneisEPlanos = {
  obj: {
    id: 'da10a6c1-3dbf-493a-9e19-9392e73af056',
    versoesTabelasHorarias: [
      {
        id: 'f097e4ff-c7b7-405a-a16b-55e23bcb690c',
        idJson: '7bccdc6e-9366-4cfc-808a-a51d08dc6790',
        statusVersao: 'EDITANDO',
        tabelaHorariaOrigem: {
          idJson: '1a6311e2-c203-46e8-9b61-2d6f4d5b42e6'
        },
        tabelaHoraria: {
          idJson: 'be4f4cbc-8a92-43a2-b275-1416efdbe5d7'
        }
      },
      {
        id: '1e6334dc-bddb-4b68-9e90-84766f662597',
        idJson: '82490503-fb61-4d59-90e3-8df4deb15379',
        statusVersao: 'ARQUIVADO',
        tabelaHoraria: {
          idJson: '1a6311e2-c203-46e8-9b61-2d6f4d5b42e6'
        }
      }
    ],
    numeroSMEE: '1234',
    sequencia: 2,
    limiteEstagio: 16,
    limiteGrupoSemaforico: 16,
    limiteAnel: 4,
    limiteDetectorPedestre: 4,
    limiteDetectorVeicular: 8,
    limiteTabelasEntreVerdes: 2,
    limitePlanos: 16,
    nomeEndereco: 'Av. Paulista, nº 475',
    dataCriacao: '03/11/2016 08:57:54',
    dataAtualizacao: '08/11/2016 17:57:14',
    CLC: '1.000.0002',
    bloqueado: true,
    planosBloqueado: false,
    verdeMin: 1,
    verdeMax: 255,
    verdeMinimoMin: 10,
    verdeMinimoMax: 255,
    verdeMaximoMin: 10,
    verdeMaximoMax: 255,
    extensaoVerdeMin: 1,
    extensaoVerdeMax: 10,
    verdeIntermediarioMin: 10,
    verdeIntermediarioMax: 255,
    defasagemMin: 0,
    defasagemMax: '255',
    amareloMin: 3,
    amareloMax: 5,
    vermelhoIntermitenteMin: 3,
    vermelhoIntermitenteMax: 32,
    vermelhoLimpezaVeicularMin: 0,
    vermelhoLimpezaVeicularMax: 7,
    vermelhoLimpezaPedestreMin: 0,
    vermelhoLimpezaPedestreMax: 5,
    atrasoGrupoMin: 0,
    atrasoGrupoMax: '20',
    verdeSegurancaVeicularMin: 10,
    verdeSegurancaVeicularMax: 30,
    verdeSegurancaPedestreMin: 4,
    verdeSegurancaPedestreMax: 10,
    maximoPermanenciaEstagioMin: 60,
    maximoPermanenciaEstagioMax: 255,
    defaultMaximoPermanenciaEstagioVeicular: 127,
    cicloMin: 30,
    cicloMax: 255,
    ausenciaDeteccaoMin: '0',
    ausenciaDeteccaoMax: '4320',
    deteccaoPermanenteMin: '0',
    deteccaoPermanenteMax: '1440',
    statusControlador: 'CONFIGURADO',
    statusControladorReal: 'EDITANDO',
    area: {
      idJson: '66b6a0c4-a1c4-11e6-970d-0401fa9c1b01'
    },
    endereco: {
      idJson: '37ed7d68-2474-4aa5-86b8-2b397bab6dbf'
    },
    modelo: {
      id: '66b6ba69-a1c4-11e6-970d-0401fa9c1b01',
      idJson: '66b6ba7e-a1c4-11e6-970d-0401fa9c1b01',
      descricao: 'Modelo Básico',
      fabricante: {
        id: '66b6a723-a1c4-11e6-970d-0401fa9c1b01',
        nome: 'Raro Labs'
      }
    },
    aneis: [
      {
        id: 'ada78968-be6f-4963-9157-b67ab4e5f8aa',
        idJson: '7ff51a82-b32d-4288-afc1-a6a9eb85d78a',
        numeroSMEE: '-',
        ativo: true,
        aceitaModoManual: true,
        posicao: 1,
        CLA: '1.000.0002.1',
        versaoPlano: {
          idJson: '7c31270a-3025-49d2-936d-87e08e21fcc3'
        },
        estagios: [
          {
            idJson: 'f1015990-bac7-4a65-9dbc-8f8a4472fd5f'
          },
          {
            idJson: '9ecae09c-42ec-443d-a7b9-b43ed392bee7'
          },
          {
            idJson: 'af5f7e28-15c7-4902-96e1-c8184c4ee3cd'
          }
        ],
        gruposSemaforicos: [
          {
            idJson: 'daed6666-f188-4464-b9cf-3fea37345621'
          },
          {
            idJson: 'fe8dc72c-f0f8-40c3-84d5-26e3c7ee1cfb'
          },
          {
            idJson: '81b0f4b6-0c3b-4dc5-ac9b-5e74f279840c'
          },
          {
            idJson: '75ae793c-d90c-4279-844f-53dc2ba788d2'
          },
          {
            idJson: '29a8321e-f81d-4e76-a54d-540021e4fc6d'
          }
        ],
        detectores: [
          {
            idJson: 'cf3d3cb9-c985-412e-892a-094d944a6ae0'
          }
        ],
        planos: [
          {
            idJson: 'f6c3f670-b93e-4528-91c8-86709ae600cb'
          },
          {
            idJson: 'd7ff9f27-1132-476d-a622-744038650be9'
          },
          {
            idJson: 'd9d40a04-0d63-4927-88a9-f3b247f628a0'
          },
          {
            idJson: '7d491161-bb12-4673-b8e9-324f88f629cd'
          },
          {
            idJson: '2ba66e27-b6e4-4571-b2e2-0bf77be547b1'
          },
          {
            idJson: '9c0bae10-1b9f-422c-a063-aa0c305b25d0'
          },
          {
            idJson: '233c11dc-b927-4df2-a4b8-7e2875061970'
          },
          {
            idJson: 'c8b26060-acd7-44c3-8e92-cbdb316d6deb'
          },
          {
            idJson: 'a891c685-9285-4257-bed1-16f25f044c24'
          },
          {
            idJson: '5270dfac-b24b-4ba0-9c79-0435c74301d5'
          },
          {
            idJson: '0ce21c68-24c6-4d17-81f2-88c581931326'
          },
          {
            idJson: 'bda5c85c-63d5-4fe7-8f6d-7382f8b2152c'
          },
          {
            idJson: 'd8ac0504-ac63-44a2-a6b1-efb33f491da6'
          },
          {
            idJson: '2819a411-92ab-4654-8ef2-001e10b9fb84'
          },
          {
            idJson: '14c59533-b7f5-4205-bed1-4e0e99bebd42'
          },
          {
            idJson: 'dd976fc4-da72-4dec-89c9-9aa7b15ebb42'
          },
          {
            idJson: '6f68ea04-988c-4d50-b439-3ad02905eff1'
          }
        ],
        endereco: {
          idJson: 'ff8f5f34-eec0-4b4e-9d50-be3f909bfdbf'
        },
        localizacao: 'Av. Paulista, nº 654'
      },
      {
        id: 'f978fca3-cfee-4159-b01d-2c8a2437b8ae',
        idJson: '9f7ac702-9979-48c7-8007-bd97a867a41a',
        ativo: true,
        aceitaModoManual: true,
        posicao: 2,
        CLA: '1.000.0002.2',
        versaoPlano: {
          idJson: '2f11b501-7a1c-4e66-a88a-d5ea61e3c10e'
        },
        estagios: [
          {
            idJson: 'ef29231a-c573-4b95-b9a2-c4c467cad9d8'
          },
          {
            idJson: 'e225807c-145e-49ce-9e97-1906c3386af5'
          },
          {
            idJson: '4d562187-7201-4590-b365-bf3621e7cd86'
          }
        ],
        gruposSemaforicos: [
          {
            idJson: 'e6f0363e-8d8c-4e45-936e-380b56791af7'
          },
          {
            idJson: '3b33af34-da3c-4803-80c4-b21bca918c2a'
          },
          {
            idJson: 'cd5f89f1-1051-4075-82ca-b861b0843b5e'
          },
          {
            idJson: 'ed58bdd3-f639-426e-b400-46d3d64b0a58'
          },
          {
            idJson: '5f53a090-d3e5-4b4d-bbde-db990caedaf2'
          }
        ],
        detectores: [
          {
            idJson: '5c579893-194a-4f6d-b3ad-afb44df20e10'
          }
        ],
        planos: [
          {
            idJson: 'b5283d44-2b17-4fcb-8277-336fb250a7d1'
          },
          {
            idJson: 'fbed2d5c-8df6-46d7-8b3a-03b4d9d75a9e'
          },
          {
            idJson: '68d09bed-8168-49d5-ac93-c05c4a4c6db6'
          },
          {
            idJson: '398aa468-075d-49fc-b940-36886add16a3'
          },
          {
            idJson: '0a64f4df-54fd-4097-963e-571d65e51123'
          },
          {
            idJson: 'f984986c-37f5-44bd-840a-dc94c8a0d850'
          },
          {
            idJson: '6780c087-42c3-42df-aa25-fad41b46f03b'
          },
          {
            idJson: '6781daa5-8106-4f87-b20d-027b2ee92907'
          },
          {
            idJson: '0b3062e0-3fad-4071-9a01-f01724c20a97'
          },
          {
            idJson: '8495d409-d4f1-4e43-bb06-045c2a56c0cf'
          },
          {
            idJson: 'b52d3443-2b72-4a32-8cc2-7d74e39229ff'
          },
          {
            idJson: '16d44222-79bd-4983-81d8-ee68934a2492'
          },
          {
            idJson: '998c7003-2267-426d-96d9-4de0518c8796'
          },
          {
            idJson: '2cf6d09f-e785-4ebd-af17-04e7ce5c72d3'
          },
          {
            idJson: '6ee596d6-9d6e-43d6-80c1-99b661388e1f'
          },
          {
            idJson: '8ad1eec4-992b-49a9-bf83-adc2ad1465d5'
          },
          {
            idJson: '10433f86-f1a3-4694-b2b0-990f0702a5d4'
          }
        ],
        endereco: {
          idJson: '8fdb99e1-15a1-4675-af82-dc084fd3fd0d'
        },
        localizacao: 'Av. Paulista, nº 475'
      },
      {
        id: '9b1c14c9-abdc-4370-9a6c-718a8cfb3b35',
        idJson: 'e874607d-664f-4cbe-b9e0-5a7676625452',
        ativo: false,
        aceitaModoManual: true,
        posicao: 3,
        CLA: '1.000.0002.3',
        estagios: [],
        gruposSemaforicos: [],
        detectores: [],
        planos: []
      },
      {
        id: 'deb8a0ba-4a00-4658-846d-f1a9cb1bc33e',
        idJson: '00c782bb-b24b-49ac-9b2b-6facffd02409',
        ativo: false,
        aceitaModoManual: true,
        posicao: 4,
        CLA: '1.000.0002.4',
        estagios: [],
        gruposSemaforicos: [],
        detectores: [],
        planos: []
      }
    ],
    estagios: [
      {
        id: 'eb2ef7c9-232e-45a7-930b-ece17575d3aa',
        idJson: '4d562187-7201-4590-b365-bf3621e7cd86',
        tempoMaximoPermanencia: 127,
        tempoMaximoPermanenciaAtivado: true,
        demandaPrioritaria: false,
        tempoVerdeDemandaPrioritaria: 1,
        posicao: 2,
        anel: {
          idJson: '9f7ac702-9979-48c7-8007-bd97a867a41a'
        },
        imagem: {
          idJson: 'eedb9199-f457-4ff0-8e6a-3aef34d22f12',
          id: 'ea435d86-aab2-4f1d-9428-91657cb16442'
        },
        origemDeTransicoesProibidas: [],
        destinoDeTransicoesProibidas: [],
        alternativaDeTransicoesProibidas: [],
        estagiosGruposSemaforicos: [
          {
            idJson: 'ca6430c8-f68d-4c78-8674-bb2fc5625fd5'
          },
          {
            idJson: 'f8866841-8e40-48e6-8dae-7a0a9987dfeb'
          }
        ],
        verdeMinimoEstagio: 10,
        isVeicular: true
      },
      {
        id: 'bbd89982-a019-4fd6-ad94-4c9b878ff936',
        idJson: 'af5f7e28-15c7-4902-96e1-c8184c4ee3cd',
        tempoMaximoPermanencia: 60,
        tempoMaximoPermanenciaAtivado: true,
        demandaPrioritaria: false,
        tempoVerdeDemandaPrioritaria: 1,
        posicao: 2,
        anel: {
          idJson: '7ff51a82-b32d-4288-afc1-a6a9eb85d78a'
        },
        imagem: {
          idJson: '68c76e3a-c8f9-435f-9e0e-363be038e101',
          id: 'f1d58b0d-d19c-4802-98da-ad3edf74c974'
        },
        origemDeTransicoesProibidas: [],
        destinoDeTransicoesProibidas: [],
        alternativaDeTransicoesProibidas: [],
        estagiosGruposSemaforicos: [
          {
            idJson: 'b4e97a2e-4f45-4333-8d20-8fc94e1ac51b'
          },
          {
            idJson: '683ea3ca-6b82-428f-b55f-e684d4ababcd'
          },
          {
            idJson: 'ee599b8f-b2d1-41b2-bceb-4082446182d2'
          }
        ],
        detector: {
          idJson: 'cf3d3cb9-c985-412e-892a-094d944a6ae0'
        },
        verdeMinimoEstagio: 4,
        isVeicular: false
      },
      {
        id: '23a27554-c177-471e-98d8-15270408c37d',
        idJson: 'ef29231a-c573-4b95-b9a2-c4c467cad9d8',
        tempoMaximoPermanencia: 0,
        tempoMaximoPermanenciaAtivado: false,
        demandaPrioritaria: true,
        tempoVerdeDemandaPrioritaria: 30,
        posicao: 1,
        anel: {
          idJson: '9f7ac702-9979-48c7-8007-bd97a867a41a'
        },
        imagem: {
          idJson: 'b3d646d1-4168-46c5-994b-4b0bc0702f39'
        },
        origemDeTransicoesProibidas: [],
        destinoDeTransicoesProibidas: [],
        alternativaDeTransicoesProibidas: [],
        estagiosGruposSemaforicos: [
          {
            idJson: 'e7cf2806-225a-4dd3-9590-536472e7f1d7'
          }
        ],
        detector: {
          idJson: '5c579893-194a-4f6d-b3ad-afb44df20e10'
        }
      },
      {
        id: '2dbe5c67-1aab-496d-ae27-6c6648b832c8',
        idJson: 'f1015990-bac7-4a65-9dbc-8f8a4472fd5f',
        tempoMaximoPermanencia: 127,
        tempoMaximoPermanenciaAtivado: true,
        demandaPrioritaria: false,
        tempoVerdeDemandaPrioritaria: 1,
        posicao: 1,
        anel: {
          idJson: '7ff51a82-b32d-4288-afc1-a6a9eb85d78a'
        },
        imagem: {
          idJson: 'cd950070-0a4c-4cb3-83cc-028057372bf0',
          id: 'daa91893-9532-47ea-8513-56a005cd6b81'
        },
        origemDeTransicoesProibidas: [],
        destinoDeTransicoesProibidas: [],
        alternativaDeTransicoesProibidas: [],
        estagiosGruposSemaforicos: [
          {
            idJson: 'a7980921-bf02-4f2e-9332-2bece687311f'
          },
          {
            idJson: 'e9717605-2003-4ee5-8d07-31021ccc581a'
          }
        ],
        verdeMinimoEstagio: 10,
        isVeicular: true
      },
      {
        id: 'a7086eb8-9f9e-4742-9fe0-e0c80c79c4f8',
        idJson: '9ecae09c-42ec-443d-a7b9-b43ed392bee7',
        tempoMaximoPermanencia: 127,
        tempoMaximoPermanenciaAtivado: true,
        demandaPrioritaria: false,
        tempoVerdeDemandaPrioritaria: 1,
        posicao: 3,
        anel: {
          idJson: '7ff51a82-b32d-4288-afc1-a6a9eb85d78a'
        },
        imagem: {
          idJson: '8828b6f2-fa44-4045-84e7-13b8b2aa1526',
          id: '00ddbe95-a761-4237-9ba2-4c5bf962d57a'
        },
        origemDeTransicoesProibidas: [],
        destinoDeTransicoesProibidas: [],
        alternativaDeTransicoesProibidas: [],
        estagiosGruposSemaforicos: [
          {
            idJson: 'e33c5a14-665a-4363-9ffd-4e52e315eadc'
          },
          {
            idJson: '96806113-b9b4-49ff-a3b7-69ab45e51a9b'
          }
        ],
        verdeMinimoEstagio: 10,
        isVeicular: true
      },
      {
        id: '3259ffc7-e58a-495b-83cb-41090cbb1fb4',
        idJson: 'e225807c-145e-49ce-9e97-1906c3386af5',
        tempoMaximoPermanencia: 127,
        tempoMaximoPermanenciaAtivado: true,
        demandaPrioritaria: false,
        tempoVerdeDemandaPrioritaria: 1,
        posicao: 3,
        anel: {
          idJson: '9f7ac702-9979-48c7-8007-bd97a867a41a'
        },
        imagem: {
          idJson: '3057b193-6543-449f-8f54-04d410e5c5be',
          id: 'c1537ace-4406-4762-9a1f-6eae03f2f451'
        },
        origemDeTransicoesProibidas: [],
        destinoDeTransicoesProibidas: [],
        alternativaDeTransicoesProibidas: [],
        estagiosGruposSemaforicos: [
          {
            idJson: '8e158e60-5683-4b94-9a1d-fb49a4915d73'
          },
          {
            idJson: '4edde9b8-a629-40b1-975d-3553908785ab'
          }
        ],
        verdeMinimoEstagio: 10,
        isVeicular: true
      }
    ],
    gruposSemaforicos: [
      {
        id: '478a8b8a-47f0-42fc-83a3-2b5de82a2cb1',
        idJson: '75ae793c-d90c-4279-844f-53dc2ba788d2',
        tipo: 'PEDESTRE',
        posicao: 3,
        faseVermelhaApagadaAmareloIntermitente: false,
        tempoVerdeSeguranca: 4,
        anel: {
          idJson: '7ff51a82-b32d-4288-afc1-a6a9eb85d78a'
        },
        verdesConflitantesOrigem: [],
        verdesConflitantesDestino: [
          {
            idJson: '069c4601-f5d9-4506-af7f-3c6a398d1041'
          },
          {
            idJson: '821ba2d3-76fa-403f-ba65-d4ac55327d76'
          }
        ],
        estagiosGruposSemaforicos: [
          {
            idJson: 'b4e97a2e-4f45-4333-8d20-8fc94e1ac51b'
          }
        ],
        transicoes: [
          {
            idJson: 'c55a1cba-41c6-40a2-acdf-fa10c6627183'
          },
          {
            idJson: 'e2ef5bb5-869f-41a1-a132-a2e465477e48'
          }
        ],
        transicoesComGanhoDePassagem: [
          {
            idJson: 'be058e00-8935-401b-b169-0070515cdcfb'
          },
          {
            idJson: '0a7491f3-6e5f-4e72-9c02-5fcea979afb5'
          }
        ],
        tabelasEntreVerdes: [
          {
            idJson: 'c84298c8-15d3-4169-8002-8b663d47d001'
          }
        ]
      },
      {
        id: '123cc9df-4740-40e7-8868-e316e77a83f6',
        idJson: 'e6f0363e-8d8c-4e45-936e-380b56791af7',
        tipo: 'VEICULAR',
        posicao: 8,
        faseVermelhaApagadaAmareloIntermitente: true,
        tempoVerdeSeguranca: 10,
        anel: {
          idJson: '9f7ac702-9979-48c7-8007-bd97a867a41a'
        },
        verdesConflitantesOrigem: [
          {
            idJson: 'c0469112-a097-4730-836e-f45120ce2ff4'
          },
          {
            idJson: '69190958-8e81-4eba-848d-480f26db84b2'
          }
        ],
        verdesConflitantesDestino: [
          {
            idJson: 'c61c8f72-4209-4dbe-baf9-b9bb07ec6c84'
          },
          {
            idJson: 'e145dce3-50b6-4564-84ce-8458bfbe3b9f'
          }
        ],
        estagiosGruposSemaforicos: [
          {
            idJson: 'e7cf2806-225a-4dd3-9590-536472e7f1d7'
          }
        ],
        transicoes: [
          {
            idJson: '3a4a3f35-6ae4-4c13-9f6e-ab9eef59215c'
          },
          {
            idJson: '659d3e75-61ce-4ae6-80ec-0b2d9a3894f6'
          }
        ],
        transicoesComGanhoDePassagem: [
          {
            idJson: '44cabd60-5b5d-4f03-9c47-3fdb003aa018'
          },
          {
            idJson: '1153725d-8a54-4570-9962-348979e01a7c'
          }
        ],
        tabelasEntreVerdes: [
          {
            idJson: '7de29827-acbc-4189-90f8-11d4abd8820b'
          }
        ]
      },
      {
        id: '9bb53b28-8129-485f-84ba-32a06e41a3ad',
        idJson: 'ed58bdd3-f639-426e-b400-46d3d64b0a58',
        tipo: 'VEICULAR',
        posicao: 7,
        faseVermelhaApagadaAmareloIntermitente: true,
        tempoVerdeSeguranca: 10,
        anel: {
          idJson: '9f7ac702-9979-48c7-8007-bd97a867a41a'
        },
        verdesConflitantesOrigem: [
          {
            idJson: 'c61c8f72-4209-4dbe-baf9-b9bb07ec6c84'
          },
          {
            idJson: '4d9caab3-5059-4f6e-b5b3-a91cce90ba71'
          }
        ],
        verdesConflitantesDestino: [
          {
            idJson: '83fb5a07-d290-4e60-a764-3241953162d1'
          }
        ],
        estagiosGruposSemaforicos: [
          {
            idJson: '4edde9b8-a629-40b1-975d-3553908785ab'
          }
        ],
        transicoes: [
          {
            idJson: 'd31f7c7f-2874-4b96-93ed-583fca7753c4'
          },
          {
            idJson: 'aeaa3854-9ecf-4608-a4fb-06db1bde16c7'
          }
        ],
        transicoesComGanhoDePassagem: [
          {
            idJson: '13bafb3c-d1b9-4ccb-bee5-f57334b5e3dc'
          },
          {
            idJson: '48282e59-9834-40a8-8b12-1bb86a0a037a'
          }
        ],
        tabelasEntreVerdes: [
          {
            idJson: 'da677c07-d773-4622-9fb7-efb587c6eb75'
          }
        ]
      },
      {
        id: '47a0896f-76da-472f-a51c-d7255ff17649',
        idJson: '29a8321e-f81d-4e76-a54d-540021e4fc6d',
        tipo: 'VEICULAR',
        posicao: 1,
        faseVermelhaApagadaAmareloIntermitente: true,
        tempoVerdeSeguranca: 10,
        anel: {
          idJson: '7ff51a82-b32d-4288-afc1-a6a9eb85d78a'
        },
        verdesConflitantesOrigem: [
          {
            idJson: '069c4601-f5d9-4506-af7f-3c6a398d1041'
          },
          {
            idJson: '8be564d0-61bc-4328-8d17-acf0f3512edc'
          },
          {
            idJson: 'e07b0c7f-8098-4bb7-b65b-e67832cb57c5'
          }
        ],
        verdesConflitantesDestino: [],
        estagiosGruposSemaforicos: [
          {
            idJson: 'a7980921-bf02-4f2e-9332-2bece687311f'
          }
        ],
        transicoes: [
          {
            idJson: '13c047e4-ca41-484e-b43c-feca3586a3f1'
          },
          {
            idJson: '9fe5181d-56fe-4a02-8244-42413e6c172b'
          }
        ],
        transicoesComGanhoDePassagem: [
          {
            idJson: '8a27e6c3-ed0f-4fd9-a90a-2a10dd2867db'
          },
          {
            idJson: '527999cd-8911-4bd2-9caf-b4e9661e5d71'
          }
        ],
        tabelasEntreVerdes: [
          {
            idJson: '55095895-4d0a-4e4e-9d1a-e373cf72537c'
          }
        ]
      },
      {
        id: '1981a33b-94b2-479a-b033-d037366259dd',
        idJson: 'daed6666-f188-4464-b9cf-3fea37345621',
        tipo: 'PEDESTRE',
        posicao: 4,
        faseVermelhaApagadaAmareloIntermitente: false,
        tempoVerdeSeguranca: 4,
        anel: {
          idJson: '7ff51a82-b32d-4288-afc1-a6a9eb85d78a'
        },
        verdesConflitantesOrigem: [],
        verdesConflitantesDestino: [
          {
            idJson: '8be564d0-61bc-4328-8d17-acf0f3512edc'
          }
        ],
        estagiosGruposSemaforicos: [
          {
            idJson: '683ea3ca-6b82-428f-b55f-e684d4ababcd'
          },
          {
            idJson: '96806113-b9b4-49ff-a3b7-69ab45e51a9b'
          }
        ],
        transicoes: [
          {
            idJson: 'e66fe9e4-58f0-44a9-b608-5623fe880343'
          },
          {
            idJson: '96a1034e-5636-430a-af93-fa60f43b3c3a'
          }
        ],
        transicoesComGanhoDePassagem: [
          {
            idJson: 'c9a1614d-5755-42b4-abde-c5298197a70d'
          },
          {
            idJson: '29a11cff-2e05-412e-b5f8-470b64cee929'
          }
        ],
        tabelasEntreVerdes: [
          {
            idJson: 'c3123af0-d474-4105-84f8-31b20bd218e5'
          }
        ]
      },
      {
        id: '456e4979-cb2f-47ea-bf20-e7322e876864',
        idJson: '81b0f4b6-0c3b-4dc5-ac9b-5e74f279840c',
        tipo: 'VEICULAR',
        posicao: 2,
        faseVermelhaApagadaAmareloIntermitente: true,
        tempoVerdeSeguranca: 10,
        anel: {
          idJson: '7ff51a82-b32d-4288-afc1-a6a9eb85d78a'
        },
        verdesConflitantesOrigem: [
          {
            idJson: '984e5d1f-c1d0-428b-a029-2ad72bc441d4'
          },
          {
            idJson: '821ba2d3-76fa-403f-ba65-d4ac55327d76'
          }
        ],
        verdesConflitantesDestino: [
          {
            idJson: 'e07b0c7f-8098-4bb7-b65b-e67832cb57c5'
          }
        ],
        estagiosGruposSemaforicos: [
          {
            idJson: 'e33c5a14-665a-4363-9ffd-4e52e315eadc'
          }
        ],
        transicoes: [
          {
            idJson: '6677a05e-b542-4c84-bc7e-eaeb3e323f87'
          },
          {
            idJson: 'e654ab7e-a112-442e-927c-14a37d9b4f90'
          }
        ],
        transicoesComGanhoDePassagem: [
          {
            idJson: 'c3316652-4de2-497b-ac66-0d7698429c78'
          },
          {
            idJson: '006306d9-a53d-43ac-85b8-b6146a0f4d3e'
          }
        ],
        tabelasEntreVerdes: [
          {
            idJson: '89e25e70-82ae-451c-ae0d-dd590bf1aaa9'
          }
        ]
      },
      {
        id: '30ecbeb9-f191-4743-85b6-28e2f5f11707',
        idJson: 'fe8dc72c-f0f8-40c3-84d5-26e3c7ee1cfb',
        tipo: 'PEDESTRE',
        posicao: 5,
        faseVermelhaApagadaAmareloIntermitente: false,
        tempoVerdeSeguranca: 4,
        anel: {
          idJson: '7ff51a82-b32d-4288-afc1-a6a9eb85d78a'
        },
        verdesConflitantesOrigem: [],
        verdesConflitantesDestino: [
          {
            idJson: '984e5d1f-c1d0-428b-a029-2ad72bc441d4'
          }
        ],
        estagiosGruposSemaforicos: [
          {
            idJson: 'e9717605-2003-4ee5-8d07-31021ccc581a'
          },
          {
            idJson: 'ee599b8f-b2d1-41b2-bceb-4082446182d2'
          }
        ],
        transicoes: [
          {
            idJson: '11227efe-8863-4cbd-918b-f009fa6ad2dd'
          },
          {
            idJson: '6501fd5d-45d3-4014-ad01-4e46a2f4fc23'
          }
        ],
        transicoesComGanhoDePassagem: [
          {
            idJson: '9bd83786-f210-4fae-84d5-d22de4fd586b'
          },
          {
            idJson: 'c8a830cc-7c9b-4c0a-8bb4-afe23d7ab3e4'
          }
        ],
        tabelasEntreVerdes: [
          {
            idJson: '17e79046-ae5f-48d3-9fae-90ae0f8486d1'
          }
        ]
      },
      {
        id: 'af58ed61-bdf0-4eff-837a-005b1c9ad66f',
        idJson: '5f53a090-d3e5-4b4d-bbde-db990caedaf2',
        tipo: 'VEICULAR',
        posicao: 6,
        faseVermelhaApagadaAmareloIntermitente: true,
        tempoVerdeSeguranca: 10,
        anel: {
          idJson: '9f7ac702-9979-48c7-8007-bd97a867a41a'
        },
        verdesConflitantesOrigem: [
          {
            idJson: '108d761c-a366-45f3-8e80-8128618d1a59'
          },
          {
            idJson: 'e145dce3-50b6-4564-84ce-8458bfbe3b9f'
          },
          {
            idJson: '83fb5a07-d290-4e60-a764-3241953162d1'
          }
        ],
        verdesConflitantesDestino: [],
        estagiosGruposSemaforicos: [
          {
            idJson: 'f8866841-8e40-48e6-8dae-7a0a9987dfeb'
          }
        ],
        transicoes: [
          {
            idJson: '02f269da-c6bd-4be3-b142-bfe14c8a2ac6'
          },
          {
            idJson: '752b21a9-178e-45d6-b6bd-245d3a69f692'
          }
        ],
        transicoesComGanhoDePassagem: [
          {
            idJson: '385fa54b-776e-4540-8f78-bc7ef0db9d1b'
          },
          {
            idJson: '3a828db3-26df-4dec-b223-d4b477623daa'
          }
        ],
        tabelasEntreVerdes: [
          {
            idJson: '98e5b41a-72a9-47b1-8bed-adbd022ac65b'
          }
        ]
      },
      {
        id: '55e36d3d-5d3e-40d8-9345-832d4feffbdb',
        idJson: '3b33af34-da3c-4803-80c4-b21bca918c2a',
        tipo: 'PEDESTRE',
        posicao: 10,
        faseVermelhaApagadaAmareloIntermitente: false,
        tempoVerdeSeguranca: 4,
        anel: {
          idJson: '9f7ac702-9979-48c7-8007-bd97a867a41a'
        },
        verdesConflitantesOrigem: [],
        verdesConflitantesDestino: [
          {
            idJson: '4d9caab3-5059-4f6e-b5b3-a91cce90ba71'
          },
          {
            idJson: '69190958-8e81-4eba-848d-480f26db84b2'
          }
        ],
        estagiosGruposSemaforicos: [
          {
            idJson: 'ca6430c8-f68d-4c78-8674-bb2fc5625fd5'
          }
        ],
        transicoes: [
          {
            idJson: '3487eb06-7d87-4daf-9e09-737e8501fc49'
          },
          {
            idJson: '3ea731af-9877-4991-8ebd-f87132519160'
          }
        ],
        transicoesComGanhoDePassagem: [
          {
            idJson: 'bd6ea42a-0f55-431f-a2c6-a923f7859138'
          },
          {
            idJson: '1d752b10-250d-4a1a-b59e-93d4a622a7aa'
          }
        ],
        tabelasEntreVerdes: [
          {
            idJson: '809fa84a-c105-4972-ad98-62786b2421d1'
          }
        ]
      },
      {
        id: '6c6939e9-c30a-4703-8bc8-09bd793c17cd',
        idJson: 'cd5f89f1-1051-4075-82ca-b861b0843b5e',
        tipo: 'PEDESTRE',
        posicao: 9,
        faseVermelhaApagadaAmareloIntermitente: false,
        tempoVerdeSeguranca: 4,
        anel: {
          idJson: '9f7ac702-9979-48c7-8007-bd97a867a41a'
        },
        verdesConflitantesOrigem: [],
        verdesConflitantesDestino: [
          {
            idJson: '108d761c-a366-45f3-8e80-8128618d1a59'
          },
          {
            idJson: 'c0469112-a097-4730-836e-f45120ce2ff4'
          }
        ],
        estagiosGruposSemaforicos: [
          {
            idJson: '8e158e60-5683-4b94-9a1d-fb49a4915d73'
          }
        ],
        transicoes: [
          {
            idJson: '6a6d5690-2a5f-44c7-ac26-438c4e7ac002'
          },
          {
            idJson: '63c127d8-e28e-45de-abc2-1ad3aab12810'
          }
        ],
        transicoesComGanhoDePassagem: [
          {
            idJson: 'e5a227e2-e0a9-4c8f-a1ab-946d1be226a2'
          },
          {
            idJson: 'd5c6166e-676b-4f59-82e1-75c2cebbb3fc'
          }
        ],
        tabelasEntreVerdes: [
          {
            idJson: '87a88607-18f2-435c-9ab0-1ac8b681d80d'
          }
        ]
      }
    ],
    detectores: [
      {
        id: '38cf5900-0207-46ff-9465-1c780d736128',
        idJson: '5c579893-194a-4f6d-b3ad-afb44df20e10',
        tipo: 'VEICULAR',
        posicao: 1,
        monitorado: false,
        tempoAusenciaDeteccao: 0,
        tempoDeteccaoPermanente: 0,
        anel: {
          idJson: '9f7ac702-9979-48c7-8007-bd97a867a41a'
        },
        estagio: {
          idJson: 'ef29231a-c573-4b95-b9a2-c4c467cad9d8'
        }
      },
      {
        id: '55da8eb6-46ba-4426-8a29-acafeda16d5d',
        idJson: 'cf3d3cb9-c985-412e-892a-094d944a6ae0',
        tipo: 'PEDESTRE',
        posicao: 1,
        monitorado: false,
        tempoAusenciaDeteccao: 0,
        tempoDeteccaoPermanente: 0,
        anel: {
          idJson: '7ff51a82-b32d-4288-afc1-a6a9eb85d78a'
        },
        estagio: {
          idJson: 'af5f7e28-15c7-4902-96e1-c8184c4ee3cd'
        }
      }
    ],
    transicoesProibidas: [],
    estagiosGruposSemaforicos: [
      {
        id: '6c2a5f3c-c963-4965-8fe0-c29c8b8a7d1a',
        idJson: 'e9717605-2003-4ee5-8d07-31021ccc581a',
        estagio: {
          idJson: 'f1015990-bac7-4a65-9dbc-8f8a4472fd5f'
        },
        grupoSemaforico: {
          idJson: 'fe8dc72c-f0f8-40c3-84d5-26e3c7ee1cfb'
        }
      },
      {
        id: '2e5f97b0-3217-40cf-ab94-141a91272085',
        idJson: 'b4e97a2e-4f45-4333-8d20-8fc94e1ac51b',
        estagio: {
          idJson: 'af5f7e28-15c7-4902-96e1-c8184c4ee3cd'
        },
        grupoSemaforico: {
          idJson: '75ae793c-d90c-4279-844f-53dc2ba788d2'
        }
      },
      {
        id: '08093cfb-796b-4c3b-b165-9532ab0e9910',
        idJson: 'e33c5a14-665a-4363-9ffd-4e52e315eadc',
        estagio: {
          idJson: '9ecae09c-42ec-443d-a7b9-b43ed392bee7'
        },
        grupoSemaforico: {
          idJson: '81b0f4b6-0c3b-4dc5-ac9b-5e74f279840c'
        }
      },
      {
        id: '6d66bcdc-15f3-4b95-bfd0-9b74409b9b24',
        idJson: 'e7cf2806-225a-4dd3-9590-536472e7f1d7',
        estagio: {
          idJson: 'ef29231a-c573-4b95-b9a2-c4c467cad9d8'
        },
        grupoSemaforico: {
          idJson: 'e6f0363e-8d8c-4e45-936e-380b56791af7'
        }
      },
      {
        id: '97f6d78a-3c00-42f7-9a95-33c305b27347',
        idJson: '4edde9b8-a629-40b1-975d-3553908785ab',
        estagio: {
          idJson: 'e225807c-145e-49ce-9e97-1906c3386af5'
        },
        grupoSemaforico: {
          idJson: 'ed58bdd3-f639-426e-b400-46d3d64b0a58'
        }
      },
      {
        id: '2a8800fa-1cc9-4373-8843-879a0010175f',
        idJson: 'ca6430c8-f68d-4c78-8674-bb2fc5625fd5',
        estagio: {
          idJson: '4d562187-7201-4590-b365-bf3621e7cd86'
        },
        grupoSemaforico: {
          idJson: '3b33af34-da3c-4803-80c4-b21bca918c2a'
        }
      },
      {
        id: 'ec03e5c1-fead-4dbe-a354-53c42314936e',
        idJson: 'f8866841-8e40-48e6-8dae-7a0a9987dfeb',
        estagio: {
          idJson: '4d562187-7201-4590-b365-bf3621e7cd86'
        },
        grupoSemaforico: {
          idJson: '5f53a090-d3e5-4b4d-bbde-db990caedaf2'
        }
      },
      {
        id: 'd43dac11-215f-42f8-b9f7-981fe3e16936',
        idJson: 'ee599b8f-b2d1-41b2-bceb-4082446182d2',
        estagio: {
          idJson: 'af5f7e28-15c7-4902-96e1-c8184c4ee3cd'
        },
        grupoSemaforico: {
          idJson: 'fe8dc72c-f0f8-40c3-84d5-26e3c7ee1cfb'
        }
      },
      {
        id: 'ad7ed4c4-0d40-44c2-9044-ea0d721e200f',
        idJson: '96806113-b9b4-49ff-a3b7-69ab45e51a9b',
        estagio: {
          idJson: '9ecae09c-42ec-443d-a7b9-b43ed392bee7'
        },
        grupoSemaforico: {
          idJson: 'daed6666-f188-4464-b9cf-3fea37345621'
        }
      },
      {
        id: '768c01ef-2ac8-4e35-908b-37e0dae1b434',
        idJson: '683ea3ca-6b82-428f-b55f-e684d4ababcd',
        estagio: {
          idJson: 'af5f7e28-15c7-4902-96e1-c8184c4ee3cd'
        },
        grupoSemaforico: {
          idJson: 'daed6666-f188-4464-b9cf-3fea37345621'
        }
      },
      {
        id: '47b3a4f9-3c7e-43a3-8a49-85629a56e3b4',
        idJson: 'a7980921-bf02-4f2e-9332-2bece687311f',
        estagio: {
          idJson: 'f1015990-bac7-4a65-9dbc-8f8a4472fd5f'
        },
        grupoSemaforico: {
          idJson: '29a8321e-f81d-4e76-a54d-540021e4fc6d'
        }
      },
      {
        id: '63d909a1-c0e6-4247-acf2-94384e6eb7b4',
        idJson: '8e158e60-5683-4b94-9a1d-fb49a4915d73',
        estagio: {
          idJson: 'e225807c-145e-49ce-9e97-1906c3386af5'
        },
        grupoSemaforico: {
          idJson: 'cd5f89f1-1051-4075-82ca-b861b0843b5e'
        }
      }
    ],
    verdesConflitantes: [
      {
        id: '9574f99a-d06f-462d-864c-8d3eaf7adb5d',
        idJson: '69190958-8e81-4eba-848d-480f26db84b2',
        origem: {
          idJson: 'e6f0363e-8d8c-4e45-936e-380b56791af7'
        },
        destino: {
          idJson: '3b33af34-da3c-4803-80c4-b21bca918c2a'
        }
      },
      {
        id: '4eef815f-25c0-459a-9fa6-838f286a134d',
        idJson: 'c0469112-a097-4730-836e-f45120ce2ff4',
        origem: {
          idJson: 'e6f0363e-8d8c-4e45-936e-380b56791af7'
        },
        destino: {
          idJson: 'cd5f89f1-1051-4075-82ca-b861b0843b5e'
        }
      },
      {
        id: '259ff0de-bc90-4790-8486-189973c8258e',
        idJson: '4d9caab3-5059-4f6e-b5b3-a91cce90ba71',
        origem: {
          idJson: 'ed58bdd3-f639-426e-b400-46d3d64b0a58'
        },
        destino: {
          idJson: '3b33af34-da3c-4803-80c4-b21bca918c2a'
        }
      },
      {
        id: '43e82f48-4a47-4b25-ad25-fa30ed17c6de',
        idJson: '984e5d1f-c1d0-428b-a029-2ad72bc441d4',
        origem: {
          idJson: '81b0f4b6-0c3b-4dc5-ac9b-5e74f279840c'
        },
        destino: {
          idJson: 'fe8dc72c-f0f8-40c3-84d5-26e3c7ee1cfb'
        }
      },
      {
        id: '0c608a5a-02de-4266-b59b-33aa0e569ff9',
        idJson: '069c4601-f5d9-4506-af7f-3c6a398d1041',
        origem: {
          idJson: '29a8321e-f81d-4e76-a54d-540021e4fc6d'
        },
        destino: {
          idJson: '75ae793c-d90c-4279-844f-53dc2ba788d2'
        }
      },
      {
        id: '4035afc1-134e-44a8-9df6-bc3b15db47d9',
        idJson: 'e145dce3-50b6-4564-84ce-8458bfbe3b9f',
        origem: {
          idJson: '5f53a090-d3e5-4b4d-bbde-db990caedaf2'
        },
        destino: {
          idJson: 'e6f0363e-8d8c-4e45-936e-380b56791af7'
        }
      },
      {
        id: 'fba2fe6d-23fe-4b80-919b-e187b92abb40',
        idJson: '83fb5a07-d290-4e60-a764-3241953162d1',
        origem: {
          idJson: '5f53a090-d3e5-4b4d-bbde-db990caedaf2'
        },
        destino: {
          idJson: 'ed58bdd3-f639-426e-b400-46d3d64b0a58'
        }
      },
      {
        id: 'a203b2de-dd51-4697-9063-e12d987e63b6',
        idJson: 'e07b0c7f-8098-4bb7-b65b-e67832cb57c5',
        origem: {
          idJson: '29a8321e-f81d-4e76-a54d-540021e4fc6d'
        },
        destino: {
          idJson: '81b0f4b6-0c3b-4dc5-ac9b-5e74f279840c'
        }
      },
      {
        id: '506fbd43-266a-4e4c-862a-569e8cf4409c',
        idJson: '8be564d0-61bc-4328-8d17-acf0f3512edc',
        origem: {
          idJson: '29a8321e-f81d-4e76-a54d-540021e4fc6d'
        },
        destino: {
          idJson: 'daed6666-f188-4464-b9cf-3fea37345621'
        }
      },
      {
        id: '05e5513f-8132-443e-b23e-2d72278335cd',
        idJson: 'c61c8f72-4209-4dbe-baf9-b9bb07ec6c84',
        origem: {
          idJson: 'ed58bdd3-f639-426e-b400-46d3d64b0a58'
        },
        destino: {
          idJson: 'e6f0363e-8d8c-4e45-936e-380b56791af7'
        }
      },
      {
        id: 'a9b0f40b-cb14-42f4-887b-a32286e99e69',
        idJson: '821ba2d3-76fa-403f-ba65-d4ac55327d76',
        origem: {
          idJson: '81b0f4b6-0c3b-4dc5-ac9b-5e74f279840c'
        },
        destino: {
          idJson: '75ae793c-d90c-4279-844f-53dc2ba788d2'
        }
      },
      {
        id: '0fcab4c4-5a99-4758-a40b-56499c05af2a',
        idJson: '108d761c-a366-45f3-8e80-8128618d1a59',
        origem: {
          idJson: '5f53a090-d3e5-4b4d-bbde-db990caedaf2'
        },
        destino: {
          idJson: 'cd5f89f1-1051-4075-82ca-b861b0843b5e'
        }
      }
    ],
    transicoes: [
      {
        id: '147bf00b-557d-4451-b42c-51fc8f0b92a2',
        idJson: '02f269da-c6bd-4be3-b142-bfe14c8a2ac6',
        origem: {
          idJson: '4d562187-7201-4590-b365-bf3621e7cd86'
        },
        destino: {
          idJson: 'ef29231a-c573-4b95-b9a2-c4c467cad9d8'
        },
        tabelaEntreVerdesTransicoes: [
          {
            idJson: 'd5c0366b-7ee9-4a3c-b526-3af88c28de36'
          }
        ],
        grupoSemaforico: {
          idJson: '5f53a090-d3e5-4b4d-bbde-db990caedaf2'
        },
        tipo: 'PERDA_DE_PASSAGEM',
        tempoAtrasoGrupo: '0',
        atrasoDeGrupo: {
          idJson: 'cb5536fe-cda8-442c-9d9a-b139eb502dd5'
        },
        modoIntermitenteOuApagado: true
      },
      {
        id: '36056dbc-4928-4877-b7af-5817d16c9ec0',
        idJson: '3487eb06-7d87-4daf-9e09-737e8501fc49',
        origem: {
          idJson: '4d562187-7201-4590-b365-bf3621e7cd86'
        },
        destino: {
          idJson: 'ef29231a-c573-4b95-b9a2-c4c467cad9d8'
        },
        tabelaEntreVerdesTransicoes: [
          {
            idJson: 'afe05718-378b-4a9c-abac-3684e68eba6e'
          }
        ],
        grupoSemaforico: {
          idJson: '3b33af34-da3c-4803-80c4-b21bca918c2a'
        },
        tipo: 'PERDA_DE_PASSAGEM',
        tempoAtrasoGrupo: '0',
        atrasoDeGrupo: {
          idJson: '12375ba0-3ef1-4831-9e4d-07d3b6b00c92'
        },
        modoIntermitenteOuApagado: true
      },
      {
        id: '697861cd-417d-4f11-936d-7586a19181d9',
        idJson: '3ea731af-9877-4991-8ebd-f87132519160',
        origem: {
          idJson: '4d562187-7201-4590-b365-bf3621e7cd86'
        },
        destino: {
          idJson: 'e225807c-145e-49ce-9e97-1906c3386af5'
        },
        tabelaEntreVerdesTransicoes: [
          {
            idJson: '769505a7-9694-4b78-b0c7-d0203b03ec82'
          }
        ],
        grupoSemaforico: {
          idJson: '3b33af34-da3c-4803-80c4-b21bca918c2a'
        },
        tipo: 'PERDA_DE_PASSAGEM',
        tempoAtrasoGrupo: '0',
        atrasoDeGrupo: {
          idJson: '825a20ed-eef3-4ff7-a3e3-db1717148d8c'
        },
        modoIntermitenteOuApagado: false
      },
      {
        id: 'a3207d91-2848-4af9-b8e6-eb3d1c824f77',
        idJson: 'd31f7c7f-2874-4b96-93ed-583fca7753c4',
        origem: {
          idJson: 'e225807c-145e-49ce-9e97-1906c3386af5'
        },
        destino: {
          idJson: 'ef29231a-c573-4b95-b9a2-c4c467cad9d8'
        },
        tabelaEntreVerdesTransicoes: [
          {
            idJson: 'ca7acd48-23c1-4187-b545-33f049f6e36f'
          }
        ],
        grupoSemaforico: {
          idJson: 'ed58bdd3-f639-426e-b400-46d3d64b0a58'
        },
        tipo: 'PERDA_DE_PASSAGEM',
        tempoAtrasoGrupo: '0',
        atrasoDeGrupo: {
          idJson: '85d7c64d-b95b-4d28-bcba-b28e6db4c8a8'
        },
        modoIntermitenteOuApagado: true
      },
      {
        id: 'e69fea56-f664-48f4-accc-ac5edeaff433',
        idJson: '9fe5181d-56fe-4a02-8244-42413e6c172b',
        origem: {
          idJson: 'f1015990-bac7-4a65-9dbc-8f8a4472fd5f'
        },
        destino: {
          idJson: '9ecae09c-42ec-443d-a7b9-b43ed392bee7'
        },
        tabelaEntreVerdesTransicoes: [
          {
            idJson: '363acc7d-36ab-4936-9c1c-eed7cc39bdc6'
          }
        ],
        grupoSemaforico: {
          idJson: '29a8321e-f81d-4e76-a54d-540021e4fc6d'
        },
        tipo: 'PERDA_DE_PASSAGEM',
        tempoAtrasoGrupo: '4',
        atrasoDeGrupo: {
          idJson: '3c94a482-92fd-4f10-a63f-8650fdee9124'
        },
        modoIntermitenteOuApagado: true
      },
      {
        id: '431bc01d-58d4-4af7-9016-e236f872ca3b',
        idJson: 'e654ab7e-a112-442e-927c-14a37d9b4f90',
        origem: {
          idJson: '9ecae09c-42ec-443d-a7b9-b43ed392bee7'
        },
        destino: {
          idJson: 'af5f7e28-15c7-4902-96e1-c8184c4ee3cd'
        },
        tabelaEntreVerdesTransicoes: [
          {
            idJson: '53624044-804d-4cfd-8074-b67708486bd4'
          }
        ],
        grupoSemaforico: {
          idJson: '81b0f4b6-0c3b-4dc5-ac9b-5e74f279840c'
        },
        tipo: 'PERDA_DE_PASSAGEM',
        tempoAtrasoGrupo: '0',
        atrasoDeGrupo: {
          idJson: '6f57d9e6-c709-4f30-ae5d-ee9ff642326e'
        },
        modoIntermitenteOuApagado: true
      },
      {
        id: 'c1a84bff-b91d-4a1a-8b86-d1554bc8dc70',
        idJson: '63c127d8-e28e-45de-abc2-1ad3aab12810',
        origem: {
          idJson: 'e225807c-145e-49ce-9e97-1906c3386af5'
        },
        destino: {
          idJson: '4d562187-7201-4590-b365-bf3621e7cd86'
        },
        tabelaEntreVerdesTransicoes: [
          {
            idJson: 'f806980c-a12c-46ac-80a9-1affb44ab0ab'
          }
        ],
        grupoSemaforico: {
          idJson: 'cd5f89f1-1051-4075-82ca-b861b0843b5e'
        },
        tipo: 'PERDA_DE_PASSAGEM',
        tempoAtrasoGrupo: '0',
        atrasoDeGrupo: {
          idJson: '3352f3b1-99a6-4f0a-8fbc-f0ef43ba6cdd'
        },
        modoIntermitenteOuApagado: true
      },
      {
        id: '7afc29af-6fb4-430f-9a09-6284259d46fd',
        idJson: '659d3e75-61ce-4ae6-80ec-0b2d9a3894f6',
        origem: {
          idJson: 'ef29231a-c573-4b95-b9a2-c4c467cad9d8'
        },
        destino: {
          idJson: 'e225807c-145e-49ce-9e97-1906c3386af5'
        },
        tabelaEntreVerdesTransicoes: [
          {
            idJson: 'f0b88dd9-cbe1-4e3e-853d-9aabc752afb4'
          }
        ],
        grupoSemaforico: {
          idJson: 'e6f0363e-8d8c-4e45-936e-380b56791af7'
        },
        tipo: 'PERDA_DE_PASSAGEM',
        tempoAtrasoGrupo: '0',
        atrasoDeGrupo: {
          idJson: '27ae2f1e-97ba-4eb5-b435-6f65feba8295'
        },
        modoIntermitenteOuApagado: true
      },
      {
        id: '45ea3bd3-d0d2-4b25-a797-8ab27db270b0',
        idJson: '6a6d5690-2a5f-44c7-ac26-438c4e7ac002',
        origem: {
          idJson: 'e225807c-145e-49ce-9e97-1906c3386af5'
        },
        destino: {
          idJson: 'ef29231a-c573-4b95-b9a2-c4c467cad9d8'
        },
        tabelaEntreVerdesTransicoes: [
          {
            idJson: '4ae658c0-aaa9-44b7-b900-cc4708658459'
          }
        ],
        grupoSemaforico: {
          idJson: 'cd5f89f1-1051-4075-82ca-b861b0843b5e'
        },
        tipo: 'PERDA_DE_PASSAGEM',
        tempoAtrasoGrupo: '0',
        atrasoDeGrupo: {
          idJson: 'eea2c31b-f8d6-44a7-909b-fa809380fbd5'
        },
        modoIntermitenteOuApagado: false
      },
      {
        id: 'fbdccf4e-a648-4757-a461-6bbdaba87e12',
        idJson: '6501fd5d-45d3-4014-ad01-4e46a2f4fc23',
        origem: {
          idJson: 'af5f7e28-15c7-4902-96e1-c8184c4ee3cd'
        },
        destino: {
          idJson: '9ecae09c-42ec-443d-a7b9-b43ed392bee7'
        },
        tabelaEntreVerdesTransicoes: [
          {
            idJson: '86331ba1-64ee-4616-89c9-a393eb51f8b7'
          }
        ],
        grupoSemaforico: {
          idJson: 'fe8dc72c-f0f8-40c3-84d5-26e3c7ee1cfb'
        },
        tipo: 'PERDA_DE_PASSAGEM',
        tempoAtrasoGrupo: '0',
        atrasoDeGrupo: {
          idJson: '0aeb9c3c-87fb-48d2-9cc9-cf4aaa9a3f9f'
        },
        modoIntermitenteOuApagado: true
      },
      {
        id: '18e5c2df-2b1b-4ee9-b477-5a0bbe86a192',
        idJson: '752b21a9-178e-45d6-b6bd-245d3a69f692',
        origem: {
          idJson: '4d562187-7201-4590-b365-bf3621e7cd86'
        },
        destino: {
          idJson: 'e225807c-145e-49ce-9e97-1906c3386af5'
        },
        tabelaEntreVerdesTransicoes: [
          {
            idJson: '2ce28bbb-0c2f-4329-8a61-ff065d72314d'
          }
        ],
        grupoSemaforico: {
          idJson: '5f53a090-d3e5-4b4d-bbde-db990caedaf2'
        },
        tipo: 'PERDA_DE_PASSAGEM',
        tempoAtrasoGrupo: '0',
        atrasoDeGrupo: {
          idJson: 'ea5a8fc9-9ec1-40ee-8d93-b7b792071f2f'
        },
        modoIntermitenteOuApagado: false
      },
      {
        id: 'f9769b96-9d06-44f7-97f9-9ae76c4afeb5',
        idJson: 'aeaa3854-9ecf-4608-a4fb-06db1bde16c7',
        origem: {
          idJson: 'e225807c-145e-49ce-9e97-1906c3386af5'
        },
        destino: {
          idJson: '4d562187-7201-4590-b365-bf3621e7cd86'
        },
        tabelaEntreVerdesTransicoes: [
          {
            idJson: '4916da4c-b7e7-4653-a892-ab57c2c32188'
          }
        ],
        grupoSemaforico: {
          idJson: 'ed58bdd3-f639-426e-b400-46d3d64b0a58'
        },
        tipo: 'PERDA_DE_PASSAGEM',
        tempoAtrasoGrupo: '0',
        atrasoDeGrupo: {
          idJson: '1a41d37a-6938-4706-90c5-da636160ea65'
        },
        modoIntermitenteOuApagado: false
      },
      {
        id: 'ee955d9c-7ebb-4c0f-beeb-3735fbf9b846',
        idJson: '96a1034e-5636-430a-af93-fa60f43b3c3a',
        origem: {
          idJson: 'af5f7e28-15c7-4902-96e1-c8184c4ee3cd'
        },
        destino: {
          idJson: 'f1015990-bac7-4a65-9dbc-8f8a4472fd5f'
        },
        tabelaEntreVerdesTransicoes: [
          {
            idJson: '2e819d55-5e41-4c71-a433-00dddf083f2a'
          }
        ],
        grupoSemaforico: {
          idJson: 'daed6666-f188-4464-b9cf-3fea37345621'
        },
        tipo: 'PERDA_DE_PASSAGEM',
        tempoAtrasoGrupo: '0',
        atrasoDeGrupo: {
          idJson: '86a7b2a1-d869-4c21-8554-6481d900b308'
        },
        modoIntermitenteOuApagado: true
      },
      {
        id: '5a93d2be-9cba-4387-83e6-a5084b7f9a28',
        idJson: 'e66fe9e4-58f0-44a9-b608-5623fe880343',
        origem: {
          idJson: '9ecae09c-42ec-443d-a7b9-b43ed392bee7'
        },
        destino: {
          idJson: 'f1015990-bac7-4a65-9dbc-8f8a4472fd5f'
        },
        tabelaEntreVerdesTransicoes: [
          {
            idJson: '2e9ac65a-8825-4eec-bef4-933495d8833f'
          }
        ],
        grupoSemaforico: {
          idJson: 'daed6666-f188-4464-b9cf-3fea37345621'
        },
        tipo: 'PERDA_DE_PASSAGEM',
        tempoAtrasoGrupo: '0',
        atrasoDeGrupo: {
          idJson: '9c0d61c8-ba01-4bee-a8fd-a113d126342e'
        },
        modoIntermitenteOuApagado: true
      },
      {
        id: '4ade8de8-34bb-4ada-9449-f1f1f4f31bf4',
        idJson: '11227efe-8863-4cbd-918b-f009fa6ad2dd',
        origem: {
          idJson: 'f1015990-bac7-4a65-9dbc-8f8a4472fd5f'
        },
        destino: {
          idJson: '9ecae09c-42ec-443d-a7b9-b43ed392bee7'
        },
        tabelaEntreVerdesTransicoes: [
          {
            idJson: 'fd7aba92-700a-41a8-a408-ee8faef6c47b'
          }
        ],
        grupoSemaforico: {
          idJson: 'fe8dc72c-f0f8-40c3-84d5-26e3c7ee1cfb'
        },
        tipo: 'PERDA_DE_PASSAGEM',
        tempoAtrasoGrupo: '0',
        atrasoDeGrupo: {
          idJson: '245db728-cbc0-448a-ac5a-183ace140261'
        },
        modoIntermitenteOuApagado: true
      },
      {
        id: '51baae61-14a5-45ca-810e-fb8148bb71ce',
        idJson: 'c55a1cba-41c6-40a2-acdf-fa10c6627183',
        origem: {
          idJson: 'af5f7e28-15c7-4902-96e1-c8184c4ee3cd'
        },
        destino: {
          idJson: 'f1015990-bac7-4a65-9dbc-8f8a4472fd5f'
        },
        tabelaEntreVerdesTransicoes: [
          {
            idJson: 'eb298e84-ef28-404f-89a0-e73fd7400c17'
          }
        ],
        grupoSemaforico: {
          idJson: '75ae793c-d90c-4279-844f-53dc2ba788d2'
        },
        tipo: 'PERDA_DE_PASSAGEM',
        tempoAtrasoGrupo: '0',
        atrasoDeGrupo: {
          idJson: 'b3b6d919-d673-4876-b016-df048057c35c'
        },
        modoIntermitenteOuApagado: true
      },
      {
        id: '0f17e251-225c-4e09-9a52-9d95b7a04f1c',
        idJson: '3a4a3f35-6ae4-4c13-9f6e-ab9eef59215c',
        origem: {
          idJson: 'ef29231a-c573-4b95-b9a2-c4c467cad9d8'
        },
        destino: {
          idJson: '4d562187-7201-4590-b365-bf3621e7cd86'
        },
        tabelaEntreVerdesTransicoes: [
          {
            idJson: '2138fdd9-8df5-45fe-9425-6927de97885b'
          }
        ],
        grupoSemaforico: {
          idJson: 'e6f0363e-8d8c-4e45-936e-380b56791af7'
        },
        tipo: 'PERDA_DE_PASSAGEM',
        tempoAtrasoGrupo: '0',
        atrasoDeGrupo: {
          idJson: 'f6da4400-c75e-4f1b-95b1-7236b3885bf2'
        },
        modoIntermitenteOuApagado: false
      },
      {
        id: '3cf21091-91df-420d-81da-09d2dde349ad',
        idJson: '6677a05e-b542-4c84-bc7e-eaeb3e323f87',
        origem: {
          idJson: '9ecae09c-42ec-443d-a7b9-b43ed392bee7'
        },
        destino: {
          idJson: 'f1015990-bac7-4a65-9dbc-8f8a4472fd5f'
        },
        tabelaEntreVerdesTransicoes: [
          {
            idJson: '421af560-8567-4399-9b80-15b90de88b99'
          }
        ],
        grupoSemaforico: {
          idJson: '81b0f4b6-0c3b-4dc5-ac9b-5e74f279840c'
        },
        tipo: 'PERDA_DE_PASSAGEM',
        tempoAtrasoGrupo: '4',
        atrasoDeGrupo: {
          idJson: '8c73c146-a220-40a8-ac55-7b53a502b96a'
        },
        modoIntermitenteOuApagado: false
      },
      {
        id: '7b9a12e2-8622-438e-a09c-9fa0ed7eed6a',
        idJson: 'e2ef5bb5-869f-41a1-a132-a2e465477e48',
        origem: {
          idJson: 'af5f7e28-15c7-4902-96e1-c8184c4ee3cd'
        },
        destino: {
          idJson: '9ecae09c-42ec-443d-a7b9-b43ed392bee7'
        },
        tabelaEntreVerdesTransicoes: [
          {
            idJson: '95da9473-7a07-4221-871f-5cbfc91ad97e'
          }
        ],
        grupoSemaforico: {
          idJson: '75ae793c-d90c-4279-844f-53dc2ba788d2'
        },
        tipo: 'PERDA_DE_PASSAGEM',
        tempoAtrasoGrupo: '0',
        atrasoDeGrupo: {
          idJson: '07bd8d5c-bf15-4b5f-897c-672af5d14374'
        },
        modoIntermitenteOuApagado: false
      },
      {
        id: 'b83ea133-8eee-4c3e-99ae-5fc8fe1c5f22',
        idJson: '13c047e4-ca41-484e-b43c-feca3586a3f1',
        origem: {
          idJson: 'f1015990-bac7-4a65-9dbc-8f8a4472fd5f'
        },
        destino: {
          idJson: 'af5f7e28-15c7-4902-96e1-c8184c4ee3cd'
        },
        tabelaEntreVerdesTransicoes: [
          {
            idJson: 'de7dd182-1222-4917-8fb7-4e154314cc79'
          }
        ],
        grupoSemaforico: {
          idJson: '29a8321e-f81d-4e76-a54d-540021e4fc6d'
        },
        tipo: 'PERDA_DE_PASSAGEM',
        tempoAtrasoGrupo: '0',
        atrasoDeGrupo: {
          idJson: '30a4e4a8-02fd-42cb-b292-a8d1667a2a01'
        },
        modoIntermitenteOuApagado: false
      }
    ],
    transicoesComGanhoDePassagem: [
      {
        id: '5f95af05-853c-4364-ab27-194ebdea3422',
        idJson: '1153725d-8a54-4570-9962-348979e01a7c',
        origem: {
          idJson: 'e225807c-145e-49ce-9e97-1906c3386af5'
        },
        destino: {
          idJson: 'ef29231a-c573-4b95-b9a2-c4c467cad9d8'
        },
        tabelaEntreVerdesTransicoes: [],
        grupoSemaforico: {
          idJson: 'e6f0363e-8d8c-4e45-936e-380b56791af7'
        },
        tipo: 'GANHO_DE_PASSAGEM',
        tempoAtrasoGrupo: '0',
        atrasoDeGrupo: {
          idJson: '00f8108a-75a5-45e0-964c-0bdc9b05e9a9'
        },
        modoIntermitenteOuApagado: false
      },
      {
        id: '00ae7ce8-ef64-45b2-a1ea-8b3e189e8198',
        idJson: '44cabd60-5b5d-4f03-9c47-3fdb003aa018',
        origem: {
          idJson: '4d562187-7201-4590-b365-bf3621e7cd86'
        },
        destino: {
          idJson: 'ef29231a-c573-4b95-b9a2-c4c467cad9d8'
        },
        tabelaEntreVerdesTransicoes: [],
        grupoSemaforico: {
          idJson: 'e6f0363e-8d8c-4e45-936e-380b56791af7'
        },
        tipo: 'GANHO_DE_PASSAGEM',
        tempoAtrasoGrupo: '0',
        atrasoDeGrupo: {
          idJson: 'afc35f70-39ca-42d9-9916-af8d82aa22e8'
        },
        modoIntermitenteOuApagado: false
      },
      {
        id: '54264869-b2ae-47d2-939a-bb7161e2fee8',
        idJson: '385fa54b-776e-4540-8f78-bc7ef0db9d1b',
        origem: {
          idJson: 'ef29231a-c573-4b95-b9a2-c4c467cad9d8'
        },
        destino: {
          idJson: '4d562187-7201-4590-b365-bf3621e7cd86'
        },
        tabelaEntreVerdesTransicoes: [],
        grupoSemaforico: {
          idJson: '5f53a090-d3e5-4b4d-bbde-db990caedaf2'
        },
        tipo: 'GANHO_DE_PASSAGEM',
        tempoAtrasoGrupo: '0',
        atrasoDeGrupo: {
          idJson: '1e5eb573-f5d8-4425-87fb-af59b43d8d05'
        },
        modoIntermitenteOuApagado: false
      },
      {
        id: '7a2caff8-524c-46b5-9f03-0c8ae98df66f',
        idJson: 'bd6ea42a-0f55-431f-a2c6-a923f7859138',
        origem: {
          idJson: 'ef29231a-c573-4b95-b9a2-c4c467cad9d8'
        },
        destino: {
          idJson: '4d562187-7201-4590-b365-bf3621e7cd86'
        },
        tabelaEntreVerdesTransicoes: [],
        grupoSemaforico: {
          idJson: '3b33af34-da3c-4803-80c4-b21bca918c2a'
        },
        tipo: 'GANHO_DE_PASSAGEM',
        tempoAtrasoGrupo: '0',
        atrasoDeGrupo: {
          idJson: 'c4737e95-4c46-48bf-bbf9-30efd292b430'
        },
        modoIntermitenteOuApagado: false
      },
      {
        id: 'f85949ba-6963-4de0-be8c-0dac8ac4b4c0',
        idJson: 'd5c6166e-676b-4f59-82e1-75c2cebbb3fc',
        origem: {
          idJson: 'ef29231a-c573-4b95-b9a2-c4c467cad9d8'
        },
        destino: {
          idJson: 'e225807c-145e-49ce-9e97-1906c3386af5'
        },
        tabelaEntreVerdesTransicoes: [],
        grupoSemaforico: {
          idJson: 'cd5f89f1-1051-4075-82ca-b861b0843b5e'
        },
        tipo: 'GANHO_DE_PASSAGEM',
        tempoAtrasoGrupo: '0',
        atrasoDeGrupo: {
          idJson: '9ddd85c8-3724-437e-899c-2e55f13f021c'
        },
        modoIntermitenteOuApagado: false
      },
      {
        id: '67bfa330-9788-46a4-91c3-adc605edad23',
        idJson: 'c3316652-4de2-497b-ac66-0d7698429c78',
        origem: {
          idJson: 'af5f7e28-15c7-4902-96e1-c8184c4ee3cd'
        },
        destino: {
          idJson: '9ecae09c-42ec-443d-a7b9-b43ed392bee7'
        },
        tabelaEntreVerdesTransicoes: [],
        grupoSemaforico: {
          idJson: '81b0f4b6-0c3b-4dc5-ac9b-5e74f279840c'
        },
        tipo: 'GANHO_DE_PASSAGEM',
        tempoAtrasoGrupo: '0',
        atrasoDeGrupo: {
          idJson: '0044cfc3-ac5e-45a6-bb1f-65020d51bf2e'
        },
        modoIntermitenteOuApagado: false
      },
      {
        id: '580b8a0a-5296-4a35-babc-4c9b1dbfcd8f',
        idJson: 'be058e00-8935-401b-b169-0070515cdcfb',
        origem: {
          idJson: '9ecae09c-42ec-443d-a7b9-b43ed392bee7'
        },
        destino: {
          idJson: 'af5f7e28-15c7-4902-96e1-c8184c4ee3cd'
        },
        tabelaEntreVerdesTransicoes: [],
        grupoSemaforico: {
          idJson: '75ae793c-d90c-4279-844f-53dc2ba788d2'
        },
        tipo: 'GANHO_DE_PASSAGEM',
        tempoAtrasoGrupo: '0',
        atrasoDeGrupo: {
          idJson: '58b04a67-8797-46fb-9288-c6d9a663b1a2'
        },
        modoIntermitenteOuApagado: false
      },
      {
        id: '0a54d2cb-b0fd-4306-85d1-1be51517814f',
        idJson: '13bafb3c-d1b9-4ccb-bee5-f57334b5e3dc',
        origem: {
          idJson: 'ef29231a-c573-4b95-b9a2-c4c467cad9d8'
        },
        destino: {
          idJson: 'e225807c-145e-49ce-9e97-1906c3386af5'
        },
        tabelaEntreVerdesTransicoes: [],
        grupoSemaforico: {
          idJson: 'ed58bdd3-f639-426e-b400-46d3d64b0a58'
        },
        tipo: 'GANHO_DE_PASSAGEM',
        tempoAtrasoGrupo: '0',
        atrasoDeGrupo: {
          idJson: 'd3f571b1-fec4-4533-a7cc-b0ad4f3b9669'
        },
        modoIntermitenteOuApagado: false
      },
      {
        id: '832819bd-6b05-409f-9e14-cbd787fccaf6',
        idJson: '48282e59-9834-40a8-8b12-1bb86a0a037a',
        origem: {
          idJson: '4d562187-7201-4590-b365-bf3621e7cd86'
        },
        destino: {
          idJson: 'e225807c-145e-49ce-9e97-1906c3386af5'
        },
        tabelaEntreVerdesTransicoes: [],
        grupoSemaforico: {
          idJson: 'ed58bdd3-f639-426e-b400-46d3d64b0a58'
        },
        tipo: 'GANHO_DE_PASSAGEM',
        tempoAtrasoGrupo: '0',
        atrasoDeGrupo: {
          idJson: '47bcdfda-de26-4997-b0c3-e72eb40e63a9'
        },
        modoIntermitenteOuApagado: false
      },
      {
        id: 'b1e8a86d-ff29-481e-8f89-832090255ba4',
        idJson: 'c8a830cc-7c9b-4c0a-8bb4-afe23d7ab3e4',
        origem: {
          idJson: '9ecae09c-42ec-443d-a7b9-b43ed392bee7'
        },
        destino: {
          idJson: 'af5f7e28-15c7-4902-96e1-c8184c4ee3cd'
        },
        tabelaEntreVerdesTransicoes: [],
        grupoSemaforico: {
          idJson: 'fe8dc72c-f0f8-40c3-84d5-26e3c7ee1cfb'
        },
        tipo: 'GANHO_DE_PASSAGEM',
        tempoAtrasoGrupo: '0',
        atrasoDeGrupo: {
          idJson: '5944be14-fe0b-4cc8-8dc6-ec0cbe1a47c4'
        },
        modoIntermitenteOuApagado: false
      },
      {
        id: 'ddbb841b-be76-4ddb-ab3b-24f3344c8062',
        idJson: '3a828db3-26df-4dec-b223-d4b477623daa',
        origem: {
          idJson: 'e225807c-145e-49ce-9e97-1906c3386af5'
        },
        destino: {
          idJson: '4d562187-7201-4590-b365-bf3621e7cd86'
        },
        tabelaEntreVerdesTransicoes: [],
        grupoSemaforico: {
          idJson: '5f53a090-d3e5-4b4d-bbde-db990caedaf2'
        },
        tipo: 'GANHO_DE_PASSAGEM',
        tempoAtrasoGrupo: '0',
        atrasoDeGrupo: {
          idJson: 'b59f1c76-f2a1-4269-b493-52aac0827605'
        },
        modoIntermitenteOuApagado: false
      },
      {
        id: '690ceb3a-f3d7-48d9-814f-2d9220d29c53',
        idJson: '9bd83786-f210-4fae-84d5-d22de4fd586b',
        origem: {
          idJson: '9ecae09c-42ec-443d-a7b9-b43ed392bee7'
        },
        destino: {
          idJson: 'f1015990-bac7-4a65-9dbc-8f8a4472fd5f'
        },
        tabelaEntreVerdesTransicoes: [],
        grupoSemaforico: {
          idJson: 'fe8dc72c-f0f8-40c3-84d5-26e3c7ee1cfb'
        },
        tipo: 'GANHO_DE_PASSAGEM',
        tempoAtrasoGrupo: '0',
        atrasoDeGrupo: {
          idJson: '89285866-ed3d-4927-a246-b432202f4448'
        },
        modoIntermitenteOuApagado: false
      },
      {
        id: '63c44207-d8cf-4ebf-aeac-01458cc4d795',
        idJson: '0a7491f3-6e5f-4e72-9c02-5fcea979afb5',
        origem: {
          idJson: 'f1015990-bac7-4a65-9dbc-8f8a4472fd5f'
        },
        destino: {
          idJson: 'af5f7e28-15c7-4902-96e1-c8184c4ee3cd'
        },
        tabelaEntreVerdesTransicoes: [],
        grupoSemaforico: {
          idJson: '75ae793c-d90c-4279-844f-53dc2ba788d2'
        },
        tipo: 'GANHO_DE_PASSAGEM',
        tempoAtrasoGrupo: '0',
        atrasoDeGrupo: {
          idJson: 'd658d553-e7cc-4a8f-a915-cce1e01ef039'
        },
        modoIntermitenteOuApagado: false
      },
      {
        id: 'bb65c23e-9bb3-4a33-8df4-784c49f15a6b',
        idJson: '006306d9-a53d-43ac-85b8-b6146a0f4d3e',
        origem: {
          idJson: 'f1015990-bac7-4a65-9dbc-8f8a4472fd5f'
        },
        destino: {
          idJson: '9ecae09c-42ec-443d-a7b9-b43ed392bee7'
        },
        tabelaEntreVerdesTransicoes: [],
        grupoSemaforico: {
          idJson: '81b0f4b6-0c3b-4dc5-ac9b-5e74f279840c'
        },
        tipo: 'GANHO_DE_PASSAGEM',
        tempoAtrasoGrupo: '0',
        atrasoDeGrupo: {
          idJson: 'fb968ff0-0dc1-4915-8f54-6fddcd7846bb'
        },
        modoIntermitenteOuApagado: false
      },
      {
        id: '6db7355e-7f55-4aaa-b7c3-26af4ffd6f39',
        idJson: '527999cd-8911-4bd2-9caf-b4e9661e5d71',
        origem: {
          idJson: '9ecae09c-42ec-443d-a7b9-b43ed392bee7'
        },
        destino: {
          idJson: 'f1015990-bac7-4a65-9dbc-8f8a4472fd5f'
        },
        tabelaEntreVerdesTransicoes: [],
        grupoSemaforico: {
          idJson: '29a8321e-f81d-4e76-a54d-540021e4fc6d'
        },
        tipo: 'GANHO_DE_PASSAGEM',
        tempoAtrasoGrupo: '0',
        atrasoDeGrupo: {
          idJson: '0e76cd7f-23ad-4a3d-a6e2-f58b066ee7f0'
        },
        modoIntermitenteOuApagado: false
      },
      {
        id: 'cd5203e9-5627-4452-a0d9-db03e431e473',
        idJson: '29a11cff-2e05-412e-b5f8-470b64cee929',
        origem: {
          idJson: 'f1015990-bac7-4a65-9dbc-8f8a4472fd5f'
        },
        destino: {
          idJson: 'af5f7e28-15c7-4902-96e1-c8184c4ee3cd'
        },
        tabelaEntreVerdesTransicoes: [],
        grupoSemaforico: {
          idJson: 'daed6666-f188-4464-b9cf-3fea37345621'
        },
        tipo: 'GANHO_DE_PASSAGEM',
        tempoAtrasoGrupo: '0',
        atrasoDeGrupo: {
          idJson: 'ea5359a2-13fa-46f9-8f5b-d3858f0b8dc7'
        },
        modoIntermitenteOuApagado: false
      },
      {
        id: '3e03cee3-394a-4dde-a126-cc6c33d5a017',
        idJson: '8a27e6c3-ed0f-4fd9-a90a-2a10dd2867db',
        origem: {
          idJson: 'af5f7e28-15c7-4902-96e1-c8184c4ee3cd'
        },
        destino: {
          idJson: 'f1015990-bac7-4a65-9dbc-8f8a4472fd5f'
        },
        tabelaEntreVerdesTransicoes: [],
        grupoSemaforico: {
          idJson: '29a8321e-f81d-4e76-a54d-540021e4fc6d'
        },
        tipo: 'GANHO_DE_PASSAGEM',
        tempoAtrasoGrupo: '0',
        atrasoDeGrupo: {
          idJson: '7386de59-8cc1-4791-bdff-70bec3a38c2b'
        },
        modoIntermitenteOuApagado: false
      },
      {
        id: '75c7d764-5ea3-4cb1-bbc7-8152c39e7a40',
        idJson: 'e5a227e2-e0a9-4c8f-a1ab-946d1be226a2',
        origem: {
          idJson: '4d562187-7201-4590-b365-bf3621e7cd86'
        },
        destino: {
          idJson: 'e225807c-145e-49ce-9e97-1906c3386af5'
        },
        tabelaEntreVerdesTransicoes: [],
        grupoSemaforico: {
          idJson: 'cd5f89f1-1051-4075-82ca-b861b0843b5e'
        },
        tipo: 'GANHO_DE_PASSAGEM',
        tempoAtrasoGrupo: '0',
        atrasoDeGrupo: {
          idJson: '9974f47a-9cb9-42e5-b126-fa1e3924e258'
        },
        modoIntermitenteOuApagado: false
      },
      {
        id: 'b8bbe34f-31e9-4447-b4b9-b58b602142b6',
        idJson: 'c9a1614d-5755-42b4-abde-c5298197a70d',
        origem: {
          idJson: 'f1015990-bac7-4a65-9dbc-8f8a4472fd5f'
        },
        destino: {
          idJson: '9ecae09c-42ec-443d-a7b9-b43ed392bee7'
        },
        tabelaEntreVerdesTransicoes: [],
        grupoSemaforico: {
          idJson: 'daed6666-f188-4464-b9cf-3fea37345621'
        },
        tipo: 'GANHO_DE_PASSAGEM',
        tempoAtrasoGrupo: '0',
        atrasoDeGrupo: {
          idJson: '652bb23d-930e-48ab-a13b-88f7a10f2b43'
        },
        modoIntermitenteOuApagado: false
      },
      {
        id: 'dd26ecc0-8a14-4c4e-9f4b-8101dee4788c',
        idJson: '1d752b10-250d-4a1a-b59e-93d4a622a7aa',
        origem: {
          idJson: 'e225807c-145e-49ce-9e97-1906c3386af5'
        },
        destino: {
          idJson: '4d562187-7201-4590-b365-bf3621e7cd86'
        },
        tabelaEntreVerdesTransicoes: [],
        grupoSemaforico: {
          idJson: '3b33af34-da3c-4803-80c4-b21bca918c2a'
        },
        tipo: 'GANHO_DE_PASSAGEM',
        tempoAtrasoGrupo: '0',
        atrasoDeGrupo: {
          idJson: '91c84e62-6c3b-4fd6-8883-992a346d0e08'
        },
        modoIntermitenteOuApagado: false
      }
    ],
    tabelasEntreVerdes: [
      {
        id: 'ac5ad9ec-7639-4c92-8b94-3bd07dbcd1dd',
        idJson: '89e25e70-82ae-451c-ae0d-dd590bf1aaa9',
        descricao: 'PADRÃO',
        posicao: 1,
        grupoSemaforico: {
          idJson: '81b0f4b6-0c3b-4dc5-ac9b-5e74f279840c'
        },
        tabelaEntreVerdesTransicoes: [
          {
            idJson: '421af560-8567-4399-9b80-15b90de88b99'
          },
          {
            idJson: '53624044-804d-4cfd-8074-b67708486bd4'
          }
        ]
      },
      {
        id: '084a636a-f029-41ef-b209-b2d10c3586ad',
        idJson: '7de29827-acbc-4189-90f8-11d4abd8820b',
        descricao: 'PADRÃO',
        posicao: 1,
        grupoSemaforico: {
          idJson: 'e6f0363e-8d8c-4e45-936e-380b56791af7'
        },
        tabelaEntreVerdesTransicoes: [
          {
            idJson: 'f0b88dd9-cbe1-4e3e-853d-9aabc752afb4'
          },
          {
            idJson: '2138fdd9-8df5-45fe-9425-6927de97885b'
          }
        ]
      },
      {
        id: 'a40d295d-10b2-46ab-abff-1428a8b75c96',
        idJson: '809fa84a-c105-4972-ad98-62786b2421d1',
        descricao: 'PADRÃO',
        posicao: 1,
        grupoSemaforico: {
          idJson: '3b33af34-da3c-4803-80c4-b21bca918c2a'
        },
        tabelaEntreVerdesTransicoes: [
          {
            idJson: 'afe05718-378b-4a9c-abac-3684e68eba6e'
          },
          {
            idJson: '769505a7-9694-4b78-b0c7-d0203b03ec82'
          }
        ]
      },
      {
        id: '363fbc05-be34-4808-a594-3d0af86bcf67',
        idJson: '55095895-4d0a-4e4e-9d1a-e373cf72537c',
        descricao: 'PADRÃO',
        posicao: 1,
        grupoSemaforico: {
          idJson: '29a8321e-f81d-4e76-a54d-540021e4fc6d'
        },
        tabelaEntreVerdesTransicoes: [
          {
            idJson: '363acc7d-36ab-4936-9c1c-eed7cc39bdc6'
          },
          {
            idJson: 'de7dd182-1222-4917-8fb7-4e154314cc79'
          }
        ]
      },
      {
        id: 'ea3cc74d-b63f-48d4-8d55-f948a4a63e7c',
        idJson: 'da677c07-d773-4622-9fb7-efb587c6eb75',
        descricao: 'PADRÃO',
        posicao: 1,
        grupoSemaforico: {
          idJson: 'ed58bdd3-f639-426e-b400-46d3d64b0a58'
        },
        tabelaEntreVerdesTransicoes: [
          {
            idJson: 'ca7acd48-23c1-4187-b545-33f049f6e36f'
          },
          {
            idJson: '4916da4c-b7e7-4653-a892-ab57c2c32188'
          }
        ]
      },
      {
        id: '49083e4d-67c2-496b-bb4d-1cb0d530bbaa',
        idJson: '98e5b41a-72a9-47b1-8bed-adbd022ac65b',
        descricao: 'PADRÃO',
        posicao: 1,
        grupoSemaforico: {
          idJson: '5f53a090-d3e5-4b4d-bbde-db990caedaf2'
        },
        tabelaEntreVerdesTransicoes: [
          {
            idJson: '2ce28bbb-0c2f-4329-8a61-ff065d72314d'
          },
          {
            idJson: 'd5c0366b-7ee9-4a3c-b526-3af88c28de36'
          }
        ]
      },
      {
        id: 'd4ad932f-1042-4bd8-9781-359aa4ab30f5',
        idJson: '87a88607-18f2-435c-9ab0-1ac8b681d80d',
        descricao: 'PADRÃO',
        posicao: 1,
        grupoSemaforico: {
          idJson: 'cd5f89f1-1051-4075-82ca-b861b0843b5e'
        },
        tabelaEntreVerdesTransicoes: [
          {
            idJson: '4ae658c0-aaa9-44b7-b900-cc4708658459'
          },
          {
            idJson: 'f806980c-a12c-46ac-80a9-1affb44ab0ab'
          }
        ]
      },
      {
        id: '7245a536-876a-4107-973f-cc9721c1ebb8',
        idJson: '17e79046-ae5f-48d3-9fae-90ae0f8486d1',
        descricao: 'PADRÃO',
        posicao: 1,
        grupoSemaforico: {
          idJson: 'fe8dc72c-f0f8-40c3-84d5-26e3c7ee1cfb'
        },
        tabelaEntreVerdesTransicoes: [
          {
            idJson: 'fd7aba92-700a-41a8-a408-ee8faef6c47b'
          },
          {
            idJson: '86331ba1-64ee-4616-89c9-a393eb51f8b7'
          }
        ]
      },
      {
        id: '61733911-bba3-49c5-b83a-9a85ee35ed9c',
        idJson: 'c84298c8-15d3-4169-8002-8b663d47d001',
        descricao: 'PADRÃO',
        posicao: 1,
        grupoSemaforico: {
          idJson: '75ae793c-d90c-4279-844f-53dc2ba788d2'
        },
        tabelaEntreVerdesTransicoes: [
          {
            idJson: 'eb298e84-ef28-404f-89a0-e73fd7400c17'
          },
          {
            idJson: '95da9473-7a07-4221-871f-5cbfc91ad97e'
          }
        ]
      },
      {
        id: 'c64dcfb8-c75a-4e7a-ba67-cc55e1e000ca',
        idJson: 'c3123af0-d474-4105-84f8-31b20bd218e5',
        descricao: 'PADRÃO',
        posicao: 1,
        grupoSemaforico: {
          idJson: 'daed6666-f188-4464-b9cf-3fea37345621'
        },
        tabelaEntreVerdesTransicoes: [
          {
            idJson: '2e9ac65a-8825-4eec-bef4-933495d8833f'
          },
          {
            idJson: '2e819d55-5e41-4c71-a433-00dddf083f2a'
          }
        ]
      }
    ],
    tabelasEntreVerdesTransicoes: [
      {
        id: '0010884e-d9c3-40ab-8291-4a7b5f0b46ad',
        idJson: 'afe05718-378b-4a9c-abac-3684e68eba6e',
        tempoVermelhoIntermitente: '3',
        tempoVermelhoLimpeza: '3',
        tempoAtrasoGrupo: '0',
        tabelaEntreVerdes: {
          idJson: '809fa84a-c105-4972-ad98-62786b2421d1'
        },
        transicao: {
          idJson: '3487eb06-7d87-4daf-9e09-737e8501fc49'
        }
      },
      {
        id: '83ed3af9-9f14-44f2-bd8f-6c9856a62788',
        idJson: '95da9473-7a07-4221-871f-5cbfc91ad97e',
        tempoVermelhoIntermitente: '7',
        tempoVermelhoLimpeza: '3',
        tempoAtrasoGrupo: '0',
        tabelaEntreVerdes: {
          idJson: 'c84298c8-15d3-4169-8002-8b663d47d001'
        },
        transicao: {
          idJson: 'e2ef5bb5-869f-41a1-a132-a2e465477e48'
        }
      },
      {
        id: '0925c520-468f-4847-83d0-65f3b20e1850',
        idJson: '363acc7d-36ab-4936-9c1c-eed7cc39bdc6',
        tempoAmarelo: '3',
        tempoVermelhoLimpeza: '3',
        tempoAtrasoGrupo: '4',
        tabelaEntreVerdes: {
          idJson: '55095895-4d0a-4e4e-9d1a-e373cf72537c'
        },
        transicao: {
          idJson: '9fe5181d-56fe-4a02-8244-42413e6c172b'
        }
      },
      {
        id: '137ed1fa-44c6-49cd-b8dc-5772e4245869',
        idJson: 'fd7aba92-700a-41a8-a408-ee8faef6c47b',
        tempoVermelhoIntermitente: '7',
        tempoVermelhoLimpeza: '3',
        tempoAtrasoGrupo: '0',
        tabelaEntreVerdes: {
          idJson: '17e79046-ae5f-48d3-9fae-90ae0f8486d1'
        },
        transicao: {
          idJson: '11227efe-8863-4cbd-918b-f009fa6ad2dd'
        }
      },
      {
        id: 'c770e121-9fd0-4712-b76d-3d8cd467b677',
        idJson: '2138fdd9-8df5-45fe-9425-6927de97885b',
        tempoAmarelo: '3',
        tempoVermelhoLimpeza: '3',
        tempoAtrasoGrupo: '0',
        tabelaEntreVerdes: {
          idJson: '7de29827-acbc-4189-90f8-11d4abd8820b'
        },
        transicao: {
          idJson: '3a4a3f35-6ae4-4c13-9f6e-ab9eef59215c'
        }
      },
      {
        id: '5d1d12b1-d774-4369-bd01-616baba1b5c3',
        idJson: '86331ba1-64ee-4616-89c9-a393eb51f8b7',
        tempoVermelhoIntermitente: '7',
        tempoVermelhoLimpeza: '3',
        tempoAtrasoGrupo: '0',
        tabelaEntreVerdes: {
          idJson: '17e79046-ae5f-48d3-9fae-90ae0f8486d1'
        },
        transicao: {
          idJson: '6501fd5d-45d3-4014-ad01-4e46a2f4fc23'
        }
      },
      {
        id: '7f2db261-299c-4d47-b0c5-25e49b4598f8',
        idJson: 'de7dd182-1222-4917-8fb7-4e154314cc79',
        tempoAmarelo: '3',
        tempoVermelhoLimpeza: '3',
        tempoAtrasoGrupo: '0',
        tabelaEntreVerdes: {
          idJson: '55095895-4d0a-4e4e-9d1a-e373cf72537c'
        },
        transicao: {
          idJson: '13c047e4-ca41-484e-b43c-feca3586a3f1'
        }
      },
      {
        id: 'd84803c0-0f7f-4fa6-a531-a6fdd629e7ac',
        idJson: '769505a7-9694-4b78-b0c7-d0203b03ec82',
        tempoVermelhoIntermitente: '3',
        tempoVermelhoLimpeza: '3',
        tempoAtrasoGrupo: '0',
        tabelaEntreVerdes: {
          idJson: '809fa84a-c105-4972-ad98-62786b2421d1'
        },
        transicao: {
          idJson: '3ea731af-9877-4991-8ebd-f87132519160'
        }
      },
      {
        id: '3e0d47b0-abfa-499d-822a-0911fd8744ad',
        idJson: '53624044-804d-4cfd-8074-b67708486bd4',
        tempoAmarelo: '3',
        tempoVermelhoLimpeza: '3',
        tempoAtrasoGrupo: '0',
        tabelaEntreVerdes: {
          idJson: '89e25e70-82ae-451c-ae0d-dd590bf1aaa9'
        },
        transicao: {
          idJson: 'e654ab7e-a112-442e-927c-14a37d9b4f90'
        }
      },
      {
        id: '7dd80bd7-1288-4045-b7ad-c688e88145fb',
        idJson: '4916da4c-b7e7-4653-a892-ab57c2c32188',
        tempoAmarelo: '3',
        tempoVermelhoLimpeza: '3',
        tempoAtrasoGrupo: '0',
        tabelaEntreVerdes: {
          idJson: 'da677c07-d773-4622-9fb7-efb587c6eb75'
        },
        transicao: {
          idJson: 'aeaa3854-9ecf-4608-a4fb-06db1bde16c7'
        }
      },
      {
        id: '445a6758-02b6-4d6e-a504-38592138a756',
        idJson: '4ae658c0-aaa9-44b7-b900-cc4708658459',
        tempoVermelhoIntermitente: '3',
        tempoVermelhoLimpeza: '3',
        tempoAtrasoGrupo: '0',
        tabelaEntreVerdes: {
          idJson: '87a88607-18f2-435c-9ab0-1ac8b681d80d'
        },
        transicao: {
          idJson: '6a6d5690-2a5f-44c7-ac26-438c4e7ac002'
        }
      },
      {
        id: '81d417cb-aa1a-463d-8fa5-f71218580cd9',
        idJson: 'eb298e84-ef28-404f-89a0-e73fd7400c17',
        tempoVermelhoIntermitente: '7',
        tempoVermelhoLimpeza: '3',
        tempoAtrasoGrupo: '0',
        tabelaEntreVerdes: {
          idJson: 'c84298c8-15d3-4169-8002-8b663d47d001'
        },
        transicao: {
          idJson: 'c55a1cba-41c6-40a2-acdf-fa10c6627183'
        }
      },
      {
        id: '09543ba8-43b9-4a8c-af42-70bce77fb51e',
        idJson: '421af560-8567-4399-9b80-15b90de88b99',
        tempoAmarelo: '3',
        tempoVermelhoLimpeza: '3',
        tempoAtrasoGrupo: '4',
        tabelaEntreVerdes: {
          idJson: '89e25e70-82ae-451c-ae0d-dd590bf1aaa9'
        },
        transicao: {
          idJson: '6677a05e-b542-4c84-bc7e-eaeb3e323f87'
        }
      },
      {
        id: '32017f45-c898-4cbb-b5f2-21237949a4b7',
        idJson: 'ca7acd48-23c1-4187-b545-33f049f6e36f',
        tempoAmarelo: '3',
        tempoVermelhoLimpeza: '3',
        tempoAtrasoGrupo: '0',
        tabelaEntreVerdes: {
          idJson: 'da677c07-d773-4622-9fb7-efb587c6eb75'
        },
        transicao: {
          idJson: 'd31f7c7f-2874-4b96-93ed-583fca7753c4'
        }
      },
      {
        id: '24a46818-8fda-4059-93ac-a0bee6b3960c',
        idJson: '2ce28bbb-0c2f-4329-8a61-ff065d72314d',
        tempoAmarelo: '3',
        tempoVermelhoLimpeza: '3',
        tempoAtrasoGrupo: '0',
        tabelaEntreVerdes: {
          idJson: '98e5b41a-72a9-47b1-8bed-adbd022ac65b'
        },
        transicao: {
          idJson: '752b21a9-178e-45d6-b6bd-245d3a69f692'
        }
      },
      {
        id: '88fee633-1224-4d59-8e55-727eb771f5c9',
        idJson: 'f806980c-a12c-46ac-80a9-1affb44ab0ab',
        tempoVermelhoIntermitente: '3',
        tempoVermelhoLimpeza: '3',
        tempoAtrasoGrupo: '0',
        tabelaEntreVerdes: {
          idJson: '87a88607-18f2-435c-9ab0-1ac8b681d80d'
        },
        transicao: {
          idJson: '63c127d8-e28e-45de-abc2-1ad3aab12810'
        }
      },
      {
        id: 'f179896d-b592-4843-9a2e-2ac377a93260',
        idJson: 'd5c0366b-7ee9-4a3c-b526-3af88c28de36',
        tempoAmarelo: '3',
        tempoVermelhoLimpeza: '3',
        tempoAtrasoGrupo: '0',
        tabelaEntreVerdes: {
          idJson: '98e5b41a-72a9-47b1-8bed-adbd022ac65b'
        },
        transicao: {
          idJson: '02f269da-c6bd-4be3-b142-bfe14c8a2ac6'
        }
      },
      {
        id: '9287a13d-6665-476e-a34f-bfe3050744fa',
        idJson: 'f0b88dd9-cbe1-4e3e-853d-9aabc752afb4',
        tempoAmarelo: '3',
        tempoVermelhoLimpeza: '3',
        tempoAtrasoGrupo: '0',
        tabelaEntreVerdes: {
          idJson: '7de29827-acbc-4189-90f8-11d4abd8820b'
        },
        transicao: {
          idJson: '659d3e75-61ce-4ae6-80ec-0b2d9a3894f6'
        }
      },
      {
        id: 'bb272133-c816-4a8e-9e00-2556ad790e84',
        idJson: '2e819d55-5e41-4c71-a433-00dddf083f2a',
        tempoVermelhoIntermitente: '7',
        tempoVermelhoLimpeza: '3',
        tempoAtrasoGrupo: '0',
        tabelaEntreVerdes: {
          idJson: 'c3123af0-d474-4105-84f8-31b20bd218e5'
        },
        transicao: {
          idJson: '96a1034e-5636-430a-af93-fa60f43b3c3a'
        }
      },
      {
        id: '77974cbe-5128-430b-ae8b-95eaf2b434e3',
        idJson: '2e9ac65a-8825-4eec-bef4-933495d8833f',
        tempoVermelhoIntermitente: '7',
        tempoVermelhoLimpeza: '3',
        tempoAtrasoGrupo: '0',
        tabelaEntreVerdes: {
          idJson: 'c3123af0-d474-4105-84f8-31b20bd218e5'
        },
        transicao: {
          idJson: 'e66fe9e4-58f0-44a9-b608-5623fe880343'
        }
      }
    ],
    planos: [
      {
        id: 'cc6b3766-642f-48bf-ade7-5e6dbc6d3b30',
        idJson: 'd7ff9f27-1132-476d-a622-744038650be9',
        posicao: 1,
        descricao: 'PLANO 1',
        tempoCiclo: 120,
        defasagem: 0,
        posicaoTabelaEntreVerde: 1,
        modoOperacao: 'TEMPO_FIXO_ISOLADO',
        dataCriacao: '08/11/2016 17:57:14',
        dataAtualizacao: '08/11/2016 17:57:14',
        anel: {
          idJson: '7ff51a82-b32d-4288-afc1-a6a9eb85d78a'
        },
        versaoPlano: {
          idJson: '7c31270a-3025-49d2-936d-87e08e21fcc3'
        },
        estagiosPlanos: [
          {
            idJson: 'a9e770d0-d3b5-4236-82af-c675dbf7533a'
          },
          {
            idJson: 'e74603b4-c9cb-4566-a3f4-400a765a1fad'
          },
          {
            idJson: '1fd9be5d-ca7d-435d-a922-5d9bb7890165'
          }
        ],
        gruposSemaforicosPlanos: [
          {
            idJson: 'e9d25c4c-7e2b-4f2c-b871-724c6912f52c'
          },
          {
            idJson: '172fefaf-6fb7-471e-b180-be3fc5beda75'
          },
          {
            idJson: '579d5df8-dd13-47ca-8ce5-12bec3121c2e'
          },
          {
            idJson: '495face4-03c8-4bed-98b9-cbe18ed10967'
          },
          {
            idJson: '09b5b379-c842-444d-8671-575858445bdb'
          }
        ],
        configurado: true
      },
      {
        id: '25186ac8-6207-41db-bc8d-88e7abc0fc9a',
        idJson: 'b5283d44-2b17-4fcb-8277-336fb250a7d1',
        posicao: 0,
        descricao: 'Exclusivo',
        tempoCiclo: 30,
        defasagem: 0,
        posicaoTabelaEntreVerde: 1,
        modoOperacao: 'MANUAL',
        dataCriacao: '08/11/2016 17:57:14',
        dataAtualizacao: '08/11/2016 17:57:14',
        anel: {
          idJson: '9f7ac702-9979-48c7-8007-bd97a867a41a'
        },
        versaoPlano: {
          idJson: '2f11b501-7a1c-4e66-a88a-d5ea61e3c10e'
        },
        estagiosPlanos: [
          {
            idJson: '829e583c-6205-49d2-9fe3-acb4e1292198'
          },
          {
            idJson: '5698a069-3670-4aad-a5f9-dfed85c9056c'
          },
          {
            idJson: 'b589c696-1b6a-4e88-8545-aaf56e32a17e'
          }
        ],
        gruposSemaforicosPlanos: [
          {
            idJson: '97918690-e4e7-4723-8ada-33e052bcbd8d'
          },
          {
            idJson: 'adbba1e2-779b-47ae-ba1d-a27beb32c679'
          },
          {
            idJson: 'db4b7bf8-1bec-4b5b-9346-dd41050ee538'
          },
          {
            idJson: 'e9a5083e-f63c-4419-9782-6168bf375377'
          },
          {
            idJson: '2a547ca2-aa1f-46fa-a542-b22cbd033edb'
          }
        ],
        configurado: true,
        manualExclusivo: true
      },
      {
        id: '15ce6a4b-e879-48f9-9427-50e1faa861b3',
        idJson: 'f6c3f670-b93e-4528-91c8-86709ae600cb',
        posicao: 0,
        descricao: 'Exclusivo',
        tempoCiclo: 30,
        defasagem: 0,
        posicaoTabelaEntreVerde: 1,
        modoOperacao: 'MANUAL',
        dataCriacao: '08/11/2016 17:57:14',
        dataAtualizacao: '08/11/2016 17:57:14',
        anel: {
          idJson: '7ff51a82-b32d-4288-afc1-a6a9eb85d78a'
        },
        versaoPlano: {
          idJson: '7c31270a-3025-49d2-936d-87e08e21fcc3'
        },
        estagiosPlanos: [
          {
            idJson: '90e6805a-1d1c-49de-977e-2cc83fa8e803'
          },
          {
            idJson: 'afa02227-9d2f-4f76-9cc7-69ef8e2c7957'
          },
          {
            idJson: 'b8679294-3ad2-4c20-bc3e-16c7eff7cf45'
          }
        ],
        gruposSemaforicosPlanos: [
          {
            idJson: '589ca023-bef9-4541-bf66-91342e839562'
          },
          {
            idJson: '6ae7ece0-add1-44f5-b0d3-ce4dcc241e2f'
          },
          {
            idJson: 'ce5025f5-d937-4102-971b-801a34de9197'
          },
          {
            idJson: '20e96106-4a74-4fb4-8a0d-51d1b2409382'
          },
          {
            idJson: '45a9929b-5326-47ea-896a-e8de6743bcc7'
          }
        ],
        configurado: true,
        manualExclusivo: true
      },
      {
        id: 'c9077d45-59f5-411c-8626-dccb2c8f65e0',
        idJson: 'fbed2d5c-8df6-46d7-8b3a-03b4d9d75a9e',
        posicao: 1,
        descricao: 'PLANO 1',
        tempoCiclo: 120,
        defasagem: 0,
        posicaoTabelaEntreVerde: 1,
        modoOperacao: 'TEMPO_FIXO_ISOLADO',
        dataCriacao: '08/11/2016 17:57:14',
        dataAtualizacao: '08/11/2016 17:57:14',
        anel: {
          idJson: '9f7ac702-9979-48c7-8007-bd97a867a41a'
        },
        versaoPlano: {
          idJson: '2f11b501-7a1c-4e66-a88a-d5ea61e3c10e'
        },
        estagiosPlanos: [
          {
            idJson: '7f930463-063c-40ea-b734-425ac45f771f'
          },
          {
            idJson: 'd3876d77-7def-4f00-bc7e-82b8182a722f'
          }
        ],
        gruposSemaforicosPlanos: [
          {
            idJson: 'dcf96665-f6af-44cc-8db6-681504ba0c25'
          },
          {
            idJson: '2a87d2dd-3b49-4c9b-baa3-a08e1f7dcf98'
          },
          {
            idJson: '8e346781-febf-4180-a288-fd6ad8145c9a'
          },
          {
            idJson: 'd86d0a99-ff86-4cf0-9899-fa75f4239ce9'
          },
          {
            idJson: '7399657c-d333-4ea9-9b84-84db93f2db91'
          }
        ],
        configurado: true
      },
      {
        id: 'fd4225d7-ab0b-48d4-b6fb-ecd48001f88d',
        idJson: '68d09bed-8168-49d5-ac93-c05c4a4c6db6',
        posicao: 2,
        descricao: 'PLANO 2',
        tempoCiclo: 120,
        defasagem: 10,
        posicaoTabelaEntreVerde: 1,
        modoOperacao: 'TEMPO_FIXO_COORDENADO',
        dataCriacao: '08/11/2016 17:57:14',
        dataAtualizacao: '08/11/2016 17:57:14',
        anel: {
          idJson: '9f7ac702-9979-48c7-8007-bd97a867a41a'
        },
        versaoPlano: {
          idJson: '2f11b501-7a1c-4e66-a88a-d5ea61e3c10e'
        },
        estagiosPlanos: [
          {
            idJson: '1b7fc601-771e-426a-be7e-6cc935d7fd85'
          },
          {
            idJson: '83f5a7ee-a0b8-45fb-a796-1ec5c081b438'
          }
        ],
        gruposSemaforicosPlanos: [
          {
            idJson: '03b61267-1325-4b96-a111-0bea4c1af80f'
          },
          {
            idJson: '2483c3d6-23e6-406e-a4ea-789fa83cadee'
          },
          {
            idJson: '6e34943d-780a-4b9e-93c1-f9e20f0c32f8'
          },
          {
            idJson: '0aa7c89b-8b77-4a02-920f-b0a742662871'
          },
          {
            idJson: '8d69fcae-07c0-426d-9820-4b097d5e59d6'
          }
        ],
        configurado: true
      },
      {
        id: 'df3be6e1-1437-406b-9df3-97db4bfd800e',
        idJson: 'd9d40a04-0d63-4927-88a9-f3b247f628a0',
        posicao: 2,
        descricao: 'PLANO 2',
        tempoCiclo: 130,
        defasagem: 0,
        posicaoTabelaEntreVerde: 1,
        modoOperacao: 'TEMPO_FIXO_COORDENADO',
        dataCriacao: '08/11/2016 17:57:14',
        dataAtualizacao: '08/11/2016 17:57:14',
        anel: {
          idJson: '7ff51a82-b32d-4288-afc1-a6a9eb85d78a'
        },
        versaoPlano: {
          idJson: '7c31270a-3025-49d2-936d-87e08e21fcc3'
        },
        estagiosPlanos: [
          {
            idJson: 'bb245c87-9ff3-4d01-9dc1-fd8d66d14a7d'
          },
          {
            idJson: '4d294d21-7b05-409c-8539-5964d9eae43f'
          },
          {
            idJson: '76727d75-dff7-4c21-b781-7b137b4d8328'
          },
          {
            idJson: '07d75fdf-4afd-429e-ae84-39f351422171'
          }
        ],
        gruposSemaforicosPlanos: [
          {
            idJson: 'a023bd05-fa07-47af-ae58-e47ea350738b'
          },
          {
            idJson: '46dcdc2f-c413-4e80-8c4b-f9e505cf4f5e'
          },
          {
            idJson: 'b1789a3b-d400-4aca-8231-ee2cabd5eeea'
          },
          {
            idJson: 'ddf87f28-36a2-4040-a462-36238faf7338'
          },
          {
            idJson: 'a7727591-72b1-46c8-a3d0-57ec81144238'
          }
        ],
        configurado: true
      },
      {
        idJson: '7d491161-bb12-4673-b8e9-324f88f629cd',
        anel: {
          idJson: '7ff51a82-b32d-4288-afc1-a6a9eb85d78a'
        },
        descricao: 'PLANO 3',
        posicao: 3,
        modoOperacao: 'TEMPO_FIXO_COORDENADO',
        posicaoTabelaEntreVerde: 1,
        gruposSemaforicosPlanos: [
          {
            idJson: '4e321a5d-59cf-4c11-a630-7ba619be3119'
          },
          {
            idJson: '18cc105c-9b9d-4f85-856a-a9701ab5fae7'
          },
          {
            idJson: 'ee0100bb-62b3-40c6-a4f2-c58b323300a2'
          },
          {
            idJson: 'f5d42e91-c764-4082-9c61-04508b50de1d'
          },
          {
            idJson: 'b4d8567b-362f-4e2b-ac13-c747ff8b111b'
          }
        ],
        estagiosPlanos: [
          {
            idJson: 'db0df932-27a6-4cff-80e3-f9b5d8e2dee8'
          },
          {
            idJson: '292b008a-dd16-4984-877c-b54e035c9db9'
          },
          {
            idJson: 'b7929edc-faf6-4100-9727-a22fe6b18f3d'
          }
        ],
        tempoCiclo: 30,
        configurado: false,
        versaoPlano: {
          idJson: '7c31270a-3025-49d2-936d-87e08e21fcc3'
        }
      },
      {
        idJson: '2ba66e27-b6e4-4571-b2e2-0bf77be547b1',
        anel: {
          idJson: '7ff51a82-b32d-4288-afc1-a6a9eb85d78a'
        },
        descricao: 'PLANO 4',
        posicao: 4,
        modoOperacao: 'TEMPO_FIXO_COORDENADO',
        posicaoTabelaEntreVerde: 1,
        gruposSemaforicosPlanos: [
          {
            idJson: 'eb8e1eb6-cfba-427a-8cd1-ade0091981eb'
          },
          {
            idJson: 'a400712a-0d02-4229-9249-db5be4728d37'
          },
          {
            idJson: '912e5f06-d305-4a7f-9522-23c33ace849f'
          },
          {
            idJson: 'e9b176e9-4c59-42e2-aa88-23d7b3d380e9'
          },
          {
            idJson: 'abfca52f-ca93-466c-a5df-472e202d9aa8'
          }
        ],
        estagiosPlanos: [
          {
            idJson: 'a22e9261-c5c9-438d-adc4-2042344c8633'
          },
          {
            idJson: '0235c784-695b-4a5b-91aa-b7f34ac63ab8'
          },
          {
            idJson: 'c295a5c0-c271-4dd9-a684-b07ee681bb76'
          }
        ],
        tempoCiclo: 30,
        configurado: false,
        versaoPlano: {
          idJson: '7c31270a-3025-49d2-936d-87e08e21fcc3'
        }
      },
      {
        idJson: '9c0bae10-1b9f-422c-a063-aa0c305b25d0',
        anel: {
          idJson: '7ff51a82-b32d-4288-afc1-a6a9eb85d78a'
        },
        descricao: 'PLANO 5',
        posicao: 5,
        modoOperacao: 'TEMPO_FIXO_COORDENADO',
        posicaoTabelaEntreVerde: 1,
        gruposSemaforicosPlanos: [
          {
            idJson: '878952db-f1ed-4d25-93d8-01c1e931ffd9'
          },
          {
            idJson: '82583925-6763-4ff1-b968-e18c3ea3c404'
          },
          {
            idJson: 'b4d2fc8b-2226-41fc-a81f-d6bc4d33d2a6'
          },
          {
            idJson: '9b60e219-3567-4d07-952b-7c3266941c75'
          },
          {
            idJson: '240d1fa8-1cbb-456c-80d7-586d8e96c2b8'
          }
        ],
        estagiosPlanos: [
          {
            idJson: '1d7dad29-6124-4c3f-b704-da64ff9687ee'
          },
          {
            idJson: 'd2988fe0-5499-4946-8b5d-5104fde769c5'
          },
          {
            idJson: '7f07e849-bc02-4671-a49e-eae543f30bde'
          }
        ],
        tempoCiclo: 30,
        configurado: false,
        versaoPlano: {
          idJson: '7c31270a-3025-49d2-936d-87e08e21fcc3'
        }
      },
      {
        idJson: '233c11dc-b927-4df2-a4b8-7e2875061970',
        anel: {
          idJson: '7ff51a82-b32d-4288-afc1-a6a9eb85d78a'
        },
        descricao: 'PLANO 6',
        posicao: 6,
        modoOperacao: 'TEMPO_FIXO_COORDENADO',
        posicaoTabelaEntreVerde: 1,
        gruposSemaforicosPlanos: [
          {
            idJson: 'd58d0a28-4bd4-4589-b05d-3340b5ad8946'
          },
          {
            idJson: '0bd391d7-474d-424d-894c-c88e95c4ab7d'
          },
          {
            idJson: 'ca6d57ee-2154-4a94-8bcc-fe12dff3f284'
          },
          {
            idJson: 'c20489b9-4dc4-4c4c-99a4-7b28a8082b01'
          },
          {
            idJson: '8d91afd5-bbed-4980-88d2-f979ee876328'
          }
        ],
        estagiosPlanos: [
          {
            idJson: '1335a376-ac2c-4cb5-a7b7-74446886f33d'
          },
          {
            idJson: '06d69eda-5ade-475b-994b-183be4f25a15'
          },
          {
            idJson: 'e39396f6-186e-4d84-b107-2f1ab4976e8d'
          }
        ],
        tempoCiclo: 30,
        configurado: false,
        versaoPlano: {
          idJson: '7c31270a-3025-49d2-936d-87e08e21fcc3'
        }
      },
      {
        idJson: 'c8b26060-acd7-44c3-8e92-cbdb316d6deb',
        anel: {
          idJson: '7ff51a82-b32d-4288-afc1-a6a9eb85d78a'
        },
        descricao: 'PLANO 7',
        posicao: 7,
        modoOperacao: 'TEMPO_FIXO_COORDENADO',
        posicaoTabelaEntreVerde: 1,
        gruposSemaforicosPlanos: [
          {
            idJson: '64f830a6-c52a-4e9b-b20e-31277ce0dcfc'
          },
          {
            idJson: '7f5702a3-aa6e-48ae-b9c3-55fd9bdb9f0e'
          },
          {
            idJson: '29ba7f62-c382-44ad-a296-582c72907941'
          },
          {
            idJson: 'ef0fea97-bc27-40e5-abd0-7b0522b480fb'
          },
          {
            idJson: 'b4e24d8a-06f4-402c-b11b-290f10f46ebe'
          }
        ],
        estagiosPlanos: [
          {
            idJson: '48f69f7e-b6e3-4fd5-a7c5-89ed664de103'
          },
          {
            idJson: 'beab5567-bb11-4d97-86d9-017a042108fb'
          },
          {
            idJson: '91687aaa-3d1e-4974-b798-6118b1ebb140'
          }
        ],
        tempoCiclo: 30,
        configurado: false,
        versaoPlano: {
          idJson: '7c31270a-3025-49d2-936d-87e08e21fcc3'
        }
      },
      {
        idJson: 'a891c685-9285-4257-bed1-16f25f044c24',
        anel: {
          idJson: '7ff51a82-b32d-4288-afc1-a6a9eb85d78a'
        },
        descricao: 'PLANO 8',
        posicao: 8,
        modoOperacao: 'TEMPO_FIXO_COORDENADO',
        posicaoTabelaEntreVerde: 1,
        gruposSemaforicosPlanos: [
          {
            idJson: '95f1239b-1cf7-44b1-af26-10e517a2b9c8'
          },
          {
            idJson: '9c2824a0-f36c-4824-b3ec-f936ac85d13a'
          },
          {
            idJson: '52330ffe-d8fb-4268-b85d-063702507649'
          },
          {
            idJson: 'fe96befe-5841-4b88-a2ed-cdca3d66c9f2'
          },
          {
            idJson: 'bce2a806-1ff4-4c5e-bbd4-d08779ed181f'
          }
        ],
        estagiosPlanos: [
          {
            idJson: '8517b095-38f0-46b2-bd79-940d6fc9b49f'
          },
          {
            idJson: '61b690c9-8273-46d0-96c9-18ba45016293'
          },
          {
            idJson: '720a99a1-e280-440b-9d97-79ac3172a63b'
          }
        ],
        tempoCiclo: 30,
        configurado: false,
        versaoPlano: {
          idJson: '7c31270a-3025-49d2-936d-87e08e21fcc3'
        }
      },
      {
        idJson: '5270dfac-b24b-4ba0-9c79-0435c74301d5',
        anel: {
          idJson: '7ff51a82-b32d-4288-afc1-a6a9eb85d78a'
        },
        descricao: 'PLANO 9',
        posicao: 9,
        modoOperacao: 'TEMPO_FIXO_COORDENADO',
        posicaoTabelaEntreVerde: 1,
        gruposSemaforicosPlanos: [
          {
            idJson: 'a6f49620-2ce2-432d-b28d-cbcf2a8be1f7'
          },
          {
            idJson: '3abc4d5d-1bdb-4ded-9b89-ad0136a345ac'
          },
          {
            idJson: '9d1840ee-8426-4231-b2d2-84d877ef94db'
          },
          {
            idJson: '33342664-3b23-4b63-b272-57353d93ef92'
          },
          {
            idJson: '8a143ca3-b25d-4a80-b541-e92eeb8a0b43'
          }
        ],
        estagiosPlanos: [
          {
            idJson: '995cb4d3-7929-4846-88e7-affd76f16bc2'
          },
          {
            idJson: 'd77c12bb-0e79-4e10-bd64-1cc91d866e2a'
          },
          {
            idJson: '23e66c75-d5fb-44eb-b67d-bef58e66a1f3'
          }
        ],
        tempoCiclo: 30,
        configurado: false,
        versaoPlano: {
          idJson: '7c31270a-3025-49d2-936d-87e08e21fcc3'
        }
      },
      {
        idJson: '0ce21c68-24c6-4d17-81f2-88c581931326',
        anel: {
          idJson: '7ff51a82-b32d-4288-afc1-a6a9eb85d78a'
        },
        descricao: 'PLANO 10',
        posicao: 10,
        modoOperacao: 'TEMPO_FIXO_COORDENADO',
        posicaoTabelaEntreVerde: 1,
        gruposSemaforicosPlanos: [
          {
            idJson: 'f53ee0e7-c7b4-4485-a87b-c0050239e046'
          },
          {
            idJson: '3319b439-170e-4cee-941c-62e49e8cbed4'
          },
          {
            idJson: '3d3a60fd-4753-4b5f-82c6-5dc6334be93b'
          },
          {
            idJson: '06246c3e-1d99-428e-ad80-b73357be20e9'
          },
          {
            idJson: '194baa79-4984-4c51-b9f4-18db5b296e18'
          }
        ],
        estagiosPlanos: [
          {
            idJson: '46578167-43fc-4070-b372-9e7c19d83033'
          },
          {
            idJson: '1a73bc65-89a9-43b2-8f50-07ef6d0d3157'
          },
          {
            idJson: 'f97e5596-594b-4f78-9007-7d0077ce9db4'
          }
        ],
        tempoCiclo: 30,
        configurado: false,
        versaoPlano: {
          idJson: '7c31270a-3025-49d2-936d-87e08e21fcc3'
        }
      },
      {
        idJson: 'bda5c85c-63d5-4fe7-8f6d-7382f8b2152c',
        anel: {
          idJson: '7ff51a82-b32d-4288-afc1-a6a9eb85d78a'
        },
        descricao: 'PLANO 11',
        posicao: 11,
        modoOperacao: 'TEMPO_FIXO_COORDENADO',
        posicaoTabelaEntreVerde: 1,
        gruposSemaforicosPlanos: [
          {
            idJson: '7bc1f8af-badb-4119-85eb-3b12b2ce0aa5'
          },
          {
            idJson: '2cbb0622-a2e5-47d1-8746-6a9d6758697e'
          },
          {
            idJson: '2ab75c6e-1f67-4fbc-8749-ddf527e43c40'
          },
          {
            idJson: '5929e28a-d11e-453a-8899-14560427d1df'
          },
          {
            idJson: 'e2222f3f-bfaf-4b2e-ab7c-bd8710399a0d'
          }
        ],
        estagiosPlanos: [
          {
            idJson: '11285659-fa8b-43df-9ee4-0e0ccdd1fb04'
          },
          {
            idJson: 'f7ce25fc-bb0a-4362-b2f7-8f76ffdbc849'
          },
          {
            idJson: '4f53e516-6849-450b-84a3-98118df7bada'
          }
        ],
        tempoCiclo: 30,
        configurado: false,
        versaoPlano: {
          idJson: '7c31270a-3025-49d2-936d-87e08e21fcc3'
        }
      },
      {
        idJson: 'd8ac0504-ac63-44a2-a6b1-efb33f491da6',
        anel: {
          idJson: '7ff51a82-b32d-4288-afc1-a6a9eb85d78a'
        },
        descricao: 'PLANO 12',
        posicao: 12,
        modoOperacao: 'TEMPO_FIXO_COORDENADO',
        posicaoTabelaEntreVerde: 1,
        gruposSemaforicosPlanos: [
          {
            idJson: '45e02769-ac61-4cda-908c-c455781855c8'
          },
          {
            idJson: 'db76f944-a0f8-4ca1-a348-48cdf9fb2790'
          },
          {
            idJson: 'a733bc1c-18c0-463f-9ffb-184ef8b8da9b'
          },
          {
            idJson: 'cbcd133f-227b-4411-8d65-68e250ff471a'
          },
          {
            idJson: '60fd26cf-c1bd-4171-a79e-acc951476ee1'
          }
        ],
        estagiosPlanos: [
          {
            idJson: '7e83ce93-0772-49a9-a707-f68aea6f1d56'
          },
          {
            idJson: '267d7ea8-950e-4f49-b037-f899acf842e7'
          },
          {
            idJson: 'b59ded38-1340-4b82-83ba-37632ec6dfd0'
          }
        ],
        tempoCiclo: 30,
        configurado: false,
        versaoPlano: {
          idJson: '7c31270a-3025-49d2-936d-87e08e21fcc3'
        }
      },
      {
        idJson: '2819a411-92ab-4654-8ef2-001e10b9fb84',
        anel: {
          idJson: '7ff51a82-b32d-4288-afc1-a6a9eb85d78a'
        },
        descricao: 'PLANO 13',
        posicao: 13,
        modoOperacao: 'TEMPO_FIXO_COORDENADO',
        posicaoTabelaEntreVerde: 1,
        gruposSemaforicosPlanos: [
          {
            idJson: 'b098d080-8ca1-4ad4-98ef-45e4cf56b9bd'
          },
          {
            idJson: '388a55d4-af7d-4d99-966b-b24469683ed4'
          },
          {
            idJson: 'e4d8ea40-5800-4f54-b199-41c5aa1239ce'
          },
          {
            idJson: 'f9076a97-0efa-4336-b518-dd6b746945d7'
          },
          {
            idJson: '49df6153-3417-464b-b074-6d89d46da708'
          }
        ],
        estagiosPlanos: [
          {
            idJson: '433144f8-d63b-40a6-82cc-555e3533f6f0'
          },
          {
            idJson: 'c02ea1be-2467-4133-af8a-f830c3319b8b'
          },
          {
            idJson: '174a46c8-e710-4980-bb7c-b83f3345f5da'
          }
        ],
        tempoCiclo: 30,
        configurado: false,
        versaoPlano: {
          idJson: '7c31270a-3025-49d2-936d-87e08e21fcc3'
        }
      },
      {
        idJson: '14c59533-b7f5-4205-bed1-4e0e99bebd42',
        anel: {
          idJson: '7ff51a82-b32d-4288-afc1-a6a9eb85d78a'
        },
        descricao: 'PLANO 14',
        posicao: 14,
        modoOperacao: 'TEMPO_FIXO_COORDENADO',
        posicaoTabelaEntreVerde: 1,
        gruposSemaforicosPlanos: [
          {
            idJson: '308218cc-2e04-47e9-816d-ffdc64259a84'
          },
          {
            idJson: '9c0b1481-5c48-4ed0-ac6b-cae3954884f5'
          },
          {
            idJson: 'faf5c73b-39ca-4905-9175-a48c1ea8cee4'
          },
          {
            idJson: '867dafe6-d1f4-4e5e-bd34-9e8820989c3b'
          },
          {
            idJson: '883fc507-d634-4bfb-975a-a05d57bb6f24'
          }
        ],
        estagiosPlanos: [
          {
            idJson: 'd9a1c38d-e5ee-467f-8fd5-12e4cf6022ce'
          },
          {
            idJson: 'fe628791-13d2-4acd-a79d-9d7ea85083c8'
          },
          {
            idJson: 'ca29ee58-a843-462b-abd7-c9f1ba76d373'
          }
        ],
        tempoCiclo: 30,
        configurado: false,
        versaoPlano: {
          idJson: '7c31270a-3025-49d2-936d-87e08e21fcc3'
        }
      },
      {
        idJson: 'dd976fc4-da72-4dec-89c9-9aa7b15ebb42',
        anel: {
          idJson: '7ff51a82-b32d-4288-afc1-a6a9eb85d78a'
        },
        descricao: 'PLANO 15',
        posicao: 15,
        modoOperacao: 'TEMPO_FIXO_COORDENADO',
        posicaoTabelaEntreVerde: 1,
        gruposSemaforicosPlanos: [
          {
            idJson: '5cbbcedc-a596-4cae-ab64-9face3f4895d'
          },
          {
            idJson: '361ec118-236c-404a-b5e3-bbb0c462419f'
          },
          {
            idJson: '80c0809c-5bac-4f71-b3f0-a8e4b06e280b'
          },
          {
            idJson: '358139e6-22e6-4549-848f-fb4d15f94f41'
          },
          {
            idJson: 'd29531b1-8cb4-48a8-a103-b5d015da8edb'
          }
        ],
        estagiosPlanos: [
          {
            idJson: '2aa91706-d94f-4ffc-a168-5d3eed667de5'
          },
          {
            idJson: 'ad73d676-f4e4-47f9-840d-adccec6dbb70'
          },
          {
            idJson: 'c4727609-4efe-48ab-8882-5f642579abfd'
          }
        ],
        tempoCiclo: 30,
        configurado: false,
        versaoPlano: {
          idJson: '7c31270a-3025-49d2-936d-87e08e21fcc3'
        }
      },
      {
        idJson: '6f68ea04-988c-4d50-b439-3ad02905eff1',
        anel: {
          idJson: '7ff51a82-b32d-4288-afc1-a6a9eb85d78a'
        },
        descricao: 'PLANO 16',
        posicao: 16,
        modoOperacao: 'TEMPO_FIXO_COORDENADO',
        posicaoTabelaEntreVerde: 1,
        gruposSemaforicosPlanos: [
          {
            idJson: '9d0f868f-2724-4a9f-98f7-6efece98aac1'
          },
          {
            idJson: '10d14d07-e4d2-4141-aee1-0ed1433428cb'
          },
          {
            idJson: '21362abd-764a-4a61-8942-7b3cd0d48fe0'
          },
          {
            idJson: 'e675c5cd-9c7e-429f-b87d-1c6a781c0525'
          },
          {
            idJson: '7e35891f-340d-42d5-8adf-2840760e46ae'
          }
        ],
        estagiosPlanos: [
          {
            idJson: '2319e19e-9a61-4418-b306-08d5b25b79e2'
          },
          {
            idJson: '78981ba3-e4da-4234-9dc5-c32ba1778bfc'
          },
          {
            idJson: '5851baef-fbd1-4e66-abfc-a8570f031a76'
          }
        ],
        tempoCiclo: 30,
        configurado: false,
        versaoPlano: {
          idJson: '7c31270a-3025-49d2-936d-87e08e21fcc3'
        }
      },
      {
        idJson: '398aa468-075d-49fc-b940-36886add16a3',
        anel: {
          idJson: '9f7ac702-9979-48c7-8007-bd97a867a41a'
        },
        descricao: 'PLANO 3',
        posicao: 3,
        modoOperacao: 'TEMPO_FIXO_COORDENADO',
        posicaoTabelaEntreVerde: 1,
        gruposSemaforicosPlanos: [
          {
            idJson: '0518b8c4-2b98-4661-8352-e7b432248574'
          },
          {
            idJson: '5f0ff01b-4843-48e2-958f-f074c16c26e2'
          },
          {
            idJson: 'dc50a2b1-dfd0-4a32-a01c-72cba3f5dea6'
          },
          {
            idJson: '1184ed2b-461c-4bfd-b267-822f0695630b'
          },
          {
            idJson: 'c4945628-b31f-4196-b32e-bbefa52d4c6d'
          }
        ],
        estagiosPlanos: [
          {
            idJson: '55dbffa7-2657-4ccb-925d-f549805b87c3'
          },
          {
            idJson: '5c0d56cb-7cf2-4126-9dd8-4a90f72112ba'
          }
        ],
        tempoCiclo: 30,
        configurado: false,
        versaoPlano: {
          idJson: '2f11b501-7a1c-4e66-a88a-d5ea61e3c10e'
        }
      },
      {
        idJson: '0a64f4df-54fd-4097-963e-571d65e51123',
        anel: {
          idJson: '9f7ac702-9979-48c7-8007-bd97a867a41a'
        },
        descricao: 'PLANO 4',
        posicao: 4,
        modoOperacao: 'TEMPO_FIXO_COORDENADO',
        posicaoTabelaEntreVerde: 1,
        gruposSemaforicosPlanos: [
          {
            idJson: 'e58e226b-1877-49ab-bc12-b028eada39b3'
          },
          {
            idJson: '1d90fc77-9548-4f35-a7b6-f46daee13177'
          },
          {
            idJson: '9e178ddd-cbbf-4ddf-b53f-4106d443c0af'
          },
          {
            idJson: 'c42ce870-fdea-457b-88e4-bc3337c5bbf3'
          },
          {
            idJson: 'd0e4a64c-f7b9-450e-9c4e-431e73a30adb'
          }
        ],
        estagiosPlanos: [
          {
            idJson: '477adc6c-c434-4571-9c99-aa0910c95163'
          },
          {
            idJson: 'e510dad8-bf1d-4b3a-b6bc-bfda1ff174bb'
          }
        ],
        tempoCiclo: 30,
        configurado: false,
        versaoPlano: {
          idJson: '2f11b501-7a1c-4e66-a88a-d5ea61e3c10e'
        }
      },
      {
        idJson: 'f984986c-37f5-44bd-840a-dc94c8a0d850',
        anel: {
          idJson: '9f7ac702-9979-48c7-8007-bd97a867a41a'
        },
        descricao: 'PLANO 5',
        posicao: 5,
        modoOperacao: 'TEMPO_FIXO_COORDENADO',
        posicaoTabelaEntreVerde: 1,
        gruposSemaforicosPlanos: [
          {
            idJson: '81d77e1b-a837-4ba0-9dff-61d3396a9764'
          },
          {
            idJson: '84f43052-3f79-45a2-b323-c816c6855ddd'
          },
          {
            idJson: '027784af-d845-4482-9984-67bd7a4b8af6'
          },
          {
            idJson: '14d0278c-c526-4f56-87d6-90262043b9b2'
          },
          {
            idJson: '559e915c-c5c3-41d4-971b-b626e0fd0a3c'
          }
        ],
        estagiosPlanos: [
          {
            idJson: 'e13b024b-81d5-4dbd-9f6c-7d336b3b77f5'
          },
          {
            idJson: '9b1e0d32-5cf6-4470-a5fa-17dfe27b40db'
          }
        ],
        tempoCiclo: 30,
        configurado: false,
        versaoPlano: {
          idJson: '2f11b501-7a1c-4e66-a88a-d5ea61e3c10e'
        }
      },
      {
        idJson: '6780c087-42c3-42df-aa25-fad41b46f03b',
        anel: {
          idJson: '9f7ac702-9979-48c7-8007-bd97a867a41a'
        },
        descricao: 'PLANO 6',
        posicao: 6,
        modoOperacao: 'TEMPO_FIXO_COORDENADO',
        posicaoTabelaEntreVerde: 1,
        gruposSemaforicosPlanos: [
          {
            idJson: '098efdb6-ffae-4d05-932f-03059974076e'
          },
          {
            idJson: 'ba4e6b57-fffb-4a93-a58a-90032e84cad2'
          },
          {
            idJson: '70b123fa-49f9-4fdf-9e76-2fad98cce313'
          },
          {
            idJson: 'f41dd47f-c7e8-46e7-8341-2ae02b925108'
          },
          {
            idJson: 'e4cf9a93-08f6-4b76-95b7-1290c43c44a9'
          }
        ],
        estagiosPlanos: [
          {
            idJson: '8862d9f1-6934-468c-b23f-b67b9fe7c68e'
          },
          {
            idJson: '58629998-6d1e-41e7-8e5c-805a249a943e'
          }
        ],
        tempoCiclo: 30,
        configurado: false,
        versaoPlano: {
          idJson: '2f11b501-7a1c-4e66-a88a-d5ea61e3c10e'
        }
      },
      {
        idJson: '6781daa5-8106-4f87-b20d-027b2ee92907',
        anel: {
          idJson: '9f7ac702-9979-48c7-8007-bd97a867a41a'
        },
        descricao: 'PLANO 7',
        posicao: 7,
        modoOperacao: 'TEMPO_FIXO_COORDENADO',
        posicaoTabelaEntreVerde: 1,
        gruposSemaforicosPlanos: [
          {
            idJson: 'bdb0f4aa-74b8-47ef-9013-c475ac026b01'
          },
          {
            idJson: 'f8725d59-2d54-49ef-84f4-085037ee5739'
          },
          {
            idJson: '99116c5b-0c05-46fb-9fbe-c1f4291babb4'
          },
          {
            idJson: '8eeb09f1-9483-48b1-a22b-f93ab5ae962c'
          },
          {
            idJson: 'ef6d5930-5ad6-4634-b5d1-0812fe97da3f'
          }
        ],
        estagiosPlanos: [
          {
            idJson: '7d47bf74-37ba-4a82-92ab-261301df8094'
          },
          {
            idJson: '7e63b61d-9e80-434d-a5eb-d6ebb8e67140'
          }
        ],
        tempoCiclo: 30,
        configurado: false,
        versaoPlano: {
          idJson: '2f11b501-7a1c-4e66-a88a-d5ea61e3c10e'
        }
      },
      {
        idJson: '0b3062e0-3fad-4071-9a01-f01724c20a97',
        anel: {
          idJson: '9f7ac702-9979-48c7-8007-bd97a867a41a'
        },
        descricao: 'PLANO 8',
        posicao: 8,
        modoOperacao: 'TEMPO_FIXO_COORDENADO',
        posicaoTabelaEntreVerde: 1,
        gruposSemaforicosPlanos: [
          {
            idJson: '5de27311-ed0e-44a5-9332-e4aca7e5564e'
          },
          {
            idJson: '8413cae6-ee51-48d7-a6ee-6c236e0bd83a'
          },
          {
            idJson: 'e886e6ed-953a-4a1c-807e-f4a033d61a48'
          },
          {
            idJson: 'dd38ff4a-aa4f-4df2-949f-a20d951df670'
          },
          {
            idJson: '4d1d9eb2-031d-4540-a561-aa0c7484af44'
          }
        ],
        estagiosPlanos: [
          {
            idJson: '56b18b3e-cb01-4069-85ad-678daad85bcf'
          },
          {
            idJson: 'f42e5d6d-49fa-4279-aedb-89e8e1fe2207'
          }
        ],
        tempoCiclo: 30,
        configurado: false,
        versaoPlano: {
          idJson: '2f11b501-7a1c-4e66-a88a-d5ea61e3c10e'
        }
      },
      {
        idJson: '8495d409-d4f1-4e43-bb06-045c2a56c0cf',
        anel: {
          idJson: '9f7ac702-9979-48c7-8007-bd97a867a41a'
        },
        descricao: 'PLANO 9',
        posicao: 9,
        modoOperacao: 'TEMPO_FIXO_COORDENADO',
        posicaoTabelaEntreVerde: 1,
        gruposSemaforicosPlanos: [
          {
            idJson: '1ddb77fc-ab77-47ef-b3fe-be8b2d5b558e'
          },
          {
            idJson: '82223081-c521-4b31-9406-65f6e393af95'
          },
          {
            idJson: 'ac1d302b-52c9-4921-966c-cd65dbeb8714'
          },
          {
            idJson: '9ce7c204-2d2f-4fa6-beac-860b3335d414'
          },
          {
            idJson: '343258f7-327d-400c-98bd-02b052e52c13'
          }
        ],
        estagiosPlanos: [
          {
            idJson: '8b9fb143-c758-497c-827f-b0e4145e418d'
          },
          {
            idJson: '814bed90-5d7a-40bb-83eb-7ba34b9f946b'
          }
        ],
        tempoCiclo: 30,
        configurado: false,
        versaoPlano: {
          idJson: '2f11b501-7a1c-4e66-a88a-d5ea61e3c10e'
        }
      },
      {
        idJson: 'b52d3443-2b72-4a32-8cc2-7d74e39229ff',
        anel: {
          idJson: '9f7ac702-9979-48c7-8007-bd97a867a41a'
        },
        descricao: 'PLANO 10',
        posicao: 10,
        modoOperacao: 'TEMPO_FIXO_COORDENADO',
        posicaoTabelaEntreVerde: 1,
        gruposSemaforicosPlanos: [
          {
            idJson: '17a195c5-3ad0-4cf8-8487-0f092f592abe'
          },
          {
            idJson: 'a8472429-60f9-4597-beeb-0054d5987626'
          },
          {
            idJson: 'd8f95923-9b23-415a-97c6-e749d5126656'
          },
          {
            idJson: '172ce5a9-8a96-4a8e-9259-bdef95dca85a'
          },
          {
            idJson: '27f00621-2ec1-427e-b712-8d30c9a6443b'
          }
        ],
        estagiosPlanos: [
          {
            idJson: '914b960d-dce1-4c9d-a1ca-032ee8c0e38f'
          },
          {
            idJson: '6f41104e-7945-4050-b373-88a13d1608e4'
          }
        ],
        tempoCiclo: 30,
        configurado: false,
        versaoPlano: {
          idJson: '2f11b501-7a1c-4e66-a88a-d5ea61e3c10e'
        }
      },
      {
        idJson: '16d44222-79bd-4983-81d8-ee68934a2492',
        anel: {
          idJson: '9f7ac702-9979-48c7-8007-bd97a867a41a'
        },
        descricao: 'PLANO 11',
        posicao: 11,
        modoOperacao: 'TEMPO_FIXO_COORDENADO',
        posicaoTabelaEntreVerde: 1,
        gruposSemaforicosPlanos: [
          {
            idJson: '55fe58d5-6f88-4a2a-8a5a-c8f6f6906cd6'
          },
          {
            idJson: '8654c573-80b0-4cf1-8ae5-551e97d95a00'
          },
          {
            idJson: '91109c4a-1e4b-43fd-a286-fc314b4d3585'
          },
          {
            idJson: '343eec80-71d5-40c8-b505-28e96281f9dd'
          },
          {
            idJson: '20e687f4-9d57-45ed-8b5b-a02992edeb3d'
          }
        ],
        estagiosPlanos: [
          {
            idJson: '01320d08-19b4-49c9-8b0a-444a8ef9c937'
          },
          {
            idJson: '3a326e8d-642b-4386-ac65-ee23e6b4d5dd'
          }
        ],
        tempoCiclo: 30,
        configurado: false,
        versaoPlano: {
          idJson: '2f11b501-7a1c-4e66-a88a-d5ea61e3c10e'
        }
      },
      {
        idJson: '998c7003-2267-426d-96d9-4de0518c8796',
        anel: {
          idJson: '9f7ac702-9979-48c7-8007-bd97a867a41a'
        },
        descricao: 'PLANO 12',
        posicao: 12,
        modoOperacao: 'TEMPO_FIXO_COORDENADO',
        posicaoTabelaEntreVerde: 1,
        gruposSemaforicosPlanos: [
          {
            idJson: 'd41e0017-e968-4e7e-93df-678c671e87e4'
          },
          {
            idJson: '1415bf45-3a33-4f0f-8fde-ed29ef289e4a'
          },
          {
            idJson: '27e19638-0ea0-46ad-888b-f8081a36ef48'
          },
          {
            idJson: 'ac5dfc56-f2fa-440e-aded-94470d10775e'
          },
          {
            idJson: 'ce977361-f065-4d45-b84c-fe66b3c74d97'
          }
        ],
        estagiosPlanos: [
          {
            idJson: 'e5475b4d-afc5-47cf-817a-ddf896f3c409'
          },
          {
            idJson: '94943f59-b4be-4a8a-aa0b-3bd9f2327620'
          }
        ],
        tempoCiclo: 30,
        configurado: false,
        versaoPlano: {
          idJson: '2f11b501-7a1c-4e66-a88a-d5ea61e3c10e'
        }
      },
      {
        idJson: '2cf6d09f-e785-4ebd-af17-04e7ce5c72d3',
        anel: {
          idJson: '9f7ac702-9979-48c7-8007-bd97a867a41a'
        },
        descricao: 'PLANO 13',
        posicao: 13,
        modoOperacao: 'TEMPO_FIXO_COORDENADO',
        posicaoTabelaEntreVerde: 1,
        gruposSemaforicosPlanos: [
          {
            idJson: '25e9ffa0-56a0-4f29-b1be-ffc435493d7f'
          },
          {
            idJson: '8236888f-7972-40d4-9c9f-2b7e89d6f61c'
          },
          {
            idJson: '14904e05-1fe0-48af-9e89-b582ab1f0b1f'
          },
          {
            idJson: '14dff6d6-a8f8-4cd9-86b4-f6c718568096'
          },
          {
            idJson: '2db8646c-ee21-4e62-ac00-058b68828fe5'
          }
        ],
        estagiosPlanos: [
          {
            idJson: 'ccc5c599-9bd8-474e-bd29-57447ce75c52'
          },
          {
            idJson: '443d6e77-7fbf-45b4-9e86-b0b7ab52caa6'
          }
        ],
        tempoCiclo: 30,
        configurado: false,
        versaoPlano: {
          idJson: '2f11b501-7a1c-4e66-a88a-d5ea61e3c10e'
        }
      },
      {
        idJson: '6ee596d6-9d6e-43d6-80c1-99b661388e1f',
        anel: {
          idJson: '9f7ac702-9979-48c7-8007-bd97a867a41a'
        },
        descricao: 'PLANO 14',
        posicao: 14,
        modoOperacao: 'TEMPO_FIXO_COORDENADO',
        posicaoTabelaEntreVerde: 1,
        gruposSemaforicosPlanos: [
          {
            idJson: 'b66063a1-3283-4d1d-9ab4-31bf6f70ea9c'
          },
          {
            idJson: '60e70c08-ad38-4c04-9320-c5b17bb7b331'
          },
          {
            idJson: 'baa512f3-15fa-440d-9e85-5440fe09aca4'
          },
          {
            idJson: '267d2a22-531d-4c85-819d-d5f85661b67b'
          },
          {
            idJson: '308dc89c-cda8-4470-aec2-f089daae4985'
          }
        ],
        estagiosPlanos: [
          {
            idJson: '88893e0f-a100-41f7-b3f3-114723d537f0'
          },
          {
            idJson: '54443c2a-1dea-463d-837d-e5d78c860a3b'
          }
        ],
        tempoCiclo: 30,
        configurado: false,
        versaoPlano: {
          idJson: '2f11b501-7a1c-4e66-a88a-d5ea61e3c10e'
        }
      },
      {
        idJson: '8ad1eec4-992b-49a9-bf83-adc2ad1465d5',
        anel: {
          idJson: '9f7ac702-9979-48c7-8007-bd97a867a41a'
        },
        descricao: 'PLANO 15',
        posicao: 15,
        modoOperacao: 'TEMPO_FIXO_COORDENADO',
        posicaoTabelaEntreVerde: 1,
        gruposSemaforicosPlanos: [
          {
            idJson: '54443aab-a290-46ab-bb31-db6ef4509447'
          },
          {
            idJson: '4eb88c99-2309-4fbc-8a4a-aac1d85e8dde'
          },
          {
            idJson: 'bbdf96b6-d62c-4fe3-a6b9-840c90c62a91'
          },
          {
            idJson: '50a73703-4d9b-4985-92cb-fdcc1e5e7ec4'
          },
          {
            idJson: '10a0e61e-28e4-45da-8d64-9e841d7fc470'
          }
        ],
        estagiosPlanos: [
          {
            idJson: '0826c7fd-c186-4aaa-956a-2fd5fbb342de'
          },
          {
            idJson: '97c83c31-c36e-40df-9e4d-b8b5a007ff8b'
          }
        ],
        tempoCiclo: 30,
        configurado: false,
        versaoPlano: {
          idJson: '2f11b501-7a1c-4e66-a88a-d5ea61e3c10e'
        }
      },
      {
        idJson: '10433f86-f1a3-4694-b2b0-990f0702a5d4',
        anel: {
          idJson: '9f7ac702-9979-48c7-8007-bd97a867a41a'
        },
        descricao: 'PLANO 16',
        posicao: 16,
        modoOperacao: 'TEMPO_FIXO_COORDENADO',
        posicaoTabelaEntreVerde: 1,
        gruposSemaforicosPlanos: [
          {
            idJson: '0f5d45e2-e3ed-4f6e-b503-008639c3278c'
          },
          {
            idJson: '6b273636-1a9e-49d6-ae06-c14e2d6d1bef'
          },
          {
            idJson: '117693aa-d071-4ad5-bc37-2b0fe132581f'
          },
          {
            idJson: '0fc0a44c-631f-4507-96b2-88e31cb2baa9'
          },
          {
            idJson: 'baac6ca4-8f3c-43aa-96ca-92f07fd8f0af'
          }
        ],
        estagiosPlanos: [
          {
            idJson: 'b8080765-891a-4e4f-b790-00b4d73c6c2d'
          },
          {
            idJson: '92f64c2d-35ee-4361-b085-2313d4a043dc'
          }
        ],
        tempoCiclo: 30,
        configurado: false,
        versaoPlano: {
          idJson: '2f11b501-7a1c-4e66-a88a-d5ea61e3c10e'
        }
      }
    ],
    gruposSemaforicosPlanos: [
      {
        id: 'bba7390a-b5e7-4521-9736-b7bcdda3e446',
        idJson: '7399657c-d333-4ea9-9b84-84db93f2db91',
        plano: {
          idJson: 'fbed2d5c-8df6-46d7-8b3a-03b4d9d75a9e'
        },
        grupoSemaforico: {
          idJson: 'cd5f89f1-1051-4075-82ca-b861b0843b5e'
        },
        ativado: true
      },
      {
        id: 'e182d3a9-aaff-42b2-b676-af7bce51d03d',
        idJson: 'a7727591-72b1-46c8-a3d0-57ec81144238',
        plano: {
          idJson: 'd9d40a04-0d63-4927-88a9-f3b247f628a0'
        },
        grupoSemaforico: {
          idJson: '81b0f4b6-0c3b-4dc5-ac9b-5e74f279840c'
        },
        ativado: true
      },
      {
        id: '6890c995-84b7-4eaf-a9d8-f14ebb9286f6',
        idJson: '09b5b379-c842-444d-8671-575858445bdb',
        plano: {
          idJson: 'd7ff9f27-1132-476d-a622-744038650be9'
        },
        grupoSemaforico: {
          idJson: '75ae793c-d90c-4279-844f-53dc2ba788d2'
        },
        ativado: true
      },
      {
        id: 'c408d90f-5502-4010-9b5b-fe19a97a4760',
        idJson: '8d69fcae-07c0-426d-9820-4b097d5e59d6',
        plano: {
          idJson: '68d09bed-8168-49d5-ac93-c05c4a4c6db6'
        },
        grupoSemaforico: {
          idJson: 'cd5f89f1-1051-4075-82ca-b861b0843b5e'
        },
        ativado: true
      },
      {
        id: '797d689e-11fc-40c6-8d9e-d15dd0f989c3',
        idJson: 'db4b7bf8-1bec-4b5b-9346-dd41050ee538',
        plano: {
          idJson: 'b5283d44-2b17-4fcb-8277-336fb250a7d1'
        },
        grupoSemaforico: {
          idJson: 'ed58bdd3-f639-426e-b400-46d3d64b0a58'
        },
        ativado: true
      },
      {
        id: '02f5fce8-4f3f-4eb5-b0b8-444e7d535044',
        idJson: '97918690-e4e7-4723-8ada-33e052bcbd8d',
        plano: {
          idJson: 'b5283d44-2b17-4fcb-8277-336fb250a7d1'
        },
        grupoSemaforico: {
          idJson: '5f53a090-d3e5-4b4d-bbde-db990caedaf2'
        },
        ativado: true
      },
      {
        id: '8257ea48-4a91-4cab-a540-5f73a5e60450',
        idJson: '6e34943d-780a-4b9e-93c1-f9e20f0c32f8',
        plano: {
          idJson: '68d09bed-8168-49d5-ac93-c05c4a4c6db6'
        },
        grupoSemaforico: {
          idJson: '3b33af34-da3c-4803-80c4-b21bca918c2a'
        },
        ativado: true
      },
      {
        id: '15f096fb-3e92-4207-98de-818b34107f5b',
        idJson: 'a023bd05-fa07-47af-ae58-e47ea350738b',
        plano: {
          idJson: 'd9d40a04-0d63-4927-88a9-f3b247f628a0'
        },
        grupoSemaforico: {
          idJson: '75ae793c-d90c-4279-844f-53dc2ba788d2'
        },
        ativado: true
      },
      {
        id: '106df6b6-a69f-4473-95e5-9cd7dd602e18',
        idJson: '03b61267-1325-4b96-a111-0bea4c1af80f',
        plano: {
          idJson: '68d09bed-8168-49d5-ac93-c05c4a4c6db6'
        },
        grupoSemaforico: {
          idJson: 'ed58bdd3-f639-426e-b400-46d3d64b0a58'
        },
        ativado: true
      },
      {
        id: '1efbce2b-13a7-4867-8ad8-31960954928d',
        idJson: 'e9d25c4c-7e2b-4f2c-b871-724c6912f52c',
        plano: {
          idJson: 'd7ff9f27-1132-476d-a622-744038650be9'
        },
        grupoSemaforico: {
          idJson: 'fe8dc72c-f0f8-40c3-84d5-26e3c7ee1cfb'
        },
        ativado: true
      },
      {
        id: '5f1f650a-7975-4c5b-98c6-73246415ca28',
        idJson: '46dcdc2f-c413-4e80-8c4b-f9e505cf4f5e',
        plano: {
          idJson: 'd9d40a04-0d63-4927-88a9-f3b247f628a0'
        },
        grupoSemaforico: {
          idJson: 'daed6666-f188-4464-b9cf-3fea37345621'
        },
        ativado: true
      },
      {
        id: '5ecd595b-34cf-4a25-9134-05c45e216451',
        idJson: '495face4-03c8-4bed-98b9-cbe18ed10967',
        plano: {
          idJson: 'd7ff9f27-1132-476d-a622-744038650be9'
        },
        grupoSemaforico: {
          idJson: '81b0f4b6-0c3b-4dc5-ac9b-5e74f279840c'
        },
        ativado: true
      },
      {
        id: '40e6d69d-0f46-4997-9a0b-0e85d94f3740',
        idJson: '579d5df8-dd13-47ca-8ce5-12bec3121c2e',
        plano: {
          idJson: 'd7ff9f27-1132-476d-a622-744038650be9'
        },
        grupoSemaforico: {
          idJson: 'daed6666-f188-4464-b9cf-3fea37345621'
        },
        ativado: true
      },
      {
        id: 'b0e32487-da9a-47c3-96e8-530d3601fb32',
        idJson: 'd86d0a99-ff86-4cf0-9899-fa75f4239ce9',
        plano: {
          idJson: 'fbed2d5c-8df6-46d7-8b3a-03b4d9d75a9e'
        },
        grupoSemaforico: {
          idJson: '5f53a090-d3e5-4b4d-bbde-db990caedaf2'
        },
        ativado: true
      },
      {
        id: '9cb6472f-c927-43db-bfd9-fb89a8264b21',
        idJson: '0aa7c89b-8b77-4a02-920f-b0a742662871',
        plano: {
          idJson: '68d09bed-8168-49d5-ac93-c05c4a4c6db6'
        },
        grupoSemaforico: {
          idJson: '5f53a090-d3e5-4b4d-bbde-db990caedaf2'
        },
        ativado: true
      },
      {
        id: '59af4a66-7b94-4dee-b594-f12005cba4b4',
        idJson: 'dcf96665-f6af-44cc-8db6-681504ba0c25',
        plano: {
          idJson: 'fbed2d5c-8df6-46d7-8b3a-03b4d9d75a9e'
        },
        grupoSemaforico: {
          idJson: 'ed58bdd3-f639-426e-b400-46d3d64b0a58'
        },
        ativado: true
      },
      {
        id: '9e58eb7b-683b-4b9e-8c5d-88d144baa48a',
        idJson: '8e346781-febf-4180-a288-fd6ad8145c9a',
        plano: {
          idJson: 'fbed2d5c-8df6-46d7-8b3a-03b4d9d75a9e'
        },
        grupoSemaforico: {
          idJson: '3b33af34-da3c-4803-80c4-b21bca918c2a'
        },
        ativado: true
      },
      {
        id: 'a203add1-619d-4052-a2a1-fa37272b3607',
        idJson: 'e9a5083e-f63c-4419-9782-6168bf375377',
        plano: {
          idJson: 'b5283d44-2b17-4fcb-8277-336fb250a7d1'
        },
        grupoSemaforico: {
          idJson: 'e6f0363e-8d8c-4e45-936e-380b56791af7'
        },
        ativado: true
      },
      {
        id: 'aa89fb5e-937c-4574-b21f-962e403892c5',
        idJson: '6ae7ece0-add1-44f5-b0d3-ce4dcc241e2f',
        plano: {
          idJson: 'f6c3f670-b93e-4528-91c8-86709ae600cb'
        },
        grupoSemaforico: {
          idJson: 'fe8dc72c-f0f8-40c3-84d5-26e3c7ee1cfb'
        },
        ativado: true
      },
      {
        id: 'ad3f0b96-eee1-49a5-9441-51376bb024ad',
        idJson: '2a547ca2-aa1f-46fa-a542-b22cbd033edb',
        plano: {
          idJson: 'b5283d44-2b17-4fcb-8277-336fb250a7d1'
        },
        grupoSemaforico: {
          idJson: '3b33af34-da3c-4803-80c4-b21bca918c2a'
        },
        ativado: true
      },
      {
        id: 'b39891d0-1574-464f-9ad9-2ca30f77eacf',
        idJson: 'ce5025f5-d937-4102-971b-801a34de9197',
        plano: {
          idJson: 'f6c3f670-b93e-4528-91c8-86709ae600cb'
        },
        grupoSemaforico: {
          idJson: '29a8321e-f81d-4e76-a54d-540021e4fc6d'
        },
        ativado: true
      },
      {
        id: 'd941d5b7-92f6-4845-a4e5-b651b1ecd729',
        idJson: 'ddf87f28-36a2-4040-a462-36238faf7338',
        plano: {
          idJson: 'd9d40a04-0d63-4927-88a9-f3b247f628a0'
        },
        grupoSemaforico: {
          idJson: '29a8321e-f81d-4e76-a54d-540021e4fc6d'
        },
        ativado: true
      },
      {
        id: '3c8cf7de-263c-4a53-839c-7efe7bcbd088',
        idJson: '172fefaf-6fb7-471e-b180-be3fc5beda75',
        plano: {
          idJson: 'd7ff9f27-1132-476d-a622-744038650be9'
        },
        grupoSemaforico: {
          idJson: '29a8321e-f81d-4e76-a54d-540021e4fc6d'
        },
        ativado: true
      },
      {
        id: '293fd42e-a845-4084-8c90-ffc6acd943b1',
        idJson: 'adbba1e2-779b-47ae-ba1d-a27beb32c679',
        plano: {
          idJson: 'b5283d44-2b17-4fcb-8277-336fb250a7d1'
        },
        grupoSemaforico: {
          idJson: 'cd5f89f1-1051-4075-82ca-b861b0843b5e'
        },
        ativado: true
      },
      {
        id: 'dfd974b3-7cad-4c0d-a271-8d3130009eff',
        idJson: '45a9929b-5326-47ea-896a-e8de6743bcc7',
        plano: {
          idJson: 'f6c3f670-b93e-4528-91c8-86709ae600cb'
        },
        grupoSemaforico: {
          idJson: 'daed6666-f188-4464-b9cf-3fea37345621'
        },
        ativado: true
      },
      {
        id: '79eaea7c-e2f6-461c-af5e-b955820ab504',
        idJson: '2a87d2dd-3b49-4c9b-baa3-a08e1f7dcf98',
        plano: {
          idJson: 'fbed2d5c-8df6-46d7-8b3a-03b4d9d75a9e'
        },
        grupoSemaforico: {
          idJson: 'e6f0363e-8d8c-4e45-936e-380b56791af7'
        },
        ativado: true
      },
      {
        id: 'caadc85d-99d1-4842-9e2f-e5bdce19ccd5',
        idJson: '20e96106-4a74-4fb4-8a0d-51d1b2409382',
        plano: {
          idJson: 'f6c3f670-b93e-4528-91c8-86709ae600cb'
        },
        grupoSemaforico: {
          idJson: '81b0f4b6-0c3b-4dc5-ac9b-5e74f279840c'
        },
        ativado: true
      },
      {
        id: 'a8d3247b-f648-4fed-8e00-684eef0715ad',
        idJson: 'b1789a3b-d400-4aca-8231-ee2cabd5eeea',
        plano: {
          idJson: 'd9d40a04-0d63-4927-88a9-f3b247f628a0'
        },
        grupoSemaforico: {
          idJson: 'fe8dc72c-f0f8-40c3-84d5-26e3c7ee1cfb'
        },
        ativado: true
      },
      {
        id: '55ce7f27-d0d6-4c7f-af4c-2902b4bc3abb',
        idJson: '2483c3d6-23e6-406e-a4ea-789fa83cadee',
        plano: {
          idJson: '68d09bed-8168-49d5-ac93-c05c4a4c6db6'
        },
        grupoSemaforico: {
          idJson: 'e6f0363e-8d8c-4e45-936e-380b56791af7'
        },
        ativado: true
      },
      {
        id: '5b465842-f649-40ae-99e5-feba235708b4',
        idJson: '589ca023-bef9-4541-bf66-91342e839562',
        plano: {
          idJson: 'f6c3f670-b93e-4528-91c8-86709ae600cb'
        },
        grupoSemaforico: {
          idJson: '75ae793c-d90c-4279-844f-53dc2ba788d2'
        },
        ativado: true
      },
      {
        idJson: '4e321a5d-59cf-4c11-a630-7ba619be3119',
        ativado: true,
        grupoSemaforico: {
          idJson: 'daed6666-f188-4464-b9cf-3fea37345621'
        },
        plano: {
          idJson: '7d491161-bb12-4673-b8e9-324f88f629cd'
        }
      },
      {
        idJson: '18cc105c-9b9d-4f85-856a-a9701ab5fae7',
        ativado: true,
        grupoSemaforico: {
          idJson: 'fe8dc72c-f0f8-40c3-84d5-26e3c7ee1cfb'
        },
        plano: {
          idJson: '7d491161-bb12-4673-b8e9-324f88f629cd'
        }
      },
      {
        idJson: 'ee0100bb-62b3-40c6-a4f2-c58b323300a2',
        ativado: true,
        grupoSemaforico: {
          idJson: '81b0f4b6-0c3b-4dc5-ac9b-5e74f279840c'
        },
        plano: {
          idJson: '7d491161-bb12-4673-b8e9-324f88f629cd'
        }
      },
      {
        idJson: 'f5d42e91-c764-4082-9c61-04508b50de1d',
        ativado: true,
        grupoSemaforico: {
          idJson: '75ae793c-d90c-4279-844f-53dc2ba788d2'
        },
        plano: {
          idJson: '7d491161-bb12-4673-b8e9-324f88f629cd'
        }
      },
      {
        idJson: 'b4d8567b-362f-4e2b-ac13-c747ff8b111b',
        ativado: true,
        grupoSemaforico: {
          idJson: '29a8321e-f81d-4e76-a54d-540021e4fc6d'
        },
        plano: {
          idJson: '7d491161-bb12-4673-b8e9-324f88f629cd'
        }
      },
      {
        idJson: 'eb8e1eb6-cfba-427a-8cd1-ade0091981eb',
        ativado: true,
        grupoSemaforico: {
          idJson: 'daed6666-f188-4464-b9cf-3fea37345621'
        },
        plano: {
          idJson: '2ba66e27-b6e4-4571-b2e2-0bf77be547b1'
        }
      },
      {
        idJson: 'a400712a-0d02-4229-9249-db5be4728d37',
        ativado: true,
        grupoSemaforico: {
          idJson: 'fe8dc72c-f0f8-40c3-84d5-26e3c7ee1cfb'
        },
        plano: {
          idJson: '2ba66e27-b6e4-4571-b2e2-0bf77be547b1'
        }
      },
      {
        idJson: '912e5f06-d305-4a7f-9522-23c33ace849f',
        ativado: true,
        grupoSemaforico: {
          idJson: '81b0f4b6-0c3b-4dc5-ac9b-5e74f279840c'
        },
        plano: {
          idJson: '2ba66e27-b6e4-4571-b2e2-0bf77be547b1'
        }
      },
      {
        idJson: 'e9b176e9-4c59-42e2-aa88-23d7b3d380e9',
        ativado: true,
        grupoSemaforico: {
          idJson: '75ae793c-d90c-4279-844f-53dc2ba788d2'
        },
        plano: {
          idJson: '2ba66e27-b6e4-4571-b2e2-0bf77be547b1'
        }
      },
      {
        idJson: 'abfca52f-ca93-466c-a5df-472e202d9aa8',
        ativado: true,
        grupoSemaforico: {
          idJson: '29a8321e-f81d-4e76-a54d-540021e4fc6d'
        },
        plano: {
          idJson: '2ba66e27-b6e4-4571-b2e2-0bf77be547b1'
        }
      },
      {
        idJson: '878952db-f1ed-4d25-93d8-01c1e931ffd9',
        ativado: true,
        grupoSemaforico: {
          idJson: 'daed6666-f188-4464-b9cf-3fea37345621'
        },
        plano: {
          idJson: '9c0bae10-1b9f-422c-a063-aa0c305b25d0'
        }
      },
      {
        idJson: '82583925-6763-4ff1-b968-e18c3ea3c404',
        ativado: true,
        grupoSemaforico: {
          idJson: 'fe8dc72c-f0f8-40c3-84d5-26e3c7ee1cfb'
        },
        plano: {
          idJson: '9c0bae10-1b9f-422c-a063-aa0c305b25d0'
        }
      },
      {
        idJson: 'b4d2fc8b-2226-41fc-a81f-d6bc4d33d2a6',
        ativado: true,
        grupoSemaforico: {
          idJson: '81b0f4b6-0c3b-4dc5-ac9b-5e74f279840c'
        },
        plano: {
          idJson: '9c0bae10-1b9f-422c-a063-aa0c305b25d0'
        }
      },
      {
        idJson: '9b60e219-3567-4d07-952b-7c3266941c75',
        ativado: true,
        grupoSemaforico: {
          idJson: '75ae793c-d90c-4279-844f-53dc2ba788d2'
        },
        plano: {
          idJson: '9c0bae10-1b9f-422c-a063-aa0c305b25d0'
        }
      },
      {
        idJson: '240d1fa8-1cbb-456c-80d7-586d8e96c2b8',
        ativado: true,
        grupoSemaforico: {
          idJson: '29a8321e-f81d-4e76-a54d-540021e4fc6d'
        },
        plano: {
          idJson: '9c0bae10-1b9f-422c-a063-aa0c305b25d0'
        }
      },
      {
        idJson: 'd58d0a28-4bd4-4589-b05d-3340b5ad8946',
        ativado: true,
        grupoSemaforico: {
          idJson: 'daed6666-f188-4464-b9cf-3fea37345621'
        },
        plano: {
          idJson: '233c11dc-b927-4df2-a4b8-7e2875061970'
        }
      },
      {
        idJson: '0bd391d7-474d-424d-894c-c88e95c4ab7d',
        ativado: true,
        grupoSemaforico: {
          idJson: 'fe8dc72c-f0f8-40c3-84d5-26e3c7ee1cfb'
        },
        plano: {
          idJson: '233c11dc-b927-4df2-a4b8-7e2875061970'
        }
      },
      {
        idJson: 'ca6d57ee-2154-4a94-8bcc-fe12dff3f284',
        ativado: true,
        grupoSemaforico: {
          idJson: '81b0f4b6-0c3b-4dc5-ac9b-5e74f279840c'
        },
        plano: {
          idJson: '233c11dc-b927-4df2-a4b8-7e2875061970'
        }
      },
      {
        idJson: 'c20489b9-4dc4-4c4c-99a4-7b28a8082b01',
        ativado: true,
        grupoSemaforico: {
          idJson: '75ae793c-d90c-4279-844f-53dc2ba788d2'
        },
        plano: {
          idJson: '233c11dc-b927-4df2-a4b8-7e2875061970'
        }
      },
      {
        idJson: '8d91afd5-bbed-4980-88d2-f979ee876328',
        ativado: true,
        grupoSemaforico: {
          idJson: '29a8321e-f81d-4e76-a54d-540021e4fc6d'
        },
        plano: {
          idJson: '233c11dc-b927-4df2-a4b8-7e2875061970'
        }
      },
      {
        idJson: '64f830a6-c52a-4e9b-b20e-31277ce0dcfc',
        ativado: true,
        grupoSemaforico: {
          idJson: 'daed6666-f188-4464-b9cf-3fea37345621'
        },
        plano: {
          idJson: 'c8b26060-acd7-44c3-8e92-cbdb316d6deb'
        }
      },
      {
        idJson: '7f5702a3-aa6e-48ae-b9c3-55fd9bdb9f0e',
        ativado: true,
        grupoSemaforico: {
          idJson: 'fe8dc72c-f0f8-40c3-84d5-26e3c7ee1cfb'
        },
        plano: {
          idJson: 'c8b26060-acd7-44c3-8e92-cbdb316d6deb'
        }
      },
      {
        idJson: '29ba7f62-c382-44ad-a296-582c72907941',
        ativado: true,
        grupoSemaforico: {
          idJson: '81b0f4b6-0c3b-4dc5-ac9b-5e74f279840c'
        },
        plano: {
          idJson: 'c8b26060-acd7-44c3-8e92-cbdb316d6deb'
        }
      },
      {
        idJson: 'ef0fea97-bc27-40e5-abd0-7b0522b480fb',
        ativado: true,
        grupoSemaforico: {
          idJson: '75ae793c-d90c-4279-844f-53dc2ba788d2'
        },
        plano: {
          idJson: 'c8b26060-acd7-44c3-8e92-cbdb316d6deb'
        }
      },
      {
        idJson: 'b4e24d8a-06f4-402c-b11b-290f10f46ebe',
        ativado: true,
        grupoSemaforico: {
          idJson: '29a8321e-f81d-4e76-a54d-540021e4fc6d'
        },
        plano: {
          idJson: 'c8b26060-acd7-44c3-8e92-cbdb316d6deb'
        }
      },
      {
        idJson: '95f1239b-1cf7-44b1-af26-10e517a2b9c8',
        ativado: true,
        grupoSemaforico: {
          idJson: 'daed6666-f188-4464-b9cf-3fea37345621'
        },
        plano: {
          idJson: 'a891c685-9285-4257-bed1-16f25f044c24'
        }
      },
      {
        idJson: '9c2824a0-f36c-4824-b3ec-f936ac85d13a',
        ativado: true,
        grupoSemaforico: {
          idJson: 'fe8dc72c-f0f8-40c3-84d5-26e3c7ee1cfb'
        },
        plano: {
          idJson: 'a891c685-9285-4257-bed1-16f25f044c24'
        }
      },
      {
        idJson: '52330ffe-d8fb-4268-b85d-063702507649',
        ativado: true,
        grupoSemaforico: {
          idJson: '81b0f4b6-0c3b-4dc5-ac9b-5e74f279840c'
        },
        plano: {
          idJson: 'a891c685-9285-4257-bed1-16f25f044c24'
        }
      },
      {
        idJson: 'fe96befe-5841-4b88-a2ed-cdca3d66c9f2',
        ativado: true,
        grupoSemaforico: {
          idJson: '75ae793c-d90c-4279-844f-53dc2ba788d2'
        },
        plano: {
          idJson: 'a891c685-9285-4257-bed1-16f25f044c24'
        }
      },
      {
        idJson: 'bce2a806-1ff4-4c5e-bbd4-d08779ed181f',
        ativado: true,
        grupoSemaforico: {
          idJson: '29a8321e-f81d-4e76-a54d-540021e4fc6d'
        },
        plano: {
          idJson: 'a891c685-9285-4257-bed1-16f25f044c24'
        }
      },
      {
        idJson: 'a6f49620-2ce2-432d-b28d-cbcf2a8be1f7',
        ativado: true,
        grupoSemaforico: {
          idJson: 'daed6666-f188-4464-b9cf-3fea37345621'
        },
        plano: {
          idJson: '5270dfac-b24b-4ba0-9c79-0435c74301d5'
        }
      },
      {
        idJson: '3abc4d5d-1bdb-4ded-9b89-ad0136a345ac',
        ativado: true,
        grupoSemaforico: {
          idJson: 'fe8dc72c-f0f8-40c3-84d5-26e3c7ee1cfb'
        },
        plano: {
          idJson: '5270dfac-b24b-4ba0-9c79-0435c74301d5'
        }
      },
      {
        idJson: '9d1840ee-8426-4231-b2d2-84d877ef94db',
        ativado: true,
        grupoSemaforico: {
          idJson: '81b0f4b6-0c3b-4dc5-ac9b-5e74f279840c'
        },
        plano: {
          idJson: '5270dfac-b24b-4ba0-9c79-0435c74301d5'
        }
      },
      {
        idJson: '33342664-3b23-4b63-b272-57353d93ef92',
        ativado: true,
        grupoSemaforico: {
          idJson: '75ae793c-d90c-4279-844f-53dc2ba788d2'
        },
        plano: {
          idJson: '5270dfac-b24b-4ba0-9c79-0435c74301d5'
        }
      },
      {
        idJson: '8a143ca3-b25d-4a80-b541-e92eeb8a0b43',
        ativado: true,
        grupoSemaforico: {
          idJson: '29a8321e-f81d-4e76-a54d-540021e4fc6d'
        },
        plano: {
          idJson: '5270dfac-b24b-4ba0-9c79-0435c74301d5'
        }
      },
      {
        idJson: 'f53ee0e7-c7b4-4485-a87b-c0050239e046',
        ativado: true,
        grupoSemaforico: {
          idJson: 'daed6666-f188-4464-b9cf-3fea37345621'
        },
        plano: {
          idJson: '0ce21c68-24c6-4d17-81f2-88c581931326'
        }
      },
      {
        idJson: '3319b439-170e-4cee-941c-62e49e8cbed4',
        ativado: true,
        grupoSemaforico: {
          idJson: 'fe8dc72c-f0f8-40c3-84d5-26e3c7ee1cfb'
        },
        plano: {
          idJson: '0ce21c68-24c6-4d17-81f2-88c581931326'
        }
      },
      {
        idJson: '3d3a60fd-4753-4b5f-82c6-5dc6334be93b',
        ativado: true,
        grupoSemaforico: {
          idJson: '81b0f4b6-0c3b-4dc5-ac9b-5e74f279840c'
        },
        plano: {
          idJson: '0ce21c68-24c6-4d17-81f2-88c581931326'
        }
      },
      {
        idJson: '06246c3e-1d99-428e-ad80-b73357be20e9',
        ativado: true,
        grupoSemaforico: {
          idJson: '75ae793c-d90c-4279-844f-53dc2ba788d2'
        },
        plano: {
          idJson: '0ce21c68-24c6-4d17-81f2-88c581931326'
        }
      },
      {
        idJson: '194baa79-4984-4c51-b9f4-18db5b296e18',
        ativado: true,
        grupoSemaforico: {
          idJson: '29a8321e-f81d-4e76-a54d-540021e4fc6d'
        },
        plano: {
          idJson: '0ce21c68-24c6-4d17-81f2-88c581931326'
        }
      },
      {
        idJson: '7bc1f8af-badb-4119-85eb-3b12b2ce0aa5',
        ativado: true,
        grupoSemaforico: {
          idJson: 'daed6666-f188-4464-b9cf-3fea37345621'
        },
        plano: {
          idJson: 'bda5c85c-63d5-4fe7-8f6d-7382f8b2152c'
        }
      },
      {
        idJson: '2cbb0622-a2e5-47d1-8746-6a9d6758697e',
        ativado: true,
        grupoSemaforico: {
          idJson: 'fe8dc72c-f0f8-40c3-84d5-26e3c7ee1cfb'
        },
        plano: {
          idJson: 'bda5c85c-63d5-4fe7-8f6d-7382f8b2152c'
        }
      },
      {
        idJson: '2ab75c6e-1f67-4fbc-8749-ddf527e43c40',
        ativado: true,
        grupoSemaforico: {
          idJson: '81b0f4b6-0c3b-4dc5-ac9b-5e74f279840c'
        },
        plano: {
          idJson: 'bda5c85c-63d5-4fe7-8f6d-7382f8b2152c'
        }
      },
      {
        idJson: '5929e28a-d11e-453a-8899-14560427d1df',
        ativado: true,
        grupoSemaforico: {
          idJson: '75ae793c-d90c-4279-844f-53dc2ba788d2'
        },
        plano: {
          idJson: 'bda5c85c-63d5-4fe7-8f6d-7382f8b2152c'
        }
      },
      {
        idJson: 'e2222f3f-bfaf-4b2e-ab7c-bd8710399a0d',
        ativado: true,
        grupoSemaforico: {
          idJson: '29a8321e-f81d-4e76-a54d-540021e4fc6d'
        },
        plano: {
          idJson: 'bda5c85c-63d5-4fe7-8f6d-7382f8b2152c'
        }
      },
      {
        idJson: '45e02769-ac61-4cda-908c-c455781855c8',
        ativado: true,
        grupoSemaforico: {
          idJson: 'daed6666-f188-4464-b9cf-3fea37345621'
        },
        plano: {
          idJson: 'd8ac0504-ac63-44a2-a6b1-efb33f491da6'
        }
      },
      {
        idJson: 'db76f944-a0f8-4ca1-a348-48cdf9fb2790',
        ativado: true,
        grupoSemaforico: {
          idJson: 'fe8dc72c-f0f8-40c3-84d5-26e3c7ee1cfb'
        },
        plano: {
          idJson: 'd8ac0504-ac63-44a2-a6b1-efb33f491da6'
        }
      },
      {
        idJson: 'a733bc1c-18c0-463f-9ffb-184ef8b8da9b',
        ativado: true,
        grupoSemaforico: {
          idJson: '81b0f4b6-0c3b-4dc5-ac9b-5e74f279840c'
        },
        plano: {
          idJson: 'd8ac0504-ac63-44a2-a6b1-efb33f491da6'
        }
      },
      {
        idJson: 'cbcd133f-227b-4411-8d65-68e250ff471a',
        ativado: true,
        grupoSemaforico: {
          idJson: '75ae793c-d90c-4279-844f-53dc2ba788d2'
        },
        plano: {
          idJson: 'd8ac0504-ac63-44a2-a6b1-efb33f491da6'
        }
      },
      {
        idJson: '60fd26cf-c1bd-4171-a79e-acc951476ee1',
        ativado: true,
        grupoSemaforico: {
          idJson: '29a8321e-f81d-4e76-a54d-540021e4fc6d'
        },
        plano: {
          idJson: 'd8ac0504-ac63-44a2-a6b1-efb33f491da6'
        }
      },
      {
        idJson: 'b098d080-8ca1-4ad4-98ef-45e4cf56b9bd',
        ativado: true,
        grupoSemaforico: {
          idJson: 'daed6666-f188-4464-b9cf-3fea37345621'
        },
        plano: {
          idJson: '2819a411-92ab-4654-8ef2-001e10b9fb84'
        }
      },
      {
        idJson: '388a55d4-af7d-4d99-966b-b24469683ed4',
        ativado: true,
        grupoSemaforico: {
          idJson: 'fe8dc72c-f0f8-40c3-84d5-26e3c7ee1cfb'
        },
        plano: {
          idJson: '2819a411-92ab-4654-8ef2-001e10b9fb84'
        }
      },
      {
        idJson: 'e4d8ea40-5800-4f54-b199-41c5aa1239ce',
        ativado: true,
        grupoSemaforico: {
          idJson: '81b0f4b6-0c3b-4dc5-ac9b-5e74f279840c'
        },
        plano: {
          idJson: '2819a411-92ab-4654-8ef2-001e10b9fb84'
        }
      },
      {
        idJson: 'f9076a97-0efa-4336-b518-dd6b746945d7',
        ativado: true,
        grupoSemaforico: {
          idJson: '75ae793c-d90c-4279-844f-53dc2ba788d2'
        },
        plano: {
          idJson: '2819a411-92ab-4654-8ef2-001e10b9fb84'
        }
      },
      {
        idJson: '49df6153-3417-464b-b074-6d89d46da708',
        ativado: true,
        grupoSemaforico: {
          idJson: '29a8321e-f81d-4e76-a54d-540021e4fc6d'
        },
        plano: {
          idJson: '2819a411-92ab-4654-8ef2-001e10b9fb84'
        }
      },
      {
        idJson: '308218cc-2e04-47e9-816d-ffdc64259a84',
        ativado: true,
        grupoSemaforico: {
          idJson: 'daed6666-f188-4464-b9cf-3fea37345621'
        },
        plano: {
          idJson: '14c59533-b7f5-4205-bed1-4e0e99bebd42'
        }
      },
      {
        idJson: '9c0b1481-5c48-4ed0-ac6b-cae3954884f5',
        ativado: true,
        grupoSemaforico: {
          idJson: 'fe8dc72c-f0f8-40c3-84d5-26e3c7ee1cfb'
        },
        plano: {
          idJson: '14c59533-b7f5-4205-bed1-4e0e99bebd42'
        }
      },
      {
        idJson: 'faf5c73b-39ca-4905-9175-a48c1ea8cee4',
        ativado: true,
        grupoSemaforico: {
          idJson: '81b0f4b6-0c3b-4dc5-ac9b-5e74f279840c'
        },
        plano: {
          idJson: '14c59533-b7f5-4205-bed1-4e0e99bebd42'
        }
      },
      {
        idJson: '867dafe6-d1f4-4e5e-bd34-9e8820989c3b',
        ativado: true,
        grupoSemaforico: {
          idJson: '75ae793c-d90c-4279-844f-53dc2ba788d2'
        },
        plano: {
          idJson: '14c59533-b7f5-4205-bed1-4e0e99bebd42'
        }
      },
      {
        idJson: '883fc507-d634-4bfb-975a-a05d57bb6f24',
        ativado: true,
        grupoSemaforico: {
          idJson: '29a8321e-f81d-4e76-a54d-540021e4fc6d'
        },
        plano: {
          idJson: '14c59533-b7f5-4205-bed1-4e0e99bebd42'
        }
      },
      {
        idJson: '5cbbcedc-a596-4cae-ab64-9face3f4895d',
        ativado: true,
        grupoSemaforico: {
          idJson: 'daed6666-f188-4464-b9cf-3fea37345621'
        },
        plano: {
          idJson: 'dd976fc4-da72-4dec-89c9-9aa7b15ebb42'
        }
      },
      {
        idJson: '361ec118-236c-404a-b5e3-bbb0c462419f',
        ativado: true,
        grupoSemaforico: {
          idJson: 'fe8dc72c-f0f8-40c3-84d5-26e3c7ee1cfb'
        },
        plano: {
          idJson: 'dd976fc4-da72-4dec-89c9-9aa7b15ebb42'
        }
      },
      {
        idJson: '80c0809c-5bac-4f71-b3f0-a8e4b06e280b',
        ativado: true,
        grupoSemaforico: {
          idJson: '81b0f4b6-0c3b-4dc5-ac9b-5e74f279840c'
        },
        plano: {
          idJson: 'dd976fc4-da72-4dec-89c9-9aa7b15ebb42'
        }
      },
      {
        idJson: '358139e6-22e6-4549-848f-fb4d15f94f41',
        ativado: true,
        grupoSemaforico: {
          idJson: '75ae793c-d90c-4279-844f-53dc2ba788d2'
        },
        plano: {
          idJson: 'dd976fc4-da72-4dec-89c9-9aa7b15ebb42'
        }
      },
      {
        idJson: 'd29531b1-8cb4-48a8-a103-b5d015da8edb',
        ativado: true,
        grupoSemaforico: {
          idJson: '29a8321e-f81d-4e76-a54d-540021e4fc6d'
        },
        plano: {
          idJson: 'dd976fc4-da72-4dec-89c9-9aa7b15ebb42'
        }
      },
      {
        idJson: '9d0f868f-2724-4a9f-98f7-6efece98aac1',
        ativado: true,
        grupoSemaforico: {
          idJson: 'daed6666-f188-4464-b9cf-3fea37345621'
        },
        plano: {
          idJson: '6f68ea04-988c-4d50-b439-3ad02905eff1'
        }
      },
      {
        idJson: '10d14d07-e4d2-4141-aee1-0ed1433428cb',
        ativado: true,
        grupoSemaforico: {
          idJson: 'fe8dc72c-f0f8-40c3-84d5-26e3c7ee1cfb'
        },
        plano: {
          idJson: '6f68ea04-988c-4d50-b439-3ad02905eff1'
        }
      },
      {
        idJson: '21362abd-764a-4a61-8942-7b3cd0d48fe0',
        ativado: true,
        grupoSemaforico: {
          idJson: '81b0f4b6-0c3b-4dc5-ac9b-5e74f279840c'
        },
        plano: {
          idJson: '6f68ea04-988c-4d50-b439-3ad02905eff1'
        }
      },
      {
        idJson: 'e675c5cd-9c7e-429f-b87d-1c6a781c0525',
        ativado: true,
        grupoSemaforico: {
          idJson: '75ae793c-d90c-4279-844f-53dc2ba788d2'
        },
        plano: {
          idJson: '6f68ea04-988c-4d50-b439-3ad02905eff1'
        }
      },
      {
        idJson: '7e35891f-340d-42d5-8adf-2840760e46ae',
        ativado: true,
        grupoSemaforico: {
          idJson: '29a8321e-f81d-4e76-a54d-540021e4fc6d'
        },
        plano: {
          idJson: '6f68ea04-988c-4d50-b439-3ad02905eff1'
        }
      },
      {
        idJson: '0518b8c4-2b98-4661-8352-e7b432248574',
        ativado: true,
        grupoSemaforico: {
          idJson: 'e6f0363e-8d8c-4e45-936e-380b56791af7'
        },
        plano: {
          idJson: '398aa468-075d-49fc-b940-36886add16a3'
        }
      },
      {
        idJson: '5f0ff01b-4843-48e2-958f-f074c16c26e2',
        ativado: true,
        grupoSemaforico: {
          idJson: '3b33af34-da3c-4803-80c4-b21bca918c2a'
        },
        plano: {
          idJson: '398aa468-075d-49fc-b940-36886add16a3'
        }
      },
      {
        idJson: 'dc50a2b1-dfd0-4a32-a01c-72cba3f5dea6',
        ativado: true,
        grupoSemaforico: {
          idJson: 'cd5f89f1-1051-4075-82ca-b861b0843b5e'
        },
        plano: {
          idJson: '398aa468-075d-49fc-b940-36886add16a3'
        }
      },
      {
        idJson: '1184ed2b-461c-4bfd-b267-822f0695630b',
        ativado: true,
        grupoSemaforico: {
          idJson: 'ed58bdd3-f639-426e-b400-46d3d64b0a58'
        },
        plano: {
          idJson: '398aa468-075d-49fc-b940-36886add16a3'
        }
      },
      {
        idJson: 'c4945628-b31f-4196-b32e-bbefa52d4c6d',
        ativado: true,
        grupoSemaforico: {
          idJson: '5f53a090-d3e5-4b4d-bbde-db990caedaf2'
        },
        plano: {
          idJson: '398aa468-075d-49fc-b940-36886add16a3'
        }
      },
      {
        idJson: 'e58e226b-1877-49ab-bc12-b028eada39b3',
        ativado: true,
        grupoSemaforico: {
          idJson: 'e6f0363e-8d8c-4e45-936e-380b56791af7'
        },
        plano: {
          idJson: '0a64f4df-54fd-4097-963e-571d65e51123'
        }
      },
      {
        idJson: '1d90fc77-9548-4f35-a7b6-f46daee13177',
        ativado: true,
        grupoSemaforico: {
          idJson: '3b33af34-da3c-4803-80c4-b21bca918c2a'
        },
        plano: {
          idJson: '0a64f4df-54fd-4097-963e-571d65e51123'
        }
      },
      {
        idJson: '9e178ddd-cbbf-4ddf-b53f-4106d443c0af',
        ativado: true,
        grupoSemaforico: {
          idJson: 'cd5f89f1-1051-4075-82ca-b861b0843b5e'
        },
        plano: {
          idJson: '0a64f4df-54fd-4097-963e-571d65e51123'
        }
      },
      {
        idJson: 'c42ce870-fdea-457b-88e4-bc3337c5bbf3',
        ativado: true,
        grupoSemaforico: {
          idJson: 'ed58bdd3-f639-426e-b400-46d3d64b0a58'
        },
        plano: {
          idJson: '0a64f4df-54fd-4097-963e-571d65e51123'
        }
      },
      {
        idJson: 'd0e4a64c-f7b9-450e-9c4e-431e73a30adb',
        ativado: true,
        grupoSemaforico: {
          idJson: '5f53a090-d3e5-4b4d-bbde-db990caedaf2'
        },
        plano: {
          idJson: '0a64f4df-54fd-4097-963e-571d65e51123'
        }
      },
      {
        idJson: '81d77e1b-a837-4ba0-9dff-61d3396a9764',
        ativado: true,
        grupoSemaforico: {
          idJson: 'e6f0363e-8d8c-4e45-936e-380b56791af7'
        },
        plano: {
          idJson: 'f984986c-37f5-44bd-840a-dc94c8a0d850'
        }
      },
      {
        idJson: '84f43052-3f79-45a2-b323-c816c6855ddd',
        ativado: true,
        grupoSemaforico: {
          idJson: '3b33af34-da3c-4803-80c4-b21bca918c2a'
        },
        plano: {
          idJson: 'f984986c-37f5-44bd-840a-dc94c8a0d850'
        }
      },
      {
        idJson: '027784af-d845-4482-9984-67bd7a4b8af6',
        ativado: true,
        grupoSemaforico: {
          idJson: 'cd5f89f1-1051-4075-82ca-b861b0843b5e'
        },
        plano: {
          idJson: 'f984986c-37f5-44bd-840a-dc94c8a0d850'
        }
      },
      {
        idJson: '14d0278c-c526-4f56-87d6-90262043b9b2',
        ativado: true,
        grupoSemaforico: {
          idJson: 'ed58bdd3-f639-426e-b400-46d3d64b0a58'
        },
        plano: {
          idJson: 'f984986c-37f5-44bd-840a-dc94c8a0d850'
        }
      },
      {
        idJson: '559e915c-c5c3-41d4-971b-b626e0fd0a3c',
        ativado: true,
        grupoSemaforico: {
          idJson: '5f53a090-d3e5-4b4d-bbde-db990caedaf2'
        },
        plano: {
          idJson: 'f984986c-37f5-44bd-840a-dc94c8a0d850'
        }
      },
      {
        idJson: '098efdb6-ffae-4d05-932f-03059974076e',
        ativado: true,
        grupoSemaforico: {
          idJson: 'e6f0363e-8d8c-4e45-936e-380b56791af7'
        },
        plano: {
          idJson: '6780c087-42c3-42df-aa25-fad41b46f03b'
        }
      },
      {
        idJson: 'ba4e6b57-fffb-4a93-a58a-90032e84cad2',
        ativado: true,
        grupoSemaforico: {
          idJson: '3b33af34-da3c-4803-80c4-b21bca918c2a'
        },
        plano: {
          idJson: '6780c087-42c3-42df-aa25-fad41b46f03b'
        }
      },
      {
        idJson: '70b123fa-49f9-4fdf-9e76-2fad98cce313',
        ativado: true,
        grupoSemaforico: {
          idJson: 'cd5f89f1-1051-4075-82ca-b861b0843b5e'
        },
        plano: {
          idJson: '6780c087-42c3-42df-aa25-fad41b46f03b'
        }
      },
      {
        idJson: 'f41dd47f-c7e8-46e7-8341-2ae02b925108',
        ativado: true,
        grupoSemaforico: {
          idJson: 'ed58bdd3-f639-426e-b400-46d3d64b0a58'
        },
        plano: {
          idJson: '6780c087-42c3-42df-aa25-fad41b46f03b'
        }
      },
      {
        idJson: 'e4cf9a93-08f6-4b76-95b7-1290c43c44a9',
        ativado: true,
        grupoSemaforico: {
          idJson: '5f53a090-d3e5-4b4d-bbde-db990caedaf2'
        },
        plano: {
          idJson: '6780c087-42c3-42df-aa25-fad41b46f03b'
        }
      },
      {
        idJson: 'bdb0f4aa-74b8-47ef-9013-c475ac026b01',
        ativado: true,
        grupoSemaforico: {
          idJson: 'e6f0363e-8d8c-4e45-936e-380b56791af7'
        },
        plano: {
          idJson: '6781daa5-8106-4f87-b20d-027b2ee92907'
        }
      },
      {
        idJson: 'f8725d59-2d54-49ef-84f4-085037ee5739',
        ativado: true,
        grupoSemaforico: {
          idJson: '3b33af34-da3c-4803-80c4-b21bca918c2a'
        },
        plano: {
          idJson: '6781daa5-8106-4f87-b20d-027b2ee92907'
        }
      },
      {
        idJson: '99116c5b-0c05-46fb-9fbe-c1f4291babb4',
        ativado: true,
        grupoSemaforico: {
          idJson: 'cd5f89f1-1051-4075-82ca-b861b0843b5e'
        },
        plano: {
          idJson: '6781daa5-8106-4f87-b20d-027b2ee92907'
        }
      },
      {
        idJson: '8eeb09f1-9483-48b1-a22b-f93ab5ae962c',
        ativado: true,
        grupoSemaforico: {
          idJson: 'ed58bdd3-f639-426e-b400-46d3d64b0a58'
        },
        plano: {
          idJson: '6781daa5-8106-4f87-b20d-027b2ee92907'
        }
      },
      {
        idJson: 'ef6d5930-5ad6-4634-b5d1-0812fe97da3f',
        ativado: true,
        grupoSemaforico: {
          idJson: '5f53a090-d3e5-4b4d-bbde-db990caedaf2'
        },
        plano: {
          idJson: '6781daa5-8106-4f87-b20d-027b2ee92907'
        }
      },
      {
        idJson: '5de27311-ed0e-44a5-9332-e4aca7e5564e',
        ativado: true,
        grupoSemaforico: {
          idJson: 'e6f0363e-8d8c-4e45-936e-380b56791af7'
        },
        plano: {
          idJson: '0b3062e0-3fad-4071-9a01-f01724c20a97'
        }
      },
      {
        idJson: '8413cae6-ee51-48d7-a6ee-6c236e0bd83a',
        ativado: true,
        grupoSemaforico: {
          idJson: '3b33af34-da3c-4803-80c4-b21bca918c2a'
        },
        plano: {
          idJson: '0b3062e0-3fad-4071-9a01-f01724c20a97'
        }
      },
      {
        idJson: 'e886e6ed-953a-4a1c-807e-f4a033d61a48',
        ativado: true,
        grupoSemaforico: {
          idJson: 'cd5f89f1-1051-4075-82ca-b861b0843b5e'
        },
        plano: {
          idJson: '0b3062e0-3fad-4071-9a01-f01724c20a97'
        }
      },
      {
        idJson: 'dd38ff4a-aa4f-4df2-949f-a20d951df670',
        ativado: true,
        grupoSemaforico: {
          idJson: 'ed58bdd3-f639-426e-b400-46d3d64b0a58'
        },
        plano: {
          idJson: '0b3062e0-3fad-4071-9a01-f01724c20a97'
        }
      },
      {
        idJson: '4d1d9eb2-031d-4540-a561-aa0c7484af44',
        ativado: true,
        grupoSemaforico: {
          idJson: '5f53a090-d3e5-4b4d-bbde-db990caedaf2'
        },
        plano: {
          idJson: '0b3062e0-3fad-4071-9a01-f01724c20a97'
        }
      },
      {
        idJson: '1ddb77fc-ab77-47ef-b3fe-be8b2d5b558e',
        ativado: true,
        grupoSemaforico: {
          idJson: 'e6f0363e-8d8c-4e45-936e-380b56791af7'
        },
        plano: {
          idJson: '8495d409-d4f1-4e43-bb06-045c2a56c0cf'
        }
      },
      {
        idJson: '82223081-c521-4b31-9406-65f6e393af95',
        ativado: true,
        grupoSemaforico: {
          idJson: '3b33af34-da3c-4803-80c4-b21bca918c2a'
        },
        plano: {
          idJson: '8495d409-d4f1-4e43-bb06-045c2a56c0cf'
        }
      },
      {
        idJson: 'ac1d302b-52c9-4921-966c-cd65dbeb8714',
        ativado: true,
        grupoSemaforico: {
          idJson: 'cd5f89f1-1051-4075-82ca-b861b0843b5e'
        },
        plano: {
          idJson: '8495d409-d4f1-4e43-bb06-045c2a56c0cf'
        }
      },
      {
        idJson: '9ce7c204-2d2f-4fa6-beac-860b3335d414',
        ativado: true,
        grupoSemaforico: {
          idJson: 'ed58bdd3-f639-426e-b400-46d3d64b0a58'
        },
        plano: {
          idJson: '8495d409-d4f1-4e43-bb06-045c2a56c0cf'
        }
      },
      {
        idJson: '343258f7-327d-400c-98bd-02b052e52c13',
        ativado: true,
        grupoSemaforico: {
          idJson: '5f53a090-d3e5-4b4d-bbde-db990caedaf2'
        },
        plano: {
          idJson: '8495d409-d4f1-4e43-bb06-045c2a56c0cf'
        }
      },
      {
        idJson: '17a195c5-3ad0-4cf8-8487-0f092f592abe',
        ativado: true,
        grupoSemaforico: {
          idJson: 'e6f0363e-8d8c-4e45-936e-380b56791af7'
        },
        plano: {
          idJson: 'b52d3443-2b72-4a32-8cc2-7d74e39229ff'
        }
      },
      {
        idJson: 'a8472429-60f9-4597-beeb-0054d5987626',
        ativado: true,
        grupoSemaforico: {
          idJson: '3b33af34-da3c-4803-80c4-b21bca918c2a'
        },
        plano: {
          idJson: 'b52d3443-2b72-4a32-8cc2-7d74e39229ff'
        }
      },
      {
        idJson: 'd8f95923-9b23-415a-97c6-e749d5126656',
        ativado: true,
        grupoSemaforico: {
          idJson: 'cd5f89f1-1051-4075-82ca-b861b0843b5e'
        },
        plano: {
          idJson: 'b52d3443-2b72-4a32-8cc2-7d74e39229ff'
        }
      },
      {
        idJson: '172ce5a9-8a96-4a8e-9259-bdef95dca85a',
        ativado: true,
        grupoSemaforico: {
          idJson: 'ed58bdd3-f639-426e-b400-46d3d64b0a58'
        },
        plano: {
          idJson: 'b52d3443-2b72-4a32-8cc2-7d74e39229ff'
        }
      },
      {
        idJson: '27f00621-2ec1-427e-b712-8d30c9a6443b',
        ativado: true,
        grupoSemaforico: {
          idJson: '5f53a090-d3e5-4b4d-bbde-db990caedaf2'
        },
        plano: {
          idJson: 'b52d3443-2b72-4a32-8cc2-7d74e39229ff'
        }
      },
      {
        idJson: '55fe58d5-6f88-4a2a-8a5a-c8f6f6906cd6',
        ativado: true,
        grupoSemaforico: {
          idJson: 'e6f0363e-8d8c-4e45-936e-380b56791af7'
        },
        plano: {
          idJson: '16d44222-79bd-4983-81d8-ee68934a2492'
        }
      },
      {
        idJson: '8654c573-80b0-4cf1-8ae5-551e97d95a00',
        ativado: true,
        grupoSemaforico: {
          idJson: '3b33af34-da3c-4803-80c4-b21bca918c2a'
        },
        plano: {
          idJson: '16d44222-79bd-4983-81d8-ee68934a2492'
        }
      },
      {
        idJson: '91109c4a-1e4b-43fd-a286-fc314b4d3585',
        ativado: true,
        grupoSemaforico: {
          idJson: 'cd5f89f1-1051-4075-82ca-b861b0843b5e'
        },
        plano: {
          idJson: '16d44222-79bd-4983-81d8-ee68934a2492'
        }
      },
      {
        idJson: '343eec80-71d5-40c8-b505-28e96281f9dd',
        ativado: true,
        grupoSemaforico: {
          idJson: 'ed58bdd3-f639-426e-b400-46d3d64b0a58'
        },
        plano: {
          idJson: '16d44222-79bd-4983-81d8-ee68934a2492'
        }
      },
      {
        idJson: '20e687f4-9d57-45ed-8b5b-a02992edeb3d',
        ativado: true,
        grupoSemaforico: {
          idJson: '5f53a090-d3e5-4b4d-bbde-db990caedaf2'
        },
        plano: {
          idJson: '16d44222-79bd-4983-81d8-ee68934a2492'
        }
      },
      {
        idJson: 'd41e0017-e968-4e7e-93df-678c671e87e4',
        ativado: true,
        grupoSemaforico: {
          idJson: 'e6f0363e-8d8c-4e45-936e-380b56791af7'
        },
        plano: {
          idJson: '998c7003-2267-426d-96d9-4de0518c8796'
        }
      },
      {
        idJson: '1415bf45-3a33-4f0f-8fde-ed29ef289e4a',
        ativado: true,
        grupoSemaforico: {
          idJson: '3b33af34-da3c-4803-80c4-b21bca918c2a'
        },
        plano: {
          idJson: '998c7003-2267-426d-96d9-4de0518c8796'
        }
      },
      {
        idJson: '27e19638-0ea0-46ad-888b-f8081a36ef48',
        ativado: true,
        grupoSemaforico: {
          idJson: 'cd5f89f1-1051-4075-82ca-b861b0843b5e'
        },
        plano: {
          idJson: '998c7003-2267-426d-96d9-4de0518c8796'
        }
      },
      {
        idJson: 'ac5dfc56-f2fa-440e-aded-94470d10775e',
        ativado: true,
        grupoSemaforico: {
          idJson: 'ed58bdd3-f639-426e-b400-46d3d64b0a58'
        },
        plano: {
          idJson: '998c7003-2267-426d-96d9-4de0518c8796'
        }
      },
      {
        idJson: 'ce977361-f065-4d45-b84c-fe66b3c74d97',
        ativado: true,
        grupoSemaforico: {
          idJson: '5f53a090-d3e5-4b4d-bbde-db990caedaf2'
        },
        plano: {
          idJson: '998c7003-2267-426d-96d9-4de0518c8796'
        }
      },
      {
        idJson: '25e9ffa0-56a0-4f29-b1be-ffc435493d7f',
        ativado: true,
        grupoSemaforico: {
          idJson: 'e6f0363e-8d8c-4e45-936e-380b56791af7'
        },
        plano: {
          idJson: '2cf6d09f-e785-4ebd-af17-04e7ce5c72d3'
        }
      },
      {
        idJson: '8236888f-7972-40d4-9c9f-2b7e89d6f61c',
        ativado: true,
        grupoSemaforico: {
          idJson: '3b33af34-da3c-4803-80c4-b21bca918c2a'
        },
        plano: {
          idJson: '2cf6d09f-e785-4ebd-af17-04e7ce5c72d3'
        }
      },
      {
        idJson: '14904e05-1fe0-48af-9e89-b582ab1f0b1f',
        ativado: true,
        grupoSemaforico: {
          idJson: 'cd5f89f1-1051-4075-82ca-b861b0843b5e'
        },
        plano: {
          idJson: '2cf6d09f-e785-4ebd-af17-04e7ce5c72d3'
        }
      },
      {
        idJson: '14dff6d6-a8f8-4cd9-86b4-f6c718568096',
        ativado: true,
        grupoSemaforico: {
          idJson: 'ed58bdd3-f639-426e-b400-46d3d64b0a58'
        },
        plano: {
          idJson: '2cf6d09f-e785-4ebd-af17-04e7ce5c72d3'
        }
      },
      {
        idJson: '2db8646c-ee21-4e62-ac00-058b68828fe5',
        ativado: true,
        grupoSemaforico: {
          idJson: '5f53a090-d3e5-4b4d-bbde-db990caedaf2'
        },
        plano: {
          idJson: '2cf6d09f-e785-4ebd-af17-04e7ce5c72d3'
        }
      },
      {
        idJson: 'b66063a1-3283-4d1d-9ab4-31bf6f70ea9c',
        ativado: true,
        grupoSemaforico: {
          idJson: 'e6f0363e-8d8c-4e45-936e-380b56791af7'
        },
        plano: {
          idJson: '6ee596d6-9d6e-43d6-80c1-99b661388e1f'
        }
      },
      {
        idJson: '60e70c08-ad38-4c04-9320-c5b17bb7b331',
        ativado: true,
        grupoSemaforico: {
          idJson: '3b33af34-da3c-4803-80c4-b21bca918c2a'
        },
        plano: {
          idJson: '6ee596d6-9d6e-43d6-80c1-99b661388e1f'
        }
      },
      {
        idJson: 'baa512f3-15fa-440d-9e85-5440fe09aca4',
        ativado: true,
        grupoSemaforico: {
          idJson: 'cd5f89f1-1051-4075-82ca-b861b0843b5e'
        },
        plano: {
          idJson: '6ee596d6-9d6e-43d6-80c1-99b661388e1f'
        }
      },
      {
        idJson: '267d2a22-531d-4c85-819d-d5f85661b67b',
        ativado: true,
        grupoSemaforico: {
          idJson: 'ed58bdd3-f639-426e-b400-46d3d64b0a58'
        },
        plano: {
          idJson: '6ee596d6-9d6e-43d6-80c1-99b661388e1f'
        }
      },
      {
        idJson: '308dc89c-cda8-4470-aec2-f089daae4985',
        ativado: true,
        grupoSemaforico: {
          idJson: '5f53a090-d3e5-4b4d-bbde-db990caedaf2'
        },
        plano: {
          idJson: '6ee596d6-9d6e-43d6-80c1-99b661388e1f'
        }
      },
      {
        idJson: '54443aab-a290-46ab-bb31-db6ef4509447',
        ativado: true,
        grupoSemaforico: {
          idJson: 'e6f0363e-8d8c-4e45-936e-380b56791af7'
        },
        plano: {
          idJson: '8ad1eec4-992b-49a9-bf83-adc2ad1465d5'
        }
      },
      {
        idJson: '4eb88c99-2309-4fbc-8a4a-aac1d85e8dde',
        ativado: true,
        grupoSemaforico: {
          idJson: '3b33af34-da3c-4803-80c4-b21bca918c2a'
        },
        plano: {
          idJson: '8ad1eec4-992b-49a9-bf83-adc2ad1465d5'
        }
      },
      {
        idJson: 'bbdf96b6-d62c-4fe3-a6b9-840c90c62a91',
        ativado: true,
        grupoSemaforico: {
          idJson: 'cd5f89f1-1051-4075-82ca-b861b0843b5e'
        },
        plano: {
          idJson: '8ad1eec4-992b-49a9-bf83-adc2ad1465d5'
        }
      },
      {
        idJson: '50a73703-4d9b-4985-92cb-fdcc1e5e7ec4',
        ativado: true,
        grupoSemaforico: {
          idJson: 'ed58bdd3-f639-426e-b400-46d3d64b0a58'
        },
        plano: {
          idJson: '8ad1eec4-992b-49a9-bf83-adc2ad1465d5'
        }
      },
      {
        idJson: '10a0e61e-28e4-45da-8d64-9e841d7fc470',
        ativado: true,
        grupoSemaforico: {
          idJson: '5f53a090-d3e5-4b4d-bbde-db990caedaf2'
        },
        plano: {
          idJson: '8ad1eec4-992b-49a9-bf83-adc2ad1465d5'
        }
      },
      {
        idJson: '0f5d45e2-e3ed-4f6e-b503-008639c3278c',
        ativado: true,
        grupoSemaforico: {
          idJson: 'e6f0363e-8d8c-4e45-936e-380b56791af7'
        },
        plano: {
          idJson: '10433f86-f1a3-4694-b2b0-990f0702a5d4'
        }
      },
      {
        idJson: '6b273636-1a9e-49d6-ae06-c14e2d6d1bef',
        ativado: true,
        grupoSemaforico: {
          idJson: '3b33af34-da3c-4803-80c4-b21bca918c2a'
        },
        plano: {
          idJson: '10433f86-f1a3-4694-b2b0-990f0702a5d4'
        }
      },
      {
        idJson: '117693aa-d071-4ad5-bc37-2b0fe132581f',
        ativado: true,
        grupoSemaforico: {
          idJson: 'cd5f89f1-1051-4075-82ca-b861b0843b5e'
        },
        plano: {
          idJson: '10433f86-f1a3-4694-b2b0-990f0702a5d4'
        }
      },
      {
        idJson: '0fc0a44c-631f-4507-96b2-88e31cb2baa9',
        ativado: true,
        grupoSemaforico: {
          idJson: 'ed58bdd3-f639-426e-b400-46d3d64b0a58'
        },
        plano: {
          idJson: '10433f86-f1a3-4694-b2b0-990f0702a5d4'
        }
      },
      {
        idJson: 'baac6ca4-8f3c-43aa-96ca-92f07fd8f0af',
        ativado: true,
        grupoSemaforico: {
          idJson: '5f53a090-d3e5-4b4d-bbde-db990caedaf2'
        },
        plano: {
          idJson: '10433f86-f1a3-4694-b2b0-990f0702a5d4'
        }
      }
    ],
    estagiosPlanos: [
      {
        id: '04ed8ff6-ef45-4900-8567-c851079aa291',
        idJson: 'bb245c87-9ff3-4d01-9dc1-fd8d66d14a7d',
        posicao: 2,
        tempoVerde: 4,
        dispensavel: false,
        plano: {
          idJson: 'd9d40a04-0d63-4927-88a9-f3b247f628a0'
        },
        estagio: {
          idJson: 'af5f7e28-15c7-4902-96e1-c8184c4ee3cd'
        }
      },
      {
        id: '0701169b-e8ad-4d7e-8baa-4fb970494cf0',
        idJson: '7f930463-063c-40ea-b734-425ac45f771f',
        posicao: 1,
        tempoVerde: 54,
        dispensavel: false,
        plano: {
          idJson: 'fbed2d5c-8df6-46d7-8b3a-03b4d9d75a9e'
        },
        estagio: {
          idJson: '4d562187-7201-4590-b365-bf3621e7cd86'
        },
        origemTransicaoProibida: false,
        destinoTransicaoProibida: false,
        tempoEstagio: 60,
        posicaoEstagio: 2
      },
      {
        id: '8d4a3a6c-50e6-4a80-85b8-01b576307799',
        idJson: 'e74603b4-c9cb-4566-a3f4-400a765a1fad',
        posicao: 2,
        tempoVerde: 14,
        dispensavel: true,
        plano: {
          idJson: 'd7ff9f27-1132-476d-a622-744038650be9'
        },
        estagio: {
          idJson: 'af5f7e28-15c7-4902-96e1-c8184c4ee3cd'
        },
        origemTransicaoProibida: false,
        destinoTransicaoProibida: false,
        tempoEstagio: 20
      },
      {
        id: 'eada1d8c-1b10-4dd5-9ff9-b4d19c2201f9',
        idJson: 'b589c696-1b6a-4e88-8545-aaf56e32a17e',
        posicao: 3,
        tempoVerde: 10,
        dispensavel: false,
        plano: {
          idJson: 'b5283d44-2b17-4fcb-8277-336fb250a7d1'
        },
        estagio: {
          idJson: 'e225807c-145e-49ce-9e97-1906c3386af5'
        }
      },
      {
        id: 'b6e1c61b-c3f8-4714-88f8-c60be83d6b56',
        idJson: '1fd9be5d-ca7d-435d-a922-5d9bb7890165',
        posicao: 3,
        tempoVerde: 40,
        dispensavel: false,
        plano: {
          idJson: 'd7ff9f27-1132-476d-a622-744038650be9'
        },
        estagio: {
          idJson: '9ecae09c-42ec-443d-a7b9-b43ed392bee7'
        },
        origemTransicaoProibida: false,
        destinoTransicaoProibida: false,
        tempoEstagio: 50
      },
      {
        id: '0c8a5592-90cd-4f1d-b60f-47e2e889761a',
        idJson: 'a9e770d0-d3b5-4236-82af-c675dbf7533a',
        posicao: 1,
        tempoVerde: 40,
        dispensavel: false,
        plano: {
          idJson: 'd7ff9f27-1132-476d-a622-744038650be9'
        },
        estagio: {
          idJson: 'f1015990-bac7-4a65-9dbc-8f8a4472fd5f'
        },
        origemTransicaoProibida: false,
        destinoTransicaoProibida: false,
        tempoEstagio: 50
      },
      {
        id: '13e8a033-2db6-4d01-a788-16e8d6f35041',
        idJson: '83f5a7ee-a0b8-45fb-a796-1ec5c081b438',
        posicao: 1,
        tempoVerde: 54,
        tempoVerdeMinimo: 0,
        tempoVerdeMaximo: 0,
        tempoVerdeIntermediario: 0,
        tempoExtensaoVerde: 0,
        dispensavel: false,
        plano: {
          idJson: '68d09bed-8168-49d5-ac93-c05c4a4c6db6'
        },
        estagio: {
          idJson: '4d562187-7201-4590-b365-bf3621e7cd86'
        }
      },
      {
        id: '4babf546-4277-471b-babc-19d3fa4aac1a',
        idJson: '4d294d21-7b05-409c-8539-5964d9eae43f',
        posicao: 1,
        tempoVerde: 40,
        tempoVerdeMinimo: 0,
        tempoVerdeMaximo: 0,
        tempoVerdeIntermediario: 0,
        tempoExtensaoVerde: 0,
        dispensavel: false,
        plano: {
          idJson: 'd9d40a04-0d63-4927-88a9-f3b247f628a0'
        },
        estagio: {
          idJson: 'f1015990-bac7-4a65-9dbc-8f8a4472fd5f'
        }
      },
      {
        id: 'cafcf868-ceaf-4e3f-96b5-afdeb691f630',
        idJson: '07d75fdf-4afd-429e-ae84-39f351422171',
        posicao: 3,
        tempoVerde: 40,
        tempoVerdeMinimo: 0,
        tempoVerdeMaximo: 0,
        tempoVerdeIntermediario: 0,
        tempoExtensaoVerde: 0,
        dispensavel: false,
        plano: {
          idJson: 'd9d40a04-0d63-4927-88a9-f3b247f628a0'
        },
        estagio: {
          idJson: '9ecae09c-42ec-443d-a7b9-b43ed392bee7'
        }
      },
      {
        id: 'aac53792-660c-41dd-9cf2-6238b4b0355f',
        idJson: '5698a069-3670-4aad-a5f9-dfed85c9056c',
        posicao: 1,
        tempoVerde: 10,
        dispensavel: false,
        plano: {
          idJson: 'b5283d44-2b17-4fcb-8277-336fb250a7d1'
        },
        estagio: {
          idJson: 'e225807c-145e-49ce-9e97-1906c3386af5'
        }
      },
      {
        id: '11517c62-04ea-4f15-bf58-8d1d2e3e8550',
        idJson: '1b7fc601-771e-426a-be7e-6cc935d7fd85',
        posicao: 2,
        tempoVerde: 54,
        tempoVerdeMinimo: 0,
        tempoVerdeMaximo: 0,
        tempoVerdeIntermediario: 0,
        tempoExtensaoVerde: 0,
        dispensavel: false,
        plano: {
          idJson: '68d09bed-8168-49d5-ac93-c05c4a4c6db6'
        },
        estagio: {
          idJson: 'e225807c-145e-49ce-9e97-1906c3386af5'
        }
      },
      {
        id: 'f82dad64-8d1e-42fd-bf50-18dcdcde9b53',
        idJson: 'afa02227-9d2f-4f76-9cc7-69ef8e2c7957',
        posicao: 3,
        tempoVerde: 10,
        dispensavel: false,
        plano: {
          idJson: 'f6c3f670-b93e-4528-91c8-86709ae600cb'
        },
        estagio: {
          idJson: '9ecae09c-42ec-443d-a7b9-b43ed392bee7'
        },
        origemTransicaoProibida: false,
        destinoTransicaoProibida: false
      },
      {
        id: '56ada7f8-1547-47fa-b8f2-14f3220afc37',
        idJson: '76727d75-dff7-4c21-b781-7b137b4d8328',
        posicao: 4,
        tempoVerde: 14,
        tempoVerdeMinimo: 0,
        tempoVerdeMaximo: 0,
        tempoVerdeIntermediario: 0,
        tempoExtensaoVerde: 0,
        dispensavel: true,
        plano: {
          idJson: 'd9d40a04-0d63-4927-88a9-f3b247f628a0'
        },
        estagio: {
          idJson: 'af5f7e28-15c7-4902-96e1-c8184c4ee3cd'
        }
      },
      {
        id: '1b096b28-0f4c-4960-b27e-2824a119f847',
        idJson: '829e583c-6205-49d2-9fe3-acb4e1292198',
        posicao: 2,
        tempoVerde: 10,
        dispensavel: false,
        plano: {
          idJson: 'b5283d44-2b17-4fcb-8277-336fb250a7d1'
        },
        estagio: {
          idJson: '4d562187-7201-4590-b365-bf3621e7cd86'
        }
      },
      {
        id: 'f9b36359-4abb-4cdc-8d3b-4fdafda5b300',
        idJson: 'b8679294-3ad2-4c20-bc3e-16c7eff7cf45',
        posicao: 2,
        tempoVerde: 4,
        dispensavel: false,
        plano: {
          idJson: 'f6c3f670-b93e-4528-91c8-86709ae600cb'
        },
        estagio: {
          idJson: 'af5f7e28-15c7-4902-96e1-c8184c4ee3cd'
        },
        origemTransicaoProibida: false,
        destinoTransicaoProibida: false
      },
      {
        id: 'cddeb726-f538-4f42-a60a-930411135c7b',
        idJson: '90e6805a-1d1c-49de-977e-2cc83fa8e803',
        posicao: 1,
        tempoVerde: 10,
        dispensavel: false,
        plano: {
          idJson: 'f6c3f670-b93e-4528-91c8-86709ae600cb'
        },
        estagio: {
          idJson: 'f1015990-bac7-4a65-9dbc-8f8a4472fd5f'
        },
        origemTransicaoProibida: false,
        destinoTransicaoProibida: false
      },
      {
        idJson: 'db0df932-27a6-4cff-80e3-f9b5d8e2dee8',
        estagio: {
          idJson: 'f1015990-bac7-4a65-9dbc-8f8a4472fd5f'
        },
        plano: {
          idJson: '7d491161-bb12-4673-b8e9-324f88f629cd'
        },
        posicao: 1,
        tempoVerde: 10,
        dispensavel: false
      },
      {
        idJson: '292b008a-dd16-4984-877c-b54e035c9db9',
        estagio: {
          idJson: '9ecae09c-42ec-443d-a7b9-b43ed392bee7'
        },
        plano: {
          idJson: '7d491161-bb12-4673-b8e9-324f88f629cd'
        },
        posicao: 3,
        tempoVerde: 10,
        dispensavel: false
      },
      {
        idJson: 'b7929edc-faf6-4100-9727-a22fe6b18f3d',
        estagio: {
          idJson: 'af5f7e28-15c7-4902-96e1-c8184c4ee3cd'
        },
        plano: {
          idJson: '7d491161-bb12-4673-b8e9-324f88f629cd'
        },
        posicao: 2,
        tempoVerde: 4,
        dispensavel: false
      },
      {
        idJson: 'a22e9261-c5c9-438d-adc4-2042344c8633',
        estagio: {
          idJson: 'f1015990-bac7-4a65-9dbc-8f8a4472fd5f'
        },
        plano: {
          idJson: '2ba66e27-b6e4-4571-b2e2-0bf77be547b1'
        },
        posicao: 1,
        tempoVerde: 10,
        dispensavel: false
      },
      {
        idJson: '0235c784-695b-4a5b-91aa-b7f34ac63ab8',
        estagio: {
          idJson: '9ecae09c-42ec-443d-a7b9-b43ed392bee7'
        },
        plano: {
          idJson: '2ba66e27-b6e4-4571-b2e2-0bf77be547b1'
        },
        posicao: 3,
        tempoVerde: 10,
        dispensavel: false
      },
      {
        idJson: 'c295a5c0-c271-4dd9-a684-b07ee681bb76',
        estagio: {
          idJson: 'af5f7e28-15c7-4902-96e1-c8184c4ee3cd'
        },
        plano: {
          idJson: '2ba66e27-b6e4-4571-b2e2-0bf77be547b1'
        },
        posicao: 2,
        tempoVerde: 4,
        dispensavel: false
      },
      {
        idJson: '1d7dad29-6124-4c3f-b704-da64ff9687ee',
        estagio: {
          idJson: 'f1015990-bac7-4a65-9dbc-8f8a4472fd5f'
        },
        plano: {
          idJson: '9c0bae10-1b9f-422c-a063-aa0c305b25d0'
        },
        posicao: 1,
        tempoVerde: 10,
        dispensavel: false
      },
      {
        idJson: 'd2988fe0-5499-4946-8b5d-5104fde769c5',
        estagio: {
          idJson: '9ecae09c-42ec-443d-a7b9-b43ed392bee7'
        },
        plano: {
          idJson: '9c0bae10-1b9f-422c-a063-aa0c305b25d0'
        },
        posicao: 3,
        tempoVerde: 10,
        dispensavel: false
      },
      {
        idJson: '7f07e849-bc02-4671-a49e-eae543f30bde',
        estagio: {
          idJson: 'af5f7e28-15c7-4902-96e1-c8184c4ee3cd'
        },
        plano: {
          idJson: '9c0bae10-1b9f-422c-a063-aa0c305b25d0'
        },
        posicao: 2,
        tempoVerde: 4,
        dispensavel: false
      },
      {
        idJson: '1335a376-ac2c-4cb5-a7b7-74446886f33d',
        estagio: {
          idJson: 'f1015990-bac7-4a65-9dbc-8f8a4472fd5f'
        },
        plano: {
          idJson: '233c11dc-b927-4df2-a4b8-7e2875061970'
        },
        posicao: 1,
        tempoVerde: 10,
        dispensavel: false
      },
      {
        idJson: '06d69eda-5ade-475b-994b-183be4f25a15',
        estagio: {
          idJson: '9ecae09c-42ec-443d-a7b9-b43ed392bee7'
        },
        plano: {
          idJson: '233c11dc-b927-4df2-a4b8-7e2875061970'
        },
        posicao: 3,
        tempoVerde: 10,
        dispensavel: false
      },
      {
        idJson: 'e39396f6-186e-4d84-b107-2f1ab4976e8d',
        estagio: {
          idJson: 'af5f7e28-15c7-4902-96e1-c8184c4ee3cd'
        },
        plano: {
          idJson: '233c11dc-b927-4df2-a4b8-7e2875061970'
        },
        posicao: 2,
        tempoVerde: 4,
        dispensavel: false
      },
      {
        idJson: '48f69f7e-b6e3-4fd5-a7c5-89ed664de103',
        estagio: {
          idJson: 'f1015990-bac7-4a65-9dbc-8f8a4472fd5f'
        },
        plano: {
          idJson: 'c8b26060-acd7-44c3-8e92-cbdb316d6deb'
        },
        posicao: 1,
        tempoVerde: 10,
        dispensavel: false
      },
      {
        idJson: 'beab5567-bb11-4d97-86d9-017a042108fb',
        estagio: {
          idJson: '9ecae09c-42ec-443d-a7b9-b43ed392bee7'
        },
        plano: {
          idJson: 'c8b26060-acd7-44c3-8e92-cbdb316d6deb'
        },
        posicao: 3,
        tempoVerde: 10,
        dispensavel: false
      },
      {
        idJson: '91687aaa-3d1e-4974-b798-6118b1ebb140',
        estagio: {
          idJson: 'af5f7e28-15c7-4902-96e1-c8184c4ee3cd'
        },
        plano: {
          idJson: 'c8b26060-acd7-44c3-8e92-cbdb316d6deb'
        },
        posicao: 2,
        tempoVerde: 4,
        dispensavel: false
      },
      {
        idJson: '8517b095-38f0-46b2-bd79-940d6fc9b49f',
        estagio: {
          idJson: 'f1015990-bac7-4a65-9dbc-8f8a4472fd5f'
        },
        plano: {
          idJson: 'a891c685-9285-4257-bed1-16f25f044c24'
        },
        posicao: 1,
        tempoVerde: 10,
        dispensavel: false
      },
      {
        idJson: '61b690c9-8273-46d0-96c9-18ba45016293',
        estagio: {
          idJson: '9ecae09c-42ec-443d-a7b9-b43ed392bee7'
        },
        plano: {
          idJson: 'a891c685-9285-4257-bed1-16f25f044c24'
        },
        posicao: 3,
        tempoVerde: 10,
        dispensavel: false
      },
      {
        idJson: '720a99a1-e280-440b-9d97-79ac3172a63b',
        estagio: {
          idJson: 'af5f7e28-15c7-4902-96e1-c8184c4ee3cd'
        },
        plano: {
          idJson: 'a891c685-9285-4257-bed1-16f25f044c24'
        },
        posicao: 2,
        tempoVerde: 4,
        dispensavel: false
      },
      {
        idJson: '995cb4d3-7929-4846-88e7-affd76f16bc2',
        estagio: {
          idJson: 'f1015990-bac7-4a65-9dbc-8f8a4472fd5f'
        },
        plano: {
          idJson: '5270dfac-b24b-4ba0-9c79-0435c74301d5'
        },
        posicao: 1,
        tempoVerde: 10,
        dispensavel: false
      },
      {
        idJson: 'd77c12bb-0e79-4e10-bd64-1cc91d866e2a',
        estagio: {
          idJson: '9ecae09c-42ec-443d-a7b9-b43ed392bee7'
        },
        plano: {
          idJson: '5270dfac-b24b-4ba0-9c79-0435c74301d5'
        },
        posicao: 3,
        tempoVerde: 10,
        dispensavel: false
      },
      {
        idJson: '23e66c75-d5fb-44eb-b67d-bef58e66a1f3',
        estagio: {
          idJson: 'af5f7e28-15c7-4902-96e1-c8184c4ee3cd'
        },
        plano: {
          idJson: '5270dfac-b24b-4ba0-9c79-0435c74301d5'
        },
        posicao: 2,
        tempoVerde: 4,
        dispensavel: false
      },
      {
        idJson: '46578167-43fc-4070-b372-9e7c19d83033',
        estagio: {
          idJson: 'f1015990-bac7-4a65-9dbc-8f8a4472fd5f'
        },
        plano: {
          idJson: '0ce21c68-24c6-4d17-81f2-88c581931326'
        },
        posicao: 1,
        tempoVerde: 10,
        dispensavel: false
      },
      {
        idJson: '1a73bc65-89a9-43b2-8f50-07ef6d0d3157',
        estagio: {
          idJson: '9ecae09c-42ec-443d-a7b9-b43ed392bee7'
        },
        plano: {
          idJson: '0ce21c68-24c6-4d17-81f2-88c581931326'
        },
        posicao: 3,
        tempoVerde: 10,
        dispensavel: false
      },
      {
        idJson: 'f97e5596-594b-4f78-9007-7d0077ce9db4',
        estagio: {
          idJson: 'af5f7e28-15c7-4902-96e1-c8184c4ee3cd'
        },
        plano: {
          idJson: '0ce21c68-24c6-4d17-81f2-88c581931326'
        },
        posicao: 2,
        tempoVerde: 4,
        dispensavel: false
      },
      {
        idJson: '11285659-fa8b-43df-9ee4-0e0ccdd1fb04',
        estagio: {
          idJson: 'f1015990-bac7-4a65-9dbc-8f8a4472fd5f'
        },
        plano: {
          idJson: 'bda5c85c-63d5-4fe7-8f6d-7382f8b2152c'
        },
        posicao: 1,
        tempoVerde: 10,
        dispensavel: false
      },
      {
        idJson: 'f7ce25fc-bb0a-4362-b2f7-8f76ffdbc849',
        estagio: {
          idJson: '9ecae09c-42ec-443d-a7b9-b43ed392bee7'
        },
        plano: {
          idJson: 'bda5c85c-63d5-4fe7-8f6d-7382f8b2152c'
        },
        posicao: 3,
        tempoVerde: 10,
        dispensavel: false
      },
      {
        idJson: '4f53e516-6849-450b-84a3-98118df7bada',
        estagio: {
          idJson: 'af5f7e28-15c7-4902-96e1-c8184c4ee3cd'
        },
        plano: {
          idJson: 'bda5c85c-63d5-4fe7-8f6d-7382f8b2152c'
        },
        posicao: 2,
        tempoVerde: 4,
        dispensavel: false
      },
      {
        idJson: '7e83ce93-0772-49a9-a707-f68aea6f1d56',
        estagio: {
          idJson: 'f1015990-bac7-4a65-9dbc-8f8a4472fd5f'
        },
        plano: {
          idJson: 'd8ac0504-ac63-44a2-a6b1-efb33f491da6'
        },
        posicao: 1,
        tempoVerde: 10,
        dispensavel: false
      },
      {
        idJson: '267d7ea8-950e-4f49-b037-f899acf842e7',
        estagio: {
          idJson: '9ecae09c-42ec-443d-a7b9-b43ed392bee7'
        },
        plano: {
          idJson: 'd8ac0504-ac63-44a2-a6b1-efb33f491da6'
        },
        posicao: 3,
        tempoVerde: 10,
        dispensavel: false
      },
      {
        idJson: 'b59ded38-1340-4b82-83ba-37632ec6dfd0',
        estagio: {
          idJson: 'af5f7e28-15c7-4902-96e1-c8184c4ee3cd'
        },
        plano: {
          idJson: 'd8ac0504-ac63-44a2-a6b1-efb33f491da6'
        },
        posicao: 2,
        tempoVerde: 4,
        dispensavel: false
      },
      {
        idJson: '433144f8-d63b-40a6-82cc-555e3533f6f0',
        estagio: {
          idJson: 'f1015990-bac7-4a65-9dbc-8f8a4472fd5f'
        },
        plano: {
          idJson: '2819a411-92ab-4654-8ef2-001e10b9fb84'
        },
        posicao: 1,
        tempoVerde: 10,
        dispensavel: false
      },
      {
        idJson: 'c02ea1be-2467-4133-af8a-f830c3319b8b',
        estagio: {
          idJson: '9ecae09c-42ec-443d-a7b9-b43ed392bee7'
        },
        plano: {
          idJson: '2819a411-92ab-4654-8ef2-001e10b9fb84'
        },
        posicao: 3,
        tempoVerde: 10,
        dispensavel: false
      },
      {
        idJson: '174a46c8-e710-4980-bb7c-b83f3345f5da',
        estagio: {
          idJson: 'af5f7e28-15c7-4902-96e1-c8184c4ee3cd'
        },
        plano: {
          idJson: '2819a411-92ab-4654-8ef2-001e10b9fb84'
        },
        posicao: 2,
        tempoVerde: 4,
        dispensavel: false
      },
      {
        idJson: 'd9a1c38d-e5ee-467f-8fd5-12e4cf6022ce',
        estagio: {
          idJson: 'f1015990-bac7-4a65-9dbc-8f8a4472fd5f'
        },
        plano: {
          idJson: '14c59533-b7f5-4205-bed1-4e0e99bebd42'
        },
        posicao: 1,
        tempoVerde: 10,
        dispensavel: false
      },
      {
        idJson: 'fe628791-13d2-4acd-a79d-9d7ea85083c8',
        estagio: {
          idJson: '9ecae09c-42ec-443d-a7b9-b43ed392bee7'
        },
        plano: {
          idJson: '14c59533-b7f5-4205-bed1-4e0e99bebd42'
        },
        posicao: 3,
        tempoVerde: 10,
        dispensavel: false
      },
      {
        idJson: 'ca29ee58-a843-462b-abd7-c9f1ba76d373',
        estagio: {
          idJson: 'af5f7e28-15c7-4902-96e1-c8184c4ee3cd'
        },
        plano: {
          idJson: '14c59533-b7f5-4205-bed1-4e0e99bebd42'
        },
        posicao: 2,
        tempoVerde: 4,
        dispensavel: false
      },
      {
        idJson: '2aa91706-d94f-4ffc-a168-5d3eed667de5',
        estagio: {
          idJson: 'f1015990-bac7-4a65-9dbc-8f8a4472fd5f'
        },
        plano: {
          idJson: 'dd976fc4-da72-4dec-89c9-9aa7b15ebb42'
        },
        posicao: 1,
        tempoVerde: 10,
        dispensavel: false
      },
      {
        idJson: 'ad73d676-f4e4-47f9-840d-adccec6dbb70',
        estagio: {
          idJson: '9ecae09c-42ec-443d-a7b9-b43ed392bee7'
        },
        plano: {
          idJson: 'dd976fc4-da72-4dec-89c9-9aa7b15ebb42'
        },
        posicao: 3,
        tempoVerde: 10,
        dispensavel: false
      },
      {
        idJson: 'c4727609-4efe-48ab-8882-5f642579abfd',
        estagio: {
          idJson: 'af5f7e28-15c7-4902-96e1-c8184c4ee3cd'
        },
        plano: {
          idJson: 'dd976fc4-da72-4dec-89c9-9aa7b15ebb42'
        },
        posicao: 2,
        tempoVerde: 4,
        dispensavel: false
      },
      {
        idJson: '2319e19e-9a61-4418-b306-08d5b25b79e2',
        estagio: {
          idJson: 'f1015990-bac7-4a65-9dbc-8f8a4472fd5f'
        },
        plano: {
          idJson: '6f68ea04-988c-4d50-b439-3ad02905eff1'
        },
        posicao: 1,
        tempoVerde: 10,
        dispensavel: false
      },
      {
        idJson: '78981ba3-e4da-4234-9dc5-c32ba1778bfc',
        estagio: {
          idJson: '9ecae09c-42ec-443d-a7b9-b43ed392bee7'
        },
        plano: {
          idJson: '6f68ea04-988c-4d50-b439-3ad02905eff1'
        },
        posicao: 3,
        tempoVerde: 10,
        dispensavel: false
      },
      {
        idJson: '5851baef-fbd1-4e66-abfc-a8570f031a76',
        estagio: {
          idJson: 'af5f7e28-15c7-4902-96e1-c8184c4ee3cd'
        },
        plano: {
          idJson: '6f68ea04-988c-4d50-b439-3ad02905eff1'
        },
        posicao: 2,
        tempoVerde: 4,
        dispensavel: false
      },
      {
        idJson: '55dbffa7-2657-4ccb-925d-f549805b87c3',
        estagio: {
          idJson: 'e225807c-145e-49ce-9e97-1906c3386af5'
        },
        plano: {
          idJson: '398aa468-075d-49fc-b940-36886add16a3'
        },
        posicao: 3,
        tempoVerde: 10,
        dispensavel: false
      },
      {
        idJson: '5c0d56cb-7cf2-4126-9dd8-4a90f72112ba',
        estagio: {
          idJson: '4d562187-7201-4590-b365-bf3621e7cd86'
        },
        plano: {
          idJson: '398aa468-075d-49fc-b940-36886add16a3'
        },
        posicao: 2,
        tempoVerde: 10,
        dispensavel: false
      },
      {
        idJson: '477adc6c-c434-4571-9c99-aa0910c95163',
        estagio: {
          idJson: 'e225807c-145e-49ce-9e97-1906c3386af5'
        },
        plano: {
          idJson: '0a64f4df-54fd-4097-963e-571d65e51123'
        },
        posicao: 3,
        tempoVerde: 10,
        dispensavel: false
      },
      {
        idJson: 'e510dad8-bf1d-4b3a-b6bc-bfda1ff174bb',
        estagio: {
          idJson: '4d562187-7201-4590-b365-bf3621e7cd86'
        },
        plano: {
          idJson: '0a64f4df-54fd-4097-963e-571d65e51123'
        },
        posicao: 2,
        tempoVerde: 10,
        dispensavel: false
      },
      {
        idJson: 'e13b024b-81d5-4dbd-9f6c-7d336b3b77f5',
        estagio: {
          idJson: 'e225807c-145e-49ce-9e97-1906c3386af5'
        },
        plano: {
          idJson: 'f984986c-37f5-44bd-840a-dc94c8a0d850'
        },
        posicao: 3,
        tempoVerde: 10,
        dispensavel: false
      },
      {
        idJson: '9b1e0d32-5cf6-4470-a5fa-17dfe27b40db',
        estagio: {
          idJson: '4d562187-7201-4590-b365-bf3621e7cd86'
        },
        plano: {
          idJson: 'f984986c-37f5-44bd-840a-dc94c8a0d850'
        },
        posicao: 2,
        tempoVerde: 10,
        dispensavel: false
      },
      {
        idJson: '8862d9f1-6934-468c-b23f-b67b9fe7c68e',
        estagio: {
          idJson: 'e225807c-145e-49ce-9e97-1906c3386af5'
        },
        plano: {
          idJson: '6780c087-42c3-42df-aa25-fad41b46f03b'
        },
        posicao: 3,
        tempoVerde: 10,
        dispensavel: false
      },
      {
        idJson: '58629998-6d1e-41e7-8e5c-805a249a943e',
        estagio: {
          idJson: '4d562187-7201-4590-b365-bf3621e7cd86'
        },
        plano: {
          idJson: '6780c087-42c3-42df-aa25-fad41b46f03b'
        },
        posicao: 2,
        tempoVerde: 10,
        dispensavel: false
      },
      {
        idJson: '7d47bf74-37ba-4a82-92ab-261301df8094',
        estagio: {
          idJson: 'e225807c-145e-49ce-9e97-1906c3386af5'
        },
        plano: {
          idJson: '6781daa5-8106-4f87-b20d-027b2ee92907'
        },
        posicao: 3,
        tempoVerde: 10,
        dispensavel: false
      },
      {
        idJson: '7e63b61d-9e80-434d-a5eb-d6ebb8e67140',
        estagio: {
          idJson: '4d562187-7201-4590-b365-bf3621e7cd86'
        },
        plano: {
          idJson: '6781daa5-8106-4f87-b20d-027b2ee92907'
        },
        posicao: 2,
        tempoVerde: 10,
        dispensavel: false
      },
      {
        idJson: '56b18b3e-cb01-4069-85ad-678daad85bcf',
        estagio: {
          idJson: 'e225807c-145e-49ce-9e97-1906c3386af5'
        },
        plano: {
          idJson: '0b3062e0-3fad-4071-9a01-f01724c20a97'
        },
        posicao: 3,
        tempoVerde: 10,
        dispensavel: false
      },
      {
        idJson: 'f42e5d6d-49fa-4279-aedb-89e8e1fe2207',
        estagio: {
          idJson: '4d562187-7201-4590-b365-bf3621e7cd86'
        },
        plano: {
          idJson: '0b3062e0-3fad-4071-9a01-f01724c20a97'
        },
        posicao: 2,
        tempoVerde: 10,
        dispensavel: false
      },
      {
        idJson: '8b9fb143-c758-497c-827f-b0e4145e418d',
        estagio: {
          idJson: 'e225807c-145e-49ce-9e97-1906c3386af5'
        },
        plano: {
          idJson: '8495d409-d4f1-4e43-bb06-045c2a56c0cf'
        },
        posicao: 3,
        tempoVerde: 10,
        dispensavel: false
      },
      {
        idJson: '814bed90-5d7a-40bb-83eb-7ba34b9f946b',
        estagio: {
          idJson: '4d562187-7201-4590-b365-bf3621e7cd86'
        },
        plano: {
          idJson: '8495d409-d4f1-4e43-bb06-045c2a56c0cf'
        },
        posicao: 2,
        tempoVerde: 10,
        dispensavel: false
      },
      {
        idJson: '914b960d-dce1-4c9d-a1ca-032ee8c0e38f',
        estagio: {
          idJson: 'e225807c-145e-49ce-9e97-1906c3386af5'
        },
        plano: {
          idJson: 'b52d3443-2b72-4a32-8cc2-7d74e39229ff'
        },
        posicao: 3,
        tempoVerde: 10,
        dispensavel: false
      },
      {
        idJson: '6f41104e-7945-4050-b373-88a13d1608e4',
        estagio: {
          idJson: '4d562187-7201-4590-b365-bf3621e7cd86'
        },
        plano: {
          idJson: 'b52d3443-2b72-4a32-8cc2-7d74e39229ff'
        },
        posicao: 2,
        tempoVerde: 10,
        dispensavel: false
      },
      {
        idJson: '01320d08-19b4-49c9-8b0a-444a8ef9c937',
        estagio: {
          idJson: 'e225807c-145e-49ce-9e97-1906c3386af5'
        },
        plano: {
          idJson: '16d44222-79bd-4983-81d8-ee68934a2492'
        },
        posicao: 3,
        tempoVerde: 10,
        dispensavel: false
      },
      {
        idJson: '3a326e8d-642b-4386-ac65-ee23e6b4d5dd',
        estagio: {
          idJson: '4d562187-7201-4590-b365-bf3621e7cd86'
        },
        plano: {
          idJson: '16d44222-79bd-4983-81d8-ee68934a2492'
        },
        posicao: 2,
        tempoVerde: 10,
        dispensavel: false
      },
      {
        idJson: 'e5475b4d-afc5-47cf-817a-ddf896f3c409',
        estagio: {
          idJson: 'e225807c-145e-49ce-9e97-1906c3386af5'
        },
        plano: {
          idJson: '998c7003-2267-426d-96d9-4de0518c8796'
        },
        posicao: 3,
        tempoVerde: 10,
        dispensavel: false
      },
      {
        idJson: '94943f59-b4be-4a8a-aa0b-3bd9f2327620',
        estagio: {
          idJson: '4d562187-7201-4590-b365-bf3621e7cd86'
        },
        plano: {
          idJson: '998c7003-2267-426d-96d9-4de0518c8796'
        },
        posicao: 2,
        tempoVerde: 10,
        dispensavel: false
      },
      {
        idJson: 'ccc5c599-9bd8-474e-bd29-57447ce75c52',
        estagio: {
          idJson: 'e225807c-145e-49ce-9e97-1906c3386af5'
        },
        plano: {
          idJson: '2cf6d09f-e785-4ebd-af17-04e7ce5c72d3'
        },
        posicao: 3,
        tempoVerde: 10,
        dispensavel: false
      },
      {
        idJson: '443d6e77-7fbf-45b4-9e86-b0b7ab52caa6',
        estagio: {
          idJson: '4d562187-7201-4590-b365-bf3621e7cd86'
        },
        plano: {
          idJson: '2cf6d09f-e785-4ebd-af17-04e7ce5c72d3'
        },
        posicao: 2,
        tempoVerde: 10,
        dispensavel: false
      },
      {
        idJson: '88893e0f-a100-41f7-b3f3-114723d537f0',
        estagio: {
          idJson: 'e225807c-145e-49ce-9e97-1906c3386af5'
        },
        plano: {
          idJson: '6ee596d6-9d6e-43d6-80c1-99b661388e1f'
        },
        posicao: 3,
        tempoVerde: 10,
        dispensavel: false
      },
      {
        idJson: '54443c2a-1dea-463d-837d-e5d78c860a3b',
        estagio: {
          idJson: '4d562187-7201-4590-b365-bf3621e7cd86'
        },
        plano: {
          idJson: '6ee596d6-9d6e-43d6-80c1-99b661388e1f'
        },
        posicao: 2,
        tempoVerde: 10,
        dispensavel: false
      },
      {
        idJson: '0826c7fd-c186-4aaa-956a-2fd5fbb342de',
        estagio: {
          idJson: 'e225807c-145e-49ce-9e97-1906c3386af5'
        },
        plano: {
          idJson: '8ad1eec4-992b-49a9-bf83-adc2ad1465d5'
        },
        posicao: 3,
        tempoVerde: 10,
        dispensavel: false
      },
      {
        idJson: '97c83c31-c36e-40df-9e4d-b8b5a007ff8b',
        estagio: {
          idJson: '4d562187-7201-4590-b365-bf3621e7cd86'
        },
        plano: {
          idJson: '8ad1eec4-992b-49a9-bf83-adc2ad1465d5'
        },
        posicao: 2,
        tempoVerde: 10,
        dispensavel: false
      },
      {
        idJson: 'b8080765-891a-4e4f-b790-00b4d73c6c2d',
        estagio: {
          idJson: 'e225807c-145e-49ce-9e97-1906c3386af5'
        },
        plano: {
          idJson: '10433f86-f1a3-4694-b2b0-990f0702a5d4'
        },
        posicao: 3,
        tempoVerde: 10,
        dispensavel: false
      },
      {
        idJson: '92f64c2d-35ee-4361-b085-2313d4a043dc',
        estagio: {
          idJson: '4d562187-7201-4590-b365-bf3621e7cd86'
        },
        plano: {
          idJson: '10433f86-f1a3-4694-b2b0-990f0702a5d4'
        },
        posicao: 2,
        tempoVerde: 10,
        dispensavel: false
      },
      {
        idJson: 'd3876d77-7def-4f00-bc7e-82b8182a722f',
        estagio: {
          idJson: '4d562187-7201-4590-b365-bf3621e7cd86'
        },
        plano: {
          idJson: 'fbed2d5c-8df6-46d7-8b3a-03b4d9d75a9e'
        },
        posicao: 2,
        tempoVerde: 10,
        dispensavel: false,
        posicaoEstagio: 2,
        origemTransicaoProibida: false,
        destinoTransicaoProibida: false
      }
    ],
    cidades: [
      {
        id: '66b66819-a1c4-11e6-970d-0401fa9c1b01',
        idJson: '66b6941e-a1c4-11e6-970d-0401fa9c1b01',
        nome: 'São Paulo',
        areas: [
          {
            idJson: '66b6a0c4-a1c4-11e6-970d-0401fa9c1b01'
          }
        ]
      }
    ],
    areas: [
      {
        id: '66b66a46-a1c4-11e6-970d-0401fa9c1b01',
        idJson: '66b6a0c4-a1c4-11e6-970d-0401fa9c1b01',
        descricao: 1,
        cidade: {
          idJson: '66b6941e-a1c4-11e6-970d-0401fa9c1b01'
        },
        limites: [],
        subareas: []
      }
    ],
    limites: [],
    todosEnderecos: [
      {
        id: '438962f3-b555-468e-89eb-1f17662660da',
        idJson: '8fdb99e1-15a1-4675-af82-dc084fd3fd0d',
        localizacao: 'Av. Paulista',
        latitude: -23.564970545102565,
        longitude: -46.65224075317383,
        localizacao2: '',
        alturaNumerica: 475
      },
      {
        id: 'f18a7589-4dc6-40e1-bf8d-cdcd7cddb9ed',
        idJson: '37ed7d68-2474-4aa5-86b8-2b397bab6dbf',
        localizacao: 'Av. Paulista',
        latitude: -23.5631141,
        longitude: -46.65439200000003,
        localizacao2: '',
        alturaNumerica: 654
      },
      {
        id: '7f6fc17d-fccc-4788-a696-fdb439416534',
        idJson: 'ff8f5f34-eec0-4b4e-9d50-be3f909bfdbf',
        localizacao: 'Av. Paulista',
        latitude: -23.5631141,
        longitude: -46.65439200000003,
        localizacao2: '',
        alturaNumerica: 654
      }
    ],
    imagens: [
      {
        id: 'ea435d86-aab2-4f1d-9428-91657cb16442',
        idJson: 'eedb9199-f457-4ff0-8e6a-3aef34d22f12',
        fileName: 'Screen Shot 2016-06-19 at 13.12.51.png',
        contentType: 'image/png'
      },
      {
        id: 'a9cbaa76-a115-4532-a8f3-e154fd7c8b9f',
        idJson: 'b3d646d1-4168-46c5-994b-4b0bc0702f39',
        fileName: 'Screen Shot 2016-06-19 at 13.12.55.png',
        contentType: 'image/png'
      },
      {
        id: '00ddbe95-a761-4237-9ba2-4c5bf962d57a',
        idJson: '8828b6f2-fa44-4045-84e7-13b8b2aa1526',
        fileName: 'Screen Shot 2016-06-19 at 13.13.05.png',
        contentType: 'image/png'
      },
      {
        id: 'f1d58b0d-d19c-4802-98da-ad3edf74c974',
        idJson: '68c76e3a-c8f9-435f-9e0e-363be038e101',
        fileName: 'Screen Shot 2016-06-19 at 13.12.55.png',
        contentType: 'image/png'
      },
      {
        id: 'daa91893-9532-47ea-8513-56a005cd6b81',
        idJson: 'cd950070-0a4c-4cb3-83cc-028057372bf0',
        fileName: 'Screen Shot 2016-06-19 at 13.12.51.png',
        contentType: 'image/png'
      },
      {
        id: 'c1537ace-4406-4762-9a1f-6eae03f2f451',
        idJson: '3057b193-6543-449f-8f54-04d410e5c5be',
        fileName: 'Screen Shot 2016-06-19 at 13.13.05.png',
        contentType: 'image/png'
      }
    ],
    atrasosDeGrupo: [
      {
        id: '9f77bfb7-f7da-497b-b0b7-b0af33363759',
        idJson: 'afc35f70-39ca-42d9-9916-af8d82aa22e8',
        atrasoDeGrupo: 0
      },
      {
        id: 'f16078b1-68d3-4ad8-9f56-f9c817bb67a8',
        idJson: '0e76cd7f-23ad-4a3d-a6e2-f58b066ee7f0',
        atrasoDeGrupo: 0
      },
      {
        id: '9d016fdb-5d32-4f61-a36d-3a2b6b8ce208',
        idJson: '27ae2f1e-97ba-4eb5-b435-6f65feba8295',
        atrasoDeGrupo: 0
      },
      {
        id: '8b7398ef-d4ca-472e-8d03-0d0d9e055480',
        idJson: 'd3f571b1-fec4-4533-a7cc-b0ad4f3b9669',
        atrasoDeGrupo: 0
      },
      {
        id: 'f8a976af-e847-40b9-ab61-5fdfc1be227f',
        idJson: 'b59f1c76-f2a1-4269-b493-52aac0827605',
        atrasoDeGrupo: 0
      },
      {
        id: 'ebb2176f-c9e1-411c-b0c3-0ed1a94eb9f7',
        idJson: 'cb5536fe-cda8-442c-9d9a-b139eb502dd5',
        atrasoDeGrupo: 0
      },
      {
        id: '8a68b8f3-5e9c-4f2e-b965-26792ba38cb4',
        idJson: 'b3b6d919-d673-4876-b016-df048057c35c',
        atrasoDeGrupo: 0
      },
      {
        id: 'ef61df05-bd32-43a8-a999-14a9776ef998',
        idJson: 'c4737e95-4c46-48bf-bbf9-30efd292b430',
        atrasoDeGrupo: 0
      },
      {
        id: 'b6f69133-99e8-4805-809e-1702b4f0f8e3',
        idJson: '0044cfc3-ac5e-45a6-bb1f-65020d51bf2e',
        atrasoDeGrupo: 0
      },
      {
        id: '2859fd0c-9312-4aed-bdfd-5e7c028412b1',
        idJson: 'fb968ff0-0dc1-4915-8f54-6fddcd7846bb',
        atrasoDeGrupo: 0
      },
      {
        id: 'beeb1f85-f055-4792-8acd-419d45958926',
        idJson: '58b04a67-8797-46fb-9288-c6d9a663b1a2',
        atrasoDeGrupo: 0
      },
      {
        id: 'fe50f2ae-af30-4381-b62e-dd12845a21f1',
        idJson: '12375ba0-3ef1-4831-9e4d-07d3b6b00c92',
        atrasoDeGrupo: 0
      },
      {
        id: 'd8873042-97d5-4b23-958b-b825030447d7',
        idJson: '652bb23d-930e-48ab-a13b-88f7a10f2b43',
        atrasoDeGrupo: 0
      },
      {
        id: '8d6ce12f-83db-4311-8274-64cc2ca9de0b',
        idJson: '86a7b2a1-d869-4c21-8554-6481d900b308',
        atrasoDeGrupo: 0
      },
      {
        id: 'bd49d117-2a1d-4ea9-9508-61b6a53c0241',
        idJson: '9c0d61c8-ba01-4bee-a8fd-a113d126342e',
        atrasoDeGrupo: 0
      },
      {
        id: '0522f391-f5a6-4d5b-be25-6f6f1c6b2d53',
        idJson: 'ea5359a2-13fa-46f9-8f5b-d3858f0b8dc7',
        atrasoDeGrupo: 0
      },
      {
        id: '3adb9c23-3206-4980-945a-3563f1100ec5',
        idJson: '91c84e62-6c3b-4fd6-8883-992a346d0e08',
        atrasoDeGrupo: 0
      },
      {
        id: 'c9411f25-e915-48f4-bda7-1f7e85daaf35',
        idJson: '00f8108a-75a5-45e0-964c-0bdc9b05e9a9',
        atrasoDeGrupo: 0
      },
      {
        id: '95d09436-a76e-4439-8d04-197a0a6ad2d2',
        idJson: '47bcdfda-de26-4997-b0c3-e72eb40e63a9',
        atrasoDeGrupo: 0
      },
      {
        id: '1fe06d69-b19d-420e-9ab4-0ff80c1c0741',
        idJson: '245db728-cbc0-448a-ac5a-183ace140261',
        atrasoDeGrupo: 0
      },
      {
        id: '5dc869dc-c3c4-415f-8208-42e231b16f10',
        idJson: 'eea2c31b-f8d6-44a7-909b-fa809380fbd5',
        atrasoDeGrupo: 0
      },
      {
        id: 'd26d46a1-d417-454e-bae4-338cff30bbc7',
        idJson: '1e5eb573-f5d8-4425-87fb-af59b43d8d05',
        atrasoDeGrupo: 0
      },
      {
        id: 'ca6c6aae-1edb-4a42-95ee-3b80ed8c8dbf',
        idJson: '7386de59-8cc1-4791-bdff-70bec3a38c2b',
        atrasoDeGrupo: 0
      },
      {
        id: '0b279379-8bc5-45ae-b301-6738e98720f8',
        idJson: '5944be14-fe0b-4cc8-8dc6-ec0cbe1a47c4',
        atrasoDeGrupo: 0
      },
      {
        id: '063f2800-774a-41de-9e4b-01b5f0760b71',
        idJson: '3352f3b1-99a6-4f0a-8fbc-f0ef43ba6cdd',
        atrasoDeGrupo: 0
      },
      {
        id: 'c681f639-c23b-44d9-9262-46b527174147',
        idJson: '9ddd85c8-3724-437e-899c-2e55f13f021c',
        atrasoDeGrupo: 0
      },
      {
        id: 'f6a3e33c-7a65-4234-8ee4-feec9d5b8f14',
        idJson: '0aeb9c3c-87fb-48d2-9cc9-cf4aaa9a3f9f',
        atrasoDeGrupo: 0
      },
      {
        id: '0cdf9bce-66dd-4885-a27d-8a4df4e9a42d',
        idJson: '89285866-ed3d-4927-a246-b432202f4448',
        atrasoDeGrupo: 0
      },
      {
        id: '4799fb9b-efad-4324-b2d6-abd445a3402b',
        idJson: '6f57d9e6-c709-4f30-ae5d-ee9ff642326e',
        atrasoDeGrupo: 0
      },
      {
        id: 'a41f0320-6ee7-498b-a07f-9a96e49e9606',
        idJson: 'ea5a8fc9-9ec1-40ee-8d93-b7b792071f2f',
        atrasoDeGrupo: 0
      },
      {
        id: '02495811-b2b2-464d-8cf0-88245ece2330',
        idJson: 'f6da4400-c75e-4f1b-95b1-7236b3885bf2',
        atrasoDeGrupo: 0
      },
      {
        id: 'b4dc289d-a8e6-4892-af6b-5d88ac214b44',
        idJson: '9974f47a-9cb9-42e5-b126-fa1e3924e258',
        atrasoDeGrupo: 0
      },
      {
        id: '914ae7e1-d524-4a23-8267-b92d770a27ef',
        idJson: '85d7c64d-b95b-4d28-bcba-b28e6db4c8a8',
        atrasoDeGrupo: 0
      },
      {
        id: '66be8944-5e3f-4497-9a36-849b3f998ce5',
        idJson: '3c94a482-92fd-4f10-a63f-8650fdee9124',
        atrasoDeGrupo: 4
      },
      {
        id: '02ecbc7c-1839-42da-9720-7ed3853e339c',
        idJson: '1a41d37a-6938-4706-90c5-da636160ea65',
        atrasoDeGrupo: 0
      },
      {
        id: '0a7d5c9b-8e2d-4c59-8a49-f08b5b8cd8ea',
        idJson: '8c73c146-a220-40a8-ac55-7b53a502b96a',
        atrasoDeGrupo: 4
      },
      {
        id: '6a81c4fa-8404-4aad-a828-0a3ac48a8113',
        idJson: 'd658d553-e7cc-4a8f-a915-cce1e01ef039',
        atrasoDeGrupo: 0
      },
      {
        id: '4b833fea-ceb4-45a0-94bc-e578ef77dc13',
        idJson: '825a20ed-eef3-4ff7-a3e3-db1717148d8c',
        atrasoDeGrupo: 0
      },
      {
        id: '9480a582-509a-4e2c-8103-95a06388f07f',
        idJson: '30a4e4a8-02fd-42cb-b292-a8d1667a2a01',
        atrasoDeGrupo: 0
      },
      {
        id: '467a7988-2e86-41fd-a15b-570be2c155cd',
        idJson: '07bd8d5c-bf15-4b5f-897c-672af5d14374',
        atrasoDeGrupo: 0
      }
    ],
    statusVersao: 'CONFIGURADO',
    versaoControlador: {
      id: '2ca4d5d0-73d6-46d3-97bf-5288d7b11c7e',
      idJson: null,
      descricao: 'sasas',
      usuario: {
        id: '66b6c9ec-a1c4-11e6-970d-0401fa9c1b01',
        nome: 'Administrador Geral',
        login: 'root',
        email: 'root@influunt.com.br'
      }
    },
    versoesPlanos: [
      {
        id: 'e615685d-8673-4652-8b67-6b75aa0a7746',
        idJson: '7c31270a-3025-49d2-936d-87e08e21fcc3',
        statusVersao: 'EDITANDO',
        anel: {
          idJson: '7ff51a82-b32d-4288-afc1-a6a9eb85d78a'
        },
        planos: [
          {
            idJson: 'f6c3f670-b93e-4528-91c8-86709ae600cb'
          },
          {
            idJson: 'd7ff9f27-1132-476d-a622-744038650be9'
          },
          {
            idJson: 'd9d40a04-0d63-4927-88a9-f3b247f628a0'
          },
          {
            idJson: '7d491161-bb12-4673-b8e9-324f88f629cd'
          },
          {
            idJson: '2ba66e27-b6e4-4571-b2e2-0bf77be547b1'
          },
          {
            idJson: '9c0bae10-1b9f-422c-a063-aa0c305b25d0'
          },
          {
            idJson: '233c11dc-b927-4df2-a4b8-7e2875061970'
          },
          {
            idJson: 'c8b26060-acd7-44c3-8e92-cbdb316d6deb'
          },
          {
            idJson: 'a891c685-9285-4257-bed1-16f25f044c24'
          },
          {
            idJson: '5270dfac-b24b-4ba0-9c79-0435c74301d5'
          },
          {
            idJson: '0ce21c68-24c6-4d17-81f2-88c581931326'
          },
          {
            idJson: 'bda5c85c-63d5-4fe7-8f6d-7382f8b2152c'
          },
          {
            idJson: 'd8ac0504-ac63-44a2-a6b1-efb33f491da6'
          },
          {
            idJson: '2819a411-92ab-4654-8ef2-001e10b9fb84'
          },
          {
            idJson: '14c59533-b7f5-4205-bed1-4e0e99bebd42'
          },
          {
            idJson: 'dd976fc4-da72-4dec-89c9-9aa7b15ebb42'
          },
          {
            idJson: '6f68ea04-988c-4d50-b439-3ad02905eff1'
          }
        ]
      },
      {
        id: '401ac8b0-6171-410b-9ce3-2fcbe8d043e9',
        idJson: '2f11b501-7a1c-4e66-a88a-d5ea61e3c10e',
        statusVersao: 'EDITANDO',
        anel: {
          idJson: '9f7ac702-9979-48c7-8007-bd97a867a41a'
        },
        planos: [
          {
            idJson: 'b5283d44-2b17-4fcb-8277-336fb250a7d1'
          },
          {
            idJson: 'fbed2d5c-8df6-46d7-8b3a-03b4d9d75a9e'
          },
          {
            idJson: '68d09bed-8168-49d5-ac93-c05c4a4c6db6'
          },
          {
            idJson: '398aa468-075d-49fc-b940-36886add16a3'
          },
          {
            idJson: '0a64f4df-54fd-4097-963e-571d65e51123'
          },
          {
            idJson: 'f984986c-37f5-44bd-840a-dc94c8a0d850'
          },
          {
            idJson: '6780c087-42c3-42df-aa25-fad41b46f03b'
          },
          {
            idJson: '6781daa5-8106-4f87-b20d-027b2ee92907'
          },
          {
            idJson: '0b3062e0-3fad-4071-9a01-f01724c20a97'
          },
          {
            idJson: '8495d409-d4f1-4e43-bb06-045c2a56c0cf'
          },
          {
            idJson: 'b52d3443-2b72-4a32-8cc2-7d74e39229ff'
          },
          {
            idJson: '16d44222-79bd-4983-81d8-ee68934a2492'
          },
          {
            idJson: '998c7003-2267-426d-96d9-4de0518c8796'
          },
          {
            idJson: '2cf6d09f-e785-4ebd-af17-04e7ce5c72d3'
          },
          {
            idJson: '6ee596d6-9d6e-43d6-80c1-99b661388e1f'
          },
          {
            idJson: '8ad1eec4-992b-49a9-bf83-adc2ad1465d5'
          },
          {
            idJson: '10433f86-f1a3-4694-b2b0-990f0702a5d4'
          }
        ]
      }
    ],
    tabelasHorarias: [
      {
        id: 'a8d11f9b-a806-4a05-8bdb-3761d914b362',
        idJson: '1a6311e2-c203-46e8-9b61-2d6f4d5b42e6',
        versaoTabelaHoraria: {
          idJson: '82490503-fb61-4d59-90e3-8df4deb15379'
        },
        eventos: [
          {
            idJson: 'b23a4174-d01d-4804-9a06-9cf9de3fd1d7'
          },
          {
            idJson: 'ad1c2d48-829b-47a4-abd7-4731234583a4'
          },
          {
            idJson: '0099fae9-5e43-4e99-9543-3225c0bc86cb'
          }
        ]
      },
      {
        id: '19bd13ca-cfa5-4824-acc3-e056765ed44a',
        idJson: 'be4f4cbc-8a92-43a2-b275-1416efdbe5d7',
        versaoTabelaHoraria: {
          idJson: '7bccdc6e-9366-4cfc-808a-a51d08dc6790'
        },
        eventos: [
          {
            idJson: '9db05d81-6279-4f8b-ba98-180d2cc15b7c'
          },
          {
            idJson: '923f1c2e-8dc4-45f8-bc73-41df63358350'
          },
          {
            idJson: 'cec7b645-0c0a-4495-acd9-1b5b27895aae'
          }
        ]
      }
    ],
    eventos: [
      {
        id: '280b7c21-6724-4a9d-9838-8c01f0e705bb',
        idJson: 'b23a4174-d01d-4804-9a06-9cf9de3fd1d7',
        posicao: '1',
        tipo: 'NORMAL',
        diaDaSemana: 'Todos os dias da semana',
        data: '08-05-0009',
        horario: '00:00:00.000',
        posicaoPlano: '1',
        tabelaHoraria: {
          idJson: '1a6311e2-c203-46e8-9b61-2d6f4d5b42e6'
        }
      },
      {
        id: 'd5f3dd73-80c8-4086-a9b9-0417b3b34ed0',
        idJson: '0099fae9-5e43-4e99-9543-3225c0bc86cb',
        posicao: '2',
        tipo: 'NORMAL',
        diaDaSemana: 'Domingo',
        data: '08-05-0009',
        horario: '00:00:00.000',
        posicaoPlano: '2',
        tabelaHoraria: {
          idJson: '1a6311e2-c203-46e8-9b61-2d6f4d5b42e6'
        }
      },
      {
        id: 'b3c498fe-c72c-4b99-93ee-2fd41ba377a6',
        idJson: '923f1c2e-8dc4-45f8-bc73-41df63358350',
        posicao: '3',
        tipo: 'NORMAL',
        diaDaSemana: 'Todos os dias da semana',
        data: '07-11-2016',
        horario: '00:00:10.000',
        posicaoPlano: '2',
        tabelaHoraria: {
          idJson: 'be4f4cbc-8a92-43a2-b275-1416efdbe5d7'
        }
      },
      {
        id: '1fb19a33-9e6e-4300-9264-65d1d9222f11',
        idJson: '9db05d81-6279-4f8b-ba98-180d2cc15b7c',
        posicao: '2',
        tipo: 'NORMAL',
        diaDaSemana: 'Domingo',
        data: '08-05-0009',
        horario: '00:00:00.000',
        posicaoPlano: '2',
        tabelaHoraria: {
          idJson: 'be4f4cbc-8a92-43a2-b275-1416efdbe5d7'
        }
      },
      {
        id: 'ff99983a-c599-42be-98c2-2d357fcd6f12',
        idJson: 'cec7b645-0c0a-4495-acd9-1b5b27895aae',
        posicao: '1',
        tipo: 'NORMAL',
        diaDaSemana: 'Todos os dias da semana',
        data: '08-05-0009',
        horario: '00:00:00.000',
        posicaoPlano: '1',
        tabelaHoraria: {
          idJson: 'be4f4cbc-8a92-43a2-b275-1416efdbe5d7'
        }
      },
      {
        id: 'cb028cdc-ba2d-4f7b-ade8-37f2b4479aa4',
        idJson: 'ad1c2d48-829b-47a4-abd7-4731234583a4',
        posicao: '3',
        tipo: 'NORMAL',
        diaDaSemana: 'Todos os dias da semana',
        data: '07-11-2016',
        horario: '00:00:10.000',
        posicaoPlano: '2',
        tabelaHoraria: {
          idJson: '1a6311e2-c203-46e8-9b61-2d6f4d5b42e6'
        }
      }
    ],
    route: 'controladores',
    reqParams: null,
    restangularized: true,
    fromServer: true,
    parentResource: null,
    restangularCollection: false
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
