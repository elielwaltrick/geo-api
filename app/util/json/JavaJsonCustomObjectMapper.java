package util.json;

import play.Logger;
import play.libs.Json;

public class JavaJsonCustomObjectMapper {

	JavaJsonCustomObjectMapper() {
		Logger.info("Setting JSON default mapper");
		Json.setObjectMapper(JsonUtil.defaultMapper());
	}

}