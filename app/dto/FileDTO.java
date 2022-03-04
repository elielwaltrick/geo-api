package dto;

import java.io.File;

public class FileDTO {
	private File file;
	private File fileOriginal;
	private byte[] bytes;
	private String fileName;
	private String fileNameOriginal;
	private String contentType;
	private String contentTypeOriginal;
	private String descricao;
	private String observacoes;
	private Integer tipo;
	private Integer subTipo;
	private String publicURL;

	public byte[] getBytes() {
		return bytes;
	}

	public String getContentType() {
		return contentType;
	}

	public String getContentTypeOriginal() {
		return contentTypeOriginal;
	}

	public String getDescricao() {
		return descricao;
	}

	public File getFile() {
		return file;
	}

	public String getFileName() {
		return fileName;
	}

	public String getFileNameOriginal() {
		return fileNameOriginal;
	}

	public File getFileOriginal() {
		return fileOriginal;
	}

	public String getObservacoes() {
		return observacoes;
	}

	public String getPublicURL() {
		return publicURL;
	}

	public Integer getSubTipo() {
		return subTipo;
	}

	public Integer getTipo() {
		return tipo;
	}

	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public void setContentTypeOriginal(String contentTypeOriginal) {
		this.contentTypeOriginal = contentTypeOriginal;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public void setFileNameOriginal(String fileNameOriginal) {
		this.fileNameOriginal = fileNameOriginal;
	}

	public void setFileOriginal(File fileOriginal) {
		this.fileOriginal = fileOriginal;
	}

	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
	}

	public void setPublicURL(String publicURL) {
		this.publicURL = publicURL;
	}

	public void setSubTipo(Integer subTipo) {
		this.subTipo = subTipo;
	}

	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}
}
