<?php
namespace App\Http\Controllers\Mock;

use App\Http\Controllers\Controller;
use Illuminate\Http\Request;
use App\Model\Users;

class MockController extends Controller
{
	public function mock(request $r){
		$r->session()->put('userid',1);
		$info = Users::UserInfoById(1);
		return response()->json($info);
	}
}