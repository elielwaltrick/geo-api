package repositories;

import javax.inject.Inject;

import models.ProjetosCamadasGeometrias;
import play.db.jpa.JPAApi;
import util.jpa.GenericJpaRepository;

public class ProjetosCamadasGeometriasRepository extends GenericJpaRepository<ProjetosCamadasGeometrias> {
	private JPAApi jpaApi;

	@Inject
	public ProjetosCamadasGeometriasRepository(JPAApi api) {
		super(api, ProjetosCamadasGeometrias.class);
		this.jpaApi = api;
	}
}