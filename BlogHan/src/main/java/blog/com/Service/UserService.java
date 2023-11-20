package blog.com.Service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import blog.com.Model.Dao.UserDao;
import blog.com.Model.Entity.UserEntity;

@Service
public class UserService {
	//UserDaooを使えるようにする
	@Autowired
	private UserDao userDao;
	

	// 登録処理メソッド
	public boolean createAccount(String userName, String password, String email) {
		
		//登録日を記録し、変数registerDateに保存
		LocalDateTime registerDate = LocalDateTime.now();
		//Daoからemailを検索し、変数userEntityに保存
		UserEntity userEntity = userDao.findByEmail(email);
		
		//データベースに入力したemailがなかったら
		if (userEntity == null) {
			//データを保存
			userDao.save(new UserEntity(userName, password, email, registerDate));
			//trueを返す
			return true;
		} else {
			//データベースに入力したemailがあったらfalseを返す
			return false;
		}
	}
	
	//ログイン処理メソッド
	public UserEntity loginCheck(String email, String password) {
		//Daoからemailとpasswordを検索し、変数userEntityに保存
		UserEntity userEntity = userDao.findByEmailAndPassword(email, password);
		//データベースに入力したemailとpasswordがなかったら
		if(userEntity == null) {
			//nullを返す
			return null;
		}else {
			//データベースに入力したemailとpasswordがあったら、emailとpasswordが保存してる変数userEntityを返す
			return userEntity;
		}
	}
	
	//
	public UserEntity getUserById(Long userId) {
		return userDao.findByUserId(userId);
	}
}
