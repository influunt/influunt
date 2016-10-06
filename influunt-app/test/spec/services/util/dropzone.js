'use strict';

describe('Service: dropzoneUtils', function () {
  // instantiate service
  var dropzoneUtils, dropZoneMock;
  beforeEach(inject(function (_dropzoneUtils_) {
    dropZoneMock = $('<div></div>');
    dropZoneMock.append('<div class="dropzone-area"></div>');
    dropZoneMock.append('<div class="dz-preview"></div>');
    dropzoneUtils = _dropzoneUtils_;
  }));

  describe('countFiles', function () {
    it('Deve manter a area de dropzone visivel caso não haja limite', function() {
      dropzoneUtils.countFiles(dropZoneMock);
      expect(dropZoneMock.find('.dropzone-area').css('display')).toBe('block');
    });

    it('Deve esconder a area de dropzone caso a quantidade de arquivos alcance o limite', function() {
      dropzoneUtils.countFiles(dropZoneMock, 1);
      expect(dropZoneMock.find('.dropzone-area').css('display')).toBe('none');
    });

    it('Deve manter a area de dropzone visivel caso a quantidade de arquivos não alcance o limite', function() {
      dropzoneUtils.countFiles(dropZoneMock, 2);
      expect(dropZoneMock.find('.dropzone-area').css('display')).toBe('block');
    });
  });

});
