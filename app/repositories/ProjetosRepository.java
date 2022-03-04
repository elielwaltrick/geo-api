package repositories;

import javax.inject.Inject;

import models.Projetos;
import play.db.jpa.JPAApi;
import util.jpa.GenericJpaRepository;

public class ProjetosRepository extends GenericJpaRepository<Projetos> {
	private JPAApi jpaApi;

	@Inject
	public ProjetosRepository(JPAApi api) {
		super(api, Projetos.class);
		this.jpaApi = api;
	}

	public void ativar(Projetos projeto) {
		projeto.setAtivo(Boolean.TRUE);
		save(projeto);
	}

	public void inativar(Projetos projeto) {
		projeto.setAtivo(Boolean.FALSE);
		save(projeto);
	}

}
