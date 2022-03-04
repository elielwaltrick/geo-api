package util.json;

import java.io.IOException;
import java.util.List;

import com.bedatadriven.jackson.datatype.jts.JtsModule;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module.Feature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import models.GenericObject;
import play.libs.Json;
import util.exception.ModelException;
import util.json.hibernate.Hibernate5ModuleSd;

public class JsonUtil {
	public static final ObjectMapper defaultMapper() {
		ObjectMapper mapper = new ObjectMapper().registerModule(new JtsModule())
				.registerModule(new Hibernate5ModuleSd().disable(Feature.USE_TRANSIENT_ANNOTATION).enable(Feature.SERIALIZE_IDENTIFIER_FOR_LAZY_NOT_LOADED_OBJECTS))
				.registerModule(new JavaTimeModule()).disable(SerializationFeature.WRITE_NULL_MAP_VALUES).enable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING)
				.setSerializationInclusion(Include.NON_NULL).disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES).disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		mapper.getSerializationConfig().getDefaultVisibilityChecker().withFieldVisibility(Visibility.ANY).withGetterVisibility(Visibility.NONE).withCreatorVisibility(Visibility.NONE)
				.withIsGetterVisibility(Visibility.NONE).withSetterVisibility(Visibility.NONE);
		return mapper;
	}

	public static <T> List<T> jsonToList(JsonNode json, Class<T> class1) throws JsonParseException, JsonMappingException, IOException {
		return jsonToList(json.toString(), class1);
	}

	public static <T> List<T> jsonToList(String jsonString, Class<T> class1) throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = JsonUtil.defaultMapper();
		JavaType type = mapper.getTypeFactory().constructCollectionType(List.class, class1);
		return mapper.readValue(jsonString, type);
	}

	public static final <A> A jsonToObject(JsonNode json, Class<A> class1) throws ModelException {
		if (json == null) {
			throw new ModelException("Expecting Json data", "Erro");
		}
		try {
			return Json.fromJson(json, class1);
		} catch (Exception e) {
			throw new ModelException("Invalid Json", "Erro");
		}
	}

	public static final <A> A mergeJsonToObject(JsonNode json, Object object) throws ModelException {
		try {
			return JsonUtil.defaultMapper().readerForUpdating(((GenericObject) object).clone()).readValue(json);
		} catch (IOException | CloneNotSupportedException e) {
			throw new ModelException("Invalid Json", "Erro");
		}
	}
}
