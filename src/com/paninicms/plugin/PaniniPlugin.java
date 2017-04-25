package com.paninicms.plugin;

import java.net.URLClassLoader;

import com.paninicms.plugin.event.GetPostsEvent;
import com.paninicms.plugin.event.PostRenderEvent;
import com.paninicms.plugin.event.PreRenderEvent;

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
	
	public void onGetPost(GetPostsEvent ev) {
		
	}
}
