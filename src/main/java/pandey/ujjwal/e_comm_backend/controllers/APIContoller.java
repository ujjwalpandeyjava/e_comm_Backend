package pandey.ujjwal.e_comm_backend.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class APIContoller {

	@GetMapping(path = { "/test" })
	public String teset() {
		System.err.println("test");
		return "index";
	}

}
