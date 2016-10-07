'use strict';

describe('Service: crud/breadcrumbs', function () {

  // instantiate service
  var breadcrumbs;
  beforeEach(inject(function (_breadcrumbs_) {
    breadcrumbs = _breadcrumbs_;
  }));

  describe('paths', function() {
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

  describe('set/remove NomeEndereco', function() {
    beforeEach(inject(function (_$rootScope_) {
      this.$rootScope = _$rootScope_;
    }));

    it('setNomeEndereco deve colocar endereço no $rootScope', function() {
      breadcrumbs.setNomeEndereco('endereço');
      expect(this.$rootScope.controladorLocalizacao).toBe('endereço');
    });

    it('removeNomeEndereco deve setar enbdereço para null no $rootScope', function() {
      breadcrumbs.setNomeEndereco('endereço');
      breadcrumbs.removeNomeEndereco();
      expect(this.$rootScope.controladorLocalizacao).toBe(null);
    });
  });

});
