package com.paninicms.utils.blog;

import java.util.HashMap;
import java.util.Map;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.IndexOptions;
import org.mongodb.morphia.annotations.Indexed;
import org.mongodb.morphia.annotations.Reference;
import org.mongodb.morphia.annotations.Transient;

import lombok.*;
import lombok.experimental.Accessors;

@Getter
@Setter
@NoArgsConstructor
@Accessors(fluent = true)
@Entity(value = "posts", noClassnameStored = true)
public class Post {
	@Id
	@Indexed(options = @IndexOptions(unique = true))
	ObjectId id = new ObjectId(); // ID do Post
	
	@Reference
	Author author; // Autor do post
	
	String slug; // Slug do post
	
	String title; // Título do post
	
	long postedIn; // Quando o post foi postado
	
	String markdownContent; // Conteúdo em formato markdown
	
	Map<String, Object> metadata = new HashMap<String, Object>(); 
	
	@Transient
	transient String content; // Conteúdo em formato html
}
