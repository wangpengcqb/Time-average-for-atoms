package com.sundogsoftware.spark

import org.apache.spark._
import org.apache.spark.SparkContext._
import org.apache.spark.sql._
import org.apache.log4j._


/** Count up average z position of atoms. */
object AtomCount {
  
case class Atom(ID:Int, element:String, x:Double, y:Double, z:Double)
/**  
  def mapper(line:String): Atom = {
    val fields = line.split(" ")
    
    val atom:Atom = Atom(fields(0).toInt, fields(1).toString, fields(2).toDouble, fields(3).toDouble, fields(4).toDouble)
    return atom
  }
**/  
  
  
  def main(args: Array[String]) {
    
    Logger.getLogger("org").setLevel(Level.ERROR)
    
    val spark = SparkSession
      .builder
      .appName("SparkSQL")
      .master("local[*]")
      .config("spark.sql.warehouse.dir", "file:///C:/temp") // Necessary to work around a Windows bug in Spark 2.0.0; omit if you're not on Windows.
      .getOrCreate()
    
    import spark.implicits._
    val lines = spark.sparkContext.textFile("G:/Software/VirtualBox_VMs/PengShare/substrate/dump_relax.pos")
    val parse = lines.map(x => x.split(" ")).filter(x => x.length == 5).filter(x => x(1) == "C")
    val atomDF = parse.map(
        x => Atom(x(0).toInt, x(1).toString, x(2).toDouble, x(3).toDouble, x(4).toDouble)
    ).toDS().cache()     
     
    println("Here is our inferred schema:")
    atomDF.printSchema()
    
    println("Let's select the ID column:")
    atomDF.select("ID").show()
    
    println("Group by ID:")
    val z_ave = atomDF.select("ID", "z").groupBy("ID").avg("z").orderBy("ID").show(false)
    
    spark.stop()
    
  }
  
}