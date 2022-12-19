
import org.apache.spark.{SparkConf, SparkContext}
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
    } else {
      var index = authorsListTemp.indexOf(Author(authorName))
      authorsListTemp(index).nbBooks + 1
//      println("nbBooks for " +authorsListTemp(index) +" = " +authorsListTemp(index).nbBooks)
    } //reuseAuthorCounter+=1

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
  val authors = authorsListTemp.toSeq

  val authorsControl = authors.groupBy(identity).map{
    case(key,value) => (key, value.length)
  }

  var errorCount = 0
  for (i<-authorsControl) {
    if(i._2 > 1) then  {
      println("Error for " +i._1 +", (s)he is duplicated " +i._2 +"x")
      errorCount += 1
    }
  }
  if (errorCount == 0) then {
    println("No duplicate found")
  }

  //print each author
//  for i <- authors
//    do println(i)

  println("authorsNameTemp.length :  " +authorsNameTemp.length)
  println("authors.length : " +authors.length)
  println("number of books : " +booksTemp.length)
  println("ReuseCounter : " +reuseAuthorCounter)
  //print each book
//  for i <- booksTemp
//    do println("Book: " +i.title +" from " +i.author.name.toUpperCase + ", stored on shelf: '"+i.exclusiveShelf +"', red " +i.readCount +"x.")

  //print all authors (objects)
  for i <- authors
    do { if(i.nbBooks != 0) then println(i)
    }

  bufferedSource.close
}