
import org.apache.spark.{SparkConf,SparkContext}
import org.apache.spark.sql.SparkSession

import scala.collection.mutable.ListBuffer
import scala.io.Source


@main
def main(): Unit = {
  println("Hello world!")

  val authors = new ListBuffer[Author]()
  val books = new ListBuffer[Book]()

  val bufferedSource = Source.fromFile("dataSource/03-GoodreadsLibraryExport.csv")
  for (line <- bufferedSource.getLines) {
    val cols = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1).map(_.trim)
    // do whatever you want with the columns here
    val author = Author(cols(2)) //using companion class Author
    if (!authors.contains(author.name)) then
      authors += author //new Author(cols(2))

    val book = Book(cols(1),cols(0), author) //using companion class Author
    if (!books.contains(book.bookId)) then
      books += book
  }

  authors.toList
  books.toList

  //print each author
  for i <- authors
    do println(i.name)

  //print each author
  for i <- books
    do println(i.title)

  bufferedSource.close
}