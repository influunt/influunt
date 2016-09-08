'use strict';

beforeEach(module('influuntApp', function(RestangularProvider, $provide) {
  RestangularProvider.setBaseUrl('');

  /**
   * Este bloco evita que o blockUI seja chamado em alguns testes.
   *
   * @return     {Object}  { description_of_the_return_value }
   */
  var blockuiInterceptor = function() {
    return {};
  };

  $provide.factory('blockuiInterceptor', blockuiInterceptor);
}));
