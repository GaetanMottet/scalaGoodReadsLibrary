
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

  val authorsNameTemp = new ListBuffer[String]() //instead of ..[Author]()
  val authorsListTemp = new ListBuffer[Author]()
  val booksTemp = new ListBuffer[Book]()
  var reuseAuthorCounter = 0;

  val bufferedSource = Source.fromFile("dataSource/03-GoodreadsLibraryExport.csv")

  for (line <- bufferedSource.getLines) {
    val cols = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1).map(_.trim)

    val authorName = cols(2)
    var author = new Author("")
    if (!authorsNameTemp.contains(authorName)) then {
      authorsNameTemp += authorName
      author = Author(authorName)
      authorsListTemp += author
    } else reuseAuthorCounter+=1

    val rating = myToInt(cols(7))
    val counter = myToInt(cols(22))

    val book = Book(cols(0),cols(5),cols(1), author,null,cols(9),cols(13),counter,rating,null)
//    book.addAuthor(author)
    var bookExt = book.storeOnShelf(cols(18))

    if (!booksTemp.contains(book.idBook)) then
      booksTemp += bookExt
  }

  authorsNameTemp.toList
  booksTemp.toList

  //build a list of authors without duplicates
  val authors = authorsListTemp.toList

  for i<-authors do {
    var countName = 0;
    if(authors.contains(i.name)) then countName+=1
    if(countName > 1) then println("ERROR : Duplicated author !")
  }

  //print each author
//  for i <- authors
//    do println(i)

  println("authorsNameTemp.length :  " +authorsNameTemp.length)
  println("authors.length : " +authors.length)
  println("number of books : " +booksTemp.length)
  println("ReuseCounter : " +reuseAuthorCounter)
  //print each book
  for i <- booksTemp
    do println("Title: " +i.title +" from " +i.author.name.toUpperCase + ", stored on shelf : "+i.exclusiveShelf +", red " +i.readCount +"x.")

  //print all authors (objects)
//  for i <- authors
//    do println(i)

  bufferedSource.close
}