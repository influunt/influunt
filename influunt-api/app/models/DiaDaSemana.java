package models;

import java.util.Arrays;

/**
 * Created by pedropires on 7/7/16.
 */
public enum DiaDaSemana {
    SEGUNDA_A_SEXTA("Segunda a Sexta"), SEGUNDA_A_SABADO("Segunda a Sábado"), SABADO_A_DOMINGO("Sábado e Domingo"),
    TODOS_OS_DIAS("Todos os dias da semana"), SEGUNDA("Segunda-feira"), TERCA("Terça-feira"),
    QUARTA("Quarta-feira"), QUINTA("Quinta-feira"), SEXTA("Sexta-feira"), SABADO("Sábado"), DOMINGO("Domingo");

    private String name;

    DiaDaSemana(String name) {
        this.name = name;
    }

    public boolean equalsName(String otherName) {
        return otherName != null && name.equals(otherName);
    }

    @Override
    public String toString() {
        return name;
    }

    public static DiaDaSemana get(String name) {
        DiaDaSemana value = Arrays.stream(DiaDaSemana.values()).filter(diaDaSemana -> diaDaSemana.equalsName(name)).findFirst().orElse(null);
        if(value == null){
            value = DiaDaSemana.valueOf(name);
        }
        return value;
    }
}
