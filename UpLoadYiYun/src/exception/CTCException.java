package exception;



public class CTCException extends Exception {
	private static final long serialVersionUID = 1L;

	/**
	 * 带异常消息的构造函数
	 * 
	 * @param message
	 *            异常消息
	 */
	public CTCException(String message) {
		super(message);
	}

	/**
	 * 带初始异常的构造函数
	 * 
	 * @param cause
	 *            初始异常
	 */
	public CTCException(Throwable cause) {
		super(cause);
	}

	/**
	 * 带异常消息和初始异常的构造函数
	 * 
	 * @param message
	 *            异常消息
	 * @param cause
	 *            初始异常
	 */
	public CTCException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * 带消息键值和参数的构造函数
	 * 
	 * @param msgKey
	 *            消息键值
	 * @param args
	 *            参数
	 */
	public CTCException(String msgKey, Object[] args) {
		super(msgKey);
	}

}
