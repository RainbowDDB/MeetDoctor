<?php

namespace App\Model;

use Illuminate\Database\Eloquent\Model;
use App\Model\BasicModel;

class User extends BasicModel
{
	protected $fillable = ['name', 'password', 'question', 'answer'];

	protected $hidden = ['created_at', 'updated_at'];

	// 检验用户是否存在，存在返回本人信息，不存在返回false
	public static function UserInfoByName($name)
	{
		$info = User::where('name', $name)->with('latest_member:id,name,alias_name,sex,weight,height,birthday')->first();
		if ($info == null) {
			return false;
		} else {
			return $info->toArray();
		}
	}

	//通过id查找用户信息，不存在返回false
	public static function UserInfoById($id)
	{
		$info = User::where('id', $id)->with('latest_member:id,name,alias_name,sex,weight,height,birthday')->first();
		if ($info == null) {
			return false;
		} else {
			return $info->toArray();
		}
	}

	//插入个人信息
	public static function AddUser($username, $password)
	{
		$user = new User();
		$user->name = $username;
		$user->password = $password;
		$result = $user->save();
		return $result;
	}

	// 修改个人信息
	public static function ModifyUser($id, array $content)
	{
		$user = new User();
		$result = $user->where('id', $id)
			->update($content);
		return $result;
	}

	public function member()
	{
		return $this->hasMany('App\Model\Member', 'user_id', 'id');
	}

	public function latest_member()
	{
		return $this->belongsTo('App\Model\Member', 'latest_member');
	}
}