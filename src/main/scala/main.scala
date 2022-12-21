
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.SparkSession
import org.glassfish.jersey.internal.jsr166.Flow.Publisher

import scala.collection.mutable.ListBuffer
import scala.io.Source
import scala.util.control.Breaks.break


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

  val goodReadsLibrary = new Library("GoodReads Library")

  // loading data from .csv
  goodReadsLibrary.loadAuthors
  goodReadsLibrary.loadPublishers

  /* Create the list of books */
  goodReadsLibrary.loadBooks


  println("======================= Books with title containing 'Double Trouble' =======================")
  val title = "Double Trouble"
  val booksWithTitle = ServiceExploration.bookByTitle(title, goodReadsLibrary.listBooks)
  for i <-booksWithTitle do {
    println(i)
  }

  //looking for a book according to its author's name
  println("======================= Books from Franklin W. Dixon =======================")
  val authorName = "Franklin W. Dixon"
  val books = ServiceExploration.booksFromAuthor(authorName, goodReadsLibrary.listBooks)
  for i <- books do {
    println(i.title +" from " +i.author)
  }

  //number of books read from an specific author
  println("Number of read books written by J.R.R : " +ServiceExploration.readBooksFromAuthor("dasdf", goodReadsLibrary))

  //sort list books alphabetically
  val sortedList = ServiceExploration.sortBooksByMyRating(goodReadsLibrary)

//  for i <- sortedList do {
//    println(i.title + " : " +i.myRating)
//  }

  //test filter on BookShelf
  val filteredByShelf = ServiceExploration.filterByBookShelves("read", goodReadsLibrary)

  for i <- filteredByShelf do println(i.title + " " +i.exclusiveShelf)

}