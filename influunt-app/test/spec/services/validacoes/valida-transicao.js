'use strict';

describe('Service: validaTransicao', function () {
  // load the service's module
  beforeEach(module('influuntApp'));

  var validaTransicao, estagios, objeto;
  beforeEach(inject(function (_validaTransicao_) {
    validaTransicao = _validaTransicao_;

    estagios = [{estagio: {idJson: 1}}, {estagio: {idJson: 2}}, {estagio: {idJson: 3}}];

    objeto = {
      estagios: [{posicao: 1, idJson: 1}, {posicao: 2, idJson: 2}, {posicao: 3, idJson: 3}],
      transicoesProibidas: [ { origem: {idJson: 1}, destino: {idJson: 3} } ]
    };
  }));

  it('E1 não pode ficar na segunda posicao, pois E1-E3 é proibido', function() {
    var ui = { item: { sortable: { index: 0, dropindex: 1 } }};
    var response = validaTransicao.valida(ui, estagios, objeto);
    var expectation = 'transição proibida de E1 para E3';
    expect(response).toBe(expectation);
  });

  it('E1 pode ficar na terceira posição, pois E3-E1 é uma transição válida, assim como E2-E3', function() {
    var ui = { item: { sortable: { index: 0, dropindex: 2 } }};
    var response = validaTransicao.valida(ui, estagios, objeto);
    expect(response).toBe(null);
  });

  it('A sequencia E3, E2, E1 é proibida, pois esta cria uma transição inválida entre E1-E3 pela lista circular',
    function() {
      // reordenando para o estado antes da troca.
      estagios = [estagios[1], estagios[0], estagios[2]];
      var ui = { item: { sortable: { index: 0, dropindex: 2 } }};
      var response = validaTransicao.valida(ui, estagios, objeto);

      var expectation = 'transição proibida de E1 para E3';
      expect(response).toBe(expectation);
    });
});
