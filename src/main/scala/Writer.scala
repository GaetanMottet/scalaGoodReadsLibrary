trait Writer:
  val name:String

case class Author(name:String, nbBooks:Int=0) extends Writer
object Author{
  def apply(s:String)=new Author(s)
}

case class CoAuthor(name:String) extends Writer