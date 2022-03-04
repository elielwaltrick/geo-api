package repositories;

import javax.inject.Inject;

import models.ProjetosCamadas;
import play.db.jpa.JPAApi;
import util.jpa.GenericJpaRepository;

public class ProjetosCamadasRepository extends GenericJpaRepository<ProjetosCamadas> {
	private JPAApi jpaApi;

	@Inject
	public ProjetosCamadasRepository(JPAApi api) {
		super(api, ProjetosCamadas.class);
		this.jpaApi = api;
	}
}
