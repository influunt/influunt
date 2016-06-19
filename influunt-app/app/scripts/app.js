'use strict';

/**
 * @ngdoc overview
 * @name influuntApp
 * @description
 * # influuntApp
 *
 * Main module of the application.
 */
angular
  .module('influuntApp', [
    'ngAnimate',
    'ngCookies',
    'ngResource',
    'ui.router',
    'ngSanitize',
    'ngTouch',
    'ui.bootstrap',
    'datatables',

    'google.places',

    'environment',

    // API consume.
    'restangular',

    // ui
    'toaster',
    'oitozero.ngSweetAlert',
    // template cache engine.
    'templates',

    // i18n modules.
    'pascalprecht.translate',
    'tmh.dynamicLocale'
  ]);
