'use strict';

/**
 * Conjunto de funções auxiliares utilizadas pelos testes do wizard de controladores.
 *
 * @type       {<type>}
 */
var ControladorAtrasoGrupoAutomatico = {
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
    "vermelhoLimpezaVeicularMax": "7",
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
        "planos": [],
        "endereco": {
          "idJson": "37608afb-115b-4bb6-a930-4a31b6e1715b"
        }
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
          "idJson": "fc8917bb-87e1-442c-ae67-ed91b9ac81cf"
        },
        "origemDeTransicoesProibidas": [],
        "destinoDeTransicoesProibidas": [],
        "alternativaDeTransicoesProibidas": [],
        "estagiosGruposSemaforicos": [
          {
            "idJson": "f323958e-1a61-47f9-a3b8-18777a600d90"
          }
        ]
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
          "idJson": "c5700a11-20cf-481a-9d62-584708a6da53"
        },
        "origemDeTransicoesProibidas": [],
        "destinoDeTransicoesProibidas": [],
        "alternativaDeTransicoesProibidas": [],
        "estagiosGruposSemaforicos": [
          {
            "idJson": "ca67eae1-4a33-4afe-8cc9-6c753ed82a0a"
          }
        ]
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
          "idJson": "67e2c36a-9d4c-4ea6-af13-4ab9d50f0b6c"
        },
        "origemDeTransicoesProibidas": [],
        "destinoDeTransicoesProibidas": [],
        "alternativaDeTransicoesProibidas": [],
        "estagiosGruposSemaforicos": [
          {
            "idJson": "4a7739ff-09ce-49dd-9011-cbfc3db6d419"
          }
        ]
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
          "idJson": "b1a69915-2818-4784-b5e8-a0f45632eef8"
        },
        "origemDeTransicoesProibidas": [],
        "destinoDeTransicoesProibidas": [],
        "alternativaDeTransicoesProibidas": [],
        "estagiosGruposSemaforicos": [
          {
            "idJson": "e5f2b2df-7884-420b-8cdb-8b5ca7774f34"
          }
        ]
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
          "idJson": "7d3df30d-39d0-4d82-bc5a-45df9dea8b1f"
        },
        "origemDeTransicoesProibidas": [],
        "destinoDeTransicoesProibidas": [],
        "alternativaDeTransicoesProibidas": [],
        "estagiosGruposSemaforicos": [
          {
            "idJson": "cad71e0d-a531-466a-aa3b-0513acc221b7"
          }
        ]
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
        ]
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
        ]
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
        ]
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
        ]
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
        ]
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
        ]
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
    "planos": [],
    "gruposSemaforicosPlanos": [],
    "estagiosPlanos": [],
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
    "versoesPlanos": [],
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
