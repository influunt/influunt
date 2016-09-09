(function () {
  'use strict';

  describe('Quadro de horários', function() {
    var dias, eventos, resposta, i;
    var dom = 'dom';
    var seg = 'seg';
    var ter = 'ter';
    var qua = 'qua';
    var qui = 'qui';
    var sex = 'sex';
    var sab = 'sab';
    beforeEach(function() {
      dias = [
        {
          label: 'Todos os dias da semana',
          value: 'TODOS_OS_DIAS',
          dias: ['dom','seg','ter','qua','qui','sex','sab'],
          prioridade:11
        },
        {
          label: 'Domingo',
          value: 'DOMINGO',
          dias: ['dom'],
          prioridade:7
        },
        {
          label: 'Segunda-feira',
          value: 'SEGUNDA',
          dias: ['seg'] ,
          prioridade:6,
        },
        {
          label: 'Terça-feira',
          value: 'TERCA',
          dias: ['ter'],
          prioridade:5
        },
        {
          label: 'Quarta-feira',
          value: 'QUARTA',
          dias: ['qua'],
          prioridade:4,
        },
        {
          label: 'Quinta-feira',
          value: 'QUINTA',
          dias: ['qui'],
          prioridade:3
        },
        {
          label: 'Sexta-feira',
          value: 'SEXTA',
          dias: ['sex'],
          prioridade:2
        },
        {
          label: 'Sábado',
          value: 'SABADO',
          dias: ['sab'],
          prioridade:1
        },
        {
          label: 'Sábado e Domingo',
          value: 'SABADO_A_DOMINGO',
          dias: ['dom','sab'],
          prioridade:8
        },

        {
          label: 'Segunda à Sexta',
          value: 'SEGUNDA_A_SEXTA',
          dias: ['seg','ter','qua','qui','sex'],
          prioridade:9
        },
        {
          label: 'Segunda à Sábado',
          value: 'SEGUNDA_A_SABADO',
          dias: ['seg','ter','qua','qui','sex','sab'],
          prioridade:10
        }
      ];
      eventos = [
        {
          idJson: 'evento-1-JSON',
          posicao: 1,
          diaDaSemana: 'DOMINGO',
          tabelaHoraria: { 
            idJson: 'th-1-JSON'
          },
          tipo: 'NORMAL',
          horario: '0:0:0'
        },
        {
          idJson: 'evento-2-JSON',
          posicao: 2,
          diaDaSemana: 'SEGUNDA',
          tabelaHoraria: { 
            idJson: 'th-1-JSON'
          },
          tipo: 'NORMAL',
          horario: '8:15:10'
        },
        {
          idJson: 'evento-3-JSON',
          posicao: 3,
          diaDaSemana: 'SEGUNDA',
          tabelaHoraria: { 
            idJson: 'th-1-JSON'
          },
          tipo: 'NORMAL',
          horario: '8:15:11'
        },
        {
          idJson: 'evento-4-JSON',
          posicao: 4,
          diaDaSemana: 'SEXTA',
          tabelaHoraria: { 
            idJson: 'th-1-JSON'
          },
          tipo: 'NORMAL',
          horario: '10:0:11'
        },
        {
          idJson: 'evento-5-JSON',
          posicao: 5,
          diaDaSemana: 'TODOS_OS_DIAS',
          tabelaHoraria: { 
            idJson: 'th-1-JSON'
          },
          tipo: 'NORMAL',
          horario: '2:4:3'
        },
        {
          idJson: 'evento-6-JSON',
          posicao: 6,
          diaDaSemana: 'SEGUNDA_A_SEXTA',
          tabelaHoraria: { 
            idJson: 'th-1-JSON'
          },
          tipo: 'NORMAL',
          horario: '22:0:0'
        },
        {
          idJson: 'evento-7-JSON',
          posicao: 7,
          diaDaSemana: 'SEGUNDA_A_SEXTA',
          tabelaHoraria: { 
            idJson: 'th-1-JSON'
          },
          tipo: 'NORMAL',
          horario: '22:0:0'
        },
        {
          idJson: 'evento-8-JSON',
          posicao: 8,
          diaDaSemana: 'TODOS_OS_DIAS',
          tabelaHoraria: { 
            idJson: 'th-1-JSON'
          },
          tipo: 'NORMAL',
          horario: '2:4:2'
        },
        {
          idJson: 'evento-9-JSON',
          posicao: 9
        }
      ];
    });
    describe('Descricao da tabela horária', function() {
      it('Deve existir um objeto de dias', function() {
        expect(dias).toBeDefined();
      });
      
      it('Deve existir um objeto de eventos', function() {
        expect(eventos).toBeDefined();
      });

      it('Deve existir 9 eventos', function() {
        expect(eventos.length).toBe(9);
      });

      it('Deve existir 8 eventos NORMAL', function() {
        expect(_.filter(eventos, {tipo: 'NORMAL'}).length).toBe(8);
      });

    });

    describe('descricao da resposta', function() {
      beforeEach(function() {
        var quadroHorarioBuilder = new influunt.components.QuadroTabelaHorario(dias, []);
        resposta = quadroHorarioBuilder.calcula();
      });

      it('Deve ter uma lista para cada dia da semana', function() {
        expect(_.size(resposta)).toBe(7);
      });

      it('Deve ter 24 intervalos para cada lista', function() {
        expect(resposta[dom].length).toBe(24);
        expect(resposta[seg].length).toBe(24);
        expect(resposta[ter].length).toBe(24);
        expect(resposta[qua].length).toBe(24);
        expect(resposta[qui].length).toBe(24);
        expect(resposta[sex].length).toBe(24);
        expect(resposta[sab].length).toBe(24);
      });

      it('Todos os intervalos devem ser vazios', function() {
        _.forEach(resposta, function(intervalos){
          _.forEach(intervalos, function(intervalo){
            expect('unset').toBe(intervalo[0].state);
          });
        });
      });
    });

    describe('descricao da resposta', function() {
      beforeEach(function() {
        var quadroHorarioBuilder = new influunt.components.QuadroTabelaHorario(dias, eventos);
        resposta = quadroHorarioBuilder.calcula();
      });

      it('Deve ter uma lista para cada dia da semana', function() {
        expect(_.size(resposta)).toBe(7);
      });

      it('Deve ter 24 intervalos para cada lista', function() {
        expect(resposta[dom].length).toBe(24);
        expect(resposta[seg].length).toBe(24);
        expect(resposta[ter].length).toBe(24);
        expect(resposta[qua].length).toBe(24);
        expect(resposta[qui].length).toBe(24);
        expect(resposta[sex].length).toBe(24);
        expect(resposta[sab].length).toBe(24);
      });

      it('Todos os intervalos devem estar preenchidos', function() {
        _.forEach(resposta, function(intervalos){
          _.forEach(intervalos, function(intervalo){
            expect('unset').not.toBe(intervalo[0].state);
          });
        });
      });

      it('Os intervalos de domingo devem ser [0-1 -> 1] e [2-23 -> 5]', function() {
        var intervalos = resposta[dom];
        for(i = 0; i <= 1; i++){
          expect('horarioColor1').toBe(intervalos[i][0].state);
        }
        for(i = 2; i <= 23; i++){
          expect('horarioColor5').toBe(intervalos[i][0].state);
        }
      });

      it('Os intervalos de segunda devem ser [0-7 -> 5], [8-21 -> 3] e [22-23 -> 6]', function() {
        var intervalos = resposta[seg];
        for(i = 0; i <= 7; i++){
          expect('horarioColor5').toBe(intervalos[i][0].state);
        }
        for(i = 8; i <= 21; i++){
          expect('horarioColor3').toBe(intervalos[i][0].state);
        }
        for(i = 22; i <= 23; i++){
          expect('horarioColor6').toBe(intervalos[i][0].state);
        }
      });

      it('Os intervalos de terça devem ser [0-1 -> 6], [2-21 -> 5] e [22-23 -> 6]', function() {
        var intervalos = resposta[ter];
        for(i = 0; i <= 1; i++){
          expect('horarioColor6').toBe(intervalos[i][0].state);
        }
        for(i = 2; i <= 21; i++){
          expect('horarioColor5').toBe(intervalos[i][0].state);
        }
        for(i = 22; i <= 23; i++){
          expect('horarioColor6').toBe(intervalos[i][0].state);
        }
      });

      it('Os intervalos de quarta devem ser [0-1 -> 6], [2-21 -> 5] e [22-23 -> 6]', function() {
        var intervalos = resposta[qua];
        for(i = 0; i <= 1; i++){
          expect('horarioColor6').toBe(intervalos[i][0].state);
        }
        for(i = 2; i <= 21; i++){
          expect('horarioColor5').toBe(intervalos[i][0].state);
        }
        for(i = 22; i <= 23; i++){
          expect('horarioColor6').toBe(intervalos[i][0].state);
        }
      });

      it('Os intervalos de quinta devem ser [0-1 -> 6], [2-21 -> 5] e [22-23 -> 6]', function() {
        var intervalos = resposta[qui];
        for(i = 0; i <= 1; i++){
          expect('horarioColor6').toBe(intervalos[i][0].state);
        }
        for(i = 2; i <= 21; i++){
          expect('horarioColor5').toBe(intervalos[i][0].state);
        }
        for(i = 22; i <= 23; i++){
          expect('horarioColor6').toBe(intervalos[i][0].state);
        }
      });

      it('Os intervalos de sexta devem ser [0-1 -> 6], [2-9 -> 5], [10-21 -> 4] e [22-23 -> 6]', function() {
        var intervalos = resposta[sex];
        for(i = 0; i <= 1; i++){
          expect('horarioColor6').toBe(intervalos[i][0].state);
        }
        for(i = 2; i <= 9; i++){
          expect('horarioColor5').toBe(intervalos[i][0].state);
        }
        for(i = 10; i <= 21; i++){
          expect('horarioColor4').toBe(intervalos[i][0].state);
        }
        for(i = 22; i <= 23; i++){
          expect('horarioColor6').toBe(intervalos[i][0].state);
        }
      });

      it('Os intervalos de sabado devem ser [0-1 -> 6] e [2-23 -> 5]', function() {
        var intervalos = resposta[sab];
        for(i = 0; i <= 1; i++){
          expect('horarioColor6').toBe(intervalos[i][0].state);
        }
        for(i = 2; i <= 23; i++){
          expect('horarioColor5').toBe(intervalos[i][0].state);
        }
      });
    });
  });
})();
