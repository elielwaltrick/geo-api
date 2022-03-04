package repositories;

import javax.inject.Inject;

import models.ProjetosUsuarios;
import play.db.jpa.JPAApi;
import util.jpa.GenericJpaRepository;

public class ProjetosUsuariosRepository extends GenericJpaRepository<ProjetosUsuarios> {
	private JPAApi jpaApi;

	@Inject
	public ProjetosUsuariosRepository(JPAApi api) {
		super(api, ProjetosUsuarios.class);
		this.jpaApi = api;
	}
}
