
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.SparkSession

import scala.collection.mutable.ListBuffer
import scala.io.Source
import scala.util.control.Breaks.break


@main
def main(): Unit = {

  val goodReadsLibrary = new Library("GoodReads Library")

  // loading data from .csv and build authors and publishers objects
  goodReadsLibrary.loadAuthors
  goodReadsLibrary.loadPublishers

  /* Create the list of books */
  goodReadsLibrary.loadBooks

  Menu.displayMenu(goodReadsLibrary)

  println("\n\n")
  println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~")
  println("======================= Service EXPLORATION =======================")
  println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~")
  println("\n")


  println("\n======================= Books BY TITLE =======================")
  val title = "Double"
  val booksWithTitle = ServiceExploration.booksByTitle(title, goodReadsLibrary)
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
//  val bookToIncrement = ServiceExploration.bookByTitle("Pride and Prejudice", goodReadsLibrary)
//  ServiceOperations.incrementReadCount(bookToIncrement)

  println("\n======================= Display books from a specific shelf =======================")
//  val booksFromShelf = ServiceOperations.selectBooksByShelf(goodReadsLibrary)
//  for b <- booksFromShelf do println(b)

  println("\n======================= Change the shelf of a book =======================")
  ServiceOperations.modifyShelf(goodReadsLibrary)


  println("\n======================= Verification =======================")
  print(ServiceExploration.bookByTitle("Foundation (Foundation, #1)", goodReadsLibrary))

}