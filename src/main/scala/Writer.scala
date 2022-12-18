trait Writer(name:String)

case class Author(name:String) extends Writer(name)
object Author{
  def apply(s:String)=new Author(s)
}

case class CoAuthor(name:String) extends Writer(name)