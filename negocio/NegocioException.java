package negocio;

public class NegocioException extends Exception {

	private static final long serialVersionUID = 1L;

	public NegocioException() {
		super();
	}

	public NegocioException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public NegocioException(String message, Throwable cause) {
		super(message, cause);
	}

	public NegocioException(String message) {
		super(message);
	}

	public NegocioException(Throwable cause) {
		super(cause);
	}
}
