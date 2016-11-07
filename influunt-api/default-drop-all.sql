alter table agrupamentos_aneis drop foreign key fk_agrupamentos_aneis_agrupamentos;
drop index ix_agrupamentos_aneis_agrupamentos on agrupamentos_aneis;

alter table agrupamentos_aneis drop foreign key fk_agrupamentos_aneis_aneis;
drop index ix_agrupamentos_aneis_aneis on agrupamentos_aneis;

alter table aneis drop foreign key fk_aneis_controlador_id;
drop index ix_aneis_controlador_id on aneis;

alter table aneis drop foreign key fk_aneis_croqui_id;

alter table areas drop foreign key fk_areas_cidade_id;
drop index ix_areas_cidade_id on areas;

alter table atrasos_de_grupos drop foreign key fk_atrasos_de_grupos_transicao_id;

alter table controladores drop foreign key fk_controladores_croqui_id;

alter table controladores drop foreign key fk_controladores_modelo_id;
drop index ix_controladores_modelo_id on controladores;

alter table controladores drop foreign key fk_controladores_area_id;
drop index ix_controladores_area_id on controladores;

alter table controladores drop foreign key fk_controladores_subarea_id;
drop index ix_controladores_subarea_id on controladores;

alter table controladores_fisicos drop foreign key fk_controladores_fisicos_area_id;
drop index ix_controladores_fisicos_area_id on controladores_fisicos;

alter table detectores drop foreign key fk_detectores_anel_id;
drop index ix_detectores_anel_id on detectores;

alter table detectores drop foreign key fk_detectores_estagio_id;

alter table detectores drop foreign key fk_detectores_controlador_id;
drop index ix_detectores_controlador_id on detectores;

alter table enderecos drop foreign key fk_enderecos_controlador_id;

alter table enderecos drop foreign key fk_enderecos_anel_id;

alter table estagios drop foreign key fk_estagios_imagem_id;

alter table estagios drop foreign key fk_estagios_anel_id;
drop index ix_estagios_anel_id on estagios;

alter table estagios drop foreign key fk_estagios_controlador_id;
drop index ix_estagios_controlador_id on estagios;

alter table estagios_grupos_semaforicos drop foreign key fk_estagios_grupos_semaforicos_estagio_id;
drop index ix_estagios_grupos_semaforicos_estagio_id on estagios_grupos_semaforicos;

alter table estagios_grupos_semaforicos drop foreign key fk_estagios_grupos_semaforicos_grupo_semaforico_id;
drop index ix_estagios_grupos_semaforicos_grupo_semaforico_id on estagios_grupos_semaforicos;

alter table estagios_planos drop foreign key fk_estagios_planos_estagio_id;
drop index ix_estagios_planos_estagio_id on estagios_planos;

alter table estagios_planos drop foreign key fk_estagios_planos_plano_id;
drop index ix_estagios_planos_plano_id on estagios_planos;

alter table estagios_planos drop foreign key fk_estagios_planos_estagio_que_recebe_estagio_dispensavel_3;
drop index ix_estagios_planos_estagio_que_recebe_estagio_dispensavel_3 on estagios_planos;

alter table eventos drop foreign key fk_eventos_tabela_horario_id;
drop index ix_eventos_tabela_horario_id on eventos;

alter table eventos drop foreign key fk_eventos_agrupamento_id;
drop index ix_eventos_agrupamento_id on eventos;

alter table grupos_semaforicos drop foreign key fk_grupos_semaforicos_anel_id;
drop index ix_grupos_semaforicos_anel_id on grupos_semaforicos;

alter table grupos_semaforicos drop foreign key fk_grupos_semaforicos_controlador_id;
drop index ix_grupos_semaforicos_controlador_id on grupos_semaforicos;

alter table grupos_semaforicos_planos drop foreign key fk_grupos_semaforicos_planos_grupo_semaforico_id;
drop index ix_grupos_semaforicos_planos_grupo_semaforico_id on grupos_semaforicos_planos;

alter table grupos_semaforicos_planos drop foreign key fk_grupos_semaforicos_planos_plano_id;
drop index ix_grupos_semaforicos_planos_plano_id on grupos_semaforicos_planos;

alter table limite_area drop foreign key fk_limite_area_area_id;
drop index ix_limite_area_area_id on limite_area;

alter table modelo_controladores drop foreign key fk_modelo_controladores_fabricante_id;
drop index ix_modelo_controladores_fabricante_id on modelo_controladores;

alter table permissoes_perfis drop foreign key fk_permissoes_perfis_perfis;
drop index ix_permissoes_perfis_perfis on permissoes_perfis;

alter table permissoes_perfis drop foreign key fk_permissoes_perfis_permissoes;
drop index ix_permissoes_perfis_permissoes on permissoes_perfis;

alter table permissoes_app_permissoes drop foreign key fk_permissoes_app_permissoes_permissoes_app;
drop index ix_permissoes_app_permissoes_permissoes_app on permissoes_app_permissoes;

alter table permissoes_app_permissoes drop foreign key fk_permissoes_app_permissoes_permissoes;
drop index ix_permissoes_app_permissoes_permissoes on permissoes_app_permissoes;

alter table planos drop foreign key fk_planos_versao_plano_id;
drop index ix_planos_versao_plano_id on planos;

alter table sessoes drop foreign key fk_sessoes_usuario_id;
drop index ix_sessoes_usuario_id on sessoes;

alter table subareas drop foreign key fk_subareas_area_id;
drop index ix_subareas_area_id on subareas;

alter table tabela_entre_verdes drop foreign key fk_tabela_entre_verdes_grupo_semaforico_id;
drop index ix_tabela_entre_verdes_grupo_semaforico_id on tabela_entre_verdes;

alter table tabela_entre_verdes_transicao drop foreign key fk_tabela_entre_verdes_transicao_tabela_entre_verdes_id;
drop index ix_tabela_entre_verdes_transicao_tabela_entre_verdes_id on tabela_entre_verdes_transicao;

alter table tabela_entre_verdes_transicao drop foreign key fk_tabela_entre_verdes_transicao_transicao_id;
drop index ix_tabela_entre_verdes_transicao_transicao_id on tabela_entre_verdes_transicao;

alter table tabela_horarios drop foreign key fk_tabela_horarios_versao_tabela_horaria_id;

alter table transicoes drop foreign key fk_transicoes_grupo_semaforico_id;
drop index ix_transicoes_grupo_semaforico_id on transicoes;

alter table transicoes drop foreign key fk_transicoes_origem_id;
drop index ix_transicoes_origem_id on transicoes;

alter table transicoes drop foreign key fk_transicoes_destino_id;
drop index ix_transicoes_destino_id on transicoes;

alter table transicoes_proibidas drop foreign key fk_transicoes_proibidas_origem_id;
drop index ix_transicoes_proibidas_origem_id on transicoes_proibidas;

alter table transicoes_proibidas drop foreign key fk_transicoes_proibidas_destino_id;
drop index ix_transicoes_proibidas_destino_id on transicoes_proibidas;

alter table transicoes_proibidas drop foreign key fk_transicoes_proibidas_alternativo_id;
drop index ix_transicoes_proibidas_alternativo_id on transicoes_proibidas;

alter table usuarios drop foreign key fk_usuarios_area_id;
drop index ix_usuarios_area_id on usuarios;

alter table usuarios drop foreign key fk_usuarios_perfil_id;
drop index ix_usuarios_perfil_id on usuarios;

alter table verdes_conflitantes drop foreign key fk_verdes_conflitantes_origem_id;
drop index ix_verdes_conflitantes_origem_id on verdes_conflitantes;

alter table verdes_conflitantes drop foreign key fk_verdes_conflitantes_destino_id;
drop index ix_verdes_conflitantes_destino_id on verdes_conflitantes;

alter table versoes_controladores drop foreign key fk_versoes_controladores_controlador_origem_id;

alter table versoes_controladores drop foreign key fk_versoes_controladores_controlador_id;

alter table versoes_controladores drop foreign key fk_versoes_controladores_controlador_fisico_id;
drop index ix_versoes_controladores_controlador_fisico_id on versoes_controladores;

alter table versoes_controladores drop foreign key fk_versoes_controladores_usuario_id;
drop index ix_versoes_controladores_usuario_id on versoes_controladores;

alter table versoes_planos drop foreign key fk_versoes_planos_versao_anterior_id;

alter table versoes_planos drop foreign key fk_versoes_planos_anel_id;
drop index ix_versoes_planos_anel_id on versoes_planos;

alter table versoes_planos drop foreign key fk_versoes_planos_usuario_id;
drop index ix_versoes_planos_usuario_id on versoes_planos;

alter table versoes_tabelas_horarias drop foreign key fk_versoes_tabelas_horarias_controlador_id;
drop index ix_versoes_tabelas_horarias_controlador_id on versoes_tabelas_horarias;

alter table versoes_tabelas_horarias drop foreign key fk_versoes_tabelas_horarias_tabela_horaria_origem_id;

alter table versoes_tabelas_horarias drop foreign key fk_versoes_tabelas_horarias_usuario_id;
drop index ix_versoes_tabelas_horarias_usuario_id on versoes_tabelas_horarias;

drop table if exists agrupamentos;

drop table if exists agrupamentos_aneis;

drop table if exists aneis;

drop table if exists areas;

drop table if exists atrasos_de_grupos;

drop table if exists cidades;

drop table if exists controladores;

drop table if exists controladores_fisicos;

drop table if exists detectores;

drop table if exists enderecos;

drop table if exists estagios;

drop table if exists estagios_grupos_semaforicos;

drop table if exists estagios_planos;

drop table if exists eventos;

drop table if exists fabricantes;

drop table if exists faixas_de_valores;

drop table if exists grupos_semaforicos;

drop table if exists grupos_semaforicos_planos;

drop table if exists imagens;

drop table if exists limite_area;

drop table if exists modelo_controladores;

drop table if exists perfis;

drop table if exists permissoes_perfis;

drop table if exists permissoes;

drop table if exists permissoes_app;

drop table if exists permissoes_app_permissoes;

drop table if exists planos;

drop table if exists sessoes;

drop table if exists subareas;

drop table if exists tabela_entre_verdes;

drop table if exists tabela_entre_verdes_transicao;

drop table if exists tabela_horarios;

drop table if exists transicoes;

drop table if exists transicoes_proibidas;

drop table if exists usuarios;

drop table if exists verdes_conflitantes;

drop table if exists versoes_controladores;

drop table if exists versoes_planos;

drop table if exists versoes_tabelas_horarias;

