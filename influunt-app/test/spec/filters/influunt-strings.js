'use strict';

describe('Filter: influuntStrings', function () {

  // initialize a new instance of the filter before each test
  var humanize, capitalize, camelize;
  beforeEach(inject(function ($filter) {
    humanize = $filter('humanize');
    capitalize = $filter('capitalize');
    camelize = $filter('camelize');
  }));

  it('Não retornará nada se uma string não for enviada.', function() {
    expect(humanize()).not.toBeDefined();
  });

  it('deveria humanizar a string', function () {
    var text = 'ola_mundo';
    expect(humanize(text)).toBe('Ola Mundo');
    expect(humanize(text)).not.toBe('Ola mundo');
    expect(humanize(text)).not.toBe('ola Mundo');

    text = 'ola-mundo';
    expect(humanize(text)).toBe('Ola Mundo');
    expect(humanize(text)).not.toBe('Ola mundo');
    expect(humanize(text)).not.toBe('ola Mundo');

    text = 'olaMundo';
    expect(humanize(text)).toBe('Ola Mundo');
    expect(humanize(text)).not.toBe('Ola mundo');
    expect(humanize(text)).not.toBe('ola Mundo');

    text = 'ola mundo';
    expect(humanize(text)).toBe('Ola Mundo');
    expect(humanize(text)).not.toBe('Ola mundo');
    expect(humanize(text)).not.toBe('ola Mundo');
  });

  it('deveria capitalizar a string', function () {
    var text = 'ola_mundo';
    expect(capitalize(text)).toBe('Ola_mundo');
    expect(capitalize(text)).not.toBe('Ola mundo');
    expect(capitalize(text)).not.toBe('ola Mundo');

    text = 'ola-mundo';
    expect(capitalize(text)).toBe('Ola-mundo');
    expect(capitalize(text)).not.toBe('Ola mundo');
    expect(capitalize(text)).not.toBe('ola Mundo');

    text = 'olaMundo';
    expect(capitalize(text)).toBe('Olamundo');
    expect(capitalize(text)).not.toBe('Ola mundo');
    expect(capitalize(text)).not.toBe('ola Mundo');

    text = 'ola mundo';
    expect(capitalize(text)).toBe('Ola mundo');
    expect(capitalize(text)).not.toBe('ola Mundo');
  });


it('deveria camelizar a string', function () {
    var text = 'ola_mundo';
    expect(camelize(text)).toBe('olaMundo');
    expect(camelize(text)).not.toBe('Ola mundo');
    expect(camelize(text)).not.toBe('ola Mundo');

    text = 'ola-mundo';
    expect(camelize(text)).toBe('olaMundo');
    expect(camelize(text)).not.toBe('Ola mundo');
    expect(camelize(text)).not.toBe('ola Mundo');

    text = 'olaMundo';
    expect(camelize(text)).toBe('olaMundo');
    expect(camelize(text)).not.toBe('Ola mundo');
    expect(camelize(text)).not.toBe('ola Mundo');

    text = 'ola mundo';
    expect(camelize(text)).toBe('olaMundo');
    expect(camelize(text)).not.toBe('Ola mundo');
    expect(camelize(text)).not.toBe('ola Mundo');
  });


});
