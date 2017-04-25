package com.paninicms.utils;

import lombok.*;
import lombok.experimental.Accessors;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class PaniniConfig {
	private String websiteUrl;
	private String frontendFolder;
	private String mongoDBDatabase;
	private int port;
}
