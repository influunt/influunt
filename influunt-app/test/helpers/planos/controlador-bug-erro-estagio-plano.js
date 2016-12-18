'use strict';

/**
 * Conjunto de funções auxiliares utilizadas pelos testes do wizard de controladores.
 *
 * @type       {<type>}
 */
var ControladorBugErroEstagioPlano = {
  obj: {
      id: 'd06c64f6-018e-49a7-b407-5f81ebc31dd5',
      versoesTabelasHoraria: [{
        id: '221141a1-98d5-4e54-8fea-d2e599537646',
        idJson: '087127a1-b4ce-4c5a-9476-e2e8a4979bfb',
        statusVersao: 'CONFIGURADO',
        tabelaHoraria: {
          idJson: '4dcd3e6d-f46a-4f74-8100-a9e4b988ee94'
        },
        dataCriacao: '16/12/2016 22:29:07',
        dataAtualizacao: '16/12/2016 22:29:59',
        usuario: {
          nome: 'Administrador Geral'
        }
      }],
      controladorFisicoId: '67a60509-9a4c-4c3e-a367-db56892eaa52',
      numeroSMEE: '1234',
      firmware: 'null',
      sequencia: 1,
      limiteEstagio: 16,
      limiteGrupoSemaforico: 16,
      limiteAnel: 4,
      limiteDetectorPedestre: 4,
      limiteDetectorVeicular: 8,
      limiteTabelasEntreVerdes: 2,
      limitePlanos: 16,
      nomeEndereco: 'Av. Paulista, nº 345',
      dataCriacao: '16/12/2016 22:14:37',
      dataAtualizacao: '16/12/2016 22:32:54',
      CLC: '1.000.0001',
      bloqueado: false,
      planosBloqueado: false,
      exclusivoParaTeste: false,
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
      vermelhoLimpezaVeicularMax: '20',
      vermelhoLimpezaPedestreMin: '0',
      vermelhoLimpezaPedestreMax: '5',
      atrasoGrupoMin: '0',
      atrasoGrupoMax: '20',
      verdeSegurancaVeicularMin: '10',
      verdeSegurancaVeicularMax: '30',
      verdeSegurancaPedestreMin: '4',
      verdeSegurancaPedestreMax: '10',
      maximoPermanenciaEstagioMin: '20',
      maximoPermanenciaEstagioMax: '255',
      defaultMaximoPermanenciaEstagioVeicular: 127,
      cicloMin: '30',
      cicloMax: '255',
      ausenciaDeteccaoMin: '0',
      ausenciaDeteccaoMax: '5800',
      deteccaoPermanenteMin: '0',
      deteccaoPermanenteMax: '10',
      statusControlador: 'SINCRONIZADO',
      statusControladorReal: 'SINCRONIZADO',
      area: {
        idJson: 'a76fa8ea-c3ed-11e6-ab15-0401fa4eb401'
      },
      endereco: {
        idJson: 'e874b569-f960-4776-a8ce-02efc2db2e90'
      },
      modelo: {
        id: 'a771d14f-c3ed-11e6-ab15-0401fa4eb401',
        idJson: 'a771d1a2-c3ed-11e6-ab15-0401fa4eb401',
        descricao: 'Modelo Básico',
        fabricante: {
          id: 'a771afeb-c3ed-11e6-ab15-0401fa4eb401',
          nome: 'Raro Labs'
        }
      },
      aneis: [{
        id: '3b073edf-1d83-43a1-ae1b-143f919a3841',
        idJson: 'a4226504-80f8-4a48-aff6-c62b3c7eb23d',
        ativo: true,
        aceitaModoManual: true,
        posicao: 3,
        CLA: '1.000.0001.3',
        versaoPlano: {
          idJson: '2f56c3ba-aab8-4453-bd24-482a468b8cb7'
        },
        estagios: [{
          idJson: 'a1ed68a0-3849-404f-8720-974730471c4b'
        }, {
          idJson: '88aab1ee-3962-4bd0-85fa-d766a53db12f'
        }, {
          idJson: '5792c329-9250-4fef-ae9a-c44d56998451'
        }],
        gruposSemaforicos: [{
          idJson: '90294e1c-cc22-4977-ba8b-ba55d1412114'
        }, {
          idJson: '919bbe48-05dc-4b98-bff1-f798c1cecd9e'
        }, {
          idJson: '02cc5059-1559-4d4e-91e7-e2f6c521b7ad'
        }, {
          idJson: '878e5030-9790-4ea5-b09a-07a1c27ba31d'
        }],
        detectores: [

        ],
        planos: [{
          idJson: 'ca53b7ff-75ab-4b52-9d7f-f4563b725367'
        }, {
          idJson: '2cedb173-3b9c-4831-8fb7-66a390dbeb45'
        }, {
          idJson: '89e75d74-65e5-4f2d-ad39-fdfa9b778fe9'
        }],
        endereco: {
          idJson: '35d48aab-32ca-4fb1-9fe2-6ef2528131a2'
        }
      }, {
        id: '3b85b11c-5ce1-475f-b887-6382684691c0',
        idJson: 'd63044f9-5653-4c5c-90ec-d482d7871e04',
        ativo: true,
        aceitaModoManual: true,
        posicao: 2,
        CLA: '1.000.0001.2',
        versaoPlano: {
          idJson: '5f6c6fe7-44a5-419d-bbea-686516f227ca'
        },
        estagios: [{
          idJson: '5c521612-2822-48e8-9a82-5e0c16d1a4bd'
        }, {
          idJson: '878776db-7e2e-438e-955b-8cc637d639de'
        }, {
          idJson: '4d8d6135-7d86-4d98-9de4-568f09675345'
        }],
        gruposSemaforicos: [{
          idJson: 'c6497c38-52d1-40c8-901c-d3e955205331'
        }, {
          idJson: 'd7c699a5-b93f-4778-9fa7-1e5b7577f91a'
        }, {
          idJson: 'a823ff37-8516-4fed-bdf5-5fb0d464824c'
        }, {
          idJson: '566023c4-919d-4e33-a944-06596652f82b'
        }, {
          idJson: '85156d31-1984-46b1-9165-19875851c0c3'
        }],
        detectores: [

        ],
        planos: [{
          idJson: 'a39e0b2f-12f7-4d92-bc09-04b659e36be7'
        }, {
          idJson: 'fdccbe6d-1a6a-417d-be51-2a8fa87ae99f'
        }, {
          idJson: '7aa35cee-16ae-4a8e-827b-b70b4490d2b6'
        }],
        endereco: {
          idJson: '951ed053-6662-44af-bea4-fc3f6e7dc474'
        }
      }, {
        id: '7cb978b6-2e0e-4053-9ead-23443ff48ec9',
        idJson: 'e8198dc9-c5c2-4c85-99d2-345032aac71e',
        ativo: true,
        aceitaModoManual: true,
        posicao: 4,
        CLA: '1.000.0001.4',
        versaoPlano: {
          idJson: '9ba3f27a-aa74-4c0b-bc04-511d51d71284'
        },
        estagios: [{
          idJson: '6a1e9ca6-33d0-40e6-aa8a-a65f461a6e5c'
        }, {
          idJson: '766ffc1a-4772-4b81-8a3a-d7ee4d9a9562'
        }],
        gruposSemaforicos: [{
          idJson: '493742b7-ccff-4650-924e-ae8300dc5df0'
        }, {
          idJson: '14b52b33-08e8-436d-880d-015feb4eb861'
        }],
        detectores: [

        ],
        planos: [{
          idJson: 'e353f93b-2803-4cfe-a82f-fbd947036a78'
        }, {
          idJson: 'cfe1bace-4707-41eb-bb34-5f5e00749864'
        }, {
          idJson: '93999b9e-4ef0-42ef-8382-c64963146cd3'
        }],
        endereco: {
          idJson: 'adbaca48-183d-4bc7-894a-4d18c83c0b1e'
        }
      }, {
        id: '7df99ee2-6963-40bd-ba12-4436602f92f1',
        idJson: '6596c0ff-441a-4b54-bcd0-1b8b80b24ada',
        numeroSMEE: '-',
        ativo: true,
        aceitaModoManual: true,
        posicao: 1,
        CLA: '1.000.0001.1',
        versaoPlano: {
          idJson: 'e9917061-18dc-4695-9a34-0f23c0fe5384'
        },
        estagios: [{
          idJson: 'f2bf846c-108d-4d58-b7f9-e3a58b827952'
        }, {
          idJson: '6f426d16-e109-41e0-83a7-b0fdadbe1c1a'
        }, {
          idJson: '9529fbb9-9ac3-400d-9c23-d7c38d9140a1'
        }],
        gruposSemaforicos: [{
          idJson: '485293a9-ee0c-45ee-a140-97cf45274d09'
        }, {
          idJson: '23ac30b7-bed1-4de1-a68f-c93fe13e9d23'
        }, {
          idJson: '25776b2b-b20a-4253-8781-d0fda2a0c1d7'
        }, {
          idJson: 'ba57b9fe-7625-4068-bbdc-654982c7c16f'
        }, {
          idJson: '266c69e2-e945-4133-a5c6-94e187c6a5cf'
        }],
        detectores: [{
          idJson: 'e1db6b7c-fdc8-40a2-aefa-3041beed9521'
        }],
        planos: [{
          idJson: '93046997-77ae-4ce3-8920-1841e890ce3d'
        }, {
          idJson: '6069b833-0873-4616-a35a-1cf3ad5a2c35'
        }, {
          idJson: '64111a27-35fb-4053-b4ea-84234bb2c431'
        }],
        endereco: {
          idJson: 'cf74c3ab-cc3d-467b-ae45-b4d9170ad305'
        }
      }],
      estagios: [{
        id: '49bbcef3-11da-4f8f-a101-7ba9c2af7e0c',
        idJson: 'f2bf846c-108d-4d58-b7f9-e3a58b827952',
        tempoMaximoPermanencia: 20,
        tempoMaximoPermanenciaAtivado: true,
        demandaPrioritaria: false,
        tempoVerdeDemandaPrioritaria: 1,
        posicao: 3,
        anel: {
          idJson: '6596c0ff-441a-4b54-bcd0-1b8b80b24ada'
        },
        imagem: {
          idJson: '0ba1f40b-7260-4a08-b6bc-9e0ea7f1de76'
        },
        detector: {
          idJson: 'e1db6b7c-fdc8-40a2-aefa-3041beed9521'
        },
        origemDeTransicoesProibidas: [

        ],
        destinoDeTransicoesProibidas: [

        ],
        alternativaDeTransicoesProibidas: [

        ],
        estagiosGruposSemaforicos: [{
          idJson: '39f98e8f-914f-4281-b44b-1a86583f625e'
        }, {
          idJson: '1b76c9f8-271b-4e49-85a0-986a814143f8'
        }, {
          idJson: 'a987c527-db37-47d6-a9b1-39594e4111f7'
        }],
        estagiosPlanos: [{
          idJson: '9f663428-4817-41aa-8573-9a6932c3a5f8'
        }, {
          idJson: '07373545-49c2-4447-a93d-ebbedda24eda'
        }, {
          idJson: 'b8c69465-9449-4f8e-8343-b3feaf6fa595'
        }]
      }, {
        id: '894f4452-8268-48ed-9974-bcad5df15811',
        idJson: '5792c329-9250-4fef-ae9a-c44d56998451',
        tempoMaximoPermanencia: 127,
        tempoMaximoPermanenciaAtivado: true,
        demandaPrioritaria: false,
        tempoVerdeDemandaPrioritaria: 1,
        posicao: 1,
        anel: {
          idJson: 'a4226504-80f8-4a48-aff6-c62b3c7eb23d'
        },
        imagem: {
          idJson: 'e78a1afd-1fdf-472d-85ac-2437c3fae7e4'
        },
        origemDeTransicoesProibidas: [

        ],
        destinoDeTransicoesProibidas: [

        ],
        alternativaDeTransicoesProibidas: [

        ],
        estagiosGruposSemaforicos: [{
          idJson: '57010ae5-d858-45b8-841e-b4f1218c376a'
        }],
        estagiosPlanos: [{
          idJson: '8359ecc4-1666-4b4c-bb46-0eeadee5dfe0'
        }, {
          idJson: 'e924a263-2fb5-47df-bf1c-f6dd7dff223a'
        }, {
          idJson: '801829da-c43b-48e9-a594-c6375d949126'
        }]
      }, {
        id: '66bc29c0-0aa5-46ec-99dc-5d02907768a7',
        idJson: '5c521612-2822-48e8-9a82-5e0c16d1a4bd',
        tempoMaximoPermanencia: 127,
        tempoMaximoPermanenciaAtivado: true,
        demandaPrioritaria: false,
        tempoVerdeDemandaPrioritaria: 1,
        posicao: 1,
        anel: {
          idJson: 'd63044f9-5653-4c5c-90ec-d482d7871e04'
        },
        imagem: {
          idJson: 'bbe0b0bb-c028-4ac4-998a-f10721b90803'
        },
        origemDeTransicoesProibidas: [

        ],
        destinoDeTransicoesProibidas: [

        ],
        alternativaDeTransicoesProibidas: [

        ],
        estagiosGruposSemaforicos: [{
          idJson: '6c59b27e-a589-4d27-95f1-584e0b2a3aab'
        }, {
          idJson: '93a23be4-4381-41e9-84d5-0ca51229d164'
        }],
        estagiosPlanos: [{
          idJson: '935641b3-b355-4b4c-941d-6296ea2613fc'
        }, {
          idJson: 'cb73376d-9b68-43fe-b933-2cfa8d63ab37'
        }, {
          idJson: '1bc97114-c752-4827-a4d6-8d2de3c7583e'
        }]
      }, {
        id: 'd49349f1-e931-4e93-b544-6f483b2fa647',
        idJson: '9529fbb9-9ac3-400d-9c23-d7c38d9140a1',
        tempoMaximoPermanencia: 127,
        tempoMaximoPermanenciaAtivado: true,
        demandaPrioritaria: false,
        tempoVerdeDemandaPrioritaria: 1,
        posicao: 1,
        anel: {
          idJson: '6596c0ff-441a-4b54-bcd0-1b8b80b24ada'
        },
        imagem: {
          idJson: '898195b7-c19f-436b-8e19-547ef5659f05'
        },
        origemDeTransicoesProibidas: [

        ],
        destinoDeTransicoesProibidas: [

        ],
        alternativaDeTransicoesProibidas: [

        ],
        estagiosGruposSemaforicos: [{
          idJson: 'bb63aa3f-40b2-47eb-a3ad-73fdd84080a7'
        }, {
          idJson: '2ce92ca1-0842-4083-9d5c-8e26775edcb6'
        }],
        estagiosPlanos: [{
          idJson: 'e3a22028-fc44-45ad-bae9-d3021dc6ea93'
        }, {
          idJson: '480e2abd-3f22-4a90-8cca-58aeed9d3ba9'
        }, {
          idJson: 'f77fe8ad-5858-49c9-9717-cd76db768f73'
        }]
      }, {
        id: 'efcbec72-2752-401c-92f0-cf986c548208',
        idJson: '4d8d6135-7d86-4d98-9de4-568f09675345',
        tempoMaximoPermanencia: 127,
        tempoMaximoPermanenciaAtivado: true,
        demandaPrioritaria: false,
        tempoVerdeDemandaPrioritaria: 1,
        posicao: 3,
        anel: {
          idJson: 'd63044f9-5653-4c5c-90ec-d482d7871e04'
        },
        imagem: {
          idJson: 'a1ff80cb-ad28-4246-8ec4-4dc464821af5'
        },
        origemDeTransicoesProibidas: [

        ],
        destinoDeTransicoesProibidas: [

        ],
        alternativaDeTransicoesProibidas: [

        ],
        estagiosGruposSemaforicos: [{
          idJson: 'b9b3dcbc-9db8-4527-ad50-7ddca7897694'
        }],
        estagiosPlanos: [{
          idJson: 'f19135b1-b2ed-42e1-bc26-d68aa9bd4e70'
        }, {
          idJson: '69a20225-a66e-4300-a404-945c6fe993b1'
        }, {
          idJson: '1ccd3d09-2f54-4be6-a4c4-e05839f1ab66'
        }]
      }, {
        id: 'e708fe6e-5e16-4998-a27a-9113ca2756d5',
        idJson: '878776db-7e2e-438e-955b-8cc637d639de',
        tempoMaximoPermanencia: 20,
        tempoMaximoPermanenciaAtivado: true,
        demandaPrioritaria: false,
        tempoVerdeDemandaPrioritaria: 1,
        posicao: 2,
        anel: {
          idJson: 'd63044f9-5653-4c5c-90ec-d482d7871e04'
        },
        imagem: {
          idJson: '4fea4552-d90d-4084-b5b7-09b18957581c'
        },
        origemDeTransicoesProibidas: [

        ],
        destinoDeTransicoesProibidas: [

        ],
        alternativaDeTransicoesProibidas: [

        ],
        estagiosGruposSemaforicos: [{
          idJson: '90a36f08-a184-4a47-b368-1d49afd0e0d4'
        }, {
          idJson: 'b606ce85-fa94-465b-a08f-6067efdacbac'
        }],
        estagiosPlanos: [{
          idJson: '6090c21d-234e-4ff6-a83d-05df212ea470'
        }, {
          idJson: '3fa746fd-f3db-4353-89f3-4ff7fa1f187b'
        }, {
          idJson: '8276be07-cbf0-4f18-8c3b-2dd53519c6ce'
        }]
      }, {
        id: '8d1a761a-3389-4e9c-84f3-d2b719cb1a92',
        idJson: '6a1e9ca6-33d0-40e6-aa8a-a65f461a6e5c',
        tempoMaximoPermanencia: 127,
        tempoMaximoPermanenciaAtivado: true,
        demandaPrioritaria: false,
        tempoVerdeDemandaPrioritaria: 1,
        posicao: 1,
        anel: {
          idJson: 'e8198dc9-c5c2-4c85-99d2-345032aac71e'
        },
        imagem: {
          idJson: 'd1ee9495-ced2-4082-b273-28692480303a'
        },
        origemDeTransicoesProibidas: [

        ],
        destinoDeTransicoesProibidas: [

        ],
        alternativaDeTransicoesProibidas: [

        ],
        estagiosGruposSemaforicos: [{
          idJson: '794000f3-bfb4-4d07-818a-29aa5a83cde6'
        }],
        estagiosPlanos: [{
          idJson: '806810b5-e051-4a78-b038-94da4e111070'
        }, {
          idJson: '5d2a09a7-5dfb-4519-814d-e2f44ec8afc3'
        }, {
          idJson: '21d4cbe0-5455-4074-b833-605e32462468'
        }]
      }, {
        id: '6f280b50-ccc7-45de-bc6f-15f74abbc10e',
        idJson: '6f426d16-e109-41e0-83a7-b0fdadbe1c1a',
        tempoMaximoPermanencia: 127,
        tempoMaximoPermanenciaAtivado: true,
        demandaPrioritaria: false,
        tempoVerdeDemandaPrioritaria: 1,
        posicao: 2,
        anel: {
          idJson: '6596c0ff-441a-4b54-bcd0-1b8b80b24ada'
        },
        imagem: {
          idJson: '40269374-313e-4f30-8352-63c9733b2874'
        },
        origemDeTransicoesProibidas: [

        ],
        destinoDeTransicoesProibidas: [

        ],
        alternativaDeTransicoesProibidas: [

        ],
        estagiosGruposSemaforicos: [{
          idJson: '84ea8683-08c3-4e68-83a2-d933822d7c90'
        }, {
          idJson: '8e9a40c8-f63d-407b-b748-6da2cc2bbc33'
        }],
        estagiosPlanos: [{
          idJson: '87e7eea7-f717-4ddd-bce2-5b0caedcce33'
        }, {
          idJson: '7931c69b-0262-46d7-adcb-fd896ea86f45'
        }, {
          idJson: 'e9580138-629b-4b16-9de7-5a80856c6c46'
        }]
      }, {
        id: 'e237d8df-3ecf-4fd6-8e6a-ee977558943a',
        idJson: '766ffc1a-4772-4b81-8a3a-d7ee4d9a9562',
        tempoMaximoPermanencia: 20,
        tempoMaximoPermanenciaAtivado: true,
        demandaPrioritaria: false,
        tempoVerdeDemandaPrioritaria: 1,
        posicao: 2,
        anel: {
          idJson: 'e8198dc9-c5c2-4c85-99d2-345032aac71e'
        },
        imagem: {
          idJson: 'b819deb6-9acf-4337-853b-70d0e9c7e8ea'
        },
        origemDeTransicoesProibidas: [

        ],
        destinoDeTransicoesProibidas: [

        ],
        alternativaDeTransicoesProibidas: [

        ],
        estagiosGruposSemaforicos: [{
          idJson: '198b92b1-a9f8-46db-b672-144bd35b256f'
        }],
        estagiosPlanos: [{
          idJson: '9ab1de61-4ef5-47fb-a232-1e5ec620f1e7'
        }, {
          idJson: '641051a5-f80f-44d4-aa5c-1bc21ce42d7a'
        }, {
          idJson: 'f4e67060-cd10-4107-b5c6-07834f1b6299'
        }, {
          idJson: '8e770f71-7981-4e8a-9d1d-8dfffbc47a4a'
        }]
      }, {
        id: '5dcc1b7d-fcb6-400f-a763-0745f52cd5b0',
        idJson: 'a1ed68a0-3849-404f-8720-974730471c4b',
        tempoMaximoPermanencia: 127,
        tempoMaximoPermanenciaAtivado: true,
        demandaPrioritaria: false,
        tempoVerdeDemandaPrioritaria: 1,
        posicao: 2,
        anel: {
          idJson: 'a4226504-80f8-4a48-aff6-c62b3c7eb23d'
        },
        imagem: {
          idJson: '1be5b42d-39ea-482e-b7b0-62bc0ec9d9bc'
        },
        origemDeTransicoesProibidas: [

        ],
        destinoDeTransicoesProibidas: [

        ],
        alternativaDeTransicoesProibidas: [

        ],
        estagiosGruposSemaforicos: [{
          idJson: 'f0535384-c37f-4993-8ae0-1dadd1c75a6b'
        }],
        estagiosPlanos: [{
          idJson: '408a3353-5c97-40c5-a900-92788ce06949'
        }, {
          idJson: 'bb18f9e4-4498-476a-a9f4-ba2b3fa75e95'
        }, {
          idJson: 'f10efebe-a8f5-4923-81af-b421fba2b4c4'
        }]
      }, {
        id: '6435dc75-4193-4e13-8834-44e071295258',
        idJson: '88aab1ee-3962-4bd0-85fa-d766a53db12f',
        tempoMaximoPermanencia: 20,
        tempoMaximoPermanenciaAtivado: true,
        demandaPrioritaria: false,
        tempoVerdeDemandaPrioritaria: 1,
        posicao: 3,
        anel: {
          idJson: 'a4226504-80f8-4a48-aff6-c62b3c7eb23d'
        },
        imagem: {
          idJson: '9555fe04-65ec-4e30-a3d4-9b880627f317'
        },
        origemDeTransicoesProibidas: [

        ],
        destinoDeTransicoesProibidas: [

        ],
        alternativaDeTransicoesProibidas: [

        ],
        estagiosGruposSemaforicos: [{
          idJson: '71094600-1348-4454-ad64-964a80abf34b'
        }, {
          idJson: '508176aa-78f7-463c-bcaa-277f1873f916'
        }],
        estagiosPlanos: [{
          idJson: '278b28df-a0b8-4d48-8b7f-7d2cb425bfb4'
        }, {
          idJson: 'b44d9d7c-3993-4ed9-99aa-03c72681ea4f'
        }, {
          idJson: '4d2b44fe-5705-4c19-800f-e621d5d191f4'
        }]
      }],
      gruposSemaforicos: [{
        id: '7184a1ec-5869-456e-a97e-6fda1b3cdeac',
        idJson: 'a823ff37-8516-4fed-bdf5-5fb0d464824c',
        tipo: 'VEICULAR',
        posicao: 10,
        faseVermelhaApagadaAmareloIntermitente: true,
        tempoVerdeSeguranca: 10,
        anel: {
          idJson: 'd63044f9-5653-4c5c-90ec-d482d7871e04'
        },
        verdesConflitantesOrigem: [

        ],
        verdesConflitantesDestino: [{
          idJson: '38351426-7b78-4e1b-af8f-865f432723a8'
        }, {
          idJson: '876ad577-9231-4bb7-84da-83a19481e491'
        }, {
          idJson: '74094da3-95eb-4243-b5b6-c95546f608bb'
        }, {
          idJson: '945fef8b-dbf7-46a3-9628-781ada0aa1bc'
        }],
        estagiosGruposSemaforicos: [{
          idJson: 'b9b3dcbc-9db8-4527-ad50-7ddca7897694'
        }],
        transicoes: [{
          idJson: 'bab2a895-d128-4642-85ad-b7324331cdcc'
        }, {
          idJson: 'a88f9581-cfa1-4c7d-818b-78dd7cafb819'
        }],
        transicoesComGanhoDePassagem: [{
          idJson: 'd016483f-54a3-433b-8c37-7585491d0bfb'
        }, {
          idJson: '6bc643bf-a508-426c-b5af-6e2f84b0ce8c'
        }],
        tabelasEntreVerdes: [{
          idJson: 'da5ddf23-dac0-4551-9624-37b07eb8ef56'
        }]
      }, {
        id: '8a67ac9e-aaf1-4eda-9b66-ebbd49a4050d',
        idJson: '90294e1c-cc22-4977-ba8b-ba55d1412114',
        tipo: 'VEICULAR',
        posicao: 12,
        faseVermelhaApagadaAmareloIntermitente: true,
        tempoVerdeSeguranca: 10,
        anel: {
          idJson: 'a4226504-80f8-4a48-aff6-c62b3c7eb23d'
        },
        verdesConflitantesOrigem: [{
          idJson: '0783b0cd-9430-40eb-96da-0fb10e7d6bf4'
        }, {
          idJson: '0a28a5aa-474c-407b-96bf-4a0e97f712fa'
        }],
        verdesConflitantesDestino: [{
          idJson: 'a9ef2c93-783e-4ccf-a400-a1b9ef8e9204'
        }],
        estagiosGruposSemaforicos: [{
          idJson: 'f0535384-c37f-4993-8ae0-1dadd1c75a6b'
        }],
        transicoes: [{
          idJson: '9ebf777d-7f78-455f-9c49-8d30ba09cc82'
        }, {
          idJson: 'a24218d3-f97a-402a-8107-59d408dcf71e'
        }],
        transicoesComGanhoDePassagem: [{
          idJson: '418b17ac-9f2e-468a-a280-d30d1a036988'
        }, {
          idJson: 'f73a1e05-250d-4404-ab59-2a9927bff83a'
        }],
        tabelasEntreVerdes: [{
          idJson: '87de7529-2f72-41be-be1d-621133c80bf5'
        }]
      }, {
        id: '8f3a37a8-12b5-4f32-be60-d6efc43e8975',
        idJson: '23ac30b7-bed1-4de1-a68f-c93fe13e9d23',
        tipo: 'PEDESTRE',
        posicao: 3,
        faseVermelhaApagadaAmareloIntermitente: false,
        tempoVerdeSeguranca: 4,
        anel: {
          idJson: '6596c0ff-441a-4b54-bcd0-1b8b80b24ada'
        },
        verdesConflitantesOrigem: [

        ],
        verdesConflitantesDestino: [{
          idJson: '71f03de1-490e-4e6e-b8bc-929c9ffce23b'
        }, {
          idJson: 'f5584579-8b74-4a33-ac30-3f5239fa0193'
        }],
        estagiosGruposSemaforicos: [{
          idJson: '39f98e8f-914f-4281-b44b-1a86583f625e'
        }],
        transicoes: [{
          idJson: 'bcfcd258-321d-4f24-b6d6-7d5c0cd32f87'
        }, {
          idJson: 'b765f211-68e2-490e-8bca-47da5c5597f0'
        }],
        transicoesComGanhoDePassagem: [{
          idJson: '5e907490-2c21-47cc-ad91-f647463b5e0c'
        }, {
          idJson: 'f7e5f078-3559-481c-a4a6-aeae5e161e70'
        }],
        tabelasEntreVerdes: [{
          idJson: '28ae1d57-b96a-4455-a43d-db69f7883af5'
        }]
      }, {
        id: 'ed1a4c3b-7628-4c5a-a9b3-350193ae3ec7',
        idJson: '878e5030-9790-4ea5-b09a-07a1c27ba31d',
        tipo: 'PEDESTRE',
        posicao: 13,
        faseVermelhaApagadaAmareloIntermitente: false,
        tempoVerdeSeguranca: 4,
        anel: {
          idJson: 'a4226504-80f8-4a48-aff6-c62b3c7eb23d'
        },
        verdesConflitantesOrigem: [

        ],
        verdesConflitantesDestino: [{
          idJson: 'f32b7de9-1a6c-48d6-892d-3ca2fada2274'
        }, {
          idJson: '0783b0cd-9430-40eb-96da-0fb10e7d6bf4'
        }],
        estagiosGruposSemaforicos: [{
          idJson: '508176aa-78f7-463c-bcaa-277f1873f916'
        }],
        transicoes: [{
          idJson: '29a07f62-5228-4d11-9ac7-08816e9d96e0'
        }, {
          idJson: 'e850b442-c77e-4284-ab6c-231c250ca67b'
        }],
        transicoesComGanhoDePassagem: [{
          idJson: 'b3d82fde-fa92-480a-a7bc-1758fa9c3d6f'
        }, {
          idJson: '56713a77-09e4-4586-b9b8-4ac50e89968f'
        }],
        tabelasEntreVerdes: [{
          idJson: '599e2e56-2fea-4e07-a49c-0bff6b986df4'
        }]
      }, {
        id: '1d4fa47d-44e1-465e-b249-4171f3b5b05e',
        idJson: '485293a9-ee0c-45ee-a140-97cf45274d09',
        tipo: 'PEDESTRE',
        posicao: 4,
        faseVermelhaApagadaAmareloIntermitente: false,
        tempoVerdeSeguranca: 4,
        anel: {
          idJson: '6596c0ff-441a-4b54-bcd0-1b8b80b24ada'
        },
        verdesConflitantesOrigem: [

        ],
        verdesConflitantesDestino: [{
          idJson: 'f3779664-8db4-4d05-92e5-1979a645cf10'
        }],
        estagiosGruposSemaforicos: [{
          idJson: '84ea8683-08c3-4e68-83a2-d933822d7c90'
        }, {
          idJson: 'a987c527-db37-47d6-a9b1-39594e4111f7'
        }],
        transicoes: [{
          idJson: '7f1a75ff-7cab-42b3-b799-e75471e84ee7'
        }, {
          idJson: 'da621643-484a-4fac-a188-85601b5af369'
        }],
        transicoesComGanhoDePassagem: [{
          idJson: '3c69d11d-d145-4034-82e6-18d44ec4d99e'
        }, {
          idJson: 'ede7f959-347a-4a35-a1e6-cd17004908e1'
        }],
        tabelasEntreVerdes: [{
          idJson: '26e6e613-b256-4a84-81fa-87298a327695'
        }]
      }, {
        id: '96b4c7bc-873e-4832-a46e-8303f8ddb611',
        idJson: '14b52b33-08e8-436d-880d-015feb4eb861',
        tipo: 'VEICULAR',
        posicao: 15,
        faseVermelhaApagadaAmareloIntermitente: true,
        tempoVerdeSeguranca: 10,
        anel: {
          idJson: 'e8198dc9-c5c2-4c85-99d2-345032aac71e'
        },
        verdesConflitantesOrigem: [{
          idJson: '240bdb70-0359-4268-bb29-8a567589ff88'
        }],
        verdesConflitantesDestino: [

        ],
        estagiosGruposSemaforicos: [{
          idJson: '794000f3-bfb4-4d07-818a-29aa5a83cde6'
        }],
        transicoes: [{
          idJson: '3afb5c03-444b-40ef-8d4e-60d4349dda46'
        }],
        transicoesComGanhoDePassagem: [{
          idJson: '86d27976-936f-412c-b8af-50205c8aa42b'
        }],
        tabelasEntreVerdes: [{
          idJson: 'ff39e15f-48ad-4122-9ef0-f55b14c94cca'
        }]
      }, {
        id: '43b95dcb-3900-420f-96a7-e9a086138ccd',
        idJson: '493742b7-ccff-4650-924e-ae8300dc5df0',
        tipo: 'PEDESTRE',
        posicao: 16,
        faseVermelhaApagadaAmareloIntermitente: false,
        tempoVerdeSeguranca: 4,
        anel: {
          idJson: 'e8198dc9-c5c2-4c85-99d2-345032aac71e'
        },
        verdesConflitantesOrigem: [

        ],
        verdesConflitantesDestino: [{
          idJson: '240bdb70-0359-4268-bb29-8a567589ff88'
        }],
        estagiosGruposSemaforicos: [{
          idJson: '198b92b1-a9f8-46db-b672-144bd35b256f'
        }],
        transicoes: [{
          idJson: '19f70d49-3ab7-45de-a23a-172610f918e7'
        }],
        transicoesComGanhoDePassagem: [{
          idJson: '3c69bcaf-df55-48b6-a0a4-e8108b8a3af0'
        }],
        tabelasEntreVerdes: [{
          idJson: 'f1a695b4-6782-49d8-9b16-e384ecc56172'
        }]
      }, {
        id: 'bcb3dbac-2a82-4c59-9cfa-c028d1c2e297',
        idJson: '919bbe48-05dc-4b98-bff1-f798c1cecd9e',
        tipo: 'VEICULAR',
        posicao: 11,
        faseVermelhaApagadaAmareloIntermitente: true,
        tempoVerdeSeguranca: 10,
        anel: {
          idJson: 'a4226504-80f8-4a48-aff6-c62b3c7eb23d'
        },
        verdesConflitantesOrigem: [{
          idJson: 'f32b7de9-1a6c-48d6-892d-3ca2fada2274'
        }, {
          idJson: '8f0b6655-0d79-4f0e-b531-040b8fc1880b'
        }, {
          idJson: 'a9ef2c93-783e-4ccf-a400-a1b9ef8e9204'
        }],
        verdesConflitantesDestino: [

        ],
        estagiosGruposSemaforicos: [{
          idJson: '57010ae5-d858-45b8-841e-b4f1218c376a'
        }],
        transicoes: [{
          idJson: 'fa0ad62b-8b4a-4889-812c-45000bc62efb'
        }, {
          idJson: '3c92af69-2266-4e75-8efe-0cfbe5ea2336'
        }],
        transicoesComGanhoDePassagem: [{
          idJson: 'efe645a8-5403-4081-b281-ab94a5bbb456'
        }, {
          idJson: 'ed747576-cedd-4e01-a5fe-c19faca0279c'
        }],
        tabelasEntreVerdes: [{
          idJson: '3e222c25-659e-44c1-9102-ee4d3cf00aaf'
        }]
      }, {
        id: '8be8d66a-fef8-48b8-b715-ba61dc561b1b',
        idJson: '566023c4-919d-4e33-a944-06596652f82b',
        tipo: 'VEICULAR',
        posicao: 7,
        faseVermelhaApagadaAmareloIntermitente: true,
        tempoVerdeSeguranca: 10,
        anel: {
          idJson: 'd63044f9-5653-4c5c-90ec-d482d7871e04'
        },
        verdesConflitantesOrigem: [{
          idJson: '876ad577-9231-4bb7-84da-83a19481e491'
        }, {
          idJson: '6ef952e9-3bbc-47d2-a789-242b5d20e4b1'
        }, {
          idJson: 'c116e1ce-c127-4771-bd6d-71209160121f'
        }],
        verdesConflitantesDestino: [

        ],
        estagiosGruposSemaforicos: [{
          idJson: '6c59b27e-a589-4d27-95f1-584e0b2a3aab'
        }],
        transicoes: [{
          idJson: '97033230-5600-4c5b-9d85-d94338875f8d'
        }, {
          idJson: 'b5311620-e9ba-4760-ac9e-10e86144c688'
        }],
        transicoesComGanhoDePassagem: [{
          idJson: '77658864-14b2-4cb3-97d2-e6108915d598'
        }, {
          idJson: '3074245d-d238-4860-a9b1-d6a2417c7662'
        }],
        tabelasEntreVerdes: [{
          idJson: 'ac85f93f-58e3-4012-8465-d8c9550c281a'
        }]
      }, {
        id: 'c983b5ca-e021-48aa-aff6-c6ad0819268b',
        idJson: 'ba57b9fe-7625-4068-bbdc-654982c7c16f',
        tipo: 'VEICULAR',
        posicao: 1,
        faseVermelhaApagadaAmareloIntermitente: true,
        tempoVerdeSeguranca: 10,
        anel: {
          idJson: '6596c0ff-441a-4b54-bcd0-1b8b80b24ada'
        },
        verdesConflitantesOrigem: [{
          idJson: '71f03de1-490e-4e6e-b8bc-929c9ffce23b'
        }, {
          idJson: '143450d4-6948-4c27-9743-4b8fb29a2566'
        }, {
          idJson: 'f3779664-8db4-4d05-92e5-1979a645cf10'
        }],
        verdesConflitantesDestino: [

        ],
        estagiosGruposSemaforicos: [{
          idJson: 'bb63aa3f-40b2-47eb-a3ad-73fdd84080a7'
        }],
        transicoes: [{
          idJson: 'df020540-575b-4d30-9ade-facdfad53ed4'
        }, {
          idJson: 'cfcc8dc1-260c-49f2-975f-a9906bb5c5a8'
        }],
        transicoesComGanhoDePassagem: [{
          idJson: '24cacb8c-b151-4961-b312-82f9b2196da4'
        }, {
          idJson: '5379b5c2-5d9f-4797-94f0-ededd2dd888b'
        }],
        tabelasEntreVerdes: [{
          idJson: 'ed3eec6b-d8ca-45b6-bde0-dd5914543357'
        }]
      }, {
        id: 'd81ea69d-688f-4cb7-b571-9463b193f6d6',
        idJson: '02cc5059-1559-4d4e-91e7-e2f6c521b7ad',
        tipo: 'PEDESTRE',
        posicao: 14,
        faseVermelhaApagadaAmareloIntermitente: false,
        tempoVerdeSeguranca: 4,
        anel: {
          idJson: 'a4226504-80f8-4a48-aff6-c62b3c7eb23d'
        },
        verdesConflitantesOrigem: [

        ],
        verdesConflitantesDestino: [{
          idJson: '0a28a5aa-474c-407b-96bf-4a0e97f712fa'
        }, {
          idJson: '8f0b6655-0d79-4f0e-b531-040b8fc1880b'
        }],
        estagiosGruposSemaforicos: [{
          idJson: '71094600-1348-4454-ad64-964a80abf34b'
        }],
        transicoes: [{
          idJson: 'a2261cfb-901e-42f6-920e-b59cbee8e872'
        }, {
          idJson: '9240a164-f4f6-44a7-95d8-f7b490384db5'
        }],
        transicoesComGanhoDePassagem: [{
          idJson: 'be5708aa-eda8-47dc-a680-569d68598974'
        }, {
          idJson: '8b75c1ef-ad96-4ebd-a452-186f725c0423'
        }],
        tabelasEntreVerdes: [{
          idJson: '8d512712-cc24-4a06-be00-2613a4a2b08d'
        }]
      }, {
        id: '91c19a35-1c98-4792-b135-4dc3483b98b3',
        idJson: '25776b2b-b20a-4253-8781-d0fda2a0c1d7',
        tipo: 'PEDESTRE',
        posicao: 5,
        faseVermelhaApagadaAmareloIntermitente: false,
        tempoVerdeSeguranca: 4,
        anel: {
          idJson: '6596c0ff-441a-4b54-bcd0-1b8b80b24ada'
        },
        verdesConflitantesOrigem: [

        ],
        verdesConflitantesDestino: [{
          idJson: '0d4f4215-6243-417f-8179-0fb97e4a9203'
        }],
        estagiosGruposSemaforicos: [{
          idJson: '1b76c9f8-271b-4e49-85a0-986a814143f8'
        }, {
          idJson: '2ce92ca1-0842-4083-9d5c-8e26775edcb6'
        }],
        transicoes: [{
          idJson: '4adb694a-8769-42c9-8e8a-79c9d5bda5c7'
        }, {
          idJson: 'ef571ca3-8b68-40fe-998f-bb87037c343a'
        }],
        transicoesComGanhoDePassagem: [{
          idJson: 'f26b555a-8c07-4292-814a-328fa39012b2'
        }, {
          idJson: '8e344e24-7cd5-4cbd-a3dd-a2ad26ff9b65'
        }],
        tabelasEntreVerdes: [{
          idJson: '048baffc-b563-46d0-80b4-42ba0e0a29f4'
        }]
      }, {
        id: 'cc90568e-b97e-41aa-b96d-9e5e696eda2d',
        idJson: '266c69e2-e945-4133-a5c6-94e187c6a5cf',
        tipo: 'VEICULAR',
        posicao: 2,
        faseVermelhaApagadaAmareloIntermitente: true,
        tempoVerdeSeguranca: 10,
        anel: {
          idJson: '6596c0ff-441a-4b54-bcd0-1b8b80b24ada'
        },
        verdesConflitantesOrigem: [{
          idJson: '0d4f4215-6243-417f-8179-0fb97e4a9203'
        }, {
          idJson: 'f5584579-8b74-4a33-ac30-3f5239fa0193'
        }],
        verdesConflitantesDestino: [{
          idJson: '143450d4-6948-4c27-9743-4b8fb29a2566'
        }],
        estagiosGruposSemaforicos: [{
          idJson: '8e9a40c8-f63d-407b-b748-6da2cc2bbc33'
        }],
        transicoes: [{
          idJson: '9a3ffa05-8189-41a2-9bc5-394cace2a11a'
        }, {
          idJson: '766b0fd3-6802-4ed2-95fa-0e76cac3f312'
        }],
        transicoesComGanhoDePassagem: [{
          idJson: 'b5ea6e61-1bf5-4af1-963c-f8f79cd9d674'
        }, {
          idJson: '962c9f21-981c-4040-baf0-dd299180191b'
        }],
        tabelasEntreVerdes: [{
          idJson: 'e12615a5-77dd-47f1-a504-ee699a366ead'
        }]
      }, {
        id: '10916819-6559-466a-b90b-eb85332f3cfa',
        idJson: 'c6497c38-52d1-40c8-901c-d3e955205331',
        tipo: 'VEICULAR',
        posicao: 6,
        faseVermelhaApagadaAmareloIntermitente: true,
        tempoVerdeSeguranca: 10,
        anel: {
          idJson: 'd63044f9-5653-4c5c-90ec-d482d7871e04'
        },
        verdesConflitantesOrigem: [{
          idJson: '38351426-7b78-4e1b-af8f-865f432723a8'
        }, {
          idJson: '2d343075-0e23-4933-b339-2b2841aacbe6'
        }, {
          idJson: '59706197-ba55-4432-9aff-813a77900662'
        }],
        verdesConflitantesDestino: [

        ],
        estagiosGruposSemaforicos: [{
          idJson: '93a23be4-4381-41e9-84d5-0ca51229d164'
        }],
        transicoes: [{
          idJson: 'a3c18b9d-95b9-4bbc-b5f1-971d1d595bc1'
        }, {
          idJson: 'a1dfa985-c887-4eba-a025-6aa4debcdc91'
        }],
        transicoesComGanhoDePassagem: [{
          idJson: '8010fc9f-4d08-4b75-8054-386935d36298'
        }, {
          idJson: '298517e7-4874-4592-b7ac-ea50a2ec40dd'
        }],
        tabelasEntreVerdes: [{
          idJson: '8b799d4c-e3f3-4829-813e-1c397054c5af'
        }]
      }, {
        id: '3158b85b-ca4d-4238-965f-758c4186cfc7',
        idJson: 'd7c699a5-b93f-4778-9fa7-1e5b7577f91a',
        tipo: 'PEDESTRE',
        posicao: 8,
        faseVermelhaApagadaAmareloIntermitente: false,
        tempoVerdeSeguranca: 4,
        anel: {
          idJson: 'd63044f9-5653-4c5c-90ec-d482d7871e04'
        },
        verdesConflitantesOrigem: [{
          idJson: '945fef8b-dbf7-46a3-9628-781ada0aa1bc'
        }],
        verdesConflitantesDestino: [{
          idJson: '6ef952e9-3bbc-47d2-a789-242b5d20e4b1'
        }, {
          idJson: '59706197-ba55-4432-9aff-813a77900662'
        }],
        estagiosGruposSemaforicos: [{
          idJson: '90a36f08-a184-4a47-b368-1d49afd0e0d4'
        }],
        transicoes: [{
          idJson: '6d2025c8-83a2-4725-9f45-6104ab95517b'
        }, {
          idJson: '7d8d2a88-89af-444f-a8aa-f976b01f8ac6'
        }],
        transicoesComGanhoDePassagem: [{
          idJson: '022296bd-b0ca-419c-b8b1-ea56fae908d3'
        }, {
          idJson: '227d745e-9102-477f-a795-3cfc5f6b241e'
        }],
        tabelasEntreVerdes: [{
          idJson: '5d494267-2ea7-4134-a517-1fc9acfb556c'
        }]
      }, {
        id: 'f6f1ef49-2b32-4038-a499-01dae4a5d862',
        idJson: '85156d31-1984-46b1-9165-19875851c0c3',
        tipo: 'PEDESTRE',
        posicao: 9,
        faseVermelhaApagadaAmareloIntermitente: false,
        tempoVerdeSeguranca: 4,
        anel: {
          idJson: 'd63044f9-5653-4c5c-90ec-d482d7871e04'
        },
        verdesConflitantesOrigem: [{
          idJson: '74094da3-95eb-4243-b5b6-c95546f608bb'
        }],
        verdesConflitantesDestino: [{
          idJson: '2d343075-0e23-4933-b339-2b2841aacbe6'
        }, {
          idJson: 'c116e1ce-c127-4771-bd6d-71209160121f'
        }],
        estagiosGruposSemaforicos: [{
          idJson: 'b606ce85-fa94-465b-a08f-6067efdacbac'
        }],
        transicoes: [{
          idJson: '4299e3b4-f7b7-42d8-a531-321557299902'
        }, {
          idJson: 'e045c5de-cb9f-4299-9eb1-5d5c168836fb'
        }],
        transicoesComGanhoDePassagem: [{
          idJson: '7411840b-30fc-4b87-9033-fc2f7f5b55f7'
        }, {
          idJson: 'ab6a18e4-0520-4a49-b0a7-2b4b0dfd7aff'
        }],
        tabelasEntreVerdes: [{
          idJson: '0456f398-1829-4171-b8ea-aa2147fcb79e'
        }]
      }],
      detectores: [{
        id: '20e5b137-fdda-46b4-b77a-ea037463b5d4',
        idJson: 'e1db6b7c-fdc8-40a2-aefa-3041beed9521',
        tipo: 'PEDESTRE',
        posicao: 1,
        monitorado: false,
        tempoAusenciaDeteccao: 0,
        tempoDeteccaoPermanente: 0,
        anel: {
          idJson: '6596c0ff-441a-4b54-bcd0-1b8b80b24ada'
        },
        estagio: {
          idJson: 'f2bf846c-108d-4d58-b7f9-e3a58b827952'
        }
      }],
      transicoesProibidas: [

      ],
      estagiosGruposSemaforicos: [{
        id: '0f4819c0-9d88-40ea-b0ea-255bd356990f',
        idJson: '90a36f08-a184-4a47-b368-1d49afd0e0d4',
        estagio: {
          idJson: '878776db-7e2e-438e-955b-8cc637d639de'
        },
        grupoSemaforico: {
          idJson: 'd7c699a5-b93f-4778-9fa7-1e5b7577f91a'
        }
      }, {
        id: '09530616-04e5-4eda-9e7d-52caca4831da',
        idJson: '39f98e8f-914f-4281-b44b-1a86583f625e',
        estagio: {
          idJson: 'f2bf846c-108d-4d58-b7f9-e3a58b827952'
        },
        grupoSemaforico: {
          idJson: '23ac30b7-bed1-4de1-a68f-c93fe13e9d23'
        }
      }, {
        id: '95dfa06a-d86d-4e00-902c-ad801a1ccef2',
        idJson: 'a987c527-db37-47d6-a9b1-39594e4111f7',
        estagio: {
          idJson: 'f2bf846c-108d-4d58-b7f9-e3a58b827952'
        },
        grupoSemaforico: {
          idJson: '485293a9-ee0c-45ee-a140-97cf45274d09'
        }
      }, {
        id: '1c66a325-a395-463c-af4b-edea79e8f5db',
        idJson: '71094600-1348-4454-ad64-964a80abf34b',
        estagio: {
          idJson: '88aab1ee-3962-4bd0-85fa-d766a53db12f'
        },
        grupoSemaforico: {
          idJson: '02cc5059-1559-4d4e-91e7-e2f6c521b7ad'
        }
      }, {
        id: '5a31ad24-692c-47f0-acd9-f31ff1b8c825',
        idJson: '198b92b1-a9f8-46db-b672-144bd35b256f',
        estagio: {
          idJson: '766ffc1a-4772-4b81-8a3a-d7ee4d9a9562'
        },
        grupoSemaforico: {
          idJson: '493742b7-ccff-4650-924e-ae8300dc5df0'
        }
      }, {
        id: '1f1c9f9e-1649-4040-afd1-2cd3d3558b0c',
        idJson: '8e9a40c8-f63d-407b-b748-6da2cc2bbc33',
        estagio: {
          idJson: '6f426d16-e109-41e0-83a7-b0fdadbe1c1a'
        },
        grupoSemaforico: {
          idJson: '266c69e2-e945-4133-a5c6-94e187c6a5cf'
        }
      }, {
        id: '9fe2f708-e36c-4adc-81e7-00b835e582b2',
        idJson: '2ce92ca1-0842-4083-9d5c-8e26775edcb6',
        estagio: {
          idJson: '9529fbb9-9ac3-400d-9c23-d7c38d9140a1'
        },
        grupoSemaforico: {
          idJson: '25776b2b-b20a-4253-8781-d0fda2a0c1d7'
        }
      }, {
        id: 'e41f847c-50be-43bd-8200-ab59ea66c6ef',
        idJson: '508176aa-78f7-463c-bcaa-277f1873f916',
        estagio: {
          idJson: '88aab1ee-3962-4bd0-85fa-d766a53db12f'
        },
        grupoSemaforico: {
          idJson: '878e5030-9790-4ea5-b09a-07a1c27ba31d'
        }
      }, {
        id: '514fedd9-448e-4233-8166-984775d9c511',
        idJson: 'bb63aa3f-40b2-47eb-a3ad-73fdd84080a7',
        estagio: {
          idJson: '9529fbb9-9ac3-400d-9c23-d7c38d9140a1'
        },
        grupoSemaforico: {
          idJson: 'ba57b9fe-7625-4068-bbdc-654982c7c16f'
        }
      }, {
        id: '9cc3ee5c-b044-492c-94cc-ae162e3a9278',
        idJson: 'b9b3dcbc-9db8-4527-ad50-7ddca7897694',
        estagio: {
          idJson: '4d8d6135-7d86-4d98-9de4-568f09675345'
        },
        grupoSemaforico: {
          idJson: 'a823ff37-8516-4fed-bdf5-5fb0d464824c'
        }
      }, {
        id: '1356b7a1-af45-48f6-927a-e90626531da7',
        idJson: '84ea8683-08c3-4e68-83a2-d933822d7c90',
        estagio: {
          idJson: '6f426d16-e109-41e0-83a7-b0fdadbe1c1a'
        },
        grupoSemaforico: {
          idJson: '485293a9-ee0c-45ee-a140-97cf45274d09'
        }
      }, {
        id: '5fa6b76d-3955-444e-aea0-a2052ac3ab29',
        idJson: '1b76c9f8-271b-4e49-85a0-986a814143f8',
        estagio: {
          idJson: 'f2bf846c-108d-4d58-b7f9-e3a58b827952'
        },
        grupoSemaforico: {
          idJson: '25776b2b-b20a-4253-8781-d0fda2a0c1d7'
        }
      }, {
        id: 'ecd6bd7e-0c5f-4567-bab5-ed06e1abd61d',
        idJson: '93a23be4-4381-41e9-84d5-0ca51229d164',
        estagio: {
          idJson: '5c521612-2822-48e8-9a82-5e0c16d1a4bd'
        },
        grupoSemaforico: {
          idJson: 'c6497c38-52d1-40c8-901c-d3e955205331'
        }
      }, {
        id: '62dac8f8-f238-4e2a-919b-c37229da3b2b',
        idJson: '794000f3-bfb4-4d07-818a-29aa5a83cde6',
        estagio: {
          idJson: '6a1e9ca6-33d0-40e6-aa8a-a65f461a6e5c'
        },
        grupoSemaforico: {
          idJson: '14b52b33-08e8-436d-880d-015feb4eb861'
        }
      }, {
        id: '362a45a8-51b0-4bca-a779-78cc02edd6e3',
        idJson: 'f0535384-c37f-4993-8ae0-1dadd1c75a6b',
        estagio: {
          idJson: 'a1ed68a0-3849-404f-8720-974730471c4b'
        },
        grupoSemaforico: {
          idJson: '90294e1c-cc22-4977-ba8b-ba55d1412114'
        }
      }, {
        id: '438066db-1dd1-4b79-b703-a3ec57127d9b',
        idJson: '57010ae5-d858-45b8-841e-b4f1218c376a',
        estagio: {
          idJson: '5792c329-9250-4fef-ae9a-c44d56998451'
        },
        grupoSemaforico: {
          idJson: '919bbe48-05dc-4b98-bff1-f798c1cecd9e'
        }
      }, {
        id: '34087478-1948-4744-8851-83cffa3683d7',
        idJson: '6c59b27e-a589-4d27-95f1-584e0b2a3aab',
        estagio: {
          idJson: '5c521612-2822-48e8-9a82-5e0c16d1a4bd'
        },
        grupoSemaforico: {
          idJson: '566023c4-919d-4e33-a944-06596652f82b'
        }
      }, {
        id: '72f2fe3f-b189-4439-a692-3aaeca61fe67',
        idJson: 'b606ce85-fa94-465b-a08f-6067efdacbac',
        estagio: {
          idJson: '878776db-7e2e-438e-955b-8cc637d639de'
        },
        grupoSemaforico: {
          idJson: '85156d31-1984-46b1-9165-19875851c0c3'
        }
      }],
      verdesConflitantes: [{
        id: 'eeeffc41-a1ca-4652-a876-ee15f0e3b499',
        idJson: 'f5584579-8b74-4a33-ac30-3f5239fa0193',
        origem: {
          idJson: '266c69e2-e945-4133-a5c6-94e187c6a5cf'
        },
        destino: {
          idJson: '23ac30b7-bed1-4de1-a68f-c93fe13e9d23'
        }
      }, {
        id: 'f562f061-2ffe-44ee-b068-3342f1e8df7d',
        idJson: 'f3779664-8db4-4d05-92e5-1979a645cf10',
        origem: {
          idJson: 'ba57b9fe-7625-4068-bbdc-654982c7c16f'
        },
        destino: {
          idJson: '485293a9-ee0c-45ee-a140-97cf45274d09'
        }
      }, {
        id: '0abebe26-0c4b-4251-97fc-bc4e057177cb',
        idJson: '38351426-7b78-4e1b-af8f-865f432723a8',
        origem: {
          idJson: 'c6497c38-52d1-40c8-901c-d3e955205331'
        },
        destino: {
          idJson: 'a823ff37-8516-4fed-bdf5-5fb0d464824c'
        }
      }, {
        id: '6d5b0f90-c374-474f-b830-1f0b53dede35',
        idJson: '0783b0cd-9430-40eb-96da-0fb10e7d6bf4',
        origem: {
          idJson: '90294e1c-cc22-4977-ba8b-ba55d1412114'
        },
        destino: {
          idJson: '878e5030-9790-4ea5-b09a-07a1c27ba31d'
        }
      }, {
        id: '9aed3a50-ce61-493c-9b44-4d7616b77305',
        idJson: '8f0b6655-0d79-4f0e-b531-040b8fc1880b',
        origem: {
          idJson: '919bbe48-05dc-4b98-bff1-f798c1cecd9e'
        },
        destino: {
          idJson: '02cc5059-1559-4d4e-91e7-e2f6c521b7ad'
        }
      }, {
        id: '670949da-761b-45ea-a3fc-cacbe4b53e6c',
        idJson: '6ef952e9-3bbc-47d2-a789-242b5d20e4b1',
        origem: {
          idJson: '566023c4-919d-4e33-a944-06596652f82b'
        },
        destino: {
          idJson: 'd7c699a5-b93f-4778-9fa7-1e5b7577f91a'
        }
      }, {
        id: '3cf6033a-c143-41ea-b012-eab8ad8b7cac',
        idJson: 'f32b7de9-1a6c-48d6-892d-3ca2fada2274',
        origem: {
          idJson: '919bbe48-05dc-4b98-bff1-f798c1cecd9e'
        },
        destino: {
          idJson: '878e5030-9790-4ea5-b09a-07a1c27ba31d'
        }
      }, {
        id: '38f0dce9-f4c3-45f6-9e9e-a256c1a05c8b',
        idJson: '876ad577-9231-4bb7-84da-83a19481e491',
        origem: {
          idJson: '566023c4-919d-4e33-a944-06596652f82b'
        },
        destino: {
          idJson: 'a823ff37-8516-4fed-bdf5-5fb0d464824c'
        }
      }, {
        id: 'bf2d2902-d99c-4672-ba59-a3d27842b592',
        idJson: '945fef8b-dbf7-46a3-9628-781ada0aa1bc',
        origem: {
          idJson: 'd7c699a5-b93f-4778-9fa7-1e5b7577f91a'
        },
        destino: {
          idJson: 'a823ff37-8516-4fed-bdf5-5fb0d464824c'
        }
      }, {
        id: '78d6e933-381b-4006-a3fc-6e4264471e89',
        idJson: '2d343075-0e23-4933-b339-2b2841aacbe6',
        origem: {
          idJson: 'c6497c38-52d1-40c8-901c-d3e955205331'
        },
        destino: {
          idJson: '85156d31-1984-46b1-9165-19875851c0c3'
        }
      }, {
        id: '29e0d040-7b3b-4a0c-bd66-976871c67384',
        idJson: '71f03de1-490e-4e6e-b8bc-929c9ffce23b',
        origem: {
          idJson: 'ba57b9fe-7625-4068-bbdc-654982c7c16f'
        },
        destino: {
          idJson: '23ac30b7-bed1-4de1-a68f-c93fe13e9d23'
        }
      }, {
        id: '7aba206b-b794-449a-8a94-df1bddadb571',
        idJson: 'c116e1ce-c127-4771-bd6d-71209160121f',
        origem: {
          idJson: '566023c4-919d-4e33-a944-06596652f82b'
        },
        destino: {
          idJson: '85156d31-1984-46b1-9165-19875851c0c3'
        }
      }, {
        id: '57039c80-6321-4a5e-b2da-a276cc0c24ff',
        idJson: '74094da3-95eb-4243-b5b6-c95546f608bb',
        origem: {
          idJson: '85156d31-1984-46b1-9165-19875851c0c3'
        },
        destino: {
          idJson: 'a823ff37-8516-4fed-bdf5-5fb0d464824c'
        }
      }, {
        id: 'b75dd06d-71c8-4e08-8a2f-6c891f553316',
        idJson: '143450d4-6948-4c27-9743-4b8fb29a2566',
        origem: {
          idJson: 'ba57b9fe-7625-4068-bbdc-654982c7c16f'
        },
        destino: {
          idJson: '266c69e2-e945-4133-a5c6-94e187c6a5cf'
        }
      }, {
        id: 'c3350cb1-c258-43ed-bc6b-04a5f8e4cf51',
        idJson: 'a9ef2c93-783e-4ccf-a400-a1b9ef8e9204',
        origem: {
          idJson: '919bbe48-05dc-4b98-bff1-f798c1cecd9e'
        },
        destino: {
          idJson: '90294e1c-cc22-4977-ba8b-ba55d1412114'
        }
      }, {
        id: '696d649b-10c5-4640-af98-2efc408c0265',
        idJson: '240bdb70-0359-4268-bb29-8a567589ff88',
        origem: {
          idJson: '14b52b33-08e8-436d-880d-015feb4eb861'
        },
        destino: {
          idJson: '493742b7-ccff-4650-924e-ae8300dc5df0'
        }
      }, {
        id: 'ae16d0a3-8a5b-4c76-a7c1-1c6ff60ec2fe',
        idJson: '0d4f4215-6243-417f-8179-0fb97e4a9203',
        origem: {
          idJson: '266c69e2-e945-4133-a5c6-94e187c6a5cf'
        },
        destino: {
          idJson: '25776b2b-b20a-4253-8781-d0fda2a0c1d7'
        }
      }, {
        id: '9678b7aa-5b7b-4491-af88-e3ac9eeba2f9',
        idJson: '59706197-ba55-4432-9aff-813a77900662',
        origem: {
          idJson: 'c6497c38-52d1-40c8-901c-d3e955205331'
        },
        destino: {
          idJson: 'd7c699a5-b93f-4778-9fa7-1e5b7577f91a'
        }
      }, {
        id: '7a18adb7-a911-4b4d-b648-6cf299da66fd',
        idJson: '0a28a5aa-474c-407b-96bf-4a0e97f712fa',
        origem: {
          idJson: '90294e1c-cc22-4977-ba8b-ba55d1412114'
        },
        destino: {
          idJson: '02cc5059-1559-4d4e-91e7-e2f6c521b7ad'
        }
      }],
      transicoes: [{
        id: 'de6f49ad-5093-43cd-b74c-b2ef8a1fa504',
        idJson: '9240a164-f4f6-44a7-95d8-f7b490384db5',
        origem: {
          idJson: '88aab1ee-3962-4bd0-85fa-d766a53db12f'
        },
        destino: {
          idJson: '5792c329-9250-4fef-ae9a-c44d56998451'
        },
        tabelaEntreVerdesTransicoes: [{
          idJson: 'e7830120-12eb-4bad-8ac6-52b2a4e2e1e7'
        }],
        grupoSemaforico: {
          idJson: '02cc5059-1559-4d4e-91e7-e2f6c521b7ad'
        },
        tipo: 'PERDA_DE_PASSAGEM',
        tempoAtrasoGrupo: '0',
        atrasoDeGrupo: {
          idJson: 'e276cf3a-448b-410e-b69a-477daaba8610'
        },
        modoIntermitenteOuApagado: true
      }, {
        id: '4e56f71b-62c6-4f17-9e7e-d1cf7b2b6133',
        idJson: 'a1dfa985-c887-4eba-a025-6aa4debcdc91',
        origem: {
          idJson: '5c521612-2822-48e8-9a82-5e0c16d1a4bd'
        },
        destino: {
          idJson: '4d8d6135-7d86-4d98-9de4-568f09675345'
        },
        tabelaEntreVerdesTransicoes: [{
          idJson: 'e82152cc-e499-4ced-8666-fca70db35df3'
        }],
        grupoSemaforico: {
          idJson: 'c6497c38-52d1-40c8-901c-d3e955205331'
        },
        tipo: 'PERDA_DE_PASSAGEM',
        tempoAtrasoGrupo: '0',
        atrasoDeGrupo: {
          idJson: 'e734f381-cc33-43b5-86e7-0f5e35592462'
        },
        modoIntermitenteOuApagado: true
      }, {
        id: '6dd950bd-957f-41c2-9e6a-56299b0f8fdc',
        idJson: '19f70d49-3ab7-45de-a23a-172610f918e7',
        origem: {
          idJson: '766ffc1a-4772-4b81-8a3a-d7ee4d9a9562'
        },
        destino: {
          idJson: '6a1e9ca6-33d0-40e6-aa8a-a65f461a6e5c'
        },
        tabelaEntreVerdesTransicoes: [{
          idJson: '12ec5d05-7903-41c9-8606-37a49a12c11f'
        }],
        grupoSemaforico: {
          idJson: '493742b7-ccff-4650-924e-ae8300dc5df0'
        },
        tipo: 'PERDA_DE_PASSAGEM',
        tempoAtrasoGrupo: '0',
        atrasoDeGrupo: {
          idJson: '1c63a975-5f28-44a8-b67b-cedf92ce88dc'
        },
        modoIntermitenteOuApagado: true
      }, {
        id: '34b28db8-ad3f-4066-9ca3-47d98ad60f5b',
        idJson: 'bcfcd258-321d-4f24-b6d6-7d5c0cd32f87',
        origem: {
          idJson: 'f2bf846c-108d-4d58-b7f9-e3a58b827952'
        },
        destino: {
          idJson: '9529fbb9-9ac3-400d-9c23-d7c38d9140a1'
        },
        tabelaEntreVerdesTransicoes: [{
          idJson: 'd8259ebd-c4df-4c6d-9199-09d8687b32ee'
        }],
        grupoSemaforico: {
          idJson: '23ac30b7-bed1-4de1-a68f-c93fe13e9d23'
        },
        tipo: 'PERDA_DE_PASSAGEM',
        tempoAtrasoGrupo: '0',
        atrasoDeGrupo: {
          idJson: '1cad9f08-103e-445e-bce6-68ab92d9d4af'
        },
        modoIntermitenteOuApagado: true
      }, {
        id: '89a84569-6670-4706-8f64-5ee4e5583f9f',
        idJson: 'a24218d3-f97a-402a-8107-59d408dcf71e',
        origem: {
          idJson: 'a1ed68a0-3849-404f-8720-974730471c4b'
        },
        destino: {
          idJson: '5792c329-9250-4fef-ae9a-c44d56998451'
        },
        tabelaEntreVerdesTransicoes: [{
          idJson: '7655eebf-2bd3-46d9-8be7-4da49d95cb1f'
        }],
        grupoSemaforico: {
          idJson: '90294e1c-cc22-4977-ba8b-ba55d1412114'
        },
        tipo: 'PERDA_DE_PASSAGEM',
        tempoAtrasoGrupo: '0',
        atrasoDeGrupo: {
          idJson: '3d2fc0ec-d9dc-4402-bc6e-d31f7da5a85f'
        },
        modoIntermitenteOuApagado: true
      }, {
        id: '1e609a99-1261-453e-8e7b-e27b33d63b86',
        idJson: 'a3c18b9d-95b9-4bbc-b5f1-971d1d595bc1',
        origem: {
          idJson: '5c521612-2822-48e8-9a82-5e0c16d1a4bd'
        },
        destino: {
          idJson: '878776db-7e2e-438e-955b-8cc637d639de'
        },
        tabelaEntreVerdesTransicoes: [{
          idJson: '67cb6096-729e-482e-9fd0-142816d8275b'
        }],
        grupoSemaforico: {
          idJson: 'c6497c38-52d1-40c8-901c-d3e955205331'
        },
        tipo: 'PERDA_DE_PASSAGEM',
        tempoAtrasoGrupo: '0',
        atrasoDeGrupo: {
          idJson: '310e4f8c-057d-4044-b5dd-3957da5ec04a'
        },
        modoIntermitenteOuApagado: false
      }, {
        id: '58343a2a-c588-48c8-b2c6-cff25f36c53b',
        idJson: 'b765f211-68e2-490e-8bca-47da5c5597f0',
        origem: {
          idJson: 'f2bf846c-108d-4d58-b7f9-e3a58b827952'
        },
        destino: {
          idJson: '6f426d16-e109-41e0-83a7-b0fdadbe1c1a'
        },
        tabelaEntreVerdesTransicoes: [{
          idJson: 'd87d1f47-4b09-47e5-8d90-cf7015367685'
        }],
        grupoSemaforico: {
          idJson: '23ac30b7-bed1-4de1-a68f-c93fe13e9d23'
        },
        tipo: 'PERDA_DE_PASSAGEM',
        tempoAtrasoGrupo: '0',
        atrasoDeGrupo: {
          idJson: 'b2b7867c-a356-421f-89cd-b27884dbe88a'
        },
        modoIntermitenteOuApagado: false
      }, {
        id: '31ec6554-c626-4f1d-82bb-ada0dcd2422b',
        idJson: '9a3ffa05-8189-41a2-9bc5-394cace2a11a',
        origem: {
          idJson: '6f426d16-e109-41e0-83a7-b0fdadbe1c1a'
        },
        destino: {
          idJson: '9529fbb9-9ac3-400d-9c23-d7c38d9140a1'
        },
        tabelaEntreVerdesTransicoes: [{
          idJson: 'e661c662-39dc-4a02-916c-93eeb524c6f7'
        }],
        grupoSemaforico: {
          idJson: '266c69e2-e945-4133-a5c6-94e187c6a5cf'
        },
        tipo: 'PERDA_DE_PASSAGEM',
        tempoAtrasoGrupo: '0',
        atrasoDeGrupo: {
          idJson: '92c78668-9e34-4ad6-8ab5-d1af77bab64e'
        },
        modoIntermitenteOuApagado: true
      }, {
        id: '4e18b858-c795-409d-b129-7ebc19c98882',
        idJson: '6d2025c8-83a2-4725-9f45-6104ab95517b',
        origem: {
          idJson: '878776db-7e2e-438e-955b-8cc637d639de'
        },
        destino: {
          idJson: '4d8d6135-7d86-4d98-9de4-568f09675345'
        },
        tabelaEntreVerdesTransicoes: [{
          idJson: '4adc677c-97d3-4623-853a-e05d0693d9bf'
        }],
        grupoSemaforico: {
          idJson: 'd7c699a5-b93f-4778-9fa7-1e5b7577f91a'
        },
        tipo: 'PERDA_DE_PASSAGEM',
        tempoAtrasoGrupo: '0',
        atrasoDeGrupo: {
          idJson: '1e2f136d-eb14-4474-bd10-4981b2fcb1f0'
        },
        modoIntermitenteOuApagado: true
      }, {
        id: '6cd3c69b-5186-445d-9280-4e3f720b8ba7',
        idJson: 'df020540-575b-4d30-9ade-facdfad53ed4',
        origem: {
          idJson: '9529fbb9-9ac3-400d-9c23-d7c38d9140a1'
        },
        destino: {
          idJson: '6f426d16-e109-41e0-83a7-b0fdadbe1c1a'
        },
        tabelaEntreVerdesTransicoes: [{
          idJson: 'ed86e67d-9a55-497e-8598-18fbbec34a29'
        }],
        grupoSemaforico: {
          idJson: 'ba57b9fe-7625-4068-bbdc-654982c7c16f'
        },
        tipo: 'PERDA_DE_PASSAGEM',
        tempoAtrasoGrupo: '0',
        atrasoDeGrupo: {
          idJson: '6c2c4328-1395-4733-8841-95d88d0162bd'
        },
        modoIntermitenteOuApagado: true
      }, {
        id: '3dfce74d-b2d9-4552-8abc-2c766e7a4a80',
        idJson: '9ebf777d-7f78-455f-9c49-8d30ba09cc82',
        origem: {
          idJson: 'a1ed68a0-3849-404f-8720-974730471c4b'
        },
        destino: {
          idJson: '88aab1ee-3962-4bd0-85fa-d766a53db12f'
        },
        tabelaEntreVerdesTransicoes: [{
          idJson: '1bb721fd-d1b6-4817-92e8-bab81541a797'
        }],
        grupoSemaforico: {
          idJson: '90294e1c-cc22-4977-ba8b-ba55d1412114'
        },
        tipo: 'PERDA_DE_PASSAGEM',
        tempoAtrasoGrupo: '0',
        atrasoDeGrupo: {
          idJson: 'f9961440-e593-41c4-97a4-887119b4a073'
        },
        modoIntermitenteOuApagado: false
      }, {
        id: '3c8664e9-cfbf-4490-b297-cb27cc591d42',
        idJson: '7f1a75ff-7cab-42b3-b799-e75471e84ee7',
        origem: {
          idJson: '6f426d16-e109-41e0-83a7-b0fdadbe1c1a'
        },
        destino: {
          idJson: '9529fbb9-9ac3-400d-9c23-d7c38d9140a1'
        },
        tabelaEntreVerdesTransicoes: [{
          idJson: '82bfaa9a-00f9-439d-966e-3466e6d8f270'
        }],
        grupoSemaforico: {
          idJson: '485293a9-ee0c-45ee-a140-97cf45274d09'
        },
        tipo: 'PERDA_DE_PASSAGEM',
        tempoAtrasoGrupo: '0',
        atrasoDeGrupo: {
          idJson: 'acc5cd47-794f-44d1-b3b5-a2521acc540b'
        },
        modoIntermitenteOuApagado: true
      }, {
        id: 'b56e803c-5b6e-4c9b-a4ae-b02ff1655dce',
        idJson: 'e045c5de-cb9f-4299-9eb1-5d5c168836fb',
        origem: {
          idJson: '878776db-7e2e-438e-955b-8cc637d639de'
        },
        destino: {
          idJson: '5c521612-2822-48e8-9a82-5e0c16d1a4bd'
        },
        tabelaEntreVerdesTransicoes: [{
          idJson: '0f0d45de-d25f-4e4e-b527-78b60e659f1f'
        }],
        grupoSemaforico: {
          idJson: '85156d31-1984-46b1-9165-19875851c0c3'
        },
        tipo: 'PERDA_DE_PASSAGEM',
        tempoAtrasoGrupo: '0',
        atrasoDeGrupo: {
          idJson: '53fb3711-f98e-422f-aaa6-2b86240fb1f1'
        },
        modoIntermitenteOuApagado: true
      }, {
        id: 'f03bba7c-e5d1-405f-96f2-6b6a47644124',
        idJson: 'cfcc8dc1-260c-49f2-975f-a9906bb5c5a8',
        origem: {
          idJson: '9529fbb9-9ac3-400d-9c23-d7c38d9140a1'
        },
        destino: {
          idJson: 'f2bf846c-108d-4d58-b7f9-e3a58b827952'
        },
        tabelaEntreVerdesTransicoes: [{
          idJson: 'ab7f3591-075b-46b6-bc9d-90089a3bde27'
        }],
        grupoSemaforico: {
          idJson: 'ba57b9fe-7625-4068-bbdc-654982c7c16f'
        },
        tipo: 'PERDA_DE_PASSAGEM',
        tempoAtrasoGrupo: '0',
        atrasoDeGrupo: {
          idJson: '159714d6-a887-433a-8be4-040f1a869a76'
        },
        modoIntermitenteOuApagado: false
      }, {
        id: 'b42c39fb-fd26-483f-80a5-a4418c7db023',
        idJson: '3c92af69-2266-4e75-8efe-0cfbe5ea2336',
        origem: {
          idJson: '5792c329-9250-4fef-ae9a-c44d56998451'
        },
        destino: {
          idJson: '88aab1ee-3962-4bd0-85fa-d766a53db12f'
        },
        tabelaEntreVerdesTransicoes: [{
          idJson: '2365ed47-6f9e-4016-ae1f-38165b25717a'
        }],
        grupoSemaforico: {
          idJson: '919bbe48-05dc-4b98-bff1-f798c1cecd9e'
        },
        tipo: 'PERDA_DE_PASSAGEM',
        tempoAtrasoGrupo: '0',
        atrasoDeGrupo: {
          idJson: '7b94eb4d-d208-4655-a6a3-e15d40117262'
        },
        modoIntermitenteOuApagado: true
      }, {
        id: '3491b934-f8af-4f7c-8c9b-8f8c1c5e6619',
        idJson: 'fa0ad62b-8b4a-4889-812c-45000bc62efb',
        origem: {
          idJson: '5792c329-9250-4fef-ae9a-c44d56998451'
        },
        destino: {
          idJson: 'a1ed68a0-3849-404f-8720-974730471c4b'
        },
        tabelaEntreVerdesTransicoes: [{
          idJson: '253f45c4-d652-44ba-b668-bb8ab5a123ca'
        }],
        grupoSemaforico: {
          idJson: '919bbe48-05dc-4b98-bff1-f798c1cecd9e'
        },
        tipo: 'PERDA_DE_PASSAGEM',
        tempoAtrasoGrupo: '0',
        atrasoDeGrupo: {
          idJson: '7e635d94-ff9e-4183-8549-f175faedff17'
        },
        modoIntermitenteOuApagado: false
      }, {
        id: '14ff514e-a4e1-45cd-8495-01acf94fe618',
        idJson: '97033230-5600-4c5b-9d85-d94338875f8d',
        origem: {
          idJson: '5c521612-2822-48e8-9a82-5e0c16d1a4bd'
        },
        destino: {
          idJson: '4d8d6135-7d86-4d98-9de4-568f09675345'
        },
        tabelaEntreVerdesTransicoes: [{
          idJson: '976b5cc7-1d0a-468c-87c7-9b70626bb52a'
        }],
        grupoSemaforico: {
          idJson: '566023c4-919d-4e33-a944-06596652f82b'
        },
        tipo: 'PERDA_DE_PASSAGEM',
        tempoAtrasoGrupo: '0',
        atrasoDeGrupo: {
          idJson: 'c851a810-01d7-4a48-bbfd-341699988ece'
        },
        modoIntermitenteOuApagado: true
      }, {
        id: 'cd28e907-56ab-4dc9-83e4-47cda28d3a92',
        idJson: 'b5311620-e9ba-4760-ac9e-10e86144c688',
        origem: {
          idJson: '5c521612-2822-48e8-9a82-5e0c16d1a4bd'
        },
        destino: {
          idJson: '878776db-7e2e-438e-955b-8cc637d639de'
        },
        tabelaEntreVerdesTransicoes: [{
          idJson: '864e078b-8045-4bc6-80a8-2d7aa347e046'
        }],
        grupoSemaforico: {
          idJson: '566023c4-919d-4e33-a944-06596652f82b'
        },
        tipo: 'PERDA_DE_PASSAGEM',
        tempoAtrasoGrupo: '0',
        atrasoDeGrupo: {
          idJson: '17d9f5c9-47a4-4109-88b3-95140a95c014'
        },
        modoIntermitenteOuApagado: false
      }, {
        id: 'a20c2f7b-27ac-4daf-a934-cd7d34109a3a',
        idJson: 'e850b442-c77e-4284-ab6c-231c250ca67b',
        origem: {
          idJson: '88aab1ee-3962-4bd0-85fa-d766a53db12f'
        },
        destino: {
          idJson: 'a1ed68a0-3849-404f-8720-974730471c4b'
        },
        tabelaEntreVerdesTransicoes: [{
          idJson: '84f02d9e-22e7-4931-af93-8b594d4c6e7f'
        }],
        grupoSemaforico: {
          idJson: '878e5030-9790-4ea5-b09a-07a1c27ba31d'
        },
        tipo: 'PERDA_DE_PASSAGEM',
        tempoAtrasoGrupo: '0',
        atrasoDeGrupo: {
          idJson: '26d36999-e31b-49c7-8210-c1aeaceec4c2'
        },
        modoIntermitenteOuApagado: true
      }, {
        id: '4e8e62ba-de76-49de-9ec9-ba8b94ec2a10',
        idJson: '4299e3b4-f7b7-42d8-a531-321557299902',
        origem: {
          idJson: '878776db-7e2e-438e-955b-8cc637d639de'
        },
        destino: {
          idJson: '4d8d6135-7d86-4d98-9de4-568f09675345'
        },
        tabelaEntreVerdesTransicoes: [{
          idJson: '7af2d1c5-1a44-4c85-8ac9-4436bf57472b'
        }],
        grupoSemaforico: {
          idJson: '85156d31-1984-46b1-9165-19875851c0c3'
        },
        tipo: 'PERDA_DE_PASSAGEM',
        tempoAtrasoGrupo: '0',
        atrasoDeGrupo: {
          idJson: 'cb5b54f2-2114-42c8-8330-259dc50cccd7'
        },
        modoIntermitenteOuApagado: false
      }, {
        id: '8271fc82-ab9b-45c8-b4a6-7010d6e1c210',
        idJson: 'a2261cfb-901e-42f6-920e-b59cbee8e872',
        origem: {
          idJson: '88aab1ee-3962-4bd0-85fa-d766a53db12f'
        },
        destino: {
          idJson: 'a1ed68a0-3849-404f-8720-974730471c4b'
        },
        tabelaEntreVerdesTransicoes: [{
          idJson: '7c7ed48c-69bf-4a37-97a0-5189428bcfd7'
        }],
        grupoSemaforico: {
          idJson: '02cc5059-1559-4d4e-91e7-e2f6c521b7ad'
        },
        tipo: 'PERDA_DE_PASSAGEM',
        tempoAtrasoGrupo: '0',
        atrasoDeGrupo: {
          idJson: '36eff934-5eb7-4e38-ae75-bb3e392e9580'
        },
        modoIntermitenteOuApagado: false
      }, {
        id: '7330c743-b43f-4803-baec-4209119c0175',
        idJson: '4adb694a-8769-42c9-8e8a-79c9d5bda5c7',
        origem: {
          idJson: '9529fbb9-9ac3-400d-9c23-d7c38d9140a1'
        },
        destino: {
          idJson: '6f426d16-e109-41e0-83a7-b0fdadbe1c1a'
        },
        tabelaEntreVerdesTransicoes: [{
          idJson: '38de2ae5-92c7-4ab3-bf9e-79ef5cc1d4f9'
        }],
        grupoSemaforico: {
          idJson: '25776b2b-b20a-4253-8781-d0fda2a0c1d7'
        },
        tipo: 'PERDA_DE_PASSAGEM',
        tempoAtrasoGrupo: '0',
        atrasoDeGrupo: {
          idJson: 'ac28b35d-b247-49b4-9446-7c41dd7236de'
        },
        modoIntermitenteOuApagado: true
      }, {
        id: '618962f2-08a2-41ee-9661-78c7f8c834e0',
        idJson: 'da621643-484a-4fac-a188-85601b5af369',
        origem: {
          idJson: 'f2bf846c-108d-4d58-b7f9-e3a58b827952'
        },
        destino: {
          idJson: '9529fbb9-9ac3-400d-9c23-d7c38d9140a1'
        },
        tabelaEntreVerdesTransicoes: [{
          idJson: 'e14a3984-ae25-4099-baee-e3451524596b'
        }],
        grupoSemaforico: {
          idJson: '485293a9-ee0c-45ee-a140-97cf45274d09'
        },
        tipo: 'PERDA_DE_PASSAGEM',
        tempoAtrasoGrupo: '0',
        atrasoDeGrupo: {
          idJson: '37589912-89ec-4d2d-8314-42a8e2821267'
        },
        modoIntermitenteOuApagado: true
      }, {
        id: '81327106-aa23-4eca-9ed5-2de108244205',
        idJson: 'ef571ca3-8b68-40fe-998f-bb87037c343a',
        origem: {
          idJson: 'f2bf846c-108d-4d58-b7f9-e3a58b827952'
        },
        destino: {
          idJson: '6f426d16-e109-41e0-83a7-b0fdadbe1c1a'
        },
        tabelaEntreVerdesTransicoes: [{
          idJson: 'a8b090c8-1350-46ed-b562-cd875262c876'
        }],
        grupoSemaforico: {
          idJson: '25776b2b-b20a-4253-8781-d0fda2a0c1d7'
        },
        tipo: 'PERDA_DE_PASSAGEM',
        tempoAtrasoGrupo: '0',
        atrasoDeGrupo: {
          idJson: 'cde0a6bd-9064-49f0-9e2a-aa5623b9f8a9'
        },
        modoIntermitenteOuApagado: true
      }, {
        id: '154dbb6f-3d11-407d-b468-81f4e25ee963',
        idJson: 'bab2a895-d128-4642-85ad-b7324331cdcc',
        origem: {
          idJson: '4d8d6135-7d86-4d98-9de4-568f09675345'
        },
        destino: {
          idJson: '878776db-7e2e-438e-955b-8cc637d639de'
        },
        tabelaEntreVerdesTransicoes: [{
          idJson: '5a93d0a6-4dc9-4f55-a4b4-a2e388c917aa'
        }],
        grupoSemaforico: {
          idJson: 'a823ff37-8516-4fed-bdf5-5fb0d464824c'
        },
        tipo: 'PERDA_DE_PASSAGEM',
        tempoAtrasoGrupo: '0',
        atrasoDeGrupo: {
          idJson: '5a8a4c85-20bd-42c6-b17b-4f56edb336ad'
        },
        modoIntermitenteOuApagado: true
      }, {
        id: '8338f4f1-4a5d-4801-bb84-25885e27deec',
        idJson: 'a88f9581-cfa1-4c7d-818b-78dd7cafb819',
        origem: {
          idJson: '4d8d6135-7d86-4d98-9de4-568f09675345'
        },
        destino: {
          idJson: '5c521612-2822-48e8-9a82-5e0c16d1a4bd'
        },
        tabelaEntreVerdesTransicoes: [{
          idJson: 'cee9ce58-4c3a-4a35-9068-077f6abff0ef'
        }],
        grupoSemaforico: {
          idJson: 'a823ff37-8516-4fed-bdf5-5fb0d464824c'
        },
        tipo: 'PERDA_DE_PASSAGEM',
        tempoAtrasoGrupo: '0',
        atrasoDeGrupo: {
          idJson: 'b4ac3c60-31e1-4dc4-b624-c93d860a9112'
        },
        modoIntermitenteOuApagado: false
      }, {
        id: '4be57253-a738-482b-af46-f65688791e3c',
        idJson: '3afb5c03-444b-40ef-8d4e-60d4349dda46',
        origem: {
          idJson: '6a1e9ca6-33d0-40e6-aa8a-a65f461a6e5c'
        },
        destino: {
          idJson: '766ffc1a-4772-4b81-8a3a-d7ee4d9a9562'
        },
        tabelaEntreVerdesTransicoes: [{
          idJson: 'a2e831c6-a461-489f-a492-a33bce7ef6fb'
        }],
        grupoSemaforico: {
          idJson: '14b52b33-08e8-436d-880d-015feb4eb861'
        },
        tipo: 'PERDA_DE_PASSAGEM',
        tempoAtrasoGrupo: '0',
        atrasoDeGrupo: {
          idJson: '9a23f0dc-452d-400c-a573-f38cffbbb0b4'
        },
        modoIntermitenteOuApagado: true
      }, {
        id: 'b4a950ff-31b7-4672-80f7-d24b026459a0',
        idJson: '766b0fd3-6802-4ed2-95fa-0e76cac3f312',
        origem: {
          idJson: '6f426d16-e109-41e0-83a7-b0fdadbe1c1a'
        },
        destino: {
          idJson: 'f2bf846c-108d-4d58-b7f9-e3a58b827952'
        },
        tabelaEntreVerdesTransicoes: [{
          idJson: '4692e00b-8d38-45e4-84c8-dc3417c9004b'
        }],
        grupoSemaforico: {
          idJson: '266c69e2-e945-4133-a5c6-94e187c6a5cf'
        },
        tipo: 'PERDA_DE_PASSAGEM',
        tempoAtrasoGrupo: '0',
        atrasoDeGrupo: {
          idJson: 'de8de944-908c-446a-a44c-3d7fec2152be'
        },
        modoIntermitenteOuApagado: false
      }, {
        id: '4ea5b3fc-5ba4-4047-a378-0b509159f08c',
        idJson: '29a07f62-5228-4d11-9ac7-08816e9d96e0',
        origem: {
          idJson: '88aab1ee-3962-4bd0-85fa-d766a53db12f'
        },
        destino: {
          idJson: '5792c329-9250-4fef-ae9a-c44d56998451'
        },
        tabelaEntreVerdesTransicoes: [{
          idJson: '224f1859-0285-4a78-a359-94b3c4055315'
        }],
        grupoSemaforico: {
          idJson: '878e5030-9790-4ea5-b09a-07a1c27ba31d'
        },
        tipo: 'PERDA_DE_PASSAGEM',
        tempoAtrasoGrupo: '0',
        atrasoDeGrupo: {
          idJson: '250ec900-ed92-404b-8543-bf9801b214f3'
        },
        modoIntermitenteOuApagado: false
      }, {
        id: 'dc65469d-599b-4b10-aa1e-c09ce6bdbfa7',
        idJson: '7d8d2a88-89af-444f-a8aa-f976b01f8ac6',
        origem: {
          idJson: '878776db-7e2e-438e-955b-8cc637d639de'
        },
        destino: {
          idJson: '5c521612-2822-48e8-9a82-5e0c16d1a4bd'
        },
        tabelaEntreVerdesTransicoes: [{
          idJson: '358e3f03-64e5-4927-8bca-4de4dd0efd73'
        }],
        grupoSemaforico: {
          idJson: 'd7c699a5-b93f-4778-9fa7-1e5b7577f91a'
        },
        tipo: 'PERDA_DE_PASSAGEM',
        tempoAtrasoGrupo: '0',
        atrasoDeGrupo: {
          idJson: '86aa7391-6649-49a6-bcab-d59d32d8e579'
        },
        modoIntermitenteOuApagado: false
      }],
      transicoesComGanhoDePassagem: [{
        id: '231b8490-343a-4940-bb9c-29f474c8ffd0',
        idJson: '24cacb8c-b151-4961-b312-82f9b2196da4',
        origem: {
          idJson: 'f2bf846c-108d-4d58-b7f9-e3a58b827952'
        },
        destino: {
          idJson: '9529fbb9-9ac3-400d-9c23-d7c38d9140a1'
        },
        tabelaEntreVerdesTransicoes: [

        ],
        grupoSemaforico: {
          idJson: 'ba57b9fe-7625-4068-bbdc-654982c7c16f'
        },
        tipo: 'GANHO_DE_PASSAGEM',
        tempoAtrasoGrupo: '0',
        atrasoDeGrupo: {
          idJson: '66c83ccb-a85a-400e-aecc-d1ac3607a071'
        },
        modoIntermitenteOuApagado: false
      }, {
        id: '1f834511-6faa-49bf-8ec5-129e42de287a',
        idJson: '3c69d11d-d145-4034-82e6-18d44ec4d99e',
        origem: {
          idJson: '9529fbb9-9ac3-400d-9c23-d7c38d9140a1'
        },
        destino: {
          idJson: '6f426d16-e109-41e0-83a7-b0fdadbe1c1a'
        },
        tabelaEntreVerdesTransicoes: [

        ],
        grupoSemaforico: {
          idJson: '485293a9-ee0c-45ee-a140-97cf45274d09'
        },
        tipo: 'GANHO_DE_PASSAGEM',
        tempoAtrasoGrupo: '0',
        atrasoDeGrupo: {
          idJson: '7d613382-a2f7-4d85-a131-04c28a353141'
        },
        modoIntermitenteOuApagado: false
      }, {
        id: '279b7dc0-cace-42a6-9f40-cd059f3b9b9e',
        idJson: 'f26b555a-8c07-4292-814a-328fa39012b2',
        origem: {
          idJson: '6f426d16-e109-41e0-83a7-b0fdadbe1c1a'
        },
        destino: {
          idJson: '9529fbb9-9ac3-400d-9c23-d7c38d9140a1'
        },
        tabelaEntreVerdesTransicoes: [

        ],
        grupoSemaforico: {
          idJson: '25776b2b-b20a-4253-8781-d0fda2a0c1d7'
        },
        tipo: 'GANHO_DE_PASSAGEM',
        tempoAtrasoGrupo: '0',
        atrasoDeGrupo: {
          idJson: 'b9507206-a2e7-4266-adb8-9f0cbb56d910'
        },
        modoIntermitenteOuApagado: false
      }, {
        id: '259b862f-075f-4e23-a14c-8f222daa0591',
        idJson: '5e907490-2c21-47cc-ad91-f647463b5e0c',
        origem: {
          idJson: '9529fbb9-9ac3-400d-9c23-d7c38d9140a1'
        },
        destino: {
          idJson: 'f2bf846c-108d-4d58-b7f9-e3a58b827952'
        },
        tabelaEntreVerdesTransicoes: [

        ],
        grupoSemaforico: {
          idJson: '23ac30b7-bed1-4de1-a68f-c93fe13e9d23'
        },
        tipo: 'GANHO_DE_PASSAGEM',
        tempoAtrasoGrupo: '0',
        atrasoDeGrupo: {
          idJson: 'bae9fadd-c2d8-4e65-be44-613fcef97de6'
        },
        modoIntermitenteOuApagado: false
      }, {
        id: 'e98396d1-b247-43c6-b7d3-d1fda9d773c9',
        idJson: '227d745e-9102-477f-a795-3cfc5f6b241e',
        origem: {
          idJson: '4d8d6135-7d86-4d98-9de4-568f09675345'
        },
        destino: {
          idJson: '878776db-7e2e-438e-955b-8cc637d639de'
        },
        tabelaEntreVerdesTransicoes: [

        ],
        grupoSemaforico: {
          idJson: 'd7c699a5-b93f-4778-9fa7-1e5b7577f91a'
        },
        tipo: 'GANHO_DE_PASSAGEM',
        tempoAtrasoGrupo: '0',
        atrasoDeGrupo: {
          idJson: '4fa5650b-67d2-4b04-824f-6d59137bb29c'
        },
        modoIntermitenteOuApagado: false
      }, {
        id: '5ece019c-ac95-4938-b03a-36bf6b5dad4c',
        idJson: '8010fc9f-4d08-4b75-8054-386935d36298',
        origem: {
          idJson: '4d8d6135-7d86-4d98-9de4-568f09675345'
        },
        destino: {
          idJson: '5c521612-2822-48e8-9a82-5e0c16d1a4bd'
        },
        tabelaEntreVerdesTransicoes: [

        ],
        grupoSemaforico: {
          idJson: 'c6497c38-52d1-40c8-901c-d3e955205331'
        },
        tipo: 'GANHO_DE_PASSAGEM',
        tempoAtrasoGrupo: '0',
        atrasoDeGrupo: {
          idJson: '5ff745af-c867-4c2e-88ad-64372645ff63'
        },
        modoIntermitenteOuApagado: false
      }, {
        id: 'cb20b269-ebe5-4c07-81f5-f6c55e7d28ff',
        idJson: 'f7e5f078-3559-481c-a4a6-aeae5e161e70',
        origem: {
          idJson: '6f426d16-e109-41e0-83a7-b0fdadbe1c1a'
        },
        destino: {
          idJson: 'f2bf846c-108d-4d58-b7f9-e3a58b827952'
        },
        tabelaEntreVerdesTransicoes: [

        ],
        grupoSemaforico: {
          idJson: '23ac30b7-bed1-4de1-a68f-c93fe13e9d23'
        },
        tipo: 'GANHO_DE_PASSAGEM',
        tempoAtrasoGrupo: '0',
        atrasoDeGrupo: {
          idJson: 'ae748de6-6b18-467c-bb6d-1070458a0d48'
        },
        modoIntermitenteOuApagado: false
      }, {
        id: '92ac7f08-bb1c-498f-afa8-ad62cefafa33',
        idJson: '77658864-14b2-4cb3-97d2-e6108915d598',
        origem: {
          idJson: '4d8d6135-7d86-4d98-9de4-568f09675345'
        },
        destino: {
          idJson: '5c521612-2822-48e8-9a82-5e0c16d1a4bd'
        },
        tabelaEntreVerdesTransicoes: [

        ],
        grupoSemaforico: {
          idJson: '566023c4-919d-4e33-a944-06596652f82b'
        },
        tipo: 'GANHO_DE_PASSAGEM',
        tempoAtrasoGrupo: '0',
        atrasoDeGrupo: {
          idJson: '3617278f-be07-4d84-b74a-9d1c03644310'
        },
        modoIntermitenteOuApagado: false
      }, {
        id: 'a46c973c-4a86-4d91-9463-9d8003372b85',
        idJson: '8b75c1ef-ad96-4ebd-a452-186f725c0423',
        origem: {
          idJson: '5792c329-9250-4fef-ae9a-c44d56998451'
        },
        destino: {
          idJson: '88aab1ee-3962-4bd0-85fa-d766a53db12f'
        },
        tabelaEntreVerdesTransicoes: [

        ],
        grupoSemaforico: {
          idJson: '02cc5059-1559-4d4e-91e7-e2f6c521b7ad'
        },
        tipo: 'GANHO_DE_PASSAGEM',
        tempoAtrasoGrupo: '0',
        atrasoDeGrupo: {
          idJson: '6ad2920a-be72-48f8-90ff-45e8f4fbfc2f'
        },
        modoIntermitenteOuApagado: false
      }, {
        id: '63d88438-3ed7-486e-a0fc-fba1409461d4',
        idJson: '298517e7-4874-4592-b7ac-ea50a2ec40dd',
        origem: {
          idJson: '878776db-7e2e-438e-955b-8cc637d639de'
        },
        destino: {
          idJson: '5c521612-2822-48e8-9a82-5e0c16d1a4bd'
        },
        tabelaEntreVerdesTransicoes: [

        ],
        grupoSemaforico: {
          idJson: 'c6497c38-52d1-40c8-901c-d3e955205331'
        },
        tipo: 'GANHO_DE_PASSAGEM',
        tempoAtrasoGrupo: '0',
        atrasoDeGrupo: {
          idJson: '8db63aa3-a1a1-433c-b5dd-4764863ebe28'
        },
        modoIntermitenteOuApagado: false
      }, {
        id: '17701604-4c95-4848-aede-383b8fc1c842',
        idJson: '418b17ac-9f2e-468a-a280-d30d1a036988',
        origem: {
          idJson: '5792c329-9250-4fef-ae9a-c44d56998451'
        },
        destino: {
          idJson: 'a1ed68a0-3849-404f-8720-974730471c4b'
        },
        tabelaEntreVerdesTransicoes: [

        ],
        grupoSemaforico: {
          idJson: '90294e1c-cc22-4977-ba8b-ba55d1412114'
        },
        tipo: 'GANHO_DE_PASSAGEM',
        tempoAtrasoGrupo: '0',
        atrasoDeGrupo: {
          idJson: '9fd12dda-dfc9-46fc-956d-242c856be527'
        },
        modoIntermitenteOuApagado: false
      }, {
        id: '057c1312-b210-4276-8266-58fd77a70b50',
        idJson: 'd016483f-54a3-433b-8c37-7585491d0bfb',
        origem: {
          idJson: '5c521612-2822-48e8-9a82-5e0c16d1a4bd'
        },
        destino: {
          idJson: '4d8d6135-7d86-4d98-9de4-568f09675345'
        },
        tabelaEntreVerdesTransicoes: [

        ],
        grupoSemaforico: {
          idJson: 'a823ff37-8516-4fed-bdf5-5fb0d464824c'
        },
        tipo: 'GANHO_DE_PASSAGEM',
        tempoAtrasoGrupo: '0',
        atrasoDeGrupo: {
          idJson: '597fb9f7-aff4-4bec-b518-8df2adc52e4e'
        },
        modoIntermitenteOuApagado: false
      }, {
        id: 'eb963444-5a6b-41c9-ba42-99976f4f58dc',
        idJson: '962c9f21-981c-4040-baf0-dd299180191b',
        origem: {
          idJson: 'f2bf846c-108d-4d58-b7f9-e3a58b827952'
        },
        destino: {
          idJson: '6f426d16-e109-41e0-83a7-b0fdadbe1c1a'
        },
        tabelaEntreVerdesTransicoes: [

        ],
        grupoSemaforico: {
          idJson: '266c69e2-e945-4133-a5c6-94e187c6a5cf'
        },
        tipo: 'GANHO_DE_PASSAGEM',
        tempoAtrasoGrupo: '0',
        atrasoDeGrupo: {
          idJson: '01d02724-d39b-4a90-9870-2b8eeb885087'
        },
        modoIntermitenteOuApagado: false
      }, {
        id: '8f68171d-ae27-4ebf-90a7-8dfa67769dcb',
        idJson: 'b5ea6e61-1bf5-4af1-963c-f8f79cd9d674',
        origem: {
          idJson: '9529fbb9-9ac3-400d-9c23-d7c38d9140a1'
        },
        destino: {
          idJson: '6f426d16-e109-41e0-83a7-b0fdadbe1c1a'
        },
        tabelaEntreVerdesTransicoes: [

        ],
        grupoSemaforico: {
          idJson: '266c69e2-e945-4133-a5c6-94e187c6a5cf'
        },
        tipo: 'GANHO_DE_PASSAGEM',
        tempoAtrasoGrupo: '0',
        atrasoDeGrupo: {
          idJson: '8a3259d3-5544-4153-97d7-4bb388bb6974'
        },
        modoIntermitenteOuApagado: false
      }, {
        id: '3f2af082-e19b-4b29-b50d-31861052a468',
        idJson: '3c69bcaf-df55-48b6-a0a4-e8108b8a3af0',
        origem: {
          idJson: '6a1e9ca6-33d0-40e6-aa8a-a65f461a6e5c'
        },
        destino: {
          idJson: '766ffc1a-4772-4b81-8a3a-d7ee4d9a9562'
        },
        tabelaEntreVerdesTransicoes: [

        ],
        grupoSemaforico: {
          idJson: '493742b7-ccff-4650-924e-ae8300dc5df0'
        },
        tipo: 'GANHO_DE_PASSAGEM',
        tempoAtrasoGrupo: '0',
        atrasoDeGrupo: {
          idJson: 'ae05a546-4f72-408b-a12b-4d9159d80994'
        },
        modoIntermitenteOuApagado: false
      }, {
        id: 'a0551e76-c52c-4495-a3ed-006153519fae',
        idJson: 'be5708aa-eda8-47dc-a680-569d68598974',
        origem: {
          idJson: 'a1ed68a0-3849-404f-8720-974730471c4b'
        },
        destino: {
          idJson: '88aab1ee-3962-4bd0-85fa-d766a53db12f'
        },
        tabelaEntreVerdesTransicoes: [

        ],
        grupoSemaforico: {
          idJson: '02cc5059-1559-4d4e-91e7-e2f6c521b7ad'
        },
        tipo: 'GANHO_DE_PASSAGEM',
        tempoAtrasoGrupo: '0',
        atrasoDeGrupo: {
          idJson: '36ee1383-d374-4ee7-905e-0586f672f23c'
        },
        modoIntermitenteOuApagado: false
      }, {
        id: '836dc29c-c949-4fc9-906c-6052c45da8db',
        idJson: 'ede7f959-347a-4a35-a1e6-cd17004908e1',
        origem: {
          idJson: '9529fbb9-9ac3-400d-9c23-d7c38d9140a1'
        },
        destino: {
          idJson: 'f2bf846c-108d-4d58-b7f9-e3a58b827952'
        },
        tabelaEntreVerdesTransicoes: [

        ],
        grupoSemaforico: {
          idJson: '485293a9-ee0c-45ee-a140-97cf45274d09'
        },
        tipo: 'GANHO_DE_PASSAGEM',
        tempoAtrasoGrupo: '0',
        atrasoDeGrupo: {
          idJson: 'def659c5-f839-45b3-a7e7-977d1085e57e'
        },
        modoIntermitenteOuApagado: false
      }, {
        id: '4f3fe2e9-4c88-4736-9f0c-f7f5d29a9aa1',
        idJson: '6bc643bf-a508-426c-b5af-6e2f84b0ce8c',
        origem: {
          idJson: '878776db-7e2e-438e-955b-8cc637d639de'
        },
        destino: {
          idJson: '4d8d6135-7d86-4d98-9de4-568f09675345'
        },
        tabelaEntreVerdesTransicoes: [

        ],
        grupoSemaforico: {
          idJson: 'a823ff37-8516-4fed-bdf5-5fb0d464824c'
        },
        tipo: 'GANHO_DE_PASSAGEM',
        tempoAtrasoGrupo: '0',
        atrasoDeGrupo: {
          idJson: '8d1dc4f3-e065-4d89-b77b-c4694c762b8d'
        },
        modoIntermitenteOuApagado: false
      }, {
        id: '7bb19214-fe8c-4605-99c6-5e153e523625',
        idJson: '86d27976-936f-412c-b8af-50205c8aa42b',
        origem: {
          idJson: '766ffc1a-4772-4b81-8a3a-d7ee4d9a9562'
        },
        destino: {
          idJson: '6a1e9ca6-33d0-40e6-aa8a-a65f461a6e5c'
        },
        tabelaEntreVerdesTransicoes: [

        ],
        grupoSemaforico: {
          idJson: '14b52b33-08e8-436d-880d-015feb4eb861'
        },
        tipo: 'GANHO_DE_PASSAGEM',
        tempoAtrasoGrupo: '0',
        atrasoDeGrupo: {
          idJson: '342c191d-3327-41ef-ab68-92213ee0cc67'
        },
        modoIntermitenteOuApagado: false
      }, {
        id: 'ed1ec454-9f87-4a8b-b517-c1151b5e81de',
        idJson: 'ed747576-cedd-4e01-a5fe-c19faca0279c',
        origem: {
          idJson: '88aab1ee-3962-4bd0-85fa-d766a53db12f'
        },
        destino: {
          idJson: '5792c329-9250-4fef-ae9a-c44d56998451'
        },
        tabelaEntreVerdesTransicoes: [

        ],
        grupoSemaforico: {
          idJson: '919bbe48-05dc-4b98-bff1-f798c1cecd9e'
        },
        tipo: 'GANHO_DE_PASSAGEM',
        tempoAtrasoGrupo: '0',
        atrasoDeGrupo: {
          idJson: '652aed10-9efa-4085-87cc-b9bbfb0700f4'
        },
        modoIntermitenteOuApagado: false
      }, {
        id: '3b33408b-b5f3-484b-8baa-7cf5ac995a76',
        idJson: '8e344e24-7cd5-4cbd-a3dd-a2ad26ff9b65',
        origem: {
          idJson: '6f426d16-e109-41e0-83a7-b0fdadbe1c1a'
        },
        destino: {
          idJson: 'f2bf846c-108d-4d58-b7f9-e3a58b827952'
        },
        tabelaEntreVerdesTransicoes: [

        ],
        grupoSemaforico: {
          idJson: '25776b2b-b20a-4253-8781-d0fda2a0c1d7'
        },
        tipo: 'GANHO_DE_PASSAGEM',
        tempoAtrasoGrupo: '0',
        atrasoDeGrupo: {
          idJson: 'd21ad13f-3bee-465a-a1f3-8389eefa1e4a'
        },
        modoIntermitenteOuApagado: false
      }, {
        id: '4a3926ef-7ef6-4afd-8662-31e8a1e146c4',
        idJson: '7411840b-30fc-4b87-9033-fc2f7f5b55f7',
        origem: {
          idJson: '5c521612-2822-48e8-9a82-5e0c16d1a4bd'
        },
        destino: {
          idJson: '878776db-7e2e-438e-955b-8cc637d639de'
        },
        tabelaEntreVerdesTransicoes: [

        ],
        grupoSemaforico: {
          idJson: '85156d31-1984-46b1-9165-19875851c0c3'
        },
        tipo: 'GANHO_DE_PASSAGEM',
        tempoAtrasoGrupo: '0',
        atrasoDeGrupo: {
          idJson: '5b33bc33-113b-4827-b241-d0b2fb28e0ad'
        },
        modoIntermitenteOuApagado: false
      }, {
        id: 'dc8cf0e4-88d8-49a4-bbd6-d3da188506bb',
        idJson: '5379b5c2-5d9f-4797-94f0-ededd2dd888b',
        origem: {
          idJson: '6f426d16-e109-41e0-83a7-b0fdadbe1c1a'
        },
        destino: {
          idJson: '9529fbb9-9ac3-400d-9c23-d7c38d9140a1'
        },
        tabelaEntreVerdesTransicoes: [

        ],
        grupoSemaforico: {
          idJson: 'ba57b9fe-7625-4068-bbdc-654982c7c16f'
        },
        tipo: 'GANHO_DE_PASSAGEM',
        tempoAtrasoGrupo: '0',
        atrasoDeGrupo: {
          idJson: 'd8be2e40-5787-4221-a070-79be30366bd0'
        },
        modoIntermitenteOuApagado: false
      }, {
        id: 'ffb810e6-2e7d-44e6-841c-c3dd7b95861b',
        idJson: 'ab6a18e4-0520-4a49-b0a7-2b4b0dfd7aff',
        origem: {
          idJson: '4d8d6135-7d86-4d98-9de4-568f09675345'
        },
        destino: {
          idJson: '878776db-7e2e-438e-955b-8cc637d639de'
        },
        tabelaEntreVerdesTransicoes: [

        ],
        grupoSemaforico: {
          idJson: '85156d31-1984-46b1-9165-19875851c0c3'
        },
        tipo: 'GANHO_DE_PASSAGEM',
        tempoAtrasoGrupo: '0',
        atrasoDeGrupo: {
          idJson: 'c2aadee7-1101-4cd0-b920-6eeb2c814057'
        },
        modoIntermitenteOuApagado: false
      }, {
        id: 'c644c139-8e1c-41ef-a03a-96de1478b8dc',
        idJson: 'efe645a8-5403-4081-b281-ab94a5bbb456',
        origem: {
          idJson: 'a1ed68a0-3849-404f-8720-974730471c4b'
        },
        destino: {
          idJson: '5792c329-9250-4fef-ae9a-c44d56998451'
        },
        tabelaEntreVerdesTransicoes: [

        ],
        grupoSemaforico: {
          idJson: '919bbe48-05dc-4b98-bff1-f798c1cecd9e'
        },
        tipo: 'GANHO_DE_PASSAGEM',
        tempoAtrasoGrupo: '0',
        atrasoDeGrupo: {
          idJson: '46ecab32-416b-4145-871a-356a05593b78'
        },
        modoIntermitenteOuApagado: false
      }, {
        id: '1f5119c6-4791-487a-9310-a501e8138848',
        idJson: 'f73a1e05-250d-4404-ab59-2a9927bff83a',
        origem: {
          idJson: '88aab1ee-3962-4bd0-85fa-d766a53db12f'
        },
        destino: {
          idJson: 'a1ed68a0-3849-404f-8720-974730471c4b'
        },
        tabelaEntreVerdesTransicoes: [

        ],
        grupoSemaforico: {
          idJson: '90294e1c-cc22-4977-ba8b-ba55d1412114'
        },
        tipo: 'GANHO_DE_PASSAGEM',
        tempoAtrasoGrupo: '0',
        atrasoDeGrupo: {
          idJson: '55b930dd-6187-43a3-a37a-442fef64654a'
        },
        modoIntermitenteOuApagado: false
      }, {
        id: 'f815f6d6-a5d1-4765-9ca1-1d42e9c5b0ea',
        idJson: '56713a77-09e4-4586-b9b8-4ac50e89968f',
        origem: {
          idJson: 'a1ed68a0-3849-404f-8720-974730471c4b'
        },
        destino: {
          idJson: '88aab1ee-3962-4bd0-85fa-d766a53db12f'
        },
        tabelaEntreVerdesTransicoes: [

        ],
        grupoSemaforico: {
          idJson: '878e5030-9790-4ea5-b09a-07a1c27ba31d'
        },
        tipo: 'GANHO_DE_PASSAGEM',
        tempoAtrasoGrupo: '0',
        atrasoDeGrupo: {
          idJson: '67804cae-7995-4244-8999-530ca2ec7ebd'
        },
        modoIntermitenteOuApagado: false
      }, {
        id: 'bc128b28-4ded-4b95-92e4-7049b78e0ab6',
        idJson: 'b3d82fde-fa92-480a-a7bc-1758fa9c3d6f',
        origem: {
          idJson: '5792c329-9250-4fef-ae9a-c44d56998451'
        },
        destino: {
          idJson: '88aab1ee-3962-4bd0-85fa-d766a53db12f'
        },
        tabelaEntreVerdesTransicoes: [

        ],
        grupoSemaforico: {
          idJson: '878e5030-9790-4ea5-b09a-07a1c27ba31d'
        },
        tipo: 'GANHO_DE_PASSAGEM',
        tempoAtrasoGrupo: '0',
        atrasoDeGrupo: {
          idJson: '82f3aaa1-16ec-4675-8222-3441e06c7492'
        },
        modoIntermitenteOuApagado: false
      }, {
        id: 'f4644089-2cf0-4bfc-abd8-932838a4a75a',
        idJson: '3074245d-d238-4860-a9b1-d6a2417c7662',
        origem: {
          idJson: '878776db-7e2e-438e-955b-8cc637d639de'
        },
        destino: {
          idJson: '5c521612-2822-48e8-9a82-5e0c16d1a4bd'
        },
        tabelaEntreVerdesTransicoes: [

        ],
        grupoSemaforico: {
          idJson: '566023c4-919d-4e33-a944-06596652f82b'
        },
        tipo: 'GANHO_DE_PASSAGEM',
        tempoAtrasoGrupo: '0',
        atrasoDeGrupo: {
          idJson: '6f4113f9-45ef-4154-a2e3-b75f97a5e93b'
        },
        modoIntermitenteOuApagado: false
      }, {
        id: 'e0862c21-22fa-41ad-bad9-7dd843e539b1',
        idJson: '022296bd-b0ca-419c-b8b1-ea56fae908d3',
        origem: {
          idJson: '5c521612-2822-48e8-9a82-5e0c16d1a4bd'
        },
        destino: {
          idJson: '878776db-7e2e-438e-955b-8cc637d639de'
        },
        tabelaEntreVerdesTransicoes: [

        ],
        grupoSemaforico: {
          idJson: 'd7c699a5-b93f-4778-9fa7-1e5b7577f91a'
        },
        tipo: 'GANHO_DE_PASSAGEM',
        tempoAtrasoGrupo: '0',
        atrasoDeGrupo: {
          idJson: 'a8aaaa96-aa4d-4046-b0ef-8a24f9e24a4d'
        },
        modoIntermitenteOuApagado: false
      }],
      tabelasEntreVerdes: [{
        id: '8a32990a-2b60-45c9-aab7-f58df0792cc8',
        idJson: '87de7529-2f72-41be-be1d-621133c80bf5',
        descricao: 'PADRÃO',
        posicao: 1,
        grupoSemaforico: {
          idJson: '90294e1c-cc22-4977-ba8b-ba55d1412114'
        },
        tabelaEntreVerdesTransicoes: [{
          idJson: '1bb721fd-d1b6-4817-92e8-bab81541a797'
        }, {
          idJson: '7655eebf-2bd3-46d9-8be7-4da49d95cb1f'
        }]
      }, {
        id: '19ad6b62-f845-40e7-8c5c-3974e02e5be3',
        idJson: '3e222c25-659e-44c1-9102-ee4d3cf00aaf',
        descricao: 'PADRÃO',
        posicao: 1,
        grupoSemaforico: {
          idJson: '919bbe48-05dc-4b98-bff1-f798c1cecd9e'
        },
        tabelaEntreVerdesTransicoes: [{
          idJson: '2365ed47-6f9e-4016-ae1f-38165b25717a'
        }, {
          idJson: '253f45c4-d652-44ba-b668-bb8ab5a123ca'
        }]
      }, {
        id: '9ed07173-2186-45b3-8770-3c9491795ade',
        idJson: '8b799d4c-e3f3-4829-813e-1c397054c5af',
        descricao: 'PADRÃO',
        posicao: 1,
        grupoSemaforico: {
          idJson: 'c6497c38-52d1-40c8-901c-d3e955205331'
        },
        tabelaEntreVerdesTransicoes: [{
          idJson: '67cb6096-729e-482e-9fd0-142816d8275b'
        }, {
          idJson: 'e82152cc-e499-4ced-8666-fca70db35df3'
        }]
      }, {
        id: '7ef256e1-dec9-46c6-802c-c2a5a62051d5',
        idJson: 'ac85f93f-58e3-4012-8465-d8c9550c281a',
        descricao: 'PADRÃO',
        posicao: 1,
        grupoSemaforico: {
          idJson: '566023c4-919d-4e33-a944-06596652f82b'
        },
        tabelaEntreVerdesTransicoes: [{
          idJson: '976b5cc7-1d0a-468c-87c7-9b70626bb52a'
        }, {
          idJson: '864e078b-8045-4bc6-80a8-2d7aa347e046'
        }]
      }, {
        id: '17de6bc5-3588-463d-a9fc-36fabcef533e',
        idJson: '28ae1d57-b96a-4455-a43d-db69f7883af5',
        descricao: 'PADRÃO',
        posicao: 1,
        grupoSemaforico: {
          idJson: '23ac30b7-bed1-4de1-a68f-c93fe13e9d23'
        },
        tabelaEntreVerdesTransicoes: [{
          idJson: 'd87d1f47-4b09-47e5-8d90-cf7015367685'
        }, {
          idJson: 'd8259ebd-c4df-4c6d-9199-09d8687b32ee'
        }]
      }, {
        id: '1b011be4-b760-4b6c-9227-271f00dbd5a9',
        idJson: '599e2e56-2fea-4e07-a49c-0bff6b986df4',
        descricao: 'PADRÃO',
        posicao: 1,
        grupoSemaforico: {
          idJson: '878e5030-9790-4ea5-b09a-07a1c27ba31d'
        },
        tabelaEntreVerdesTransicoes: [{
          idJson: '224f1859-0285-4a78-a359-94b3c4055315'
        }, {
          idJson: '84f02d9e-22e7-4931-af93-8b594d4c6e7f'
        }]
      }, {
        id: '2af5b8cd-6d77-46f1-8552-c8d1549d57e8',
        idJson: 'ff39e15f-48ad-4122-9ef0-f55b14c94cca',
        descricao: 'PADRÃO',
        posicao: 1,
        grupoSemaforico: {
          idJson: '14b52b33-08e8-436d-880d-015feb4eb861'
        },
        tabelaEntreVerdesTransicoes: [{
          idJson: 'a2e831c6-a461-489f-a492-a33bce7ef6fb'
        }]
      }, {
        id: '412d12ef-bfba-46c5-8a97-1773276073e9',
        idJson: 'f1a695b4-6782-49d8-9b16-e384ecc56172',
        descricao: 'PADRÃO',
        posicao: 1,
        grupoSemaforico: {
          idJson: '493742b7-ccff-4650-924e-ae8300dc5df0'
        },
        tabelaEntreVerdesTransicoes: [{
          idJson: '12ec5d05-7903-41c9-8606-37a49a12c11f'
        }]
      }, {
        id: '48420ce4-0d22-4f76-b7a3-5a33c9f62b73',
        idJson: 'ed3eec6b-d8ca-45b6-bde0-dd5914543357',
        descricao: 'PADRÃO',
        posicao: 1,
        grupoSemaforico: {
          idJson: 'ba57b9fe-7625-4068-bbdc-654982c7c16f'
        },
        tabelaEntreVerdesTransicoes: [{
          idJson: 'ed86e67d-9a55-497e-8598-18fbbec34a29'
        }, {
          idJson: 'ab7f3591-075b-46b6-bc9d-90089a3bde27'
        }]
      }, {
        id: '6a474d10-443e-42f9-991c-14ac6015b77d',
        idJson: '26e6e613-b256-4a84-81fa-87298a327695',
        descricao: 'PADRÃO',
        posicao: 1,
        grupoSemaforico: {
          idJson: '485293a9-ee0c-45ee-a140-97cf45274d09'
        },
        tabelaEntreVerdesTransicoes: [{
          idJson: 'e14a3984-ae25-4099-baee-e3451524596b'
        }, {
          idJson: '82bfaa9a-00f9-439d-966e-3466e6d8f270'
        }]
      }, {
        id: '22856c10-6288-48e6-aef3-fafc2b14c93f',
        idJson: '0456f398-1829-4171-b8ea-aa2147fcb79e',
        descricao: 'PADRÃO',
        posicao: 1,
        grupoSemaforico: {
          idJson: '85156d31-1984-46b1-9165-19875851c0c3'
        },
        tabelaEntreVerdesTransicoes: [{
          idJson: '0f0d45de-d25f-4e4e-b527-78b60e659f1f'
        }, {
          idJson: '7af2d1c5-1a44-4c85-8ac9-4436bf57472b'
        }]
      }, {
        id: 'aa7c9e17-f615-4a00-921e-5660b082aca4',
        idJson: 'e12615a5-77dd-47f1-a504-ee699a366ead',
        descricao: 'PADRÃO',
        posicao: 1,
        grupoSemaforico: {
          idJson: '266c69e2-e945-4133-a5c6-94e187c6a5cf'
        },
        tabelaEntreVerdesTransicoes: [{
          idJson: 'e661c662-39dc-4a02-916c-93eeb524c6f7'
        }, {
          idJson: '4692e00b-8d38-45e4-84c8-dc3417c9004b'
        }]
      }, {
        id: '92a9cb04-3df2-4130-be59-6ca18afa376e',
        idJson: '5d494267-2ea7-4134-a517-1fc9acfb556c',
        descricao: 'PADRÃO',
        posicao: 1,
        grupoSemaforico: {
          idJson: 'd7c699a5-b93f-4778-9fa7-1e5b7577f91a'
        },
        tabelaEntreVerdesTransicoes: [{
          idJson: '358e3f03-64e5-4927-8bca-4de4dd0efd73'
        }, {
          idJson: '4adc677c-97d3-4623-853a-e05d0693d9bf'
        }]
      }, {
        id: '17e0061b-a92c-4f4a-8747-4d4b768e2d00',
        idJson: '8d512712-cc24-4a06-be00-2613a4a2b08d',
        descricao: 'PADRÃO',
        posicao: 1,
        grupoSemaforico: {
          idJson: '02cc5059-1559-4d4e-91e7-e2f6c521b7ad'
        },
        tabelaEntreVerdesTransicoes: [{
          idJson: 'e7830120-12eb-4bad-8ac6-52b2a4e2e1e7'
        }, {
          idJson: '7c7ed48c-69bf-4a37-97a0-5189428bcfd7'
        }]
      }, {
        id: '123c7d0f-4e53-4d88-b59d-834bb28a96bf',
        idJson: 'da5ddf23-dac0-4551-9624-37b07eb8ef56',
        descricao: 'PADRÃO',
        posicao: 1,
        grupoSemaforico: {
          idJson: 'a823ff37-8516-4fed-bdf5-5fb0d464824c'
        },
        tabelaEntreVerdesTransicoes: [{
          idJson: '5a93d0a6-4dc9-4f55-a4b4-a2e388c917aa'
        }, {
          idJson: 'cee9ce58-4c3a-4a35-9068-077f6abff0ef'
        }]
      }, {
        id: '71b5fb2c-65c7-4bf1-b98e-549a58c3ee78',
        idJson: '048baffc-b563-46d0-80b4-42ba0e0a29f4',
        descricao: 'PADRÃO',
        posicao: 1,
        grupoSemaforico: {
          idJson: '25776b2b-b20a-4253-8781-d0fda2a0c1d7'
        },
        tabelaEntreVerdesTransicoes: [{
          idJson: 'a8b090c8-1350-46ed-b562-cd875262c876'
        }, {
          idJson: '38de2ae5-92c7-4ab3-bf9e-79ef5cc1d4f9'
        }]
      }],
      tabelasEntreVerdesTransicoes: [{
        id: '3fd9b534-dd04-41ff-9057-84ca587262b3',
        idJson: 'e661c662-39dc-4a02-916c-93eeb524c6f7',
        tempoAmarelo: '3',
        tempoVermelhoLimpeza: '3',
        tempoAtrasoGrupo: '0',
        tabelaEntreVerdes: {
          idJson: 'e12615a5-77dd-47f1-a504-ee699a366ead'
        },
        transicao: {
          idJson: '9a3ffa05-8189-41a2-9bc5-394cace2a11a'
        }
      }, {
        id: 'd521950c-619b-4e7b-b9e9-797125e87716',
        idJson: '12ec5d05-7903-41c9-8606-37a49a12c11f',
        tempoVermelhoIntermitente: '3',
        tempoVermelhoLimpeza: '3',
        tempoAtrasoGrupo: '0',
        tabelaEntreVerdes: {
          idJson: 'f1a695b4-6782-49d8-9b16-e384ecc56172'
        },
        transicao: {
          idJson: '19f70d49-3ab7-45de-a23a-172610f918e7'
        }
      }, {
        id: 'a613d467-5218-4995-b120-e90f3ad8a94a',
        idJson: '1bb721fd-d1b6-4817-92e8-bab81541a797',
        tempoAmarelo: '3',
        tempoVermelhoLimpeza: '3',
        tempoAtrasoGrupo: '0',
        tabelaEntreVerdes: {
          idJson: '87de7529-2f72-41be-be1d-621133c80bf5'
        },
        transicao: {
          idJson: '9ebf777d-7f78-455f-9c49-8d30ba09cc82'
        }
      }, {
        id: '56e97e9a-e171-4c73-bd0e-15a298e87d80',
        idJson: 'a8b090c8-1350-46ed-b562-cd875262c876',
        tempoVermelhoIntermitente: '3',
        tempoVermelhoLimpeza: '3',
        tempoAtrasoGrupo: '0',
        tabelaEntreVerdes: {
          idJson: '048baffc-b563-46d0-80b4-42ba0e0a29f4'
        },
        transicao: {
          idJson: 'ef571ca3-8b68-40fe-998f-bb87037c343a'
        }
      }, {
        id: '3306e856-a861-4742-9e02-b9ba7b5da924',
        idJson: '5a93d0a6-4dc9-4f55-a4b4-a2e388c917aa',
        tempoAmarelo: '3',
        tempoVermelhoLimpeza: '3',
        tempoAtrasoGrupo: '0',
        tabelaEntreVerdes: {
          idJson: 'da5ddf23-dac0-4551-9624-37b07eb8ef56'
        },
        transicao: {
          idJson: 'bab2a895-d128-4642-85ad-b7324331cdcc'
        }
      }, {
        id: '59f415c7-6155-450e-b822-9a726c23ed69',
        idJson: '67cb6096-729e-482e-9fd0-142816d8275b',
        tempoAmarelo: '3',
        tempoVermelhoLimpeza: '3',
        tempoAtrasoGrupo: '0',
        tabelaEntreVerdes: {
          idJson: '8b799d4c-e3f3-4829-813e-1c397054c5af'
        },
        transicao: {
          idJson: 'a3c18b9d-95b9-4bbc-b5f1-971d1d595bc1'
        }
      }, {
        id: '55e7a983-715e-4c81-9fe8-f74fb409f920',
        idJson: '0f0d45de-d25f-4e4e-b527-78b60e659f1f',
        tempoVermelhoIntermitente: '3',
        tempoVermelhoLimpeza: '3',
        tempoAtrasoGrupo: '0',
        tabelaEntreVerdes: {
          idJson: '0456f398-1829-4171-b8ea-aa2147fcb79e'
        },
        transicao: {
          idJson: 'e045c5de-cb9f-4299-9eb1-5d5c168836fb'
        }
      }, {
        id: '3647324d-7d45-4cdf-b560-e87433c1b032',
        idJson: 'e14a3984-ae25-4099-baee-e3451524596b',
        tempoVermelhoIntermitente: '3',
        tempoVermelhoLimpeza: '3',
        tempoAtrasoGrupo: '0',
        tabelaEntreVerdes: {
          idJson: '26e6e613-b256-4a84-81fa-87298a327695'
        },
        transicao: {
          idJson: 'da621643-484a-4fac-a188-85601b5af369'
        }
      }, {
        id: 'fe8a48de-55ac-4617-bb5d-98e2c80c3799',
        idJson: 'ab7f3591-075b-46b6-bc9d-90089a3bde27',
        tempoAmarelo: '3',
        tempoVermelhoLimpeza: '3',
        tempoAtrasoGrupo: '0',
        tabelaEntreVerdes: {
          idJson: 'ed3eec6b-d8ca-45b6-bde0-dd5914543357'
        },
        transicao: {
          idJson: 'cfcc8dc1-260c-49f2-975f-a9906bb5c5a8'
        }
      }, {
        id: 'c36d9cee-7e23-467b-a565-70396853e3c8',
        idJson: '976b5cc7-1d0a-468c-87c7-9b70626bb52a',
        tempoAmarelo: '3',
        tempoVermelhoLimpeza: '3',
        tempoAtrasoGrupo: '0',
        tabelaEntreVerdes: {
          idJson: 'ac85f93f-58e3-4012-8465-d8c9550c281a'
        },
        transicao: {
          idJson: '97033230-5600-4c5b-9d85-d94338875f8d'
        }
      }, {
        id: 'f75bf82a-0001-4375-903b-eb6180b0a400',
        idJson: 'ed86e67d-9a55-497e-8598-18fbbec34a29',
        tempoAmarelo: '3',
        tempoVermelhoLimpeza: '3',
        tempoAtrasoGrupo: '0',
        tabelaEntreVerdes: {
          idJson: 'ed3eec6b-d8ca-45b6-bde0-dd5914543357'
        },
        transicao: {
          idJson: 'df020540-575b-4d30-9ade-facdfad53ed4'
        }
      }, {
        id: '8648e10e-e86f-4972-b356-90b6b97e1c6d',
        idJson: '7c7ed48c-69bf-4a37-97a0-5189428bcfd7',
        tempoVermelhoIntermitente: '3',
        tempoVermelhoLimpeza: '3',
        tempoAtrasoGrupo: '0',
        tabelaEntreVerdes: {
          idJson: '8d512712-cc24-4a06-be00-2613a4a2b08d'
        },
        transicao: {
          idJson: 'a2261cfb-901e-42f6-920e-b59cbee8e872'
        }
      }, {
        id: '7d392a9c-f23c-456c-8f40-fdcff3641cc4',
        idJson: '38de2ae5-92c7-4ab3-bf9e-79ef5cc1d4f9',
        tempoVermelhoIntermitente: '3',
        tempoVermelhoLimpeza: '3',
        tempoAtrasoGrupo: '0',
        tabelaEntreVerdes: {
          idJson: '048baffc-b563-46d0-80b4-42ba0e0a29f4'
        },
        transicao: {
          idJson: '4adb694a-8769-42c9-8e8a-79c9d5bda5c7'
        }
      }, {
        id: 'bae55bfb-3257-4df6-92c2-228f21b59318',
        idJson: '4adc677c-97d3-4623-853a-e05d0693d9bf',
        tempoVermelhoIntermitente: '3',
        tempoVermelhoLimpeza: '3',
        tempoAtrasoGrupo: '0',
        tabelaEntreVerdes: {
          idJson: '5d494267-2ea7-4134-a517-1fc9acfb556c'
        },
        transicao: {
          idJson: '6d2025c8-83a2-4725-9f45-6104ab95517b'
        }
      }, {
        id: '91bd5e89-0e63-487b-a258-43f3d842d612',
        idJson: '82bfaa9a-00f9-439d-966e-3466e6d8f270',
        tempoVermelhoIntermitente: '3',
        tempoVermelhoLimpeza: '3',
        tempoAtrasoGrupo: '0',
        tabelaEntreVerdes: {
          idJson: '26e6e613-b256-4a84-81fa-87298a327695'
        },
        transicao: {
          idJson: '7f1a75ff-7cab-42b3-b799-e75471e84ee7'
        }
      }, {
        id: '051a8716-080f-402f-9f15-b44f75e083b7',
        idJson: '224f1859-0285-4a78-a359-94b3c4055315',
        tempoVermelhoIntermitente: '3',
        tempoVermelhoLimpeza: '3',
        tempoAtrasoGrupo: '0',
        tabelaEntreVerdes: {
          idJson: '599e2e56-2fea-4e07-a49c-0bff6b986df4'
        },
        transicao: {
          idJson: '29a07f62-5228-4d11-9ac7-08816e9d96e0'
        }
      }, {
        id: 'fefedae3-f16f-45f5-ba06-4eb872cf1b7c',
        idJson: '253f45c4-d652-44ba-b668-bb8ab5a123ca',
        tempoAmarelo: '3',
        tempoVermelhoLimpeza: '3',
        tempoAtrasoGrupo: '0',
        tabelaEntreVerdes: {
          idJson: '3e222c25-659e-44c1-9102-ee4d3cf00aaf'
        },
        transicao: {
          idJson: 'fa0ad62b-8b4a-4889-812c-45000bc62efb'
        }
      }, {
        id: 'b5488664-efcd-42b5-90fd-edf1dbde6c33',
        idJson: '7655eebf-2bd3-46d9-8be7-4da49d95cb1f',
        tempoAmarelo: '3',
        tempoVermelhoLimpeza: '3',
        tempoAtrasoGrupo: '0',
        tabelaEntreVerdes: {
          idJson: '87de7529-2f72-41be-be1d-621133c80bf5'
        },
        transicao: {
          idJson: 'a24218d3-f97a-402a-8107-59d408dcf71e'
        }
      }, {
        id: '4a9da770-b0aa-48a8-9b93-b883a613acee',
        idJson: 'd8259ebd-c4df-4c6d-9199-09d8687b32ee',
        tempoVermelhoIntermitente: '3',
        tempoVermelhoLimpeza: '3',
        tempoAtrasoGrupo: '0',
        tabelaEntreVerdes: {
          idJson: '28ae1d57-b96a-4455-a43d-db69f7883af5'
        },
        transicao: {
          idJson: 'bcfcd258-321d-4f24-b6d6-7d5c0cd32f87'
        }
      }, {
        id: 'b875818a-ad8a-4ba4-b25b-a52db1a8c94b',
        idJson: '7af2d1c5-1a44-4c85-8ac9-4436bf57472b',
        tempoVermelhoIntermitente: '3',
        tempoVermelhoLimpeza: '3',
        tempoAtrasoGrupo: '0',
        tabelaEntreVerdes: {
          idJson: '0456f398-1829-4171-b8ea-aa2147fcb79e'
        },
        transicao: {
          idJson: '4299e3b4-f7b7-42d8-a531-321557299902'
        }
      }, {
        id: '27e43edc-a09a-4037-b3a3-1f28f316a64b',
        idJson: 'd87d1f47-4b09-47e5-8d90-cf7015367685',
        tempoVermelhoIntermitente: '3',
        tempoVermelhoLimpeza: '3',
        tempoAtrasoGrupo: '0',
        tabelaEntreVerdes: {
          idJson: '28ae1d57-b96a-4455-a43d-db69f7883af5'
        },
        transicao: {
          idJson: 'b765f211-68e2-490e-8bca-47da5c5597f0'
        }
      }, {
        id: 'fd57ed35-efad-4700-bf1f-5b7631452a5b',
        idJson: '864e078b-8045-4bc6-80a8-2d7aa347e046',
        tempoAmarelo: '3',
        tempoVermelhoLimpeza: '3',
        tempoAtrasoGrupo: '0',
        tabelaEntreVerdes: {
          idJson: 'ac85f93f-58e3-4012-8465-d8c9550c281a'
        },
        transicao: {
          idJson: 'b5311620-e9ba-4760-ac9e-10e86144c688'
        }
      }, {
        id: 'b0d47b0a-40f4-4f86-9878-baf2c078b6e2',
        idJson: '2365ed47-6f9e-4016-ae1f-38165b25717a',
        tempoAmarelo: '3',
        tempoVermelhoLimpeza: '3',
        tempoAtrasoGrupo: '0',
        tabelaEntreVerdes: {
          idJson: '3e222c25-659e-44c1-9102-ee4d3cf00aaf'
        },
        transicao: {
          idJson: '3c92af69-2266-4e75-8efe-0cfbe5ea2336'
        }
      }, {
        id: '7195b2e1-09aa-4cac-af64-237d4ee128a8',
        idJson: '358e3f03-64e5-4927-8bca-4de4dd0efd73',
        tempoVermelhoIntermitente: '3',
        tempoVermelhoLimpeza: '3',
        tempoAtrasoGrupo: '0',
        tabelaEntreVerdes: {
          idJson: '5d494267-2ea7-4134-a517-1fc9acfb556c'
        },
        transicao: {
          idJson: '7d8d2a88-89af-444f-a8aa-f976b01f8ac6'
        }
      }, {
        id: '40a2620b-c6cb-4a97-a8b7-6046447e00fd',
        idJson: 'cee9ce58-4c3a-4a35-9068-077f6abff0ef',
        tempoAmarelo: '3',
        tempoVermelhoLimpeza: '3',
        tempoAtrasoGrupo: '0',
        tabelaEntreVerdes: {
          idJson: 'da5ddf23-dac0-4551-9624-37b07eb8ef56'
        },
        transicao: {
          idJson: 'a88f9581-cfa1-4c7d-818b-78dd7cafb819'
        }
      }, {
        id: '078fcee1-424c-4cd3-8361-9ca6c110ce73',
        idJson: 'e7830120-12eb-4bad-8ac6-52b2a4e2e1e7',
        tempoVermelhoIntermitente: '3',
        tempoVermelhoLimpeza: '3',
        tempoAtrasoGrupo: '0',
        tabelaEntreVerdes: {
          idJson: '8d512712-cc24-4a06-be00-2613a4a2b08d'
        },
        transicao: {
          idJson: '9240a164-f4f6-44a7-95d8-f7b490384db5'
        }
      }, {
        id: 'a34ec3e2-b050-4e28-a78e-b5ac31e62858',
        idJson: '4692e00b-8d38-45e4-84c8-dc3417c9004b',
        tempoAmarelo: '3',
        tempoVermelhoLimpeza: '3',
        tempoAtrasoGrupo: '0',
        tabelaEntreVerdes: {
          idJson: 'e12615a5-77dd-47f1-a504-ee699a366ead'
        },
        transicao: {
          idJson: '766b0fd3-6802-4ed2-95fa-0e76cac3f312'
        }
      }, {
        id: '6ad3938f-9dbe-4e76-a0b5-354712ea2451',
        idJson: 'e82152cc-e499-4ced-8666-fca70db35df3',
        tempoAmarelo: '3',
        tempoVermelhoLimpeza: '3',
        tempoAtrasoGrupo: '0',
        tabelaEntreVerdes: {
          idJson: '8b799d4c-e3f3-4829-813e-1c397054c5af'
        },
        transicao: {
          idJson: 'a1dfa985-c887-4eba-a025-6aa4debcdc91'
        }
      }, {
        id: '4ff0e152-900a-4a54-951d-3242d159c72b',
        idJson: '84f02d9e-22e7-4931-af93-8b594d4c6e7f',
        tempoVermelhoIntermitente: '3',
        tempoVermelhoLimpeza: '3',
        tempoAtrasoGrupo: '0',
        tabelaEntreVerdes: {
          idJson: '599e2e56-2fea-4e07-a49c-0bff6b986df4'
        },
        transicao: {
          idJson: 'e850b442-c77e-4284-ab6c-231c250ca67b'
        }
      }, {
        id: 'd8584b2c-f01f-4889-a460-c868224452d9',
        idJson: 'a2e831c6-a461-489f-a492-a33bce7ef6fb',
        tempoAmarelo: '3',
        tempoVermelhoLimpeza: '3',
        tempoAtrasoGrupo: '0',
        tabelaEntreVerdes: {
          idJson: 'ff39e15f-48ad-4122-9ef0-f55b14c94cca'
        },
        transicao: {
          idJson: '3afb5c03-444b-40ef-8d4e-60d4349dda46'
        }
      }],
      planos: [{
        id: '5477ccc1-636d-4706-8fbb-0e4fc6099ccd',
        idJson: 'a39e0b2f-12f7-4d92-bc09-04b659e36be7',
        posicao: 1,
        descricao: 'PLANO 1',
        tempoCiclo: 120,
        defasagem: 0,
        posicaoTabelaEntreVerde: 1,
        modoOperacao: 'TEMPO_FIXO_COORDENADO',
        dataCriacao: '16/12/2016 22:28:14',
        dataAtualizacao: '16/12/2016 22:29:46',
        anel: {
          idJson: 'd63044f9-5653-4c5c-90ec-d482d7871e04'
        },
        versaoPlano: {
          idJson: '5f6c6fe7-44a5-419d-bbea-686516f227ca'
        },
        estagiosPlanos: [{
          idJson: '69a20225-a66e-4300-a404-945c6fe993b1'
        }, {
          idJson: '1bc97114-c752-4827-a4d6-8d2de3c7583e'
        }, {
          idJson: '8276be07-cbf0-4f18-8c3b-2dd53519c6ce'
        }],
        gruposSemaforicosPlanos: [{
          idJson: '16431c13-2c0a-4add-8061-baeb2bffb6d2'
        }, {
          idJson: 'e437ffaf-eb79-4d4f-9f7e-317c123415b2'
        }, {
          idJson: 'eb462496-7e4c-4ee8-9623-c251d01c83b9'
        }, {
          idJson: '5140a6a7-e056-4d91-8517-6efcefe3dcba'
        }, {
          idJson: '326c6371-34f0-4211-9e7a-0e70e5258b65'
        }]
      }, {
        id: '5688d07c-caf3-47c2-a30e-8354934f298d',
        idJson: '2cedb173-3b9c-4831-8fb7-66a390dbeb45',
        posicao: 2,
        descricao: 'PLANO 2',
        tempoCiclo: 30,
        defasagem: 0,
        posicaoTabelaEntreVerde: 1,
        modoOperacao: 'INTERMITENTE',
        dataCriacao: '16/12/2016 22:28:15',
        dataAtualizacao: '16/12/2016 22:29:46',
        anel: {
          idJson: 'a4226504-80f8-4a48-aff6-c62b3c7eb23d'
        },
        versaoPlano: {
          idJson: '2f56c3ba-aab8-4453-bd24-482a468b8cb7'
        },
        estagiosPlanos: [{
          idJson: '278b28df-a0b8-4d48-8b7f-7d2cb425bfb4'
        }, {
          idJson: '408a3353-5c97-40c5-a900-92788ce06949'
        }, {
          idJson: '801829da-c43b-48e9-a594-c6375d949126'
        }],
        gruposSemaforicosPlanos: [{
          idJson: '2f927ecc-5566-4eec-a748-c4130307d92e'
        }, {
          idJson: 'aa5223a8-ef01-439a-ba40-d7f67ca57120'
        }, {
          idJson: '9fc2dfb9-bbb7-4aa8-b265-74deb62a725e'
        }, {
          idJson: 'b241f77a-9c24-4311-b907-84c8a1db2597'
        }]
      }, {
        id: 'b85cc163-a42f-41e5-b34f-05845cf30e57',
        idJson: '89e75d74-65e5-4f2d-ad39-fdfa9b778fe9',
        posicao: 1,
        descricao: 'PLANO 1',
        tempoCiclo: 120,
        defasagem: 0,
        posicaoTabelaEntreVerde: 1,
        modoOperacao: 'TEMPO_FIXO_COORDENADO',
        dataCriacao: '16/12/2016 22:28:15',
        dataAtualizacao: '16/12/2016 22:29:46',
        anel: {
          idJson: 'a4226504-80f8-4a48-aff6-c62b3c7eb23d'
        },
        versaoPlano: {
          idJson: '2f56c3ba-aab8-4453-bd24-482a468b8cb7'
        },
        estagiosPlanos: [{
          idJson: '8359ecc4-1666-4b4c-bb46-0eeadee5dfe0'
        }, {
          idJson: 'f10efebe-a8f5-4923-81af-b421fba2b4c4'
        }, {
          idJson: '4d2b44fe-5705-4c19-800f-e621d5d191f4'
        }],
        gruposSemaforicosPlanos: [{
          idJson: 'f144b17d-5e2d-4d09-a69c-4c32569b2dc5'
        }, {
          idJson: 'f608f352-016e-4429-bd6f-5738455b036f'
        }, {
          idJson: '2fca9468-ec32-42d0-8560-33d585e16d0b'
        }, {
          idJson: '84825401-a7c9-44ec-ad50-88cfb16f0590'
        }]
      }, {
        id: '885f70a8-9e60-4ea9-a985-250d2d2542d7',
        idJson: 'cfe1bace-4707-41eb-bb34-5f5e00749864',
        posicao: 2,
        descricao: 'PLANO 2',
        tempoCiclo: 30,
        defasagem: 0,
        posicaoTabelaEntreVerde: 1,
        modoOperacao: 'INTERMITENTE',
        dataCriacao: '16/12/2016 22:28:15',
        dataAtualizacao: '16/12/2016 22:29:47',
        anel: {
          idJson: 'e8198dc9-c5c2-4c85-99d2-345032aac71e'
        },
        versaoPlano: {
          idJson: '9ba3f27a-aa74-4c0b-bc04-511d51d71284'
        },
        estagiosPlanos: [{
          idJson: 'f4e67060-cd10-4107-b5c6-07834f1b6299'
        }, {
          idJson: '21d4cbe0-5455-4074-b833-605e32462468'
        }],
        gruposSemaforicosPlanos: [{
          idJson: 'ef24892e-e055-4d59-acd2-d96ce32c0b19'
        }, {
          idJson: '1d93aaee-ed99-4578-a282-c31680610c86'
        }]
      }, {
        id: '4bece7e7-1ccb-4889-8d0f-e52ff577a995',
        idJson: '6069b833-0873-4616-a35a-1cf3ad5a2c35',
        posicao: 0,
        descricao: 'Exclusivo',
        tempoCiclo: 30,
        defasagem: 0,
        posicaoTabelaEntreVerde: 1,
        modoOperacao: 'MANUAL',
        dataCriacao: '16/12/2016 22:28:14',
        dataAtualizacao: '16/12/2016 22:29:47',
        anel: {
          idJson: '6596c0ff-441a-4b54-bcd0-1b8b80b24ada'
        },
        versaoPlano: {
          idJson: 'e9917061-18dc-4695-9a34-0f23c0fe5384'
        },
        estagiosPlanos: [{
          idJson: '9f663428-4817-41aa-8573-9a6932c3a5f8'
        }, {
          idJson: '7931c69b-0262-46d7-adcb-fd896ea86f45'
        }, {
          idJson: 'f77fe8ad-5858-49c9-9717-cd76db768f73'
        }],
        gruposSemaforicosPlanos: [{
          idJson: 'a930194e-f61c-4ad3-93a8-8771ba5f376b'
        }, {
          idJson: 'f5a8315b-cbc4-45be-98b7-3b4c288a09cc'
        }, {
          idJson: '19835e0a-c266-4786-a724-438c3f6d4372'
        }, {
          idJson: '8d7628ae-57cc-4737-9f8a-8a4ce6d0fcc1'
        }, {
          idJson: '455490f3-a5a7-4d27-a14d-fdec8b1f3e3e'
        }]
      }, {
        id: '029d908b-6083-40b7-b6df-ca3ce67a76bf',
        idJson: 'ca53b7ff-75ab-4b52-9d7f-f4563b725367',
        posicao: 0,
        descricao: 'Exclusivo',
        tempoCiclo: 30,
        defasagem: 0,
        posicaoTabelaEntreVerde: 1,
        modoOperacao: 'MANUAL',
        dataCriacao: '16/12/2016 22:28:15',
        dataAtualizacao: '16/12/2016 22:29:46',
        anel: {
          idJson: 'a4226504-80f8-4a48-aff6-c62b3c7eb23d'
        },
        versaoPlano: {
          idJson: '2f56c3ba-aab8-4453-bd24-482a468b8cb7'
        },
        estagiosPlanos: [{
          idJson: 'b44d9d7c-3993-4ed9-99aa-03c72681ea4f'
        }, {
          idJson: 'bb18f9e4-4498-476a-a9f4-ba2b3fa75e95'
        }, {
          idJson: 'e924a263-2fb5-47df-bf1c-f6dd7dff223a'
        }],
        gruposSemaforicosPlanos: [{
          idJson: '6a495a7c-29d5-4a0a-8c77-d0bcc423eb0c'
        }, {
          idJson: 'b9e1b03b-345c-47ed-8abc-2e3228648b7b'
        }, {
          idJson: '7bd6a4d0-3294-4f95-9d13-0cda5b1121f7'
        }, {
          idJson: '8aedef50-3aeb-4a1d-8757-e6560e9df421'
        }]
      }, {
        id: '28348626-9556-4b78-872a-004bd95c5980',
        idJson: 'e353f93b-2803-4cfe-a82f-fbd947036a78',
        posicao: 1,
        descricao: 'PLANO 1',
        tempoCiclo: 120,
        defasagem: 0,
        posicaoTabelaEntreVerde: 1,
        modoOperacao: 'TEMPO_FIXO_COORDENADO',
        dataCriacao: '16/12/2016 22:28:15',
        dataAtualizacao: '16/12/2016 22:29:47',
        anel: {
          idJson: 'e8198dc9-c5c2-4c85-99d2-345032aac71e'
        },
        versaoPlano: {
          idJson: '9ba3f27a-aa74-4c0b-bc04-511d51d71284'
        },
        estagiosPlanos: [{
          idJson: '8e770f71-7981-4e8a-9d1d-8dfffbc47a4a'
        }, {
          idJson: '806810b5-e051-4a78-b038-94da4e111070'
        }],
        gruposSemaforicosPlanos: [{
          idJson: 'b551e3a7-35a4-4f44-bf52-30f9d1b06663'
        }, {
          idJson: '28f3061d-dd3f-4550-b97c-c12d28556399'
        }]
      }, {
        id: 'd3e1bab4-9793-4df7-a2ce-e4f52d9a2153',
        idJson: '7aa35cee-16ae-4a8e-827b-b70b4490d2b6',
        posicao: 0,
        descricao: 'Exclusivo',
        tempoCiclo: 30,
        defasagem: 0,
        posicaoTabelaEntreVerde: 1,
        modoOperacao: 'MANUAL',
        dataCriacao: '16/12/2016 22:28:14',
        dataAtualizacao: '16/12/2016 22:29:46',
        anel: {
          idJson: 'd63044f9-5653-4c5c-90ec-d482d7871e04'
        },
        versaoPlano: {
          idJson: '5f6c6fe7-44a5-419d-bbea-686516f227ca'
        },
        estagiosPlanos: [{
          idJson: 'f19135b1-b2ed-42e1-bc26-d68aa9bd4e70'
        }, {
          idJson: 'cb73376d-9b68-43fe-b933-2cfa8d63ab37'
        }, {
          idJson: '3fa746fd-f3db-4353-89f3-4ff7fa1f187b'
        }],
        gruposSemaforicosPlanos: [{
          idJson: '9cd3183d-8c21-4e49-bc53-937f4e588c6f'
        }, {
          idJson: 'd4e8ec17-2eb4-447e-86a8-7b041d733004'
        }, {
          idJson: '48e7c11b-b5c5-4a49-b90c-baf409344eef'
        }, {
          idJson: '93e8d4ec-3545-4c59-8b3a-2c4dcc2a5f5c'
        }, {
          idJson: '7f25f984-9b6c-41f2-9b96-3aa927315ae6'
        }]
      }, {
        id: 'f4b0f9f7-35f7-4e9a-8b86-3837698be213',
        idJson: '93999b9e-4ef0-42ef-8382-c64963146cd3',
        posicao: 0,
        descricao: 'Exclusivo',
        tempoCiclo: 30,
        defasagem: 0,
        posicaoTabelaEntreVerde: 1,
        modoOperacao: 'MANUAL',
        dataCriacao: '16/12/2016 22:28:15',
        dataAtualizacao: '16/12/2016 22:29:47',
        anel: {
          idJson: 'e8198dc9-c5c2-4c85-99d2-345032aac71e'
        },
        versaoPlano: {
          idJson: '9ba3f27a-aa74-4c0b-bc04-511d51d71284'
        },
        estagiosPlanos: [{
          idJson: '9ab1de61-4ef5-47fb-a232-1e5ec620f1e7'
        }, {
          idJson: '641051a5-f80f-44d4-aa5c-1bc21ce42d7a'
        }, {
          idJson: '5d2a09a7-5dfb-4519-814d-e2f44ec8afc3'
        }],
        gruposSemaforicosPlanos: [{
          idJson: '48bb3776-7e99-4d8e-8e47-289e63759516'
        }, {
          idJson: 'dbb19547-8dfd-4102-84f8-9c4e34432d22'
        }]
      }, {
        id: '92c3308d-ea5d-416e-96f3-2b3f848454ce',
        idJson: '64111a27-35fb-4053-b4ea-84234bb2c431',
        posicao: 2,
        descricao: 'PLANO 2',
        tempoCiclo: 30,
        defasagem: 0,
        posicaoTabelaEntreVerde: 1,
        modoOperacao: 'INTERMITENTE',
        dataCriacao: '16/12/2016 22:28:14',
        dataAtualizacao: '16/12/2016 22:29:47',
        anel: {
          idJson: '6596c0ff-441a-4b54-bcd0-1b8b80b24ada'
        },
        versaoPlano: {
          idJson: 'e9917061-18dc-4695-9a34-0f23c0fe5384'
        },
        estagiosPlanos: [{
          idJson: 'e3a22028-fc44-45ad-bae9-d3021dc6ea93'
        }, {
          idJson: 'b8c69465-9449-4f8e-8343-b3feaf6fa595'
        }, {
          idJson: 'e9580138-629b-4b16-9de7-5a80856c6c46'
        }],
        gruposSemaforicosPlanos: [{
          idJson: 'f2e890c1-461b-4f6a-bd84-417486634eea'
        }, {
          idJson: '10d58f7a-4cca-434c-afb4-0a9eaea7f528'
        }, {
          idJson: 'a5b991ed-5f51-4763-a490-ba3668a4113b'
        }, {
          idJson: '47c3c982-464e-47d6-abb3-2512d6a3a3b5'
        }, {
          idJson: '1c8c0ad3-b143-401c-b157-4eb40b615d91'
        }]
      }, {
        id: '2be889bb-2bd7-4a0b-b9a9-dab5b40ea5f2',
        idJson: '93046997-77ae-4ce3-8920-1841e890ce3d',
        posicao: 1,
        descricao: 'PLANO 1',
        tempoCiclo: 120,
        defasagem: 0,
        posicaoTabelaEntreVerde: 1,
        modoOperacao: 'TEMPO_FIXO_COORDENADO',
        dataCriacao: '16/12/2016 22:28:14',
        dataAtualizacao: '16/12/2016 22:29:47',
        anel: {
          idJson: '6596c0ff-441a-4b54-bcd0-1b8b80b24ada'
        },
        versaoPlano: {
          idJson: 'e9917061-18dc-4695-9a34-0f23c0fe5384'
        },
        estagiosPlanos: [{
          idJson: '87e7eea7-f717-4ddd-bce2-5b0caedcce33'
        }, {
          idJson: '07373545-49c2-4447-a93d-ebbedda24eda'
        }, {
          idJson: '480e2abd-3f22-4a90-8cca-58aeed9d3ba9'
        }],
        gruposSemaforicosPlanos: [{
          idJson: 'b1cc034b-c188-4719-9494-5fe62df04c1a'
        }, {
          idJson: '8e71bab6-bf23-42f0-98a3-f1c058d46846'
        }, {
          idJson: '05002851-7b35-4bdb-a9c9-c7c7ff66b44c'
        }, {
          idJson: 'e869d143-314b-4d6d-9595-ab6ff12b5322'
        }, {
          idJson: 'b0e510f0-d9fd-4f96-a801-2bdf772fa715'
        }]
      }, {
        id: 'a1892327-da14-48b8-831a-6b9006f0fa33',
        idJson: 'fdccbe6d-1a6a-417d-be51-2a8fa87ae99f',
        posicao: 2,
        descricao: 'PLANO 2',
        tempoCiclo: 30,
        defasagem: 0,
        posicaoTabelaEntreVerde: 1,
        modoOperacao: 'INTERMITENTE',
        dataCriacao: '16/12/2016 22:28:14',
        dataAtualizacao: '16/12/2016 22:29:46',
        anel: {
          idJson: 'd63044f9-5653-4c5c-90ec-d482d7871e04'
        },
        versaoPlano: {
          idJson: '5f6c6fe7-44a5-419d-bbea-686516f227ca'
        },
        estagiosPlanos: [{
          idJson: '6090c21d-234e-4ff6-a83d-05df212ea470'
        }, {
          idJson: '935641b3-b355-4b4c-941d-6296ea2613fc'
        }, {
          idJson: '1ccd3d09-2f54-4be6-a4c4-e05839f1ab66'
        }],
        gruposSemaforicosPlanos: [{
          idJson: '2f84425f-1a74-4a8b-94a9-b435bb8e1b1a'
        }, {
          idJson: 'eb7e5774-9ac4-4025-88a3-3002d0036b4f'
        }, {
          idJson: 'da27653d-0000-459b-9777-1708849e4b14'
        }, {
          idJson: '77d28229-2a69-4c00-a8cf-03969fc0398d'
        }, {
          idJson: '60fff247-c9f3-443b-80a1-7a5f36709bf4'
        }]
      }],
      gruposSemaforicosPlanos: [{
        id: '04b03110-630b-4d85-be62-8699cd2f66ea',
        idJson: 'e437ffaf-eb79-4d4f-9f7e-317c123415b2',
        plano: {
          idJson: 'a39e0b2f-12f7-4d92-bc09-04b659e36be7'
        },
        grupoSemaforico: {
          idJson: 'd7c699a5-b93f-4778-9fa7-1e5b7577f91a'
        },
        ativado: true
      }, {
        id: '2749a237-cc0f-4dbf-b4fe-826dada61f1d',
        idJson: 'b551e3a7-35a4-4f44-bf52-30f9d1b06663',
        plano: {
          idJson: 'e353f93b-2803-4cfe-a82f-fbd947036a78'
        },
        grupoSemaforico: {
          idJson: '14b52b33-08e8-436d-880d-015feb4eb861'
        },
        ativado: true
      }, {
        id: '7b56f27a-5614-435d-8b5e-e8b87f56184b',
        idJson: 'da27653d-0000-459b-9777-1708849e4b14',
        plano: {
          idJson: 'fdccbe6d-1a6a-417d-be51-2a8fa87ae99f'
        },
        grupoSemaforico: {
          idJson: 'c6497c38-52d1-40c8-901c-d3e955205331'
        },
        ativado: true
      }, {
        id: '825841eb-aead-4fec-a7ef-07ae163503d5',
        idJson: '8d7628ae-57cc-4737-9f8a-8a4ce6d0fcc1',
        plano: {
          idJson: '6069b833-0873-4616-a35a-1cf3ad5a2c35'
        },
        grupoSemaforico: {
          idJson: 'ba57b9fe-7625-4068-bbdc-654982c7c16f'
        },
        ativado: true
      }, {
        id: '6e0cabab-1fc8-459f-92da-26fca9517312',
        idJson: 'f5a8315b-cbc4-45be-98b7-3b4c288a09cc',
        plano: {
          idJson: '6069b833-0873-4616-a35a-1cf3ad5a2c35'
        },
        grupoSemaforico: {
          idJson: '25776b2b-b20a-4253-8781-d0fda2a0c1d7'
        },
        ativado: true
      }, {
        id: '57618c91-20d1-4151-806b-48e8d4e0d952',
        idJson: '8e71bab6-bf23-42f0-98a3-f1c058d46846',
        plano: {
          idJson: '93046997-77ae-4ce3-8920-1841e890ce3d'
        },
        grupoSemaforico: {
          idJson: '23ac30b7-bed1-4de1-a68f-c93fe13e9d23'
        },
        ativado: true
      }, {
        id: '7706e5a2-d0df-4500-b574-f8bc19a51615',
        idJson: 'eb7e5774-9ac4-4025-88a3-3002d0036b4f',
        plano: {
          idJson: 'fdccbe6d-1a6a-417d-be51-2a8fa87ae99f'
        },
        grupoSemaforico: {
          idJson: '566023c4-919d-4e33-a944-06596652f82b'
        },
        ativado: true
      }, {
        id: 'fe10dde6-eba4-471a-bff7-34d999e00f38',
        idJson: 'b241f77a-9c24-4311-b907-84c8a1db2597',
        plano: {
          idJson: '2cedb173-3b9c-4831-8fb7-66a390dbeb45'
        },
        grupoSemaforico: {
          idJson: '919bbe48-05dc-4b98-bff1-f798c1cecd9e'
        },
        ativado: true
      }, {
        id: '9adc9db7-975e-43d3-974e-5e5e0d3c6163',
        idJson: '48e7c11b-b5c5-4a49-b90c-baf409344eef',
        plano: {
          idJson: '7aa35cee-16ae-4a8e-827b-b70b4490d2b6'
        },
        grupoSemaforico: {
          idJson: '85156d31-1984-46b1-9165-19875851c0c3'
        },
        ativado: true
      }, {
        id: '16c314a0-7aa2-4a3e-ba95-ca4907ed8f40',
        idJson: '48bb3776-7e99-4d8e-8e47-289e63759516',
        plano: {
          idJson: '93999b9e-4ef0-42ef-8382-c64963146cd3'
        },
        grupoSemaforico: {
          idJson: '493742b7-ccff-4650-924e-ae8300dc5df0'
        },
        ativado: true
      }, {
        id: '84e36df3-9c22-4295-8d86-8fd0773d9cf5',
        idJson: 'd4e8ec17-2eb4-447e-86a8-7b041d733004',
        plano: {
          idJson: '7aa35cee-16ae-4a8e-827b-b70b4490d2b6'
        },
        grupoSemaforico: {
          idJson: 'c6497c38-52d1-40c8-901c-d3e955205331'
        },
        ativado: true
      }, {
        id: 'ab49fbe6-e568-43b1-8cc6-99f3592fa877',
        idJson: 'aa5223a8-ef01-439a-ba40-d7f67ca57120',
        plano: {
          idJson: '2cedb173-3b9c-4831-8fb7-66a390dbeb45'
        },
        grupoSemaforico: {
          idJson: '02cc5059-1559-4d4e-91e7-e2f6c521b7ad'
        },
        ativado: true
      }, {
        id: '36b72d34-f40e-4d3a-ad88-b1ea1c19d671',
        idJson: 'f2e890c1-461b-4f6a-bd84-417486634eea',
        plano: {
          idJson: '64111a27-35fb-4053-b4ea-84234bb2c431'
        },
        grupoSemaforico: {
          idJson: '485293a9-ee0c-45ee-a140-97cf45274d09'
        },
        ativado: true
      }, {
        id: '29996f76-41e8-4fd3-8281-3446f47d5430',
        idJson: '2f84425f-1a74-4a8b-94a9-b435bb8e1b1a',
        plano: {
          idJson: 'fdccbe6d-1a6a-417d-be51-2a8fa87ae99f'
        },
        grupoSemaforico: {
          idJson: 'a823ff37-8516-4fed-bdf5-5fb0d464824c'
        },
        ativado: true
      }, {
        id: 'cafd5159-66f7-4490-834a-ab14200fd893',
        idJson: '8aedef50-3aeb-4a1d-8757-e6560e9df421',
        plano: {
          idJson: 'ca53b7ff-75ab-4b52-9d7f-f4563b725367'
        },
        grupoSemaforico: {
          idJson: '90294e1c-cc22-4977-ba8b-ba55d1412114'
        },
        ativado: true
      }, {
        id: 'a9ace8a1-d80f-4796-a3f6-f7aae77e51fc',
        idJson: '93e8d4ec-3545-4c59-8b3a-2c4dcc2a5f5c',
        plano: {
          idJson: '7aa35cee-16ae-4a8e-827b-b70b4490d2b6'
        },
        grupoSemaforico: {
          idJson: 'a823ff37-8516-4fed-bdf5-5fb0d464824c'
        },
        ativado: true
      }, {
        id: '79807567-9f62-4713-9458-a805dac6320e',
        idJson: '19835e0a-c266-4786-a724-438c3f6d4372',
        plano: {
          idJson: '6069b833-0873-4616-a35a-1cf3ad5a2c35'
        },
        grupoSemaforico: {
          idJson: '485293a9-ee0c-45ee-a140-97cf45274d09'
        },
        ativado: true
      }, {
        id: '7c7f5fdd-0784-4da4-92f6-de7ad94e217b',
        idJson: '47c3c982-464e-47d6-abb3-2512d6a3a3b5',
        plano: {
          idJson: '64111a27-35fb-4053-b4ea-84234bb2c431'
        },
        grupoSemaforico: {
          idJson: '23ac30b7-bed1-4de1-a68f-c93fe13e9d23'
        },
        ativado: true
      }, {
        id: '5f7ca725-799d-4930-8215-82e8a134527e',
        idJson: '2f927ecc-5566-4eec-a748-c4130307d92e',
        plano: {
          idJson: '2cedb173-3b9c-4831-8fb7-66a390dbeb45'
        },
        grupoSemaforico: {
          idJson: '90294e1c-cc22-4977-ba8b-ba55d1412114'
        },
        ativado: true
      }, {
        id: '13a965bd-248c-4575-b510-af6aaafdf458',
        idJson: 'a930194e-f61c-4ad3-93a8-8771ba5f376b',
        plano: {
          idJson: '6069b833-0873-4616-a35a-1cf3ad5a2c35'
        },
        grupoSemaforico: {
          idJson: '23ac30b7-bed1-4de1-a68f-c93fe13e9d23'
        },
        ativado: true
      }, {
        id: '4ba0fdb3-02bb-4241-9cfa-3565ea988c7c',
        idJson: '9cd3183d-8c21-4e49-bc53-937f4e588c6f',
        plano: {
          idJson: '7aa35cee-16ae-4a8e-827b-b70b4490d2b6'
        },
        grupoSemaforico: {
          idJson: '566023c4-919d-4e33-a944-06596652f82b'
        },
        ativado: true
      }, {
        id: 'e7a5b4f3-6e0a-4587-af44-f3424ffb6ec6',
        idJson: '7f25f984-9b6c-41f2-9b96-3aa927315ae6',
        plano: {
          idJson: '7aa35cee-16ae-4a8e-827b-b70b4490d2b6'
        },
        grupoSemaforico: {
          idJson: 'd7c699a5-b93f-4778-9fa7-1e5b7577f91a'
        },
        ativado: true
      }, {
        id: 'ce6bca68-b5ca-4843-8c95-01098a9be351',
        idJson: '77d28229-2a69-4c00-a8cf-03969fc0398d',
        plano: {
          idJson: 'fdccbe6d-1a6a-417d-be51-2a8fa87ae99f'
        },
        grupoSemaforico: {
          idJson: 'd7c699a5-b93f-4778-9fa7-1e5b7577f91a'
        },
        ativado: true
      }, {
        id: '7c66a7f3-924b-453b-9970-db72746abd70',
        idJson: 'dbb19547-8dfd-4102-84f8-9c4e34432d22',
        plano: {
          idJson: '93999b9e-4ef0-42ef-8382-c64963146cd3'
        },
        grupoSemaforico: {
          idJson: '14b52b33-08e8-436d-880d-015feb4eb861'
        },
        ativado: true
      }, {
        id: '50d599df-2f7b-48ad-b1a4-57e10ccb80ef',
        idJson: '6a495a7c-29d5-4a0a-8c77-d0bcc423eb0c',
        plano: {
          idJson: 'ca53b7ff-75ab-4b52-9d7f-f4563b725367'
        },
        grupoSemaforico: {
          idJson: '919bbe48-05dc-4b98-bff1-f798c1cecd9e'
        },
        ativado: true
      }, {
        id: '587beb2a-08a7-45ec-a0a1-69a95f3091cd',
        idJson: 'a5b991ed-5f51-4763-a490-ba3668a4113b',
        plano: {
          idJson: '64111a27-35fb-4053-b4ea-84234bb2c431'
        },
        grupoSemaforico: {
          idJson: '266c69e2-e945-4133-a5c6-94e187c6a5cf'
        },
        ativado: true
      }, {
        id: '9ddb9a19-9be4-4bc6-a505-e5acbc067c6e',
        idJson: '7bd6a4d0-3294-4f95-9d13-0cda5b1121f7',
        plano: {
          idJson: 'ca53b7ff-75ab-4b52-9d7f-f4563b725367'
        },
        grupoSemaforico: {
          idJson: '878e5030-9790-4ea5-b09a-07a1c27ba31d'
        },
        ativado: true
      }, {
        id: 'e77fcc8a-a423-4b22-8f43-2a9a4ac737ae',
        idJson: 'e869d143-314b-4d6d-9595-ab6ff12b5322',
        plano: {
          idJson: '93046997-77ae-4ce3-8920-1841e890ce3d'
        },
        grupoSemaforico: {
          idJson: '25776b2b-b20a-4253-8781-d0fda2a0c1d7'
        },
        ativado: true
      }, {
        id: '72c9b0f2-6468-4a0e-a729-d8b19055f684',
        idJson: '05002851-7b35-4bdb-a9c9-c7c7ff66b44c',
        plano: {
          idJson: '93046997-77ae-4ce3-8920-1841e890ce3d'
        },
        grupoSemaforico: {
          idJson: '485293a9-ee0c-45ee-a140-97cf45274d09'
        },
        ativado: true
      }, {
        id: '7d758856-9928-4573-8c1e-b852ff244bcb',
        idJson: '326c6371-34f0-4211-9e7a-0e70e5258b65',
        plano: {
          idJson: 'a39e0b2f-12f7-4d92-bc09-04b659e36be7'
        },
        grupoSemaforico: {
          idJson: 'a823ff37-8516-4fed-bdf5-5fb0d464824c'
        },
        ativado: true
      }, {
        id: '221cfafa-1435-4d9c-a80f-a8009eddffc0',
        idJson: 'f608f352-016e-4429-bd6f-5738455b036f',
        plano: {
          idJson: '89e75d74-65e5-4f2d-ad39-fdfa9b778fe9'
        },
        grupoSemaforico: {
          idJson: '878e5030-9790-4ea5-b09a-07a1c27ba31d'
        },
        ativado: true
      }, {
        id: '6fee4b1d-4f06-4f80-8682-4f5d8a894fc1',
        idJson: '1d93aaee-ed99-4578-a282-c31680610c86',
        plano: {
          idJson: 'cfe1bace-4707-41eb-bb34-5f5e00749864'
        },
        grupoSemaforico: {
          idJson: '14b52b33-08e8-436d-880d-015feb4eb861'
        },
        ativado: true
      }, {
        id: 'b7fa9386-02f2-4d15-aec2-cb6b879f57b3',
        idJson: '9fc2dfb9-bbb7-4aa8-b265-74deb62a725e',
        plano: {
          idJson: '2cedb173-3b9c-4831-8fb7-66a390dbeb45'
        },
        grupoSemaforico: {
          idJson: '878e5030-9790-4ea5-b09a-07a1c27ba31d'
        },
        ativado: true
      }, {
        id: 'f398d0e6-a988-4b99-88f8-16eb60a7c534',
        idJson: '1c8c0ad3-b143-401c-b157-4eb40b615d91',
        plano: {
          idJson: '64111a27-35fb-4053-b4ea-84234bb2c431'
        },
        grupoSemaforico: {
          idJson: '25776b2b-b20a-4253-8781-d0fda2a0c1d7'
        },
        ativado: true
      }, {
        id: 'de6e21d1-88b5-495c-841d-d71f345c63c0',
        idJson: '60fff247-c9f3-443b-80a1-7a5f36709bf4',
        plano: {
          idJson: 'fdccbe6d-1a6a-417d-be51-2a8fa87ae99f'
        },
        grupoSemaforico: {
          idJson: '85156d31-1984-46b1-9165-19875851c0c3'
        },
        ativado: true
      }, {
        id: '1326b754-2482-4ec6-8426-5dac84264c13',
        idJson: 'b1cc034b-c188-4719-9494-5fe62df04c1a',
        plano: {
          idJson: '93046997-77ae-4ce3-8920-1841e890ce3d'
        },
        grupoSemaforico: {
          idJson: '266c69e2-e945-4133-a5c6-94e187c6a5cf'
        },
        ativado: true
      }, {
        id: '002877e0-6a3a-496c-b7c7-bd7ffb415185',
        idJson: '16431c13-2c0a-4add-8061-baeb2bffb6d2',
        plano: {
          idJson: 'a39e0b2f-12f7-4d92-bc09-04b659e36be7'
        },
        grupoSemaforico: {
          idJson: '85156d31-1984-46b1-9165-19875851c0c3'
        },
        ativado: true
      }, {
        id: '27b7bfd2-8101-45c0-bd60-c1efd934ff0f',
        idJson: 'ef24892e-e055-4d59-acd2-d96ce32c0b19',
        plano: {
          idJson: 'cfe1bace-4707-41eb-bb34-5f5e00749864'
        },
        grupoSemaforico: {
          idJson: '493742b7-ccff-4650-924e-ae8300dc5df0'
        },
        ativado: true
      }, {
        id: '63267d9b-58d5-40de-ad8e-c5307e1e7863',
        idJson: '2fca9468-ec32-42d0-8560-33d585e16d0b',
        plano: {
          idJson: '89e75d74-65e5-4f2d-ad39-fdfa9b778fe9'
        },
        grupoSemaforico: {
          idJson: '90294e1c-cc22-4977-ba8b-ba55d1412114'
        },
        ativado: true
      }, {
        id: '302926ee-38de-4017-b9ca-6e280efc7e32',
        idJson: 'eb462496-7e4c-4ee8-9623-c251d01c83b9',
        plano: {
          idJson: 'a39e0b2f-12f7-4d92-bc09-04b659e36be7'
        },
        grupoSemaforico: {
          idJson: '566023c4-919d-4e33-a944-06596652f82b'
        },
        ativado: true
      }, {
        id: '42a8b7a2-99c2-4ded-934e-8a39b2089a6f',
        idJson: '10d58f7a-4cca-434c-afb4-0a9eaea7f528',
        plano: {
          idJson: '64111a27-35fb-4053-b4ea-84234bb2c431'
        },
        grupoSemaforico: {
          idJson: 'ba57b9fe-7625-4068-bbdc-654982c7c16f'
        },
        ativado: true
      }, {
        id: '532aa473-73f1-4bb5-908f-e05a8814403c',
        idJson: '5140a6a7-e056-4d91-8517-6efcefe3dcba',
        plano: {
          idJson: 'a39e0b2f-12f7-4d92-bc09-04b659e36be7'
        },
        grupoSemaforico: {
          idJson: 'c6497c38-52d1-40c8-901c-d3e955205331'
        },
        ativado: true
      }, {
        id: '5da56e4c-7e26-49a1-9b22-71d41b94d27e',
        idJson: 'b9e1b03b-345c-47ed-8abc-2e3228648b7b',
        plano: {
          idJson: 'ca53b7ff-75ab-4b52-9d7f-f4563b725367'
        },
        grupoSemaforico: {
          idJson: '02cc5059-1559-4d4e-91e7-e2f6c521b7ad'
        },
        ativado: true
      }, {
        id: 'b408cf79-9ee4-4457-a227-7b7a3cf588eb',
        idJson: '455490f3-a5a7-4d27-a14d-fdec8b1f3e3e',
        plano: {
          idJson: '6069b833-0873-4616-a35a-1cf3ad5a2c35'
        },
        grupoSemaforico: {
          idJson: '266c69e2-e945-4133-a5c6-94e187c6a5cf'
        },
        ativado: true
      }, {
        id: '65e33fa2-f3c0-4d8a-a114-95e9dbf02f56',
        idJson: '84825401-a7c9-44ec-ad50-88cfb16f0590',
        plano: {
          idJson: '89e75d74-65e5-4f2d-ad39-fdfa9b778fe9'
        },
        grupoSemaforico: {
          idJson: '919bbe48-05dc-4b98-bff1-f798c1cecd9e'
        },
        ativado: true
      }, {
        id: 'ffe944a1-4889-4057-b487-a0980d96687d',
        idJson: 'b0e510f0-d9fd-4f96-a801-2bdf772fa715',
        plano: {
          idJson: '93046997-77ae-4ce3-8920-1841e890ce3d'
        },
        grupoSemaforico: {
          idJson: 'ba57b9fe-7625-4068-bbdc-654982c7c16f'
        },
        ativado: true
      }, {
        id: '0c698378-fb90-4b39-bbb9-b495af6e9702',
        idJson: 'f144b17d-5e2d-4d09-a69c-4c32569b2dc5',
        plano: {
          idJson: '89e75d74-65e5-4f2d-ad39-fdfa9b778fe9'
        },
        grupoSemaforico: {
          idJson: '02cc5059-1559-4d4e-91e7-e2f6c521b7ad'
        },
        ativado: true
      }, {
        id: 'ab3da920-a1cb-43aa-9676-1d6b5574a9a2',
        idJson: '28f3061d-dd3f-4550-b97c-c12d28556399',
        plano: {
          idJson: 'e353f93b-2803-4cfe-a82f-fbd947036a78'
        },
        grupoSemaforico: {
          idJson: '493742b7-ccff-4650-924e-ae8300dc5df0'
        },
        ativado: true
      }],
      estagiosPlanos: [{
        id: 'b1c48668-a385-4d3c-a4ce-4b7f59aba3c9',
        idJson: '1bc97114-c752-4827-a4d6-8d2de3c7583e',
        posicao: 1,
        tempoVerde: 50,
        dispensavel: false,
        plano: {
          idJson: 'a39e0b2f-12f7-4d92-bc09-04b659e36be7'
        },
        estagio: {
          idJson: '5c521612-2822-48e8-9a82-5e0c16d1a4bd'
        }
      }, {
        id: 'e94ec97f-83fb-4c9c-bdbd-77be84b19df4',
        idJson: '21d4cbe0-5455-4074-b833-605e32462468',
        posicao: 1,
        tempoVerde: 10,
        tempoVerdeMinimo: 0,
        tempoVerdeMaximo: 0,
        tempoVerdeIntermediario: 0,
        tempoExtensaoVerde: 0.0,
        dispensavel: false,
        plano: {
          idJson: 'cfe1bace-4707-41eb-bb34-5f5e00749864'
        },
        estagio: {
          idJson: '6a1e9ca6-33d0-40e6-aa8a-a65f461a6e5c'
        }
      }, {
        id: '3c6b92d0-97ce-48a7-87ad-ffcdf877563b',
        idJson: '9ab1de61-4ef5-47fb-a232-1e5ec620f1e7',
        posicao: 2,
        tempoVerde: 4,
        dispensavel: false,
        plano: {
          idJson: '93999b9e-4ef0-42ef-8382-c64963146cd3'
        },
        estagio: {
          idJson: '766ffc1a-4772-4b81-8a3a-d7ee4d9a9562'
        }
      }, {
        id: '2166debd-c2c0-424f-85f3-ac85a9d0a702',
        idJson: '408a3353-5c97-40c5-a900-92788ce06949',
        posicao: 2,
        tempoVerde: 10,
        tempoVerdeMinimo: 0,
        tempoVerdeMaximo: 0,
        tempoVerdeIntermediario: 0,
        tempoExtensaoVerde: 0.0,
        dispensavel: false,
        plano: {
          idJson: '2cedb173-3b9c-4831-8fb7-66a390dbeb45'
        },
        estagio: {
          idJson: 'a1ed68a0-3849-404f-8720-974730471c4b'
        }
      }, {
        id: 'f3613c7a-d56b-4cb5-961d-0c06a2ba04b8',
        idJson: '801829da-c43b-48e9-a594-c6375d949126',
        posicao: 1,
        tempoVerde: 10,
        tempoVerdeMinimo: 0,
        tempoVerdeMaximo: 0,
        tempoVerdeIntermediario: 0,
        tempoExtensaoVerde: 0.0,
        dispensavel: false,
        plano: {
          idJson: '2cedb173-3b9c-4831-8fb7-66a390dbeb45'
        },
        estagio: {
          idJson: '5792c329-9250-4fef-ae9a-c44d56998451'
        }
      }, {
        id: '857eb4a7-2f71-4908-9d86-43314f4787da',
        idJson: '3fa746fd-f3db-4353-89f3-4ff7fa1f187b',
        posicao: 2,
        tempoVerde: 4,
        dispensavel: false,
        plano: {
          idJson: '7aa35cee-16ae-4a8e-827b-b70b4490d2b6'
        },
        estagio: {
          idJson: '878776db-7e2e-438e-955b-8cc637d639de'
        }
      }, {
        id: 'c4f4d584-62ff-4a45-8b62-4694709605ed',
        idJson: '806810b5-e051-4a78-b038-94da4e111070',
        posicao: 1,
        tempoVerde: 90,
        dispensavel: false,
        plano: {
          idJson: 'e353f93b-2803-4cfe-a82f-fbd947036a78'
        },
        estagio: {
          idJson: '6a1e9ca6-33d0-40e6-aa8a-a65f461a6e5c'
        }
      }, {
        id: '9391646a-1960-4158-a239-f6503b0d59a1',
        idJson: '8e770f71-7981-4e8a-9d1d-8dfffbc47a4a',
        posicao: 2,
        tempoVerde: 18,
        dispensavel: false,
        plano: {
          idJson: 'e353f93b-2803-4cfe-a82f-fbd947036a78'
        },
        estagio: {
          idJson: '766ffc1a-4772-4b81-8a3a-d7ee4d9a9562'
        }
      }, {
        id: '73af1cbc-0003-4f85-bc1f-9328dd9b6405',
        idJson: '935641b3-b355-4b4c-941d-6296ea2613fc',
        posicao: 1,
        tempoVerde: 10,
        tempoVerdeMinimo: 0,
        tempoVerdeMaximo: 0,
        tempoVerdeIntermediario: 0,
        tempoExtensaoVerde: 0.0,
        dispensavel: false,
        plano: {
          idJson: 'fdccbe6d-1a6a-417d-be51-2a8fa87ae99f'
        },
        estagio: {
          idJson: '5c521612-2822-48e8-9a82-5e0c16d1a4bd'
        }
      }, {
        id: 'f4ed62e3-4301-4bf2-91da-d87e875a50bd',
        idJson: 'f77fe8ad-5858-49c9-9717-cd76db768f73',
        posicao: 1,
        tempoVerde: 10,
        dispensavel: false,
        plano: {
          idJson: '6069b833-0873-4616-a35a-1cf3ad5a2c35'
        },
        estagio: {
          idJson: '9529fbb9-9ac3-400d-9c23-d7c38d9140a1'
        }
      }, {
        id: '1ea1079e-83a9-492b-9cc7-11cf4a0c8afd',
        idJson: '6090c21d-234e-4ff6-a83d-05df212ea470',
        posicao: 2,
        tempoVerde: 4,
        tempoVerdeMinimo: 0,
        tempoVerdeMaximo: 0,
        tempoVerdeIntermediario: 0,
        tempoExtensaoVerde: 0.0,
        dispensavel: false,
        plano: {
          idJson: 'fdccbe6d-1a6a-417d-be51-2a8fa87ae99f'
        },
        estagio: {
          idJson: '878776db-7e2e-438e-955b-8cc637d639de'
        }
      }, {
        id: 'b4107d8b-897a-41f6-8b1a-25ecb712f454',
        idJson: 'f10efebe-a8f5-4923-81af-b421fba2b4c4',
        posicao: 2,
        tempoVerde: 34,
        dispensavel: false,
        plano: {
          idJson: '89e75d74-65e5-4f2d-ad39-fdfa9b778fe9'
        },
        estagio: {
          idJson: 'a1ed68a0-3849-404f-8720-974730471c4b'
        }
      }, {
        id: '0d1a5033-d530-4705-9421-6747af5e33ba',
        idJson: '278b28df-a0b8-4d48-8b7f-7d2cb425bfb4',
        posicao: 3,
        tempoVerde: 4,
        tempoVerdeMinimo: 0,
        tempoVerdeMaximo: 0,
        tempoVerdeIntermediario: 0,
        tempoExtensaoVerde: 0.0,
        dispensavel: false,
        plano: {
          idJson: '2cedb173-3b9c-4831-8fb7-66a390dbeb45'
        },
        estagio: {
          idJson: '88aab1ee-3962-4bd0-85fa-d766a53db12f'
        }
      }, {
        id: 'ae343524-a60c-4585-bfab-04d3889f6742',
        idJson: '69a20225-a66e-4300-a404-945c6fe993b1',
        posicao: 3,
        tempoVerde: 32,
        dispensavel: false,
        plano: {
          idJson: 'a39e0b2f-12f7-4d92-bc09-04b659e36be7'
        },
        estagio: {
          idJson: '4d8d6135-7d86-4d98-9de4-568f09675345'
        }
      }, {
        id: '16887e70-8478-4cd4-acd0-8b354b53874f',
        idJson: 'b44d9d7c-3993-4ed9-99aa-03c72681ea4f',
        posicao: 3,
        tempoVerde: 4,
        dispensavel: false,
        plano: {
          idJson: 'ca53b7ff-75ab-4b52-9d7f-f4563b725367'
        },
        estagio: {
          idJson: '88aab1ee-3962-4bd0-85fa-d766a53db12f'
        }
      }, {
        id: '7a771afe-6e9c-4486-b23d-b4430634992d',
        idJson: 'bb18f9e4-4498-476a-a9f4-ba2b3fa75e95',
        posicao: 2,
        tempoVerde: 10,
        dispensavel: false,
        plano: {
          idJson: 'ca53b7ff-75ab-4b52-9d7f-f4563b725367'
        },
        estagio: {
          idJson: 'a1ed68a0-3849-404f-8720-974730471c4b'
        }
      }, {
        id: '69f453e0-faf4-4227-9f43-e8611047cd46',
        idJson: '07373545-49c2-4447-a93d-ebbedda24eda',
        posicao: 3,
        tempoVerde: 20,
        dispensavel: false,
        plano: {
          idJson: '93046997-77ae-4ce3-8920-1841e890ce3d'
        },
        estagio: {
          idJson: 'f2bf846c-108d-4d58-b7f9-e3a58b827952'
        }
      }, {
        id: 'c6d418da-6553-43d5-a724-c53ed6eada50',
        idJson: '1ccd3d09-2f54-4be6-a4c4-e05839f1ab66',
        posicao: 3,
        tempoVerde: 10,
        tempoVerdeMinimo: 0,
        tempoVerdeMaximo: 0,
        tempoVerdeIntermediario: 0,
        tempoExtensaoVerde: 0.0,
        dispensavel: false,
        plano: {
          idJson: 'fdccbe6d-1a6a-417d-be51-2a8fa87ae99f'
        },
        estagio: {
          idJson: '4d8d6135-7d86-4d98-9de4-568f09675345'
        }
      }, {
        id: 'cb3ae42e-1968-43f9-b4ac-39b1815b0e1b',
        idJson: '5d2a09a7-5dfb-4519-814d-e2f44ec8afc3',
        posicao: 1,
        tempoVerde: 10,
        dispensavel: false,
        plano: {
          idJson: '93999b9e-4ef0-42ef-8382-c64963146cd3'
        },
        estagio: {
          idJson: '6a1e9ca6-33d0-40e6-aa8a-a65f461a6e5c'
        }
      }, {
        id: '58e40d14-82c7-464b-a571-e1164fb90251',
        idJson: '87e7eea7-f717-4ddd-bce2-5b0caedcce33',
        posicao: 2,
        tempoVerde: 30,
        dispensavel: false,
        plano: {
          idJson: '93046997-77ae-4ce3-8920-1841e890ce3d'
        },
        estagio: {
          idJson: '6f426d16-e109-41e0-83a7-b0fdadbe1c1a'
        }
      }, {
        id: '65ffa154-6d5a-4f61-bb73-1d05a9b395da',
        idJson: '8359ecc4-1666-4b4c-bb46-0eeadee5dfe0',
        posicao: 1,
        tempoVerde: 50,
        dispensavel: false,
        plano: {
          idJson: '89e75d74-65e5-4f2d-ad39-fdfa9b778fe9'
        },
        estagio: {
          idJson: '5792c329-9250-4fef-ae9a-c44d56998451'
        }
      }, {
        id: '1730be4e-55dc-49f1-9bd5-b98563d9ee0e',
        idJson: '9f663428-4817-41aa-8573-9a6932c3a5f8',
        posicao: 3,
        tempoVerde: 4,
        dispensavel: false,
        plano: {
          idJson: '6069b833-0873-4616-a35a-1cf3ad5a2c35'
        },
        estagio: {
          idJson: 'f2bf846c-108d-4d58-b7f9-e3a58b827952'
        }
      }, {
        id: '8e20b2c9-f2d3-4aa9-8fc0-ed34e70aa6ec',
        idJson: 'e924a263-2fb5-47df-bf1c-f6dd7dff223a',
        posicao: 1,
        tempoVerde: 10,
        dispensavel: false,
        plano: {
          idJson: 'ca53b7ff-75ab-4b52-9d7f-f4563b725367'
        },
        estagio: {
          idJson: '5792c329-9250-4fef-ae9a-c44d56998451'
        }
      }, {
        id: 'e5c9beb6-a308-49e8-b4a6-c63f49e969fd',
        idJson: '8276be07-cbf0-4f18-8c3b-2dd53519c6ce',
        posicao: 2,
        tempoVerde: 20,
        dispensavel: false,
        plano: {
          idJson: 'a39e0b2f-12f7-4d92-bc09-04b659e36be7'
        },
        estagio: {
          idJson: '878776db-7e2e-438e-955b-8cc637d639de'
        }
      }, {
        id: '832bf7ae-dca8-4587-ae91-8dc6d5ee668c',
        idJson: 'cb73376d-9b68-43fe-b933-2cfa8d63ab37',
        posicao: 1,
        tempoVerde: 10,
        dispensavel: false,
        plano: {
          idJson: '7aa35cee-16ae-4a8e-827b-b70b4490d2b6'
        },
        estagio: {
          idJson: '5c521612-2822-48e8-9a82-5e0c16d1a4bd'
        }
      }, {
        id: '403856e8-9dbc-44c9-8941-15d849965ff9',
        idJson: 'f19135b1-b2ed-42e1-bc26-d68aa9bd4e70',
        posicao: 3,
        tempoVerde: 10,
        dispensavel: false,
        plano: {
          idJson: '7aa35cee-16ae-4a8e-827b-b70b4490d2b6'
        },
        estagio: {
          idJson: '4d8d6135-7d86-4d98-9de4-568f09675345'
        }
      }, {
        id: '89cb41db-2c0b-434c-9d49-0f9daf6fc05e',
        idJson: '641051a5-f80f-44d4-aa5c-1bc21ce42d7a',
        posicao: 3,
        tempoVerde: 4,
        dispensavel: false,
        plano: {
          idJson: '93999b9e-4ef0-42ef-8382-c64963146cd3'
        },
        estagio: {
          idJson: '766ffc1a-4772-4b81-8a3a-d7ee4d9a9562'
        }
      }, {
        id: 'a1e08da5-133e-4fc4-a2f5-47262e65352d',
        idJson: '7931c69b-0262-46d7-adcb-fd896ea86f45',
        posicao: 2,
        tempoVerde: 10,
        dispensavel: false,
        plano: {
          idJson: '6069b833-0873-4616-a35a-1cf3ad5a2c35'
        },
        estagio: {
          idJson: '6f426d16-e109-41e0-83a7-b0fdadbe1c1a'
        }
      }, {
        id: 'cb1098e2-6f5d-487d-a3f0-62c0f743c32a',
        idJson: '4d2b44fe-5705-4c19-800f-e621d5d191f4',
        posicao: 3,
        tempoVerde: 18,
        dispensavel: false,
        plano: {
          idJson: '89e75d74-65e5-4f2d-ad39-fdfa9b778fe9'
        },
        estagio: {
          idJson: '88aab1ee-3962-4bd0-85fa-d766a53db12f'
        }
      }, {
        id: '70840048-3f22-4990-907a-c6c26017760f',
        idJson: 'b8c69465-9449-4f8e-8343-b3feaf6fa595',
        posicao: 3,
        tempoVerde: 4,
        tempoVerdeMinimo: 0,
        tempoVerdeMaximo: 0,
        tempoVerdeIntermediario: 0,
        tempoExtensaoVerde: 0.0,
        dispensavel: false,
        plano: {
          idJson: '64111a27-35fb-4053-b4ea-84234bb2c431'
        },
        estagio: {
          idJson: 'f2bf846c-108d-4d58-b7f9-e3a58b827952'
        }
      }, {
        id: '688ebef8-5024-4077-a4b1-d94ffff80ae2',
        idJson: 'e3a22028-fc44-45ad-bae9-d3021dc6ea93',
        posicao: 1,
        tempoVerde: 10,
        tempoVerdeMinimo: 0,
        tempoVerdeMaximo: 0,
        tempoVerdeIntermediario: 0,
        tempoExtensaoVerde: 0.0,
        dispensavel: false,
        plano: {
          idJson: '64111a27-35fb-4053-b4ea-84234bb2c431'
        },
        estagio: {
          idJson: '9529fbb9-9ac3-400d-9c23-d7c38d9140a1'
        }
      }, {
        id: 'c98c12f7-276e-467d-bf5b-0016d969f591',
        idJson: 'e9580138-629b-4b16-9de7-5a80856c6c46',
        posicao: 2,
        tempoVerde: 10,
        tempoVerdeMinimo: 0,
        tempoVerdeMaximo: 0,
        tempoVerdeIntermediario: 0,
        tempoExtensaoVerde: 0.0,
        dispensavel: false,
        plano: {
          idJson: '64111a27-35fb-4053-b4ea-84234bb2c431'
        },
        estagio: {
          idJson: '6f426d16-e109-41e0-83a7-b0fdadbe1c1a'
        }
      }, {
        id: '7a75623f-c45b-4eff-b5d6-85d0c5c5668b',
        idJson: '480e2abd-3f22-4a90-8cca-58aeed9d3ba9',
        posicao: 1,
        tempoVerde: 52,
        dispensavel: false,
        plano: {
          idJson: '93046997-77ae-4ce3-8920-1841e890ce3d'
        },
        estagio: {
          idJson: '9529fbb9-9ac3-400d-9c23-d7c38d9140a1'
        }
      }, {
        id: '92df7162-fa65-4c7b-9741-eb9219219ff1',
        idJson: 'f4e67060-cd10-4107-b5c6-07834f1b6299',
        posicao: 2,
        tempoVerde: 4,
        tempoVerdeMinimo: 0,
        tempoVerdeMaximo: 0,
        tempoVerdeIntermediario: 0,
        tempoExtensaoVerde: 0.0,
        dispensavel: false,
        plano: {
          idJson: 'cfe1bace-4707-41eb-bb34-5f5e00749864'
        },
        estagio: {
          idJson: '766ffc1a-4772-4b81-8a3a-d7ee4d9a9562'
        }
      }],
      cidades: [{
        id: 'a76f6dae-c3ed-11e6-ab15-0401fa4eb401',
        idJson: 'a76f86d9-c3ed-11e6-ab15-0401fa4eb401',
        nome: 'São Paulo',
        areas: [{
          idJson: 'a76fa8ea-c3ed-11e6-ab15-0401fa4eb401'
        }]
      }],
      areas: [{
        id: 'a76f7661-c3ed-11e6-ab15-0401fa4eb401',
        idJson: 'a76fa8ea-c3ed-11e6-ab15-0401fa4eb401',
        descricao: 1,
        cidade: {
          idJson: 'a76f86d9-c3ed-11e6-ab15-0401fa4eb401'
        },
        limites: [

        ],
        subareas: [

        ]
      }],
      limites: [

      ],
      todosEnderecos: [{
        id: '33018554-5f8c-4ecc-be64-7571ac8c301f',
        idJson: '951ed053-6662-44af-bea4-fc3f6e7dc474',
        localizacao: 'Av. Paulista',
        latitude: -23.5631141,
        longitude: -46.65439200000003,
        localizacao2: '',
        alturaNumerica: 345
      }, {
        id: '66d2506e-0740-4c23-93ed-8d01858cd343',
        idJson: 'adbaca48-183d-4bc7-894a-4d18c83c0b1e',
        localizacao: 'Av. Paulista',
        latitude: -23.5631141,
        longitude: -46.65439200000003,
        localizacao2: '',
        alturaNumerica: 123
      }, {
        id: '3216a96a-6079-4d4d-b74b-d7815d9ae9f4',
        idJson: 'e874b569-f960-4776-a8ce-02efc2db2e90',
        localizacao: 'Av. Paulista',
        latitude: -23.5631141,
        longitude: -46.65439200000003,
        localizacao2: '',
        alturaNumerica: 123
      }, {
        id: '6492155d-d226-46de-b32d-a49fa89397a2',
        idJson: '35d48aab-32ca-4fb1-9fe2-6ef2528131a2',
        localizacao: 'Av. Paulista',
        latitude: -23.5631141,
        longitude: -46.65439200000003,
        localizacao2: '',
        alturaNumerica: 678
      }, {
        id: 'c03124f2-6280-4d45-8c44-9cce286b8f1e',
        idJson: 'cf74c3ab-cc3d-467b-ae45-b4d9170ad305',
        localizacao: 'Av. Paulista',
        latitude: -23.5631141,
        longitude: -46.65439200000003,
        localizacao2: '',
        alturaNumerica: 123
      }],
      imagens: [{
        id: 'e161a3e0-f9b7-41a6-a97b-98ed59f93516',
        idJson: '1be5b42d-39ea-482e-b7b0-62bc0ec9d9bc',
        fileName: 'est2.png',
        contentType: 'image/png'
      }, {
        id: 'f0030fb4-2743-4fef-ae12-326f25e32a9e',
        idJson: '9555fe04-65ec-4e30-a3d4-9b880627f317',
        fileName: 'est3.png',
        contentType: 'image/png'
      }, {
        id: 'd1100cb5-3e20-4823-97ad-5a2831d6ee7f',
        idJson: '0ba1f40b-7260-4a08-b6bc-9e0ea7f1de76',
        fileName: 'est3.png',
        contentType: 'image/png'
      }, {
        id: '0892db00-2d21-44ec-8f50-c53e1444c772',
        idJson: 'b819deb6-9acf-4337-853b-70d0e9c7e8ea',
        fileName: 'est1.png',
        contentType: 'image/png'
      }, {
        id: 'ccebba04-5c12-47ec-ba5e-f5a549ded764',
        idJson: 'a1ff80cb-ad28-4246-8ec4-4dc464821af5',
        fileName: 'est3.png',
        contentType: 'image/png'
      }, {
        id: '7819b5d7-7369-4367-bfed-cc749699904e',
        idJson: 'd1ee9495-ced2-4082-b273-28692480303a',
        fileName: 'est2.png',
        contentType: 'image/png'
      }, {
        id: '5d67fed7-ea84-41d4-996a-ae0df546ad60',
        idJson: '898195b7-c19f-436b-8e19-547ef5659f05',
        fileName: 'est1.png',
        contentType: 'image/png'
      }, {
        id: '21b8ac6c-7705-4d5e-a7a0-ce921d8d12fa',
        idJson: 'bbe0b0bb-c028-4ac4-998a-f10721b90803',
        fileName: 'est2.png',
        contentType: 'image/png'
      }, {
        id: '173c1a02-089e-4553-bc50-db80fecaaa84',
        idJson: '4fea4552-d90d-4084-b5b7-09b18957581c',
        fileName: 'est1.png',
        contentType: 'image/png'
      }, {
        id: '8d53f8bf-682c-478a-ac9d-87ea94ec0805',
        idJson: 'e78a1afd-1fdf-472d-85ac-2437c3fae7e4',
        fileName: 'est1.png',
        contentType: 'image/png'
      }, {
        id: '569dca5a-1806-4c4d-be40-6cee69ce95c6',
        idJson: '40269374-313e-4f30-8352-63c9733b2874',
        fileName: 'est2.png',
        contentType: 'image/png'
      }],
      atrasosDeGrupo: [{
        id: '0b0fc311-6316-4501-87a5-190f0d151b3f',
        idJson: 'b4ac3c60-31e1-4dc4-b624-c93d860a9112',
        atrasoDeGrupo: 0
      }, {
        id: '8808da22-8d35-4eca-b44f-1df49fbf7eea',
        idJson: '36ee1383-d374-4ee7-905e-0586f672f23c',
        atrasoDeGrupo: 0
      }, {
        id: '332cd0b1-4eff-4b01-8364-a811c0ae3326',
        idJson: 'cde0a6bd-9064-49f0-9e2a-aa5623b9f8a9',
        atrasoDeGrupo: 0
      }, {
        id: '354e860a-c12f-420b-87ba-ac0fc52ebea2',
        idJson: '67804cae-7995-4244-8999-530ca2ec7ebd',
        atrasoDeGrupo: 0
      }, {
        id: '43304b5c-8c1f-4556-895e-a2979c150651',
        idJson: 'b9507206-a2e7-4266-adb8-9f0cbb56d910',
        atrasoDeGrupo: 0
      }, {
        id: 'ce74d7e3-c164-47dd-90e3-de8af8163369',
        idJson: 'acc5cd47-794f-44d1-b3b5-a2521acc540b',
        atrasoDeGrupo: 0
      }, {
        id: '33a41acb-6cf5-4848-8912-7544435d3e61',
        idJson: '3d2fc0ec-d9dc-4402-bc6e-d31f7da5a85f',
        atrasoDeGrupo: 0
      }, {
        id: 'b03d417b-abc1-4529-aec5-816ee30ec631',
        idJson: '652aed10-9efa-4085-87cc-b9bbfb0700f4',
        atrasoDeGrupo: 0
      }, {
        id: '92e7c71d-be3c-40ac-ac35-5aabb524fc45',
        idJson: '3617278f-be07-4d84-b74a-9d1c03644310',
        atrasoDeGrupo: 0
      }, {
        id: 'f76db99e-e7d3-4a9b-89fd-7c206bd15cc4',
        idJson: 'c2aadee7-1101-4cd0-b920-6eeb2c814057',
        atrasoDeGrupo: 0
      }, {
        id: '6e3df0f7-a3e3-4503-a3d5-52b14a16086f',
        idJson: '53fb3711-f98e-422f-aaa6-2b86240fb1f1',
        atrasoDeGrupo: 0
      }, {
        id: '665ec44c-b60d-47e1-9159-7d0056cb7c7f',
        idJson: 'e734f381-cc33-43b5-86e7-0f5e35592462',
        atrasoDeGrupo: 0
      }, {
        id: '436e0aeb-9259-4892-b5d0-25307d151d54',
        idJson: '26d36999-e31b-49c7-8210-c1aeaceec4c2',
        atrasoDeGrupo: 0
      }, {
        id: '0a666944-aacb-4b09-9ef4-5effad45fbd5',
        idJson: 'c851a810-01d7-4a48-bbfd-341699988ece',
        atrasoDeGrupo: 0
      }, {
        id: 'eb8c0f0e-29a0-4d75-87b0-7f524b62aaf5',
        idJson: '9a23f0dc-452d-400c-a573-f38cffbbb0b4',
        atrasoDeGrupo: 0
      }, {
        id: 'de573a09-3763-4ed5-bf57-1929524635ed',
        idJson: '37589912-89ec-4d2d-8314-42a8e2821267',
        atrasoDeGrupo: 0
      }, {
        id: '544aac34-d2f8-4a6c-b145-c349dc05ff2b',
        idJson: 'b2b7867c-a356-421f-89cd-b27884dbe88a',
        atrasoDeGrupo: 0
      }, {
        id: 'f2f14ddd-ff7b-43b9-a162-59944d8d8dc0',
        idJson: '5a8a4c85-20bd-42c6-b17b-4f56edb336ad',
        atrasoDeGrupo: 0
      }, {
        id: '36d65346-4780-4853-8e92-4b45f58077fb',
        idJson: 'd8be2e40-5787-4221-a070-79be30366bd0',
        atrasoDeGrupo: 0
      }, {
        id: '56581c6f-5440-4ff0-8f83-e041f0c0b93b',
        idJson: '55b930dd-6187-43a3-a37a-442fef64654a',
        atrasoDeGrupo: 0
      }, {
        id: '7485c010-da40-4358-a2c1-f8c3eb5fa8fa',
        idJson: '92c78668-9e34-4ad6-8ab5-d1af77bab64e',
        atrasoDeGrupo: 0
      }, {
        id: '9e8338bf-713b-461e-a4dd-681ff7fd2348',
        idJson: '5ff745af-c867-4c2e-88ad-64372645ff63',
        atrasoDeGrupo: 0
      }, {
        id: 'eedbd03f-7ad9-4dfe-a63c-4862a82f023a',
        idJson: '6ad2920a-be72-48f8-90ff-45e8f4fbfc2f',
        atrasoDeGrupo: 0
      }, {
        id: '8c93be8d-5ab4-4a32-b668-8f7dc7a32319',
        idJson: '4fa5650b-67d2-4b04-824f-6d59137bb29c',
        atrasoDeGrupo: 0
      }, {
        id: '5f27d949-0a04-40cb-a730-2fa83cf56374',
        idJson: 'ac28b35d-b247-49b4-9446-7c41dd7236de',
        atrasoDeGrupo: 0
      }, {
        id: 'aea65191-78d1-4ef6-b3c4-811a85f0cca5',
        idJson: 'ae748de6-6b18-467c-bb6d-1070458a0d48',
        atrasoDeGrupo: 0
      }, {
        id: '6bd9afe0-a22b-4ea8-8ded-8cae97a5f1bc',
        idJson: 'cb5b54f2-2114-42c8-8330-259dc50cccd7',
        atrasoDeGrupo: 0
      }, {
        id: '85f2db01-b341-4ead-b6fe-ce1de5cae625',
        idJson: 'bae9fadd-c2d8-4e65-be44-613fcef97de6',
        atrasoDeGrupo: 0
      }, {
        id: '94a5ff47-b468-484e-9c95-f449cfe1c6bd',
        idJson: '01d02724-d39b-4a90-9870-2b8eeb885087',
        atrasoDeGrupo: 0
      }, {
        id: '97e54e6b-be38-4c95-a01e-9af6c86fe3fb',
        idJson: '8a3259d3-5544-4153-97d7-4bb388bb6974',
        atrasoDeGrupo: 0
      }, {
        id: '9d39a332-3dfc-44f2-8881-ad7cafa5db6c',
        idJson: '1e2f136d-eb14-4474-bd10-4981b2fcb1f0',
        atrasoDeGrupo: 0
      }, {
        id: '1c7f86f5-9c59-4ce7-92da-7a49305cc8fe',
        idJson: '82f3aaa1-16ec-4675-8222-3441e06c7492',
        atrasoDeGrupo: 0
      }, {
        id: 'fb1db412-31b5-4ae6-8dea-099956aa92c4',
        idJson: '36eff934-5eb7-4e38-ae75-bb3e392e9580',
        atrasoDeGrupo: 0
      }, {
        id: '15249001-4335-47a0-8331-3c889e87b7bd',
        idJson: 'f9961440-e593-41c4-97a4-887119b4a073',
        atrasoDeGrupo: 0
      }, {
        id: 'f76d0efd-4679-4efd-8303-ab90e036547e',
        idJson: 'e276cf3a-448b-410e-b69a-477daaba8610',
        atrasoDeGrupo: 0
      }, {
        id: '1c4fd0c1-2013-4adc-a2fd-0634377468f1',
        idJson: '7d613382-a2f7-4d85-a131-04c28a353141',
        atrasoDeGrupo: 0
      }, {
        id: 'c52587ee-e783-4ff3-b53d-c87594702d33',
        idJson: 'a8aaaa96-aa4d-4046-b0ef-8a24f9e24a4d',
        atrasoDeGrupo: 0
      }, {
        id: '2f3f277a-97a1-420b-aca9-a025bbbd8384',
        idJson: 'd21ad13f-3bee-465a-a1f3-8389eefa1e4a',
        atrasoDeGrupo: 0
      }, {
        id: 'ee1272e1-6300-449c-a5fe-6270bf5e13a2',
        idJson: '1cad9f08-103e-445e-bce6-68ab92d9d4af',
        atrasoDeGrupo: 0
      }, {
        id: '920e55af-c890-4f9a-bf39-227e97b1d532',
        idJson: '159714d6-a887-433a-8be4-040f1a869a76',
        atrasoDeGrupo: 0
      }, {
        id: '53282901-01e8-4540-a3aa-1e2ee3552e20',
        idJson: '7e635d94-ff9e-4183-8549-f175faedff17',
        atrasoDeGrupo: 0
      }, {
        id: '1550f245-07f5-4ad0-a7c8-57de34b1e569',
        idJson: '597fb9f7-aff4-4bec-b518-8df2adc52e4e',
        atrasoDeGrupo: 0
      }, {
        id: '0394b1a7-a6bf-4c33-856c-62eb44723983',
        idJson: '250ec900-ed92-404b-8543-bf9801b214f3',
        atrasoDeGrupo: 0
      }, {
        id: 'dd46b49b-43d3-42fe-8eed-fce0ab0d52f7',
        idJson: '17d9f5c9-47a4-4109-88b3-95140a95c014',
        atrasoDeGrupo: 0
      }, {
        id: 'e55a3894-cd82-4ed4-a1b8-518eb10a8fa1',
        idJson: '8d1dc4f3-e065-4d89-b77b-c4694c762b8d',
        atrasoDeGrupo: 0
      }, {
        id: 'b3c6c78d-0ef9-4197-8a08-75a45c23cb0b',
        idJson: '66c83ccb-a85a-400e-aecc-d1ac3607a071',
        atrasoDeGrupo: 0
      }, {
        id: 'ce88b66d-6e69-428f-bfd7-96792d00b0a9',
        idJson: '1c63a975-5f28-44a8-b67b-cedf92ce88dc',
        atrasoDeGrupo: 0
      }, {
        id: '7dd79d08-2ce2-47cc-82d4-f45ec103748d',
        idJson: 'def659c5-f839-45b3-a7e7-977d1085e57e',
        atrasoDeGrupo: 0
      }, {
        id: 'f9a182ec-9e81-4c79-a6c7-6c360443865a',
        idJson: '7b94eb4d-d208-4655-a6a3-e15d40117262',
        atrasoDeGrupo: 0
      }, {
        id: '85708162-4a50-424d-98ab-138d3252ee18',
        idJson: '9fd12dda-dfc9-46fc-956d-242c856be527',
        atrasoDeGrupo: 0
      }, {
        id: 'dee52fcd-8a23-4f94-8d0f-2882e34463e3',
        idJson: 'ae05a546-4f72-408b-a12b-4d9159d80994',
        atrasoDeGrupo: 0
      }, {
        id: 'e0b907db-0529-4a14-a7cf-9184dcfb39af',
        idJson: '5b33bc33-113b-4827-b241-d0b2fb28e0ad',
        atrasoDeGrupo: 0
      }, {
        id: 'e8479c69-b364-4535-9ff2-7330674eb73d',
        idJson: '6f4113f9-45ef-4154-a2e3-b75f97a5e93b',
        atrasoDeGrupo: 0
      }, {
        id: '391862ba-bc0a-4120-99fd-ea64db6d2222',
        idJson: '46ecab32-416b-4145-871a-356a05593b78',
        atrasoDeGrupo: 0
      }, {
        id: 'edd66140-e5ae-4c95-8a0d-fe769aca48b6',
        idJson: '86aa7391-6649-49a6-bcab-d59d32d8e579',
        atrasoDeGrupo: 0
      }, {
        id: '6b820894-dd67-4db4-943d-91ce589b9833',
        idJson: '6c2c4328-1395-4733-8841-95d88d0162bd',
        atrasoDeGrupo: 0
      }, {
        id: 'b2a45a23-5e10-421d-86c4-e91144a3f92e',
        idJson: '310e4f8c-057d-4044-b5dd-3957da5ec04a',
        atrasoDeGrupo: 0
      }, {
        id: '9b2a2131-8649-4609-aa79-1fa4559bf478',
        idJson: '342c191d-3327-41ef-ab68-92213ee0cc67',
        atrasoDeGrupo: 0
      }, {
        id: '139bffef-2308-4744-b505-f14dac94aff0',
        idJson: 'de8de944-908c-446a-a44c-3d7fec2152be',
        atrasoDeGrupo: 0
      }, {
        id: '9bc68520-026f-4627-92c1-5bdf4be426fd',
        idJson: '8db63aa3-a1a1-433c-b5dd-4764863ebe28',
        atrasoDeGrupo: 0
      }],
      versaoControlador: {
        id: '63bfedc5-1eef-4a9e-ba29-2c914d17458e',
        idJson: null,
        descricao: 'Inicial',
        statusVersao: 'SINCRONIZADO',
        controlador: {
          id: 'd06c64f6-018e-49a7-b407-5f81ebc31dd5'
        },
        controladorFisico: {
          id: '67a60509-9a4c-4c3e-a367-db56892eaa52'
        },
        usuario: {
          id: 'a7726386-c3ed-11e6-ab15-0401fa4eb401',
          nome: 'Administrador Geral',
          login: 'root',
          email: 'root@influunt.com.br'
        }
      },
      statusVersao: 'SINCRONIZADO',
      versoesPlanos: [{
        id: 'fd690796-6c2c-46c1-a100-b3f355d0637a',
        idJson: '2f56c3ba-aab8-4453-bd24-482a468b8cb7',
        statusVersao: 'CONFIGURADO',
        anel: {
          idJson: 'a4226504-80f8-4a48-aff6-c62b3c7eb23d'
        },
        planos: [{
          idJson: 'ca53b7ff-75ab-4b52-9d7f-f4563b725367'
        }, {
          idJson: '2cedb173-3b9c-4831-8fb7-66a390dbeb45'
        }, {
          idJson: '89e75d74-65e5-4f2d-ad39-fdfa9b778fe9'
        }]
      }, {
        id: 'a307f16a-1deb-4828-8796-514b2246ec55',
        idJson: 'e9917061-18dc-4695-9a34-0f23c0fe5384',
        statusVersao: 'CONFIGURADO',
        anel: {
          idJson: '6596c0ff-441a-4b54-bcd0-1b8b80b24ada'
        },
        planos: [{
          idJson: '93046997-77ae-4ce3-8920-1841e890ce3d'
        }, {
          idJson: '6069b833-0873-4616-a35a-1cf3ad5a2c35'
        }, {
          idJson: '64111a27-35fb-4053-b4ea-84234bb2c431'
        }]
      }, {
        id: '0208f23b-9c94-4377-9f32-acc21e458d58',
        idJson: '9ba3f27a-aa74-4c0b-bc04-511d51d71284',
        statusVersao: 'CONFIGURADO',
        anel: {
          idJson: 'e8198dc9-c5c2-4c85-99d2-345032aac71e'
        },
        planos: [{
          idJson: 'e353f93b-2803-4cfe-a82f-fbd947036a78'
        }, {
          idJson: 'cfe1bace-4707-41eb-bb34-5f5e00749864'
        }, {
          idJson: '93999b9e-4ef0-42ef-8382-c64963146cd3'
        }]
      }, {
        id: 'a12e695c-70be-4ce7-87a5-a83b544da126',
        idJson: '5f6c6fe7-44a5-419d-bbea-686516f227ca',
        statusVersao: 'CONFIGURADO',
        anel: {
          idJson: 'd63044f9-5653-4c5c-90ec-d482d7871e04'
        },
        planos: [{
          idJson: 'a39e0b2f-12f7-4d92-bc09-04b659e36be7'
        }, {
          idJson: 'fdccbe6d-1a6a-417d-be51-2a8fa87ae99f'
        }, {
          idJson: '7aa35cee-16ae-4a8e-827b-b70b4490d2b6'
        }]
      }],
      tabelasHorarias: [{
        id: '8bcab27f-fa35-48bb-a853-b3e477cc6d4f',
        idJson: '4dcd3e6d-f46a-4f74-8100-a9e4b988ee94',
        versaoTabelaHoraria: {
          idJson: '087127a1-b4ce-4c5a-9476-e2e8a4979bfb'
        },
        eventos: [{
          idJson: '89952be9-17f5-4f59-82d6-ad3d2eafa21b'
        }, {
          idJson: '6e9e6b4d-f45e-4989-899d-eb86b75334f6'
        }, {
          idJson: '430a6466-a02d-4ecd-a0f1-e565a2997187'
        }]
      }],
      eventos: [{
        id: '9d5c6c0e-e5ea-4437-8ad0-cb422fc13608',
        idJson: '6e9e6b4d-f45e-4989-899d-eb86b75334f6',
        posicao: '2',
        tipo: 'NORMAL',
        diaDaSemana: 'Todos os dias da semana',
        data: '16-12-2016',
        horario: '05:00:00.000',
        posicaoPlano: '2',
        tabelaHoraria: {
          idJson: '4dcd3e6d-f46a-4f74-8100-a9e4b988ee94'
        }
      }, {
        id: 'c7f8c2cf-1f19-4501-a69b-8624072064a8',
        idJson: '430a6466-a02d-4ecd-a0f1-e565a2997187',
        posicao: '1',
        tipo: 'NORMAL',
        diaDaSemana: 'Todos os dias da semana',
        data: '16-12-2016',
        horario: '00:00:00.000',
        posicaoPlano: '1',
        tabelaHoraria: {
          idJson: '4dcd3e6d-f46a-4f74-8100-a9e4b988ee94'
        }
      }, {
        id: '660c4720-a1e8-49fd-9da4-14a3c354091c',
        idJson: '89952be9-17f5-4f59-82d6-ad3d2eafa21b',
        posicao: '3',
        tipo: 'NORMAL',
        diaDaSemana: 'Todos os dias da semana',
        data: '16-12-2016',
        horario: '09:00:00.000',
        posicaoPlano: '1',
        tabelaHoraria: {
          idJson: '4dcd3e6d-f46a-4f74-8100-a9e4b988ee94'
        }
      }]
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
