package com.twolattes.json;

import java.util.Set;

import com.twolattes.json.Entity;
import com.twolattes.json.Value;

@Entity
public class ContravariantCollectionEntity {
	@Value
	private Set<? super String> oupsy;

	public Set<? super String> getOupsy() {
		return oupsy;
	}
}
