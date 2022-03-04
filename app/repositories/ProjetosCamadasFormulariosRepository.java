package repositories;

import javax.inject.Inject;

import models.ProjetosCamadasFormularios;
import play.db.jpa.JPAApi;
import util.jpa.GenericJpaRepository;

public class ProjetosCamadasFormulariosRepository extends GenericJpaRepository<ProjetosCamadasFormularios> {
	private JPAApi jpaApi;

	@Inject
	public ProjetosCamadasFormulariosRepository(JPAApi api) {
		super(api, ProjetosCamadasFormularios.class);
		this.jpaApi = api;
	}
}