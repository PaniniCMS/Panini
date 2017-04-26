package com.paninicms.plugin.event.blog;

import com.paninicms.utils.RenderContext;
import com.paninicms.utils.blog.Post;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ReadPostEvent {
	private Post post;
	private RenderContext context;
}
