// Karma configuration
// Generated on 2016-06-01

module.exports = function(config) {
  'use strict';

  config.set({
    // enable / disable watching file and executing tests whenever any file changes
    autoWatch: true,

    // base path, that will be used to resolve files and exclude
    basePath: '../',

    // testing framework to use (jasmine/mocha/qunit/...)
    // as well as any additional frameworks (requirejs/chai/sinon/...)
    frameworks: [
      'jasmine'
    ],

    // list of files / patterns to load in the browser
    files: [
      // Bower plugins
      'bower_components/jquery/dist/jquery.js',
      'bower_components/angular/angular.js',
      'bower_components/angular-mocks/angular-mocks.js',
      'bower_components/angular-resource/angular-resource.js',
      'bower_components/angular-cookies/angular-cookies.js',
      'bower_components/angular-sanitize/angular-sanitize.js',
      'bower_components/angular-ui-router/release/angular-ui-router.js',
      'bower_components/angular-animate/angular-animate.js',
      'bower_components/angular-touch/angular-touch.js',
      'bower_components/angular-translate/angular-translate.js',
      'bower_components/messageformat/messageformat.js',
      'bower_components/angular-translate-interpolation-messageformat/angular-translate-interpolation-messageformat.js',
      'bower_components/angular-translate-loader-static-files/angular-translate-loader-static-files.js',
      'bower_components/angular-dynamic-locale/src/tmhDynamicLocale.js',
      'bower_components/lodash/lodash.js',
      'bower_components/restangular/dist/restangular.js',
      'bower_components/datatables/media/js/jquery.dataTables.js',
      'bower_components/angular-datatables/dist/angular-datatables.js',
      'bower_components/angular-datatables/dist/plugins/bootstrap/angular-datatables.bootstrap.js',
      'bower_components/angular-datatables/dist/plugins/colreorder/angular-datatables.colreorder.js',
      'bower_components/angular-datatables/dist/plugins/columnfilter/angular-datatables.columnfilter.js',
      'bower_components/angular-datatables/dist/plugins/light-columnfilter/angular-datatables.light-columnfilter.js',
      'bower_components/angular-datatables/dist/plugins/colvis/angular-datatables.colvis.js',
      'bower_components/angular-datatables/dist/plugins/fixedcolumns/angular-datatables.fixedcolumns.js',
      'bower_components/angular-datatables/dist/plugins/fixedheader/angular-datatables.fixedheader.js',
      'bower_components/angular-datatables/dist/plugins/scroller/angular-datatables.scroller.js',
      'bower_components/angular-datatables/dist/plugins/tabletools/angular-datatables.tabletools.js',
      'bower_components/angular-datatables/dist/plugins/buttons/angular-datatables.buttons.js',
      'bower_components/angular-datatables/dist/plugins/select/angular-datatables.select.js',
      'bower_components/AngularJS-Toaster/toaster.js',
      'bower_components/sweetalert/dist/sweetalert.min.js',
      'bower_components/ngSweetAlert/SweetAlert.js',
      'bower_components/leaflet/dist/leaflet-src.js',
      'bower_components/angular-google-places-autocomplete/src/autocomplete.js',
      'bower_components/angular-google-places-autocomplete/dist/autocomplete.min.js',
      'bower_components/angular-ui-sortable/sortable.js',
      'bower_components/blockUI/jquery.blockUI.js',
      'bower_components/select2/dist/js/select2.js',
      'bower_components/uuid.js/src/uuid.js',
      'bower_components/bowser/src/bowser.js',
      'bower_components/jquery.slimscroll/jquery.slimscroll.js',
      'bower_components/moment/moment.js',
      'bower_components/moment-timezone/builds/moment-timezone-with-data-2010-2020.js',
      'bower_components/angular-datepicker/dist/angular-datepicker.js',
      'bower_components/angular-tooltips/dist/angular-tooltips.min.js',
      'bower_components/string/dist/string.min.js',
      'bower_components/hull-js/dist/hull.js',
      'bower_components/angular-permission/dist/angular-permission.js',
      'bower_components/angular-permission/dist/angular-permission-ui.js',
      'bower_components/angular-permission/dist/angular-permission-ng.js',
      'bower_components/humanize-duration/humanize-duration.js',

      // inspinea scripts.
      'app/plugins/jquery-ui-1.12.0/jquery-ui.js',
      'app/plugins/metisMenu/jquery.metisMenu.js',
      'app/plugins/ui-bootstrap-tpls-1.1.2.min.js',
      'app/plugins/inspinia.js',
      'app/plugins/iCheck/icheck.js',
      'app/plugins/dropzone/dropzone.js',
      'app/plugins/roundSlider-1.3/roundslider.js',
      'app/plugins/uiTabs/ui.tabs.overflowResize.js',
      'app/plugins/uiTabs/lib/ui.tabs.addTab.js',
      'app/plugins/uiTabs/lib/ui.tabs.closable.js',
      'app/plugins/phasertips/Phasetips.js',

      // influunt app plugins.
      'app/plugins/influunt/**/*.js',

      // tests and scripts.
      'test/helpers/**/*.js',
      'test/mock/**/*.js',
      'app/scripts/**/*.js',
      'test/spec/**/*.js'

      // paterne for images

    ],

    // list of files / patterns to exclude
    exclude: [
      'app/scripts/services/initializers/translations.js'
    ],

    // web server port
    port: 8081,

    // Start these browsers, currently available:
    // - Chrome
    // - ChromeCanary
    // - Firefox
    // - Opera
    // - Safari (only Mac)
    // - PhantomJS
    // - IE (only Windows)
    browsers: [
      'PhantomJS'
    ],

    // reporters: ['progress', 'kjhtml', 'coverage'],
    reporters: ['progress', 'kjhtml'],

    // plugins to enable
    plugins: [
      'karma-phantomjs-launcher',
      'karma-jasmine',
      'karma-jasmine-html-reporter',
      // 'karma-coverage'
    ],

    // Continuous Integration mode
    // if true, it capture browsers, run tests and exit
    singleRun: false,

    colors: true,

    // level of logging
    // possible values: LOG_DISABLE || LOG_ERROR || LOG_WARN || LOG_INFO || LOG_DEBUG
    logLevel: config.LOG_INFO,

    // preprocessors: {
    //   'app/scripts/**/*.js': ['coverage'],
    //   'app/plugins/influunt/**/*.js': ['coverage']
    // },

    // coverageReporter: {
    //   reporters: [
    //     {
    //       type: 'lcov',
    //       dir: 'coverage/',
    //       subdir: '.'
    //     }
    //   ]
    // }

    // Uncomment the following lines if you are using grunt's server to run the tests
    // proxies: {
    //   '/': 'http://localhost:9000/'
    // },
    // URL root prevent conflicts with the site root
    // urlRoot: '_karma_'
  });
};
