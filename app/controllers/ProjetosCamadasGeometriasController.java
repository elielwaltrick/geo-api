package controllers;

import static play.libs.Json.toJson;

import javax.inject.Inject;

import com.fasterxml.jackson.databind.JsonNode;

import models.ProjetosCamadasGeometrias;
import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import repositories.ProjetosCamadasGeometriasRepository;
import util.MensagemSistema;
import util.exception.ModelException;
import util.json.JsonUtil;

@Security.Authenticated(Secured.class)
public class ProjetosCamadasGeometriasController extends Controller {

	@Inject
	private ProjetosCamadasGeometriasRepository projetosCamadasGeometriasRepository;

	private void beforeDelete(ProjetosCamadasGeometrias rojetosCamadasGeometrias) throws ModelException {

	}

	private void beforeSave(ProjetosCamadasGeometrias projetosCamadasGeometrias) throws ModelException {

	}

	private ProjetosCamadasGeometrias beforeUpdate(Integer id, JsonNode node) throws ModelException {
		ProjetosCamadasGeometrias projCamadaGeomBanco = projetosCamadasGeometriasRepository.retrieve(id);
		if (projCamadaGeomBanco == null) {
			throw new ModelException("Geometria não encontrada!", "Erro");
		}
		ProjetosCamadasGeometrias projCamadaGeom = JsonUtil.mergeJsonToObject(node, projCamadaGeomBanco);
		return projCamadaGeom;
	}

	@Transactional
	public Result delete(Integer id) {
		try {
			doDelete(id);
			return ok(Json.toJson(new MensagemSistema("Geometria excluída com sucesso!", "Sucesso")));
		} catch (ModelException e) {
			return badRequest(Json.toJson(new MensagemSistema(e.getMsg(), e.getTipo())));
		}
	}

	private void doDelete(Integer id) throws ModelException {
		ProjetosCamadasGeometrias projCamadasGeom = projetosCamadasGeometriasRepository.retrieve(id);
		if (projCamadasGeom == null) {
			throw new ModelException("Geometria não encontrada", "Erro");
		}
		beforeDelete(projCamadasGeom);
		projetosCamadasGeometriasRepository.delete(projCamadasGeom);
	}

	private ProjetosCamadasGeometrias doSave(ProjetosCamadasGeometrias projetoCamadaGeom) throws ModelException {
		return projetosCamadasGeometriasRepository.save(projetoCamadaGeom);
	}

	private ProjetosCamadasGeometrias doUpdate(Integer id, JsonNode node) throws ModelException {
		ProjetosCamadasGeometrias projCamadaGeom = beforeUpdate(id, node);
		return projetosCamadasGeometriasRepository.save(projCamadaGeom);
	}

	@Transactional
	public Result list() {
		return ok(Json.toJson(projetosCamadasGeometriasRepository.list()));
	}

	@Transactional
	public Result retrieve(Integer id) {
		ProjetosCamadasGeometrias projCamadasGeom = projetosCamadasGeometriasRepository.retrieve(id);
		if (projCamadasGeom == null) {
			return badRequest(Json.toJson(new MensagemSistema("Geometria não encontrada!", "Erro")));
		}
		return ok(Json.toJson(projCamadasGeom));
	}

	@Transactional
	public Result save() {
		try {
			ProjetosCamadasGeometrias projCamadaGeom = JsonUtil.jsonToObject(request().body().asJson(), ProjetosCamadasGeometrias.class);
			return ok(toJson(doSave(projCamadaGeom)));
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
