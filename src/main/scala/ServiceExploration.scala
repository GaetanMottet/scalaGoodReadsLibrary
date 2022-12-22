import scala.io.StdIn
//import BookShelf._

object ServiceExploration {
  def booksFromAuthor(a: String, lb: Library): Seq[Book] = {
    val aLower = a.toLowerCase()
    val filteredList = lb.listBooks.filter(x => x.author.name.toLowerCase().contains(aLower))
    if(filteredList.isEmpty) then println("No book found for this author")
    filteredList
  }

  def booksByTitle(t: String, l: Library): Seq[Book] = {
    val tLower = t.toLowerCase()
    val filteredList = l.listBooks.filter(x => x.title.toLowerCase().contains(tLower))
    filteredList
  }

  def bookByTitle(t:String, l: Library): Book = {
    val matchingBooks = booksByTitle(t,l)

    if(matchingBooks.isEmpty){
      println("No book found")
      return null
    }
    if(matchingBooks.length > 1) then {

      println("More than one book found. Please select one : ")
      val mapping = matchingBooks.zipWithIndex.toMap
      for b <- mapping.toSeq.sortBy(_._2) do println(b._2 + 1 + ". " + b._1.title)
      var valid = false
      var rep = 0
      while(valid != true) {
        try {
          rep = StdIn.readInt()
          if (rep > 0) {
            valid = true
          } else {
            println("Please enter a valid number from the list")
          }
        } catch {
          case e: NumberFormatException => println("Invalid input. Please enter a valid number from the list.")
        }
      }
      val book = mapping.find(_._2 == rep-1).get._1
      book
    } else
      matchingBooks.head


  }


  //number of read boods by author
  def readBooksFromAuthor(an: String, l:Library): String = {
    //check if author exists
    val booksByAuthor = booksFromAuthor(an, l)
    if(booksByAuthor.length < 1) {
      return "Author not found or no book from this author"
    }
    //filter only read books
    val nbRead = booksByAuthor.filter(_.readCount > 0).map(_.readCount).length
    nbRead.toString
  }

  //sort by myRating
  def sortBooksByMyRating(l:Library): Seq[Book] = {
    val listBooks = l.listBooks
    val sortedList = listBooks.sortBy(b => b.myRating).reverse
    sortedList
  }

}
