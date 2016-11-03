'use strict';

/**
 * Conjunto de funções auxiliares utilizadas pelos testes do wizard de controladores.
 *
 * @type       {<type>}
 */
var ControladorAtrasoGrupoAutomatico = {
  obj: {
    "id": "9f3ccb14-67de-46c5-b16d-fce3ceeb5c2c",
    "versoesTabelasHorarias": [
      {
        "id": "a8d64af2-9caf-4a98-ad00-a1736e9f816c",
        "idJson": "ea98e9cd-c024-417b-a2ea-18e1cfedf277",
        "statusVersao": "EDITANDO",
        "tabelaHoraria": {
          "idJson": "799b4f9f-4e83-4261-be45-0dc5600274c5"
        }
      }
    ],
    "numeroSMEE": "3341",
    "numeroSMEEConjugado1": "3342",
    "numeroSMEEConjugado2": "3343",
    "firmware": "1",
    "croqui": {
      "id": "73fdefc0-372c-41af-814f-7a702d791941",
      "idJson": "4dc0fbf6-6878-4a52-b644-258bdd89d9e5"
    },
    "sequencia": 2,
    "limiteEstagio": 16,
    "limiteGrupoSemaforico": 16,
    "limiteAnel": 4,
    "limiteDetectorPedestre": 4,
    "limiteDetectorVeicular": 8,
    "limiteTabelasEntreVerdes": 2,
    "limitePlanos": 16,
    "nomeEndereco": "R. Emília Marengo com Rua Itapura",
    "dataCriacao": "18/10/2016 12:26:22",
    "dataAtualizacao": "27/10/2016 16:01:33",
    "CLC": "3.003.0002",
    "bloqueado": false,
    "planosBloqueado": false,
    "verdeMin": "1",
    "verdeMax": "255",
    "verdeMinimoMin": "10",
    "verdeMinimoMax": "255",
    "verdeMaximoMin": "10",
    "verdeMaximoMax": "255",
    "extensaoVerdeMin": "1.0",
    "extensaoVerdeMax": "10.0",
    "verdeIntermediarioMin": "10",
    "verdeIntermediarioMax": "255",
    "defasagemMin": "0",
    "defasagemMax": "255",
    "amareloMin": "3",
    "amareloMax": "5",
    "vermelhoIntermitenteMin": "3",
    "vermelhoIntermitenteMax": "32",
    "vermelhoLimpezaVeicularMin": "0",
    "vermelhoLimpezaVeicularMax": "15",
    "vermelhoLimpezaPedestreMin": "0",
    "vermelhoLimpezaPedestreMax": "5",
    "atrasoGrupoMin": "0",
    "atrasoGrupoMax": "20",
    "verdeSegurancaVeicularMin": "10",
    "verdeSegurancaVeicularMax": "30",
    "verdeSegurancaPedestreMin": "4",
    "verdeSegurancaPedestreMax": "10",
    "maximoPermanenciaEstagioMin": "60",
    "maximoPermanenciaEstagioMax": "255",
    "defaultMaximoPermanenciaEstagioVeicular": 127,
    "cicloMin": "30",
    "cicloMax": "255",
    "ausenciaDeteccaoMin": "0",
    "ausenciaDeteccaoMax": "4320",
    "deteccaoPermanenteMin": "0",
    "deteccaoPermanenteMax": "1440",
    "statusControlador": "EDITANDO",
    "statusControladorReal": "EDITANDO",
    "area": {
      "idJson": "afb12c64-d2a5-4af5-bb6b-ce692ce25bc4"
    },
    "subarea": {
      "id": "970c11cb-1489-40c6-a379-fd518774c579",
      "idJson": "5c1dc0f8-04ef-449d-a242-445948ffb31c",
      "nome": "TATUAPE",
      "numero": 3
    },
    "endereco": {
      "idJson": "c568ebcc-4112-47b0-b6dd-3a1fd4a8a6ba"
    },
    "modelo": {
      "id": "181eb724-b2f3-46ec-9b13-32020fbe0641",
      "idJson": "a26fd46c-dc8a-4b9d-9001-8005abb022f4",
      "descricao": "CD300",
      "fabricante": {
        "id": "0a6ca3de-d2af-4f1f-81eb-056398f3eea3",
        "nome": "DIGICON"
      }
    },
    "aneis": [
      {
        "id": "0dcc2eef-3be5-45ca-96f6-c8b3b42441af",
        "idJson": "cd610cd4-f2cf-4079-93a2-fad92a85f493",
        "numeroSMEE": "3342",
        "ativo": true,
        "aceitaModoManual": true,
        "posicao": 2,
        "CLA": "3.003.0002.2",
        "croqui": {
          "id": "27045ccb-4b4d-430e-936e-d56cf696c8b5",
          "idJson": "3344355f-8f91-4228-9fb2-94bc07aeafc3"
        },
        "versaoPlano": {
          "idJson": "fb186624-2c73-49f9-8d7c-3fc9d3b7de63"
        },
        "estagios": [
          {
            "idJson": "e3cf6c23-8180-4e8f-9c87-f7a4589aa44e"
          },
          {
            "idJson": "2a189819-36c3-4cc0-a989-6f8f19cff390"
          }
        ],
        "gruposSemaforicos": [
          {
            "idJson": "f07d9d8b-31c1-48d1-ad44-3450509e026f"
          },
          {
            "idJson": "d3f9840a-d6d5-45aa-94cf-14de2b1fec41"
          }
        ],
        "detectores": [],
        "planos": [
          {
            "idJson": "986222a1-e6e9-40bf-b79e-1f7aeb88c5ce"
          },
          {
            "idJson": "8ea9bc20-9313-4113-a0b7-51e16df68eb4"
          },
          {
            "idJson": "aa614e64-a228-428c-93d9-f586fcdfb68a"
          },
          {
            "idJson": "b962be0a-3b23-4efa-aef0-c19e461d8859"
          },
          {
            "idJson": "abdaffeb-10e7-4134-8584-2b017c0ca408"
          },
          {
            "idJson": "7669c3e5-b086-4c41-bd5c-cdcb19a13147"
          }
        ],
        "endereco": {
          "idJson": "dbfcc505-982d-4d1a-86a1-d1316b18c846"
        }
      },
      {
        "id": "51c6e466-35f4-4026-82ec-f0625b4ace52",
        "idJson": "d9c67923-8710-4fb5-866a-38c028ee86a4",
        "numeroSMEE": "3341",
        "ativo": true,
        "aceitaModoManual": true,
        "posicao": 1,
        "CLA": "3.003.0002.1",
        "croqui": {
          "id": "8a136970-fa35-432f-8472-d3fbf9afb20a",
          "idJson": "a52e186b-12d7-47c4-9710-f98f75209044"
        },
        "versaoPlano": {
          "idJson": "c2aff556-c252-4e9d-a03c-637d3a2d0ed5"
        },
        "estagios": [
          {
            "idJson": "bdb72a13-a29b-4dff-ab69-05b69f433d72"
          },
          {
            "idJson": "0575779d-b038-47f0-b72e-ff11a7d47ccc"
          },
          {
            "idJson": "513ccc36-b0dd-4846-b404-226f14941f68"
          }
        ],
        "gruposSemaforicos": [
          {
            "idJson": "89e8cab5-2337-40d6-b6a8-c7816cd1ca5e"
          },
          {
            "idJson": "93c9f615-bb22-4cf6-a11f-b1f36b7903f0"
          },
          {
            "idJson": "eccc3c8b-218f-42f1-8bee-11fec6062d21"
          },
          {
            "idJson": "e60d0d71-ca19-47f0-942c-1276ff3734dd"
          },
          {
            "idJson": "27024439-a90f-4568-b643-b46a98b430fd"
          }
        ],
        "detectores": [
          {
            "idJson": "eb1e16a5-095a-4a70-8fe6-fa00eaf17c6a"
          }
        ],
        "planos": [
          {
            "idJson": "163f67f0-cfd8-48f2-94af-c7f59940dc55"
          },
          {
            "idJson": "2914de80-2f0c-4fda-9d38-e126a253fa76"
          },
          {
            "idJson": "294c9a8e-57a0-4a1f-8e49-d4a1b4015fc6"
          },
          {
            "idJson": "8ad00916-355c-4fc5-bdec-4c2cb2187e27"
          },
          {
            "idJson": "3839e784-5006-45e3-8742-b06b6801e153"
          },
          {
            "idJson": "7d56b43d-09c6-4dad-a16c-c333a8f716da"
          }
        ],
        "endereco": {
          "idJson": "6f151dbe-4b8a-4eaf-a70a-63ea02e299af"
        }
      },
      {
        "id": "5f5c9041-afb7-4be8-87e3-f29c3d5788a2",
        "idJson": "2d545706-f81d-4979-af5d-adc9ee9cfb55",
        "ativo": false,
        "aceitaModoManual": false,
        "posicao": 4,
        "CLA": "3.003.0002.4",
        "estagios": [],
        "gruposSemaforicos": [],
        "detectores": [],
        "planos": []
      },
      {
        "id": "8704be66-72f4-40da-a8cf-864612e06653",
        "idJson": "078e9420-b13b-48ba-beeb-95ba45d9d8d3",
        "numeroSMEE": "3343",
        "ativo": true,
        "aceitaModoManual": true,
        "posicao": 3,
        "CLA": "3.003.0002.3",
        "croqui": {
          "id": "25127706-13ec-4842-bb83-b2659a3f4691",
          "idJson": "8da07721-ffa6-47cc-847c-786337b4ab85"
        },
        "versaoPlano": {
          "idJson": "9df20e92-0b20-42d5-93d6-297cd4289319"
        },
        "estagios": [
          {
            "idJson": "28821b34-c80e-436b-9c42-6ee2f7da81bd"
          },
          {
            "idJson": "00f04cee-ff99-4690-9029-c88c55928096"
          },
          {
            "idJson": "3f6dac3e-909b-4690-b9ad-af666752a571"
          }
        ],
        "gruposSemaforicos": [
          {
            "idJson": "c4c97b82-c269-4571-a84e-011e770d4b8e"
          },
          {
            "idJson": "09316e06-fed0-46f0-86be-30eb46a20c27"
          },
          {
            "idJson": "fc0601be-87ac-42cb-b5ec-625fc445610a"
          }
        ],
        "detectores": [
          {
            "idJson": "36bd6980-475b-47bc-a3d1-09bf166c2c31"
          }
        ],
        "planos": [
          {
            "idJson": "2cc8025b-debb-4694-b459-297b59f9fd21"
          },
          {
            "idJson": "15a9c3e1-fe1d-4ea8-a6d1-2d3aff4ed177"
          },
          {
            "idJson": "9c3ae287-1e26-4688-9489-870757c48936"
          },
          {
            "idJson": "906ba906-527f-44ce-8da2-6cbab108b4ee"
          },
          {
            "idJson": "abe96d47-fa89-4b12-a674-ee26741d861a"
          },
          {
            "idJson": "d666dabb-03a6-4e66-9818-9c198f6f669e"
          }
        ],
        "endereco": {
          "idJson": "2d5ed6fe-721e-4136-b682-a4d81e9c598b"
        }
      }
    ],
    "estagios": [
      {
        "id": "93c8af4d-5e01-4a6d-9935-b9e643c659e8",
        "idJson": "0575779d-b038-47f0-b72e-ff11a7d47ccc",
        "tempoMaximoPermanencia": 127,
        "tempoMaximoPermanenciaAtivado": true,
        "demandaPrioritaria": false,
        "tempoVerdeDemandaPrioritaria": 1,
        "posicao": 3,
        "anel": {
          "idJson": "d9c67923-8710-4fb5-866a-38c028ee86a4"
        },
        "imagem": {
          "idJson": "023fc423-d200-4802-a10d-278bc8dbf250"
        },
        "origemDeTransicoesProibidas": [
          {
            "idJson": "0ed7a7f4-db35-4a52-8322-8294a80f2401"
          }
        ],
        "destinoDeTransicoesProibidas": [],
        "alternativaDeTransicoesProibidas": [
          {
            "idJson": "efdaa90c-3544-4d5d-bb3c-3b99ff77d313"
          }
        ],
        "estagiosGruposSemaforicos": [
          {
            "idJson": "dcdc677a-6e16-4ffc-aca5-bf2eeacaba18"
          },
          {
            "idJson": "789ff361-1fcb-4a31-b011-1c39eb33653c"
          }
        ]
      },
      {
        "id": "b04167db-e819-4c7e-ad3a-d7576b48d8c8",
        "idJson": "513ccc36-b0dd-4846-b404-226f14941f68",
        "tempoMaximoPermanencia": 127,
        "tempoMaximoPermanenciaAtivado": true,
        "demandaPrioritaria": false,
        "tempoVerdeDemandaPrioritaria": 1,
        "posicao": 1,
        "anel": {
          "idJson": "d9c67923-8710-4fb5-866a-38c028ee86a4"
        },
        "imagem": {
          "idJson": "9ff36447-ddbd-49e4-9ca4-7afe404c6686"
        },
        "origemDeTransicoesProibidas": [],
        "destinoDeTransicoesProibidas": [
          {
            "idJson": "efdaa90c-3544-4d5d-bb3c-3b99ff77d313"
          }
        ],
        "alternativaDeTransicoesProibidas": [
          {
            "idJson": "0ed7a7f4-db35-4a52-8322-8294a80f2401"
          }
        ],
        "estagiosGruposSemaforicos": [
          {
            "idJson": "e292aadb-1d84-4617-87a6-b5779b6ed582"
          },
          {
            "idJson": "45f51508-692d-4252-aa85-79883bca6601"
          }
        ]
      },
      {
        "id": "16dc1e23-4761-4eef-9c61-1273c19f9a89",
        "idJson": "28821b34-c80e-436b-9c42-6ee2f7da81bd",
        "tempoMaximoPermanencia": 127,
        "tempoMaximoPermanenciaAtivado": true,
        "demandaPrioritaria": false,
        "tempoVerdeDemandaPrioritaria": 1,
        "posicao": 3,
        "anel": {
          "idJson": "078e9420-b13b-48ba-beeb-95ba45d9d8d3"
        },
        "imagem": {
          "idJson": "ad3de68a-00f4-462b-a868-c491bbf43cca"
        },
        "origemDeTransicoesProibidas": [],
        "destinoDeTransicoesProibidas": [],
        "alternativaDeTransicoesProibidas": [],
        "estagiosGruposSemaforicos": [
          {
            "idJson": "f378104d-e243-4c17-9b17-9ab0e25869cb"
          }
        ]
      },
      {
        "id": "d22bae9b-77e2-44bd-9617-25edae0b5df4",
        "idJson": "3f6dac3e-909b-4690-b9ad-af666752a571",
        "tempoMaximoPermanencia": 127,
        "tempoMaximoPermanenciaAtivado": true,
        "demandaPrioritaria": false,
        "tempoVerdeDemandaPrioritaria": 1,
        "posicao": 2,
        "anel": {
          "idJson": "078e9420-b13b-48ba-beeb-95ba45d9d8d3"
        },
        "imagem": {
          "idJson": "24e23b1a-d05e-47cf-992b-14a3398a2bcb"
        },
        "origemDeTransicoesProibidas": [],
        "destinoDeTransicoesProibidas": [],
        "alternativaDeTransicoesProibidas": [],
        "estagiosGruposSemaforicos": [
          {
            "idJson": "11ef45ac-5552-4f78-ba7c-12e88a37521d"
          }
        ],
        "detector": {
          "idJson": "36bd6980-475b-47bc-a3d1-09bf166c2c31"
        }
      },
      {
        "id": "3c672b46-14a2-4711-aa40-210391faa13b",
        "idJson": "bdb72a13-a29b-4dff-ab69-05b69f433d72",
        "tempoMaximoPermanencia": 60,
        "tempoMaximoPermanenciaAtivado": true,
        "demandaPrioritaria": false,
        "tempoVerdeDemandaPrioritaria": 1,
        "posicao": 2,
        "anel": {
          "idJson": "d9c67923-8710-4fb5-866a-38c028ee86a4"
        },
        "imagem": {
          "idJson": "aeb254e3-7541-40f3-a71b-022a0bba211e"
        },
        "origemDeTransicoesProibidas": [
          {
            "idJson": "efdaa90c-3544-4d5d-bb3c-3b99ff77d313"
          }
        ],
        "destinoDeTransicoesProibidas": [
          {
            "idJson": "0ed7a7f4-db35-4a52-8322-8294a80f2401"
          }
        ],
        "alternativaDeTransicoesProibidas": [],
        "estagiosGruposSemaforicos": [
          {
            "idJson": "ddfa24e1-8e90-4509-bc82-9f27cc35547e"
          },
          {
            "idJson": "13739af3-be34-44a4-a3d5-95528824d7ef"
          }
        ],
        "detector": {
          "idJson": "eb1e16a5-095a-4a70-8fe6-fa00eaf17c6a"
        }
      },
      {
        "id": "3d815b0a-747b-46e1-8b7b-1f2b1662891a",
        "idJson": "e3cf6c23-8180-4e8f-9c87-f7a4589aa44e",
        "tempoMaximoPermanencia": 127,
        "tempoMaximoPermanenciaAtivado": true,
        "demandaPrioritaria": false,
        "tempoVerdeDemandaPrioritaria": 1,
        "posicao": 2,
        "anel": {
          "idJson": "cd610cd4-f2cf-4079-93a2-fad92a85f493"
        },
        "imagem": {
          "idJson": "6ce52c3e-2e5a-455e-966d-863b93e448df"
        },
        "origemDeTransicoesProibidas": [],
        "destinoDeTransicoesProibidas": [],
        "alternativaDeTransicoesProibidas": [],
        "estagiosGruposSemaforicos": [
          {
            "idJson": "a74c1b23-a596-4774-ae10-a3a0a5123248"
          }
        ]
      },
      {
        "id": "8eace505-238a-40a1-91ef-718f17e61251",
        "idJson": "2a189819-36c3-4cc0-a989-6f8f19cff390",
        "tempoMaximoPermanencia": 127,
        "tempoMaximoPermanenciaAtivado": true,
        "demandaPrioritaria": false,
        "tempoVerdeDemandaPrioritaria": 1,
        "posicao": 1,
        "anel": {
          "idJson": "cd610cd4-f2cf-4079-93a2-fad92a85f493"
        },
        "imagem": {
          "idJson": "9f32efcd-d832-4bdd-b9d1-1e39ad2a4f95"
        },
        "origemDeTransicoesProibidas": [],
        "destinoDeTransicoesProibidas": [],
        "alternativaDeTransicoesProibidas": [],
        "estagiosGruposSemaforicos": [
          {
            "idJson": "7af20d19-5d56-4438-938a-874ead2f239c"
          }
        ]
      },
      {
        "id": "2bb5d5a9-8f4e-4f74-82b8-b3a134ebd7e9",
        "idJson": "00f04cee-ff99-4690-9029-c88c55928096",
        "tempoMaximoPermanencia": 127,
        "tempoMaximoPermanenciaAtivado": true,
        "demandaPrioritaria": false,
        "tempoVerdeDemandaPrioritaria": 1,
        "posicao": 1,
        "anel": {
          "idJson": "078e9420-b13b-48ba-beeb-95ba45d9d8d3"
        },
        "imagem": {
          "idJson": "54b5b708-cb38-4bca-b57a-3b077c02598f"
        },
        "origemDeTransicoesProibidas": [],
        "destinoDeTransicoesProibidas": [],
        "alternativaDeTransicoesProibidas": [],
        "estagiosGruposSemaforicos": [
          {
            "idJson": "97f380a7-ee28-4470-b1a7-a2f625ee46a0"
          }
        ]
      }
    ],
    "gruposSemaforicos": [
      {
        "id": "a0abef0f-7ad4-4c45-81d2-19562c3a4e1c",
        "idJson": "eccc3c8b-218f-42f1-8bee-11fec6062d21",
        "tipo": "VEICULAR",
        "posicao": 5,
        "faseVermelhaApagadaAmareloIntermitente": true,
        "tempoVerdeSeguranca": 10,
        "anel": {
          "idJson": "d9c67923-8710-4fb5-866a-38c028ee86a4"
        },
        "verdesConflitantesOrigem": [],
        "verdesConflitantesDestino": [
          {
            "idJson": "ca918ee5-f3aa-47e5-9b72-3946c3f03e46"
          }
        ],
        "estagiosGruposSemaforicos": [
          {
            "idJson": "dcdc677a-6e16-4ffc-aca5-bf2eeacaba18"
          },
          {
            "idJson": "e292aadb-1d84-4617-87a6-b5779b6ed582"
          }
        ],
        "transicoes": [
          {
            "idJson": "eb7d0c1c-1fe9-4ccc-911b-c6ab20605a50"
          },
          {
            "idJson": "e24240bd-3e67-48bd-a19c-803536a82227"
          },
          {
            "idJson": "5e07234d-b178-4fc7-962a-847c92e9784d"
          }
        ],
        "transicoesComGanhoDePassagem": [
          {
            "idJson": "16cbd3bb-cf8a-47e0-8879-16f0526bd000"
          },
          {
            "idJson": "b56bff80-0f58-48a6-b891-439125656ce1"
          },
          {
            "idJson": "d65b55f8-a970-4756-a6ab-bbf711b786ba"
          }
        ],
        "tabelasEntreVerdes": [
          {
            "idJson": "7f0aec59-5ad6-4dc7-ac09-aa1bdd1b5fb5"
          }
        ]
      },
      {
        "id": "bbbb221f-c75b-48bf-bd4f-09271e087ad7",
        "idJson": "d3f9840a-d6d5-45aa-94cf-14de2b1fec41",
        "tipo": "VEICULAR",
        "posicao": 7,
        "faseVermelhaApagadaAmareloIntermitente": true,
        "tempoVerdeSeguranca": 10,
        "anel": {
          "idJson": "cd610cd4-f2cf-4079-93a2-fad92a85f493"
        },
        "verdesConflitantesOrigem": [],
        "verdesConflitantesDestino": [
          {
            "idJson": "63ac962c-b23b-4e0c-8b67-466a51e3e9fb"
          }
        ],
        "estagiosGruposSemaforicos": [
          {
            "idJson": "7af20d19-5d56-4438-938a-874ead2f239c"
          }
        ],
        "transicoes": [
          {
            "idJson": "d7b6a981-5697-4edb-b586-f8ceda3d6327"
          }
        ],
        "transicoesComGanhoDePassagem": [
          {
            "idJson": "9a6482e8-7d9f-488a-8c5a-fc7841832eb2"
          }
        ],
        "tabelasEntreVerdes": [
          {
            "idJson": "02dfce93-5029-4d0f-a623-ee736b8e2a99"
          }
        ]
      },
      {
        "id": "4873504f-05ac-46a7-8893-378c3785826f",
        "idJson": "89e8cab5-2337-40d6-b6a8-c7816cd1ca5e",
        "tipo": "VEICULAR",
        "posicao": 3,
        "faseVermelhaApagadaAmareloIntermitente": true,
        "tempoVerdeSeguranca": 10,
        "anel": {
          "idJson": "d9c67923-8710-4fb5-866a-38c028ee86a4"
        },
        "verdesConflitantesOrigem": [],
        "verdesConflitantesDestino": [
          {
            "idJson": "3e8c2ff0-3062-4c9f-b03b-5e0a1a2af8a6"
          }
        ],
        "estagiosGruposSemaforicos": [
          {
            "idJson": "789ff361-1fcb-4a31-b011-1c39eb33653c"
          }
        ],
        "transicoes": [
          {
            "idJson": "88305f20-3caa-4dcd-b4e1-ad2d350e33a5"
          }
        ],
        "transicoesComGanhoDePassagem": [
          {
            "idJson": "f1ec1441-a755-4cdd-9256-e2cde3331f15"
          },
          {
            "idJson": "ea161bad-92d8-416e-bc72-480b2dfcb161"
          }
        ],
        "tabelasEntreVerdes": [
          {
            "idJson": "378b346d-5f84-4de3-bf50-6d3c1853fa08"
          }
        ]
      },
      {
        "id": "c4a60a86-ba10-40b0-995d-48cf44185c8c",
        "idJson": "e60d0d71-ca19-47f0-942c-1276ff3734dd",
        "tipo": "VEICULAR",
        "posicao": 2,
        "faseVermelhaApagadaAmareloIntermitente": true,
        "tempoVerdeSeguranca": 10,
        "anel": {
          "idJson": "d9c67923-8710-4fb5-866a-38c028ee86a4"
        },
        "verdesConflitantesOrigem": [],
        "verdesConflitantesDestino": [
          {
            "idJson": "efaeee61-c44e-4d63-af29-b662a033d271"
          }
        ],
        "estagiosGruposSemaforicos": [
          {
            "idJson": "13739af3-be34-44a4-a3d5-95528824d7ef"
          }
        ],
        "transicoes": [
          {
            "idJson": "d6c2300a-255e-4035-bd78-eb0857679ed6"
          }
        ],
        "transicoesComGanhoDePassagem": [
          {
            "idJson": "73bac9a1-21ba-438a-a766-dfd397266689"
          }
        ],
        "tabelasEntreVerdes": [
          {
            "idJson": "7987e164-fa4a-46a7-b928-74679d410e91"
          }
        ]
      },
      {
        "id": "812d6142-ea42-4880-be0d-083f3b2f681b",
        "idJson": "f07d9d8b-31c1-48d1-ad44-3450509e026f",
        "tipo": "VEICULAR",
        "posicao": 6,
        "faseVermelhaApagadaAmareloIntermitente": true,
        "tempoVerdeSeguranca": 10,
        "anel": {
          "idJson": "cd610cd4-f2cf-4079-93a2-fad92a85f493"
        },
        "verdesConflitantesOrigem": [
          {
            "idJson": "63ac962c-b23b-4e0c-8b67-466a51e3e9fb"
          }
        ],
        "verdesConflitantesDestino": [],
        "estagiosGruposSemaforicos": [
          {
            "idJson": "a74c1b23-a596-4774-ae10-a3a0a5123248"
          }
        ],
        "transicoes": [
          {
            "idJson": "f8bd914d-4e67-42f1-8264-391dc7782af6"
          }
        ],
        "transicoesComGanhoDePassagem": [
          {
            "idJson": "98553938-7586-43b1-a2d8-6b17af5e53e5"
          }
        ],
        "tabelasEntreVerdes": [
          {
            "idJson": "5867fb67-2628-4f96-8fd6-28fbd3189523"
          }
        ]
      },
      {
        "id": "f9a4aba8-c8b7-4607-9944-f6818cadc244",
        "idJson": "27024439-a90f-4568-b643-b46a98b430fd",
        "tipo": "VEICULAR",
        "posicao": 1,
        "faseVermelhaApagadaAmareloIntermitente": true,
        "tempoVerdeSeguranca": 10,
        "anel": {
          "idJson": "d9c67923-8710-4fb5-866a-38c028ee86a4"
        },
        "verdesConflitantesOrigem": [
          {
            "idJson": "3e8c2ff0-3062-4c9f-b03b-5e0a1a2af8a6"
          },
          {
            "idJson": "efaeee61-c44e-4d63-af29-b662a033d271"
          }
        ],
        "verdesConflitantesDestino": [],
        "estagiosGruposSemaforicos": [
          {
            "idJson": "45f51508-692d-4252-aa85-79883bca6601"
          }
        ],
        "transicoes": [
          {
            "idJson": "bc75c24e-5696-41f5-a51c-ba6f7794f94d"
          },
          {
            "idJson": "444425fd-25c8-4d05-be10-f82b9b6c831f"
          }
        ],
        "transicoesComGanhoDePassagem": [
          {
            "idJson": "d6642ae7-6ca2-481b-b458-de6c260db08f"
          }
        ],
        "tabelasEntreVerdes": [
          {
            "idJson": "4bc2ea66-02c4-4836-b10c-05bc09a4f682"
          }
        ]
      },
      {
        "id": "4db796e2-2c2c-4069-b48f-f7d4cd54b124",
        "idJson": "09316e06-fed0-46f0-86be-30eb46a20c27",
        "tipo": "VEICULAR",
        "posicao": 9,
        "faseVermelhaApagadaAmareloIntermitente": true,
        "tempoVerdeSeguranca": 10,
        "anel": {
          "idJson": "078e9420-b13b-48ba-beeb-95ba45d9d8d3"
        },
        "verdesConflitantesOrigem": [
          {
            "idJson": "7070acaf-743b-4812-ace5-8afb8810bbc7"
          }
        ],
        "verdesConflitantesDestino": [
          {
            "idJson": "a9a07e45-8f18-4726-a1fd-3be4ca36530a"
          }
        ],
        "estagiosGruposSemaforicos": [
          {
            "idJson": "97f380a7-ee28-4470-b1a7-a2f625ee46a0"
          }
        ],
        "transicoes": [
          {
            "idJson": "a8dc0d86-ea75-4921-9c9c-a1777608c640"
          },
          {
            "idJson": "dec9bf63-b86a-485f-946d-9a92b8df754f"
          }
        ],
        "transicoesComGanhoDePassagem": [
          {
            "idJson": "7a76e208-830d-4b87-8423-ab553f5267eb"
          },
          {
            "idJson": "b56b4d45-8fbc-4211-8ed7-27330937fa79"
          }
        ],
        "tabelasEntreVerdes": [
          {
            "idJson": "d25bdd29-99a3-484f-a423-41103be68ba1"
          }
        ]
      },
      {
        "id": "5b02fbff-54f8-4d7a-aa96-9cb13ae5ecf0",
        "idJson": "fc0601be-87ac-42cb-b5ec-625fc445610a",
        "tipo": "VEICULAR",
        "posicao": 8,
        "faseVermelhaApagadaAmareloIntermitente": true,
        "tempoVerdeSeguranca": 10,
        "anel": {
          "idJson": "078e9420-b13b-48ba-beeb-95ba45d9d8d3"
        },
        "verdesConflitantesOrigem": [
          {
            "idJson": "bf402035-f071-4550-b148-f267ddbed79c"
          },
          {
            "idJson": "a9a07e45-8f18-4726-a1fd-3be4ca36530a"
          }
        ],
        "verdesConflitantesDestino": [],
        "estagiosGruposSemaforicos": [
          {
            "idJson": "11ef45ac-5552-4f78-ba7c-12e88a37521d"
          }
        ],
        "transicoes": [
          {
            "idJson": "bd2cb690-2073-4885-993c-6e00ab621abf"
          },
          {
            "idJson": "69e3a75e-6488-4635-849c-ff88c470f51a"
          }
        ],
        "transicoesComGanhoDePassagem": [
          {
            "idJson": "ee014905-a3c7-4d4c-82c8-408577f58f1d"
          },
          {
            "idJson": "d2371b6d-11af-4757-8c90-1dc6464ddaca"
          }
        ],
        "tabelasEntreVerdes": [
          {
            "idJson": "ec2e3e78-f726-41ba-a5b7-56835ab3798d"
          }
        ]
      },
      {
        "id": "51f0b2d9-7a73-443f-8c46-685cd6e971a5",
        "idJson": "93c9f615-bb22-4cf6-a11f-b1f36b7903f0",
        "tipo": "PEDESTRE",
        "posicao": 4,
        "faseVermelhaApagadaAmareloIntermitente": false,
        "tempoVerdeSeguranca": 4,
        "anel": {
          "idJson": "d9c67923-8710-4fb5-866a-38c028ee86a4"
        },
        "verdesConflitantesOrigem": [
          {
            "idJson": "ca918ee5-f3aa-47e5-9b72-3946c3f03e46"
          }
        ],
        "verdesConflitantesDestino": [],
        "estagiosGruposSemaforicos": [
          {
            "idJson": "ddfa24e1-8e90-4509-bc82-9f27cc35547e"
          }
        ],
        "transicoes": [
          {
            "idJson": "f3605b4f-dbe4-46bb-a950-bcc742d302cc"
          }
        ],
        "transicoesComGanhoDePassagem": [
          {
            "idJson": "32204ffb-bd31-4aa8-972d-264d1ab45797"
          }
        ],
        "tabelasEntreVerdes": [
          {
            "idJson": "01c385b3-7a4e-4379-bf61-577f9835f4fd"
          }
        ]
      },
      {
        "id": "4617e01e-abf6-4ba2-82cc-c811250a2201",
        "idJson": "c4c97b82-c269-4571-a84e-011e770d4b8e",
        "tipo": "VEICULAR",
        "posicao": 10,
        "faseVermelhaApagadaAmareloIntermitente": true,
        "tempoVerdeSeguranca": 10,
        "anel": {
          "idJson": "078e9420-b13b-48ba-beeb-95ba45d9d8d3"
        },
        "verdesConflitantesOrigem": [],
        "verdesConflitantesDestino": [
          {
            "idJson": "bf402035-f071-4550-b148-f267ddbed79c"
          },
          {
            "idJson": "7070acaf-743b-4812-ace5-8afb8810bbc7"
          }
        ],
        "estagiosGruposSemaforicos": [
          {
            "idJson": "f378104d-e243-4c17-9b17-9ab0e25869cb"
          }
        ],
        "transicoes": [
          {
            "idJson": "d2ec33ee-f26c-4cfa-a12a-25763dad5acf"
          },
          {
            "idJson": "896e48d3-70f3-44ca-831e-b974e41b5f7b"
          }
        ],
        "transicoesComGanhoDePassagem": [
          {
            "idJson": "9ebf40f9-b393-4a4b-8784-1ba04c36c290"
          },
          {
            "idJson": "277045a5-e2b9-434d-8c02-de0ebb4b3d47"
          }
        ],
        "tabelasEntreVerdes": [
          {
            "idJson": "21a41bb4-4e72-4a7c-8dd5-763e1c6209d0"
          }
        ]
      }
    ],
    "detectores": [
      {
        "id": "e18efee5-4114-4681-b2be-4176023a2aa2",
        "idJson": "36bd6980-475b-47bc-a3d1-09bf166c2c31",
        "tipo": "VEICULAR",
        "posicao": 1,
        "monitorado": true,
        "tempoAusenciaDeteccao": 60,
        "tempoDeteccaoPermanente": 120,
        "anel": {
          "idJson": "078e9420-b13b-48ba-beeb-95ba45d9d8d3"
        },
        "estagio": {
          "idJson": "3f6dac3e-909b-4690-b9ad-af666752a571"
        }
      },
      {
        "id": "8531772e-932e-4dbc-9aec-3c827501f6b8",
        "idJson": "eb1e16a5-095a-4a70-8fe6-fa00eaf17c6a",
        "tipo": "PEDESTRE",
        "posicao": 1,
        "monitorado": true,
        "tempoAusenciaDeteccao": 60,
        "tempoDeteccaoPermanente": 120,
        "anel": {
          "idJson": "d9c67923-8710-4fb5-866a-38c028ee86a4"
        },
        "estagio": {
          "idJson": "bdb72a13-a29b-4dff-ab69-05b69f433d72"
        }
      }
    ],
    "transicoesProibidas": [
      {
        "id": "47823ad5-679c-4978-996c-6f38776bc038",
        "idJson": "0ed7a7f4-db35-4a52-8322-8294a80f2401",
        "origem": {
          "idJson": "0575779d-b038-47f0-b72e-ff11a7d47ccc"
        },
        "destino": {
          "idJson": "bdb72a13-a29b-4dff-ab69-05b69f433d72"
        },
        "alternativo": {
          "idJson": "513ccc36-b0dd-4846-b404-226f14941f68"
        }
      },
      {
        "id": "67b12b8d-24b9-4c76-b9f2-1b1e7be4b895",
        "idJson": "efdaa90c-3544-4d5d-bb3c-3b99ff77d313",
        "origem": {
          "idJson": "bdb72a13-a29b-4dff-ab69-05b69f433d72"
        },
        "destino": {
          "idJson": "513ccc36-b0dd-4846-b404-226f14941f68"
        },
        "alternativo": {
          "idJson": "0575779d-b038-47f0-b72e-ff11a7d47ccc"
        }
      }
    ],
    "estagiosGruposSemaforicos": [
      {
        "id": "04aab9a7-5851-4f9f-9cf7-254afdbc281b",
        "idJson": "dcdc677a-6e16-4ffc-aca5-bf2eeacaba18",
        "estagio": {
          "idJson": "0575779d-b038-47f0-b72e-ff11a7d47ccc"
        },
        "grupoSemaforico": {
          "idJson": "eccc3c8b-218f-42f1-8bee-11fec6062d21"
        }
      },
      {
        "id": "e4e0af69-4865-4083-8b8e-9679c611e4ba",
        "idJson": "789ff361-1fcb-4a31-b011-1c39eb33653c",
        "estagio": {
          "idJson": "0575779d-b038-47f0-b72e-ff11a7d47ccc"
        },
        "grupoSemaforico": {
          "idJson": "89e8cab5-2337-40d6-b6a8-c7816cd1ca5e"
        }
      },
      {
        "id": "0b5d8bfd-76a2-44e7-8154-9276ae9f1454",
        "idJson": "e292aadb-1d84-4617-87a6-b5779b6ed582",
        "estagio": {
          "idJson": "513ccc36-b0dd-4846-b404-226f14941f68"
        },
        "grupoSemaforico": {
          "idJson": "eccc3c8b-218f-42f1-8bee-11fec6062d21"
        }
      },
      {
        "id": "8643fd4f-1bee-4e35-aafe-38550958bea1",
        "idJson": "7af20d19-5d56-4438-938a-874ead2f239c",
        "estagio": {
          "idJson": "2a189819-36c3-4cc0-a989-6f8f19cff390"
        },
        "grupoSemaforico": {
          "idJson": "d3f9840a-d6d5-45aa-94cf-14de2b1fec41"
        }
      },
      {
        "id": "74ddcbf8-707d-4dce-bedd-c717b02a05af",
        "idJson": "f378104d-e243-4c17-9b17-9ab0e25869cb",
        "estagio": {
          "idJson": "28821b34-c80e-436b-9c42-6ee2f7da81bd"
        },
        "grupoSemaforico": {
          "idJson": "c4c97b82-c269-4571-a84e-011e770d4b8e"
        }
      },
      {
        "id": "6a97dcae-40a5-42c1-ad2a-24a5a704f0fb",
        "idJson": "11ef45ac-5552-4f78-ba7c-12e88a37521d",
        "estagio": {
          "idJson": "3f6dac3e-909b-4690-b9ad-af666752a571"
        },
        "grupoSemaforico": {
          "idJson": "fc0601be-87ac-42cb-b5ec-625fc445610a"
        }
      },
      {
        "id": "c961d263-84bc-4db2-b4c9-e28cf753f13e",
        "idJson": "97f380a7-ee28-4470-b1a7-a2f625ee46a0",
        "estagio": {
          "idJson": "00f04cee-ff99-4690-9029-c88c55928096"
        },
        "grupoSemaforico": {
          "idJson": "09316e06-fed0-46f0-86be-30eb46a20c27"
        }
      },
      {
        "id": "b8b371f7-961c-456c-b3c4-48f5f9a3bc21",
        "idJson": "ddfa24e1-8e90-4509-bc82-9f27cc35547e",
        "estagio": {
          "idJson": "bdb72a13-a29b-4dff-ab69-05b69f433d72"
        },
        "grupoSemaforico": {
          "idJson": "93c9f615-bb22-4cf6-a11f-b1f36b7903f0"
        }
      },
      {
        "id": "b3b9affa-571f-4e89-b0ee-368cd48711db",
        "idJson": "45f51508-692d-4252-aa85-79883bca6601",
        "estagio": {
          "idJson": "513ccc36-b0dd-4846-b404-226f14941f68"
        },
        "grupoSemaforico": {
          "idJson": "27024439-a90f-4568-b643-b46a98b430fd"
        }
      },
      {
        "id": "cd7891df-6207-47cd-9e2e-a7c3df124f63",
        "idJson": "13739af3-be34-44a4-a3d5-95528824d7ef",
        "estagio": {
          "idJson": "bdb72a13-a29b-4dff-ab69-05b69f433d72"
        },
        "grupoSemaforico": {
          "idJson": "e60d0d71-ca19-47f0-942c-1276ff3734dd"
        }
      },
      {
        "id": "f3727bac-670d-4e39-a21f-db85e3ae5d26",
        "idJson": "a74c1b23-a596-4774-ae10-a3a0a5123248",
        "estagio": {
          "idJson": "e3cf6c23-8180-4e8f-9c87-f7a4589aa44e"
        },
        "grupoSemaforico": {
          "idJson": "f07d9d8b-31c1-48d1-ad44-3450509e026f"
        }
      }
    ],
    "verdesConflitantes": [
      {
        "id": "79f507d0-d6b3-46b1-a2ae-9efef2c95c70",
        "idJson": "63ac962c-b23b-4e0c-8b67-466a51e3e9fb",
        "origem": {
          "idJson": "f07d9d8b-31c1-48d1-ad44-3450509e026f"
        },
        "destino": {
          "idJson": "d3f9840a-d6d5-45aa-94cf-14de2b1fec41"
        }
      },
      {
        "id": "76b2fb32-231d-4e51-bb75-846fbbb999eb",
        "idJson": "ca918ee5-f3aa-47e5-9b72-3946c3f03e46",
        "origem": {
          "idJson": "93c9f615-bb22-4cf6-a11f-b1f36b7903f0"
        },
        "destino": {
          "idJson": "eccc3c8b-218f-42f1-8bee-11fec6062d21"
        }
      },
      {
        "id": "bc01e848-931b-4796-bb4e-d7a119fdd15d",
        "idJson": "efaeee61-c44e-4d63-af29-b662a033d271",
        "origem": {
          "idJson": "27024439-a90f-4568-b643-b46a98b430fd"
        },
        "destino": {
          "idJson": "e60d0d71-ca19-47f0-942c-1276ff3734dd"
        }
      },
      {
        "id": "5cfc101a-b10f-45e2-a327-67af8d971677",
        "idJson": "bf402035-f071-4550-b148-f267ddbed79c",
        "origem": {
          "idJson": "fc0601be-87ac-42cb-b5ec-625fc445610a"
        },
        "destino": {
          "idJson": "c4c97b82-c269-4571-a84e-011e770d4b8e"
        }
      },
      {
        "id": "a9398bf2-9cf3-4ca0-9420-416574558d6d",
        "idJson": "7070acaf-743b-4812-ace5-8afb8810bbc7",
        "origem": {
          "idJson": "09316e06-fed0-46f0-86be-30eb46a20c27"
        },
        "destino": {
          "idJson": "c4c97b82-c269-4571-a84e-011e770d4b8e"
        }
      },
      {
        "id": "991d67c9-91ca-44da-ba59-c61a6c5a03aa",
        "idJson": "3e8c2ff0-3062-4c9f-b03b-5e0a1a2af8a6",
        "origem": {
          "idJson": "27024439-a90f-4568-b643-b46a98b430fd"
        },
        "destino": {
          "idJson": "89e8cab5-2337-40d6-b6a8-c7816cd1ca5e"
        }
      },
      {
        "id": "e865bbb7-7b08-4ad8-8afe-e2668d7b1ed8",
        "idJson": "a9a07e45-8f18-4726-a1fd-3be4ca36530a",
        "origem": {
          "idJson": "fc0601be-87ac-42cb-b5ec-625fc445610a"
        },
        "destino": {
          "idJson": "09316e06-fed0-46f0-86be-30eb46a20c27"
        }
      }
    ],
    "transicoes": [
      {
        "id": "8bf63fbe-8871-4071-b120-144441f9da7d",
        "idJson": "d2ec33ee-f26c-4cfa-a12a-25763dad5acf",
        "origem": {
          "idJson": "28821b34-c80e-436b-9c42-6ee2f7da81bd"
        },
        "destino": {
          "idJson": "3f6dac3e-909b-4690-b9ad-af666752a571"
        },
        "tabelaEntreVerdesTransicoes": [
          {
            "idJson": "9d364fdd-ae5d-46e2-8e81-268fc0983ccd"
          }
        ],
        "grupoSemaforico": {
          "idJson": "c4c97b82-c269-4571-a84e-011e770d4b8e"
        },
        "tipo": "PERDA_DE_PASSAGEM",
        "tempoAtrasoGrupo": "0",
        "atrasoDeGrupo": {
          "idJson": "138ec3f8-bd9c-421b-9b3e-9b875bf69ac6"
        }
      },
      {
        "id": "f379f709-fee3-47df-a7d8-424f2d98e3fb",
        "idJson": "dec9bf63-b86a-485f-946d-9a92b8df754f",
        "origem": {
          "idJson": "00f04cee-ff99-4690-9029-c88c55928096"
        },
        "destino": {
          "idJson": "28821b34-c80e-436b-9c42-6ee2f7da81bd"
        },
        "tabelaEntreVerdesTransicoes": [
          {
            "idJson": "fb4743d0-bb1d-404b-b3f2-71fff544c171"
          }
        ],
        "grupoSemaforico": {
          "idJson": "09316e06-fed0-46f0-86be-30eb46a20c27"
        },
        "tipo": "PERDA_DE_PASSAGEM",
        "tempoAtrasoGrupo": "0",
        "atrasoDeGrupo": {
          "idJson": "79a447ea-0cd9-4d53-b75e-edebc2045586"
        }
      },
      {
        "id": "56736902-3ee2-4124-9b4b-56c340f73fc4",
        "idJson": "eb7d0c1c-1fe9-4ccc-911b-c6ab20605a50",
        "origem": {
          "idJson": "513ccc36-b0dd-4846-b404-226f14941f68"
        },
        "destino": {
          "idJson": "0575779d-b038-47f0-b72e-ff11a7d47ccc"
        },
        "tabelaEntreVerdesTransicoes": [
          {
            "idJson": "ebad9c9f-836b-4fb7-b404-590dcd7a15b8"
          }
        ],
        "grupoSemaforico": {
          "idJson": "eccc3c8b-218f-42f1-8bee-11fec6062d21"
        },
        "tipo": "PERDA_DE_PASSAGEM",
        "tempoAtrasoGrupo": "0",
        "atrasoDeGrupo": {
          "idJson": "fe696de0-9329-462e-9d62-c14721f7fdc2"
        }
      },
      {
        "id": "840279e3-1dd9-4906-8be5-4e7d599f224a",
        "idJson": "e24240bd-3e67-48bd-a19c-803536a82227",
        "origem": {
          "idJson": "0575779d-b038-47f0-b72e-ff11a7d47ccc"
        },
        "destino": {
          "idJson": "513ccc36-b0dd-4846-b404-226f14941f68"
        },
        "tabelaEntreVerdesTransicoes": [
          {
            "idJson": "e8107f82-0c03-462c-a15e-1cd3930807c3"
          }
        ],
        "grupoSemaforico": {
          "idJson": "eccc3c8b-218f-42f1-8bee-11fec6062d21"
        },
        "tipo": "PERDA_DE_PASSAGEM",
        "tempoAtrasoGrupo": "0",
        "atrasoDeGrupo": {
          "idJson": "4c81ebc7-b708-4ecb-a2fc-c5f387b8a23e"
        }
      },
      {
        "id": "69f7a744-4d88-4263-8c44-190c41611a3b",
        "idJson": "444425fd-25c8-4d05-be10-f82b9b6c831f",
        "origem": {
          "idJson": "513ccc36-b0dd-4846-b404-226f14941f68"
        },
        "destino": {
          "idJson": "bdb72a13-a29b-4dff-ab69-05b69f433d72"
        },
        "tabelaEntreVerdesTransicoes": [
          {
            "idJson": "e7163c56-17f7-40d8-9cfd-6071623217e7"
          }
        ],
        "grupoSemaforico": {
          "idJson": "27024439-a90f-4568-b643-b46a98b430fd"
        },
        "tipo": "PERDA_DE_PASSAGEM",
        "tempoAtrasoGrupo": "0",
        "atrasoDeGrupo": {
          "idJson": "8a593f50-efa3-4a20-9346-60aaf573aa85"
        }
      },
      {
        "id": "a0f7fddc-a92b-4760-9938-1db7084ddfa3",
        "idJson": "69e3a75e-6488-4635-849c-ff88c470f51a",
        "origem": {
          "idJson": "3f6dac3e-909b-4690-b9ad-af666752a571"
        },
        "destino": {
          "idJson": "00f04cee-ff99-4690-9029-c88c55928096"
        },
        "tabelaEntreVerdesTransicoes": [
          {
            "idJson": "c71bd0a0-eca7-4bee-942a-fde3c0d7d16f"
          }
        ],
        "grupoSemaforico": {
          "idJson": "fc0601be-87ac-42cb-b5ec-625fc445610a"
        },
        "tipo": "PERDA_DE_PASSAGEM",
        "tempoAtrasoGrupo": "0",
        "atrasoDeGrupo": {
          "idJson": "e0d7b37b-6eeb-44a7-86ae-60b3807d7915"
        }
      },
      {
        "id": "1f28b278-f973-4e1a-8d1a-705fb807d279",
        "idJson": "d7b6a981-5697-4edb-b586-f8ceda3d6327",
        "origem": {
          "idJson": "2a189819-36c3-4cc0-a989-6f8f19cff390"
        },
        "destino": {
          "idJson": "e3cf6c23-8180-4e8f-9c87-f7a4589aa44e"
        },
        "tabelaEntreVerdesTransicoes": [
          {
            "idJson": "02e16b92-80a9-425b-a440-f616bac1b7a3"
          }
        ],
        "grupoSemaforico": {
          "idJson": "d3f9840a-d6d5-45aa-94cf-14de2b1fec41"
        },
        "tipo": "PERDA_DE_PASSAGEM",
        "tempoAtrasoGrupo": "0",
        "atrasoDeGrupo": {
          "idJson": "ee4d4f1e-7c3c-467c-b4f2-b4faac55ba11"
        }
      },
      {
        "id": "49a44cda-2850-491f-bc59-fb289fade04c",
        "idJson": "bd2cb690-2073-4885-993c-6e00ab621abf",
        "origem": {
          "idJson": "3f6dac3e-909b-4690-b9ad-af666752a571"
        },
        "destino": {
          "idJson": "28821b34-c80e-436b-9c42-6ee2f7da81bd"
        },
        "tabelaEntreVerdesTransicoes": [
          {
            "idJson": "d19ae739-2a8a-46e0-bb8f-4bb2cca67458"
          }
        ],
        "grupoSemaforico": {
          "idJson": "fc0601be-87ac-42cb-b5ec-625fc445610a"
        },
        "tipo": "PERDA_DE_PASSAGEM",
        "tempoAtrasoGrupo": "0",
        "atrasoDeGrupo": {
          "idJson": "06f25a7f-10b5-4514-8531-a1f07d3c79f3"
        }
      },
      {
        "id": "670a4034-5d09-4e7c-882d-12712803755b",
        "idJson": "d6c2300a-255e-4035-bd78-eb0857679ed6",
        "origem": {
          "idJson": "bdb72a13-a29b-4dff-ab69-05b69f433d72"
        },
        "destino": {
          "idJson": "0575779d-b038-47f0-b72e-ff11a7d47ccc"
        },
        "tabelaEntreVerdesTransicoes": [
          {
            "idJson": "10f39602-46b3-4fe8-9762-41b5cbdce1e0"
          }
        ],
        "grupoSemaforico": {
          "idJson": "e60d0d71-ca19-47f0-942c-1276ff3734dd"
        },
        "tipo": "PERDA_DE_PASSAGEM",
        "tempoAtrasoGrupo": "0",
        "atrasoDeGrupo": {
          "idJson": "dfe22096-51f6-43b1-a07c-484f2d260774"
        }
      },
      {
        "id": "c82182bb-123c-485f-ac8d-71d4b7fa1681",
        "idJson": "f3605b4f-dbe4-46bb-a950-bcc742d302cc",
        "origem": {
          "idJson": "bdb72a13-a29b-4dff-ab69-05b69f433d72"
        },
        "destino": {
          "idJson": "0575779d-b038-47f0-b72e-ff11a7d47ccc"
        },
        "tabelaEntreVerdesTransicoes": [
          {
            "idJson": "22c85e88-3811-4820-b8cf-40dc9fa64de5"
          }
        ],
        "grupoSemaforico": {
          "idJson": "93c9f615-bb22-4cf6-a11f-b1f36b7903f0"
        },
        "tipo": "PERDA_DE_PASSAGEM",
        "tempoAtrasoGrupo": "0",
        "atrasoDeGrupo": {
          "idJson": "ae9fe6be-5e1e-4549-aef0-18cb42633c02"
        }
      },
      {
        "id": "c4d0f54b-d027-4e87-8291-a172f276b261",
        "idJson": "f8bd914d-4e67-42f1-8264-391dc7782af6",
        "origem": {
          "idJson": "e3cf6c23-8180-4e8f-9c87-f7a4589aa44e"
        },
        "destino": {
          "idJson": "2a189819-36c3-4cc0-a989-6f8f19cff390"
        },
        "tabelaEntreVerdesTransicoes": [
          {
            "idJson": "7ec40cbb-d497-4daa-8864-9d6c3df75a18"
          }
        ],
        "grupoSemaforico": {
          "idJson": "f07d9d8b-31c1-48d1-ad44-3450509e026f"
        },
        "tipo": "PERDA_DE_PASSAGEM",
        "tempoAtrasoGrupo": "0",
        "atrasoDeGrupo": {
          "idJson": "45c5e895-0a35-4724-beea-8d9d874772f1"
        }
      },
      {
        "id": "26c97391-16bd-45d8-89aa-c125b0b6a765",
        "idJson": "88305f20-3caa-4dcd-b4e1-ad2d350e33a5",
        "origem": {
          "idJson": "0575779d-b038-47f0-b72e-ff11a7d47ccc"
        },
        "destino": {
          "idJson": "513ccc36-b0dd-4846-b404-226f14941f68"
        },
        "tabelaEntreVerdesTransicoes": [
          {
            "idJson": "7f21b51d-51b7-481e-851a-4c82f804a154"
          }
        ],
        "grupoSemaforico": {
          "idJson": "89e8cab5-2337-40d6-b6a8-c7816cd1ca5e"
        },
        "tipo": "PERDA_DE_PASSAGEM",
        "tempoAtrasoGrupo": "0",
        "atrasoDeGrupo": {
          "idJson": "469bbc28-f783-433f-a386-5dfbd8b51251"
        }
      },
      {
        "id": "bd664add-b845-419a-bfd8-01470e5621a1",
        "idJson": "a8dc0d86-ea75-4921-9c9c-a1777608c640",
        "origem": {
          "idJson": "00f04cee-ff99-4690-9029-c88c55928096"
        },
        "destino": {
          "idJson": "3f6dac3e-909b-4690-b9ad-af666752a571"
        },
        "tabelaEntreVerdesTransicoes": [
          {
            "idJson": "5d956368-910a-4949-b287-a6e92904fc13"
          }
        ],
        "grupoSemaforico": {
          "idJson": "09316e06-fed0-46f0-86be-30eb46a20c27"
        },
        "tipo": "PERDA_DE_PASSAGEM",
        "tempoAtrasoGrupo": "0",
        "atrasoDeGrupo": {
          "idJson": "e84c6301-421c-4dce-9a24-3710eb37bfb5"
        }
      },
      {
        "id": "5a433c00-b426-4b63-85c0-ab1e47152dca",
        "idJson": "bc75c24e-5696-41f5-a51c-ba6f7794f94d",
        "origem": {
          "idJson": "513ccc36-b0dd-4846-b404-226f14941f68"
        },
        "destino": {
          "idJson": "0575779d-b038-47f0-b72e-ff11a7d47ccc"
        },
        "tabelaEntreVerdesTransicoes": [
          {
            "idJson": "741242c9-f0cb-43be-b852-2e04a5ec50f2"
          }
        ],
        "grupoSemaforico": {
          "idJson": "27024439-a90f-4568-b643-b46a98b430fd"
        },
        "tipo": "PERDA_DE_PASSAGEM",
        "tempoAtrasoGrupo": "0",
        "atrasoDeGrupo": {
          "idJson": "c691dddb-7411-47c1-94b0-72902d32e859"
        }
      },
      {
        "id": "f4ecb5fc-a5b6-4838-97fa-28f78e82a36d",
        "idJson": "5e07234d-b178-4fc7-962a-847c92e9784d",
        "origem": {
          "idJson": "513ccc36-b0dd-4846-b404-226f14941f68"
        },
        "destino": {
          "idJson": "bdb72a13-a29b-4dff-ab69-05b69f433d72"
        },
        "tabelaEntreVerdesTransicoes": [
          {
            "idJson": "f25f61b0-64be-42b8-948c-24f3c08882a3"
          }
        ],
        "grupoSemaforico": {
          "idJson": "eccc3c8b-218f-42f1-8bee-11fec6062d21"
        },
        "tipo": "PERDA_DE_PASSAGEM",
        "tempoAtrasoGrupo": "5",
        "atrasoDeGrupo": {
          "idJson": "dae2fedc-3cda-4176-b810-cfef655d91eb"
        }
      },
      {
        "id": "8c291639-8185-4e43-ab63-797611d630b3",
        "idJson": "896e48d3-70f3-44ca-831e-b974e41b5f7b",
        "origem": {
          "idJson": "28821b34-c80e-436b-9c42-6ee2f7da81bd"
        },
        "destino": {
          "idJson": "00f04cee-ff99-4690-9029-c88c55928096"
        },
        "tabelaEntreVerdesTransicoes": [
          {
            "idJson": "7fbe3c87-70a5-4f20-b111-7035ee3384ea"
          }
        ],
        "grupoSemaforico": {
          "idJson": "c4c97b82-c269-4571-a84e-011e770d4b8e"
        },
        "tipo": "PERDA_DE_PASSAGEM",
        "tempoAtrasoGrupo": "0",
        "atrasoDeGrupo": {
          "idJson": "4cc23558-e561-4dfb-ad06-a3e738fc6d8a"
        }
      }
    ],
    "transicoesComGanhoDePassagem": [
      {
        "id": "3f2e4c4e-8f80-48d5-a800-66335ac26895",
        "idJson": "d2371b6d-11af-4757-8c90-1dc6464ddaca",
        "origem": {
          "idJson": "28821b34-c80e-436b-9c42-6ee2f7da81bd"
        },
        "destino": {
          "idJson": "3f6dac3e-909b-4690-b9ad-af666752a571"
        },
        "tabelaEntreVerdesTransicoes": [],
        "grupoSemaforico": {
          "idJson": "fc0601be-87ac-42cb-b5ec-625fc445610a"
        },
        "tipo": "GANHO_DE_PASSAGEM",
        "tempoAtrasoGrupo": "0",
        "atrasoDeGrupo": {
          "idJson": "21fa526d-96cc-4f99-977a-76343044cc85"
        }
      },
      {
        "id": "33e85d3d-e35e-4796-9f7d-6324262228a9",
        "idJson": "ee014905-a3c7-4d4c-82c8-408577f58f1d",
        "origem": {
          "idJson": "00f04cee-ff99-4690-9029-c88c55928096"
        },
        "destino": {
          "idJson": "3f6dac3e-909b-4690-b9ad-af666752a571"
        },
        "tabelaEntreVerdesTransicoes": [],
        "grupoSemaforico": {
          "idJson": "fc0601be-87ac-42cb-b5ec-625fc445610a"
        },
        "tipo": "GANHO_DE_PASSAGEM",
        "tempoAtrasoGrupo": "0",
        "atrasoDeGrupo": {
          "idJson": "b31ed703-f63a-4c40-b966-32411c2d7560"
        }
      },
      {
        "id": "b5b163ce-713a-4b1f-b7a2-1304f78a4dc2",
        "idJson": "b56b4d45-8fbc-4211-8ed7-27330937fa79",
        "origem": {
          "idJson": "3f6dac3e-909b-4690-b9ad-af666752a571"
        },
        "destino": {
          "idJson": "00f04cee-ff99-4690-9029-c88c55928096"
        },
        "tabelaEntreVerdesTransicoes": [],
        "grupoSemaforico": {
          "idJson": "09316e06-fed0-46f0-86be-30eb46a20c27"
        },
        "tipo": "GANHO_DE_PASSAGEM",
        "tempoAtrasoGrupo": "0",
        "atrasoDeGrupo": {
          "idJson": "f694e0a4-e2a4-4e76-8dec-c553468c069f"
        }
      },
      {
        "id": "a5c21717-18b2-47ab-a096-ac34ee1a2942",
        "idJson": "d6642ae7-6ca2-481b-b458-de6c260db08f",
        "origem": {
          "idJson": "0575779d-b038-47f0-b72e-ff11a7d47ccc"
        },
        "destino": {
          "idJson": "513ccc36-b0dd-4846-b404-226f14941f68"
        },
        "tabelaEntreVerdesTransicoes": [],
        "grupoSemaforico": {
          "idJson": "27024439-a90f-4568-b643-b46a98b430fd"
        },
        "tipo": "GANHO_DE_PASSAGEM",
        "tempoAtrasoGrupo": "0",
        "atrasoDeGrupo": {
          "idJson": "f996ba97-c667-45ec-89bd-e395aef39118"
        }
      },
      {
        "id": "8a324ce4-144b-47ab-a0ea-24e79db38b35",
        "idJson": "7a76e208-830d-4b87-8423-ab553f5267eb",
        "origem": {
          "idJson": "28821b34-c80e-436b-9c42-6ee2f7da81bd"
        },
        "destino": {
          "idJson": "00f04cee-ff99-4690-9029-c88c55928096"
        },
        "tabelaEntreVerdesTransicoes": [],
        "grupoSemaforico": {
          "idJson": "09316e06-fed0-46f0-86be-30eb46a20c27"
        },
        "tipo": "GANHO_DE_PASSAGEM",
        "tempoAtrasoGrupo": "0",
        "atrasoDeGrupo": {
          "idJson": "8e21abad-157d-4e89-ad00-d430c87b1f58"
        }
      },
      {
        "id": "c2095d65-0dc1-41fa-99cb-5e35128ec5b4",
        "idJson": "32204ffb-bd31-4aa8-972d-264d1ab45797",
        "origem": {
          "idJson": "513ccc36-b0dd-4846-b404-226f14941f68"
        },
        "destino": {
          "idJson": "bdb72a13-a29b-4dff-ab69-05b69f433d72"
        },
        "tabelaEntreVerdesTransicoes": [],
        "grupoSemaforico": {
          "idJson": "93c9f615-bb22-4cf6-a11f-b1f36b7903f0"
        },
        "tipo": "GANHO_DE_PASSAGEM",
        "tempoAtrasoGrupo": "0",
        "atrasoDeGrupo": {
          "idJson": "1956fafd-a46f-45bb-a862-ed4f83b1a7f1"
        }
      },
      {
        "id": "18d00b47-ca9f-4d8b-9e20-4f99690435ec",
        "idJson": "9a6482e8-7d9f-488a-8c5a-fc7841832eb2",
        "origem": {
          "idJson": "e3cf6c23-8180-4e8f-9c87-f7a4589aa44e"
        },
        "destino": {
          "idJson": "2a189819-36c3-4cc0-a989-6f8f19cff390"
        },
        "tabelaEntreVerdesTransicoes": [],
        "grupoSemaforico": {
          "idJson": "d3f9840a-d6d5-45aa-94cf-14de2b1fec41"
        },
        "tipo": "GANHO_DE_PASSAGEM",
        "tempoAtrasoGrupo": "0",
        "atrasoDeGrupo": {
          "idJson": "3f6c4b43-9143-4fee-ae23-7a651aa969c7"
        }
      },
      {
        "id": "d855ae0c-2536-4814-8b0e-7d105440a9b0",
        "idJson": "277045a5-e2b9-434d-8c02-de0ebb4b3d47",
        "origem": {
          "idJson": "3f6dac3e-909b-4690-b9ad-af666752a571"
        },
        "destino": {
          "idJson": "28821b34-c80e-436b-9c42-6ee2f7da81bd"
        },
        "tabelaEntreVerdesTransicoes": [],
        "grupoSemaforico": {
          "idJson": "c4c97b82-c269-4571-a84e-011e770d4b8e"
        },
        "tipo": "GANHO_DE_PASSAGEM",
        "tempoAtrasoGrupo": "0",
        "atrasoDeGrupo": {
          "idJson": "d95cfd03-b425-40c7-87e3-86b6eb04fc75"
        }
      },
      {
        "id": "bcadb5ad-0071-47cd-bd70-8c2757acb4f1",
        "idJson": "d65b55f8-a970-4756-a6ab-bbf711b786ba",
        "origem": {
          "idJson": "0575779d-b038-47f0-b72e-ff11a7d47ccc"
        },
        "destino": {
          "idJson": "513ccc36-b0dd-4846-b404-226f14941f68"
        },
        "tabelaEntreVerdesTransicoes": [],
        "grupoSemaforico": {
          "idJson": "eccc3c8b-218f-42f1-8bee-11fec6062d21"
        },
        "tipo": "GANHO_DE_PASSAGEM",
        "tempoAtrasoGrupo": "0",
        "atrasoDeGrupo": {
          "idJson": "f9193497-3f21-4b5f-9800-8950308bcfb2"
        }
      },
      {
        "id": "0c1e2e24-c838-4abb-a9a0-5160ed2fe1dc",
        "idJson": "16cbd3bb-cf8a-47e0-8879-16f0526bd000",
        "origem": {
          "idJson": "bdb72a13-a29b-4dff-ab69-05b69f433d72"
        },
        "destino": {
          "idJson": "0575779d-b038-47f0-b72e-ff11a7d47ccc"
        },
        "tabelaEntreVerdesTransicoes": [],
        "grupoSemaforico": {
          "idJson": "eccc3c8b-218f-42f1-8bee-11fec6062d21"
        },
        "tipo": "GANHO_DE_PASSAGEM",
        "tempoAtrasoGrupo": "0",
        "atrasoDeGrupo": {
          "idJson": "d8b4e8f7-5f6b-4394-b153-ae74af3de5f3"
        }
      },
      {
        "id": "3f64ae22-e00c-43b6-967c-794e9b26413d",
        "idJson": "b56bff80-0f58-48a6-b891-439125656ce1",
        "origem": {
          "idJson": "513ccc36-b0dd-4846-b404-226f14941f68"
        },
        "destino": {
          "idJson": "0575779d-b038-47f0-b72e-ff11a7d47ccc"
        },
        "tabelaEntreVerdesTransicoes": [],
        "grupoSemaforico": {
          "idJson": "eccc3c8b-218f-42f1-8bee-11fec6062d21"
        },
        "tipo": "GANHO_DE_PASSAGEM",
        "tempoAtrasoGrupo": "0",
        "atrasoDeGrupo": {
          "idJson": "39cf3152-8535-4bc6-bf99-3817bb3573c1"
        }
      },
      {
        "id": "3d3d1a6e-8a28-44a5-836f-697ff99d3cec",
        "idJson": "f1ec1441-a755-4cdd-9256-e2cde3331f15",
        "origem": {
          "idJson": "bdb72a13-a29b-4dff-ab69-05b69f433d72"
        },
        "destino": {
          "idJson": "0575779d-b038-47f0-b72e-ff11a7d47ccc"
        },
        "tabelaEntreVerdesTransicoes": [],
        "grupoSemaforico": {
          "idJson": "89e8cab5-2337-40d6-b6a8-c7816cd1ca5e"
        },
        "tipo": "GANHO_DE_PASSAGEM",
        "tempoAtrasoGrupo": "0",
        "atrasoDeGrupo": {
          "idJson": "b24492db-a537-441a-8a70-42496f22fcd2"
        }
      },
      {
        "id": "f4e5fd7c-56bc-4be5-99fb-d5d5426c2486",
        "idJson": "ea161bad-92d8-416e-bc72-480b2dfcb161",
        "origem": {
          "idJson": "513ccc36-b0dd-4846-b404-226f14941f68"
        },
        "destino": {
          "idJson": "0575779d-b038-47f0-b72e-ff11a7d47ccc"
        },
        "tabelaEntreVerdesTransicoes": [],
        "grupoSemaforico": {
          "idJson": "89e8cab5-2337-40d6-b6a8-c7816cd1ca5e"
        },
        "tipo": "GANHO_DE_PASSAGEM",
        "tempoAtrasoGrupo": "0",
        "atrasoDeGrupo": {
          "idJson": "29555a67-c694-4a0d-8419-43477fc6c359"
        }
      },
      {
        "id": "b2f99595-44e7-439f-a483-22c2853f80c0",
        "idJson": "73bac9a1-21ba-438a-a766-dfd397266689",
        "origem": {
          "idJson": "513ccc36-b0dd-4846-b404-226f14941f68"
        },
        "destino": {
          "idJson": "bdb72a13-a29b-4dff-ab69-05b69f433d72"
        },
        "tabelaEntreVerdesTransicoes": [],
        "grupoSemaforico": {
          "idJson": "e60d0d71-ca19-47f0-942c-1276ff3734dd"
        },
        "tipo": "GANHO_DE_PASSAGEM",
        "tempoAtrasoGrupo": "0",
        "atrasoDeGrupo": {
          "idJson": "bd0fbc56-d804-4d70-bca0-252aaeac488f"
        }
      },
      {
        "id": "a8ffb33b-39e8-4321-ad0d-174a3991fa3c",
        "idJson": "9ebf40f9-b393-4a4b-8784-1ba04c36c290",
        "origem": {
          "idJson": "00f04cee-ff99-4690-9029-c88c55928096"
        },
        "destino": {
          "idJson": "28821b34-c80e-436b-9c42-6ee2f7da81bd"
        },
        "tabelaEntreVerdesTransicoes": [],
        "grupoSemaforico": {
          "idJson": "c4c97b82-c269-4571-a84e-011e770d4b8e"
        },
        "tipo": "GANHO_DE_PASSAGEM",
        "tempoAtrasoGrupo": "0",
        "atrasoDeGrupo": {
          "idJson": "a33b7102-331b-4be7-84f2-d117a5d5ac91"
        }
      },
      {
        "id": "5a17f181-299c-4c9b-8d6f-5b2fb45746e8",
        "idJson": "98553938-7586-43b1-a2d8-6b17af5e53e5",
        "origem": {
          "idJson": "2a189819-36c3-4cc0-a989-6f8f19cff390"
        },
        "destino": {
          "idJson": "e3cf6c23-8180-4e8f-9c87-f7a4589aa44e"
        },
        "tabelaEntreVerdesTransicoes": [],
        "grupoSemaforico": {
          "idJson": "f07d9d8b-31c1-48d1-ad44-3450509e026f"
        },
        "tipo": "GANHO_DE_PASSAGEM",
        "tempoAtrasoGrupo": "0",
        "atrasoDeGrupo": {
          "idJson": "a6f38291-71a5-4da1-a875-65ae5bd49199"
        }
      }
    ],
    "tabelasEntreVerdes": [
      {
        "id": "73ca5152-d43f-4687-885c-c2038eb0505f",
        "idJson": "21a41bb4-4e72-4a7c-8dd5-763e1c6209d0",
        "descricao": "PADRÃO",
        "posicao": 1,
        "grupoSemaforico": {
          "idJson": "c4c97b82-c269-4571-a84e-011e770d4b8e"
        },
        "tabelaEntreVerdesTransicoes": [
          {
            "idJson": "7fbe3c87-70a5-4f20-b111-7035ee3384ea"
          },
          {
            "idJson": "9d364fdd-ae5d-46e2-8e81-268fc0983ccd"
          }
        ]
      },
      {
        "id": "db85b45a-c62b-431a-bfa7-361a168d93e7",
        "idJson": "7f0aec59-5ad6-4dc7-ac09-aa1bdd1b5fb5",
        "descricao": "PADRÃO",
        "posicao": 1,
        "grupoSemaforico": {
          "idJson": "eccc3c8b-218f-42f1-8bee-11fec6062d21"
        },
        "tabelaEntreVerdesTransicoes": [
          {
            "idJson": "f25f61b0-64be-42b8-948c-24f3c08882a3"
          },
          {
            "idJson": "e8107f82-0c03-462c-a15e-1cd3930807c3"
          },
          {
            "idJson": "ebad9c9f-836b-4fb7-b404-590dcd7a15b8"
          }
        ]
      },
      {
        "id": "1d394a4f-84c7-4252-b7b6-64fe06cca908",
        "idJson": "02dfce93-5029-4d0f-a623-ee736b8e2a99",
        "descricao": "PADRÃO",
        "posicao": 1,
        "grupoSemaforico": {
          "idJson": "d3f9840a-d6d5-45aa-94cf-14de2b1fec41"
        },
        "tabelaEntreVerdesTransicoes": [
          {
            "idJson": "02e16b92-80a9-425b-a440-f616bac1b7a3"
          }
        ]
      },
      {
        "id": "af309fe6-9115-4b14-8e75-84e0f867f9c6",
        "idJson": "378b346d-5f84-4de3-bf50-6d3c1853fa08",
        "descricao": "PADRÃO",
        "posicao": 1,
        "grupoSemaforico": {
          "idJson": "89e8cab5-2337-40d6-b6a8-c7816cd1ca5e"
        },
        "tabelaEntreVerdesTransicoes": [
          {
            "idJson": "7f21b51d-51b7-481e-851a-4c82f804a154"
          }
        ]
      },
      {
        "id": "aa2ddbce-312d-455b-83ca-5f46e51747ee",
        "idJson": "5867fb67-2628-4f96-8fd6-28fbd3189523",
        "descricao": "PADRÃO",
        "posicao": 1,
        "grupoSemaforico": {
          "idJson": "f07d9d8b-31c1-48d1-ad44-3450509e026f"
        },
        "tabelaEntreVerdesTransicoes": [
          {
            "idJson": "7ec40cbb-d497-4daa-8864-9d6c3df75a18"
          }
        ]
      },
      {
        "id": "ac0008d1-5076-43b2-8d49-be6ec7ed0bde",
        "idJson": "01c385b3-7a4e-4379-bf61-577f9835f4fd",
        "descricao": "PADRÃO",
        "posicao": 1,
        "grupoSemaforico": {
          "idJson": "93c9f615-bb22-4cf6-a11f-b1f36b7903f0"
        },
        "tabelaEntreVerdesTransicoes": [
          {
            "idJson": "22c85e88-3811-4820-b8cf-40dc9fa64de5"
          }
        ]
      },
      {
        "id": "91aead0b-c4c9-4125-a80a-dec415dc40fa",
        "idJson": "7987e164-fa4a-46a7-b928-74679d410e91",
        "descricao": "PADRÃO",
        "posicao": 1,
        "grupoSemaforico": {
          "idJson": "e60d0d71-ca19-47f0-942c-1276ff3734dd"
        },
        "tabelaEntreVerdesTransicoes": [
          {
            "idJson": "10f39602-46b3-4fe8-9762-41b5cbdce1e0"
          }
        ]
      },
      {
        "id": "c787b366-3bd1-44a1-a7b9-d698191fe3bb",
        "idJson": "ec2e3e78-f726-41ba-a5b7-56835ab3798d",
        "descricao": "PADRÃO",
        "posicao": 1,
        "grupoSemaforico": {
          "idJson": "fc0601be-87ac-42cb-b5ec-625fc445610a"
        },
        "tabelaEntreVerdesTransicoes": [
          {
            "idJson": "c71bd0a0-eca7-4bee-942a-fde3c0d7d16f"
          },
          {
            "idJson": "d19ae739-2a8a-46e0-bb8f-4bb2cca67458"
          }
        ]
      },
      {
        "id": "916b340f-72e9-4b4c-b396-15d2b895740b",
        "idJson": "d25bdd29-99a3-484f-a423-41103be68ba1",
        "descricao": "PADRÃO",
        "posicao": 1,
        "grupoSemaforico": {
          "idJson": "09316e06-fed0-46f0-86be-30eb46a20c27"
        },
        "tabelaEntreVerdesTransicoes": [
          {
            "idJson": "5d956368-910a-4949-b287-a6e92904fc13"
          },
          {
            "idJson": "fb4743d0-bb1d-404b-b3f2-71fff544c171"
          }
        ]
      },
      {
        "id": "9c7238b5-4655-463a-a1e9-c84bae4dff79",
        "idJson": "4bc2ea66-02c4-4836-b10c-05bc09a4f682",
        "descricao": "PADRÃO",
        "posicao": 1,
        "grupoSemaforico": {
          "idJson": "27024439-a90f-4568-b643-b46a98b430fd"
        },
        "tabelaEntreVerdesTransicoes": [
          {
            "idJson": "741242c9-f0cb-43be-b852-2e04a5ec50f2"
          },
          {
            "idJson": "e7163c56-17f7-40d8-9cfd-6071623217e7"
          }
        ]
      }
    ],
    "tabelasEntreVerdesTransicoes": [
      {
        "id": "654fcd86-9392-4212-bd4f-c675fb8e66a3",
        "idJson": "7ec40cbb-d497-4daa-8864-9d6c3df75a18",
        "tempoAmarelo": "3",
        "tempoVermelhoLimpeza": "2",
        "tempoAtrasoGrupo": "0",
        "tabelaEntreVerdes": {
          "idJson": "5867fb67-2628-4f96-8fd6-28fbd3189523"
        },
        "transicao": {
          "idJson": "f8bd914d-4e67-42f1-8264-391dc7782af6"
        }
      },
      {
        "id": "ee39c74d-712f-4c50-bef1-0260b2912c91",
        "idJson": "02e16b92-80a9-425b-a440-f616bac1b7a3",
        "tempoAmarelo": "3",
        "tempoVermelhoLimpeza": "2",
        "tempoAtrasoGrupo": "0",
        "tabelaEntreVerdes": {
          "idJson": "02dfce93-5029-4d0f-a623-ee736b8e2a99"
        },
        "transicao": {
          "idJson": "d7b6a981-5697-4edb-b586-f8ceda3d6327"
        }
      },
      {
        "id": "463974b9-c8b0-4945-8853-5c5054947d47",
        "idJson": "22c85e88-3811-4820-b8cf-40dc9fa64de5",
        "tempoVermelhoIntermitente": "9",
        "tempoVermelhoLimpeza": "1",
        "tempoAtrasoGrupo": "0",
        "tabelaEntreVerdes": {
          "idJson": "01c385b3-7a4e-4379-bf61-577f9835f4fd"
        },
        "transicao": {
          "idJson": "f3605b4f-dbe4-46bb-a950-bcc742d302cc"
        }
      },
      {
        "id": "f0fc584f-13c1-430f-a513-dad625affe5d",
        "idJson": "ebad9c9f-836b-4fb7-b404-590dcd7a15b8",
        "tempoAmarelo": "3",
        "tempoVermelhoLimpeza": "2",
        "tempoAtrasoGrupo": "0",
        "tabelaEntreVerdes": {
          "idJson": "7f0aec59-5ad6-4dc7-ac09-aa1bdd1b5fb5"
        },
        "transicao": {
          "idJson": "eb7d0c1c-1fe9-4ccc-911b-c6ab20605a50"
        }
      },
      {
        "id": "232a627a-7e7d-457f-81e6-c4e04ee5b4f2",
        "idJson": "5d956368-910a-4949-b287-a6e92904fc13",
        "tempoAmarelo": "3",
        "tempoVermelhoLimpeza": "2",
        "tempoAtrasoGrupo": "0",
        "tabelaEntreVerdes": {
          "idJson": "d25bdd29-99a3-484f-a423-41103be68ba1"
        },
        "transicao": {
          "idJson": "a8dc0d86-ea75-4921-9c9c-a1777608c640"
        }
      },
      {
        "id": "d3007827-e7a0-4c09-b43c-a67674813ff4",
        "idJson": "e8107f82-0c03-462c-a15e-1cd3930807c3",
        "tempoAmarelo": "3",
        "tempoVermelhoLimpeza": "2",
        "tempoAtrasoGrupo": "0",
        "tabelaEntreVerdes": {
          "idJson": "7f0aec59-5ad6-4dc7-ac09-aa1bdd1b5fb5"
        },
        "transicao": {
          "idJson": "e24240bd-3e67-48bd-a19c-803536a82227"
        }
      },
      {
        "id": "75f2fef4-94f2-4b83-bc16-f697987b08ec",
        "idJson": "9d364fdd-ae5d-46e2-8e81-268fc0983ccd",
        "tempoAmarelo": "3",
        "tempoVermelhoLimpeza": "2",
        "tempoAtrasoGrupo": "0",
        "tabelaEntreVerdes": {
          "idJson": "21a41bb4-4e72-4a7c-8dd5-763e1c6209d0"
        },
        "transicao": {
          "idJson": "d2ec33ee-f26c-4cfa-a12a-25763dad5acf"
        }
      },
      {
        "id": "063f3d2e-43c8-4418-8aa2-119119938373",
        "idJson": "c71bd0a0-eca7-4bee-942a-fde3c0d7d16f",
        "tempoAmarelo": "3",
        "tempoVermelhoLimpeza": "2",
        "tempoAtrasoGrupo": "0",
        "tabelaEntreVerdes": {
          "idJson": "ec2e3e78-f726-41ba-a5b7-56835ab3798d"
        },
        "transicao": {
          "idJson": "69e3a75e-6488-4635-849c-ff88c470f51a"
        }
      },
      {
        "id": "b3e95236-3daf-476b-bc05-b4666fe97363",
        "idJson": "e7163c56-17f7-40d8-9cfd-6071623217e7",
        "tempoAmarelo": "3",
        "tempoVermelhoLimpeza": "2",
        "tempoAtrasoGrupo": "0",
        "tabelaEntreVerdes": {
          "idJson": "4bc2ea66-02c4-4836-b10c-05bc09a4f682"
        },
        "transicao": {
          "idJson": "444425fd-25c8-4d05-be10-f82b9b6c831f"
        }
      },
      {
        "id": "391b2017-6513-4626-a70c-4d2e6d17f5c7",
        "idJson": "7fbe3c87-70a5-4f20-b111-7035ee3384ea",
        "tempoAmarelo": "3",
        "tempoVermelhoLimpeza": "2",
        "tempoAtrasoGrupo": "0",
        "tabelaEntreVerdes": {
          "idJson": "21a41bb4-4e72-4a7c-8dd5-763e1c6209d0"
        },
        "transicao": {
          "idJson": "896e48d3-70f3-44ca-831e-b974e41b5f7b"
        }
      },
      {
        "id": "ca5e3317-4dbf-4aa7-b657-3ce0414e1346",
        "idJson": "10f39602-46b3-4fe8-9762-41b5cbdce1e0",
        "tempoAmarelo": "3",
        "tempoVermelhoLimpeza": "2",
        "tempoAtrasoGrupo": "0",
        "tabelaEntreVerdes": {
          "idJson": "7987e164-fa4a-46a7-b928-74679d410e91"
        },
        "transicao": {
          "idJson": "d6c2300a-255e-4035-bd78-eb0857679ed6"
        }
      },
      {
        "id": "3e74fa2f-7580-49b2-81b0-1de72c576b8e",
        "idJson": "fb4743d0-bb1d-404b-b3f2-71fff544c171",
        "tempoAmarelo": "3",
        "tempoVermelhoLimpeza": "2",
        "tempoAtrasoGrupo": "0",
        "tabelaEntreVerdes": {
          "idJson": "d25bdd29-99a3-484f-a423-41103be68ba1"
        },
        "transicao": {
          "idJson": "dec9bf63-b86a-485f-946d-9a92b8df754f"
        }
      },
      {
        "id": "80241b75-13ab-46ed-87c4-7b4f0a67d643",
        "idJson": "d19ae739-2a8a-46e0-bb8f-4bb2cca67458",
        "tempoAmarelo": "3",
        "tempoVermelhoLimpeza": "2",
        "tempoAtrasoGrupo": "0",
        "tabelaEntreVerdes": {
          "idJson": "ec2e3e78-f726-41ba-a5b7-56835ab3798d"
        },
        "transicao": {
          "idJson": "bd2cb690-2073-4885-993c-6e00ab621abf"
        }
      },
      {
        "id": "ff69591e-f629-4b4c-93e4-84638302901a",
        "idJson": "7f21b51d-51b7-481e-851a-4c82f804a154",
        "tempoAmarelo": "3",
        "tempoVermelhoLimpeza": "2",
        "tempoAtrasoGrupo": "0",
        "tabelaEntreVerdes": {
          "idJson": "378b346d-5f84-4de3-bf50-6d3c1853fa08"
        },
        "transicao": {
          "idJson": "88305f20-3caa-4dcd-b4e1-ad2d350e33a5"
        }
      },
      {
        "id": "461d103d-570b-40f1-bda3-d183058609d1",
        "idJson": "f25f61b0-64be-42b8-948c-24f3c08882a3",
        "tempoAmarelo": "3",
        "tempoVermelhoLimpeza": "2",
        "tempoAtrasoGrupo": "5",
        "tabelaEntreVerdes": {
          "idJson": "7f0aec59-5ad6-4dc7-ac09-aa1bdd1b5fb5"
        },
        "transicao": {
          "idJson": "5e07234d-b178-4fc7-962a-847c92e9784d"
        }
      },
      {
        "id": "a527d03a-b25a-48bf-aace-5806d27ab927",
        "idJson": "741242c9-f0cb-43be-b852-2e04a5ec50f2",
        "tempoAmarelo": "3",
        "tempoVermelhoLimpeza": "2",
        "tempoAtrasoGrupo": "0",
        "tabelaEntreVerdes": {
          "idJson": "4bc2ea66-02c4-4836-b10c-05bc09a4f682"
        },
        "transicao": {
          "idJson": "bc75c24e-5696-41f5-a51c-ba6f7794f94d"
        }
      }
    ],
    "planos": [
      {
        "id": "3aa7b5ac-f0fd-475a-9133-325ed90070a0",
        "idJson": "986222a1-e6e9-40bf-b79e-1f7aeb88c5ce",
        "posicao": 4,
        "descricao": "PLANO 4",
        "tempoCiclo": 150,
        "defasagem": 10,
        "posicaoTabelaEntreVerde": 1,
        "modoOperacao": "TEMPO_FIXO_COORDENADO",
        "dataCriacao": "27/10/2016 15:51:51",
        "dataAtualizacao": "27/10/2016 16:01:34",
        "anel": {
          "idJson": "cd610cd4-f2cf-4079-93a2-fad92a85f493"
        },
        "versaoPlano": {
          "idJson": "fb186624-2c73-49f9-8d7c-3fc9d3b7de63"
        },
        "estagiosPlanos": [
          {
            "idJson": "572cc401-ebf2-4cb3-b68e-4c9ecd44af53"
          },
          {
            "idJson": "e7c54bdf-7c7f-485a-8c5c-f10ad853b9a5"
          }
        ],
        "gruposSemaforicosPlanos": [
          {
            "idJson": "0ce4f617-2336-4874-a5af-716939c4de88"
          },
          {
            "idJson": "44e7037f-6126-45c3-8a40-a0f230fe4674"
          }
        ]
      },
      {
        "id": "a0460b53-d1b8-437b-80ac-8ca84dd65921",
        "idJson": "abe96d47-fa89-4b12-a674-ee26741d861a",
        "posicao": 2,
        "descricao": "PLANO 2",
        "tempoCiclo": 120,
        "defasagem": 20,
        "posicaoTabelaEntreVerde": 1,
        "modoOperacao": "TEMPO_FIXO_COORDENADO",
        "dataCriacao": "27/10/2016 15:51:51",
        "dataAtualizacao": "27/10/2016 16:01:34",
        "anel": {
          "idJson": "078e9420-b13b-48ba-beeb-95ba45d9d8d3"
        },
        "versaoPlano": {
          "idJson": "9df20e92-0b20-42d5-93d6-297cd4289319"
        },
        "estagiosPlanos": [
          {
            "idJson": "a2d08c74-1155-4080-822f-629d8f28df19"
          },
          {
            "idJson": "8d09ed2a-9b64-4c5c-aeed-0dbad32650c4"
          },
          {
            "idJson": "0ec2e9a8-3ba2-4855-98e7-a8fa62b46f46"
          }
        ],
        "gruposSemaforicosPlanos": [
          {
            "idJson": "99e20545-c535-4b30-8dd0-6c74e2424def"
          },
          {
            "idJson": "02d2b530-7e99-48fd-81eb-0f27aaad2b8f"
          },
          {
            "idJson": "af73c79f-2508-4813-b8de-7a787b11b9ba"
          }
        ]
      },
      {
        "id": "35783ce0-7098-4280-9a7d-108768836ca9",
        "idJson": "2cc8025b-debb-4694-b459-297b59f9fd21",
        "posicao": 6,
        "descricao": "PLANO 6",
        "tempoCiclo": 100,
        "defasagem": 20,
        "posicaoTabelaEntreVerde": 1,
        "modoOperacao": "TEMPO_FIXO_COORDENADO",
        "dataCriacao": "27/10/2016 15:52:59",
        "dataAtualizacao": "27/10/2016 16:01:34",
        "anel": {
          "idJson": "078e9420-b13b-48ba-beeb-95ba45d9d8d3"
        },
        "versaoPlano": {
          "idJson": "9df20e92-0b20-42d5-93d6-297cd4289319"
        },
        "estagiosPlanos": [
          {
            "idJson": "9ac13292-76dd-408f-a236-77e11c7f1c55"
          },
          {
            "idJson": "19e39603-d937-434f-bbed-6e9b19c6417e"
          },
          {
            "idJson": "1bbdb634-e8ea-417e-87a7-507bf2267292"
          }
        ],
        "gruposSemaforicosPlanos": [
          {
            "idJson": "d66c21b7-dee9-4f63-9bf5-b63c14eb9428"
          },
          {
            "idJson": "6c6a0a1c-e96e-46d1-a448-01cda7979dbc"
          },
          {
            "idJson": "60e83003-cab2-41ef-8d0f-3d74b7c98613"
          }
        ]
      },
      {
        "id": "7b16d4c5-4783-4f3b-86a7-b45d4348fa53",
        "idJson": "2914de80-2f0c-4fda-9d38-e126a253fa76",
        "posicao": 1,
        "descricao": "PLANO 1",
        "tempoCiclo": 100,
        "defasagem": 0,
        "posicaoTabelaEntreVerde": 1,
        "modoOperacao": "TEMPO_FIXO_COORDENADO",
        "dataCriacao": "20/10/2016 09:51:00",
        "dataAtualizacao": "27/10/2016 16:01:34",
        "anel": {
          "idJson": "d9c67923-8710-4fb5-866a-38c028ee86a4"
        },
        "versaoPlano": {
          "idJson": "c2aff556-c252-4e9d-a03c-637d3a2d0ed5"
        },
        "estagiosPlanos": [
          {
            "idJson": "aa6e5a8e-2a57-475d-8230-c3ba0ca0c0ae"
          },
          {
            "idJson": "9d7bb8d0-2ce0-4ce8-929c-5d44efff3452"
          },
          {
            "idJson": "845e96e7-80d1-4ca0-9423-dcfc04245bc9"
          }
        ],
        "gruposSemaforicosPlanos": [
          {
            "idJson": "3d14937c-1ead-40ea-b206-c2829c4d1f3a"
          },
          {
            "idJson": "dd1655d0-613c-4af2-bc4f-2e84902c5d01"
          },
          {
            "idJson": "b075aa32-1fbd-4b58-a5fd-05774eb0cd6d"
          },
          {
            "idJson": "2d0ca816-c419-4c59-a620-e4012a35af5c"
          },
          {
            "idJson": "b150e3c4-18ed-4b8a-92ff-26ffaa0b170a"
          }
        ]
      },
      {
        "id": "a004bd7f-9bff-4ce9-bb79-e023a57d5b5a",
        "idJson": "294c9a8e-57a0-4a1f-8e49-d4a1b4015fc6",
        "posicao": 2,
        "descricao": "PLANO 2",
        "tempoCiclo": 120,
        "defasagem": 0,
        "posicaoTabelaEntreVerde": 1,
        "modoOperacao": "TEMPO_FIXO_COORDENADO",
        "dataCriacao": "27/10/2016 15:43:42",
        "dataAtualizacao": "27/10/2016 16:01:34",
        "anel": {
          "idJson": "d9c67923-8710-4fb5-866a-38c028ee86a4"
        },
        "versaoPlano": {
          "idJson": "c2aff556-c252-4e9d-a03c-637d3a2d0ed5"
        },
        "estagiosPlanos": [
          {
            "idJson": "667a1aed-a129-4286-9f6e-9bea3e08f8da"
          },
          {
            "idJson": "faa5acd5-389a-41a9-9c7d-bb225852b8e9"
          },
          {
            "idJson": "7e172e4f-fedf-45d7-abdf-c89b41f7ecb2"
          }
        ],
        "gruposSemaforicosPlanos": [
          {
            "idJson": "82930d31-e9c5-4dd0-b287-173c00e4875c"
          },
          {
            "idJson": "e4f2a6e3-e040-491c-a0f9-330c3b60758c"
          },
          {
            "idJson": "8ea424f7-7dc0-4ee8-bd2f-c5419e176fc9"
          },
          {
            "idJson": "a86c34cc-9f97-495d-aae7-30e9e25ca2d0"
          },
          {
            "idJson": "53d33c25-8851-4815-bf30-26d361c10e82"
          }
        ]
      },
      {
        "id": "b1906fc7-205e-40c6-b0a1-dfb8fd79a17b",
        "idJson": "3839e784-5006-45e3-8742-b06b6801e153",
        "posicao": 6,
        "descricao": "PLANO 6",
        "tempoCiclo": 100,
        "defasagem": 0,
        "posicaoTabelaEntreVerde": 1,
        "modoOperacao": "TEMPO_FIXO_COORDENADO",
        "dataCriacao": "27/10/2016 15:52:59",
        "dataAtualizacao": "27/10/2016 16:01:34",
        "anel": {
          "idJson": "d9c67923-8710-4fb5-866a-38c028ee86a4"
        },
        "versaoPlano": {
          "idJson": "c2aff556-c252-4e9d-a03c-637d3a2d0ed5"
        },
        "estagiosPlanos": [
          {
            "idJson": "a5204462-cee2-407f-9713-c0ccb4566677"
          },
          {
            "idJson": "741efab8-8f3b-4991-ad7c-c591449d398f"
          },
          {
            "idJson": "1d8f595a-92e1-4806-b76f-5789fcc8ab34"
          }
        ],
        "gruposSemaforicosPlanos": [
          {
            "idJson": "e2d05229-3d02-4022-946c-addcec001103"
          },
          {
            "idJson": "c03dc485-03bc-4949-9f2b-eec4dcc6dafd"
          },
          {
            "idJson": "fe76461b-a7cc-4055-9c10-151d121083dd"
          },
          {
            "idJson": "80239416-2975-4cc0-afcf-1e95cd590594"
          },
          {
            "idJson": "8c8f4f99-df4b-48cc-b3e9-e31520dfa8cd"
          }
        ]
      },
      {
        "id": "c0fd3707-7f04-47b0-87f4-901b0ceaa9d8",
        "idJson": "b962be0a-3b23-4efa-aef0-c19e461d8859",
        "posicao": 0,
        "descricao": "Exclusivo",
        "tempoCiclo": 30,
        "defasagem": 0,
        "posicaoTabelaEntreVerde": 1,
        "modoOperacao": "MANUAL",
        "dataCriacao": "20/10/2016 09:51:00",
        "dataAtualizacao": "27/10/2016 16:01:34",
        "anel": {
          "idJson": "cd610cd4-f2cf-4079-93a2-fad92a85f493"
        },
        "versaoPlano": {
          "idJson": "fb186624-2c73-49f9-8d7c-3fc9d3b7de63"
        },
        "estagiosPlanos": [
          {
            "idJson": "1e56b2b7-268e-456e-ab31-3f71053a16e1"
          },
          {
            "idJson": "f1e79171-3faa-498c-9b38-3d60d2f961b4"
          }
        ],
        "gruposSemaforicosPlanos": [
          {
            "idJson": "ecd955ba-53d8-4e12-a941-b2d9aad8ddf3"
          },
          {
            "idJson": "40c78d56-7fe0-48b3-9d9a-a561e0ba8aca"
          }
        ]
      },
      {
        "id": "435209bd-485f-4b82-89cd-768a853deaf1",
        "idJson": "8ea9bc20-9313-4113-a0b7-51e16df68eb4",
        "posicao": 2,
        "descricao": "PLANO 2",
        "tempoCiclo": 120,
        "defasagem": 10,
        "posicaoTabelaEntreVerde": 1,
        "modoOperacao": "TEMPO_FIXO_COORDENADO",
        "dataCriacao": "27/10/2016 15:51:51",
        "dataAtualizacao": "27/10/2016 16:01:34",
        "anel": {
          "idJson": "cd610cd4-f2cf-4079-93a2-fad92a85f493"
        },
        "versaoPlano": {
          "idJson": "fb186624-2c73-49f9-8d7c-3fc9d3b7de63"
        },
        "estagiosPlanos": [
          {
            "idJson": "260a212e-f0d1-4652-81e3-37b3fdb17dc8"
          },
          {
            "idJson": "b4ec2c9e-6c5d-46fc-8240-d1b5766a55a7"
          }
        ],
        "gruposSemaforicosPlanos": [
          {
            "idJson": "2c031aa0-b11e-4656-aec7-7d1f770b1d58"
          },
          {
            "idJson": "babcef7f-b9a8-47cc-9893-b899bd2d3d8a"
          }
        ]
      },
      {
        "id": "be356830-9930-4674-90f1-2455c150b41a",
        "idJson": "d666dabb-03a6-4e66-9818-9c198f6f669e",
        "posicao": 0,
        "descricao": "Exclusivo",
        "tempoCiclo": 30,
        "defasagem": 0,
        "posicaoTabelaEntreVerde": 1,
        "modoOperacao": "MANUAL",
        "dataCriacao": "20/10/2016 09:51:00",
        "dataAtualizacao": "27/10/2016 16:01:34",
        "anel": {
          "idJson": "078e9420-b13b-48ba-beeb-95ba45d9d8d3"
        },
        "versaoPlano": {
          "idJson": "9df20e92-0b20-42d5-93d6-297cd4289319"
        },
        "estagiosPlanos": [
          {
            "idJson": "3d75895a-9359-42fd-a384-6f6a87c6d79d"
          },
          {
            "idJson": "6074ac32-f44b-4ff5-a8e9-522727f15d81"
          }
        ],
        "gruposSemaforicosPlanos": [
          {
            "idJson": "cb9bd906-cd71-42a1-a91e-b1e72a72b193"
          },
          {
            "idJson": "ddcdbeeb-6f87-4ccc-8945-22e268674e3d"
          },
          {
            "idJson": "a6cc3f54-6737-401f-8a32-c36b495c34ff"
          }
        ]
      },
      {
        "id": "f59d5408-6e02-4bc7-9967-d7358bb779e8",
        "idJson": "7d56b43d-09c6-4dad-a16c-c333a8f716da",
        "posicao": 5,
        "descricao": "PLANO 5",
        "tempoCiclo": 120,
        "defasagem": 0,
        "posicaoTabelaEntreVerde": 1,
        "modoOperacao": "TEMPO_FIXO_COORDENADO",
        "dataCriacao": "27/10/2016 15:43:42",
        "dataAtualizacao": "27/10/2016 16:01:34",
        "anel": {
          "idJson": "d9c67923-8710-4fb5-866a-38c028ee86a4"
        },
        "versaoPlano": {
          "idJson": "c2aff556-c252-4e9d-a03c-637d3a2d0ed5"
        },
        "estagiosPlanos": [
          {
            "idJson": "67984282-eb6a-4bbc-a317-89f546c7810d"
          },
          {
            "idJson": "94c9090d-17ef-4bfa-8116-e58473458b30"
          },
          {
            "idJson": "2e813cb6-c89c-4783-a455-b04e9314c654"
          }
        ],
        "gruposSemaforicosPlanos": [
          {
            "idJson": "798ebf36-c2ee-4751-95f3-098f718a277e"
          },
          {
            "idJson": "8aff3bda-4c5b-444e-9c25-91ffa95e5b4d"
          },
          {
            "idJson": "d1688f0f-a3d6-4d8b-906e-b17160c8fa3a"
          },
          {
            "idJson": "7b6c07a6-b484-45f0-b296-fb88e06fcff7"
          },
          {
            "idJson": "e987f8c4-d9f1-48e1-bcd7-564fcc2fd9df"
          }
        ]
      },
      {
        "id": "770097f5-805a-4802-8a0b-d43a27a48078",
        "idJson": "906ba906-527f-44ce-8da2-6cbab108b4ee",
        "posicao": 5,
        "descricao": "PLANO 5",
        "tempoCiclo": 120,
        "defasagem": 20,
        "posicaoTabelaEntreVerde": 1,
        "modoOperacao": "TEMPO_FIXO_COORDENADO",
        "dataCriacao": "27/10/2016 15:51:51",
        "dataAtualizacao": "27/10/2016 16:01:34",
        "anel": {
          "idJson": "078e9420-b13b-48ba-beeb-95ba45d9d8d3"
        },
        "versaoPlano": {
          "idJson": "9df20e92-0b20-42d5-93d6-297cd4289319"
        },
        "estagiosPlanos": [
          {
            "idJson": "3fa147dc-f754-4012-b5cc-7127140e33e1"
          },
          {
            "idJson": "cd11786d-e161-4a89-a428-680574476414"
          },
          {
            "idJson": "e72964d4-0495-4b90-9a3c-d83a744e41fc"
          }
        ],
        "gruposSemaforicosPlanos": [
          {
            "idJson": "01710734-88aa-4316-af22-632b30194ea4"
          },
          {
            "idJson": "6fd05896-5dd0-40f9-b5ea-8acb4edd1be7"
          },
          {
            "idJson": "c44f9802-bace-478c-8ddc-0c8dcac70aee"
          }
        ]
      },
      {
        "id": "c74f8ef8-07da-48b7-909e-d2042f8561d4",
        "idJson": "abdaffeb-10e7-4134-8584-2b017c0ca408",
        "posicao": 5,
        "descricao": "PLANO 5",
        "tempoCiclo": 120,
        "defasagem": 10,
        "posicaoTabelaEntreVerde": 1,
        "modoOperacao": "TEMPO_FIXO_COORDENADO",
        "dataCriacao": "27/10/2016 15:51:51",
        "dataAtualizacao": "27/10/2016 16:01:34",
        "anel": {
          "idJson": "cd610cd4-f2cf-4079-93a2-fad92a85f493"
        },
        "versaoPlano": {
          "idJson": "fb186624-2c73-49f9-8d7c-3fc9d3b7de63"
        },
        "estagiosPlanos": [
          {
            "idJson": "0dc47cf3-e088-472b-87c5-289cc5b422d1"
          },
          {
            "idJson": "7a92067e-7200-4a40-bd75-f3ad20185da2"
          }
        ],
        "gruposSemaforicosPlanos": [
          {
            "idJson": "d9fbc30d-af05-43fd-b66c-8f48de0e0a37"
          },
          {
            "idJson": "61e2eeea-1a7a-49a2-8859-eb133d3660e6"
          }
        ]
      },
      {
        "id": "65e3796f-cd5a-475e-8053-0041f74cc8c8",
        "idJson": "163f67f0-cfd8-48f2-94af-c7f59940dc55",
        "posicao": 4,
        "descricao": "PLANO 4",
        "tempoCiclo": 150,
        "defasagem": 0,
        "posicaoTabelaEntreVerde": 1,
        "modoOperacao": "TEMPO_FIXO_COORDENADO",
        "dataCriacao": "27/10/2016 15:43:42",
        "dataAtualizacao": "27/10/2016 16:01:34",
        "anel": {
          "idJson": "d9c67923-8710-4fb5-866a-38c028ee86a4"
        },
        "versaoPlano": {
          "idJson": "c2aff556-c252-4e9d-a03c-637d3a2d0ed5"
        },
        "estagiosPlanos": [
          {
            "idJson": "0bce0ff9-7384-42db-bf8a-0363641ff271"
          },
          {
            "idJson": "05a42ee4-ea59-471d-9259-a86a9a83763a"
          },
          {
            "idJson": "61d2fb94-cc53-4c10-9d91-4383a19a76c9"
          }
        ],
        "gruposSemaforicosPlanos": [
          {
            "idJson": "789a5903-c5b1-4e5d-a255-70018e778804"
          },
          {
            "idJson": "cac087c7-3e46-450d-8e03-ba9c12223468"
          },
          {
            "idJson": "03f6b757-d9f9-4c31-af26-19c16404f1c7"
          },
          {
            "idJson": "cfc1fb5c-5315-4919-831e-b3f9c8be1368"
          },
          {
            "idJson": "186310fb-07fb-45b6-91a7-80a39769d89c"
          }
        ]
      },
      {
        "id": "a3bbac03-aa73-4047-9e15-75d7ccf948b6",
        "idJson": "8ad00916-355c-4fc5-bdec-4c2cb2187e27",
        "posicao": 0,
        "descricao": "Exclusivo",
        "tempoCiclo": 30,
        "defasagem": 0,
        "posicaoTabelaEntreVerde": 1,
        "modoOperacao": "MANUAL",
        "dataCriacao": "20/10/2016 09:51:00",
        "dataAtualizacao": "27/10/2016 16:01:34",
        "anel": {
          "idJson": "d9c67923-8710-4fb5-866a-38c028ee86a4"
        },
        "versaoPlano": {
          "idJson": "c2aff556-c252-4e9d-a03c-637d3a2d0ed5"
        },
        "estagiosPlanos": [
          {
            "idJson": "551bcef2-b277-4110-8239-2ac5a31ca4b7"
          },
          {
            "idJson": "80216539-df79-405b-866b-bd13c98a2bf6"
          },
          {
            "idJson": "da0833b5-4e07-49ef-a824-27ba46a02274"
          }
        ],
        "gruposSemaforicosPlanos": [
          {
            "idJson": "b221f70c-61b2-40b8-ad8d-f59dc86d2c8d"
          },
          {
            "idJson": "b25ff2e0-be66-445c-abbb-62d22c3b89a9"
          },
          {
            "idJson": "b5b4cf27-0796-4c75-a4b6-c3b004a647f5"
          },
          {
            "idJson": "3ec42f13-d0b7-4243-a636-2d9208d4eb95"
          },
          {
            "idJson": "b9a36d41-dedb-4a8b-a362-500f900fa3b4"
          }
        ]
      },
      {
        "id": "c921bec5-fc2a-44c4-b72d-f397d9ba1f38",
        "idJson": "7669c3e5-b086-4c41-bd5c-cdcb19a13147",
        "posicao": 6,
        "descricao": "PLANO 6",
        "tempoCiclo": 100,
        "defasagem": 10,
        "posicaoTabelaEntreVerde": 1,
        "modoOperacao": "TEMPO_FIXO_COORDENADO",
        "dataCriacao": "27/10/2016 15:52:59",
        "dataAtualizacao": "27/10/2016 16:01:34",
        "anel": {
          "idJson": "cd610cd4-f2cf-4079-93a2-fad92a85f493"
        },
        "versaoPlano": {
          "idJson": "fb186624-2c73-49f9-8d7c-3fc9d3b7de63"
        },
        "estagiosPlanos": [
          {
            "idJson": "6a1f3bed-e9ec-4fdc-9792-457384a410d4"
          },
          {
            "idJson": "017b9015-ea7a-4bc7-a6d9-84f460aafc90"
          }
        ],
        "gruposSemaforicosPlanos": [
          {
            "idJson": "3de64147-c947-4074-a23c-cad0d0656267"
          },
          {
            "idJson": "f099b2db-da62-4fc8-88dc-df3eff79eebe"
          }
        ]
      },
      {
        "id": "62c0e37b-4fff-4eb0-9a58-30201e5f29da",
        "idJson": "aa614e64-a228-428c-93d9-f586fcdfb68a",
        "posicao": 1,
        "descricao": "PLANO 1",
        "tempoCiclo": 100,
        "defasagem": 10,
        "posicaoTabelaEntreVerde": 1,
        "modoOperacao": "TEMPO_FIXO_COORDENADO",
        "dataCriacao": "20/10/2016 09:51:00",
        "dataAtualizacao": "27/10/2016 16:01:34",
        "anel": {
          "idJson": "cd610cd4-f2cf-4079-93a2-fad92a85f493"
        },
        "versaoPlano": {
          "idJson": "fb186624-2c73-49f9-8d7c-3fc9d3b7de63"
        },
        "estagiosPlanos": [
          {
            "idJson": "e555a4b5-7e68-4ee0-a2ac-4f3137eeb85f"
          },
          {
            "idJson": "612b982a-a6c2-4f1e-9399-c961b5f57954"
          }
        ],
        "gruposSemaforicosPlanos": [
          {
            "idJson": "77bd14da-d4c9-44c5-9611-632bac8b9c44"
          },
          {
            "idJson": "a2d1ecb0-0d95-4a22-8786-bc796874960c"
          }
        ]
      },
      {
        "id": "3cb69e37-4d6f-4512-822f-001e16510012",
        "idJson": "15a9c3e1-fe1d-4ea8-a6d1-2d3aff4ed177",
        "posicao": 4,
        "descricao": "PLANO 4",
        "tempoCiclo": 150,
        "defasagem": 20,
        "posicaoTabelaEntreVerde": 1,
        "modoOperacao": "TEMPO_FIXO_COORDENADO",
        "dataCriacao": "27/10/2016 15:51:51",
        "dataAtualizacao": "27/10/2016 16:01:34",
        "anel": {
          "idJson": "078e9420-b13b-48ba-beeb-95ba45d9d8d3"
        },
        "versaoPlano": {
          "idJson": "9df20e92-0b20-42d5-93d6-297cd4289319"
        },
        "estagiosPlanos": [
          {
            "idJson": "d66bc893-737a-456e-a095-c4170d3ee543"
          },
          {
            "idJson": "84b081f7-8291-411e-b929-fd9d07e3e4c1"
          },
          {
            "idJson": "840d1cba-11eb-474f-8258-0e4b83d6b575"
          }
        ],
        "gruposSemaforicosPlanos": [
          {
            "idJson": "970987cd-de38-4676-b8b5-57087d25383d"
          },
          {
            "idJson": "c90091b2-6818-447e-a543-c2fb2201a21c"
          },
          {
            "idJson": "38b7b150-8a63-44fa-8174-835d6d505fdb"
          }
        ]
      },
      {
        "id": "482fec3e-7f4a-4ee6-9ed4-faedec602212",
        "idJson": "9c3ae287-1e26-4688-9489-870757c48936",
        "posicao": 1,
        "descricao": "PLANO 1",
        "tempoCiclo": 100,
        "defasagem": 20,
        "posicaoTabelaEntreVerde": 1,
        "modoOperacao": "TEMPO_FIXO_COORDENADO",
        "dataCriacao": "20/10/2016 09:51:00",
        "dataAtualizacao": "27/10/2016 16:01:34",
        "anel": {
          "idJson": "078e9420-b13b-48ba-beeb-95ba45d9d8d3"
        },
        "versaoPlano": {
          "idJson": "9df20e92-0b20-42d5-93d6-297cd4289319"
        },
        "estagiosPlanos": [
          {
            "idJson": "e700267a-a0a6-48f4-a6de-08fecd02904d"
          },
          {
            "idJson": "1356d7ba-ab1d-4ae8-88bb-e6738acf7a65"
          },
          {
            "idJson": "5e21cdd9-6852-4629-a3d0-177c5c0defa1"
          }
        ],
        "gruposSemaforicosPlanos": [
          {
            "idJson": "2ba304b2-c2b9-4cec-9eb8-0de45150ac32"
          },
          {
            "idJson": "00a32ed7-d0aa-43ff-a5f4-68c0889a5922"
          },
          {
            "idJson": "3aba071f-2d8c-4954-a987-3bd28241e579"
          }
        ]
      }
    ],
    "gruposSemaforicosPlanos": [
      {
        "id": "5f72b432-5fe0-4901-a9f6-275b838383b3",
        "idJson": "8ea424f7-7dc0-4ee8-bd2f-c5419e176fc9",
        "plano": {
          "idJson": "294c9a8e-57a0-4a1f-8e49-d4a1b4015fc6"
        },
        "grupoSemaforico": {
          "idJson": "89e8cab5-2337-40d6-b6a8-c7816cd1ca5e"
        },
        "ativado": true
      },
      {
        "id": "60684757-d5f1-4d12-a337-024de9f282b2",
        "idJson": "b25ff2e0-be66-445c-abbb-62d22c3b89a9",
        "plano": {
          "idJson": "8ad00916-355c-4fc5-bdec-4c2cb2187e27"
        },
        "grupoSemaforico": {
          "idJson": "e60d0d71-ca19-47f0-942c-1276ff3734dd"
        },
        "ativado": true
      },
      {
        "id": "e4291f18-ea88-47bd-a2b3-ca7cc5b86fc0",
        "idJson": "60e83003-cab2-41ef-8d0f-3d74b7c98613",
        "plano": {
          "idJson": "2cc8025b-debb-4694-b459-297b59f9fd21"
        },
        "grupoSemaforico": {
          "idJson": "fc0601be-87ac-42cb-b5ec-625fc445610a"
        },
        "ativado": true
      },
      {
        "id": "8b4a4150-8cd9-47af-b1e4-0a8ddd72796f",
        "idJson": "fe76461b-a7cc-4055-9c10-151d121083dd",
        "plano": {
          "idJson": "3839e784-5006-45e3-8742-b06b6801e153"
        },
        "grupoSemaforico": {
          "idJson": "89e8cab5-2337-40d6-b6a8-c7816cd1ca5e"
        },
        "ativado": true
      },
      {
        "id": "833f4575-468d-496d-af0a-4ec91f193a5b",
        "idJson": "00a32ed7-d0aa-43ff-a5f4-68c0889a5922",
        "plano": {
          "idJson": "9c3ae287-1e26-4688-9489-870757c48936"
        },
        "grupoSemaforico": {
          "idJson": "09316e06-fed0-46f0-86be-30eb46a20c27"
        },
        "ativado": true
      },
      {
        "id": "c28c1fc9-ceb6-4093-b1af-74486d8d9958",
        "idJson": "b9a36d41-dedb-4a8b-a362-500f900fa3b4",
        "plano": {
          "idJson": "8ad00916-355c-4fc5-bdec-4c2cb2187e27"
        },
        "grupoSemaforico": {
          "idJson": "27024439-a90f-4568-b643-b46a98b430fd"
        },
        "ativado": true
      },
      {
        "id": "9b8ba970-a14e-4177-8896-c0df7352f1a9",
        "idJson": "186310fb-07fb-45b6-91a7-80a39769d89c",
        "plano": {
          "idJson": "163f67f0-cfd8-48f2-94af-c7f59940dc55"
        },
        "grupoSemaforico": {
          "idJson": "27024439-a90f-4568-b643-b46a98b430fd"
        },
        "ativado": true
      },
      {
        "id": "ead78a9a-33f9-45d9-8c52-7298451f0064",
        "idJson": "53d33c25-8851-4815-bf30-26d361c10e82",
        "plano": {
          "idJson": "294c9a8e-57a0-4a1f-8e49-d4a1b4015fc6"
        },
        "grupoSemaforico": {
          "idJson": "eccc3c8b-218f-42f1-8bee-11fec6062d21"
        },
        "ativado": true
      },
      {
        "id": "998229ea-5bca-426f-b328-99ec7db83f80",
        "idJson": "babcef7f-b9a8-47cc-9893-b899bd2d3d8a",
        "plano": {
          "idJson": "8ea9bc20-9313-4113-a0b7-51e16df68eb4"
        },
        "grupoSemaforico": {
          "idJson": "f07d9d8b-31c1-48d1-ad44-3450509e026f"
        },
        "ativado": true
      },
      {
        "id": "f8e89b46-d3d4-4aa1-a795-ef04ae9043bf",
        "idJson": "c44f9802-bace-478c-8ddc-0c8dcac70aee",
        "plano": {
          "idJson": "906ba906-527f-44ce-8da2-6cbab108b4ee"
        },
        "grupoSemaforico": {
          "idJson": "09316e06-fed0-46f0-86be-30eb46a20c27"
        },
        "ativado": true
      },
      {
        "id": "0451d43c-4bcf-42cb-8262-1d64bc079482",
        "idJson": "789a5903-c5b1-4e5d-a255-70018e778804",
        "plano": {
          "idJson": "163f67f0-cfd8-48f2-94af-c7f59940dc55"
        },
        "grupoSemaforico": {
          "idJson": "e60d0d71-ca19-47f0-942c-1276ff3734dd"
        },
        "ativado": true
      },
      {
        "id": "5c15943e-f1b6-4bb2-a700-916efbe892d7",
        "idJson": "7b6c07a6-b484-45f0-b296-fb88e06fcff7",
        "plano": {
          "idJson": "7d56b43d-09c6-4dad-a16c-c333a8f716da"
        },
        "grupoSemaforico": {
          "idJson": "93c9f615-bb22-4cf6-a11f-b1f36b7903f0"
        },
        "ativado": true
      },
      {
        "id": "a576bb7f-4c55-46a0-82c9-db173fb6d764",
        "idJson": "8c8f4f99-df4b-48cc-b3e9-e31520dfa8cd",
        "plano": {
          "idJson": "3839e784-5006-45e3-8742-b06b6801e153"
        },
        "grupoSemaforico": {
          "idJson": "27024439-a90f-4568-b643-b46a98b430fd"
        },
        "ativado": true
      },
      {
        "id": "82304cd6-4e4d-410b-ab33-a830abcaebbb",
        "idJson": "61e2eeea-1a7a-49a2-8859-eb133d3660e6",
        "plano": {
          "idJson": "abdaffeb-10e7-4134-8584-2b017c0ca408"
        },
        "grupoSemaforico": {
          "idJson": "f07d9d8b-31c1-48d1-ad44-3450509e026f"
        },
        "ativado": true
      },
      {
        "id": "57a26f3f-6daf-488d-8729-ecb611a9414a",
        "idJson": "a2d1ecb0-0d95-4a22-8786-bc796874960c",
        "plano": {
          "idJson": "aa614e64-a228-428c-93d9-f586fcdfb68a"
        },
        "grupoSemaforico": {
          "idJson": "f07d9d8b-31c1-48d1-ad44-3450509e026f"
        },
        "ativado": true
      },
      {
        "id": "7612acbb-1d10-4fdc-b5a8-20a5f527c120",
        "idJson": "dd1655d0-613c-4af2-bc4f-2e84902c5d01",
        "plano": {
          "idJson": "2914de80-2f0c-4fda-9d38-e126a253fa76"
        },
        "grupoSemaforico": {
          "idJson": "89e8cab5-2337-40d6-b6a8-c7816cd1ca5e"
        },
        "ativado": true
      },
      {
        "id": "acd0173b-cd07-4753-8053-32a6db2f868c",
        "idJson": "3ec42f13-d0b7-4243-a636-2d9208d4eb95",
        "plano": {
          "idJson": "8ad00916-355c-4fc5-bdec-4c2cb2187e27"
        },
        "grupoSemaforico": {
          "idJson": "eccc3c8b-218f-42f1-8bee-11fec6062d21"
        },
        "ativado": true
      },
      {
        "id": "4e778947-0d18-4c6e-bd5e-0359fa34787b",
        "idJson": "a6cc3f54-6737-401f-8a32-c36b495c34ff",
        "plano": {
          "idJson": "d666dabb-03a6-4e66-9818-9c198f6f669e"
        },
        "grupoSemaforico": {
          "idJson": "09316e06-fed0-46f0-86be-30eb46a20c27"
        },
        "ativado": true
      },
      {
        "id": "7d4a4627-0385-43d5-92a0-f76254dd9269",
        "idJson": "0ce4f617-2336-4874-a5af-716939c4de88",
        "plano": {
          "idJson": "986222a1-e6e9-40bf-b79e-1f7aeb88c5ce"
        },
        "grupoSemaforico": {
          "idJson": "f07d9d8b-31c1-48d1-ad44-3450509e026f"
        },
        "ativado": true
      },
      {
        "id": "3495c2e7-494c-45f4-a496-25de47410c79",
        "idJson": "3de64147-c947-4074-a23c-cad0d0656267",
        "plano": {
          "idJson": "7669c3e5-b086-4c41-bd5c-cdcb19a13147"
        },
        "grupoSemaforico": {
          "idJson": "d3f9840a-d6d5-45aa-94cf-14de2b1fec41"
        },
        "ativado": true
      },
      {
        "id": "200bb984-de5a-4710-aae0-689da30d3b40",
        "idJson": "03f6b757-d9f9-4c31-af26-19c16404f1c7",
        "plano": {
          "idJson": "163f67f0-cfd8-48f2-94af-c7f59940dc55"
        },
        "grupoSemaforico": {
          "idJson": "eccc3c8b-218f-42f1-8bee-11fec6062d21"
        },
        "ativado": true
      },
      {
        "id": "a3c2f02f-6669-4d13-9809-403351eeeae2",
        "idJson": "b5b4cf27-0796-4c75-a4b6-c3b004a647f5",
        "plano": {
          "idJson": "8ad00916-355c-4fc5-bdec-4c2cb2187e27"
        },
        "grupoSemaforico": {
          "idJson": "89e8cab5-2337-40d6-b6a8-c7816cd1ca5e"
        },
        "ativado": true
      },
      {
        "id": "360b2e3d-6fc7-4164-a9f1-c7fb146c1008",
        "idJson": "77bd14da-d4c9-44c5-9611-632bac8b9c44",
        "plano": {
          "idJson": "aa614e64-a228-428c-93d9-f586fcdfb68a"
        },
        "grupoSemaforico": {
          "idJson": "d3f9840a-d6d5-45aa-94cf-14de2b1fec41"
        },
        "ativado": true
      },
      {
        "id": "d02f1c82-61f5-49de-a284-fca9951e7e55",
        "idJson": "af73c79f-2508-4813-b8de-7a787b11b9ba",
        "plano": {
          "idJson": "abe96d47-fa89-4b12-a674-ee26741d861a"
        },
        "grupoSemaforico": {
          "idJson": "09316e06-fed0-46f0-86be-30eb46a20c27"
        },
        "ativado": true
      },
      {
        "id": "966229b2-3dd5-4f66-bf46-384dc9dc5f30",
        "idJson": "80239416-2975-4cc0-afcf-1e95cd590594",
        "plano": {
          "idJson": "3839e784-5006-45e3-8742-b06b6801e153"
        },
        "grupoSemaforico": {
          "idJson": "e60d0d71-ca19-47f0-942c-1276ff3734dd"
        },
        "ativado": true
      },
      {
        "id": "06d20cb0-9385-4aee-a439-ce614a07bfd0",
        "idJson": "99e20545-c535-4b30-8dd0-6c74e2424def",
        "plano": {
          "idJson": "abe96d47-fa89-4b12-a674-ee26741d861a"
        },
        "grupoSemaforico": {
          "idJson": "fc0601be-87ac-42cb-b5ec-625fc445610a"
        },
        "ativado": true
      },
      {
        "id": "6d8d2954-7a5e-4220-b53a-1233788973e9",
        "idJson": "e987f8c4-d9f1-48e1-bcd7-564fcc2fd9df",
        "plano": {
          "idJson": "7d56b43d-09c6-4dad-a16c-c333a8f716da"
        },
        "grupoSemaforico": {
          "idJson": "89e8cab5-2337-40d6-b6a8-c7816cd1ca5e"
        },
        "ativado": true
      },
      {
        "id": "ac7b9700-c94a-4440-9e30-afa58a496fd9",
        "idJson": "01710734-88aa-4316-af22-632b30194ea4",
        "plano": {
          "idJson": "906ba906-527f-44ce-8da2-6cbab108b4ee"
        },
        "grupoSemaforico": {
          "idJson": "fc0601be-87ac-42cb-b5ec-625fc445610a"
        },
        "ativado": true
      },
      {
        "id": "118f88cb-fc4a-488d-b889-308c845406f7",
        "idJson": "d9fbc30d-af05-43fd-b66c-8f48de0e0a37",
        "plano": {
          "idJson": "abdaffeb-10e7-4134-8584-2b017c0ca408"
        },
        "grupoSemaforico": {
          "idJson": "d3f9840a-d6d5-45aa-94cf-14de2b1fec41"
        },
        "ativado": true
      },
      {
        "id": "e34aff24-0373-497b-afaf-65baa735633d",
        "idJson": "38b7b150-8a63-44fa-8174-835d6d505fdb",
        "plano": {
          "idJson": "15a9c3e1-fe1d-4ea8-a6d1-2d3aff4ed177"
        },
        "grupoSemaforico": {
          "idJson": "09316e06-fed0-46f0-86be-30eb46a20c27"
        },
        "ativado": true
      },
      {
        "id": "396f60f8-f337-41bd-b9d3-c54311fe4a17",
        "idJson": "e4f2a6e3-e040-491c-a0f9-330c3b60758c",
        "plano": {
          "idJson": "294c9a8e-57a0-4a1f-8e49-d4a1b4015fc6"
        },
        "grupoSemaforico": {
          "idJson": "93c9f615-bb22-4cf6-a11f-b1f36b7903f0"
        },
        "ativado": true
      },
      {
        "id": "8c5a3ca9-cdb4-42bb-ad12-e841c3c673ea",
        "idJson": "2c031aa0-b11e-4656-aec7-7d1f770b1d58",
        "plano": {
          "idJson": "8ea9bc20-9313-4113-a0b7-51e16df68eb4"
        },
        "grupoSemaforico": {
          "idJson": "d3f9840a-d6d5-45aa-94cf-14de2b1fec41"
        },
        "ativado": true
      },
      {
        "id": "090d566a-ba6d-4a70-8362-ee559fd42466",
        "idJson": "cac087c7-3e46-450d-8e03-ba9c12223468",
        "plano": {
          "idJson": "163f67f0-cfd8-48f2-94af-c7f59940dc55"
        },
        "grupoSemaforico": {
          "idJson": "93c9f615-bb22-4cf6-a11f-b1f36b7903f0"
        },
        "ativado": true
      },
      {
        "id": "7bfaad0b-d5e4-42eb-9a56-4402c33a857b",
        "idJson": "6c6a0a1c-e96e-46d1-a448-01cda7979dbc",
        "plano": {
          "idJson": "2cc8025b-debb-4694-b459-297b59f9fd21"
        },
        "grupoSemaforico": {
          "idJson": "09316e06-fed0-46f0-86be-30eb46a20c27"
        },
        "ativado": true
      },
      {
        "id": "4af93ff9-0891-4fe1-be05-3883473c7206",
        "idJson": "c03dc485-03bc-4949-9f2b-eec4dcc6dafd",
        "plano": {
          "idJson": "3839e784-5006-45e3-8742-b06b6801e153"
        },
        "grupoSemaforico": {
          "idJson": "eccc3c8b-218f-42f1-8bee-11fec6062d21"
        },
        "ativado": true
      },
      {
        "id": "c05a58df-acb3-418c-b722-937dc22cf177",
        "idJson": "b075aa32-1fbd-4b58-a5fd-05774eb0cd6d",
        "plano": {
          "idJson": "2914de80-2f0c-4fda-9d38-e126a253fa76"
        },
        "grupoSemaforico": {
          "idJson": "27024439-a90f-4568-b643-b46a98b430fd"
        },
        "ativado": true
      },
      {
        "id": "a536b742-80ea-4522-9d26-e3d64adaf213",
        "idJson": "a86c34cc-9f97-495d-aae7-30e9e25ca2d0",
        "plano": {
          "idJson": "294c9a8e-57a0-4a1f-8e49-d4a1b4015fc6"
        },
        "grupoSemaforico": {
          "idJson": "27024439-a90f-4568-b643-b46a98b430fd"
        },
        "ativado": true
      },
      {
        "id": "26fca13c-dbed-4240-8c8b-3586b416b8e4",
        "idJson": "d1688f0f-a3d6-4d8b-906e-b17160c8fa3a",
        "plano": {
          "idJson": "7d56b43d-09c6-4dad-a16c-c333a8f716da"
        },
        "grupoSemaforico": {
          "idJson": "eccc3c8b-218f-42f1-8bee-11fec6062d21"
        },
        "ativado": true
      },
      {
        "id": "6f757cd4-d645-4ca2-b64d-6166e85cb236",
        "idJson": "970987cd-de38-4676-b8b5-57087d25383d",
        "plano": {
          "idJson": "15a9c3e1-fe1d-4ea8-a6d1-2d3aff4ed177"
        },
        "grupoSemaforico": {
          "idJson": "fc0601be-87ac-42cb-b5ec-625fc445610a"
        },
        "ativado": true
      },
      {
        "id": "48e44a15-6007-4424-9511-f2a0b6e2139d",
        "idJson": "02d2b530-7e99-48fd-81eb-0f27aaad2b8f",
        "plano": {
          "idJson": "abe96d47-fa89-4b12-a674-ee26741d861a"
        },
        "grupoSemaforico": {
          "idJson": "c4c97b82-c269-4571-a84e-011e770d4b8e"
        },
        "ativado": true
      },
      {
        "id": "a36d81eb-eda8-48b6-af0a-f7a1ae4c09a5",
        "idJson": "40c78d56-7fe0-48b3-9d9a-a561e0ba8aca",
        "plano": {
          "idJson": "b962be0a-3b23-4efa-aef0-c19e461d8859"
        },
        "grupoSemaforico": {
          "idJson": "f07d9d8b-31c1-48d1-ad44-3450509e026f"
        },
        "ativado": true
      },
      {
        "id": "6765be28-22de-41b2-a6a8-1e3a1a8a0da1",
        "idJson": "f099b2db-da62-4fc8-88dc-df3eff79eebe",
        "plano": {
          "idJson": "7669c3e5-b086-4c41-bd5c-cdcb19a13147"
        },
        "grupoSemaforico": {
          "idJson": "f07d9d8b-31c1-48d1-ad44-3450509e026f"
        },
        "ativado": true
      },
      {
        "id": "4cf16af7-0831-4c3a-8b7f-eefc3e3d36a5",
        "idJson": "3d14937c-1ead-40ea-b206-c2829c4d1f3a",
        "plano": {
          "idJson": "2914de80-2f0c-4fda-9d38-e126a253fa76"
        },
        "grupoSemaforico": {
          "idJson": "93c9f615-bb22-4cf6-a11f-b1f36b7903f0"
        },
        "ativado": true
      },
      {
        "id": "458a265c-f78b-4203-b4af-ec4d81221908",
        "idJson": "ddcdbeeb-6f87-4ccc-8945-22e268674e3d",
        "plano": {
          "idJson": "d666dabb-03a6-4e66-9818-9c198f6f669e"
        },
        "grupoSemaforico": {
          "idJson": "fc0601be-87ac-42cb-b5ec-625fc445610a"
        },
        "ativado": true
      },
      {
        "id": "953cef19-b101-46c9-921b-02f3199e688d",
        "idJson": "3aba071f-2d8c-4954-a987-3bd28241e579",
        "plano": {
          "idJson": "9c3ae287-1e26-4688-9489-870757c48936"
        },
        "grupoSemaforico": {
          "idJson": "c4c97b82-c269-4571-a84e-011e770d4b8e"
        },
        "ativado": true
      },
      {
        "id": "25de0cf5-56dd-48bb-a14a-4c4e11fddb47",
        "idJson": "d66c21b7-dee9-4f63-9bf5-b63c14eb9428",
        "plano": {
          "idJson": "2cc8025b-debb-4694-b459-297b59f9fd21"
        },
        "grupoSemaforico": {
          "idJson": "c4c97b82-c269-4571-a84e-011e770d4b8e"
        },
        "ativado": true
      },
      {
        "id": "e7f0749b-8e7b-4ee3-9110-b7719aaad1df",
        "idJson": "b150e3c4-18ed-4b8a-92ff-26ffaa0b170a",
        "plano": {
          "idJson": "2914de80-2f0c-4fda-9d38-e126a253fa76"
        },
        "grupoSemaforico": {
          "idJson": "eccc3c8b-218f-42f1-8bee-11fec6062d21"
        },
        "ativado": true
      },
      {
        "id": "5d764607-5e48-4ebf-8164-ea1c80345dcb",
        "idJson": "ecd955ba-53d8-4e12-a941-b2d9aad8ddf3",
        "plano": {
          "idJson": "b962be0a-3b23-4efa-aef0-c19e461d8859"
        },
        "grupoSemaforico": {
          "idJson": "d3f9840a-d6d5-45aa-94cf-14de2b1fec41"
        },
        "ativado": true
      },
      {
        "id": "07dd13c9-0c60-4093-969c-759dcddc9a3b",
        "idJson": "798ebf36-c2ee-4751-95f3-098f718a277e",
        "plano": {
          "idJson": "7d56b43d-09c6-4dad-a16c-c333a8f716da"
        },
        "grupoSemaforico": {
          "idJson": "27024439-a90f-4568-b643-b46a98b430fd"
        },
        "ativado": true
      },
      {
        "id": "d9928417-0d56-48cb-af1b-ca414985bdcd",
        "idJson": "c90091b2-6818-447e-a543-c2fb2201a21c",
        "plano": {
          "idJson": "15a9c3e1-fe1d-4ea8-a6d1-2d3aff4ed177"
        },
        "grupoSemaforico": {
          "idJson": "c4c97b82-c269-4571-a84e-011e770d4b8e"
        },
        "ativado": true
      },
      {
        "id": "03dcd82d-0b6f-44c5-959e-717652db4d5b",
        "idJson": "82930d31-e9c5-4dd0-b287-173c00e4875c",
        "plano": {
          "idJson": "294c9a8e-57a0-4a1f-8e49-d4a1b4015fc6"
        },
        "grupoSemaforico": {
          "idJson": "e60d0d71-ca19-47f0-942c-1276ff3734dd"
        },
        "ativado": true
      },
      {
        "id": "17b06db6-f3c6-4f8f-adf9-391ae55add00",
        "idJson": "e2d05229-3d02-4022-946c-addcec001103",
        "plano": {
          "idJson": "3839e784-5006-45e3-8742-b06b6801e153"
        },
        "grupoSemaforico": {
          "idJson": "93c9f615-bb22-4cf6-a11f-b1f36b7903f0"
        },
        "ativado": true
      },
      {
        "id": "214ff44a-d57f-4853-9095-b4acdf20d775",
        "idJson": "cb9bd906-cd71-42a1-a91e-b1e72a72b193",
        "plano": {
          "idJson": "d666dabb-03a6-4e66-9818-9c198f6f669e"
        },
        "grupoSemaforico": {
          "idJson": "c4c97b82-c269-4571-a84e-011e770d4b8e"
        },
        "ativado": true
      },
      {
        "id": "db31d34f-89e3-4136-8125-65857961a8da",
        "idJson": "2d0ca816-c419-4c59-a620-e4012a35af5c",
        "plano": {
          "idJson": "2914de80-2f0c-4fda-9d38-e126a253fa76"
        },
        "grupoSemaforico": {
          "idJson": "e60d0d71-ca19-47f0-942c-1276ff3734dd"
        },
        "ativado": true
      },
      {
        "id": "7e8dfd53-7db5-4b25-a331-d34cfbd47c80",
        "idJson": "cfc1fb5c-5315-4919-831e-b3f9c8be1368",
        "plano": {
          "idJson": "163f67f0-cfd8-48f2-94af-c7f59940dc55"
        },
        "grupoSemaforico": {
          "idJson": "89e8cab5-2337-40d6-b6a8-c7816cd1ca5e"
        },
        "ativado": true
      },
      {
        "id": "d5b9b156-2277-475d-89f4-74b05853780f",
        "idJson": "44e7037f-6126-45c3-8a40-a0f230fe4674",
        "plano": {
          "idJson": "986222a1-e6e9-40bf-b79e-1f7aeb88c5ce"
        },
        "grupoSemaforico": {
          "idJson": "d3f9840a-d6d5-45aa-94cf-14de2b1fec41"
        },
        "ativado": true
      },
      {
        "id": "01d558be-31e8-408f-a46c-6141699dabee",
        "idJson": "b221f70c-61b2-40b8-ad8d-f59dc86d2c8d",
        "plano": {
          "idJson": "8ad00916-355c-4fc5-bdec-4c2cb2187e27"
        },
        "grupoSemaforico": {
          "idJson": "93c9f615-bb22-4cf6-a11f-b1f36b7903f0"
        },
        "ativado": true
      },
      {
        "id": "17cc76ee-6c28-4734-afe2-01e5591d5636",
        "idJson": "8aff3bda-4c5b-444e-9c25-91ffa95e5b4d",
        "plano": {
          "idJson": "7d56b43d-09c6-4dad-a16c-c333a8f716da"
        },
        "grupoSemaforico": {
          "idJson": "e60d0d71-ca19-47f0-942c-1276ff3734dd"
        },
        "ativado": true
      },
      {
        "id": "ae067cac-d84f-4ea7-a651-50f4d1351dd6",
        "idJson": "6fd05896-5dd0-40f9-b5ea-8acb4edd1be7",
        "plano": {
          "idJson": "906ba906-527f-44ce-8da2-6cbab108b4ee"
        },
        "grupoSemaforico": {
          "idJson": "c4c97b82-c269-4571-a84e-011e770d4b8e"
        },
        "ativado": true
      },
      {
        "id": "7f0ebc10-22a6-41b0-845b-d09af7b0db55",
        "idJson": "2ba304b2-c2b9-4cec-9eb8-0de45150ac32",
        "plano": {
          "idJson": "9c3ae287-1e26-4688-9489-870757c48936"
        },
        "grupoSemaforico": {
          "idJson": "fc0601be-87ac-42cb-b5ec-625fc445610a"
        },
        "ativado": true
      }
    ],
    "estagiosPlanos": [
      {
        "id": "7b27a7b6-609c-4f6f-9616-cb7692514f76",
        "idJson": "0bce0ff9-7384-42db-bf8a-0363641ff271",
        "posicao": 3,
        "tempoVerde": 25,
        "tempoVerdeMinimo": 0,
        "tempoVerdeMaximo": 0,
        "tempoVerdeIntermediario": 0,
        "tempoExtensaoVerde": 0.0,
        "dispensavel": false,
        "plano": {
          "idJson": "163f67f0-cfd8-48f2-94af-c7f59940dc55"
        },
        "estagio": {
          "idJson": "0575779d-b038-47f0-b72e-ff11a7d47ccc"
        }
      },
      {
        "id": "546c5875-64ac-4dd4-b884-8d9cd170cf1f",
        "idJson": "d66bc893-737a-456e-a095-c4170d3ee543",
        "posicao": 2,
        "tempoVerde": 30,
        "tempoVerdeMinimo": 0,
        "tempoVerdeMaximo": 0,
        "tempoVerdeIntermediario": 0,
        "tempoExtensaoVerde": 0.0,
        "dispensavel": false,
        "plano": {
          "idJson": "15a9c3e1-fe1d-4ea8-a6d1-2d3aff4ed177"
        },
        "estagio": {
          "idJson": "3f6dac3e-909b-4690-b9ad-af666752a571"
        }
      },
      {
        "id": "d05baa23-96b9-4967-ae1b-ad7bed1b6543",
        "idJson": "5e21cdd9-6852-4629-a3d0-177c5c0defa1",
        "posicao": 3,
        "tempoVerde": 20,
        "tempoVerdeMinimo": 0,
        "tempoVerdeMaximo": 0,
        "tempoVerdeIntermediario": 0,
        "tempoExtensaoVerde": 0.0,
        "dispensavel": false,
        "plano": {
          "idJson": "9c3ae287-1e26-4688-9489-870757c48936"
        },
        "estagio": {
          "idJson": "28821b34-c80e-436b-9c42-6ee2f7da81bd"
        }
      },
      {
        "id": "5062eb5e-d93b-4449-b9e7-f90738a64705",
        "idJson": "1e56b2b7-268e-456e-ab31-3f71053a16e1",
        "posicao": 2,
        "tempoVerde": 10,
        "dispensavel": false,
        "plano": {
          "idJson": "b962be0a-3b23-4efa-aef0-c19e461d8859"
        },
        "estagio": {
          "idJson": "e3cf6c23-8180-4e8f-9c87-f7a4589aa44e"
        }
      },
      {
        "id": "e94442c9-5ad8-4d74-a5bd-185af9102d87",
        "idJson": "e72964d4-0495-4b90-9a3c-d83a744e41fc",
        "posicao": 1,
        "tempoVerde": 65,
        "tempoVerdeMinimo": 0,
        "tempoVerdeMaximo": 0,
        "tempoVerdeIntermediario": 0,
        "tempoExtensaoVerde": 0.0,
        "dispensavel": false,
        "plano": {
          "idJson": "906ba906-527f-44ce-8da2-6cbab108b4ee"
        },
        "estagio": {
          "idJson": "00f04cee-ff99-4690-9029-c88c55928096"
        }
      },
      {
        "id": "334b09d8-0c9e-4deb-8f64-abc92a211019",
        "idJson": "6074ac32-f44b-4ff5-a8e9-522727f15d81",
        "posicao": 1,
        "tempoVerde": 10,
        "dispensavel": false,
        "plano": {
          "idJson": "d666dabb-03a6-4e66-9818-9c198f6f669e"
        },
        "estagio": {
          "idJson": "00f04cee-ff99-4690-9029-c88c55928096"
        }
      },
      {
        "id": "6edd1a39-c103-4362-932a-ca6d2ddabbf9",
        "idJson": "cd11786d-e161-4a89-a428-680574476414",
        "posicao": 3,
        "tempoVerde": 20,
        "tempoVerdeMinimo": 0,
        "tempoVerdeMaximo": 0,
        "tempoVerdeIntermediario": 0,
        "tempoExtensaoVerde": 0.0,
        "dispensavel": false,
        "plano": {
          "idJson": "906ba906-527f-44ce-8da2-6cbab108b4ee"
        },
        "estagio": {
          "idJson": "28821b34-c80e-436b-9c42-6ee2f7da81bd"
        }
      },
      {
        "id": "0200a8af-7b2e-49e5-be2f-842a7249bc19",
        "idJson": "67984282-eb6a-4bbc-a317-89f546c7810d",
        "posicao": 2,
        "tempoVerde": 25,
        "tempoVerdeMinimo": 0,
        "tempoVerdeMaximo": 0,
        "tempoVerdeIntermediario": 0,
        "tempoExtensaoVerde": 0.0,
        "dispensavel": false,
        "plano": {
          "idJson": "7d56b43d-09c6-4dad-a16c-c333a8f716da"
        },
        "estagio": {
          "idJson": "bdb72a13-a29b-4dff-ab69-05b69f433d72"
        }
      },
      {
        "id": "ec58f61d-af80-40c6-8386-7503ecaf9670",
        "idJson": "1bbdb634-e8ea-417e-87a7-507bf2267292",
        "posicao": 2,
        "tempoVerde": 20,
        "tempoVerdeMinimo": 0,
        "tempoVerdeMaximo": 0,
        "tempoVerdeIntermediario": 0,
        "tempoExtensaoVerde": 0.0,
        "dispensavel": false,
        "plano": {
          "idJson": "2cc8025b-debb-4694-b459-297b59f9fd21"
        },
        "estagio": {
          "idJson": "3f6dac3e-909b-4690-b9ad-af666752a571"
        }
      },
      {
        "id": "51bd69db-8a2e-42ef-a0bd-a3a0c1e3ef75",
        "idJson": "a2d08c74-1155-4080-822f-629d8f28df19",
        "posicao": 1,
        "tempoVerde": 65,
        "tempoVerdeMinimo": 0,
        "tempoVerdeMaximo": 0,
        "tempoVerdeIntermediario": 0,
        "tempoExtensaoVerde": 0.0,
        "dispensavel": false,
        "plano": {
          "idJson": "abe96d47-fa89-4b12-a674-ee26741d861a"
        },
        "estagio": {
          "idJson": "00f04cee-ff99-4690-9029-c88c55928096"
        }
      },
      {
        "id": "9f7e609e-cf86-4bd9-bb09-439abdba00e8",
        "idJson": "017b9015-ea7a-4bc7-a6d9-84f460aafc90",
        "posicao": 2,
        "tempoVerde": 20,
        "tempoVerdeMinimo": 0,
        "tempoVerdeMaximo": 0,
        "tempoVerdeIntermediario": 0,
        "tempoExtensaoVerde": 0.0,
        "dispensavel": false,
        "plano": {
          "idJson": "7669c3e5-b086-4c41-bd5c-cdcb19a13147"
        },
        "estagio": {
          "idJson": "e3cf6c23-8180-4e8f-9c87-f7a4589aa44e"
        }
      },
      {
        "id": "db95b5f4-0923-4159-ae40-b76e047467d8",
        "idJson": "19e39603-d937-434f-bbed-6e9b19c6417e",
        "posicao": 3,
        "tempoVerde": 20,
        "tempoVerdeMinimo": 0,
        "tempoVerdeMaximo": 0,
        "tempoVerdeIntermediario": 0,
        "tempoExtensaoVerde": 0.0,
        "dispensavel": false,
        "plano": {
          "idJson": "2cc8025b-debb-4694-b459-297b59f9fd21"
        },
        "estagio": {
          "idJson": "28821b34-c80e-436b-9c42-6ee2f7da81bd"
        }
      },
      {
        "id": "ea035f66-f435-46ce-9036-acdbe07b2c39",
        "idJson": "faa5acd5-389a-41a9-9c7d-bb225852b8e9",
        "posicao": 1,
        "tempoVerde": 60,
        "tempoVerdeMinimo": 0,
        "tempoVerdeMaximo": 0,
        "tempoVerdeIntermediario": 0,
        "tempoExtensaoVerde": 0.0,
        "dispensavel": false,
        "plano": {
          "idJson": "294c9a8e-57a0-4a1f-8e49-d4a1b4015fc6"
        },
        "estagio": {
          "idJson": "513ccc36-b0dd-4846-b404-226f14941f68"
        }
      },
      {
        "id": "53d5a661-c564-4a96-97ae-31b9668e59f1",
        "idJson": "9d7bb8d0-2ce0-4ce8-929c-5d44efff3452",
        "posicao": 3,
        "tempoVerde": 20,
        "tempoVerdeMinimo": 0,
        "tempoVerdeMaximo": 0,
        "tempoVerdeIntermediario": 0,
        "tempoExtensaoVerde": 0.0,
        "dispensavel": false,
        "plano": {
          "idJson": "2914de80-2f0c-4fda-9d38-e126a253fa76"
        },
        "estagio": {
          "idJson": "0575779d-b038-47f0-b72e-ff11a7d47ccc"
        }
      },
      {
        "id": "bdd8ae2e-895b-4ab7-90f0-fffe745d48af",
        "idJson": "84b081f7-8291-411e-b929-fd9d07e3e4c1",
        "posicao": 1,
        "tempoVerde": 75,
        "tempoVerdeMinimo": 0,
        "tempoVerdeMaximo": 0,
        "tempoVerdeIntermediario": 0,
        "tempoExtensaoVerde": 0.0,
        "dispensavel": false,
        "plano": {
          "idJson": "15a9c3e1-fe1d-4ea8-a6d1-2d3aff4ed177"
        },
        "estagio": {
          "idJson": "00f04cee-ff99-4690-9029-c88c55928096"
        }
      },
      {
        "id": "2ce6ea65-3c1a-413b-aeb2-b1812ef1ea15",
        "idJson": "e700267a-a0a6-48f4-a6de-08fecd02904d",
        "posicao": 1,
        "tempoVerde": 45,
        "tempoVerdeMinimo": 0,
        "tempoVerdeMaximo": 0,
        "tempoVerdeIntermediario": 0,
        "tempoExtensaoVerde": 0.0,
        "dispensavel": false,
        "plano": {
          "idJson": "9c3ae287-1e26-4688-9489-870757c48936"
        },
        "estagio": {
          "idJson": "00f04cee-ff99-4690-9029-c88c55928096"
        }
      },
      {
        "id": "3264a52e-efbe-475f-8b48-af6b550aa568",
        "idJson": "572cc401-ebf2-4cb3-b68e-4c9ecd44af53",
        "posicao": 2,
        "tempoVerde": 30,
        "tempoVerdeMinimo": 0,
        "tempoVerdeMaximo": 0,
        "tempoVerdeIntermediario": 0,
        "tempoExtensaoVerde": 0.0,
        "dispensavel": false,
        "plano": {
          "idJson": "986222a1-e6e9-40bf-b79e-1f7aeb88c5ce"
        },
        "estagio": {
          "idJson": "e3cf6c23-8180-4e8f-9c87-f7a4589aa44e"
        }
      },
      {
        "id": "193bb080-8d66-4b17-9307-141b13ec7c1d",
        "idJson": "551bcef2-b277-4110-8239-2ac5a31ca4b7",
        "posicao": 2,
        "tempoVerde": 10,
        "dispensavel": false,
        "plano": {
          "idJson": "8ad00916-355c-4fc5-bdec-4c2cb2187e27"
        },
        "estagio": {
          "idJson": "bdb72a13-a29b-4dff-ab69-05b69f433d72"
        }
      },
      {
        "id": "2f5b2c32-c315-4ef9-a6ad-c87d68f3fb37",
        "idJson": "a5204462-cee2-407f-9713-c0ccb4566677",
        "posicao": 1,
        "tempoVerde": 40,
        "tempoVerdeMinimo": 0,
        "tempoVerdeMaximo": 0,
        "tempoVerdeIntermediario": 0,
        "tempoExtensaoVerde": 0.0,
        "dispensavel": false,
        "plano": {
          "idJson": "3839e784-5006-45e3-8742-b06b6801e153"
        },
        "estagio": {
          "idJson": "513ccc36-b0dd-4846-b404-226f14941f68"
        }
      },
      {
        "id": "6198b380-633f-4732-9a60-f0b7e881370a",
        "idJson": "8d09ed2a-9b64-4c5c-aeed-0dbad32650c4",
        "posicao": 2,
        "tempoVerde": 20,
        "tempoVerdeMinimo": 0,
        "tempoVerdeMaximo": 0,
        "tempoVerdeIntermediario": 0,
        "tempoExtensaoVerde": 0.0,
        "dispensavel": false,
        "plano": {
          "idJson": "abe96d47-fa89-4b12-a674-ee26741d861a"
        },
        "estagio": {
          "idJson": "3f6dac3e-909b-4690-b9ad-af666752a571"
        }
      },
      {
        "id": "78505ada-36bd-4875-8cfa-1a1244c80508",
        "idJson": "260a212e-f0d1-4652-81e3-37b3fdb17dc8",
        "posicao": 1,
        "tempoVerde": 90,
        "tempoVerdeMinimo": 0,
        "tempoVerdeMaximo": 0,
        "tempoVerdeIntermediario": 0,
        "tempoExtensaoVerde": 0.0,
        "dispensavel": false,
        "plano": {
          "idJson": "8ea9bc20-9313-4113-a0b7-51e16df68eb4"
        },
        "estagio": {
          "idJson": "2a189819-36c3-4cc0-a989-6f8f19cff390"
        }
      },
      {
        "id": "6d6af573-e58f-4550-baa7-9030c37a6f1e",
        "idJson": "0ec2e9a8-3ba2-4855-98e7-a8fa62b46f46",
        "posicao": 3,
        "tempoVerde": 20,
        "tempoVerdeMinimo": 0,
        "tempoVerdeMaximo": 0,
        "tempoVerdeIntermediario": 0,
        "tempoExtensaoVerde": 0.0,
        "dispensavel": false,
        "plano": {
          "idJson": "abe96d47-fa89-4b12-a674-ee26741d861a"
        },
        "estagio": {
          "idJson": "28821b34-c80e-436b-9c42-6ee2f7da81bd"
        }
      },
      {
        "id": "3053b7af-71f5-4c4b-af74-2b68c5315139",
        "idJson": "3fa147dc-f754-4012-b5cc-7127140e33e1",
        "posicao": 2,
        "tempoVerde": 20,
        "tempoVerdeMinimo": 0,
        "tempoVerdeMaximo": 0,
        "tempoVerdeIntermediario": 0,
        "tempoExtensaoVerde": 0.0,
        "dispensavel": false,
        "plano": {
          "idJson": "906ba906-527f-44ce-8da2-6cbab108b4ee"
        },
        "estagio": {
          "idJson": "3f6dac3e-909b-4690-b9ad-af666752a571"
        }
      },
      {
        "id": "ef58535e-af1d-4287-af4d-e7670ccd0bcd",
        "idJson": "1d8f595a-92e1-4806-b76f-5789fcc8ab34",
        "posicao": 2,
        "tempoVerde": 20,
        "tempoVerdeMinimo": 0,
        "tempoVerdeMaximo": 0,
        "tempoVerdeIntermediario": 0,
        "tempoExtensaoVerde": 0.0,
        "dispensavel": false,
        "plano": {
          "idJson": "3839e784-5006-45e3-8742-b06b6801e153"
        },
        "estagio": {
          "idJson": "bdb72a13-a29b-4dff-ab69-05b69f433d72"
        }
      },
      {
        "id": "4030c3f0-1e79-46fb-9afb-512c6f5736c8",
        "idJson": "aa6e5a8e-2a57-475d-8230-c3ba0ca0c0ae",
        "posicao": 2,
        "tempoVerde": 20,
        "tempoVerdeMinimo": 0,
        "tempoVerdeMaximo": 0,
        "tempoVerdeIntermediario": 0,
        "tempoExtensaoVerde": 0.0,
        "dispensavel": false,
        "plano": {
          "idJson": "2914de80-2f0c-4fda-9d38-e126a253fa76"
        },
        "estagio": {
          "idJson": "bdb72a13-a29b-4dff-ab69-05b69f433d72"
        }
      },
      {
        "id": "8ebd55bf-4d64-47da-9144-fdc23cac7cb3",
        "idJson": "845e96e7-80d1-4ca0-9423-dcfc04245bc9",
        "posicao": 1,
        "tempoVerde": 40,
        "tempoVerdeMinimo": 0,
        "tempoVerdeMaximo": 0,
        "tempoVerdeIntermediario": 0,
        "tempoExtensaoVerde": 0.0,
        "dispensavel": false,
        "plano": {
          "idJson": "2914de80-2f0c-4fda-9d38-e126a253fa76"
        },
        "estagio": {
          "idJson": "513ccc36-b0dd-4846-b404-226f14941f68"
        }
      },
      {
        "id": "ef4f4582-ec00-4783-8de4-cb3f0516eca2",
        "idJson": "7e172e4f-fedf-45d7-abdf-c89b41f7ecb2",
        "posicao": 3,
        "tempoVerde": 20,
        "tempoVerdeMinimo": 0,
        "tempoVerdeMaximo": 0,
        "tempoVerdeIntermediario": 0,
        "tempoExtensaoVerde": 0.0,
        "dispensavel": false,
        "plano": {
          "idJson": "294c9a8e-57a0-4a1f-8e49-d4a1b4015fc6"
        },
        "estagio": {
          "idJson": "0575779d-b038-47f0-b72e-ff11a7d47ccc"
        }
      },
      {
        "id": "6293e6c0-33c6-4ebd-a24d-9edabcacd848",
        "idJson": "741efab8-8f3b-4991-ad7c-c591449d398f",
        "posicao": 3,
        "tempoVerde": 20,
        "tempoVerdeMinimo": 0,
        "tempoVerdeMaximo": 0,
        "tempoVerdeIntermediario": 0,
        "tempoExtensaoVerde": 0.0,
        "dispensavel": false,
        "plano": {
          "idJson": "3839e784-5006-45e3-8742-b06b6801e153"
        },
        "estagio": {
          "idJson": "0575779d-b038-47f0-b72e-ff11a7d47ccc"
        }
      },
      {
        "id": "2ff79322-13bc-4a0e-bda9-b87d861eb5ec",
        "idJson": "80216539-df79-405b-866b-bd13c98a2bf6",
        "posicao": 1,
        "tempoVerde": 10,
        "dispensavel": false,
        "plano": {
          "idJson": "8ad00916-355c-4fc5-bdec-4c2cb2187e27"
        },
        "estagio": {
          "idJson": "513ccc36-b0dd-4846-b404-226f14941f68"
        }
      },
      {
        "id": "7f6e4061-b724-4128-bf59-0f213c55255b",
        "idJson": "e555a4b5-7e68-4ee0-a2ac-4f3137eeb85f",
        "posicao": 1,
        "tempoVerde": 70,
        "tempoVerdeMinimo": 0,
        "tempoVerdeMaximo": 0,
        "tempoVerdeIntermediario": 0,
        "tempoExtensaoVerde": 0.0,
        "dispensavel": false,
        "plano": {
          "idJson": "aa614e64-a228-428c-93d9-f586fcdfb68a"
        },
        "estagio": {
          "idJson": "2a189819-36c3-4cc0-a989-6f8f19cff390"
        }
      },
      {
        "id": "b5a805be-846e-414e-a2b9-b28c39934e8e",
        "idJson": "667a1aed-a129-4286-9f6e-9bea3e08f8da",
        "posicao": 2,
        "tempoVerde": 20,
        "tempoVerdeMinimo": 0,
        "tempoVerdeMaximo": 0,
        "tempoVerdeIntermediario": 0,
        "tempoExtensaoVerde": 0.0,
        "dispensavel": false,
        "plano": {
          "idJson": "294c9a8e-57a0-4a1f-8e49-d4a1b4015fc6"
        },
        "estagio": {
          "idJson": "bdb72a13-a29b-4dff-ab69-05b69f433d72"
        }
      },
      {
        "id": "78d13d95-a5d4-4c2d-b192-5901d79d0d30",
        "idJson": "b4ec2c9e-6c5d-46fc-8240-d1b5766a55a7",
        "posicao": 2,
        "tempoVerde": 20,
        "tempoVerdeMinimo": 0,
        "tempoVerdeMaximo": 0,
        "tempoVerdeIntermediario": 0,
        "tempoExtensaoVerde": 0.0,
        "dispensavel": false,
        "plano": {
          "idJson": "8ea9bc20-9313-4113-a0b7-51e16df68eb4"
        },
        "estagio": {
          "idJson": "e3cf6c23-8180-4e8f-9c87-f7a4589aa44e"
        }
      },
      {
        "id": "3280d856-1575-47f7-8f31-7e7e29b19664",
        "idJson": "3d75895a-9359-42fd-a384-6f6a87c6d79d",
        "posicao": 2,
        "tempoVerde": 10,
        "dispensavel": false,
        "plano": {
          "idJson": "d666dabb-03a6-4e66-9818-9c198f6f669e"
        },
        "estagio": {
          "idJson": "28821b34-c80e-436b-9c42-6ee2f7da81bd"
        }
      },
      {
        "id": "9d2c29c2-e8a2-4346-baf4-51ce6b387526",
        "idJson": "6a1f3bed-e9ec-4fdc-9792-457384a410d4",
        "posicao": 1,
        "tempoVerde": 70,
        "tempoVerdeMinimo": 0,
        "tempoVerdeMaximo": 0,
        "tempoVerdeIntermediario": 0,
        "tempoExtensaoVerde": 0.0,
        "dispensavel": false,
        "plano": {
          "idJson": "7669c3e5-b086-4c41-bd5c-cdcb19a13147"
        },
        "estagio": {
          "idJson": "2a189819-36c3-4cc0-a989-6f8f19cff390"
        }
      },
      {
        "id": "3e84c9e2-f533-43b4-91bc-ff5900249f1d",
        "idJson": "94c9090d-17ef-4bfa-8116-e58473458b30",
        "posicao": 3,
        "tempoVerde": 25,
        "tempoVerdeMinimo": 0,
        "tempoVerdeMaximo": 0,
        "tempoVerdeIntermediario": 0,
        "tempoExtensaoVerde": 0.0,
        "dispensavel": false,
        "plano": {
          "idJson": "7d56b43d-09c6-4dad-a16c-c333a8f716da"
        },
        "estagio": {
          "idJson": "0575779d-b038-47f0-b72e-ff11a7d47ccc"
        }
      },
      {
        "id": "0153e37b-83e7-4ce4-8b06-97461d8c3c98",
        "idJson": "0dc47cf3-e088-472b-87c5-289cc5b422d1",
        "posicao": 2,
        "tempoVerde": 20,
        "tempoVerdeMinimo": 0,
        "tempoVerdeMaximo": 0,
        "tempoVerdeIntermediario": 0,
        "tempoExtensaoVerde": 0.0,
        "dispensavel": false,
        "plano": {
          "idJson": "abdaffeb-10e7-4134-8584-2b017c0ca408"
        },
        "estagio": {
          "idJson": "e3cf6c23-8180-4e8f-9c87-f7a4589aa44e"
        }
      },
      {
        "id": "9fe5a8e3-a697-42ab-a6bd-7d3d13021f95",
        "idJson": "05a42ee4-ea59-471d-9259-a86a9a83763a",
        "posicao": 2,
        "tempoVerde": 25,
        "tempoVerdeMinimo": 0,
        "tempoVerdeMaximo": 0,
        "tempoVerdeIntermediario": 0,
        "tempoExtensaoVerde": 0.0,
        "dispensavel": false,
        "plano": {
          "idJson": "163f67f0-cfd8-48f2-94af-c7f59940dc55"
        },
        "estagio": {
          "idJson": "bdb72a13-a29b-4dff-ab69-05b69f433d72"
        }
      },
      {
        "id": "3d85ba78-df4d-4a42-a940-40bafbe42eaa",
        "idJson": "9ac13292-76dd-408f-a236-77e11c7f1c55",
        "posicao": 1,
        "tempoVerde": 45,
        "tempoVerdeMinimo": 0,
        "tempoVerdeMaximo": 0,
        "tempoVerdeIntermediario": 0,
        "tempoExtensaoVerde": 0.0,
        "dispensavel": false,
        "plano": {
          "idJson": "2cc8025b-debb-4694-b459-297b59f9fd21"
        },
        "estagio": {
          "idJson": "00f04cee-ff99-4690-9029-c88c55928096"
        }
      },
      {
        "id": "d588f8e1-d634-432d-926b-cce419708d3f",
        "idJson": "840d1cba-11eb-474f-8258-0e4b83d6b575",
        "posicao": 3,
        "tempoVerde": 30,
        "tempoVerdeMinimo": 0,
        "tempoVerdeMaximo": 0,
        "tempoVerdeIntermediario": 0,
        "tempoExtensaoVerde": 0.0,
        "dispensavel": false,
        "plano": {
          "idJson": "15a9c3e1-fe1d-4ea8-a6d1-2d3aff4ed177"
        },
        "estagio": {
          "idJson": "28821b34-c80e-436b-9c42-6ee2f7da81bd"
        }
      },
      {
        "id": "d998ff07-3bd7-4e12-a3cc-3cf1c2537ad7",
        "idJson": "612b982a-a6c2-4f1e-9399-c961b5f57954",
        "posicao": 2,
        "tempoVerde": 20,
        "tempoVerdeMinimo": 0,
        "tempoVerdeMaximo": 0,
        "tempoVerdeIntermediario": 0,
        "tempoExtensaoVerde": 0.0,
        "dispensavel": false,
        "plano": {
          "idJson": "aa614e64-a228-428c-93d9-f586fcdfb68a"
        },
        "estagio": {
          "idJson": "e3cf6c23-8180-4e8f-9c87-f7a4589aa44e"
        }
      },
      {
        "id": "96ff0910-5a09-47c3-bbc9-f01be3547089",
        "idJson": "2e813cb6-c89c-4783-a455-b04e9314c654",
        "posicao": 1,
        "tempoVerde": 50,
        "tempoVerdeMinimo": 0,
        "tempoVerdeMaximo": 0,
        "tempoVerdeIntermediario": 0,
        "tempoExtensaoVerde": 0.0,
        "dispensavel": false,
        "plano": {
          "idJson": "7d56b43d-09c6-4dad-a16c-c333a8f716da"
        },
        "estagio": {
          "idJson": "513ccc36-b0dd-4846-b404-226f14941f68"
        }
      },
      {
        "id": "f2960469-6577-4f58-aea4-0a5e86b1f63d",
        "idJson": "61d2fb94-cc53-4c10-9d91-4383a19a76c9",
        "posicao": 1,
        "tempoVerde": 80,
        "tempoVerdeMinimo": 0,
        "tempoVerdeMaximo": 0,
        "tempoVerdeIntermediario": 0,
        "tempoExtensaoVerde": 0.0,
        "dispensavel": false,
        "plano": {
          "idJson": "163f67f0-cfd8-48f2-94af-c7f59940dc55"
        },
        "estagio": {
          "idJson": "513ccc36-b0dd-4846-b404-226f14941f68"
        }
      },
      {
        "id": "a3340d13-dca8-4600-948a-55024b8a3a65",
        "idJson": "1356d7ba-ab1d-4ae8-88bb-e6738acf7a65",
        "posicao": 2,
        "tempoVerde": 20,
        "tempoVerdeMinimo": 0,
        "tempoVerdeMaximo": 0,
        "tempoVerdeIntermediario": 0,
        "tempoExtensaoVerde": 0.0,
        "dispensavel": false,
        "plano": {
          "idJson": "9c3ae287-1e26-4688-9489-870757c48936"
        },
        "estagio": {
          "idJson": "3f6dac3e-909b-4690-b9ad-af666752a571"
        }
      },
      {
        "id": "a98147c0-f143-4f98-91c4-84ef0d8512d1",
        "idJson": "f1e79171-3faa-498c-9b38-3d60d2f961b4",
        "posicao": 1,
        "tempoVerde": 10,
        "dispensavel": false,
        "plano": {
          "idJson": "b962be0a-3b23-4efa-aef0-c19e461d8859"
        },
        "estagio": {
          "idJson": "2a189819-36c3-4cc0-a989-6f8f19cff390"
        }
      },
      {
        "id": "c0d36b22-e2c2-46b6-97e8-ba0b47622ebf",
        "idJson": "7a92067e-7200-4a40-bd75-f3ad20185da2",
        "posicao": 1,
        "tempoVerde": 90,
        "tempoVerdeMinimo": 0,
        "tempoVerdeMaximo": 0,
        "tempoVerdeIntermediario": 0,
        "tempoExtensaoVerde": 0.0,
        "dispensavel": false,
        "plano": {
          "idJson": "abdaffeb-10e7-4134-8584-2b017c0ca408"
        },
        "estagio": {
          "idJson": "2a189819-36c3-4cc0-a989-6f8f19cff390"
        }
      },
      {
        "id": "b474e6f0-722f-4779-a9a4-3ff39c7a408a",
        "idJson": "da0833b5-4e07-49ef-a824-27ba46a02274",
        "posicao": 3,
        "tempoVerde": 10,
        "dispensavel": false,
        "plano": {
          "idJson": "8ad00916-355c-4fc5-bdec-4c2cb2187e27"
        },
        "estagio": {
          "idJson": "0575779d-b038-47f0-b72e-ff11a7d47ccc"
        }
      },
      {
        "id": "60e454dd-a989-46bf-a46b-61490fd96585",
        "idJson": "e7c54bdf-7c7f-485a-8c5c-f10ad853b9a5",
        "posicao": 1,
        "tempoVerde": 110,
        "tempoVerdeMinimo": 0,
        "tempoVerdeMaximo": 0,
        "tempoVerdeIntermediario": 0,
        "tempoExtensaoVerde": 0.0,
        "dispensavel": false,
        "plano": {
          "idJson": "986222a1-e6e9-40bf-b79e-1f7aeb88c5ce"
        },
        "estagio": {
          "idJson": "2a189819-36c3-4cc0-a989-6f8f19cff390"
        }
      }
    ],
    "cidades": [
      {
        "id": "8d92d1b1-8ee3-11e6-970d-0401fa9c1b01",
        "idJson": "8d92dde5-8ee3-11e6-970d-0401fa9c1b01",
        "nome": "São Paulo",
        "areas": [
          {
            "idJson": "5c012ad1-aafe-46fa-b12b-037e76d8f1e6"
          },
          {
            "idJson": "2ee35ce3-3acf-47f5-8239-002f9ba95ca2"
          },
          {
            "idJson": "5599a1cc-1256-463f-bb83-9ede6a6f09be"
          },
          {
            "idJson": "afb12c64-d2a5-4af5-bb6b-ce692ce25bc4"
          },
          {
            "idJson": "3a9239c7-e8f3-4b8c-b9b1-09c5197ae796"
          }
        ]
      }
    ],
    "areas": [
      {
        "id": "408466c5-fb47-42d3-b96b-6d4f8867112c",
        "idJson": "2ee35ce3-3acf-47f5-8239-002f9ba95ca2",
        "descricao": 4,
        "cidade": {
          "idJson": "8d92dde5-8ee3-11e6-970d-0401fa9c1b01"
        },
        "limites": [],
        "subareas": [
          {
            "id": "7c344fd0-2569-415d-a4df-31dcb34cdbb9",
            "idJson": "43290b02-16e6-4058-b9bb-0bb05257b85b",
            "nome": "VERGUEIRO",
            "numero": "0"
          }
        ]
      },
      {
        "id": "5b1971e9-c0b7-4b9c-9521-1d37c02bfc88",
        "idJson": "5599a1cc-1256-463f-bb83-9ede6a6f09be",
        "descricao": 5,
        "cidade": {
          "idJson": "8d92dde5-8ee3-11e6-970d-0401fa9c1b01"
        },
        "limites": [],
        "subareas": [
          {
            "id": "143c5ccc-e43f-4a1a-ad81-796f93d65678",
            "idJson": "d30215ed-92e8-429c-a682-1a8be3c5e232",
            "nome": "Francisco Morato",
            "numero": "54"
          },
          {
            "id": "8fdc23f2-a6d7-4c3f-b10d-3545a74c410b",
            "idJson": "fc969794-b0d8-4688-9997-ad644a4da55a",
            "nome": "CONSOLAÇÃO 2",
            "numero": "14"
          }
        ]
      },
      {
        "id": "8d92d514-8ee3-11e6-970d-0401fa9c1b01",
        "idJson": "3a9239c7-e8f3-4b8c-b9b1-09c5197ae796",
        "descricao": 1,
        "cidade": {
          "idJson": "8d92dde5-8ee3-11e6-970d-0401fa9c1b01"
        },
        "limites": [],
        "subareas": [
          {
            "id": "6fbcf8d3-2d86-492a-ad7a-1292a27e565c",
            "idJson": "71f2dfaf-54ad-413b-bf9f-a6b0f6ab5b7a",
            "nome": "Consolação",
            "numero": "1112"
          },
          {
            "id": "baa931c0-47e7-4ac7-b257-3c6a0ff44cb6",
            "idJson": "e93d92a4-c953-4822-823a-4f11829ff9f2",
            "nome": "HIGIENOPOLIS",
            "numero": "1111"
          }
        ]
      },
      {
        "id": "3db52841-4c79-4c8b-92ec-b3d64c4d8e62",
        "idJson": "5c012ad1-aafe-46fa-b12b-037e76d8f1e6",
        "descricao": 2,
        "cidade": {
          "idJson": "8d92dde5-8ee3-11e6-970d-0401fa9c1b01"
        },
        "limites": [],
        "subareas": [
          {
            "id": "c14b0886-bfa5-45f6-b92f-2048527b32a4",
            "idJson": "8f69faf4-9175-425d-ac9e-fc3b1419886e",
            "nome": "Pompeia",
            "numero": "228"
          }
        ]
      },
      {
        "id": "8486f761-aefa-4c73-a6c8-c965866d06d0",
        "idJson": "afb12c64-d2a5-4af5-bb6b-ce692ce25bc4",
        "descricao": 3,
        "cidade": {
          "idJson": "8d92dde5-8ee3-11e6-970d-0401fa9c1b01"
        },
        "limites": [],
        "subareas": [
          {
            "id": "970c11cb-1489-40c6-a379-fd518774c579",
            "idJson": "5c1dc0f8-04ef-449d-a242-445948ffb31c",
            "nome": "TATUAPE",
            "numero": "3"
          }
        ]
      }
    ],
    "limites": [],
    "todosEnderecos": [
      {
        "id": "80887422-0362-4a1f-848b-4fee83320998",
        "idJson": "dbfcc505-982d-4d1a-86a1-d1316b18c846",
        "localizacao": "R. Emília Marengo",
        "latitude": -23.5541189,
        "longitude": -46.560647700000004,
        "localizacao2": "Rua Itapura"
      },
      {
        "id": "058faa2f-1cbc-4678-8d22-9c64ba630d2c",
        "idJson": "6f151dbe-4b8a-4eaf-a70a-63ea02e299af",
        "localizacao": "R. Emília Marengo",
        "latitude": -23.5541189,
        "longitude": -46.560647700000004,
        "localizacao2": "Rua Itapura"
      },
      {
        "id": "01baadb9-2758-4bcb-a319-db2c116ba05e",
        "idJson": "c568ebcc-4112-47b0-b6dd-3a1fd4a8a6ba",
        "localizacao": "R. Emília Marengo",
        "latitude": -23.5541189,
        "longitude": -46.560647700000004,
        "localizacao2": "Rua Itapura"
      },
      {
        "id": "be300ffb-0b6c-407b-9007-f2e4bf59b54b",
        "idJson": "2d5ed6fe-721e-4136-b682-a4d81e9c598b",
        "localizacao": "R. Emília Marengo",
        "latitude": -23.5541189,
        "longitude": -46.560647700000004,
        "localizacao2": "Rua Serra de Japi"
      }
    ],
    "imagens": [
      {
        "id": "73fdefc0-372c-41af-814f-7a702d791941",
        "idJson": "4dc0fbf6-6878-4a52-b644-258bdd89d9e5",
        "fileName": "EMILIA MARENGO X ITAPURA CROQUI.bmp",
        "contentType": "image/bmp"
      },
      {
        "id": "8dc2fdb6-39ee-4b61-9737-4427ca6fd6d8",
        "idJson": "6ce52c3e-2e5a-455e-966d-863b93e448df",
        "fileName": "3342-A2-E2  ITAPURA X EMILIA MARENGO.JPG",
        "contentType": "image/jpeg"
      },
      {
        "id": "cd2011ce-e6d7-44cd-b474-9fb243f8213b",
        "idJson": "9ff36447-ddbd-49e4-9ca4-7afe404c6686",
        "fileName": "3340 -A1-E1  EMILIA MARENGO X FCO MARENGO.jpg",
        "contentType": "image/jpeg"
      },
      {
        "id": "a5e866bf-19b5-43df-b1ec-8edd681a2edd",
        "idJson": "aeb254e3-7541-40f3-a71b-022a0bba211e",
        "fileName": "3340 -A1-E2  FCO MARENGO X EMILIA MARENGO.jpg",
        "contentType": "image/jpeg"
      },
      {
        "id": "4a452395-ea7f-4955-b5bb-74f5fe8df83f",
        "idJson": "023fc423-d200-4802-a10d-278bc8dbf250",
        "fileName": "3340 -A1-E3  FCO MARENGO X EMILIA MAENGO.jpg",
        "contentType": "image/jpeg"
      },
      {
        "id": "aa7b1253-5bb5-4b32-9dce-55590c32c4f1",
        "idJson": "24e23b1a-d05e-47cf-992b-14a3398a2bcb",
        "fileName": "3342-A3-E2  SERRA JAPI SENTIDO CERET.JPG",
        "contentType": "image/jpeg"
      },
      {
        "id": "bbc615de-fff6-4129-9b15-d90566c142ca",
        "idJson": "54b5b708-cb38-4bca-b57a-3b077c02598f",
        "fileName": "3342-A3-E1  EMILIA MARENGO X SERRA JAPI.JPG",
        "contentType": "image/jpeg"
      },
      {
        "id": "27045ccb-4b4d-430e-936e-d56cf696c8b5",
        "idJson": "3344355f-8f91-4228-9fb2-94bc07aeafc3",
        "fileName": "EMILIA MARENGO X ITAPURA CROQUI.bmp",
        "contentType": "image/bmp"
      },
      {
        "id": "25127706-13ec-4842-bb83-b2659a3f4691",
        "idJson": "8da07721-ffa6-47cc-847c-786337b4ab85",
        "fileName": "EMILIA MARENGO X ITAPURA CROQUI.bmp",
        "contentType": "image/bmp"
      },
      {
        "id": "7a85e2dd-eb7e-4c71-a31d-7649f1e16e82",
        "idJson": "ad3de68a-00f4-462b-a868-c491bbf43cca",
        "fileName": "3342-A3-E2  SERRA JAPI SENTIDO RADIAL.JPG",
        "contentType": "image/jpeg"
      },
      {
        "id": "8a136970-fa35-432f-8472-d3fbf9afb20a",
        "idJson": "a52e186b-12d7-47c4-9710-f98f75209044",
        "fileName": "3340 - ANEL 1 - EMILIA MARENGO X FCO MARENGO.bmp",
        "contentType": "image/bmp"
      },
      {
        "id": "ef8e3dba-8918-4dec-b270-4d1458ee4a8d",
        "idJson": "9f32efcd-d832-4bdd-b9d1-1e39ad2a4f95",
        "fileName": "3342-A2-E1  EMILIA MARENGO X ITAPURA.JPG",
        "contentType": "image/jpeg"
      }
    ],
    "atrasosDeGrupo": [
      {
        "id": "25dbf639-0e59-4678-8b36-2fe9f240f1f5",
        "idJson": "8a593f50-efa3-4a20-9346-60aaf573aa85",
        "atrasoDeGrupo": 0
      },
      {
        "id": "2f20c4c3-082e-448a-9c8d-a5ca0c5626b1",
        "idJson": "e84c6301-421c-4dce-9a24-3710eb37bfb5",
        "atrasoDeGrupo": 0
      },
      {
        "id": "349ceff5-e91e-4f8d-9be2-c4374df43fcf",
        "idJson": "f9193497-3f21-4b5f-9800-8950308bcfb2",
        "atrasoDeGrupo": 0
      },
      {
        "id": "bfa59917-732a-4414-8d64-07d015a3e956",
        "idJson": "f996ba97-c667-45ec-89bd-e395aef39118",
        "atrasoDeGrupo": 0
      },
      {
        "id": "8fe38987-9556-419c-ad10-9abb46008756",
        "idJson": "21fa526d-96cc-4f99-977a-76343044cc85",
        "atrasoDeGrupo": 0
      },
      {
        "id": "71b2a1a6-db17-40ad-87f3-5c8305357e23",
        "idJson": "b31ed703-f63a-4c40-b966-32411c2d7560",
        "atrasoDeGrupo": 0
      },
      {
        "id": "cbd7fab6-e8df-49fa-91dc-221e2288327b",
        "idJson": "138ec3f8-bd9c-421b-9b3e-9b875bf69ac6",
        "atrasoDeGrupo": 0
      },
      {
        "id": "489cd818-5f4a-4e34-8dbc-4c28cdf77111",
        "idJson": "e0d7b37b-6eeb-44a7-86ae-60b3807d7915",
        "atrasoDeGrupo": 0
      },
      {
        "id": "507d0833-8fd4-4afc-b51f-41275148c21a",
        "idJson": "c691dddb-7411-47c1-94b0-72902d32e859",
        "atrasoDeGrupo": 0
      },
      {
        "id": "1b86dca1-5cf6-4453-8bec-d2a89c885623",
        "idJson": "3f6c4b43-9143-4fee-ae23-7a651aa969c7",
        "atrasoDeGrupo": 0
      },
      {
        "id": "2ad2283d-80b4-4474-86ae-43b9566ad6bd",
        "idJson": "469bbc28-f783-433f-a386-5dfbd8b51251",
        "atrasoDeGrupo": 0
      },
      {
        "id": "7b346908-bbc8-4d66-b6a6-1810baaaa037",
        "idJson": "ae9fe6be-5e1e-4549-aef0-18cb42633c02",
        "atrasoDeGrupo": 0
      },
      {
        "id": "fb3db793-4f06-44f7-add6-50f6c3d7ac82",
        "idJson": "29555a67-c694-4a0d-8419-43477fc6c359",
        "atrasoDeGrupo": 0
      },
      {
        "id": "99c94322-142b-434f-92df-c6172c7a7b65",
        "idJson": "06f25a7f-10b5-4514-8531-a1f07d3c79f3",
        "atrasoDeGrupo": 0
      },
      {
        "id": "91337dac-9f22-43ba-87b1-ef487c61bf11",
        "idJson": "4cc23558-e561-4dfb-ad06-a3e738fc6d8a",
        "atrasoDeGrupo": 0
      },
      {
        "id": "f18bc164-fde2-4ee9-9130-85c39684a941",
        "idJson": "39cf3152-8535-4bc6-bf99-3817bb3573c1",
        "atrasoDeGrupo": 0
      },
      {
        "id": "e91411be-f5dc-47b8-aaee-30bbc4ea0993",
        "idJson": "79a447ea-0cd9-4d53-b75e-edebc2045586",
        "atrasoDeGrupo": 0
      },
      {
        "id": "44218209-e8ed-4dca-9daa-ef9ad22b657b",
        "idJson": "fe696de0-9329-462e-9d62-c14721f7fdc2",
        "atrasoDeGrupo": 0
      },
      {
        "id": "c5d9e8a9-cb81-4a38-9bb8-2cffcba389e3",
        "idJson": "dae2fedc-3cda-4176-b810-cfef655d91eb",
        "atrasoDeGrupo": 5
      },
      {
        "id": "6723a2b4-bf34-43eb-a8b9-6164926be565",
        "idJson": "a33b7102-331b-4be7-84f2-d117a5d5ac91",
        "atrasoDeGrupo": 0
      },
      {
        "id": "f9de1520-2d1f-46a8-bcb7-5289de875559",
        "idJson": "bd0fbc56-d804-4d70-bca0-252aaeac488f",
        "atrasoDeGrupo": 0
      },
      {
        "id": "14736550-d69c-4521-8e53-300c70cba2a6",
        "idJson": "a6f38291-71a5-4da1-a875-65ae5bd49199",
        "atrasoDeGrupo": 0
      },
      {
        "id": "095f4a3b-a608-4cfc-8a94-238810f50a1b",
        "idJson": "1956fafd-a46f-45bb-a862-ed4f83b1a7f1",
        "atrasoDeGrupo": 0
      },
      {
        "id": "da82930b-1368-43da-8628-41ece1e01f1a",
        "idJson": "d8b4e8f7-5f6b-4394-b153-ae74af3de5f3",
        "atrasoDeGrupo": 0
      },
      {
        "id": "4f5e89f6-3a95-4f2c-8a0c-c47e6699256e",
        "idJson": "8e21abad-157d-4e89-ad00-d430c87b1f58",
        "atrasoDeGrupo": 0
      },
      {
        "id": "d416295a-b833-4e53-bb76-a6316211168d",
        "idJson": "4c81ebc7-b708-4ecb-a2fc-c5f387b8a23e",
        "atrasoDeGrupo": 0
      },
      {
        "id": "81c34d45-c027-4637-a117-e477150d1f78",
        "idJson": "45c5e895-0a35-4724-beea-8d9d874772f1",
        "atrasoDeGrupo": 0
      },
      {
        "id": "1d708e5d-acd1-4ccb-ac3a-ab135d45ec66",
        "idJson": "f694e0a4-e2a4-4e76-8dec-c553468c069f",
        "atrasoDeGrupo": 0
      },
      {
        "id": "088ce7a5-58cc-48e3-a05a-a0540e70c1bd",
        "idJson": "d95cfd03-b425-40c7-87e3-86b6eb04fc75",
        "atrasoDeGrupo": 0
      },
      {
        "id": "ace0abb7-e9af-4b3e-a59b-de6518d03be4",
        "idJson": "b24492db-a537-441a-8a70-42496f22fcd2",
        "atrasoDeGrupo": 0
      },
      {
        "id": "7111eb1a-050a-449e-8139-b09b2f0b0831",
        "idJson": "dfe22096-51f6-43b1-a07c-484f2d260774",
        "atrasoDeGrupo": 0
      },
      {
        "id": "dcba0e2d-11e8-481b-828e-1d25ff663e0f",
        "idJson": "ee4d4f1e-7c3c-467c-b4f2-b4faac55ba11",
        "atrasoDeGrupo": 0
      }
    ],
    "statusVersao": "EDITANDO",
    "versaoControlador": {
      "id": "96dbea4d-746c-4f50-93d9-94d6e10082bc",
      "idJson": null,
      "descricao": "Controlador criado pelo usuário: Administrador Geral",
      "usuario": {
        "id": "8d930297-8ee3-11e6-970d-0401fa9c1b01",
        "nome": "Administrador Geral",
        "login": "root",
        "email": "root@influunt.com.br",
        "area": {
          "idJson": "afb12c64-d2a5-4af5-bb6b-ce692ce25bc4"
        }
      }
    },
    "versoesPlanos": [
      {
        "id": "4a6f5ec9-b795-453b-830f-8bc9b5c98350",
        "idJson": "c2aff556-c252-4e9d-a03c-637d3a2d0ed5",
        "statusVersao": "EDITANDO",
        "anel": {
          "idJson": "d9c67923-8710-4fb5-866a-38c028ee86a4"
        },
        "planos": [
          {
            "idJson": "163f67f0-cfd8-48f2-94af-c7f59940dc55"
          },
          {
            "idJson": "2914de80-2f0c-4fda-9d38-e126a253fa76"
          },
          {
            "idJson": "294c9a8e-57a0-4a1f-8e49-d4a1b4015fc6"
          },
          {
            "idJson": "8ad00916-355c-4fc5-bdec-4c2cb2187e27"
          },
          {
            "idJson": "3839e784-5006-45e3-8742-b06b6801e153"
          },
          {
            "idJson": "7d56b43d-09c6-4dad-a16c-c333a8f716da"
          }
        ]
      },
      {
        "id": "bb331ce4-f686-45f6-be36-e88c570a39ae",
        "idJson": "9df20e92-0b20-42d5-93d6-297cd4289319",
        "statusVersao": "EDITANDO",
        "anel": {
          "idJson": "078e9420-b13b-48ba-beeb-95ba45d9d8d3"
        },
        "planos": [
          {
            "idJson": "2cc8025b-debb-4694-b459-297b59f9fd21"
          },
          {
            "idJson": "15a9c3e1-fe1d-4ea8-a6d1-2d3aff4ed177"
          },
          {
            "idJson": "9c3ae287-1e26-4688-9489-870757c48936"
          },
          {
            "idJson": "906ba906-527f-44ce-8da2-6cbab108b4ee"
          },
          {
            "idJson": "abe96d47-fa89-4b12-a674-ee26741d861a"
          },
          {
            "idJson": "d666dabb-03a6-4e66-9818-9c198f6f669e"
          }
        ]
      },
      {
        "id": "2855c8f4-f4f2-4918-be50-4250d8b328b8",
        "idJson": "fb186624-2c73-49f9-8d7c-3fc9d3b7de63",
        "statusVersao": "EDITANDO",
        "anel": {
          "idJson": "cd610cd4-f2cf-4079-93a2-fad92a85f493"
        },
        "planos": [
          {
            "idJson": "986222a1-e6e9-40bf-b79e-1f7aeb88c5ce"
          },
          {
            "idJson": "8ea9bc20-9313-4113-a0b7-51e16df68eb4"
          },
          {
            "idJson": "aa614e64-a228-428c-93d9-f586fcdfb68a"
          },
          {
            "idJson": "b962be0a-3b23-4efa-aef0-c19e461d8859"
          },
          {
            "idJson": "abdaffeb-10e7-4134-8584-2b017c0ca408"
          },
          {
            "idJson": "7669c3e5-b086-4c41-bd5c-cdcb19a13147"
          }
        ]
      }
    ],
    "tabelasHorarias": [
      {
        "id": "880586c3-32a1-4dcd-8c08-1270168eaafb",
        "idJson": "799b4f9f-4e83-4261-be45-0dc5600274c5",
        "versaoTabelaHoraria": {
          "idJson": "ea98e9cd-c024-417b-a2ea-18e1cfedf277"
        },
        "eventos": [
          {
            "idJson": "7be53fc4-09c7-40b1-8a67-f08d6a4bce00"
          },
          {
            "idJson": "8868c6d1-4202-4524-999d-929b36c2635c"
          },
          {
            "idJson": "d27accef-56dd-46f5-aca4-8737cd10f56d"
          },
          {
            "idJson": "947c80d5-e0a7-47a4-a329-4596b74265cd"
          },
          {
            "idJson": "92822d44-5459-4cd0-a7a3-b74c5d5ada4b"
          }
        ]
      }
    ],
    "eventos": [
      {
        "id": "5c46e658-9aa4-4e48-98d9-9bd435a63fb5",
        "idJson": "d27accef-56dd-46f5-aca4-8737cd10f56d",
        "posicao": "2",
        "tipo": "NORMAL",
        "diaDaSemana": "Segunda à Sábado",
        "data": "27-10-2016",
        "horario": "10:00:00.000",
        "posicaoPlano": "2",
        "tabelaHoraria": {
          "idJson": "799b4f9f-4e83-4261-be45-0dc5600274c5"
        }
      },
      {
        "id": "19db6a4f-262e-48dd-9638-f82d1621a78d",
        "idJson": "7be53fc4-09c7-40b1-8a67-f08d6a4bce00",
        "posicao": "4",
        "tipo": "NORMAL",
        "diaDaSemana": "Segunda à Sexta",
        "data": "27-10-2016",
        "horario": "18:00:00.000",
        "posicaoPlano": "5",
        "tabelaHoraria": {
          "idJson": "799b4f9f-4e83-4261-be45-0dc5600274c5"
        }
      },
      {
        "id": "bef29ba9-38d2-4b70-8317-bb514d2ef74c",
        "idJson": "92822d44-5459-4cd0-a7a3-b74c5d5ada4b",
        "posicao": "5",
        "tipo": "NORMAL",
        "diaDaSemana": "Todos os dias da semana",
        "data": "27-10-2016",
        "horario": "20:00:00.000",
        "posicaoPlano": "6",
        "tabelaHoraria": {
          "idJson": "799b4f9f-4e83-4261-be45-0dc5600274c5"
        }
      },
      {
        "id": "57a53da2-205c-4382-ac91-eaf5a070446b",
        "idJson": "8868c6d1-4202-4524-999d-929b36c2635c",
        "posicao": "1",
        "tipo": "NORMAL",
        "diaDaSemana": "Todos os dias da semana",
        "data": "08-04-0026",
        "horario": "06:00:00.000",
        "posicaoPlano": "1",
        "tabelaHoraria": {
          "idJson": "799b4f9f-4e83-4261-be45-0dc5600274c5"
        }
      },
      {
        "id": "5d0db162-901f-44be-864e-8ea45acacfae",
        "idJson": "947c80d5-e0a7-47a4-a329-4596b74265cd",
        "posicao": "3",
        "tipo": "NORMAL",
        "diaDaSemana": "Segunda à Sexta",
        "data": "27-10-2016",
        "horario": "16:00:00.000",
        "posicaoPlano": "4",
        "tabelaHoraria": {
          "idJson": "799b4f9f-4e83-4261-be45-0dc5600274c5"
        }
      }
    ]
  },
  getControladorId: function() {
    return this.obj.id;
  },
  get: function() {
    return _.cloneDeep(this.obj);
  }
};
