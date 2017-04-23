package com.paninicms.plugin;

import java.net.URLClassLoader;

import com.paninicms.plugin.event.PostRenderEvent;
import com.paninicms.utils.RenderContext;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaniniPlugin {
	URLClassLoader classLoader;
	
	public void onEnable() {
		
	}
	
	public void onPreRender(RenderContext context) {
		
	}
	
	public void onPostRender(PostRenderEvent ev) {
		
	}
}
