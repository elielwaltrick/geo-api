package controllers;

import javax.inject.Inject;

import com.fasterxml.jackson.databind.node.ObjectNode;

import models.Usuarios;
import play.data.Form;
import play.data.FormFactory;
import play.data.validation.Constraints;
import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Security; 
import repositories.UsuariosRepository;
import util.MensagemSistema;

public class SecurityController extends Controller {

	public static class Login {

		@Constraints.Required
		@Constraints.Email
		public String email;

		@Constraints.Required
		public String senha;

		public String getEmail() {
			return email;
		}

		public String getSenha() {
			return senha;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public void setSenha(String senha) {
			this.senha = senha;
		}

	}

	public final static String AUTH_TOKEN_HEADER = "X-AUTH-TOKEN";

	public static final String AUTH_TOKEN = "authToken";

	public static Usuarios getUsuario() {
		return (Usuarios) Http.Context.current().args.get("usuario");
	}

	@Inject
	FormFactory formFactory;

	@Inject
	UsuariosRepository usuariosRepository;

	// returns an authToken
	@Transactional
	public Result login() {
		Form<Login> loginForm = formFactory.form(Login.class).bindFromRequest();

		if (loginForm.hasErrors()) {
			return badRequest(loginForm.errorsAsJson());
		}

		Login login = loginForm.get();
		Usuarios usuario = usuariosRepository.findByEmailAndPassword(login.email, login.senha);

		if (usuario == null) {
			return unauthorized(Json.toJson(new MensagemSistema("Usuário ou senha inválidos.", "error")));
		} else {
			String authToken = usuariosRepository.createToken(usuario);
			ObjectNode loginResposta = Json.newObject();
			loginResposta.put(AUTH_TOKEN, authToken);
			response().setCookie(Http.Cookie.builder(AUTH_TOKEN, authToken).withSecure(ctx().request().secure()).build());
			return ok(loginResposta);
		}
	}

	@Transactional
	@Security.Authenticated(Secured.class)
	public Result logout() {
		response().discardCookie(AUTH_TOKEN);
		usuariosRepository.deleteAuthToken(getUsuario());
		return ok();
	}

}