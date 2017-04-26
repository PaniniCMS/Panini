package com.paninicms.plugin.event;

import com.paninicms.plugin.event.blog.GetPagesEvent;
import com.paninicms.plugin.event.blog.GetPostsEvent;
import com.paninicms.plugin.event.blog.PostRenderEvent;
import com.paninicms.plugin.event.blog.PreRenderEvent;
import com.paninicms.plugin.event.blog.ReadPageEvent;
import com.paninicms.plugin.event.blog.ReadPostEvent;

public class ListenerAdapter {
	public void onPreRender(PreRenderEvent ev) {
		
	}
	
	public void onPostRender(PostRenderEvent ev) {
		
	}
	
	public void onBlogPostsLoaded(GetPostsEvent ev) {
		
	}

	public void onBlogPagesLoaded(GetPagesEvent ev) {
		
	}
	
	public void onReadPostEvent(ReadPostEvent readPostEvent) {

	}
	
	public void onReadPageEvent(ReadPageEvent readPageEvent) {

	}
}
