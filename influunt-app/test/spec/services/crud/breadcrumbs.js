'use strict';

describe('Service: crud/breadcrumbs', function () {

  // load the service's module
  // beforeEach(module('influuntApp'));

  // instantiate service
  var breadcrumbs;
  beforeEach(inject(function (_breadcrumbs_) {
    breadcrumbs = _breadcrumbs_;
  }));

  it ('Rotas filhas de rotas abstratas Devem possuir dois níveis de breadcrumbs', function() {
    var state = {
      'name': 'app.cidades',
      'data': {
        'title': 'cidades.titulo'
      }
    };

    var result = breadcrumbs.path(state);
    expect(result.length).toBe(2);
  });

  it('A rota resultante de uma abstrata deve ser igual à rota descrita em redirectTo', function() {
    var state = {
      'name': 'app',
      'abstract': true,
      'data': {
        'title': 'geral.dashboard',
        'redirectTo': 'app.main'
      }
    };

    var result = breadcrumbs.path(state);

    expect(result.length).toBe(1);
    expect(result[0].url).toBe(state.data.redirectTo);
  });

});
