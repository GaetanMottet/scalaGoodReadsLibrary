import scala.io.StdIn

object Menu {

  def displayMenu(library: Library): Unit = {

    println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~")
    println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~")
    println("Welcome to your " +library.name +"'s application")
    println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~")
    println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~")

    println("\nWhat do you want to do ?")
    
    val options = Map(
      1 -> " 1. Search for a single book by its title (can be a partial title)",
      2 -> " 2. Search for all books by partial title (can be a list of books)",
      3 -> " 3. Search for all books from a specific author",
      4 -> " 4. Display the number of books you've read, by specific author",
      5 -> " 5. Display the list of your books, sort by your rating",
      6 -> " 6. Display the list of your books by a specific shelf",
      7 -> " 7. Add a book",
      8 -> " 8. Calculate and display the average rating of books you have read",
      9 -> " 9. Increment the number of time you have read a specific book",
      10 -> "10. Modify your rating of a book",
      11 -> "11. Change the shelf of a book",
      12 -> "any other key to exit",
    )

    //display the menu's options
    for o <- options.toSeq.sortBy(_._1) do println(o._2)
    //Manage the user's answer and do the action choosed
    val rep = StdIn.readLine()
      rep match {
        case "1" => println("Your book : " + searchBookByTitle(library).toString)
        case "2" => searchBooksByTitle(library)
        case "3" => searchBooksByAuthor(library)
        case "4" => searchReadBooksByAuthor(library)
        case "5" =>
          val listBooks = ServiceExploration.sortBooksByMyRating(library)
          for b <- listBooks do println("Your rate : " +b.myRating + " for " +b.title)

        case "6" => 
          val listBooks = ServiceOperations.selectBooksByShelf(library)
          for b <- listBooks do println(b.title)
        case "7" => ServiceOperations.addBook(library)
        case "8" => println("Average of your ratings : " +ServiceOperations.avgMyRatings(library))
        case "9" => incrementBookReadCounter(library)
        case "10" => modifyRating(library)
        case "11" => ServiceOperations.modifyShelf(library)
        case _ => finish()
      }
    displayMenu(library)
  }

  //Method to search for a specific book by it's title
  private def searchBookByTitle(library: Library): Book = {
    println("Enter the title of a book (can be partial)")
    val rep = StdIn.readLine()
    val book = ServiceExploration.bookByTitle(rep, library)
    book
}

  //Method to search for books by title
  private def searchBooksByTitle(library: Library) = {
    println("Enter the title of a book (can be partial)")
    val rep = StdIn.readLine()
    val books = ServiceExploration.booksByTitle(rep, library)
    println("Your books : ")
    for b <- books do println(b.toString)
  }

  //Method to search for books for one author
  private def searchBooksByAuthor(library: Library): Unit = {
    println("Enter the name of the author you are looking for")
    val rep = StdIn.readLine()
    val books = ServiceExploration.booksFromAuthor(rep, library)
    println("Your books : ")
    for b <- books do println(b.toString)
  }

  //Method to search how many book i've read for a specific author
  private def searchReadBooksByAuthor(library: Library): Unit = {
    println("Enter the name of the author you are looking for")
    val rep = StdIn.readLine()
    val nbBooks = ServiceExploration.readBooksFromAuthor(rep, library)
    println("Number of read books : " +nbBooks)
    
    val totalBooks = ServiceExploration.booksFromAuthor(rep,library).length
    println("Number total of books for the author "+rep+" : " +totalBooks)
  }

  //Method to search for a specific book and change hpw many time we have read it
  private def incrementBookReadCounter(library: Library): Unit = {
    //1. Search a book
    val book = searchBookByTitle(library)
    //2. Increment the read counter
    ServiceOperations.incrementReadCount(book)
  }

  //Method to search for a specific book and change it's rating
  private def modifyRating(library: Library): Unit = {
    //1. Search a book
    val book = searchBookByTitle(library)
    //2. modify rating
    book.myRating = ServiceOperations.askForModifyRating()
    //3. print the book
    println("the new rating is "+book.myRating +" for " +book)
  }
  
  //Method to quit the program
  private def finish(): Unit = {

    println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~")
    println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~")
    println("         Bye bye ! Thanks for your trust           ")
    println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~")
    println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~")
    System.exit(0)
  }

}
