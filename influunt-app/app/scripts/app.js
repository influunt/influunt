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

    'environment',

    // API consume.
    'restangular',

    // ui
    'toaster',
    'oitozero.ngSweetAlert',
    'ui.bootstrap',
    'ui.sortable',
    'datatables',
    'google.places',
    '720kb.tooltips',

    // template cache engine.
    'templates',

    // i18n modules.
    'pascalprecht.translate',
    'tmh.dynamicLocale',

    //DatePicker
    'datePicker'
  ]);
