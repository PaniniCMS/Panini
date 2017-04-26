package com.paninicms.plugin;

import java.net.URLClassLoader;

import com.paninicms.plugin.event.GetPagesEvent;
import com.paninicms.plugin.event.GetPostsEvent;
import com.paninicms.plugin.event.PostRenderEvent;
import com.paninicms.plugin.event.PreRenderEvent;
import com.paninicms.plugin.event.ReadPageEvent;
import com.paninicms.plugin.event.ReadPostEvent;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaniniPlugin {
	URLClassLoader classLoader;
	
	public void onEnable() {
		
	}
	
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
