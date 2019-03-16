<?php

namespace App\Model;

use Illuminate\Database\Eloquent\Model;
use App\Model\BasicModel;


class Member extends BasicModel
{
	protected $hidden = ['created_at', 'updated_at'];

	public static function GetMemberList($userid)
	{
		$list = Member::where('user_id', $userid)->get();
		return $list->toArray();
	}

	public static function CreateMember($userid, $name, $alias=null, $sex=1, $weight=null, $height=null, $birthday=null)
	{
		$Member = new Member();
		$Member->user_id = $userid;
		$Member->name = $name;
		$Member->alias_name = $alias;
		$Member->sex = $sex;
		$Member->weight = $weight;
		$Member->height = $height;
		$Member->birthday = $birthday;
		$Member->save();
		return $Member->id;
	}

	public function user()
	{
		return $this->belongsTo('App\Model\User');
	}

}
