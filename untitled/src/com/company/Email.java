package com.company;

import com.company.MeioDeContato;

public class Email implements MeioDeContato {

	@Override
	public void enviar() {
		System.out.println("Enviado e-mail");
		
	}

}
