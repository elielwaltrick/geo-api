package util.jpa;

import java.util.List;

import javax.persistence.TypedQuery;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;

public class PageUtil {

	private static void applyPagination(Criteria query, int pageNumber, int pageSize) {
		query.setMaxResults(pageSize + 1);
		query.setFirstResult(pageNumber * pageSize);
	}

	private static void applyPagination(TypedQuery<?> query, int pageNumber, int pageSize) {
		query.setMaxResults(pageSize + 1);
		query.setFirstResult(pageNumber * pageSize);
	}

	public static Page readPage(Criteria query, int pageNumber, int pageSize) {
		return readPage(query, pageNumber, pageSize, null);
	}

	public static Page readPage(Criteria query, int pageNumber, int pageSize, Order order) {
		int count = 0;
		boolean more = false;

		applyPagination(query, pageNumber, pageSize);
		query.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

		if (order != null) {
			query.addOrder(order);
		}

		List<?> list = query.list();

		if (list.size() > pageSize) {
			list.remove(list.size() - 1);
			more = true;
		}
		Page pageResult = new Page(list, pageNumber, count, pageSize, more);
		return pageResult;
	}

	public static Page readPage(TypedQuery<?> query, int pageNumber, int pageSize) {
		int count = 0;
		boolean more = false;

		applyPagination(query, pageNumber, pageSize);

		List<?> list = query.getResultList();

		if (list.size() > pageSize) {
			list.remove(list.size() - 1);
			more = true;
		}

		Page pageResult = new Page(list, pageNumber, count, pageSize, more);
		return pageResult;
	}
}
