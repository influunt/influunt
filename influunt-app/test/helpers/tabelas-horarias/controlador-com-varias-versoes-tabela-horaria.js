'use strict';

/**
 * Conjunto de funções auxiliares utilizadas pelos testes do wizard de controladores.
 *
 * @type       {<type>}
 */
var ControladorVariasVersoesTabelaHoraria = {
  obj: {
    id: '2a83d3f2-cf6a-495c-9c1d-6abd1eae5f45',
    versoesTabelasHorarias: [
      {
        id: '4022f8d7-90a7-45cb-b623-a40a8d89b6d0',
        idJson: 'e41e36e9-0b83-4676-a213-7fa526650d7a',
        statusVersao: 'CONFIGURADO',
        tabelaHorariaOrigem: {
          idJson: '9a4b49a6-cfc5-47b7-8933-528bc747b542'
        },
        tabelaHoraria: {
          idJson: '313fc2a1-696d-4fcc-a157-82cb0b9ee3cb'
        },
        dataCriacao: '15/11/2016 14:43:38',
        usuario: {
          nome: 'Administrador Geral'
        }
      },
      {
        id: 'ea4ee874-3d39-47cf-a954-07a449b17474',
        idJson: '4303504b-00a5-44ea-99a4-6638dac2a344',
        statusVersao: 'ARQUIVADO',
        tabelaHorariaOrigem: {
          idJson: 'b971456e-720a-4183-a507-f1dde8406991'
        },
        tabelaHoraria: {
          idJson: '5db68e62-8c06-4c74-aa7a-9f26b8ff3e6f'
        },
        dataCriacao: '14/11/2016 11:10:36',
        usuario: {
          nome: 'Administrador Geral'
        }
      },
      {
        id: '410e6ecf-535f-4494-9557-2eab70cc4a45',
        idJson: 'b7a1efab-c606-483a-b06a-24f485c201c3',
        statusVersao: 'ARQUIVADO',
        tabelaHorariaOrigem: {
          idJson: 'a573c841-34f1-4b03-b84b-9b7ac192d197'
        },
        tabelaHoraria: {
          idJson: '9a4b49a6-cfc5-47b7-8933-528bc747b542'
        },
        dataCriacao: '15/11/2016 13:06:09',
        usuario: {
          nome: 'Administrador Geral'
        }
      },
      {
        id: '7cf67c18-9ae9-48fa-81a0-ad449ced75af',
        idJson: 'c2ea79e8-eecf-4b20-a632-545d8985a6fe',
        statusVersao: 'ARQUIVADO',
        tabelaHorariaOrigem: {
          idJson: '5db68e62-8c06-4c74-aa7a-9f26b8ff3e6f'
        },
        tabelaHoraria: {
          idJson: 'a573c841-34f1-4b03-b84b-9b7ac192d197'
        },
        dataCriacao: '14/11/2016 11:21:59',
        usuario: {
          nome: 'Administrador Geral'
        }
      },
      {
        id: '314c4de5-62d9-4e4b-b442-753c160efab7',
        idJson: '96122f94-d82c-442e-9118-ccd3bfc1fc7f',
        statusVersao: 'ARQUIVADO',
        tabelaHoraria: {
          idJson: '421ed1a4-8575-4b70-ac2b-b2f8de3e2641'
        },
        dataCriacao: '31/10/2016 20:50:14'
      },
      {
        id: 'd88802c6-cbde-4573-93a1-a030055d197f',
        idJson: 'c82d4ec8-7cc5-4e97-bb18-2da15467a835',
        statusVersao: 'ARQUIVADO',
        tabelaHorariaOrigem: {
          idJson: '421ed1a4-8575-4b70-ac2b-b2f8de3e2641'
        },
        tabelaHoraria: {
          idJson: 'b971456e-720a-4183-a507-f1dde8406991'
        },
        dataCriacao: '14/11/2016 11:06:56',
        usuario: {
          nome: 'Administrador Geral'
        }
      }
    ],
    numeroSMEE: '87687',
    sequencia: 11,
    limiteEstagio: 2,
    limiteGrupoSemaforico: 2,
    limiteAnel: 12,
    limiteDetectorPedestre: 1,
    limiteDetectorVeicular: 1,
    limiteTabelasEntreVerdes: 1,
    limitePlanos: 16,
    nomeEndereco: 'R. Seabra, nº 234',
    dataCriacao: '10/11/2016 20:37:07',
    dataAtualizacao: '16/11/2016 11:20:51',
    CLC: '1.001.0011',
    bloqueado: false,
    planosBloqueado: false,
    verdeMin: '1',
    verdeMax: '255',
    verdeMinimoMin: '10',
    verdeMinimoMax: '255',
    verdeMaximoMin: '10',
    verdeMaximoMax: '255',
    extensaoVerdeMin: '1.0',
    extensaoVerdeMax: '10.0',
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
    atrasoGrupoMax: '20',
    verdeSegurancaVeicularMin: '10',
    verdeSegurancaVeicularMax: '30',
    verdeSegurancaPedestreMin: '4',
    verdeSegurancaPedestreMax: '10',
    maximoPermanenciaEstagioMin: '60',
    maximoPermanenciaEstagioMax: '255',
    defaultMaximoPermanenciaEstagioVeicular: 127,
    cicloMin: '30',
    cicloMax: '255',
    ausenciaDeteccaoMin: '0',
    ausenciaDeteccaoMax: '4320',
    deteccaoPermanenteMin: '0',
    deteccaoPermanenteMax: '1440',
    statusControlador: 'CONFIGURADO',
    statusControladorReal: 'CONFIGURADO',
    area: {
      idJson: '66b6a0c4-a1c4-11e6-970d-0401fa9c1b01'
    },
    subarea: {
      id: '120d8195-d115-4257-a539-27e18fd2c787',
      idJson: '9a334b0c-99a6-4454-99a6-7d746291da8e',
      nome: 'Subarea 1',
      numero: 1,
      area: {
        idJson: '66b6a0c4-a1c4-11e6-970d-0401fa9c1b01'
      }
    },
    endereco: {
      idJson: '01d8dd76-0676-4676-84f1-f3a33a2c909a'
    },
    modelo: {
      id: '9c2239d4-461c-40fe-9338-a5d6d3f35d97',
      idJson: 'bf6f5f2e-0e0f-40d9-a0f0-551fea811987',
      descricao: 'Modelo DIGITAL FUN',
      fabricante: {
        id: '7a83ca86-6a26-43d8-83d5-70114248c2db',
        nome: 'DIGITAL FUN'
      }
    },
    aneis: [
      {
        id: '00c5172b-392a-4b83-b31e-b561f2f23ef6',
        idJson: 'e36322ef-b4f0-462c-bf04-502d62fa7eef',
        ativo: false,
        aceitaModoManual: true,
        posicao: 8,
        CLA: '1.001.0011.8',
        estagios: [],
        gruposSemaforicos: [],
        detectores: [],
        planos: []
      },
      {
        id: '0e9c032f-e1c7-426b-ba22-15b93465d4d5',
        idJson: '7ed5bf7d-bf79-4d9e-bad4-f94bfbc1b7a5',
        ativo: false,
        aceitaModoManual: true,
        posicao: 9,
        CLA: '1.001.0011.9',
        estagios: [],
        gruposSemaforicos: [],
        detectores: [],
        planos: []
      },
      {
        id: '160f5dd2-24e1-4a07-88fd-d5cdc9c7cd61',
        idJson: '65620c05-655f-40b2-92aa-5b9bee63f06e',
        ativo: false,
        aceitaModoManual: true,
        posicao: 4,
        CLA: '1.001.0011.4',
        estagios: [],
        gruposSemaforicos: [],
        detectores: [],
        planos: []
      },
      {
        id: '1bc4bf45-0c82-4c91-876c-79db5c1efbdc',
        idJson: '45585536-2d18-401b-a922-d304ced5d213',
        numeroSMEE: '-',
        ativo: true,
        aceitaModoManual: true,
        posicao: 1,
        CLA: '1.001.0011.1',
        versaoPlano: {
          idJson: '3e1d3913-3523-4b42-8801-42f4f6125bef'
        },
        estagios: [
          {
            idJson: '4ab3da75-57d8-4373-839a-a5ef10efefba'
          },
          {
            idJson: '2f3e0fc6-7972-4d4c-8d3d-b67c65af5b9c'
          }
        ],
        gruposSemaforicos: [
          {
            idJson: '305b032f-504a-4673-8f21-5e22c298c49c'
          },
          {
            idJson: '6db40d2f-e9f8-4214-adf8-a2d81be447da'
          }
        ],
        detectores: [],
        planos: [
          {
            idJson: '5883af2b-845c-4e40-ad9c-1b27e446545e'
          },
          {
            idJson: 'd7eb8d54-88c3-48ae-945a-71d3f60412ef'
          },
          {
            idJson: '05d09234-2ba9-40fe-a2b7-16d92cf899da'
          }
        ],
        endereco: {
          idJson: '3c52dbd1-9849-4cbf-9391-96558e1099b5'
        }
      },
      {
        id: '2d8490c1-74a7-4675-b93b-ab9dfeca0677',
        idJson: '05dddf8b-cfba-4e26-862a-6f93b1929ccb',
        ativo: false,
        aceitaModoManual: true,
        posicao: 6,
        CLA: '1.001.0011.6',
        estagios: [],
        gruposSemaforicos: [],
        detectores: [],
        planos: []
      },
      {
        id: '4ba512db-de1c-4b08-8d70-9d339f95bfe2',
        idJson: 'dbdcb368-064b-465a-99f2-02d50b1f3b59',
        ativo: false,
        aceitaModoManual: true,
        posicao: 12,
        CLA: '1.001.0011.12',
        estagios: [],
        gruposSemaforicos: [],
        detectores: [],
        planos: []
      },
      {
        id: '508bcada-ef88-40a0-a89e-f30d78e65b1e',
        idJson: '5a9f028a-e12d-40b5-94ab-0e4fee5eefbf',
        ativo: false,
        aceitaModoManual: true,
        posicao: 11,
        CLA: '1.001.0011.11',
        estagios: [],
        gruposSemaforicos: [],
        detectores: [],
        planos: []
      },
      {
        id: '5b647b66-dd01-4bf2-a8a0-afb60f0ce5fb',
        idJson: 'f609a89f-8a84-4d6b-9ae4-88da6c7148de',
        ativo: false,
        aceitaModoManual: true,
        posicao: 7,
        CLA: '1.001.0011.7',
        estagios: [],
        gruposSemaforicos: [],
        detectores: [],
        planos: []
      },
      {
        id: '6b021815-c9d7-45e8-a61f-d17eb76766d3',
        idJson: 'cbe90e71-5316-4697-9bfa-3d01ffd67dd9',
        ativo: false,
        aceitaModoManual: true,
        posicao: 3,
        CLA: '1.001.0011.3',
        estagios: [],
        gruposSemaforicos: [],
        detectores: [],
        planos: []
      },
      {
        id: '721889d5-6ef4-46e9-8366-f78bdfc30c0c',
        idJson: '26d251e6-4f20-4eed-bd1c-82b86a1b301b',
        ativo: false,
        aceitaModoManual: true,
        posicao: 2,
        CLA: '1.001.0011.2',
        estagios: [],
        gruposSemaforicos: [],
        detectores: [],
        planos: []
      },
      {
        id: '7cdfb3bf-2ca3-4427-971b-4f8cf85aa1f7',
        idJson: 'acddd6e8-68e0-4ec2-8830-5c166a48071d',
        ativo: false,
        aceitaModoManual: true,
        posicao: 10,
        CLA: '1.001.0011.10',
        estagios: [],
        gruposSemaforicos: [],
        detectores: [],
        planos: []
      },
      {
        id: 'e0d7e1c6-5186-4c79-be06-944309226a2f',
        idJson: 'fff2e23d-404d-4531-a77c-3ed2ebc43967',
        ativo: false,
        aceitaModoManual: true,
        posicao: 5,
        CLA: '1.001.0011.5',
        estagios: [],
        gruposSemaforicos: [],
        detectores: [],
        planos: []
      }
    ],
    estagios: [
      {
        id: 'df51dcd7-961b-4326-a130-ccc5193d337f',
        idJson: '2f3e0fc6-7972-4d4c-8d3d-b67c65af5b9c',
        tempoMaximoPermanencia: 127,
        tempoMaximoPermanenciaAtivado: true,
        demandaPrioritaria: false,
        tempoVerdeDemandaPrioritaria: 1,
        posicao: 2,
        anel: {
          idJson: '45585536-2d18-401b-a922-d304ced5d213'
        },
        imagem: {
          idJson: 'b655c937-8275-4608-b6a6-60d123ceeea3'
        },
        origemDeTransicoesProibidas: [],
        destinoDeTransicoesProibidas: [],
        alternativaDeTransicoesProibidas: [],
        estagiosGruposSemaforicos: [
          {
            idJson: 'f1f31ffa-ea93-4cc3-bbda-2384810a6f12'
          }
        ],
        estagiosPlanos: [
          {
            idJson: 'dddd5cd5-b808-47f7-8634-b1d58872af4f'
          },
          {
            idJson: 'e9f2e649-0b87-42d6-8a71-6fea772280ef'
          },
          {
            idJson: 'cf86cfeb-7cc0-4bca-93f3-bc837d724df0'
          },
          {
            idJson: '058c75f0-8852-458f-9532-1bb159e1e4b7'
          }
        ]
      },
      {
        id: 'bb97dfb0-c215-41d0-88f2-83bd9236d808',
        idJson: '4ab3da75-57d8-4373-839a-a5ef10efefba',
        tempoMaximoPermanencia: 127,
        tempoMaximoPermanenciaAtivado: true,
        demandaPrioritaria: false,
        tempoVerdeDemandaPrioritaria: 1,
        posicao: 1,
        anel: {
          idJson: '45585536-2d18-401b-a922-d304ced5d213'
        },
        imagem: {
          idJson: 'dbe37641-5900-49e4-8343-fb668322e1a8'
        },
        origemDeTransicoesProibidas: [],
        destinoDeTransicoesProibidas: [],
        alternativaDeTransicoesProibidas: [],
        estagiosGruposSemaforicos: [
          {
            idJson: 'c009475d-7349-47ec-9fb3-570322c18c43'
          }
        ],
        estagiosPlanos: [
          {
            idJson: 'a665afff-d22a-46f7-a5f7-038db3950894'
          },
          {
            idJson: '0414fd7e-d07b-4574-8248-2c927024acf3'
          },
          {
            idJson: 'fe6a15e8-0314-443b-a711-b8398fc8cbeb'
          },
          {
            idJson: 'a1136af2-2e23-47c8-971a-2c32c508b70c'
          }
        ]
      }
    ],
    gruposSemaforicos: [
      {
        id: 'c828bb98-4155-4acc-8e5e-28ac81be7794',
        idJson: '6db40d2f-e9f8-4214-adf8-a2d81be447da',
        tipo: 'VEICULAR',
        posicao: 1,
        faseVermelhaApagadaAmareloIntermitente: true,
        tempoVerdeSeguranca: 10,
        anel: {
          idJson: '45585536-2d18-401b-a922-d304ced5d213'
        },
        verdesConflitantesOrigem: [
          {
            idJson: 'a36ede99-82c3-4adb-b076-a818036d7c85'
          }
        ],
        verdesConflitantesDestino: [],
        estagiosGruposSemaforicos: [
          {
            idJson: 'c009475d-7349-47ec-9fb3-570322c18c43'
          }
        ],
        transicoes: [
          {
            idJson: 'c04ad21d-5827-40e2-9fa5-848a4f62d391'
          }
        ],
        transicoesComGanhoDePassagem: [
          {
            idJson: '47498caa-2ff7-4059-ba5f-dffbe5528cd5'
          }
        ],
        tabelasEntreVerdes: [
          {
            idJson: '0d2e7062-3dc0-4c63-85a4-1bffe3ea8997'
          }
        ]
      },
      {
        id: '174901d9-816c-4572-b04b-e0355fb16e15',
        idJson: '305b032f-504a-4673-8f21-5e22c298c49c',
        tipo: 'VEICULAR',
        posicao: 2,
        faseVermelhaApagadaAmareloIntermitente: true,
        tempoVerdeSeguranca: 10,
        anel: {
          idJson: '45585536-2d18-401b-a922-d304ced5d213'
        },
        verdesConflitantesOrigem: [],
        verdesConflitantesDestino: [
          {
            idJson: 'a36ede99-82c3-4adb-b076-a818036d7c85'
          }
        ],
        estagiosGruposSemaforicos: [
          {
            idJson: 'f1f31ffa-ea93-4cc3-bbda-2384810a6f12'
          }
        ],
        transicoes: [
          {
            idJson: '81b6b3d6-913a-472d-9205-7ca140fa0df7'
          }
        ],
        transicoesComGanhoDePassagem: [
          {
            idJson: 'b0fd4f14-4b3e-4e0f-9483-333aeeff8198'
          }
        ],
        tabelasEntreVerdes: [
          {
            idJson: 'ac14391f-fab1-4df2-ad67-41bad280b32a'
          }
        ]
      }
    ],
    detectores: [],
    transicoesProibidas: [],
    estagiosGruposSemaforicos: [
      {
        id: 'fa2e005b-184a-4714-98a8-c3302ef33d38',
        idJson: 'c009475d-7349-47ec-9fb3-570322c18c43',
        estagio: {
          idJson: '4ab3da75-57d8-4373-839a-a5ef10efefba'
        },
        grupoSemaforico: {
          idJson: '6db40d2f-e9f8-4214-adf8-a2d81be447da'
        }
      },
      {
        id: '08d8f395-3ffe-4db8-865c-2c1e4ae54754',
        idJson: 'f1f31ffa-ea93-4cc3-bbda-2384810a6f12',
        estagio: {
          idJson: '2f3e0fc6-7972-4d4c-8d3d-b67c65af5b9c'
        },
        grupoSemaforico: {
          idJson: '305b032f-504a-4673-8f21-5e22c298c49c'
        }
      }
    ],
    verdesConflitantes: [
      {
        id: '111653b4-1c1e-4f2e-8d77-f8425cfb7c34',
        idJson: 'a36ede99-82c3-4adb-b076-a818036d7c85',
        origem: {
          idJson: '6db40d2f-e9f8-4214-adf8-a2d81be447da'
        },
        destino: {
          idJson: '305b032f-504a-4673-8f21-5e22c298c49c'
        }
      }
    ],
    transicoes: [
      {
        id: 'b522a8f1-74ff-4e54-9c8a-612e1fa95dc5',
        idJson: '81b6b3d6-913a-472d-9205-7ca140fa0df7',
        origem: {
          idJson: '2f3e0fc6-7972-4d4c-8d3d-b67c65af5b9c'
        },
        destino: {
          idJson: '4ab3da75-57d8-4373-839a-a5ef10efefba'
        },
        tabelaEntreVerdesTransicoes: [
          {
            idJson: '0223c6ad-cec1-417b-8c27-5c48bce3617a'
          }
        ],
        grupoSemaforico: {
          idJson: '305b032f-504a-4673-8f21-5e22c298c49c'
        },
        tipo: 'PERDA_DE_PASSAGEM',
        tempoAtrasoGrupo: '0',
        atrasoDeGrupo: {
          idJson: 'ecda3b91-8aeb-4713-83c5-5c47079d593d'
        },
        modoIntermitenteOuApagado: true
      },
      {
        id: 'b3bc9ff0-82c8-42da-aa56-009446aee361',
        idJson: 'c04ad21d-5827-40e2-9fa5-848a4f62d391',
        origem: {
          idJson: '4ab3da75-57d8-4373-839a-a5ef10efefba'
        },
        destino: {
          idJson: '2f3e0fc6-7972-4d4c-8d3d-b67c65af5b9c'
        },
        tabelaEntreVerdesTransicoes: [
          {
            idJson: '5dda4594-6ad4-4907-a3b8-7b1520a6fd5a'
          }
        ],
        grupoSemaforico: {
          idJson: '6db40d2f-e9f8-4214-adf8-a2d81be447da'
        },
        tipo: 'PERDA_DE_PASSAGEM',
        tempoAtrasoGrupo: '0',
        atrasoDeGrupo: {
          idJson: '578e2552-4359-4545-9683-4a4bc5713189'
        },
        modoIntermitenteOuApagado: true
      }
    ],
    transicoesComGanhoDePassagem: [
      {
        id: '4d8f9956-59ce-47a3-b4d8-5a468da6f146',
        idJson: '47498caa-2ff7-4059-ba5f-dffbe5528cd5',
        origem: {
          idJson: '2f3e0fc6-7972-4d4c-8d3d-b67c65af5b9c'
        },
        destino: {
          idJson: '4ab3da75-57d8-4373-839a-a5ef10efefba'
        },
        tabelaEntreVerdesTransicoes: [],
        grupoSemaforico: {
          idJson: '6db40d2f-e9f8-4214-adf8-a2d81be447da'
        },
        tipo: 'GANHO_DE_PASSAGEM',
        tempoAtrasoGrupo: '0',
        atrasoDeGrupo: {
          idJson: '9efcb81f-fc09-43c7-adf9-c85da0f92404'
        },
        modoIntermitenteOuApagado: false
      },
      {
        id: '1e0b157e-82ba-4e60-a441-51dc424551cd',
        idJson: 'b0fd4f14-4b3e-4e0f-9483-333aeeff8198',
        origem: {
          idJson: '4ab3da75-57d8-4373-839a-a5ef10efefba'
        },
        destino: {
          idJson: '2f3e0fc6-7972-4d4c-8d3d-b67c65af5b9c'
        },
        tabelaEntreVerdesTransicoes: [],
        grupoSemaforico: {
          idJson: '305b032f-504a-4673-8f21-5e22c298c49c'
        },
        tipo: 'GANHO_DE_PASSAGEM',
        tempoAtrasoGrupo: '0',
        atrasoDeGrupo: {
          idJson: '648c4887-0e77-40db-b7c4-849501eb8946'
        },
        modoIntermitenteOuApagado: false
      }
    ],
    tabelasEntreVerdes: [
      {
        id: '5855f09f-9f11-4a3d-87b6-9b36815dfc5d',
        idJson: '0d2e7062-3dc0-4c63-85a4-1bffe3ea8997',
        descricao: 'PADRÃO',
        posicao: 1,
        grupoSemaforico: {
          idJson: '6db40d2f-e9f8-4214-adf8-a2d81be447da'
        },
        tabelaEntreVerdesTransicoes: [
          {
            idJson: '5dda4594-6ad4-4907-a3b8-7b1520a6fd5a'
          }
        ]
      },
      {
        id: '1938ad53-c68c-4052-b152-fba71e723632',
        idJson: 'ac14391f-fab1-4df2-ad67-41bad280b32a',
        descricao: 'PADRÃO',
        posicao: 1,
        grupoSemaforico: {
          idJson: '305b032f-504a-4673-8f21-5e22c298c49c'
        },
        tabelaEntreVerdesTransicoes: [
          {
            idJson: '0223c6ad-cec1-417b-8c27-5c48bce3617a'
          }
        ]
      }
    ],
    tabelasEntreVerdesTransicoes: [
      {
        id: 'c285338e-ef11-4bba-ac69-aade7deea5db',
        idJson: '0223c6ad-cec1-417b-8c27-5c48bce3617a',
        tempoAmarelo: '4',
        tempoVermelhoLimpeza: '0',
        tempoAtrasoGrupo: '0',
        tabelaEntreVerdes: {
          idJson: 'ac14391f-fab1-4df2-ad67-41bad280b32a'
        },
        transicao: {
          idJson: '81b6b3d6-913a-472d-9205-7ca140fa0df7'
        }
      },
      {
        id: '7ff56f9f-e0c0-449b-bdc0-d14b7fe47073',
        idJson: '5dda4594-6ad4-4907-a3b8-7b1520a6fd5a',
        tempoAmarelo: '4',
        tempoVermelhoLimpeza: '0',
        tempoAtrasoGrupo: '0',
        tabelaEntreVerdes: {
          idJson: '0d2e7062-3dc0-4c63-85a4-1bffe3ea8997'
        },
        transicao: {
          idJson: 'c04ad21d-5827-40e2-9fa5-848a4f62d391'
        }
      }
    ],
    planos: [
      {
        id: '9d71e8cb-40f0-4eba-97af-55725a9a6aea',
        idJson: 'd7eb8d54-88c3-48ae-945a-71d3f60412ef',
        posicao: 1,
        descricao: 'Teste',
        tempoCiclo: 30,
        defasagem: 0,
        posicaoTabelaEntreVerde: 1,
        modoOperacao: 'TEMPO_FIXO_COORDENADO',
        dataCriacao: '14/11/2016 11:10:36',
        dataAtualizacao: '15/11/2016 15:49:33',
        anel: {
          idJson: '45585536-2d18-401b-a922-d304ced5d213'
        },
        versaoPlano: {
          idJson: '3e1d3913-3523-4b42-8801-42f4f6125bef'
        },
        estagiosPlanos: [
          {
            idJson: 'dddd5cd5-b808-47f7-8634-b1d58872af4f'
          },
          {
            idJson: 'a1136af2-2e23-47c8-971a-2c32c508b70c'
          }
        ],
        gruposSemaforicosPlanos: [
          {
            idJson: '0fcb9a64-bff5-4d9c-82c1-8d84c865721d'
          },
          {
            idJson: '696fc7bd-1a93-4ee6-a5d0-d84b285a1b29'
          }
        ]
      },
      {
        id: 'bcdd3283-3bc2-420a-8c88-5b5d2d43ea24',
        idJson: '05d09234-2ba9-40fe-a2b7-16d92cf899da',
        posicao: 2,
        descricao: 'Madrugada',
        tempoCiclo: 30,
        defasagem: 0,
        posicaoTabelaEntreVerde: 1,
        modoOperacao: 'TEMPO_FIXO_COORDENADO',
        dataCriacao: '14/11/2016 11:11:48',
        dataAtualizacao: '15/11/2016 15:49:33',
        anel: {
          idJson: '45585536-2d18-401b-a922-d304ced5d213'
        },
        versaoPlano: {
          idJson: '3e1d3913-3523-4b42-8801-42f4f6125bef'
        },
        estagiosPlanos: [
          {
            idJson: 'a665afff-d22a-46f7-a5f7-038db3950894'
          },
          {
            idJson: 'e9f2e649-0b87-42d6-8a71-6fea772280ef'
          }
        ],
        gruposSemaforicosPlanos: [
          {
            idJson: '815aad80-581a-4632-a70b-6d4fb505713d'
          },
          {
            idJson: 'f80d3743-ae9d-4fdf-9ce3-98672558cff1'
          }
        ]
      },
      {
        id: '952dfc35-db64-46ec-bbbb-c9976d9765dc',
        idJson: '5883af2b-845c-4e40-ad9c-1b27e446545e',
        posicao: 3,
        descricao: 'FDS',
        tempoCiclo: 38,
        defasagem: 0,
        posicaoTabelaEntreVerde: 1,
        modoOperacao: 'TEMPO_FIXO_COORDENADO',
        dataCriacao: '14/11/2016 11:11:48',
        dataAtualizacao: '15/11/2016 15:49:33',
        anel: {
          idJson: '45585536-2d18-401b-a922-d304ced5d213'
        },
        versaoPlano: {
          idJson: '3e1d3913-3523-4b42-8801-42f4f6125bef'
        },
        estagiosPlanos: [
          {
            idJson: 'fe6a15e8-0314-443b-a711-b8398fc8cbeb'
          },
          {
            idJson: '058c75f0-8852-458f-9532-1bb159e1e4b7'
          }
        ],
        gruposSemaforicosPlanos: [
          {
            idJson: '9e7f02f8-d052-455f-aa6f-81b2539afa80'
          },
          {
            idJson: '4dd7a881-df55-4233-86b0-3a6efd205e00'
          }
        ]
      }
    ],
    gruposSemaforicosPlanos: [
      {
        id: '36438b2a-7432-408e-b9f7-b7a462499c31',
        idJson: '9e7f02f8-d052-455f-aa6f-81b2539afa80',
        plano: {
          idJson: '5883af2b-845c-4e40-ad9c-1b27e446545e'
        },
        grupoSemaforico: {
          idJson: '305b032f-504a-4673-8f21-5e22c298c49c'
        },
        ativado: true
      },
      {
        id: 'd68f97a6-7a8d-4dc9-bb38-e8a35205f336',
        idJson: 'f80d3743-ae9d-4fdf-9ce3-98672558cff1',
        plano: {
          idJson: '05d09234-2ba9-40fe-a2b7-16d92cf899da'
        },
        grupoSemaforico: {
          idJson: '305b032f-504a-4673-8f21-5e22c298c49c'
        },
        ativado: true
      },
      {
        id: '6bd2ec2d-c528-41cf-bb94-a7096d735b7a',
        idJson: '0fcb9a64-bff5-4d9c-82c1-8d84c865721d',
        plano: {
          idJson: 'd7eb8d54-88c3-48ae-945a-71d3f60412ef'
        },
        grupoSemaforico: {
          idJson: '6db40d2f-e9f8-4214-adf8-a2d81be447da'
        },
        ativado: true
      },
      {
        id: '167f278c-da39-4843-9c2f-5565abced108',
        idJson: '815aad80-581a-4632-a70b-6d4fb505713d',
        plano: {
          idJson: '05d09234-2ba9-40fe-a2b7-16d92cf899da'
        },
        grupoSemaforico: {
          idJson: '6db40d2f-e9f8-4214-adf8-a2d81be447da'
        },
        ativado: true
      },
      {
        id: '9fc3a4ac-8ac1-445f-b4c2-0a7c18309efc',
        idJson: '4dd7a881-df55-4233-86b0-3a6efd205e00',
        plano: {
          idJson: '5883af2b-845c-4e40-ad9c-1b27e446545e'
        },
        grupoSemaforico: {
          idJson: '6db40d2f-e9f8-4214-adf8-a2d81be447da'
        },
        ativado: true
      },
      {
        id: '9f8fc301-23a2-4d2d-abcd-8729cfbb5839',
        idJson: '696fc7bd-1a93-4ee6-a5d0-d84b285a1b29',
        plano: {
          idJson: 'd7eb8d54-88c3-48ae-945a-71d3f60412ef'
        },
        grupoSemaforico: {
          idJson: '305b032f-504a-4673-8f21-5e22c298c49c'
        },
        ativado: true
      }
    ],
    estagiosPlanos: [
      {
        id: 'dfd2e445-3a7b-4c41-b047-c78eaf1bb45f',
        idJson: '058c75f0-8852-458f-9532-1bb159e1e4b7',
        posicao: 2,
        tempoVerde: 15,
        dispensavel: false,
        plano: {
          idJson: '5883af2b-845c-4e40-ad9c-1b27e446545e'
        },
        estagio: {
          idJson: '2f3e0fc6-7972-4d4c-8d3d-b67c65af5b9c'
        }
      },
      {
        id: 'a9a616c2-b6f6-472d-9a66-bcdcd91bd855',
        idJson: 'fe6a15e8-0314-443b-a711-b8398fc8cbeb',
        posicao: 1,
        tempoVerde: 15,
        dispensavel: false,
        plano: {
          idJson: '5883af2b-845c-4e40-ad9c-1b27e446545e'
        },
        estagio: {
          idJson: '4ab3da75-57d8-4373-839a-a5ef10efefba'
        }
      },
      {
        id: '2015fce7-3974-4bd1-a3ba-e316ff7fa944',
        idJson: 'dddd5cd5-b808-47f7-8634-b1d58872af4f',
        posicao: 2,
        tempoVerde: 10,
        dispensavel: false,
        plano: {
          idJson: 'd7eb8d54-88c3-48ae-945a-71d3f60412ef'
        },
        estagio: {
          idJson: '2f3e0fc6-7972-4d4c-8d3d-b67c65af5b9c'
        }
      },
      {
        id: '08faa407-d6c6-47d4-90ad-572bdbd19b5b',
        idJson: 'a665afff-d22a-46f7-a5f7-038db3950894',
        posicao: 1,
        tempoVerde: 10,
        dispensavel: false,
        plano: {
          idJson: '05d09234-2ba9-40fe-a2b7-16d92cf899da'
        },
        estagio: {
          idJson: '4ab3da75-57d8-4373-839a-a5ef10efefba'
        }
      },
      {
        id: '8f12aa8e-0910-487d-84da-812e21236cd5',
        idJson: '0414fd7e-d07b-4574-8248-2c927024acf3',
        posicao: 1,
        tempoVerde: 12,
        dispensavel: false,
        plano: {
          idJson: '55638a32-46e5-418f-b75d-a19f0fe68742'
        },
        estagio: {
          idJson: '4ab3da75-57d8-4373-839a-a5ef10efefba'
        }
      },
      {
        id: 'be701897-04e6-4093-bd33-1898503c043c',
        idJson: 'a1136af2-2e23-47c8-971a-2c32c508b70c',
        posicao: 1,
        tempoVerde: 12,
        dispensavel: false,
        plano: {
          idJson: 'd7eb8d54-88c3-48ae-945a-71d3f60412ef'
        },
        estagio: {
          idJson: '4ab3da75-57d8-4373-839a-a5ef10efefba'
        }
      },
      {
        id: '9950840a-c3b1-4ab8-a52b-07cdb501bcc8',
        idJson: 'cf86cfeb-7cc0-4bca-93f3-bc837d724df0',
        posicao: 2,
        tempoVerde: 10,
        dispensavel: false,
        plano: {
          idJson: '55638a32-46e5-418f-b75d-a19f0fe68742'
        },
        estagio: {
          idJson: '2f3e0fc6-7972-4d4c-8d3d-b67c65af5b9c'
        }
      },
      {
        id: '4dc22840-0b9a-4437-ba8a-8863c4bd84c8',
        idJson: 'e9f2e649-0b87-42d6-8a71-6fea772280ef',
        posicao: 2,
        tempoVerde: 12,
        dispensavel: false,
        plano: {
          idJson: '05d09234-2ba9-40fe-a2b7-16d92cf899da'
        },
        estagio: {
          idJson: '2f3e0fc6-7972-4d4c-8d3d-b67c65af5b9c'
        }
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
        subareas: [
          {
            id: '120d8195-d115-4257-a539-27e18fd2c787',
            idJson: '9a334b0c-99a6-4454-99a6-7d746291da8e',
            nome: 'Subarea 1',
            numero: '1'
          }
        ]
      }
    ],
    limites: [],
    todosEnderecos: [
      {
        id: '2325dd1b-7200-4446-909d-3c93677b1331',
        idJson: '01d8dd76-0676-4676-84f1-f3a33a2c909a',
        localizacao: 'R. Seabra',
        latitude: -23.5545363,
        longitude: -46.4496168,
        localizacao2: '',
        alturaNumerica: 234
      },
      {
        id: '357e59bb-15e6-4405-9056-cd78f59d3d80',
        idJson: '3c52dbd1-9849-4cbf-9391-96558e1099b5',
        localizacao: 'R. Seabra',
        latitude: -23.5545363,
        longitude: -46.4496168,
        localizacao2: '',
        alturaNumerica: 234
      }
    ],
    imagens: [
      {
        id: '6cb1ce29-2c5f-482d-97b6-5d61ce28a1d7',
        idJson: 'b655c937-8275-4608-b6a6-60d123ceeea3',
        fileName: '00-pessoas.jpg',
        contentType: 'image/jpeg'
      },
      {
        id: '93c5cf46-67be-434c-b0a7-9ac747dde066',
        idJson: 'dbe37641-5900-49e4-8343-fb668322e1a8',
        fileName: '00-carro.jpg',
        contentType: 'image/jpeg'
      }
    ],
    atrasosDeGrupo: [
      {
        id: '9ab5ea0b-161b-456a-a6ce-da64dc92496b',
        idJson: '578e2552-4359-4545-9683-4a4bc5713189',
        atrasoDeGrupo: 0
      },
      {
        id: 'bdc08646-e480-4702-a5d4-f735eb84d864',
        idJson: '648c4887-0e77-40db-b7c4-849501eb8946',
        atrasoDeGrupo: 0
      },
      {
        id: 'd5c12b34-d272-4ea0-8f67-a61ba2222428',
        idJson: '9efcb81f-fc09-43c7-adf9-c85da0f92404',
        atrasoDeGrupo: 0
      },
      {
        id: 'c199e777-1af9-4a9a-8c14-27c6f9a98db3',
        idJson: 'ecda3b91-8aeb-4713-83c5-5c47079d593d',
        atrasoDeGrupo: 0
      }
    ],
    versaoControlador: {
      id: '95d65356-84a5-4b37-906f-f03205050c13',
      idJson: null,
      descricao: 'opa opa opa',
      statusVersao: 'CONFIGURADO',
      controlador: {
        id: '2a83d3f2-cf6a-495c-9c1d-6abd1eae5f45'
      },
      controladorFisico: {
        id: 'b5c979f4-964f-4350-b0ae-64bb0f30e531'
      },
      usuario: {
        id: '66b6c9ec-a1c4-11e6-970d-0401fa9c1b01',
        nome: 'Administrador Geral',
        login: 'root',
        email: 'root@influunt.com.br'
      }
    },
    statusVersao: 'CONFIGURADO',
    versoesPlanos: [
      {
        id: '6a544d57-0580-4c69-b740-786b7112a64e',
        idJson: '3e1d3913-3523-4b42-8801-42f4f6125bef',
        statusVersao: 'CONFIGURADO',
        anel: {
          idJson: '45585536-2d18-401b-a922-d304ced5d213'
        },
        planos: [
          {
            idJson: '5883af2b-845c-4e40-ad9c-1b27e446545e'
          },
          {
            idJson: 'd7eb8d54-88c3-48ae-945a-71d3f60412ef'
          },
          {
            idJson: '05d09234-2ba9-40fe-a2b7-16d92cf899da'
          }
        ]
      }
    ],
    tabelasHorarias: [
      {
        id: '08082480-48b6-4539-a9c1-f184677efe10',
        idJson: '313fc2a1-696d-4fcc-a157-82cb0b9ee3cb',
        versaoTabelaHoraria: {
          idJson: 'e41e36e9-0b83-4676-a213-7fa526650d7a'
        },
        eventos: [
          {
            idJson: 'a9e5e892-7365-4518-ac50-6d7031fd3623'
          },
          {
            idJson: 'e25305b5-6801-49a8-8482-a8a6d30714db'
          },
          {
            idJson: '75c987e4-2ce8-4b04-8bd6-4d9c6fc37a31'
          },
          {
            idJson: '36399e84-478a-48e9-9bf2-b338886c6592'
          },
          {
            idJson: 'ec57b5b9-b262-4ccc-8803-5b92b6465ba6'
          },
          {
            idJson: '9d2b5180-4838-4d4c-ac9b-99348e593be6'
          },
          {
            idJson: 'c16128d7-893c-4d79-89c0-9eaf2db9e0b1'
          },
          {
            idJson: 'd17922b5-460a-4028-807a-c70f021c5b64'
          },
          {
            idJson: 'efd94764-776a-4c97-a1cb-51a9bf6eb460'
          }
        ]
      },
      {
        id: '0f2a69c6-5df3-4865-877d-9e280f3ecf96',
        idJson: 'b971456e-720a-4183-a507-f1dde8406991',
        versaoTabelaHoraria: {
          idJson: 'c82d4ec8-7cc5-4e97-bb18-2da15467a835'
        },
        eventos: [
          {
            idJson: '9a4bf71c-f93a-4d55-99a2-55183225acc8'
          },
          {
            idJson: '80bd8bbd-0963-492b-b55f-776903f19f67'
          },
          {
            idJson: 'f128b18e-0a4e-4bc7-8955-c57c646ecd32'
          },
          {
            idJson: 'b5d94622-540c-4342-8d4a-cf8df7b8ebab'
          }
        ]
      },
      {
        id: '937c3b0a-52d5-408e-9bb9-654d562d4651',
        idJson: 'a573c841-34f1-4b03-b84b-9b7ac192d197',
        versaoTabelaHoraria: {
          idJson: 'c2ea79e8-eecf-4b20-a632-545d8985a6fe'
        },
        eventos: [
          {
            idJson: '9a0bc766-60d9-4870-bde8-7c89f1f59d33'
          },
          {
            idJson: 'f57943f0-e97e-484c-b9fb-5236032da35a'
          },
          {
            idJson: '81e17e0c-e84c-4ed6-8fe8-c4e015878a6d'
          },
          {
            idJson: 'ed850b99-c990-4e55-9158-895ce88019c4'
          }
        ]
      },
      {
        id: '657e991f-c40b-4988-ba61-b37ef512de03',
        idJson: '421ed1a4-8575-4b70-ac2b-b2f8de3e2641',
        versaoTabelaHoraria: {
          idJson: '96122f94-d82c-442e-9118-ccd3bfc1fc7f'
        },
        eventos: [
          {
            idJson: 'ad8b7aba-355c-487c-b210-af3714cdb209'
          }
        ]
      },
      {
        id: '55783a9c-0b56-4866-9f5d-0c7e675c4d1b',
        idJson: '9a4b49a6-cfc5-47b7-8933-528bc747b542',
        versaoTabelaHoraria: {
          idJson: 'b7a1efab-c606-483a-b06a-24f485c201c3'
        },
        eventos: [
          {
            idJson: '6f355bed-a775-4dd1-8e69-5fcec636e3b3'
          },
          {
            idJson: '4cc822a0-7bcf-4eab-8d6f-04062a462c7b'
          },
          {
            idJson: '8775ed97-cf98-4042-8908-b7b9c34e096a'
          },
          {
            idJson: 'e19a2557-5e89-4c0c-90ed-db11a3bc3a04'
          },
          {
            idJson: 'fc6092ee-bb3a-4314-a69d-a4d1c1380e9c'
          },
          {
            idJson: '887efd08-3db2-4523-929a-88db2c82ac9e'
          },
          {
            idJson: '13280ae9-6dfa-4d70-a4a5-3a65aec7ac7e'
          },
          {
            idJson: '2d9953b6-b1ca-4c71-821b-35a390caa0f3'
          }
        ]
      },
      {
        id: 'c3a5e1f2-0abe-43e9-84e4-c91c94b9dbaa',
        idJson: '5db68e62-8c06-4c74-aa7a-9f26b8ff3e6f',
        versaoTabelaHoraria: {
          idJson: '4303504b-00a5-44ea-99a4-6638dac2a344'
        },
        eventos: [
          {
            idJson: 'e32d674d-97c3-41fa-864d-c32f62f3f65e'
          },
          {
            idJson: '2246a5f1-20c2-4995-8ba0-26c37db155ac'
          },
          {
            idJson: '7f28484e-089c-459b-b7d7-835395bfbf2d'
          },
          {
            idJson: '36615262-52ef-4416-975d-cafdf0f77823'
          }
        ]
      }
    ],
    eventos: [
      {
        id: '8e6d68e1-3ae4-4672-968b-c815f0130db0',
        idJson: 'fc6092ee-bb3a-4314-a69d-a4d1c1380e9c',
        posicao: '1',
        tipo: 'ESPECIAL_NAO_RECORRENTE',
        nome: 'ENR 1',
        data: '08-05-2016',
        horario: '00:00:00.000',
        posicaoPlano: '2',
        tabelaHoraria: {
          idJson: '9a4b49a6-cfc5-47b7-8933-528bc747b542'
        }
      },
      {
        id: '6496d16b-4699-42d2-bfb3-35653b7769f3',
        idJson: '7f28484e-089c-459b-b7d7-835395bfbf2d',
        posicao: '3',
        tipo: 'NORMAL',
        diaDaSemana: 'Segunda à Sexta',
        data: '20-05-0008',
        horario: '20:00:00.000',
        posicaoPlano: '1',
        tabelaHoraria: {
          idJson: '5db68e62-8c06-4c74-aa7a-9f26b8ff3e6f'
        }
      },
      {
        id: '019badb9-f261-42cb-8aed-d10c503c375a',
        idJson: '9a0bc766-60d9-4870-bde8-7c89f1f59d33',
        posicao: '4',
        tipo: 'NORMAL',
        diaDaSemana: 'Sábado e Domingo',
        data: '20-05-0008',
        horario: '09:00:00.000',
        posicaoPlano: '3',
        tabelaHoraria: {
          idJson: 'a573c841-34f1-4b03-b84b-9b7ac192d197'
        }
      },
      {
        id: '43d4d8b8-d5e4-473a-9eee-d4b3f50310d9',
        idJson: '75c987e4-2ce8-4b04-8bd6-4d9c6fc37a31',
        posicao: '1',
        tipo: 'NORMAL',
        diaDaSemana: 'Todos os dias da semana',
        data: '08-05-0016',
        horario: '00:00:00.000',
        posicaoPlano: '1',
        tabelaHoraria: {
          idJson: '313fc2a1-696d-4fcc-a157-82cb0b9ee3cb'
        }
      },
      {
        id: '82a1582e-5dcc-4728-80fe-d3970fdc0677',
        idJson: '4cc822a0-7bcf-4eab-8d6f-04062a462c7b',
        posicao: '1',
        tipo: 'NORMAL',
        diaDaSemana: 'Todos os dias da semana',
        data: '08-05-0016',
        horario: '00:00:00.000',
        posicaoPlano: '1',
        tabelaHoraria: {
          idJson: '9a4b49a6-cfc5-47b7-8933-528bc747b542'
        }
      },
      {
        id: '6d6791ff-0061-4309-8f5c-2378ac6a1077',
        idJson: '9d2b5180-4838-4d4c-ac9b-99348e593be6',
        posicao: '2',
        tipo: 'ESPECIAL_NAO_RECORRENTE',
        nome: 'ENR 1',
        data: '15-11-1996',
        horario: '00:00:00.000',
        posicaoPlano: '1',
        tabelaHoraria: {
          idJson: '313fc2a1-696d-4fcc-a157-82cb0b9ee3cb'
        }
      },
      {
        id: '8c592747-16ad-4a71-8989-36ac6d678f6c',
        idJson: 'c16128d7-893c-4d79-89c0-9eaf2db9e0b1',
        posicao: '4',
        tipo: 'NORMAL',
        diaDaSemana: 'Sábado e Domingo',
        data: '20-05-0008',
        horario: '09:00:00.000',
        posicaoPlano: '3',
        tabelaHoraria: {
          idJson: '313fc2a1-696d-4fcc-a157-82cb0b9ee3cb'
        }
      },
      {
        id: '1ee65e97-c439-499d-91cd-73490b37f278',
        idJson: 'ad8b7aba-355c-487c-b210-af3714cdb209',
        posicao: '1',
        tipo: 'NORMAL',
        diaDaSemana: 'Todos os dias da semana',
        data: '08-05-0016',
        horario: '00:00:00.000',
        posicaoPlano: '1',
        tabelaHoraria: {
          idJson: '421ed1a4-8575-4b70-ac2b-b2f8de3e2641'
        }
      },
      {
        id: 'cc8e381a-aab8-45fd-bc33-4315cc354b4c',
        idJson: 'efd94764-776a-4c97-a1cb-51a9bf6eb460',
        posicao: '3',
        tipo: 'ESPECIAL_RECORRENTE',
        nome: 'ER 1',
        data: '15-11-2016',
        horario: '00:00:00.000',
        posicaoPlano: '1',
        tabelaHoraria: {
          idJson: '313fc2a1-696d-4fcc-a157-82cb0b9ee3cb'
        }
      },
      {
        id: '5c3946a5-206d-477a-8adf-5408a61f37ad',
        idJson: '80bd8bbd-0963-492b-b55f-776903f19f67',
        posicao: '3',
        tipo: 'NORMAL',
        diaDaSemana: 'Segunda à Sexta',
        data: '20-05-0008',
        horario: '20:00:00.000',
        posicaoPlano: '1',
        tabelaHoraria: {
          idJson: 'b971456e-720a-4183-a507-f1dde8406991'
        }
      },
      {
        id: '830a6356-7001-4f07-bbb1-f2ec1d731aa3',
        idJson: '8775ed97-cf98-4042-8908-b7b9c34e096a',
        posicao: '4',
        tipo: 'NORMAL',
        diaDaSemana: 'Sábado e Domingo',
        data: '20-05-0008',
        horario: '09:00:00.000',
        posicaoPlano: '3',
        tabelaHoraria: {
          idJson: '9a4b49a6-cfc5-47b7-8933-528bc747b542'
        }
      },
      {
        id: '4ee397c1-23a6-4045-9856-1feff1c40839',
        idJson: '2246a5f1-20c2-4995-8ba0-26c37db155ac',
        posicao: '1',
        tipo: 'NORMAL',
        diaDaSemana: 'Todos os dias da semana',
        data: '08-05-0016',
        horario: '00:00:00.000',
        posicaoPlano: '1',
        tabelaHoraria: {
          idJson: '5db68e62-8c06-4c74-aa7a-9f26b8ff3e6f'
        }
      },
      {
        id: '06f3f315-75f8-4669-9acb-463ded422929',
        idJson: 'f57943f0-e97e-484c-b9fb-5236032da35a',
        posicao: '1',
        tipo: 'NORMAL',
        diaDaSemana: 'Todos os dias da semana',
        data: '08-05-0016',
        horario: '00:00:00.000',
        posicaoPlano: '1',
        tabelaHoraria: {
          idJson: 'a573c841-34f1-4b03-b84b-9b7ac192d197'
        }
      },
      {
        id: 'aad00072-f60e-4a11-a77e-e9468876939d',
        idJson: 'b5d94622-540c-4342-8d4a-cf8df7b8ebab',
        posicao: '2',
        tipo: 'NORMAL',
        diaDaSemana: 'Segunda à Sexta',
        data: '20-05-0008',
        horario: '17:00:00.000',
        posicaoPlano: '1',
        tabelaHoraria: {
          idJson: 'b971456e-720a-4183-a507-f1dde8406991'
        }
      },
      {
        id: '2b5dcb04-a3ec-411d-8a37-ede40360882e',
        idJson: 'e25305b5-6801-49a8-8482-a8a6d30714db',
        posicao: '1',
        tipo: 'ESPECIAL_NAO_RECORRENTE',
        nome: 'ENR 1',
        data: '15-11-2016',
        horario: '00:00:00.000',
        posicaoPlano: '1',
        tabelaHoraria: {
          idJson: '313fc2a1-696d-4fcc-a157-82cb0b9ee3cb'
        }
      },
      {
        id: 'ac606e23-66fc-4c5d-8063-c420f812f167',
        idJson: '13280ae9-6dfa-4d70-a4a5-3a65aec7ac7e',
        posicao: '2',
        tipo: 'ESPECIAL_NAO_RECORRENTE',
        nome: 'ENR 2',
        data: '08-05-2016',
        horario: '08:00:00.000',
        posicaoPlano: '1',
        tabelaHoraria: {
          idJson: '9a4b49a6-cfc5-47b7-8933-528bc747b542'
        }
      },
      {
        id: '4a250145-b773-4a3f-aa7e-77da61311908',
        idJson: '36399e84-478a-48e9-9bf2-b338886c6592',
        posicao: '2',
        tipo: 'ESPECIAL_RECORRENTE',
        nome: 'ER 2',
        data: '08-04-0018',
        horario: '08:00:00.000',
        posicaoPlano: '2',
        tabelaHoraria: {
          idJson: '313fc2a1-696d-4fcc-a157-82cb0b9ee3cb'
        }
      },
      {
        id: '8891c726-6e3c-41d2-934c-9100fd65a236',
        idJson: 'e19a2557-5e89-4c0c-90ed-db11a3bc3a04',
        posicao: '2',
        tipo: 'ESPECIAL_RECORRENTE',
        nome: 'ER 2',
        data: '08-04-0018',
        horario: '08:00:00.000',
        posicaoPlano: '2',
        tabelaHoraria: {
          idJson: '9a4b49a6-cfc5-47b7-8933-528bc747b542'
        }
      },
      {
        id: 'a7708add-e0f9-409b-8678-9cfb013c81f1',
        idJson: 'f128b18e-0a4e-4bc7-8955-c57c646ecd32',
        posicao: '4',
        tipo: 'NORMAL',
        diaDaSemana: 'Sábado e Domingo',
        data: '20-05-0008',
        horario: '08:00:00.000',
        posicaoPlano: '1',
        tabelaHoraria: {
          idJson: 'b971456e-720a-4183-a507-f1dde8406991'
        }
      },
      {
        id: '775e24fb-6e02-4201-b6d6-57cefe21fd22',
        idJson: '6f355bed-a775-4dd1-8e69-5fcec636e3b3',
        posicao: '3',
        tipo: 'NORMAL',
        diaDaSemana: 'Segunda à Sexta',
        data: '20-05-0008',
        horario: '20:00:00.000',
        posicaoPlano: '1',
        tabelaHoraria: {
          idJson: '9a4b49a6-cfc5-47b7-8933-528bc747b542'
        }
      },
      {
        id: '1192fad1-16ab-4518-87b4-5f2b66a1d68d',
        idJson: 'a9e5e892-7365-4518-ac50-6d7031fd3623',
        posicao: '2',
        tipo: 'NORMAL',
        diaDaSemana: 'Segunda à Sexta',
        data: '08-05-0020',
        horario: '17:00:00.000',
        posicaoPlano: '2',
        tabelaHoraria: {
          idJson: '313fc2a1-696d-4fcc-a157-82cb0b9ee3cb'
        }
      },
      {
        id: '959cfe4f-7a18-4c24-879c-2a05e2928296',
        idJson: '887efd08-3db2-4523-929a-88db2c82ac9e',
        posicao: '1',
        tipo: 'ESPECIAL_RECORRENTE',
        nome: 'ER 1',
        data: '08-05-0021',
        horario: '00:00:00.000',
        posicaoPlano: '1',
        tabelaHoraria: {
          idJson: '9a4b49a6-cfc5-47b7-8933-528bc747b542'
        }
      },
      {
        id: '52080b19-7500-49c5-981a-6fe3ea8bfa86',
        idJson: 'ec57b5b9-b262-4ccc-8803-5b92b6465ba6',
        posicao: '3',
        tipo: 'NORMAL',
        diaDaSemana: 'Segunda à Sexta',
        data: '20-05-0008',
        horario: '20:00:00.000',
        posicaoPlano: '1',
        tabelaHoraria: {
          idJson: '313fc2a1-696d-4fcc-a157-82cb0b9ee3cb'
        }
      },
      {
        id: 'b4532dc8-7d73-41eb-a065-b6392a7b14bc',
        idJson: 'd17922b5-460a-4028-807a-c70f021c5b64',
        posicao: '1',
        tipo: 'ESPECIAL_RECORRENTE',
        nome: 'ER 1',
        data: '14-11-2016',
        horario: '00:00:00.000',
        posicaoPlano: '1',
        tabelaHoraria: {
          idJson: '313fc2a1-696d-4fcc-a157-82cb0b9ee3cb'
        }
      },
      {
        id: '473a57ae-3fb3-44c7-b9ad-46d4defbb73a',
        idJson: 'e32d674d-97c3-41fa-864d-c32f62f3f65e',
        posicao: '4',
        tipo: 'NORMAL',
        diaDaSemana: 'Sábado e Domingo',
        data: '08-05-0020',
        horario: '08:00:00.000',
        posicaoPlano: '3',
        tabelaHoraria: {
          idJson: '5db68e62-8c06-4c74-aa7a-9f26b8ff3e6f'
        }
      },
      {
        id: '8e4cd304-ee05-4e05-9b50-0079ea04362d',
        idJson: '36615262-52ef-4416-975d-cafdf0f77823',
        posicao: '2',
        tipo: 'NORMAL',
        diaDaSemana: 'Segunda à Sexta',
        data: '08-05-0020',
        horario: '17:00:00.000',
        posicaoPlano: '2',
        tabelaHoraria: {
          idJson: '5db68e62-8c06-4c74-aa7a-9f26b8ff3e6f'
        }
      },
      {
        id: 'cd9f0512-3546-4883-95e0-6498f0960029',
        idJson: '2d9953b6-b1ca-4c71-821b-35a390caa0f3',
        posicao: '2',
        tipo: 'NORMAL',
        diaDaSemana: 'Segunda à Sexta',
        data: '08-05-0020',
        horario: '17:00:00.000',
        posicaoPlano: '2',
        tabelaHoraria: {
          idJson: '9a4b49a6-cfc5-47b7-8933-528bc747b542'
        }
      },
      {
        id: '02cf5f96-4f57-4c8c-af7d-30f48226293a',
        idJson: '9a4bf71c-f93a-4d55-99a2-55183225acc8',
        posicao: '1',
        tipo: 'NORMAL',
        diaDaSemana: 'Todos os dias da semana',
        data: '08-05-0016',
        horario: '00:00:00.000',
        posicaoPlano: '1',
        tabelaHoraria: {
          idJson: 'b971456e-720a-4183-a507-f1dde8406991'
        }
      },
      {
        id: 'fafcb49d-70c8-46d6-9eab-bd23e9ab4707',
        idJson: '81e17e0c-e84c-4ed6-8fe8-c4e015878a6d',
        posicao: '2',
        tipo: 'NORMAL',
        diaDaSemana: 'Segunda à Sexta',
        data: '08-05-0020',
        horario: '17:00:00.000',
        posicaoPlano: '2',
        tabelaHoraria: {
          idJson: 'a573c841-34f1-4b03-b84b-9b7ac192d197'
        }
      },
      {
        id: 'fda95d55-68f6-4033-9d78-2b123bbf08de',
        idJson: 'ed850b99-c990-4e55-9158-895ce88019c4',
        posicao: '3',
        tipo: 'NORMAL',
        diaDaSemana: 'Segunda à Sexta',
        data: '20-05-0008',
        horario: '20:00:00.000',
        posicaoPlano: '1',
        tabelaHoraria: {
          idJson: 'a573c841-34f1-4b03-b84b-9b7ac192d197'
        }
      }
    ]
  },
  getControladorId: function() {
    return this.obj.id;
  },
  getVersaoTabelaHorariaConfigurada: function() {
    return _.find(this.obj.versoesTabelasHorarias, { statusVersao: 'CONFIGURADO' });
  },
  get: function() {
    return _.cloneDeep(this.obj);
  }
};
