'use strict';

describe('Service: dropzoneUtils', function () {

  // load the service's module
  beforeEach(module('influuntApp'));

  // instantiate service
  var dropzoneUtils;
  beforeEach(inject(function (_dropzoneUtils_) {
    dropzoneUtils = _dropzoneUtils_;
  }));

  it('should do something', function () {
    expect(!!dropzoneUtils).toBe(true);
  });

});
