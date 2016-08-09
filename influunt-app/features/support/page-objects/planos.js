'use strict';

var worldObj = require('../world');

var world = new worldObj.World();

var PlanosPage = function () {

  this.cadastrarControlador = function() {
    return world.execSqlScript('features/support/scripts/planos/controlador.sql');
  };

  this.clicarBotaoPlanos = function() {
    return world.waitForOverlayDisappear().then(function() {
      return world.findLinkByText("Planos").click();
    });
  };

  this.isPlanos = function() {
    return world.waitFor('a[data-ng-click="adicionarPlano()"]');
  };

  // this.clicarBotaSalvar = fucntion() {
  //
  // };

  this.selecionarModoOperacao = function(modoOperacao) {
    var modos = ['Atuado', 'Coordenado', 'Isolado', 'Intermitente', 'Apagado'];
    var index = modos.indexOf(modoOperacao) + 1;
    return world.getElement('[name="modoOperacao"] option:nth-child('+index+')').click()
    // return world.selectOption('[name="modoOperacao"]', modoOperacao)
    .then(function() {
      return world.sleep(500);
    })
  };

  this.isDiagramaModo = function(grupo, modoOperacao) {
    var _this = this;
    var script = 'return $("div#visualization div.vis-left div.vis-label:contains(\''+grupo+'\')").index() + 1;';
    return world.execJavascript(script).then(function(indexGrupo) {
      switch(modoOperacao) {
        case 'Apagado':
          return _this.isDiagramaApagado(indexGrupo);
        case 'Intermitente':
          return _this.isDiagramaIntermitente(indexGrupo);
        case 'Operação Normal':
          return _this.isDiagramaOperacaoNormal(indexGrupo);
        default:
          throw new Error('Modo de operação não reconhecido: '+modoOperacao);
      }
    });
  };

  this.isDiagramaApagado = function(indexGrupo) {
    return world.waitFor('div#visualization div.vis-foreground div.vis-group:nth-child('+indexGrupo+') div.indicacao-apagado');
  };

  this.isDiagramaIntermitente = function(indexGrupo) {
    return world.waitFor('div#visualization div.vis-foreground div.vis-group:nth-child('+indexGrupo+') div.indicacao-intermitente');
  };

  this.isDiagramaOperacaoNormal = function(indexGrupo) {
    return world.waitFor('div#visualization div.vis-foreground div.vis-group:nth-child('+indexGrupo+') div.indicacao-vermelho').then(function() {
      return world.waitFor('div#visualization div.vis-foreground div.vis-group:nth-child('+indexGrupo+') div.indicacao-verde');
    });
  }



  this.isTabelaEntreVerdesHidden = function() {
    return world.waitForInverse('select[name="tabelaEntreverdes"]').then(true);
  };

  this.isTabelaEntreVerdesVisible = function() {
    return world.waitFor('select[name="tabelaEntreverdes"]').then(true);
  };

  this.isTempoDeCicloHidden = function() {
    return world.waitForInverse('influunt-knob[title="TEMPO DE CICLO"]');
  };

  this.isTempoDeCicloVisible = function() {
    return world.waitFor('influunt-knob[title="TEMPO DE CICLO"]');
  };

  this.isTempoDefasagemHidden = function() {
    return world.waitForInverse('influunt-knob[title="DEFASAGEM"]');
  };

  this.isTempoDefasagemVisible = function() {
    return world.waitFor('influunt-knob[title="DEFASAGEM"]');
  };

  this.clicarBotaoConfigurarEstagio = function(estagio) {
    return world.getElementByXpath('//ul[contains(@class, "planos")]//h4[contains(text(), "'+estagio+'")]/../button').click().then(function() {
      return world.waitFor('div#myModal.in');
    }).then(function() {
      return world.waitForAnimationFinishes('div#myModal');
    });
  };

  this.marcarValorConfig = function(field, value) {
    var baseSelector = 'influunt-knob[title="'+field.toUpperCase()+'"]';
    return world.getElement(baseSelector + ' p.knob-value').click().then(function() {
      return world.resetValue(baseSelector + ' input.rs-input', value);
    }).then(function() {
      return world.sleep(500);
    });
  };

  this.fecharCaixaConfiguracao = function() {
    return world.getElement('div#myModal div.modal-footer button').click();
  };

  this.clicarBotaoAdicionarNovoPlano = function() {
    return world.getElement('a[data-ng-click="adicionarPlano()"]').click();
  };



  this.trocarEstagiosDeLugar = function(estagio1, estagio2) {
    var elementFrom, elementTo, locationFrom, locationTo;
    return world.getElementByXpath('//ul[contains(@class, "planos")]//h4[contains(text(), "'+estagio1+'")]/..').then(function(element) {
      elementFrom = element;
      return world.getElementByXpath('//ul[contains(@class, "planos")]//h4[contains(text(), "'+estagio2+'")]/..')
    }).then(function(element) {
      elementTo = element;
      return elementFrom.getLocation();
    }).then(function(loc) {
      locationFrom = loc;
      return elementTo.getLocation();
    }).then(function(loc) {
      locationTo = loc;
      var diffX = (locationTo.x - locationFrom.x) * 1.5;
      console.log('diffX: ', diffX)
      return world.dragAndDrop(elementFrom, { x: diffX, y: 0 });
    });
  };







  // var LOGIN_PATH = '/login';

  // this.acessar = function() {
  //   return world.visit(LOGIN_PATH);
  // };

  // this.preencherUsuario = function(usuario) {
  //   return world.setValue('[name="usuario"]', usuario);
  // };

  // this.preencherSenha = function(senha) {
  //   return world.setValue('[name="senha"]', senha);
  // };

  // this.confirmarFormulario = function() {
  //   return world.setValue('[name="senha"]', world.webdriver.Key.ENTER);
  // };

  // this.preencherFormulario = function(usuario, senha) {
  //   var thisPage = this;
  //   return this.preencherUsuario(usuario).then(function() {
  //     return thisPage.preencherSenha(senha);
  //   }).then(function() {
  //     return thisPage.confirmarFormulario();
  //   });
  // };

  // this.campoUsuarioInvalido = function() {
  //   return world.waitFor('.usuario p.error-msg:not(.ng-hide)').then(function() {
  //     return world.getElements('.usuario p.error-msg:not(.ng-hide)');
  //   }).then(function(elements) {
  //     return elements.length === 1;
  //   });
  // };

  // this.campoSenhaInvalido = function() {
  //   return world.waitFor('.senha p.error-msg:not(.ng-hide)').then(function() {
  //     return world.getElements('.senha p.error-msg:not(.ng-hide)');
  //   }).then(function(elements) {
  //       return elements.length === 1;
  //   });
  // };

  // this.loginInvalido = function() {
  //   return world.waitFor('div[class^="sweet-alert"][class$="visible"]').then(function() {
  //     return world.getElement('div[class^="sweet-alert"] p[style="display: block;"]').getText();
  //   });
  // };

  // this.getUrl = function() {
  //   return world.getCurrentUrl();
  // };

  // this.isDashboard = function() {
  //   return world.waitFor('h2.ng-binding').then(function(){
  //     return world.getElement('h2.ng-binding').getText().then(function(text) {
  //       return text === 'Dashboard';
  //     });
  //   });
  // };
};

module.exports = PlanosPage;
