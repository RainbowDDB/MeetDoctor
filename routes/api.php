<?php

use Illuminate\Http\Request;

/*
|--------------------------------------------------------------------------
| API Routes
|--------------------------------------------------------------------------
|
| Here is where you can register API routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| is assigned the "api" middleware group. Enjoy building your API!
|
*/

// Auth

//模拟登陆接口
Route::get('/mock','Mock\MockController@mock');
//登陆接口
Route::post('/user/login','Auth\AuthController@login');
//注册接口
Route::get('/user/registered','Auth\AuthController@register');
//用户名查重接口
Route::get('/user/check','Auth\AuthController@checkname');
//登陆状态检验
Route::get('/user/checklogin','Auth\AuthController@checklogin');

// Ask

//问询接口
Route::post('/ask/answer','AskController@ask');
//进入问询页面查询状态接口
Route::get('/ask/state','AskController@enter');

// Member

//用户对象列表加载
Route::get('/person/GetMemberList','PersonController@GetMemberList');
//用户创建对象
Route::get('/person/CreateMember','PersonController@CreateMember');