public class SmsServiceUnitTest {
	
	private static final String DUMMY_CODE = "000000";
	
	@InjectMocks
	private SmsService smsService;
	
	@Before
	public void setting() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void SMS_인증코드_예외_테스트() {
		assertBadRequestException(null, DUMMY_CODE);
		assertBadRequestException("", DUMMY_CODE);
		assertBadRequestException(" ", DUMMY_CODE);
	}
	
	private void assertBadRequestException(String destination, String code) {
		try {
			smsService.send(destination, code);
			fail();
		}
		catch (Exception e) {
			assertThat(e, instanceOf(BadRequestException.class));
		}
	}
}
