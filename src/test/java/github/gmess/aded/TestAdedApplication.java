package github.gmess.aded;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

@TestConfiguration(proxyBeanMethods = false)
public class TestAdedApplication {

	public static void main(String[] args) {
		SpringApplication.from(AdedApplication::main).with(TestAdedApplication.class).run(args);
	}

}
