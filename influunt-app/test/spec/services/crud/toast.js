'use strict';

describe('Service: crud/toast', function () {

  // load the service's module
  beforeEach(module('influuntApp'));

  var toaster;

  // instantiate service
  var toast;
  beforeEach(inject(function (_toast_, _toaster_) {
    toast = _toast_;
    toaster = _toaster_;

    spyOn(toaster, 'pop');
  }));

  it('Deve exibir um toaster na tela quando o metodo sucesso for chamado.', function () {
    toast.success('teste');
    expect(toaster.pop).toHaveBeenCalled();
  });

  it('Deve exibir um toaster na tela quando o metodo erro for chamado.', function () {
    toast.error('teste');
    expect(toaster.pop).toHaveBeenCalled();
  });

});
