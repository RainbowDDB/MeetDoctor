<?php

namespace App\Http\Controllers;

/**
 *
 */

use App\Http\Controllers\Controller;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Redis;
use App\Model\History;

class AskController extends Controller
{
	public function ask(request $r)
	{
		// 输入参数
		$type = $r->input('type', 0);
		$question = $r->input('question', '');
		$words = $r->input('w', '');

		$userid = GetUserId($r);

		// 类型4为重开问询
		if ($type == 4) {
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
//				$a = $this->SaveRedis($r, 'u', 1, $words);
				$a = GetUserMember($r);
				if ($a) {
					$return = $this->CurlAI($words);
					if ($return) {
						$AI_back = $return;
						$history_list = json_encode(array(array('u_a'=>'u','result'=>0,'words'=>$words),array('u_a'=>'a','result'=>1,'words'=>$AI_back)));
						History::CreateHistory($userid,$history_list);
						// 处理信息 type=4 结果页
						return response()->json(['type' => 4, 'content' => json_decode($AI_back, false), 'success' => 1], 200);
					} else {
						$success = 0;
						$msg = '服务器故障';
						return response()->json(['type' => 4, 'msg' => $msg, 'success' => 0], 500);
					}
				} else {
					return response('No Member', 402);
				}
				break;
			// 回答问题（程度类）
			case 2:
//				$a = $this->SaveRedis($r, 'u', 2, $words, $question);
				if ($a) {
					// 处理信息
					return response()->json(['type' => 1, 'answer' => '收到'], 200);
				} else {
					return response('No Member', 402);
				}
				break;
			// 回答问题（boolean类）
			case 3:
//				$a = $this->SaveRedis($r, 'u', 3, $words, $question);
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
			$return = array('state' => 0, 'list' => []);
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

	// curl请求ai算法
	private function CurlAI($words)
	{
		$response = $this->request(config('Ask.AI_API') . '?content=' . $words);
		if (!$response['status']) {
			return false;
		} else {
			return $response['return']['content'];
		}
	}

	// curl爬虫打包
	private function request($url, $post = null, $follow = null, $cookie = null, $returnCookie = 0)
	{
		//补全url，支持相对地址跟踪爬虫
		if ($follow != null) {
			// '/abc/d'为根目录下的相对地址
			if (preg_match('/^\/[^\/].+$/', $url, $redict_url) > 0) {
				$host = explode('/', $follow)[0] . '//' . explode('/', $follow)[2];
				$last_url = $host . $redict_url[0];
				// '//abc/d'为缺少协议的形式
			} elseif (preg_match('/^\/\/.+$/', $url, $redict_url) > 0) {
				$agr = explode('/', $follow)[0];
				$last_url = $agr . $url;
				// 'abc/d'为这个页面文件下的相对地址
			} elseif (preg_match('/^http(s)?/', $url, $redict_url) == 0) {
				$host = explode('/', $follow)[0] . '//' . explode('/', $follow)[2];
				$last_url = $host . '/' . $url;
			}
		} elseif (preg_match('/^\/\/.+$/', $url, $redict_url) > 0) {
			$last_url = 'http:' . $url;
		}
		if (!isset($last_url)) {
			$last_url = $url;
		}
		//创建curl模拟浏览
		$curl = curl_init();
		curl_setopt($curl, CURLOPT_URL, $last_url);
		curl_setopt($curl, CURLOPT_USERAGENT, 'Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.1; Trident/6.0)');
		curl_setopt($curl, CURLOPT_FOLLOWLOCATION, 1);
		curl_setopt($curl, CURLOPT_AUTOREFERER, 1);
		curl_setopt($curl, CURLOPT_REFERER, $follow);
		curl_setopt($curl, CURLOPT_SSL_VERIFYPEER, FALSE);
		curl_setopt($curl, CURLOPT_SSL_VERIFYHOST, FALSE);
		//配置post数据
		if ($post) {
			curl_setopt($curl, CURLOPT_POST, 1);
			curl_setopt($curl, CURLOPT_POSTFIELDS, http_build_query($post));
		}
		//配置cookie
		if ($cookie) {
			curl_setopt($curl, CURLOPT_COOKIE, $cookie);
		}
		curl_setopt($curl, CURLOPT_HEADER, $returnCookie);
		curl_setopt($curl, CURLOPT_TIMEOUT, 5);
		curl_setopt($curl, CURLOPT_RETURNTRANSFER, 1);
		$data = curl_exec($curl);
		$httpCode = curl_getinfo($curl, CURLINFO_HTTP_CODE);
		if ($httpCode == '404' or $httpCode == '400') {
			return array('status' => '0', 'return' => array('news_url' => $url, 'refer' => ($follow == null) ? '' : $follow, 'reason' => $httpCode));
		}
		if (curl_errno($curl) == '28') {
			return array('status' => '0', 'return' => array('news_url' => $url, 'refer' => ($follow == null) ? '' : $follow, 'reason' => 'CURL TIMEOUT'));
		}
		$last_url = curl_getinfo($curl, CURLINFO_EFFECTIVE_URL);
		curl_close($curl);
		//设置返回对象
		$return['cookie'] = '';
		$return['ask_url'] = $url;
		$return['ask_follow'] = $follow;
		//后一行的参数是跳转后最终的url，上面的是发起请求的url
		$return['url'] = $last_url;
		if ($returnCookie) {
			list($header, $body) = explode("\r\n\r\n", $data, 2);
			preg_match_all("/Set\-Cookie:([^;]*);/", $header, $matches);
			$return['cookie'] = implode(';', $matches[1]);
			$return['content'] = $body;
		} else {
			$return['content'] = $data;
		}
		return array('status' => '1', 'return' => $return);
	}
}