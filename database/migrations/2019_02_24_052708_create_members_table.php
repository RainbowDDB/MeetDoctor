<?php

use Illuminate\Support\Facades\Schema;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Database\Migrations\Migration;

class CreateMembersTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('members', function (Blueprint $table) {
            $table->increments('id');
            $table->integer('user_id')->comment('users表外键');
            $table->string('name')->comment('对象名字');
            $table->string('alias_name')->comment('对象备注')->nullable();
            $table->boolean('sex')->default(true)->comment('性别 true:男;false:女');
            $table->float('weight')->comment('体重')->nullable();
            $table->float('height')->comment('身高')->nullable();
            $table->date('birthday')->comment('出生日期')->nullable();
            $table->timestamps();
			$table->index('user_id');
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::dropIfExists('members');
    }
}
