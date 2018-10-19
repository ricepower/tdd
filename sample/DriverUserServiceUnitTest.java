public class DriverUserServiceUnitTest {

	@InjectMocks
	private DriverUserService driverUserService;
	
	@Before
	public void setting() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void 드라이버_가입상태_테스트() {
		Driver driver = new Driver();
		
		driver.setState(Enums.DriverState.P);
		assertBadRequestEx(driver);
		
		driver.setState(Enums.DriverState.B);
		assertBadRequestEx(driver);
		
		driver.setState(Enums.DriverState.I);
		assertBadRequestEx(driver);
	}
	
	@Test
	public void 드라이버_업무상태_테스트() {
		Driver driver = new Driver();
		driver.setState(Enums.DriverState.A);
		
		driver.setWorking(Enums.Working.A);
		assertBadRequestEx(driver);
		
		driver.setWorking(Enums.Working.D);
		assertBadRequestEx(driver);
	}

	private void assertBadRequestEx(Driver driver) {
		try {
			driverUserService.attendances(driver);
			fail();
		}
		catch (Exception e) {
			assertThat(e, instanceOf(BadRequestException.class));
		}
	}
}
