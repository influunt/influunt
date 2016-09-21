package com.company;

import com.company.MeioDeContato;

public class SMS implements MeioDeContato {

	@Override
	public void enviar() {
		System.out.println("Enviado SMS");
		
	}

}
