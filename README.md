# Scala Project : GoodReadsLibrary

Developped by Gaétan Mottet & Mégane Solliard

### Description

Our project was to execute several exploration and operations on the cvs GoodReadsLibrary, which contains data about books.

We created a class Library that contains 3 lists (data from csv) :
  - authors list
  - publishers list
  - books list

We also created a trait Person with 2 case class extending this trait : Author & BookPublisher

So for the case class book we were able to add an Author and a Publisher as well as importants details about the book :
  - id
  - ISBN
  - title
  - publication year
  - how many time we have read it
  - our rating
  - And on which shelf is it

  We used and implicit class BookExt in order to select and store one book into a shelf.
  Mostly because we created an enum for the shelves available which is named : BookShelf.
  
  
  ### Exploration & Operation methods done
    - Add a new Book
    - Search a book by its title (retrieve either 1 or a list if a partial title)
    - Search a book by author
    - Display the number of books you've read, by specific author
    - Sort books by my rating
    - Modify my rating of one book
    - Calculate average of my rating
    - Increment the number of times i read a book
    - Filter books according to Shelf selected
    - Modify the shelf of a book

