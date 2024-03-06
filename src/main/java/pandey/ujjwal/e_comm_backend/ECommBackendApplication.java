package pandey.ujjwal.e_comm_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

@SpringBootApplication
//@ComponentScan(basePackages = { "pandey.ujjwal.e_comm_backend" }, excludeFilters = @Filter(type = FilterType.CUSTOM, value = MyExcludedClass.class))
//@ComponentScan({ "pandey.ujjwal.e_comm_backend", "pandey.ujjwal.e_comm_backend.configs" })
@ComponentScans({ @ComponentScan("pandey.ujjwal.e_comm_backend"),
		@ComponentScan("pandey.ujjwal.e_comm_backend.configs") })
public class ECommBackendApplication {

	public static void main(String[] args) {
//		SpringApplication.run(ECommBackendApplication.class, args);
		SpringApplication app = new SpringApplication(ECommBackendApplication.class);
		app.setAdditionalProfiles("dev");
		app.run(args);
	}

}
