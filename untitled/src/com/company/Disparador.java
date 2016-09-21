package com.company;

import com.company.MeioDeContato;

import java.util.List;

public class Disparador {

	public void disparar(List<MeioDeContato> meios){
		for(MeioDeContato meio : meios){
			meio.enviar();
		};
	}
}
