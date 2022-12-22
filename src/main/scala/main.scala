
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.SparkSession

import scala.collection.mutable.ListBuffer
import scala.io.Source
import scala.util.control.Breaks.break


@main
def main(): Unit = {
  //The library will store every Authors - Publishers - Books lists that are in the .cvs
  val goodReadsLibrary = new Library("GoodReads Library")

  //Loading all data from .csv and build authors, publishers and books objects into Lists of Library Object
  goodReadsLibrary.initLibrary

  //Manage actions that can be done 
  Menu.displayMenu(goodReadsLibrary)
}