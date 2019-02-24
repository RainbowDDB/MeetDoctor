<?php
namespace App\Model;

use Illuminate\Database\Eloquent\Model;
use App\Model\BasicModel;

class User extends BasicModel
{
	protected $fillable = ['name','password','question','answer'];

	protected $hidden = ['created_at','updated_at'];

	// 检验用户是否存在，存在返回本人信息，不存在返回false
	public static function UserInfoByName($name){
		$info = User::where('name',$name)->first();
		if($info == null){
			return false;
		}else{
			return $info->toArray();
		}
	}

	//通过id查找用户信息，不存在返回false
	public static function UserInfoById($id){
		$info = User::where('id',$id)->first();
		if($info == null){
			return false;
		}else{
			return $info->toArray();
		}
	}

	//插入个人信息
	public static function AddUser($username,$password){
		$user = new User();
		$user->name = $username;
		$user->password = $password;
		$result = $user->save();
		return $result;
	}


}