'use strict';

/**
 * @ngdoc constant
 * @name influunt.APP_SETTINGS
 * @description
 * # APP_SETTINGS
 * Provides which id of settings the app must search for when self-configuring.
 *
 * #### Default values
 * * **theme:** Positive (blue scheme)
 * * **font_family:** [Roboto](http://www.fontsquirrel.com/fonts/roboto)
 */
angular.module('influuntApp')
  .constant('APP_SETTINGS',
    {
      'defaultLocale': 'pt-br'
    }
  );
