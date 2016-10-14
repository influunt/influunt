'use strict';

var ResumoControladorPage = require('../../support/page-objects/resumo_controladores');

module.exports = function() {
  var resumoControladorPage = new ResumoControladorPage();

  this.Given(/^o sistema deverá redirecionar para a revisão do controlador$/, function () {
    return resumoControladorPage.isResumoControlador();
  });

  this.Given(/^o sistema deverá mostrar as informações do dados básicos$/, function () {
    return resumoControladorPage.informacaoDadoBasico();
  });

  this.Given(/^o sistema deverá mostrar as informações iniciais do anel 1$/, function () {
    var informacoes = {
      'Logradouro': 'Av. Paulista com R. Bela Cintra',
      'CLA': '1.000.0001.1',
      'SMEE': '-',
      'gsp': '1',
      'gsv': '2',
      'gdp': '2',
      'gdv': '2'
    };
    return resumoControladorPage.informacaoBasicaAnel(informacoes);
  });

  this.Given(/^o sistema deverá mostrar as informações iniciais do anel 2$/, function () {
     var informacoes = {
      'Logradouro': 'Av. Amazonas com Av. Contorno',
      'CLA': '1.000.0001.2',
      'SMEE': '-',
      'gsp': '0',
      'gsv': '2',
      'gdp': '0',
      'gdv': '2'
    };
    return resumoControladorPage.informacaoBasicaAnel(informacoes);
  });

  this.Given(/^o sistema deverá mostrar as informações dos grupos semafóricos$/, function () {
     var informacoes = {
      'Titulo1': 'Grupo 1',
      'Titulo2': 'Grupo 2',
      'Titulo3': 'Grupo 3',
      'Tipo': 'PEDESTRE',
      'Fase': 'Não colocar em amarelo intermitente',
      'VerdeS': '4s'
    };
    return resumoControladorPage.informacaoGrupoSemaforico(informacoes);
  });

   this.Given(/^o sistema deverá mostrar as informações para Associação Estágio x Grupo Semafórico$/, function () {
     var informacoes = {
      'Estagio1': 'E1',
      'Estagio2': 'E2',
      'Estagio3': 'E3',
      'Estagio4': 'E4',
      'G1': 'G1',
      'G2': 'G2',
      'G3': 'G3',
      'G4': 'G4'
    };
    return resumoControladorPage.informacaoAssociacaoVsGrupo(informacoes);
  });

  this.Given(/^o sistema deverá mostrar as informações para Verdes Conflitantes$/, function () {
     var informacoes = {
      'g1g2': 'G1-G2',
      'g2g3': 'G2-G3'
    };
    return resumoControladorPage.informacaoVerdesConflitantes(informacoes);
  });

  this.Given(/^o sistema deverá mostrar as informações para Transições Proibidas$/, function () {
     var informacoes = {
      'TituloTp': 'Transição Proibida',
      'TituloAlternativa': 'Alternativa',
      'Tp1': 'E3-E4',
      'Tp2': 'E4-E3',
      'Alternativa1': 'E3',
      'Alternativa2': 'E1',
    };
    return resumoControladorPage.informacaoTransicoesProibidas(informacoes);
  });

  this.Given(/^o sistema deverá mostrar as informações para Detectores$/, function () {
     var informacoes = {
      'Dp1': 'DP1',
      'Dp2': 'DP1',
      'Dv1': 'DV1',
      'Dv2': 'DV2',
      'E3': 'E3',
      'E4': 'E4',
      'E1': 'E1',
      'E2': 'E2',
      'temMonitoramento': 'com-monitoramento'
    };
    return resumoControladorPage.informacaoDetectores(informacoes);
  });

  this.Given(/^o sistema deverá mostrar as informações para Tabela Entreverdes do grupo G1$/, function () {
    var informacoes = {'Padrao1': 'E1-E3', 'Padrao2': 'E1-E2','Padrao3': 'E1-E4'};
    return resumoControladorPage.informacaoTabelaEntreverdesEAtrasoGrupo(informacoes);
  });

  this.Given(/^o sistema deverá mostrar as informações para Tabela Entreverdes do grupo G2$/, function () {
    var informacoes = {'Padrao1': 'E2-E4', 'Padrao2': 'E2-E1','Padrao3': 'E2-E3'};
    return resumoControladorPage.informacaoTabelaEntreverdesEAtrasoGrupo(informacoes);
  });

  this.Given(/^o sistema deverá mostrar as informações para Tabela Entreverdes do grupo G3$/, function () {
    var informacoes = {'Padrao1': 'E4-E1', 'Padrao2': 'E3-E1','Padrao3': 'E4-E2'};
    return resumoControladorPage.informacaoTabelaEntreverdesEAtrasoGrupo(informacoes);
  });

  this.Given(/^o sistema deverá mostrar as informações para Tabela Entreverdes do grupo G4$/, function () {
    var informacoes = {'Padrao1': 'E1-E3'};
    return resumoControladorPage.informacaoTabelaEntreverdesEAtrasoGrupo(informacoes);
  });

  this.Given(/^o sistema deverá mostrar as informações para Tabela Entreverdes do grupo G5$/, function () {
    var informacoes = {'Padrao1': 'E2-E1'};
    return resumoControladorPage.informacaoTabelaEntreverdesEAtrasoGrupo(informacoes);
  });

  this.Given(/^que o usuário mudar o grupo semafórico em Tabela Entreverdes clicando no grupo "([^"]*)"$/, function (grupo) {
    var pathTabelaVerdes = 'tabelasEntreVerdes';
    return resumoControladorPage.selecionarGrupoSemaforico(grupo, pathTabelaVerdes);
  });

  this.Given(/^o sistema deverá mostrar as informações para Atraso de grupo do grupo G1$/, function () {
    var informacoes = {'Padrao1': 'E1-E4', 'Padrao2': 'E1-E2','Padrao3': 'E1-E3'};
    return resumoControladorPage.informacaoTabelaEntreverdesEAtrasoGrupo(informacoes);
  });

  this.Given(/^que o usuário mudar o grupo semafórico em Atraso de Grupo clicando no grupo "([^"]*)"$/, function (grupo) {
    var pathAtrasoGrupo = 'atrasoDeGrupo';
    return resumoControladorPage.selecionarGrupoSemaforico(grupo, pathAtrasoGrupo);
  });

  this.Given(/^o sistema deverá mostrar as informações para Atraso de grupo do grupo G2$/, function () {
    var informacoes = {'Padrao1': 'E2-E4', 'Padrao2': 'E2-E3','Padrao3': 'E2-E1'};
    return resumoControladorPage.informacaoTabelaEntreverdesEAtrasoGrupo(informacoes);
  });

  this.Given(/^o sistema deverá mostrar as informações para Atraso de grupo do grupo G3$/, function () {
    var informacoes = {'Padrao1': 'E4-E2', 'Padrao2': 'E3-E2','Padrao3': 'E4-E1'};
    return resumoControladorPage.informacaoTabelaEntreverdesEAtrasoGrupo(informacoes);
  });

  this.Given(/^o sistema deverá mostrar as informações para Atraso de grupo do grupo G4$/, function () {
    var informacoes = {'Padrao1': 'E1-E2'};
    return resumoControladorPage.informacaoTabelaEntreverdesEAtrasoGrupo(informacoes);
  });

  this.Given(/^o sistema deverá mostrar as informações para Atraso de grupo do grupo G5$/, function () {
    var informacoes = {'Padrao1': 'E2-E1'};
    return resumoControladorPage.informacaoTabelaEntreverdesEAtrasoGrupo(informacoes);
  });

  this.Given(/^que transições proibidas não exista dados a serem exibidos$/, function () {
    return resumoControladorPage.naoExistemDados();
  });
};
