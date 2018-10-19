public class DriverAuthServiceUnitTest {

	@InjectMocks
	private DriverAuthService driverAuthService;
	
	@Before
	public void setting() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void 이메일패턴_테스() {
		DriverSignupReqVo requestVo = new DriverSignupReqVo();
		String[] testCase = {"xxxx@domain.", "xxxx@domain.com1", "@domain.com", "domain.com", "xxxx@.com"};
		
		for (int i = 0; i < testCase.length; i++) {
			requestVo.setEmail(testCase[i]);
			assertExceptionThrown(requestVo, BadRequestException.class);
		}
	}
	
	@Test
	public void 비밀번호패턴_테스트() {
		// 최소 8~16자리에 숫자, 문자, 특수문자 각각 1개 이상 포함
		DriverSignupReqVo requestVo = new DriverSignupReqVo();
		requestVo.setEmail("xxxx@domain.com");
		String[] testCase = {"7777777", "17171717171717171", "aaaaaaaa", "aaaaaaaaA", "aaaaaaaa4", "aaaaaaaa4a", "aaaaaaaa4aA", "aaaaaaaa4aAd43", "aaaaf3dfsds~dddf0"};
		
		for (int i = 0; i < testCase.length; i++) {
			requestVo.setPassword(testCase[i]);
			assertExceptionThrown(requestVo, BadRequestException.class);
		}
	}
	
	private void assertExceptionThrown(DriverSignupReqVo requestVo, Class<? extends Exception> type) {
		Exception thrownException = null;
		try {
			driverAuthService.signup(requestVo);
		}
		catch (Exception exception) {
			thrownException = exception;
		}
		assertThat(thrownException, instanceOf(type));
	}
}
