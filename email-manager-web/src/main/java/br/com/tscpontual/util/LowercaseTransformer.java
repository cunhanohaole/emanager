package br.com.tscpontual.util;

import org.apache.commons.collections.Transformer;

public class LowercaseTransformer implements Transformer {

	@Override
	public Object transform(Object input) {
		return ((String)input).toLowerCase();
	}

}
