trait Person:
  val name:String

case class Author(name:String, var nbBooks:Int=0) extends Person
  def addWrittenBookTo(a:Author)=
    a.nbBooks = a.nbBooks+1

case class CoAuthor(name:String) extends Person

case class BookPublisher(name:String, var nbBooks:Int=0) extends Person
  def addPublishedBookTo(p: BookPublisher) =
    p.nbBooks = p.nbBooks + 1