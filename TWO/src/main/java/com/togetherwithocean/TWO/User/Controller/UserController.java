package com.togetherwithocean.TWO.User.Controller;

import com.togetherwithocean.TWO.User.DTO.PostFindUserEmailReq;
import com.togetherwithocean.TWO.User.DTO.PostFindUserPasswdReq;
import com.togetherwithocean.TWO.User.Domain.User;
import com.togetherwithocean.TWO.User.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 이메일 찾기 api
    @PostMapping("/find-email")
    public ResponseEntity<String> findUserEmail(@RequestBody PostFindUserEmailReq postFindUserEmailReq) {

        User findUser = userService.getUserByRealName(postFindUserEmailReq.getRealName()); // 유저명으로 유저 조회

        // DB 상에 해당 유저가 존재하는지 확인
        if (findUser == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("가입된 유저가 아닙니다.");

        // DB 상의 유저 정보와 일치하는지 확인 - 유저명과 유저 번호가 같은 유저 가리키는지
        if (!findUser.getPhoneNumber().equals(postFindUserEmailReq.getPhoneNumber()))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("가입된 유저가 아닙니다.");

        // 인증 번호 확인 유무 체크
        if (postFindUserEmailReq.getConfirm())
            return ResponseEntity.status(HttpStatus.OK).body(findUser.getNickname() + "님의 이메일은 " + findUser.getEmail() + " 입니다.");
        else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("인증 번호 확인이 되지 않았습니다.");
    }

    // 비밀번호 찾기 api
    @PatchMapping("/find-pw")
    public ResponseEntity<String> findUserPasswd(@RequestBody PostFindUserPasswdReq postFindUserPasswdReq) {

        User findUser = userService.getUserByRealName(postFindUserPasswdReq.getRealName()); // 유저명으로 유저 조회

        // DB 상에 해당 유저가 존재하는지 확인
        if (findUser == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("가입된 유저가 아닙니다.");

        // DB 상의 유저 정보와 일치하는지 확인 - 유저명과 유저 이메일이 같은 유저 가리키는지
        if (!findUser.getEmail().equals(postFindUserPasswdReq.getEmail()))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("가입된 유저가 아닙니다.");

        // 인증 번호 확인 유무 체크
        if (!postFindUserPasswdReq.getConfirm())
            return ResponseEntity.status(HttpStatus.OK).body("인증 번호 확인이 되지 않았습니다.");

        // 비밀번호 일치 확인
        if (postFindUserPasswdReq.getPasswd().equals(postFindUserPasswdReq.getRe_passwd())) {
            findUser.setPasswd(postFindUserPasswdReq.getPasswd());
            userService.updatePasswd(findUser);
            return ResponseEntity.status(HttpStatus.OK).body("비밀번호 설정이 완료되었습니다.");
        }
        else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("비밀번호가 일치하지 않습니다.");
    }
}