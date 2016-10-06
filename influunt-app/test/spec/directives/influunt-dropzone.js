'use strict';

describe('Directive: influuntDropzone', function () {

  var scope, element;

  beforeEach(inject(function ($rootScope, $compile) {
    scope = $rootScope.$new();

    element = angular.element('<form class="dropzone" id="estagios" influunt-dropzone>');
    element = $compile(element)(scope);
    scope.$apply();
  }));

  it('Deve criar um dropzone', function() {
    expect(element.hasClass('dz-clickable')).toBeTruthy();
  });
});
