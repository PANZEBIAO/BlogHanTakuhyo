package blog.com.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import blog.com.Service.UserService;

@Controller
public class RegisterController {
	// UserServiceとリンク
	@Autowired
	private UserService userService;

	// 登録画面の表示
	@GetMapping("/register")
	public String getRegisterPage() {
		return "register.html";
	}
	
	//登録処理
	@PostMapping("/register/process")
	public String register(@RequestParam String userName,
			@RequestParam String password,
			@RequestParam String email) {
		//userServiceのcreateAccountを呼び出し
		if (userService.createAccount(userName, password, email)) {
			//trueだったらログインページに飛ぶ
			return "register_success.html";
		}else {
			//falseだったら、登録画面に戻す
			return "redirect:/register";
		}
	}

}
