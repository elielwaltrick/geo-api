package controllers;

import static play.libs.Json.toJson;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import models.Usuarios;
import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import repositories.UsuariosRepository;
import util.BCrypt;
import util.MensagemSistema;
import util.exception.ModelException;
import util.json.JsonUtil;

@Security.Authenticated(Secured.class)
public class UsuariosController extends Controller {

	@Inject
	private UsuariosRepository usuariosRepository;

	private void beforeSave(Usuarios usuario) throws ModelException {
		Usuarios findByEmail = usuariosRepository.findByEmail(usuario.getEmail());
		if (findByEmail != null) {
			throw new ModelException("Já existe um usuário com este e-mail");
		}
	}

	private Usuarios beforeUpdate(Integer id, Usuarios usuario) throws ModelException {
		Usuarios userBanco = usuariosRepository.retrieve(id);
		if (userBanco == null) {
			throw new ModelException("Usuário não encontrado!", "error");
		}

		return userBanco;
	}

	@Transactional
	public Result delete(Integer id) {
		try {
			doDelete(id);
			return ok(Json.toJson(new MensagemSistema("Usuário excluído com sucesso!", "success")));
		} catch (ModelException e) {
			return badRequest(Json.toJson(new MensagemSistema(e.getMsg(), e.getTipo())));
		}
	}

	private void doDelete(Integer id) throws ModelException {
		Usuarios userBanco = usuariosRepository.retrieve(id);
		if (userBanco == null) {
			throw new ModelException("Usuário não encontrado", "error");
		}

		usuariosRepository.delete(userBanco);
	}

	private Usuarios doSave(Usuarios usuario) throws ModelException {
		beforeSave(usuario);
		usuario.setSenha(BCrypt.hashpw(usuario.getSenha(), BCrypt.gensalt(12)));
		return usuariosRepository.save(usuario);
	}

	private Usuarios doUpdate(Integer id, Usuarios usuario) throws ModelException {
		Usuarios userBanco = beforeUpdate(id, usuario);
		userBanco.setNome(usuario.getNome());
		if (!StringUtils.isBlank(usuario.getSenha())) {
			userBanco.setSenha(BCrypt.hashpw(usuario.getSenha(), BCrypt.gensalt(12)));
		}

		return usuariosRepository.save(userBanco);
	}

	@Transactional
	public Result list() {
		return ok(Json.toJson(usuariosRepository.list()));
	}

	@Transactional
	public Result retrieve(Integer id) throws ModelException {
		Usuarios usuario = usuariosRepository.retrieve(id);
		if (usuario == null) {
			return badRequest(Json.toJson(new MensagemSistema("Usuário não encontrado!", "error")));
		}
		return ok(Json.toJson(usuario));
	}

	@Transactional
	public Result save() {
		try {
			Usuarios usuario = JsonUtil.jsonToObject(request().body().asJson(), Usuarios.class);
			return ok(toJson(doSave(usuario)));
		} catch (ModelException e) {
			return badRequest(Json.toJson(new MensagemSistema(e.getMsg(), e.getTipo())));
		}
	}

	@Transactional
	public Result update(Integer id) {
		try {
			Usuarios usuario = JsonUtil.jsonToObject(request().body().asJson(), Usuarios.class);
			return ok(toJson(doUpdate(id, usuario)));
		} catch (ModelException e) {
			return badRequest(Json.toJson(new MensagemSistema(e.getMsg(), e.getTipo())));
		}
	}

}
