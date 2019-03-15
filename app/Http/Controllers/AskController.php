<?php

namespace App\Http\Controllers;

/**
 *
 */

use App\Http\Controllers\Controller;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Redis;

class AskController extends Controller
{
	public function ask(request $r)
	{
		// 输入参数
		$type = $r->input('type', 0);
		$question = $r->input('question', '');
		$words = $r->input('w', '');
		// 类型4为重开问询
		if($type == 4){
				$member = GetUserMember($r);
				if (!$member) {
					return false;
				}
				$RedisKey = config('Ask.RedisHead') . $member;
				Redis::del($RedisKey);
				return response('success', 200);
		}
		// 拦截空白发送
		if ($words == '') return response('Parameter:W can\'t be empty!', 401);
		switch ($type) {
			// 正常输入语音
			case 1:
				$a = $this->SaveRedis($r, 'u', 1, $words);
				if ($a) {
					// 处理信息
					return response()->json(['type' => 1, 'answer' => '收到'], 200);
				} else {
					return response('No Member', 402);
				}
				break;
			// 回答问题（程度类）
			case 2:
				$a = $this->SaveRedis($r, 'u', 2, $words, $question);
				if ($a) {
					// 处理信息
					return response()->json(['type' => 1, 'answer' => '收到'], 200);
				} else {
					return response('No Member', 402);
				}
				break;
			// 回答问题（boolean类）
			case 3:
				$a = $this->SaveRedis($r, 'u', 3, $words, $question);
				if ($a) {
					// 处理信息
					return response()->json(['type' => 1, 'answer' => '收到'], 200);
				} else {
					return response('No Member', 402);
				}
				break;
			// 其他错误类型
			default:
				return response('Parameter:Type is no avail!', 401);
				break;
		}
	}

	// 进入问询页面时，查询历史记录
	public function enter(request $r)
	{
		$member = GetUserMember($r);
		if (!$member) {
			return response('No Member', 402);
		}
		$time = time();
		$RedisKey = config('Ask.RedisHead') . $member;

		$list = Redis::command('lRange', [$RedisKey, 0, -1]);
		if ($list == [] or $list[0] < $time) {
			Redis::del($RedisKey);
			$return = array('state' => 0, 'list' => '空空如也');
		} else {
			unset($list[0]);
			$return = array('state' => 1, 'list' => $list);
		}
		return response()->json($return, 200);
	}

	// 保存对话信息到redis
	private function SaveRedis(request $r, $u_a, $type, $words, $question = '')
	{
		// redisKey组合
		$member = GetUserMember($r);
		if (!$member) {
			return false;
		}
		$RedisKey = config('Ask.RedisHead') . $member;
		// 时间+有效期
		$time = time() + config('Ask.Effective_Time');
		// 保存信息
		if (Redis::command('lIndex', [$RedisKey, 0]) == null) {
			Redis::command('lPush', [$RedisKey, $time]);
		} else {
			Redis::command('lSet', [$RedisKey, 0, $time]);
		}
		Redis::command('rPush', [$RedisKey, json_encode(['u_a' => $u_a, 'type' => $type, 'words' => $words, 'question' => $question])]);
		return true;
	}
}