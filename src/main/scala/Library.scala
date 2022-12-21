import scala.:+
import scala.collection.mutable.ListBuffer
import scala.io.Source

class Library(val name: String){
  var authorsList = Seq.empty[Author]
  var publishersList = Seq.empty[BookPublisher]
  var listBooks = Seq.empty[Book]


  /*
1.  a. from buffer : créer la liste des auteurs
    b. modifier de val à toSeq ou map (pour pouvoir récupérer chaque auteur au besoin
2.  a. from buffer : créer la liste des éditeurs
    b. modifier de val à map pour récupérer pour chaque livre
3.  a. idem si on veut pour CoAuthors
4.  a. from buffer : créer la liste des livres avec le bon auteur et le bon éditeur
 */

  def loadAuthors = {
    val bufferedSource = Source.fromFile("dataSource/03-GoodreadsLibraryExport.csv")

    val authorsNameTemp = ListBuffer[String]()

    /*
  1 a. from buffer : create a list of authors' name without duplicate.
                     Same for editor's name (publishers) */

    for (line <- bufferedSource.getLines) {
      val cols = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1).map(_.trim)

      //Check null here ??
      val authorName = cols(2)
      //if this author's name doesn't exist, then add it to the authorsNameTemp
      if !authorsNameTemp.contains(authorName) then authorsNameTemp += authorName
    }

    bufferedSource.close // even if it is not closed, it seems not to be reusable later in the code. So might as well close it...

    // 1 b. Create a List of all authors (objects) in purpose to reuse it when necessary. For instance when creating a book
    authorsNameTemp.toList // transform the ListBuffer into a list

    //create a mutable list where add all authors
    val authorsListBuffer = new ListBuffer[Author]()

    for (i <- authorsNameTemp) {
      val author = Author(i)
      authorsListBuffer.addOne(author)
    }

    authorsList = authorsListBuffer.toSeq

  }

  // loading Publishers
  def loadPublishers = {
    val bufferedSource = Source.fromFile("dataSource/03-GoodreadsLibraryExport.csv")
    val publishersNameTemp = ListBuffer[String]()

    /*
  1 a. from buffer : create a list of authors' name without duplicate.
                     Same for editor's name (publishers)
  */
    for (line <- bufferedSource.getLines) {
      val cols = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1).map(_.trim)

      val publisherName = cols(9)
      //if this publisher's name doesn't exist, then add it to the publishersNameTemp
      if !publishersNameTemp.contains(publisherName) then publishersNameTemp += publisherName
    }
    bufferedSource.close // even if it is not closed, it seems not to be reusable later in the code. So might as well close it...

    // 1 b. Create a List of all authors (objects) in purpose to reuse it when necessary. For instance when creating a book
    publishersNameTemp.toList // transform the ListBuffer into a list

    //create a mutable list where add all authors
    val publishersListBuffer = new ListBuffer[BookPublisher]()

    for (i <- publishersNameTemp) {
      val publisher = BookPublisher(i)
      publishersListBuffer.addOne(publisher)
    }

    publishersList = publishersListBuffer.toSeq
  }

  def loadBooks = {
    val bufferedSourceForBooks = Source.fromFile("dataSource/03-GoodreadsLibraryExport.csv")
    val booksTemp = new ListBuffer[Book]()
    for (line <- bufferedSourceForBooks.getLines) {
      val cols = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1).map(_.trim)

      val rating = myToInt(cols(7))
      val counter = myToInt(cols(22))
      val authorName = cols(2)
      if (cols(2).equals(" ")) then println("LDJKFASDFASD")

      //get the author in authorList, according to his/her name
      var author = Author("")
      for (i <- authorsList) {
        if i.name.contains(authorName) then {
          author = i
          addWrittenBookTo(author)
          //        i.nbBooks += 1 // increments the number of written books for this author
        }
      }

      //get the publisher from publishersList, according to his/her name
      val publisherName = cols(9)
      var publisher = BookPublisher("")
      for (i <- publishersList) {
        if i.name.contains(publisherName) then {
          publisher = i
          addPublishedBookTo(publisher)
        }
      }

      //book constructor needs : idBook, isbn, title, author, coAuthors:Seq[CoAuthor]=Seq.empty, publisher, originalPublicYear, readCount, myRating, exclusiveShelf:BookShelf
      val book = Book(cols(0), Some(cols(5)), cols(1), author, null, publisher, Some(cols(13)), counter, rating, null)
      //add the shelf
      var bookExt = book.storeOnShelf(cols(18))

      if (!booksTemp.contains(book.idBook)) then
        booksTemp += bookExt
    }
    println("Here we actually must have finished with books...")
    bufferedSourceForBooks.close

    listBooks = booksTemp.toSeq
  }

  def myToInt(s: String): Int = {
    try {
      s.toInt
    } catch {
      case e: Exception => 0
    }
  }

}
