<?php

namespace App\Http\Controllers\Auth;

use App\Http\Controllers\Controller;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Auth;
use App\Model\Users;

class AuthController extends Controller
{
	public function login(request $r)
	{
		// username
		$username = $r->input('username');
		// userpassword
		$userpassword = $r->input('password');
		if($username == ''|| $userpassword == '' || $_SERVER['REQUEST_METHOD'] != 'POST'){
			return response('Unauthorized',401);
		}
		$info = Users::UserInfoByName($username);
		if($info){
			$name = 1;
			if($info['password'] == $userpassword){
				$result = 1;
			}else{
				$result = 0;
			}
		}else{
			$result = 0;
			$name = 0;
		}
		return response()->json(array('result'=>$result,'name'=>$name),200);
	}
}