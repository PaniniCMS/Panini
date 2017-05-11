package com.paninicms.plugin.event.blog;

import com.paninicms.utils.RenderContext;
import com.paninicms.utils.blog.Page;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ReadBlogPageEvent {
	private Page page;
	private RenderContext context;
}
