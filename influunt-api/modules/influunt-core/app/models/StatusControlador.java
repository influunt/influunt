package models;

/**
 * Created by rodrigosol on 7/28/16.
 */
public enum StatusControlador {

    //StatusDevice inicial na configuração de um controlador
    EM_CONFIGURACAO,

    //StatusDevice quando é terminada a primeira configuração de um controlador
    CONFIGURADO,

    //Quando o primeiro plano é transmitido a um controlador
    ATIVO,

    // Quando o controlador for clonado e está sendo editado
    EM_EDICAO;

}
