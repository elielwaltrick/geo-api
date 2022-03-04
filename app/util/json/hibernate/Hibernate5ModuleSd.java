package util.json.hibernate;

import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import com.fasterxml.jackson.datatype.hibernate5.HibernateSerializerModifier;

public class Hibernate5ModuleSd extends Hibernate5Module {
	@Override
	public void setupModule(com.fasterxml.jackson.databind.Module.SetupContext context) {
		/*
		 * First, append annotation introspector (no need to override, esp. as
		 * we just implement couple of methods)
		 */
		// Then add serializers we need
		AnnotationIntrospector ai = annotationIntrospector();
		if (ai != null) {
			context.appendAnnotationIntrospector(ai);
		}
		context.addSerializers(new HibernateSerializersSd(_mapping, _moduleFeatures));
		context.addBeanSerializerModifier(new HibernateSerializerModifier(_moduleFeatures, super._sessionFactory));
	}
}
