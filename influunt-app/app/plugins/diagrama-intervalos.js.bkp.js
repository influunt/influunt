var influunt;
(function (influunt) {
  var components;
  (function (components) {
    var DiagramaIntervalos = (function () {
      function DiagramaIntervalos() {
      }
      DiagramaIntervalos.prototype.calcula = function () {
        return {"resultado": {"status": 200,"erros": ["erro 1","erro 2"],"estagios": [{"posicao": 1,"duracao": 60},{"posicao": 2,"duracao": 30},{"posicao": 3,"duracao": 30}],"grupos": [{"posicao": 1,"intervalos": [{"status": 3,"duracao": 10},{"status": 3,"duracao": 10},{"status": 3,"duracao": 10},{"status": 1,"duracao": 30},{"status": 2,"duracao": 10},{"status": 3,"duracao": 10},{"status": 3,"duracao": 10},{"status": 3,"duracao": 10},{"status": 3,"duracao": 10},{"status": 3,"duracao": 10}]},{"posicao": 2,"intervalos": [{"status": 1,"duracao": 10},{"status": 2,"duracao": 10},{"status": 3,"duracao": 10},{"status": 3,"duracao": 30},{"status": 3,"duracao": 10},{"status": 3,"duracao": 10},{"status": 3,"duracao": 10},{"status": 3,"duracao": 10},{"status": 3,"duracao": 10},{"status": 1,"duracao": 10}]},{"posicao": 3,"intervalos": [{"status": 3,"duracao": 10},{"status": 3,"duracao": 10},{"status": 3,"duracao": 10},{"status": 3,"duracao": 30},{"status": 3,"duracao": 10},{"status": 3,"duracao": 10},{"status": 1,"duracao": 10},{"status": 4,"duracao": 10},{"status": 3,"duracao": 10},{"status": 3,"duracao": 10}]},{"posicao": 4,"intervalos": [{"status": 4,"duracao": 10},{"status": 4,"duracao": 10},{"status": 3,"duracao": 10},{"status": 3,"duracao": 30},{"status": 3,"duracao": 10},{"status": 3,"duracao": 10},{"status": 1,"duracao": 10},{"status": 1,"duracao": 10},{"status": 1,"duracao": 10},{"status": 1,"duracao": 10}]},{"posicao": 5,"intervalos": [{"status": 3,"duracao": 10},{"status": 3,"duracao": 10},{"status": 3,"duracao": 10},{"status": 1,"duracao": 30},{"status": 1,"duracao": 10},{"status": 1,"duracao": 10},{"status": 1,"duracao": 10},{"status": 4,"duracao": 10},{"status": 3,"duracao": 10},{"status": 3,"duracao": 10}]}]}};
      };
      return DiagramaIntervalos;
    }());
    components.DiagramaIntervalos = DiagramaIntervalos;
    var DiagramaIntervalos;
    (function (DiagramaIntervalos) {
      var Resultado = (function () {
        function Resultado(__parent) {
          this.__parent = __parent;
          this.status = 0;
        }
        return Resultado;
      }());
      DiagramaIntervalos.Resultado = Resultado;
      var Estagio = (function () {
        function Estagio(__parent) {
          this.__parent = __parent;
          this.posicao = 0;
          this.duracao = 0;
        }
        return Estagio;
      }());
      DiagramaIntervalos.Estagio = Estagio;
      var Grupo = (function () {
        function Grupo(__parent) {
          this.__parent = __parent;
          this.posicao = 0;
        }
        return Grupo;
      }());
      DiagramaIntervalos.Grupo = Grupo;
      var Intervalo = (function () {
        function Intervalo(__parent) {
          this.__parent = __parent;
          this.status = 0;
          this.duracao = 0;
        }
        return Intervalo;
      }());
      DiagramaIntervalos.Intervalo = Intervalo;
    })(DiagramaIntervalos = components.DiagramaIntervalos || (components.DiagramaIntervalos = {}));
  })(components = influunt.components || (influunt.components = {}));
})(influunt || (influunt = {}));
