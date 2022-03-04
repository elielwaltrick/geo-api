package util.exception;

public class ModelException extends Exception {

	private static final long serialVersionUID = 1L;

	private String msg;
	private String tipo;

	public ModelException(String msg) {
		super(msg);
		this.msg = msg;
	}

	public ModelException(String msg, String tipo) {
		super(msg);
		this.msg = msg;
		this.tipo = tipo;
	}

	public String getMsg() {
		return msg;
	}

	public String getTipo() {
		return tipo;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
}
