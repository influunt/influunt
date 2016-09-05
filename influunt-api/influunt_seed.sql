BEGIN;

SET @CidadeId = UUID();
INSERT INTO `cidades` (`id`, `id_json`, `nome`, `data_criacao`, `data_atualizacao`) VALUES (@CidadeId, UUID(), 'São Paulo', NOW(), NOW());
INSERT INTO `areas` (`id`, `id_json`, `descricao`, `cidade_id`, `data_criacao`, `data_atualizacao`) VALUES (UUID(), UUID(), 1, @CidadeId, NOW(), NOW());

SET @FabricanteId = UUID();
INSERT INTO `fabricantes` (`id`, `id_json`, `nome`, `data_criacao`, `data_atualizacao`) VALUES (@FabricanteId, UUID(), 'Raro Labs', NOW(), NOW());
INSERT INTO `modelo_controladores` (`id`, `id_json`, `descricao`, `fabricante_id`, `limite_estagio`, `limite_grupo_semaforico`, `limite_anel`, `limite_detector_pedestre`, `limite_detector_veicular`, `limite_tabelas_entre_verdes`, `limite_planos`, `data_criacao`, `data_atualizacao`) VALUES (UUID(), UUID(), 'Modelo Básico', @FabricanteId, 16, 16, 4, 4, 8, 2, 16, NOW(), NOW());
INSERT INTO `usuarios` (`id`, `login`, `senha`, `email`, `nome`, `root`, `data_criacao`, `data_atualizacao`) VALUES (UUID(), 'root', '$2a$10$EzudGIqkxquJjLGawuMrOu9K6S28yc/R/YSAVxsvb5bSryOYWd5eq', 'root@influunt.com.br', 'Administrador Geral', true, NOW(), NOW());



-- Perfis / Permissões
Set @PerfilAdministradorId = UUID();
Set @PerfilEngenheiroId = UUID();
Set @PerfilOperadorId = UUID();
Set @PerfilTecnicoId = UUID();
INSERT into `perfis` (`id`, `nome`, `data_criacao`, `data_atualizacao`) VALUE (@PerfilAdministradorId, 'Administrador', NOW(), NOW());
INSERT into `perfis` (`id`, `nome`, `data_criacao`, `data_atualizacao`) VALUE (@PerfilEngenheiroId, 'Engenheiro', NOW(), NOW());
INSERT into `perfis` (`id`, `nome`, `data_criacao`, `data_atualizacao`) VALUE (@PerfilOperadorId, 'Operador', NOW(), NOW());
INSERT into `perfis` (`id`, `nome`, `data_criacao`, `data_atualizacao`) VALUE (@PerfilTecnicoId, 'Técnico', NOW(), NOW());


-- # Login
Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'POST /api/v1/login', '[Login] - Entrar no Sistema', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilOperadorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilTecnicoId, @PermissaoId);


-- # Menu Cadastros

-- # CRUD Cidades
Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/cidades', '[Cidade] - Listar Cidades', NOW(), NOW());
-- INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'POST /api/v1/cidades', '[Cidade] - Criar Nova Cidade', NOW(), NOW());
-- INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'PUT /api/v1/cidades/$id<[^/]+>', '[Cidade] - Editar Cidade', NOW(), NOW());
-- INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/cidades/$id<[^/]+>', '[Cidade] - Exibir uma Cidade', NOW(), NOW());
-- INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'DELETE /api/v1/cidades/$id<[^/]+>', '[Cidade] - Remover Cidade', NOW(), NOW());
-- INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);


-- # CRUD Areas
Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/areas', '[Área] - Listar Áreas', NOW(), NOW());
-- INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'POST /api/v1/areas', '[Área] - Criar Nova Área', NOW(), NOW());
-- INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'PUT /api/v1/areas/$id<[^/]+>', '[Área] - Editar Área', NOW(), NOW());
-- INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/areas/$id<[^/]+>', '[Área] - Exibir uma Área', NOW(), NOW());
-- INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'DELETE /api/v1/areas/$id<[^/]+>', '[Área] - Remover Área', NOW(), NOW());
-- INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);


-- # CRUD Agrupamentos
Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/agrupamentos', '[Agrupamento] - Listar Agrupamentos', NOW(), NOW());
-- INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'POST /api/v1/agrupamentos', '[Agrupamento] - Criar Novo Agrupamento', NOW(), NOW());
-- INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'PUT /api/v1/agrupamentos/$id<[^/]+>', '[Agrupamento] - Editar Agrupamento', NOW(), NOW());
-- INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/agrupamentos/$id<[^/]+>', '[Agrupamento] - Exibir um Agrupamento', NOW(), NOW());
-- INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'DELETE /api/v1/agrupamentos/$id<[^/]+>', '[Agrupamento] - Remover Agrupamento', NOW(), NOW());
-- INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);


-- # CRUD Imagens
Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'POST /api/v1/imagens', '[Imagem] - Cadastrar Imagem', NOW(), NOW());
-- INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/imagens/$id<[^/]+>', '[Imagem] - Exibir Imagem', NOW(), NOW());
-- INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);


-- # CRUD Permissões
Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/permissoes', '[Permissão] - Listar Permissão', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
-- INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'POST /api/v1/permissoes', '[Permissão] - Criar Nova Permissão', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
-- INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'PUT /api/v1/permissoes/$id<[^/]+>', '[Permissão] - Editar Permissão', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
-- INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/permissoes/$id<[^/]+>', '[Permissão] - Exibir Permissão', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
-- INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'DELETE /api/v1/permissoes/$id<[^/]+>', '[Permissão] - Remover Permissão', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
-- INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);


-- # CRUD Perfis
Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/perfis', '[Perfil] - Listar Perfis', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
-- INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'POST /api/v1/perfis', '[Perfil] - Criar Novo Perfil', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
-- INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'PUT /api/v1/perfis/$id<[^/]+>', '[Perfil] - Editar Perfil', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
-- INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/perfis/$id<[^/]+>', '[Perfil] - Exibir Perfil', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
-- INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'DELETE /api/v1/perfis/$id<[^/]+>', '[Perfil] - Remover Perfil', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
-- INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);


-- # CRUD Usuários
Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/usuarios', '[Usuário] - Listar Usuários', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
-- INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'POST /api/v1/usuarios', '[Usuário] - Criar um Novo Usuário', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
-- INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'PUT /api/v1/usuarios/$id<[^/]+>', '[Usuário] - Editar um Novo Usuário', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
-- INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/usuarios/$id<[^/]+>', '[Usuário] - Exibir um Usuário', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
-- INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'DELETE /api/v1/usuarios/$id<[^/]+>', '[Usuário] - Remover Usuários', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
-- INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);




-- # Menu Configurações

-- # CRUD Fabricantes
Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/fabricantes', '[Fabricante] - Listar Fabricantes', NOW(), NOW());
-- INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'POST /api/v1/fabricantes', '[Fabricante] - Cadastrar Novo Fabricante', NOW(), NOW());
-- INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'PUT /api/v1/fabricantes/$id<[^/]+>', '[Fabricante] - Editar Fabricante', NOW(), NOW());
-- INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/fabricantes/$id<[^/]+>', '[Fabricante] - Exibir Fabricante', NOW(), NOW());
-- INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'DELETE /api/v1/fabricantes/$id<[^/]+>', '[Fabricante] - Remover Fabricante', NOW(), NOW());
-- INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);




-- # Menu Programação

-- # CRUD controladores
Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/controladores', '[Controladores] - Listar Controladores', NOW(), NOW());
-- INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/controladores/$id<[^/]+>', '[Controladores] - Exibir Controlador', NOW(), NOW());
-- INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'DELETE /api/v1/controladores/$id<[^/]+>', '[Controladores] - Remover Controlador', NOW(), NOW());
-- INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);


-- # Wizard Controladores
Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'POST /api/v1/controladores/dados_basicos', '[Controladores] - Cadastrar Dados Básicos', NOW(), NOW());
-- INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'POST /api/v1/controladores/aneis', '[Controladores] - Cadastrar Anéis', NOW(), NOW());
-- INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'POST /api/v1/controladores/grupos_semaforicos', '[Controladores] - Cadastrar Grupos Semafóricos', NOW(), NOW());
-- INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'POST /api/v1/controladores/associacao_grupos_semaforicos', '[Controladores] - Associar Grupos Semafóricos', NOW(), NOW());
-- INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'POST /api/v1/controladores/verdes_conflitantes', '[Controladores] - Cadastrar Verdes Conflitantes', NOW(), NOW());
-- INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'POST /api/v1/controladores/transicoes_proibidas', '[Controladores] - Cadastrar Transições Proibidas', NOW(), NOW());
-- INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'POST /api/v1/controladores/entre_verdes', '[Controladores] - Cadastrar Tabelas Entre-Verdes', NOW(), NOW());
-- INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'POST /api/v1/controladores/associacao_detectores', '[Controladores] - Associar Detectores', NOW(), NOW());
-- INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'POST /api/v1/controladores/atraso_de_grupo', '[Controladores] - Cadastrar atraso de grupo', NOW(), NOW());
-- INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);



-- # Planos
Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'POST /api/v1/planos', '[Controladores] - Visualizar Planos', NOW(), NOW());
-- INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);


-- # Helpers
Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/helpers/controlador', '[Controlador] - Exibir tela de Cadastro de Controladores', NOW(), NOW());
-- INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);

COMMIT;
