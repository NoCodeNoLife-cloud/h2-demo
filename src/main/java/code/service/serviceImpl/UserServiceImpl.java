package code.service.serviceImpl;

import code.entity.User;
import code.repository.UserRepository;
import code.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
	private final UserRepository userDao;

	public UserServiceImpl(UserRepository userDao) {this.userDao = userDao;}

	@Override
	public void addUser(User user) {
		userDao.save(user);
	}

	@Override
	public List<User> list() {
		return userDao.findAll();
	}
}