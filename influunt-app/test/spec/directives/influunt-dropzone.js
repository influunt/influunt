'use strict';

describe('Directive: influuntDropzone', function () {

  // load the directive's module
  beforeEach(module('influuntApp'));

  var scope;

  beforeEach(inject(function ($rootScope) {
    scope = $rootScope.$new();
  }));

  it('Deve criar um dropzone', inject(function($compile) {
    var element = angular.element('<form class="dropzone" id="my-awesome-dropzone" imagens-url="dados.imagensUrl" enctype="multipart/form-data" influunt-dropzone anel="currentAnel" on-success="associaImagemAoEstagio(upload, imagem)" on-delete="removeImagemDoEstagio(imagem)">');
    element = $compile(element)(scope);
    scope.$apply();

    expect(true).toBe(true);
  }));
});
