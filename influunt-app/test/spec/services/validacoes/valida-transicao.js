'use strict';

describe('Service: validaTransicao', function () {

  // load the service's module
  beforeEach(module('influuntApp'));

  // instantiate service
  var validaTransicao, estagios;
  beforeEach(inject(function (_validaTransicao_) {
    validaTransicao = _validaTransicao_;

    estagios = [
      {
        id: 1,
        posicao: 1,
        origemDeTransicoesProibidas: [{
          origem: {id: 1}, destino: {id: 3}
        }],
        destinoDeTransicoesProibidas: []
      },
      {
        id: 2,
        posicao: 2,
        origemDeTransicoesProibidas: [],
        destinoDeTransicoesProibidas: []
      },
      {
        id: 3,
        posicao: 3,
        origemDeTransicoesProibidas: [],
        destinoDeTransicoesProibidas: [{
          origem: {id: 1}, destino: {id: 3}
        }]
      }
    ]
  }));

  describe('Dado os estágios E1, E2, E3 onde é proibida a transicao E1-E3', function () {
    it('E1 não pode ficar na segunda posicao, pois E1-E3 é proibido', function() {
      var ui = { item: { sortable: { index: 0, dropindex: 1 } }};
      var response = validaTransicao.valida(ui, estagios);
      var expectation = 'transição proibida de E1 para E3';
      expect(response).toBe(expectation);
    });

    it('E1 pode ficar na terceira posição, pois E3-E1 é uma transição válida, assim como E2-E3', function() {
      var ui = { item: { sortable: { index: 0, dropindex: 2 } }};
      var response = validaTransicao.valida(ui, estagios);
      expect(response).toBe(null);
    });


    it('A sequencia E3, E2, E1 é proibida, pois esta cria uma transição inválida entre E1-E3 pela lista circular',
      function() {
        // reordenando para o estado antes da troca.
        estagios = [estagios[1], estagios[0], estagios[2]];
        var ui = { item: { sortable: { index: 0, dropindex: 2 } }};
        var response = validaTransicao.valida(ui, estagios);

        var expectation = 'transição proibida de E1 para E3';
        expect(response).toBe(expectation);
      });

  });

  // it('should do something', function () {
  //   expect(!!validaTransicao).toBe(true);
  // });

});
