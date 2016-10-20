'use strict';

var worldObj = require('../world');
var world = new worldObj.World();
var path = require('path');

var WizardControladorPage = function () {
  var INDEX_PATH = '/app/controladores';

  this.world = world;

  this.indexPage = function() {
    world.visit(INDEX_PATH);
    return this.isIndexPage();
  };

  this.isIndexPage = function() {
    var novoControladorButton = 'a[ui-sref="app.wizard_controladores.dados_basicos"]';
    return world.waitFor(novoControladorButton);
  };

  this.isWizardPasso = function(passo) {
    var _this = this;
    return world.waitForOverlayDisappear().then(function() {
      switch(passo) {
        case 'Dados Básicos':
          return _this.isWizardDadosBasicos();
        case 'Anéis':
          return _this.isWizardAneis();
        case 'Grupos Semafóricos':
          return _this.isWizardGruposSemaforicos();
        case 'Verdes Conflitantes':
          return _this.isWizardVerdesConflitantes();
        case 'Associação':
          return _this.isWizardAssociacao();
        case 'Transições Proibidas':
          return _this.isWizardTransicoesProibidas();
        case 'Atraso de Grupo':
          return _this.isWizardAtrasoDeGrupo();
        case 'Tabela Entre Verdes':
          return _this.isWizardTabelaEntreVerdes();
        case 'Detectores':
          return _this.isWizardDetectores();
        case 'Revisão':
          return _this.isWizardRevisao();
        default:
          throw new Error('Passo não encontrado: '+passo);
      }
    });
  };

  this.isWizardRevisao = function() {
    return world.waitForByXpath('//li[contains(@ng-class, "app.wizard_controladores.revisao")][contains(@class, "active")]');
  };

  this.isWizardDadosBasicos = function() {
    return world.waitForByXpath('//li[contains(@ng-class, "app.wizard_controladores.dados_basicos")][contains(@class, "active")]');
  };

  this.isWizardAneis = function() {
    return world.waitForByXpath('//li[contains(@ng-class, "app.wizard_controladores.aneis")][contains(@class, "active")]');
  };

  this.isWizardGruposSemaforicos = function() {
    return world.waitForByXpath('//li[contains(@ng-class, "app.wizard_controladores.configuracao_grupo")][contains(@class, "active")]');
  };

  this.isWizardVerdesConflitantes = function() {
    return world.waitForByXpath('//li[contains(@ng-class, "app.wizard_controladores.verdes_conflitantes")][contains(@class, "active")]');
  };

  this.isWizardAssociacao = function() {
    return world.waitForByXpath('//li[contains(@ng-class, "app.wizard_controladores.associacao")][contains(@class, "active")]');
  };

  this.isWizardTransicoesProibidas = function() {
    return world.waitForByXpath('//li[contains(@ng-class, "app.wizard_controladores.transicoes_proibidas")][contains(@class, "active")]');
  };

  this.isWizardAtrasoDeGrupo = function() {
    return world.waitForByXpath('//li[contains(@ng-class, "app.wizard_controladores.atraso_de_grupo")][contains(@class, "active")]');
  };

  this.isWizardTabelaEntreVerdes = function() {
    return world.waitForByXpath('//li[contains(@ng-class, "app.wizard_controladores.entre_verdes")][contains(@class, "active")]');
  };

  this.isWizardDetectores = function() {
    return world.waitForByXpath('//li[contains(@ng-class, "app.wizard_controladores.associacao_detectores")][contains(@class, "active")]');
  };

  this.clicarBotaoProximoPasso = function() {
    return world.scrollToDown().then(function() {
      return world.findLinkByText('Salvar e Avançar').click().then(function(){
        return world.sleep(100);
      });
    });
  };

  this.getErrorMessageFor = function(fieldSelector) {
    var selector = fieldSelector + ' + p[class*="error-msg"]';
    return world.waitFor(selector).then(function() {
      return world.getElement(selector).getText();
    });
  };

  this.getAlertErrorMessages = function() {
    return world.waitFor('div.alert').then(function() {
      return world.getElement('div.alert').getText();
    });
  };

  this.errorMessages = function(passo) {
    switch(passo) {
      case 'Dados Básicos':
        return this.errorMessagesDadosBasicos();
      case 'Grupos Semafóricos':
        return this.errorMessagesGruposSemaforicos();
      case 'Anéis':
        return this.errorMessagesAneis();
      case 'Associação':
        return this.errorMessagesAssociacao();
      case 'Verdes Conflitantes':
        return this.errorMessagesVerdesConflitantesSemMarcacao();
      case 'Detectores':
        return this.errorMessagesDetectores();
      default:
        throw new Error('Passo não encontrado: '+passo);
    }
  };

  this.errorMessagesDadosBasicos = function() {
    var thisWizardPage = this;
    var messages = [];
    return thisWizardPage.getErrorMessageFor('[name="area"]').then(function() {

      return new Promise(function (resolve, reject) {
        for (var i = 0; i < messages.length; i++) {
          if (messages[i].msg !== 'não pode ficar em branco') {
            reject('expected \''+messages[i].msg+'\' to be \'não pode ficar em branco\'     (campo: '+messages[i].campo+')');
          }
        }
        resolve(true);
      });
    });
  };

  this.errorMessagesAneis = function() {
    var thisWizardPage = this;
    var messages = [];
    return thisWizardPage.getErrorMessageFor('helper-endereco[latitude="currentEnderecos[0].latitude"]').then(function(msg) {
      messages.push({campo: 'localizacao1', msg: msg});
      return thisWizardPage.getErrorMessageFor('helper-endereco[latitude="currentEnderecos[1].latitude"]');
    }).then(function(msg) {
      messages.push({campo: 'localizacao2', msg: msg});
      return thisWizardPage.getErrorMessageFor('[name="enderecos[1].latitude"]');
    }).then(function(msg) {
      messages.push({campo: 'localizacao2.latitude', msg: msg});
      return thisWizardPage.getErrorMessageFor('[name="enderecos[1].longitude"]');
    }).then(function(msg) {
      messages.push({campo: 'localizacao2.longitude', msg: msg});
      return new Promise(function (resolve, reject) {
        for (var i = 0; i < messages.length; i++) {
          if (messages[i].msg !== 'não pode ficar em branco') {
            reject('expected \''+messages[i].msg+'\' to be \'não pode ficar em branco\'     (campo: '+messages[i].campo+')');
          }
        }
        resolve(true);
      });
    }).then(function() {
      return thisWizardPage.getAlertErrorMessages().then(function(text) {
        return new Promise(function(resolve, reject) {
          if (text.match(/Um anel ativo deve ter ao menos dois estágios e no máximo o limite do modelo do controlador/) !== null) {
            resolve(true);
          } else {
            reject('required error message not found: "Um anel ativo deve ter ao menos dois estágios e no máximo o limite do modelo do controlador"');
          }
        });
      });
    }).catch(function(e) {
      console.log('error on errorMessagesAneis():', e);
      throw new Error(e);
    });
  };

  this.errorMessagesGruposSemaforicosAneis = function() {
    return this.getAlertErrorMessages().then(function(text) {
      return new Promise(function(resolve, reject) {
        if (text.match(/Esse anel deve ter no mínimo 2 grupos semáforicos/) !== null) {
          resolve(true);
        } else {
          reject('required error message not found: "Esse anel deve ter no mínimo 2 grupos semáforicos"');
        }
      });
    });
  };

  this.errorMessageAssociacaoEstagio = function(estagio, anel) {
    return world.execJavascript('return $("p:contains(\''+estagio+'\')").parent().children("span.badge-danger:visible").length').then(function(numElements) {
      return new Promise(function(resolve, reject) {
        if (numElements > 0) {
          resolve(true);
        } else {
          reject('Expected an error badge for estágio "'+estagio+'" in Anel '+anel);
        }
      });
    });
  };

   this.errorTransacaoProibidaEstagio = function(estagio, anel) {
    return world.execJavascript('return $("h4:contains(\''+estagio+'\')").parent().children("span.badge-danger:visible").length').then(function(numElements) {
      return new Promise(function(resolve, reject) {
        if (numElements > 0) {
          resolve(true);
        } else {
          reject('Expected an error badge for estágio "'+estagio+'" in Anel '+anel);
        }
      });
    });
  };

  this.errorMessagesAssociacao = function() {
    var _this = this;
    return Promise.all([
      _this.errorMessageAssociacaoEstagio('E1', 1),
      _this.errorMessageAssociacaoEstagio('E2', 1),
      _this.errorMessageAssociacaoEstagio('E3', 1)
    ]).then(function() {
      return _this.selecionarAnel(2);
    }).then(function() {
      return Promise.all([
        _this.errorMessageAssociacaoEstagio('E1', 2),
        _this.errorMessageAssociacaoEstagio('E2', 2)
      ]);
    }).then(function() {
      return Promise.resolve(true);
    });
  };

  this.errorMessagesVerdesConflitantesSemMarcacao = function() {
    var _this = this;
    return Promise.all([
      _this.errorMessagesVerdesConflitantesGrupo('G1'),
      _this.errorMessagesVerdesConflitantesGrupo('G2'),
      _this.errorMessagesVerdesConflitantesGrupo('G3')
    ]).then(function() {
      return _this.selecionarAnel(2);
    }).then(function() {
      return Promise.all([
        _this.errorMessagesVerdesConflitantesGrupo('G4'),
        _this.errorMessagesVerdesConflitantesGrupo('G5')
      ]);
    }).then(function() {
      return Promise.resolve(true);
    });
  };

  this.errorMessageDetector = function(detector) {
    return world.execJavascript('return $("strong:contains(\''+detector+'\') + span.badge-danger:visible").length').then(function(numElements) {
      return new Promise(function(resolve, reject) {
        if (numElements > 0) {
          resolve(true);
        } else {
          reject('Badge de erro não encontrada para o detector '+detector);
        }
      });
    });
  };

  this.errorMessagesDetectores = function() {
    return Promise.all([
      this.errorMessageDetector('DP1'),
      this.errorMessageDetector('DV1'),
      this.errorMessageDetector('DV2')
    ]).then(function() {
      return Promise.resolve(true);
    });
  };

  this.isEstagioAlternativoInvalido = function(transicao) {
    var estagioAlternativo = '#estagio-alternativo-' + transicao;
    return world.waitFor('.has-error').then(function() {
      return world.getElements(estagioAlternativo + '.has-error').then(function(res) {
        return res.length > 0;
      });
    });
  };

  this.errorMessagesVerdesConflitantesGrupo = function(grupo) {
    return this.getAlertErrorMessages().then(function(text) {
      return new Promise(function(resolve, reject) {
        if (text.match(new RegExp(grupo + ': Esse grupo semafórico deve ter ao menos um verde conflitante')) !== null) {
          resolve(true);
        } else {
          reject('required error message not found: "'+grupo+': Esse grupo semafórico deve ter ao menos um verde conflitante"');
        }
      });
    });
  };

  this.cadastrarEntidadesDadosBasicos = function() {
    return world.execSqlScript('features/support/scripts/controladores/entidades_dados_basicos.sql');
  };

  this.addImagens = function(localImagem) {
    var corquiPath = '../resources/croquigeral.jpg';
    var anel1Est1  = '../resources/anel1_est1.jpg';
    var anel1Est2  = '../resources/anel1_est2.jpg';
    var anel1Est3  = '../resources/anel1_est3.jpg';
    var anel2Est1  = '../resources/anel2_est1.jpg';
    var anel2Est2  = '../resources/anel2_est2.jpg';

    switch(localImagem) {
      case 'Croqui':
        return this.adicionarImagem(corquiPath);
      case 'Anel 1 como Estágio1':
        return this.adicionarImagem(anel1Est1);
      case 'Anel 1 como Estágio2':
        return this.adicionarImagem(anel1Est2);
      case 'Anel 1 como Estágio3':
        return this.adicionarImagem(anel1Est3);
      case 'Anel 2 como Estágio1':
        return this.adicionarImagem(anel2Est1);
      case 'Anel 2 como Estágio2':
        return this.adicionarImagem(anel2Est2);
      default:
        throw new Error('Localização da imagem não encontrada: '+localImagem);
     }
  };

  this.adicionarImagem = function(locationPath) {
    var filePath = path.join(__dirname, ''+locationPath+'');
    var promises = [];

    promises.push(world.dropzoneUpload(filePath));

    return Promise.all(promises).then(function() {
      return world.waitForAJAX();
    });
  };

  this.selecionaEstagioAlternativoParaTransicaoProibida = function(transicao, estagio) {
    var transicaoSelector = '#estagio-alternativo-' + transicao + ' select';
    return world.selectOption(transicaoSelector, estagio);
  };

  this.associarGrupoSemaforicoEstagio = function(grupo, estagio) {
    var selector = '"li[data-ng-repeat=\'(indexEstagio, estagio) in currentEstagios\']:nth-child('+estagio.substring(1)+') p:contains(\''+grupo+'\')").parent().find("input"';
    return world.sleep(600).then(function(){
      return world.checkICheck(selector).then(function(){
        return world.sleep(100);
      });
    });
  };

  this.marcarSegundoAnelComoAtivo = function() {
    return world.execJavascript('window.scrollTo(0, 0);').then(function() {
      return world.getElement('li.addTab').click();
    });
  };

  this.selecionarAnel = function(numAnel) {
    return world.waitForOverlayDisappear().then(function() {
      return world.execJavascript('window.scrollTo(0, 0);');
    }).then(function() {
      return world.getElement('li[role="tab"]:not(.addTab):nth-child('+numAnel+')').click();
    });
  };

  this.selecionarTipoGrupoSemaforico = function(grupoSemaforico, tipo) {
    var index = tipo === 'Pedestre' ? 2 : 1;
    var xpathSelector = '//h3[text() = "'+grupoSemaforico+'"]/../../../..//select[@name="tipoGrupoSemaforico"]/option['+index+']';
    return world.sleep(600).then(function(){
      return world.getElementByXpath(xpathSelector).click().then(function(){
        return world.sleep(100);
      });
    });
  };

  this.clearVerdesConflitantes = function() {
    return world.getElements('i.sinal-verde-conflitante.ativo').then(function(elements) {
      var limit = elements.length / 2;
      for (var i = 0; i < limit; i++) {
        return world.scrollToDown().then(function() {
          elements[i].click();
        });
      }
    });
  };

  this.clearEstagiosAlternativos = function() {
    return world.getElements('i.transicao-proibida.ativo').then(function(elements) {
      for (var i = 0; i < elements.length; i++) {
        return world.scrollToDown().then(function() {
          elements[i].click();
        });
      }
    });
  };

  this.marcarConflito = function(g1, g2) {
    return world.sleep(600).then(function(){
      return world.execJavascript('return $(\'th:contains("'+g2+'")\').index() + 1').then(function(col) {
        return world.getElementByXpath('//td//strong[text() = "'+g1+'"]/../../td['+col+']').click();
      });
    });
  };

  this.marcarTransicao = function(e1, e2) {
    var row = parseInt(e2.substring(1));
    var col = parseInt(e1.substring(1)) + (row === 1 ? 2 : 1);
    return world.scrollToDown().then(function() {
      return world.getElement('tbody tr:nth-child('+row+') td:nth-child('+col+')').click();
    });
  };

  this.clickFecharModal = function() {
    return world.clickButton('div.modal button');
  };

  this.clickBotaoSimSweet = function() {
    return world.clickButton('div.sweet-alert button.confirm');
  };

  this.clicarBotao = function(text) {
    return world.sleep(600).then(function(){
      return world.findLinkByText(text).then(function(link) {
        return link.click();
      }).then(function() {
        return world.waitForAJAX();
      });
    });
  };

  this.isIndex = function() {
    return world.waitForByXpath('//h5[contains(text() = "Programação")]');
  };

  this.adicionarGruposSemaforicosAoAnel = function(numGrupos) {
    var promises = [];
    for (var i = 0; i < numGrupos; i++) {
      promises.push(function() {
        return world.scrollToDown().then(function() {
          return world.getElement('a[data-ng-click="adicionaGrupoSemaforico()"]').click();
        });
      });
    };

    return promises.reduce(function(previous, current) {
      return previous
      .then(world.waitForOverlayDisappear)
      .then(current);
    }, Promise.resolve());
  };

  this.marcarTempoAtrasoGrupo = function(value, field) {
    var baseSelector = 'influunt-knob[title="'+field+'"]';
    world.sleep(500);
    return world.getElement(baseSelector + ' p.knob-value').click().then(function() {
      return world.resetValue(baseSelector + ' input.rs-input', value);
    }).then(world.waitForAnimationFinishes);
  };

  this.marcarTempoAtrasoGrupoTransicao = function(tipoTransicao, value, posicao) {
    var baseSelector = 'li[data-ng-repeat="transicao in '+tipoTransicao+'"] div.initial-position div.knob-item[id="tv_'+posicao+'"] influunt-knob[title="Atraso de Grupo"]';
    world.sleep(500);
    return world.getElement(baseSelector + ' p.knob-value').click().then(function() {
      return world.resetValue(baseSelector + ' input.rs-input', value);
    }).then(world.waitForAnimationFinishes);
  };

  this.selecionarGrupo = function(grupo){
    return world.execJavascript('window.scrollTo(0, 0);').then(function() {
      return world.findLinkByText(grupo).click();
    });
  };

  this.confirmaSemConfiguracao = function(){
    var xpath = '//ins[contains(@class, "iCheck-helper")]';
    return world.scrollToDown().then(function() {
      return world.getElementByXpath(xpath).click();
    });
  };

  this.marcarTempoEntreVerdes = function(value, field, transicao) {
    transicao = transicao.split('-');
    var origem = transicao[0];
    var destino = transicao[1];
    var baseSelector = 'li[data-origem="'+origem+'"][data-destino="'+destino+'"] influunt-knob[title="'+field+'"]';
    return world.getElement(baseSelector + ' p.knob-value').click().then(function() {
      return world.resetValue(baseSelector + ' input.rs-input', value);
    }).then(world.waitForAnimationFinishes);
  };

  this.adicionarDetector = function(tipoDetector) {
    var indexTipo = tipoDetector === 'Pedestre' ? 1 : 2;
    return world.getElement('div.dropup button').click().then(function() {
      return world.getElement('div.dropup ul li:nth-child('+indexTipo+')').click();
    });
  };

  this.preencherCampoSMEECom123 = function() {
    return world.setValue('[name="numeroSMEE"]', '123');
  };

  this.preencherModificao = function(valor){
    return world.setValue('div.sweet-alert input', valor);
  };

  this.associarDetectorEstagio = function(detector, estagio) {
    return world.execJavascript('return $(\'th:contains("'+estagio+'")\').index() + 1').then(function(col) {
      return world.scrollToDown().then(function() {
        return world.getElementByXpath('//td//strong[text() = "'+detector+'"]/../../td['+col+']').click();
      });
    }).then(function() {
      return world.waitForOverlayDisappear();
    });
  };
};

module.exports = WizardControladorPage;
