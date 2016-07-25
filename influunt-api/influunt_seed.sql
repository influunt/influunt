BEGIN;
SET @CidadeId = UUID();
INSERT INTO `cidades` (`id`, `nome`, `data_criacao`, `data_atualizacao`) VALUES (@CidadeId, 'São Paulo', NOW(), NOW());
INSERT INTO `areas` (`id`, `descricao`, `cidade_id`, `data_criacao`, `data_atualizacao`) VALUES (UUID(), 51, @CidadeId, NOW(), NOW());
SET @FabricanteId = UUID();
INSERT INTO `fabricantes` (`id`, `nome`, `data_criacao`, `data_atualizacao`) VALUES (@FabricanteId, 'Raro Labs', NOW(), NOW());
SET @ConfiguracaoId = UUID();
INSERT INTO `configuracao_controladores` (`id`, `descricao`, `limite_estagio`, `limite_grupo_semaforico`, `limite_anel`, `limite_detector_pedestre`, `limite_detector_veicular`, `limite_tabelas_entre_verdes`, `data_criacao`, `data_atualizacao`) VALUES (@ConfiguracaoId, 'DESC', '16', '16', '16', '16', '16', '2', NOW(), NOW());
INSERT INTO `modelo_controladores` (`id`, `descricao`, `fabricante_id`, `configuracao_id`, `data_criacao`, `data_atualizacao`) VALUES (UUID(), 'Desc modelo', @FabricanteId, @ConfiguracaoId, NOW(), NOW());
INSERT INTO `usuarios` (`login`,`senha`, `email`, `nome`, `root`, `data_criacao`, `data_atualizacao`) VALUES ('root', '$2a$10$EzudGIqkxquJjLGawuMrOu9K6S28yc/R/YSAVxsvb5bSryOYWd5eq', 'root@influunt.com.br','Administrador Geral', true, NOW(), NOW());

Set @PerfilId = UUID();
INSERT into `perfis` (`id`,`nome`,`data_criacao`, `data_atualizacao`) VALUE (@PerfilId,'Administrador',NOW(), NOW());

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`,`chave`,`descricao`,`data_criacao`, `data_atualizacao`) values (@PermissaoId,'GET /api/v1/cidades','[Cidade] - Listar Cidades',NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`,`permissao_id`) VALUES (@PerfilId,@PermissaoId);

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`,`chave`,`descricao`,`data_criacao`, `data_atualizacao`) values (@PermissaoId,'POST /api/v1/cidades','[Cidade] - Criar Nova Cidade',NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`,`permissao_id`) VALUES (@PerfilId,@PermissaoId);

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`,`chave`,`descricao`,`data_criacao`, `data_atualizacao`) values (@PermissaoId,'PUT /api/v1/cidades/$id<[^/]+>','[Cidade] - Editar Cidade',NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`,`permissao_id`) VALUES (@PerfilId,@PermissaoId);

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`,`chave`,`descricao`,`data_criacao`, `data_atualizacao`) values (@PermissaoId,'GET /api/v1/cidades/$id<[^/]+>','[Cidade] - Exibir uma Cidade',NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`,`permissao_id`) VALUES (@PerfilId,@PermissaoId);

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`,`chave`,`descricao`,`data_criacao`, `data_atualizacao`) values (@PermissaoId,'DELETE /api/v1/cidades/$id<[^/]+>','[Cidade] - Remover Cidade',NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`,`permissao_id`) VALUES (@PerfilId,@PermissaoId);

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`,`chave`,`descricao`,`data_criacao`, `data_atualizacao`) values (@PermissaoId,'GET /api/v1/areas','[Área] - Listar Áreas',NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`,`permissao_id`) VALUES (@PerfilId,@PermissaoId);

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`,`chave`,`descricao`,`data_criacao`, `data_atualizacao`) values (@PermissaoId,'POST /api/v1/areas','[Área] - Criar Nova Área',NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`,`permissao_id`) VALUES (@PerfilId,@PermissaoId);

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`,`chave`,`descricao`,`data_criacao`, `data_atualizacao`) values (@PermissaoId,'PUT /api/v1/areas/$id<[^/]+>','[Área] - Editar Área',NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`,`permissao_id`) VALUES (@PerfilId,@PermissaoId);

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`,`chave`,`descricao`,`data_criacao`, `data_atualizacao`) values (@PermissaoId,'GET /api/v1/areas/$id<[^/]+>','[Área] - Exibir uma Área',NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`,`permissao_id`) VALUES (@PerfilId,@PermissaoId);

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`,`chave`,`descricao`,`data_criacao`, `data_atualizacao`) values (@PermissaoId,'DELETE /api/v1/areas/$id<[^/]+>','[Área] - Remover Área',NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`,`permissao_id`) VALUES (@PerfilId,@PermissaoId);

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`,`chave`,`descricao`,`data_criacao`, `data_atualizacao`) values (@PermissaoId,'GET /api/v1/controladores','[Controladores] - Listar Controladores',NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`,`permissao_id`) VALUES (@PerfilId,@PermissaoId);

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`,`chave`,`descricao`,`data_criacao`, `data_atualizacao`) values (@PermissaoId,'POST /api/v1/controladores/dados_basicos','[Controladores] - Cadastrar Dados Básicos',NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`,`permissao_id`) VALUES (@PerfilId,@PermissaoId);

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`,`chave`,`descricao`,`data_criacao`, `data_atualizacao`) values (@PermissaoId,'POST /api/v1/controladores/aneis','[Controladores] - Cadastrar Anéis',NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`,`permissao_id`) VALUES (@PerfilId,@PermissaoId);

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`,`chave`,`descricao`,`data_criacao`, `data_atualizacao`) values (@PermissaoId,'POST /api/v1/controladores/associacao_grupos_semaforicos','[Controladores] - Cadastrar Grupos Semafóricos',NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`,`permissao_id`) VALUES (@PerfilId,@PermissaoId);

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`,`chave`,`descricao`,`data_criacao`, `data_atualizacao`) values (@PermissaoId,'POST /api/v1/controladores/verdes_conflitantes','[Controladores] - Cadastrar Verdes Conflitantes',NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`,`permissao_id`) VALUES (@PerfilId,@PermissaoId);

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`,`chave`,`descricao`,`data_criacao`, `data_atualizacao`) values (@PermissaoId,'GET /api/v1/controladores/$id<[^/]+>','[Controladores] - Exibir Controlador',NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`,`permissao_id`) VALUES (@PerfilId,@PermissaoId);

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`,`chave`,`descricao`,`data_criacao`, `data_atualizacao`) values (@PermissaoId,'DELETE /api/v1/controladores/$id<[^/]+>','[Controladores] - Remover Controlador',NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`,`permissao_id`) VALUES (@PerfilId,@PermissaoId);

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`,`chave`,`descricao`,`data_criacao`, `data_atualizacao`) values (@PermissaoId,'GET /api/v1/fabricantes','[Fabricante] - Listar Fabricantes',NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`,`permissao_id`) VALUES (@PerfilId,@PermissaoId);

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`,`chave`,`descricao`,`data_criacao`, `data_atualizacao`) values (@PermissaoId,'POST /api/v1/fabricantes','[Fabricante] - Cadastrar Novo Fabricante',NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`,`permissao_id`) VALUES (@PerfilId,@PermissaoId);

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`,`chave`,`descricao`,`data_criacao`, `data_atualizacao`) values (@PermissaoId,'PUT /api/v1/fabricantes/$id<[^/]+>','[Fabricante] - Editar Fabricante',NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`,`permissao_id`) VALUES (@PerfilId,@PermissaoId);

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`,`chave`,`descricao`,`data_criacao`, `data_atualizacao`) values (@PermissaoId,'GET /api/v1/fabricantes/$id<[^/]+>','[Fabricante] - Exibir Fabricante',NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`,`permissao_id`) VALUES (@PerfilId,@PermissaoId);

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`,`chave`,`descricao`,`data_criacao`, `data_atualizacao`) values (@PermissaoId,'DELETE /api/v1/fabricantes/$id<[^/]+>','[Fabricante] - Remover Fabricante',NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`,`permissao_id`) VALUES (@PerfilId,@PermissaoId);

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`,`chave`,`descricao`,`data_criacao`, `data_atualizacao`) values (@PermissaoId,'GET /api/v1/configuracoes_controladores','[Configuração de Controlador] - Listar Configurações',NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`,`permissao_id`) VALUES (@PerfilId,@PermissaoId);

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`,`chave`,`descricao`,`data_criacao`, `data_atualizacao`) values (@PermissaoId,'POST /api/v1/configuracoes_controladores','[Configuração de Controlador] - Criar Uma Nova Configuração',NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`,`permissao_id`) VALUES (@PerfilId,@PermissaoId);

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`,`chave`,`descricao`,`data_criacao`, `data_atualizacao`) values (@PermissaoId,'PUT /api/v1/configuracoes_controladores/$id<[^/]+>','[Configuração de Controlador] - Editar Configuração',NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`,`permissao_id`) VALUES (@PerfilId,@PermissaoId);

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`,`chave`,`descricao`,`data_criacao`, `data_atualizacao`) values (@PermissaoId,'GET /api/v1/configuracoes_controladores/$id<[^/]+>','[Configuração de Controlador] - Exibir Configuração',NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`,`permissao_id`) VALUES (@PerfilId,@PermissaoId);

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`,`chave`,`descricao`,`data_criacao`, `data_atualizacao`) values (@PermissaoId,'DELETE /api/v1/configuracoes_controladores/$id<[^/]+>','[Configuração de Controlador] - Remover Configuração',NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`,`permissao_id`) VALUES (@PerfilId,@PermissaoId);

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`,`chave`,`descricao`,`data_criacao`, `data_atualizacao`) values (@PermissaoId,'GET /api/v1/modelos_controladores','[Modelo de Controlador] - Listar Modelos de Controladores',NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`,`permissao_id`) VALUES (@PerfilId,@PermissaoId);

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`,`chave`,`descricao`,`data_criacao`, `data_atualizacao`) values (@PermissaoId,'POST /api/v1/modelos_controladores','[Modelo de Controlador] - Criar Novo Modelo de Controlador',NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`,`permissao_id`) VALUES (@PerfilId,@PermissaoId);

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`,`chave`,`descricao`,`data_criacao`, `data_atualizacao`) values (@PermissaoId,'PUT /api/v1/modelos_controladores/$id<[^/]+>','[Modelo de Controlador] - Editar Modelo de Controlador',NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`,`permissao_id`) VALUES (@PerfilId,@PermissaoId);

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`,`chave`,`descricao`,`data_criacao`, `data_atualizacao`) values (@PermissaoId,'GET /api/v1/modelos_controladores/$id<[^/]+>','[Modelo de Controlador] - Exibir Modelo de Controlador',NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`,`permissao_id`) VALUES (@PerfilId,@PermissaoId);

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`,`chave`,`descricao`,`data_criacao`, `data_atualizacao`) values (@PermissaoId,'DELETE /api/v1/modelos_controladores/$id<[^/]+>','[Modelo de Controlador] - Remover Modelo de Controlador',NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`,`permissao_id`) VALUES (@PerfilId,@PermissaoId);

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`,`chave`,`descricao`,`data_criacao`, `data_atualizacao`) values (@PermissaoId,'GET /api/v1/usuarios','[Usuário] - Listar Usuários',NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`,`permissao_id`) VALUES (@PerfilId,@PermissaoId);

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`,`chave`,`descricao`,`data_criacao`, `data_atualizacao`) values (@PermissaoId,'POST /api/v1/usuarios','[Usuário] - Criar um Novo Usuário',NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`,`permissao_id`) VALUES (@PerfilId,@PermissaoId);

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`,`chave`,`descricao`,`data_criacao`, `data_atualizacao`) values (@PermissaoId,'PUT /api/v1/usuarios/$id<[^/]+>','[Usuário] - Editar um Novo Usuário',NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`,`permissao_id`) VALUES (@PerfilId,@PermissaoId);

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`,`chave`,`descricao`,`data_criacao`, `data_atualizacao`) values (@PermissaoId,'GET /api/v1/usuarios/$id<[^/]+>','[Usuário] - Exibir um Usuário',NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`,`permissao_id`) VALUES (@PerfilId,@PermissaoId);

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`,`chave`,`descricao`,`data_criacao`, `data_atualizacao`) values (@PermissaoId,'DELETE /api/v1/usuarios/$id<[^/]+>','[Usuário] - Remover Usuários',NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`,`permissao_id`) VALUES (@PerfilId,@PermissaoId);

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`,`chave`,`descricao`,`data_criacao`, `data_atualizacao`) values (@PermissaoId,'GET /api/v1/perfis','[Perfil] - Listar Perfis',NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`,`permissao_id`) VALUES (@PerfilId,@PermissaoId);

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`,`chave`,`descricao`,`data_criacao`, `data_atualizacao`) values (@PermissaoId,'POST /api/v1/perfis','[Perfil] - Criar Novo Perfil',NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`,`permissao_id`) VALUES (@PerfilId,@PermissaoId);

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`,`chave`,`descricao`,`data_criacao`, `data_atualizacao`) values (@PermissaoId,'PUT /api/v1/perfis/$id<[^/]+>','[Perfil] - Editar Perfil',NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`,`permissao_id`) VALUES (@PerfilId,@PermissaoId);

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`,`chave`,`descricao`,`data_criacao`, `data_atualizacao`) values (@PermissaoId,'GET /api/v1/perfis/$id<[^/]+>','[Perfil] - Exibir Perfil',NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`,`permissao_id`) VALUES (@PerfilId,@PermissaoId);

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`,`chave`,`descricao`,`data_criacao`, `data_atualizacao`) values (@PermissaoId,'DELETE /api/v1/perfis/$id<[^/]+>','[Perfil] - Remover Perfil',NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`,`permissao_id`) VALUES (@PerfilId,@PermissaoId);

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`,`chave`,`descricao`,`data_criacao`, `data_atualizacao`) values (@PermissaoId,'GET /api/v1/permissoes','[Permissão] - Listar Permissão',NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`,`permissao_id`) VALUES (@PerfilId,@PermissaoId);

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`,`chave`,`descricao`,`data_criacao`, `data_atualizacao`) values (@PermissaoId,'POST /api/v1/permissoes','[Permissão] - Criar Nova Permissão',NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`,`permissao_id`) VALUES (@PerfilId,@PermissaoId);

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`,`chave`,`descricao`,`data_criacao`, `data_atualizacao`) values (@PermissaoId,'PUT /api/v1/permissoes/$id<[^/]+>','[Permissão] - Editar Permissão',NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`,`permissao_id`) VALUES (@PerfilId,@PermissaoId);

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`,`chave`,`descricao`,`data_criacao`, `data_atualizacao`) values (@PermissaoId,'GET /api/v1/permissoes/$id<[^/]+>','[Permissão] - Exibir Permissão',NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`,`permissao_id`) VALUES (@PerfilId,@PermissaoId);

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`,`chave`,`descricao`,`data_criacao`, `data_atualizacao`) values (@PermissaoId,'DELETE /api/v1/permissoes/$id<[^/]+>','[Permissão] - Remover Permissão',NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`,`permissao_id`) VALUES (@PerfilId,@PermissaoId);

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`,`chave`,`descricao`,`data_criacao`, `data_atualizacao`) values (@PermissaoId,'POST /api/v1/imagens','[Imagem] - Cadastrar Imagem',NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`,`permissao_id`) VALUES (@PerfilId,@PermissaoId);

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`,`chave`,`descricao`,`data_criacao`, `data_atualizacao`) values (@PermissaoId,'GET /api/v1/imagens/$id<[^/]+>','[Imagem] - Exibir Imagem',NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`,`permissao_id`) VALUES (@PerfilId,@PermissaoId);

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`,`chave`,`descricao`,`data_criacao`, `data_atualizacao`) values (@PermissaoId,'GET /api/v1/helpers/controlador','[Controlador] - Exibir tela de Cadastro de Controladores',NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`,`permissao_id`) VALUES (@PerfilId,@PermissaoId);

COMMIT;

