package controllers;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import javax.inject.Inject;

import models.Usuarios;
import play.libs.Json;
import play.mvc.Http.Context;
import play.mvc.Result;
import play.mvc.Results;
import play.mvc.Security;
import repositories.UsuariosRepository;
import util.GenericConstants;
import util.MensagemSistema;

public class Secured extends Security.Authenticator {

	public static CompletionStage<Result> forbiddenResult(String message) {
		MensagemSistema mensagemSistema = new MensagemSistema((message != null ? message : "Você não tem permissão para acessar este recurso!"), "Erro");
		return CompletableFuture.completedFuture(Results.forbidden(Json.toJson(mensagemSistema)));
	}

	@Inject
	UsuariosRepository usuariosRepository;

	@Override
	public String getUsername(Context ctx) {
		if (ctx.request().header(SecurityController.AUTH_TOKEN_HEADER).isPresent()) {
			String token = ctx.request().header(SecurityController.AUTH_TOKEN_HEADER).get();
			Usuarios usuario = usuariosRepository.findByAuthToken(token);
			if (usuario != null) {
				ctx.args.put(GenericConstants.USUARIO_CONTEXT, usuario);
				return usuario.getEmail();
			}
		}

		return null;
	}

	@Override
	public Result onUnauthorized(Context ctx) {
		Usuarios usuarioLogado = (Usuarios) ctx.args.get(GenericConstants.USUARIO_CONTEXT);
		if (usuarioLogado != null) {
			return forbidden(Json.toJson(new MensagemSistema("Você não tem permissão para acessar este recurso", "error")));
		}
		return unauthorized(Json.toJson(new MensagemSistema("Sessão expirada. Por favor faça login novamente.", "error")));
	}

}