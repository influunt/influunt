'use strict';

/**
 * Conjunto de funções auxiliares utilizadas pelos testes do wizard de controladores.
 *
 * @type       {<type>}
 */
var ControladorAtrasoGrupoAutomatico = {
  obj: {
    'id': '4b87d1f7-7921-4588-b17a-4d005a8ec4ad',
    'versoesTabelasHorarias': [
      {
        'id': '70a35933-c599-41f2-9424-e083cdcc3f4a',
        'idJson': 'a2d145ab-cf3d-4c63-839a-49beeb9e962e',
        'statusVersao': 'EDITANDO',
        'tabelaHoraria': {
          'idJson': '77987356-2f13-4ac4-bed2-c0aecf7d6706'
        }
      }
    ],
    'numeroSMEE': '3341',
    'numeroSMEEConjugado1': '3342',
    'numeroSMEEConjugado2': '3343',
    'sequencia': 4,
    'limiteEstagio': 16,
    'limiteGrupoSemaforico': 16,
    'limiteAnel': 4,
    'limiteDetectorPedestre': 4,
    'limiteDetectorVeicular': 8,
    'limiteTabelasEntreVerdes': 2,
    'limitePlanos': 16,
    'nomeEndereco': 'R. Emília Marengo com Rua Itapura. ref.: 386',
    'dataCriacao': '03/11/2016 09:08:41',
    'dataAtualizacao': '07/11/2016 11:43:34',
    'CLC': '1.000.0004',
    'bloqueado': false,
    'planosBloqueado': false,
    'verdeMin': '1',
    'verdeMax': '255',
    'verdeMinimoMin': '10',
    'verdeMinimoMax': '255',
    'verdeMaximoMin': '10',
    'verdeMaximoMax': '255',
    'extensaoVerdeMin': '1.0',
    'extensaoVerdeMax': '10.0',
    'verdeIntermediarioMin': '10',
    'verdeIntermediarioMax': '255',
    'defasagemMin': '0',
    'defasagemMax': '255',
    'amareloMin': '3',
    'amareloMax': '5',
    'vermelhoIntermitenteMin': '3',
    'vermelhoIntermitenteMax': '32',
    'vermelhoLimpezaVeicularMin': '0',
    'vermelhoLimpezaVeicularMax': '7',
    'vermelhoLimpezaPedestreMin': '0',
    'vermelhoLimpezaPedestreMax': '5',
    'atrasoGrupoMin': '0',
    'atrasoGrupoMax': '20',
    'verdeSegurancaVeicularMin': '10',
    'verdeSegurancaVeicularMax': '30',
    'verdeSegurancaPedestreMin': '4',
    'verdeSegurancaPedestreMax': '10',
    'maximoPermanenciaEstagioMin': '60',
    'maximoPermanenciaEstagioMax': '255',
    'defaultMaximoPermanenciaEstagioVeicular': 127,
    'cicloMin': '30',
    'cicloMax': '255',
    'ausenciaDeteccaoMin': '0',
    'ausenciaDeteccaoMax': '4320',
    'deteccaoPermanenteMin': '0',
    'deteccaoPermanenteMax': '1440',
    'statusControlador': 'EM_CONFIGURACAO',
    'statusControladorReal': 'EM_CONFIGURACAO',
    'area': {
      'idJson': '66b6a0c4-a1c4-11e6-970d-0401fa9c1b01'
    },
    'endereco': {
      'idJson': '8586d3ea-8a3b-4c51-b171-cda543eabd05'
    },
    'modelo': {
      'id': '66b6ba69-a1c4-11e6-970d-0401fa9c1b01',
      'idJson': '66b6ba7e-a1c4-11e6-970d-0401fa9c1b01',
      'descricao': 'Modelo Básico',
      'fabricante': {
        'id': '66b6a723-a1c4-11e6-970d-0401fa9c1b01',
        'nome': 'Raro Labs'
      }
    },
    'aneis': [
      {
        'id': '1d6e1cef-1ec4-41f4-9532-03f5bd600e3f',
        'idJson': 'ea5465fe-c93b-464b-8c96-d47e2036a3d1',
        'ativo': true,
        'aceitaModoManual': true,
        'posicao': 2,
        'CLA': '1.000.0004.2',
        'versaoPlano': {
          'idJson': '67f12a26-3839-4c80-a712-778a7f76bde6'
        },
        'estagios': [
          {
            'idJson': '3b068b57-e93e-415d-88eb-84c5e35ccd3e'
          },
          {
            'idJson': '5ba381df-3f3d-40a4-bc3c-5b2ff88f1605'
          }
        ],
        'gruposSemaforicos': [
          {
            'idJson': 'aca70ec5-72b3-4878-9c3a-e66ae0ca314c'
          },
          {
            'idJson': 'bfb13da2-8ebf-49f4-8a7d-2c778bc0d0c0'
          }
        ],
        'detectores': [],
        'planos': [
          {
            'idJson': '24510147-ba9f-4a61-815d-63565ffca56f'
          },
          {
            'idJson': '29ba9ea9-d15c-46a5-b017-f507bf9c216d'
          }
        ],
        'endereco': {
          'idJson': 'e48eec2a-0b7a-44bf-889e-1c28fbdc58ea'
        }
      },
      {
        'id': '8d529b11-9c02-400f-b37e-77bca6f16cfe',
        'idJson': '31802649-dcdd-42b1-8b03-60a6194e7e16',
        'ativo': false,
        'aceitaModoManual': true,
        'posicao': 4,
        'CLA': '1.000.0004.4',
        'estagios': [],
        'gruposSemaforicos': [],
        'detectores': [],
        'planos': []
      },
      {
        'id': '9a12678a-b1af-4a24-b122-609908d58582',
        'idJson': 'f30c1621-ec51-47ed-bff1-a9758512d865',
        'ativo': true,
        'aceitaModoManual': true,
        'posicao': 3,
        'CLA': '1.000.0004.3',
        'versaoPlano': {
          'idJson': 'ac4757a0-87bc-4b3b-a391-1e3ca8f9571b'
        },
        'estagios': [
          {
            'idJson': '01f5dce2-391a-4930-882d-bf0d656fb17a'
          },
          {
            'idJson': '6b771a42-d8d2-4f90-9fc7-fd82bfcbd8b9'
          },
          {
            'idJson': 'd3d9c928-61df-4eee-b87c-22c674037d70'
          }
        ],
        'gruposSemaforicos': [
          {
            'idJson': '7264e985-8082-4120-8577-cb83f8b26e90'
          },
          {
            'idJson': '17763263-95ac-46ef-8d3e-7bbb98f2cdd2'
          },
          {
            'idJson': '71c8973f-87d4-42b4-92bb-49cf5693259a'
          }
        ],
        'detectores': [
          {
            'idJson': 'e57e24cf-0295-4a9e-8534-35afac23b532'
          }
        ],
        'planos': [
          {
            'idJson': '10d40af6-212e-4dc4-879a-81575f16e11f'
          },
          {
            'idJson': 'a867bcce-2e4a-44fc-b88d-5a9579b74857'
          }
        ],
        'endereco': {
          'idJson': '07ff47fa-558c-47e4-b604-5f0f190bebf7'
        }
      },
      {
        'id': 'fe3610bb-155b-4ee5-821e-1abcad30755e',
        'idJson': 'e7457fca-897e-4bd9-a380-050c4c6c58b8',
        'numeroSMEE': '3341',
        'ativo': true,
        'aceitaModoManual': true,
        'posicao': 1,
        'CLA': '1.000.0004.1',
        'versaoPlano': {
          'idJson': 'd3ea90e7-ceda-4100-bf9a-e845a0e2af38'
        },
        'estagios': [
          {
            'idJson': '242d2c3c-1773-4688-8c1e-fde218755bd1'
          },
          {
            'idJson': '83f9f8e0-7308-4e76-a275-643564205996'
          },
          {
            'idJson': '48964f57-8d94-4abc-b5cd-8e9458459d5c'
          }
        ],
        'gruposSemaforicos': [
          {
            'idJson': '306eb390-603f-4ff1-a395-815bcc6d075e'
          },
          {
            'idJson': '1ebe4788-d27c-491f-a167-15822aaf7725'
          },
          {
            'idJson': '91738db7-eb21-4281-a075-8beae9eee256'
          },
          {
            'idJson': '15a4b75c-93c5-47bf-86ba-e9c424a9cc04'
          },
          {
            'idJson': '9050c64b-81f4-478d-85bf-834a46df783c'
          }
        ],
        'detectores': [
          {
            'idJson': '5ef1fad1-499c-417e-a2f4-706d6b0a6f6b'
          }
        ],
        'planos': [
          {
            'idJson': 'acbdfff1-2d2f-4582-bed3-639513470da9'
          },
          {
            'idJson': '9d937706-5b1a-4007-b9e4-a530c13b1409'
          }
        ],
        'endereco': {
          'idJson': '3632c309-6fb8-4d81-b7da-0ecab452ba7c'
        }
      }
    ],
    'estagios': [
      {
        'id': '02a90d32-cafb-4f30-8aa3-e111956be126',
        'idJson': '01f5dce2-391a-4930-882d-bf0d656fb17a',
        'tempoMaximoPermanencia': 127,
        'tempoMaximoPermanenciaAtivado': true,
        'demandaPrioritaria': false,
        'tempoVerdeDemandaPrioritaria': 1,
        'posicao': 3,
        'anel': {
          'idJson': 'f30c1621-ec51-47ed-bff1-a9758512d865'
        },
        'imagem': {
          'idJson': '4c2f780e-0ee4-43a8-9fb8-3741e16700b4'
        },
        'origemDeTransicoesProibidas': [],
        'destinoDeTransicoesProibidas': [],
        'alternativaDeTransicoesProibidas': [],
        'estagiosGruposSemaforicos': [
          {
            'idJson': 'c52f614f-2dc9-4fef-b4e7-f2a40f1d7073'
          }
        ]
      },
      {
        'id': '9e647fea-27a8-431a-9db1-235160e652f3',
        'idJson': '5ba381df-3f3d-40a4-bc3c-5b2ff88f1605',
        'tempoMaximoPermanencia': 127,
        'tempoMaximoPermanenciaAtivado': true,
        'demandaPrioritaria': false,
        'tempoVerdeDemandaPrioritaria': 1,
        'posicao': 1,
        'anel': {
          'idJson': 'ea5465fe-c93b-464b-8c96-d47e2036a3d1'
        },
        'imagem': {
          'idJson': '4e1e4cde-2746-42e0-ad2f-d37aac3948d8'
        },
        'origemDeTransicoesProibidas': [],
        'destinoDeTransicoesProibidas': [],
        'alternativaDeTransicoesProibidas': [],
        'estagiosGruposSemaforicos': [
          {
            'idJson': '1a92ac9b-a680-4057-8e62-a31679cbeb66'
          }
        ]
      },
      {
        'id': '22ac3625-580c-44c8-a880-b1c19c292485',
        'idJson': '6b771a42-d8d2-4f90-9fc7-fd82bfcbd8b9',
        'tempoMaximoPermanencia': 127,
        'tempoMaximoPermanenciaAtivado': true,
        'demandaPrioritaria': false,
        'tempoVerdeDemandaPrioritaria': 1,
        'posicao': 2,
        'anel': {
          'idJson': 'f30c1621-ec51-47ed-bff1-a9758512d865'
        },
        'imagem': {
          'idJson': '608ee6c8-81dd-47fd-9834-f02181f7c243'
        },
        'origemDeTransicoesProibidas': [],
        'destinoDeTransicoesProibidas': [],
        'alternativaDeTransicoesProibidas': [],
        'estagiosGruposSemaforicos': [
          {
            'idJson': '2e63fd43-c4a6-478d-8ded-21eb4a2f2946'
          }
        ],
        'detector': {
          'idJson': 'e57e24cf-0295-4a9e-8534-35afac23b532'
        }
      },
      {
        'id': '1750fded-dc81-44eb-a9d5-2e87e03637ee',
        'idJson': '242d2c3c-1773-4688-8c1e-fde218755bd1',
        'tempoMaximoPermanencia': 127,
        'tempoMaximoPermanenciaAtivado': true,
        'demandaPrioritaria': false,
        'tempoVerdeDemandaPrioritaria': 1,
        'posicao': 1,
        'anel': {
          'idJson': 'e7457fca-897e-4bd9-a380-050c4c6c58b8'
        },
        'imagem': {
          'idJson': '352f6715-db29-4b3e-9303-fd5dc123289b'
        },
        'origemDeTransicoesProibidas': [],
        'destinoDeTransicoesProibidas': [
          {
            'idJson': 'ab63f066-a001-4d45-b07a-1ad615470ec8'
          }
        ],
        'alternativaDeTransicoesProibidas': [
          {
            'idJson': '1759a0fa-81cc-4989-9111-aa94cca33e3d'
          }
        ],
        'estagiosGruposSemaforicos': [
          {
            'idJson': '204ccea9-24a9-4ed2-b99c-0d25bfdee572'
          },
          {
            'idJson': '19e5206c-25d8-4586-a02c-4d2837950ea3'
          }
        ]
      },
      {
        'id': '1b7fcdb9-2201-4601-85d0-118ef30f43dc',
        'idJson': '83f9f8e0-7308-4e76-a275-643564205996',
        'tempoMaximoPermanencia': 127,
        'tempoMaximoPermanenciaAtivado': true,
        'demandaPrioritaria': false,
        'tempoVerdeDemandaPrioritaria': 1,
        'posicao': 3,
        'anel': {
          'idJson': 'e7457fca-897e-4bd9-a380-050c4c6c58b8'
        },
        'imagem': {
          'idJson': 'cc73f6d9-1a14-4a9d-9b8b-7c8e07a17c94'
        },
        'origemDeTransicoesProibidas': [
          {
            'idJson': '1759a0fa-81cc-4989-9111-aa94cca33e3d'
          }
        ],
        'destinoDeTransicoesProibidas': [],
        'alternativaDeTransicoesProibidas': [
          {
            'idJson': 'ab63f066-a001-4d45-b07a-1ad615470ec8'
          }
        ],
        'estagiosGruposSemaforicos': [
          {
            'idJson': '3895aa23-3224-4b14-8dc1-a28297d5376d'
          },
          {
            'idJson': '3e136e19-b02b-44f5-b5c7-1a6d306d430b'
          }
        ]
      },
      {
        'id': 'e8f1b3cc-6c89-40bb-98f7-2081d614f164',
        'idJson': 'd3d9c928-61df-4eee-b87c-22c674037d70',
        'tempoMaximoPermanencia': 127,
        'tempoMaximoPermanenciaAtivado': true,
        'demandaPrioritaria': false,
        'tempoVerdeDemandaPrioritaria': 1,
        'posicao': 1,
        'anel': {
          'idJson': 'f30c1621-ec51-47ed-bff1-a9758512d865'
        },
        'imagem': {
          'idJson': '7ab268a9-7b30-4851-b9a2-64c445fcb662'
        },
        'origemDeTransicoesProibidas': [],
        'destinoDeTransicoesProibidas': [],
        'alternativaDeTransicoesProibidas': [],
        'estagiosGruposSemaforicos': [
          {
            'idJson': '6ea843c3-c283-42e1-9288-e81aae4cb9fd'
          }
        ]
      },
      {
        'id': '731074d9-12f7-4d0e-a784-b5530a856357',
        'idJson': '3b068b57-e93e-415d-88eb-84c5e35ccd3e',
        'tempoMaximoPermanencia': 127,
        'tempoMaximoPermanenciaAtivado': true,
        'demandaPrioritaria': false,
        'tempoVerdeDemandaPrioritaria': 1,
        'posicao': 2,
        'anel': {
          'idJson': 'ea5465fe-c93b-464b-8c96-d47e2036a3d1'
        },
        'imagem': {
          'idJson': 'db2e885f-d3f0-4afc-8f28-bf974df5fbac'
        },
        'origemDeTransicoesProibidas': [],
        'destinoDeTransicoesProibidas': [],
        'alternativaDeTransicoesProibidas': [],
        'estagiosGruposSemaforicos': [
          {
            'idJson': '58fe27eb-9808-414e-97de-076516b78d79'
          }
        ]
      },
      {
        'id': 'd26843f7-5e39-4531-985a-407982156329',
        'idJson': '48964f57-8d94-4abc-b5cd-8e9458459d5c',
        'tempoMaximoPermanencia': 60,
        'tempoMaximoPermanenciaAtivado': true,
        'demandaPrioritaria': false,
        'tempoVerdeDemandaPrioritaria': 1,
        'posicao': 2,
        'anel': {
          'idJson': 'e7457fca-897e-4bd9-a380-050c4c6c58b8'
        },
        'imagem': {
          'idJson': 'a98e40d4-c307-430c-820a-f87fc725ccce'
        },
        'origemDeTransicoesProibidas': [
          {
            'idJson': 'ab63f066-a001-4d45-b07a-1ad615470ec8'
          }
        ],
        'destinoDeTransicoesProibidas': [
          {
            'idJson': '1759a0fa-81cc-4989-9111-aa94cca33e3d'
          }
        ],
        'alternativaDeTransicoesProibidas': [],
        'estagiosGruposSemaforicos': [
          {
            'idJson': 'bbf8e4a8-a955-4125-9ec2-c98c0247c1ae'
          },
          {
            'idJson': '10f01256-3205-4cb2-a92b-339564630e1a'
          }
        ],
        'detector': {
          'idJson': '5ef1fad1-499c-417e-a2f4-706d6b0a6f6b'
        }
      }
    ],
    'gruposSemaforicos': [
      {
        'id': '8970ad8b-149e-47c8-ac81-47a17c6f42e6',
        'idJson': '15a4b75c-93c5-47bf-86ba-e9c424a9cc04',
        'tipo': 'VEICULAR',
        'posicao': 5,
        'faseVermelhaApagadaAmareloIntermitente': true,
        'tempoVerdeSeguranca': 10,
        'anel': {
          'idJson': 'e7457fca-897e-4bd9-a380-050c4c6c58b8'
        },
        'verdesConflitantesOrigem': [],
        'verdesConflitantesDestino': [
          {
            'idJson': 'eae40cc0-5c90-4664-aa85-74f468d1df6d'
          }
        ],
        'estagiosGruposSemaforicos': [
          {
            'idJson': '3895aa23-3224-4b14-8dc1-a28297d5376d'
          },
          {
            'idJson': '204ccea9-24a9-4ed2-b99c-0d25bfdee572'
          }
        ],
        'transicoes': [
          {
            'idJson': 'd5a8b3de-df9f-4449-8dd1-55f2a9805080'
          }
        ],
        'transicoesComGanhoDePassagem': [
          {
            'idJson': '63c98351-d4ac-489c-917f-39bac6595a8b'
          }
        ],
        'tabelasEntreVerdes': [
          {
            'idJson': '6be66f7e-eb77-4f31-ab6f-eec4e46ed4d1'
          }
        ]
      },
      {
        'id': '0465cda7-24a8-4e5a-befe-f05c90bb62b3',
        'idJson': '7264e985-8082-4120-8577-cb83f8b26e90',
        'tipo': 'VEICULAR',
        'posicao': 8,
        'faseVermelhaApagadaAmareloIntermitente': true,
        'tempoVerdeSeguranca': 10,
        'anel': {
          'idJson': 'f30c1621-ec51-47ed-bff1-a9758512d865'
        },
        'verdesConflitantesOrigem': [
          {
            'idJson': 'a2f0b558-6b6e-4818-bbd1-d273254875dd'
          },
          {
            'idJson': '137f1164-e501-4fd2-904d-8e151d151ee7'
          }
        ],
        'verdesConflitantesDestino': [],
        'estagiosGruposSemaforicos': [
          {
            'idJson': '2e63fd43-c4a6-478d-8ded-21eb4a2f2946'
          }
        ],
        'transicoes': [
          {
            'idJson': '81cd2daa-d5d1-44d2-ad23-5bf0764a0b10'
          },
          {
            'idJson': '44077fec-0719-4a7c-81e9-7ee5eadcdf5d'
          }
        ],
        'transicoesComGanhoDePassagem': [
          {
            'idJson': '4c814a1a-c0ee-46f1-bdf8-d9e7be2b3526'
          },
          {
            'idJson': '4356ad3b-325c-4a98-a4f9-698738be5832'
          }
        ],
        'tabelasEntreVerdes': [
          {
            'idJson': '2bfc75fa-614b-4d15-892f-7b86e246ae4a'
          }
        ]
      },
      {
        'id': 'e378cf38-89f8-46d8-81c8-a1beb21ee9cd',
        'idJson': '9050c64b-81f4-478d-85bf-834a46df783c',
        'tipo': 'VEICULAR',
        'posicao': 1,
        'faseVermelhaApagadaAmareloIntermitente': true,
        'tempoVerdeSeguranca': 10,
        'anel': {
          'idJson': 'e7457fca-897e-4bd9-a380-050c4c6c58b8'
        },
        'verdesConflitantesOrigem': [
          {
            'idJson': '12be3c45-3f05-4898-9d5f-424fc5e80d15'
          },
          {
            'idJson': 'b813f6c1-9416-49d4-be5f-88e830f19ef3'
          }
        ],
        'verdesConflitantesDestino': [],
        'estagiosGruposSemaforicos': [
          {
            'idJson': '19e5206c-25d8-4586-a02c-4d2837950ea3'
          }
        ],
        'transicoes': [
          {
            'idJson': 'c712c980-6a6c-48b3-abac-3daca1c1fd40'
          },
          {
            'idJson': '1b362201-eef3-440c-943f-87f16854fa82'
          }
        ],
        'transicoesComGanhoDePassagem': [
          {
            'idJson': '9156e406-7f65-4b27-a177-c1d89025158b'
          }
        ],
        'tabelasEntreVerdes': [
          {
            'idJson': '16f1dd3e-1c96-466f-bed6-40660481fd09'
          }
        ]
      },
      {
        'id': '3805c8bc-10d5-4d8b-a89d-812de8da87e4',
        'idJson': '17763263-95ac-46ef-8d3e-7bbb98f2cdd2',
        'tipo': 'VEICULAR',
        'posicao': 9,
        'faseVermelhaApagadaAmareloIntermitente': true,
        'tempoVerdeSeguranca': 10,
        'anel': {
          'idJson': 'f30c1621-ec51-47ed-bff1-a9758512d865'
        },
        'verdesConflitantesOrigem': [
          {
            'idJson': '55fc54a5-0be6-43ce-88cd-ffcd6243144e'
          }
        ],
        'verdesConflitantesDestino': [
          {
            'idJson': '137f1164-e501-4fd2-904d-8e151d151ee7'
          }
        ],
        'estagiosGruposSemaforicos': [
          {
            'idJson': '6ea843c3-c283-42e1-9288-e81aae4cb9fd'
          }
        ],
        'transicoes': [
          {
            'idJson': '73d91029-9f8e-4e89-8c18-85f876fa41c3'
          },
          {
            'idJson': '850fe771-dddf-494d-933a-0dbb7d714dd7'
          }
        ],
        'transicoesComGanhoDePassagem': [
          {
            'idJson': 'be5768f6-330a-483a-9530-2b61bdc5fe01'
          },
          {
            'idJson': 'bc71ea05-62e4-4fc0-bfd3-941720da8b76'
          }
        ],
        'tabelasEntreVerdes': [
          {
            'idJson': '92a17459-ca97-4c8e-9b72-7fdcab877a62'
          }
        ]
      },
      {
        'id': '46168ae9-26f0-4cbc-9ebe-b45d8f068133',
        'idJson': '91738db7-eb21-4281-a075-8beae9eee256',
        'tipo': 'VEICULAR',
        'posicao': 2,
        'faseVermelhaApagadaAmareloIntermitente': true,
        'tempoVerdeSeguranca': 10,
        'anel': {
          'idJson': 'e7457fca-897e-4bd9-a380-050c4c6c58b8'
        },
        'verdesConflitantesOrigem': [],
        'verdesConflitantesDestino': [
          {
            'idJson': 'b813f6c1-9416-49d4-be5f-88e830f19ef3'
          }
        ],
        'estagiosGruposSemaforicos': [
          {
            'idJson': '10f01256-3205-4cb2-a92b-339564630e1a'
          }
        ],
        'transicoes': [
          {
            'idJson': '7789bdfa-765f-48ef-8bdc-2a84f709291c'
          }
        ],
        'transicoesComGanhoDePassagem': [
          {
            'idJson': '5f35641b-250c-479e-994b-697478544687'
          }
        ],
        'tabelasEntreVerdes': [
          {
            'idJson': '7e29b911-52e3-4db9-9af0-45a4d45f03a6'
          }
        ]
      },
      {
        'id': '329b305c-37c4-47bf-9d88-acc5e2fbff62',
        'idJson': '306eb390-603f-4ff1-a395-815bcc6d075e',
        'tipo': 'PEDESTRE',
        'posicao': 4,
        'faseVermelhaApagadaAmareloIntermitente': false,
        'tempoVerdeSeguranca': 4,
        'anel': {
          'idJson': 'e7457fca-897e-4bd9-a380-050c4c6c58b8'
        },
        'verdesConflitantesOrigem': [
          {
            'idJson': 'eae40cc0-5c90-4664-aa85-74f468d1df6d'
          }
        ],
        'verdesConflitantesDestino': [],
        'estagiosGruposSemaforicos': [
          {
            'idJson': 'bbf8e4a8-a955-4125-9ec2-c98c0247c1ae'
          }
        ],
        'transicoes': [
          {
            'idJson': '99333939-b2b2-4db9-8448-059f0a635c5d'
          }
        ],
        'transicoesComGanhoDePassagem': [
          {
            'idJson': '9665b14a-0618-4063-b5af-697992c6a341'
          }
        ],
        'tabelasEntreVerdes': [
          {
            'idJson': 'fd884e45-cc3f-4963-b257-c039c47f80d4'
          }
        ]
      },
      {
        'id': '432629d9-908b-4c77-a4f2-37085fba755f',
        'idJson': '1ebe4788-d27c-491f-a167-15822aaf7725',
        'tipo': 'VEICULAR',
        'posicao': 3,
        'faseVermelhaApagadaAmareloIntermitente': true,
        'tempoVerdeSeguranca': 10,
        'anel': {
          'idJson': 'e7457fca-897e-4bd9-a380-050c4c6c58b8'
        },
        'verdesConflitantesOrigem': [],
        'verdesConflitantesDestino': [
          {
            'idJson': '12be3c45-3f05-4898-9d5f-424fc5e80d15'
          }
        ],
        'estagiosGruposSemaforicos': [
          {
            'idJson': '3e136e19-b02b-44f5-b5c7-1a6d306d430b'
          }
        ],
        'transicoes': [
          {
            'idJson': '13244186-7491-4572-aa9c-ecead504a732'
          }
        ],
        'transicoesComGanhoDePassagem': [
          {
            'idJson': '3549c7af-a0fa-4e0e-91a8-b071525ba12e'
          },
          {
            'idJson': 'd2dfb650-ba71-4cc9-a134-15a6ba72d990'
          }
        ],
        'tabelasEntreVerdes': [
          {
            'idJson': '7db1328d-d570-4ae3-8b91-56bf3835b366'
          }
        ]
      },
      {
        'id': 'ec9a92c7-eb62-4eee-806f-8ad264328005',
        'idJson': '71c8973f-87d4-42b4-92bb-49cf5693259a',
        'tipo': 'VEICULAR',
        'posicao': 10,
        'faseVermelhaApagadaAmareloIntermitente': true,
        'tempoVerdeSeguranca': 10,
        'anel': {
          'idJson': 'f30c1621-ec51-47ed-bff1-a9758512d865'
        },
        'verdesConflitantesOrigem': [],
        'verdesConflitantesDestino': [
          {
            'idJson': '55fc54a5-0be6-43ce-88cd-ffcd6243144e'
          },
          {
            'idJson': 'a2f0b558-6b6e-4818-bbd1-d273254875dd'
          }
        ],
        'estagiosGruposSemaforicos': [
          {
            'idJson': 'c52f614f-2dc9-4fef-b4e7-f2a40f1d7073'
          }
        ],
        'transicoes': [
          {
            'idJson': '47a89330-821e-41c2-97bb-1acdca7105ba'
          },
          {
            'idJson': 'c88d9dbd-c8fa-495e-8244-59bf3993315c'
          }
        ],
        'transicoesComGanhoDePassagem': [
          {
            'idJson': '7385fd02-4fbd-4d3e-a602-e92823fb4b7f'
          },
          {
            'idJson': '4a049e7a-89c9-4629-ad5a-63e3d714a11f'
          }
        ],
        'tabelasEntreVerdes': [
          {
            'idJson': '65f898a8-a5ec-494c-9329-dda4aa2a0f3c'
          }
        ]
      },
      {
        'id': '965dfafb-8cbc-4754-aa2f-c721f53112b1',
        'idJson': 'bfb13da2-8ebf-49f4-8a7d-2c778bc0d0c0',
        'tipo': 'VEICULAR',
        'posicao': 7,
        'faseVermelhaApagadaAmareloIntermitente': true,
        'tempoVerdeSeguranca': 10,
        'anel': {
          'idJson': 'ea5465fe-c93b-464b-8c96-d47e2036a3d1'
        },
        'verdesConflitantesOrigem': [],
        'verdesConflitantesDestino': [
          {
            'idJson': 'f2838f50-f6c8-4b0b-b5fd-6b2b01b785f0'
          }
        ],
        'estagiosGruposSemaforicos': [
          {
            'idJson': '1a92ac9b-a680-4057-8e62-a31679cbeb66'
          }
        ],
        'transicoes': [
          {
            'idJson': 'bae2e050-e047-4321-b971-d480db51de22'
          }
        ],
        'transicoesComGanhoDePassagem': [
          {
            'idJson': 'ee5407e9-fa17-4ee0-b193-613d3968204a'
          }
        ],
        'tabelasEntreVerdes': [
          {
            'idJson': '4b9a4e03-d9e1-45e7-ac6f-f07a5527bfc9'
          }
        ]
      },
      {
        'id': '415f434d-d1b4-4af7-8423-b9d9cfa1ae75',
        'idJson': 'aca70ec5-72b3-4878-9c3a-e66ae0ca314c',
        'tipo': 'VEICULAR',
        'posicao': 6,
        'faseVermelhaApagadaAmareloIntermitente': true,
        'tempoVerdeSeguranca': 10,
        'anel': {
          'idJson': 'ea5465fe-c93b-464b-8c96-d47e2036a3d1'
        },
        'verdesConflitantesOrigem': [
          {
            'idJson': 'f2838f50-f6c8-4b0b-b5fd-6b2b01b785f0'
          }
        ],
        'verdesConflitantesDestino': [],
        'estagiosGruposSemaforicos': [
          {
            'idJson': '58fe27eb-9808-414e-97de-076516b78d79'
          }
        ],
        'transicoes': [
          {
            'idJson': '6022d71b-55dc-45d4-95c1-6da4b85c2c6e'
          }
        ],
        'transicoesComGanhoDePassagem': [
          {
            'idJson': '03e0529b-b4cc-4f39-bbd2-cd7e368db772'
          }
        ],
        'tabelasEntreVerdes': [
          {
            'idJson': 'c73f738f-4ee4-4757-888c-ec2195437e7a'
          }
        ]
      }
    ],
    'detectores': [
      {
        'id': '83b463f1-90f9-49c4-a2c5-b38cb9fc61a1',
        'idJson': '5ef1fad1-499c-417e-a2f4-706d6b0a6f6b',
        'tipo': 'PEDESTRE',
        'posicao': 1,
        'monitorado': true,
        'tempoAusenciaDeteccao': 60,
        'tempoDeteccaoPermanente': 120,
        'anel': {
          'idJson': 'e7457fca-897e-4bd9-a380-050c4c6c58b8'
        },
        'estagio': {
          'idJson': '48964f57-8d94-4abc-b5cd-8e9458459d5c'
        }
      },
      {
        'id': '8ed0b1e4-c98a-407d-8595-2c4b34a7448f',
        'idJson': 'e57e24cf-0295-4a9e-8534-35afac23b532',
        'tipo': 'VEICULAR',
        'posicao': 1,
        'monitorado': true,
        'tempoAusenciaDeteccao': 60,
        'tempoDeteccaoPermanente': 120,
        'anel': {
          'idJson': 'f30c1621-ec51-47ed-bff1-a9758512d865'
        },
        'estagio': {
          'idJson': '6b771a42-d8d2-4f90-9fc7-fd82bfcbd8b9'
        }
      }
    ],
    'transicoesProibidas': [
      {
        'id': '35b7796f-fee0-4588-a853-299b95a68a2f',
        'idJson': '1759a0fa-81cc-4989-9111-aa94cca33e3d',
        'origem': {
          'idJson': '83f9f8e0-7308-4e76-a275-643564205996'
        },
        'destino': {
          'idJson': '48964f57-8d94-4abc-b5cd-8e9458459d5c'
        },
        'alternativo': {
          'idJson': '242d2c3c-1773-4688-8c1e-fde218755bd1'
        }
      },
      {
        'id': 'a614666b-5a67-403f-b685-898d9781bb63',
        'idJson': 'ab63f066-a001-4d45-b07a-1ad615470ec8',
        'origem': {
          'idJson': '48964f57-8d94-4abc-b5cd-8e9458459d5c'
        },
        'destino': {
          'idJson': '242d2c3c-1773-4688-8c1e-fde218755bd1'
        },
        'alternativo': {
          'idJson': '83f9f8e0-7308-4e76-a275-643564205996'
        }
      }
    ],
    'estagiosGruposSemaforicos': [
      {
        'id': '1c7e0986-52e9-45d8-97c9-ece9046e1233',
        'idJson': 'c52f614f-2dc9-4fef-b4e7-f2a40f1d7073',
        'estagio': {
          'idJson': '01f5dce2-391a-4930-882d-bf0d656fb17a'
        },
        'grupoSemaforico': {
          'idJson': '71c8973f-87d4-42b4-92bb-49cf5693259a'
        }
      },
      {
        'id': '67689ca4-67f8-413d-8af8-f4f1151e7bf1',
        'idJson': '6ea843c3-c283-42e1-9288-e81aae4cb9fd',
        'estagio': {
          'idJson': 'd3d9c928-61df-4eee-b87c-22c674037d70'
        },
        'grupoSemaforico': {
          'idJson': '17763263-95ac-46ef-8d3e-7bbb98f2cdd2'
        }
      },
      {
        'id': '8050fdbc-ec04-4689-896b-d706a8c65c4b',
        'idJson': '204ccea9-24a9-4ed2-b99c-0d25bfdee572',
        'estagio': {
          'idJson': '242d2c3c-1773-4688-8c1e-fde218755bd1'
        },
        'grupoSemaforico': {
          'idJson': '15a4b75c-93c5-47bf-86ba-e9c424a9cc04'
        }
      },
      {
        'id': '0ef6d3db-bab9-4c36-b4b6-55e2eab1bb03',
        'idJson': '3895aa23-3224-4b14-8dc1-a28297d5376d',
        'estagio': {
          'idJson': '83f9f8e0-7308-4e76-a275-643564205996'
        },
        'grupoSemaforico': {
          'idJson': '15a4b75c-93c5-47bf-86ba-e9c424a9cc04'
        }
      },
      {
        'id': '413e5ded-feeb-4ff9-9a80-1ef32041b090',
        'idJson': '3e136e19-b02b-44f5-b5c7-1a6d306d430b',
        'estagio': {
          'idJson': '83f9f8e0-7308-4e76-a275-643564205996'
        },
        'grupoSemaforico': {
          'idJson': '1ebe4788-d27c-491f-a167-15822aaf7725'
        }
      },
      {
        'id': '482f1610-6966-4c92-9f69-cb468ee6aca0',
        'idJson': '58fe27eb-9808-414e-97de-076516b78d79',
        'estagio': {
          'idJson': '3b068b57-e93e-415d-88eb-84c5e35ccd3e'
        },
        'grupoSemaforico': {
          'idJson': 'aca70ec5-72b3-4878-9c3a-e66ae0ca314c'
        }
      },
      {
        'id': '1b26ba75-0809-49dd-8df3-556a49b78874',
        'idJson': 'bbf8e4a8-a955-4125-9ec2-c98c0247c1ae',
        'estagio': {
          'idJson': '48964f57-8d94-4abc-b5cd-8e9458459d5c'
        },
        'grupoSemaforico': {
          'idJson': '306eb390-603f-4ff1-a395-815bcc6d075e'
        }
      },
      {
        'id': 'c8cab97a-b889-40c6-8828-d313b6d12c15',
        'idJson': '1a92ac9b-a680-4057-8e62-a31679cbeb66',
        'estagio': {
          'idJson': '5ba381df-3f3d-40a4-bc3c-5b2ff88f1605'
        },
        'grupoSemaforico': {
          'idJson': 'bfb13da2-8ebf-49f4-8a7d-2c778bc0d0c0'
        }
      },
      {
        'id': 'c2a6b2c4-d7ce-44b6-af72-ea9b67b83920',
        'idJson': '10f01256-3205-4cb2-a92b-339564630e1a',
        'estagio': {
          'idJson': '48964f57-8d94-4abc-b5cd-8e9458459d5c'
        },
        'grupoSemaforico': {
          'idJson': '91738db7-eb21-4281-a075-8beae9eee256'
        }
      },
      {
        'id': '82a5c973-df3a-49ca-99e4-c2305ea3f487',
        'idJson': '2e63fd43-c4a6-478d-8ded-21eb4a2f2946',
        'estagio': {
          'idJson': '6b771a42-d8d2-4f90-9fc7-fd82bfcbd8b9'
        },
        'grupoSemaforico': {
          'idJson': '7264e985-8082-4120-8577-cb83f8b26e90'
        }
      },
      {
        'id': '8d90f790-18c5-4afa-95f6-4f621b0bb3ce',
        'idJson': '19e5206c-25d8-4586-a02c-4d2837950ea3',
        'estagio': {
          'idJson': '242d2c3c-1773-4688-8c1e-fde218755bd1'
        },
        'grupoSemaforico': {
          'idJson': '9050c64b-81f4-478d-85bf-834a46df783c'
        }
      }
    ],
    'verdesConflitantes': [
      {
        'id': '891c339a-877e-4a27-82e4-fba54697c3eb',
        'idJson': 'a2f0b558-6b6e-4818-bbd1-d273254875dd',
        'origem': {
          'idJson': '7264e985-8082-4120-8577-cb83f8b26e90'
        },
        'destino': {
          'idJson': '71c8973f-87d4-42b4-92bb-49cf5693259a'
        }
      },
      {
        'id': '44335e64-4468-472d-bf42-cc84ea37abc8',
        'idJson': '12be3c45-3f05-4898-9d5f-424fc5e80d15',
        'origem': {
          'idJson': '9050c64b-81f4-478d-85bf-834a46df783c'
        },
        'destino': {
          'idJson': '1ebe4788-d27c-491f-a167-15822aaf7725'
        }
      },
      {
        'id': '944de8d7-3e6c-433d-bbdb-e34af1c4f1b3',
        'idJson': 'f2838f50-f6c8-4b0b-b5fd-6b2b01b785f0',
        'origem': {
          'idJson': 'aca70ec5-72b3-4878-9c3a-e66ae0ca314c'
        },
        'destino': {
          'idJson': 'bfb13da2-8ebf-49f4-8a7d-2c778bc0d0c0'
        }
      },
      {
        'id': '546e6808-6e65-4237-8b56-b440cd6453e8',
        'idJson': 'eae40cc0-5c90-4664-aa85-74f468d1df6d',
        'origem': {
          'idJson': '306eb390-603f-4ff1-a395-815bcc6d075e'
        },
        'destino': {
          'idJson': '15a4b75c-93c5-47bf-86ba-e9c424a9cc04'
        }
      },
      {
        'id': '566ff7fd-a5f5-4f75-ae46-ece92a602025',
        'idJson': 'b813f6c1-9416-49d4-be5f-88e830f19ef3',
        'origem': {
          'idJson': '9050c64b-81f4-478d-85bf-834a46df783c'
        },
        'destino': {
          'idJson': '91738db7-eb21-4281-a075-8beae9eee256'
        }
      },
      {
        'id': '919f77d8-d728-4336-b1b2-051f1faa899e',
        'idJson': '137f1164-e501-4fd2-904d-8e151d151ee7',
        'origem': {
          'idJson': '7264e985-8082-4120-8577-cb83f8b26e90'
        },
        'destino': {
          'idJson': '17763263-95ac-46ef-8d3e-7bbb98f2cdd2'
        }
      },
      {
        'id': '64d2f2f6-c456-4062-8082-1ae6b6b7c9d4',
        'idJson': '55fc54a5-0be6-43ce-88cd-ffcd6243144e',
        'origem': {
          'idJson': '17763263-95ac-46ef-8d3e-7bbb98f2cdd2'
        },
        'destino': {
          'idJson': '71c8973f-87d4-42b4-92bb-49cf5693259a'
        }
      }
    ],
    'transicoes': [
      {
        'id': '9a347c44-af32-4169-af7a-a1e3a0894347',
        'idJson': '44077fec-0719-4a7c-81e9-7ee5eadcdf5d',
        'origem': {
          'idJson': '6b771a42-d8d2-4f90-9fc7-fd82bfcbd8b9'
        },
        'destino': {
          'idJson': '01f5dce2-391a-4930-882d-bf0d656fb17a'
        },
        'tabelaEntreVerdesTransicoes': [
          {
            'idJson': '485dcd4c-04d9-403a-975f-f94702c621f3'
          }
        ],
        'grupoSemaforico': {
          'idJson': '7264e985-8082-4120-8577-cb83f8b26e90'
        },
        'tipo': 'PERDA_DE_PASSAGEM',
        'tempoAtrasoGrupo': '0',
        'atrasoDeGrupo': {
          'idJson': '899db721-6b35-4a5a-a99e-87cf7f4eaffb'
        }
      },
      {
        'id': '9251a328-24bd-475b-af98-93eff016ca5d',
        'idJson': '850fe771-dddf-494d-933a-0dbb7d714dd7',
        'origem': {
          'idJson': 'd3d9c928-61df-4eee-b87c-22c674037d70'
        },
        'destino': {
          'idJson': '6b771a42-d8d2-4f90-9fc7-fd82bfcbd8b9'
        },
        'tabelaEntreVerdesTransicoes': [
          {
            'idJson': 'c6e18c3e-e97f-4522-9a92-0856ff727351'
          }
        ],
        'grupoSemaforico': {
          'idJson': '17763263-95ac-46ef-8d3e-7bbb98f2cdd2'
        },
        'tipo': 'PERDA_DE_PASSAGEM',
        'tempoAtrasoGrupo': '0',
        'atrasoDeGrupo': {
          'idJson': '7d813db2-cf8b-470e-9225-c14d42147725'
        }
      },
      {
        'id': '0037a96b-4b98-416f-ae4b-e7a8aed94ec1',
        'idJson': '6022d71b-55dc-45d4-95c1-6da4b85c2c6e',
        'origem': {
          'idJson': '3b068b57-e93e-415d-88eb-84c5e35ccd3e'
        },
        'destino': {
          'idJson': '5ba381df-3f3d-40a4-bc3c-5b2ff88f1605'
        },
        'tabelaEntreVerdesTransicoes': [
          {
            'idJson': 'bcd773a0-51cb-41c2-a0d4-06700becd2ad'
          }
        ],
        'grupoSemaforico': {
          'idJson': 'aca70ec5-72b3-4878-9c3a-e66ae0ca314c'
        },
        'tipo': 'PERDA_DE_PASSAGEM',
        'tempoAtrasoGrupo': '0',
        'atrasoDeGrupo': {
          'idJson': '0138279b-e059-4e4c-8553-2a7d88ab267a'
        }
      },
      {
        'id': '627d1f7d-ae3f-4c4f-a0f2-2ceeb6100fb7',
        'idJson': '81cd2daa-d5d1-44d2-ad23-5bf0764a0b10',
        'origem': {
          'idJson': '6b771a42-d8d2-4f90-9fc7-fd82bfcbd8b9'
        },
        'destino': {
          'idJson': 'd3d9c928-61df-4eee-b87c-22c674037d70'
        },
        'tabelaEntreVerdesTransicoes': [
          {
            'idJson': 'a1fe6b9b-8403-4d4a-ac4d-f8bc11645428'
          }
        ],
        'grupoSemaforico': {
          'idJson': '7264e985-8082-4120-8577-cb83f8b26e90'
        },
        'tipo': 'PERDA_DE_PASSAGEM',
        'tempoAtrasoGrupo': '0',
        'atrasoDeGrupo': {
          'idJson': '7f3daf53-ccd2-46dc-bc9f-2a9daa9c61eb'
        }
      },
      {
        'id': 'e78dfe47-5b6b-48ea-8aa3-6aad3ecb92b6',
        'idJson': '13244186-7491-4572-aa9c-ecead504a732',
        'origem': {
          'idJson': '83f9f8e0-7308-4e76-a275-643564205996'
        },
        'destino': {
          'idJson': '242d2c3c-1773-4688-8c1e-fde218755bd1'
        },
        'tabelaEntreVerdesTransicoes': [
          {
            'idJson': '6bef2396-4067-45d2-9ada-9c83d9864373'
          }
        ],
        'grupoSemaforico': {
          'idJson': '1ebe4788-d27c-491f-a167-15822aaf7725'
        },
        'tipo': 'PERDA_DE_PASSAGEM',
        'tempoAtrasoGrupo': '0',
        'atrasoDeGrupo': {
          'idJson': 'ec0332a7-ef0a-41b1-9fa2-8450cadc7d13'
        }
      },
      {
        'id': '7c358a08-70f2-40f3-a1da-237a136ed5b2',
        'idJson': '73d91029-9f8e-4e89-8c18-85f876fa41c3',
        'origem': {
          'idJson': 'd3d9c928-61df-4eee-b87c-22c674037d70'
        },
        'destino': {
          'idJson': '01f5dce2-391a-4930-882d-bf0d656fb17a'
        },
        'tabelaEntreVerdesTransicoes': [
          {
            'idJson': '96e8e6f7-7a24-4241-92e3-8218a3459e6a'
          }
        ],
        'grupoSemaforico': {
          'idJson': '17763263-95ac-46ef-8d3e-7bbb98f2cdd2'
        },
        'tipo': 'PERDA_DE_PASSAGEM',
        'tempoAtrasoGrupo': '0',
        'atrasoDeGrupo': {
          'idJson': 'f3e860c9-303b-4d95-b59d-0e9b33c15a57'
        }
      },
      {
        'id': '5cb7f170-40d3-495f-870a-9c4886828c5c',
        'idJson': 'c88d9dbd-c8fa-495e-8244-59bf3993315c',
        'origem': {
          'idJson': '01f5dce2-391a-4930-882d-bf0d656fb17a'
        },
        'destino': {
          'idJson': '6b771a42-d8d2-4f90-9fc7-fd82bfcbd8b9'
        },
        'tabelaEntreVerdesTransicoes': [
          {
            'idJson': '32423729-0fa5-418f-be00-c1672a7e4ed5'
          }
        ],
        'grupoSemaforico': {
          'idJson': '71c8973f-87d4-42b4-92bb-49cf5693259a'
        },
        'tipo': 'PERDA_DE_PASSAGEM',
        'tempoAtrasoGrupo': '0',
        'atrasoDeGrupo': {
          'idJson': '5f8c5a62-d942-4cdc-b1f5-3e4b1a9cff3a'
        }
      },
      {
        'id': '7009bd44-928b-47b0-aab5-4b953c64bf5b',
        'idJson': '7789bdfa-765f-48ef-8bdc-2a84f709291c',
        'origem': {
          'idJson': '48964f57-8d94-4abc-b5cd-8e9458459d5c'
        },
        'destino': {
          'idJson': '83f9f8e0-7308-4e76-a275-643564205996'
        },
        'tabelaEntreVerdesTransicoes': [
          {
            'idJson': '7b6a58d2-ebca-4976-b7d1-b99d286940c1'
          }
        ],
        'grupoSemaforico': {
          'idJson': '91738db7-eb21-4281-a075-8beae9eee256'
        },
        'tipo': 'PERDA_DE_PASSAGEM',
        'tempoAtrasoGrupo': '0',
        'atrasoDeGrupo': {
          'idJson': '7260777e-9a06-4f60-9ce6-434022bc44e9'
        }
      },
      {
        'id': 'd6524cf7-90a3-42a4-97dd-102c318e537e',
        'idJson': '99333939-b2b2-4db9-8448-059f0a635c5d',
        'origem': {
          'idJson': '48964f57-8d94-4abc-b5cd-8e9458459d5c'
        },
        'destino': {
          'idJson': '83f9f8e0-7308-4e76-a275-643564205996'
        },
        'tabelaEntreVerdesTransicoes': [
          {
            'idJson': 'faff3a9a-dfc4-4e18-9df6-5c1a496ea6aa'
          }
        ],
        'grupoSemaforico': {
          'idJson': '306eb390-603f-4ff1-a395-815bcc6d075e'
        },
        'tipo': 'PERDA_DE_PASSAGEM',
        'tempoAtrasoGrupo': '0',
        'atrasoDeGrupo': {
          'idJson': '480d66b0-ee4e-461a-b80a-eac5258283d4'
        }
      },
      {
        'id': '467f7eeb-71d7-4af2-a38e-dc86dceeecb7',
        'idJson': '47a89330-821e-41c2-97bb-1acdca7105ba',
        'origem': {
          'idJson': '01f5dce2-391a-4930-882d-bf0d656fb17a'
        },
        'destino': {
          'idJson': 'd3d9c928-61df-4eee-b87c-22c674037d70'
        },
        'tabelaEntreVerdesTransicoes': [
          {
            'idJson': 'd446d05c-beb3-4e6e-9e58-cdad204728f4'
          }
        ],
        'grupoSemaforico': {
          'idJson': '71c8973f-87d4-42b4-92bb-49cf5693259a'
        },
        'tipo': 'PERDA_DE_PASSAGEM',
        'tempoAtrasoGrupo': '0',
        'atrasoDeGrupo': {
          'idJson': '8dbbc26d-f123-4d7a-8071-537a1004b275'
        }
      },
      {
        'id': '9cb2dbbe-264d-4b56-93db-2a7dc862e10b',
        'idJson': '1b362201-eef3-440c-943f-87f16854fa82',
        'origem': {
          'idJson': '242d2c3c-1773-4688-8c1e-fde218755bd1'
        },
        'destino': {
          'idJson': '83f9f8e0-7308-4e76-a275-643564205996'
        },
        'tabelaEntreVerdesTransicoes': [
          {
            'idJson': 'f3be26fa-a0b7-45f2-846e-eb1d00a42bbf'
          }
        ],
        'grupoSemaforico': {
          'idJson': '9050c64b-81f4-478d-85bf-834a46df783c'
        },
        'tipo': 'PERDA_DE_PASSAGEM',
        'tempoAtrasoGrupo': '0',
        'atrasoDeGrupo': {
          'idJson': 'b1062658-2181-4dc1-b1d0-c1d008e2de6a'
        }
      },
      {
        'id': '291cb9ec-3ec3-4df1-add2-dd4ad90b441d',
        'idJson': 'c712c980-6a6c-48b3-abac-3daca1c1fd40',
        'origem': {
          'idJson': '242d2c3c-1773-4688-8c1e-fde218755bd1'
        },
        'destino': {
          'idJson': '48964f57-8d94-4abc-b5cd-8e9458459d5c'
        },
        'tabelaEntreVerdesTransicoes': [
          {
            'idJson': 'cae0635f-3986-4176-8e1e-5b9cc5fc093e'
          }
        ],
        'grupoSemaforico': {
          'idJson': '9050c64b-81f4-478d-85bf-834a46df783c'
        },
        'tipo': 'PERDA_DE_PASSAGEM',
        'tempoAtrasoGrupo': '0',
        'atrasoDeGrupo': {
          'idJson': 'f4f5da2c-a588-424e-8aaa-a61fa70c7ead'
        }
      },
      {
        'id': '9b4267be-c4cd-42f3-bee1-c4ec9b4cce94',
        'idJson': 'd5a8b3de-df9f-4449-8dd1-55f2a9805080',
        'origem': {
          'idJson': '242d2c3c-1773-4688-8c1e-fde218755bd1'
        },
        'destino': {
          'idJson': '48964f57-8d94-4abc-b5cd-8e9458459d5c'
        },
        'tabelaEntreVerdesTransicoes': [
          {
            'idJson': '63876dc0-2440-40ff-b8c1-78937b9e64ba'
          }
        ],
        'grupoSemaforico': {
          'idJson': '15a4b75c-93c5-47bf-86ba-e9c424a9cc04'
        },
        'tipo': 'PERDA_DE_PASSAGEM',
        'tempoAtrasoGrupo': '5',
        'atrasoDeGrupo': {
          'idJson': '928f8f62-11e2-4f9b-bc98-66ccddba3ac0'
        }
      },
      {
        'id': '95f513b3-8dd9-4471-82da-fcb23c57899a',
        'idJson': 'bae2e050-e047-4321-b971-d480db51de22',
        'origem': {
          'idJson': '5ba381df-3f3d-40a4-bc3c-5b2ff88f1605'
        },
        'destino': {
          'idJson': '3b068b57-e93e-415d-88eb-84c5e35ccd3e'
        },
        'tabelaEntreVerdesTransicoes': [
          {
            'idJson': '648859bd-40ef-467f-8fb0-9c7e2724fefd'
          }
        ],
        'grupoSemaforico': {
          'idJson': 'bfb13da2-8ebf-49f4-8a7d-2c778bc0d0c0'
        },
        'tipo': 'PERDA_DE_PASSAGEM',
        'tempoAtrasoGrupo': '0',
        'atrasoDeGrupo': {
          'idJson': '41cd846b-6044-4472-a312-e2d90da2e4f8'
        }
      }
    ],
    'transicoesComGanhoDePassagem': [
      {
        'id': '9be8755e-de17-4f8e-86c6-dd79898de25f',
        'idJson': '4c814a1a-c0ee-46f1-bdf8-d9e7be2b3526',
        'origem': {
          'idJson': 'd3d9c928-61df-4eee-b87c-22c674037d70'
        },
        'destino': {
          'idJson': '6b771a42-d8d2-4f90-9fc7-fd82bfcbd8b9'
        },
        'tabelaEntreVerdesTransicoes': [],
        'grupoSemaforico': {
          'idJson': '7264e985-8082-4120-8577-cb83f8b26e90'
        },
        'tipo': 'GANHO_DE_PASSAGEM',
        'tempoAtrasoGrupo': '0',
        'atrasoDeGrupo': {
          'idJson': '71b02e0e-a945-46de-a8b4-93ee5bf3079f'
        }
      },
      {
        'id': '3040b42a-a0da-4532-8c25-e1dbac96d6cb',
        'idJson': 'be5768f6-330a-483a-9530-2b61bdc5fe01',
        'origem': {
          'idJson': '6b771a42-d8d2-4f90-9fc7-fd82bfcbd8b9'
        },
        'destino': {
          'idJson': 'd3d9c928-61df-4eee-b87c-22c674037d70'
        },
        'tabelaEntreVerdesTransicoes': [],
        'grupoSemaforico': {
          'idJson': '17763263-95ac-46ef-8d3e-7bbb98f2cdd2'
        },
        'tipo': 'GANHO_DE_PASSAGEM',
        'tempoAtrasoGrupo': '0',
        'atrasoDeGrupo': {
          'idJson': '09723680-9b3c-4f6d-8610-692ce972fbc0'
        }
      },
      {
        'id': '65c5c178-4225-4a5e-902f-9da8e00c49ed',
        'idJson': '9665b14a-0618-4063-b5af-697992c6a341',
        'origem': {
          'idJson': '242d2c3c-1773-4688-8c1e-fde218755bd1'
        },
        'destino': {
          'idJson': '48964f57-8d94-4abc-b5cd-8e9458459d5c'
        },
        'tabelaEntreVerdesTransicoes': [],
        'grupoSemaforico': {
          'idJson': '306eb390-603f-4ff1-a395-815bcc6d075e'
        },
        'tipo': 'GANHO_DE_PASSAGEM',
        'tempoAtrasoGrupo': '0',
        'atrasoDeGrupo': {
          'idJson': 'c73e6ced-2cad-48f4-b91e-5adbc0e17fd9'
        }
      },
      {
        'id': '8faeb1fa-b415-4bfd-985f-adc7d3e31079',
        'idJson': '5f35641b-250c-479e-994b-697478544687',
        'origem': {
          'idJson': '242d2c3c-1773-4688-8c1e-fde218755bd1'
        },
        'destino': {
          'idJson': '48964f57-8d94-4abc-b5cd-8e9458459d5c'
        },
        'tabelaEntreVerdesTransicoes': [],
        'grupoSemaforico': {
          'idJson': '91738db7-eb21-4281-a075-8beae9eee256'
        },
        'tipo': 'GANHO_DE_PASSAGEM',
        'tempoAtrasoGrupo': '0',
        'atrasoDeGrupo': {
          'idJson': '7da877cf-61b5-4ec1-9cca-850c6314b2fd'
        }
      },
      {
        'id': '292615b1-c6dd-459f-b4d5-0881f7c6346f',
        'idJson': 'd2dfb650-ba71-4cc9-a134-15a6ba72d990',
        'origem': {
          'idJson': '242d2c3c-1773-4688-8c1e-fde218755bd1'
        },
        'destino': {
          'idJson': '83f9f8e0-7308-4e76-a275-643564205996'
        },
        'tabelaEntreVerdesTransicoes': [],
        'grupoSemaforico': {
          'idJson': '1ebe4788-d27c-491f-a167-15822aaf7725'
        },
        'tipo': 'GANHO_DE_PASSAGEM',
        'tempoAtrasoGrupo': '0',
        'atrasoDeGrupo': {
          'idJson': '5926d6cc-7417-4e33-8e62-ff6381aa64f7'
        }
      },
      {
        'id': '71115627-bae5-43aa-8c1a-27a82553c939',
        'idJson': 'ee5407e9-fa17-4ee0-b193-613d3968204a',
        'origem': {
          'idJson': '3b068b57-e93e-415d-88eb-84c5e35ccd3e'
        },
        'destino': {
          'idJson': '5ba381df-3f3d-40a4-bc3c-5b2ff88f1605'
        },
        'tabelaEntreVerdesTransicoes': [],
        'grupoSemaforico': {
          'idJson': 'bfb13da2-8ebf-49f4-8a7d-2c778bc0d0c0'
        },
        'tipo': 'GANHO_DE_PASSAGEM',
        'tempoAtrasoGrupo': '0',
        'atrasoDeGrupo': {
          'idJson': 'f22c031a-e324-47e6-9878-9fe1ebbc5a5a'
        }
      },
      {
        'id': '1aa90140-27ca-482c-84a6-6edc186e5bb8',
        'idJson': '3549c7af-a0fa-4e0e-91a8-b071525ba12e',
        'origem': {
          'idJson': '48964f57-8d94-4abc-b5cd-8e9458459d5c'
        },
        'destino': {
          'idJson': '83f9f8e0-7308-4e76-a275-643564205996'
        },
        'tabelaEntreVerdesTransicoes': [],
        'grupoSemaforico': {
          'idJson': '1ebe4788-d27c-491f-a167-15822aaf7725'
        },
        'tipo': 'GANHO_DE_PASSAGEM',
        'tempoAtrasoGrupo': '0',
        'atrasoDeGrupo': {
          'idJson': '1b3323e3-3e81-4777-a60c-3b22854cb0fb'
        }
      },
      {
        'id': '4abfc473-2b1a-42da-841c-1660b04a0b5c',
        'idJson': 'bc71ea05-62e4-4fc0-bfd3-941720da8b76',
        'origem': {
          'idJson': '01f5dce2-391a-4930-882d-bf0d656fb17a'
        },
        'destino': {
          'idJson': 'd3d9c928-61df-4eee-b87c-22c674037d70'
        },
        'tabelaEntreVerdesTransicoes': [],
        'grupoSemaforico': {
          'idJson': '17763263-95ac-46ef-8d3e-7bbb98f2cdd2'
        },
        'tipo': 'GANHO_DE_PASSAGEM',
        'tempoAtrasoGrupo': '0',
        'atrasoDeGrupo': {
          'idJson': '04763187-feb5-4896-8344-2b5e5ae3469c'
        }
      },
      {
        'id': 'de170e0e-16b2-4084-8bdf-6f2b2aeb03cd',
        'idJson': '4a049e7a-89c9-4629-ad5a-63e3d714a11f',
        'origem': {
          'idJson': '6b771a42-d8d2-4f90-9fc7-fd82bfcbd8b9'
        },
        'destino': {
          'idJson': '01f5dce2-391a-4930-882d-bf0d656fb17a'
        },
        'tabelaEntreVerdesTransicoes': [],
        'grupoSemaforico': {
          'idJson': '71c8973f-87d4-42b4-92bb-49cf5693259a'
        },
        'tipo': 'GANHO_DE_PASSAGEM',
        'tempoAtrasoGrupo': '0',
        'atrasoDeGrupo': {
          'idJson': 'a5515725-323a-4e73-9d79-8a698fce7aa6'
        }
      },
      {
        'id': 'cf3d625c-05df-4ea5-8e66-c1c133ad25e4',
        'idJson': '63c98351-d4ac-489c-917f-39bac6595a8b',
        'origem': {
          'idJson': '48964f57-8d94-4abc-b5cd-8e9458459d5c'
        },
        'destino': {
          'idJson': '83f9f8e0-7308-4e76-a275-643564205996'
        },
        'tabelaEntreVerdesTransicoes': [],
        'grupoSemaforico': {
          'idJson': '15a4b75c-93c5-47bf-86ba-e9c424a9cc04'
        },
        'tipo': 'GANHO_DE_PASSAGEM',
        'tempoAtrasoGrupo': '0',
        'atrasoDeGrupo': {
          'idJson': 'dbcc1e62-bb75-4b4c-9dc7-7baa7ed31758'
        }
      },
      {
        'id': '59583532-8118-4005-bbdb-622eea0bba02',
        'idJson': '9156e406-7f65-4b27-a177-c1d89025158b',
        'origem': {
          'idJson': '83f9f8e0-7308-4e76-a275-643564205996'
        },
        'destino': {
          'idJson': '242d2c3c-1773-4688-8c1e-fde218755bd1'
        },
        'tabelaEntreVerdesTransicoes': [],
        'grupoSemaforico': {
          'idJson': '9050c64b-81f4-478d-85bf-834a46df783c'
        },
        'tipo': 'GANHO_DE_PASSAGEM',
        'tempoAtrasoGrupo': '0',
        'atrasoDeGrupo': {
          'idJson': '1e405201-a291-468d-ad7f-9e6604b0ff60'
        }
      },
      {
        'id': 'b244aa42-f340-4982-b6eb-265b769fc6bd',
        'idJson': '4356ad3b-325c-4a98-a4f9-698738be5832',
        'origem': {
          'idJson': '01f5dce2-391a-4930-882d-bf0d656fb17a'
        },
        'destino': {
          'idJson': '6b771a42-d8d2-4f90-9fc7-fd82bfcbd8b9'
        },
        'tabelaEntreVerdesTransicoes': [],
        'grupoSemaforico': {
          'idJson': '7264e985-8082-4120-8577-cb83f8b26e90'
        },
        'tipo': 'GANHO_DE_PASSAGEM',
        'tempoAtrasoGrupo': '0',
        'atrasoDeGrupo': {
          'idJson': 'e834089c-1d54-4ae1-b49f-be3a9fa41eb1'
        }
      },
      {
        'id': '32799afe-1d5f-418a-b802-7a9fb36871b3',
        'idJson': '7385fd02-4fbd-4d3e-a602-e92823fb4b7f',
        'origem': {
          'idJson': 'd3d9c928-61df-4eee-b87c-22c674037d70'
        },
        'destino': {
          'idJson': '01f5dce2-391a-4930-882d-bf0d656fb17a'
        },
        'tabelaEntreVerdesTransicoes': [],
        'grupoSemaforico': {
          'idJson': '71c8973f-87d4-42b4-92bb-49cf5693259a'
        },
        'tipo': 'GANHO_DE_PASSAGEM',
        'tempoAtrasoGrupo': '0',
        'atrasoDeGrupo': {
          'idJson': 'b41d4cba-7575-4750-b397-f983567c0780'
        }
      },
      {
        'id': '8238ebca-268f-406d-88ef-8d9d4c786604',
        'idJson': '03e0529b-b4cc-4f39-bbd2-cd7e368db772',
        'origem': {
          'idJson': '5ba381df-3f3d-40a4-bc3c-5b2ff88f1605'
        },
        'destino': {
          'idJson': '3b068b57-e93e-415d-88eb-84c5e35ccd3e'
        },
        'tabelaEntreVerdesTransicoes': [],
        'grupoSemaforico': {
          'idJson': 'aca70ec5-72b3-4878-9c3a-e66ae0ca314c'
        },
        'tipo': 'GANHO_DE_PASSAGEM',
        'tempoAtrasoGrupo': '0',
        'atrasoDeGrupo': {
          'idJson': '750e3a9b-b063-4d51-9f8c-41711a8eee23'
        }
      }
    ],
    'tabelasEntreVerdes': [
      {
        'id': 'a2550895-6e26-48b8-be5d-04b84e365176',
        'idJson': '2bfc75fa-614b-4d15-892f-7b86e246ae4a',
        'descricao': 'PADRÃO',
        'posicao': 1,
        'grupoSemaforico': {
          'idJson': '7264e985-8082-4120-8577-cb83f8b26e90'
        },
        'tabelaEntreVerdesTransicoes': [
          {
            'idJson': '485dcd4c-04d9-403a-975f-f94702c621f3'
          },
          {
            'idJson': 'a1fe6b9b-8403-4d4a-ac4d-f8bc11645428'
          }
        ]
      },
      {
        'id': 'a89dc9d9-17b7-465d-8328-b4b7f63e4f66',
        'idJson': 'fd884e45-cc3f-4963-b257-c039c47f80d4',
        'descricao': 'PADRÃO',
        'posicao': 1,
        'grupoSemaforico': {
          'idJson': '306eb390-603f-4ff1-a395-815bcc6d075e'
        },
        'tabelaEntreVerdesTransicoes': [
          {
            'idJson': 'faff3a9a-dfc4-4e18-9df6-5c1a496ea6aa'
          }
        ]
      },
      {
        'id': 'aca2502e-c45c-425a-a734-5bac3b606611',
        'idJson': '6be66f7e-eb77-4f31-ab6f-eec4e46ed4d1',
        'descricao': 'PADRÃO',
        'posicao': 1,
        'grupoSemaforico': {
          'idJson': '15a4b75c-93c5-47bf-86ba-e9c424a9cc04'
        },
        'tabelaEntreVerdesTransicoes': [
          {
            'idJson': '63876dc0-2440-40ff-b8c1-78937b9e64ba'
          }
        ]
      },
      {
        'id': '86c9f9d9-87cc-43b0-a9e7-345b8d54f348',
        'idJson': '7db1328d-d570-4ae3-8b91-56bf3835b366',
        'descricao': 'PADRÃO',
        'posicao': 1,
        'grupoSemaforico': {
          'idJson': '1ebe4788-d27c-491f-a167-15822aaf7725'
        },
        'tabelaEntreVerdesTransicoes': [
          {
            'idJson': '6bef2396-4067-45d2-9ada-9c83d9864373'
          }
        ]
      },
      {
        'id': '6c2c3171-c964-493d-99b3-c7cfbdee69ff',
        'idJson': '92a17459-ca97-4c8e-9b72-7fdcab877a62',
        'descricao': 'PADRÃO',
        'posicao': 1,
        'grupoSemaforico': {
          'idJson': '17763263-95ac-46ef-8d3e-7bbb98f2cdd2'
        },
        'tabelaEntreVerdesTransicoes': [
          {
            'idJson': '96e8e6f7-7a24-4241-92e3-8218a3459e6a'
          },
          {
            'idJson': 'c6e18c3e-e97f-4522-9a92-0856ff727351'
          }
        ]
      },
      {
        'id': '9858586a-58e6-4b31-a074-2ccbbc73689c',
        'idJson': '16f1dd3e-1c96-466f-bed6-40660481fd09',
        'descricao': 'PADRÃO',
        'posicao': 1,
        'grupoSemaforico': {
          'idJson': '9050c64b-81f4-478d-85bf-834a46df783c'
        },
        'tabelaEntreVerdesTransicoes': [
          {
            'idJson': 'f3be26fa-a0b7-45f2-846e-eb1d00a42bbf'
          },
          {
            'idJson': 'cae0635f-3986-4176-8e1e-5b9cc5fc093e'
          }
        ]
      },
      {
        'id': 'e8ea0fee-8a74-4681-b30a-9f6466c8686d',
        'idJson': '7e29b911-52e3-4db9-9af0-45a4d45f03a6',
        'descricao': 'PADRÃO',
        'posicao': 1,
        'grupoSemaforico': {
          'idJson': '91738db7-eb21-4281-a075-8beae9eee256'
        },
        'tabelaEntreVerdesTransicoes': [
          {
            'idJson': '7b6a58d2-ebca-4976-b7d1-b99d286940c1'
          }
        ]
      },
      {
        'id': '18653ca1-3fde-45af-983e-47978ab5e7e9',
        'idJson': 'c73f738f-4ee4-4757-888c-ec2195437e7a',
        'descricao': 'PADRÃO',
        'posicao': 1,
        'grupoSemaforico': {
          'idJson': 'aca70ec5-72b3-4878-9c3a-e66ae0ca314c'
        },
        'tabelaEntreVerdesTransicoes': [
          {
            'idJson': 'bcd773a0-51cb-41c2-a0d4-06700becd2ad'
          }
        ]
      },
      {
        'id': 'f8e92226-3163-4c7f-8dfe-e62c996521f2',
        'idJson': '4b9a4e03-d9e1-45e7-ac6f-f07a5527bfc9',
        'descricao': 'PADRÃO',
        'posicao': 1,
        'grupoSemaforico': {
          'idJson': 'bfb13da2-8ebf-49f4-8a7d-2c778bc0d0c0'
        },
        'tabelaEntreVerdesTransicoes': [
          {
            'idJson': '648859bd-40ef-467f-8fb0-9c7e2724fefd'
          }
        ]
      },
      {
        'id': '626b2dc0-bf1b-4a73-b15b-e823becec579',
        'idJson': '65f898a8-a5ec-494c-9329-dda4aa2a0f3c',
        'descricao': 'PADRÃO',
        'posicao': 1,
        'grupoSemaforico': {
          'idJson': '71c8973f-87d4-42b4-92bb-49cf5693259a'
        },
        'tabelaEntreVerdesTransicoes': [
          {
            'idJson': '32423729-0fa5-418f-be00-c1672a7e4ed5'
          },
          {
            'idJson': 'd446d05c-beb3-4e6e-9e58-cdad204728f4'
          }
        ]
      }
    ],
    'tabelasEntreVerdesTransicoes': [
      {
        'id': '709298ab-bab0-46e3-8cde-a25b9950557d',
        'idJson': 'a1fe6b9b-8403-4d4a-ac4d-f8bc11645428',
        'tempoAmarelo': '3',
        'tempoVermelhoLimpeza': '2',
        'tempoAtrasoGrupo': '0',
        'tabelaEntreVerdes': {
          'idJson': '2bfc75fa-614b-4d15-892f-7b86e246ae4a'
        },
        'transicao': {
          'idJson': '81cd2daa-d5d1-44d2-ad23-5bf0764a0b10'
        }
      },
      {
        'id': 'b930b95e-5af4-4daa-ba5f-4fdcba70f471',
        'idJson': '648859bd-40ef-467f-8fb0-9c7e2724fefd',
        'tempoAmarelo': '3',
        'tempoVermelhoLimpeza': '2',
        'tempoAtrasoGrupo': '0',
        'tabelaEntreVerdes': {
          'idJson': '4b9a4e03-d9e1-45e7-ac6f-f07a5527bfc9'
        },
        'transicao': {
          'idJson': 'bae2e050-e047-4321-b971-d480db51de22'
        }
      },
      {
        'id': 'fea05d70-6dd2-465f-a799-f2a370b9f4d6',
        'idJson': 'c6e18c3e-e97f-4522-9a92-0856ff727351',
        'tempoAmarelo': '3',
        'tempoVermelhoLimpeza': '2',
        'tempoAtrasoGrupo': '0',
        'tabelaEntreVerdes': {
          'idJson': '92a17459-ca97-4c8e-9b72-7fdcab877a62'
        },
        'transicao': {
          'idJson': '850fe771-dddf-494d-933a-0dbb7d714dd7'
        }
      },
      {
        'id': '05fcd0b6-ab81-435c-aea6-b6be19c67987',
        'idJson': '485dcd4c-04d9-403a-975f-f94702c621f3',
        'tempoAmarelo': '3',
        'tempoVermelhoLimpeza': '2',
        'tempoAtrasoGrupo': '0',
        'tabelaEntreVerdes': {
          'idJson': '2bfc75fa-614b-4d15-892f-7b86e246ae4a'
        },
        'transicao': {
          'idJson': '44077fec-0719-4a7c-81e9-7ee5eadcdf5d'
        }
      },
      {
        'id': 'cc089fa4-83f8-40df-a427-165b41fca71e',
        'idJson': 'bcd773a0-51cb-41c2-a0d4-06700becd2ad',
        'tempoAmarelo': '3',
        'tempoVermelhoLimpeza': '2',
        'tempoAtrasoGrupo': '0',
        'tabelaEntreVerdes': {
          'idJson': 'c73f738f-4ee4-4757-888c-ec2195437e7a'
        },
        'transicao': {
          'idJson': '6022d71b-55dc-45d4-95c1-6da4b85c2c6e'
        }
      },
      {
        'id': 'f67e9efb-0325-4500-b8ae-af37509890eb',
        'idJson': 'd446d05c-beb3-4e6e-9e58-cdad204728f4',
        'tempoAmarelo': '3',
        'tempoVermelhoLimpeza': '2',
        'tempoAtrasoGrupo': '0',
        'tabelaEntreVerdes': {
          'idJson': '65f898a8-a5ec-494c-9329-dda4aa2a0f3c'
        },
        'transicao': {
          'idJson': '47a89330-821e-41c2-97bb-1acdca7105ba'
        }
      },
      {
        'id': 'a5140f18-a117-4e8e-b6d8-479e62c59afe',
        'idJson': '6bef2396-4067-45d2-9ada-9c83d9864373',
        'tempoAmarelo': '3',
        'tempoVermelhoLimpeza': '2',
        'tempoAtrasoGrupo': '0',
        'tabelaEntreVerdes': {
          'idJson': '7db1328d-d570-4ae3-8b91-56bf3835b366'
        },
        'transicao': {
          'idJson': '13244186-7491-4572-aa9c-ecead504a732'
        }
      },
      {
        'id': 'f4614c1a-4790-4614-b1af-109d11473348',
        'idJson': '32423729-0fa5-418f-be00-c1672a7e4ed5',
        'tempoAmarelo': '3',
        'tempoVermelhoLimpeza': '2',
        'tempoAtrasoGrupo': '0',
        'tabelaEntreVerdes': {
          'idJson': '65f898a8-a5ec-494c-9329-dda4aa2a0f3c'
        },
        'transicao': {
          'idJson': 'c88d9dbd-c8fa-495e-8244-59bf3993315c'
        }
      },
      {
        'id': '86b3cd17-27c1-4735-945f-a12823b12f35',
        'idJson': 'cae0635f-3986-4176-8e1e-5b9cc5fc093e',
        'tempoAmarelo': '3',
        'tempoVermelhoLimpeza': '2',
        'tempoAtrasoGrupo': '0',
        'tabelaEntreVerdes': {
          'idJson': '16f1dd3e-1c96-466f-bed6-40660481fd09'
        },
        'transicao': {
          'idJson': 'c712c980-6a6c-48b3-abac-3daca1c1fd40'
        }
      },
      {
        'id': '16fcb198-2ba5-46bc-b67e-863e2b7a65a4',
        'idJson': '63876dc0-2440-40ff-b8c1-78937b9e64ba',
        'tempoAmarelo': '3',
        'tempoVermelhoLimpeza': '2',
        'tempoAtrasoGrupo': '5',
        'tabelaEntreVerdes': {
          'idJson': '6be66f7e-eb77-4f31-ab6f-eec4e46ed4d1'
        },
        'transicao': {
          'idJson': 'd5a8b3de-df9f-4449-8dd1-55f2a9805080'
        }
      },
      {
        'id': 'e23640e4-6a9b-4299-8b57-a4d3368698a9',
        'idJson': 'faff3a9a-dfc4-4e18-9df6-5c1a496ea6aa',
        'tempoVermelhoIntermitente': '9',
        'tempoVermelhoLimpeza': '1',
        'tempoAtrasoGrupo': '0',
        'tabelaEntreVerdes': {
          'idJson': 'fd884e45-cc3f-4963-b257-c039c47f80d4'
        },
        'transicao': {
          'idJson': '99333939-b2b2-4db9-8448-059f0a635c5d'
        }
      },
      {
        'id': '24bc4a5f-0eb8-4e00-a633-09f929367e3e',
        'idJson': 'f3be26fa-a0b7-45f2-846e-eb1d00a42bbf',
        'tempoAmarelo': '3',
        'tempoVermelhoLimpeza': '2',
        'tempoAtrasoGrupo': '0',
        'tabelaEntreVerdes': {
          'idJson': '16f1dd3e-1c96-466f-bed6-40660481fd09'
        },
        'transicao': {
          'idJson': '1b362201-eef3-440c-943f-87f16854fa82'
        }
      },
      {
        'id': '2991fc68-d198-4764-87ad-b224385b04b9',
        'idJson': '96e8e6f7-7a24-4241-92e3-8218a3459e6a',
        'tempoAmarelo': '3',
        'tempoVermelhoLimpeza': '2',
        'tempoAtrasoGrupo': '0',
        'tabelaEntreVerdes': {
          'idJson': '92a17459-ca97-4c8e-9b72-7fdcab877a62'
        },
        'transicao': {
          'idJson': '73d91029-9f8e-4e89-8c18-85f876fa41c3'
        }
      },
      {
        'id': 'ac9c91e6-d8c8-4f99-8dd7-251ad26682f3',
        'idJson': '7b6a58d2-ebca-4976-b7d1-b99d286940c1',
        'tempoAmarelo': '3',
        'tempoVermelhoLimpeza': '2',
        'tempoAtrasoGrupo': '0',
        'tabelaEntreVerdes': {
          'idJson': '7e29b911-52e3-4db9-9af0-45a4d45f03a6'
        },
        'transicao': {
          'idJson': '7789bdfa-765f-48ef-8bdc-2a84f709291c'
        }
      }
    ],
    'planos': [
      {
        'id': 'd740afbe-1fb5-4ba2-ba9d-163cf9ef89de',
        'idJson': '29ba9ea9-d15c-46a5-b017-f507bf9c216d',
        'posicao': 2,
        'descricao': 'PLANO 2',
        'tempoCiclo': 100,
        'defasagem': 10,
        'posicaoTabelaEntreVerde': 1,
        'modoOperacao': 'TEMPO_FIXO_COORDENADO',
        'dataCriacao': '03/11/2016 11:56:49',
        'dataAtualizacao': '07/11/2016 11:43:36',
        'anel': {
          'idJson': 'ea5465fe-c93b-464b-8c96-d47e2036a3d1'
        },
        'versaoPlano': {
          'idJson': '67f12a26-3839-4c80-a712-778a7f76bde6'
        },
        'estagiosPlanos': [
          {
            'idJson': 'cbbf7cb7-5e86-4492-bad5-7b611449c549'
          },
          {
            'idJson': 'd403afeb-3443-4f3f-8997-da4f36d719d4'
          }
        ],
        'gruposSemaforicosPlanos': [
          {
            'idJson': '8bbbf90f-395d-47dd-baaa-77199eafde07'
          },
          {
            'idJson': '84fe0d6e-6230-47b0-a6a5-ce2d7e6145fd'
          }
        ]
      },
      {
        'id': '8050eae7-1cb5-4928-b543-34a5eb11b309',
        'idJson': 'acbdfff1-2d2f-4582-bed3-639513470da9',
        'posicao': 1,
        'descricao': 'PLANO 1',
        'tempoCiclo': 100,
        'defasagem': 0,
        'posicaoTabelaEntreVerde': 1,
        'modoOperacao': 'TEMPO_FIXO_ISOLADO',
        'dataCriacao': '03/11/2016 11:52:52',
        'dataAtualizacao': '07/11/2016 11:43:34',
        'anel': {
          'idJson': 'e7457fca-897e-4bd9-a380-050c4c6c58b8'
        },
        'versaoPlano': {
          'idJson': 'd3ea90e7-ceda-4100-bf9a-e845a0e2af38'
        },
        'estagiosPlanos': [
          {
            'idJson': '15cefbcb-cd7b-4888-b796-5d54f50a5440'
          },
          {
            'idJson': '7526a62d-dd06-48df-9983-dbe1178af6c5'
          },
          {
            'idJson': 'afdde69e-f28e-4df6-9a3f-94607572cd68'
          }
        ],
        'gruposSemaforicosPlanos': [
          {
            'idJson': 'd480451c-1752-4509-b452-ab982e692d75'
          },
          {
            'idJson': '8a43e1b1-e31d-4903-9c56-7e876d3651f1'
          },
          {
            'idJson': '37e0ac36-0e97-42fa-96f9-1437ce55f179'
          },
          {
            'idJson': '7bf6057f-da51-40e5-ac92-71877ff9741e'
          },
          {
            'idJson': '1587873b-0c8c-4fb9-8aa4-7e0fd094d244'
          }
        ]
      },
      {
        'id': '848fa7db-e4a8-4769-b0e3-5ada2771013e',
        'idJson': '24510147-ba9f-4a61-815d-63565ffca56f',
        'posicao': 1,
        'descricao': 'PLANO 1',
        'tempoCiclo': 100,
        'defasagem': 0,
        'posicaoTabelaEntreVerde': 1,
        'modoOperacao': 'TEMPO_FIXO_ISOLADO',
        'dataCriacao': '03/11/2016 11:52:52',
        'dataAtualizacao': '07/11/2016 11:43:36',
        'anel': {
          'idJson': 'ea5465fe-c93b-464b-8c96-d47e2036a3d1'
        },
        'versaoPlano': {
          'idJson': '67f12a26-3839-4c80-a712-778a7f76bde6'
        },
        'estagiosPlanos': [
          {
            'idJson': '591aafe0-9c60-4a27-a148-5fab31acb393'
          },
          {
            'idJson': '4c82fdb3-3caf-451f-b6b5-8a6d5a0120ef'
          }
        ],
        'gruposSemaforicosPlanos': [
          {
            'idJson': '1fe3dd9d-234d-4e06-a7d6-964fb42de277'
          },
          {
            'idJson': '85791665-ac7d-4b99-bbb8-6c2eaa0ae434'
          }
        ]
      },
      {
        'id': 'b674a6e7-7f73-4a72-8c13-f80833fa0e7f',
        'idJson': '9d937706-5b1a-4007-b9e4-a530c13b1409',
        'posicao': 2,
        'descricao': 'PLANO 2',
        'tempoCiclo': 100,
        'defasagem': 0,
        'posicaoTabelaEntreVerde': 1,
        'modoOperacao': 'TEMPO_FIXO_COORDENADO',
        'dataCriacao': '03/11/2016 11:56:49',
        'dataAtualizacao': '07/11/2016 11:43:34',
        'anel': {
          'idJson': 'e7457fca-897e-4bd9-a380-050c4c6c58b8'
        },
        'versaoPlano': {
          'idJson': 'd3ea90e7-ceda-4100-bf9a-e845a0e2af38'
        },
        'estagiosPlanos': [
          {
            'idJson': '7d6d243a-2459-49d8-a7a9-f2f95331ec38'
          },
          {
            'idJson': 'a1e13b0c-81bd-4a60-be1f-6a9b3edd9094'
          },
          {
            'idJson': '4b55b0a2-181b-48ba-a9a0-886a5ff449b4'
          }
        ],
        'gruposSemaforicosPlanos': [
          {
            'idJson': '04d41226-3251-4cab-9809-230292e54361'
          },
          {
            'idJson': 'ca5e93fa-b2e2-447a-8849-10f391803148'
          },
          {
            'idJson': '9a8006c3-4014-4003-8738-7163fa2943d9'
          },
          {
            'idJson': 'd4b338c3-1268-4104-9d4f-23a500ca9b4f'
          },
          {
            'idJson': '5268a241-af92-4cf0-acd1-0635892edc1f'
          }
        ]
      },
      {
        'id': 'cdb34573-f232-4a85-84b3-46439e4fbd15',
        'idJson': 'a867bcce-2e4a-44fc-b88d-5a9579b74857',
        'posicao': 1,
        'descricao': 'PLANO 1',
        'tempoCiclo': 100,
        'defasagem': 0,
        'posicaoTabelaEntreVerde': 1,
        'modoOperacao': 'TEMPO_FIXO_ISOLADO',
        'dataCriacao': '03/11/2016 11:52:52',
        'dataAtualizacao': '07/11/2016 11:43:36',
        'anel': {
          'idJson': 'f30c1621-ec51-47ed-bff1-a9758512d865'
        },
        'versaoPlano': {
          'idJson': 'ac4757a0-87bc-4b3b-a391-1e3ca8f9571b'
        },
        'estagiosPlanos': [
          {
            'idJson': 'ab5ba2e3-421d-46ff-a522-6e748b7dbf83'
          },
          {
            'idJson': '85a656df-0751-4e7c-b805-c52e2fd8c9c5'
          },
          {
            'idJson': '0f8cb387-d20f-4fec-b81d-1d2ed320f36d'
          }
        ],
        'gruposSemaforicosPlanos': [
          {
            'idJson': 'da158461-1a58-4245-a41d-f734e99b545d'
          },
          {
            'idJson': 'f2ad334b-a4a7-4c39-ae4c-5edcb36c715c'
          },
          {
            'idJson': 'dfd8d3a9-1639-42e3-8b4c-1bf8dce756b3'
          }
        ]
      },
      {
        'id': '58bd7967-2791-4e1a-b85d-aec210154e48',
        'idJson': '10d40af6-212e-4dc4-879a-81575f16e11f',
        'posicao': 2,
        'descricao': 'PLANO 2',
        'tempoCiclo': 100,
        'defasagem': 20,
        'posicaoTabelaEntreVerde': 1,
        'modoOperacao': 'TEMPO_FIXO_COORDENADO',
        'dataCriacao': '03/11/2016 11:56:49',
        'dataAtualizacao': '07/11/2016 11:43:36',
        'anel': {
          'idJson': 'f30c1621-ec51-47ed-bff1-a9758512d865'
        },
        'versaoPlano': {
          'idJson': 'ac4757a0-87bc-4b3b-a391-1e3ca8f9571b'
        },
        'estagiosPlanos': [
          {
            'idJson': 'f6800131-d61f-4e66-a6f2-2732e2af2cad'
          },
          {
            'idJson': '6822afc1-736e-4dda-96c0-f5d9e90bd96e'
          },
          {
            'idJson': '1f24064e-dab2-49e0-835a-6c9a816b8924'
          }
        ],
        'gruposSemaforicosPlanos': [
          {
            'idJson': 'f637d495-e2fc-4e0d-8698-b0ad296a58ec'
          },
          {
            'idJson': '6fbd1c2b-df48-4979-9f28-ceb16c11bb05'
          },
          {
            'idJson': '30a76fb3-375e-4c68-95c2-d5fe3b97b6af'
          }
        ]
      }
    ],
    'gruposSemaforicosPlanos': [
      {
        'id': 'cb34fd26-8614-45ab-a866-b4bd09cff660',
        'idJson': '84fe0d6e-6230-47b0-a6a5-ce2d7e6145fd',
        'plano': {
          'idJson': '29ba9ea9-d15c-46a5-b017-f507bf9c216d'
        },
        'grupoSemaforico': {
          'idJson': 'bfb13da2-8ebf-49f4-8a7d-2c778bc0d0c0'
        },
        'ativado': true
      },
      {
        'id': '2416a7f0-324f-4f6a-9ad9-5333895197a9',
        'idJson': 'd480451c-1752-4509-b452-ab982e692d75',
        'plano': {
          'idJson': 'acbdfff1-2d2f-4582-bed3-639513470da9'
        },
        'grupoSemaforico': {
          'idJson': '306eb390-603f-4ff1-a395-815bcc6d075e'
        },
        'ativado': true
      },
      {
        'id': '46785196-0ee1-4d95-9e0e-ee1280bcfb77',
        'idJson': '6fbd1c2b-df48-4979-9f28-ceb16c11bb05',
        'plano': {
          'idJson': '10d40af6-212e-4dc4-879a-81575f16e11f'
        },
        'grupoSemaforico': {
          'idJson': '17763263-95ac-46ef-8d3e-7bbb98f2cdd2'
        },
        'ativado': true
      },
      {
        'id': '3d25f5ad-1e31-4a54-ba68-3fee37167dfe',
        'idJson': '8bbbf90f-395d-47dd-baaa-77199eafde07',
        'plano': {
          'idJson': '29ba9ea9-d15c-46a5-b017-f507bf9c216d'
        },
        'grupoSemaforico': {
          'idJson': 'aca70ec5-72b3-4878-9c3a-e66ae0ca314c'
        },
        'ativado': true
      },
      {
        'id': 'fb62a84e-ac62-4f48-b88e-3e724e295755',
        'idJson': '1587873b-0c8c-4fb9-8aa4-7e0fd094d244',
        'plano': {
          'idJson': 'acbdfff1-2d2f-4582-bed3-639513470da9'
        },
        'grupoSemaforico': {
          'idJson': '9050c64b-81f4-478d-85bf-834a46df783c'
        },
        'ativado': true
      },
      {
        'id': 'd4227ab8-1929-4005-acd4-4c070cf3e98f',
        'idJson': '85791665-ac7d-4b99-bbb8-6c2eaa0ae434',
        'plano': {
          'idJson': '24510147-ba9f-4a61-815d-63565ffca56f'
        },
        'grupoSemaforico': {
          'idJson': 'aca70ec5-72b3-4878-9c3a-e66ae0ca314c'
        },
        'ativado': true
      },
      {
        'id': '79768244-2935-4f95-9f86-0ab489ab79a7',
        'idJson': 'ca5e93fa-b2e2-447a-8849-10f391803148',
        'plano': {
          'idJson': '9d937706-5b1a-4007-b9e4-a530c13b1409'
        },
        'grupoSemaforico': {
          'idJson': '91738db7-eb21-4281-a075-8beae9eee256'
        },
        'ativado': true
      },
      {
        'id': 'f2ea225e-bd88-46da-abab-1db0a9ecba52',
        'idJson': '5268a241-af92-4cf0-acd1-0635892edc1f',
        'plano': {
          'idJson': '9d937706-5b1a-4007-b9e4-a530c13b1409'
        },
        'grupoSemaforico': {
          'idJson': '1ebe4788-d27c-491f-a167-15822aaf7725'
        },
        'ativado': true
      },
      {
        'id': 'bb4f13a1-00ba-499a-984d-088e38c5d094',
        'idJson': '1fe3dd9d-234d-4e06-a7d6-964fb42de277',
        'plano': {
          'idJson': '24510147-ba9f-4a61-815d-63565ffca56f'
        },
        'grupoSemaforico': {
          'idJson': 'bfb13da2-8ebf-49f4-8a7d-2c778bc0d0c0'
        },
        'ativado': true
      },
      {
        'id': '2059bd03-ddd9-4146-ab87-5351100a6139',
        'idJson': '04d41226-3251-4cab-9809-230292e54361',
        'plano': {
          'idJson': '9d937706-5b1a-4007-b9e4-a530c13b1409'
        },
        'grupoSemaforico': {
          'idJson': '306eb390-603f-4ff1-a395-815bcc6d075e'
        },
        'ativado': true
      },
      {
        'id': 'ae45fcb1-d767-4c10-a659-427cd0f4e549',
        'idJson': '8a43e1b1-e31d-4903-9c56-7e876d3651f1',
        'plano': {
          'idJson': 'acbdfff1-2d2f-4582-bed3-639513470da9'
        },
        'grupoSemaforico': {
          'idJson': '15a4b75c-93c5-47bf-86ba-e9c424a9cc04'
        },
        'ativado': true
      },
      {
        'id': '0f7f5713-4398-47e1-a4aa-b13fb9efc7e7',
        'idJson': 'f2ad334b-a4a7-4c39-ae4c-5edcb36c715c',
        'plano': {
          'idJson': 'a867bcce-2e4a-44fc-b88d-5a9579b74857'
        },
        'grupoSemaforico': {
          'idJson': '7264e985-8082-4120-8577-cb83f8b26e90'
        },
        'ativado': true
      },
      {
        'id': 'f71637b4-b0fa-47de-9ca5-188f62102705',
        'idJson': '7bf6057f-da51-40e5-ac92-71877ff9741e',
        'plano': {
          'idJson': 'acbdfff1-2d2f-4582-bed3-639513470da9'
        },
        'grupoSemaforico': {
          'idJson': '1ebe4788-d27c-491f-a167-15822aaf7725'
        },
        'ativado': true
      },
      {
        'id': '0b1db0fe-c35f-404e-b646-ccf18dbf2d49',
        'idJson': 'da158461-1a58-4245-a41d-f734e99b545d',
        'plano': {
          'idJson': 'a867bcce-2e4a-44fc-b88d-5a9579b74857'
        },
        'grupoSemaforico': {
          'idJson': '71c8973f-87d4-42b4-92bb-49cf5693259a'
        },
        'ativado': true
      },
      {
        'id': '64d49176-0cb4-4f14-a551-c7134fb8e674',
        'idJson': 'dfd8d3a9-1639-42e3-8b4c-1bf8dce756b3',
        'plano': {
          'idJson': 'a867bcce-2e4a-44fc-b88d-5a9579b74857'
        },
        'grupoSemaforico': {
          'idJson': '17763263-95ac-46ef-8d3e-7bbb98f2cdd2'
        },
        'ativado': true
      },
      {
        'id': 'e96eb836-6d7d-437e-8ed1-754164a1367e',
        'idJson': '37e0ac36-0e97-42fa-96f9-1437ce55f179',
        'plano': {
          'idJson': 'acbdfff1-2d2f-4582-bed3-639513470da9'
        },
        'grupoSemaforico': {
          'idJson': '91738db7-eb21-4281-a075-8beae9eee256'
        },
        'ativado': true
      },
      {
        'id': 'd7920391-fd86-4cdd-9e30-0b6b6eb24e73',
        'idJson': '9a8006c3-4014-4003-8738-7163fa2943d9',
        'plano': {
          'idJson': '9d937706-5b1a-4007-b9e4-a530c13b1409'
        },
        'grupoSemaforico': {
          'idJson': '9050c64b-81f4-478d-85bf-834a46df783c'
        },
        'ativado': true
      },
      {
        'id': '5dab68d4-8709-4c4e-a7d0-736cdb3c7e12',
        'idJson': '30a76fb3-375e-4c68-95c2-d5fe3b97b6af',
        'plano': {
          'idJson': '10d40af6-212e-4dc4-879a-81575f16e11f'
        },
        'grupoSemaforico': {
          'idJson': '7264e985-8082-4120-8577-cb83f8b26e90'
        },
        'ativado': true
      },
      {
        'id': 'e649ad63-d094-4276-97fa-6628c86bd107',
        'idJson': 'd4b338c3-1268-4104-9d4f-23a500ca9b4f',
        'plano': {
          'idJson': '9d937706-5b1a-4007-b9e4-a530c13b1409'
        },
        'grupoSemaforico': {
          'idJson': '15a4b75c-93c5-47bf-86ba-e9c424a9cc04'
        },
        'ativado': true
      },
      {
        'id': '2faa2537-fa2c-4790-bb0e-fc4aa34b1977',
        'idJson': 'f637d495-e2fc-4e0d-8698-b0ad296a58ec',
        'plano': {
          'idJson': '10d40af6-212e-4dc4-879a-81575f16e11f'
        },
        'grupoSemaforico': {
          'idJson': '71c8973f-87d4-42b4-92bb-49cf5693259a'
        },
        'ativado': true
      }
    ],
    'estagiosPlanos': [
      {
        'id': '1780a729-3c54-4dd1-8ec7-c1847aeec1f6',
        'idJson': '591aafe0-9c60-4a27-a148-5fab31acb393',
        'posicao': 1,
        'tempoVerde': 70,
        'dispensavel': false,
        'plano': {
          'idJson': '24510147-ba9f-4a61-815d-63565ffca56f'
        },
        'estagio': {
          'idJson': '5ba381df-3f3d-40a4-bc3c-5b2ff88f1605'
        }
      },
      {
        'id': '6efa63a4-cbae-4d41-ac17-b85d2f4128f6',
        'idJson': 'd403afeb-3443-4f3f-8997-da4f36d719d4',
        'posicao': 1,
        'tempoVerde': 70,
        'tempoVerdeMinimo': 0,
        'tempoVerdeMaximo': 0,
        'tempoVerdeIntermediario': 0,
        'tempoExtensaoVerde': 0.0,
        'dispensavel': false,
        'plano': {
          'idJson': '29ba9ea9-d15c-46a5-b017-f507bf9c216d'
        },
        'estagio': {
          'idJson': '5ba381df-3f3d-40a4-bc3c-5b2ff88f1605'
        }
      },
      {
        'id': '63538482-ec0f-4b7a-b049-70b30bd9f0ad',
        'idJson': '7526a62d-dd06-48df-9983-dbe1178af6c5',
        'posicao': 1,
        'tempoVerde': 40,
        'dispensavel': false,
        'plano': {
          'idJson': 'acbdfff1-2d2f-4582-bed3-639513470da9'
        },
        'estagio': {
          'idJson': '242d2c3c-1773-4688-8c1e-fde218755bd1'
        }
      },
      {
        'id': '7d644561-6683-4974-8554-19dd7d4e3737',
        'idJson': '4c82fdb3-3caf-451f-b6b5-8a6d5a0120ef',
        'posicao': 2,
        'tempoVerde': 20,
        'dispensavel': false,
        'plano': {
          'idJson': '24510147-ba9f-4a61-815d-63565ffca56f'
        },
        'estagio': {
          'idJson': '3b068b57-e93e-415d-88eb-84c5e35ccd3e'
        }
      },
      {
        'id': 'c0bf97f5-b487-4f06-8a75-9b65d062e14e',
        'idJson': '6822afc1-736e-4dda-96c0-f5d9e90bd96e',
        'posicao': 1,
        'tempoVerde': 45,
        'tempoVerdeMinimo': 0,
        'tempoVerdeMaximo': 0,
        'tempoVerdeIntermediario': 0,
        'tempoExtensaoVerde': 0.0,
        'dispensavel': false,
        'plano': {
          'idJson': '10d40af6-212e-4dc4-879a-81575f16e11f'
        },
        'estagio': {
          'idJson': 'd3d9c928-61df-4eee-b87c-22c674037d70'
        }
      },
      {
        'id': 'd7e2540a-bf8e-499c-93be-ff01b88a4b32',
        'idJson': 'afdde69e-f28e-4df6-9a3f-94607572cd68',
        'posicao': 3,
        'tempoVerde': 20,
        'dispensavel': false,
        'plano': {
          'idJson': 'acbdfff1-2d2f-4582-bed3-639513470da9'
        },
        'estagio': {
          'idJson': '83f9f8e0-7308-4e76-a275-643564205996'
        }
      },
      {
        'id': '3d070349-2372-48a5-9ae0-400cff2d6f51',
        'idJson': 'f6800131-d61f-4e66-a6f2-2732e2af2cad',
        'posicao': 2,
        'tempoVerde': 20,
        'tempoVerdeMinimo': 0,
        'tempoVerdeMaximo': 0,
        'tempoVerdeIntermediario': 0,
        'tempoExtensaoVerde': 0.0,
        'dispensavel': false,
        'plano': {
          'idJson': '10d40af6-212e-4dc4-879a-81575f16e11f'
        },
        'estagio': {
          'idJson': '6b771a42-d8d2-4f90-9fc7-fd82bfcbd8b9'
        }
      },
      {
        'id': 'd22513dc-a283-49f7-aef4-c5b8f1ca7d9f',
        'idJson': '4b55b0a2-181b-48ba-a9a0-886a5ff449b4',
        'posicao': 3,
        'tempoVerde': 20,
        'tempoVerdeMinimo': 0,
        'tempoVerdeMaximo': 0,
        'tempoVerdeIntermediario': 0,
        'tempoExtensaoVerde': 0.0,
        'dispensavel': false,
        'plano': {
          'idJson': '9d937706-5b1a-4007-b9e4-a530c13b1409'
        },
        'estagio': {
          'idJson': '83f9f8e0-7308-4e76-a275-643564205996'
        }
      },
      {
        'id': 'd556e7ed-6a66-4ade-989b-fdaef74ef507',
        'idJson': '1f24064e-dab2-49e0-835a-6c9a816b8924',
        'posicao': 3,
        'tempoVerde': 20,
        'tempoVerdeMinimo': 0,
        'tempoVerdeMaximo': 0,
        'tempoVerdeIntermediario': 0,
        'tempoExtensaoVerde': 0.0,
        'dispensavel': false,
        'plano': {
          'idJson': '10d40af6-212e-4dc4-879a-81575f16e11f'
        },
        'estagio': {
          'idJson': '01f5dce2-391a-4930-882d-bf0d656fb17a'
        }
      },
      {
        'id': 'b83b9e56-64fa-4736-b29f-1dda6cac28a3',
        'idJson': 'a1e13b0c-81bd-4a60-be1f-6a9b3edd9094',
        'posicao': 1,
        'tempoVerde': 40,
        'tempoVerdeMinimo': 0,
        'tempoVerdeMaximo': 0,
        'tempoVerdeIntermediario': 0,
        'tempoExtensaoVerde': 0.0,
        'dispensavel': false,
        'plano': {
          'idJson': '9d937706-5b1a-4007-b9e4-a530c13b1409'
        },
        'estagio': {
          'idJson': '242d2c3c-1773-4688-8c1e-fde218755bd1'
        }
      },
      {
        'id': '23e23cc8-c51e-4e7b-acd4-65743df36041',
        'idJson': 'ab5ba2e3-421d-46ff-a522-6e748b7dbf83',
        'posicao': 1,
        'tempoVerde': 45,
        'dispensavel': false,
        'plano': {
          'idJson': 'a867bcce-2e4a-44fc-b88d-5a9579b74857'
        },
        'estagio': {
          'idJson': 'd3d9c928-61df-4eee-b87c-22c674037d70'
        }
      },
      {
        'id': '4fdb9ae4-3200-4a44-812c-305a353d4cb8',
        'idJson': 'cbbf7cb7-5e86-4492-bad5-7b611449c549',
        'posicao': 2,
        'tempoVerde': 20,
        'tempoVerdeMinimo': 0,
        'tempoVerdeMaximo': 0,
        'tempoVerdeIntermediario': 0,
        'tempoExtensaoVerde': 0.0,
        'dispensavel': false,
        'plano': {
          'idJson': '29ba9ea9-d15c-46a5-b017-f507bf9c216d'
        },
        'estagio': {
          'idJson': '3b068b57-e93e-415d-88eb-84c5e35ccd3e'
        }
      },
      {
        'id': '95687d45-d8b3-480d-a2fd-b04a98aed91d',
        'idJson': '85a656df-0751-4e7c-b805-c52e2fd8c9c5',
        'posicao': 2,
        'tempoVerde': 20,
        'dispensavel': false,
        'plano': {
          'idJson': 'a867bcce-2e4a-44fc-b88d-5a9579b74857'
        },
        'estagio': {
          'idJson': '6b771a42-d8d2-4f90-9fc7-fd82bfcbd8b9'
        }
      },
      {
        'id': '46ff2a87-c281-4ad1-9cfd-f13cffb731a7',
        'idJson': '7d6d243a-2459-49d8-a7a9-f2f95331ec38',
        'posicao': 2,
        'tempoVerde': 20,
        'tempoVerdeMinimo': 0,
        'tempoVerdeMaximo': 0,
        'tempoVerdeIntermediario': 0,
        'tempoExtensaoVerde': 0.0,
        'dispensavel': false,
        'plano': {
          'idJson': '9d937706-5b1a-4007-b9e4-a530c13b1409'
        },
        'estagio': {
          'idJson': '48964f57-8d94-4abc-b5cd-8e9458459d5c'
        }
      },
      {
        'id': '413cf969-bdd2-4713-96b1-fc4dc8a6b0e1',
        'idJson': '15cefbcb-cd7b-4888-b796-5d54f50a5440',
        'posicao': 2,
        'tempoVerde': 20,
        'dispensavel': false,
        'plano': {
          'idJson': 'acbdfff1-2d2f-4582-bed3-639513470da9'
        },
        'estagio': {
          'idJson': '48964f57-8d94-4abc-b5cd-8e9458459d5c'
        }
      },
      {
        'id': '9dc3f739-d89a-4ab7-abc8-254242ce57eb',
        'idJson': '0f8cb387-d20f-4fec-b81d-1d2ed320f36d',
        'posicao': 3,
        'tempoVerde': 20,
        'dispensavel': false,
        'plano': {
          'idJson': 'a867bcce-2e4a-44fc-b88d-5a9579b74857'
        },
        'estagio': {
          'idJson': '01f5dce2-391a-4930-882d-bf0d656fb17a'
        }
      }
    ],
    'cidades': [
      {
        'id': '66b66819-a1c4-11e6-970d-0401fa9c1b01',
        'idJson': '66b6941e-a1c4-11e6-970d-0401fa9c1b01',
        'nome': 'São Paulo',
        'areas': [
          {
            'idJson': '66b6a0c4-a1c4-11e6-970d-0401fa9c1b01'
          }
        ]
      }
    ],
    'areas': [
      {
        'id': '66b66a46-a1c4-11e6-970d-0401fa9c1b01',
        'idJson': '66b6a0c4-a1c4-11e6-970d-0401fa9c1b01',
        'descricao': 1,
        'cidade': {
          'idJson': '66b6941e-a1c4-11e6-970d-0401fa9c1b01'
        },
        'limites': [],
        'subareas': []
      }
    ],
    'limites': [],
    'todosEnderecos': [
      {
        'id': '611337d8-00c8-4781-a61e-c611eb4897a5',
        'idJson': '07ff47fa-558c-47e4-b604-5f0f190bebf7',
        'localizacao': 'R. Emília Marengo',
        'latitude': -23.5541189,
        'longitude': -46.560647700000004,
        'localizacao2': 'Rua Serra de Japi'
      },
      {
        'id': '32e0fd89-bf06-433e-823e-feb993f7d9e8',
        'idJson': 'e48eec2a-0b7a-44bf-889e-1c28fbdc58ea',
        'localizacao': 'R. Emília Marengo',
        'latitude': -23.5541189,
        'longitude': -46.560647700000004,
        'localizacao2': 'Rua Itapura'
      },
      {
        'id': '0be97b59-160c-43e6-aa14-748b995f121d',
        'idJson': '8586d3ea-8a3b-4c51-b171-cda543eabd05',
        'localizacao': 'R. Emília Marengo',
        'latitude': -23.5541189,
        'longitude': -46.560647700000004,
        'localizacao2': 'Rua Itapura',
        'referencia': '386'
      },
      {
        'id': '822f0012-4958-42f5-a7dd-ff432a4af463',
        'idJson': '3632c309-6fb8-4d81-b7da-0ecab452ba7c',
        'localizacao': 'R. Emília Marengo',
        'latitude': -23.5541189,
        'longitude': -46.560647700000004,
        'localizacao2': 'Rua Itapura',
        'referencia': '386'
      }
    ],
    'imagens': [
      {
        'id': 'e6eb958b-e1c4-4909-99ee-1556c1b7bf0f',
        'idJson': '4c2f780e-0ee4-43a8-9fb8-3741e16700b4',
        'fileName': '2889ca15-b59a-40be-8490-9f802ea3e24d.jpg',
        'contentType': 'image/jpeg'
      },
      {
        'id': '4dbc889b-db26-4e32-a373-feb57ee9c204',
        'idJson': '608ee6c8-81dd-47fd-9834-f02181f7c243',
        'fileName': '140483c6-71ca-4ba3-a238-d25b5ebaa107.jpg',
        'contentType': 'image/jpeg'
      },
      {
        'id': '053a97e6-6278-4527-9e58-0261bcc1f767',
        'idJson': '352f6715-db29-4b3e-9303-fd5dc123289b',
        'fileName': '7c2c88b9-109b-4ea3-a04f-1d7313432c77.jpg',
        'contentType': 'image/jpeg'
      },
      {
        'id': '594296d3-1b16-41f5-a20b-8eec148a5758',
        'idJson': '4e1e4cde-2746-42e0-ad2f-d37aac3948d8',
        'fileName': '2889ca15-b59a-40be-8490-9f802ea3e24d.jpg',
        'contentType': 'image/jpeg'
      },
      {
        'id': 'b0b62b84-ebe0-49f0-8553-6d7e3590307c',
        'idJson': 'db2e885f-d3f0-4afc-8f28-bf974df5fbac',
        'fileName': '7c2c88b9-109b-4ea3-a04f-1d7313432c77.jpg',
        'contentType': 'image/jpeg'
      },
      {
        'id': 'b390281f-497b-4324-8927-38b807b64981',
        'idJson': 'cc73f6d9-1a14-4a9d-9b8b-7c8e07a17c94',
        'fileName': '140483c6-71ca-4ba3-a238-d25b5ebaa107.jpg',
        'contentType': 'image/jpeg'
      },
      {
        'id': 'b1104ccd-f3ad-42c7-ac14-0d9ce9ff8a75',
        'idJson': 'a98e40d4-c307-430c-820a-f87fc725ccce',
        'fileName': '2889ca15-b59a-40be-8490-9f802ea3e24d.jpg',
        'contentType': 'image/jpeg'
      },
      {
        'id': 'a9b8a845-2862-4c4c-a001-a8612366463f',
        'idJson': '7ab268a9-7b30-4851-b9a2-64c445fcb662',
        'fileName': '7c2c88b9-109b-4ea3-a04f-1d7313432c77.jpg',
        'contentType': 'image/jpeg'
      }
    ],
    'atrasosDeGrupo': [
      {
        'id': '479610ae-1d35-44f9-9bf7-e53089a246ea',
        'idJson': '5f8c5a62-d942-4cdc-b1f5-3e4b1a9cff3a',
        'atrasoDeGrupo': 0
      },
      {
        'id': '59f87943-4d43-4e3a-8e80-fe53068dc603',
        'idJson': '480d66b0-ee4e-461a-b80a-eac5258283d4',
        'atrasoDeGrupo': 0
      },
      {
        'id': 'dfd1bc97-a6d7-4470-90ba-ea7840556b2a',
        'idJson': 'f22c031a-e324-47e6-9878-9fe1ebbc5a5a',
        'atrasoDeGrupo': 0
      },
      {
        'id': 'b6d1adde-8128-471e-860e-18b7eb87d355',
        'idJson': '71b02e0e-a945-46de-a8b4-93ee5bf3079f',
        'atrasoDeGrupo': 0
      },
      {
        'id': 'd6870361-4579-497c-ab5c-780706bdd7bc',
        'idJson': '1b3323e3-3e81-4777-a60c-3b22854cb0fb',
        'atrasoDeGrupo': 0
      },
      {
        'id': '2b17ca73-c0a2-4c3a-8735-527eb1e18135',
        'idJson': 'e834089c-1d54-4ae1-b49f-be3a9fa41eb1',
        'atrasoDeGrupo': 0
      },
      {
        'id': '899710b2-8df2-42c1-bc4e-2cf9fabfef29',
        'idJson': 'b1062658-2181-4dc1-b1d0-c1d008e2de6a',
        'atrasoDeGrupo': 0
      },
      {
        'id': '28ea8e7b-3a52-4602-83c7-9aaa6a32b4e9',
        'idJson': 'f4f5da2c-a588-424e-8aaa-a61fa70c7ead',
        'atrasoDeGrupo': 0
      },
      {
        'id': '0e100079-4d47-49ab-9b05-6255e6018864',
        'idJson': 'dbcc1e62-bb75-4b4c-9dc7-7baa7ed31758',
        'atrasoDeGrupo': 0
      },
      {
        'id': 'a61adc8f-14fc-467c-94ab-fb0a771c7261',
        'idJson': '0138279b-e059-4e4c-8553-2a7d88ab267a',
        'atrasoDeGrupo': 0
      },
      {
        'id': '36c0a402-cc29-4e22-95d1-07468d1446aa',
        'idJson': 'f3e860c9-303b-4d95-b59d-0e9b33c15a57',
        'atrasoDeGrupo': 0
      },
      {
        'id': '0cef06cc-4043-454d-9039-fa636ab37f38',
        'idJson': '7f3daf53-ccd2-46dc-bc9f-2a9daa9c61eb',
        'atrasoDeGrupo': 0
      },
      {
        'id': 'b2756953-dfaf-41b8-abd6-b4e288c368a0',
        'idJson': 'c73e6ced-2cad-48f4-b91e-5adbc0e17fd9',
        'atrasoDeGrupo': 0
      },
      {
        'id': 'd1979c3b-4e19-47af-a559-9b5d5cb13e30',
        'idJson': '09723680-9b3c-4f6d-8610-692ce972fbc0',
        'atrasoDeGrupo': 0
      },
      {
        'id': 'c1196155-c75b-425e-abcf-5f045f20483a',
        'idJson': '5926d6cc-7417-4e33-8e62-ff6381aa64f7',
        'atrasoDeGrupo': 0
      },
      {
        'id': '7197ad30-fd00-477c-b11a-db8ce57d8ba3',
        'idJson': '1e405201-a291-468d-ad7f-9e6604b0ff60',
        'atrasoDeGrupo': 0
      },
      {
        'id': '5668d2de-1712-4699-8337-678a44b88132',
        'idJson': '7260777e-9a06-4f60-9ce6-434022bc44e9',
        'atrasoDeGrupo': 0
      },
      {
        'id': '03391f26-03d4-4093-8529-47dff8d46ac1',
        'idJson': '928f8f62-11e2-4f9b-bc98-66ccddba3ac0',
        'atrasoDeGrupo': 5
      },
      {
        'id': '6ef4d7ec-447b-4d8e-8197-84ba7d61d571',
        'idJson': '7da877cf-61b5-4ec1-9cca-850c6314b2fd',
        'atrasoDeGrupo': 0
      },
      {
        'id': 'ce48c9de-4633-4467-84da-46099c16f940',
        'idJson': 'ec0332a7-ef0a-41b1-9fa2-8450cadc7d13',
        'atrasoDeGrupo': 0
      },
      {
        'id': 'aa681784-c173-467d-b5a6-996092e8e249',
        'idJson': 'a5515725-323a-4e73-9d79-8a698fce7aa6',
        'atrasoDeGrupo': 0
      },
      {
        'id': 'e0467335-5aab-40c6-8fcd-0dd58afddb5e',
        'idJson': '750e3a9b-b063-4d51-9f8c-41711a8eee23',
        'atrasoDeGrupo': 0
      },
      {
        'id': '3601e2db-a629-41fc-b5ea-953fa37c497d',
        'idJson': 'b41d4cba-7575-4750-b397-f983567c0780',
        'atrasoDeGrupo': 0
      },
      {
        'id': 'cc052067-e082-46e0-8901-bf55ee81b2c9',
        'idJson': '04763187-feb5-4896-8344-2b5e5ae3469c',
        'atrasoDeGrupo': 0
      },
      {
        'id': 'a666ec4a-e5d5-4337-9703-77a2344887ec',
        'idJson': '7d813db2-cf8b-470e-9225-c14d42147725',
        'atrasoDeGrupo': 0
      },
      {
        'id': '58d7974b-3b41-47d1-ae8c-6bb9dfac6060',
        'idJson': '899db721-6b35-4a5a-a99e-87cf7f4eaffb',
        'atrasoDeGrupo': 0
      },
      {
        'id': '5e01fe46-4722-413d-9c40-6e5c806d9263',
        'idJson': '41cd846b-6044-4472-a312-e2d90da2e4f8',
        'atrasoDeGrupo': 0
      },
      {
        'id': '226f8b85-54b6-4570-abb7-4705aae51aeb',
        'idJson': '8dbbc26d-f123-4d7a-8071-537a1004b275',
        'atrasoDeGrupo': 0
      }
    ],
    'statusVersao': 'EM_CONFIGURACAO',
    'versaoControlador': {
      'id': 'b9e57d30-016d-4e55-9bef-1864d0433966',
      'idJson': null,
      'descricao': 'Controlador criado pelo usuário: paulo',
      'usuario': {
        'id': 'd348b85a-ceb0-49a9-9dcb-d54126db8bae',
        'nome': 'paulo',
        'login': 'paulo',
        'email': 'paulo@rarolabs.com.br',
        'area': {
          'idJson': '66b6a0c4-a1c4-11e6-970d-0401fa9c1b01'
        }
      }
    },
    'versoesPlanos': [
      {
        'id': 'e71fb42c-6c4d-4d29-b0f5-27a98274bf0a',
        'idJson': 'd3ea90e7-ceda-4100-bf9a-e845a0e2af38',
        'statusVersao': 'EDITANDO',
        'anel': {
          'idJson': 'e7457fca-897e-4bd9-a380-050c4c6c58b8'
        },
        'planos': [
          {
            'idJson': 'acbdfff1-2d2f-4582-bed3-639513470da9'
          },
          {
            'idJson': '9d937706-5b1a-4007-b9e4-a530c13b1409'
          }
        ]
      },
      {
        'id': '74f1eac9-4eeb-4ecc-9611-87eabaee5b1f',
        'idJson': 'ac4757a0-87bc-4b3b-a391-1e3ca8f9571b',
        'statusVersao': 'EDITANDO',
        'anel': {
          'idJson': 'f30c1621-ec51-47ed-bff1-a9758512d865'
        },
        'planos': [
          {
            'idJson': '10d40af6-212e-4dc4-879a-81575f16e11f'
          },
          {
            'idJson': 'a867bcce-2e4a-44fc-b88d-5a9579b74857'
          }
        ]
      },
      {
        'id': 'de914912-1dbb-4f86-adb9-3cee6bb53282',
        'idJson': '67f12a26-3839-4c80-a712-778a7f76bde6',
        'statusVersao': 'EDITANDO',
        'anel': {
          'idJson': 'ea5465fe-c93b-464b-8c96-d47e2036a3d1'
        },
        'planos': [
          {
            'idJson': '24510147-ba9f-4a61-815d-63565ffca56f'
          },
          {
            'idJson': '29ba9ea9-d15c-46a5-b017-f507bf9c216d'
          }
        ]
      }
    ],
    'tabelasHorarias': [
      {
        'id': '42109f1d-6f45-4b71-afc4-db55c27c48ba',
        'idJson': '77987356-2f13-4ac4-bed2-c0aecf7d6706',
        'versaoTabelaHoraria': {
          'idJson': 'a2d145ab-cf3d-4c63-839a-49beeb9e962e'
        },
        'eventos': [
          {
            'idJson': '1b9a5f47-cd6e-4c3d-b851-91075a5fcc18'
          }
        ]
      }
    ],
    'eventos': [
      {
        'id': '40496bfc-5621-4c92-ab5a-fa1fc1920240',
        'idJson': '1b9a5f47-cd6e-4c3d-b851-91075a5fcc18',
        'posicao': '1',
        'tipo': 'NORMAL',
        'diaDaSemana': 'Todos os dias da semana',
        'data': '07-11-2016',
        'horario': '00:00:00.000',
        'posicaoPlano': '1',
        'tabelaHoraria': {
          'idJson': '77987356-2f13-4ac4-bed2-c0aecf7d6706'
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
