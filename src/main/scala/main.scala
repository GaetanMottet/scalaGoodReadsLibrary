
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

  println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~")
  println("======================= Service EXPLORATION =======================")
  println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~")
  println("\n")


  println("\n======================= Books BY TITLE =======================")
  val title = "Pride and Prejudice"
  val booksWithTitle = ServiceExploration.bookByTitle(title, goodReadsLibrary)
  for i <-booksWithTitle do {
    println(i)
  }

  //looking for a book according to its author's name
  println("\n======================= Books FROM SPECIFIC AUTHOR =======================")
  val authorName = "Franklin W. Dixon"
  val books = ServiceExploration.booksFromAuthor(authorName, goodReadsLibrary)
//  for i <- books do {
//    println(i.title +" from " +i.author)
//  }

  //number of books read from an specific author
  println("\n======================= Books READ FROM SPECIFIC AUTHOR =======================")
  println("Number of read books written by J.R.R : " +ServiceExploration.readBooksFromAuthor("J.R.r", goodReadsLibrary))

  //sort list books alphabetically
  println("\n======================= ALL Books sorted alphabetically =======================")
  val sortedList = ServiceExploration.sortBooksByMyRating(goodReadsLibrary)
//  for i <- sortedList do {
//    println(i.title + " : " +i.myRating)
//  }

  //test filter on BookShelf
  println("\n======================= Books by SHELF =======================")
  val filteredByShelf = ServiceExploration.filterByBookShelves(BookShelf.read, goodReadsLibrary)
//  for i <- filteredByShelf do println(i.title + " from the shelf : " +i.exclusiveShelf)

  println("\n")
  println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~")
  println("======================= Service OPERATIONS =======================")
  println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~")
  println("\n")

  println("\n======================= add a new book =======================")
//  val myNewBook = ServiceOperations.addBook(goodReadsLibrary)
//  println(myNewBook)

  println("\n======================= average of ratings =======================")
  val average = ServiceOperations.avgMyRatings(goodReadsLibrary)
  print(average)

  println("\n======================= increment the read count for a book =======================")
  val bookToIncrement = ServiceExploration.bookByTitle("Pride and Prejudice", goodReadsLibrary)
  ServiceOperations.incrementReadCount(bookToIncrement.head)



}