SELECT @PerfilAdministradorId = id FROM perfis WHERE nome = 'Administrador';

Set @PermissaoId = RANDOM_UUID();
SET @DefinirPermissoesId = RANDOM_UUID();
INSERT INTO `permissoes_app` (`id`, `chave`, `nome`, `descricao`, `data_criacao`, `data_atualizacao`) VALUES
  (@DefinirPermissoesId, 'definirPermissoes', '[Perfis] - Definir as permissões dos perfis', 'O usuário com essa permissão pode definir as permissões dos perfis cadastrados no sistema. Se o usuário não tiver essa permissão o botão "Definir Permissões" é escondido.', NOW(), NOW());
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@DefinirPermissoesId, @PermissaoId);
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);

Set @PermissaoId = RANDOM_UUID();
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (@PermissaoId, ' ', '[Administração] - Visualizar todas as Areas', NOW(), NOW());
INSERT INTO `permissoes_perfis` (`perfil_id`, `permissao_id`) VALUES (@PerfilAdministradorId, @PermissaoId);
SET @permAppId = RANDOM_UUID();
INSERT INTO `permissoes_app` (`id`, `chave`, `nome`, `descricao`, `data_criacao`, `data_atualizacao`) VALUES
  (@permAppId, 'visualizarTodasAreas', '[Administração] - Visualizar Todas as Áreas', 'O usuário com essa permissão pode ver os dados relativos a todas as áreas cadastradas. Sem essa permissão o usuário somente vê os dados associados com a sua área.', NOW(), NOW());
INSERT INTO `permissoes_app_permissoes` (`permissao_app_id`, `permissao_id`) VALUES (@permAppId, @PermissaoId);
