package repositories;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import models.ProjetosCamadasValores;
import play.db.jpa.JPAApi;
import util.jpa.GenericJpaRepository;

public class ProjetosCamadasValoresRepository extends GenericJpaRepository<ProjetosCamadasValores> {
	private JPAApi jpaApi;

	@Inject
	public ProjetosCamadasValoresRepository(JPAApi api) {
		super(api, ProjetosCamadasValores.class);
		this.jpaApi = api;
	}

	@SuppressWarnings("unchecked")
	public ProjetosCamadasValores retrieveByIdGeometria(Integer id) {
		EntityManager em = getEm();
		Query query = em.createQuery("select pcv from ProjetosCamadasValores pcv where projetoCamadaGeometria.id = :id");
		query.setParameter("id", id);
		ProjetosCamadasValores valor = null;

		try {
			valor = (ProjetosCamadasValores) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}

		return valor;
	}
}