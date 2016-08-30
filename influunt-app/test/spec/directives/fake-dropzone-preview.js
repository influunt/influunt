'use strict';

describe('Directive: fakeDropzonePreview', function () {

  // load the directive's module
  beforeEach(module('influuntApp'));

  var element,
    scope;

  beforeEach(inject(function ($rootScope) {
    scope = $rootScope.$new();
  }));

  // it('Deverá criar um elemento de preview do dropzone com os dados do anel enviado',
  //   inject(function($compile) {
  //     scope.aneis = [{
  //       idAnel: 'idAnel',
  //       nome: 'nome',
  //       source: 'source',
  //       estagios: [{id: 'id',imagem: {id: 'id',filename: 'filename'}}]
  //     }];
  //     element = angular.element('<form><fake-dropzone-preview><fake-dropzone-preview></form>');
  //     element = $compile(element)(scope);
  //     scope.$broadcast('influuntWizard.dropzoneOk');
  //     scope.$apply();

  //     var previews = $(element[0]).children('.dz-preview');

  //     expect(previews.length).toBe(1);
  //   }));
});
