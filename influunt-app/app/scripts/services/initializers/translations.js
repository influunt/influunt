'use strict';

var LOCALE_LOCATION_PATTERN = 'json/locales/angular-locale_{{locale}}.js';

/**
 * @ngdoc run
 * @name influuntApp
 * @description
 * # I18N configuration
 * Inicialização dos componentes de internacionalização do projeto (i18n)
 *
 * Este módulo depende dos pacotes de localização de [angular translate](https://github.com/angular-translate/angular-translate).
 */
angular.module('influuntApp')
  .config(['$translateProvider', 'tmhDynamicLocaleProvider', 'APP_SETTINGS',
    function($translateProvider, tmhDynamicLocaleProvider, APP_SETTINGS) {
      $translateProvider.useSanitizeValueStrategy(null);
      $translateProvider.useStaticFilesLoader({
        prefix: 'json/i18n-',
        suffix: '.json'
      });

      $translateProvider.useMessageFormatInterpolation();
      $translateProvider.preferredLanguage(APP_SETTINGS.defaultLocale);

      tmhDynamicLocaleProvider.localeLocationPattern('json/locales/angular-locale_{{locale}}.js');
    }])
    .run(['tmhDynamicLocale', 'APP_SETTINGS', '$http', '$log',
      function(tmhDynamicLocale, APP_SETTINGS, $http, $log) {

        var file = LOCALE_LOCATION_PATTERN.replace(/\{\{locale\}\}/g, APP_SETTINGS.defaultLocale);
        $http.get(file)
          .then(function() {
            tmhDynamicLocale.set(APP_SETTINGS.defaultLocale);
          })
          .catch(function(err) {

            if (err.status === 404) {
              var msg = 'o arquivo ' + file + ' não foi encontrado!';
              msg += ' Para o correto funcionamento, copie o devido arquivo em';
              msg += ' https://github.com/angular/angular.js/tree/master/src/ngLocale';
              msg += ' para ' + LOCALE_LOCATION_PATTERN;
              $log.error(msg);
            }
          });

      }]);
