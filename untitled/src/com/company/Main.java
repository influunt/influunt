package com.company;

import com.company.Disparador;
import com.company.MeioDeContato;

import java.util.ArrayList;
import java.util.List;

public class Main {
	public static void main(String[] args) {
		MeioDeContato m1 = new SMS();
		MeioDeContato m2 = new Email();
		Disparador disparador = new Disparador();
		List<MeioDeContato> lista = new ArrayList<>();
		lista.add(m1);
		lista.add(m2);

		disparador.disparar(lista);
		
		
	}
}
