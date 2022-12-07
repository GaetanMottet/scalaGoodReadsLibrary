
import org.apache.spark.{SparkConf,SparkContext}
import org.apache.spark.sql.SparkSession

import scala.collection.mutable.ListBuffer
import scala.io.Source


@main
def main(): Unit = {
  println("Hello world!")

  val spark = SparkSession
    .builder()
    .appName("scalaGoodReadsLibrary")
//    .config("spark.some.config.option", "some-value")
    .getOrCreate()

  //https://www.projectpro.io/recipes/handle-comma-column-value-of-csv-file-while-reading-spark-scala

  val dataReader = spark.read
    .format("csv")
    .option("header", "true")
    .option("escapeQuotes", "true")
    .load("dataSource/03-GoodreadsLibraryExport.csv")

  dataReader.show()

//  val authors = new ListBuffer[Author]()
//  val bufferedSource = Source.fromFile("dataSource/03-GoodreadsLibraryExport.csv")
//  for (line <- bufferedSource.getLines.drop(1)) {
//    for (line <- dataReader.map()) {
//      val cols = line.split(",").map(_.trim)
      //col(0) = Book Id, col(1)=title, col(2) = author
      //    println(s"${cols(0)}|${cols(1)}|${cols(2)}|${cols(3)}")
      //
      //    val author = Author(cols(2)) //using companion class Author
      //    if(!authors.contains(author.name)) then
      //      authors += author//new Author(cols(2))
      //
//    }


//  authors.toList
//
//  //print each author
//  for i <- authors
//    do println(i.name)
//
//
//  dataReader.close;

}