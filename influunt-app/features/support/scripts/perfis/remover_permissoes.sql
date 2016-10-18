SET @PermissaoVisualizarAreasId = (SELECT id FROM "permissoes" where chave = 'visualizarTodasAreas');
SET @PermissaoDefinirPerfis = (SELECT id FROM "permissoes_app" where chave = 'definirPermissoes');

DELETE FROM permissoes_perfis where permissao_id = @PermissaoVisualizarAreasId;

-- Remove permissao para defenir perfis
DELETE FROM permissoes_app_permissoes where permissao_app_id = @PermissaoDefinirPerfis;
DELETE FROM permissoes_app where id = @PermissaoDefinirPerfis;
