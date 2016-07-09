'use strict';

var worldObj = require('../world');
var world = new worldObj.World();
var path = require('path');

var WizardControladorPage = function () {
  var INDEX_PATH = '/app/controladores';

  var novoControladorButton = 'button[ui-sref="app.wizard_controladores.dados_basicos"]';

  this.world = world;

  this.indexPage = function() {
    world.visit(INDEX_PATH);
    return world.waitFor(novoControladorButton);
  };

  this.clicarBotaoNovoControlador = function() {
    return world.clickButton(novoControladorButton);
  };

  this.isWizardPasso = function(passo) {
    switch(passo) {
      case 'Dados Básicos':
        return this.isWizardDadosBasicos();
      case 'Anéis':
        return this.isWizardAneis();
      case 'Associação':
        return this.isWizardAssociacao();
      case 'Verdes Conflitantes':
        return this.isWizardVerdesConflitantes();
      case 'Transições Proibidas':
        return this.isWizardTransicoesProibidas();
      default:
        throw new Error('Passo não encontrado: '+passo);
    }
  };

  this.isWizardDadosBasicos = function() {
    return world.waitFor('li[class*="wizard-steps"][class*="current"] a[step-id="dadosBasicos"]');
  };

  this.isWizardAneis = function() {
    return world.waitFor('li[class*="wizard-steps"][class*="current"] a[step-id="aneis"]');
  };

  this.isWizardAssociacao = function() {
    return world.waitFor('li[class*="wizard-steps"][class*="current"] a[step-id="associacao"]');
  };

  this.isWizardVerdesConflitantes = function() {
    return world.waitFor('li[class*="wizard-steps"][class*="current"] a[step-id="verdesConflitantes"]');
  };

  this.isWizardTransicoesProibidas = function() {
    return world.waitFor('li[class*="wizard-steps"][class*="current"] a[step-id="transicoesProibidas"]');
  };

  this.clicarBotaoProximoPasso = function() {
    return world.findLinkByText('Próximo').click();
  };

  this.getErrorMessageFor = function(campo) {
    return world.waitFor('[name="'+campo+'"] + p[class*="error-msg"]').then(function() {
      return world.getElement('[name="'+campo+'"] + p[class*="error-msg"]').getText();
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
      case 'Anéis':
        return this.errorMessagesAneis();
      case 'Associação':
        return this.errorMessagesAssociacao();
      case 'Verdes Conflitantes':
        return this.errorMessagesVerdesConflitantesSemMarcacao();
      default:
        throw new Error('Passo não encontrado: '+passo);
    }
  };

  this.errorMessagesDadosBasicos = function() {
    var thisWizardPage = this;
    var messages = [];
    return thisWizardPage.getErrorMessageFor('area').then(function(msg) {
      messages.push({campo: 'area', msg: msg});
      return thisWizardPage.getErrorMessageFor('localizacao');
    }).then(function(msg) {
      messages.push({campo: 'localizacao', msg: msg});
      return thisWizardPage.getErrorMessageFor('latitude');
    }).then(function(msg) {
      messages.push({campo: 'latitude', msg: msg});
      return thisWizardPage.getErrorMessageFor('longitude');
    }).then(function(msg) {
      messages.push({campo: 'longitude', msg: msg});
      return thisWizardPage.getErrorMessageFor('modelo');
    }).then(function(msg) {
      messages.push({campo: 'modelo', msg: msg});
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
    return thisWizardPage.getErrorMessageFor('latitude').then(function(msg) {
      messages.push([msg, 'Latitude deve ser informada']);
      return thisWizardPage.getErrorMessageFor('longitude');
    }).then(function(msg) {
      messages.push([msg, 'Longitude deve ser informada']);
      return new Promise(function (resolve, reject) {
        for (var i = 0; i < messages.length; i++) {
          if (messages[i][0] !== messages[i][1]) {
            reject('expected "'+messages[i][0]+'" to be "'+messages[i][1]+'"');
          }
        }
        resolve(true);
      });
    }).then(function() {
      return thisWizardPage.getAlertErrorMessages().then(function(text) {
        return new Promise(function(resolve, reject) {
          if (text.match(/Um anel ativo deve ter ao menos dois estágios e no máximo o limite do modelo do controlador/) !== null) {
            if (text.match(/Esse anel deve ter no mínimo 2 grupos semáforicos/) !== null) {
              resolve(true);
            } else {
              reject('required error message not found: "Esse anel deve ter no mínimo 2 grupos semáforicos"');
            }
          } else {
            reject('required error message not found: "Um anel ativo deve ter ao menos dois estágios e no máximo o limite do modelo do controlador"');
          }
        });
      });
    });
  };

  this.errorMessagesAssociacao = function() {
    var thisWizardPage = this;
    return thisWizardPage.getAlertErrorMessages().then(function(text) {
      return new Promise(function(resolve, reject) {
        if (text.match(/Quantidade de grupos semáforicos de pedestre diferente do definido no anel/) !== null) {
          if (text.match(/Quantidade de grupos semáforicos veiculares diferente do definido no anel/) !== null) {
            resolve(true);
          } else {
            reject('required error message not found: "Quantidade de grupos semáforicos veiculares diferente do definido no anel"');
          }
        } else {
          reject('required error message not found: "Quantidade de grupos semáforicos de pedestre diferente do definido no anel"');
        }
      });
    });
  };

  this.errorMessagesVerdesConflitantesSemMarcacao = function() {
    return this.getAlertErrorMessages().then(function(text) {
      return new Promise(function(resolve, reject) {
        if (text.match(/G1: Esse grupo semafórico deve ter ao menos um verde conflitante/) !== null) {
          if (text.match(/G2: Esse grupo semafórico deve ter ao menos um verde conflitante/) !== null) {
            if (text.match(/G3: Esse grupo semafórico deve ter ao menos um verde conflitante/) !== null) {
              if (text.match(/G4: Esse grupo semafórico deve ter ao menos um verde conflitante/) !== null) {
                resolve(true);
              } else {
                reject('required error message not found: "G4: Esse grupo semafórico deve ter ao menos um verde conflitante"');
              }
            } else {
              reject('required error message not found: "G3: Esse grupo semafórico deve ter ao menos um verde conflitante"');
            }
          } else {
            reject('required error message not found: "G2: Esse grupo semafórico deve ter ao menos um verde conflitante"');
          }
        } else {
          reject('required error message not found: "G1: Esse grupo semafórico deve ter ao menos um verde conflitante"');
        }
      });
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

  this.errorMessagesVerdesConflitantesComConflito = function() {
    return this.getAlertErrorMessages().then(function(text) {
      return new Promise(function(resolve, reject) {
        if (text.match(/G1: Esse grupo semafórico não pode ter verde conflitante com grupo semafórico de outro anel/) !== null) {
          if (text.match(/G2: Esse grupo semafórico não pode ter verde conflitante com grupo semafórico de outro anel/) !== null) {
            if (text.match(/G3: Esse grupo semafórico não pode ter verde conflitante com grupo semafórico de outro anel/) !== null) {
              if (text.match(/G4: Esse grupo semafórico não pode ter verde conflitante com grupo semafórico de outro anel/) !== null) {
                resolve(true);
              } else {
                reject('required error message not found: "G4: Esse grupo semafórico não pode ter verde conflitante com grupo semafórico de outro anel"');
              }
            } else {
              reject('required error message not found: "G3: Esse grupo semafórico não pode ter verde conflitante com grupo semafórico de outro anel"');
            }
          } else {
            reject('required error message not found: "G2: Esse grupo semafórico não pode ter verde conflitante com grupo semafórico de outro anel"');
          }
        } else {
          reject('required error message not found: "G1: Esse grupo semafórico não pode ter verde conflitante com grupo semafórico de outro anel"');
        }
      });
    });
  };

  this.cadastrarEntidadesDadosBasicos = function() {
    return world.execSqlScript('features/support/scripts/controladores/entidades_dados_basicos.sql');
  };

  this.adicionarImagensEstagios = function(qtde) {
    var filePath = path.join(__dirname, '../resources/ubuntu.jpeg');
    var promises = [];
    for(var i = 0; i < qtde; i++) {
      promises.push(world.dropzoneUpload(filePath));
    }

    return Promise.all(promises).then(function() {
      return world.waitForAJAX();
    });
  };

  this.selecionarEstagio = function(estagio) {
    var estagioSelector = 'li[data-ng-repeat="(indexEstagio, estagio) in currentAnel.estagios"]:nth-child('+estagio.substring(1)+') img';
    return world.getElement(estagioSelector).click();
  };

  this.selecionaEstagioAlternativoParaTransicaoProibida = function(transicao, estagio) {
    var transicaoSelector = '#estagio-alternativo-' + transicao + ' select';
    return world.selectOption(transicaoSelector, estagio);
  };

  this.associarGrupoSemaforicoEstagio = function(grupo, estagio) {
    var estagioSelector = 'li[data-ng-repeat="(indexEstagio, estagio) in currentAnel.estagios"]:nth-child('+estagio.substring(1)+')';
    var inputSelector = 'label > p:contains("'+grupo+'") + div > input';
    return this.selecionarEstagio(estagio).then(function() {
      return world.checkICheck('\'' + estagioSelector + ' ' + inputSelector + '\'');
    });
  };

  this.marcarSegundoAnelComoAtivo = function() {
    return world.checkICheck('$(\'[type="checkbox"]\')[1]');
  };

  this.selecionarSegundoAnel = function() {
    return world.getElement('li[data-ng-repeat="anel in aneis"]:nth-child(2) label').click();
  };

  this.selecionarTipoGrupoSemaforico = function(tipoGrupoSemaforico) {
    var index = tipoGrupoSemaforico === 'Pedestre' ? 3 : 2;
    return world.getElement('select[name="tipoGrupoSemaforico"] option:nth-child('+index+')').click();
  };

  this.clearVerdesConflitantes = function() {
    return world.getElements('i.sinal-verde-conflitante.ativo').then(function(elements) {
      var limit = elements.length / 2;
      for (var i = 0; i < limit; i++) {
        elements[i].click();
      }
    });
  };

  this.clearEstagiosAlternativos = function() {
    return world.getElements('i.transicao-proibida.ativo').then(function(elements) {
      for (var i = 0; i < elements.length; i++) {
        elements[i].click();
      }
    });
  };

  this.marcarConflito = function(g1, g2) {
    var row = parseInt(g1.substring(1));
    var col = parseInt(g2.substring(1)) + (row % 2 === 0 ? 1 : 2);
    return world.getElement('tbody tr:nth-child('+row+') td:nth-child('+col+')').click();
  };

  this.marcarTransicao = function(e1, e2) {
    var row = parseInt(e2.substring(1));
    var col = parseInt(e1.substring(1)) + (row === 1 ? 2 : 1);
    return world.getElement('tbody tr:nth-child('+row+') td:nth-child('+col+')').click();
  };

  this.clicarBotaoFinalizar = function() {
    return world.findLinkByText('Finalizar').then(function(link) {
      return link.click();
    }).then(function() {
      return world.waitForAJAX();
    });
  };

  this.isIndex = function() {
    return world.waitFor('li[ng-repeat^="controlador in lista"]');
  };

};

module.exports = WizardControladorPage;
