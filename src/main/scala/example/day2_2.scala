// package example

// import akka.actor._
// import java.io._
// import scala.io._

// case class Store(key: String, value: String)
// case class Lookup(key: String)
// case class Delete(key: String)
// case class KeyValue(key: String, value: String)
// case object ReadInput

// class CacheStore(fileStore: ActorRef) extends Actor {
//   private val cache = scala.collection.mutable.HashMap[String, String]()

//   def receive = {
//     case Store(key, value) =>
//       cache.put(key, value)
//       println(s"Stored key '$key' with value '$value'")
//       fileStore ! Store(key, value)
      
//     case Lookup(key) =>
//       cache.get(key) match {
//         case Some(value) => println(s"Found key '$key' with value '$value'")
//         case None => println(s"Key '$key' not found")
//       }
      
//     case Delete(key) =>
//       cache.remove(key) match {
//         case Some(value) => println(s"Deleted key '$key' with value '$value'")
//         case None => println(s"Key '$key' not found")
//       }
//       fileStore ! Delete(key)
//   }
// }

// class FileStore extends Actor {
//   private val file = "store.txt"
//   private val deletedValue = "deleted"

//   def receive = {
//     case Store(key, value) =>
//       val writer = new FileWriter(file, true)
//       writer.write(s"$key,$value\n")
//       writer.close()
//       println(s"Stored key '$key' with value '$value'")
      
//     case Lookup(key) =>
//       val lines = Source.fromFile(file).getLines().toList.reverse
//       val value = lines.find(_.startsWith(s"$key,"))
//                        .map(_.substring(key.length + 1))
//                        .filter(_ != deletedValue)
//       value match {
//         case Some(v) => println(s"Found key '$key' with value '$v'")
//         case None => println(s"Key '$key' not found")
//       }
      
//     case Delete(key) =>
//       val writer = new FileWriter(file, true)
//       writer.write(s"$key,$deletedValue\n")
//       writer.close()
//       println(s"Deleted key '$key'")
//   }
// }

// class ConsoleReader(keyValueStore: ActorRef) extends Actor {
//   import scala.io.StdIn.readLine

//   def receive = {
//     case ReadInput =>
//       println("Choose store, lookup or delete:")
//       val input = readLine()
//       input.split("\\s+") match {
//         case Array("store", key, value) => keyValueStore ! Store(key, value)
//         case Array("lookup", key) => keyValueStore ! Lookup(key)
//         case Array("delete", key) => keyValueStore ! Delete(key)
//         case _ => println("Invalid command")
//       }
//       self ! ReadInput
//   }
// }

// object KeyValueStoreApp extends App {
//   val system = ActorSystem("KeyValueStoreSystem")
//   val fileStore = system.actorOf(Props[FileStore], name = "fileStore")
//   val keyValueStore = system.actorOf(Props(new CacheStore(fileStore)), name = "keyValueStore")
//   val consoleReader = system.actorOf(Props(new ConsoleReader(keyValueStore)), name = "consoleReader")

//   consoleReader ! ReadInput
// }

