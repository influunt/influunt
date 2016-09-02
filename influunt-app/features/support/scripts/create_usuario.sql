DELETE FROM `sessoes`;
DELETE FROM `versoes_controladores`;
DELETE FROM `usuarios`;
INSERT INTO `usuarios` (`id`, `login`, `senha`, `email`, `nome`, `root`, `data_criacao`, `data_atualizacao`) VALUES
                       ('2f0e0547-3135-428b-8f6d-0a1098eca0a5', 'root', '$2a$10$EzudGIqkxquJjLGawuMrOu9K6S28yc/R/YSAVxsvb5bSryOYWd5eq', 'root@influunt.com.br', 'Administrador Geral', 'true', CURRENT_TIME(), CURRENT_TIME());
