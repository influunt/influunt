'use strict';

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
        prefix: 'i18n/i18n-',
        suffix: '.json'
      });

      $translateProvider.useMessageFormatInterpolation();
      $translateProvider.preferredLanguage(APP_SETTINGS.default_locale);

      tmhDynamicLocaleProvider.localeLocationPattern('i18n/locales/angular-locale_{{locale}}.js');
    }])
    .run(['tmhDynamicLocale', 'APP_SETTINGS', function(tmhDynamicLocale, APP_SETTINGS) {
      tmhDynamicLocale.set(APP_SETTINGS.default_locale);
    }])
  ;

