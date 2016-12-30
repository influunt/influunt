SET @AreaId = (SELECT id FROM "areas" where descricao = '1');
SET @ControladorId = (SELECT id FROM "controladores" where nome_endereco = 'Av. Paulista com R. Bela Cintra');

SET @SubareaId   = RANDOM_UUID();
INSERT INTO `subareas` (`id`, `id_json`, `nome`, `numero`, `area_id`, `data_criacao`, `data_atualizacao`)
VALUES
  (@SubareaId,RANDOM_UUID(),'AREA SUL PAULISTA',3,@AreaId,NOW(),NOW());

update controladores set subarea_id = @SubareaId where id = @ControladorId;
