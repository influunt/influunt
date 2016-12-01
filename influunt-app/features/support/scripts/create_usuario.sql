DELETE FROM `sessoes`;
DELETE FROM `versoes_controladores`;
DELETE FROM `permissoes_perfis`;
DELETE FROM `permissoes`;
DELETE FROM `permissoes_app`;
DELETE FROM `permissoes_app_permissoes`;
DELETE FROM `perfis`;
DELETE FROM `usuarios`;

-- Set @AreaId = (SELECT m.id FROM AREAS m WHERE m.descricao = '1');

SELECT @AreaId = id FROM areas WHERE descricao = '1';


-- Perfis / Permissões
Set @PerfilAdministradorId = RANDOM_UUID();
INSERT into `perfis` (`id`, `nome`, `data_criacao`, `data_atualizacao`) VALUES (@PerfilAdministradorId, 'Administrador', NOW(), NOW());
Set @PerfilEngenheiroId = RANDOM_UUID();
INSERT into `perfis` (`id`, `nome`, `data_criacao`, `data_atualizacao`) VALUES (@PerfilEngenheiroId, 'Engenheiro', NOW(), NOW());
Set @PerfilOperadorId = RANDOM_UUID();
INSERT into `perfis` (`id`, `nome`, `data_criacao`, `data_atualizacao`) VALUES (@PerfilOperadorId, 'Operador', NOW(), NOW());
Set @PerfilTecnicoId = RANDOM_UUID();
INSERT into `perfis` (`id`, `nome`, `data_criacao`, `data_atualizacao`) VALUES (@PerfilTecnicoId, 'Técnico', NOW(), NOW());

-- # Usuario Mobilab
INSERT INTO `usuarios` (`id`, `login`, `senha`, `email`, `nome`, `root`, `area_id`, `perfil_id`, `data_criacao`, `data_atualizacao`) VALUES
                       ('90574f0a-cd7b-477c-9fa6-98c426813ea8', 'mobilab', '$2a$10$bfiF2TyTirIyEh6AmWK1huI5.ol0.OxBC3hM9a7Nrc2x9TM.SBooG', 'mobilab@mobilab.com.br', 'Mobilab',             false, @AreaId, @PerfilAdministradorId, NOW(), NOW()),
                       ('2f0e0547-3135-428b-8f6d-0a1098eca0a5', 'root',    '$2a$10$EzudGIqkxquJjLGawuMrOu9K6S28yc/R/YSAVxsvb5bSryOYWd5eq', 'root@influunt.com.br',   'Administrador Geral', true,  @AreaId, @PerfilAdministradorId, NOW(), NOW());



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

SET @GerarRelatorioTabelaHoraria = RANDOM_UUID();
INSERT INTO `permissoes_app` (`id`, `chave`, `nome`, `descricao`, `data_criacao`, `data_atualizacao`) VALUES
  (@GerarRelatorioTabelaHoraria, 'gerarRelatorioTabelaHoraria', '[Relatórios] - Gerar relatórios de tabelas horárias', 'Gerar um relatório de uma tabela horária em um determinado dia. Se o usuário não tiver essa permissão o link "Tabela Horária" no menu "Relatórios" é escondido.', NOW(), NOW());



-- # CRUD Cidades

Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/cidades', '[Cidade] - Ver lista de cidades', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);
SET @permAppId = RANDOM_UUID();
INSERT INTO `permissoes_app`  (`id`, `chave`, `nome`, `descricao`, `data_criacao`, `data_atualizacao`) VALUES
  (@permAppId, 'listarCidades', '[Cidade] - Ver a lista de Cidades', 'O usuário com essa permissão pode ver a lista de cidades cadastradas no sistema. Se o usuário não tiver essa permissão o link "Cidades" não é mostrado dentro do menu "Cadastros".', NOW(), NOW());
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@permAppId, @PermissaoId);

Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'POST /api/v1/cidades', '[Cidade] - Criar Nova Cidade', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);
SET @permAppId = RANDOM_UUID();
INSERT INTO `permissoes_app`  (`id`, `chave`, `nome`, `descricao`, `data_criacao`, `data_atualizacao`) VALUES
  (@permAppId, 'criarCidades', '[Cidade] - Criar Cidades', 'O usuário com essa permissão pode criar novas cidades no sistema. Se o usuário não tiver essa permissão o botão "+Novo" é escondido.', NOW(), NOW());
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@permAppId, @PermissaoId);

Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'PUT /api/v1/cidades/$id<[^/]+>', '[Cidade] - Editar Cidade', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);
SET @permAppId = RANDOM_UUID();
INSERT INTO `permissoes_app`  (`id`, `chave`, `nome`, `descricao`, `data_criacao`, `data_atualizacao`) VALUES
  (@permAppId, 'editarCidades', '[Cidade] - Editar Cidades','O usuário com essa permissão pode alterar os dados de cidades cadastradas no sistema.  Se o usuário não tiver essa permissão o botão "Editar" é escondido.', NOW(), NOW());
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@permAppId, @PermissaoId);

Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/cidades/$id<[^/]+>', '[Cidade] - Exibir uma Cidade', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);
SET @permAppId = RANDOM_UUID();
INSERT INTO `permissoes_app` (`id`, `chave`, `nome`, `descricao`, `data_criacao`, `data_atualizacao`) VALUES
  (@permAppId, 'visualizarCidades', '[Cidade] - Ver Detalhes de Cidades', 'O usuário com essa permissão pode ver todos os dados relativos às cidades cadastradas no sistema. Se o usuário não tiver essa permissão o botão "Visualizar" é escondido.', NOW(), NOW());
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@permAppId, @PermissaoId);

Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'DELETE /api/v1/cidades/$id<[^/]+>', '[Cidade] - Excluir Cidade', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);
SET @permAppId = RANDOM_UUID();
INSERT INTO `permissoes_app`  (`id`, `chave`, `nome`, `descricao`, `data_criacao`, `data_atualizacao`) VALUES
  (@permAppId, 'removerCidades', '[Cidade] - Excluir Cidades', 'O usuário com essa permissão pode remover do banco de dados as cidades cadastradas no sistema. Se o usuário não tiver essa permissão o botão "Excluir" é escondido.', NOW(), NOW());
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@permAppId, @PermissaoId);




-- # CRUD Areas

Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/areas', '[Área] - Ver a lista de Áreas', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);
SET @permAppId = RANDOM_UUID();
INSERT INTO `permissoes_app` (`id`, `chave`, `nome`, `descricao`, `data_criacao`, `data_atualizacao`) VALUES
  (@permAppId, 'listarAreas', '[Área] - Ver a lista de Áreas','O usuário com essa permissão pode ver a lista de áreas cadastradas no sistema. Se o usuário não tiver essa permissão o link "Áreas" não é mostrado dentro do menu "Cadastros".', NOW(), NOW());
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@permAppId, @PermissaoId);
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@EditarUsuariosId, @PermissaoId);


Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'POST /api/v1/areas', '[Área] - Criar Nova Área', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);
SET @permAppId = RANDOM_UUID();
INSERT INTO `permissoes_app` (`id`, `chave`, `nome`, `descricao`, `data_criacao`, `data_atualizacao`) VALUES
  (@permAppId, 'criarAreas', '[Área] - Criar Áreas','O usuário com essa permissão pode criar novas áreas no sistema. Se o usuário não tiver essa permissão o botão "+Novo" é escondido.', NOW(), NOW());
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@permAppId, @PermissaoId);


Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'PUT /api/v1/areas/$id<[^/]+>', '[Área] - Editar Área', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);
SET @permAppId = RANDOM_UUID();
INSERT INTO `permissoes_app` (`id`, `chave`, `nome`, `descricao`, `data_criacao`, `data_atualizacao`) VALUES
  (@permAppId, 'editarAreas', '[Área] - Editar Áreas', 'O usuário com essa permissão pode alterar os dados de áreas cadastradas no sistema. Se o usuário não tiver essa permissão o botão "Editar" é escondido.', NOW(), NOW());
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@permAppId, @PermissaoId);


Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/areas/$id<[^/]+>', '[Área] - Exibir uma Área', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);
SET @permAppId = RANDOM_UUID();
INSERT INTO `permissoes_app` (`id`, `chave`, `nome`, `descricao`, `data_criacao`, `data_atualizacao`) VALUES
  (@permAppId, 'visualizarAreas', '[Área] - Ver Detalhes de Áreas', 'O usuário com essa permissão pode ver todos os dados relativos às áreas cadastradas no sistema. Se o usuário não tiver essa permissão o botão "Visualizar" é escondido.', NOW(), NOW());
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@permAppId, @PermissaoId);


Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'DELETE /api/v1/areas/$id<[^/]+>', '[Área] - Remover Área', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);
SET @permAppId = RANDOM_UUID();
INSERT INTO `permissoes_app` (`id`, `chave`, `nome`, `descricao`, `data_criacao`, `data_atualizacao`) VALUES
  (@permAppId, 'removerAreas', '[Área] - Excluir Áreas', 'O usuário com essa permissão pode excluir do banco de dados as áreas cadastradas no sistema. Se o usuário não tiver essa permissão o botão "Excluir" é escondido.',NOW(), NOW());
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@permAppId, @PermissaoId);





-- # CRUD Subareas

Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/subareas', '[Subárea] - Ver Lista de SubáreasListar Subárea', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);
SET @permAppId = RANDOM_UUID();
INSERT INTO `permissoes_app` (`id`, `chave`, `nome`, `descricao`, `data_criacao`, `data_atualizacao`) VALUES
  (@permAppId, 'listarSubareas', '[Subárea] - Ver Lista de Subáreas', 'O usuário com essa permissão pode ver a lista de subáreas cadastradas no sistema. Se o usuário não tiver essa permissão o link "Subáreas" não é mostrado dentro do menu "Cadastros".', NOW(), NOW());
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@permAppId, @PermissaoId);
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@CriarAgrupamentoId, @PermissaoId);

Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'POST /api/v1/subareas', '[Subárea] - Criar Nova Subárea', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);
SET @permAppId = RANDOM_UUID();
INSERT INTO `permissoes_app` (`id`, `chave`, `nome`, `descricao`, `data_criacao`, `data_atualizacao`) VALUES
  (@permAppId, 'criarSubareas', '[Subárea] - Criar Subáreas', 'O usuário com essa permissão pode criar novas subáreas no sistema. Se o usuário não tiver essa permissão o botão "+Novo" é escondido.', NOW(), NOW());
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@permAppId, @PermissaoId);

Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'PUT /api/v1/subareas/$id<[^/]+>', '[Subárea] - Editar Subárea', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);
SET @permAppId = RANDOM_UUID();
INSERT INTO `permissoes_app` (`id`, `chave`, `nome`, `descricao`, `data_criacao`, `data_atualizacao`) VALUES
  (@permAppId, 'editarSubareas', '[Subárea] - Editar Subáreas','O usuário com essa permissão pode alterar os dados de subáreas cadastradas no sistema. Se o usuário não tiver essa permissão o botão "Editar" é escondido.', NOW(), NOW());
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@permAppId, @PermissaoId);

Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/subareas/$id<[^/]+>', '[Subárea] - Exibir uma Subárea', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);
SET @permAppId = RANDOM_UUID();
INSERT INTO `permissoes_app` (`id`, `chave`, `nome`, `descricao`, `data_criacao`, `data_atualizacao`) VALUES
  (@permAppId, 'visualizarSubareas', '[Subárea] - Ver Detalhes de Subáreas', 'O usuário com essa permissão pode ver todos os dados relativos às subáreas cadastradas no sistema. Se o usuário não tiver essa permissão o botão "Visualizar" é escondido.', NOW(), NOW());
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@permAppId, @PermissaoId);

Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'DELETE /api/v1/subareas/$id<[^/]+>', '[Subárea] - Remover Subárea', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);
SET @permAppId = RANDOM_UUID();
INSERT INTO `permissoes_app` (`id`, `chave`, `nome`, `descricao`, `data_criacao`, `data_atualizacao`) VALUES
  (@permAppId, 'removerSubareas', '[Subárea] - Excluir Subáreas', 'O usuário com essa permissão pode excluir do banco de dados as subáreas cadastradas no sistema. Se o usuário não tiver essa permissão o botão "Excluir" é escondido.', NOW(), NOW());
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@permAppId, @PermissaoId);




-- # CRUD Fabricantes

Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/fabricantes', '[Fabricante] - Listar Fabricantes', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);
SET @permAppId = RANDOM_UUID();
INSERT INTO `permissoes_app` (`id`, `chave`, `nome`, `descricao`, `data_criacao`, `data_atualizacao`) VALUES
  (@permAppId, 'listarFabricantes', '[Fabricante] - Ver a lista de Fabricantes', 'O usuário com essa permissão pode ver a lista de fabricantes cadastrados no sistema. Se o usuário não tiver essa permissão o link "Fabricantes" não é mostrado dentro do menu "Cadastros".', NOW(), NOW());
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@permAppId, @PermissaoId);


Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'POST /api/v1/fabricantes', '[Fabricante] - Cadastrar Novo Fabricante', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);
SET @permAppId = RANDOM_UUID();
INSERT INTO `permissoes_app`(`id`, `chave`, `nome`, `descricao`, `data_criacao`, `data_atualizacao`) VALUES
  (@permAppId, 'criarFabricantes', '[Fabricante] - Criar Fabricantes', 'O usuário com essa permissão pode criar novos fabricantes no sistema. Se o usuário não tiver essa permissão o botão "+Novo" é escondido.', NOW(), NOW());
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@permAppId, @PermissaoId);


Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'PUT /api/v1/fabricantes/$id<[^/]+>', '[Fabricante] - Editar Fabricante', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);
SET @permAppId = RANDOM_UUID();
INSERT INTO `permissoes_app` (`id`, `chave`, `nome`, `descricao`, `data_criacao`, `data_atualizacao`) VALUES
  (@permAppId, 'editarFabricantes', '[Fabricante] - Editar Fabricantes','O usuário com essa permissão pode alterar os dados de fabricantes cadastrados no sistema. Se o usuário não tiver essa permissão o botão "Editar" é escondido.', NOW(), NOW());
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@permAppId, @PermissaoId);


Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/fabricantes/$id<[^/]+>', '[Fabricante] - Exibir Fabricante', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);
SET @permAppId = RANDOM_UUID();
INSERT INTO `permissoes_app` (`id`, `chave`, `nome`, `descricao`, `data_criacao`, `data_atualizacao`) VALUES
  (@permAppId, 'visualizarFabricantes', '[Fabricante] - Ver Detalhes de Fabricantes', 'O usuário com essa permissão pode ver todos os dados relativos aos fabricantes cadastrados no sistema. Se o usuário não tiver essa permissão o botão "Visualizar" é escondido.', NOW(), NOW());
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@permAppId, @PermissaoId);


Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'DELETE /api/v1/fabricantes/$id<[^/]+>', '[Fabricante] - Remover Fabricante', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);
SET @permAppId = RANDOM_UUID();
INSERT INTO `permissoes_app` (`id`, `chave`, `nome`, `descricao`, `data_criacao`, `data_atualizacao`) VALUES
  (@permAppId, 'removerFabricantes', '[Fabricante] - Excluir Fabricantes', 'O usuário com essa permissão pode apagar do banco de dados os fabricantes cadastrados no sistema. Se o usuário não tiver essa permissão o botão "Excluir" é escondido.', NOW(), NOW());
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@permAppId, @PermissaoId);




-- # CRUD Modelos de Controladores

Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/modelos', '[Modelo Controlador] - Listar Modelos de Controladores', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);
SET @permAppId = RANDOM_UUID();
INSERT INTO `permissoes_app` (`id`, `chave`, `nome`, `descricao`, `data_criacao`, `data_atualizacao`) VALUES
  (@permAppId, 'listarModelos', '[Modelo Controlador] - Ver a lista de  Modelos de Controladores', 'O usuário com essa permissão pode ver a lista de modelos de controladores cadastrados no sistema. Se o usuário não tiver essa permissão o link "Modelo de Controladores" não é mostrado dentro do menu "Cadastros".', NOW(), NOW());
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@permAppId, @PermissaoId);

Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'POST /api/v1/modelos', '[Modelo Controlador] - Cadastrar Novo Modelo de Controlador', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);
SET @permAppId = RANDOM_UUID();
INSERT INTO `permissoes_app` (`id`, `chave`, `nome`, `descricao`, `data_criacao`, `data_atualizacao`) VALUES
  (@permAppId, 'criarModelos', '[Modelo Controlador] - Criar Modelos de Controladores', 'O usuário com essa permissão pode criar novos modelos de controladores no sistema. Se o usuário não tiver essa permissão o botão "+Novo" é escondido.', NOW(), NOW());
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@permAppId, @PermissaoId);

Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'PUT /api/v1/modelos/$id<[^/]+>', '[Modelo Controlador] - Editar Modelo de Controlador', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);
SET @permAppId = RANDOM_UUID();
INSERT INTO `permissoes_app` (`id`, `chave`, `nome`, `descricao`, `data_criacao`, `data_atualizacao`) VALUES
  (@permAppId, 'editarModelos', '[Modelo Controlador] - Editar Modelos de Controladores', 'O usuário com essa permissão pode alterar os dados de modelos de controladores cadastrados no sistema. Se o usuário não tiver essa permissão o botão "Editar" é escondido.', NOW(), NOW());
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@permAppId, @PermissaoId);

Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/modelos/$id<[^/]+>', '[Modelo Controlador] - Exibir Modelo de Controlador', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);
SET @permAppId = RANDOM_UUID();
INSERT INTO `permissoes_app` (`id`, `chave`, `nome`, `descricao`, `data_criacao`, `data_atualizacao`) VALUES
  (@permAppId, 'visualizarModelos', '[Modelo Controlador] - Ver Detalhes de Modelos de Controladores', 'O usuário com essa permissão pode ver todos os dados relativos aos modelos de controladores cadastrados no sistema. Se o usuário não tiver essa permissão o botão "Visualizar" é escondido.', NOW(), NOW());
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@permAppId, @PermissaoId);

Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'DELETE /api/v1/modelos/$id<[^/]+>', '[Modelo Controlador] - Remover Modelo de Controlador', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);
SET @permAppId = RANDOM_UUID();
INSERT INTO `permissoes_app` (`id`, `chave`, `nome`, `descricao`, `data_criacao`, `data_atualizacao`) VALUES
   (@permAppId, 'removerModelos', '[Modelo Controlador] - Exlcuir Modelos de Controladores', 'O usuário com essa permissão pode excluir do banco de dados os modelos de controladores cadastrados no sistema. Se o usuário não tiver essa permissão o botão "Excluir" é escondido.', NOW(), NOW());
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@permAppId, @PermissaoId);




-- # CRUD Agrupamentos

Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/agrupamentos', '[Agrupamento] - Listar Agrupamentos', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);
SET @permAppId = RANDOM_UUID();
INSERT INTO `permissoes_app` (`id`, `chave`, `nome`, `descricao`, `data_criacao`, `data_atualizacao`) VALUES
  (@permAppId, 'listarAgrupamentos', '[Agrupamentos] - Ver a lista de Agrupamentos', 'O usuário com essa permissão pode ver a lista de agrupamentos cadastrados no sistema. Se o usuário não tiver essa permissão o link "Agrupamentos" não é mostrado dentro do menu "Cadastros".', NOW(), NOW());
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@permAppId, @PermissaoId);

Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'POST /api/v1/agrupamentos', '[Agrupamento] - Criar Novo Agrupamento', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);
SET @permAppId = RANDOM_UUID();
INSERT INTO `permissoes_app` (`id`, `chave`, `nome`, `descricao`, `data_criacao`, `data_atualizacao`) VALUES
  (@permAppId, 'editarAgrupamentos', '[Agrupamentos] - Criar Agrupamentos','O usuário com essa permissão pode criar novos agrupamentos no sistema. Se o usuário não tiver essa permissão o botão "+Novo" é escondido.', NOW(), NOW());
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@CriarAgrupamentoId, @PermissaoId);

Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'PUT /api/v1/agrupamentos/$id<[^/]+>', '[Agrupamento] - Editar Agrupamento', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);
SET @permAppId = RANDOM_UUID();
INSERT INTO `permissoes_app` (`id`, `chave`, `nome`, `descricao`, `data_criacao`, `data_atualizacao`) VALUES
  (@permAppId, 'criarAgrupamentos', '[Agrupamentos] - Editar Agrupamentos', 'O usuário com essa permissão pode alterar os dados de agrupamentos cadastrados no sistema. Se o usuário não tiver essa permissão o botão "Editar" é escondido.', NOW(), NOW());
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@permAppId, @PermissaoId);

Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/agrupamentos/$id<[^/]+>', '[Agrupamento] - Exibir um Agrupamento', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);
SET @permAppId = RANDOM_UUID();
INSERT INTO `permissoes_app` (`id`, `chave`, `nome`, `descricao`, `data_criacao`, `data_atualizacao`) VALUES
  (@permAppId, 'visualizarAgrupamentos', '[Agrupamentos] - Ver Detalhes de Agrupamentos', 'O usuário com essa permissão pode ver todos os dados relativos aos agrupamentos cadastrados no sistema. Se o usuário não tiver essa permissão o botão "Visualizar" é escondido.', NOW(), NOW());
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@permAppId, @PermissaoId);

Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'DELETE /api/v1/agrupamentos/$id<[^/]+>', '[Agrupamento] - Remover Agrupamento', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);
SET @permAppId = RANDOM_UUID();
INSERT INTO `permissoes_app` (`id`, `chave`, `nome`, `descricao`, `data_criacao`, `data_atualizacao`)
  VALUES (@permAppId, 'removerAgrupamentos', '[Agrupamentos] - Excluir Agrupamentos', 'O usuário com essa permissão pode excluir do banco de dados os agrupamentos cadastrados no sistema. Se o usuário não tiver essa permissão o botão "Excluir" é escondido.', NOW(), NOW());
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@permAppId, @PermissaoId);




-- # CRUD Imagens

Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'POST /api/v1/imagens', '[Imagem] - Cadastrar Imagem', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@criarControladorId, @PermissaoId);

Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'DELETE /api/v1/imagens/$id<[^/]+>', '[Imagem] - Apagar Imagem', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@criarControladorId, @PermissaoId);

Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'DELETE /api/v1/imagens/$id<[^/]+>/croqui', '[Imagem] - Apagar Imagem de Croqui', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@criarControladorId, @PermissaoId);




-- # CRUD Usuários

Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/usuarios', '[Usuário] - Listar Usuários', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
-- INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);
SET @permAppId = RANDOM_UUID();
INSERT INTO `permissoes_app` (`id`, `chave`, `nome`, `descricao`, `data_criacao`, `data_atualizacao`) VALUES
  (@permAppId, 'listarUsuarios', '[Usuários] - Ver a Lista de Usuários', 'O usuário com essa permissão pode ver a lista de usuários cadastrados no sistema. Se o usuário não tiver essa permissão o link "Usuários" não é mostrado dentro do menu "Cadastros".', NOW(), NOW());
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@permAppId, @PermissaoId);

Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'POST /api/v1/usuarios', '[Usuário] - Criar um Novo Usuário', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
-- INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);
SET @permAppId = RANDOM_UUID();
INSERT INTO `permissoes_app` (`id`, `chave`, `nome`, `descricao`, `data_criacao`, `data_atualizacao`) VALUES
  (@permAppId, 'criarUsuarios', '[Usuários] - Criar Usuários', 'O usuário com essa permissão pode criar novos usuários no sistema. Se o usuário não tiver essa permissão o botão "+Novo" é escondido.', NOW(), NOW());
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@permAppId, @PermissaoId);

Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'PUT /api/v1/usuarios/$id<[^/]+>', '[Usuário] - Editar um Usuário', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
-- INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);
SET @permAppId = RANDOM_UUID();
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@EditarUsuariosId, @PermissaoId);

Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/usuarios/$id<[^/]+>', '[Usuário] - Exibir um Usuário', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
-- INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);
SET @permAppId = RANDOM_UUID();
INSERT INTO `permissoes_app` (`id`, `chave`, `nome`, `descricao`, `data_criacao`, `data_atualizacao`) VALUES
  (@permAppId, 'visualizarUsuarios', '[Usuários] - Ver Detalhes de Usuários', 'O usuário com essa permissão pode ver todos os dados relativos aos usuários cadastrados no sistema. Se o usuário não tiver essa permissão o botão "Visualizar" é escondido.', NOW(), NOW());
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@permAppId, @PermissaoId);
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@EditarUsuariosId, @PermissaoId);

Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'DELETE /api/v1/usuarios/$id<[^/]+>', '[Usuário] - Remover Usuários', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
-- INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);
SET @permAppId = RANDOM_UUID();
INSERT INTO `permissoes_app` (`id`, `chave`, `nome`, `descricao`, `data_criacao`, `data_atualizacao`) VALUES
  (@permAppId, 'removerUsuarios', '[Usuários] - Excluir Usuários', 'O usuário com essa permissão pode apagar do banco de dados os usuários cadastrados no sistema. Se o usuário não tiver essa permissão o botão "Excluir" é escondido.', NOW(), NOW());
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@permAppId, @PermissaoId);

Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/usuarios/$id<[^/]+>/access_log', '[Usuário] - Visualizar Log de Acesso', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
-- INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);
SET @permAppId = RANDOM_UUID();
INSERT INTO `permissoes_app` (`id`, `chave`, `nome`, `descricao`, `data_criacao`, `data_atualizacao`) VALUES
  (@permAppId, 'verLogAcessoUsuarios', '[Usuários] - Ver Log de Acesso de Usuários', 'O usuário com essa permissão pode ver o log de acesso dos usuários cadastrados no sistema. Se o usuário não tiver essa permissão o botão "Log de Acesso" é escondido.', NOW(), NOW());
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@permAppId, @PermissaoId);




-- # CRUD Perfis

Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/perfis', '[Perfil] - Listar Perfis', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
-- INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);
SET @permAppId = RANDOM_UUID();
INSERT INTO `permissoes_app` (`id`, `chave`, `nome`, `descricao`, `data_criacao`, `data_atualizacao`) VALUES
  (@permAppId, 'listarPerfis', '[Perfis] - Ver a lista de Perfis', 'O usuário com essa permissão pode ver a lista de perfis cadastrados no sistema. Se o usuário não tiver essa permissão o link "Perfis" não é mostrado dentro do menu "Cadastros".', NOW(), NOW());
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@permAppId, @PermissaoId);
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@EditarUsuariosId, @PermissaoId);
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@DefinirPermissoesId, @PermissaoId);


Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'POST /api/v1/perfis', '[Perfil] - Criar Novo Perfil', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
-- INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);
SET @permAppId = RANDOM_UUID();
INSERT INTO `permissoes_app` (`id`, `chave`, `nome`, `descricao`, `data_criacao`, `data_atualizacao`) VALUES
  (@permAppId, 'criarPerfis', '[Perfis] - Criar Perfis', 'O usuário com essa permissão pode criar novos perfis no sistema. Se o usuário não tiver essa permissão o botão "+Novo" é escondido.', NOW(), NOW());
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@permAppId, @PermissaoId);


Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'PUT /api/v1/perfis/$id<[^/]+>', '[Perfil] - Editar Perfil', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
-- INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@DefinirPermissoesId, @PermissaoId);
SET @permAppId = RANDOM_UUID();
INSERT INTO `permissoes_app` (`id`, `chave`, `nome`, `descricao`, `data_criacao`, `data_atualizacao`) VALUES
  (@permAppId, 'editarPerfis', '[Perfis] - Editar Perfis', 'O usuário com essa permissão pode ver todos os dados relativos aos perfis cadastrados no sistema. Se o usuário não tiver essa permissão o botão "Visualizar" é escondido.', NOW(), NOW());
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@permAppId, @PermissaoId);


Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/perfis/$id<[^/]+>', '[Perfil] - Exibir Perfil', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@DefinirPermissoesId, @PermissaoId);


Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'DELETE /api/v1/perfis/$id<[^/]+>', '[Perfil] - Remover Perfil', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
-- INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);
SET @permAppId = RANDOM_UUID();
INSERT INTO `permissoes_app` (`id`, `chave`, `nome`, `descricao`, `data_criacao`, `data_atualizacao`) VALUES
  (@permAppId, 'removerPerfis', '[Perfis] - Excluir Perfis', 'O usuário com essa permissão pode excluir do banco de dados os perfis cadastrados no sistema. Se o usuário não tiver essa permissão o botão "Excluir" é escondido.', NOW(), NOW());
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@permAppId, @PermissaoId);


-- # CRUD Permissões

Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/permissoes', '[Permissão] - Listar Permissão', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
-- INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@DefinirPermissoesId, @PermissaoId);

Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'POST /api/v1/permissoes', '[Permissão] - Criar Nova Permissão', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
-- INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);

Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'PUT /api/v1/permissoes/$id<[^/]+>', '[Permissão] - Editar Permissão', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
-- INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);

Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/permissoes/$id<[^/]+>', '[Permissão] - Exibir Permissão', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
-- INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);

Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'DELETE /api/v1/permissoes/$id<[^/]+>', '[Permissão] - Remover Permissão', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
-- INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);





-- # CRUD controladores

Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/controladores', '[Controladores] - Listar Controladores (Programação)', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@listarControladoresId, @PermissaoId);
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@visualizarControladorId, @PermissaoId);
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@criarControladorId, @PermissaoId);
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@verNoMapaId, @PermissaoId);
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@verPlanosId, @PermissaoId);
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@criarPlanosId, @PermissaoId);
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@verTabelaHorariaId, @PermissaoId);
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@criarTabelaHorariaId, @PermissaoId);

Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/controladores/mapas', '[Controladores] - Listar Controladores (Mapas)', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@verNoMapaId, @PermissaoId);

Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/controladores/agrupamentos', '[Controladores] - Listar Controladores (Agrupamentos)', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@CriarAgrupamentoId, @PermissaoId);

Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/controladores/$id<[^/]+>', '[Controladores] - Exibir Controlador', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@visualizarControladorId, @PermissaoId);
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@criarControladorId, @PermissaoId);
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@verPlanosId, @PermissaoId);
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@criarPlanosId, @PermissaoId);
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@verTabelaHorariaId, @PermissaoId);
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@criarTabelaHorariaId, @PermissaoId);

-- TODO: verificar onde essa rota é usada
Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'DELETE /api/v1/controladores/$id<[^/]+>', '[Controladores] - Remover Controlador', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);


Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'POST /api/v1/controladores/$id<[^/]+>/edit', '[Controladores] - Editar Controlador Ativo', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@criarControladorId, @PermissaoId);

Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/controladores/$id<[^/]+>/timeline', '[Controladores] - Visualizar Histórico de Controladores', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@visualizarControladorId, @PermissaoId);

Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/controladores/$id<[^/]+>/pode_editar', '[Controladores] - Editar Controlador', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@criarControladorId, @PermissaoId);
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@criarPlanosId, @PermissaoId);
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@criarTabelaHorariaId, @PermissaoId);

Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'PUT /api/v1/controladores/$id<[^/]+>/finalizar', '[Controladores] - Finalizar Programação do Controlador', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@criarControladorId, @PermissaoId);


-- TODO: verificar onde essa rota é usada
Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'PUT /api/v1/controladores/$id<[^/]+>/ativar', '[Controladores] - Ativar Controlador', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);

Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'DELETE /api/v1/controladores/$id<[^/]+>/cancelar_edicao', '[Controladores] - Cancelar Edição', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@criarControladorId, @PermissaoId);

-- TODO: verificar onde essa rota é usada
Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/controladores/$id<[^/]+>/editar_planos', '[Controladores] - Editar Planos', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);

-- TODO: verificar onde essa rota é usada
Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/controladores/$id<[^/]+>/editar_tabelas_horarias', '[Controladores] - Editar Tabela Horária', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);

-- TODO: verificar onde essa rota é usada
Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'PUT /api/v1/controladores/$id<[^/]+>/atualizar_descricao', '[Controladores] - Finalizar Controlador', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);




-- # Wizard Controladores

Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'POST /api/v1/controladores/dados_basicos', '[Controladores] - Cadastrar Dados Básicos', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@criarControladorId, @PermissaoId);


Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'POST /api/v1/controladores/aneis', '[Controladores] - Cadastrar Anéis', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@criarControladorId, @PermissaoId);

Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'POST /api/v1/controladores/grupos_semaforicos', '[Controladores] - Cadastrar Grupos Semafóricos', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@criarControladorId, @PermissaoId);

Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'POST /api/v1/controladores/associacao_grupos_semaforicos', '[Controladores] - Associar Grupos Semafóricos', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@criarControladorId, @PermissaoId);

Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'POST /api/v1/controladores/verdes_conflitantes', '[Controladores] - Cadastrar Verdes Conflitantes', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@criarControladorId, @PermissaoId);

Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'POST /api/v1/controladores/transicoes_proibidas', '[Controladores] - Cadastrar Transições Proibidas', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@criarControladorId, @PermissaoId);

Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'POST /api/v1/controladores/atraso_de_grupo', '[Controladores] - Cadastrar atraso de grupo', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@criarControladorId, @PermissaoId);

Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'POST /api/v1/controladores/entre_verdes', '[Controladores] - Cadastrar Tabelas Entre-Verdes', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@criarControladorId, @PermissaoId);

Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'POST /api/v1/controladores/associacao_detectores', '[Controladores] - Associar Detectores', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@criarControladorId, @PermissaoId);


Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/controladores/mapas', '[Controladores] - Ver Mapa', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);

Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'DELETE /api/v1/controladores/$id<[^/]+>/remover_planos_tabelas_horarias', '[Controladores] - Remover Planos e Tabelas Horarios do  Controlador', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);



-- # Planos

Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'POST /api/v1/planos', '[Controladores] - Visualizar Planos', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@criarPlanosId, @PermissaoId);

Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/planos/$id<[^/]+>/timeline', '[Planos] - Visualizar Histórico de Planos', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@verPlanosId, @PermissaoId);
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@criarPlanosId, @PermissaoId);

Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'DELETE /api/v1/planos/$id<[^/]+>', '[Planos] - Excluir Plano', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@criarPlanosId, @PermissaoId);

Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'DELETE /api/v1/planos/$id<[^/]+>/cancelar_edicao', '[Planos] - Cancelar Edição de Plano', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@criarPlanosId, @PermissaoId);



-- #Tabela Horária

Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'POST /api/v1/tabelas_horarias', '[Controladores] - Visualizar Tabela Horária', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@criarTabelaHorariaId, @PermissaoId);

Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/tabelas_horarias/$id<[^/]+>/timeline', '[Tabela Horária] - Visualizar Histórico de Tabela Horária', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@verTabelaHorariaId, @PermissaoId);
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@criarTabelaHorariaId, @PermissaoId);

Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'DELETE /api/v1/tabelas_horarias/$id<[^/]+>/cancelar_edicao', '[Tabela Horária] - Cancelar edição', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@criarTabelaHorariaId, @PermissaoId);


-- #Exclusão

Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'DELETE /api/v1/estagios/$id<[^/]+>', '[Estágios] - Excluir Estágio', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@criarControladorId, @PermissaoId);

Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'DELETE /api/v1/detectores/$id<[^/]+>', '[Detectores] - Excluir Detector', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@criarControladorId, @PermissaoId);

Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'DELETE /api/v1/eventos/$id<[^/]+>', '[Eventos] - Excluir Evento', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@criarTabelaHorariaId, @PermissaoId);


-- # Helpers

Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/helpers/controlador', '[Controlador] - Exibir tela de Cadastro de Controladores', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@visualizarControladorId, @PermissaoId);
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@criarControladorId, @PermissaoId);
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@verPlanosId, @PermissaoId);
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@criarPlanosId, @PermissaoId);



-- # Auditorias

Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/auditorias', '[Auditoria] - Ver a lista de modificações (Auditoria)', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);
SET @permAppId = RANDOM_UUID();
INSERT INTO `permissoes_app` (`id`, `chave`, `nome`, `descricao`, `data_criacao`, `data_atualizacao`) VALUES
  (@permAppId, 'listarAuditorias', '[Auditoria] - Ver a lista de modificações (Auditoria)', 'O usuário com essa permissão pode ver a lista de modificações nas entidades cadastradas no sistema. Se o usuário não tiver essa permissão o menu "Auditoria" é escondido.', NOW(), NOW());
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@permAppId, @PermissaoId);

Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/auditorias/$id<[^/]+>', '[Auditoria] - Visualizar Auditoria', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);
SET @permAppId = RANDOM_UUID();
INSERT INTO `permissoes_app` (`id`, `chave`, `nome`, `descricao`, `data_criacao`, `data_atualizacao`) VALUES
  (@permAppId, 'visualizarAuditorias', '[Auditoria] - Ver Detalhes das modificações (Auditoria)', 'O usuário com essa permissão pode ver os detalhes das modificações realizadas no sistema. Se o usuário não tiver essa permissão o botão "Visualizar" é escondido.', NOW(), NOW());
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@permAppId, @PermissaoId);



-- # Faixas de Valores

Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/faixas_de_valores', '[Faixas de Valores] - Visualizar Faixas de Valores', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
-- INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);
SET @permAppId = RANDOM_UUID();
INSERT INTO `permissoes_app` (`id`, `chave`, `nome`, `descricao`, `data_criacao`, `data_atualizacao`) VALUES
  (@permAppId, 'visualizarFaixasDeValores', '[Faixas de Valores] - Ver Faixas de Valores', 'O usuário com essa permissão pode ver os limites cadastrados especificados para os valores utilizados na parâmetros da programação. Se o usuário não tiver essa permissão o link "Faixas de Valores" não é mostrado dentro do menu "Cadastros".', NOW(), NOW());
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@permAppId, @PermissaoId);

Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'PUT /api/v1/faixas_de_valores', '[Faixas de Valores] - Editar Faixas de Valores', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
-- INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);
SET @permAppId = RANDOM_UUID();
INSERT INTO `permissoes_app` (`id`, `chave`, `nome`, `descricao`, `data_criacao`, `data_atualizacao`) VALUES
  (@permAppId, 'editarFaixasDeValores', '[Faixas de Valores] - Editar Faixas de Valores', 'O usuário com essa permissão pode alterar os limites cadastrados especificados para os valores utilizados na parâmetros da programação. Se o usuário não tiver essa permissão a edição dos valores fica bloqueada.', NOW(), NOW());
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@permAppId, @PermissaoId);



-- # ModoOperacao Controlador

Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/monitoramento/modo_operacoes_controladores/$id<[^/]+>', '[Monitoramento] - Visualizar Modo de Operação do Controlador', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);

Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/monitoramento/modo_operacoes_controladores', '[Monitoramento] - Visualizar Modos de Operação dos Controladores', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);

Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/monitoramento/modo_operacoes_controladores/$id<[^/]+>/ultimo_status', '[Monitoramento] - Visualizar Último Modo de Operação de Controlador', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);

Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/monitoramento/modo_operacoes_controladores/$id<[^/]+>/historico/$pagina<[^/]+>/$tamanho<[^/]+>', '[Monitoramento] - Visualizar Último Modo de Operação de Controlador com Paginação', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);

Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/monitoramento/status_aneis', '[Monitoramento] - Visualizar Status dos aneis', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);

-- # Erros Controladores

Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/monitoramento/erros_controladores/$id<[^/]+>', '[Monitoramento] - Visualizar Erros do Controlador', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);

Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/monitoramento/erros_controladores', '[Monitoramento] - Visualizar Erros dos Controladores', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);

Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/monitoramento/erros_controladores/$id<[^/]+>/ultimo_status', '[Monitoramento] - Visualizar Último Erros de Controlador', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);

Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/monitoramento/erros_controladores/$id<[^/]+>/historico/$pagina<[^/]+>/$tamanho<[^/]+>', '[Monitoramento] - Visualizar Último Erros de Controlador com Paginação', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);



-- # ImposicaoPlanos Controlador

Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/monitoramento/imposicoes_planos/$id<[^/]+>', '[Monitoramento] - Visualizar Imposição de Plano do Controlador', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);

Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/monitoramento/imposicoes_planos', '[Monitoramento] - Visualizar Imposição de Planos dos Controladores', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);

Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/monitoramento/imposicoes_planos/$id<[^/]+>/ultimo_status', '[Monitoramento] - Visualizar Última Imposição de Plano de Controlador', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);

Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/monitoramento/imposicoes_planos/$id<[^/]+>/historico/$pagina<[^/]+>/$tamanho<[^/]+>', '[Monitoramento] - Visualizar Última Imposição de Plano de Controlador com Paginação', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);



-- # Monitoramento

Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/monitoramento/status_controladores', '[Monitoramento] - Visualizar Status dos Controladores', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);
SET @permAppId = RANDOM_UUID();
INSERT INTO `permissoes_app`(`id`, `chave`, `nome`, `descricao`, `data_criacao`, `data_atualizacao`) VALUES
  (@permAppId, 'visualizarStatusControladores', '[Dashboard] - Ver Status dos Controladores', 'O usuário com essa permissão pode acessar o dashboard com os status dos controladores em operação.', NOW(), NOW());
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@permAppId, @PermissaoId);

Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/monitoramento/controladores_onlines', '[Monitoramento] - Visualizar Status dos Controladores Online', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);
SET @permAppId = RANDOM_UUID();
INSERT INTO `permissoes_app` (`id`, `chave`, `nome`, `descricao`, `data_criacao`, `data_atualizacao`) VALUES
  (@permAppId, 'visualizarStatusControladoresOnline', '[Dashboard] - Ver a lista de Controladores Online', 'O usuário com essa permissão pode acessar a lista com os controladores onlines no momento.', NOW(), NOW());
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@permAppId, @PermissaoId);

Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/monitoramento/controladores_offlines', '[Monitoramento] - Visualizar Status dos Controladores Offline', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);
SET @permAppId = RANDOM_UUID();
INSERT INTO `permissoes_app` (`id`, `chave`, `nome`, `descricao`, `data_criacao`, `data_atualizacao`) VALUES
  (@permAppId, 'visualizarStatusControladoresOffline', '[Dashboard] - Ver a lista de Controladores Offline', 'O usuário com essa permissão pode acessar a lista com os controladores offlines no momento.', NOW(), NOW());
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@permAppId, @PermissaoId);

Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/monitoramento/detalhe_controlador/$id<[^/]+>', '[Monitoramento] - Visualizar Detalhes de Controlador', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);
SET @permAppId = RANDOM_UUID();
INSERT INTO `permissoes_app` (`id`, `chave`, `nome`, `descricao`, `data_criacao`, `data_atualizacao`) VALUES
  (@permAppId, 'visualizarDetalhesControlador', '[Dashboard] - Ver Detalhes do Status dos Controladores', 'O usuário com essa permissão pode acessar ver os detalhes do status de um controlador.', NOW(), NOW());
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@permAppId, @PermissaoId);



-- # StatusDevice Conexão

Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/monitoramento/status_conexao/$id<[^/]+>', '[Monitoramento] - Visualizar Status da Conexão de Controlador', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);

Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/monitoramento/status_conexao', '[Monitoramento] - Visualizar Status da Conexão de Controladores', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);

Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/monitoramento/status_conexao/$id<[^/]+>/ultimo_status', '[Monitoramento] - Visualizar Último Status da Conexão de Controlador', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);

Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/monitoramento/status_conexao/$id<[^/]+>/historico/$pagina<[^/]+>/$tamanho<[^/]+>', '[Monitoramento] - Visualizar Último Status da Conexão de Controlador com Paginação', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);



-- # StatusDevice Status

Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/monitoramento/status/$id<[^/]+>', '[Monitoramento] - Visualizar Status de Controlador', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);

Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/monitoramento/status', '[Monitoramento] - Visualizar Status de Controladores', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);

Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/monitoramento/status/$id<[^/]+>/ultimo_status', '[Monitoramento] - Visualizar Último Status de Controlador', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);

Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/monitoramento/status/$id<[^/]+>/historico/$pagina<[^/]+>/$tamanho<[^/]+>', '[Monitoramento] - Visualizar Último Status de Controlador com Paginação', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);

-- # Permissao visualizar todas as Areas

Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'visualizarTodasAreas', '[Administração] - Visualizar todas as Areas', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
SET @permAppId = RANDOM_UUID();
INSERT INTO `permissoes_app` (`id`, `chave`, `nome`, `descricao`, `data_criacao`, `data_atualizacao`) VALUES
  (@permAppId, 'visualizarTodasAreas', '[Administração] - Visualizar Todas as Áreas', 'O usuário com essa permissão pode ver os dados relativos a todas as áreas cadastradas. Sem essa permissão o usuário somente vê os dados associados com a sua área.', NOW(), NOW());
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@permAppId, @PermissaoId);


-- # Permissao Relatorios

Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/relatorios/auditoria', '[Relatórios] - Relatório de Auditoria', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
SET @permAppId = RANDOM_UUID();
INSERT INTO `permissoes_app` (`id`, `chave`, `nome`, `descricao`, `data_criacao`, `data_atualizacao`) VALUES
  (@permAppId, 'gerarRelatorioAuditorias', '[Relatórios] - Gerar Relatórios Auditoria', 'O usuário com essa permissão pode gerar relatórios de auditoria.', NOW(), NOW());
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@permAppId, @PermissaoId);

Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/relatorios/controladores_status', '[Relatórios] - Relatório de Controladores por Status', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
SET @permAppId = RANDOM_UUID();
INSERT INTO `permissoes_app` (`id`, `chave`, `nome`, `descricao`, `data_criacao`, `data_atualizacao`) VALUES
  (@permAppId, 'gerarRelatorioControladoresStatus', '[Relatórios] - Gerar Relatórios Controladores por Status', 'O usuário com essa permissão pode gerar relatórios de controladores por status.', NOW(), NOW());
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@permAppId, @PermissaoId);

Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/relatorios/controladores_falhas', '[Relatórios] - Relatório de Controladores por Falhas', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
SET @permAppId = RANDOM_UUID();
INSERT INTO `permissoes_app` (`id`, `chave`, `nome`, `descricao`, `data_criacao`, `data_atualizacao`) VALUES
  (@permAppId, 'gerarRelatorioControladoresFalhas', '[Relatórios] - Gerar Relatórios Controladores por Falhas', 'O usuário com essa permissão pode gerar relatórios de controladores por falhas.', NOW(), NOW());
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@permAppId, @PermissaoId);

Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/relatorios/controladores_entreverdes', '[Relatórios] - Relatório de Controladores com Atualizações Entreverdes', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
SET @permAppId = RANDOM_UUID();
INSERT INTO `permissoes_app` (`id`, `chave`, `nome`, `descricao`, `data_criacao`, `data_atualizacao`) VALUES
  (@permAppId, 'gerarRelatorioControladoresEntreverdes', '[Relatórios] - Gerar Relatórios Controladores com Atualizações Entreverdes', 'O usuário com essa permissão pode gerar relatórios de controladores com atualizações entreverdes.', NOW(), NOW());
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@permAppId, @PermissaoId);

Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/relatorios/planos', '[Relatórios] - Relatório de Planos', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
SET @permAppId = RANDOM_UUID();
INSERT INTO `permissoes_app` (`id`, `chave`, `nome`, `descricao`, `data_criacao`, `data_atualizacao`) VALUES
  (@permAppId, 'gerarRelatorioPlanos', '[Relatórios] - Gerar Relatórios Planos', 'O usuário com essa permissão pode gerar relatórios de planos.', NOW(), NOW());
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@permAppId, @PermissaoId);

Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/relatorios/tabela_horaria', '[Relatórios] - Relatório de Tabela Horária em um determinado dia.', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@GerarRelatorioTabelaHoraria, @PermissaoId);

Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/relatorios/controlador/$id<[^/]+>', '[Relatórios] - Relatório do controlador', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
SET @permAppId = RANDOM_UUID();
INSERT INTO `permissoes_app` (`id`, `chave`, `nome`, `descricao`, `data_criacao`, `data_atualizacao`) VALUES
  (@permAppId, 'gerarRelatorioControlador', '[Relatórios] - Gerar Relatório do Controlador em PDF', 'O usuário com essa permissão pode gerar relatórios de controladores e fazer o download em PDF.', NOW(), NOW());
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@permAppId, @PermissaoId);

-- # Permissao Simulação
Set @SimularId = RANDOM_UUID();
INSERT INTO `permissoes_app` (`id`, `chave`, `nome`, `descricao`, `data_criacao`, `data_atualizacao`) VALUES
  (@SimularId, 'simularControlador', '[Simulação] - Simular Funcionamento de Controlador', 'O usuário com essa permissão pode realizar a simulação de um controlador.', NOW(), NOW());

Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/controladores/$id<[^/]+>/simulacao', '[Controladores] - Listar controladores para simulação', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@SimularId, @PermissaoId);

Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'POST /api/v1/simulacao', '[Simulação] - Simular Funcionamento de Controlador', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@SimularId, @PermissaoId);
