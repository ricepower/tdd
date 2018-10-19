public class DriverTripServiceUnitTest {

	@InjectMocks
	private DriverTripService driverTripService;
	
	@Mock
	private TripRepository tripRepository;
	
	@Before
	public void setting() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void finish_테스트() {
		트립이_null일때_finish_예외처리_테스트();
		드라이버_운전상태_확인_예외처리_테스트();
		트립_상태가_운행중이_아닐때_예외처리_테스트();
	}

	private void 트립이_null일때_finish_예외처리_테스트() {
		when(tripRepository.findBySq(0L)).thenReturn(null);
		runFinish((driver, sq) -> driverTripService.finish(driver, sq), null, 0L);
	}

	private void 드라이버_운전상태_확인_예외처리_테스트() {
		when(tripRepository.findBySq(0L)).thenReturn(new Trip());
		드라이버_운전상태확인_테스트_실행(Enums.DriverState.P, Enums.Working.A);
		드라이버_운전상태확인_테스트_실행(Enums.DriverState.P, Enums.Working.D);
		드라이버_운전상태확인_테스트_실행(Enums.DriverState.P, Enums.Working.Q);
		드라이버_운전상태확인_테스트_실행(Enums.DriverState.A, Enums.Working.A);
		드라이버_운전상태확인_테스트_실행(Enums.DriverState.A, Enums.Working.Q);
		드라이버_운전상태확인_테스트_실행(Enums.DriverState.I, Enums.Working.A);
		드라이버_운전상태확인_테스트_실행(Enums.DriverState.I, Enums.Working.D);
		드라이버_운전상태확인_테스트_실행(Enums.DriverState.I, Enums.Working.Q);
		드라이버_운전상태확인_테스트_실행(Enums.DriverState.B, Enums.Working.A);
		드라이버_운전상태확인_테스트_실행(Enums.DriverState.B, Enums.Working.D);
		드라이버_운전상태확인_테스트_실행(Enums.DriverState.B, Enums.Working.Q);
	}

	private void 드라이버_운전상태확인_테스트_실행(Enums.DriverState state, Enums.Working working) {
		Driver drivingDriver = new Driver();
		drivingDriver.setState(state);
		drivingDriver.setWorking(working);
		runFinish((driver, sq) -> driverTripService.finish(driver, sq), drivingDriver, 0L);
	}
	
	private void 트립_상태가_운행중이_아닐때_예외처리_테스트() {
		트립_운행중상태_테스트_실행(Enums.TripState.MW);
		트립_운행중상태_테스트_실행(Enums.TripState.MC);
		트립_운행중상태_테스트_실행(Enums.TripState.TC);
		트립_운행중상태_테스트_실행(Enums.TripState.CC);
		트립_운행중상태_테스트_실행(Enums.TripState.DC);
		트립_운행중상태_테스트_실행(Enums.TripState.MF);
	}

	private void 트립_운행중상태_테스트_실행(Enums.TripState state) {
		Driver drivingDriver = given_운전가능상태_드라이버();
		
		Trip trip = new Trip();
		when(tripRepository.findBySq(0L)).thenReturn(trip);
		trip.setState(state);
		
		runFinish((driver, sq) -> driverTripService.finish(driver, sq), drivingDriver, 0L);
	}

	private Driver given_운전가능상태_드라이버() {
		Driver drivingDriver = new Driver();
		drivingDriver.setState(Enums.DriverState.A);
		drivingDriver.setWorking(Enums.Working.D);
		return drivingDriver;
	}

	private void runFinish(BiConsumer<Driver, Long> consumer, Driver driver, long sq) {
		try {
			consumer.accept(driver, sq);
			fail();
		}
		catch (Exception e) {
			assertThat(e, instanceOf(BadRequestException.class));
		}
	}
	
	
	
	@Test
	public void cancel_테스트() {
		트립이_null일때_cancel_예외처리_테스트();
		드라이버가_운행취소가능_상태_예외처리_테스트();
		트립이_매칭완료일때_예외처리_테스트();
	}

	private void 트립이_null일때_cancel_예외처리_테스트() {
		when(tripRepository.findBySq(0L)).thenReturn(null);
		runFinish((driver, sq) -> driverTripService.cancel(driver, sq), null, 0L);
	}

	private void 드라이버가_운행취소가능_상태_예외처리_테스트() {
		when(tripRepository.findBySq(0L)).thenReturn(new Trip());

		운행취소가능상태_테스트_실행(Enums.DriverState.P, Enums.Working.A);
		운행취소가능상태_테스트_실행(Enums.DriverState.P, Enums.Working.D);
		운행취소가능상태_테스트_실행(Enums.DriverState.P, Enums.Working.Q);
		운행취소가능상태_테스트_실행(Enums.DriverState.A, Enums.Working.A);
		운행취소가능상태_테스트_실행(Enums.DriverState.A, Enums.Working.Q);
		운행취소가능상태_테스트_실행(Enums.DriverState.I, Enums.Working.A);
		운행취소가능상태_테스트_실행(Enums.DriverState.I, Enums.Working.D);
		운행취소가능상태_테스트_실행(Enums.DriverState.I, Enums.Working.Q);
		운행취소가능상태_테스트_실행(Enums.DriverState.B, Enums.Working.A);
		운행취소가능상태_테스트_실행(Enums.DriverState.B, Enums.Working.D);
		운행취소가능상태_테스트_실행(Enums.DriverState.B, Enums.Working.Q);
	}
	
	private void 운행취소가능상태_테스트_실행(Enums.DriverState state, Enums.Working working) {
		Driver badStatedriver = new Driver();
		badStatedriver.setState(state);
		badStatedriver.setWorking(working);
		runFinish((driver, sq) -> driverTripService.cancel(driver, sq), badStatedriver, 0L);
	}
	
	private void 트립이_매칭완료일때_예외처리_테스트() {
		트립_매칭완료상태_테스트_실행(Enums.TripState.MW);
		트립_매칭완료상태_테스트_실행(Enums.TripState.TS);
		트립_매칭완료상태_테스트_실행(Enums.TripState.TC);
		트립_매칭완료상태_테스트_실행(Enums.TripState.CC);
		트립_매칭완료상태_테스트_실행(Enums.TripState.DC);
		트립_매칭완료상태_테스트_실행(Enums.TripState.MF);
	}
	
	private void 트립_매칭완료상태_테스트_실행(Enums.TripState state) {
		Driver canDrivingDriver = given_운전가능상태_드라이버();
		
		Trip trip = new Trip();
		trip.setState(state);
		when(tripRepository.findBySq(0L)).thenReturn(trip);
		runFinish((driver, sq) -> driverTripService.cancel(driver, sq), canDrivingDriver, 0L);
	}
}
