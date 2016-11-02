'use strict';

describe('Service: influuntAlert', function () {

  var SweetAlert;

  // instantiate service
  var influuntAlert;
  beforeEach(inject(function (_influuntAlert_, _SweetAlert_) {
    influuntAlert = _influuntAlert_;
    SweetAlert = _SweetAlert_;

    spyOn(SweetAlert, 'swal');
  }));

  it('Deve exibir um alert com sucesso.', function () {
    var title = 'Good job!';
    var text =  'You clicked the button!';

    influuntAlert.success(title, text);
    expect(SweetAlert.swal).toHaveBeenCalled();
  });
});
