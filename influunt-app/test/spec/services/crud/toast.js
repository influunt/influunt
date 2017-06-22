'use strict';

describe('Service: crud/toast', function () {


  // instantiate service
  var toast;
  beforeEach(inject(function (_toast_) {
    toast = _toast_;

    spyOn(toastr, 'success');
    spyOn(toastr, 'warning');
    spyOn(toastr, 'error');
  }));

  it('Deve exibir um toastr na tela quando o metodo sucesso for chamado.', function () {
    toast.success('teste');
    expect(toastr.success).toHaveBeenCalled();
  });

  it('Deve exibir um toastr na tela quando o metodo alerta for chamado.', function () {
    toast.warn('teste');
    expect(toastr.warning).toHaveBeenCalled();
  });

  it('Deve exibir um toastr na tela quando o metodo erro for chamado.', function () {
    toast.error('teste');
    expect(toastr.error).toHaveBeenCalled();
  });

});
