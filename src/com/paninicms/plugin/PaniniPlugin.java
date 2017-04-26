package com.paninicms.plugin;

import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

import com.paninicms.plugin.event.ListenerAdapter;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaniniPlugin extends ListenerAdapter {
	URLClassLoader classLoader;
	private List<ListenerAdapter> listenerAdapters = new ArrayList<ListenerAdapter>();
	
	public void onEnable() {
		
	}
	
	public void registerListener(ListenerAdapter listenerAdapter) {
		listenerAdapters.add(listenerAdapter);
	}
}
