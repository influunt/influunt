DELETE FROM `agrupamentos`;

-- Esse agrupamento tem dependência com controlaor, antes de cria-lo é devce ser chamando o controlador.sql
SET @AgrupamentoId = RANDOM_UUID();

INSERT INTO `agrupamentos` (`id`, `nome`, `tipo`, `numero`, `descricao`, `data_criacao`, `data_atualizacao`, `posicao_plano`, `dia_da_semana`, `horario`) VALUES
(@AgrupamentoId, 'Corredor da Paulista', 'CORREDOR', '2000', 'descricao1', NOW(), NOW(), '1', 'SEGUNDA', NOW());
INSERT INTO `agrupamentos_aneis` (`agrupamento_id`, `anel_id`) VALUES
(@AgrupamentoId, '287415c8-1d96-4ab1-a04f-377b19cd9238');
