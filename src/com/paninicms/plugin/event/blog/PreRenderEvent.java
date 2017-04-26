package com.paninicms.plugin.event.blog;

import com.paninicms.utils.RenderContext;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PreRenderEvent {
	private RenderContext renderContext;
}
