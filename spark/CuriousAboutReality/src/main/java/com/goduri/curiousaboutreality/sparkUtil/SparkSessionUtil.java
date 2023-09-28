package com.goduri.curiousaboutreality.sparkUtil;

import java.util.List;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import org.apache.spark.ml.feature.CountVectorizer;
import org.apache.spark.ml.feature.CountVectorizerModel;
import org.apache.spark.ml.feature.IDFModel;
import org.apache.spark.ml.feature.IDF;

import org.apache.spark.SparkConf;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.ArrayType;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructField;

import scala.Tuple2;

public class SparkSessionUtil {

	private static SparkSessionUtil sparkUtil;

	private final JavaSparkContext javaSparkContext;
	private final SparkSession sparkSession;

	/*
		spark 연결을 끊고 연결하고 하는게 오버헤드라고 생각해서
		그냥 싱글톤으로 만들고 계속 사용.
	 */
	public static SparkSessionUtil getInstance(){
		if(sparkUtil == null){
			sparkUtil = new SparkSessionUtil();
		}
		return sparkUtil;
	}

	private SparkSessionUtil(){
		javaSparkContext = new JavaSparkContext("local[2]","jwSpark");
		sparkSession = SparkSession.builder().appName("jwSpark").master("local[2]").getOrCreate();
	}

	private SparkConf initSparkUtil(){
		return new SparkConf().setMaster("local[2]").setAppName("SparkTFIDF");
	}


	public Dataset<Row> makeDataset(List<List<String>> wordsList){
		JavaRDD<Row> rowRdd = javaSparkContext.parallelize(wordsList).map(words -> RowFactory.create((Object)words.toArray(new String[0])));
		return sparkSession.createDataFrame(rowRdd, DataTypes.createStructType(new StructField[]{
				DataTypes.createStructField("words", new ArrayType(DataTypes.StringType, true), false)
			})
		);
	}

	public Tuple2<CountVectorizerModel, IDFModel> fitModels(Dataset<Row> dataset){
		CountVectorizer cv = new CountVectorizer()
			.setInputCol("words")
			.setOutputCol("rawFeatures");

		CountVectorizerModel cvm = cv.fit(dataset);

		Dataset<Row> featurizedData = cvm.transform(dataset);

		IDF idf = new IDF()
			.setMinDocFreq(0)
			.setInputCol("rawFeatures")
			.setOutputCol("features");

		IDFModel idfm = idf.fit(featurizedData);

		return new Tuple2<>(cvm, idfm);
	}
}
