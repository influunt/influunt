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
INSERT INTO `permissoes_app` (`id`, `chave`, `nome`, `data_criacao`, `data_atualizacao`) VALUES (@permAppId, 'listarCidades', '[Cidade] - Listar Cidades', NOW(), NOW());
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@permAppId, @PermissaoId);

Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'POST /api/v1/cidades', '[Cidade] - Criar Nova Cidade', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilBasicoCrud, @PermissaoId);
SET @permAppId = RANDOM_UUID();
INSERT INTO `permissoes_app` (`id`, `chave`, `nome`, `data_criacao`, `data_atualizacao`) VALUES (@permAppId, 'criarCidades', '[Cidade] - Criar Cidades', NOW(), NOW());
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@permAppId, @PermissaoId);

Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'PUT /api/v1/cidades/$id<[^/]+>', '[Cidade] - Editar Cidade', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilBasicoCrud, @PermissaoId);
SET @permAppId = RANDOM_UUID();
INSERT INTO `permissoes_app` (`id`, `chave`, `nome`, `data_criacao`, `data_atualizacao`) VALUES (@permAppId, 'editarCidades', '[Cidade] - Editar Cidades', NOW(), NOW());
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@permAppId, @PermissaoId);

Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/cidades/$id<[^/]+>', '[Cidade] - Exibir uma Cidade', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilBasicoCrud, @PermissaoId);
SET @permAppId = RANDOM_UUID();
INSERT INTO `permissoes_app` (`id`, `chave`, `nome`, `data_criacao`, `data_atualizacao`) VALUES (@permAppId, 'visualizarCidades', '[Cidade] - Ver Detalhes de Cidades', NOW(), NOW());
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@permAppId, @PermissaoId);

Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'DELETE /api/v1/cidades/$id<[^/]+>', '[Cidade] - Remover Cidade', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilBasicoCrud, @PermissaoId);
SET @permAppId = RANDOM_UUID();
INSERT INTO `permissoes_app` (`id`, `chave`, `nome`, `data_criacao`, `data_atualizacao`) VALUES (@permAppId, 'removerCidades', '[Cidade] - Remover Cidades', NOW(), NOW());
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@permAppId, @PermissaoId);


-- # CRUD Areas

Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/areas', '[Área] - Listar Áreas', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilBasicoCrud, @PermissaoId);
SET @permAppId = RANDOM_UUID();
INSERT INTO `permissoes_app` (`id`, `chave`, `nome`, `data_criacao`, `data_atualizacao`) VALUES (@permAppId, 'listarAreas', '[Área] - Listar Áreas', NOW(), NOW());
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@permAppId, @PermissaoId);


Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'POST /api/v1/areas', '[Área] - Criar Nova Área', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilBasicoCrud, @PermissaoId);
SET @permAppId = RANDOM_UUID();
INSERT INTO `permissoes_app` (`id`, `chave`, `nome`, `data_criacao`, `data_atualizacao`) VALUES (@permAppId, 'criarAreas', '[Área] - Criar Áreas', NOW(), NOW());
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@permAppId, @PermissaoId);


Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'PUT /api/v1/areas/$id<[^/]+>', '[Área] - Editar Área', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilBasicoCrud, @PermissaoId);
SET @permAppId = RANDOM_UUID();
INSERT INTO `permissoes_app` (`id`, `chave`, `nome`, `data_criacao`, `data_atualizacao`) VALUES (@permAppId, 'editarAreas', '[Área] - Editar Áreas', NOW(), NOW());
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@permAppId, @PermissaoId);


Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/areas/$id<[^/]+>', '[Área] - Exibir uma Área', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilBasicoCrud, @PermissaoId);
SET @permAppId = RANDOM_UUID();
INSERT INTO `permissoes_app` (`id`, `chave`, `nome`, `data_criacao`, `data_atualizacao`) VALUES (@permAppId, 'visualizarAreas', '[Área] - Ver Detalhes de Áreas', NOW(), NOW());
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@permAppId, @PermissaoId);


Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'DELETE /api/v1/areas/$id<[^/]+>', '[Área] - Remover Área', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilBasicoCrud, @PermissaoId);
SET @permAppId = RANDOM_UUID();
INSERT INTO `permissoes_app` (`id`, `chave`, `nome`, `data_criacao`, `data_atualizacao`) VALUES (@permAppId, 'removerAreas', '[Área] - Remover Áreas', NOW(), NOW());
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@permAppId, @PermissaoId);



-- # CRUD Subareas

Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/subareas', '[Subárea] - Listar Subárea', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilBasicoCrud, @PermissaoId);
SET @permAppId = RANDOM_UUID();
INSERT INTO `permissoes_app` (`id`, `chave`, `nome`, `data_criacao`, `data_atualizacao`) VALUES (@permAppId, 'listarSubareas', '[Subárea] - Listar Subáreas', NOW(), NOW());
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@permAppId, @PermissaoId);

Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'POST /api/v1/subareas', '[Subárea] - Criar Nova Subárea', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilBasicoCrud, @PermissaoId);
SET @permAppId = RANDOM_UUID();
INSERT INTO `permissoes_app` (`id`, `chave`, `nome`, `data_criacao`, `data_atualizacao`) VALUES (@permAppId, 'criarSubareas', '[Subárea] - Criar Subáreas', NOW(), NOW());
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@permAppId, @PermissaoId);

Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'PUT /api/v1/subareas/$id<[^/]+>', '[Subárea] - Editar Subárea', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilBasicoCrud, @PermissaoId);
SET @permAppId = RANDOM_UUID();
INSERT INTO `permissoes_app` (`id`, `chave`, `nome`, `data_criacao`, `data_atualizacao`) VALUES (@permAppId, 'editarSubareas', '[Subárea] - Editar Subáreas', NOW(), NOW());
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@permAppId, @PermissaoId);

Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/subareas/$id<[^/]+>', '[Subárea] - Exibir uma Subárea', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilBasicoCrud, @PermissaoId);
SET @permAppId = RANDOM_UUID();
INSERT INTO `permissoes_app` (`id`, `chave`, `nome`, `data_criacao`, `data_atualizacao`) VALUES (@permAppId, 'visualizarSubareas', '[Subárea] - Ver Detalhes de Subáreas', NOW(), NOW());
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@permAppId, @PermissaoId);

Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'DELETE /api/v1/subareas/$id<[^/]+>', '[Subárea] - Remover Subárea', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilBasicoCrud, @PermissaoId);
SET @permAppId = RANDOM_UUID();
INSERT INTO `permissoes_app` (`id`, `chave`, `nome`, `data_criacao`, `data_atualizacao`) VALUES (@permAppId, 'removerSubareas', '[Subárea] - Remover Subáreas', NOW(), NOW());
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@permAppId, @PermissaoId);



-- # CRUD Fabricantes

Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/fabricantes', '[Fabricante] - Listar Fabricantes', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilBasicoCrud, @PermissaoId);
SET @permAppId = RANDOM_UUID();
INSERT INTO `permissoes_app` (`id`, `chave`, `nome`, `data_criacao`, `data_atualizacao`) VALUES (@permAppId, 'listarFabricantes', '[Fabricante] - Listar Fabricantes', NOW(), NOW());
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@permAppId, @PermissaoId);


Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'POST /api/v1/fabricantes', '[Fabricante] - Cadastrar Novo Fabricante', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilBasicoCrud, @PermissaoId);
SET @permAppId = RANDOM_UUID();
INSERT INTO `permissoes_app` (`id`, `chave`, `nome`, `data_criacao`, `data_atualizacao`) VALUES (@permAppId, 'criarFabricantes', '[Fabricante] - Criar Fabricantes', NOW(), NOW());
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@permAppId, @PermissaoId);


Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'PUT /api/v1/fabricantes/$id<[^/]+>', '[Fabricante] - Editar Fabricante', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilBasicoCrud, @PermissaoId);
SET @permAppId = RANDOM_UUID();
INSERT INTO `permissoes_app` (`id`, `chave`, `nome`, `data_criacao`, `data_atualizacao`) VALUES (@permAppId, 'editarFabricantes', '[Fabricante] - Editar Fabricantes', NOW(), NOW());
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@permAppId, @PermissaoId);


Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/fabricantes/$id<[^/]+>', '[Fabricante] - Exibir Fabricante', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilBasicoCrud, @PermissaoId);
SET @permAppId = RANDOM_UUID();
INSERT INTO `permissoes_app` (`id`, `chave`, `nome`, `data_criacao`, `data_atualizacao`) VALUES (@permAppId, 'visualizarFabricantes', '[Fabricante] - Ver Detalhes de Fabricantes', NOW(), NOW());
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@permAppId, @PermissaoId);


Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'DELETE /api/v1/fabricantes/$id<[^/]+>', '[Fabricante] - Remover Fabricante', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilBasicoCrud, @PermissaoId);
SET @permAppId = RANDOM_UUID();
INSERT INTO `permissoes_app` (`id`, `chave`, `nome`, `data_criacao`, `data_atualizacao`) VALUES (@permAppId, 'removerFabricantes', '[Fabricante] - Remover Fabricantes', NOW(), NOW());
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@permAppId, @PermissaoId);

COMMIT;
