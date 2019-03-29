<?php
namespace App\Http\Controllers\Mock;

use App\Http\Controllers\Controller;
use Illuminate\Http\Request;
use App\Model\User;

class MockController extends Controller
{
	public function mock(request $r){
		$r->session()->put('userid',1);
		$r->session()->put('user_member',1);
		$info = User::UserInfoById(1);
		return response()->json($info);
	}
}