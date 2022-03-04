package util.json.hibernate;

import org.hibernate.engine.spi.Mapping;
import org.hibernate.proxy.HibernateProxy;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.datatype.hibernate5.HibernateSerializers;

public class HibernateSerializersSd extends HibernateSerializers {

	public HibernateSerializersSd(int features) {
		super(features);
	}

	public HibernateSerializersSd(Mapping _mapping, int _moduleFeatures) {
		super(_mapping, _moduleFeatures);
	}

	@Override
	public JsonSerializer<?> findSerializer(SerializationConfig config, JavaType type, BeanDescription beanDesc) {
		Class<?> raw = type.getRawClass();
		if (HibernateProxy.class.isAssignableFrom(raw)) {
			return new HibernateProxySerializerSd(_forceLoading, _serializeIdentifiers, _mapping);
		}
		return null;
	}

}
