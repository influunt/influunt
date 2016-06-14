// Generated on 2016-06-01 using generator-angular 0.15.1
'use strict';

var gulp = require('gulp');
var $ = require('gulp-load-plugins')();
var openURL = require('open');
var lazypipe = require('lazypipe');
var rimraf = require('rimraf');
var wiredep = require('wiredep').stream;
var runSequence = require('run-sequence');
var bowerJsonFile = require('./bower.json');

var ngConstant = require('gulp-ng-constant');

var angularTemplateCache = require('gulp-angular-templatecache');
var concat = require('gulp-concat');
var childProcess = require('child_process');
var exec = childProcess.exec;

var yeoman = {
  app: bowerJsonFile.appPath || 'app',
  dist: 'dist'
};

var paths = {
  scripts: [
    yeoman.app + '/scripts/**/*.js'
  ],
  styles: [yeoman.app + '/styles/**/*.scss'],
  test: ['test/spec/**/*.js'],
  testRequire: [
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

    // inspinea scripts.
    yeoman.app + '/plugins/metisMenu/jquery.metisMenu.js',
    yeoman.app + '/plugins/ui-bootstrap-tpls-1.1.2.min.js',
    yeoman.app + '/plugins/inspinia.js'
    // yeoman.app + '/json/**/*.js'
    // 'test/mock/**/*.js'
  ],
  cucumberFeatures: ['features/**/*.feature'],
  cucumberStepDefinitions: ['features/step_definitions/**/*.steps.js'],
  cucumberSupportScripts: ['features/support/**/*.js'],
  // karma: 'karma.conf.js',
  karma: 'test/karma.conf.js',
  views: {
    main: yeoman.app + '/index.html',
    files: [yeoman.app + '/views/**/*.html']
  }
};

// //////////////////////
// Reusable pipelines //
// //////////////////////

var templateCache = function() {
  var TEMPLATE_HEADER = 'angular.module("<%= module %>"<%= standalone %>).run(["$templateCache", function($templateCache) {';
  var useStrictTemplate = '\'use strict\';';
  return gulp.src(paths.views.files)
    .pipe(angularTemplateCache({
      root: 'views', standalone: true,
      templateHeader: useStrictTemplate + '\n\n' + TEMPLATE_HEADER
    }))
    .pipe(concat('templates.js'))
    .pipe(gulp.dest('app/scripts'))
    .pipe($.connect.reload());
};

/**
 * Runs cucumber task for a specific file (if there's a .feature file passed as paramter) or for all .feature files.
 *
 * @param      {Object}  file    The file.
 */
var runCucumber = function(file) {
  var features = file && file.path && !!file.path.match(/\.feature$/) ? file.path : 'features/';

  exec('node node_modules/cucumber/bin/cucumber.js ' + features + ' -f pretty -t ~@ignore',
    function(err, stdout, stderr) {
      console.log(stdout);
      console.log(stderr);
    });
};

var lintScripts = lazypipe()
  .pipe($.jshint, '.jshintrc')
  .pipe($.jshint.reporter, 'jshint-stylish');

var styles = lazypipe()
  .pipe($.sass, {
    outputStyle: 'expanded',
    precision: 10
  })
  .pipe($.autoprefixer, 'last 1 version')
  .pipe(gulp.dest, 'app/styles');
  // .pipe(gulp.dest, '.tmp/styles');

// /////////
// Tasks //
// /////////

gulp.task('constants', function () {
  var myConfig = require('./app/json/env.json');
  var envConfig = myConfig[process.env.ENVIRONMENT || 'development'];

  return ngConstant({
      name: 'environment',
      constants: envConfig,
      stream: true,
      template: `'use strict';
angular.module('<%- moduleName %>'<% if (deps) { %>, <%= JSON.stringify(deps) %><% } %>)
<% constants.forEach(function(constant) { %>
.constant('<%- constant.name %>', <%= constant.value %>)
<% }) %>;`
    })
    .pipe(gulp.dest('app/scripts'));
});

gulp.task('templates', function() {
  return templateCache();
});

gulp.task('styles', function () {
  return gulp.src(paths.styles)
    .pipe(styles());
});

gulp.task('lint:scripts', function () {
  return gulp.src(paths.scripts)
    .pipe(lintScripts());
});

gulp.task('clean:tmp', function (cb) {
  rimraf('./.tmp', cb);
});

gulp.task('start:client', ['start:server', 'styles'], function () {
  openURL('http://localhost:9009');
});

gulp.task('start:server', function() {
  $.connect.server({
    root: [yeoman.app, '.tmp'],
    livereload: true,
    // Change this to '0.0.0.0' to access the server from outside.
    port: 9009,
    middleware: function (connect) {
      return [connect().use('/bower_components', connect.static('./bower_components'))];
    }
  });
});

gulp.task('start:server:test', function() {
  $.connect.server({
    root: ['test', yeoman.app, '.tmp'],
    livereload: true,
    port: 9001,
    middleware: function (connect) {
      return [connect().use('/bower_components', connect.static('./bower_components'))];
    }
  });
});

gulp.task('watch', function () {
  $.watch(paths.styles)
    .pipe($.plumber())
    .pipe(styles())
    .pipe($.connect.reload());

  $.watch(paths.scripts)
    .pipe($.plumber())
    .pipe(lintScripts())
    .pipe($.connect.reload());

  $.watch(paths.test)
    .pipe($.plumber())
    .pipe(lintScripts());

  $.watch(paths.cucumberStepDefinitions)
    .pipe($.plumber())
    .pipe(lintScripts());

    $.watch(paths.cucumberSupportScripts)
    .pipe($.plumber())
    .pipe(lintScripts());

  gulp.watch(paths.views.files, ['templates']);
  gulp.watch('bower.json', ['bower']);
});

gulp.task('serve', function(cb) {
  runSequence('clean:tmp',
    ['constants'],
    ['lint:scripts'],
    ['start:client'],
    ['templates'],
    'watch', cb);
});

gulp.task('serve:prod', function() {
  $.connect.server({
    root: [yeoman.dist],
    livereload: true,
    port: 9009
  });
});

gulp.task('test', ['start:server:test'], function() {
  var testToFiles = paths.testRequire.concat(paths.scripts, paths.test);
  var cucumberFiles = paths.cucumberFeatures.concat(paths.cucumberStepDefinitions);

  gulp.watch(cucumberFiles)
    .on('change', function(file) {
      runCucumber(file);
    });

  return gulp.src(testToFiles)
    .pipe($.karma({
      configFile: paths.karma,
      action: 'watch'
    }));
});

gulp.task('test:cucumber', runCucumber);

// inject bower components
gulp.task('bower', function () {
  return gulp.src(paths.views.main)
    .pipe(wiredep({
      // directory: yeoman.app + '/bower_components',
      ignorePath: '..'
    }))
  // .pipe(gulp.dest(yeoman.app + '/views'));
  .pipe(gulp.dest(yeoman.app));
});

// /////////
// Build //
// /////////

gulp.task('clean:dist', function(cb) {
  rimraf('./dist', cb);
});

gulp.task('client:build', ['bower', 'html', 'styles'], function () {
  var jsFilter = $.filter('**/*.js');
  var cssFilter = $.filter('**/*.css');

  return gulp.src(paths.views.main)
    .pipe($.useref({searchPath: [yeoman.app, yeoman.temp]}))
    .pipe(jsFilter)
    .pipe($.ngAnnotate())
    .pipe($.uglify())
    .pipe(jsFilter.restore())
    .pipe(cssFilter)
    .pipe($.minifyCss({cache: true}))
    .pipe(cssFilter.restore())
    .pipe(gulp.dest(yeoman.dist));
});

gulp.task('html', function () {
  return gulp.src(yeoman.app + '/views/**/*')
    .pipe(gulp.dest(yeoman.dist + '/views'));
});

gulp.task('images', function () {
  return gulp.src(yeoman.app + '/images/**/*')
    .pipe($.cache($.imagemin({
      optimizationLevel: 5,
      progressive: true,
      interlaced: true
    })))
    .pipe(gulp.dest(yeoman.dist + '/images'));
});

gulp.task('copy:extras', function () {
  // hardcoded copy font-awesome fonts to app.
  gulp
    .src(['bower_components/font-awesome/fonts/fontawesome-webfont.*'])
    .pipe(gulp.dest(yeoman.dist + '/fonts/'));

  // hardcoded copy patterns (images) from inspinea pack.
  gulp
    .src(yeoman.app + '/styles/patterns/*.*', {dot: true})
    .pipe(gulp.dest(yeoman.dist + '/styles/patterns/'));

  gulp
    .src(yeoman.app + '/json/**/*.*', {dot: true})
    .pipe(gulp.dest(yeoman.dist + '/json/'));

  return gulp
    .src(yeoman.app + '/*/.*', {dot: true})
    .pipe(gulp.dest(yeoman.dist));
});

gulp.task('copy:fonts', function () {
  return gulp.src(yeoman.app + '/fonts/**/*')
    .pipe(gulp.dest(yeoman.dist + '/fonts'));
});

gulp.task('build', ['clean:dist'], function () {
  runSequence(['images', 'copy:extras', 'copy:fonts', 'client:build', 'templates']);
});

gulp.task('default', ['build']);
