package itcs.com.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class DemoApplicationTests {

	@Test
	void main() {
		DemoApplication.main(new String[] {});
		assertThat(true).isTrue(); // Simple assertion to ensure the main method runs without exceptions
	}
}
