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

Set @PerfilBasicoCrud = RANDOM_UUID();

Set @cidadeID = RANDOM_UUID();
Set @areaId = RANDOM_UUID();
INSERT INTO `cidades` (`id`, `id_json`, `nome`, `data_criacao`, `data_atualizacao`) VALUES (@cidadeID,RANDOM_UUID(),'São Paulo',NOW(),NOW());
INSERT INTO `areas` (`id`, `id_json`, `descricao`, `cidade_id`, `data_criacao`, `data_atualizacao`) VALUES (@areaId,RANDOM_UUID(),1,@cidadeID,NOW(),NOW());
INSERT into `perfis` (`id`, `nome`, `data_criacao`, `data_atualizacao`) VALUES (@PerfilBasicoCrud, 'Coordenador', NOW(), NOW());
INSERT INTO `usuarios` (`id`, `login`, `senha`, `email`, `nome`, `root`, `area_id`, `perfil_id`, `data_criacao`, `data_atualizacao`) VALUES
                       (RANDOM_UUID(), 'mobilab', '$2a$10$bfiF2TyTirIyEh6AmWK1huI5.ol0.OxBC3hM9a7Nrc2x9TM.SBooG', 'mobilab@mobilab.com.br', 'Mobilab', false, @areaId, @PerfilBasicoCrud, NOW(), NOW());


-- # CRUD Cidades

Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/cidades', '[Cidade] - Listar Cidades', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilBasicoCrud, @PermissaoId);
SET @permAppId = RANDOM_UUID();
INSERT INTO `permissoes_app`  (`id`, `chave`, `nome`, `descricao`, `data_criacao`, `data_atualizacao`) VALUES
  (@permAppId, 'listarCidades', '[Cidade] - Ver a lista de Cidades', 'O usuário com essa permissão pode ver a lista de cidades cadastradas no sistema. Se o usuário não tiver essa permissão o link "Cidades" não é mostrado dentro do menu "Cadastros".', NOW(), NOW());
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@permAppId, @PermissaoId);

Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'POST /api/v1/cidades', '[Cidade] - Criar Nova Cidade', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilBasicoCrud, @PermissaoId);
SET @permAppId = RANDOM_UUID();
INSERT INTO `permissoes_app`  (`id`, `chave`, `nome`, `descricao`, `data_criacao`, `data_atualizacao`) VALUES
  (@permAppId, 'criarCidades', '[Cidade] - Criar Cidades', 'O usuário com essa permissão pode criar novas cidades no sistema. Se o usuário não tiver essa permissão o botão "+Novo" é escondido.', NOW(), NOW());
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@permAppId, @PermissaoId);

Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'PUT /api/v1/cidades/$id<[^/]+>', '[Cidade] - Editar Cidade', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilBasicoCrud, @PermissaoId);
SET @permAppId = RANDOM_UUID();
INSERT INTO `permissoes_app`  (`id`, `chave`, `nome`, `descricao`, `data_criacao`, `data_atualizacao`) VALUES
  (@permAppId, 'editarCidades', '[Cidade] - Editar Cidades','O usuário com essa permissão pode alterar os dados de cidades cadastradas no sistema.  Se o usuário não tiver essa permissão o botão "Editar" é escondido.', NOW(), NOW());
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@permAppId, @PermissaoId);

Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/cidades/$id<[^/]+>', '[Cidade] - Exibir uma Cidade', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilBasicoCrud, @PermissaoId);
SET @permAppId = RANDOM_UUID();
INSERT INTO `permissoes_app` (`id`, `chave`, `nome`, `descricao`, `data_criacao`, `data_atualizacao`) VALUES
  (@permAppId, 'visualizarCidades', '[Cidade] - Ver Detalhes de Cidades', 'O usuário com essa permissão pode ver todos os dados relativos às cidades cadastradas no sistema. Se o usuário não tiver essa permissão o botão "Visualizar" é escondido.', NOW(), NOW());
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@permAppId, @PermissaoId);

Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'DELETE /api/v1/cidades/$id<[^/]+>', '[Cidade] - Remover Cidade', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilBasicoCrud, @PermissaoId);
SET @permAppId = RANDOM_UUID();
INSERT INTO `permissoes_app`  (`id`, `chave`, `nome`, `descricao`, `data_criacao`, `data_atualizacao`) VALUES
  (@permAppId, 'removerCidades', '[Cidade] - Excluir Cidades', 'O usuário com essa permissão pode remover do banco de dados as cidades cadastradas no sistema. Se o usuário não tiver essa permissão o botão "Excluir" é escondido.', NOW(), NOW());
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@permAppId, @PermissaoId);


-- # CRUD Areas

Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/areas', '[Área] - Listar Áreas', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilBasicoCrud, @PermissaoId);
SET @permAppId = RANDOM_UUID();
INSERT INTO `permissoes_app` (`id`, `chave`, `nome`, `descricao`, `data_criacao`, `data_atualizacao`) VALUES
  (@permAppId, 'listarAreas', '[Área] - Ver a lista de Áreas','O usuário com essa permissão pode ver a lista de áreas cadastradas no sistema. Se o usuário não tiver essa permissão o link "Áreas" não é mostrado dentro do menu "Cadastros".', NOW(), NOW());
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@permAppId, @PermissaoId);


Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'POST /api/v1/areas', '[Área] - Criar Nova Área', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilBasicoCrud, @PermissaoId);
SET @permAppId = RANDOM_UUID();
INSERT INTO `permissoes_app` (`id`, `chave`, `nome`, `descricao`, `data_criacao`, `data_atualizacao`) VALUES
  (@permAppId, 'criarAreas', '[Área] - Criar Áreas','O usuário com essa permissão pode criar novas áreas no sistema. Se o usuário não tiver essa permissão o botão "+Novo" é escondido.', NOW(), NOW());
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@permAppId, @PermissaoId);


Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'PUT /api/v1/areas/$id<[^/]+>', '[Área] - Editar Área', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilBasicoCrud, @PermissaoId);
SET @permAppId = RANDOM_UUID();
INSERT INTO `permissoes_app` (`id`, `chave`, `nome`, `descricao`, `data_criacao`, `data_atualizacao`) VALUES
  (@permAppId, 'editarAreas', '[Área] - Editar Áreas', 'O usuário com essa permissão pode alterar os dados de áreas cadastradas no sistema. Se o usuário não tiver essa permissão o botão "Editar" é escondido.', NOW(), NOW());
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@permAppId, @PermissaoId);


Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/areas/$id<[^/]+>', '[Área] - Exibir uma Área', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilBasicoCrud, @PermissaoId);
SET @permAppId = RANDOM_UUID();
INSERT INTO `permissoes_app` (`id`, `chave`, `nome`, `descricao`, `data_criacao`, `data_atualizacao`) VALUES
  (@permAppId, 'visualizarAreas', '[Área] - Ver Detalhes de Áreas', 'O usuário com essa permissão pode ver todos os dados relativos às áreas cadastradas no sistema. Se o usuário não tiver essa permissão o botão "Visualizar" é escondido.', NOW(), NOW());
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@permAppId, @PermissaoId);


Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'DELETE /api/v1/areas/$id<[^/]+>', '[Área] - Remover Área', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilBasicoCrud, @PermissaoId);
SET @permAppId = RANDOM_UUID();
INSERT INTO `permissoes_app` (`id`, `chave`, `nome`, `descricao`, `data_criacao`, `data_atualizacao`) VALUES
  (@permAppId, 'removerAreas', '[Área] - Excluir Áreas', 'O usuário com essa permissão pode excluir do banco de dados as áreas cadastradas no sistema. Se o usuário não tiver essa permissão o botão "Excluir" é escondido.',NOW(), NOW());
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@permAppId, @PermissaoId);



-- # CRUD Subareas

Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/subareas', '[Subárea] - Listar Subárea', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilBasicoCrud, @PermissaoId);
SET @permAppId = RANDOM_UUID();
INSERT INTO `permissoes_app` (`id`, `chave`, `nome`, `descricao`, `data_criacao`, `data_atualizacao`) VALUES
  (@permAppId, 'listarSubareas', '[Subárea] - Ver Lista de Subáreas', 'O usuário com essa permissão pode ver a lista de subáreas cadastradas no sistema. Se o usuário não tiver essa permissão o link "Subáreas" não é mostrado dentro do menu "Cadastros".', NOW(), NOW());
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@permAppId, @PermissaoId);

Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'POST /api/v1/subareas', '[Subárea] - Criar Nova Subárea', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilBasicoCrud, @PermissaoId);
SET @permAppId = RANDOM_UUID();
INSERT INTO `permissoes_app` (`id`, `chave`, `nome`, `descricao`, `data_criacao`, `data_atualizacao`) VALUES
  (@permAppId, 'criarSubareas', '[Subárea] - Criar Subáreas', 'O usuário com essa permissão pode criar novas subáreas no sistema. Se o usuário não tiver essa permissão o botão "+Novo" é escondido.', NOW(), NOW());
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@permAppId, @PermissaoId);

Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'PUT /api/v1/subareas/$id<[^/]+>', '[Subárea] - Editar Subárea', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilBasicoCrud, @PermissaoId);
SET @permAppId = RANDOM_UUID();
INSERT INTO `permissoes_app` (`id`, `chave`, `nome`, `descricao`, `data_criacao`, `data_atualizacao`) VALUES
  (@permAppId, 'editarSubareas', '[Subárea] - Editar Subáreas','O usuário com essa permissão pode alterar os dados de subáreas cadastradas no sistema. Se o usuário não tiver essa permissão o botão "Editar" é escondido.', NOW(), NOW());
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@permAppId, @PermissaoId);

Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/subareas/$id<[^/]+>', '[Subárea] - Exibir uma Subárea', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilBasicoCrud, @PermissaoId);
SET @permAppId = RANDOM_UUID();
INSERT INTO `permissoes_app` (`id`, `chave`, `nome`, `descricao`, `data_criacao`, `data_atualizacao`) VALUES
  (@permAppId, 'visualizarSubareas', '[Subárea] - Ver Detalhes de Subáreas', 'O usuário com essa permissão pode ver todos os dados relativos às subáreas cadastradas no sistema. Se o usuário não tiver essa permissão o botão "Visualizar" é escondido.', NOW(), NOW());
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@permAppId, @PermissaoId);

Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'DELETE /api/v1/subareas/$id<[^/]+>', '[Subárea] - Remover Subárea', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilBasicoCrud, @PermissaoId);
SET @permAppId = RANDOM_UUID();
INSERT INTO `permissoes_app` (`id`, `chave`, `nome`, `descricao`, `data_criacao`, `data_atualizacao`) VALUES
  (@permAppId, 'removerSubareas', '[Subárea] - Excluir Subáreas', 'O usuário com essa permissão pode excluir do banco de dados as subáreas cadastradas no sistema. Se o usuário não tiver essa permissão o botão "Excluir" é escondido.', NOW(), NOW());
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@permAppId, @PermissaoId);



-- # CRUD Fabricantes

Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/fabricantes', '[Fabricante] - Listar Fabricantes', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilBasicoCrud, @PermissaoId);
SET @permAppId = RANDOM_UUID();
INSERT INTO `permissoes_app` (`id`, `chave`, `nome`, `descricao`, `data_criacao`, `data_atualizacao`) VALUES
  (@permAppId, 'listarFabricantes', '[Fabricante] - Ver a lista de Fabricantes', 'O usuário com essa permissão pode ver a lista de fabricantes cadastrados no sistema. Se o usuário não tiver essa permissão o link "Fabricantes" não é mostrado dentro do menu "Cadastros".', NOW(), NOW());
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@permAppId, @PermissaoId);


Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'POST /api/v1/fabricantes', '[Fabricante] - Cadastrar Novo Fabricante', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilBasicoCrud, @PermissaoId);
SET @permAppId = RANDOM_UUID();
INSERT INTO `permissoes_app`(`id`, `chave`, `nome`, `descricao`, `data_criacao`, `data_atualizacao`) VALUES
  (@permAppId, 'criarFabricantes', '[Fabricante] - Criar Fabricantes', 'O usuário com essa permissão pode criar novos fabricantes no sistema. Se o usuário não tiver essa permissão o botão "+Novo" é escondido.', NOW(), NOW());
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@permAppId, @PermissaoId);


Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'PUT /api/v1/fabricantes/$id<[^/]+>', '[Fabricante] - Editar Fabricante', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilBasicoCrud, @PermissaoId);
SET @permAppId = RANDOM_UUID();
INSERT INTO `permissoes_app` (`id`, `chave`, `nome`, `descricao`, `data_criacao`, `data_atualizacao`) VALUES
  (@permAppId, 'editarFabricantes', '[Fabricante] - Editar Fabricantes','O usuário com essa permissão pode alterar os dados de fabricantes cadastrados no sistema. Se o usuário não tiver essa permissão o botão "Editar" é escondido.', NOW(), NOW());
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@permAppId, @PermissaoId);


Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/fabricantes/$id<[^/]+>', '[Fabricante] - Exibir Fabricante', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilBasicoCrud, @PermissaoId);
SET @permAppId = RANDOM_UUID();
INSERT INTO `permissoes_app` (`id`, `chave`, `nome`, `descricao`, `data_criacao`, `data_atualizacao`) VALUES
  (@permAppId, 'visualizarFabricantes', '[Fabricante] - Ver Detalhes de Fabricantes', 'O usuário com essa permissão pode ver todos os dados relativos aos fabricantes cadastrados no sistema. Se o usuário não tiver essa permissão o botão "Visualizar" é escondido.', NOW(), NOW());
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@permAppId, @PermissaoId);


Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'DELETE /api/v1/fabricantes/$id<[^/]+>', '[Fabricante] - Remover Fabricante', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilBasicoCrud, @PermissaoId);
SET @permAppId = RANDOM_UUID();
INSERT INTO `permissoes_app` (`id`, `chave`, `nome`, `descricao`, `data_criacao`, `data_atualizacao`) VALUES
  (@permAppId, 'removerFabricantes', '[Fabricante] - Excluir Fabricantes', 'O usuário com essa permissão pode apagar do banco de dados os fabricantes cadastrados no sistema. Se o usuário não tiver essa permissão o botão "Excluir" é escondido.', NOW(), NOW());
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@permAppId, @PermissaoId);

COMMIT;
