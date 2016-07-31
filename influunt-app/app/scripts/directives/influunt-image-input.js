'use strict';

/**
 * @ngdoc directive
 * @name influuntApp.directive:influuntImageInput
 * @description
 * # influuntImageInput
 */
angular.module('influuntApp')
  .directive('influuntImageInput', ['$http', function ($http) {
    return {
      restrict: 'E',
      scope: {
        uploadUrl: '=',
        onUpload: '&',
        onUploadError: '&'
      },
      templateUrl: 'views/directives/influunt-image-input.html',
      link: function (scope, element) {

        var uploadImage = function(e) {
          var imagem = element.find('input')[0].files[0]
          var formData = new FormData();
          formData.append('imagem', imagem);
          $.ajax({
            url: scope.uploadUrl,
            method: 'POST',
            data: formData,
            contentType: false,
            processData: false,
            headers: {
              authToken: localStorage.token
            },
            success: onSuccess,
            error: onError
          })
        };

        var onSuccess = function(response) {
          if (angular.isFunction(scope.onUpload)) {
            scope.$apply(function() {
              scope.onUpload()(response);
            })
          }
        };

        var onError = function(response) {
          if (angular.isFunction(scope.onUploadError)) {
            scope.onUploadError()(response);
          }
        };

        $(element).on('change', uploadImage)
      }
    };
  }]);
