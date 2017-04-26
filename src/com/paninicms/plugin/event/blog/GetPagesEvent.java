package com.paninicms.plugin.event.blog;

import java.util.List;

import com.paninicms.utils.blog.Page;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetPagesEvent {
	private List<Page> loadedPages;
}
