# language: pt
@performance
Funcionalidade: Verificar a performance do sistema

  Cenário: Listagem de controladores
    Dado que o sistema possua "30" controladores cadastrados
    E que o usuário aguarde um tempo de "2000" milisegundos
    E que o usuário acesse a página de listagem de controladores
    Então o sistema deverá mostrar "30" controladores cadastrados
