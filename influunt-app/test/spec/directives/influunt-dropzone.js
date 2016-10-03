'use strict';

describe('Directive: influuntDropzone', function () {

  var scope;

  beforeEach(inject(function ($rootScope) {
    scope = $rootScope.$new();
  }));

  it('Deve criar um dropzone', inject(function($compile) {
    var element = angular.element('<form class="dropzone" id="estagios" influunt-dropzone>');
    element = $compile(element)(scope);
    scope.$apply();

    expect(true).toBe(true);
  }));
});
