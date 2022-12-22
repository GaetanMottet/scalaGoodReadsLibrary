//import BookShelf._

object ServiceExploration {
  def booksFromAuthor(a: String, lb: Library) =
    val aLower = a.toLowerCase()
    lb.listBooks.filter(x => x.author.name.toLowerCase().contains(aLower))

  def bookByTitle(t:String, l: Library)=
    val tLower = t.toLowerCase()
    l.listBooks.filter(x => x.title.toLowerCase().contains(tLower))

  //nbre livres lus par auteur
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

  //sort selon rating
  def sortBooksByMyRating(l:Library): Seq[Book] = {
    val listBooks = l.listBooks
    listBooks.sortBy(b => b.myRating).reverse
  }


  //filter by bookShelf
/* ======================== DOESN'T WORK PROPERLY ==========================
  def filterByBookShelves(s: String, l: Library): Seq[Book] = {
    val listBooks = l.listBooks
    val sLower = s.toLowerCase()
//    val shelfEnum = withName(sLower)
    val shelfEnum = BookShelf.undefined
    listBooks.filter(b => b.exclusiveShelf == shelfEnum)
  }*/

  def filterByBookShelves(s: BookShelf, l: Library): Seq[Book] = {
    val listBooks = l.listBooks
    listBooks.filter(b => b.exclusiveShelf == s)
  }


}
