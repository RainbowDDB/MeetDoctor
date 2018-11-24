<?php
namespace App\Model;

use Illuminate\Database\Eloquent\Model;
use App\Model\BasicModel;

class Users extends BasicModel
{
	protected $fillable = ['name','password','question','answer'];

	protected $hidden = ['created_at','updated_at'];

	// 检验用户是否存在，存在返回本人信息，不存在返回
	public static function UserInfoByName($name){
		$info = Users::where('name',$name)->first();
		if($info == null){
			return false;
		}else{
			return $info->toArray();
		}
	}


}