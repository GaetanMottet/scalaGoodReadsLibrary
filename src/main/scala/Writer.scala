trait Writer:
  val name:String

case class Author(name:String, var nbBooks:Int=0) extends Writer
  def addWrittenBookTo(a:Author)=
    a.nbBooks = a.nbBooks+1

case class CoAuthor(name:String) extends Writer