package util.json.hibernate;

import java.lang.reflect.Field;
import java.util.HashMap;

import javax.persistence.Id;

import org.hibernate.engine.spi.Mapping;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.proxy.HibernateProxy;
import org.hibernate.proxy.LazyInitializer;

import com.fasterxml.jackson.datatype.hibernate5.HibernateProxySerializer;

public class HibernateProxySerializerSd extends HibernateProxySerializer {

	public HibernateProxySerializerSd(boolean forceLazyLoading) {
		super(forceLazyLoading);
	}

	public HibernateProxySerializerSd(boolean _forceLoading, boolean _serializeIdentifiers, Mapping _mapping) {
		super(_forceLoading, _serializeIdentifiers, _mapping);
	}

	@Override
	protected Object findProxied(HibernateProxy proxy) {
		LazyInitializer init = proxy.getHibernateLazyInitializer();
		if (!_forceLazyLoading && init.isUninitialized()) {
			if (_serializeIdentifier) {
				final String idName;
				if (_mapping != null) {
					idName = _mapping.getIdentifierPropertyName(init.getEntityName());
				} else {
					final SessionImplementor session = init.getSession();
					if (session != null) {
						idName = session.getFactory().getIdentifierPropertyName(init.getEntityName());
					} else {
						String idFieldName = null;
						try {
							for (Field field : init.getPersistentClass().getDeclaredFields()) {
								if (field.isAnnotationPresent(Id.class)) {
									idFieldName = field.getName();
									break;
								}
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
						idName = idFieldName == null ? init.getEntityName() : idFieldName;
					}
				}
				final Object idValue = init.getIdentifier();
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put(idName, idValue);
				return map;
			}
			return null;
		}
		return init.getImplementation();
	}
}
