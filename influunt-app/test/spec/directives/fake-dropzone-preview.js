'use strict';

describe('Directive: fakeDropzonePreview', function () {

  var element,
      scope,
      $compile,
      deferred;

  beforeEach(inject(function (_$compile_, $rootScope, $q) {
    $compile = _$compile_;
    scope = $rootScope.$new();

    scope.anel = {posicao: 1};
    scope.imagens = [
      { nomeImagem: 'nomeImagem_1', url: 'url_1', idJson: 'idJson_1' }
    ];
    scope.onDelete = function() {};

    deferred = $q.defer();
    spyOn(scope, 'onDelete').and.returnValue(deferred.promise);

    element = angular.element(
      '<form><fake-dropzone-preview anel="anel" imagens="imagens" on-delete="onDelete"></fake-dropzone-preview></form>'
    );
    element = $compile(element)(scope);
    scope.$apply();
  }));

  it('Deve criar imagens para cada uma das imagens do objeto de "imagens"', function() {
    expect($(element).find('.dz-preview').length).toBe(scope.imagens.length);
  });

  it('Deve criar previews para uma coleção ou somente uma imagem', function() {
    scope.imagens = scope.imagens[0];
    scope.$apply();
    expect($(element).find('.dz-preview').length).toBe(1);
  });

  describe('remover imagem', function () {
    beforeEach(function() {
      $(element).find('.dz-preview').find('.dz-remove').trigger('click');
      scope.$apply();
    });

    it('Deve chamar a função registrada em on-delete', function() {
      expect(scope.onDelete).toHaveBeenCalled();
    });

    it('Deve remover um elemento do dropzone se a resposta de "onDelete" for true', function() {
      scope.imagens[0].dropzoneId = 'croqui_0';
      spyOn(Dropzone, 'forElement').and.returnValue({files: scope.imagens});

      deferred.resolve(true);
      scope.$apply();
      expect($(element).find('.dz-preview').length).toBe(0);
    });

    it('Deve manter os fakes inalterados se a resposta de "onDelete" for false', function() {
      deferred.resolve(false);
      scope.$apply();
      expect($(element).find('.dz-preview').length).toBe(scope.imagens.length);
    });

  });

});
