<?php

namespace App\Http\Controllers\Auth;

/**
 * login 登陆接口
 * register 注册接口
 * checkname 检验用户名是否存在
 * checklogin 检验登陆状态
 */

use App\Http\Controllers\Controller;
use Illuminate\Http\Request;
use App\Model\User;

class AuthController extends Controller
{
	public function login(request $r)
	{
		// username
		$username = $r->input('username');
		// userpassword
		$userpassword = $r->input('password');
		if ($username == '' || $userpassword == '' || $_SERVER['REQUEST_METHOD'] != 'POST') {
			return response('Unauthorized', 401);
		}
		$info = User::UserInfoByName($username);
		if ($info) {
			$name = 1;
			if ($info['password'] == $userpassword) {
				$userid = $info['id'];
				$r->session()->put('userid', $userid);
				$result = 1;
			} else {
				$result = 0;
			}
		} else {
			$result = 0;
			$name = 0;
		}
		return response()->json(array('result' => $result, 'name' => $name), 200);
	}

	public function register(request $r)
	{
		// username
		$username = $r->input('username');
		//userpassword
		$userpassword = md5($r->input('password'));
		if($username == ''||$userpassword == ''){
			return response('Unauthorized', 401);
		}
		$info = User::UserInfoByName($username);
		if($info){
			return response('User is present!',400);
		}else{
			$result = User::AddUser($username,$userpassword);
			if($result){
				return response('Success',200);
			}else{
				return response('Error',404);
			}
		}
	}

	public function checkname(request $r)
	{
		//username
		$username = $r->input('username');
		if($username == ''){
			return response('Unauthorized', 401);
		}
		$info = User::UserInfoByName($username);
		if($info){
			return response('User is present!',400);
		}else{
			return response('Username is ability',200);
		}
	}

	public function checklogin(request $r)
	{
		$userid = GetUserId($r);
		if($userid){
			return response('Success',200);
		}else{
			return response('False',406);
		}
	}
}