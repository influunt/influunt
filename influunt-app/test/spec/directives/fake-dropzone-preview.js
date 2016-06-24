'use strict';

describe('Directive: fakeDropzonePreview', function () {

  // load the directive's module
  beforeEach(module('influuntApp'));

  var element,
    scope;

  beforeEach(inject(function ($rootScope) {
    scope = $rootScope.$new();
  }));

  it('Deverá criar um elemento de preview do dropzone com os dados do anel enviado',
    inject(function($compile, $timeout) {
      scope.aneis = [{
        id_anel: 'id_anel',
        nome: 'nome',
        source: 'source',
        estagios: [{id: 'id',imagem: {id: 'id',filename: 'filename'}}]
      }];
      element = angular.element('<form><fake-dropzone-preview aneis="aneis"><fake-dropzone-preview></form>');
      element = $compile(element)(scope);
      scope.$apply();

      $timeout.flush();
      $timeout.verifyNoPendingTasks();

      var previews = $(element[0]).children('.dz-preview');

      expect(previews.length).toBe(1);
    }));
});
