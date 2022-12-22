import org.fusesource.hawtjni.runtime.Library

import scala.:+
import scala.collection.mutable.ListBuffer
import scala.io.Source

//Class that store every data loaded or created
class Library(val name: String){
  var authorsList = Seq.empty[Author]
  var publishersList = Seq.empty[BookPublisher]
  var listBooks = Seq.empty[Book]

  //Load everything before beginning program
  def initLibrary = {
    loadAuthors
    loadPublishers
    loadBooks
  }

  // loading Authors
  def loadAuthors = {
    val bufferedSource = Source.fromFile("dataSource/03-GoodreadsLibraryExport.csv")

    val authorsNameTemp = ListBuffer[String]()

    //1a. from buffer : create a list of authors' name without duplicate.
    for (line <- bufferedSource.getLines) {
      val cols = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1).map(_.trim)

      val authorName = cols(2)
      //if this author's name doesn't exist, then add it to the authorsNameTemp
      if !authorsNameTemp.contains(authorName) then authorsNameTemp += authorName
    }
    bufferedSource.close

    // 1 b. Create a List of all authors (objects) in purpose to reuse it when necessary. For instance when creating a book
    authorsNameTemp.toList // transform the ListBuffer into a list

    //1c. Create a mutable list where add all authors
    val authorsListBuffer = new ListBuffer[Author]()
    for (i <- authorsNameTemp) {
      val author = Author(i,0)
      authorsListBuffer.addOne(author)
    }

    authorsList = authorsListBuffer.toSeq
  }

  // loading Publishers
  def loadPublishers = {
    val bufferedSource = Source.fromFile("dataSource/03-GoodreadsLibraryExport.csv")
    val publishersNameTemp = ListBuffer[String]()

  //1a. from buffer : create a list of publisher's name without duplicate.
    for (line <- bufferedSource.getLines) {
      val cols = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1).map(_.trim)

      val publisherName = cols(9)
      //if this publisher's name doesn't exist, then add it to the publishersNameTemp
      if !publishersNameTemp.contains(publisherName) then publishersNameTemp += publisherName
    }
    bufferedSource.close

    // 1b. Create a List of all publishers (objects) in purpose to reuse it when necessary. For instance when creating a book
    publishersNameTemp.toList // transform the ListBuffer into a list

    // 1c. Create a mutable list where add all publishers
    val publishersListBuffer = new ListBuffer[BookPublisher]()
    for (i <- publishersNameTemp) {
      val publisher = BookPublisher(i,0)
      publishersListBuffer.addOne(publisher)
    }
    publishersList = publishersListBuffer.toSeq
  }

  def loadBooks = {
    val bufferedSourceForBooks = Source.fromFile("dataSource/03-GoodreadsLibraryExport.csv")
    val booksTemp = new ListBuffer[Book]()
    for (line <- bufferedSourceForBooks.getLines) {
      val cols = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1).map(_.trim)

      var rating = 0
      var counter = 0
      var authorName = ""
      rating = myToInt(cols(7))
      counter = myToInt(cols(22))
      authorName = cols(2)

      //get the author in authorList, according to his/her name & Create the author if doesn't exist yet
      val author = checkAuthor(authorName)
      addWrittenBookTo(author) // increments the number of written books for this author
4
      //get the publisher from publishersList, according to his/her name
      val publisherName = cols(9)
      val publisher = checkPublisher(publisherName)
      addPublishedBookTo(publisher)

      var book = Book(cols(0), Some(cols(5)), cols(1), author, publisher, Some(cols(13)), counter, rating, null)
      //add the corresponding shelf
      var bookExt = book.storeOnShelf(cols(18))
      if (!booksTemp.contains(book.idBook)) then
        booksTemp += bookExt

      bookExt = null
    }
    println("All Data have been loaded now ! ")
    bufferedSourceForBooks.close
    listBooks = booksTemp.toSeq
  }

  //Check that the object doesn't already exist in the list before adding it
  def checkAuthor(authorName: String): Author = {
    var author = Author("",0)
    for (i <- authorsList) {
      if i.name.contains(authorName) then {
        author = i
      } else {
        author = Author(authorName,0)
      }
    }
    author
  }

  //Check that the object doesn't already exist in the list before adding it
  def checkPublisher(publisherName: String): BookPublisher = {
    var publisher = BookPublisher("",0)
    for (i <- publishersList) {
      if i.name.contains(publisherName) then {
        publisher = i
      } else {
        publisher = BookPublisher(publisherName,0)
      }
    }
    publisher
  }

  //To transform a string to an int
  def myToInt(s: String): Int = {
    try {
      s.toInt
    } catch {
      case e: Exception => 0
    }
  }

}
