'use strict';

describe('Service: handleValidations', function () {

  // load the service's module
  // beforeEach(module('influuntApp'));

  // instantiate service
  var handleValidations;
  beforeEach(inject(function (_handleValidations_) {
    handleValidations = _handleValidations_;
  }));

  it('Deve retornar um hash vazio caso não existam erros', function () {
    expect(handleValidations.handle()).toEqual({});
  });

  it('Deve retornar um objeto simples com as mensagens de erro por caminho devolvido.', function() {
    var errors = [{path: 'test', message: 'message-test'}];
    expect(handleValidations.handle(errors)).toEqual({test: ['message-test']});
  });

  it('Uma mensagem com path vazio será criada no atributo general. ', function() {
    var errors = [
      {path: '', message: 'msg-1'},
      {path: '', message: 'msg-2'},
    ];

    var expectativa = {general: ['msg-1', 'msg-2']};
    expect(handleValidations.handle(errors)).toEqual(expectativa);
  });

  it('Deve retornar um objeto composto com mensagens de erro com caminho composto.' +
    'Se o caminho denotar um array, as mensagens estarão abaixo do titulo "general"', function() {
    var errors = [
      {path: 'test[0]', message: 'message-0'},
      {path: 'test[0]', message: 'message-0.1'},
      {path: 'test[1]', message: 'message-1'}
    ];
    var expectativa = {
      test:[
        {general:['message-0','message-0.1']},
        {general:['message-1']}
      ]
    };
    expect(handleValidations.handle(errors)).toEqual(expectativa);
  });

  it('Deve retornar um objeto composto com mensagens de erro com caminho composto.' +
    'Se o caminho denotar um objeto, as mensagens serão organizadas conforme o caminho', function() {
      var errors = [
        {path: 'test[0].test_1', message: 'message-0'},
        {path: 'test[0].test_2', message: 'message-0.1'},
        {path: 'test[1].test_3', message: 'message-1'}
      ];

      var expectativa = {
        test:[
          {
            test_1: ['message-0'],
            test_2: ['message-0.1']
          },
          {
            test_3:['message-1']
          }
        ]
      };

      expect(handleValidations.handle(errors)).toEqual(expectativa);
  });

  it('Qualquer indice de array no caminho enviado deve ser processado', function() {
    var errors = [
      {path: 'test[9999].test_2', message: 'test_2'},
    ];

    var response = handleValidations.handle(errors);
    expect(response.test[0]).not.toBeDefined();
    expect(response.test[9999]).toBeDefined();
  });

  it('Toda mensagem de anel também deverá incluir um atributo "all", onde todas as mensagens serão reunidas',
    function() {
      var errors = [
        {path: 'aneis[1].test_1', message: 'teste_1'},
        {path: 'aneis[1].test_2', message: 'teste_2'},
        {path: 'aneis[1].test_3', message: 'teste_3'}
      ];

      var response = handleValidations.handle(errors);
      expect(response.aneis[1].all).toBeDefined();
  });

  describe('bugs', function () {
    it ('O objeto de erros de um anel pode ser um array erros ou somente um erro (string)', function() {
      var errors = [
        {path: 'aneis', message: 'teste_1'}
      ];
      var response = handleValidations.handle(errors);
      expect(response.aneis[0]).toBe('teste_1');
    });
  });

});
