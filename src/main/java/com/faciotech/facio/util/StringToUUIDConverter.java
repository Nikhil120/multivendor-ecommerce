package com.faciotech.facio.util;

import java.util.UUID;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import io.micrometer.common.lang.NonNull;

@Component
public class StringToUUIDConverter implements Converter<String, UUID> {

	@Override
	public UUID convert(@NonNull String uuid) {
		return UUID.fromString(uuid);
	}

}
