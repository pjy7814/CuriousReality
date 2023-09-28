package com.goduri.curiousaboutreality.sparkUtil;

import java.util.List;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.mllib.feature.HashingTF;
import org.apache.spark.mllib.feature.IDF;
import org.apache.spark.mllib.linalg.Vector;
import org.apache.spark.sql.SparkSession;

public class SparkUtil {

	private static SparkUtil sparkUtil;

	private final JavaSparkContext javaSparkContext;
	private final SparkSession sparkSession;

	/*
		spark 연결을 끊고 연결하고 하는게 오버헤드라고 생각해서
		그냥 싱글톤으로 만들고 계속 사용.
	 */
	public static SparkUtil getInstance(){
		if(sparkUtil == null){
			sparkUtil = new SparkUtil();
		}
		return sparkUtil;
	}

	private SparkUtil(){
		javaSparkContext = new JavaSparkContext("local[2]","jwSpark");
		sparkSession = SparkSession.builder().appName("jwSpark").master("local[2]").getOrCreate();
	}

	private SparkConf initSparkUtil(){
		return new SparkConf().setMaster("local[2]").setAppName("SparkTFIDF");
	}

	public JavaRDD<List<String>> makeRDD(List<List<String>> wordsList){
		return javaSparkContext.parallelize(wordsList);
	}

	public JavaRDD<Vector> calculateTF(JavaRDD<List<String>> javaRdd){
		// numFeature가 power of 2를 권장
		HashingTF hashingTF = new HashingTF(16384);
		return hashingTF.transform(javaRdd);
	}

	public JavaRDD<Vector> calculateIDF(JavaRDD<Vector> tfv){
		return new IDF(1).fit(tfv).transform(tfv);
	}

}
