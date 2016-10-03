DELETE FROM `sessoes`;
DELETE FROM `versoes_controladores`;
DELETE FROM `permissoes_perfis`;
DELETE FROM `permissoes`;
DELETE FROM `perfis`;
DELETE FROM `usuarios`;


-- Perfis / Permissões
INSERT into `perfis` (`id`, `nome`, `data_criacao`, `data_atualizacao`) VALUES
                     ('720397e4-75d3-11e6-aa4a-fe9f24d4bd34', 'Administrador', CURRENT_TIME(), CURRENT_TIME());
INSERT into `perfis` (`id`, `nome`, `data_criacao`, `data_atualizacao`) VALUES
                     ('720df00e-75d3-11e6-aa4a-fe9f24d4bd34', 'Engenheiro', CURRENT_TIME(), CURRENT_TIME());
INSERT into `perfis` (`id`, `nome`, `data_criacao`, `data_atualizacao`) VALUES
                     ('721823e4-75d3-11e6-aa4a-fe9f24d4bd34', 'Operador', CURRENT_TIME(), CURRENT_TIME());
INSERT into `perfis` (`id`, `nome`, `data_criacao`, `data_atualizacao`) VALUES
                     ('72223442-75d3-11e6-aa4a-fe9f24d4bd34', 'Técnico', CURRENT_TIME(), CURRENT_TIME());


-- # Login
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) VALUES
                         ('722c689a-75d3-11e6-aa4a-fe9f24d4bd34', 'POST /api/v1/login', '[Login] - Entrar no Sistema', CURRENT_TIME(), CURRENT_TIME());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES
                                ('720397e4-75d3-11e6-aa4a-fe9f24d4bd34', '722c689a-75d3-11e6-aa4a-fe9f24d4bd34');
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES
                                ('720df00e-75d3-11e6-aa4a-fe9f24d4bd34', '722c689a-75d3-11e6-aa4a-fe9f24d4bd34');
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES
                                ('721823e4-75d3-11e6-aa4a-fe9f24d4bd34', '722c689a-75d3-11e6-aa4a-fe9f24d4bd34');
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES
                                ('72223442-75d3-11e6-aa4a-fe9f24d4bd34', '722c689a-75d3-11e6-aa4a-fe9f24d4bd34');


-- # CRUD Permissões PERFIL ADMINISTRADOR
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values
                         ('739cba7c-75d3-11e6-aa4a-fe9f24d4bd34', 'GET /api/v1/permissoes', '[Permissão] - Listar Permissão', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES
                                ('720397e4-75d3-11e6-aa4a-fe9f24d4bd34', '739cba7c-75d3-11e6-aa4a-fe9f24d4bd34');
-- INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);

INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values
                         ('73ab055a-75d3-11e6-aa4a-fe9f24d4bd34', 'POST /api/v1/permissoes', '[Permissão] - Criar Nova Permissão', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES
                                ('720397e4-75d3-11e6-aa4a-fe9f24d4bd34', '73ab055a-75d3-11e6-aa4a-fe9f24d4bd34');
-- INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);

INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values
                         ('73ba50aa-75d3-11e6-aa4a-fe9f24d4bd34', 'PUT /api/v1/permissoes/$id<[^/]+>', '[Permissão] - Editar Permissão', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES
                                ('720397e4-75d3-11e6-aa4a-fe9f24d4bd34', '73ba50aa-75d3-11e6-aa4a-fe9f24d4bd34');
-- INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);

INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values
                         ('73c9c3aa-75d3-11e6-aa4a-fe9f24d4bd34', 'GET /api/v1/permissoes/$id<[^/]+>', '[Permissão] - Exibir Permissão', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES
                                ('720397e4-75d3-11e6-aa4a-fe9f24d4bd34', '73c9c3aa-75d3-11e6-aa4a-fe9f24d4bd34');
-- INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);

INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values
                         ('73d9c43a-75d3-11e6-aa4a-fe9f24d4bd34', 'DELETE /api/v1/permissoes/$id<[^/]+>', '[Permissão] - Remover Permissão', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES
                                ('720397e4-75d3-11e6-aa4a-fe9f24d4bd34', '73d9c43a-75d3-11e6-aa4a-fe9f24d4bd34');
-- INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);

-- # CRUD Perfis ADMINISTRADOR
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values
                         ('73e90e36-75d3-11e6-aa4a-fe9f24d4bd34', 'GET /api/v1/perfis', '[Perfil] - Listar Perfis', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES
                                ('720397e4-75d3-11e6-aa4a-fe9f24d4bd34', '73e90e36-75d3-11e6-aa4a-fe9f24d4bd34');
-- INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);

INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values
                         ('73f7afae-75d3-11e6-aa4a-fe9f24d4bd34', 'POST /api/v1/perfis', '[Perfil] - Criar Novo Perfil', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES
                                ('720397e4-75d3-11e6-aa4a-fe9f24d4bd34', '73f7afae-75d3-11e6-aa4a-fe9f24d4bd34');
-- INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);

INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values
                         ('74070288-75d3-11e6-aa4a-fe9f24d4bd34', 'PUT /api/v1/perfis/$id<[^/]+>', '[Perfil] - Editar Perfil', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES
                                ('720397e4-75d3-11e6-aa4a-fe9f24d4bd34', '74070288-75d3-11e6-aa4a-fe9f24d4bd34');
-- INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);

INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values
                         ('7417190c-75d3-11e6-aa4a-fe9f24d4bd34', 'GET /api/v1/perfis/$id<[^/]+>', '[Perfil] - Exibir Perfil', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES
                                ('720397e4-75d3-11e6-aa4a-fe9f24d4bd34', '7417190c-75d3-11e6-aa4a-fe9f24d4bd34');
-- INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);

INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values
                         ('74258640-75d3-11e6-aa4a-fe9f24d4bd34', 'DELETE /api/v1/perfis/$id<[^/]+>', '[Perfil] - Remover Perfil', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES
                                ('720397e4-75d3-11e6-aa4a-fe9f24d4bd34', '74258640-75d3-11e6-aa4a-fe9f24d4bd34');
-- INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);


-- # CRUD Usuários -- ADMINISTRADOR
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values
                         ('7434e61c-75d3-11e6-aa4a-fe9f24d4bd34', 'GET /api/v1/usuarios', '[Usuário] - Listar Usuários', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES
                                ('720397e4-75d3-11e6-aa4a-fe9f24d4bd34' ,'7434e61c-75d3-11e6-aa4a-fe9f24d4bd34');
-- INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);

INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values
                         ('744432ca-75d3-11e6-aa4a-fe9f24d4bd34', 'POST /api/v1/usuarios', '[Usuário] - Criar um Novo Usuário', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES
                                ('720397e4-75d3-11e6-aa4a-fe9f24d4bd34', '744432ca-75d3-11e6-aa4a-fe9f24d4bd34');
-- INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);

INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values
                         ('74538054-75d3-11e6-aa4a-fe9f24d4bd34', 'PUT /api/v1/usuarios/$id<[^/]+>', '[Usuário] - Editar um Novo Usuário', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES
                                ('720397e4-75d3-11e6-aa4a-fe9f24d4bd34', '74538054-75d3-11e6-aa4a-fe9f24d4bd34');
-- INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);

INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values
                         ('7462cece-75d3-11e6-aa4a-fe9f24d4bd34', 'GET /api/v1/usuarios/$id<[^/]+>', '[Usuário] - Exibir um Usuário', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES
                                ('720397e4-75d3-11e6-aa4a-fe9f24d4bd34', '7462cece-75d3-11e6-aa4a-fe9f24d4bd34');
-- INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);

INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values
                         ('74721bf4-75d3-11e6-aa4a-fe9f24d4bd34', 'DELETE /api/v1/usuarios/$id<[^/]+>', '[Usuário] - Remover Usuários', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES
                                ('720397e4-75d3-11e6-aa4a-fe9f24d4bd34', '74721bf4-75d3-11e6-aa4a-fe9f24d4bd34');
-- INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);


-- # Menu Cadastros - Engenheiro

-- # CRUD Cidades
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) VALUES
                         ('724b0610-75d3-11e6-aa4a-fe9f24d4bd34', 'GET /api/v1/cidades', '[Cidade] - Listar Cidades', NOW(), NOW());
-- INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES
                                ('720df00e-75d3-11e6-aa4a-fe9f24d4bd34','724b0610-75d3-11e6-aa4a-fe9f24d4bd34');

INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values
                         ('725b1f96-75d3-11e6-aa4a-fe9f24d4bd34', 'POST /api/v1/cidades', '[Cidade] - Criar Nova Cidade', NOW(), NOW());

INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES
                                ('720df00e-75d3-11e6-aa4a-fe9f24d4bd34', '725b1f96-75d3-11e6-aa4a-fe9f24d4bd34');

INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) VALUES
                         ('72699f9e-75d3-11e6-aa4a-fe9f24d4bd34', 'PUT /api/v1/cidades/$id<[^/]+>', '[Cidade] - Editar Cidade', NOW(), NOW());
-- INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES
                                ('720df00e-75d3-11e6-aa4a-fe9f24d4bd34', '72699f9e-75d3-11e6-aa4a-fe9f24d4bd34');


INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values
                         ('7278eaee-75d3-11e6-aa4a-fe9f24d4bd34', 'GET /api/v1/cidades/$id<[^/]+>', '[Cidade] - Exibir uma Cidade', NOW(), NOW());
-- INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES
                                ('720df00e-75d3-11e6-aa4a-fe9f24d4bd34', '7278eaee-75d3-11e6-aa4a-fe9f24d4bd34');

INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values
                         ('72883706-75d3-11e6-aa4a-fe9f24d4bd34', 'DELETE /api/v1/cidades/$id<[^/]+>', '[Cidade] - Remover Cidade', NOW(), NOW());
-- INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES
                                ('720df00e-75d3-11e6-aa4a-fe9f24d4bd34', '72883706-75d3-11e6-aa4a-fe9f24d4bd34');


-- # CRUD Areas - Engenheiro
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values
                         ('72987440-75d3-11e6-aa4a-fe9f24d4bd34', 'GET /api/v1/areas', '[Área] - Listar Áreas', NOW(), NOW());
-- INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES
                                ('720df00e-75d3-11e6-aa4a-fe9f24d4bd34', '72987440-75d3-11e6-aa4a-fe9f24d4bd34');

INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values
                         ('72a7a79e-75d3-11e6-aa4a-fe9f24d4bd34', 'POST /api/v1/areas', '[Área] - Criar Nova Área', NOW(), NOW());
-- INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES
                                ('720df00e-75d3-11e6-aa4a-fe9f24d4bd34', '72a7a79e-75d3-11e6-aa4a-fe9f24d4bd34');

INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values
                         ('72b6f0c8-75d3-11e6-aa4a-fe9f24d4bd34', 'PUT /api/v1/areas/$id<[^/]+>', '[Área] - Editar Área', NOW(), NOW());
-- INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES
                                ('720df00e-75d3-11e6-aa4a-fe9f24d4bd34', '72b6f0c8-75d3-11e6-aa4a-fe9f24d4bd34');

INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values
                         ('72c65540-75d3-11e6-aa4a-fe9f24d4bd34', 'GET /api/v1/areas/$id<[^/]+>', '[Área] - Exibir uma Área', NOW(), NOW());
-- INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES
                                ('720df00e-75d3-11e6-aa4a-fe9f24d4bd34', '72c65540-75d3-11e6-aa4a-fe9f24d4bd34');

INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values
                         ('72d584fc-75d3-11e6-aa4a-fe9f24d4bd34', 'DELETE /api/v1/areas/$id<[^/]+>', '[Área] - Remover Área', NOW(), NOW());
-- INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES
                                ('720df00e-75d3-11e6-aa4a-fe9f24d4bd34', '72d584fc-75d3-11e6-aa4a-fe9f24d4bd34');


-- # CRUD Subareas - Engenheiro
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values
                         ('72e4da06-75d3-11e6-aa4a-fe9f24d4bd34', 'GET /api/v1/subareas', '[Subárea] - Listar Subárea', NOW(), NOW());
-- INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES
                                ('720df00e-75d3-11e6-aa4a-fe9f24d4bd34', '72e4da06-75d3-11e6-aa4a-fe9f24d4bd34');

INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values
                         ('72f42d9e-75d3-11e6-aa4a-fe9f24d4bd34', 'POST /api/v1/subareas', '[Subárea] - Criar Nova Subárea', NOW(), NOW());
-- INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES
                                ('720df00e-75d3-11e6-aa4a-fe9f24d4bd34', '72f42d9e-75d3-11e6-aa4a-fe9f24d4bd34');

INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values
                         ('73036818-75d3-11e6-aa4a-fe9f24d4bd34', 'PUT /api/v1/subareas/$id<[^/]+>', '[Subárea] - Editar Subárea', NOW(), NOW());
-- INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES
                                ('720df00e-75d3-11e6-aa4a-fe9f24d4bd34', '73036818-75d3-11e6-aa4a-fe9f24d4bd34');

INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values
                         ('7312c402-75d3-11e6-aa4a-fe9f24d4bd34', 'GET /api/v1/subareas/$id<[^/]+>', '[Subárea] - Exibir uma Subárea', NOW(), NOW());
-- INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES
                                ('720df00e-75d3-11e6-aa4a-fe9f24d4bd34', '7312c402-75d3-11e6-aa4a-fe9f24d4bd34');

INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values
                         ('73221146-75d3-11e6-aa4a-fe9f24d4bd34', 'DELETE /api/v1/subareas/$id<[^/]+>', '[Subárea] - Remover Subárea', NOW(), NOW());
-- INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES
                                ('720df00e-75d3-11e6-aa4a-fe9f24d4bd34', '73221146-75d3-11e6-aa4a-fe9f24d4bd34');

-- # CRUD Agrupamentos - Engenheiro
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values
                         ('733164ac-75d3-11e6-aa4a-fe9f24d4bd34', 'GET /api/v1/agrupamentos', '[Agrupamento] - Listar Agrupamentos', NOW(), NOW());
-- INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES
                                ('720df00e-75d3-11e6-aa4a-fe9f24d4bd34', '733164ac-75d3-11e6-aa4a-fe9f24d4bd34');

INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values
                         ('7340af70-75d3-11e6-aa4a-fe9f24d4bd34', 'POST /api/v1/agrupamentos', '[Agrupamento] - Criar Novo Agrupamento', NOW(), NOW());
-- INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES
                                ('720df00e-75d3-11e6-aa4a-fe9f24d4bd34', '7340af70-75d3-11e6-aa4a-fe9f24d4bd34');

INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values
                         ('734ffe80-75d3-11e6-aa4a-fe9f24d4bd34', 'PUT /api/v1/agrupamentos/$id<[^/]+>', '[Agrupamento] - Editar Agrupamento', NOW(), NOW());
-- INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES
                                ('720df00e-75d3-11e6-aa4a-fe9f24d4bd34', '734ffe80-75d3-11e6-aa4a-fe9f24d4bd34');

INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values
                         ('735f6f1e-75d3-11e6-aa4a-fe9f24d4bd34', 'GET /api/v1/agrupamentos/$id<[^/]+>', '[Agrupamento] - Exibir um Agrupamento', NOW(), NOW());
-- INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES
                                ('720df00e-75d3-11e6-aa4a-fe9f24d4bd34', '735f6f1e-75d3-11e6-aa4a-fe9f24d4bd34');

INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values
                         ('736dd5ea-75d3-11e6-aa4a-fe9f24d4bd34', 'DELETE /api/v1/agrupamentos/$id<[^/]+>', '[Agrupamento] - Remover Agrupamento', NOW(), NOW());
-- INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES
                                ('720df00e-75d3-11e6-aa4a-fe9f24d4bd34', '736dd5ea-75d3-11e6-aa4a-fe9f24d4bd34');


-- # CRUD Imagens - Engenheiro
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values
                         ('737e0c26-75d3-11e6-aa4a-fe9f24d4bd34', 'POST /api/v1/imagens', '[Imagem] - Cadastrar Imagem', NOW(), NOW());
-- INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES
                                ('720df00e-75d3-11e6-aa4a-fe9f24d4bd34','737e0c26-75d3-11e6-aa4a-fe9f24d4bd34');

INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values
                         ('738d61f8-75d3-11e6-aa4a-fe9f24d4bd34', 'GET /api/v1/imagens/$id<[^/]+>', '[Imagem] - Exibir Imagem', NOW(), NOW());
-- INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES
                                ('720df00e-75d3-11e6-aa4a-fe9f24d4bd34', '738d61f8-75d3-11e6-aa4a-fe9f24d4bd34');



-- # RECUPERAR SENHA - ENGENHEIRO
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values
                         ('74721bf4-75d3-11e6-aa4b-fe9f24d4bd34', 'POST /api/v1/recuperar_senha', '[Usuário] - Recuperar Senha', NOW(), NOW());
-- INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES
                                ('720df00e-75d3-11e6-aa4a-fe9f24d4bd34', '74721bf4-75d3-11e6-aa4b-fe9f24d4bd34');

INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values
                         ('75720bf4-75d3-11e6-aa4b-fe9f24d4bd34', 'POST /api/v1/redefinir_senha', '[Usuário] - Redefinir Senha', NOW(), NOW());
-- INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES
                                ('720df00e-75d3-11e6-aa4a-fe9f24d4bd34', '75720bf4-75d3-11e6-aa4b-fe9f24d4bd34');



-- # Menu Configurações

-- # CRUD Fabricantes - ENGENHEIRO
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values
                         ('74814304-75d3-11e6-aa4a-fe9f24d4bd34', 'GET /api/v1/fabricantes', '[Fabricante] - Listar Fabricantes', NOW(), NOW());
-- INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES
                                ('720df00e-75d3-11e6-aa4a-fe9f24d4bd34', '74814304-75d3-11e6-aa4a-fe9f24d4bd34');

INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values
                         ('74909a7a-75d3-11e6-aa4a-fe9f24d4bd34', 'POST /api/v1/fabricantes', '[Fabricante] - Cadastrar Novo Fabricante', NOW(), NOW());
-- INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES
                                ('720df00e-75d3-11e6-aa4a-fe9f24d4bd34', '74909a7a-75d3-11e6-aa4a-fe9f24d4bd34');

INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values
                         ('749fe480-75d3-11e6-aa4a-fe9f24d4bd34', 'PUT /api/v1/fabricantes/$id<[^/]+>', '[Fabricante] - Editar Fabricante', NOW(), NOW());
-- INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES
                                ('720df00e-75d3-11e6-aa4a-fe9f24d4bd34', '749fe480-75d3-11e6-aa4a-fe9f24d4bd34');

INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values
                         ('74af2cba-75d3-11e6-aa4a-fe9f24d4bd34', 'GET /api/v1/fabricantes/$id<[^/]+>', '[Fabricante] - Exibir Fabricante', NOW(), NOW());
-- INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES
                                ('720df00e-75d3-11e6-aa4a-fe9f24d4bd34', '74af2cba-75d3-11e6-aa4a-fe9f24d4bd34');

INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values
                         ('74bea622-75d3-11e6-aa4a-fe9f24d4bd34', 'DELETE /api/v1/fabricantes/$id<[^/]+>', '[Fabricante] - Remover Fabricante', NOW(), NOW());
-- INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES
                                ('720df00e-75d3-11e6-aa4a-fe9f24d4bd34', '74bea622-75d3-11e6-aa4a-fe9f24d4bd34');



-- # Menu Programação

-- # CRUD controladores - ENGENHEIRO
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values
                         ('74cdd0f2-75d3-11e6-aa4a-fe9f24d4bd34', 'GET /api/v1/controladores', '[Controladores] - Listar Controladores', NOW(), NOW());
-- INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES
                                ('720df00e-75d3-11e6-aa4a-fe9f24d4bd34', '74cdd0f2-75d3-11e6-aa4a-fe9f24d4bd34');

INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values
                         ('74dd1c1a-75d3-11e6-aa4a-fe9f24d4bd34', 'GET /api/v1/controladores/$id<[^/]+>', '[Controladores] - Exibir Controlador', NOW(), NOW());
-- INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES
                                ('720df00e-75d3-11e6-aa4a-fe9f24d4bd34', '74dd1c1a-75d3-11e6-aa4a-fe9f24d4bd34');

INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values
                         ('74ec7264-75d3-11e6-aa4a-fe9f24d4bd34', 'DELETE /api/v1/controladores/$id<[^/]+>', '[Controladores] - Remover Controlador', NOW(), NOW());
-- INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES
                                ('720df00e-75d3-11e6-aa4a-fe9f24d4bd34', '74ec7264-75d3-11e6-aa4a-fe9f24d4bd34');

INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values
                         ('74fbd6f0-75d3-11e6-aa4a-fe9f24d4bd34', 'PUT /api/v1/controladores/$id<[^/]+>/ativar', '[Controladores] - Ativar Controlador', NOW(), NOW());
-- INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES
                                ('720df00e-75d3-11e6-aa4a-fe9f24d4bd34', '74fbd6f0-75d3-11e6-aa4a-fe9f24d4bd34');

INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values
                         ('750b206a-75d3-11e6-aa4a-fe9f24d4bd34', 'DELETE /api/v1/controladores/$id<[^/]+>/cancelar_edicao', '[Controladores] - Cancelar edição', NOW(), NOW());
-- INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES
                                ('720df00e-75d3-11e6-aa4a-fe9f24d4bd34', '750b206a-75d3-11e6-aa4a-fe9f24d4bd34');

INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values
                         ('751a73d0-75d3-11e6-aa4a-fe9f24d4bd34', 'GET /api/v1/controladores/$id<[^/]+>/editar_planos', '[Controladores] - Editar Planos', NOW(), NOW());
-- INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES
                                ('720df00e-75d3-11e6-aa4a-fe9f24d4bd34','751a73d0-75d3-11e6-aa4a-fe9f24d4bd34');

INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values
                         ('7529c600-75d3-11e6-aa4a-fe9f24d4bd34', 'GET /api/v1/controladores/$id<[^/]+>/editar_tabela_horaria', '[Controladores] - Editar Tabela Horária', NOW(), NOW());
-- INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES
                                ('720df00e-75d3-11e6-aa4a-fe9f24d4bd34', '7529c600-75d3-11e6-aa4a-fe9f24d4bd34');

INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values
                         ('7538e928-75d3-11e6-aa4a-fe9f24d4bd34', 'GET /api/v1/controladores/$id<[^/]+>/pode_editar', '[Controladores] - Editar Controlador', NOW(), NOW());
-- INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES
                                ('720df00e-75d3-11e6-aa4a-fe9f24d4bd34', '7538e928-75d3-11e6-aa4a-fe9f24d4bd34');

INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values
                         ('75485ba6-75d3-11e6-aa4a-fe9f24d4bd34', 'GET /api/v1/controladores/$id<[^/]+>/timeline', '[Controladores] - Histórico Controlador', NOW(), NOW());
-- INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES
                                ('720df00e-75d3-11e6-aa4a-fe9f24d4bd34', '75485ba6-75d3-11e6-aa4a-fe9f24d4bd34');




-- # Wizard Controladores - ENGENHEIRO
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values
                         ('7557ac6e-75d3-11e6-aa4a-fe9f24d4bd34', 'POST /api/v1/controladores/dados_basicos', '[Controladores] - Cadastrar Dados Básicos', NOW(), NOW());
-- INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES
                                ('720df00e-75d3-11e6-aa4a-fe9f24d4bd34', '7557ac6e-75d3-11e6-aa4a-fe9f24d4bd34');

INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values
                         ('7566dbbc-75d3-11e6-aa4a-fe9f24d4bd34', 'POST /api/v1/controladores/aneis', '[Controladores] - Cadastrar Anéis', NOW(), NOW());
-- INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES
                                ('720df00e-75d3-11e6-aa4a-fe9f24d4bd34', '7566dbbc-75d3-11e6-aa4a-fe9f24d4bd34');

INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values
                         ('75764e08-75d3-11e6-aa4a-fe9f24d4bd34', 'POST /api/v1/controladores/grupos_semaforicos', '[Controladores] - Cadastrar Grupos Semafóricos', NOW(), NOW());
-- INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES
                                ('720df00e-75d3-11e6-aa4a-fe9f24d4bd34', '75764e08-75d3-11e6-aa4a-fe9f24d4bd34');

INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values
                         ('75858ff8-75d3-11e6-aa4a-fe9f24d4bd34', 'POST /api/v1/controladores/associacao_grupos_semaforicos', '[Controladores] - Associar Grupos Semafóricos', NOW(), NOW());
-- INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES
                                ('720df00e-75d3-11e6-aa4a-fe9f24d4bd34', '75858ff8-75d3-11e6-aa4a-fe9f24d4bd34');

INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values
                         ('7594bfdc-75d3-11e6-aa4a-fe9f24d4bd34', 'POST /api/v1/controladores/verdes_conflitantes', '[Controladores] - Cadastrar Verdes Conflitantes', NOW(), NOW());
-- INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES
                                ('720df00e-75d3-11e6-aa4a-fe9f24d4bd34', '7594bfdc-75d3-11e6-aa4a-fe9f24d4bd34');

INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values
                         ('75a4288c-75d3-11e6-aa4a-fe9f24d4bd34', 'POST /api/v1/controladores/transicoes_proibidas', '[Controladores] - Cadastrar Transições Proibidas', NOW(), NOW());
-- INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES
                                ('720df00e-75d3-11e6-aa4a-fe9f24d4bd34', '75a4288c-75d3-11e6-aa4a-fe9f24d4bd34');

INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values
                         ('75b37ba2-75d3-11e6-aa4a-fe9f24d4bd34', 'POST /api/v1/controladores/entre_verdes', '[Controladores] - Cadastrar Tabelas Entre-Verdes', NOW(), NOW());
-- INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES
                                ('720df00e-75d3-11e6-aa4a-fe9f24d4bd34', '75b37ba2-75d3-11e6-aa4a-fe9f24d4bd34');

INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values
                         ('75c2e8b2-75d3-11e6-aa4a-fe9f24d4bd34', 'POST /api/v1/controladores/associacao_detectores', '[Controladores] - Associar Detectores', NOW(), NOW());
-- INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES
                                ('720df00e-75d3-11e6-aa4a-fe9f24d4bd34', '75c2e8b2-75d3-11e6-aa4a-fe9f24d4bd34');

INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values
                         ('75d21774-75d3-11e6-aa4a-fe9f24d4bd34', 'POST /api/v1/controladores/atraso_de_grupo', '[Controladores] - Cadastrar atraso de grupo', NOW(), NOW());
-- INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES
                                ('720df00e-75d3-11e6-aa4a-fe9f24d4bd34', '75d21774-75d3-11e6-aa4a-fe9f24d4bd34');

INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values
                         ('75e143d4-75d3-11e6-aa4a-fe9f24d4bd34', 'PUT /api/v1/controladores/$id<[^/]+>/atualizar_descricao', '[Controladores] - Finalizar Controlador', NOW(), NOW());
-- INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES
                                ('720df00e-75d3-11e6-aa4a-fe9f24d4bd34', '75e143d4-75d3-11e6-aa4a-fe9f24d4bd34');



-- # Planos - ENGENHEIRO
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values
                         ('75f0b5b2-75d3-11e6-aa4a-fe9f24d4bd34', 'POST /api/v1/planos', '[Controladores] - Visualizar Planos', NOW(), NOW());
-- INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES
                                ('720df00e-75d3-11e6-aa4a-fe9f24d4bd34', '75f0b5b2-75d3-11e6-aa4a-fe9f24d4bd34');

INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values
                         ('75fffe1e-75d3-11e6-aa4a-fe9f24d4bd34', 'GET /api/v1/planos/$id<[^/]+>/timeline', '[Planos] - Histórico de Planos', NOW(), NOW());
-- INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES
                                ('720df00e-75d3-11e6-aa4a-fe9f24d4bd34', '75fffe1e-75d3-11e6-aa4a-fe9f24d4bd34');

INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values
                         ('760f2f88-75d3-11e6-aa4a-fe9f24d4bd34', 'DELETE /api/v1/planos/$id<[^/]+>/edit/cancel', '[Planos] - Cancelar edição', NOW(), NOW());
-- INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES
                                ('720df00e-75d3-11e6-aa4a-fe9f24d4bd34', '760f2f88-75d3-11e6-aa4a-fe9f24d4bd34');



-- #Tabela Horaria - ENGENHEIRO
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values
                         ('761e7984-75d3-11e6-aa4a-fe9f24d4bd34', 'POST /api/v1/tabela_horarios', '[Controladores] - Visualizar Tabela Horária', NOW(), NOW());
-- INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES
                                ('720df00e-75d3-11e6-aa4a-fe9f24d4bd34', '761e7984-75d3-11e6-aa4a-fe9f24d4bd34');

INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values
                         ('762de6da-75d3-11e6-aa4a-fe9f24d4bd34', 'GET /api/v1/tabela_horarios/$id<[^/]+>/timeline', '[Tabela Horária] - Histórico de Tabela Horária', NOW(), NOW());
-- INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES
                                ('720df00e-75d3-11e6-aa4a-fe9f24d4bd34', '762de6da-75d3-11e6-aa4a-fe9f24d4bd34');

INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values
                         ('763d3aae-75d3-11e6-aa4a-fe9f24d4bd34', 'DELETE /api/v1/tabela_horarios/$id<[^/]+>/cancelar_edicao', '[Tabela Horária] - Cancelar edição', NOW(), NOW());
-- INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES
                                ('720df00e-75d3-11e6-aa4a-fe9f24d4bd34', '763d3aae-75d3-11e6-aa4a-fe9f24d4bd34');



-- #Exclusão - ENGENHEIRO
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values
                         ('764c647a-75d3-11e6-aa4a-fe9f24d4bd34', 'DELETE /api/v1/planos/$id<[^/]+>', '[Planos] - Excluir', NOW(), NOW());
-- INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES
                                ('720df00e-75d3-11e6-aa4a-fe9f24d4bd34', '764c647a-75d3-11e6-aa4a-fe9f24d4bd34');

INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values
                         ('765bd7fc-75d3-11e6-aa4a-fe9f24d4bd34', 'DELETE /api/v1/estagios/$id<[^/]+>', '[Estágios] - Excluir', NOW(), NOW());
-- INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES
                                ('720df00e-75d3-11e6-aa4a-fe9f24d4bd34', '765bd7fc-75d3-11e6-aa4a-fe9f24d4bd34');

INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values
                         ('766b164a-75d3-11e6-aa4a-fe9f24d4bd34', 'DELETE /api/v1/detectores/$id<[^/]+>', '[Detectores] - Excluir', NOW(), NOW());
-- INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES
                                ('720df00e-75d3-11e6-aa4a-fe9f24d4bd34', '766b164a-75d3-11e6-aa4a-fe9f24d4bd34');

INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values
                         ('767a5588-75d3-11e6-aa4a-fe9f24d4bd34', 'DELETE /api/v1/eventos/$id<[^/]+>', '[Eventos] - Excluir', NOW(), NOW());
-- INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES
                                ('720df00e-75d3-11e6-aa4a-fe9f24d4bd34', '767a5588-75d3-11e6-aa4a-fe9f24d4bd34');

INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values
                         ('768a7d14-75d3-11e6-aa4a-fe9f24d4bd34', 'DELETE /api/v1/imagens/$id<[^/]+>', '[Imagens] - Excluir', NOW(), NOW());
-- INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES
                                ('720df00e-75d3-11e6-aa4a-fe9f24d4bd34', '768a7d14-75d3-11e6-aa4a-fe9f24d4bd34');



-- # Helpers - ENGENHEIRO
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values
                         ('7698ed36-75d3-11e6-aa4a-fe9f24d4bd34', 'GET /api/v1/helpers/controlador', '[Controlador] - Exibir tela de Cadastro de Controladores', NOW(), NOW());
-- INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES
                                ('720df00e-75d3-11e6-aa4a-fe9f24d4bd34', '7698ed36-75d3-11e6-aa4a-fe9f24d4bd34');

-- # Auditoriaas - ENGENHEIRO
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values
                         ('76a85fc8-75d3-11e6-aa4a-fe9f24d4bd34', 'GET /api/v1/auditorias/', '[Auditoria] - Listar auditorias', NOW(), NOW());
-- INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES
                                ('720df00e-75d3-11e6-aa4a-fe9f24d4bd34', '76a85fc8-75d3-11e6-aa4a-fe9f24d4bd34');

INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values
                         ('76b7911e-75d3-11e6-aa4a-fe9f24d4bd34', 'GET /api/v1/auditorias/$id<[^/]+>', '[Auditoria] - Histórico da Auditoria', NOW(), NOW());
-- INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES
                                ('720df00e-75d3-11e6-aa4a-fe9f24d4bd34','76b7911e-75d3-11e6-aa4a-fe9f24d4bd34');


--INSERT USUÁRIOS

INSERT INTO `usuarios` (`id`, `login`, `senha`, `email`, `nome`, `root`, `perfil_id`, `data_criacao`, `data_atualizacao`) VALUES
                       ('2f0e0547-3135-428b-8f6d-0a1098eca0a5', 'root', '$2a$10$EzudGIqkxquJjLGawuMrOu9K6S28yc/R/YSAVxsvb5bSryOYWd5eq', 'root@influunt.com.br', 'Administrador Geral', 'true', '720397e4-75d3-11e6-aa4a-fe9f24d4bd34', CURRENT_TIME(), CURRENT_TIME());

INSERT INTO `usuarios` (`id`, `login`, `senha`, `email`, `nome`, `root`, `perfil_id`, `data_criacao`, `data_atualizacao`) VALUES
                       ('2f0e0547-3234-439b-8f6d-0a1098eca0a6', 'engenheiro', '$2a$10$EzudGIqkxquJjLGawuMrOu9K6S28yc/R/YSAVxsvb5bSryOYWd5eq', 'engenheiro@influunt.com.br', 'Engenheiro', 'false', '720df00e-75d3-11e6-aa4a-fe9f24d4bd34', CURRENT_TIME(), CURRENT_TIME());





