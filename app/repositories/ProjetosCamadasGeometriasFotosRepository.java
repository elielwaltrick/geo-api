package repositories;

import javax.inject.Inject;

import models.ProjetosCamadasGeometriasFotos;
import play.db.jpa.JPAApi;
import util.jpa.GenericJpaRepository;

public class ProjetosCamadasGeometriasFotosRepository extends GenericJpaRepository<ProjetosCamadasGeometriasFotos> {
	private JPAApi jpaApi;

	@Inject
	public ProjetosCamadasGeometriasFotosRepository(JPAApi api) {
		super(api, ProjetosCamadasGeometriasFotos.class);
		this.jpaApi = api;
	}
}