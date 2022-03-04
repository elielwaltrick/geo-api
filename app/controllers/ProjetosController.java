package controllers;

import static play.libs.Json.toJson;

import javax.inject.Inject;

import com.fasterxml.jackson.databind.JsonNode;

import models.Projetos;
import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import repositories.ProjetosRepository;
import util.MensagemSistema;
import util.exception.ModelException;
import util.json.JsonUtil;

@Security.Authenticated(Secured.class)
public class ProjetosController extends Controller {

	@Inject
	private ProjetosRepository projetosRepository;

	@Transactional
	public Result ativar(Integer id) {
		try {
			doAtivar(id);
			return ok(Json.toJson(new MensagemSistema("Projeto ativado com sucesso!", "Sucesso")));
		} catch (ModelException e) {
			return badRequest(Json.toJson(new MensagemSistema(e.getMsg(), e.getTipo())));
		}
	}

	private void beforeDelete(Projetos projeto) throws ModelException {

	}

	private void beforeSave(Projetos projeto) throws ModelException {

	}

	private Projetos beforeUpdate(Integer id, JsonNode node) throws ModelException {
		Projetos projBanco = projetosRepository.retrieve(id);
		if (projBanco == null) {
			throw new ModelException("Projeto não encontrado!", "Erro");
		}
		Projetos projeto = JsonUtil.mergeJsonToObject(node, projBanco);
		return projeto;
	}

	@Transactional
	public Result delete(Integer id) {
		try {
			doDelete(id);
			return ok(Json.toJson(new MensagemSistema("Projeto excluído com sucesso!", "Sucesso")));
		} catch (ModelException e) {
			return badRequest(Json.toJson(new MensagemSistema(e.getMsg(), e.getTipo())));
		}
	}

	private void doAtivar(Integer id) throws ModelException {
		Projetos projeto = projetosRepository.retrieve(id);
		if (projeto == null) {
			throw new ModelException("Projeto não encontrado", "Erro");
		}
		projetosRepository.ativar(projeto);
	}

	private void doDelete(Integer id) throws ModelException {
		Projetos projeto = projetosRepository.retrieve(id);
		if (projeto == null) {
			throw new ModelException("Projeto não encontrado", "Erro");
		}
		beforeDelete(projeto);
		projetosRepository.delete(projeto);
	}

	private void doInativar(Integer id) throws ModelException {
		Projetos projeto = projetosRepository.retrieve(id);
		if (projeto == null) {
			throw new ModelException("Projeto não encontrado", "Erro");
		}
		projetosRepository.inativar(projeto);
	}

	private Projetos doSave(Projetos projeto) throws ModelException {
		return projetosRepository.save(projeto);
	}

	private Projetos doUpdate(Integer id, JsonNode node) throws ModelException {
		Projetos projeto = beforeUpdate(id, node);
		return projetosRepository.save(projeto);
	}

	@Transactional
	public Result inativar(Integer id) {
		try {
			doInativar(id);
			return ok(Json.toJson(new MensagemSistema("Projeto inativado com sucesso!", "Sucesso")));
		} catch (ModelException e) {
			return badRequest(Json.toJson(new MensagemSistema(e.getMsg(), e.getTipo())));
		}
	}

	@Transactional
	public Result list() {
		return ok(Json.toJson(projetosRepository.list()));
	}

	@Transactional
	public Result retrieve(Integer id) {
		Projetos projeto = projetosRepository.retrieve(id);
		if (projeto == null) {
			return badRequest(Json.toJson(new MensagemSistema("Projeto não encontrado!", "Erro")));
		}

		return ok(Json.toJson(projeto));
	}

	@Transactional
	public Result save() {
		try {
			Projetos projeto = JsonUtil.jsonToObject(request().body().asJson(), Projetos.class);
			return ok(toJson(doSave(projeto)));
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
