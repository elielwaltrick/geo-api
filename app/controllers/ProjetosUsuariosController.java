package controllers;

import javax.inject.Inject;

import play.mvc.Controller;
import play.mvc.Security;
import repositories.ProjetosUsuariosRepository;

@Security.Authenticated(Secured.class)
public class ProjetosUsuariosController extends Controller {

	@Inject
	private ProjetosUsuariosRepository projetosUsuariosRepository;

}
