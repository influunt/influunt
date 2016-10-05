'use strict';

describe('Service: validacoesAneis', function () {

  // instantiate service
  var validacoesAneis, controlador, anel;
  beforeEach(inject(function (_validacoesAneis_) {
    validacoesAneis = _validacoesAneis_;

    // Aneis e controladores no formato esperado da API
    controlador = {'id':1,'descricao':'opa 1','numeroSMEE':'oa 1','firmware':'123','latitude':-23.55187083133668,'longitude':-46.664085388183594,'dataCriacao':'19/06/2016 16:12:41','dataAtualizacao':'19/06/2016 16:14:20','CLC':'1.000.0001','area':{'id':'3d5da070-8dcb-4fed-a8b2-ca8aa8d3c5f5','descricao':1,'dataCriacao':'19/06/2016 16:10:56','dataAtualizacao':'19/06/2016 16:10:56','cidade':{'id':'584a3016-2739-4ea5-802b-9c2e702772cc','nome':'Belo Horizonte','dataCriacao':'19/06/2016 16:10:28','dataAtualizacao':'19/06/2016 16:10:28','areas':[{'id':'3d5da070-8dcb-4fed-a8b2-ca8aa8d3c5f5','descricao':1},{'id':'cdbb59a8-d4e1-4bcf-9cdf-062e0324d3e3','descricao':2}]},'limites':[{'id':'5a53316c-9130-470f-97a1-7c7e5ee97e78','latitude':'22.0','longitude':'22.0'},{'id':'5c86d9cc-1918-432a-81a0-777066f81267','latitude':'12.0','longitude':'21.0'},{'id':'91c9fb91-d9d5-487e-b463-27dd2da81b25','latitude':'21.0','longitude':'12.0'}]},'modelo':{'id':'77b632c9-565e-4fd4-b8df-a51115412518','descricao':'opa 1','dataCriacao':'19/06/2016 16:12:11','dataAtualizacao':'19/06/2016 16:12:11','fabricante':{'id':'c581e0ce-5243-4756-b8af-e87dfb636554','nome':'fab 1','dataCriacao':'19/06/2016 16:12:03','dataAtualizacao':'19/06/2016 16:12:11','modelos':[{'id':'77b632c9-565e-4fd4-b8df-a51115412518','descricao':'opa 1','configuracao':{'id':'2ae77195-ee3e-4f88-b4b2-5d49e70f6780','descricao':'opa 1','limiteEstagio':4,'limiteGrupoSemaforico':4,'limiteAnel':4,'limiteDetectorPedestre':4,'limiteDetectorVeicular':8,'dataCriacao':'19/06/2016 16:11:54','dataAtualizacao':'19/06/2016 16:11:54'}}]},'configuracao':{'id':'2ae77195-ee3e-4f88-b4b2-5d49e70f6780','descricao':'opa 1','limiteEstagio':4,'limiteGrupoSemaforico':4,'limiteAnel':4,'limiteDetectorPedestre':4,'limiteDetectorVeicular':8,'dataCriacao':'19/06/2016 16:11:54','dataAtualizacao':'19/06/2016 16:11:54'}},'aneis':[{'id':'2de5bfdd-1e54-47a0-bdc9-8efc528744a3','ativo':false,'quantidadeGrupoPedestre':0,'quantidadeGrupoVeicular':0,'quantidadeDetectorPedestre':0,'quantidadeDetectorVeicular':0,'dataCriacao':'19/06/2016 16:14:20','dataAtualizacao':'19/06/2016 16:14:20','movimentos':[],'gruposSemaforicos':[]},{'id':'31ad7971-8985-4fac-9579-489474d71005','ativo':false,'quantidadeGrupoPedestre':0,'quantidadeGrupoVeicular':0,'quantidadeDetectorPedestre':0,'quantidadeDetectorVeicular':0,'dataCriacao':'19/06/2016 16:14:20','dataAtualizacao':'19/06/2016 16:14:20','movimentos':[],'gruposSemaforicos':[]},{'id':'37909475-ff5c-47e8-a264-2b828f01db81','descricao':'asda','ativo':true,'posicao':1,'latitude':-23.5484875099175,'longitude':-46.66314125061035,'quantidadeGrupoPedestre':1,'quantidadeGrupoVeicular':1,'quantidadeDetectorPedestre':1,'quantidadeDetectorVeicular':1,'dataCriacao':'19/06/2016 16:14:20','dataAtualizacao':'19/06/2016 16:14:20','movimentos':[{'id':'731f4e42-fa46-4b81-a593-66d2dbf6a6a8','imagem':{'id':'1d830fc4-9d6b-4723-9e12-688c264b9ce0','filename':'12321359_986438248070903_1173574894423312875_n.jpg','contentType':'image/jpeg','dataCriacao':'19/06/2016 16:13:00','dataAtualizacao':'19/06/2016 16:13:00'},'estagio':{'id':'ee135911-9de5-46ea-9384-668c41c5ba4a','demandaPrioritaria':false,'dataCriacao':'19/06/2016 16:14:20','dataAtualizacao':'19/06/2016 16:14:20'},'dataCriacao':'19/06/2016 16:14:20','dataAtualizacao':'19/06/2016 16:14:20'},{'id':'e24ff439-82b1-4b71-bf8a-ff56e347ac61','imagem':{'id':'88dcc8fc-f336-44c1-8523-e53fd3172054','filename':'12963901_10154615163486840_8455352346796368737_n.jpg','contentType':'image/jpeg','dataCriacao':'19/06/2016 16:13:00','dataAtualizacao':'19/06/2016 16:13:00'},'estagio':{'id':'c2902225-efcf-43b2-a587-e20fc02cb440','demandaPrioritaria':false,'dataCriacao':'19/06/2016 16:14:20','dataAtualizacao':'19/06/2016 16:14:20'},'dataCriacao':'19/06/2016 16:14:20','dataAtualizacao':'19/06/2016 16:14:20'}],'gruposSemaforicos':[]},{'id':'fd1e98cf-0144-4a90-80f5-79991c2780dd','ativo':false,'quantidadeGrupoPedestre':0,'quantidadeGrupoVeicular':0,'quantidadeDetectorPedestre':0,'quantidadeDetectorVeicular':0,'dataCriacao':'19/06/2016 16:14:20','dataAtualizacao':'19/06/2016 16:14:20','movimentos':[],'gruposSemaforicos':[]}],'gruposSemaforicos':[]};
    anel = {'id':'37909475-ff5c-47e8-a264-2b828f01db81','descricao':'asda','ativo':true,'posicao':1,'latitude':-23.5484875099175,'longitude':-46.66314125061035,'quantidadeGrupoPedestre':1,'quantidadeGrupoVeicular':1,'quantidadeDetectorPedestre':1,'quantidadeDetectorVeicular':1,'dataCriacao':'19/06/2016 16:14:20','dataAtualizacao':'19/06/2016 16:14:20','movimentos':[{'id':'731f4e42-fa46-4b81-a593-66d2dbf6a6a8','imagem':{'id':'1d830fc4-9d6b-4723-9e12-688c264b9ce0','filename':'12321359_986438248070903_1173574894423312875_n.jpg','contentType':'image/jpeg','dataCriacao':'19/06/2016 16:13:00','dataAtualizacao':'19/06/2016 16:13:00'},'estagio':{'id':'ee135911-9de5-46ea-9384-668c41c5ba4a','demandaPrioritaria':false,'dataCriacao':'19/06/2016 16:14:20','dataAtualizacao':'19/06/2016 16:14:20'},'dataCriacao':'19/06/2016 16:14:20','dataAtualizacao':'19/06/2016 16:14:20'},{'id':'e24ff439-82b1-4b71-bf8a-ff56e347ac61','imagem':{'id':'88dcc8fc-f336-44c1-8523-e53fd3172054','filename':'12963901_10154615163486840_8455352346796368737_n.jpg','contentType':'image/jpeg','dataCriacao':'19/06/2016 16:13:00','dataAtualizacao':'19/06/2016 16:13:00'},'estagio':{'id':'c2902225-efcf-43b2-a587-e20fc02cb440','demandaPrioritaria':false,'dataCriacao':'19/06/2016 16:14:20','dataAtualizacao':'19/06/2016 16:14:20'},'dataCriacao':'19/06/2016 16:14:20','dataAtualizacao':'19/06/2016 16:14:20'}],'gruposSemaforicos':[]};
  }));

  it ('Deve considerar o conjunto de aneis inválidos caso não haja aneis ou controlador nos parâmetros', function() {
    var result = validacoesAneis.valida();
    expect(result).toBe(false);
  });

  it('Deve considerar um anel inválido caso não tenha os campos requeridos.', function() {
    anel.descricao = null;
    var result = validacoesAneis.valida([anel], controlador);
    expect(result).toBe(false);
  });

  it('Deve considerar um anel inválido caso não tenha ao menos um grupo semafórico definido', function() {
    anel.quantidadeGrupoPedestre = null;
    anel.quantidadeGrupoVeicular = null;

    var result = validacoesAneis.valida([anel], controlador);
    expect(result).toBe(false);
  });

  it('Deve considerar um anel invalido caso a quantidade de grupos seja maior que' +
    'aquela suportada pelo controlador', function() {
      anel.quantidadeGrupoPedestre = 10;
      anel.quantidadeGrupoVeicular = 10;
      controlador.modelo.configuracao.limiteGrupoSemaforico = 1;
      var result = validacoesAneis.valida([anel], controlador);

      expect(result).toBe(false);
    });

  it('Deve retornar uma mensagem de validacao caso haja problemas com a quantidade de grupos semaforicos',
    function() {
      anel.quantidadeGrupoPedestre = 10;
      anel.quantidadeGrupoVeicular = 10;
      controlador.modelo.configuracao.limiteGrupoSemaforico = 1;
      validacoesAneis.valida([anel], controlador);
      var result = validacoesAneis.retornaMensagensValidacao([anel]);

      expect(result.length).toBe(1);
    });

  it('Deve considerar um anel invalido caso a quantidade de detectores veicular' +
     'seja maior que aquela suportada pelo controlador', function() {
        anel.quantidadeDetectorVeicular = 10;
        controlador.modelo.configuracao.limiteDetectorVeicular = 1;
        var result = validacoesAneis.valida([anel], controlador);

        expect(result).toBe(false);
      });

  it('Deve retornar uma mensagem de validacao caso haja problemas com a quantidade de detectores veicular',
    function() {
      anel.quantidadeDetectorVeicular = 10;
      controlador.modelo.configuracao.limiteDetectorVeicular = 1;
      validacoesAneis.valida([anel], controlador);
      var result = validacoesAneis.retornaMensagensValidacao([anel]);

      expect(result.length).toBe(1);
    });

  it('Deve considerar um anel invalido caso a quantidade de detectores de pedestres' +
     'seja maior que aquela suportada pelo controlador', function() {
        anel.quantidadeDetectorPedestre = 10;
        controlador.modelo.configuracao.limiteDetectorPedestre = 1;
        var result = validacoesAneis.valida([anel], controlador);

        expect(result).toBe(false);
      });

  it('Deve retornar uma mensagem de validacao caso haja problemas com a quantidade de detectores de pedestres',
    function() {
      anel.quantidadeDetectorPedestre = 10;
      controlador.modelo.configuracao.limiteDetectorPedestre = 1;
      validacoesAneis.valida([anel], controlador);
      var result = validacoesAneis.retornaMensagensValidacao([anel]);

      expect(result.length).toBe(1);
    });

  it('Deve validar um anel com as configuracoes corretas', function() {
    var result = validacoesAneis.valida([anel], controlador);
    expect(result).toBe(true);
  });

  it('Caso o anel seja válido, não deve ser retornada nenhuma mensagem de validacao', function() {
    validacoesAneis.valida([anel], controlador);
    var result = validacoesAneis.retornaMensagensValidacao([anel]);
    expect(result.length).toBe(0);
  });

});
