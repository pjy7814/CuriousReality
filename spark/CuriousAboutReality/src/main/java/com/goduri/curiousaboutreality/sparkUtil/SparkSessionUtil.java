package com.goduri.curiousaboutreality.sparkUtil;

import java.util.List;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import org.apache.spark.ml.feature.CountVectorizer;
import org.apache.spark.ml.feature.CountVectorizerModel;
import org.apache.spark.ml.feature.IDFModel;
import org.apache.spark.ml.feature.IDF;

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

	/**
	 * spark 연결을 끊고 연결하고 하는게 오버헤드라고 생각해서
	 * 그냥 싱글톤으로 만들고 계속 사용.
	 * 
	 * 또한 하나의 jvm에서는 하나의 스파크 세션이 존재하야 하므로 싱글톤으로 생성
	 */
	public static SparkSessionUtil getInstance(){
		if(sparkUtil == null){
			sparkUtil = new SparkSessionUtil();
		}
		return sparkUtil;
	}

	private SparkSessionUtil(){
		// 여기의 master:local[2]는 local에서 쓰레드 2개를 사용한다는 뜻.
		javaSparkContext = new JavaSparkContext("local[2]","jwSpark");
		sparkSession = SparkSession.builder().appName("jwSpark").master("local[2]").getOrCreate();
	}

	public Dataset<Row> makeDataset(List<List<String>> wordsList){
		JavaRDD<Row> rowRdd = javaSparkContext.parallelize(wordsList).map(words -> RowFactory.create((Object)words.toArray(new String[0])));
		// 메소드 이름은 createDataFrame인데 막상 리턴 타입은 DataSet;;
		return sparkSession.createDataFrame(rowRdd, DataTypes.createStructType(new StructField[]{
				DataTypes.createStructField("words", new ArrayType(DataTypes.StringType, true), false)
			})
		);
	}

	public Tuple2<CountVectorizerModel, IDFModel> fitModels(Dataset<Row> dataset){
		CountVectorizer cv = new CountVectorizer()
			.setInputCol("words")
			.setOutputCol("rawFeatures");
		   // .setMinDF(5);

		CountVectorizerModel cvm = cv.fit(dataset);

		Dataset<Row> featuredData = cvm.transform(dataset);

		IDF idf = new IDF()
			//.setMinDocFreq(2)
			.setInputCol("rawFeatures")
			.setOutputCol("features");

		IDFModel idfModel = idf.fit(featuredData);

		return new Tuple2<>(cvm, idfModel);
	}
}
