package repositories;

import java.util.UUID;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import models.Usuarios;
import play.db.jpa.JPAApi;
import util.BCrypt;
import util.jpa.GenericJpaRepository;

public class UsuariosRepository extends GenericJpaRepository<Usuarios> {
	private JPAApi jpaApi;

	@Inject
	public UsuariosRepository(JPAApi api) {
		super(api, Usuarios.class);
		this.jpaApi = api;
	}

	public String createToken(Usuarios usuario) {
		EntityManager em = getEm();
		String token = UUID.randomUUID().toString();
		usuario.setAuthToken(token);
		em.merge(usuario);
		return token;
	}

	public void deleteAuthToken(Usuarios usuario) {
		EntityManager em = getEm();
		usuario.setAuthToken(null);
		em.merge(usuario);
	}

	public Usuarios findByAuthToken(String authToken) {
		// TODO: ver o que fazer em caso de colisao de UUID
		// provavalmente throw exception se retornar mais de 2 registros do bd

		EntityManager em = getEm();
		Query query = em.createQuery("select u from Usuarios u where authToken = :authToken");
		query.setParameter("authToken", authToken);

		Usuarios usuario = null;

		try {
			usuario = (Usuarios) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}

		return usuario;
	}

	public Usuarios findByEmail(String email) {
		EntityManager em = getEm();
		TypedQuery<Usuarios> query = em.createQuery("select u from Usuarios u where email = :email", Usuarios.class);
		query.setParameter("email", email);
		try {
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public Usuarios findByEmailAndPassword(String email, String password) {
		EntityManager em = getEm();
		TypedQuery<Usuarios> query = em.createQuery("select u from Usuarios u where email = :email", Usuarios.class);
		query.setParameter("email", email);
		Usuarios usuario = null;

		try {
			usuario = query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}

		if (usuario != null) {
			if (BCrypt.checkpw(password, usuario.getSenha())) {
				return usuario;
			}
		}

		return null;
	}

}
