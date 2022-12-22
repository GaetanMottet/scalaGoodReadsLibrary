import Author.*
import BookShelf._
import org.apache.hadoop.util.Options.IntegerOption
import shaded.parquet.it.unimi.dsi.fastutil.ints.IntOpenHashSet;

case class Book(idBook: String, isbn: Option[String], title: String, author: Author, coAuthors:Seq[CoAuthor]=Seq.empty, publisher: BookPublisher, originalPublicYear: Option[String], readCount: Int, myRating: Int, exclusiveShelf: BookShelf)

implicit class BookExt(b:Book):
  def addAuhtor(a:Author) =
    b.copy(author = a)
  def addCoAuthor(ca:CoAuthor) =
    b.copy(coAuthors = b.coAuthors :+ ca)

  def incrementReadCount() =
    b.copy(readCount = b.readCount+1)

  def evaluate(rate:Int) =
    b.copy(myRating = rate)

  def selectShelf(shelf:String) =
  shelf match {
    case "to-read" => BookShelf.toRead
    case "currently-reading" => BookShelf.currentlyReading
    case "read" => BookShelf.read
    case "dnf" => BookShelf.dnf
    case _ => BookShelf.undefined
  }
  def storeOnShelf(shelf:String) =
    b.copy(exclusiveShelf = selectShelf(shelf))
//    val shelfEnum = withName(shelf)
//    b.copy(exclusiveShelf = shelfEnum)
