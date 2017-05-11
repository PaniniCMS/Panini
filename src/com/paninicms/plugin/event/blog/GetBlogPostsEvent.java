package com.paninicms.plugin.event.blog;

import java.util.List;

import com.paninicms.utils.blog.Post;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetBlogPostsEvent {
	private List<Post> loadedPosts;
}
