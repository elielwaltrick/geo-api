package controllers;

import java.io.File;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import dto.FileDTO;
import models.ProjetosCamadasGeometrias;
import models.ProjetosCamadasGeometriasFotos;
import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Http.MultipartFormData;
import play.mvc.Http.MultipartFormData.FilePart;
import play.mvc.Result;
import play.mvc.Security;
import repositories.ProjetosCamadasGeometriasFotosRepository;
import repositories.ProjetosCamadasGeometriasRepository;
import util.MensagemSistema;
import util.aws.AwsS3Service;
import util.exception.ModelException;

@Security.Authenticated(Secured.class)
public class ProjetosCamadasGeometriasFotosController extends Controller {

	private AwsS3Service s3Service = AwsS3Service.getInstance();

	@Inject
	private ProjetosCamadasGeometriasFotosRepository projetosCamadasGeometriasFotosRepository;

	@Inject
	private ProjetosCamadasGeometriasRepository projetosCamadasGeometriasRepository;

	private ProjetosCamadasGeometriasFotos saveFile(String urlFile, Integer geomId, Integer tipo) throws ModelException {
		if (StringUtils.isBlank(urlFile)) {
			throw new ModelException("Erro ao gerar URL do arquivo.", "Erro");
		}

		ProjetosCamadasGeometrias geom = projetosCamadasGeometriasRepository.retrieve(geomId);
		if (geom == null) {
			throw new ModelException("Geometria não encontrado!", "Erro");
		}

		ProjetosCamadasGeometriasFotos foto = new ProjetosCamadasGeometriasFotos();
		foto.setProjetoCamadaGeometria(geom);
		foto.setTipo(tipo);
		foto.setFoto(urlFile);
		return projetosCamadasGeometriasFotosRepository.save(foto);
	}

	@Transactional
	public Result uploadFile(Integer geomId, Integer tipo) {
		MultipartFormData<File> body = request().body().asMultipartFormData();
		try {
			if (body != null) {
				FilePart<File> file = body.getFile("file");
				FileDTO fileDTO = new FileDTO();
				fileDTO.setContentType(file.getContentType());
				fileDTO.setFileName(file.getFilename());
				fileDTO.setFile(file.getFile());

				if (fileDTO.getFile() != null) {
					s3Service.sendFileToS3(fileDTO, AwsS3Service.getPathGeom(geomId));
					return ok(Json.toJson(saveFile(fileDTO.getPublicURL(), geomId, tipo)));
				} else {
					throw new ModelException("Você deve selecionar imagens ou arquivos pdf, doc e xls.", "Erro");
				}
			}
		} catch (ModelException e) {
			badRequest(Json.toJson(new MensagemSistema("Erro AWS S3: " + e.getMessage(), e.getTipo())));
		}
		return badRequest();
	}

}
