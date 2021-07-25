// 사용자 정의 예외클래스 정의
public class LoginFailedException extends Exception {

	public LoginFailedException(String message) {
		super(message); // Exception(String) 생성자가 호출됨
	}

}
