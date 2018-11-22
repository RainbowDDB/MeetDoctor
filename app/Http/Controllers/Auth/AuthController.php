<?php

namespace App\Http\Controllers\Auth;

use App\Http\Controllers\Controller;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Auth;

class AuthController extends Controller
{
	public function login(request $r)
	{
		// username
		$username = $r->input('username');
		// userpassword
		$userpassword = $r->input('password');
		// success

		// false
		print_R($r->input);
	}
}