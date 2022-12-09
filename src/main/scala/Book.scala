

class Book(val title: String, val bookId: String, author: Author)

object Book:
  def apply(title:String, bookId:String, author: Author) = new Book(title,bookId, author)