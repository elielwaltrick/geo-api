package util.modules;
import com.google.inject.AbstractModule;

import util.json.JavaJsonCustomObjectMapper;

public class CustomJsonModuleLoader extends AbstractModule {

	@Override
	protected void configure() {
		bind(JavaJsonCustomObjectMapper.class).asEagerSingleton();
	}

}
