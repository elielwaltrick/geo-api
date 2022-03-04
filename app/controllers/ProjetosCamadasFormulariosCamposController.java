package controllers;

import static play.libs.Json.toJson;

import javax.inject.Inject;

import com.fasterxml.jackson.databind.JsonNode;

import models.ProjetosCamadasFormulariosCampos;
import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import repositories.ProjetosCamadasFormulariosCamposRepository;
import util.MensagemSistema;
import util.exception.ModelException;
import util.json.JsonUtil;

@Security.Authenticated(Secured.class)
public class ProjetosCamadasFormulariosCamposController extends Controller {

	@Inject
	private ProjetosCamadasFormulariosCamposRepository projetosCamadasFormulariosCamposRepository;

	private void beforeDelete(ProjetosCamadasFormulariosCampos projetoCamadaFormularioCampo) throws ModelException {

	}

	private void beforeSave(ProjetosCamadasFormulariosCampos projetoCamadaFormularioCampo) throws ModelException {

	}

	private ProjetosCamadasFormulariosCampos beforeUpdate(Integer id, JsonNode node) throws ModelException {
		ProjetosCamadasFormulariosCampos projCamadaFormCampoBanco = projetosCamadasFormulariosCamposRepository.retrieve(id);
		if (projCamadaFormCampoBanco == null) {
			throw new ModelException("Campo não encontrado!", "Erro");
		}
		ProjetosCamadasFormulariosCampos projCamadaFormCampo = JsonUtil.mergeJsonToObject(node, projCamadaFormCampoBanco);
		return projCamadaFormCampo;
	}

	@Transactional
	public Result delete(Integer id) {
		try {
			doDelete(id);
			return ok(Json.toJson(new MensagemSistema("Campo excluído com sucesso!", "Sucesso")));
		} catch (ModelException e) {
			return badRequest(Json.toJson(new MensagemSistema(e.getMsg(), e.getTipo())));
		}
	}

	private void doDelete(Integer id) throws ModelException {
		ProjetosCamadasFormulariosCampos projCamadasFormCampo = projetosCamadasFormulariosCamposRepository.retrieve(id);
		if (projCamadasFormCampo == null) {
			throw new ModelException("Campo não encontrado", "Erro");
		}
		beforeDelete(projCamadasFormCampo);
		projetosCamadasFormulariosCamposRepository.delete(projCamadasFormCampo);
	}

	private ProjetosCamadasFormulariosCampos doSave(ProjetosCamadasFormulariosCampos projetoCamadaFormCampo) throws ModelException {
		return projetosCamadasFormulariosCamposRepository.save(projetoCamadaFormCampo);
	}

	private ProjetosCamadasFormulariosCampos doUpdate(Integer id, JsonNode node) throws ModelException {
		ProjetosCamadasFormulariosCampos projCamadaFormCampo = beforeUpdate(id, node);
		return projetosCamadasFormulariosCamposRepository.save(projCamadaFormCampo);
	}

	@Transactional
	public Result list() {
		return ok(Json.toJson(projetosCamadasFormulariosCamposRepository.list()));
	}

	@Transactional
	public Result retrieve(Integer id) {
		ProjetosCamadasFormulariosCampos projCamadasFormCampo = projetosCamadasFormulariosCamposRepository.retrieve(id);
		if (projCamadasFormCampo == null) {
			return badRequest(Json.toJson(new MensagemSistema("Campo não encontrado!", "Erro")));
		}
		return ok(Json.toJson(projCamadasFormCampo));
	}

	@Transactional
	public Result save() {
		try {
			ProjetosCamadasFormulariosCampos projCamadaFormCampo = JsonUtil.jsonToObject(request().body().asJson(), ProjetosCamadasFormulariosCampos.class);
			return ok(toJson(doSave(projCamadaFormCampo)));
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
