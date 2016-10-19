package models;

import java.util.Arrays;

/**
 * Created by pedropires on 7/7/16.
 */
public enum DiaDaSemana {
    //Nao alterar a ordem, esta organizado por precedencia
    TODOS_OS_DIAS("Todos os dias da semana"),
    SEGUNDA_A_SABADO("Segunda à Sábado"),
    SEGUNDA_A_SEXTA("Segunda à Sexta"),
    SABADO_A_DOMINGO("Sábado e Domingo"),
    DOMINGO("Domingo"),
    SEGUNDA("Segunda-feira"),
    TERCA("Terça-feira"),
    QUARTA("Quarta-feira"),
    QUINTA("Quinta-feira"),
    SEXTA("Sexta-feira"),
    SABADO("Sábado");

    private String name;

    DiaDaSemana(String name) {
        this.name = name;
    }

    public static DiaDaSemana get(String name) {
        DiaDaSemana value = Arrays.stream(DiaDaSemana.values()).filter(diaDaSemana -> diaDaSemana.equalsName(name)).findFirst().orElse(null);
        if (value == null) {
            value = DiaDaSemana.valueOf(name);
        }
        return value;
    }

    public boolean equalsName(String otherName) {
        return otherName != null && otherName.equals(name);
    }

    @Override
    public String toString() {
        return name;
    }


    public int[] momentosDeAtivacao(final int horaNoDia) {
        switch (this) {
            case DOMINGO:
                return new int[]{DOMINGO.inicio() + horaNoDia};
            case SEGUNDA:
                return new int[]{SEGUNDA.inicio() + horaNoDia};
            case TERCA:
                return new int[]{TERCA.inicio() + horaNoDia};
            case QUARTA:
                return new int[]{QUARTA.inicio() + horaNoDia};
            case QUINTA:
                return new int[]{QUINTA.inicio() + horaNoDia};
            case SEXTA:
                return new int[]{SEXTA.inicio() + horaNoDia};
            case SABADO:
                return new int[]{SABADO.inicio() + horaNoDia};
            case SABADO_A_DOMINGO:
                return new int[]{
                        SABADO.inicio() + horaNoDia,
                        DOMINGO.inicio() + horaNoDia
                };
            case SEGUNDA_A_SEXTA:
                return new int[]{
                        SEGUNDA.inicio() + horaNoDia,
                        TERCA.inicio() + horaNoDia,
                        QUARTA.inicio() + horaNoDia,
                        QUINTA.inicio() + horaNoDia,
                        SEXTA.inicio() + horaNoDia
                };
            case SEGUNDA_A_SABADO:
                return new int[]{
                        SEGUNDA.inicio() + horaNoDia,
                        TERCA.inicio() + horaNoDia,
                        QUARTA.inicio() + horaNoDia,
                        QUINTA.inicio() + horaNoDia,
                        SEXTA.inicio() + horaNoDia,
                        SABADO.inicio() + horaNoDia
                };
            case TODOS_OS_DIAS:
                return new int[]{
                        DOMINGO.inicio() + horaNoDia,
                        SEGUNDA.inicio() + horaNoDia,
                        TERCA.inicio() + horaNoDia,
                        QUARTA.inicio() + horaNoDia,
                        QUINTA.inicio() + horaNoDia,
                        SEXTA.inicio() + horaNoDia,
                        SABADO.inicio() + horaNoDia
                };

            default:
                return new int[]{};
        }

    }

    public int getDia() {
        switch (this) {
            case DOMINGO:
                return 1;
            case SEGUNDA:
                return 2;
            case TERCA:
                return 3;
            case QUARTA:
                return 4;
            case QUINTA:
                return 5;
            case SEXTA:
                return 6;
            case SABADO:
                return 7;
            case SABADO_A_DOMINGO:
                return 7;
            case SEGUNDA_A_SEXTA:
                return 2;
            case SEGUNDA_A_SABADO:
                return 2;
            case TODOS_OS_DIAS:
                return 1;
            default:
                return 1;
        }

    }

    public int inicio() {
        switch (this) {
            case DOMINGO:
                return 0;
            case SEGUNDA:
                return 86400000;
            case TERCA:
                return 172800000;
            case QUARTA:
                return 259200000;
            case QUINTA:
                return 345600000;
            case SEXTA:
                return 432000000;
            case SABADO:
                return 518400000;
            default:
                return 0;
        }
    }

    public boolean contains(int outroDia) {
        switch (outroDia) {
            case 1:
                return this == DOMINGO || this == SABADO_A_DOMINGO || this == TODOS_OS_DIAS;
            case 2:
                return this == SEGUNDA || this == SEGUNDA_A_SEXTA || this == SEGUNDA_A_SABADO || this == TODOS_OS_DIAS;
            case 3:
                return this == TERCA || this == SEGUNDA_A_SEXTA || this == SEGUNDA_A_SABADO || this == TODOS_OS_DIAS;
            case 4:
                return this == QUARTA || this == SEGUNDA_A_SEXTA || this == SEGUNDA_A_SABADO || this == TODOS_OS_DIAS;
            case 5:
                return this == QUINTA || this == SEGUNDA_A_SEXTA || this == SEGUNDA_A_SABADO || this == TODOS_OS_DIAS;
            case 6:
                return this == SEXTA || this == SEGUNDA_A_SEXTA || this == SEGUNDA_A_SABADO || this == TODOS_OS_DIAS;
            case 7:
                return this == SABADO || this == SEGUNDA_A_SABADO || this == TODOS_OS_DIAS;
            default:
                return false;
        }
    }
}
