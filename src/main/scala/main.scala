
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

  val bufferedSource = Source.fromFile("dataSource/03-GoodreadsLibraryExport.csv")

  /*
  1.  a. from buffer : créer la liste des auteurs
      b. modifier de val à toSeq ou map (pour pouvoir récupérer chaque auteur au besoin
  2.  a. from buffer : créer la liste des éditeurs
      b. modifier de val à map pour récupérer pour chaque livre
  3.  a. idem si on veut pour CoAuthors
  4.  a. from buffer : créer la liste des livres avec le bon auteur et le bon éditeur
   */


  val authorsNameTemp = ListBuffer[String]()
  val publishersNameTemp = ListBuffer[String]()
  /*
  1 a. from buffer : create a list of authors' name without duplicate.
                     Same for editor's name (publishers)
  */
  for (line <- bufferedSource.getLines) {
    val cols = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1).map(_.trim)

    //Check null here ??
    val authorName = cols(2)
    //if this author's name doesn't exist, then add it to the authorsNameTemp
    if !authorsNameTemp.contains(authorName) then authorsNameTemp += authorName


    val publisherName = cols(9)
    //if this publisher's name doesn't exist, then add it to the publishersNameTemp
    if !publishersNameTemp.contains(publisherName) then publishersNameTemp += publisherName

  }
  bufferedSource.close // even if it is not closed, it seems not to be reusable later in the code. So might as well close it...

  // 1 b. Create a List of all authors (objects) in purpose to reuse it when necessary. For instance when creating a book
  authorsNameTemp.toList // transform the ListBuffer into a list

  val authorsListBuffer = new ListBuffer[Author]() //create a mutable list where add all authors
  for (i<-authorsNameTemp) {
    val author = Author(i)
    authorsListBuffer.addOne(author)
  }
  val authorsList = authorsListBuffer.toList //modify authors in an immutable list

  //for printing all authors :
//  for i<- authorsList do println(i)
  //...or a specific one :
  for (i <- authorsList) do if(i.name.contains("J.R.R.")) then {
//    i.nbBooks += 1 // to test if nbBooks is correctly accessible (works !)
    println(i)
  }

//   1 c. Same as authors but for publishers
 publishersNameTemp.toList
//
  val publishersListBuffer = new ListBuffer[BookPublisher]()
  for (i <- publishersNameTemp) {
    val publisher = BookPublisher(i)
    publishersListBuffer.addOne(publisher)
  }
  val publisherList = publishersListBuffer.toList

/*
* Create the list of books
* */

  // It seems to be mandatory to create a new buffer (despite the identical source), even if the "bufferedSource" is not closed
  val bufferedSourceForBooks = Source.fromFile("dataSource/03-GoodreadsLibraryExport.csv")
  val booksTemp = new ListBuffer[Book]()
  for (line <- bufferedSourceForBooks.getLines) {
    val cols = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1).map(_.trim)

    val rating = myToInt(cols(7))
    val counter = myToInt(cols(22))
    val authorName = cols(2)
    if(cols(2).equals(" ")) then println("LDJKFASDFASD")

    //get the author in authorList, according to his/her name
    var author = Author("")
    for (i <- authorsList) {
      if i.name.contains(authorName) then {
        author = i
        addWrittenBookTo(author)
//        i.nbBooks += 1 // increments the number of written books for this author
      }
    }
    //book constructor needs : idBook, isbn, title, author, coAuthors:Seq[CoAuthor]=Seq.empty, publisher, originalPublicYear, readCount, myRating, exclusiveShelf:BookShelf
    val book = Book(cols(0),cols(5),cols(1), author,null,cols(9),cols(13),counter,rating,null)
    //add the shelf
    var bookExt = book.storeOnShelf(cols(18))

    if (!booksTemp.contains(book.idBook)) then
      booksTemp += bookExt
  }
  println("Here we actually must have finished with books...")
  bufferedSourceForBooks.close

  val bookList = booksTemp.toList

  // ============================== STARTING WITH SERVICE (GoodReadsLibraryService) ======================================

  //looking for a specific book
  println("======================= Books with title containing 'Double Trouble' =======================")
  val title = "Double Trouble"
  val booksWithTitle = ServiceExploration.bookByTitle(title, bookList)
  for i <-booksWithTitle do {
    println(i)
  }

  //looking for a book according to its author's name
  println("======================= Books from Franklin W. Dixon =======================")
  val authorName = "Franklin W. Dixon"
  val books = ServiceExploration.booksFromAuthor(authorName, bookList)
  for i <- books do {
    println(i.title +" from " +i.author)
  }

}