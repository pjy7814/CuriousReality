package com.goduri.curiousaboutreality.util;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;

import ai.bareun.tagger.Tagged;
import ai.bareun.tagger.Tagger;

public class BareunUtil {
	@Value("${bareun.key}")
	String key;
	public List<String> getMorphologicalAnalysis(String mainText){
		Tagged tag = new Tagger("localhost", key).tag(mainText);
		return tag.nouns();
	}
}
