DELETE FROM `cidades`;
DELETE FROM `areas`;
DELETE FROM `sessoes`;
DELETE FROM `versoes_controladores`;
DELETE FROM `permissoes_perfis`;
DELETE FROM `permissoes`;
DELETE FROM `permissoes_app`;
DELETE FROM `permissoes_app_permissoes`;
DELETE FROM `perfis`;
DELETE FROM `usuarios`;

Set @PerfilProgramador = RANDOM_UUID();

Set @cidadeID = RANDOM_UUID();
Set @areaId = RANDOM_UUID();
INSERT INTO `cidades` (`id`, `id_json`, `nome`, `data_criacao`, `data_atualizacao`) VALUES (@cidadeID,RANDOM_UUID(),'São Paulo',NOW(),NOW());
INSERT INTO `areas` (`id`, `id_json`, `descricao`, `cidade_id`, `data_criacao`, `data_atualizacao`) VALUES (@areaId,RANDOM_UUID(),1,@cidadeID,NOW(),NOW());
INSERT into `perfis` (`id`, `nome`, `data_criacao`, `data_atualizacao`) VALUES (@PerfilProgramador, 'Programador', NOW(), NOW());
INSERT INTO `usuarios` (`id`, `login`, `senha`, `email`, `nome`, `root`, `area_id`, `perfil_id`, `data_criacao`, `data_atualizacao`) VALUES
                       (RANDOM_UUID(), 'mobilab', '$2a$10$bfiF2TyTirIyEh6AmWK1huI5.ol0.OxBC3hM9a7Nrc2x9TM.SBooG', 'mobilab@mobilab.com.br', 'Mobilab', false, @areaId, @PerfilProgramador, NOW(), NOW());

-- # Permissões no APP
SET @listarControladoresId = RANDOM_UUID();
INSERT INTO `permissoes_app` (`id`, `chave`, `nome`, `descricao`, `data_criacao`, `data_atualizacao`) VALUES
  (@listarControladoresId, 'listarControladores', '[Programação] - Ver Controladores Cadastrados', 'Ver lista de controladores cadastrados. A lista de controladores é a que é mostrada ao clicar no link "Programação". Se o usuário não tiver essa permissão, o link "Programação" é escondido do usuário.', NOW(), NOW());

SET @visualizarControladorId = RANDOM_UUID();
INSERT INTO `permissoes_app` (`id`, `chave`, `nome`, `descricao`, `data_criacao`, `data_atualizacao`) VALUES
  (@visualizarControladorId, 'verControlador', '[Programação] - Ver Detalhes do Status dos Controladores', 'Permissão para que o usuário consiga acessar a tela com os detalhes de programação de um controlador. Sem essa permissão usuário não irá visualizar o botão “Configuração”.', NOW(), NOW());

SET @criarControladorId = RANDOM_UUID();
INSERT INTO `permissoes_app` (`id`, `chave`, `nome`, `descricao`, `data_criacao`, `data_atualizacao`) VALUES
  (@criarControladorId, 'criarControlador', '[Programação] - Criar e editar Controladores', 'Criar/Editar controladores. Se o usuário não tiver esta permissão, o botão "+Novo" é escondido do usuário na tela de listagem de controladores e não poderá editar controladores.',NOW(), NOW());

SET @verNoMapaId = RANDOM_UUID();
INSERT INTO `permissoes_app` (`id`, `chave`, `nome`, `descricao`, `data_criacao`, `data_atualizacao`) VALUES
  (@verNoMapaId, 'verNoMapa', '[Programação] - Ver Controladores no Mapa', 'O usuário com esta permissão pode acessar a visualização no mapa dos controladores cadastrados. Se o usuário não tiver essa permissão, o botão "Ver no mapa" é escondido do usuário e ele não terá acesso.', NOW(), NOW());

SET @verPlanosId = RANDOM_UUID();
INSERT INTO `permissoes_app` (`id`, `chave`, `nome`, `descricao`, `data_criacao`, `data_atualizacao`) VALUES
  (@verPlanosId, 'verPlanos', '[Programação] - Ver Planos', 'O usuário com essa permissão pode ver os planos cadastrados no sistema. Se o usuário não tiver essa permissão, o botão "Planos" é escondido do usuário e não irá ter acesso.', NOW(), NOW());

SET @criarPlanosId = RANDOM_UUID();
INSERT INTO `permissoes_app` (`id`, `chave`, `nome`, `descricao`, `data_criacao`, `data_atualizacao`) VALUES
  (@criarPlanosId, 'criarPlanos', '[Programação] - Criar e editar Planos', 'Criar/Editar planos no sistema. Se o usuário não tiver essa permissão, os botões "Editar" e “Novo”  serão escondidos do usuário, e não irá ter acesso.', NOW(), NOW());

SET @verTabelaHorariaId = RANDOM_UUID();
INSERT INTO `permissoes_app` (`id`, `chave`, `nome`, `descricao`, `data_criacao`, `data_atualizacao`) VALUES
  (@verTabelaHorariaId, 'verTabelaHoraria', '[Programação] - Ver Tabelas Horárias', 'O usuário com essa permissão pode ver as tabelas horárias cadastradas no sistema. Se o usuário não tiver essa permissão, o botão "Tabelas Horárias" é escondido do usuário.', NOW(), NOW());

SET @criarTabelaHorariaId = RANDOM_UUID();
INSERT INTO `permissoes_app` (`id`, `chave`, `nome`, `descricao`, `data_criacao`, `data_atualizacao`) VALUES
  (@criarTabelaHorariaId, 'criarTabelaHoraria', '[Programação] - Criar e editar Tabelas Horárias', 'Criar/Editar tabelas horárias no sistema. Se o usuário não tiver essa permissão, o botão "Editar" é escondido do usuário.', NOW(), NOW());

SET @EditarUsuariosId = RANDOM_UUID();
INSERT INTO `permissoes_app` (`id`, `chave`, `nome`, `descricao`, `data_criacao`, `data_atualizacao`) VALUES
  (@EditarUsuariosId, 'editarUsuarios', '[Usuários] - Editar Usuários', 'Editar usuários no sistema. Se o usuário não tiver essa permissão, o botão "Editar" é escondido do usuário.', NOW(), NOW());

SET @DefinirPermissoesId = RANDOM_UUID();
INSERT INTO `permissoes_app` (`id`, `chave`, `nome`, `descricao`, `data_criacao`, `data_atualizacao`) VALUES
  (@DefinirPermissoesId, 'definirPermissoes', '[Perfis] - Definir as permissões dos perfis', 'O usuário com essa permissão pode definir as permissões dos perfis cadastrados no sistema. Se o usuário não tiver essa permissão o botão "Definir Permissões" é escondido.', NOW(), NOW());

SET @CriarAgrupamentoId = RANDOM_UUID();
INSERT INTO `permissoes_app` (`id`, `chave`, `nome`, `descricao`, `data_criacao`, `data_atualizacao`) VALUES
  (@CriarAgrupamentoId, 'criarAgrupamentos', '[Agrupamentos] - Criar Agrupamentos', 'Criar novos agrupamentos (rotas e corredores) no sistema. Se o usuário não tiver essa permissão o botão "+Novo" é escondido e ele não terá acesso.', NOW(), NOW());




-- # CRUD Modelos de Controladores

Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/modelos', '[Modelo Controlador] - Listar Modelos de Controladores', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilProgramador, @PermissaoId);
Set @permAppId = RANDOM_UUID();
INSERT INTO `permissoes_app` (`id`, `chave`, `nome`, `descricao`, `data_criacao`, `data_atualizacao`) VALUES
  (@permAppId, 'listarModelos', '[Modelo Controlador] - Ver a lista de  Modelos de Controladores', 'O usuário com essa permissão pode ver a lista de modelos de controladores cadastrados no sistema. Se o usuário não tiver essa permissão o link "Modelo de Controladores" não é mostrado dentro do menu "Cadastros".', NOW(), NOW());
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@permAppId, @PermissaoId);

Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'POST /api/v1/modelos', '[Modelo Controlador] - Cadastrar Novo Modelo de Controlador', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilProgramador, @PermissaoId);
Set @permAppId = RANDOM_UUID();
INSERT INTO `permissoes_app` (`id`, `chave`, `nome`, `descricao`, `data_criacao`, `data_atualizacao`) VALUES
  (@permAppId, 'criarModelos', '[Modelo Controlador] - Criar Modelos de Controladores', 'O usuário com essa permissão pode criar novos modelos de controladores no sistema. Se o usuário não tiver essa permissão o botão "+Novo" é escondido.', NOW(), NOW());
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@permAppId, @PermissaoId);

Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'PUT /api/v1/modelos/$id<[^/]+>', '[Modelo Controlador] - Editar Modelo de Controlador', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilProgramador, @PermissaoId);
Set @permAppId = RANDOM_UUID();
INSERT INTO `permissoes_app` (`id`, `chave`, `nome`, `descricao`, `data_criacao`, `data_atualizacao`) VALUES
  (@permAppId, 'editarModelos', '[Modelo Controlador] - Editar Modelos de Controladores', 'O usuário com essa permissão pode alterar os dados de modelos de controladores cadastrados no sistema. Se o usuário não tiver essa permissão o botão "Editar" é escondido.', NOW(), NOW());
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@permAppId, @PermissaoId);

Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/modelos/$id<[^/]+>', '[Modelo Controlador] - Exibir Modelo de Controlador', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilProgramador, @PermissaoId);
Set @permAppId = RANDOM_UUID();
INSERT INTO `permissoes_app` (`id`, `chave`, `nome`, `descricao`, `data_criacao`, `data_atualizacao`) VALUES
  (@permAppId, 'visualizarModelos', '[Modelo Controlador] - Ver Detalhes de Modelos de Controladores', 'O usuário com essa permissão pode ver todos os dados relativos aos modelos de controladores cadastrados no sistema. Se o usuário não tiver essa permissão o botão "Visualizar" é escondido.', NOW(), NOW());
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@permAppId, @PermissaoId);

Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'DELETE /api/v1/modelos/$id<[^/]+>', '[Modelo Controlador] - Remover Modelo de Controlador', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilProgramador, @PermissaoId);
Set @permAppId = RANDOM_UUID();
INSERT INTO `permissoes_app` (`id`, `chave`, `nome`, `descricao`, `data_criacao`, `data_atualizacao`) VALUES
   (@permAppId, 'removerModelos', '[Modelo Controlador] - Exlcuir Modelos de Controladores', 'O usuário com essa permissão pode excluir do banco de dados os modelos de controladores cadastrados no sistema. Se o usuário não tiver essa permissão o botão "Excluir" é escondido.', NOW(), NOW());
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@permAppId, @PermissaoId);



-- # CRUD Imagens

Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'POST /api/v1/imagens', '[Imagem] - Cadastrar Imagem', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilProgramador, @PermissaoId);

Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'DELETE /api/v1/imagens/$id<[^/]+>', '[Imagem] - Apagar Imagem', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilProgramador, @PermissaoId);

Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'DELETE /api/v1/imagens/$id<[^/]+>/croqui', '[Imagem] - Apagar Imagem de Croqui', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilProgramador, @PermissaoId);


-- # CRUD controladores

Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/controladores', '[Controladores] - Listar Controladores (Programação)', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilProgramador, @PermissaoId);
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@visualizarControladorId, @PermissaoId);
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@criarControladorId, @PermissaoId);
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@verNoMapaId, @PermissaoId);
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@verPlanosId, @PermissaoId);
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@criarPlanosId, @PermissaoId);
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@verTabelaHorariaId, @PermissaoId);
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@criarTabelaHorariaId, @PermissaoId);

Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/controladores/mapas', '[Controladores] - Listar Controladores (Mapas)', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilProgramador, @PermissaoId);

Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/controladores/agrupamentos', '[Controladores] - Listar Controladores (Agrupamentos)', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilProgramador, @PermissaoId);

Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/controladores/$id<[^/]+>', '[Controladores] - Exibir Controlador', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilProgramador, @PermissaoId);
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@criarControladorId, @PermissaoId);
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@verPlanosId, @PermissaoId);
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@criarPlanosId, @PermissaoId);
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@verTabelaHorariaId, @PermissaoId);
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@criarTabelaHorariaId, @PermissaoId);

-- TODO: verificar onde essa rota é usada
Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'DELETE /api/v1/controladores/$id<[^/]+>', '[Controladores] - Remover Controlador', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilProgramador, @PermissaoId);

Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'POST /api/v1/controladores/$id<[^/]+>/edit', '[Controladores] - Editar Controlador Ativo', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilProgramador, @PermissaoId);

Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/controladores/$id<[^/]+>/timeline', '[Controladores] - Visualizar Histórico de Controladores', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilProgramador, @PermissaoId);

Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/controladores/$id<[^/]+>/pode_editar', '[Controladores] - Editar Controlador', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilProgramador, @PermissaoId);
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@criarPlanosId, @PermissaoId);
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@criarTabelaHorariaId, @PermissaoId);

Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'PUT /api/v1/controladores/$id<[^/]+>/finalizar', '[Controladores] - Finalizar Programação do Controlador', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilProgramador, @PermissaoId);


-- TODO: verificar onde essa rota é usada
Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'PUT /api/v1/controladores/$id<[^/]+>/ativar', '[Controladores] - Ativar Controlador', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilProgramador, @PermissaoId);
Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'DELETE /api/v1/controladores/$id<[^/]+>/cancelar_edicao', '[Controladores] - Cancelar Edição', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilProgramador, @PermissaoId);

-- TODO: verificar onde essa rota é usada
Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/controladores/$id<[^/]+>/editar_planos', '[Controladores] - Editar Planos', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilProgramador, @PermissaoId);
-- TODO: verificar onde essa rota é usada
Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/controladores/$id<[^/]+>/editar_tabelas_horarias', '[Controladores] - Editar Tabela Horária', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilProgramador, @PermissaoId);
-- TODO: verificar onde essa rota é usada
Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'PUT /api/v1/controladores/$id<[^/]+>/atualizar_descricao', '[Controladores] - Finalizar Controlador', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilProgramador, @PermissaoId);



-- # Wizard Controladores

Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'POST /api/v1/controladores/dados_basicos', '[Controladores] - Cadastrar Dados Básicos', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilProgramador, @PermissaoId);


Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'POST /api/v1/controladores/aneis', '[Controladores] - Cadastrar Anéis', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilProgramador, @PermissaoId);

Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'POST /api/v1/controladores/grupos_semaforicos', '[Controladores] - Cadastrar Grupos Semafóricos', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilProgramador, @PermissaoId);

Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'POST /api/v1/controladores/associacao_grupos_semaforicos', '[Controladores] - Associar Grupos Semafóricos', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilProgramador, @PermissaoId);

Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'POST /api/v1/controladores/verdes_conflitantes', '[Controladores] - Cadastrar Verdes Conflitantes', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilProgramador, @PermissaoId);

Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'POST /api/v1/controladores/transicoes_proibidas', '[Controladores] - Cadastrar Transições Proibidas', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilProgramador, @PermissaoId);

Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'POST /api/v1/controladores/atraso_de_grupo', '[Controladores] - Cadastrar atraso de grupo', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilProgramador, @PermissaoId);

Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'POST /api/v1/controladores/entre_verdes', '[Controladores] - Cadastrar Tabelas Entre-Verdes', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilProgramador, @PermissaoId);

Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'POST /api/v1/controladores/associacao_detectores', '[Controladores] - Associar Detectores', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilProgramador, @PermissaoId);


Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/controladores/mapas', '[Controladores] - Ver Mapa', NOW(), NOW());
Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'DELETE /api/v1/controladores/$id<[^/]+>/remover_planos_tabelas_horarios', '[Controladores] - Remover Planos e Tabelas Horarios do  Controlador', NOW(), NOW());
-- INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilProgramador, @PermissaoId);


-- # Planos

Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'POST /api/v1/planos', '[Controladores] - Visualizar Planos', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilProgramador, @PermissaoId);

Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/planos/$id<[^/]+>/timeline', '[Planos] - Visualizar Histórico de Planos', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilProgramador, @PermissaoId);
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@criarPlanosId, @PermissaoId);

Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'DELETE /api/v1/planos/$id<[^/]+>', '[Planos] - Excluir Plano', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilProgramador, @PermissaoId);

Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'DELETE /api/v1/planos/$id<[^/]+>/cancelar_edicao', '[Planos] - Cancelar Edição de Plano', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilProgramador, @PermissaoId);



-- #Tabela Horária

Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'POST /api/v1/tabelas_horarias', '[Controladores] - Visualizar Tabela Horária', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilProgramador, @PermissaoId);

Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/tabelas_horarias/$id<[^/]+>/timeline', '[Tabela Horária] - Visualizar Histórico de Tabela Horária', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilProgramador, @PermissaoId);
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@criarTabelaHorariaId, @PermissaoId);

Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'DELETE /api/v1/tabelas_horarias/$id<[^/]+>/cancelar_edicao', '[Tabela Horária] - Cancelar edição', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilProgramador, @PermissaoId);


-- #Exclusão

Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'DELETE /api/v1/estagios/$id<[^/]+>', '[Estágios] - Excluir Estágio', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilProgramador, @PermissaoId);

Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'DELETE /api/v1/detectores/$id<[^/]+>', '[Detectores] - Excluir Detector', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilProgramador, @PermissaoId);

Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'DELETE /api/v1/eventos/$id<[^/]+>', '[Eventos] - Excluir Evento', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilProgramador, @PermissaoId);


-- # Helpers

Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/helpers/controlador', '[Controlador] - Exibir tela de Cadastro de Controladores', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilProgramador, @PermissaoId);
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@criarControladorId, @PermissaoId);
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@verPlanosId, @PermissaoId);
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@criarPlanosId, @PermissaoId);



-- # ModoOperacao Controlador

Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/monitoramento/modo_operacoes_controladores/$id<[^/]+>', '[Monitoramento] - Visualizar Modo de Operação do Controlador', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilProgramador, @PermissaoId);
Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/monitoramento/modo_operacoes_controladores', '[Monitoramento] - Visualizar Modos de Operação dos Controladores', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilProgramador, @PermissaoId);
Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/monitoramento/modo_operacoes_controladores/$id<[^/]+>/ultimo_status', '[Monitoramento] - Visualizar Último Modo de Operação de Controlador', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilProgramador, @PermissaoId);
Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/monitoramento/modo_operacoes_controladores/$id<[^/]+>/historico/$pagina<[^/]+>/$tamanho<[^/]+>', '[Monitoramento] - Visualizar Último Modo de Operação de Controlador com Paginação', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilProgramador, @PermissaoId);


-- # Erros Controladores

Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/monitoramento/erros_controladores/$id<[^/]+>', '[Monitoramento] - Visualizar Erros do Controlador', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilProgramador, @PermissaoId);
Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/monitoramento/erros_controladores', '[Monitoramento] - Visualizar Erros dos Controladores', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilProgramador, @PermissaoId);
Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/monitoramento/erros_controladores/$id<[^/]+>/ultimo_status', '[Monitoramento] - Visualizar Último Erros de Controlador', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilProgramador, @PermissaoId);
Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/monitoramento/erros_controladores/$id<[^/]+>/historico/$pagina<[^/]+>/$tamanho<[^/]+>', '[Monitoramento] - Visualizar Último Erros de Controlador com Paginação', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilProgramador, @PermissaoId);


-- # ImposicaoPlanos Controlador

Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/monitoramento/imposicoes_planos/$id<[^/]+>', '[Monitoramento] - Visualizar Imposição de Plano do Controlador', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilProgramador, @PermissaoId);
Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/monitoramento/imposicoes_planos', '[Monitoramento] - Visualizar Imposição de Planos dos Controladores', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilProgramador, @PermissaoId);
Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/monitoramento/imposicoes_planos/$id<[^/]+>/ultimo_status', '[Monitoramento] - Visualizar Última Imposição de Plano de Controlador', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilProgramador, @PermissaoId);
Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/monitoramento/imposicoes_planos/$id<[^/]+>/historico/$pagina<[^/]+>/$tamanho<[^/]+>', '[Monitoramento] - Visualizar Última Imposição de Plano de Controlador com Paginação', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilProgramador, @PermissaoId);


-- # Monitoramento

Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/monitoramento/status_controladores', '[Monitoramento] - Visualizar Status dos Controladores', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilProgramador, @PermissaoId);
Set @permAppId = RANDOM_UUID();
INSERT INTO `permissoes_app`(`id`, `chave`, `nome`, `descricao`, `data_criacao`, `data_atualizacao`) VALUES
  (@permAppId, 'visualizarStatusControladores', '[Dashboard] - Ver Status dos Controladores', 'O usuário com essa permissão pode acessar o dashboard com os status dos controladores em operação.', NOW(), NOW());
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@permAppId, @PermissaoId);

Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/monitoramento/controladores_onlines', '[Monitoramento] - Visualizar Status dos Controladores Online', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilProgramador, @PermissaoId);
Set @permAppId = RANDOM_UUID();
INSERT INTO `permissoes_app` (`id`, `chave`, `nome`, `descricao`, `data_criacao`, `data_atualizacao`) VALUES
  (@permAppId, 'visualizarStatusControladoresOnline', '[Dashboard] - Ver a lista de Controladores Online', 'O usuário com essa permissão pode acessar a lista com os controladores onlines no momento.', NOW(), NOW());
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@permAppId, @PermissaoId);

Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/monitoramento/controladores_offlines', '[Monitoramento] - Visualizar Status dos Controladores Offline', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilProgramador, @PermissaoId);
Set @permAppId = RANDOM_UUID();
INSERT INTO `permissoes_app` (`id`, `chave`, `nome`, `descricao`, `data_criacao`, `data_atualizacao`) VALUES
  (@permAppId, 'visualizarStatusControladoresOffline', '[Dashboard] - Ver a lista de Controladores Offline', 'O usuário com essa permissão pode acessar a lista com os controladores offlines no momento.', NOW(), NOW());
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@permAppId, @PermissaoId);

Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/monitoramento/detalhe_controlador/$id<[^/]+>', '[Monitoramento] - Visualizar Detalhes de Controlador', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilProgramador, @PermissaoId);
Set @permAppId = RANDOM_UUID();
INSERT INTO `permissoes_app` (`id`, `chave`, `nome`, `descricao`, `data_criacao`, `data_atualizacao`) VALUES
  (@permAppId, 'visualizarDetalhesControlador', '[Dashboard] - Ver Detalhes do Status dos Controladores', 'O usuário com essa permissão pode acessar ver os detalhes do status de um controlador.', NOW(), NOW());
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@permAppId, @PermissaoId);



-- # StatusDevice Conexão

Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/monitoramento/status_conexao/$id<[^/]+>', '[Monitoramento] - Visualizar Status da Conexão de Controlador', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilProgramador, @PermissaoId);
Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/monitoramento/status_conexao', '[Monitoramento] - Visualizar Status da Conexão de Controladores', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilProgramador, @PermissaoId);
Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/monitoramento/status_conexao/$id<[^/]+>/ultimo_status', '[Monitoramento] - Visualizar Último Status da Conexão de Controlador', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilProgramador, @PermissaoId);
Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/monitoramento/status_conexao/$id<[^/]+>/historico/$pagina<[^/]+>/$tamanho<[^/]+>', '[Monitoramento] - Visualizar Último Status da Conexão de Controlador com Paginação', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilProgramador, @PermissaoId);


-- # StatusDevice Status

Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/monitoramento/status/$id<[^/]+>', '[Monitoramento] - Visualizar Status de Controlador', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilProgramador, @PermissaoId);
Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/monitoramento/status', '[Monitoramento] - Visualizar Status de Controladores', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilProgramador, @PermissaoId);
Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/monitoramento/status/$id<[^/]+>/ultimo_status', '[Monitoramento] - Visualizar Último Status de Controlador', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilProgramador, @PermissaoId);
Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/monitoramento/status/$id<[^/]+>/historico/$pagina<[^/]+>/$tamanho<[^/]+>', '[Monitoramento] - Visualizar Último Status de Controlador com Paginação', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilProgramador, @PermissaoId);
-- # Permissao visualizar todas as Areas

Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'visualizarTodasAreas', '[Administração] - Visualizar todas as Areas', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilProgramador, @PermissaoId);
SET @permAppId = RANDOM_UUID();
INSERT INTO `permissoes_app` (`id`, `chave`, `nome`, `descricao`, `data_criacao`, `data_atualizacao`) VALUES
  (@permAppId, 'visualizarTodasAreas', '[Administração] - Visualizar Todas as Áreas', 'O usuário com essa permissão pode ver os dados relativos a todas as áreas cadastradas. Sem essa permissão o usuário somente vê os dados associados com a sua área.', NOW(), NOW());
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@permAppId, @PermissaoId);


COMMIT;

