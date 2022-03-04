package util.jpa;

import org.hibernate.boot.Metadata;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;
import org.hibernate.integrator.spi.Integrator;
import org.hibernate.service.spi.SessionFactoryServiceRegistry;

public class ReplicationEventListenerIntegrator implements Integrator {

	public static final ReplicationEventListenerIntegrator INSTANCE = new ReplicationEventListenerIntegrator();

	@Override
	public void disintegrate(SessionFactoryImplementor sessionFactory, SessionFactoryServiceRegistry serviceRegistry) {

	}

	@Override
	public void integrate(Metadata metadata, SessionFactoryImplementor sessionFactory, SessionFactoryServiceRegistry serviceRegistry) {

		final EventListenerRegistry eventListenerRegistry = serviceRegistry.getService(EventListenerRegistry.class);

		eventListenerRegistry.appendListeners(EventType.PRE_UPDATE, ListenerTeste.INSTANCE);

	}
}