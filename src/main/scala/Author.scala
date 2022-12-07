

class Author(val name: String)

object Author:
  def apply(s:String) = new Author(s)