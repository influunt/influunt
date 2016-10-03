'use strict';

describe('Filter: nomeEndereco', function () {

  // initialize a new instance of the filter before each test
  var nomeEndereco, endereco;
  beforeEach(inject(function ($filter) {
    nomeEndereco = $filter('nomeEndereco');
    endereco = {
      localizacao: 'texto 1',
      localizacao2: 'texto 2',
      alturaNumerica: '123',
      referencia: 'texto 3'
    };
  }));

  it('Deve retornar false se não houver dados sobre o endereco ou sobre a localização dele', function() {
    expect(nomeEndereco()).not.toBeTruthy();
    expect(nomeEndereco({})).not.toBeTruthy();
  });

  it('Deve retornar somente a localização caso somente ela seja definida', function() {
    delete endereco.localizacao2;
    delete endereco.alturaNumerica;
    delete endereco.referencia;
    expect(nomeEndereco(endereco)).toBe(endereco.localizacao);
  });

  it('Deve retornar "`localizacao1` com `localizacao2`" quando ambas forem informadas', function() {
    delete endereco.alturaNumerica;
    delete endereco.referencia;
    expect(nomeEndereco(endereco)).toBe(endereco.localizacao + ' com ' + endereco.localizacao2);
  });

  it('Deve retornar, "`localizacao1`, nº `alturaNumerica`" se ambos forem informados', function() {
    delete endereco.localizacao2;
    delete endereco.referencia;
    expect(nomeEndereco(endereco)).toMatch(endereco.localizacao + ', nº ' + endereco.alturaNumerica);
  });

  it('Deve retornar, "`localizacao1`. ref.: `referencia` se ambos forem informados', function() {
    delete endereco.localizacao2;
    delete endereco.alturaNumerica;
    expect(nomeEndereco(endereco)).toMatch(endereco.localizacao + '. ref.: ' + endereco.referencia);
  });

  it('Deve retornar um endereço completo, se todos os elementos forem informados', function() {
    var expectation = [
      endereco.localizacao, ', nº ', endereco.alturaNumerica, ' com ',
      endereco.localizacao2, '. ref.: ', endereco.referencia
    ].join('');
    expect(nomeEndereco(endereco)).toMatch(expectation);
  });
});
