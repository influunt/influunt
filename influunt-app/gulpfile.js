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

var debug = require('gulp-debug');

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
    yeoman.app + '/plugins/metisMenu/jquery.metisMenu.js',
    yeoman.app + '/plugins/ui-bootstrap-tpls-1.1.2.min.js',
    yeoman.app + '/plugins/inspinia.js',

    'test/mock/**/*.js'
  ],
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

var lintScripts = lazypipe()
  .pipe($.jshint, '.jshintrc')
  .pipe($.jshint.reporter, 'jshint-stylish');

var styles = lazypipe()
  .pipe($.sass, {
    outputStyle: 'expanded',
    precision: 10
  })
  .pipe($.autoprefixer, 'last 1 version')
  .pipe(gulp.dest, '.tmp/styles');

// /////////
// Tasks //
// /////////

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
  openURL('http://localhost:9000');
});

gulp.task('start:server', function() {
  $.connect.server({
    root: [yeoman.app, '.tmp'],
    livereload: true,
    // Change this to '0.0.0.0' to access the server from outside.
    port: 9000,
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

  $.watch(paths.views.files)
    .pipe($.plumber())
    .pipe($.connect.reload());

  $.watch(paths.scripts)
    .pipe($.plumber())
    .pipe(lintScripts())
    .pipe($.connect.reload());

  $.watch(paths.test)
    .pipe($.plumber())
    .pipe(lintScripts());

  gulp.watch('bower.json', ['bower']);
});

gulp.task('serve', function(cb) {
  runSequence('clean:tmp',
    ['lint:scripts'],
    ['start:client'],
    'watch', cb);
});

gulp.task('serve:prod', function() {
  $.connect.server({
    root: [yeoman.dist],
    livereload: true,
    port: 9000
  });
});

gulp.task('test', ['start:server:test'], function() {
  var testToFiles = paths.testRequire.concat(paths.scripts, paths.test);

  return gulp.src(testToFiles)
    .pipe($.karma({
      configFile: paths.karma,
      action: 'watch'
    }));
});

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

  return gulp
    .src(yeoman.app + '/*/.*', {dot: true})
    .pipe(gulp.dest(yeoman.dist));
});

gulp.task('copy:fonts', function () {
  return gulp.src(yeoman.app + '/fonts/**/*')
    .pipe(gulp.dest(yeoman.dist + '/fonts'));
});

gulp.task('build', ['clean:dist'], function () {
  runSequence(['images', 'copy:extras', 'copy:fonts', 'client:build']);
});

gulp.task('default', ['build']);
