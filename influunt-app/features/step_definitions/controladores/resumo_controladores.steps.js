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

  this.Given(/^o sistema deverá mostrar as informações íniciais do anel 1$/, function () {
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

  this.Given(/^o sistema deverá mostrar as informações íniciais do anel 2$/, function () {
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

  this.Given(/^o sistema deverá mostrar as informações dos grupos semáforicos$/, function () {
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
      'g1-g2': 'G1-G2',
      'g2-g3': 'G2-G3'
    };
    return resumoControladorPage.informacaoVerdesConflitantes(informacoes);
  });


};
