package util;

public class MensagemSistema {

	private String mensagem, tipo;

	public MensagemSistema() {

	}

	public MensagemSistema(String mensagem, String tipo) {
		this.mensagem = mensagem;
		this.tipo = tipo;
	}

	public String getMensagem() {
		return mensagem;
	}

	public String getTipo() {
		return tipo;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

}
