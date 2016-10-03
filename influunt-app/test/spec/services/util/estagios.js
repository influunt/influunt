'use strict';

describe('Service: utilEstagios', function () {

  // instantiate service
  var utilEstagios, lista;
  beforeEach(inject(function (_utilEstagios_) {
    utilEstagios = _utilEstagios_;
    lista = [1, 2, 3, 4, 5];
  }));

  describe('getProximoEstagio', function () {
    it('Deve retornar o elemento de index 1 se pesquisar o proximo ao indice 0', function() {
      var result = utilEstagios.getProximoEstagio(lista, 0);
      expect(result).toBe(lista[1]);
    });

    it('Deve retornar o elemento de index 0 se pesquisar o ultimo elemento da lista', function() {
      var result = utilEstagios.getProximoEstagio(lista, lista.length - 1);
      expect(result).toBe(lista[0]);
    });

    it('Deve retornar null se for pesquisado um indice maior que o tamanho do array', function() {
      var result = utilEstagios.getProximoEstagio(lista, 100);
      expect(result).toBe(null);
    });

    it('Deve retornar null se for pesquisado um indice menor que 0', function() {
      var result = utilEstagios.getProximoEstagio(lista, -1);
      expect(result).toBe(null);
    });
  });

  describe('getEstagioAnterior', function () {
    it('Deve retornar o elemento de index 0 se pesquisar o anterior ao indice 1', function() {
      var result = utilEstagios.getEstagioAnterior(lista, 1);
      expect(result).toBe(lista[0]);
    });

    it('Deve retornar o elemento de index 5 se pesquisar o anterior ao primeiro elemento da lista', function() {
      var result = utilEstagios.getEstagioAnterior(lista, 0);
      expect(result).toBe(lista[4]);
    });

    it('Deve retornar null se for pesquisado um indice maior que o tamanho do array', function() {
      var result = utilEstagios.getEstagioAnterior(lista, 100);
      expect(result).toBe(null);
    });

    it('Deve retornar null se for pesquisado um indice menor que 0', function() {
      var result = utilEstagios.getEstagioAnterior(lista, -1);
      expect(result).toBe(null);
    });

  });

});
