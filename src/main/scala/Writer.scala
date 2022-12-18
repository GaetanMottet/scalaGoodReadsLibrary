trait Writer(name:String)

case class Author(name:String) extends Writer(name)

case class CoAuthor(name:String) extends Writer(name)