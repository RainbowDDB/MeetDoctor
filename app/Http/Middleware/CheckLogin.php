<?php

namespace App\Http\Middleware;

use Closure;

class CheckLogin
{
	/**
	 * Handle an incoming request.
	 *
	 * @param  \Illuminate\Http\Request $request
	 * @param  \Closure $next
	 * @return mixed
	 */
	public function handle($request, Closure $next)
	{
		$p = $request->path();

		// 登陆注册页不拦截
		if($this->StartWith($p,'api/user/') or $this->StartWith($p,'mock/'))
			return $next($request);

		// 未登录返回406
		$userid = GetUserId($request);
		if(!$userid){
			return response('',406);
		}

		return $next($request);
	}

	public function StartWith($req, $str)
	{
		$len = strlen($str);
		return (substr($req, 0, $len) === $str);
	}
}
