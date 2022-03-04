package util.jpa;

import java.util.ArrayList;
import java.util.Collection;

public class Page {
	private Collection<?> resultList = new ArrayList<>();
	private int pageSize;
	private boolean more = false;

	public Page(Collection<?> resultList, int page, int totalCount, int pageSize, boolean more) {
		this.setResultList(resultList);
		this.setPageSize(pageSize);
		this.setMore(more);
	}

	public int getPageSize() {
		return pageSize;
	}

	public Collection<?> getResultList() {
		return resultList;
	}

	public boolean isMore() {
		return more;
	}

	public void setMore(boolean more) {
		this.more = more;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public void setResultList(Collection<?> resultList) {
		this.resultList = resultList;
	}

}