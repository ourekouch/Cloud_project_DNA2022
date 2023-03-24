// package example

// import java.io._
// import scala.io._

// case class Store(key: String, value: String)
// case class Lookup(key: String)
// case class Delete(key: String)
// case class KeyValue(key: String, value: String)
// case object ReadInput

// class KeyValueStore(file: File) {
//   private val deletedValue = "deleted"

//   def store(key: String, value: String): Unit = {
//     val writer = new FileWriter(file, true)
//     writer.write(s"$key,$value\n")
//     writer.close()
//     println(s"Stored key '$key' with value '$value'")
//   }

//   def lookup(key: String): Unit = {
//     val lines = Source.fromFile(file).getLines().toList.reverse
//     val value = lines
//       .find(_.startsWith(s"$key,"))
//       .map(_.substring(key.length + 1))
//       .filter(_ != deletedValue)
//     value match {
//       case Some(v) => println(s"Found key '$key' with value '$v'")
//       case None => println(s"Key '$key' not found")
//     }
//   }

//   def delete(key: String): Unit = {
//     val writer = new FileWriter(file, true)
//     writer.write(s"$key,$deletedValue\n")
//     writer.close()
//     println(s"Deleted key '$key'")
//   }
// }

// class ConsoleReader(keyValueStore: KeyValueStore) {
//   import scala.io.StdIn.readLine

//   def run(): Unit = {
//     while (true) {
//       val input = readLine()
//       input.split("\\s+") match {
//         case Array("store", key, value) => keyValueStore.store(key, value)
//         case Array("lookup", key) => keyValueStore.lookup(key)
//         case Array("delete", key) => keyValueStore.delete(key)
//         case _ => println("Invalid command")
//       }
//     }
//   }
// }

// object KeyValueStoreApp extends App {
//   val file = new File("store.txt")
//   val keyValueStore = new KeyValueStore(file)
//   val consoleReader = new ConsoleReader(keyValueStore)

//   // Start the console reader
//   consoleReader.run()
// }
