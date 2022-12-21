import BookShelf._

object ServiceExploration {
  def booksFromAuthor(a:String, l:List[Book])=
    l.filter(x => x.author.name.contains(a))

  def booksFromAuthor(a: String, l: Seq[Book]) =
    l.filter(x => x.author.name.contains(a))

  def booksFromAuthor(a:Author, l:List[Book])=
    l.filter(x => x.author.name.contains(a.name))

  def booksFromAuthor(a: Author, l: Seq[Book]) =
    l.filter(x => x.author.name.contains(a.name))

  def bookByTitle(t:String, lb:List[Book])=
    val tLower = t.toLowerCase()
    lb.filter(x => x.title.toLowerCase().contains(tLower))

  def bookByTitle(t: String, lb: Seq[Book]) =
    val tLower = t.toLowerCase()
    lb.filter(x => x.title.toLowerCase().contains(tLower))

  //nbre livres lus par auteur
  def readBooksFromAuthor(an: String, l:Library): String = {
    //check if author exists
    val listBooks = l.listBooks
    val booksByAuthor = booksFromAuthor(an, listBooks)
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


  //filtrer selon bookShelves
  def filterByBookShelves(s: String, l: Library): Seq[Book] = {
    val listBooks = l.listBooks
    val sLower = s.toLowerCase()
    val shelfEnum = withName(sLower)

    listBooks.filter(b => b.exclusiveShelf == shelfEnum)
  }


}
