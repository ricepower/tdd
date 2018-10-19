```java
public class AuthServiceUnitTest {

	private final static String USER = "user";
	
	@InjectMocks
	private AuthService authService;
	
	@Mock
	private DriverUserReadService mockDriverReadService;
	
	@Mock
	private CustomerUserService mockCustomerService;
	
	@Before
	public void setting() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void 이메일_패턴_예외_테스트() {
		assertThatBadRequestException(USER, "MalformedEmail");
		assertThatBadRequestException(USER, "MalformedEmail@");
		assertThatBadRequestException(USER, "MalformedEmail@winfor");
		assertThatBadRequestException(USER, "MalformedEmail@winfor.com1");
	}
	
	@Test
	public void 존재하지않는_계정_예외_테스트() {
		String noneExistingUser = "NoneExistingUser@winfor.com";
		when(mockDriverReadService.findDriverByEmailOrElseException(noneExistingUser)).thenThrow(BadRequestException.class);
		when(mockCustomerService.findCustomerByEmailOrElseException(noneExistingUser)).thenThrow(BadRequestException.class);
		assertThatBadRequestException("driver", noneExistingUser);
		assertThatBadRequestException("customer", noneExistingUser);
	}
	
	private void assertThatBadRequestException(String user, String malformedEmail) {
		try {
			authService.sendDriverPasswordResetEmail(user, malformedEmail);
			fail();
		}
		catch (Exception exception) {
			assertThat(exception, instanceOf(BadRequestException.class));
		}
	}
}
```
