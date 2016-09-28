alter table agrupamentos_aneis drop constraint if exists fk_agrupamentos_aneis_agrupamentos;
drop index if exists ix_agrupamentos_aneis_agrupamentos;

alter table agrupamentos_aneis drop constraint if exists fk_agrupamentos_aneis_aneis;
drop index if exists ix_agrupamentos_aneis_aneis;

alter table aneis drop constraint if exists fk_aneis_controlador_id;
drop index if exists ix_aneis_controlador_id;

alter table aneis drop constraint if exists fk_aneis_croqui_id;

alter table areas drop constraint if exists fk_areas_cidade_id;
drop index if exists ix_areas_cidade_id;

alter table atrasos_de_grupos drop constraint if exists fk_atrasos_de_grupos_transicao_id;

alter table controladores drop constraint if exists fk_controladores_modelo_id;
drop index if exists ix_controladores_modelo_id;

alter table controladores drop constraint if exists fk_controladores_area_id;
drop index if exists ix_controladores_area_id;

alter table controladores drop constraint if exists fk_controladores_subarea_id;
drop index if exists ix_controladores_subarea_id;

alter table controladores_fisicos drop constraint if exists fk_controladores_fisicos_area_id;
drop index if exists ix_controladores_fisicos_area_id;

alter table detectores drop constraint if exists fk_detectores_anel_id;
drop index if exists ix_detectores_anel_id;

alter table detectores drop constraint if exists fk_detectores_estagio_id;

alter table detectores drop constraint if exists fk_detectores_controlador_id;
drop index if exists ix_detectores_controlador_id;

alter table enderecos drop constraint if exists fk_enderecos_controlador_id;

alter table enderecos drop constraint if exists fk_enderecos_anel_id;

alter table estagios drop constraint if exists fk_estagios_imagem_id;

alter table estagios drop constraint if exists fk_estagios_anel_id;
drop index if exists ix_estagios_anel_id;

alter table estagios drop constraint if exists fk_estagios_controlador_id;
drop index if exists ix_estagios_controlador_id;

alter table estagios_grupos_semaforicos drop constraint if exists fk_estagios_grupos_semaforicos_estagio_id;
drop index if exists ix_estagios_grupos_semaforicos_estagio_id;

alter table estagios_grupos_semaforicos drop constraint if exists fk_estagios_grupos_semaforicos_grupo_semaforico_id;
drop index if exists ix_estagios_grupos_semaforicos_grupo_semaforico_id;

alter table estagios_planos drop constraint if exists fk_estagios_planos_estagio_id;
drop index if exists ix_estagios_planos_estagio_id;

alter table estagios_planos drop constraint if exists fk_estagios_planos_plano_id;
drop index if exists ix_estagios_planos_plano_id;

alter table estagios_planos drop constraint if exists fk_estagios_planos_estagio_que_recebe_estagio_dispensavel_3;
drop index if exists ix_estagios_planos_estagio_que_recebe_estagio_dispensavel_3;

alter table eventos drop constraint if exists fk_eventos_tabela_horario_id;
drop index if exists ix_eventos_tabela_horario_id;

alter table grupos_semaforicos drop constraint if exists fk_grupos_semaforicos_anel_id;
drop index if exists ix_grupos_semaforicos_anel_id;

alter table grupos_semaforicos drop constraint if exists fk_grupos_semaforicos_controlador_id;
drop index if exists ix_grupos_semaforicos_controlador_id;

alter table grupos_semaforicos_planos drop constraint if exists fk_grupos_semaforicos_planos_grupo_semaforico_id;
drop index if exists ix_grupos_semaforicos_planos_grupo_semaforico_id;

alter table grupos_semaforicos_planos drop constraint if exists fk_grupos_semaforicos_planos_plano_id;
drop index if exists ix_grupos_semaforicos_planos_plano_id;

alter table intervalos drop constraint if exists fk_intervalos_grupo_semaforico_plano_id;
drop index if exists ix_intervalos_grupo_semaforico_plano_id;

alter table limite_area drop constraint if exists fk_limite_area_area_id;
drop index if exists ix_limite_area_area_id;

alter table modelo_controladores drop constraint if exists fk_modelo_controladores_fabricante_id;
drop index if exists ix_modelo_controladores_fabricante_id;

alter table permissoes_perfis drop constraint if exists fk_permissoes_perfis_perfis;
drop index if exists ix_permissoes_perfis_perfis;

alter table permissoes_perfis drop constraint if exists fk_permissoes_perfis_permissoes;
drop index if exists ix_permissoes_perfis_permissoes;

alter table planos drop constraint if exists fk_planos_versao_plano_id;
drop index if exists ix_planos_versao_plano_id;

alter table sessoes drop constraint if exists fk_sessoes_usuario_id;
drop index if exists ix_sessoes_usuario_id;

alter table subareas drop constraint if exists fk_subareas_area_id;
drop index if exists ix_subareas_area_id;

alter table tabela_entre_verdes drop constraint if exists fk_tabela_entre_verdes_grupo_semaforico_id;
drop index if exists ix_tabela_entre_verdes_grupo_semaforico_id;

alter table tabela_entre_verdes_transicao drop constraint if exists fk_tabela_entre_verdes_transicao_tabela_entre_verdes_id;
drop index if exists ix_tabela_entre_verdes_transicao_tabela_entre_verdes_id;

alter table tabela_entre_verdes_transicao drop constraint if exists fk_tabela_entre_verdes_transicao_transicao_id;
drop index if exists ix_tabela_entre_verdes_transicao_transicao_id;

alter table tabela_horarios drop constraint if exists fk_tabela_horarios_versao_tabela_horaria_id;

alter table transicoes drop constraint if exists fk_transicoes_grupo_semaforico_id;
drop index if exists ix_transicoes_grupo_semaforico_id;

alter table transicoes drop constraint if exists fk_transicoes_origem_id;
drop index if exists ix_transicoes_origem_id;

alter table transicoes drop constraint if exists fk_transicoes_destino_id;
drop index if exists ix_transicoes_destino_id;

alter table transicoes_proibidas drop constraint if exists fk_transicoes_proibidas_origem_id;
drop index if exists ix_transicoes_proibidas_origem_id;

alter table transicoes_proibidas drop constraint if exists fk_transicoes_proibidas_destino_id;
drop index if exists ix_transicoes_proibidas_destino_id;

alter table transicoes_proibidas drop constraint if exists fk_transicoes_proibidas_alternativo_id;
drop index if exists ix_transicoes_proibidas_alternativo_id;

alter table usuarios drop constraint if exists fk_usuarios_area_id;
drop index if exists ix_usuarios_area_id;

alter table usuarios drop constraint if exists fk_usuarios_perfil_id;
drop index if exists ix_usuarios_perfil_id;

alter table verdes_conflitantes drop constraint if exists fk_verdes_conflitantes_origem_id;
drop index if exists ix_verdes_conflitantes_origem_id;

alter table verdes_conflitantes drop constraint if exists fk_verdes_conflitantes_destino_id;
drop index if exists ix_verdes_conflitantes_destino_id;

alter table versoes_controladores drop constraint if exists fk_versoes_controladores_controlador_origem_id;

alter table versoes_controladores drop constraint if exists fk_versoes_controladores_controlador_id;

alter table versoes_controladores drop constraint if exists fk_versoes_controladores_controlador_fisico_id;
drop index if exists ix_versoes_controladores_controlador_fisico_id;

alter table versoes_controladores drop constraint if exists fk_versoes_controladores_usuario_id;
drop index if exists ix_versoes_controladores_usuario_id;

alter table versoes_planos drop constraint if exists fk_versoes_planos_versao_anterior_id;

alter table versoes_planos drop constraint if exists fk_versoes_planos_anel_id;
drop index if exists ix_versoes_planos_anel_id;

alter table versoes_planos drop constraint if exists fk_versoes_planos_usuario_id;
drop index if exists ix_versoes_planos_usuario_id;

alter table versoes_tabelas_horarias drop constraint if exists fk_versoes_tabelas_horarias_controlador_id;
drop index if exists ix_versoes_tabelas_horarias_controlador_id;

alter table versoes_tabelas_horarias drop constraint if exists fk_versoes_tabelas_horarias_tabela_horaria_origem_id;

alter table versoes_tabelas_horarias drop constraint if exists fk_versoes_tabelas_horarias_usuario_id;
drop index if exists ix_versoes_tabelas_horarias_usuario_id;

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

drop table if exists intervalos;

drop table if exists limite_area;

drop table if exists modelo_controladores;

drop table if exists perfis;

drop table if exists permissoes_perfis;

drop table if exists permissoes;

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

