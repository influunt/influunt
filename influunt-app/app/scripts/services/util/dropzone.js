'use strict';

/**
 * @ngdoc service
 * @name influuntApp.dropzoneUtils
 * @description
 * # dropzoneUtils
 * Factory in the influuntApp.
 */
angular.module('influuntApp')
  .factory('dropzoneUtils', function () {

    var countFiles = function(element, maxFiles) {
      maxFiles = maxFiles || Infinity;
      var thisDropzone = $(element);
      var files = thisDropzone.find('.dz-preview:not(.hide)');

      return files.length >= maxFiles ? thisDropzone.find('.dropzone-area').hide() : thisDropzone.find('.dropzone-area').show();
    };

    return {
      countFiles: countFiles
    };
  });
