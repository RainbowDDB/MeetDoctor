<?php

namespace App\Model;

use Illuminate\Database\Eloquent\Model;

class History extends Model
{
	protected $hidden = ['created_at', 'updated_at'];

	public static function CreateHistory($userid, $content)
	{
		$history = new History();
		$history->user_id = $userid;
		$history->content = $content;
		$history->save();
		return $history->id;
	}

	public function user()
	{
		return $this->belongsTo('App\Model\User');
	}
}
