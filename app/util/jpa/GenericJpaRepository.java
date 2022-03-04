package util.jpa;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.AuditQuery;
import org.hibernate.jdbc.Work;

import models.Usuarios;
import play.db.jpa.JPAApi;
import play.mvc.Http;

public class GenericJpaRepository<T> {

	int pageNumber;
	int pageSize;
	private Class<T> clazz;

	private JPAApi jpaApi;

	public GenericJpaRepository(JPAApi api, Class<T> clazz) {
		this.jpaApi = api;
		this.clazz = clazz;
	}

	public void customizeQuery(Criteria query) {
	}

	public void delete(T entity) {
		getEm().remove(entity);
	}

	public EntityManager getEm() {
		Usuarios usuario = (Usuarios) Http.Context.current().args.get("usuario");
		String idTenant = String.valueOf((Integer) 2);
		Session session = (Session) jpaApi.em().getDelegate();

		session.doWork(new Work() {
			@Override
			public void execute(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement("SET SESSION geo.tenant = " + String.valueOf(idTenant));
				ps.execute();
			}
		});

		System.out.println(selectCurrentTenant());

		return jpaApi.em();
	}

	public Criteria getNewHibernateCriteria() {
		Criteria query = getNewHibernateCriteria(clazz);
		query.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return query;
	}

	public Criteria getNewHibernateCriteria(Class<T> clazz1) {
		return getSession().createCriteria(clazz1);
	}

	public Long getNextValSeq(String nomeSequence) {
		org.hibernate.Query query = getSession().createSQLQuery("select nextval('" + nomeSequence + "')");

		return ((BigInteger) query.uniqueResult()).longValue();
	}

	public Session getSession() {
		return (Session) getEm().getDelegate();
	}

	@SuppressWarnings("unchecked")
	public List<T> list() {
		Criteria query = getNewHibernateCriteria(clazz);
		customizeQuery(query);
		query.addOrder(Order.asc("nome"));
		return query.list();
	}

	public Page list(int pageNumber, int pageSize) {
		return list(pageNumber, pageSize, null);
	}

	public Page list(int pageNumber, int pageSize, Order order) {
		Criteria query = getNewHibernateCriteria(clazz);
		customizeQuery(query);
		if (order != null) {
			query.addOrder(order);
		}
		return PageUtil.readPage(query, pageNumber, pageSize);
	}

	public Page list(int pageNumber, int pageSize, Order order, Criteria query) {
		if (order != null) {
			query.addOrder(order);
		}
		return PageUtil.readPage(query, pageNumber, pageSize);
	}

	@SuppressWarnings("unchecked")
	public List<Object> listRevisions(Object id) {
		AuditReader auditReader = AuditReaderFactory.get(getEm());

		AuditQuery auditQuery = auditReader.createQuery().forRevisionsOfEntity(clazz, false, true);
		return auditQuery.add(AuditEntity.id().eq(id)).addOrder(AuditEntity.revisionNumber().desc()).getResultList();
	}

	@SuppressWarnings("unchecked")
	public T retrieve(Object id) {
		Criteria query = getNewHibernateCriteria(clazz);
		customizeQuery(query);
		query.add(Restrictions.idEq(id));
		try {
			return (T) query.uniqueResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public T save(T entity) {
		return getEm().merge(entity);
	}

	@SuppressWarnings("unchecked")
	public String selectCurrentTenant() {
		EntityManager em = jpaApi.em();
		String q = "SELECT current_setting('geo.tenant')";
		Query query = em.createNativeQuery(q);
		String t = (String) query.getSingleResult();
		return t;
	}
}
