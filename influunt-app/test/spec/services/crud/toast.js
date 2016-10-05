'use strict';

describe('Service: crud/toast', function () {

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

  it('Deve exibir um toaster na tela quando o metodo alerta for chamado.', function () {
    toast.warn('teste');
    expect(toaster.pop).toHaveBeenCalled();
  });

  it('Deve exibir um toaster na tela quando o metodo erro for chamado.', function () {
    toast.error('teste');
    expect(toaster.pop).toHaveBeenCalled();
  });

});
