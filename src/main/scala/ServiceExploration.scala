object ServiceExploration {
  //Recherche de livre selon titre / éditeur

  def booksFromAuthor(a:String, l:List[Book])=
    l.filter(x => x.author.name.contains(a))

  def booksFromAuthor(a:Author, l:List[Book])=
    l.filter(x => x.author.name.contains(a.name))

  def bookByTitle(t:String, lb:List[Book])=
    val tLower = t.toLowerCase()
    lb.filter(x => x.title.toLowerCase().contains(tLower))

  //Grouper (sort) les livres par auteur

  //Nombre de livres lus par auteur (cf .reduce)

  //Sort livres selon MyRating

  //Filter livres selon bookShelves
}
