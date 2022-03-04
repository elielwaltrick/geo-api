package controllers;

import play.mvc.Controller;
import play.mvc.Result;

public class ApplicationController extends Controller {

	public Result opts() {
		response().setHeader("Access-Control-Allow-Origin", "*");
		response().setHeader("Access-Control-Allow-Headers", "*");
		return ok("");
	}
}
