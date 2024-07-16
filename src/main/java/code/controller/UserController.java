package code.controller;

import code.entity.User;
import code.exception.CustomWebException;
import code.service.UserService;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/user")
public class UserController {
	private final UserService userService;

	public UserController(UserService userService) {this.userService = userService;}

	/**
	 * Adds a user.
	 *
	 * @param user the user
	 *
	 * @return the response entity
	 */
	@RequestMapping("add")
	@ResponseBody
	@SneakyThrows
	public ResponseEntity<User> add(User user) {
		Random random = new Random();
		if (random.nextInt(0, 2) % 2 == 0) {
			throw new CustomWebException("something wrong");
		}
		userService.addUser(user);
		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	/**
	 * Retrieves a list of users.
	 *
	 * @return the list of users
	 */
	@RequestMapping("list")
	@ResponseBody
	public ResponseEntity<List<User>> list() {
		return new ResponseEntity<>(userService.list(), HttpStatus.OK);
	}
}