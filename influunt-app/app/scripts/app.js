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
    // 'ngRaven',
    'ngAnimate',
    'ngCookies',
    'ngResource',
    'ui.router',

    // angular-permissions: deve ser incluído
    // imediatamente após o ui.router
    'permission',
    'permission.ui',

    'ngSanitize',
    'ngTouch',

    'environment',

    // API consume.
    'restangular',

    // expiração de sessão por falta de uso.
    'ngIdle',

    // ui
    'oitozero.ngSweetAlert',
    'ui.bootstrap',
    'ui.sortable',
    '720kb.tooltips',

    // template cache engine.
    'templates',

    // i18n modules.
    'pascalprecht.translate',
    'tmh.dynamicLocale',

    //DatePicker
    'datePicker',

    //MorrisChart
    'angular.morris'
  ]);
