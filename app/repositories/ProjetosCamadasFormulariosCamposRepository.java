package repositories;

import javax.inject.Inject;

import models.ProjetosCamadasFormulariosCampos;
import play.db.jpa.JPAApi;
import util.jpa.GenericJpaRepository;

public class ProjetosCamadasFormulariosCamposRepository extends GenericJpaRepository<ProjetosCamadasFormulariosCampos> {
	private JPAApi jpaApi;

	@Inject
	public ProjetosCamadasFormulariosCamposRepository(JPAApi api) {
		super(api, ProjetosCamadasFormulariosCampos.class);
		this.jpaApi = api;
	}
}