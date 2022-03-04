package controllers;

import static play.libs.Json.toJson;

import javax.inject.Inject;

import com.fasterxml.jackson.databind.JsonNode;

import models.ProjetosCamadas;
import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import repositories.ProjetosCamadasRepository;
import util.MensagemSistema;
import util.exception.ModelException;
import util.json.JsonUtil;

@Security.Authenticated(Secured.class)
public class ProjetosCamadasController extends Controller {

	@Inject
	private ProjetosCamadasRepository projetosCamadasRepository;

	private void beforeDelete(ProjetosCamadas projetoCamada) throws ModelException {

	}

	private void beforeSave(ProjetosCamadas projetoCamada) throws ModelException {

	}

	private ProjetosCamadas beforeUpdate(Integer id, JsonNode node) throws ModelException {
		ProjetosCamadas projCamadaBanco = projetosCamadasRepository.retrieve(id);
		if (projCamadaBanco == null) {
			throw new ModelException("Camada não encontrada!", "Erro");
		}
		ProjetosCamadas projCamada = JsonUtil.mergeJsonToObject(node, projCamadaBanco);
		return projCamada;
	}

	@Transactional
	public Result delete(Integer id) {
		try {
			doDelete(id);
			return ok(Json.toJson(new MensagemSistema("Camada excluída com sucesso!", "Sucesso")));
		} catch (ModelException e) {
			return badRequest(Json.toJson(new MensagemSistema(e.getMsg(), e.getTipo())));
		}
	}

	private void doDelete(Integer id) throws ModelException {
		ProjetosCamadas projCamadas = projetosCamadasRepository.retrieve(id);
		if (projCamadas == null) {
			throw new ModelException("Camada não encontrada", "Erro");
		}
		beforeDelete(projCamadas);
		projetosCamadasRepository.delete(projCamadas);
	}

	private ProjetosCamadas doSave(ProjetosCamadas projetoCamada) throws ModelException {
		return projetosCamadasRepository.save(projetoCamada);
	}

	private ProjetosCamadas doUpdate(Integer id, JsonNode node) throws ModelException {
		ProjetosCamadas projCamada = beforeUpdate(id, node);
		return projetosCamadasRepository.save(projCamada);
	}

	@Transactional
	public Result list() {
		return ok(Json.toJson(projetosCamadasRepository.list()));
	}

	@Transactional
	public Result retrieve(Integer id) {
		ProjetosCamadas projCamadas = projetosCamadasRepository.retrieve(id);
		if (projCamadas == null) {
			return badRequest(Json.toJson(new MensagemSistema("Camada não encontrada!", "Erro")));
		}
		return ok(Json.toJson(projCamadas));
	}

	@Transactional
	public Result save() {
		try {
			ProjetosCamadas projCamada = JsonUtil.jsonToObject(request().body().asJson(), ProjetosCamadas.class);
			return ok(toJson(doSave(projCamada)));
		} catch (ModelException e) {
			return badRequest(Json.toJson(new MensagemSistema(e.getMsg(), e.getTipo())));
		}
	}

	@Transactional
	public Result update(Integer id) {
		try {
			return ok(toJson(doUpdate(id, request().body().asJson())));
		} catch (ModelException e) {
			return badRequest(Json.toJson(new MensagemSistema(e.getMsg(), e.getTipo())));
		}
	}

}
