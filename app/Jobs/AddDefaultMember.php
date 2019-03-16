<?php

namespace App\Jobs;

use Illuminate\Bus\Queueable;
use Illuminate\Queue\SerializesModels;
use Illuminate\Queue\InteractsWithQueue;
use Illuminate\Contracts\Queue\ShouldQueue;
use Illuminate\Foundation\Bus\Dispatchable;
use App\Model\User;
use App\Model\Member;

class AddDefaultMember implements ShouldQueue
{
	use Dispatchable, InteractsWithQueue, Queueable, SerializesModels;

	/**
	 * Create a new job instance.
	 *
	 * @return void
	 */
	private $userid;
	public function __construct($userid)
	{
		$this->userid = $userid;
	}

	/**
	 * Execute the job.
	 *
	 * @return void
	 */
	public function handle()
	{
		$new_id = Member::CreateMember($this->userid,config('Person.Member.DefaultName'));
		User::ModifyUser($this->userid,['latest_member'=>$new_id]);
	}
}
