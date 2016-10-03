'use strict';

describe('Filter: imageSource', function () {

  var appRoot = 'base_url';

  // initialize a new instance of the filter before each test
  var imageSource;
  beforeEach(inject(function ($filter) {
    imageSource = $filter('imageSource');
  }));

  it('Deve retornar a url completa de um arquivo a partir do id passado ao filter', function() {
    var text = 'my-id';
    var result = imageSource(text);
    var expectation = appRoot + '/imagens/' + text;
    expect(result).toBe(expectation);
  });

  it('Não deverá retornar conteúdo algum caso nenhum valor seja enviado', function() {
    expect(imageSource()).toBeUndefined();
  });

});
