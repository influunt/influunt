# language: pt
Funcionalidade: Listagem de dados de controladores
  Como um usuário logado
  Com o objetivo de ver a lista de controladores registrados no sistema
  O usuário deve acessar a tela de controladores

  # Cenários de forma de exibição de conteúdo.
  Cenário: Listagem de controladores no modo de visualização de cards
    Dado a lista de controladores registrados
    | id | descricao | numeroSMEE | idControlador | numeroSMEEConjugado1 | numeroSMEEConjugado2 | numeroSMEEConjugado3 | firmware | dataCriacao | dataAtualizacao |
    | 1  | cont_1    | 1          | 1             | 1                    | 1                    | 1                    | 123      | 01/01/2000  | 01/01/2000      |
    | 2  | cont_2    | 2          | 2             | 2                    | 2                    | 2                    | 223      | 01/01/2000  | 01/01/2000      |
    | 3  | cont_3    | 3          | 3             | 3                    | 3                    | 3                    | 323      | 01/01/2000  | 01/01/2000      |
    Quando o usuário acessar a tela de controladores
    E clicar no botão de visualização em cards
    Então o sistema deve apresentar a lista dos controladores no formato de visualização de cards

  Cenário: Listagem de controladores no modo de visualização em grid
    Dado a lista de controladores registrados
    | id | descricao | numeroSMEE | idControlador | numeroSMEEConjugado1 | numeroSMEEConjugado2 | numeroSMEEConjugado3 | firmware | dataCriacao | dataAtualizacao |
    | 1  | cont_1    | 1          | 1             | 1                    | 1                    | 1                    | 123      | 01/01/2000  | 01/01/2000      |
    | 2  | cont_2    | 2          | 2             | 2                    | 2                    | 2                    | 223      | 01/01/2000  | 01/01/2000      |
    | 3  | cont_3    | 3          | 3             | 3                    | 3                    | 3                    | 323      | 01/01/2000  | 01/01/2000      |
    Quando o usuário acessar a tela de controladores
    E clicar no botão de visualização em cards
    Então o sistema deve apresentar a lista de controladores no formato de visualização em grid


  # Cenários de filtros e pesquisa de controladores
  Cenário: Filtro de controladores
    Dado a lista de controladores registrados
    | id | descricao | numeroSMEE | idControlador | numeroSMEEConjugado1 | numeroSMEEConjugado2 | numeroSMEEConjugado3 | firmware | dataCriacao | dataAtualizacao |
    | 1  | cont_1    | 1          | 1             | 1                    | 1                    | 1                    | 123      | 01/01/2000  | 01/01/2000      |
    | 2  | cont_2    | 2          | 2             | 2                    | 2                    | 2                    | 223      | 01/01/2000  | 01/01/2000      |
    | 3  | cont_3    | 3          | 3             | 3                    | 3                    | 3                    | 323      | 01/01/2000  | 01/01/2000      |
    Quando o usuário acessar a tela de controladores
    Então devem existir as opções "cont_1", "cont_2" e "cont_3" no filtro de controladores.

    Quando o usuário marcar a opção "cont_1" no filtro de controladores (lateral à esquerda)
    Então a lista de controladores deve exibir somente a opção de "cont_1"

    Quando o usuário marcar as opções: "cont_1", "cont_2" no filtro de controladores (lateral à esquerda)
    Então a lista de controladores deve exibir as opções: "cont_1", "cont_2"

    Quando o usuário desmarcar todas as opções no filtro de controladores (lateral à esquerda)
    Então a lista de controladores deve exibir as opções: "cont_1", "cont_2", "cont_3"

  Cenário: Pesquisa de controladores
    Dado a lista de controladores registrados
    | id | descricao | numeroSMEE | idControlador | numeroSMEEConjugado1 | numeroSMEEConjugado2 | numeroSMEEConjugado3 | firmware | dataCriacao | dataAtualizacao |
    | 1  | cont_1    | 1          | 1             | 1                    | 1                    | 1                    | 123      | 01/01/2000  | 01/01/2000      |
    | 2  | cont_2    | 2          | 2             | 2                    | 2                    | 2                    | 223      | 01/01/2000  | 01/01/2000      |
    | 3  | cont_3    | 3          | 3             | 3                    | 3                    | 3                    | 323      | 01/01/2000  | 01/01/2000      |
    Quando o usuário escrever no campo de pesquisa "cont_1"
    Então a lista de controladores deve exibir somente a opção de "cont_1"

    Quando o usuário escrever no campo de pesquisa "cont_"
    Então a lista de controladores deve exibir as opções: "cont_1", "cont_2", "cont_3"

    Quando o usuário apagar todo o conteúdo do campo de pesquisa
    Então a lista de controladores deve exibir as opções: "cont_1", "cont_2", "cont_3"
