'use strict';

describe('Service: PermissionsService', function () {

  var PermissionsService,
      $httpBackend,
      PermPermissionStore,
      PermRoleStore,
      permissoesResponse = {
        permissoes: [
          { chave: 'perm1' },
          { chave: 'perm2' },
          { chave: 'perm3' },
          { chave: 'visualizarTodasAreas' }
        ],
        permissoesApp: [
          { chave: 'permApp1', permissoes: [{ chave: 'perm1' }] },
          { chave: 'permApp2', permissoes: [{ chave: 'perm2' }, { chave: 'perm3' }] },
          { chave: 'visualizarTodasAreas', permissoes: [{ chave: 'visualizarTodasAreas' }] }
        ]
      };

  beforeEach(inject(function (_PermissionsService_, _$httpBackend_, _PermPermissionStore_, _PermRoleStore_) {
    PermissionsService = _PermissionsService_;
    $httpBackend = _$httpBackend_;
    PermPermissionStore = _PermPermissionStore_;
    PermRoleStore = _PermRoleStore_;
  }));

  describe('getPermissions()', function () {
    it('deve fazer requisição para buscar todos as permissões', function() {
      $httpBackend.expectGET('/permissoes/roles');
      PermissionsService.getPermissions();
    });
  });


  describe('loadPermissions()', function () {
    beforeEach(function () {
      $httpBackend.expectGET('/permissoes/roles').respond(permissoesResponse);
    });

    it('carrega todas as permissões API e APP no angular-permission', function() {
      PermissionsService.loadPermissions().then(function() {
        expect(PermissionsService.getPermissions).toHaveBeenCalled();

        var permissions = PermPermissionStore.getStore();
        expect(Object.keys(permissions).length).toBe(4);

        var roles = PermRoleStore.getStore();
        expect(Object.keys(roles).length).toBe(3);
      });
    });

    it('atualiza os dados do usuário logado', function() {
      var usuario = {
        id: 'teste',
        login: 'teste',
        email: 'teste@example.com',
        root: true,
        permissoes: []
      }
      localStorage.setItem('usuario', JSON.stringify(usuario));

      var usuarioLocal = JSON.parse(localStorage.usuario);
      expect(usuarioLocal.id).toBe('teste');
      expect(usuarioLocal.login).toBe('teste');
      expect(usuarioLocal.email).toBe('teste@example.com');
      expect(usuarioLocal.root).toBe(true);
      expect(usuarioLocal.permissoes.length).toBe(0);

      usuario.id = 'novo teste';
      usuario.login = 'login';
      usuario.email = 'novo_teste@example.com';
      usuario.root = false;
      usuario.permissoes = ['permissão1'];
      localStorage.setItem('usuario', JSON.stringify(usuario));


      PermissionsService.loadPermissions().then(function() {
        usuarioLocal = JSON.parse(localStorage.usuario);
        expect(usuarioLocal.id).toBe('novo teste');
        expect(usuarioLocal.login).toBe('login');
        expect(usuarioLocal.email).toBe('novo_teste@example.com');
        expect(usuarioLocal.root).toBe(false);
        expect(usuarioLocal.permissoes.length).toBe(1);
        expect(usuarioLocal.permissoes[0]).toBe('permissão1');
      });
    });
  });

  describe('podeVisualizarTodasAreas()', function () {
    beforeEach(function () {
      $httpBackend.expectGET('/permissoes/roles').respond(permissoesResponse);
      this.usuario = {
        id: 'teste',
        login: 'teste',
        email: 'teste@example.com',
        root: false,
        permissoes: ['visualizarTodasAreas']
      }
    });

    it('permite visualizar se usuário tiver a permissão \'visualizarTodasAreas\'', function() {
      localStorage.setItem('usuario', JSON.stringify(this.usuario));

      PermissionsService.loadPermissions().then(function() {
        expect(PermissionsService.podeVisualizarTodasAreas()).toBe(true);
      });
    });

    it('permite visualizar se usuário for root', function() {
      this.usuario.root = true;
      this.usuario.permissions = [];
      localStorage.setItem('usuario', JSON.stringify(this.usuario));

      PermissionsService.loadPermissions().then(function() {
        expect(PermissionsService.podeVisualizarTodasAreas()).toBe(true);
      });
    });

    it('não permite visualizar se usuário não for root nem tiver a permissão \'visualizarTodasAreas\'', function() {
      this.usuario.permissions = [];
      localStorage.setItem('usuario', JSON.stringify(this.usuario));

      PermissionsService.loadPermissions().then(function() {
        expect(PermissionsService.podeVisualizarTodasAreas()).toBe(false);
      });
    });
  });

});
