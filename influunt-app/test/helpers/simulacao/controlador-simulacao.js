'use strict';

/**
 * Conjunto de funções auxiliares utilizadas pelos testes do wizard de controladores.
 *
 * @type       {<type>}
 */
var ControladorSimulacao = {
  obj: {
    controlador: {
      id: 'da10a6c1-3dbf-493a-9e19-9392e73af056',
      nomeEndereco: 'Av. Paulista, nº 654',
      gruposSemaforicos: [
        {
          id: '1981a33b-94b2-479a-b033-d037366259dd',
          descricao: 'G4',
          posicao: 4
        },
        {
          id: '30ecbeb9-f191-4743-85b6-28e2f5f11707',
          descricao: 'G5',
          posicao: 5
        },
        {
          id: '456e4979-cb2f-47ea-bf20-e7322e876864',
          descricao: 'G2',
          posicao: 2
        },
        {
          id: '478a8b8a-47f0-42fc-83a3-2b5de82a2cb1',
          descricao: 'G3',
          posicao: 3
        },
        {
          id: '47a0896f-76da-472f-a51c-d7255ff17649',
          descricao: 'G1',
          posicao: 1
        },
        {
          id: '123cc9df-4740-40e7-8868-e316e77a83f6',
          descricao: 'G8',
          posicao: 8
        },
        {
          id: '55e36d3d-5d3e-40d8-9345-832d4feffbdb',
          descricao: 'G10',
          posicao: 10
        },
        {
          id: '6c6939e9-c30a-4703-8bc8-09bd793c17cd',
          descricao: 'G9',
          posicao: 9
        },
        {
          id: '9bb53b28-8129-485f-84ba-32a06e41a3ad',
          descricao: 'G7',
          posicao: 7
        },
        {
          id: 'af58ed61-bdf0-4eff-837a-005b1c9ad66f',
          descricao: 'G6',
          posicao: 6
        }
      ],
      aneis: [
        {
          id: 'ada78968-be6f-4963-9157-b67ab4e5f8aa',
          posicao: 1,
          detectores: [
            {
              id: '55da8eb6-46ba-4426-8a29-acafeda16d5d',
              tipo: 'PEDESTRE',
              posicao: 1,
              monitorado: false
            }
          ],
          planos: [
            {
              id: '12698621-bb22-483d-866a-db1e09fe6c87',
              posicao: 0,
              descricao: 'Exclusivo',
              modoOperacao: 'MANUAL'
            },
            {
              id: '7bd7e404-7820-4d03-815e-ff66ccf2bebb',
              posicao: 2,
              descricao: 'PLANO 2',
              modoOperacao: 'TEMPO_FIXO_COORDENADO'
            },
            {
              id: '9fa856d6-f2ae-4f8b-a833-b0a3efb5a29d',
              posicao: 1,
              descricao: 'PLANO 1',
              modoOperacao: 'TEMPO_FIXO_ISOLADO'
            }
          ]
        },
        {
          id: 'f978fca3-cfee-4159-b01d-2c8a2437b8ae',
          posicao: 2,
          detectores: [
            {
              id: '38cf5900-0207-46ff-9465-1c780d736128',
              tipo: 'VEICULAR',
              posicao: 1,
              monitorado: false
            }
          ],
          planos: [
            {
              id: '8b1faca2-fb67-4829-9fec-f19df8392cb1',
              posicao: 2,
              descricao: 'PLANO 2',
              modoOperacao: 'TEMPO_FIXO_COORDENADO'
            },
            {
              id: 'e2e0e916-e61b-4eb9-8de0-ee30ec38ba81',
              posicao: 0,
              descricao: 'Exclusivo',
              modoOperacao: 'MANUAL'
            },
            {
              id: 'f5072d1c-7c27-46d7-9537-5f24704aa206',
              posicao: 1,
              descricao: 'PLANO 1',
              modoOperacao: 'TEMPO_FIXO_ISOLADO'
            }
          ]
        }
      ]
    },
    falhas: [
      {
        tipo: 'FALHA_FASE_VERMELHA_DE_GRUPO_SEMAFORICO_APAGADA',
        codigo: 1,
        descricao: 'Fase vermelha do grupo semafórico apagada',
        descricaoParam: 'Grupo Semafórico',
        tipoParam: 'GRUPO_SEMAFORICO'
      },
      {
        tipo: 'FALHA_FASE_VERMELHA_DE_GRUPO_SEMAFORICO_REMOCAO',
        codigo: 2,
        descricao: 'Fase vermelha do grupo semafórico apagada removida',
        descricaoParam: 'Grupo Semafórico',
        tipoParam: 'GRUPO_SEMAFORICO'
      },
      {
        tipo: 'FALHA_FOCO_VERMELHO_DE_GRUPO_SEMAFORICO_APAGADA',
        codigo: 7,
        descricao: 'Foco vermelho apagado',
        descricaoParam: 'Grupo Semafórico',
        tipoParam: 'GRUPO_SEMAFORICO'
      },
      {
        tipo: 'FALHA_FOCO_VERMELHO_DE_GRUPO_SEMAFORICO_REMOCAO',
        codigo: 8,
        descricao: 'Foco vermelho apagado removida',
        descricaoParam: 'Grupo Semafórico',
        tipoParam: 'GRUPO_SEMAFORICO'
      },
      {
        tipo: 'FALHA_DETECTOR_VEICULAR_FALTA_ACIONAMENTO',
        codigo: 13,
        descricao: 'Falha detector veicular - Falta de Acionamento',
        descricaoParam: 'Detector Veicular',
        tipoParam: 'DETECTOR_VEICULAR'
      },
      {
        tipo: 'FALHA_DETECTOR_VEICULAR_ACIONAMENTO_DIRETO',
        codigo: 14,
        descricao: 'Falha detector veicular - Acionamento Direto',
        descricaoParam: 'Detector Veicular',
        tipoParam: 'DETECTOR_VEICULAR'
      },
      {
        tipo: 'FALHA_DETECTOR_PEDESTRE_FALTA_ACIONAMENTO',
        codigo: 15,
        descricao: 'Falha detector pedestre - Falta de Acionamento',
        descricaoParam: 'Detector Pedestre',
        tipoParam: 'DETECTOR_PEDESTRE'
      },
      {
        tipo: 'FALHA_DETECTOR_PEDESTRE_ACIONAMENTO_DIRETO',
        codigo: 16,
        descricao: 'Falha detector pedestre - Acionamento Direto',
        descricaoParam: 'Detector Pedestre',
        tipoParam: 'DETECTOR_PEDESTRE'
      },
      {
        tipo: 'FALHA_DESRESPEITO_AO_TEMPO_MAXIMO_DE_PERMANENCIA_NO_ESTAGIO',
        codigo: 26,
        descricao: 'Desrespeito ao tempo máximo de permanencia no estágio',
        descricaoParam: 'Anel',
        tipoParam: 'ANEL'
      },
      {
        tipo: 'FALHA_VERDES_CONFLITANTES',
        codigo: 27,
        descricao: 'Verdes conflitantes'
      },
      {
        tipo: 'FALHA_AMARELO_INTERMITENTE',
        codigo: 28,
        descricao: 'Amarelo Intermitente'
      },
      {
        tipo: 'FALHA_SEMAFORO_APAGADO',
        codigo: 29,
        descricao: 'Semáforo apagado'
      },
      {
        tipo: 'FALHA_ACERTO_RELOGIO_GPS',
        codigo: 52,
        descricao: 'Falha acerto relógio GPS'
      }
    ],
    alarmes: [
      {
        tipo: 'ALARME_ABERTURA_DA_PORTA_PRINCIPAL_DO_CONTROLADOR',
        codigo: 1,
        descricao: 'Abertura da porta principal do controlador'
      },
      {
        tipo: 'ALARME_FECHAMENTO_DA_PORTA_PRINCIPAL_DO_CONTROLADOR',
        codigo: 2,
        descricao: 'Fechamento da porta principal do controlador'
      },
      {
        tipo: 'ALARME_ABERTURA_DA_PORTA_DO_PAINEL_DE_FACILIDADES_DO_CONTROLADOR',
        codigo: 3,
        descricao: 'Abertura da porta do painel de facilidades do controlador'
      },
      {
        tipo: 'ALARME_FECHAMENTO_DA_PORTA_DO_PAINEL_DE_FACILIDADES_DO_CONTROLADOR',
        codigo: 4,
        descricao: 'Fechamento da porta do painel de facilidades do controlador'
      },
      {
        tipo: 'ALARME_INSERCAO_DE_PLUG',
        codigo: 5,
        descricao: 'Inserção de plug'
      },
      {
        tipo: 'ALARME_RETIRADA_DO_PLUG',
        codigo: 6,
        descricao: 'Retirada de plug'
      }
    ]
  },
  getControladorId: function() {
    return this.obj.controlador.id;
  },
  getAnelAtivoId: function() {
    return _.find(this.obj.aneis, 'ativo').id;
  },
  get: function() {
    return _.cloneDeep(this.obj);
  },
  getFalhaComParametro: function() {
    return _.find(this.obj.falhas, { codigo: 1 });
  },
  getFalhaSemParametro: function() {
    return _.find(this.obj.falhas, { codigo: 27 });
  }
};
