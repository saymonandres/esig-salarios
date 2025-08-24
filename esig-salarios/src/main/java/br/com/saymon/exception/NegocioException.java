package br.com.saymon.exception;

public class NegocioException extends RuntimeException {

	private static final long serialVersionUID = -4136608198367011211L;

	public NegocioException(String msg) {
        super(msg);
    }
}
