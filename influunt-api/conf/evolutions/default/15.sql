# --- !Ups
update permissoes set chave = 'DELETE /api/v1/controladores/$id<[^/]+>/remover_planos_tabelas_horarias' where chave = 'DELETE /api/v1/controladores/$id<[^/]+>/remover_planos_tabelas_horarios'


# --- !Downs
update permissoes set chave = 'DELETE /api/v1/controladores/$id<[^/]+>/remover_planos_tabelas_horarios' where chave = 'DELETE /api/v1/controladores/$id<[^/]+>/remover_planos_tabelas_horarias'