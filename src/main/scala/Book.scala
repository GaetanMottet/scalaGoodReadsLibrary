import Author.*
import BookShelf._
import org.apache.hadoop.util.Options.IntegerOption
import shaded.parquet.it.unimi.dsi.fastutil.ints.IntOpenHashSet;

case class Book(idBook: String, isbn: Option[String], title: String, author: Author,  publisher: BookPublisher, originalPublicYear: Option[String], var readCount: Int, var myRating: Int, var exclusiveShelf: BookShelf){
  override def toString: String = {
    s"ID = $idBook, ISBN = ${isbn.getOrElse("")}, TITLE = $title, AUTHOR = ${author.name}, PUBLISHER = ${publisher.name}, PUBLICATION YEAR = ${originalPublicYear.getOrElse("")}, TIMES READ = $readCount, RATING = $myRating, IS ON THE SHELF = $exclusiveShelf"
  }
}

implicit class BookExt(b:Book):

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

