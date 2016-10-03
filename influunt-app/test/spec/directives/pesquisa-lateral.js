'use strict';

describe('Directive: pesquisaLateral', function () {

  // load the directive's module
  // beforeEach(module('influuntApp'));

  var element,
    scope;

  beforeEach(inject(function ($rootScope, $compile) {
    scope = $rootScope.$new();
    scope.pesquisa = {
      onClose: function() {},
      campos: [
        {nome: 'campo_1', label: 'campo 1', tipo: 'texto' },
        {nome: 'campo_2', label: 'campo 2', tipo: 'numerico'},
        {nome: 'campo_3', label: 'campo 3', tipo: 'data'}
      ]
    };
    element = angular.element('<pesquisa-lateral on-close="pesquisa.onClose()"></pesquisa-lateral>');
    element.attr('ng-model', 'pesquisa.filtro');
    element.attr('campos', 'pesquisa.campos');
    element = $compile(element)(scope);
    scope.$apply();

    spyOn(scope.pesquisa, 'onClose');
  }));

  describe('Campos de Pesquisa', function () {
    it('Deve criar um campo de pesquisa para cada campo informado nos "campos" da diretiva', function () {
      var campos = $(element).find('.form-group');
      expect(campos.length).toBe(scope.pesquisa.campos.length);
    });

    it('Os campos de tipo texto devem ser compostos por um select contendo as opções de filtro e um campo [type=text]',
      function() {
        var campoTexto = $(element).find('.texto .campos-pesquisa');
        expect(campoTexto.find('select')).toBeDefined();
        expect(campoTexto.find('input[type=text]')).toBeDefined();
      });

    it('Os campos de tipo numérico devem ser compostos por um select contendo as opções de filtro e um campo [type=number]',
      function() {
        var campoNumerico = $(element).find('.numerico').find('.campos-pesquisa');
        expect(campoNumerico.find('select')).toBeDefined();
        expect(campoNumerico.find('input[type=number]')).toBeDefined();
      });

    it('Os campos de tipo data devem ser compostos por dois campos, data de inicio e data de fim', function() {
      var campoData = $(element).find('.data');
      expect(campoData.find('input[type=datetime]').length).toBe(2);
    });
  });

  describe('pesquisar', function () {
    it('Deve executar o método registrado em "on-close" ao pesquisar.', function() {
      $(element).find('.botao-pesquisar').trigger('click');
      scope.$apply();

      expect(scope.pesquisa.onClose).toHaveBeenCalled();
    });

    it('Deve limpar a pesquisa quando o botão "limpar pesquisa" for clicado. "on-close" também deverá ser chamado',
      function() {
        scope.pesquisa.campos[0].valor = 'pesquisa';
        $(element).find('.botao-limpar-pesquisa').trigger('click');
        scope.$apply();

        // expect(scope.pesquisa.campos[0].valor).toBe(null);
        expect(scope.pesquisa.onClose).toHaveBeenCalled();
      });
  });
});
