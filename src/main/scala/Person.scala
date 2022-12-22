trait Person:
  val name:String

case class Author(name:String, var nbBooks:Int) extends Person
  def addWrittenBookTo(a:Author)=
    a.nbBooks = a.nbBooks+1


case class BookPublisher(name:String, var nbBooks:Int) extends Person
  def addPublishedBookTo(p: BookPublisher) =
    p.nbBooks = p.nbBooks + 1