public class CodeGeneratorUnitTest {

	@Test
	public void 드라이버_코드생성_테스트() {
		assertThat(CodeGenerator.userCodeGenerate(Enums.UserTypes.D, 5), is("DA01A00006"));
		assertThat(CodeGenerator.userCodeGenerate(Enums.UserTypes.D, 90000), is("DA01A90001"));
		assertThat(CodeGenerator.userCodeGenerate(Enums.UserTypes.D, 99999), is("DA01B00000"));
		assertThat(CodeGenerator.userCodeGenerate(Enums.UserTypes.D, 100000), is("DA01B00001"));
		assertThat(CodeGenerator.userCodeGenerate(Enums.UserTypes.D, 199999), is("DA01C00000"));
		assertThat(CodeGenerator.userCodeGenerate(Enums.UserTypes.D, 2499999), is("DA01Z00000"));
		assertThat(CodeGenerator.userCodeGenerate(Enums.UserTypes.D, 2500000), is("DA01Z00001"));
		assertThat(CodeGenerator.userCodeGenerate(Enums.UserTypes.D, 2599998), is("DA01Z99999"));
		
		try {
			CodeGenerator.userCodeGenerate(Enums.UserTypes.D, 2599999);
		}
		catch (Exception exception) {
			assertThat(exception, instanceOf(IllegalStateException.class));
		}
	}
}
