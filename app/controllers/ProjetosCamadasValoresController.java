package controllers;

import static play.libs.Json.toJson;

import javax.inject.Inject;

import com.fasterxml.jackson.databind.JsonNode;

import models.ProjetosCamadasValores;
import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import repositories.ProjetosCamadasValoresRepository;
import util.MensagemSistema;
import util.exception.ModelException;
import util.json.JsonUtil;

@Security.Authenticated(Secured.class)
public class ProjetosCamadasValoresController extends Controller {

	@Inject
	private ProjetosCamadasValoresRepository projetosCamadasValoresRepository;

	private void beforeDelete(ProjetosCamadasValores projetoCamadaValor) throws ModelException {

	}

	private void beforeSave(ProjetosCamadasValores projetoCamadaValor) throws ModelException {

	}

	private ProjetosCamadasValores beforeUpdate(Integer id, JsonNode node) throws ModelException {
		ProjetosCamadasValores projCamadaValorBanco = projetosCamadasValoresRepository.retrieve(id);
		if (projCamadaValorBanco == null) {
			throw new ModelException("Valor não encontrado!", "Erro");
		}
		ProjetosCamadasValores projCamadaValor = JsonUtil.mergeJsonToObject(node, projCamadaValorBanco);
		return projCamadaValor;
	}

	@Transactional
	public Result delete(Integer id) {
		try {
			doDelete(id);
			return ok(Json.toJson(new MensagemSistema("Valor excluído com sucesso!", "Sucesso")));
		} catch (ModelException e) {
			return badRequest(Json.toJson(new MensagemSistema(e.getMsg(), e.getTipo())));
		}
	}

	private void doDelete(Integer id) throws ModelException {
		ProjetosCamadasValores projCamadaValor = projetosCamadasValoresRepository.retrieve(id);
		if (projCamadaValor == null) {
			throw new ModelException("Valor não encontrado", "Erro");
		}
		beforeDelete(projCamadaValor);
		projetosCamadasValoresRepository.delete(projCamadaValor);
	}

	private ProjetosCamadasValores doSave(ProjetosCamadasValores projetosCamadasValores) throws ModelException {
		return projetosCamadasValoresRepository.save(projetosCamadasValores);
	}

	private ProjetosCamadasValores doUpdate(Integer id, JsonNode node) throws ModelException {
		ProjetosCamadasValores projCamadaValor = beforeUpdate(id, node);
		return projetosCamadasValoresRepository.save(projCamadaValor);
	}

	@Transactional
	public Result list() {
		return ok(Json.toJson(projetosCamadasValoresRepository.list()));
	}

	@Transactional
	public Result retrieve(Integer id) {
		ProjetosCamadasValores projCamadaValor = projetosCamadasValoresRepository.retrieve(id);
		if (projCamadaValor == null) {
			return badRequest(Json.toJson(new MensagemSistema("Valor não encontrado!", "Erro")));
		}
		return ok(Json.toJson(projCamadaValor));
	}

	@Transactional
	public Result retrieveByIdGeometria(Integer id) {
		ProjetosCamadasValores projCamadaValor = projetosCamadasValoresRepository.retrieveByIdGeometria(id);
		if (projCamadaValor == null) {
			return badRequest(Json.toJson(new MensagemSistema("Valor não encontrado!", "Erro")));
		}
		return ok(Json.toJson(projCamadaValor));
	}

	@Transactional
	public Result save() {
		try {
			ProjetosCamadasValores projCamadaValor = JsonUtil.jsonToObject(request().body().asJson(), ProjetosCamadasValores.class);
			return ok(toJson(doSave(projCamadaValor)));
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
