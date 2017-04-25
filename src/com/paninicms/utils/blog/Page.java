package com.paninicms.utils.blog;

import org.mongodb.morphia.annotations.Entity;
import lombok.*;
import lombok.experimental.Accessors;

@Getter
@Setter
@NoArgsConstructor
@Accessors(fluent = true)
@Entity(value = "pages", noClassnameStored = true)
public class Page extends Post {} // A page is the same thing as an post, so we are going to extend the "Post" class
