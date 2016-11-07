'use strict';

/**
 * Conjunto de funções auxiliares utilizadas pelos testes do wizard de controladores.
 *
 * @type       {<type>}
 */
var ControladorAtrasoGrupoAutomaticoComConflito = {
  obj: {
    "id": "977d85e8-1c7a-45cc-807e-a51bcdbbe7f8",
    "versoesTabelasHorarias": [],
    "sequencia": 5,
    "limiteEstagio": 16,
    "limiteGrupoSemaforico": 16,
    "limiteAnel": 4,
    "limiteDetectorPedestre": 4,
    "limiteDetectorVeicular": 8,
    "limiteTabelasEntreVerdes": 2,
    "limitePlanos": 16,
    "nomeEndereco": "Rua Paul Bouthilier, nº 266",
    "dataCriacao": "07/11/2016 10:58:31",
    "dataAtualizacao": "07/11/2016 11:04:03",
    "CLC": "1.000.0005",
    "bloqueado": false,
    "planosBloqueado": false,
    "verdeMin": 1,
    "verdeMax": 255,
    "verdeMinimoMin": 10,
    "verdeMinimoMax": 255,
    "verdeMaximoMin": 10,
    "verdeMaximoMax": 255,
    "extensaoVerdeMin": 1,
    "extensaoVerdeMax": 10,
    "verdeIntermediarioMin": 10,
    "verdeIntermediarioMax": 255,
    "defasagemMin": 0,
    "defasagemMax": "255",
    "amareloMin": 3,
    "amareloMax": 5,
    "vermelhoIntermitenteMin": 3,
    "vermelhoIntermitenteMax": 32,
    "vermelhoLimpezaVeicularMin": 0,
    "vermelhoLimpezaVeicularMax": 7,
    "vermelhoLimpezaPedestreMin": 0,
    "vermelhoLimpezaPedestreMax": 5,
    "atrasoGrupoMin": 0,
    "atrasoGrupoMax": "20",
    "verdeSegurancaVeicularMin": 10,
    "verdeSegurancaVeicularMax": 30,
    "verdeSegurancaPedestreMin": 4,
    "verdeSegurancaPedestreMax": 10,
    "maximoPermanenciaEstagioMin": 60,
    "maximoPermanenciaEstagioMax": 255,
    "defaultMaximoPermanenciaEstagioVeicular": 127,
    "cicloMin": 30,
    "cicloMax": 255,
    "ausenciaDeteccaoMin": "0",
    "ausenciaDeteccaoMax": "4320",
    "deteccaoPermanenteMin": "0",
    "deteccaoPermanenteMax": "1440",
    "statusControlador": "EM_CONFIGURACAO",
    "statusControladorReal": "EM_CONFIGURACAO",
    "area": {
      "idJson": "66b6a0c4-a1c4-11e6-970d-0401fa9c1b01"
    },
    "endereco": {
      "idJson": "34de0938-8610-4daa-88ae-cbee4df8890a"
    },
    "modelo": {
      "id": "66b6ba69-a1c4-11e6-970d-0401fa9c1b01",
      "idJson": "66b6ba7e-a1c4-11e6-970d-0401fa9c1b01",
      "descricao": "Modelo Básico",
      "fabricante": {
        "id": "66b6a723-a1c4-11e6-970d-0401fa9c1b01",
        "nome": "Raro Labs"
      }
    },
    "aneis": [
      {
        "id": "06fc938e-ef15-494f-931c-e1ae78db5e0a",
        "idJson": "c6787208-ede2-4b5f-b857-efa5ad97f0ae",
        "numeroSMEE": "-",
        "ativo": true,
        "aceitaModoManual": true,
        "posicao": 1,
        "CLA": "1.000.0005.1",
        "estagios": [
          {
            "idJson": "25dd2981-084e-4b00-be77-b92052c25502"
          },
          {
            "idJson": "fcc3c63e-cb36-4966-9407-1b8bf30c3106"
          },
          {
            "idJson": "bd3d834a-d83c-4ac0-b52a-04c2bdbd34a7"
          },
          {
            "idJson": "8a1e2214-588c-465a-9e37-d838c56f2106"
          },
          {
            "idJson": "73d11d5b-7d01-455c-9764-2e222b01bc8b"
          }
        ],
        "gruposSemaforicos": [
          {
            "idJson": "911ebc3b-25b7-4599-8531-a2f776850b11"
          },
          {
            "idJson": "e04d3c80-06de-45c1-b635-c81ce567ac8c"
          },
          {
            "idJson": "3e22d9c8-6a98-45cc-8bb9-b4e9cfb5a696"
          },
          {
            "idJson": "9e494c36-72d9-485c-b69e-85ac411e912a"
          },
          {
            "idJson": "02c45e6c-c594-40e6-b11a-a6b238db83b2"
          }
        ],
        "detectores": [],
        "planos": [
          {
            "idJson": "d89e7a44-dfc5-47a7-bdde-11820f06749f"
          },
          {
            "idJson": "bd7ba623-c2e7-49ee-a306-4353f5513a7c"
          },
          {
            "idJson": "216d0496-f7b6-4038-95b7-1975218e050a"
          },
          {
            "idJson": "986d4974-baad-4c9d-ad93-ec9a23a982ef"
          },
          {
            "idJson": "f9ecbfed-2372-4a5c-a3bf-555dde6bdec5"
          },
          {
            "idJson": "4e3c0565-954f-4586-bda1-7fc7a89dcdfd"
          },
          {
            "idJson": "b80ebfe7-fee4-477f-be85-f7cdbf6b15c0"
          },
          {
            "idJson": "179a236e-119f-4e47-95cf-1e8c8988c265"
          },
          {
            "idJson": "aa49b13d-e7c0-4c65-8985-9f371dd53bfd"
          },
          {
            "idJson": "0ef18b30-6185-4af9-be67-4382bb721c14"
          },
          {
            "idJson": "3bb2b8b8-450f-40ae-a31b-096d327bade6"
          },
          {
            "idJson": "9bd9461c-7792-4aa2-a8a6-805945607a8d"
          },
          {
            "idJson": "9a13a395-cc26-4210-a84a-cc950a69ac4c"
          },
          {
            "idJson": "75d979f9-9d31-4d8f-be82-cb92c36a360d"
          },
          {
            "idJson": "26307aa2-23c7-4711-82b5-1cd3780c33d0"
          },
          {
            "idJson": "106b7f1d-a993-43a1-bb65-57b743379fcd"
          },
          {
            "idJson": "68ba2551-67fb-40fd-92df-b7a5ad5b053d"
          }
        ],
        "endereco": {
          "idJson": "37608afb-115b-4bb6-a930-4a31b6e1715b"
        },
        "versaoPlano": {
          "idJson": "8005179f-4974-454e-a900-cdcf3bd0c58f"
        },
        "localizacao": "Rua Paul Bouthilier, nº 266"
      },
      {
        "id": "31e4fb3e-5633-4e85-b91b-20dff999e742",
        "idJson": "692283f1-ef40-46b1-975e-19d87ba32ef4",
        "ativo": false,
        "aceitaModoManual": true,
        "posicao": 2,
        "CLA": "1.000.0005.2",
        "estagios": [],
        "gruposSemaforicos": [],
        "detectores": [],
        "planos": []
      },
      {
        "id": "d922624c-4ff3-44d2-9217-d2f30e7f5ef7",
        "idJson": "10559751-15c7-432f-8d6b-37f92cd5b0ec",
        "ativo": false,
        "aceitaModoManual": true,
        "posicao": 3,
        "CLA": "1.000.0005.3",
        "estagios": [],
        "gruposSemaforicos": [],
        "detectores": [],
        "planos": []
      },
      {
        "id": "3a938f4c-bd74-491c-a5c2-7050f8cdf60f",
        "idJson": "32db49f0-caa9-4f86-862c-e93f6dd8dbaf",
        "ativo": false,
        "aceitaModoManual": true,
        "posicao": 4,
        "CLA": "1.000.0005.4",
        "estagios": [],
        "gruposSemaforicos": [],
        "detectores": [],
        "planos": []
      }
    ],
    "estagios": [
      {
        "id": "13ab0b73-eadd-42f7-b040-ed60697225a5",
        "idJson": "25dd2981-084e-4b00-be77-b92052c25502",
        "tempoMaximoPermanencia": 127,
        "tempoMaximoPermanenciaAtivado": true,
        "demandaPrioritaria": false,
        "tempoVerdeDemandaPrioritaria": 1,
        "posicao": 2,
        "anel": {
          "idJson": "c6787208-ede2-4b5f-b857-efa5ad97f0ae"
        },
        "imagem": {
          "idJson": "fc8917bb-87e1-442c-ae67-ed91b9ac81cf",
          "id": "a312d88d-53d9-4a74-82bf-1d3eb90446fc"
        },
        "origemDeTransicoesProibidas": [],
        "destinoDeTransicoesProibidas": [],
        "alternativaDeTransicoesProibidas": [],
        "estagiosGruposSemaforicos": [
          {
            "idJson": "f323958e-1a61-47f9-a3b8-18777a600d90"
          }
        ],
        "verdeMinimoEstagio": 10,
        "isVeicular": true
      },
      {
        "id": "cc9541c8-32e1-4bbc-8339-1c0bf170c5bc",
        "idJson": "73d11d5b-7d01-455c-9764-2e222b01bc8b",
        "tempoMaximoPermanencia": 127,
        "tempoMaximoPermanenciaAtivado": true,
        "demandaPrioritaria": false,
        "tempoVerdeDemandaPrioritaria": 1,
        "posicao": 1,
        "anel": {
          "idJson": "c6787208-ede2-4b5f-b857-efa5ad97f0ae"
        },
        "imagem": {
          "idJson": "c5700a11-20cf-481a-9d62-584708a6da53",
          "id": "fab1e63f-a4be-4f09-8f8f-4e9305bd03fb"
        },
        "origemDeTransicoesProibidas": [],
        "destinoDeTransicoesProibidas": [],
        "alternativaDeTransicoesProibidas": [],
        "estagiosGruposSemaforicos": [
          {
            "idJson": "ca67eae1-4a33-4afe-8cc9-6c753ed82a0a"
          }
        ],
        "verdeMinimoEstagio": 10,
        "isVeicular": true
      },
      {
        "id": "c4791f0e-e3ef-4fd0-9f2e-dc617e6e5952",
        "idJson": "8a1e2214-588c-465a-9e37-d838c56f2106",
        "tempoMaximoPermanencia": 127,
        "tempoMaximoPermanenciaAtivado": true,
        "demandaPrioritaria": false,
        "tempoVerdeDemandaPrioritaria": 1,
        "posicao": 3,
        "anel": {
          "idJson": "c6787208-ede2-4b5f-b857-efa5ad97f0ae"
        },
        "imagem": {
          "idJson": "67e2c36a-9d4c-4ea6-af13-4ab9d50f0b6c",
          "id": "60a24530-c6d2-49e0-a9c3-06de765634b6"
        },
        "origemDeTransicoesProibidas": [],
        "destinoDeTransicoesProibidas": [],
        "alternativaDeTransicoesProibidas": [],
        "estagiosGruposSemaforicos": [
          {
            "idJson": "4a7739ff-09ce-49dd-9011-cbfc3db6d419"
          }
        ],
        "verdeMinimoEstagio": 10,
        "isVeicular": true
      },
      {
        "id": "70f5f3f9-51de-4569-ab2d-09993d4330b6",
        "idJson": "fcc3c63e-cb36-4966-9407-1b8bf30c3106",
        "tempoMaximoPermanencia": 127,
        "tempoMaximoPermanenciaAtivado": true,
        "demandaPrioritaria": false,
        "tempoVerdeDemandaPrioritaria": 1,
        "posicao": 5,
        "anel": {
          "idJson": "c6787208-ede2-4b5f-b857-efa5ad97f0ae"
        },
        "imagem": {
          "idJson": "b1a69915-2818-4784-b5e8-a0f45632eef8",
          "id": "bf1479f6-3ac8-44c4-92e8-eeb9065e8bf9"
        },
        "origemDeTransicoesProibidas": [],
        "destinoDeTransicoesProibidas": [],
        "alternativaDeTransicoesProibidas": [],
        "estagiosGruposSemaforicos": [
          {
            "idJson": "e5f2b2df-7884-420b-8cdb-8b5ca7774f34"
          }
        ],
        "verdeMinimoEstagio": 10,
        "isVeicular": true
      },
      {
        "id": "c2284f71-5b37-4140-a25a-03bba319dc05",
        "idJson": "bd3d834a-d83c-4ac0-b52a-04c2bdbd34a7",
        "tempoMaximoPermanencia": 127,
        "tempoMaximoPermanenciaAtivado": true,
        "demandaPrioritaria": false,
        "tempoVerdeDemandaPrioritaria": 1,
        "posicao": 4,
        "anel": {
          "idJson": "c6787208-ede2-4b5f-b857-efa5ad97f0ae"
        },
        "imagem": {
          "idJson": "7d3df30d-39d0-4d82-bc5a-45df9dea8b1f",
          "id": "454eb503-95e6-499d-8ce7-a859ce865a72"
        },
        "origemDeTransicoesProibidas": [],
        "destinoDeTransicoesProibidas": [],
        "alternativaDeTransicoesProibidas": [],
        "estagiosGruposSemaforicos": [
          {
            "idJson": "cad71e0d-a531-466a-aa3b-0513acc221b7"
          }
        ],
        "verdeMinimoEstagio": 10,
        "isVeicular": true
      }
    ],
    "gruposSemaforicos": [
      {
        "id": "5cf8d093-7df0-4795-9e83-3afe7b3f5dfb",
        "idJson": "911ebc3b-25b7-4599-8531-a2f776850b11",
        "tipo": "VEICULAR",
        "posicao": 1,
        "faseVermelhaApagadaAmareloIntermitente": true,
        "tempoVerdeSeguranca": 10,
        "anel": {
          "idJson": "c6787208-ede2-4b5f-b857-efa5ad97f0ae"
        },
        "verdesConflitantesOrigem": [
          {
            "idJson": "0c145ff2-5c72-4b76-9a7d-ec3b4d314096"
          },
          {
            "idJson": "556b62ea-5649-4fe1-8482-8cf17c91ccee"
          },
          {
            "idJson": "59ff92e4-4917-4e29-9485-0b0a9b47ff7f"
          },
          {
            "idJson": "49025b46-ef88-4091-a6be-3c008ec16ab3"
          }
        ],
        "verdesConflitantesDestino": [],
        "estagiosGruposSemaforicos": [
          {
            "idJson": "ca67eae1-4a33-4afe-8cc9-6c753ed82a0a"
          }
        ],
        "transicoes": [
          {
            "idJson": "465962e3-ef1d-4112-92d3-075a52f51d78"
          },
          {
            "idJson": "9eed26ef-8024-42c2-8e4a-c538526a3bfd"
          },
          {
            "idJson": "5268c307-4b8e-43ef-871f-44cbad9f5a03"
          },
          {
            "idJson": "a0cd7c36-96bb-44e3-af19-516bf44ba5f3"
          }
        ],
        "transicoesComGanhoDePassagem": [
          {
            "idJson": "9c77f27d-7a13-40a5-9371-fb75b18f0d80"
          },
          {
            "idJson": "25187f82-3ab1-47b8-96a4-8f85c8bb0336"
          },
          {
            "idJson": "59817026-e53b-42a3-9e6e-93977fa8c4e5"
          },
          {
            "idJson": "219a3d87-b0ff-4e00-8f97-b594385308a9"
          }
        ],
        "tabelasEntreVerdes": [
          {
            "idJson": "4de0a2ed-0b01-45ee-bedf-ad211edb7d1d"
          }
        ],
        "$$hashKey": "object:281"
      },
      {
        "id": "793d1393-ba2d-4d4c-b27a-1c732a0475c2",
        "idJson": "e04d3c80-06de-45c1-b635-c81ce567ac8c",
        "tipo": "VEICULAR",
        "posicao": 4,
        "faseVermelhaApagadaAmareloIntermitente": true,
        "tempoVerdeSeguranca": 10,
        "anel": {
          "idJson": "c6787208-ede2-4b5f-b857-efa5ad97f0ae"
        },
        "verdesConflitantesOrigem": [
          {
            "idJson": "11c36ae7-4d0f-488e-b795-de6c4b661873"
          }
        ],
        "verdesConflitantesDestino": [
          {
            "idJson": "b2f76fd0-9831-4e33-8347-1cf75ad396dd"
          },
          {
            "idJson": "556b62ea-5649-4fe1-8482-8cf17c91ccee"
          },
          {
            "idJson": "be542cb7-9ba1-4fa1-91f8-dd02c915b54e"
          }
        ],
        "estagiosGruposSemaforicos": [
          {
            "idJson": "cad71e0d-a531-466a-aa3b-0513acc221b7"
          }
        ],
        "transicoes": [
          {
            "idJson": "f7024646-e09a-4a20-b0e6-dc6a5be3a1ad"
          },
          {
            "idJson": "0b9d7813-d6d0-4d44-8e86-2c1d3cbba0e2"
          },
          {
            "idJson": "021d9bca-8b05-4788-88d6-4630c50f58f1"
          },
          {
            "idJson": "e49ec9b7-d2dd-4196-add1-0a0d57909b4d"
          }
        ],
        "transicoesComGanhoDePassagem": [
          {
            "idJson": "63ca8341-5d30-421c-b30b-fbbeeb8fb543"
          },
          {
            "idJson": "c33018b4-307f-47ff-aeb4-530852f56461"
          },
          {
            "idJson": "6d6bd531-af8e-4d57-a515-cac5509a6322"
          },
          {
            "idJson": "98b9415e-6c8f-482a-b1bc-6c77ef3a3657"
          }
        ],
        "tabelasEntreVerdes": [
          {
            "idJson": "5a1575f0-22ef-49b9-be73-63b8bc8f34f0"
          }
        ],
        "$$hashKey": "object:284"
      },
      {
        "id": "f8de468e-1b0f-46f7-ac03-7f1a89467ac4",
        "idJson": "9e494c36-72d9-485c-b69e-85ac411e912a",
        "tipo": "VEICULAR",
        "posicao": 2,
        "faseVermelhaApagadaAmareloIntermitente": true,
        "tempoVerdeSeguranca": 10,
        "anel": {
          "idJson": "c6787208-ede2-4b5f-b857-efa5ad97f0ae"
        },
        "verdesConflitantesOrigem": [
          {
            "idJson": "b2f76fd0-9831-4e33-8347-1cf75ad396dd"
          },
          {
            "idJson": "b5a1d64d-87fa-4675-886b-e13c4c24c726"
          },
          {
            "idJson": "465313f4-124b-456e-bbfc-60937c4beae2"
          }
        ],
        "verdesConflitantesDestino": [
          {
            "idJson": "0c145ff2-5c72-4b76-9a7d-ec3b4d314096"
          }
        ],
        "estagiosGruposSemaforicos": [
          {
            "idJson": "f323958e-1a61-47f9-a3b8-18777a600d90"
          }
        ],
        "transicoes": [
          {
            "idJson": "c04f6293-e686-4081-95a8-9c9264cb0476"
          },
          {
            "idJson": "68de146a-098d-4296-8e63-b8d4ce72638a"
          },
          {
            "idJson": "27db0977-2f35-490e-bac6-3abc9b541913"
          },
          {
            "idJson": "ab7e1472-5745-456b-822d-8cb04f050e6d"
          }
        ],
        "transicoesComGanhoDePassagem": [
          {
            "idJson": "d85a559e-b497-4076-a001-e77815dbf9fe"
          },
          {
            "idJson": "f8ca96f0-f76f-48fd-b0a0-73335a39f17f"
          },
          {
            "idJson": "974fa792-8176-43d9-b27f-8588cb33ade0"
          },
          {
            "idJson": "4b8ca812-3aa2-42d2-83cc-e6d63333143d"
          }
        ],
        "tabelasEntreVerdes": [
          {
            "idJson": "80024360-e2de-4c19-8c43-9d1fc85b016b"
          }
        ],
        "$$hashKey": "object:282"
      },
      {
        "id": "fafc8ca1-c4a7-4b54-a2e9-ff5fa0b39375",
        "idJson": "02c45e6c-c594-40e6-b11a-a6b238db83b2",
        "tipo": "VEICULAR",
        "posicao": 3,
        "faseVermelhaApagadaAmareloIntermitente": true,
        "tempoVerdeSeguranca": 10,
        "anel": {
          "idJson": "c6787208-ede2-4b5f-b857-efa5ad97f0ae"
        },
        "verdesConflitantesOrigem": [
          {
            "idJson": "78cf6b89-7c93-4e10-ac00-c2680ca15c71"
          },
          {
            "idJson": "be542cb7-9ba1-4fa1-91f8-dd02c915b54e"
          }
        ],
        "verdesConflitantesDestino": [
          {
            "idJson": "b5a1d64d-87fa-4675-886b-e13c4c24c726"
          },
          {
            "idJson": "49025b46-ef88-4091-a6be-3c008ec16ab3"
          }
        ],
        "estagiosGruposSemaforicos": [
          {
            "idJson": "4a7739ff-09ce-49dd-9011-cbfc3db6d419"
          }
        ],
        "transicoes": [
          {
            "idJson": "b42ce9f6-b0e2-44ea-8efc-d873178a7851"
          },
          {
            "idJson": "b1204656-0e33-497c-b4b8-f2ce21f992b8"
          },
          {
            "idJson": "9bfae4e3-31e9-4009-be79-18325bb1a58e"
          },
          {
            "idJson": "7702a320-877a-4e97-b099-3340a8a6e1e9"
          }
        ],
        "transicoesComGanhoDePassagem": [
          {
            "idJson": "3e18807f-f5da-49eb-a9a8-b8f6a8b1b45e"
          },
          {
            "idJson": "b51f4511-98fd-4cf9-95db-d0e3e3c45424"
          },
          {
            "idJson": "fd3e05d1-6b18-4d00-98b7-3e0820105e0e"
          },
          {
            "idJson": "57838e7e-2587-498b-a6da-54dd7bf91378"
          }
        ],
        "tabelasEntreVerdes": [
          {
            "idJson": "2a9e5868-be11-4c65-b093-bf05fdf911e1"
          }
        ],
        "$$hashKey": "object:283"
      },
      {
        "id": "e921c47b-2401-4d00-b64a-a65ddfcf06f2",
        "idJson": "3e22d9c8-6a98-45cc-8bb9-b4e9cfb5a696",
        "tipo": "VEICULAR",
        "posicao": 5,
        "faseVermelhaApagadaAmareloIntermitente": true,
        "tempoVerdeSeguranca": 10,
        "anel": {
          "idJson": "c6787208-ede2-4b5f-b857-efa5ad97f0ae"
        },
        "verdesConflitantesOrigem": [],
        "verdesConflitantesDestino": [
          {
            "idJson": "78cf6b89-7c93-4e10-ac00-c2680ca15c71"
          },
          {
            "idJson": "11c36ae7-4d0f-488e-b795-de6c4b661873"
          },
          {
            "idJson": "59ff92e4-4917-4e29-9485-0b0a9b47ff7f"
          },
          {
            "idJson": "465313f4-124b-456e-bbfc-60937c4beae2"
          }
        ],
        "estagiosGruposSemaforicos": [
          {
            "idJson": "e5f2b2df-7884-420b-8cdb-8b5ca7774f34"
          }
        ],
        "transicoes": [
          {
            "idJson": "f955e99c-228c-4b0e-b52e-0eb82457544c"
          },
          {
            "idJson": "b0b5dbd7-7310-4130-9879-cd33b30770ab"
          },
          {
            "idJson": "ba1d9989-daf3-46a9-978f-38c43720d7e7"
          },
          {
            "idJson": "9ce4768d-d453-4ac2-854e-758c9dc8d9fc"
          }
        ],
        "transicoesComGanhoDePassagem": [
          {
            "idJson": "f64d5475-66a7-4a67-b2f9-345b1ce9ad96"
          },
          {
            "idJson": "4cd3f041-188a-4bee-a8a8-12f0c5292b64"
          },
          {
            "idJson": "3fd7473b-f14a-4500-8cc8-cb452051c587"
          },
          {
            "idJson": "682db8dc-7321-4e56-ae09-89c31d98672d"
          }
        ],
        "tabelasEntreVerdes": [
          {
            "idJson": "2364aeba-1c75-4080-a1d1-5fef25cf2d10"
          }
        ],
        "$$hashKey": "object:285"
      }
    ],
    "detectores": [],
    "transicoesProibidas": [],
    "estagiosGruposSemaforicos": [
      {
        "id": "7ddadf8a-0b63-4fd8-be61-7b8250276e22",
        "idJson": "f323958e-1a61-47f9-a3b8-18777a600d90",
        "estagio": {
          "idJson": "25dd2981-084e-4b00-be77-b92052c25502"
        },
        "grupoSemaforico": {
          "idJson": "9e494c36-72d9-485c-b69e-85ac411e912a"
        }
      },
      {
        "id": "5005072f-43b9-49a5-b527-fd1d9ab6ab53",
        "idJson": "e5f2b2df-7884-420b-8cdb-8b5ca7774f34",
        "estagio": {
          "idJson": "fcc3c63e-cb36-4966-9407-1b8bf30c3106"
        },
        "grupoSemaforico": {
          "idJson": "3e22d9c8-6a98-45cc-8bb9-b4e9cfb5a696"
        }
      },
      {
        "id": "911b1791-920c-4be8-ab77-68810649c202",
        "idJson": "cad71e0d-a531-466a-aa3b-0513acc221b7",
        "estagio": {
          "idJson": "bd3d834a-d83c-4ac0-b52a-04c2bdbd34a7"
        },
        "grupoSemaforico": {
          "idJson": "e04d3c80-06de-45c1-b635-c81ce567ac8c"
        }
      },
      {
        "id": "79fe384a-6534-40f6-8bd2-15f0a10236f4",
        "idJson": "ca67eae1-4a33-4afe-8cc9-6c753ed82a0a",
        "estagio": {
          "idJson": "73d11d5b-7d01-455c-9764-2e222b01bc8b"
        },
        "grupoSemaforico": {
          "idJson": "911ebc3b-25b7-4599-8531-a2f776850b11"
        }
      },
      {
        "id": "5f80e468-40f5-41c1-8404-4fef12bffcab",
        "idJson": "4a7739ff-09ce-49dd-9011-cbfc3db6d419",
        "estagio": {
          "idJson": "8a1e2214-588c-465a-9e37-d838c56f2106"
        },
        "grupoSemaforico": {
          "idJson": "02c45e6c-c594-40e6-b11a-a6b238db83b2"
        }
      }
    ],
    "verdesConflitantes": [
      {
        "id": "65560509-edb7-47e7-9adb-26123fc5b022",
        "idJson": "b2f76fd0-9831-4e33-8347-1cf75ad396dd",
        "origem": {
          "idJson": "9e494c36-72d9-485c-b69e-85ac411e912a"
        },
        "destino": {
          "idJson": "e04d3c80-06de-45c1-b635-c81ce567ac8c"
        }
      },
      {
        "id": "a58a7ea0-0fc4-482a-a59d-c65beda853ba",
        "idJson": "78cf6b89-7c93-4e10-ac00-c2680ca15c71",
        "origem": {
          "idJson": "02c45e6c-c594-40e6-b11a-a6b238db83b2"
        },
        "destino": {
          "idJson": "3e22d9c8-6a98-45cc-8bb9-b4e9cfb5a696"
        }
      },
      {
        "id": "c6981d12-5821-453f-b2e3-0d9ac97db4bd",
        "idJson": "59ff92e4-4917-4e29-9485-0b0a9b47ff7f",
        "origem": {
          "idJson": "911ebc3b-25b7-4599-8531-a2f776850b11"
        },
        "destino": {
          "idJson": "3e22d9c8-6a98-45cc-8bb9-b4e9cfb5a696"
        }
      },
      {
        "id": "cc21353a-dd4d-4841-b730-e8b3a509a544",
        "idJson": "be542cb7-9ba1-4fa1-91f8-dd02c915b54e",
        "origem": {
          "idJson": "02c45e6c-c594-40e6-b11a-a6b238db83b2"
        },
        "destino": {
          "idJson": "e04d3c80-06de-45c1-b635-c81ce567ac8c"
        }
      },
      {
        "id": "4237b219-bc76-4992-a8db-4afb932ffe8b",
        "idJson": "0c145ff2-5c72-4b76-9a7d-ec3b4d314096",
        "origem": {
          "idJson": "911ebc3b-25b7-4599-8531-a2f776850b11"
        },
        "destino": {
          "idJson": "9e494c36-72d9-485c-b69e-85ac411e912a"
        }
      },
      {
        "id": "f04d534e-f6e2-4a46-b572-72bd474087f4",
        "idJson": "465313f4-124b-456e-bbfc-60937c4beae2",
        "origem": {
          "idJson": "9e494c36-72d9-485c-b69e-85ac411e912a"
        },
        "destino": {
          "idJson": "3e22d9c8-6a98-45cc-8bb9-b4e9cfb5a696"
        }
      },
      {
        "id": "823edf39-f865-4eb4-91a2-5254dc7188ce",
        "idJson": "556b62ea-5649-4fe1-8482-8cf17c91ccee",
        "origem": {
          "idJson": "911ebc3b-25b7-4599-8531-a2f776850b11"
        },
        "destino": {
          "idJson": "e04d3c80-06de-45c1-b635-c81ce567ac8c"
        }
      },
      {
        "id": "a85df6e2-9a0b-4264-bb30-de44a942d1f2",
        "idJson": "11c36ae7-4d0f-488e-b795-de6c4b661873",
        "origem": {
          "idJson": "e04d3c80-06de-45c1-b635-c81ce567ac8c"
        },
        "destino": {
          "idJson": "3e22d9c8-6a98-45cc-8bb9-b4e9cfb5a696"
        }
      },
      {
        "id": "b89238af-f1b6-4848-89f3-ad5c9684d0f5",
        "idJson": "b5a1d64d-87fa-4675-886b-e13c4c24c726",
        "origem": {
          "idJson": "9e494c36-72d9-485c-b69e-85ac411e912a"
        },
        "destino": {
          "idJson": "02c45e6c-c594-40e6-b11a-a6b238db83b2"
        }
      },
      {
        "id": "d2c2d8f8-c567-418e-a12c-b5b9bfa1b4c3",
        "idJson": "49025b46-ef88-4091-a6be-3c008ec16ab3",
        "origem": {
          "idJson": "911ebc3b-25b7-4599-8531-a2f776850b11"
        },
        "destino": {
          "idJson": "02c45e6c-c594-40e6-b11a-a6b238db83b2"
        }
      }
    ],
    "transicoes": [
      {
        "id": "2172753d-3100-4f41-954a-4f30e00ecfc4",
        "idJson": "c04f6293-e686-4081-95a8-9c9264cb0476",
        "origem": {
          "idJson": "25dd2981-084e-4b00-be77-b92052c25502"
        },
        "destino": {
          "idJson": "fcc3c63e-cb36-4966-9407-1b8bf30c3106"
        },
        "tabelaEntreVerdesTransicoes": [
          {
            "idJson": "809f84aa-b665-4911-8b88-416f5e678df2"
          }
        ],
        "grupoSemaforico": {
          "idJson": "9e494c36-72d9-485c-b69e-85ac411e912a"
        },
        "tipo": "PERDA_DE_PASSAGEM",
        "tempoAtrasoGrupo": "5",
        "atrasoDeGrupo": {
          "idJson": "8414673f-59aa-4134-84f0-a7acd1ed025f"
        }
      },
      {
        "id": "4e829a24-16f0-4e11-a30c-782094c0b761",
        "idJson": "b42ce9f6-b0e2-44ea-8efc-d873178a7851",
        "origem": {
          "idJson": "8a1e2214-588c-465a-9e37-d838c56f2106"
        },
        "destino": {
          "idJson": "25dd2981-084e-4b00-be77-b92052c25502"
        },
        "tabelaEntreVerdesTransicoes": [
          {
            "idJson": "ecc9fc17-1a6a-4826-97d5-80ea710e8944"
          }
        ],
        "grupoSemaforico": {
          "idJson": "02c45e6c-c594-40e6-b11a-a6b238db83b2"
        },
        "tipo": "PERDA_DE_PASSAGEM",
        "tempoAtrasoGrupo": "5",
        "atrasoDeGrupo": {
          "idJson": "dc289c77-b5ac-4870-9812-c7f032ada3bb"
        }
      },
      {
        "id": "7d7346aa-f464-48d5-b09d-4695fd5372a7",
        "idJson": "9bfae4e3-31e9-4009-be79-18325bb1a58e",
        "origem": {
          "idJson": "8a1e2214-588c-465a-9e37-d838c56f2106"
        },
        "destino": {
          "idJson": "73d11d5b-7d01-455c-9764-2e222b01bc8b"
        },
        "tabelaEntreVerdesTransicoes": [
          {
            "idJson": "9064e375-d024-42c5-a244-7ff0771cefc0"
          }
        ],
        "grupoSemaforico": {
          "idJson": "02c45e6c-c594-40e6-b11a-a6b238db83b2"
        },
        "tipo": "PERDA_DE_PASSAGEM",
        "tempoAtrasoGrupo": "5",
        "atrasoDeGrupo": {
          "idJson": "c716fd33-ff9e-4abd-8932-08bd9170f7e5"
        }
      },
      {
        "id": "c4265955-1305-450f-af93-cf804fcff07d",
        "idJson": "e49ec9b7-d2dd-4196-add1-0a0d57909b4d",
        "origem": {
          "idJson": "bd3d834a-d83c-4ac0-b52a-04c2bdbd34a7"
        },
        "destino": {
          "idJson": "fcc3c63e-cb36-4966-9407-1b8bf30c3106"
        },
        "tabelaEntreVerdesTransicoes": [
          {
            "idJson": "52a431c2-de52-4236-a4a0-8d3347c06ab0"
          }
        ],
        "grupoSemaforico": {
          "idJson": "e04d3c80-06de-45c1-b635-c81ce567ac8c"
        },
        "tipo": "PERDA_DE_PASSAGEM",
        "tempoAtrasoGrupo": "5",
        "atrasoDeGrupo": {
          "idJson": "abb98a14-c2ca-4f3a-9b58-820ef29699c8"
        }
      },
      {
        "id": "d645213c-e355-4492-b2f7-97f4ec7b59f7",
        "idJson": "7702a320-877a-4e97-b099-3340a8a6e1e9",
        "origem": {
          "idJson": "8a1e2214-588c-465a-9e37-d838c56f2106"
        },
        "destino": {
          "idJson": "fcc3c63e-cb36-4966-9407-1b8bf30c3106"
        },
        "tabelaEntreVerdesTransicoes": [
          {
            "idJson": "81f0022c-68b0-4732-8a96-b5da38d8dab6"
          }
        ],
        "grupoSemaforico": {
          "idJson": "02c45e6c-c594-40e6-b11a-a6b238db83b2"
        },
        "tipo": "PERDA_DE_PASSAGEM",
        "tempoAtrasoGrupo": "5",
        "atrasoDeGrupo": {
          "idJson": "3e7c2ed7-3609-4fed-b99c-b66227561b5e"
        }
      },
      {
        "id": "465da776-6c47-400d-89ae-de539e908bc5",
        "idJson": "f955e99c-228c-4b0e-b52e-0eb82457544c",
        "origem": {
          "idJson": "fcc3c63e-cb36-4966-9407-1b8bf30c3106"
        },
        "destino": {
          "idJson": "73d11d5b-7d01-455c-9764-2e222b01bc8b"
        },
        "tabelaEntreVerdesTransicoes": [
          {
            "idJson": "fd502b39-5d1c-42ba-80e3-2e5c16db25f4"
          }
        ],
        "grupoSemaforico": {
          "idJson": "3e22d9c8-6a98-45cc-8bb9-b4e9cfb5a696"
        },
        "tipo": "PERDA_DE_PASSAGEM",
        "tempoAtrasoGrupo": "5",
        "atrasoDeGrupo": {
          "idJson": "b45bec75-0bda-4adf-adb7-8522fc465dd2"
        }
      },
      {
        "id": "32cb9f01-5048-476d-8cfb-b6180706eee2",
        "idJson": "465962e3-ef1d-4112-92d3-075a52f51d78",
        "origem": {
          "idJson": "73d11d5b-7d01-455c-9764-2e222b01bc8b"
        },
        "destino": {
          "idJson": "bd3d834a-d83c-4ac0-b52a-04c2bdbd34a7"
        },
        "tabelaEntreVerdesTransicoes": [
          {
            "idJson": "5ecc43ff-93b0-447f-abde-c48f161614ca"
          }
        ],
        "grupoSemaforico": {
          "idJson": "911ebc3b-25b7-4599-8531-a2f776850b11"
        },
        "tipo": "PERDA_DE_PASSAGEM",
        "tempoAtrasoGrupo": "5",
        "atrasoDeGrupo": {
          "idJson": "83a116bc-6309-4db7-8d73-f06eec6e3058"
        }
      },
      {
        "id": "35b4bfdc-a81b-4afe-a51d-7ef3779d20dc",
        "idJson": "f7024646-e09a-4a20-b0e6-dc6a5be3a1ad",
        "origem": {
          "idJson": "bd3d834a-d83c-4ac0-b52a-04c2bdbd34a7"
        },
        "destino": {
          "idJson": "25dd2981-084e-4b00-be77-b92052c25502"
        },
        "tabelaEntreVerdesTransicoes": [
          {
            "idJson": "17cd61bc-1762-4079-9d09-e60a48ffabc1"
          }
        ],
        "grupoSemaforico": {
          "idJson": "e04d3c80-06de-45c1-b635-c81ce567ac8c"
        },
        "tipo": "PERDA_DE_PASSAGEM",
        "tempoAtrasoGrupo": "5",
        "atrasoDeGrupo": {
          "idJson": "1fe95f5e-9046-43e0-af58-b76ba0649bfe"
        }
      },
      {
        "id": "6c84666c-6cc5-4c94-8af5-a41787c4ccf4",
        "idJson": "b1204656-0e33-497c-b4b8-f2ce21f992b8",
        "origem": {
          "idJson": "8a1e2214-588c-465a-9e37-d838c56f2106"
        },
        "destino": {
          "idJson": "bd3d834a-d83c-4ac0-b52a-04c2bdbd34a7"
        },
        "tabelaEntreVerdesTransicoes": [
          {
            "idJson": "a516983d-e862-4f51-a4f5-e48c33d40bb3"
          }
        ],
        "grupoSemaforico": {
          "idJson": "02c45e6c-c594-40e6-b11a-a6b238db83b2"
        },
        "tipo": "PERDA_DE_PASSAGEM",
        "tempoAtrasoGrupo": "5",
        "atrasoDeGrupo": {
          "idJson": "b500d312-a06d-4be5-b6aa-bc96eabe57d3"
        }
      },
      {
        "id": "4122ceac-e2fd-4a2f-9fae-12cd6d09b959",
        "idJson": "0b9d7813-d6d0-4d44-8e86-2c1d3cbba0e2",
        "origem": {
          "idJson": "bd3d834a-d83c-4ac0-b52a-04c2bdbd34a7"
        },
        "destino": {
          "idJson": "73d11d5b-7d01-455c-9764-2e222b01bc8b"
        },
        "tabelaEntreVerdesTransicoes": [
          {
            "idJson": "9cbacbf5-99c4-4f3a-8f38-4a92cc3a724a"
          }
        ],
        "grupoSemaforico": {
          "idJson": "e04d3c80-06de-45c1-b635-c81ce567ac8c"
        },
        "tipo": "PERDA_DE_PASSAGEM",
        "tempoAtrasoGrupo": "5",
        "atrasoDeGrupo": {
          "idJson": "5b4b8692-eae6-4b5d-bbad-c6019644354f"
        }
      },
      {
        "id": "4df70be3-c670-453d-a15e-31ed47f3d742",
        "idJson": "b0b5dbd7-7310-4130-9879-cd33b30770ab",
        "origem": {
          "idJson": "fcc3c63e-cb36-4966-9407-1b8bf30c3106"
        },
        "destino": {
          "idJson": "bd3d834a-d83c-4ac0-b52a-04c2bdbd34a7"
        },
        "tabelaEntreVerdesTransicoes": [
          {
            "idJson": "f3e81596-32e4-476d-8c7f-a92dd9497c02"
          }
        ],
        "grupoSemaforico": {
          "idJson": "3e22d9c8-6a98-45cc-8bb9-b4e9cfb5a696"
        },
        "tipo": "PERDA_DE_PASSAGEM",
        "tempoAtrasoGrupo": "5",
        "atrasoDeGrupo": {
          "idJson": "2d59e869-62a3-4d30-acb6-ef07a99905dd"
        }
      },
      {
        "id": "7bbb0ce1-856d-4369-84e1-d39d0c2b3208",
        "idJson": "27db0977-2f35-490e-bac6-3abc9b541913",
        "origem": {
          "idJson": "25dd2981-084e-4b00-be77-b92052c25502"
        },
        "destino": {
          "idJson": "8a1e2214-588c-465a-9e37-d838c56f2106"
        },
        "tabelaEntreVerdesTransicoes": [
          {
            "idJson": "e4a32618-59fb-4d7c-9ff5-63d16afbb344"
          }
        ],
        "grupoSemaforico": {
          "idJson": "9e494c36-72d9-485c-b69e-85ac411e912a"
        },
        "tipo": "PERDA_DE_PASSAGEM",
        "tempoAtrasoGrupo": "5",
        "atrasoDeGrupo": {
          "idJson": "479147e5-65fa-4e81-b12e-69f4b86006ff"
        }
      },
      {
        "id": "adcbadc6-c9aa-4fb2-8a69-02b6fa4012a7",
        "idJson": "9ce4768d-d453-4ac2-854e-758c9dc8d9fc",
        "origem": {
          "idJson": "fcc3c63e-cb36-4966-9407-1b8bf30c3106"
        },
        "destino": {
          "idJson": "8a1e2214-588c-465a-9e37-d838c56f2106"
        },
        "tabelaEntreVerdesTransicoes": [
          {
            "idJson": "d0449305-d544-4cd5-b0cf-5c6dba57db5b"
          }
        ],
        "grupoSemaforico": {
          "idJson": "3e22d9c8-6a98-45cc-8bb9-b4e9cfb5a696"
        },
        "tipo": "PERDA_DE_PASSAGEM",
        "tempoAtrasoGrupo": "5",
        "atrasoDeGrupo": {
          "idJson": "d6fee3eb-6ba5-4a66-b557-1b1fbf46e3a5"
        }
      },
      {
        "id": "afe2768d-2827-469b-9854-de20d55a8133",
        "idJson": "ab7e1472-5745-456b-822d-8cb04f050e6d",
        "origem": {
          "idJson": "25dd2981-084e-4b00-be77-b92052c25502"
        },
        "destino": {
          "idJson": "bd3d834a-d83c-4ac0-b52a-04c2bdbd34a7"
        },
        "tabelaEntreVerdesTransicoes": [
          {
            "idJson": "3c53b3e0-e8e8-49e3-8cca-c7c2f271c862"
          }
        ],
        "grupoSemaforico": {
          "idJson": "9e494c36-72d9-485c-b69e-85ac411e912a"
        },
        "tipo": "PERDA_DE_PASSAGEM",
        "tempoAtrasoGrupo": "5",
        "atrasoDeGrupo": {
          "idJson": "063c19fb-f778-41a6-b8f6-0740d20c0fbb"
        }
      },
      {
        "id": "65aa652c-6670-4330-bffd-6546c8eddd2b",
        "idJson": "9eed26ef-8024-42c2-8e4a-c538526a3bfd",
        "origem": {
          "idJson": "73d11d5b-7d01-455c-9764-2e222b01bc8b"
        },
        "destino": {
          "idJson": "25dd2981-084e-4b00-be77-b92052c25502"
        },
        "tabelaEntreVerdesTransicoes": [
          {
            "idJson": "44595c20-eaa3-42b6-941b-fa1f907851e8"
          }
        ],
        "grupoSemaforico": {
          "idJson": "911ebc3b-25b7-4599-8531-a2f776850b11"
        },
        "tipo": "PERDA_DE_PASSAGEM",
        "tempoAtrasoGrupo": "5",
        "atrasoDeGrupo": {
          "idJson": "da63d11d-6028-4852-99bd-2ef8f7ba3e7f"
        }
      },
      {
        "id": "8b2450ba-a6b8-461c-ad59-2e6ccd9ecf60",
        "idJson": "021d9bca-8b05-4788-88d6-4630c50f58f1",
        "origem": {
          "idJson": "bd3d834a-d83c-4ac0-b52a-04c2bdbd34a7"
        },
        "destino": {
          "idJson": "8a1e2214-588c-465a-9e37-d838c56f2106"
        },
        "tabelaEntreVerdesTransicoes": [
          {
            "idJson": "69aab84a-627b-4f9d-9361-c2ab2dd8248e"
          }
        ],
        "grupoSemaforico": {
          "idJson": "e04d3c80-06de-45c1-b635-c81ce567ac8c"
        },
        "tipo": "PERDA_DE_PASSAGEM",
        "tempoAtrasoGrupo": "5",
        "atrasoDeGrupo": {
          "idJson": "e1f85131-1cca-4111-a6cf-20fe56ce1429"
        }
      },
      {
        "id": "6352e8f6-f506-40c8-8f69-5965f4d9309b",
        "idJson": "68de146a-098d-4296-8e63-b8d4ce72638a",
        "origem": {
          "idJson": "25dd2981-084e-4b00-be77-b92052c25502"
        },
        "destino": {
          "idJson": "73d11d5b-7d01-455c-9764-2e222b01bc8b"
        },
        "tabelaEntreVerdesTransicoes": [
          {
            "idJson": "9bfeee92-c4b8-4fde-8f66-ac54db5e0296"
          }
        ],
        "grupoSemaforico": {
          "idJson": "9e494c36-72d9-485c-b69e-85ac411e912a"
        },
        "tipo": "PERDA_DE_PASSAGEM",
        "tempoAtrasoGrupo": "5",
        "atrasoDeGrupo": {
          "idJson": "143faebf-8c4a-4b4d-b897-f7cdf85dbc48"
        }
      },
      {
        "id": "c7e5dfc3-e663-4a13-8812-7dd9c6c4c26b",
        "idJson": "a0cd7c36-96bb-44e3-af19-516bf44ba5f3",
        "origem": {
          "idJson": "73d11d5b-7d01-455c-9764-2e222b01bc8b"
        },
        "destino": {
          "idJson": "8a1e2214-588c-465a-9e37-d838c56f2106"
        },
        "tabelaEntreVerdesTransicoes": [
          {
            "idJson": "81e4d2e4-499c-40a8-8686-e93693abe44a"
          }
        ],
        "grupoSemaforico": {
          "idJson": "911ebc3b-25b7-4599-8531-a2f776850b11"
        },
        "tipo": "PERDA_DE_PASSAGEM",
        "tempoAtrasoGrupo": "5",
        "atrasoDeGrupo": {
          "idJson": "60d6eb36-4d5e-44e7-9c28-d9e3ca19ad8c"
        }
      },
      {
        "id": "a2124af5-6610-474b-b644-b3b90688fbe6",
        "idJson": "5268c307-4b8e-43ef-871f-44cbad9f5a03",
        "origem": {
          "idJson": "73d11d5b-7d01-455c-9764-2e222b01bc8b"
        },
        "destino": {
          "idJson": "fcc3c63e-cb36-4966-9407-1b8bf30c3106"
        },
        "tabelaEntreVerdesTransicoes": [
          {
            "idJson": "7a50358c-4267-4e97-ac61-f8acfcc34397"
          }
        ],
        "grupoSemaforico": {
          "idJson": "911ebc3b-25b7-4599-8531-a2f776850b11"
        },
        "tipo": "PERDA_DE_PASSAGEM",
        "tempoAtrasoGrupo": "5",
        "atrasoDeGrupo": {
          "idJson": "c53c83a1-fadb-42e4-9d06-e144e5510c56"
        }
      },
      {
        "id": "a235ad33-db0d-41fb-8ab5-ddce6df4e10e",
        "idJson": "ba1d9989-daf3-46a9-978f-38c43720d7e7",
        "origem": {
          "idJson": "fcc3c63e-cb36-4966-9407-1b8bf30c3106"
        },
        "destino": {
          "idJson": "25dd2981-084e-4b00-be77-b92052c25502"
        },
        "tabelaEntreVerdesTransicoes": [
          {
            "idJson": "cd59b056-a84d-4d30-8bfc-d3d7ad285fde"
          }
        ],
        "grupoSemaforico": {
          "idJson": "3e22d9c8-6a98-45cc-8bb9-b4e9cfb5a696"
        },
        "tipo": "PERDA_DE_PASSAGEM",
        "tempoAtrasoGrupo": "5",
        "atrasoDeGrupo": {
          "idJson": "a28405dc-2ab2-463a-856b-31266b6f6582"
        }
      }
    ],
    "transicoesComGanhoDePassagem": [
      {
        "id": "0d1dbccb-88fe-4e25-8f51-1ec2bb6cfa14",
        "idJson": "3e18807f-f5da-49eb-a9a8-b8f6a8b1b45e",
        "origem": {
          "idJson": "73d11d5b-7d01-455c-9764-2e222b01bc8b"
        },
        "destino": {
          "idJson": "8a1e2214-588c-465a-9e37-d838c56f2106"
        },
        "tabelaEntreVerdesTransicoes": [],
        "grupoSemaforico": {
          "idJson": "02c45e6c-c594-40e6-b11a-a6b238db83b2"
        },
        "tipo": "GANHO_DE_PASSAGEM",
        "tempoAtrasoGrupo": "0",
        "atrasoDeGrupo": {
          "idJson": "5e7d2172-1133-4755-a24a-ae3f3df52a97"
        }
      },
      {
        "id": "aa463d7d-64ba-4503-88fe-4076ffe5b7bd",
        "idJson": "3fd7473b-f14a-4500-8cc8-cb452051c587",
        "origem": {
          "idJson": "25dd2981-084e-4b00-be77-b92052c25502"
        },
        "destino": {
          "idJson": "fcc3c63e-cb36-4966-9407-1b8bf30c3106"
        },
        "tabelaEntreVerdesTransicoes": [],
        "grupoSemaforico": {
          "idJson": "3e22d9c8-6a98-45cc-8bb9-b4e9cfb5a696"
        },
        "tipo": "GANHO_DE_PASSAGEM",
        "tempoAtrasoGrupo": "0",
        "atrasoDeGrupo": {
          "idJson": "df2ba05a-f3d8-4982-9bfa-a23d3aca3d67"
        }
      },
      {
        "id": "0f104ce0-0bdb-4dcc-aaa2-775885120f09",
        "idJson": "d85a559e-b497-4076-a001-e77815dbf9fe",
        "origem": {
          "idJson": "73d11d5b-7d01-455c-9764-2e222b01bc8b"
        },
        "destino": {
          "idJson": "25dd2981-084e-4b00-be77-b92052c25502"
        },
        "tabelaEntreVerdesTransicoes": [],
        "grupoSemaforico": {
          "idJson": "9e494c36-72d9-485c-b69e-85ac411e912a"
        },
        "tipo": "GANHO_DE_PASSAGEM",
        "tempoAtrasoGrupo": "0",
        "atrasoDeGrupo": {
          "idJson": "e95f208a-2fe4-4ae9-8579-9400517b08e1"
        }
      },
      {
        "id": "9e281b6e-2370-4f68-bcbd-98937f33e95a",
        "idJson": "59817026-e53b-42a3-9e6e-93977fa8c4e5",
        "origem": {
          "idJson": "8a1e2214-588c-465a-9e37-d838c56f2106"
        },
        "destino": {
          "idJson": "73d11d5b-7d01-455c-9764-2e222b01bc8b"
        },
        "tabelaEntreVerdesTransicoes": [],
        "grupoSemaforico": {
          "idJson": "911ebc3b-25b7-4599-8531-a2f776850b11"
        },
        "tipo": "GANHO_DE_PASSAGEM",
        "tempoAtrasoGrupo": "0",
        "atrasoDeGrupo": {
          "idJson": "18c2b66b-578d-4c5e-9ba4-dc8b1fb8f526"
        }
      },
      {
        "id": "270f6568-24d7-4466-9a8d-f9c7942d181c",
        "idJson": "f8ca96f0-f76f-48fd-b0a0-73335a39f17f",
        "origem": {
          "idJson": "bd3d834a-d83c-4ac0-b52a-04c2bdbd34a7"
        },
        "destino": {
          "idJson": "25dd2981-084e-4b00-be77-b92052c25502"
        },
        "tabelaEntreVerdesTransicoes": [],
        "grupoSemaforico": {
          "idJson": "9e494c36-72d9-485c-b69e-85ac411e912a"
        },
        "tipo": "GANHO_DE_PASSAGEM",
        "tempoAtrasoGrupo": "0",
        "atrasoDeGrupo": {
          "idJson": "8de1d5f0-727a-4293-8c82-4ebb30928fb6"
        }
      },
      {
        "id": "491f66f5-39c0-487f-b05b-d849011d99a2",
        "idJson": "9c77f27d-7a13-40a5-9371-fb75b18f0d80",
        "origem": {
          "idJson": "bd3d834a-d83c-4ac0-b52a-04c2bdbd34a7"
        },
        "destino": {
          "idJson": "73d11d5b-7d01-455c-9764-2e222b01bc8b"
        },
        "tabelaEntreVerdesTransicoes": [],
        "grupoSemaforico": {
          "idJson": "911ebc3b-25b7-4599-8531-a2f776850b11"
        },
        "tipo": "GANHO_DE_PASSAGEM",
        "tempoAtrasoGrupo": "0",
        "atrasoDeGrupo": {
          "idJson": "4a433770-bfe9-4154-bc23-ce174da513cf"
        }
      },
      {
        "id": "7d461244-d951-49fb-98ac-2422def142da",
        "idJson": "25187f82-3ab1-47b8-96a4-8f85c8bb0336",
        "origem": {
          "idJson": "fcc3c63e-cb36-4966-9407-1b8bf30c3106"
        },
        "destino": {
          "idJson": "73d11d5b-7d01-455c-9764-2e222b01bc8b"
        },
        "tabelaEntreVerdesTransicoes": [],
        "grupoSemaforico": {
          "idJson": "911ebc3b-25b7-4599-8531-a2f776850b11"
        },
        "tipo": "GANHO_DE_PASSAGEM",
        "tempoAtrasoGrupo": "0",
        "atrasoDeGrupo": {
          "idJson": "b1241cd6-20f5-4123-b18e-5abe1201a16c"
        }
      },
      {
        "id": "3216ef54-79e7-4c69-a98c-b4f0eccb091b",
        "idJson": "63ca8341-5d30-421c-b30b-fbbeeb8fb543",
        "origem": {
          "idJson": "8a1e2214-588c-465a-9e37-d838c56f2106"
        },
        "destino": {
          "idJson": "bd3d834a-d83c-4ac0-b52a-04c2bdbd34a7"
        },
        "tabelaEntreVerdesTransicoes": [],
        "grupoSemaforico": {
          "idJson": "e04d3c80-06de-45c1-b635-c81ce567ac8c"
        },
        "tipo": "GANHO_DE_PASSAGEM",
        "tempoAtrasoGrupo": "0",
        "atrasoDeGrupo": {
          "idJson": "3586b29d-82c8-4418-96de-6939cb4f2e19"
        }
      },
      {
        "id": "b63b5a69-df1c-43ab-8c85-125b79bc86cf",
        "idJson": "6d6bd531-af8e-4d57-a515-cac5509a6322",
        "origem": {
          "idJson": "73d11d5b-7d01-455c-9764-2e222b01bc8b"
        },
        "destino": {
          "idJson": "bd3d834a-d83c-4ac0-b52a-04c2bdbd34a7"
        },
        "tabelaEntreVerdesTransicoes": [],
        "grupoSemaforico": {
          "idJson": "e04d3c80-06de-45c1-b635-c81ce567ac8c"
        },
        "tipo": "GANHO_DE_PASSAGEM",
        "tempoAtrasoGrupo": "0",
        "atrasoDeGrupo": {
          "idJson": "9d92155c-37e4-4532-a243-a8281a79726a"
        }
      },
      {
        "id": "8011071d-3012-495f-881c-7d4a3ac11417",
        "idJson": "4cd3f041-188a-4bee-a8a8-12f0c5292b64",
        "origem": {
          "idJson": "73d11d5b-7d01-455c-9764-2e222b01bc8b"
        },
        "destino": {
          "idJson": "fcc3c63e-cb36-4966-9407-1b8bf30c3106"
        },
        "tabelaEntreVerdesTransicoes": [],
        "grupoSemaforico": {
          "idJson": "3e22d9c8-6a98-45cc-8bb9-b4e9cfb5a696"
        },
        "tipo": "GANHO_DE_PASSAGEM",
        "tempoAtrasoGrupo": "0",
        "atrasoDeGrupo": {
          "idJson": "08143eb2-6fc5-4af2-be1c-edd7d2b44b80"
        }
      },
      {
        "id": "2c41873a-9d25-4a2a-b143-63eacf677539",
        "idJson": "b51f4511-98fd-4cf9-95db-d0e3e3c45424",
        "origem": {
          "idJson": "25dd2981-084e-4b00-be77-b92052c25502"
        },
        "destino": {
          "idJson": "8a1e2214-588c-465a-9e37-d838c56f2106"
        },
        "tabelaEntreVerdesTransicoes": [],
        "grupoSemaforico": {
          "idJson": "02c45e6c-c594-40e6-b11a-a6b238db83b2"
        },
        "tipo": "GANHO_DE_PASSAGEM",
        "tempoAtrasoGrupo": "0",
        "atrasoDeGrupo": {
          "idJson": "8bb5a33c-1e51-45bd-b484-b2665d043380"
        }
      },
      {
        "id": "89c30244-ab54-4b38-82c6-100c649a64b8",
        "idJson": "c33018b4-307f-47ff-aeb4-530852f56461",
        "origem": {
          "idJson": "fcc3c63e-cb36-4966-9407-1b8bf30c3106"
        },
        "destino": {
          "idJson": "bd3d834a-d83c-4ac0-b52a-04c2bdbd34a7"
        },
        "tabelaEntreVerdesTransicoes": [],
        "grupoSemaforico": {
          "idJson": "e04d3c80-06de-45c1-b635-c81ce567ac8c"
        },
        "tipo": "GANHO_DE_PASSAGEM",
        "tempoAtrasoGrupo": "0",
        "atrasoDeGrupo": {
          "idJson": "9f442c61-4c5e-4798-97e9-9162808af25f"
        }
      },
      {
        "id": "70b8275b-fe59-4325-8929-d88e489f5991",
        "idJson": "57838e7e-2587-498b-a6da-54dd7bf91378",
        "origem": {
          "idJson": "fcc3c63e-cb36-4966-9407-1b8bf30c3106"
        },
        "destino": {
          "idJson": "8a1e2214-588c-465a-9e37-d838c56f2106"
        },
        "tabelaEntreVerdesTransicoes": [],
        "grupoSemaforico": {
          "idJson": "02c45e6c-c594-40e6-b11a-a6b238db83b2"
        },
        "tipo": "GANHO_DE_PASSAGEM",
        "tempoAtrasoGrupo": "0",
        "atrasoDeGrupo": {
          "idJson": "492baba5-779c-4b2f-8b94-f388dcd763b4"
        }
      },
      {
        "id": "cc3df215-1a28-46e0-99ce-cc145be03b16",
        "idJson": "682db8dc-7321-4e56-ae09-89c31d98672d",
        "origem": {
          "idJson": "bd3d834a-d83c-4ac0-b52a-04c2bdbd34a7"
        },
        "destino": {
          "idJson": "fcc3c63e-cb36-4966-9407-1b8bf30c3106"
        },
        "tabelaEntreVerdesTransicoes": [],
        "grupoSemaforico": {
          "idJson": "3e22d9c8-6a98-45cc-8bb9-b4e9cfb5a696"
        },
        "tipo": "GANHO_DE_PASSAGEM",
        "tempoAtrasoGrupo": "0",
        "atrasoDeGrupo": {
          "idJson": "ae763424-11a8-4c99-9e50-4c6c862ac633"
        }
      },
      {
        "id": "bcdaa8ff-c8c7-4829-83f2-37f42f31714b",
        "idJson": "219a3d87-b0ff-4e00-8f97-b594385308a9",
        "origem": {
          "idJson": "25dd2981-084e-4b00-be77-b92052c25502"
        },
        "destino": {
          "idJson": "73d11d5b-7d01-455c-9764-2e222b01bc8b"
        },
        "tabelaEntreVerdesTransicoes": [],
        "grupoSemaforico": {
          "idJson": "911ebc3b-25b7-4599-8531-a2f776850b11"
        },
        "tipo": "GANHO_DE_PASSAGEM",
        "tempoAtrasoGrupo": "0",
        "atrasoDeGrupo": {
          "idJson": "c70d4cd2-c892-464a-a57a-08b11677d96c"
        }
      },
      {
        "id": "e1586f68-17f2-4abe-a687-5cc8ffab102f",
        "idJson": "98b9415e-6c8f-482a-b1bc-6c77ef3a3657",
        "origem": {
          "idJson": "25dd2981-084e-4b00-be77-b92052c25502"
        },
        "destino": {
          "idJson": "bd3d834a-d83c-4ac0-b52a-04c2bdbd34a7"
        },
        "tabelaEntreVerdesTransicoes": [],
        "grupoSemaforico": {
          "idJson": "e04d3c80-06de-45c1-b635-c81ce567ac8c"
        },
        "tipo": "GANHO_DE_PASSAGEM",
        "tempoAtrasoGrupo": "0",
        "atrasoDeGrupo": {
          "idJson": "7d3b46e1-4397-4f17-a1e5-7f9421ac9d09"
        }
      },
      {
        "id": "63da3d9e-ee88-4c06-b1ea-9dcd41b4743c",
        "idJson": "4b8ca812-3aa2-42d2-83cc-e6d63333143d",
        "origem": {
          "idJson": "8a1e2214-588c-465a-9e37-d838c56f2106"
        },
        "destino": {
          "idJson": "25dd2981-084e-4b00-be77-b92052c25502"
        },
        "tabelaEntreVerdesTransicoes": [],
        "grupoSemaforico": {
          "idJson": "9e494c36-72d9-485c-b69e-85ac411e912a"
        },
        "tipo": "GANHO_DE_PASSAGEM",
        "tempoAtrasoGrupo": "0",
        "atrasoDeGrupo": {
          "idJson": "0081b2fb-9a15-402c-890c-a2d77947cbde"
        }
      },
      {
        "id": "35813ac2-e25d-4fba-8210-a231a39cee3f",
        "idJson": "fd3e05d1-6b18-4d00-98b7-3e0820105e0e",
        "origem": {
          "idJson": "bd3d834a-d83c-4ac0-b52a-04c2bdbd34a7"
        },
        "destino": {
          "idJson": "8a1e2214-588c-465a-9e37-d838c56f2106"
        },
        "tabelaEntreVerdesTransicoes": [],
        "grupoSemaforico": {
          "idJson": "02c45e6c-c594-40e6-b11a-a6b238db83b2"
        },
        "tipo": "GANHO_DE_PASSAGEM",
        "tempoAtrasoGrupo": "0",
        "atrasoDeGrupo": {
          "idJson": "32555598-915c-47d3-95e6-c13df01179f4"
        }
      },
      {
        "id": "075810c9-c867-4e15-8c1d-ecdc23b7b8a2",
        "idJson": "f64d5475-66a7-4a67-b2f9-345b1ce9ad96",
        "origem": {
          "idJson": "8a1e2214-588c-465a-9e37-d838c56f2106"
        },
        "destino": {
          "idJson": "fcc3c63e-cb36-4966-9407-1b8bf30c3106"
        },
        "tabelaEntreVerdesTransicoes": [],
        "grupoSemaforico": {
          "idJson": "3e22d9c8-6a98-45cc-8bb9-b4e9cfb5a696"
        },
        "tipo": "GANHO_DE_PASSAGEM",
        "tempoAtrasoGrupo": "0",
        "atrasoDeGrupo": {
          "idJson": "ea15b4ac-56d6-4f2e-9a0c-fbb0f52750ec"
        }
      },
      {
        "id": "5ab9e5bf-54b3-4149-b853-9d081f633a15",
        "idJson": "974fa792-8176-43d9-b27f-8588cb33ade0",
        "origem": {
          "idJson": "fcc3c63e-cb36-4966-9407-1b8bf30c3106"
        },
        "destino": {
          "idJson": "25dd2981-084e-4b00-be77-b92052c25502"
        },
        "tabelaEntreVerdesTransicoes": [],
        "grupoSemaforico": {
          "idJson": "9e494c36-72d9-485c-b69e-85ac411e912a"
        },
        "tipo": "GANHO_DE_PASSAGEM",
        "tempoAtrasoGrupo": "0",
        "atrasoDeGrupo": {
          "idJson": "56e0b3e5-c278-4804-a4cf-b4e97a58f299"
        }
      }
    ],
    "tabelasEntreVerdes": [
      {
        "id": "86f3234c-97e6-4781-a8eb-6b9efd4d6a5e",
        "idJson": "80024360-e2de-4c19-8c43-9d1fc85b016b",
        "descricao": "PADRÃO",
        "posicao": 1,
        "grupoSemaforico": {
          "idJson": "9e494c36-72d9-485c-b69e-85ac411e912a"
        },
        "tabelaEntreVerdesTransicoes": [
          {
            "idJson": "e4a32618-59fb-4d7c-9ff5-63d16afbb344"
          },
          {
            "idJson": "9bfeee92-c4b8-4fde-8f66-ac54db5e0296"
          },
          {
            "idJson": "3c53b3e0-e8e8-49e3-8cca-c7c2f271c862"
          },
          {
            "idJson": "809f84aa-b665-4911-8b88-416f5e678df2"
          }
        ]
      },
      {
        "id": "cc1c9ada-75c1-4a3c-9227-83280cafef7f",
        "idJson": "2364aeba-1c75-4080-a1d1-5fef25cf2d10",
        "descricao": "PADRÃO",
        "posicao": 1,
        "grupoSemaforico": {
          "idJson": "3e22d9c8-6a98-45cc-8bb9-b4e9cfb5a696"
        },
        "tabelaEntreVerdesTransicoes": [
          {
            "idJson": "d0449305-d544-4cd5-b0cf-5c6dba57db5b"
          },
          {
            "idJson": "f3e81596-32e4-476d-8c7f-a92dd9497c02"
          },
          {
            "idJson": "fd502b39-5d1c-42ba-80e3-2e5c16db25f4"
          },
          {
            "idJson": "cd59b056-a84d-4d30-8bfc-d3d7ad285fde"
          }
        ]
      },
      {
        "id": "d4580a9f-f8e5-41c5-8136-fc82f76bd88a",
        "idJson": "5a1575f0-22ef-49b9-be73-63b8bc8f34f0",
        "descricao": "PADRÃO",
        "posicao": 1,
        "grupoSemaforico": {
          "idJson": "e04d3c80-06de-45c1-b635-c81ce567ac8c"
        },
        "tabelaEntreVerdesTransicoes": [
          {
            "idJson": "69aab84a-627b-4f9d-9361-c2ab2dd8248e"
          },
          {
            "idJson": "17cd61bc-1762-4079-9d09-e60a48ffabc1"
          },
          {
            "idJson": "52a431c2-de52-4236-a4a0-8d3347c06ab0"
          },
          {
            "idJson": "9cbacbf5-99c4-4f3a-8f38-4a92cc3a724a"
          }
        ]
      },
      {
        "id": "4c684983-ff09-432a-baf9-1f1bdd8d82ab",
        "idJson": "4de0a2ed-0b01-45ee-bedf-ad211edb7d1d",
        "descricao": "PADRÃO",
        "posicao": 1,
        "grupoSemaforico": {
          "idJson": "911ebc3b-25b7-4599-8531-a2f776850b11"
        },
        "tabelaEntreVerdesTransicoes": [
          {
            "idJson": "5ecc43ff-93b0-447f-abde-c48f161614ca"
          },
          {
            "idJson": "44595c20-eaa3-42b6-941b-fa1f907851e8"
          },
          {
            "idJson": "7a50358c-4267-4e97-ac61-f8acfcc34397"
          },
          {
            "idJson": "81e4d2e4-499c-40a8-8686-e93693abe44a"
          }
        ],
        "$$hashKey": "object:286"
      },
      {
        "id": "4bab7d19-f4a4-41f9-bdfb-1bd8e4084167",
        "idJson": "2a9e5868-be11-4c65-b093-bf05fdf911e1",
        "descricao": "PADRÃO",
        "posicao": 1,
        "grupoSemaforico": {
          "idJson": "02c45e6c-c594-40e6-b11a-a6b238db83b2"
        },
        "tabelaEntreVerdesTransicoes": [
          {
            "idJson": "a516983d-e862-4f51-a4f5-e48c33d40bb3"
          },
          {
            "idJson": "9064e375-d024-42c5-a244-7ff0771cefc0"
          },
          {
            "idJson": "81f0022c-68b0-4732-8a96-b5da38d8dab6"
          },
          {
            "idJson": "ecc9fc17-1a6a-4826-97d5-80ea710e8944"
          }
        ]
      }
    ],
    "tabelasEntreVerdesTransicoes": [
      {
        "id": "e2753b5d-34b1-4dd1-b64d-0625c2781fc2",
        "idJson": "9cbacbf5-99c4-4f3a-8f38-4a92cc3a724a",
        "tempoAmarelo": "3",
        "tempoVermelhoLimpeza": "3",
        "tempoAtrasoGrupo": "5",
        "tabelaEntreVerdes": {
          "idJson": "5a1575f0-22ef-49b9-be73-63b8bc8f34f0"
        },
        "transicao": {
          "idJson": "0b9d7813-d6d0-4d44-8e86-2c1d3cbba0e2"
        }
      },
      {
        "id": "93d602d5-762e-4bb6-a4aa-c6343bc93c72",
        "idJson": "7a50358c-4267-4e97-ac61-f8acfcc34397",
        "tempoAmarelo": "3",
        "tempoVermelhoLimpeza": "3",
        "tempoAtrasoGrupo": "5",
        "tabelaEntreVerdes": {
          "idJson": "4de0a2ed-0b01-45ee-bedf-ad211edb7d1d"
        },
        "transicao": {
          "idJson": "5268c307-4b8e-43ef-871f-44cbad9f5a03"
        }
      },
      {
        "id": "aa7e23e7-5b1b-49a7-be40-146f0b551767",
        "idJson": "17cd61bc-1762-4079-9d09-e60a48ffabc1",
        "tempoAmarelo": "3",
        "tempoVermelhoLimpeza": "3",
        "tempoAtrasoGrupo": "5",
        "tabelaEntreVerdes": {
          "idJson": "5a1575f0-22ef-49b9-be73-63b8bc8f34f0"
        },
        "transicao": {
          "idJson": "f7024646-e09a-4a20-b0e6-dc6a5be3a1ad"
        }
      },
      {
        "id": "ae6473fd-49c7-470b-bd30-71621c520da6",
        "idJson": "809f84aa-b665-4911-8b88-416f5e678df2",
        "tempoAmarelo": "3",
        "tempoVermelhoLimpeza": "3",
        "tempoAtrasoGrupo": "5",
        "tabelaEntreVerdes": {
          "idJson": "80024360-e2de-4c19-8c43-9d1fc85b016b"
        },
        "transicao": {
          "idJson": "c04f6293-e686-4081-95a8-9c9264cb0476"
        }
      },
      {
        "id": "b4d2a330-94e4-4f63-a5cf-8fc0f9a38196",
        "idJson": "9064e375-d024-42c5-a244-7ff0771cefc0",
        "tempoAmarelo": "3",
        "tempoVermelhoLimpeza": "3",
        "tempoAtrasoGrupo": "5",
        "tabelaEntreVerdes": {
          "idJson": "2a9e5868-be11-4c65-b093-bf05fdf911e1"
        },
        "transicao": {
          "idJson": "9bfae4e3-31e9-4009-be79-18325bb1a58e"
        }
      },
      {
        "id": "4f26ebdc-683a-4adc-88aa-2f7a1c401f4c",
        "idJson": "f3e81596-32e4-476d-8c7f-a92dd9497c02",
        "tempoAmarelo": "3",
        "tempoVermelhoLimpeza": "3",
        "tempoAtrasoGrupo": "5",
        "tabelaEntreVerdes": {
          "idJson": "2364aeba-1c75-4080-a1d1-5fef25cf2d10"
        },
        "transicao": {
          "idJson": "b0b5dbd7-7310-4130-9879-cd33b30770ab"
        }
      },
      {
        "id": "d988f644-345a-4dae-9155-d8cf310d2899",
        "idJson": "52a431c2-de52-4236-a4a0-8d3347c06ab0",
        "tempoAmarelo": "3",
        "tempoVermelhoLimpeza": "3",
        "tempoAtrasoGrupo": "5",
        "tabelaEntreVerdes": {
          "idJson": "5a1575f0-22ef-49b9-be73-63b8bc8f34f0"
        },
        "transicao": {
          "idJson": "e49ec9b7-d2dd-4196-add1-0a0d57909b4d"
        }
      },
      {
        "id": "fb29ed19-aa28-4378-a086-98747b7912ad",
        "idJson": "ecc9fc17-1a6a-4826-97d5-80ea710e8944",
        "tempoAmarelo": "3",
        "tempoVermelhoLimpeza": "3",
        "tempoAtrasoGrupo": "5",
        "tabelaEntreVerdes": {
          "idJson": "2a9e5868-be11-4c65-b093-bf05fdf911e1"
        },
        "transicao": {
          "idJson": "b42ce9f6-b0e2-44ea-8efc-d873178a7851"
        }
      },
      {
        "id": "28b42f14-3cb2-4498-9551-9dfd81b6f7cd",
        "idJson": "9bfeee92-c4b8-4fde-8f66-ac54db5e0296",
        "tempoAmarelo": "3",
        "tempoVermelhoLimpeza": "3",
        "tempoAtrasoGrupo": "5",
        "tabelaEntreVerdes": {
          "idJson": "80024360-e2de-4c19-8c43-9d1fc85b016b"
        },
        "transicao": {
          "idJson": "68de146a-098d-4296-8e63-b8d4ce72638a"
        }
      },
      {
        "id": "51d8ed63-b5bc-47e4-9ab1-bf274d0b888b",
        "idJson": "fd502b39-5d1c-42ba-80e3-2e5c16db25f4",
        "tempoAmarelo": "3",
        "tempoVermelhoLimpeza": "3",
        "tempoAtrasoGrupo": "5",
        "tabelaEntreVerdes": {
          "idJson": "2364aeba-1c75-4080-a1d1-5fef25cf2d10"
        },
        "transicao": {
          "idJson": "f955e99c-228c-4b0e-b52e-0eb82457544c"
        }
      },
      {
        "id": "dc7bbe9b-b36b-4af7-85e0-1d4165ccaa29",
        "idJson": "81f0022c-68b0-4732-8a96-b5da38d8dab6",
        "tempoAmarelo": "3",
        "tempoVermelhoLimpeza": "3",
        "tempoAtrasoGrupo": "5",
        "tabelaEntreVerdes": {
          "idJson": "2a9e5868-be11-4c65-b093-bf05fdf911e1"
        },
        "transicao": {
          "idJson": "7702a320-877a-4e97-b099-3340a8a6e1e9"
        }
      },
      {
        "id": "39e91279-14bd-4b93-bdc7-f2cc573a88ed",
        "idJson": "d0449305-d544-4cd5-b0cf-5c6dba57db5b",
        "tempoAmarelo": "3",
        "tempoVermelhoLimpeza": "3",
        "tempoAtrasoGrupo": "5",
        "tabelaEntreVerdes": {
          "idJson": "2364aeba-1c75-4080-a1d1-5fef25cf2d10"
        },
        "transicao": {
          "idJson": "9ce4768d-d453-4ac2-854e-758c9dc8d9fc"
        }
      },
      {
        "id": "067ecb7b-cff4-449c-9bdf-f5b85ed13a74",
        "idJson": "e4a32618-59fb-4d7c-9ff5-63d16afbb344",
        "tempoAmarelo": "3",
        "tempoVermelhoLimpeza": "3",
        "tempoAtrasoGrupo": "5",
        "tabelaEntreVerdes": {
          "idJson": "80024360-e2de-4c19-8c43-9d1fc85b016b"
        },
        "transicao": {
          "idJson": "27db0977-2f35-490e-bac6-3abc9b541913"
        }
      },
      {
        "id": "a028e281-1df4-434b-850f-63f289c9398d",
        "idJson": "cd59b056-a84d-4d30-8bfc-d3d7ad285fde",
        "tempoAmarelo": "3",
        "tempoVermelhoLimpeza": "3",
        "tempoAtrasoGrupo": "5",
        "tabelaEntreVerdes": {
          "idJson": "2364aeba-1c75-4080-a1d1-5fef25cf2d10"
        },
        "transicao": {
          "idJson": "ba1d9989-daf3-46a9-978f-38c43720d7e7"
        }
      },
      {
        "id": "88ba84b9-074c-42e5-a8d2-da5e1285b3a5",
        "idJson": "44595c20-eaa3-42b6-941b-fa1f907851e8",
        "tempoAmarelo": "3",
        "tempoVermelhoLimpeza": "3",
        "tempoAtrasoGrupo": "5",
        "tabelaEntreVerdes": {
          "idJson": "4de0a2ed-0b01-45ee-bedf-ad211edb7d1d"
        },
        "transicao": {
          "idJson": "9eed26ef-8024-42c2-8e4a-c538526a3bfd"
        }
      },
      {
        "id": "2e24d690-a9e7-461b-afd7-cb5398b2555d",
        "idJson": "69aab84a-627b-4f9d-9361-c2ab2dd8248e",
        "tempoAmarelo": "3",
        "tempoVermelhoLimpeza": "3",
        "tempoAtrasoGrupo": "5",
        "tabelaEntreVerdes": {
          "idJson": "5a1575f0-22ef-49b9-be73-63b8bc8f34f0"
        },
        "transicao": {
          "idJson": "021d9bca-8b05-4788-88d6-4630c50f58f1"
        }
      },
      {
        "id": "7f27fa00-4315-42de-9eb8-6154e748b7a2",
        "idJson": "a516983d-e862-4f51-a4f5-e48c33d40bb3",
        "tempoAmarelo": "3",
        "tempoVermelhoLimpeza": "3",
        "tempoAtrasoGrupo": "5",
        "tabelaEntreVerdes": {
          "idJson": "2a9e5868-be11-4c65-b093-bf05fdf911e1"
        },
        "transicao": {
          "idJson": "b1204656-0e33-497c-b4b8-f2ce21f992b8"
        }
      },
      {
        "id": "04b8f0d1-ce5f-4492-9c75-dadb348c2dec",
        "idJson": "5ecc43ff-93b0-447f-abde-c48f161614ca",
        "tempoAmarelo": "3",
        "tempoVermelhoLimpeza": "3",
        "tempoAtrasoGrupo": "5",
        "tabelaEntreVerdes": {
          "idJson": "4de0a2ed-0b01-45ee-bedf-ad211edb7d1d"
        },
        "transicao": {
          "idJson": "465962e3-ef1d-4112-92d3-075a52f51d78"
        }
      },
      {
        "id": "d679e4cc-e42e-4477-8db1-131457411086",
        "idJson": "81e4d2e4-499c-40a8-8686-e93693abe44a",
        "tempoAmarelo": "3",
        "tempoVermelhoLimpeza": "3",
        "tempoAtrasoGrupo": "5",
        "tabelaEntreVerdes": {
          "idJson": "4de0a2ed-0b01-45ee-bedf-ad211edb7d1d"
        },
        "transicao": {
          "idJson": "a0cd7c36-96bb-44e3-af19-516bf44ba5f3"
        }
      },
      {
        "id": "3e06cd90-a5c6-4811-a9b1-ddacb47bbadc",
        "idJson": "3c53b3e0-e8e8-49e3-8cca-c7c2f271c862",
        "tempoAmarelo": "3",
        "tempoVermelhoLimpeza": "3",
        "tempoAtrasoGrupo": "5",
        "tabelaEntreVerdes": {
          "idJson": "80024360-e2de-4c19-8c43-9d1fc85b016b"
        },
        "transicao": {
          "idJson": "ab7e1472-5745-456b-822d-8cb04f050e6d"
        }
      }
    ],
    "planos": [
      {
        "idJson": "d89e7a44-dfc5-47a7-bdde-11820f06749f",
        "anel": {
          "idJson": "c6787208-ede2-4b5f-b857-efa5ad97f0ae"
        },
        "descricao": "Exclusivo",
        "posicao": 0,
        "modoOperacao": "MANUAL",
        "posicaoTabelaEntreVerde": 1,
        "gruposSemaforicosPlanos": [
          {
            "idJson": "9f80092a-a27a-4f5a-ade5-45a2e7602e4f"
          },
          {
            "idJson": "369059a6-69ff-46bb-95e5-7bf8d5d4409e"
          },
          {
            "idJson": "1c1e3764-b093-4cf8-8133-455783e4e888"
          },
          {
            "idJson": "cc0b887c-e792-4fdc-b3e9-597bc7077e9d"
          },
          {
            "idJson": "794d9e75-1486-4e90-b9c6-545c71239623"
          }
        ],
        "estagiosPlanos": [
          {
            "idJson": "718dee9c-c8fe-4012-8610-802f27460abf"
          },
          {
            "idJson": "1be34f39-e636-47d2-9692-f4bde80618a3"
          },
          {
            "idJson": "0435df12-657d-4f78-b3ad-e71ba92d21d7"
          },
          {
            "idJson": "0825cbed-cfa6-4d1a-9246-f02a8b84f81a"
          },
          {
            "idJson": "85dbef47-8693-4aea-ab92-a2983cb06302"
          }
        ],
        "tempoCiclo": 30,
        "configurado": false,
        "versaoPlano": {
          "idJson": "8005179f-4974-454e-a900-cdcf3bd0c58f"
        },
        "manualExclusivo": true
      },
      {
        "idJson": "bd7ba623-c2e7-49ee-a306-4353f5513a7c",
        "anel": {
          "idJson": "c6787208-ede2-4b5f-b857-efa5ad97f0ae"
        },
        "descricao": "PLANO 1",
        "posicao": 1,
        "modoOperacao": "TEMPO_FIXO_ISOLADO",
        "posicaoTabelaEntreVerde": 1,
        "gruposSemaforicosPlanos": [
          {
            "idJson": "d8c5477b-bf0e-47f2-94ee-46329559ebc0"
          },
          {
            "idJson": "8bed2d5d-fc20-4829-b744-9548927274f7"
          },
          {
            "idJson": "fe6bfc32-ebd1-4393-9d38-5d27db295867"
          },
          {
            "idJson": "aa2728a2-71e5-4ff1-9ab4-9bc43622a6b6"
          },
          {
            "idJson": "28188470-e640-43ad-9d8e-2b992d9bda08"
          }
        ],
        "estagiosPlanos": [
          {
            "idJson": "02784cd1-5ab1-429d-8312-042eada4de94"
          },
          {
            "idJson": "8578bf1a-c226-45ae-8c85-52a4a50a7fa3"
          },
          {
            "idJson": "8072c59c-773e-4904-adab-90a2bb1c0b5f"
          },
          {
            "idJson": "2f8d3167-3376-40e2-a3bd-073000c9c4c2"
          },
          {
            "idJson": "e2a53443-8072-429f-b31d-8e712ff2b471"
          }
        ],
        "tempoCiclo": 100,
        "configurado": true,
        "versaoPlano": {
          "idJson": "8005179f-4974-454e-a900-cdcf3bd0c58f"
        }
      },
      {
        "idJson": "216d0496-f7b6-4038-95b7-1975218e050a",
        "anel": {
          "idJson": "c6787208-ede2-4b5f-b857-efa5ad97f0ae"
        },
        "descricao": "PLANO 2",
        "posicao": 2,
        "modoOperacao": "TEMPO_FIXO_ISOLADO",
        "posicaoTabelaEntreVerde": 1,
        "gruposSemaforicosPlanos": [
          {
            "idJson": "91ed7599-15e4-4660-828b-9ee921a8b30e"
          },
          {
            "idJson": "8cecf451-b01c-4ced-9d0c-826fb53f3fd7"
          },
          {
            "idJson": "44dca8cb-700c-48aa-bfb7-987d543d6493"
          },
          {
            "idJson": "c5a285e9-40c0-49f3-8cb8-582aebaec570"
          },
          {
            "idJson": "3b3a1aa1-b5c6-4819-8df0-879430f6de04"
          }
        ],
        "estagiosPlanos": [
          {
            "idJson": "e232bc1f-67bd-43cf-897e-dc08c329018c"
          },
          {
            "idJson": "af435d08-f043-445e-8cae-732d5b0f17be"
          },
          {
            "idJson": "e32f6a3d-bfed-4100-9da9-edaf46515896"
          },
          {
            "idJson": "b590187f-efda-4986-8aa7-678097997fe7"
          },
          {
            "idJson": "e1fe0e3d-1c72-4561-98e1-e3b727801e37"
          }
        ],
        "tempoCiclo": 30,
        "configurado": false,
        "versaoPlano": {
          "idJson": "8005179f-4974-454e-a900-cdcf3bd0c58f"
        }
      },
      {
        "idJson": "986d4974-baad-4c9d-ad93-ec9a23a982ef",
        "anel": {
          "idJson": "c6787208-ede2-4b5f-b857-efa5ad97f0ae"
        },
        "descricao": "PLANO 3",
        "posicao": 3,
        "modoOperacao": "TEMPO_FIXO_ISOLADO",
        "posicaoTabelaEntreVerde": 1,
        "gruposSemaforicosPlanos": [
          {
            "idJson": "ffde822e-c03f-41ec-9271-c094ee3e0239"
          },
          {
            "idJson": "869addf9-e54f-41fe-84c7-1577e2ccd151"
          },
          {
            "idJson": "2660ba06-9cf2-4bd8-8723-3834f417ddc7"
          },
          {
            "idJson": "3669063b-f2dc-4c38-b29e-9c2ec230f483"
          },
          {
            "idJson": "25508f7c-09e7-4961-b1a8-169f112f58f6"
          }
        ],
        "estagiosPlanos": [
          {
            "idJson": "7c88c6cf-758c-4450-85da-3871337ce801"
          },
          {
            "idJson": "d240b912-529c-4152-a132-95ed10444b2b"
          },
          {
            "idJson": "4f980485-763a-41a4-b128-28972d5faaf6"
          },
          {
            "idJson": "b9ef614a-7fd2-496a-ad85-c99a9be9799a"
          },
          {
            "idJson": "bf3d7fee-5210-48cd-9bfa-a025a860f09e"
          }
        ],
        "tempoCiclo": 30,
        "configurado": false,
        "versaoPlano": {
          "idJson": "8005179f-4974-454e-a900-cdcf3bd0c58f"
        }
      },
      {
        "idJson": "f9ecbfed-2372-4a5c-a3bf-555dde6bdec5",
        "anel": {
          "idJson": "c6787208-ede2-4b5f-b857-efa5ad97f0ae"
        },
        "descricao": "PLANO 4",
        "posicao": 4,
        "modoOperacao": "TEMPO_FIXO_ISOLADO",
        "posicaoTabelaEntreVerde": 1,
        "gruposSemaforicosPlanos": [
          {
            "idJson": "22ceeed8-c8cf-4448-8bbe-9967ca49f521"
          },
          {
            "idJson": "f3b0b32b-5978-4a54-9a6f-f0c4ff6e544c"
          },
          {
            "idJson": "b6e425ee-2241-4f62-a931-401458f0a9d8"
          },
          {
            "idJson": "f7ab9a7b-3907-40ef-ae86-423cf8912129"
          },
          {
            "idJson": "40dd97ec-cb54-416c-9b89-6f725dc35d10"
          }
        ],
        "estagiosPlanos": [
          {
            "idJson": "1fae84bd-2c25-497d-ae49-dbc75c199344"
          },
          {
            "idJson": "a9376ae1-d39d-4417-9a87-d826e64339d8"
          },
          {
            "idJson": "7bf9c4dc-d6ec-46eb-a0c0-4baf701289a6"
          },
          {
            "idJson": "034a0f40-58c9-4c0c-aff6-c187a3031138"
          },
          {
            "idJson": "a03f4b95-ed63-4188-af9b-bbe0affea532"
          }
        ],
        "tempoCiclo": 30,
        "configurado": false,
        "versaoPlano": {
          "idJson": "8005179f-4974-454e-a900-cdcf3bd0c58f"
        }
      },
      {
        "idJson": "4e3c0565-954f-4586-bda1-7fc7a89dcdfd",
        "anel": {
          "idJson": "c6787208-ede2-4b5f-b857-efa5ad97f0ae"
        },
        "descricao": "PLANO 5",
        "posicao": 5,
        "modoOperacao": "TEMPO_FIXO_ISOLADO",
        "posicaoTabelaEntreVerde": 1,
        "gruposSemaforicosPlanos": [
          {
            "idJson": "50718f50-8680-496e-9c5b-f4864bb4b3c6"
          },
          {
            "idJson": "34b04967-3094-482f-9004-e4053bbfca26"
          },
          {
            "idJson": "aeb96a3b-67ff-4f1c-8cbf-2213b81f182e"
          },
          {
            "idJson": "86ca5433-440a-46cf-ac9c-2e98a86bf6ee"
          },
          {
            "idJson": "393fcf23-3f39-4e92-9084-e12d89fdb899"
          }
        ],
        "estagiosPlanos": [
          {
            "idJson": "f44c6c0f-5bd3-412e-b324-667faadc5397"
          },
          {
            "idJson": "69236cf2-73fa-4e84-85b6-088855029592"
          },
          {
            "idJson": "d9ea17d8-0541-421a-bca9-1a485dc0f8b7"
          },
          {
            "idJson": "0e24cf0b-9f93-424b-8cd7-318a4c055c0f"
          },
          {
            "idJson": "0943e20f-0b6a-4d08-bfc7-aa8be7c1dfc5"
          }
        ],
        "tempoCiclo": 30,
        "configurado": false,
        "versaoPlano": {
          "idJson": "8005179f-4974-454e-a900-cdcf3bd0c58f"
        }
      },
      {
        "idJson": "b80ebfe7-fee4-477f-be85-f7cdbf6b15c0",
        "anel": {
          "idJson": "c6787208-ede2-4b5f-b857-efa5ad97f0ae"
        },
        "descricao": "PLANO 6",
        "posicao": 6,
        "modoOperacao": "TEMPO_FIXO_ISOLADO",
        "posicaoTabelaEntreVerde": 1,
        "gruposSemaforicosPlanos": [
          {
            "idJson": "eb5aceaa-3e4f-4805-bc6a-d2a2c0abd5c6"
          },
          {
            "idJson": "9166b3b6-e4ac-4f08-971c-023fd45a1f36"
          },
          {
            "idJson": "b09d18f7-feca-42c1-8c33-b0ca0241b702"
          },
          {
            "idJson": "9bd0316f-53bd-401b-a985-fc9696aa27c5"
          },
          {
            "idJson": "da4f3e1b-1272-48eb-b40d-cc04baebebab"
          }
        ],
        "estagiosPlanos": [
          {
            "idJson": "34a16448-82f0-4bd0-9c9b-672d5b4bc62c"
          },
          {
            "idJson": "8d1c63e1-e236-43ea-9b3f-de41c06d6fb2"
          },
          {
            "idJson": "d4484d46-479a-4897-82e9-7667d9f57312"
          },
          {
            "idJson": "93e6d5e1-e80c-42bc-a8c3-4d2b07a599b2"
          },
          {
            "idJson": "14db8e18-978c-4520-a9cb-1c4365452f9f"
          }
        ],
        "tempoCiclo": 30,
        "configurado": false,
        "versaoPlano": {
          "idJson": "8005179f-4974-454e-a900-cdcf3bd0c58f"
        }
      },
      {
        "idJson": "179a236e-119f-4e47-95cf-1e8c8988c265",
        "anel": {
          "idJson": "c6787208-ede2-4b5f-b857-efa5ad97f0ae"
        },
        "descricao": "PLANO 7",
        "posicao": 7,
        "modoOperacao": "TEMPO_FIXO_ISOLADO",
        "posicaoTabelaEntreVerde": 1,
        "gruposSemaforicosPlanos": [
          {
            "idJson": "57686ccc-316e-4bc4-8b8c-cade3c1cb323"
          },
          {
            "idJson": "99ef4549-4943-475f-ad44-d003089af891"
          },
          {
            "idJson": "dd98f0dd-ef7a-4f9f-9ed4-f04470e9f663"
          },
          {
            "idJson": "631a022f-54a2-4151-b227-e6347517776e"
          },
          {
            "idJson": "8d304413-7282-413e-915d-7157cfea6e3a"
          }
        ],
        "estagiosPlanos": [
          {
            "idJson": "d8089bf1-8605-46ed-8c1a-60af0e521145"
          },
          {
            "idJson": "c945db00-3d0a-49e1-956f-c7cfd6998cd3"
          },
          {
            "idJson": "2a4594e0-7809-4ae3-a646-9554a867cf2e"
          },
          {
            "idJson": "804de185-09de-478a-a475-da1b1110bd0d"
          },
          {
            "idJson": "57aa4c80-32af-4293-b2d5-504242fc162b"
          }
        ],
        "tempoCiclo": 30,
        "configurado": false,
        "versaoPlano": {
          "idJson": "8005179f-4974-454e-a900-cdcf3bd0c58f"
        }
      },
      {
        "idJson": "aa49b13d-e7c0-4c65-8985-9f371dd53bfd",
        "anel": {
          "idJson": "c6787208-ede2-4b5f-b857-efa5ad97f0ae"
        },
        "descricao": "PLANO 8",
        "posicao": 8,
        "modoOperacao": "TEMPO_FIXO_ISOLADO",
        "posicaoTabelaEntreVerde": 1,
        "gruposSemaforicosPlanos": [
          {
            "idJson": "a288f728-b9b7-45b5-8d52-b8516a5cde26"
          },
          {
            "idJson": "92ac501c-b756-47e1-bebb-c2d53f3c9542"
          },
          {
            "idJson": "e9e7c011-5937-41ef-ba12-20998afcfa44"
          },
          {
            "idJson": "50bd3034-f3ad-4e27-8a44-945f6db841b4"
          },
          {
            "idJson": "9adcd6b5-3e3f-4710-980e-5565d63e8a6b"
          }
        ],
        "estagiosPlanos": [
          {
            "idJson": "5b453258-9c0d-4cbe-8a1d-4cc9f74a03c1"
          },
          {
            "idJson": "67d80596-9736-4056-8ba5-820777c03de5"
          },
          {
            "idJson": "36f931e4-622b-4598-b5c8-90ec4f54421f"
          },
          {
            "idJson": "c4bbb3a0-7e23-40b2-9bdb-4814ba78dabc"
          },
          {
            "idJson": "15c2f48e-3cc9-4280-b1d7-f7b399049262"
          }
        ],
        "tempoCiclo": 30,
        "configurado": false,
        "versaoPlano": {
          "idJson": "8005179f-4974-454e-a900-cdcf3bd0c58f"
        }
      },
      {
        "idJson": "0ef18b30-6185-4af9-be67-4382bb721c14",
        "anel": {
          "idJson": "c6787208-ede2-4b5f-b857-efa5ad97f0ae"
        },
        "descricao": "PLANO 9",
        "posicao": 9,
        "modoOperacao": "TEMPO_FIXO_ISOLADO",
        "posicaoTabelaEntreVerde": 1,
        "gruposSemaforicosPlanos": [
          {
            "idJson": "c5c367e3-2fbc-4c6a-88b4-570224b1fb63"
          },
          {
            "idJson": "02cb17a2-4438-409b-ab64-d6ea4469afc5"
          },
          {
            "idJson": "071b53b9-b8a7-4b6b-9708-30b5abb07c08"
          },
          {
            "idJson": "43111abd-8fd9-4b1d-8733-11613431e84b"
          },
          {
            "idJson": "47c73ef2-9ef2-4a90-bb16-c762ca400610"
          }
        ],
        "estagiosPlanos": [
          {
            "idJson": "22eea508-b1d1-4160-9206-bf1a0e189e06"
          },
          {
            "idJson": "774d7c93-db3b-42bb-884a-49c813a9deb6"
          },
          {
            "idJson": "42298636-8441-44db-a813-a8e393c1eefe"
          },
          {
            "idJson": "86520062-84a8-4e9a-b4af-ffb1ac25c66b"
          },
          {
            "idJson": "100fc407-9087-4be8-981f-ed231b49791b"
          }
        ],
        "tempoCiclo": 30,
        "configurado": false,
        "versaoPlano": {
          "idJson": "8005179f-4974-454e-a900-cdcf3bd0c58f"
        }
      },
      {
        "idJson": "3bb2b8b8-450f-40ae-a31b-096d327bade6",
        "anel": {
          "idJson": "c6787208-ede2-4b5f-b857-efa5ad97f0ae"
        },
        "descricao": "PLANO 10",
        "posicao": 10,
        "modoOperacao": "TEMPO_FIXO_ISOLADO",
        "posicaoTabelaEntreVerde": 1,
        "gruposSemaforicosPlanos": [
          {
            "idJson": "49b94a9c-a982-4387-9d9c-9142f1582743"
          },
          {
            "idJson": "862cdf5d-9f28-4e2b-96ef-18d93b2a9dba"
          },
          {
            "idJson": "b9ca091a-dc34-42fd-a5a7-4551cc52d35b"
          },
          {
            "idJson": "0ac7fe2e-fc31-4761-ac2d-d50b1b0dc299"
          },
          {
            "idJson": "9431707e-22e5-4d79-b3b7-1eb43cbafd7f"
          }
        ],
        "estagiosPlanos": [
          {
            "idJson": "f16163b1-b738-4836-988c-dc2151216f4e"
          },
          {
            "idJson": "31b40527-6843-4608-b563-50f09fd10009"
          },
          {
            "idJson": "e0468356-9241-467f-a82f-cb9e759ccfd3"
          },
          {
            "idJson": "8946b6a4-7e49-41e2-a974-22f391e1b81f"
          },
          {
            "idJson": "9ca5b951-6fb4-4fe6-8ea0-94f3b927f5a0"
          }
        ],
        "tempoCiclo": 30,
        "configurado": false,
        "versaoPlano": {
          "idJson": "8005179f-4974-454e-a900-cdcf3bd0c58f"
        }
      },
      {
        "idJson": "9bd9461c-7792-4aa2-a8a6-805945607a8d",
        "anel": {
          "idJson": "c6787208-ede2-4b5f-b857-efa5ad97f0ae"
        },
        "descricao": "PLANO 11",
        "posicao": 11,
        "modoOperacao": "TEMPO_FIXO_ISOLADO",
        "posicaoTabelaEntreVerde": 1,
        "gruposSemaforicosPlanos": [
          {
            "idJson": "5da0c9e4-3d98-487a-9549-ba8b8f2400b9"
          },
          {
            "idJson": "c85be328-510c-4c0d-9b2a-94dfa8b9d780"
          },
          {
            "idJson": "f4ad8ded-8e3e-48e3-ac2a-72e91911c299"
          },
          {
            "idJson": "9741db28-1d28-4676-9fbb-7d22e57feacc"
          },
          {
            "idJson": "b7b4c17d-c92d-48ab-ace9-0f4fb551f658"
          }
        ],
        "estagiosPlanos": [
          {
            "idJson": "88c91d69-e6a6-4c59-90d2-81b355a852d1"
          },
          {
            "idJson": "59655463-4847-4cd0-937d-2f6f9d159c78"
          },
          {
            "idJson": "1f28795d-2517-4362-a7d7-0196d0c21ff2"
          },
          {
            "idJson": "105060b9-04ab-46d4-980a-11d100dacc76"
          },
          {
            "idJson": "9c90f3ca-784e-4d26-be11-1451ee30b8ca"
          }
        ],
        "tempoCiclo": 30,
        "configurado": false,
        "versaoPlano": {
          "idJson": "8005179f-4974-454e-a900-cdcf3bd0c58f"
        }
      },
      {
        "idJson": "9a13a395-cc26-4210-a84a-cc950a69ac4c",
        "anel": {
          "idJson": "c6787208-ede2-4b5f-b857-efa5ad97f0ae"
        },
        "descricao": "PLANO 12",
        "posicao": 12,
        "modoOperacao": "TEMPO_FIXO_ISOLADO",
        "posicaoTabelaEntreVerde": 1,
        "gruposSemaforicosPlanos": [
          {
            "idJson": "574b5d6d-ad63-4299-9534-6d0dfa64b662"
          },
          {
            "idJson": "7ddf9057-caab-4aa5-8222-f2489cda764c"
          },
          {
            "idJson": "4fffae1d-e0db-4902-959d-dcd6896a6c30"
          },
          {
            "idJson": "3906ce4a-bf25-4db6-a13a-6022c04c97d2"
          },
          {
            "idJson": "dcbc4d98-e994-4af0-882e-1650585d8809"
          }
        ],
        "estagiosPlanos": [
          {
            "idJson": "7b648c8d-1a00-41f0-b382-9cdbe45057f2"
          },
          {
            "idJson": "8dfd44d9-10b2-4813-8881-9ae72c71ef8d"
          },
          {
            "idJson": "2daae396-dd34-4e78-8608-b509bd71f610"
          },
          {
            "idJson": "466bc3ae-833e-4687-b878-972b8df375cb"
          },
          {
            "idJson": "efb73e51-69bc-4be3-8403-0132cb9256e1"
          }
        ],
        "tempoCiclo": 30,
        "configurado": false,
        "versaoPlano": {
          "idJson": "8005179f-4974-454e-a900-cdcf3bd0c58f"
        }
      },
      {
        "idJson": "75d979f9-9d31-4d8f-be82-cb92c36a360d",
        "anel": {
          "idJson": "c6787208-ede2-4b5f-b857-efa5ad97f0ae"
        },
        "descricao": "PLANO 13",
        "posicao": 13,
        "modoOperacao": "TEMPO_FIXO_ISOLADO",
        "posicaoTabelaEntreVerde": 1,
        "gruposSemaforicosPlanos": [
          {
            "idJson": "6bcffff9-36d4-4cd2-a58c-739be092099c"
          },
          {
            "idJson": "7ec60cfb-6b3c-442c-a2e2-4299e959d2b1"
          },
          {
            "idJson": "0c9a61f4-6052-4937-896b-e86caa60221f"
          },
          {
            "idJson": "fc75d2c8-793d-4546-ada0-d7fb182719ff"
          },
          {
            "idJson": "74644d0c-191c-4a88-b476-09d8ba462c5e"
          }
        ],
        "estagiosPlanos": [
          {
            "idJson": "763871ad-eece-4ddd-bd14-f4954b28e618"
          },
          {
            "idJson": "3ea6a7e6-da29-422f-97e8-224330a9b81d"
          },
          {
            "idJson": "7cad5eec-2e33-4e69-8f4e-fcadad66f250"
          },
          {
            "idJson": "41116921-843e-4046-b002-88630c2b98c8"
          },
          {
            "idJson": "d68cb97f-2a00-42c8-a7df-87a22da58320"
          }
        ],
        "tempoCiclo": 30,
        "configurado": false,
        "versaoPlano": {
          "idJson": "8005179f-4974-454e-a900-cdcf3bd0c58f"
        }
      },
      {
        "idJson": "26307aa2-23c7-4711-82b5-1cd3780c33d0",
        "anel": {
          "idJson": "c6787208-ede2-4b5f-b857-efa5ad97f0ae"
        },
        "descricao": "PLANO 14",
        "posicao": 14,
        "modoOperacao": "TEMPO_FIXO_ISOLADO",
        "posicaoTabelaEntreVerde": 1,
        "gruposSemaforicosPlanos": [
          {
            "idJson": "ce6e32fd-623f-4157-b945-8c2f4602e9e2"
          },
          {
            "idJson": "df2e38e1-b08c-400f-b755-6fadb4443325"
          },
          {
            "idJson": "34585496-8472-43e4-95c6-e819eabfb92a"
          },
          {
            "idJson": "10901202-8750-41b1-a805-d872037f0b3e"
          },
          {
            "idJson": "1497c4be-4a32-4836-86b6-70be2a193bf7"
          }
        ],
        "estagiosPlanos": [
          {
            "idJson": "01749fa5-ffa9-4284-9c8c-a591360bb026"
          },
          {
            "idJson": "e459ff60-6758-4afe-8aa1-fa0a98efb7fb"
          },
          {
            "idJson": "4f2f402c-124d-4c70-8569-9c0a44a38fdc"
          },
          {
            "idJson": "57478df6-1f05-4516-8048-b25629f75403"
          },
          {
            "idJson": "e4d33149-394f-4be1-b261-aa6409c13dec"
          }
        ],
        "tempoCiclo": 30,
        "configurado": false,
        "versaoPlano": {
          "idJson": "8005179f-4974-454e-a900-cdcf3bd0c58f"
        }
      },
      {
        "idJson": "106b7f1d-a993-43a1-bb65-57b743379fcd",
        "anel": {
          "idJson": "c6787208-ede2-4b5f-b857-efa5ad97f0ae"
        },
        "descricao": "PLANO 15",
        "posicao": 15,
        "modoOperacao": "TEMPO_FIXO_ISOLADO",
        "posicaoTabelaEntreVerde": 1,
        "gruposSemaforicosPlanos": [
          {
            "idJson": "1bc338d3-107a-4fbf-abf3-004093fdbfd2"
          },
          {
            "idJson": "7b0d29dc-57ef-4be7-86f4-ac9e824da1ea"
          },
          {
            "idJson": "ecb69c27-64c6-4467-8699-fa53f37075fa"
          },
          {
            "idJson": "7053ceca-e416-4b3e-a65f-0bf004b50372"
          },
          {
            "idJson": "4ed2275f-e068-4dfe-9bd9-9291746692de"
          }
        ],
        "estagiosPlanos": [
          {
            "idJson": "5c1cd917-6fc4-4ec4-a33c-81481a8c69ee"
          },
          {
            "idJson": "121e98e4-e13b-472d-a229-a3aa1c5b76aa"
          },
          {
            "idJson": "9da83fb9-1764-4668-b3a5-9bb48c983c69"
          },
          {
            "idJson": "c6fbf5c6-8b35-41ed-ad94-953e14f38fec"
          },
          {
            "idJson": "113e637c-0174-4964-816e-0cd381bde0cc"
          }
        ],
        "tempoCiclo": 30,
        "configurado": false,
        "versaoPlano": {
          "idJson": "8005179f-4974-454e-a900-cdcf3bd0c58f"
        }
      },
      {
        "idJson": "68ba2551-67fb-40fd-92df-b7a5ad5b053d",
        "anel": {
          "idJson": "c6787208-ede2-4b5f-b857-efa5ad97f0ae"
        },
        "descricao": "PLANO 16",
        "posicao": 16,
        "modoOperacao": "TEMPO_FIXO_ISOLADO",
        "posicaoTabelaEntreVerde": 1,
        "gruposSemaforicosPlanos": [
          {
            "idJson": "33158a9b-38cd-4893-bf88-4c79f4c2e04f"
          },
          {
            "idJson": "63991501-1150-40fd-b5ea-538becdd9bf3"
          },
          {
            "idJson": "aee5e705-2d24-4dca-9b81-8f81859a8c8c"
          },
          {
            "idJson": "a530778e-0fda-4025-92e0-783bce47aa37"
          },
          {
            "idJson": "1c46aaeb-f389-4de8-9b69-de5cf756f6ab"
          }
        ],
        "estagiosPlanos": [
          {
            "idJson": "77a029f3-7b4e-4a7f-82c3-15dd5b2e2df7"
          },
          {
            "idJson": "bd94ba16-5c56-4e54-938d-d45672a232b0"
          },
          {
            "idJson": "3138331e-2f9a-4ea8-a2dc-56708caa7036"
          },
          {
            "idJson": "9faa3bf4-8f55-42f2-b85f-ef3c00b29875"
          },
          {
            "idJson": "a82823bd-306e-4932-a132-2c17bcc6ce81"
          }
        ],
        "tempoCiclo": 30,
        "configurado": false,
        "versaoPlano": {
          "idJson": "8005179f-4974-454e-a900-cdcf3bd0c58f"
        }
      }
    ],
    "gruposSemaforicosPlanos": [
      {
        "idJson": "9f80092a-a27a-4f5a-ade5-45a2e7602e4f",
        "ativado": true,
        "grupoSemaforico": {
          "idJson": "911ebc3b-25b7-4599-8531-a2f776850b11"
        },
        "plano": {
          "idJson": "d89e7a44-dfc5-47a7-bdde-11820f06749f"
        }
      },
      {
        "idJson": "369059a6-69ff-46bb-95e5-7bf8d5d4409e",
        "ativado": true,
        "grupoSemaforico": {
          "idJson": "e04d3c80-06de-45c1-b635-c81ce567ac8c"
        },
        "plano": {
          "idJson": "d89e7a44-dfc5-47a7-bdde-11820f06749f"
        }
      },
      {
        "idJson": "1c1e3764-b093-4cf8-8133-455783e4e888",
        "ativado": true,
        "grupoSemaforico": {
          "idJson": "3e22d9c8-6a98-45cc-8bb9-b4e9cfb5a696"
        },
        "plano": {
          "idJson": "d89e7a44-dfc5-47a7-bdde-11820f06749f"
        }
      },
      {
        "idJson": "cc0b887c-e792-4fdc-b3e9-597bc7077e9d",
        "ativado": true,
        "grupoSemaforico": {
          "idJson": "9e494c36-72d9-485c-b69e-85ac411e912a"
        },
        "plano": {
          "idJson": "d89e7a44-dfc5-47a7-bdde-11820f06749f"
        }
      },
      {
        "idJson": "794d9e75-1486-4e90-b9c6-545c71239623",
        "ativado": true,
        "grupoSemaforico": {
          "idJson": "02c45e6c-c594-40e6-b11a-a6b238db83b2"
        },
        "plano": {
          "idJson": "d89e7a44-dfc5-47a7-bdde-11820f06749f"
        }
      },
      {
        "idJson": "d8c5477b-bf0e-47f2-94ee-46329559ebc0",
        "ativado": true,
        "grupoSemaforico": {
          "idJson": "911ebc3b-25b7-4599-8531-a2f776850b11"
        },
        "plano": {
          "idJson": "bd7ba623-c2e7-49ee-a306-4353f5513a7c"
        }
      },
      {
        "idJson": "8bed2d5d-fc20-4829-b744-9548927274f7",
        "ativado": true,
        "grupoSemaforico": {
          "idJson": "e04d3c80-06de-45c1-b635-c81ce567ac8c"
        },
        "plano": {
          "idJson": "bd7ba623-c2e7-49ee-a306-4353f5513a7c"
        }
      },
      {
        "idJson": "fe6bfc32-ebd1-4393-9d38-5d27db295867",
        "ativado": true,
        "grupoSemaforico": {
          "idJson": "3e22d9c8-6a98-45cc-8bb9-b4e9cfb5a696"
        },
        "plano": {
          "idJson": "bd7ba623-c2e7-49ee-a306-4353f5513a7c"
        }
      },
      {
        "idJson": "aa2728a2-71e5-4ff1-9ab4-9bc43622a6b6",
        "ativado": true,
        "grupoSemaforico": {
          "idJson": "9e494c36-72d9-485c-b69e-85ac411e912a"
        },
        "plano": {
          "idJson": "bd7ba623-c2e7-49ee-a306-4353f5513a7c"
        }
      },
      {
        "idJson": "28188470-e640-43ad-9d8e-2b992d9bda08",
        "ativado": true,
        "grupoSemaforico": {
          "idJson": "02c45e6c-c594-40e6-b11a-a6b238db83b2"
        },
        "plano": {
          "idJson": "bd7ba623-c2e7-49ee-a306-4353f5513a7c"
        }
      },
      {
        "idJson": "91ed7599-15e4-4660-828b-9ee921a8b30e",
        "ativado": true,
        "grupoSemaforico": {
          "idJson": "911ebc3b-25b7-4599-8531-a2f776850b11"
        },
        "plano": {
          "idJson": "216d0496-f7b6-4038-95b7-1975218e050a"
        }
      },
      {
        "idJson": "8cecf451-b01c-4ced-9d0c-826fb53f3fd7",
        "ativado": true,
        "grupoSemaforico": {
          "idJson": "e04d3c80-06de-45c1-b635-c81ce567ac8c"
        },
        "plano": {
          "idJson": "216d0496-f7b6-4038-95b7-1975218e050a"
        }
      },
      {
        "idJson": "44dca8cb-700c-48aa-bfb7-987d543d6493",
        "ativado": true,
        "grupoSemaforico": {
          "idJson": "3e22d9c8-6a98-45cc-8bb9-b4e9cfb5a696"
        },
        "plano": {
          "idJson": "216d0496-f7b6-4038-95b7-1975218e050a"
        }
      },
      {
        "idJson": "c5a285e9-40c0-49f3-8cb8-582aebaec570",
        "ativado": true,
        "grupoSemaforico": {
          "idJson": "9e494c36-72d9-485c-b69e-85ac411e912a"
        },
        "plano": {
          "idJson": "216d0496-f7b6-4038-95b7-1975218e050a"
        }
      },
      {
        "idJson": "3b3a1aa1-b5c6-4819-8df0-879430f6de04",
        "ativado": true,
        "grupoSemaforico": {
          "idJson": "02c45e6c-c594-40e6-b11a-a6b238db83b2"
        },
        "plano": {
          "idJson": "216d0496-f7b6-4038-95b7-1975218e050a"
        }
      },
      {
        "idJson": "ffde822e-c03f-41ec-9271-c094ee3e0239",
        "ativado": true,
        "grupoSemaforico": {
          "idJson": "911ebc3b-25b7-4599-8531-a2f776850b11"
        },
        "plano": {
          "idJson": "986d4974-baad-4c9d-ad93-ec9a23a982ef"
        }
      },
      {
        "idJson": "869addf9-e54f-41fe-84c7-1577e2ccd151",
        "ativado": true,
        "grupoSemaforico": {
          "idJson": "e04d3c80-06de-45c1-b635-c81ce567ac8c"
        },
        "plano": {
          "idJson": "986d4974-baad-4c9d-ad93-ec9a23a982ef"
        }
      },
      {
        "idJson": "2660ba06-9cf2-4bd8-8723-3834f417ddc7",
        "ativado": true,
        "grupoSemaforico": {
          "idJson": "3e22d9c8-6a98-45cc-8bb9-b4e9cfb5a696"
        },
        "plano": {
          "idJson": "986d4974-baad-4c9d-ad93-ec9a23a982ef"
        }
      },
      {
        "idJson": "3669063b-f2dc-4c38-b29e-9c2ec230f483",
        "ativado": true,
        "grupoSemaforico": {
          "idJson": "9e494c36-72d9-485c-b69e-85ac411e912a"
        },
        "plano": {
          "idJson": "986d4974-baad-4c9d-ad93-ec9a23a982ef"
        }
      },
      {
        "idJson": "25508f7c-09e7-4961-b1a8-169f112f58f6",
        "ativado": true,
        "grupoSemaforico": {
          "idJson": "02c45e6c-c594-40e6-b11a-a6b238db83b2"
        },
        "plano": {
          "idJson": "986d4974-baad-4c9d-ad93-ec9a23a982ef"
        }
      },
      {
        "idJson": "22ceeed8-c8cf-4448-8bbe-9967ca49f521",
        "ativado": true,
        "grupoSemaforico": {
          "idJson": "911ebc3b-25b7-4599-8531-a2f776850b11"
        },
        "plano": {
          "idJson": "f9ecbfed-2372-4a5c-a3bf-555dde6bdec5"
        }
      },
      {
        "idJson": "f3b0b32b-5978-4a54-9a6f-f0c4ff6e544c",
        "ativado": true,
        "grupoSemaforico": {
          "idJson": "e04d3c80-06de-45c1-b635-c81ce567ac8c"
        },
        "plano": {
          "idJson": "f9ecbfed-2372-4a5c-a3bf-555dde6bdec5"
        }
      },
      {
        "idJson": "b6e425ee-2241-4f62-a931-401458f0a9d8",
        "ativado": true,
        "grupoSemaforico": {
          "idJson": "3e22d9c8-6a98-45cc-8bb9-b4e9cfb5a696"
        },
        "plano": {
          "idJson": "f9ecbfed-2372-4a5c-a3bf-555dde6bdec5"
        }
      },
      {
        "idJson": "f7ab9a7b-3907-40ef-ae86-423cf8912129",
        "ativado": true,
        "grupoSemaforico": {
          "idJson": "9e494c36-72d9-485c-b69e-85ac411e912a"
        },
        "plano": {
          "idJson": "f9ecbfed-2372-4a5c-a3bf-555dde6bdec5"
        }
      },
      {
        "idJson": "40dd97ec-cb54-416c-9b89-6f725dc35d10",
        "ativado": true,
        "grupoSemaforico": {
          "idJson": "02c45e6c-c594-40e6-b11a-a6b238db83b2"
        },
        "plano": {
          "idJson": "f9ecbfed-2372-4a5c-a3bf-555dde6bdec5"
        }
      },
      {
        "idJson": "50718f50-8680-496e-9c5b-f4864bb4b3c6",
        "ativado": true,
        "grupoSemaforico": {
          "idJson": "911ebc3b-25b7-4599-8531-a2f776850b11"
        },
        "plano": {
          "idJson": "4e3c0565-954f-4586-bda1-7fc7a89dcdfd"
        }
      },
      {
        "idJson": "34b04967-3094-482f-9004-e4053bbfca26",
        "ativado": true,
        "grupoSemaforico": {
          "idJson": "e04d3c80-06de-45c1-b635-c81ce567ac8c"
        },
        "plano": {
          "idJson": "4e3c0565-954f-4586-bda1-7fc7a89dcdfd"
        }
      },
      {
        "idJson": "aeb96a3b-67ff-4f1c-8cbf-2213b81f182e",
        "ativado": true,
        "grupoSemaforico": {
          "idJson": "3e22d9c8-6a98-45cc-8bb9-b4e9cfb5a696"
        },
        "plano": {
          "idJson": "4e3c0565-954f-4586-bda1-7fc7a89dcdfd"
        }
      },
      {
        "idJson": "86ca5433-440a-46cf-ac9c-2e98a86bf6ee",
        "ativado": true,
        "grupoSemaforico": {
          "idJson": "9e494c36-72d9-485c-b69e-85ac411e912a"
        },
        "plano": {
          "idJson": "4e3c0565-954f-4586-bda1-7fc7a89dcdfd"
        }
      },
      {
        "idJson": "393fcf23-3f39-4e92-9084-e12d89fdb899",
        "ativado": true,
        "grupoSemaforico": {
          "idJson": "02c45e6c-c594-40e6-b11a-a6b238db83b2"
        },
        "plano": {
          "idJson": "4e3c0565-954f-4586-bda1-7fc7a89dcdfd"
        }
      },
      {
        "idJson": "eb5aceaa-3e4f-4805-bc6a-d2a2c0abd5c6",
        "ativado": true,
        "grupoSemaforico": {
          "idJson": "911ebc3b-25b7-4599-8531-a2f776850b11"
        },
        "plano": {
          "idJson": "b80ebfe7-fee4-477f-be85-f7cdbf6b15c0"
        }
      },
      {
        "idJson": "9166b3b6-e4ac-4f08-971c-023fd45a1f36",
        "ativado": true,
        "grupoSemaforico": {
          "idJson": "e04d3c80-06de-45c1-b635-c81ce567ac8c"
        },
        "plano": {
          "idJson": "b80ebfe7-fee4-477f-be85-f7cdbf6b15c0"
        }
      },
      {
        "idJson": "b09d18f7-feca-42c1-8c33-b0ca0241b702",
        "ativado": true,
        "grupoSemaforico": {
          "idJson": "3e22d9c8-6a98-45cc-8bb9-b4e9cfb5a696"
        },
        "plano": {
          "idJson": "b80ebfe7-fee4-477f-be85-f7cdbf6b15c0"
        }
      },
      {
        "idJson": "9bd0316f-53bd-401b-a985-fc9696aa27c5",
        "ativado": true,
        "grupoSemaforico": {
          "idJson": "9e494c36-72d9-485c-b69e-85ac411e912a"
        },
        "plano": {
          "idJson": "b80ebfe7-fee4-477f-be85-f7cdbf6b15c0"
        }
      },
      {
        "idJson": "da4f3e1b-1272-48eb-b40d-cc04baebebab",
        "ativado": true,
        "grupoSemaforico": {
          "idJson": "02c45e6c-c594-40e6-b11a-a6b238db83b2"
        },
        "plano": {
          "idJson": "b80ebfe7-fee4-477f-be85-f7cdbf6b15c0"
        }
      },
      {
        "idJson": "57686ccc-316e-4bc4-8b8c-cade3c1cb323",
        "ativado": true,
        "grupoSemaforico": {
          "idJson": "911ebc3b-25b7-4599-8531-a2f776850b11"
        },
        "plano": {
          "idJson": "179a236e-119f-4e47-95cf-1e8c8988c265"
        }
      },
      {
        "idJson": "99ef4549-4943-475f-ad44-d003089af891",
        "ativado": true,
        "grupoSemaforico": {
          "idJson": "e04d3c80-06de-45c1-b635-c81ce567ac8c"
        },
        "plano": {
          "idJson": "179a236e-119f-4e47-95cf-1e8c8988c265"
        }
      },
      {
        "idJson": "dd98f0dd-ef7a-4f9f-9ed4-f04470e9f663",
        "ativado": true,
        "grupoSemaforico": {
          "idJson": "3e22d9c8-6a98-45cc-8bb9-b4e9cfb5a696"
        },
        "plano": {
          "idJson": "179a236e-119f-4e47-95cf-1e8c8988c265"
        }
      },
      {
        "idJson": "631a022f-54a2-4151-b227-e6347517776e",
        "ativado": true,
        "grupoSemaforico": {
          "idJson": "9e494c36-72d9-485c-b69e-85ac411e912a"
        },
        "plano": {
          "idJson": "179a236e-119f-4e47-95cf-1e8c8988c265"
        }
      },
      {
        "idJson": "8d304413-7282-413e-915d-7157cfea6e3a",
        "ativado": true,
        "grupoSemaforico": {
          "idJson": "02c45e6c-c594-40e6-b11a-a6b238db83b2"
        },
        "plano": {
          "idJson": "179a236e-119f-4e47-95cf-1e8c8988c265"
        }
      },
      {
        "idJson": "a288f728-b9b7-45b5-8d52-b8516a5cde26",
        "ativado": true,
        "grupoSemaforico": {
          "idJson": "911ebc3b-25b7-4599-8531-a2f776850b11"
        },
        "plano": {
          "idJson": "aa49b13d-e7c0-4c65-8985-9f371dd53bfd"
        }
      },
      {
        "idJson": "92ac501c-b756-47e1-bebb-c2d53f3c9542",
        "ativado": true,
        "grupoSemaforico": {
          "idJson": "e04d3c80-06de-45c1-b635-c81ce567ac8c"
        },
        "plano": {
          "idJson": "aa49b13d-e7c0-4c65-8985-9f371dd53bfd"
        }
      },
      {
        "idJson": "e9e7c011-5937-41ef-ba12-20998afcfa44",
        "ativado": true,
        "grupoSemaforico": {
          "idJson": "3e22d9c8-6a98-45cc-8bb9-b4e9cfb5a696"
        },
        "plano": {
          "idJson": "aa49b13d-e7c0-4c65-8985-9f371dd53bfd"
        }
      },
      {
        "idJson": "50bd3034-f3ad-4e27-8a44-945f6db841b4",
        "ativado": true,
        "grupoSemaforico": {
          "idJson": "9e494c36-72d9-485c-b69e-85ac411e912a"
        },
        "plano": {
          "idJson": "aa49b13d-e7c0-4c65-8985-9f371dd53bfd"
        }
      },
      {
        "idJson": "9adcd6b5-3e3f-4710-980e-5565d63e8a6b",
        "ativado": true,
        "grupoSemaforico": {
          "idJson": "02c45e6c-c594-40e6-b11a-a6b238db83b2"
        },
        "plano": {
          "idJson": "aa49b13d-e7c0-4c65-8985-9f371dd53bfd"
        }
      },
      {
        "idJson": "c5c367e3-2fbc-4c6a-88b4-570224b1fb63",
        "ativado": true,
        "grupoSemaforico": {
          "idJson": "911ebc3b-25b7-4599-8531-a2f776850b11"
        },
        "plano": {
          "idJson": "0ef18b30-6185-4af9-be67-4382bb721c14"
        }
      },
      {
        "idJson": "02cb17a2-4438-409b-ab64-d6ea4469afc5",
        "ativado": true,
        "grupoSemaforico": {
          "idJson": "e04d3c80-06de-45c1-b635-c81ce567ac8c"
        },
        "plano": {
          "idJson": "0ef18b30-6185-4af9-be67-4382bb721c14"
        }
      },
      {
        "idJson": "071b53b9-b8a7-4b6b-9708-30b5abb07c08",
        "ativado": true,
        "grupoSemaforico": {
          "idJson": "3e22d9c8-6a98-45cc-8bb9-b4e9cfb5a696"
        },
        "plano": {
          "idJson": "0ef18b30-6185-4af9-be67-4382bb721c14"
        }
      },
      {
        "idJson": "43111abd-8fd9-4b1d-8733-11613431e84b",
        "ativado": true,
        "grupoSemaforico": {
          "idJson": "9e494c36-72d9-485c-b69e-85ac411e912a"
        },
        "plano": {
          "idJson": "0ef18b30-6185-4af9-be67-4382bb721c14"
        }
      },
      {
        "idJson": "47c73ef2-9ef2-4a90-bb16-c762ca400610",
        "ativado": true,
        "grupoSemaforico": {
          "idJson": "02c45e6c-c594-40e6-b11a-a6b238db83b2"
        },
        "plano": {
          "idJson": "0ef18b30-6185-4af9-be67-4382bb721c14"
        }
      },
      {
        "idJson": "49b94a9c-a982-4387-9d9c-9142f1582743",
        "ativado": true,
        "grupoSemaforico": {
          "idJson": "911ebc3b-25b7-4599-8531-a2f776850b11"
        },
        "plano": {
          "idJson": "3bb2b8b8-450f-40ae-a31b-096d327bade6"
        }
      },
      {
        "idJson": "862cdf5d-9f28-4e2b-96ef-18d93b2a9dba",
        "ativado": true,
        "grupoSemaforico": {
          "idJson": "e04d3c80-06de-45c1-b635-c81ce567ac8c"
        },
        "plano": {
          "idJson": "3bb2b8b8-450f-40ae-a31b-096d327bade6"
        }
      },
      {
        "idJson": "b9ca091a-dc34-42fd-a5a7-4551cc52d35b",
        "ativado": true,
        "grupoSemaforico": {
          "idJson": "3e22d9c8-6a98-45cc-8bb9-b4e9cfb5a696"
        },
        "plano": {
          "idJson": "3bb2b8b8-450f-40ae-a31b-096d327bade6"
        }
      },
      {
        "idJson": "0ac7fe2e-fc31-4761-ac2d-d50b1b0dc299",
        "ativado": true,
        "grupoSemaforico": {
          "idJson": "9e494c36-72d9-485c-b69e-85ac411e912a"
        },
        "plano": {
          "idJson": "3bb2b8b8-450f-40ae-a31b-096d327bade6"
        }
      },
      {
        "idJson": "9431707e-22e5-4d79-b3b7-1eb43cbafd7f",
        "ativado": true,
        "grupoSemaforico": {
          "idJson": "02c45e6c-c594-40e6-b11a-a6b238db83b2"
        },
        "plano": {
          "idJson": "3bb2b8b8-450f-40ae-a31b-096d327bade6"
        }
      },
      {
        "idJson": "5da0c9e4-3d98-487a-9549-ba8b8f2400b9",
        "ativado": true,
        "grupoSemaforico": {
          "idJson": "911ebc3b-25b7-4599-8531-a2f776850b11"
        },
        "plano": {
          "idJson": "9bd9461c-7792-4aa2-a8a6-805945607a8d"
        }
      },
      {
        "idJson": "c85be328-510c-4c0d-9b2a-94dfa8b9d780",
        "ativado": true,
        "grupoSemaforico": {
          "idJson": "e04d3c80-06de-45c1-b635-c81ce567ac8c"
        },
        "plano": {
          "idJson": "9bd9461c-7792-4aa2-a8a6-805945607a8d"
        }
      },
      {
        "idJson": "f4ad8ded-8e3e-48e3-ac2a-72e91911c299",
        "ativado": true,
        "grupoSemaforico": {
          "idJson": "3e22d9c8-6a98-45cc-8bb9-b4e9cfb5a696"
        },
        "plano": {
          "idJson": "9bd9461c-7792-4aa2-a8a6-805945607a8d"
        }
      },
      {
        "idJson": "9741db28-1d28-4676-9fbb-7d22e57feacc",
        "ativado": true,
        "grupoSemaforico": {
          "idJson": "9e494c36-72d9-485c-b69e-85ac411e912a"
        },
        "plano": {
          "idJson": "9bd9461c-7792-4aa2-a8a6-805945607a8d"
        }
      },
      {
        "idJson": "b7b4c17d-c92d-48ab-ace9-0f4fb551f658",
        "ativado": true,
        "grupoSemaforico": {
          "idJson": "02c45e6c-c594-40e6-b11a-a6b238db83b2"
        },
        "plano": {
          "idJson": "9bd9461c-7792-4aa2-a8a6-805945607a8d"
        }
      },
      {
        "idJson": "574b5d6d-ad63-4299-9534-6d0dfa64b662",
        "ativado": true,
        "grupoSemaforico": {
          "idJson": "911ebc3b-25b7-4599-8531-a2f776850b11"
        },
        "plano": {
          "idJson": "9a13a395-cc26-4210-a84a-cc950a69ac4c"
        }
      },
      {
        "idJson": "7ddf9057-caab-4aa5-8222-f2489cda764c",
        "ativado": true,
        "grupoSemaforico": {
          "idJson": "e04d3c80-06de-45c1-b635-c81ce567ac8c"
        },
        "plano": {
          "idJson": "9a13a395-cc26-4210-a84a-cc950a69ac4c"
        }
      },
      {
        "idJson": "4fffae1d-e0db-4902-959d-dcd6896a6c30",
        "ativado": true,
        "grupoSemaforico": {
          "idJson": "3e22d9c8-6a98-45cc-8bb9-b4e9cfb5a696"
        },
        "plano": {
          "idJson": "9a13a395-cc26-4210-a84a-cc950a69ac4c"
        }
      },
      {
        "idJson": "3906ce4a-bf25-4db6-a13a-6022c04c97d2",
        "ativado": true,
        "grupoSemaforico": {
          "idJson": "9e494c36-72d9-485c-b69e-85ac411e912a"
        },
        "plano": {
          "idJson": "9a13a395-cc26-4210-a84a-cc950a69ac4c"
        }
      },
      {
        "idJson": "dcbc4d98-e994-4af0-882e-1650585d8809",
        "ativado": true,
        "grupoSemaforico": {
          "idJson": "02c45e6c-c594-40e6-b11a-a6b238db83b2"
        },
        "plano": {
          "idJson": "9a13a395-cc26-4210-a84a-cc950a69ac4c"
        }
      },
      {
        "idJson": "6bcffff9-36d4-4cd2-a58c-739be092099c",
        "ativado": true,
        "grupoSemaforico": {
          "idJson": "911ebc3b-25b7-4599-8531-a2f776850b11"
        },
        "plano": {
          "idJson": "75d979f9-9d31-4d8f-be82-cb92c36a360d"
        }
      },
      {
        "idJson": "7ec60cfb-6b3c-442c-a2e2-4299e959d2b1",
        "ativado": true,
        "grupoSemaforico": {
          "idJson": "e04d3c80-06de-45c1-b635-c81ce567ac8c"
        },
        "plano": {
          "idJson": "75d979f9-9d31-4d8f-be82-cb92c36a360d"
        }
      },
      {
        "idJson": "0c9a61f4-6052-4937-896b-e86caa60221f",
        "ativado": true,
        "grupoSemaforico": {
          "idJson": "3e22d9c8-6a98-45cc-8bb9-b4e9cfb5a696"
        },
        "plano": {
          "idJson": "75d979f9-9d31-4d8f-be82-cb92c36a360d"
        }
      },
      {
        "idJson": "fc75d2c8-793d-4546-ada0-d7fb182719ff",
        "ativado": true,
        "grupoSemaforico": {
          "idJson": "9e494c36-72d9-485c-b69e-85ac411e912a"
        },
        "plano": {
          "idJson": "75d979f9-9d31-4d8f-be82-cb92c36a360d"
        }
      },
      {
        "idJson": "74644d0c-191c-4a88-b476-09d8ba462c5e",
        "ativado": true,
        "grupoSemaforico": {
          "idJson": "02c45e6c-c594-40e6-b11a-a6b238db83b2"
        },
        "plano": {
          "idJson": "75d979f9-9d31-4d8f-be82-cb92c36a360d"
        }
      },
      {
        "idJson": "ce6e32fd-623f-4157-b945-8c2f4602e9e2",
        "ativado": true,
        "grupoSemaforico": {
          "idJson": "911ebc3b-25b7-4599-8531-a2f776850b11"
        },
        "plano": {
          "idJson": "26307aa2-23c7-4711-82b5-1cd3780c33d0"
        }
      },
      {
        "idJson": "df2e38e1-b08c-400f-b755-6fadb4443325",
        "ativado": true,
        "grupoSemaforico": {
          "idJson": "e04d3c80-06de-45c1-b635-c81ce567ac8c"
        },
        "plano": {
          "idJson": "26307aa2-23c7-4711-82b5-1cd3780c33d0"
        }
      },
      {
        "idJson": "34585496-8472-43e4-95c6-e819eabfb92a",
        "ativado": true,
        "grupoSemaforico": {
          "idJson": "3e22d9c8-6a98-45cc-8bb9-b4e9cfb5a696"
        },
        "plano": {
          "idJson": "26307aa2-23c7-4711-82b5-1cd3780c33d0"
        }
      },
      {
        "idJson": "10901202-8750-41b1-a805-d872037f0b3e",
        "ativado": true,
        "grupoSemaforico": {
          "idJson": "9e494c36-72d9-485c-b69e-85ac411e912a"
        },
        "plano": {
          "idJson": "26307aa2-23c7-4711-82b5-1cd3780c33d0"
        }
      },
      {
        "idJson": "1497c4be-4a32-4836-86b6-70be2a193bf7",
        "ativado": true,
        "grupoSemaforico": {
          "idJson": "02c45e6c-c594-40e6-b11a-a6b238db83b2"
        },
        "plano": {
          "idJson": "26307aa2-23c7-4711-82b5-1cd3780c33d0"
        }
      },
      {
        "idJson": "1bc338d3-107a-4fbf-abf3-004093fdbfd2",
        "ativado": true,
        "grupoSemaforico": {
          "idJson": "911ebc3b-25b7-4599-8531-a2f776850b11"
        },
        "plano": {
          "idJson": "106b7f1d-a993-43a1-bb65-57b743379fcd"
        }
      },
      {
        "idJson": "7b0d29dc-57ef-4be7-86f4-ac9e824da1ea",
        "ativado": true,
        "grupoSemaforico": {
          "idJson": "e04d3c80-06de-45c1-b635-c81ce567ac8c"
        },
        "plano": {
          "idJson": "106b7f1d-a993-43a1-bb65-57b743379fcd"
        }
      },
      {
        "idJson": "ecb69c27-64c6-4467-8699-fa53f37075fa",
        "ativado": true,
        "grupoSemaforico": {
          "idJson": "3e22d9c8-6a98-45cc-8bb9-b4e9cfb5a696"
        },
        "plano": {
          "idJson": "106b7f1d-a993-43a1-bb65-57b743379fcd"
        }
      },
      {
        "idJson": "7053ceca-e416-4b3e-a65f-0bf004b50372",
        "ativado": true,
        "grupoSemaforico": {
          "idJson": "9e494c36-72d9-485c-b69e-85ac411e912a"
        },
        "plano": {
          "idJson": "106b7f1d-a993-43a1-bb65-57b743379fcd"
        }
      },
      {
        "idJson": "4ed2275f-e068-4dfe-9bd9-9291746692de",
        "ativado": true,
        "grupoSemaforico": {
          "idJson": "02c45e6c-c594-40e6-b11a-a6b238db83b2"
        },
        "plano": {
          "idJson": "106b7f1d-a993-43a1-bb65-57b743379fcd"
        }
      },
      {
        "idJson": "33158a9b-38cd-4893-bf88-4c79f4c2e04f",
        "ativado": true,
        "grupoSemaforico": {
          "idJson": "911ebc3b-25b7-4599-8531-a2f776850b11"
        },
        "plano": {
          "idJson": "68ba2551-67fb-40fd-92df-b7a5ad5b053d"
        }
      },
      {
        "idJson": "63991501-1150-40fd-b5ea-538becdd9bf3",
        "ativado": true,
        "grupoSemaforico": {
          "idJson": "e04d3c80-06de-45c1-b635-c81ce567ac8c"
        },
        "plano": {
          "idJson": "68ba2551-67fb-40fd-92df-b7a5ad5b053d"
        }
      },
      {
        "idJson": "aee5e705-2d24-4dca-9b81-8f81859a8c8c",
        "ativado": true,
        "grupoSemaforico": {
          "idJson": "3e22d9c8-6a98-45cc-8bb9-b4e9cfb5a696"
        },
        "plano": {
          "idJson": "68ba2551-67fb-40fd-92df-b7a5ad5b053d"
        }
      },
      {
        "idJson": "a530778e-0fda-4025-92e0-783bce47aa37",
        "ativado": true,
        "grupoSemaforico": {
          "idJson": "9e494c36-72d9-485c-b69e-85ac411e912a"
        },
        "plano": {
          "idJson": "68ba2551-67fb-40fd-92df-b7a5ad5b053d"
        }
      },
      {
        "idJson": "1c46aaeb-f389-4de8-9b69-de5cf756f6ab",
        "ativado": true,
        "grupoSemaforico": {
          "idJson": "02c45e6c-c594-40e6-b11a-a6b238db83b2"
        },
        "plano": {
          "idJson": "68ba2551-67fb-40fd-92df-b7a5ad5b053d"
        }
      }
    ],
    "estagiosPlanos": [
      {
        "idJson": "718dee9c-c8fe-4012-8610-802f27460abf",
        "estagio": {
          "idJson": "25dd2981-084e-4b00-be77-b92052c25502"
        },
        "plano": {
          "idJson": "d89e7a44-dfc5-47a7-bdde-11820f06749f"
        },
        "posicao": 2,
        "tempoVerde": 10,
        "dispensavel": false,
        "origemTransicaoProibida": false,
        "destinoTransicaoProibida": false
      },
      {
        "idJson": "1be34f39-e636-47d2-9692-f4bde80618a3",
        "estagio": {
          "idJson": "fcc3c63e-cb36-4966-9407-1b8bf30c3106"
        },
        "plano": {
          "idJson": "d89e7a44-dfc5-47a7-bdde-11820f06749f"
        },
        "posicao": 5,
        "tempoVerde": 10,
        "dispensavel": false,
        "origemTransicaoProibida": false,
        "destinoTransicaoProibida": false
      },
      {
        "idJson": "0435df12-657d-4f78-b3ad-e71ba92d21d7",
        "estagio": {
          "idJson": "bd3d834a-d83c-4ac0-b52a-04c2bdbd34a7"
        },
        "plano": {
          "idJson": "d89e7a44-dfc5-47a7-bdde-11820f06749f"
        },
        "posicao": 4,
        "tempoVerde": 10,
        "dispensavel": false,
        "origemTransicaoProibida": false,
        "destinoTransicaoProibida": false
      },
      {
        "idJson": "0825cbed-cfa6-4d1a-9246-f02a8b84f81a",
        "estagio": {
          "idJson": "8a1e2214-588c-465a-9e37-d838c56f2106"
        },
        "plano": {
          "idJson": "d89e7a44-dfc5-47a7-bdde-11820f06749f"
        },
        "posicao": 3,
        "tempoVerde": 10,
        "dispensavel": false,
        "origemTransicaoProibida": false,
        "destinoTransicaoProibida": false
      },
      {
        "idJson": "85dbef47-8693-4aea-ab92-a2983cb06302",
        "estagio": {
          "idJson": "73d11d5b-7d01-455c-9764-2e222b01bc8b"
        },
        "plano": {
          "idJson": "d89e7a44-dfc5-47a7-bdde-11820f06749f"
        },
        "posicao": 1,
        "tempoVerde": 10,
        "dispensavel": false,
        "origemTransicaoProibida": false,
        "destinoTransicaoProibida": false
      },
      {
        "idJson": "02784cd1-5ab1-429d-8312-042eada4de94",
        "estagio": {
          "idJson": "25dd2981-084e-4b00-be77-b92052c25502"
        },
        "plano": {
          "idJson": "bd7ba623-c2e7-49ee-a306-4353f5513a7c"
        },
        "posicao": 2,
        "tempoVerde": 10,
        "dispensavel": false,
        "$$hashKey": "object:501",
        "origemTransicaoProibida": false,
        "destinoTransicaoProibida": false
      },
      {
        "idJson": "8578bf1a-c226-45ae-8c85-52a4a50a7fa3",
        "estagio": {
          "idJson": "fcc3c63e-cb36-4966-9407-1b8bf30c3106"
        },
        "plano": {
          "idJson": "bd7ba623-c2e7-49ee-a306-4353f5513a7c"
        },
        "posicao": 5,
        "tempoVerde": 10,
        "dispensavel": false,
        "$$hashKey": "object:504",
        "origemTransicaoProibida": false,
        "destinoTransicaoProibida": false
      },
      {
        "idJson": "8072c59c-773e-4904-adab-90a2bb1c0b5f",
        "estagio": {
          "idJson": "bd3d834a-d83c-4ac0-b52a-04c2bdbd34a7"
        },
        "plano": {
          "idJson": "bd7ba623-c2e7-49ee-a306-4353f5513a7c"
        },
        "posicao": 4,
        "tempoVerde": 10,
        "dispensavel": false,
        "$$hashKey": "object:503",
        "origemTransicaoProibida": false,
        "destinoTransicaoProibida": false
      },
      {
        "idJson": "2f8d3167-3376-40e2-a3bd-073000c9c4c2",
        "estagio": {
          "idJson": "8a1e2214-588c-465a-9e37-d838c56f2106"
        },
        "plano": {
          "idJson": "bd7ba623-c2e7-49ee-a306-4353f5513a7c"
        },
        "posicao": 3,
        "tempoVerde": 10,
        "dispensavel": false,
        "$$hashKey": "object:502",
        "origemTransicaoProibida": false,
        "destinoTransicaoProibida": false
      },
      {
        "idJson": "e2a53443-8072-429f-b31d-8e712ff2b471",
        "estagio": {
          "idJson": "73d11d5b-7d01-455c-9764-2e222b01bc8b"
        },
        "plano": {
          "idJson": "bd7ba623-c2e7-49ee-a306-4353f5513a7c"
        },
        "posicao": 1,
        "tempoVerde": 10,
        "dispensavel": false,
        "$$hashKey": "object:500",
        "origemTransicaoProibida": false,
        "destinoTransicaoProibida": false
      },
      {
        "idJson": "e232bc1f-67bd-43cf-897e-dc08c329018c",
        "estagio": {
          "idJson": "25dd2981-084e-4b00-be77-b92052c25502"
        },
        "plano": {
          "idJson": "216d0496-f7b6-4038-95b7-1975218e050a"
        },
        "posicao": 2,
        "tempoVerde": 10,
        "dispensavel": false
      },
      {
        "idJson": "af435d08-f043-445e-8cae-732d5b0f17be",
        "estagio": {
          "idJson": "fcc3c63e-cb36-4966-9407-1b8bf30c3106"
        },
        "plano": {
          "idJson": "216d0496-f7b6-4038-95b7-1975218e050a"
        },
        "posicao": 5,
        "tempoVerde": 10,
        "dispensavel": false
      },
      {
        "idJson": "e32f6a3d-bfed-4100-9da9-edaf46515896",
        "estagio": {
          "idJson": "bd3d834a-d83c-4ac0-b52a-04c2bdbd34a7"
        },
        "plano": {
          "idJson": "216d0496-f7b6-4038-95b7-1975218e050a"
        },
        "posicao": 4,
        "tempoVerde": 10,
        "dispensavel": false
      },
      {
        "idJson": "b590187f-efda-4986-8aa7-678097997fe7",
        "estagio": {
          "idJson": "8a1e2214-588c-465a-9e37-d838c56f2106"
        },
        "plano": {
          "idJson": "216d0496-f7b6-4038-95b7-1975218e050a"
        },
        "posicao": 3,
        "tempoVerde": 10,
        "dispensavel": false
      },
      {
        "idJson": "e1fe0e3d-1c72-4561-98e1-e3b727801e37",
        "estagio": {
          "idJson": "73d11d5b-7d01-455c-9764-2e222b01bc8b"
        },
        "plano": {
          "idJson": "216d0496-f7b6-4038-95b7-1975218e050a"
        },
        "posicao": 1,
        "tempoVerde": 10,
        "dispensavel": false
      },
      {
        "idJson": "7c88c6cf-758c-4450-85da-3871337ce801",
        "estagio": {
          "idJson": "25dd2981-084e-4b00-be77-b92052c25502"
        },
        "plano": {
          "idJson": "986d4974-baad-4c9d-ad93-ec9a23a982ef"
        },
        "posicao": 2,
        "tempoVerde": 10,
        "dispensavel": false
      },
      {
        "idJson": "d240b912-529c-4152-a132-95ed10444b2b",
        "estagio": {
          "idJson": "fcc3c63e-cb36-4966-9407-1b8bf30c3106"
        },
        "plano": {
          "idJson": "986d4974-baad-4c9d-ad93-ec9a23a982ef"
        },
        "posicao": 5,
        "tempoVerde": 10,
        "dispensavel": false
      },
      {
        "idJson": "4f980485-763a-41a4-b128-28972d5faaf6",
        "estagio": {
          "idJson": "bd3d834a-d83c-4ac0-b52a-04c2bdbd34a7"
        },
        "plano": {
          "idJson": "986d4974-baad-4c9d-ad93-ec9a23a982ef"
        },
        "posicao": 4,
        "tempoVerde": 10,
        "dispensavel": false
      },
      {
        "idJson": "b9ef614a-7fd2-496a-ad85-c99a9be9799a",
        "estagio": {
          "idJson": "8a1e2214-588c-465a-9e37-d838c56f2106"
        },
        "plano": {
          "idJson": "986d4974-baad-4c9d-ad93-ec9a23a982ef"
        },
        "posicao": 3,
        "tempoVerde": 10,
        "dispensavel": false
      },
      {
        "idJson": "bf3d7fee-5210-48cd-9bfa-a025a860f09e",
        "estagio": {
          "idJson": "73d11d5b-7d01-455c-9764-2e222b01bc8b"
        },
        "plano": {
          "idJson": "986d4974-baad-4c9d-ad93-ec9a23a982ef"
        },
        "posicao": 1,
        "tempoVerde": 10,
        "dispensavel": false
      },
      {
        "idJson": "1fae84bd-2c25-497d-ae49-dbc75c199344",
        "estagio": {
          "idJson": "25dd2981-084e-4b00-be77-b92052c25502"
        },
        "plano": {
          "idJson": "f9ecbfed-2372-4a5c-a3bf-555dde6bdec5"
        },
        "posicao": 2,
        "tempoVerde": 10,
        "dispensavel": false
      },
      {
        "idJson": "a9376ae1-d39d-4417-9a87-d826e64339d8",
        "estagio": {
          "idJson": "fcc3c63e-cb36-4966-9407-1b8bf30c3106"
        },
        "plano": {
          "idJson": "f9ecbfed-2372-4a5c-a3bf-555dde6bdec5"
        },
        "posicao": 5,
        "tempoVerde": 10,
        "dispensavel": false
      },
      {
        "idJson": "7bf9c4dc-d6ec-46eb-a0c0-4baf701289a6",
        "estagio": {
          "idJson": "bd3d834a-d83c-4ac0-b52a-04c2bdbd34a7"
        },
        "plano": {
          "idJson": "f9ecbfed-2372-4a5c-a3bf-555dde6bdec5"
        },
        "posicao": 4,
        "tempoVerde": 10,
        "dispensavel": false
      },
      {
        "idJson": "034a0f40-58c9-4c0c-aff6-c187a3031138",
        "estagio": {
          "idJson": "8a1e2214-588c-465a-9e37-d838c56f2106"
        },
        "plano": {
          "idJson": "f9ecbfed-2372-4a5c-a3bf-555dde6bdec5"
        },
        "posicao": 3,
        "tempoVerde": 10,
        "dispensavel": false
      },
      {
        "idJson": "a03f4b95-ed63-4188-af9b-bbe0affea532",
        "estagio": {
          "idJson": "73d11d5b-7d01-455c-9764-2e222b01bc8b"
        },
        "plano": {
          "idJson": "f9ecbfed-2372-4a5c-a3bf-555dde6bdec5"
        },
        "posicao": 1,
        "tempoVerde": 10,
        "dispensavel": false
      },
      {
        "idJson": "f44c6c0f-5bd3-412e-b324-667faadc5397",
        "estagio": {
          "idJson": "25dd2981-084e-4b00-be77-b92052c25502"
        },
        "plano": {
          "idJson": "4e3c0565-954f-4586-bda1-7fc7a89dcdfd"
        },
        "posicao": 2,
        "tempoVerde": 10,
        "dispensavel": false
      },
      {
        "idJson": "69236cf2-73fa-4e84-85b6-088855029592",
        "estagio": {
          "idJson": "fcc3c63e-cb36-4966-9407-1b8bf30c3106"
        },
        "plano": {
          "idJson": "4e3c0565-954f-4586-bda1-7fc7a89dcdfd"
        },
        "posicao": 5,
        "tempoVerde": 10,
        "dispensavel": false
      },
      {
        "idJson": "d9ea17d8-0541-421a-bca9-1a485dc0f8b7",
        "estagio": {
          "idJson": "bd3d834a-d83c-4ac0-b52a-04c2bdbd34a7"
        },
        "plano": {
          "idJson": "4e3c0565-954f-4586-bda1-7fc7a89dcdfd"
        },
        "posicao": 4,
        "tempoVerde": 10,
        "dispensavel": false
      },
      {
        "idJson": "0e24cf0b-9f93-424b-8cd7-318a4c055c0f",
        "estagio": {
          "idJson": "8a1e2214-588c-465a-9e37-d838c56f2106"
        },
        "plano": {
          "idJson": "4e3c0565-954f-4586-bda1-7fc7a89dcdfd"
        },
        "posicao": 3,
        "tempoVerde": 10,
        "dispensavel": false
      },
      {
        "idJson": "0943e20f-0b6a-4d08-bfc7-aa8be7c1dfc5",
        "estagio": {
          "idJson": "73d11d5b-7d01-455c-9764-2e222b01bc8b"
        },
        "plano": {
          "idJson": "4e3c0565-954f-4586-bda1-7fc7a89dcdfd"
        },
        "posicao": 1,
        "tempoVerde": 10,
        "dispensavel": false
      },
      {
        "idJson": "34a16448-82f0-4bd0-9c9b-672d5b4bc62c",
        "estagio": {
          "idJson": "25dd2981-084e-4b00-be77-b92052c25502"
        },
        "plano": {
          "idJson": "b80ebfe7-fee4-477f-be85-f7cdbf6b15c0"
        },
        "posicao": 2,
        "tempoVerde": 10,
        "dispensavel": false
      },
      {
        "idJson": "8d1c63e1-e236-43ea-9b3f-de41c06d6fb2",
        "estagio": {
          "idJson": "fcc3c63e-cb36-4966-9407-1b8bf30c3106"
        },
        "plano": {
          "idJson": "b80ebfe7-fee4-477f-be85-f7cdbf6b15c0"
        },
        "posicao": 5,
        "tempoVerde": 10,
        "dispensavel": false
      },
      {
        "idJson": "d4484d46-479a-4897-82e9-7667d9f57312",
        "estagio": {
          "idJson": "bd3d834a-d83c-4ac0-b52a-04c2bdbd34a7"
        },
        "plano": {
          "idJson": "b80ebfe7-fee4-477f-be85-f7cdbf6b15c0"
        },
        "posicao": 4,
        "tempoVerde": 10,
        "dispensavel": false
      },
      {
        "idJson": "93e6d5e1-e80c-42bc-a8c3-4d2b07a599b2",
        "estagio": {
          "idJson": "8a1e2214-588c-465a-9e37-d838c56f2106"
        },
        "plano": {
          "idJson": "b80ebfe7-fee4-477f-be85-f7cdbf6b15c0"
        },
        "posicao": 3,
        "tempoVerde": 10,
        "dispensavel": false
      },
      {
        "idJson": "14db8e18-978c-4520-a9cb-1c4365452f9f",
        "estagio": {
          "idJson": "73d11d5b-7d01-455c-9764-2e222b01bc8b"
        },
        "plano": {
          "idJson": "b80ebfe7-fee4-477f-be85-f7cdbf6b15c0"
        },
        "posicao": 1,
        "tempoVerde": 10,
        "dispensavel": false
      },
      {
        "idJson": "d8089bf1-8605-46ed-8c1a-60af0e521145",
        "estagio": {
          "idJson": "25dd2981-084e-4b00-be77-b92052c25502"
        },
        "plano": {
          "idJson": "179a236e-119f-4e47-95cf-1e8c8988c265"
        },
        "posicao": 2,
        "tempoVerde": 10,
        "dispensavel": false
      },
      {
        "idJson": "c945db00-3d0a-49e1-956f-c7cfd6998cd3",
        "estagio": {
          "idJson": "fcc3c63e-cb36-4966-9407-1b8bf30c3106"
        },
        "plano": {
          "idJson": "179a236e-119f-4e47-95cf-1e8c8988c265"
        },
        "posicao": 5,
        "tempoVerde": 10,
        "dispensavel": false
      },
      {
        "idJson": "2a4594e0-7809-4ae3-a646-9554a867cf2e",
        "estagio": {
          "idJson": "bd3d834a-d83c-4ac0-b52a-04c2bdbd34a7"
        },
        "plano": {
          "idJson": "179a236e-119f-4e47-95cf-1e8c8988c265"
        },
        "posicao": 4,
        "tempoVerde": 10,
        "dispensavel": false
      },
      {
        "idJson": "804de185-09de-478a-a475-da1b1110bd0d",
        "estagio": {
          "idJson": "8a1e2214-588c-465a-9e37-d838c56f2106"
        },
        "plano": {
          "idJson": "179a236e-119f-4e47-95cf-1e8c8988c265"
        },
        "posicao": 3,
        "tempoVerde": 10,
        "dispensavel": false
      },
      {
        "idJson": "57aa4c80-32af-4293-b2d5-504242fc162b",
        "estagio": {
          "idJson": "73d11d5b-7d01-455c-9764-2e222b01bc8b"
        },
        "plano": {
          "idJson": "179a236e-119f-4e47-95cf-1e8c8988c265"
        },
        "posicao": 1,
        "tempoVerde": 10,
        "dispensavel": false
      },
      {
        "idJson": "5b453258-9c0d-4cbe-8a1d-4cc9f74a03c1",
        "estagio": {
          "idJson": "25dd2981-084e-4b00-be77-b92052c25502"
        },
        "plano": {
          "idJson": "aa49b13d-e7c0-4c65-8985-9f371dd53bfd"
        },
        "posicao": 2,
        "tempoVerde": 10,
        "dispensavel": false
      },
      {
        "idJson": "67d80596-9736-4056-8ba5-820777c03de5",
        "estagio": {
          "idJson": "fcc3c63e-cb36-4966-9407-1b8bf30c3106"
        },
        "plano": {
          "idJson": "aa49b13d-e7c0-4c65-8985-9f371dd53bfd"
        },
        "posicao": 5,
        "tempoVerde": 10,
        "dispensavel": false
      },
      {
        "idJson": "36f931e4-622b-4598-b5c8-90ec4f54421f",
        "estagio": {
          "idJson": "bd3d834a-d83c-4ac0-b52a-04c2bdbd34a7"
        },
        "plano": {
          "idJson": "aa49b13d-e7c0-4c65-8985-9f371dd53bfd"
        },
        "posicao": 4,
        "tempoVerde": 10,
        "dispensavel": false
      },
      {
        "idJson": "c4bbb3a0-7e23-40b2-9bdb-4814ba78dabc",
        "estagio": {
          "idJson": "8a1e2214-588c-465a-9e37-d838c56f2106"
        },
        "plano": {
          "idJson": "aa49b13d-e7c0-4c65-8985-9f371dd53bfd"
        },
        "posicao": 3,
        "tempoVerde": 10,
        "dispensavel": false
      },
      {
        "idJson": "15c2f48e-3cc9-4280-b1d7-f7b399049262",
        "estagio": {
          "idJson": "73d11d5b-7d01-455c-9764-2e222b01bc8b"
        },
        "plano": {
          "idJson": "aa49b13d-e7c0-4c65-8985-9f371dd53bfd"
        },
        "posicao": 1,
        "tempoVerde": 10,
        "dispensavel": false
      },
      {
        "idJson": "22eea508-b1d1-4160-9206-bf1a0e189e06",
        "estagio": {
          "idJson": "25dd2981-084e-4b00-be77-b92052c25502"
        },
        "plano": {
          "idJson": "0ef18b30-6185-4af9-be67-4382bb721c14"
        },
        "posicao": 2,
        "tempoVerde": 10,
        "dispensavel": false
      },
      {
        "idJson": "774d7c93-db3b-42bb-884a-49c813a9deb6",
        "estagio": {
          "idJson": "fcc3c63e-cb36-4966-9407-1b8bf30c3106"
        },
        "plano": {
          "idJson": "0ef18b30-6185-4af9-be67-4382bb721c14"
        },
        "posicao": 5,
        "tempoVerde": 10,
        "dispensavel": false
      },
      {
        "idJson": "42298636-8441-44db-a813-a8e393c1eefe",
        "estagio": {
          "idJson": "bd3d834a-d83c-4ac0-b52a-04c2bdbd34a7"
        },
        "plano": {
          "idJson": "0ef18b30-6185-4af9-be67-4382bb721c14"
        },
        "posicao": 4,
        "tempoVerde": 10,
        "dispensavel": false
      },
      {
        "idJson": "86520062-84a8-4e9a-b4af-ffb1ac25c66b",
        "estagio": {
          "idJson": "8a1e2214-588c-465a-9e37-d838c56f2106"
        },
        "plano": {
          "idJson": "0ef18b30-6185-4af9-be67-4382bb721c14"
        },
        "posicao": 3,
        "tempoVerde": 10,
        "dispensavel": false
      },
      {
        "idJson": "100fc407-9087-4be8-981f-ed231b49791b",
        "estagio": {
          "idJson": "73d11d5b-7d01-455c-9764-2e222b01bc8b"
        },
        "plano": {
          "idJson": "0ef18b30-6185-4af9-be67-4382bb721c14"
        },
        "posicao": 1,
        "tempoVerde": 10,
        "dispensavel": false
      },
      {
        "idJson": "f16163b1-b738-4836-988c-dc2151216f4e",
        "estagio": {
          "idJson": "25dd2981-084e-4b00-be77-b92052c25502"
        },
        "plano": {
          "idJson": "3bb2b8b8-450f-40ae-a31b-096d327bade6"
        },
        "posicao": 2,
        "tempoVerde": 10,
        "dispensavel": false
      },
      {
        "idJson": "31b40527-6843-4608-b563-50f09fd10009",
        "estagio": {
          "idJson": "fcc3c63e-cb36-4966-9407-1b8bf30c3106"
        },
        "plano": {
          "idJson": "3bb2b8b8-450f-40ae-a31b-096d327bade6"
        },
        "posicao": 5,
        "tempoVerde": 10,
        "dispensavel": false
      },
      {
        "idJson": "e0468356-9241-467f-a82f-cb9e759ccfd3",
        "estagio": {
          "idJson": "bd3d834a-d83c-4ac0-b52a-04c2bdbd34a7"
        },
        "plano": {
          "idJson": "3bb2b8b8-450f-40ae-a31b-096d327bade6"
        },
        "posicao": 4,
        "tempoVerde": 10,
        "dispensavel": false
      },
      {
        "idJson": "8946b6a4-7e49-41e2-a974-22f391e1b81f",
        "estagio": {
          "idJson": "8a1e2214-588c-465a-9e37-d838c56f2106"
        },
        "plano": {
          "idJson": "3bb2b8b8-450f-40ae-a31b-096d327bade6"
        },
        "posicao": 3,
        "tempoVerde": 10,
        "dispensavel": false
      },
      {
        "idJson": "9ca5b951-6fb4-4fe6-8ea0-94f3b927f5a0",
        "estagio": {
          "idJson": "73d11d5b-7d01-455c-9764-2e222b01bc8b"
        },
        "plano": {
          "idJson": "3bb2b8b8-450f-40ae-a31b-096d327bade6"
        },
        "posicao": 1,
        "tempoVerde": 10,
        "dispensavel": false
      },
      {
        "idJson": "88c91d69-e6a6-4c59-90d2-81b355a852d1",
        "estagio": {
          "idJson": "25dd2981-084e-4b00-be77-b92052c25502"
        },
        "plano": {
          "idJson": "9bd9461c-7792-4aa2-a8a6-805945607a8d"
        },
        "posicao": 2,
        "tempoVerde": 10,
        "dispensavel": false
      },
      {
        "idJson": "59655463-4847-4cd0-937d-2f6f9d159c78",
        "estagio": {
          "idJson": "fcc3c63e-cb36-4966-9407-1b8bf30c3106"
        },
        "plano": {
          "idJson": "9bd9461c-7792-4aa2-a8a6-805945607a8d"
        },
        "posicao": 5,
        "tempoVerde": 10,
        "dispensavel": false
      },
      {
        "idJson": "1f28795d-2517-4362-a7d7-0196d0c21ff2",
        "estagio": {
          "idJson": "bd3d834a-d83c-4ac0-b52a-04c2bdbd34a7"
        },
        "plano": {
          "idJson": "9bd9461c-7792-4aa2-a8a6-805945607a8d"
        },
        "posicao": 4,
        "tempoVerde": 10,
        "dispensavel": false
      },
      {
        "idJson": "105060b9-04ab-46d4-980a-11d100dacc76",
        "estagio": {
          "idJson": "8a1e2214-588c-465a-9e37-d838c56f2106"
        },
        "plano": {
          "idJson": "9bd9461c-7792-4aa2-a8a6-805945607a8d"
        },
        "posicao": 3,
        "tempoVerde": 10,
        "dispensavel": false
      },
      {
        "idJson": "9c90f3ca-784e-4d26-be11-1451ee30b8ca",
        "estagio": {
          "idJson": "73d11d5b-7d01-455c-9764-2e222b01bc8b"
        },
        "plano": {
          "idJson": "9bd9461c-7792-4aa2-a8a6-805945607a8d"
        },
        "posicao": 1,
        "tempoVerde": 10,
        "dispensavel": false
      },
      {
        "idJson": "7b648c8d-1a00-41f0-b382-9cdbe45057f2",
        "estagio": {
          "idJson": "25dd2981-084e-4b00-be77-b92052c25502"
        },
        "plano": {
          "idJson": "9a13a395-cc26-4210-a84a-cc950a69ac4c"
        },
        "posicao": 2,
        "tempoVerde": 10,
        "dispensavel": false
      },
      {
        "idJson": "8dfd44d9-10b2-4813-8881-9ae72c71ef8d",
        "estagio": {
          "idJson": "fcc3c63e-cb36-4966-9407-1b8bf30c3106"
        },
        "plano": {
          "idJson": "9a13a395-cc26-4210-a84a-cc950a69ac4c"
        },
        "posicao": 5,
        "tempoVerde": 10,
        "dispensavel": false
      },
      {
        "idJson": "2daae396-dd34-4e78-8608-b509bd71f610",
        "estagio": {
          "idJson": "bd3d834a-d83c-4ac0-b52a-04c2bdbd34a7"
        },
        "plano": {
          "idJson": "9a13a395-cc26-4210-a84a-cc950a69ac4c"
        },
        "posicao": 4,
        "tempoVerde": 10,
        "dispensavel": false
      },
      {
        "idJson": "466bc3ae-833e-4687-b878-972b8df375cb",
        "estagio": {
          "idJson": "8a1e2214-588c-465a-9e37-d838c56f2106"
        },
        "plano": {
          "idJson": "9a13a395-cc26-4210-a84a-cc950a69ac4c"
        },
        "posicao": 3,
        "tempoVerde": 10,
        "dispensavel": false
      },
      {
        "idJson": "efb73e51-69bc-4be3-8403-0132cb9256e1",
        "estagio": {
          "idJson": "73d11d5b-7d01-455c-9764-2e222b01bc8b"
        },
        "plano": {
          "idJson": "9a13a395-cc26-4210-a84a-cc950a69ac4c"
        },
        "posicao": 1,
        "tempoVerde": 10,
        "dispensavel": false
      },
      {
        "idJson": "763871ad-eece-4ddd-bd14-f4954b28e618",
        "estagio": {
          "idJson": "25dd2981-084e-4b00-be77-b92052c25502"
        },
        "plano": {
          "idJson": "75d979f9-9d31-4d8f-be82-cb92c36a360d"
        },
        "posicao": 2,
        "tempoVerde": 10,
        "dispensavel": false
      },
      {
        "idJson": "3ea6a7e6-da29-422f-97e8-224330a9b81d",
        "estagio": {
          "idJson": "fcc3c63e-cb36-4966-9407-1b8bf30c3106"
        },
        "plano": {
          "idJson": "75d979f9-9d31-4d8f-be82-cb92c36a360d"
        },
        "posicao": 5,
        "tempoVerde": 10,
        "dispensavel": false
      },
      {
        "idJson": "7cad5eec-2e33-4e69-8f4e-fcadad66f250",
        "estagio": {
          "idJson": "bd3d834a-d83c-4ac0-b52a-04c2bdbd34a7"
        },
        "plano": {
          "idJson": "75d979f9-9d31-4d8f-be82-cb92c36a360d"
        },
        "posicao": 4,
        "tempoVerde": 10,
        "dispensavel": false
      },
      {
        "idJson": "41116921-843e-4046-b002-88630c2b98c8",
        "estagio": {
          "idJson": "8a1e2214-588c-465a-9e37-d838c56f2106"
        },
        "plano": {
          "idJson": "75d979f9-9d31-4d8f-be82-cb92c36a360d"
        },
        "posicao": 3,
        "tempoVerde": 10,
        "dispensavel": false
      },
      {
        "idJson": "d68cb97f-2a00-42c8-a7df-87a22da58320",
        "estagio": {
          "idJson": "73d11d5b-7d01-455c-9764-2e222b01bc8b"
        },
        "plano": {
          "idJson": "75d979f9-9d31-4d8f-be82-cb92c36a360d"
        },
        "posicao": 1,
        "tempoVerde": 10,
        "dispensavel": false
      },
      {
        "idJson": "01749fa5-ffa9-4284-9c8c-a591360bb026",
        "estagio": {
          "idJson": "25dd2981-084e-4b00-be77-b92052c25502"
        },
        "plano": {
          "idJson": "26307aa2-23c7-4711-82b5-1cd3780c33d0"
        },
        "posicao": 2,
        "tempoVerde": 10,
        "dispensavel": false
      },
      {
        "idJson": "e459ff60-6758-4afe-8aa1-fa0a98efb7fb",
        "estagio": {
          "idJson": "fcc3c63e-cb36-4966-9407-1b8bf30c3106"
        },
        "plano": {
          "idJson": "26307aa2-23c7-4711-82b5-1cd3780c33d0"
        },
        "posicao": 5,
        "tempoVerde": 10,
        "dispensavel": false
      },
      {
        "idJson": "4f2f402c-124d-4c70-8569-9c0a44a38fdc",
        "estagio": {
          "idJson": "bd3d834a-d83c-4ac0-b52a-04c2bdbd34a7"
        },
        "plano": {
          "idJson": "26307aa2-23c7-4711-82b5-1cd3780c33d0"
        },
        "posicao": 4,
        "tempoVerde": 10,
        "dispensavel": false
      },
      {
        "idJson": "57478df6-1f05-4516-8048-b25629f75403",
        "estagio": {
          "idJson": "8a1e2214-588c-465a-9e37-d838c56f2106"
        },
        "plano": {
          "idJson": "26307aa2-23c7-4711-82b5-1cd3780c33d0"
        },
        "posicao": 3,
        "tempoVerde": 10,
        "dispensavel": false
      },
      {
        "idJson": "e4d33149-394f-4be1-b261-aa6409c13dec",
        "estagio": {
          "idJson": "73d11d5b-7d01-455c-9764-2e222b01bc8b"
        },
        "plano": {
          "idJson": "26307aa2-23c7-4711-82b5-1cd3780c33d0"
        },
        "posicao": 1,
        "tempoVerde": 10,
        "dispensavel": false
      },
      {
        "idJson": "5c1cd917-6fc4-4ec4-a33c-81481a8c69ee",
        "estagio": {
          "idJson": "25dd2981-084e-4b00-be77-b92052c25502"
        },
        "plano": {
          "idJson": "106b7f1d-a993-43a1-bb65-57b743379fcd"
        },
        "posicao": 2,
        "tempoVerde": 10,
        "dispensavel": false
      },
      {
        "idJson": "121e98e4-e13b-472d-a229-a3aa1c5b76aa",
        "estagio": {
          "idJson": "fcc3c63e-cb36-4966-9407-1b8bf30c3106"
        },
        "plano": {
          "idJson": "106b7f1d-a993-43a1-bb65-57b743379fcd"
        },
        "posicao": 5,
        "tempoVerde": 10,
        "dispensavel": false
      },
      {
        "idJson": "9da83fb9-1764-4668-b3a5-9bb48c983c69",
        "estagio": {
          "idJson": "bd3d834a-d83c-4ac0-b52a-04c2bdbd34a7"
        },
        "plano": {
          "idJson": "106b7f1d-a993-43a1-bb65-57b743379fcd"
        },
        "posicao": 4,
        "tempoVerde": 10,
        "dispensavel": false
      },
      {
        "idJson": "c6fbf5c6-8b35-41ed-ad94-953e14f38fec",
        "estagio": {
          "idJson": "8a1e2214-588c-465a-9e37-d838c56f2106"
        },
        "plano": {
          "idJson": "106b7f1d-a993-43a1-bb65-57b743379fcd"
        },
        "posicao": 3,
        "tempoVerde": 10,
        "dispensavel": false
      },
      {
        "idJson": "113e637c-0174-4964-816e-0cd381bde0cc",
        "estagio": {
          "idJson": "73d11d5b-7d01-455c-9764-2e222b01bc8b"
        },
        "plano": {
          "idJson": "106b7f1d-a993-43a1-bb65-57b743379fcd"
        },
        "posicao": 1,
        "tempoVerde": 10,
        "dispensavel": false
      },
      {
        "idJson": "77a029f3-7b4e-4a7f-82c3-15dd5b2e2df7",
        "estagio": {
          "idJson": "25dd2981-084e-4b00-be77-b92052c25502"
        },
        "plano": {
          "idJson": "68ba2551-67fb-40fd-92df-b7a5ad5b053d"
        },
        "posicao": 2,
        "tempoVerde": 10,
        "dispensavel": false
      },
      {
        "idJson": "bd94ba16-5c56-4e54-938d-d45672a232b0",
        "estagio": {
          "idJson": "fcc3c63e-cb36-4966-9407-1b8bf30c3106"
        },
        "plano": {
          "idJson": "68ba2551-67fb-40fd-92df-b7a5ad5b053d"
        },
        "posicao": 5,
        "tempoVerde": 10,
        "dispensavel": false
      },
      {
        "idJson": "3138331e-2f9a-4ea8-a2dc-56708caa7036",
        "estagio": {
          "idJson": "bd3d834a-d83c-4ac0-b52a-04c2bdbd34a7"
        },
        "plano": {
          "idJson": "68ba2551-67fb-40fd-92df-b7a5ad5b053d"
        },
        "posicao": 4,
        "tempoVerde": 10,
        "dispensavel": false
      },
      {
        "idJson": "9faa3bf4-8f55-42f2-b85f-ef3c00b29875",
        "estagio": {
          "idJson": "8a1e2214-588c-465a-9e37-d838c56f2106"
        },
        "plano": {
          "idJson": "68ba2551-67fb-40fd-92df-b7a5ad5b053d"
        },
        "posicao": 3,
        "tempoVerde": 10,
        "dispensavel": false
      },
      {
        "idJson": "a82823bd-306e-4932-a132-2c17bcc6ce81",
        "estagio": {
          "idJson": "73d11d5b-7d01-455c-9764-2e222b01bc8b"
        },
        "plano": {
          "idJson": "68ba2551-67fb-40fd-92df-b7a5ad5b053d"
        },
        "posicao": 1,
        "tempoVerde": 10,
        "dispensavel": false
      }
    ],
    "cidades": [
      {
        "id": "66b66819-a1c4-11e6-970d-0401fa9c1b01",
        "idJson": "66b6941e-a1c4-11e6-970d-0401fa9c1b01",
        "nome": "São Paulo",
        "areas": [
          {
            "idJson": "66b6a0c4-a1c4-11e6-970d-0401fa9c1b01"
          }
        ]
      }
    ],
    "areas": [
      {
        "id": "66b66a46-a1c4-11e6-970d-0401fa9c1b01",
        "idJson": "66b6a0c4-a1c4-11e6-970d-0401fa9c1b01",
        "descricao": 1,
        "cidade": {
          "idJson": "66b6941e-a1c4-11e6-970d-0401fa9c1b01"
        },
        "limites": [],
        "subareas": []
      }
    ],
    "limites": [],
    "todosEnderecos": [
      {
        "id": "66253408-7a21-4ac6-98ec-f27eccd58362",
        "idJson": "34de0938-8610-4daa-88ae-cbee4df8890a",
        "localizacao": "Rua Paul Bouthilier",
        "latitude": -19.9513211,
        "longitude": -43.921468600000026,
        "localizacao2": "",
        "alturaNumerica": 266
      },
      {
        "id": "695f004c-5712-4667-8ec8-6ab86b3ea51d",
        "idJson": "37608afb-115b-4bb6-a930-4a31b6e1715b",
        "localizacao": "Rua Paul Bouthilier",
        "latitude": -19.9513211,
        "longitude": -43.921468600000026,
        "localizacao2": "",
        "alturaNumerica": 266
      }
    ],
    "imagens": [
      {
        "id": "fab1e63f-a4be-4f09-8f8f-4e9305bd03fb",
        "idJson": "c5700a11-20cf-481a-9d62-584708a6da53",
        "fileName": "004.jpg",
        "contentType": "image/jpeg"
      },
      {
        "id": "a312d88d-53d9-4a74-82bf-1d3eb90446fc",
        "idJson": "fc8917bb-87e1-442c-ae67-ed91b9ac81cf",
        "fileName": "003.jpg",
        "contentType": "image/jpeg"
      },
      {
        "id": "bf1479f6-3ac8-44c4-92e8-eeb9065e8bf9",
        "idJson": "b1a69915-2818-4784-b5e8-a0f45632eef8",
        "fileName": "010.jpg",
        "contentType": "image/jpeg"
      },
      {
        "id": "60a24530-c6d2-49e0-a9c3-06de765634b6",
        "idJson": "67e2c36a-9d4c-4ea6-af13-4ab9d50f0b6c",
        "fileName": "006.jpg",
        "contentType": "image/jpeg"
      },
      {
        "id": "454eb503-95e6-499d-8ce7-a859ce865a72",
        "idJson": "7d3df30d-39d0-4d82-bc5a-45df9dea8b1f",
        "fileName": "005.jpg",
        "contentType": "image/jpeg"
      }
    ],
    "atrasosDeGrupo": [
      {
        "id": "bc7d5f1d-1509-4977-9ec7-f2afa8ace168",
        "idJson": "5e7d2172-1133-4755-a24a-ae3f3df52a97",
        "atrasoDeGrupo": 0
      },
      {
        "id": "5395e796-2558-4d17-9881-af219bc5e18b",
        "idJson": "5b4b8692-eae6-4b5d-bbad-c6019644354f",
        "atrasoDeGrupo": 5
      },
      {
        "id": "ea03f174-91fb-409c-98c9-8c40a172b822",
        "idJson": "2d59e869-62a3-4d30-acb6-ef07a99905dd",
        "atrasoDeGrupo": 5
      },
      {
        "id": "35dfeddf-f4b5-44a9-b518-89e5ce4e8e66",
        "idJson": "479147e5-65fa-4e81-b12e-69f4b86006ff",
        "atrasoDeGrupo": 5
      },
      {
        "id": "8a05eafb-fb81-4f34-afc4-ea531134f9fb",
        "idJson": "063c19fb-f778-41a6-b8f6-0740d20c0fbb",
        "atrasoDeGrupo": 5
      },
      {
        "id": "05ad60f1-5087-4e29-ba28-dd5d8d2ccc23",
        "idJson": "c53c83a1-fadb-42e4-9d06-e144e5510c56",
        "atrasoDeGrupo": 5
      },
      {
        "id": "3e0fd25b-2114-4ae5-8bc0-b8f889028842",
        "idJson": "3586b29d-82c8-4418-96de-6939cb4f2e19",
        "atrasoDeGrupo": 0
      },
      {
        "id": "1f56a3ea-60f5-479c-a437-205f5e54a7eb",
        "idJson": "ae763424-11a8-4c99-9e50-4c6c862ac633",
        "atrasoDeGrupo": 0
      },
      {
        "id": "cefc606a-fb90-4aee-beec-6e19094a4102",
        "idJson": "da63d11d-6028-4852-99bd-2ef8f7ba3e7f",
        "atrasoDeGrupo": 5
      },
      {
        "id": "71a3a8fa-e7ea-4406-8758-285c4ae5a12c",
        "idJson": "32555598-915c-47d3-95e6-c13df01179f4",
        "atrasoDeGrupo": 0
      },
      {
        "id": "18d5dfd3-96e8-4195-bac7-8d90fa76e29c",
        "idJson": "e1f85131-1cca-4111-a6cf-20fe56ce1429",
        "atrasoDeGrupo": 5
      },
      {
        "id": "267a65c6-4650-45e4-b63b-4c97d15002dc",
        "idJson": "83a116bc-6309-4db7-8d73-f06eec6e3058",
        "atrasoDeGrupo": 5
      },
      {
        "id": "cf5041a9-dc1e-4f34-833c-aa882e61522b",
        "idJson": "4a433770-bfe9-4154-bc23-ce174da513cf",
        "atrasoDeGrupo": 0
      },
      {
        "id": "8583698b-452d-4298-b7de-ce88deebb2b1",
        "idJson": "143faebf-8c4a-4b4d-b897-f7cdf85dbc48",
        "atrasoDeGrupo": 5
      },
      {
        "id": "1707329b-9116-4e8f-9342-88f65d587a12",
        "idJson": "492baba5-779c-4b2f-8b94-f388dcd763b4",
        "atrasoDeGrupo": 0
      },
      {
        "id": "ed7ed73a-a509-45a6-a4d9-c93141166da8",
        "idJson": "60d6eb36-4d5e-44e7-9c28-d9e3ca19ad8c",
        "atrasoDeGrupo": 5
      },
      {
        "id": "07c220df-5146-4930-8eb8-2807af6d6278",
        "idJson": "b500d312-a06d-4be5-b6aa-bc96eabe57d3",
        "atrasoDeGrupo": 5
      },
      {
        "id": "ec42d256-b8b2-41db-a3e5-3cfcd7c608a9",
        "idJson": "b45bec75-0bda-4adf-adb7-8522fc465dd2",
        "atrasoDeGrupo": 5
      },
      {
        "id": "4e6c403b-c7bb-463b-ad3b-c3a411f02364",
        "idJson": "9d92155c-37e4-4532-a243-a8281a79726a",
        "atrasoDeGrupo": 0
      },
      {
        "id": "acd2a9de-d7ac-468d-8439-e5ababd15eac",
        "idJson": "c716fd33-ff9e-4abd-8932-08bd9170f7e5",
        "atrasoDeGrupo": 5
      },
      {
        "id": "77ce1530-9e24-4706-9f15-20a680387e2b",
        "idJson": "8de1d5f0-727a-4293-8c82-4ebb30928fb6",
        "atrasoDeGrupo": 0
      },
      {
        "id": "3ac59bf5-42dc-4d3a-ad35-f52cb5b7283c",
        "idJson": "0081b2fb-9a15-402c-890c-a2d77947cbde",
        "atrasoDeGrupo": 0
      },
      {
        "id": "497ca0fe-f203-4d10-bd7d-de93ab4939bc",
        "idJson": "a28405dc-2ab2-463a-856b-31266b6f6582",
        "atrasoDeGrupo": 5
      },
      {
        "id": "8d53e547-4348-4d85-9d79-099260abb4af",
        "idJson": "ea15b4ac-56d6-4f2e-9a0c-fbb0f52750ec",
        "atrasoDeGrupo": 0
      },
      {
        "id": "f80f19c9-361a-45e5-a16d-d33986501122",
        "idJson": "3e7c2ed7-3609-4fed-b99c-b66227561b5e",
        "atrasoDeGrupo": 5
      },
      {
        "id": "faa39bd6-bdb0-458b-b74d-23b029438642",
        "idJson": "08143eb2-6fc5-4af2-be1c-edd7d2b44b80",
        "atrasoDeGrupo": 0
      },
      {
        "id": "3b4d1d9a-39cf-40d1-9fa5-6d0a442aaa43",
        "idJson": "8bb5a33c-1e51-45bd-b484-b2665d043380",
        "atrasoDeGrupo": 0
      },
      {
        "id": "7ce1bb65-65aa-4e54-8730-42450284f3d0",
        "idJson": "abb98a14-c2ca-4f3a-9b58-820ef29699c8",
        "atrasoDeGrupo": 5
      },
      {
        "id": "c5460bf2-ac3a-4057-8596-a87c85acd99b",
        "idJson": "d6fee3eb-6ba5-4a66-b557-1b1fbf46e3a5",
        "atrasoDeGrupo": 5
      },
      {
        "id": "bfbed5cf-9436-4d67-88d9-c7e1f1d2fe60",
        "idJson": "18c2b66b-578d-4c5e-9ba4-dc8b1fb8f526",
        "atrasoDeGrupo": 0
      },
      {
        "id": "c0a98d85-6754-4da8-81d4-87bc2cacc480",
        "idJson": "1fe95f5e-9046-43e0-af58-b76ba0649bfe",
        "atrasoDeGrupo": 5
      },
      {
        "id": "177ac49a-1071-40c8-9bd9-7980411fabda",
        "idJson": "dc289c77-b5ac-4870-9812-c7f032ada3bb",
        "atrasoDeGrupo": 5
      },
      {
        "id": "ef539037-c5de-4b4e-919a-9a2f148ef73f",
        "idJson": "8414673f-59aa-4134-84f0-a7acd1ed025f",
        "atrasoDeGrupo": 5
      },
      {
        "id": "f7d2d989-65d2-4963-a810-4360a031cb2b",
        "idJson": "c70d4cd2-c892-464a-a57a-08b11677d96c",
        "atrasoDeGrupo": 0
      },
      {
        "id": "6e161f9f-f2eb-4298-a234-ce32caff484e",
        "idJson": "56e0b3e5-c278-4804-a4cf-b4e97a58f299",
        "atrasoDeGrupo": 0
      },
      {
        "id": "7ed2402d-cbd0-4760-950b-5f8ae188dff5",
        "idJson": "e95f208a-2fe4-4ae9-8579-9400517b08e1",
        "atrasoDeGrupo": 0
      },
      {
        "id": "845af5cc-1739-4ee8-95f3-31b4e3932c8e",
        "idJson": "df2ba05a-f3d8-4982-9bfa-a23d3aca3d67",
        "atrasoDeGrupo": 0
      },
      {
        "id": "97c90cc0-4fb5-4dc3-8e86-74ec7ccdc5d7",
        "idJson": "9f442c61-4c5e-4798-97e9-9162808af25f",
        "atrasoDeGrupo": 0
      },
      {
        "id": "6904b78e-e442-4e93-abbc-d5ce85d3ea37",
        "idJson": "b1241cd6-20f5-4123-b18e-5abe1201a16c",
        "atrasoDeGrupo": 0
      },
      {
        "id": "a3a76655-3794-43cf-9c16-ca99a8f0706e",
        "idJson": "7d3b46e1-4397-4f17-a1e5-7f9421ac9d09",
        "atrasoDeGrupo": 0
      }
    ],
    "statusVersao": "EM_CONFIGURACAO",
    "versaoControlador": {
      "id": "752d0d7c-ac11-418b-bcb7-db7d5a2d5112",
      "idJson": null,
      "descricao": "Controlador criado pelo usuário: paulo",
      "usuario": {
        "id": "d348b85a-ceb0-49a9-9dcb-d54126db8bae",
        "nome": "paulo",
        "login": "paulo",
        "email": "paulo@rarolabs.com.br",
        "area": {
          "idJson": "66b6a0c4-a1c4-11e6-970d-0401fa9c1b01"
        }
      }
    },
    "versoesPlanos": [
      {
        "idJson": "8005179f-4974-454e-a900-cdcf3bd0c58f",
        "anel": {
          "idJson": "c6787208-ede2-4b5f-b857-efa5ad97f0ae"
        },
        "planos": [
          {
            "idJson": "d89e7a44-dfc5-47a7-bdde-11820f06749f"
          },
          {
            "idJson": "bd7ba623-c2e7-49ee-a306-4353f5513a7c"
          },
          {
            "idJson": "216d0496-f7b6-4038-95b7-1975218e050a"
          },
          {
            "idJson": "986d4974-baad-4c9d-ad93-ec9a23a982ef"
          },
          {
            "idJson": "f9ecbfed-2372-4a5c-a3bf-555dde6bdec5"
          },
          {
            "idJson": "4e3c0565-954f-4586-bda1-7fc7a89dcdfd"
          },
          {
            "idJson": "b80ebfe7-fee4-477f-be85-f7cdbf6b15c0"
          },
          {
            "idJson": "179a236e-119f-4e47-95cf-1e8c8988c265"
          },
          {
            "idJson": "aa49b13d-e7c0-4c65-8985-9f371dd53bfd"
          },
          {
            "idJson": "0ef18b30-6185-4af9-be67-4382bb721c14"
          },
          {
            "idJson": "3bb2b8b8-450f-40ae-a31b-096d327bade6"
          },
          {
            "idJson": "9bd9461c-7792-4aa2-a8a6-805945607a8d"
          },
          {
            "idJson": "9a13a395-cc26-4210-a84a-cc950a69ac4c"
          },
          {
            "idJson": "75d979f9-9d31-4d8f-be82-cb92c36a360d"
          },
          {
            "idJson": "26307aa2-23c7-4711-82b5-1cd3780c33d0"
          },
          {
            "idJson": "106b7f1d-a993-43a1-bb65-57b743379fcd"
          },
          {
            "idJson": "68ba2551-67fb-40fd-92df-b7a5ad5b053d"
          }
        ]
      }
    ],
    "tabelasHorarias": [],
    "eventos": []
  },
  getControladorId: function() {
    return this.obj.id;
  },
  get: function() {
    return _.cloneDeep(this.obj);
  }
};
