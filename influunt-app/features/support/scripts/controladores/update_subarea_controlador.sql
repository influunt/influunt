SET @AreaId = (SELECT id FROM "areas" where descricao = '3');

SET @ControladorId = (SELECT id FROM "controladores" where nome_endereco = 'Avenida Nove de Julho com Av. Paulista');

SET @SubareaId = (SELECT id FROM "subareas" where numero = '3');

update controladores set subarea_id = @SubareaId where id = @ControladorId;
