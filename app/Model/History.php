<?php

namespace App\Model;

use Illuminate\Database\Eloquent\Model;

class History extends Model
{
	protected $hidden = ['updated_at'];

	public static function CreateHistory($userid, $content)
	{
		$history = new History();
		$history->user_id = $userid;
		$history->content = $content;
		$history->save();
		return $history->id;
	}

	public static function GetHistory($userid)
	{
		$history = new History();
		$result = $history->where('user_id',$userid)->orderBy('id', 'DESC')->get();
		return $result;

	}

	public function user()
	{
		return $this->belongsTo('App\Model\User');
	}
}
