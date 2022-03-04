package controllers;

import static play.libs.Json.toJson;

import javax.inject.Inject;

import com.fasterxml.jackson.databind.JsonNode;

import models.ProjetosCamadasFormularios;
import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import repositories.ProjetosCamadasFormulariosRepository;
import util.MensagemSistema;
import util.exception.ModelException;
import util.json.JsonUtil;

@Security.Authenticated(Secured.class)
public class ProjetosCamadasFormulariosController extends Controller {

	@Inject
	private ProjetosCamadasFormulariosRepository projetosCamadasFormulariosRepository;

	private void beforeDelete(ProjetosCamadasFormularios projetoCamadaFormulario) throws ModelException {

	}

	private void beforeSave(ProjetosCamadasFormularios projetoCamadaFormulario) throws ModelException {

	}

	private ProjetosCamadasFormularios beforeUpdate(Integer id, JsonNode node) throws ModelException {
		ProjetosCamadasFormularios projCamadaFormBanco = projetosCamadasFormulariosRepository.retrieve(id);
		if (projCamadaFormBanco == null) {
			throw new ModelException("Formulário não encontrada!", "Erro");
		}
		ProjetosCamadasFormularios projCamadaForm = JsonUtil.mergeJsonToObject(node, projCamadaFormBanco);
		return projCamadaForm;
	}

	@Transactional
	public Result delete(Integer id) {
		try {
			doDelete(id);
			return ok(Json.toJson(new MensagemSistema("Formulário excluído com sucesso!", "Sucesso")));
		} catch (ModelException e) {
			return badRequest(Json.toJson(new MensagemSistema(e.getMsg(), e.getTipo())));
		}
	}

	private void doDelete(Integer id) throws ModelException {
		ProjetosCamadasFormularios projCamadasForm = projetosCamadasFormulariosRepository.retrieve(id);
		if (projCamadasForm == null) {
			throw new ModelException("Formulário não encontrado", "Erro");
		}
		beforeDelete(projCamadasForm);
		projetosCamadasFormulariosRepository.delete(projCamadasForm);
	}

	private ProjetosCamadasFormularios doSave(ProjetosCamadasFormularios projetoCamadaForm) throws ModelException {
		return projetosCamadasFormulariosRepository.save(projetoCamadaForm);
	}

	private ProjetosCamadasFormularios doUpdate(Integer id, JsonNode node) throws ModelException {
		ProjetosCamadasFormularios projCamadaForm = beforeUpdate(id, node);
		return projetosCamadasFormulariosRepository.save(projCamadaForm);
	}

	@Transactional
	public Result list() {
		return ok(Json.toJson(projetosCamadasFormulariosRepository.list()));
	}

	@Transactional
	public Result retrieve(Integer id) {
		ProjetosCamadasFormularios projCamadasForm = projetosCamadasFormulariosRepository.retrieve(id);
		if (projCamadasForm == null) {
			return badRequest(Json.toJson(new MensagemSistema("Formulário não encontrado!", "Erro")));
		}
		return ok(Json.toJson(projCamadasForm));
	}

	@Transactional
	public Result save() {
		try {
			ProjetosCamadasFormularios projCamadaForm = JsonUtil.jsonToObject(request().body().asJson(), ProjetosCamadasFormularios.class);
			return ok(toJson(doSave(projCamadaForm)));
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
