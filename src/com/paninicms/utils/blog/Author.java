package com.paninicms.utils.blog;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.IndexOptions;
import org.mongodb.morphia.annotations.Indexed;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity(value = "authors", noClassnameStored = true)
public class Author {
	@Id
	@Indexed(options = @IndexOptions(unique = true))
	private ObjectId id = new ObjectId();
	
	@Indexed(options = @IndexOptions(unique = true))
	private String username;
	
	private String displayName;
	private String password; // SHA256 wow
	private String avatar; // wow avatar
}
