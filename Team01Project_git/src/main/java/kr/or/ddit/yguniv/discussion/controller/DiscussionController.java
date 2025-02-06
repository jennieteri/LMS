package kr.or.ddit.yguniv.discussion.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/yguniv/discussion")
public class DiscussionController {
	@GetMapping("request")
	public String selectDiscussionRequestList() {
		return "discussion/discussionRequestList";
	}
	
	@GetMapping("request/{dscsnCd}")
	public String selectDiscussionRequest(@PathVariable String dscsnCd) {
		return "discussion/discussionRequestDetail";
	}
	
	@GetMapping("request/new")
	public String discussionRequestForm() {
		return "discussion/discussionRequestForm";
	}
	
	@PostMapping("request/new")
	public void insertDiscussionRequest() {
		
	}
	
	@PutMapping("request/{dscsnCd}/edit")
	public void updateDiscussionRequest(@PathVariable String dscsnCd) {
		
	}
	
	@DeleteMapping("request/{dscsnCd}")
	public void deleteDiscussionRequest(@PathVariable String dscsnCd) {
		
	}
	
	@PutMapping("request/{dscsnCd}/consent")
	public void consentDiscussionRequest(@PathVariable String dscsnCd) {
		
	}
	
	@PutMapping("request/{dscsnCd}/return")
	public void returnDiscussionRequest(@PathVariable String dscsnCd) {
		
	}
	
	@GetMapping
	public String selectDiscussionList() {
		return "discussion/discussionList";
	}
	
	@GetMapping("{dscsnCd}")
	public String selectDiscussion(@PathVariable String dscsnCd) {
		return "discussion/discussionDetail";
	}
	
	@GetMapping("{dscsnCd}/new")
	public String discussionForm(@PathVariable String dscsnCd) {
		return "discussion/discussionForm";
	}
	
	@PostMapping("{dscsnCd}/new")
	public void insertDiscussion() {
		
	}
	
	@PutMapping("{dscsnCd}/edit")
	public void updateDiscussion() {
		
	}
}
