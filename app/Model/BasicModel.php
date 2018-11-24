<?php
/**
 * Created by PhpStorm.
 * Users: HP
 * Date: 2018/11/24
 * Time: 10:48
 */
namespace App\Model;

use Illuminate\Database\Eloquent\Model;

class BasicModel extends Model
{
	public static function withCertain($relatedModel, $columns = ['id'])
	{
		return self::with([$relatedModel => function ($query) use ($columns) {
			$query->select($columns);
		}]);
	}
}