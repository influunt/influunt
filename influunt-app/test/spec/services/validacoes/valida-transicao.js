'use strict';

describe('Service: validaTransicao', function () {
  // load the service's module
  // beforeEach(module('influuntApp'));

  var validaTransicao, estagios, objeto;
  beforeEach(inject(function (_validaTransicao_) {
    validaTransicao = _validaTransicao_;

    estagios = [{estagio: {idJson: 1}}, {estagio: {idJson: 2}}, {estagio: {idJson: 3}}];

    objeto = {
      estagios: [{posicao: 1, idJson: 1}, {posicao: 2, idJson: 2}, {posicao: 3, idJson: 3}],
      estagiosPlanos: [{estagio: {idJson: 1}},{estagio: {idJson: 2}},{estagio: {idJson: 3}}],
      transicoesProibidas: [ { origem: {idJson: 1}, destino: {idJson: 3} } ]
    };
  }));

  it('Retorna uma lista vazia quando não houver erros', function() {
    var response = validaTransicao.valida();
    expect(response).toEqual([]);
  });

  it('E1 não pode ficar na segunda posicao, pois E1-E3 é proibido', function() {
    var obj = objeto.estagiosPlanos;
    obj = [obj[1], obj[0], obj[2]];

    var response = validaTransicao.valida(obj, objeto);
    var expectation = [
      {mensagem: 'transição proibida de E1 para E3',origem: 1,destino: 2},
      {mensagem: 'transição proibida de E1 para E3',origem: 1,destino: 2}
    ];

    expect(response).toEqual(expectation);
  });

  it('E1 pode ficar na terceira posição, pois E3-E1 é uma transição válida, assim como E2-E3', function() {
    var obj = objeto.estagiosPlanos;
    obj = [obj[1], obj[2], obj[0]];

    var response = validaTransicao.valida(obj, objeto);
    var expectation = [];

    expect(response).toEqual(expectation);
  });

  it('A sequencia E3, E2, E1 é proibida, pois esta cria uma transição inválida entre E1-E3 pela lista circular',
    function() {
      var obj = objeto.estagiosPlanos;
      obj = [obj[2], obj[1], obj[0]];

      var response = validaTransicao.valida(obj, objeto);
      var expectation = [
        {mensagem: 'transição proibida de E1 para E3', origem: 2, destino: 0},
        {mensagem: 'transição proibida de E1 para E3', origem: 2, destino: 0}
      ];

      expect(response).toEqual(expectation);
    });
});
