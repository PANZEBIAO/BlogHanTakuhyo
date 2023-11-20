package blog.com.Model.Dao;

import org.springframework.data.jpa.repository.JpaRepository;

import blog.com.Model.Entity.UserEntity;


public interface UserDao extends JpaRepository<UserEntity, Long> {
	//保存処理
	UserEntity save(UserEntity userEntity);
	
	//SELECT * FROM users WHERE userId=?
	UserEntity findByUserId(Long userId);
	
	//SELECT * FROM users WHERE userName=?
	UserEntity findByEmail(String email);
	
	//SELECT * FROM users WHERE email= ? AND password = ?
	UserEntity findByEmailAndPassword(String email, String password);
}
