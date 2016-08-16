'use strict';

describe('Filter: markersAneisPopup', function () {

  // load the filter's module
  beforeEach(module('influuntApp'));

  // initialize a new instance of the filter before each test
  var markersAneisPopup, anel, controlador;
  beforeEach(inject(function ($filter) {
    markersAneisPopup = $filter('markersAneisPopup');
  }));

  it('Deve retornar ao menos o título (cla do anel) no html do popup do marker de anel.', function () {
    anel = {CLA: 'cla-1'};
    controlador = {};
    var result = $(markersAneisPopup(anel, controlador));
    expect(result.find('strong').length).toBe(1);
    expect(result.find('strong').text()).toBe('cla-1');
  });

  it('Deve compor uma lista com a quantidade de detectores veiculares e de pedestres', function() {
    anel = {detectores: [{idJson: 1}, {idJson: 2}, {idJson: 3}]};
    controlador = {
      detectores: [
        {idJson: 1, tipo: 'VEICULAR'},
        {idJson: 2, tipo: 'VEICULAR'},
        {idJson: 3, tipo: 'PEDESTRE'}
      ]
    };

    var result = $(markersAneisPopup(anel, controlador));
    expect(result.find('ul').length).toBe(1);
    expect(result.find('li').length).toBe(2);
    expect(result.find('li:nth-child(1)').text()).toBe('2 detector(es) veicular(es)');
    expect(result.find('li:nth-child(2)').text()).toBe('1 detector(es) de pedestres');
  });

});
