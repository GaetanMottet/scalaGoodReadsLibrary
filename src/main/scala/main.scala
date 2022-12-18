
import org.apache.spark.{SparkConf,SparkContext}
import org.apache.spark.sql.SparkSession

import scala.collection.mutable.ListBuffer
import scala.io.Source


@main
def main(): Unit = {
  println("Hello world!")

  def myToInt(s: String): Int = {
    try {
      s.toInt
    } catch {
      case e: Exception => 0
    }
  }

  val authorsTemp = new ListBuffer[String]() //instead of ..[Author]()
  val booksTemp = new ListBuffer[Book]()

  val bufferedSource = Source.fromFile("dataSource/03-GoodreadsLibraryExport.csv")

  for (line <- bufferedSource.getLines) {
    val cols = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1).map(_.trim)
    // do whatever you want with the columns here
    val author = Author(cols(2)) //using companion class Author
    if (!authorsTemp.contains(author.name)) then
      authorsTemp += author.name

    val rating = myToInt(cols(7))
    val counter = myToInt(cols(22))

    val book = Book(cols(0),cols(5),cols(1), null,null,cols(9),cols(13),counter,rating,null)
    book.addAuhtor(author)
    book.storeOnShelf(cols(18))

    if (!booksTemp.contains(book.idBook)) then
      booksTemp += book
  }

  authorsTemp.toList
  booksTemp.toList

  //build a list of authors without duplicates
  val authors = authorsTemp.toList

  //print each author
//  for i <- authors
//    do println(i)

  println("authorsTemp.length :  " +authorsTemp.length)
  println("authors.length : " +authors.length)
  println("number of books : " +booksTemp.length)
  //print each book
  for i <- booksTemp
    do println(i.exclusiveShelf)

  bufferedSource.close
}