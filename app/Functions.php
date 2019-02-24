<?php

use Illuminate\Http\Request;

function GetUserId(Request $r)
{
	if ($r->session()->get('userid')) {
		return $r->session()->get('userid');
	} else {
		return false;
	}
}
