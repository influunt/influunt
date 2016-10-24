-- Seta a área 2 para o usuário mobilab

SET @areaId = (SELECT id FROM areas WHERE descricao = 2);

UPDATE usuarios SET area_id=@areaID WHERE login='mobilab';
