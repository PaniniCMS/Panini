package com.paninicms.plugin;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

import com.paninicms.plugin.event.Listener;
import com.paninicms.plugin.event.SubscribeEvent;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaniniPlugin {
	URLClassLoader classLoader;
	private List<Listener> listeners = new ArrayList<Listener>();
	
	public void onEnable() {
		
	}
	
	public void registerListener(Listener listener) {
		listeners.add(listener);
	}
	
	public static void executeEvent(Listener listener, Object event) {
		Class clazz = listener.getClass();
		
		for (Method method : clazz.getDeclaredMethods()) {
			System.out.println("Method: " + method.getName());
			if (method.isAnnotationPresent(SubscribeEvent.class)) {
				System.out.println("Yes, it is present!");
				if (method.getParameterTypes().length == 1 && method.getParameterTypes()[0] == event.getClass()) {
					try {
						method.invoke(listener, event);
					} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
}
