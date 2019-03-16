<?php

namespace App\Http\Controllers;

/**
 *
 */

use App\Http\Controllers\Controller;
use Illuminate\Http\Request;
use App\Model\Member;
use App\Model\User;

class PersonController extends Controller
{
	public function GetMemberList(request $r)
	{
		$userid = GetUserId($r);
		$list = Member::GetMemberList($userid);
		$info = User::UserInfoById($userid);
		$chosen = $info['latest_member']['id'];
		return response()->json(array('list'=>$list,'chosen_id'=>$chosen));
	}

	public function CreateMember(request $r)
	{
		$userid = GetUserId($r);
		// 输入对象的信息
		$name = $r->input('name', '');
		$alias = $r->input('alias', null);
		$sex = $r->input('sex', 1);
		$weight = $r->input('weight', null);
		$height = $r->input('height', null);
		$birthday = $r->input('birthday', null);
		// 检查数据有效性
		if (trim($name) == '') return response('Name is required', 401);

		preg_match(config('Person.Double'), $weight, $result);
		if ($weight != null and !isset($result[0])) return response('Weight is no avail!', 401);

		preg_match(config('Person.Double'), $height, $result);
		if ($height != null and !isset($result[0])) return response('Height is no avail!', 401);

		preg_match(config('Person.Sex'), $sex, $result);
		if (!isset($result[0])) return response('Sex is no avail!', 401);

		preg_match(config('Person.Birthday'), $birthday, $result);
		if ($birthday != null and !isset($result[0])) return response('Birthday is no avail!', 401);
		// 插入数据
		$New_id = Member::CreateMember($userid,$name,$alias,$sex,$weight,$height,$birthday);
		// 获取用户个人信息
		$user_info = User::UserInfoById($userid);
		if($user_info['latest_member'] == []){
			User::ModifyUser($userid,['latest_member'=>$New_id]);
			$Chosen = $new_id;
		}else{
			$Chosen = $user_info['latest_member']['id'];
		}
		// 查询本人的对象列表
		$list = Member::GetMemberList($userid);
		return response()->json(array('list'=>$list,'chosen_id'=>$Chosen));
	}
}