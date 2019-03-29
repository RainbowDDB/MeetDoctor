<?php

use Illuminate\Http\Request;

function SaveUserId(Request $r, $userid)
{
	$r->session()->put('userid', $userid);
}

function GetUserId(Request $r)
{
	if ($r->session()->get('userid')) {
		return $r->session()->get('userid');
	} else {
		return false;
	}
}

function SaveUserMember(Request $r, $memberid)
{
	$r->session()->put('user_member', $memberid);
}

function GetUserMember(Request $r)
{
	if ($r->session()->get('user_member')) {
		return $r->session()->get('user_member');
	} else {
		return false;
	}
}
