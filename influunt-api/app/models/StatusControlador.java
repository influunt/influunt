package models;

/**
 * Created by rodrigosol on 7/28/16.
 */
public enum StatusControlador {

    //Status inicial na configuração de um controlador
    EM_CONFIGURACAO,

    //Status quando é terminada a primeira configuração de um controlador
    CONFIGURADO,

    //Quando o primeiro plano é transmitido a um controlador
    ATIVO,

    // Quando o controladro for clonado e está sendo editado
    EM_EDICAO,
    // TODO - Refactor VersaoControlador
    CLONADO;


}
