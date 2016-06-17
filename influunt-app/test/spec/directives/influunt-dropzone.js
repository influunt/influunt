'use strict';

describe('Directive: influuntDropzone', function () {

  // load the directive's module
  beforeEach(module('influuntApp'));

  var element,
    scope;

  beforeEach(inject(function ($rootScope) {
    scope = $rootScope.$new();
  }));

  xit('should make hidden element visible', inject(function ($compile) {
    element = angular.element('<influunt-dropzone></influunt-dropzone>');
    element = $compile(element)(scope);
    expect(element.text()).toBe('this is the influuntDropzone directive');
  }));
});
