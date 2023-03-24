// import akka.actor._

// case class Store(key: String, value: String)
// case class Lookup(key: String)
// case class Delete(key: String)

// class KeyValueStore extends Actor {
//   val kvStore = scala.collection.mutable.Map[String, String]()

//   def receive = {
//     case Store(key, value) =>
//       kvStore.put(key, value)
//       sender ! s"Stored value ${value} for key ${key}"
//     case Lookup(key) =>
//       val result = kvStore.getOrElse(key, s"No value found for key ${key}")
//       sender ! result
//     case Delete(key) =>
//       kvStore.remove(key)
//       sender ! s"Deleted value for key ${key}"
//   }
// }

// class ConsoleReader(kvStore: ActorRef) extends Actor {
//   def receive = {
//     case input: String =>
//       val commandArgs = input.split(" ")
//       commandArgs.head match {
//         case "store" =>
//           if (commandArgs.length == 3) {
//             kvStore ! Store(commandArgs(1), commandArgs(2))
//           } else {
//             println("Invalid command. Usage: store key value")
//           }
//         case "lookup" =>
//           if (commandArgs.length == 2) {
//             kvStore ! Lookup(commandArgs(1))
//           } else {
//             println("Invalid command. Usage: lookup key")
//           }
//         case "delete" =>
//           if (commandArgs.length == 2) {
//             kvStore ! Delete(commandArgs(1))
//           } else {
//             println("Invalid command. Usage: delete key")
//           }
//         case _ =>
//           println(s"Unknown command: ${commandArgs.head}")
//       }
//   }
// }

// object DistributedStoreSystem {
//   def main(args: Array[String]): Unit = {
//     val system = ActorSystem("DistributedStoreSystem")

//     val kvStore = system.actorOf(Props[KeyValueStore], "kvStore")
//     val consoleReader = system.actorOf(Props(new ConsoleReader(kvStore)), "consoleReader")

//     while (true) {
//       println("Enter command ( with actors this time) (e.g. store key value, lookup key, delete key):")
//       val input = scala.io.StdIn.readLine()
//       consoleReader ! input
//     }
//   }
// }
