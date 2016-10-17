BEGIN;

SET @CidadeId = UUID();
SET @AreaId = UUID();
INSERT INTO `cidades` (`id`, `id_json`, `nome`, `data_criacao`, `data_atualizacao`) VALUES (@CidadeId, UUID(), 'São Paulo', NOW(), NOW());
INSERT INTO `areas` (`id`, `id_json`, `descricao`, `cidade_id`, `data_criacao`, `data_atualizacao`) VALUES (@AreaId, UUID(), 1, @CidadeId, NOW(), NOW());

SET @FabricanteId = UUID();
INSERT INTO `fabricantes` (`id`, `id_json`, `nome`, `data_criacao`, `data_atualizacao`) VALUES (@FabricanteId, UUID(), 'Raro Labs', NOW(), NOW());
INSERT INTO `modelo_controladores` (`id`, `id_json`, `descricao`, `fabricante_id`, `limite_estagio`, `limite_grupo_semaforico`, `limite_anel`, `limite_detector_pedestre`, `limite_detector_veicular`, `limite_tabelas_entre_verdes`, `limite_planos`, `data_criacao`, `data_atualizacao`) VALUES (UUID(), UUID(), 'Modelo Básico', @FabricanteId, 16, 16, 4, 4, 8, 2, 16, NOW(), NOW());
INSERT INTO `usuarios` (`id`, `login`, `senha`, `email`, `nome`, `root`, `data_criacao`, `data_atualizacao`) VALUES (UUID(), 'root', '$2a$10$EzudGIqkxquJjLGawuMrOu9K6S28yc/R/YSAVxsvb5bSryOYWd5eq', 'root@influunt.com.br', 'Administrador Geral', true, NOW(), NOW());

-- # Faixas de Valores com valores Padrão
INSERT INTO `influuntdev`.`faixas_de_valores` (`id`, `id_json`, `tempo_defasagem_min`, `tempo_defasagem_max`, `tempo_amarelo_min`, `tempo_amarelo_max`, `tempo_vermelho_intermitente_min`, `tempo_vermelho_intermitente_max`, `tempo_vermelho_limpeza_veicular_min`, `tempo_vermelho_limpeza_veicular_max`, `tempo_vermelho_limpeza_pedestre_min`, `tempo_vermelho_limpeza_pedestre_max`, `tempo_atraso_grupo_min`, `tempo_atraso_grupo_max`, `tempo_verde_seguranca_veicular_min`, `tempo_verde_seguranca_veicular_max`, `tempo_verde_seguranca_pedestre_min`, `tempo_verde_seguranca_pedestre_max`, `tempo_maximo_permanencia_estagio_min`, `tempo_maximo_permanencia_estagio_max`, `default_tempo_maximo_permanencia_estagio_veicular`, `tempo_ciclo_min`, `tempo_ciclo_max`, `tempo_verde_minimo_min`, `tempo_verde_minimo_max`, `tempo_verde_maximo_min`, `tempo_verde_maximo_max`, `tempo_verde_intermediario_min`, `tempo_verde_intermediario_max`, `tempo_extensao_verde_min`, `tempo_extensao_verde_max`, `tempo_verde_min`, `tempo_verde_max`, `tempo_ausencia_deteccao_min`, `tempo_ausencia_deteccao_max`, `tempo_deteccao_permanente_min`, `tempo_deteccao_permanente_max`, `data_criacao`, `data_atualizacao`) VALUES (UUID(), UUID(), '0', '255', '3', '5', '3', '32', '0', '7', '0', '5', '0', '20', '10', '30', '4', '10', '60', '255', '127', '30', '255', '10', '255', '10', '255', '10', '255', '1.0', '10.0', '1', '255', '0', '4320', '0', '1440', NOW(), NOW());

-- Perfis / Permissões
Set @PerfilAdministradorId = UUID();
INSERT into `perfis` (`id`, `nome`, `data_criacao`, `data_atualizacao`) VALUE (@PerfilAdministradorId, 'Administrador', NOW(), NOW());
Set @PerfilEngenheiroId = UUID();
INSERT into `perfis` (`id`, `nome`, `data_criacao`, `data_atualizacao`) VALUE (@PerfilEngenheiroId, 'Engenheiro', NOW(), NOW());
Set @PerfilOperadorId = UUID();
INSERT into `perfis` (`id`, `nome`, `data_criacao`, `data_atualizacao`) VALUE (@PerfilOperadorId, 'Operador', NOW(), NOW());
Set @PerfilTecnicoId = UUID();
INSERT into `perfis` (`id`, `nome`, `data_criacao`, `data_atualizacao`) VALUE (@PerfilTecnicoId, 'Técnico', NOW(), NOW());

-- # Usuario Mobilab
INSERT INTO `usuarios` (`id`, `login`, `senha`, `email`, `nome`, `root`, `area_id`, `perfil_id`, `data_criacao`, `data_atualizacao`) VALUES (UUID(), 'mobilab', '$2a$10$bfiF2TyTirIyEh6AmWK1huI5.ol0.OxBC3hM9a7Nrc2x9TM.SBooG', 'mobilab@mobilab.com.br', 'Mobilab', false, @AreaId, @PerfilAdministradorId, NOW(), NOW());


# Permissões no APP
SET @listarControladoresId = UUID();
INSERT INTO `permissoes_app` (`id`, `chave`, `nome`, `data_criacao`, `data_atualizacao`) VALUES (@listarControladoresId, 'listarControladores', '[Programação] Ver Controladores Cadastrados', NOW(), NOW());
SET @visualizarControladorId = UUID();
INSERT INTO `permissoes_app` (`id`, `chave`, `nome`, `data_criacao`, `data_atualizacao`) VALUES (@visualizarControladorId, 'verControlador', '[Programação] Ver Detalhes de Controladores', NOW(), NOW());
SET @criarControladorId = UUID();
INSERT INTO `permissoes_app` (`id`, `chave`, `nome`, `data_criacao`, `data_atualizacao`) VALUES (@criarControladorId, 'criarControlador', '[Programação] Criar Novos Controladores', NOW(), NOW());
SET @verNoMapaId = UUID();
INSERT INTO `permissoes_app` (`id`, `chave`, `nome`, `data_criacao`, `data_atualizacao`) VALUES (@verNoMapaId, 'verNoMapa', '[Programação] Ver Controladores no Mapa', NOW(), NOW());
SET @verPlanosId = UUID();
INSERT INTO `permissoes_app` (`id`, `chave`, `nome`, `data_criacao`, `data_atualizacao`) VALUES (@verPlanosId, 'verPlanos', '[Programação] Ver Planos', NOW(), NOW());
SET @criarPlanosId = UUID();
INSERT INTO `permissoes_app` (`id`, `chave`, `nome`, `data_criacao`, `data_atualizacao`) VALUES (@criarPlanosId, 'criarPlanos', '[Programação] Criar Planos', NOW(), NOW());
SET @verTabelaHorariaId = UUID();
INSERT INTO `permissoes_app` (`id`, `chave`, `nome`, `data_criacao`, `data_atualizacao`) VALUES (@verTabelaHorariaId, 'verTabelaHoraria', '[Programação] Ver Tabelas Horárias', NOW(), NOW());
SET @criarTabelaHorariaId = UUID();
INSERT INTO `permissoes_app` (`id`, `chave`, `nome`, `data_criacao`, `data_atualizacao`) VALUES (@criarTabelaHorariaId, 'criarTabelaHoraria', '[Programação] Criar Tabelas Horárias', NOW(), NOW());
SET @CriarAgrupamentoId = UUID();
INSERT INTO `permissoes_app` (`id`, `chave`, `nome`, `data_criacao`, `data_atualizacao`) VALUES (@CriarAgrupamentoId, 'criarAgrupamentos', '[Agrupamentos] - Criar Agrupamentos', NOW(), NOW());
SET @EditarUsuariosId = UUID();
INSERT INTO `permissoes_app` (`id`, `chave`, `nome`, `data_criacao`, `data_atualizacao`) VALUES (@EditarUsuariosId, 'editarUsuarios', '[Usuários] - Editar Usuários', NOW(), NOW());
SET @DefinirPermissoesId = UUID();
INSERT INTO `permissoes_app` (`id`, `chave`, `nome`, `data_criacao`, `data_atualizacao`) VALUES (@DefinirPermissoesId, 'definirPermissoes', '[Perfis] - Definir Permissões', NOW(), NOW());




-- # CRUD Cidades

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/cidades', '[Cidade] - Listar Cidades', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);
SET @permAppId = UUID();
INSERT INTO `permissoes_app` (`id`, `chave`, `nome`, `data_criacao`, `data_atualizacao`) VALUES (@permAppId, 'listarCidades', '[Cidade] - Listar Cidades', NOW(), NOW());
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@permAppId, @PermissaoId);

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'POST /api/v1/cidades', '[Cidade] - Criar Nova Cidade', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);
SET @permAppId = UUID();
INSERT INTO `permissoes_app` (`id`, `chave`, `nome`, `data_criacao`, `data_atualizacao`) VALUES (@permAppId, 'criarCidades', '[Cidade] - Criar Cidades', NOW(), NOW());
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@permAppId, @PermissaoId);

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'PUT /api/v1/cidades/$id<[^/]+>', '[Cidade] - Editar Cidade', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);
SET @permAppId = UUID();
INSERT INTO `permissoes_app` (`id`, `chave`, `nome`, `data_criacao`, `data_atualizacao`) VALUES (@permAppId, 'editarCidades', '[Cidade] - Editar Cidades', NOW(), NOW());
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@permAppId, @PermissaoId);

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/cidades/$id<[^/]+>', '[Cidade] - Exibir uma Cidade', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);
SET @permAppId = UUID();
INSERT INTO `permissoes_app` (`id`, `chave`, `nome`, `data_criacao`, `data_atualizacao`) VALUES (@permAppId, 'visualizarCidades', '[Cidade] - Ver Detalhes de Cidades', NOW(), NOW());
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@permAppId, @PermissaoId);

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'DELETE /api/v1/cidades/$id<[^/]+>', '[Cidade] - Remover Cidade', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);
SET @permAppId = UUID();
INSERT INTO `permissoes_app` (`id`, `chave`, `nome`, `data_criacao`, `data_atualizacao`) VALUES (@permAppId, 'removerCidades', '[Cidade] - Remover Cidades', NOW(), NOW());
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@permAppId, @PermissaoId);




-- # CRUD Areas

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/areas', '[Área] - Listar Áreas', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);
SET @permAppId = UUID();
INSERT INTO `permissoes_app` (`id`, `chave`, `nome`, `data_criacao`, `data_atualizacao`) VALUES (@permAppId, 'listarAreas', '[Área] - Listar Áreas', NOW(), NOW());
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@permAppId, @PermissaoId);
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@EditarUsuariosId, @PermissaoId);


Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'POST /api/v1/areas', '[Área] - Criar Nova Área', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);
SET @permAppId = UUID();
INSERT INTO `permissoes_app` (`id`, `chave`, `nome`, `data_criacao`, `data_atualizacao`) VALUES (@permAppId, 'criarAreas', '[Área] - Criar Áreas', NOW(), NOW());
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@permAppId, @PermissaoId);


Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'PUT /api/v1/areas/$id<[^/]+>', '[Área] - Editar Área', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);
SET @permAppId = UUID();
INSERT INTO `permissoes_app` (`id`, `chave`, `nome`, `data_criacao`, `data_atualizacao`) VALUES (@permAppId, 'editarAreas', '[Área] - Editar Áreas', NOW(), NOW());
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@permAppId, @PermissaoId);


Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/areas/$id<[^/]+>', '[Área] - Exibir uma Área', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);
SET @permAppId = UUID();
INSERT INTO `permissoes_app` (`id`, `chave`, `nome`, `data_criacao`, `data_atualizacao`) VALUES (@permAppId, 'visualizarAreas', '[Área] - Ver Detalhes de Áreas', NOW(), NOW());
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@permAppId, @PermissaoId);


Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'DELETE /api/v1/areas/$id<[^/]+>', '[Área] - Remover Área', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);
SET @permAppId = UUID();
INSERT INTO `permissoes_app` (`id`, `chave`, `nome`, `data_criacao`, `data_atualizacao`) VALUES (@permAppId, 'removerAreas', '[Área] - Remover Áreas', NOW(), NOW());
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@permAppId, @PermissaoId);





-- # CRUD Subareas

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/subareas', '[Subárea] - Listar Subárea', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);
SET @permAppId = UUID();
INSERT INTO `permissoes_app` (`id`, `chave`, `nome`, `data_criacao`, `data_atualizacao`) VALUES (@permAppId, 'listarSubareas', '[Subárea] - Listar Subáreas', NOW(), NOW());
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@permAppId, @PermissaoId);
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@CriarAgrupamentoId, @PermissaoId);

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'POST /api/v1/subareas', '[Subárea] - Criar Nova Subárea', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);
SET @permAppId = UUID();
INSERT INTO `permissoes_app` (`id`, `chave`, `nome`, `data_criacao`, `data_atualizacao`) VALUES (@permAppId, 'criarSubareas', '[Subárea] - Criar Subáreas', NOW(), NOW());
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@permAppId, @PermissaoId);

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'PUT /api/v1/subareas/$id<[^/]+>', '[Subárea] - Editar Subárea', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);
SET @permAppId = UUID();
INSERT INTO `permissoes_app` (`id`, `chave`, `nome`, `data_criacao`, `data_atualizacao`) VALUES (@permAppId, 'editarSubareas', '[Subárea] - Editar Subáreas', NOW(), NOW());
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@permAppId, @PermissaoId);

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/subareas/$id<[^/]+>', '[Subárea] - Exibir uma Subárea', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);
SET @permAppId = UUID();
INSERT INTO `permissoes_app` (`id`, `chave`, `nome`, `data_criacao`, `data_atualizacao`) VALUES (@permAppId, 'visualizarSubareas', '[Subárea] - Ver Detalhes de Subáreas', NOW(), NOW());
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@permAppId, @PermissaoId);

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'DELETE /api/v1/subareas/$id<[^/]+>', '[Subárea] - Remover Subárea', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);
SET @permAppId = UUID();
INSERT INTO `permissoes_app` (`id`, `chave`, `nome`, `data_criacao`, `data_atualizacao`) VALUES (@permAppId, 'removerSubareas', '[Subárea] - Remover Subáreas', NOW(), NOW());
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@permAppId, @PermissaoId);




-- # CRUD Fabricantes

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/fabricantes', '[Fabricante] - Listar Fabricantes', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);
SET @permAppId = UUID();
INSERT INTO `permissoes_app` (`id`, `chave`, `nome`, `data_criacao`, `data_atualizacao`) VALUES (@permAppId, 'listarFabricantes', '[Fabricante] - Listar Fabricantes', NOW(), NOW());
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@permAppId, @PermissaoId);


Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'POST /api/v1/fabricantes', '[Fabricante] - Cadastrar Novo Fabricante', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);
SET @permAppId = UUID();
INSERT INTO `permissoes_app` (`id`, `chave`, `nome`, `data_criacao`, `data_atualizacao`) VALUES (@permAppId, 'criarFabricantes', '[Fabricante] - Criar Fabricantes', NOW(), NOW());
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@permAppId, @PermissaoId);


Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'PUT /api/v1/fabricantes/$id<[^/]+>', '[Fabricante] - Editar Fabricante', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);
SET @permAppId = UUID();
INSERT INTO `permissoes_app` (`id`, `chave`, `nome`, `data_criacao`, `data_atualizacao`) VALUES (@permAppId, 'editarFabricantes', '[Fabricante] - Editar Fabricantes', NOW(), NOW());
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@permAppId, @PermissaoId);


Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/fabricantes/$id<[^/]+>', '[Fabricante] - Exibir Fabricante', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);
SET @permAppId = UUID();
INSERT INTO `permissoes_app` (`id`, `chave`, `nome`, `data_criacao`, `data_atualizacao`) VALUES (@permAppId, 'visualizarFabricantes', '[Fabricante] - Ver Detalhes de Fabricantes', NOW(), NOW());
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@permAppId, @PermissaoId);


Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'DELETE /api/v1/fabricantes/$id<[^/]+>', '[Fabricante] - Remover Fabricante', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);
SET @permAppId = UUID();
INSERT INTO `permissoes_app` (`id`, `chave`, `nome`, `data_criacao`, `data_atualizacao`) VALUES (@permAppId, 'removerFabricantes', '[Fabricante] - Remover Fabricantes', NOW(), NOW());
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@permAppId, @PermissaoId);




-- # CRUD Modelos de Controladores

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/modelos', '[Modelo Controlador] - Listar Modelos de Controladores', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);
SET @permAppId = UUID();
INSERT INTO `permissoes_app` (`id`, `chave`, `nome`, `data_criacao`, `data_atualizacao`) VALUES (@permAppId, 'listarModelos', '[Modelo Controlador] - Listar Modelos de Controladores', NOW(), NOW());
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@permAppId, @PermissaoId);

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'POST /api/v1/modelos', '[Modelo Controlador] - Cadastrar Novo Modelo de Controlador', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);
SET @permAppId = UUID();
INSERT INTO `permissoes_app` (`id`, `chave`, `nome`, `data_criacao`, `data_atualizacao`) VALUES (@permAppId, 'criarModelos', '[Modelo Controlador] - Criar Modelos de Controladores', NOW(), NOW());
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@permAppId, @PermissaoId);

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'PUT /api/v1/modelos/$id<[^/]+>', '[Modelo Controlador] - Editar Modelo de Controlador', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);
SET @permAppId = UUID();
INSERT INTO `permissoes_app` (`id`, `chave`, `nome`, `data_criacao`, `data_atualizacao`) VALUES (@permAppId, 'editarModelos', '[Modelo Controlador] - Editar Modelos de Controladores', NOW(), NOW());
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@permAppId, @PermissaoId);

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/modelos/$id<[^/]+>', '[Modelo Controlador] - Exibir Modelo de Controlador', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);
SET @permAppId = UUID();
INSERT INTO `permissoes_app` (`id`, `chave`, `nome`, `data_criacao`, `data_atualizacao`) VALUES (@permAppId, 'visualizarModelos', '[Modelo Controlador] - Ver Detalhes de Modelos de Controladores', NOW(), NOW());
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@permAppId, @PermissaoId);

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'DELETE /api/v1/modelos/$id<[^/]+>', '[Modelo Controlador] - Remover Modelo de Controlador', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);
SET @permAppId = UUID();
INSERT INTO `permissoes_app` (`id`, `chave`, `nome`, `data_criacao`, `data_atualizacao`) VALUES (@permAppId, 'removerModelos', '[Modelo Controlador] - Remover Modelos de Controladores', NOW(), NOW());
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@permAppId, @PermissaoId);




-- # CRUD Agrupamentos

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/agrupamentos', '[Agrupamento] - Listar Agrupamentos', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);
SET @permAppId = UUID();
INSERT INTO `permissoes_app` (`id`, `chave`, `nome`, `data_criacao`, `data_atualizacao`) VALUES (@permAppId, 'listarAgrupamentos', '[Agrupamentos] - Listar Agrupamentos', NOW(), NOW());
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@permAppId, @PermissaoId);

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'POST /api/v1/agrupamentos', '[Agrupamento] - Criar Novo Agrupamento', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@CriarAgrupamentoId, @PermissaoId);
SET @permAppId = UUID();
INSERT INTO `permissoes_app` (`id`, `chave`, `nome`, `data_criacao`, `data_atualizacao`) VALUES (@permAppId, 'criarAgrupamentos', '[Agrupamentos] - Criar Agrupamentos', NOW(), NOW());
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@permAppId, @PermissaoId);

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'PUT /api/v1/agrupamentos/$id<[^/]+>', '[Agrupamento] - Editar Agrupamento', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);
SET @permAppId = UUID();
INSERT INTO `permissoes_app` (`id`, `chave`, `nome`, `data_criacao`, `data_atualizacao`) VALUES (@permAppId, 'editarAgrupamentos', '[Agrupamentos] - Editar Agrupamentos', NOW(), NOW());
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@permAppId, @PermissaoId);

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/agrupamentos/$id<[^/]+>', '[Agrupamento] - Exibir um Agrupamento', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);
SET @permAppId = UUID();
INSERT INTO `permissoes_app` (`id`, `chave`, `nome`, `data_criacao`, `data_atualizacao`) VALUES (@permAppId, 'visualizarAgrupamentos', '[Agrupamentos] - Ver Detalhes de Agrupamentos', NOW(), NOW());
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@permAppId, @PermissaoId);

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'DELETE /api/v1/agrupamentos/$id<[^/]+>', '[Agrupamento] - Remover Agrupamento', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);
SET @permAppId = UUID();
INSERT INTO `permissoes_app` (`id`, `chave`, `nome`, `data_criacao`, `data_atualizacao`) VALUES (@permAppId, 'removerAgrupamentos', '[Agrupamentos] - Remover Agrupamentos', NOW(), NOW());
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@permAppId, @PermissaoId);




-- # CRUD Imagens

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'POST /api/v1/imagens', '[Imagem] - Cadastrar Imagem', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@criarControladorId, @PermissaoId);

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'DELETE /api/v1/imagens/$id<[^/]+>', '[Imagem] - Apagar Imagem', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@criarControladorId, @PermissaoId);

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'DELETE /api/v1/imagens/$id<[^/]+>/croqui', '[Imagem] - Apagar Imagem de Croqui', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@criarControladorId, @PermissaoId);




-- # CRUD Usuários

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/usuarios', '[Usuário] - Listar Usuários', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
-- INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);
SET @permAppId = UUID();
INSERT INTO `permissoes_app` (`id`, `chave`, `nome`, `data_criacao`, `data_atualizacao`) VALUES (@permAppId, 'listarUsuarios', '[Usuários] - Listar Usuários', NOW(), NOW());
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@permAppId, @PermissaoId);

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'POST /api/v1/usuarios', '[Usuário] - Criar um Novo Usuário', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
-- INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);
SET @permAppId = UUID();
INSERT INTO `permissoes_app` (`id`, `chave`, `nome`, `data_criacao`, `data_atualizacao`) VALUES (@permAppId, 'criarUsuarios', '[Usuários] - Criar Usuários', NOW(), NOW());
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@permAppId, @PermissaoId);

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'PUT /api/v1/usuarios/$id<[^/]+>', '[Usuário] - Editar um Usuário', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
-- INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);
SET @permAppId = UUID();
-- INSERT INTO `permissoes_app` (`id`, `chave`, `nome`, `data_criacao`, `data_atualizacao`) VALUES (@permAppId, 'editarUsuarios', '[Usuários] - Editar Usuários', NOW(), NOW());
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@EditarUsuariosId, @PermissaoId);

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/usuarios/$id<[^/]+>', '[Usuário] - Exibir um Usuário', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
-- INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);
SET @permAppId = UUID();
INSERT INTO `permissoes_app` (`id`, `chave`, `nome`, `data_criacao`, `data_atualizacao`) VALUES (@permAppId, 'visualizarUsuarios', '[Usuários] - Ver Detalhes de Usuários', NOW(), NOW());
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@permAppId, @PermissaoId);
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@EditarUsuariosId, @PermissaoId);

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'DELETE /api/v1/usuarios/$id<[^/]+>', '[Usuário] - Remover Usuários', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
-- INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);
SET @permAppId = UUID();
INSERT INTO `permissoes_app` (`id`, `chave`, `nome`, `data_criacao`, `data_atualizacao`) VALUES (@permAppId, 'removerUsuarios', '[Usuários] - Remover Usuários', NOW(), NOW());
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@permAppId, @PermissaoId);

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/usuarios/$id<[^/]+>/access_log', '[Usuário] - Visualizar Log de Acesso', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
-- INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);
SET @permAppId = UUID();
INSERT INTO `permissoes_app` (`id`, `chave`, `nome`, `data_criacao`, `data_atualizacao`) VALUES (@permAppId, 'verLogAcessoUsuarios', '[Usuários] - Ver Log de Acesso de Usuários', NOW(), NOW());
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@permAppId, @PermissaoId);




-- # CRUD Perfis

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/perfis', '[Perfil] - Listar Perfis', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
-- INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);
SET @permAppId = UUID();
INSERT INTO `permissoes_app` (`id`, `chave`, `nome`, `data_criacao`, `data_atualizacao`) VALUES (@permAppId, 'listarPerfis', '[Perfis] - Listar Perfis', NOW(), NOW());
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@permAppId, @PermissaoId);
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@EditarUsuariosId, @PermissaoId);
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@DefinirPermissoesId, @PermissaoId);

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'POST /api/v1/perfis', '[Perfil] - Criar Novo Perfil', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
-- INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);
SET @permAppId = UUID();
INSERT INTO `permissoes_app` (`id`, `chave`, `nome`, `data_criacao`, `data_atualizacao`) VALUES (@permAppId, 'criarPerfis', '[Perfis] - Criar Perfis', NOW(), NOW());
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@permAppId, @PermissaoId);

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'PUT /api/v1/perfis/$id<[^/]+>', '[Perfil] - Editar Perfil', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
-- INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);
SET @permAppId = UUID();
INSERT INTO `permissoes_app` (`id`, `chave`, `nome`, `data_criacao`, `data_atualizacao`) VALUES (@permAppId, 'editarPerfis', '[Perfis] - Editar Perfis', NOW(), NOW());
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@permAppId, @PermissaoId);
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@DefinirPermissoesId, @PermissaoId);

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/perfis/$id<[^/]+>', '[Perfil] - Exibir Perfil', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
-- INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);
SET @permAppId = UUID();
INSERT INTO `permissoes_app` (`id`, `chave`, `nome`, `data_criacao`, `data_atualizacao`) VALUES (@permAppId, 'visualizarPerfis', '[Perfis] - Ver Detalhes de Perfis', NOW(), NOW());
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@permAppId, @PermissaoId);
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@DefinirPermissoesId, @PermissaoId);

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'DELETE /api/v1/perfis/$id<[^/]+>', '[Perfil] - Remover Perfil', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
-- INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);
SET @permAppId = UUID();
INSERT INTO `permissoes_app` (`id`, `chave`, `nome`, `data_criacao`, `data_atualizacao`) VALUES (@permAppId, 'removerPerfis', '[Perfis] - Remover Perfis', NOW(), NOW());
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@permAppId, @PermissaoId);




-- # CRUD Permissões

-- Set @PermissaoId = UUID();
-- INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/permissoes', '[Permissão] - Listar Permissão', NOW(), NOW());
-- INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
-- -- INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);
-- SET @permAppId = UUID();
-- INSERT INTO `permissoes_app` (`id`, `chave`, `nome`, `data_criacao`, `data_atualizacao`) VALUES (@permAppId, 'listarPermissoes', '[Permissões] - Listar Permissões', NOW(), NOW());
-- INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@permAppId, @PermissaoId);
-- INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@DefinirPermissoesId, @PermissaoId);

-- Set @PermissaoId = UUID();
-- INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'POST /api/v1/permissoes', '[Permissão] - Criar Nova Permissão', NOW(), NOW());
-- INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
-- -- INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);
-- SET @permAppId = UUID();
-- INSERT INTO `permissoes_app` (`id`, `chave`, `nome`, `data_criacao`, `data_atualizacao`) VALUES (@permAppId, 'criarPermissoes', '[Permissões] - Criar Permissões', NOW(), NOW());
-- INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@permAppId, @PermissaoId);

-- Set @PermissaoId = UUID();
-- INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'PUT /api/v1/permissoes/$id<[^/]+>', '[Permissão] - Editar Permissão', NOW(), NOW());
-- INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
-- -- INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);
-- SET @permAppId = UUID();
-- INSERT INTO `permissoes_app` (`id`, `chave`, `nome`, `data_criacao`, `data_atualizacao`) VALUES (@permAppId, 'editarPermissoes', '[Permissões] - Editar Permissões', NOW(), NOW());
-- INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@permAppId, @PermissaoId);

-- Set @PermissaoId = UUID();
-- INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/permissoes/$id<[^/]+>', '[Permissão] - Exibir Permissão', NOW(), NOW());
-- INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
-- -- INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);
-- SET @permAppId = UUID();
-- INSERT INTO `permissoes_app` (`id`, `chave`, `nome`, `data_criacao`, `data_atualizacao`) VALUES (@permAppId, 'visualizarPermissoes', '[Permissões] - Ver Detalhes de Permissões', NOW(), NOW());
-- INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@permAppId, @PermissaoId);

-- Set @PermissaoId = UUID();
-- INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'DELETE /api/v1/permissoes/$id<[^/]+>', '[Permissão] - Remover Permissão', NOW(), NOW());
-- INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
-- -- INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);
-- SET @permAppId = UUID();
-- INSERT INTO `permissoes_app` (`id`, `chave`, `nome`, `data_criacao`, `data_atualizacao`) VALUES (@permAppId, 'removerPermissoes', '[Permissões] - Remover Permissões', NOW(), NOW());
-- INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@permAppId, @PermissaoId);





-- # CRUD controladores

Set @PermissaoId = UUID();
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

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/controladores/mapas', '[Controladores] - Listar Controladores (Mapas)', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@verNoMapaId, @PermissaoId);

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/controladores/agrupamentos', '[Controladores] - Listar Controladores (Agrupamentos)', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@CriarAgrupamentoId, @PermissaoId);

Set @PermissaoId = UUID();
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
Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'DELETE /api/v1/controladores/$id<[^/]+>', '[Controladores] - Remover Controlador', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);


Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'POST /api/v1/controladores/$id<[^/]+>/edit', '[Controladores] - Editar Controlador Ativo', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@criarControladorId, @PermissaoId);

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/controladores/$id<[^/]+>/timeline', '[Controladores] - Visualizar Histórico de Controladores', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@visualizarControladorId, @PermissaoId);

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/controladores/$id<[^/]+>/pode_editar', '[Controladores] - Editar Controlador', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@criarControladorId, @PermissaoId);
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@criarPlanosId, @PermissaoId);
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@criarTabelaHorariaId, @PermissaoId);

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'PUT /api/v1/controladores/$id<[^/]+>/finalizar', '[Controladores] - Finalizar Programação do Controlador', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@criarControladorId, @PermissaoId);


-- TODO: verificar onde essa rota é usada
Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'PUT /api/v1/controladores/$id<[^/]+>/ativar', '[Controladores] - Ativar Controlador', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'DELETE /api/v1/controladores/$id<[^/]+>/cancelar_edicao', '[Controladores] - Cancelar Edição', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@criarControladorId, @PermissaoId);

-- TODO: verificar onde essa rota é usada
Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/controladores/$id<[^/]+>/editar_planos', '[Controladores] - Editar Planos', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);

-- TODO: verificar onde essa rota é usada
Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/controladores/$id<[^/]+>/editar_tabelas_horarias', '[Controladores] - Editar Tabela Horária', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);

-- TODO: verificar onde essa rota é usada
Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'PUT /api/v1/controladores/$id<[^/]+>/atualizar_descricao', '[Controladores] - Finalizar Controlador', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);




-- # Wizard Controladores

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'POST /api/v1/controladores/dados_basicos', '[Controladores] - Cadastrar Dados Básicos', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@criarControladorId, @PermissaoId);


Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'POST /api/v1/controladores/aneis', '[Controladores] - Cadastrar Anéis', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@criarControladorId, @PermissaoId);

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'POST /api/v1/controladores/grupos_semaforicos', '[Controladores] - Cadastrar Grupos Semafóricos', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@criarControladorId, @PermissaoId);

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'POST /api/v1/controladores/associacao_grupos_semaforicos', '[Controladores] - Associar Grupos Semafóricos', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@criarControladorId, @PermissaoId);

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'POST /api/v1/controladores/verdes_conflitantes', '[Controladores] - Cadastrar Verdes Conflitantes', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@criarControladorId, @PermissaoId);

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'POST /api/v1/controladores/transicoes_proibidas', '[Controladores] - Cadastrar Transições Proibidas', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@criarControladorId, @PermissaoId);

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'POST /api/v1/controladores/atraso_de_grupo', '[Controladores] - Cadastrar atraso de grupo', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@criarControladorId, @PermissaoId);

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'POST /api/v1/controladores/entre_verdes', '[Controladores] - Cadastrar Tabelas Entre-Verdes', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@criarControladorId, @PermissaoId);

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'POST /api/v1/controladores/associacao_detectores', '[Controladores] - Associar Detectores', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@criarControladorId, @PermissaoId);


Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/controladores/mapas', '[Controladores] - Ver Mapa', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'DELETE /api/v1/controladores/$id<[^/]+>/remover_planos_tabelas_horarios', '[Controladores] - Remover Planos e Tabelas Horarios do  Controlador', NOW(), NOW());
-- INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);



-- # Planos

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'POST /api/v1/planos', '[Controladores] - Visualizar Planos', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@criarPlanosId, @PermissaoId);

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/planos/$id<[^/]+>/timeline', '[Planos] - Visualizar Histórico de Planos', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@verPlanosId, @PermissaoId);
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@criarPlanosId, @PermissaoId);

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'DELETE /api/v1/planos/$id<[^/]+>', '[Planos] - Excluir Plano', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@criarPlanosId, @PermissaoId);

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'DELETE /api/v1/planos/$id<[^/]+>/cancelar_edicao', '[Planos] - Cancelar Edição de Plano', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@criarPlanosId, @PermissaoId);



-- #Tabela Horária

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'POST /api/v1/tabelas_horarias', '[Controladores] - Visualizar Tabela Horária', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@criarTabelaHorariaId, @PermissaoId);

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/tabelas_horarias/$id<[^/]+>/timeline', '[Tabela Horária] - Visualizar Histórico de Tabela Horária', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@verTabelaHorariaId, @PermissaoId);
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@criarTabelaHorariaId, @PermissaoId);

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'DELETE /api/v1/tabelas_horarias/$id<[^/]+>/cancelar_edicao', '[Tabela Horária] - Cancelar edição', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@criarTabelaHorariaId, @PermissaoId);


-- #Exclusão

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'DELETE /api/v1/estagios/$id<[^/]+>', '[Estágios] - Excluir Estágio', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@criarControladorId, @PermissaoId);

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'DELETE /api/v1/detectores/$id<[^/]+>', '[Detectores] - Excluir Detector', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@criarControladorId, @PermissaoId);

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'DELETE /api/v1/eventos/$id<[^/]+>', '[Eventos] - Excluir Evento', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@criarTabelaHorariaId, @PermissaoId);


-- # Helpers

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/helpers/controlador', '[Controlador] - Exibir tela de Cadastro de Controladores', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@visualizarControladorId, @PermissaoId);
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@criarControladorId, @PermissaoId);
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@verPlanosId, @PermissaoId);
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@criarPlanosId, @PermissaoId);



-- # Auditorias

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/auditorias', '[Auditoria] - Listar Auditorias', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);
SET @permAppId = UUID();
INSERT INTO `permissoes_app` (`id`, `chave`, `nome`, `data_criacao`, `data_atualizacao`) VALUES (@permAppId, 'listarAuditorias', '[Auditoria] - Listar Auditorias', NOW(), NOW());
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@permAppId, @PermissaoId);

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/auditorias/$id<[^/]+>', '[Auditoria] - Visualizar Auditoria', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);
SET @permAppId = UUID();
INSERT INTO `permissoes_app` (`id`, `chave`, `nome`, `data_criacao`, `data_atualizacao`) VALUES (@permAppId, 'visualizarAuditorias', '[Auditoria] - Ver Detalhes de Auditorias', NOW(), NOW());
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@permAppId, @PermissaoId);



-- # Faixas de Valores

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/faixas_de_valores', '[Faixas de Valores] - Visualizar Faixas de Valores', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
-- INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);
SET @permAppId = UUID();
INSERT INTO `permissoes_app` (`id`, `chave`, `nome`, `data_criacao`, `data_atualizacao`) VALUES (@permAppId, 'visualizarFaixasDeValores', '[Faixas de Valores] - Ver Faixas de Valores', NOW(), NOW());
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@permAppId, @PermissaoId);

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'PUT /api/v1/faixas_de_valores', '[Faixas de Valores] - Editar Faixas de Valores', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
-- INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);
SET @permAppId = UUID();
INSERT INTO `permissoes_app` (`id`, `chave`, `nome`, `data_criacao`, `data_atualizacao`) VALUES (@permAppId, 'editarFaixasDeValores', '[Faixas de Valores] - Editar Faixas de Valores', NOW(), NOW());
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@permAppId, @PermissaoId);



-- # ModoOperacao Controlador

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/monitoramento/modo_operacoes_controladores/$id<[^/]+>', '[Monitoramento] - Visualizar Modo de Operação do Controlador', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/monitoramento/modo_operacoes_controladores', '[Monitoramento] - Visualizar Modos de Operação dos Controladores', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/monitoramento/modo_operacoes_controladores/$id<[^/]+>/ultimo_status', '[Monitoramento] - Visualizar Último Modo de Operação de Controlador', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/monitoramento/modo_operacoes_controladores/$id<[^/]+>/historico/$pagina<[^/]+>/$tamanho<[^/]+>', '[Monitoramento] - Visualizar Último Modo de Operação de Controlador com Paginação', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);



-- # Erros Controladores

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/monitoramento/erros_controladores/$id<[^/]+>', '[Monitoramento] - Visualizar Erros do Controlador', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/monitoramento/erros_controladores', '[Monitoramento] - Visualizar Erros dos Controladores', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/monitoramento/erros_controladores/$id<[^/]+>/ultimo_status', '[Monitoramento] - Visualizar Último Erros de Controlador', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/monitoramento/erros_controladores/$id<[^/]+>/historico/$pagina<[^/]+>/$tamanho<[^/]+>', '[Monitoramento] - Visualizar Último Erros de Controlador com Paginação', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);



-- # ImposicaoPlanos Controlador

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/monitoramento/imposicoes_planos/$id<[^/]+>', '[Monitoramento] - Visualizar Imposição de Plano do Controlador', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/monitoramento/imposicoes_planos', '[Monitoramento] - Visualizar Imposição de Planos dos Controladores', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/monitoramento/imposicoes_planos/$id<[^/]+>/ultimo_status', '[Monitoramento] - Visualizar Última Imposição de Plano de Controlador', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/monitoramento/imposicoes_planos/$id<[^/]+>/historico/$pagina<[^/]+>/$tamanho<[^/]+>', '[Monitoramento] - Visualizar Última Imposição de Plano de Controlador com Paginação', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);



-- # Monitoramento

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/monitoramento/status_controladores', '[Monitoramento] - Visualizar Status dos Controladores', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);
SET @permAppId = UUID();
INSERT INTO `permissoes_app` (`id`, `chave`, `nome`, `data_criacao`, `data_atualizacao`) VALUES (@permAppId, 'visualizarStatusControladores', '[Dashboard] - Ver Status dos Controladores', NOW(), NOW());
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@permAppId, @PermissaoId);

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/monitoramento/controladores_onlines', '[Monitoramento] - Visualizar Status dos Controladores Online', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);
SET @permAppId = UUID();
INSERT INTO `permissoes_app` (`id`, `chave`, `nome`, `data_criacao`, `data_atualizacao`) VALUES (@permAppId, 'visualizarStatusControladoresOnline', '[Dashboard] - Ver Controladores Online', NOW(), NOW());
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@permAppId, @PermissaoId);

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/monitoramento/controladores_offlines', '[Monitoramento] - Visualizar Status dos Controladores Offline', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);
SET @permAppId = UUID();
INSERT INTO `permissoes_app` (`id`, `chave`, `nome`, `data_criacao`, `data_atualizacao`) VALUES (@permAppId, 'visualizarStatusControladoresOffline', '[Dashboard] - Ver Controladores Offline', NOW(), NOW());
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@permAppId, @PermissaoId);

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/monitoramento/detalhe_controlador/$id<[^/]+>', '[Monitoramento] - Visualizar Detalhes de Controlador', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);
SET @permAppId = UUID();
INSERT INTO `permissoes_app` (`id`, `chave`, `nome`, `data_criacao`, `data_atualizacao`) VALUES (@permAppId, 'visualizarDetalhesControlador', '[Dashboard] - Ver Detalhes dos Controladores', NOW(), NOW());
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@permAppId, @PermissaoId);



-- # StatusDevice Conexão

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/monitoramento/status_conexao/$id<[^/]+>', '[Monitoramento] - Visualizar Status da Conexão de Controlador', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/monitoramento/status_conexao', '[Monitoramento] - Visualizar Status da Conexão de Controladores', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/monitoramento/status_conexao/$id<[^/]+>/ultimo_status', '[Monitoramento] - Visualizar Último Status da Conexão de Controlador', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/monitoramento/status_conexao/$id<[^/]+>/historico/$pagina<[^/]+>/$tamanho<[^/]+>', '[Monitoramento] - Visualizar Último Status da Conexão de Controlador com Paginação', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);



-- # StatusDevice Status

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/monitoramento/status/$id<[^/]+>', '[Monitoramento] - Visualizar Status de Controlador', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/monitoramento/status', '[Monitoramento] - Visualizar Status de Controladores', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/monitoramento/status/$id<[^/]+>/ultimo_status', '[Monitoramento] - Visualizar Último Status de Controlador', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/monitoramento/status/$id<[^/]+>/historico/$pagina<[^/]+>/$tamanho<[^/]+>', '[Monitoramento] - Visualizar Último Status de Controlador com Paginação', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilEngenheiroId, @PermissaoId);

-- # Permissao visualizar todas as Areas

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'visualizarTodasAreas', '[Administração] - Visualizar todas as Areas', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
SET @permAppId = UUID();
INSERT INTO `permissoes_app` (`id`, `chave`, `nome`, `data_criacao`, `data_atualizacao`) VALUES (@permAppId, 'visualizarTodasAreas', '[Administração] - Visualizar Todas as Áreas', NOW(), NOW());
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@permAppId, @PermissaoId);


-- # Permissao Relatorios

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/relatorios/auditoria', '[Relatórios] - Relatório de Auditoria', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
SET @permAppId = UUID();
INSERT INTO `permissoes_app` (`id`, `chave`, `nome`, `data_criacao`, `data_atualizacao`) VALUES (@permAppId, 'gerarRelatorioAuditorias', '[Relatórios] - Gerar Relatórios Auditoria', NOW(), NOW());
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@permAppId, @PermissaoId);

Set @PermissaoId = UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, 'GET /api/v1/relatorios/controladores_status', '[Relatórios] - Relatório de Controladores por Status', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
SET @permAppId = UUID();
INSERT INTO `permissoes_app` (`id`, `chave`, `nome`, `data_criacao`, `data_atualizacao`) VALUES (@permAppId, 'gerarRelatorioControladoresStatus', '[Relatórios] - Gerar Relatórios Controladores por Status', NOW(), NOW());
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@permAppId, @PermissaoId);

COMMIT;
