import java.time.Year
import scala.io.StdIn

object ServiceOperations {
   // add a book
   def addBook(lb:Library): Book ={
     // 0. TO DO : method to create new book by asking information to the user, step by step ?
     val b = createBook (lb)
     //a. Add author to authorsList
     val newAuthorsList = lb.authorsList :+ b.author
     lb.authorsList = newAuthorsList
     //b. Add publisher to publishersList
     val newPublishersList = lb.publishersList :+ b.publisher
     lb.publishersList = newPublishersList
     //c. Add the book to listBooks
     val newListBooks = lb.listBooks :+ b
     lb.listBooks = newListBooks

     b
   }
   def createBook(l: Library): Book = {
     println("Enter the id of your book :")
     val idBook = StdIn.readLine()

     println("Enter the isbn of your book :")
     val isbn = StdIn.readLine()

     println("Enter the title of your book :")
     val title = StdIn.readLine()

     println("Enter the author of your book :")
     val authorName = StdIn.readLine()
     //check if author already exists
     val author = l.checkAuthor(authorName)

     println("Enter the publisher of your book :")
     val publisherName = StdIn.readLine()
     //check publisher
     val publisher = l.checkPublisher(publisherName)


     // Get publication Year
     var publicYear = ""
     var valid = false
     while(valid != true) {
       println("Enter the publication's year of your book :")
       try {
         val num = StdIn.readInt()
         publicYear = num.toString

         val yearToCheck = Year.of(num)
         yearToCheck.isBefore(Year.now())
         if (yearToCheck.isBefore(Year.now())) {
           valid = true
         } else {
           println("Please enter a valid year (before now)")
         }

       } catch {
         case e: NumberFormatException => println("Invalid input. Please enter a year.")
       }
     }

    // Get the number of readings
     var readCount = -1
     while (readCount < 0) {
       println("Enter how many times you already read this book :")
       try {
         readCount = StdIn.readInt()
       } catch {
         case e: NumberFormatException => println("Invalid input. Please enter a number.")
       }

     }


     //Get and check values entered for the rating
     val myRating = askForModifyRating()


     //Get the shelf where to put the book
     println("Enter the shelf where to put your book :")
     // display the list of BookShelf => user's choice
     println("A. " + BookShelf.toRead)
     println("B. " + BookShelf.currentlyReading)
     println("C. " + BookShelf.read)
     println("D. " + BookShelf.dnf)
     println("E. " + BookShelf.undefined)

     val shelfString = StdIn.readLine()

     // match case by user's choice
     def choosenShelf(shelfString: String): BookShelf = {
     shelfString.toUpperCase() match {
       case "A" => BookShelf.toRead
       case "B" => BookShelf.currentlyReading
       case "C" => BookShelf.read
       case "D" => BookShelf.dnf
       case _ => BookShelf.undefined // case of E or other than A,B,C,D
       }
     }

    val exclusiveShelf = choosenShelf(shelfString)

    val b = Book(idBook: String, Option(isbn), title, author, null, publisher, Option(publicYear), readCount, myRating, exclusiveShelf)

     //add book to author and publisher
  addWrittenBookTo(author)
  addPublishedBookTo(publisher)

     println(b.title + " has been created correctly.")
    l.listBooks = l.listBooks :+ b
    b
   }

   //moyenne des MyRatings
   def avgMyRatings(l: Library): Double = {
     val listBooks = l.listBooks.filter(_.readCount > 0)
     var sum = 0.0
     for(b <- listBooks) do sum += b.myRating
     sum/listBooks.length

   }


   //augmenter readCount
   def incrementReadCount(b: Book) = {
     b.incrementReadCount()
     println("The book '" +b.title + " has been read " +b.readCount)
     println("Do you want to reevaluate your rating ? Y/N")
     val rep = StdIn.readLine()
     rep.toUpperCase match {
       case "Y" => b.evaluate(askForModifyRating())
     }
     println("The book '" +b.title + " has been read " +b.readCount +" time(s) and has a rate of " +b.myRating)
   }
   //Modify MyRating
   def askForModifyRating(): Int = {
     var myRatingToCheck = -1
     while (myRatingToCheck < 0 || myRatingToCheck > 5) {
       println("Enter your rate of this book (0-5) :")
       try {
         myRatingToCheck = StdIn.readInt()
       } catch {
         case e: NumberFormatException => println("Invalid input. Please enter a number.")
       }
     }
     myRatingToCheck
   }

  //Afficher ExclusiveShelf "to-read"

}
