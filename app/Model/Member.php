<?php

namespace App\Model;

use Illuminate\Database\Eloquent\Model;
use App\Model\BasicModel;


class Member extends BasicModel
{
	protected $hidden = ['created_at','updated_at'];

	public function user()
	{
		return $this->belongsTo('App\Model\User');
	}

}
