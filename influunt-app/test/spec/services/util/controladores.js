'use strict';

describe('Service: utilControladores', function () {

  // load the service's module
  // beforeEach(module('influuntApp'));

  // instantiate service
  var utilControladores, controlador;
  beforeEach(inject(function (_utilControladores_) {
    utilControladores = _utilControladores_;
    controlador = ControladorBasico.get();
  }));

  describe('parseLimitsToInt', function () {
    var knownFields = [
      'amareloMax', 'amareloMin', 'atrasoGrupoMin', 'cicloMax', 'cicloMin', 'defasagemMin', 'extensaoVerdeMax',
      'extensaoVerdeMin', 'maximoPermanenciaEstagioMax', 'maximoPermanenciaEstagioMin', 'verdeIntermediarioMax',
      'verdeIntermediarioMin', 'verdeMax', 'verdeMaximoMax', 'verdeMaximoMin', 'verdeMin', 'verdeMinimoMax',
      'verdeMinimoMin', 'verdeSegurancaPedestreMax', 'verdeSegurancaPedestreMin', 'verdeSegurancaVeicularMax',
      'verdeSegurancaVeicularMin', 'vermelhoIntermitenteMax', 'vermelhoIntermitenteMin', 'vermelhoLimpezaPedestreMax',
      'vermelhoLimpezaPedestreMin', 'vermelhoLimpezaVeicularMax', 'vermelhoLimpezaVeicularMin'
    ];

    beforeEach(function() {
      controlador = utilControladores.parseLimitsToInt(controlador);
    });

    it('Deve executar todos os valores de limites min e max das configurações de um controlador', function() {
      var obj = utilControladores.parseLimitsToInt({});
      knownFields.forEach(function(field) {
        expect(obj[field]).toBeDefined();
      });
    });

    it('Deve converter os valores padrão de um controlador para valores inteiros', function() {
      knownFields.forEach(function(field) {
        expect(controlador[field]).toEqual(jasmine.any(Number));
      });
    });
  });

});
