
// import scala.collection.mutable.HashMap

// object DistributedStoreSystem {
//   def main(args: Array[String]): Unit = {
//     val kvStore = new HashMap[String, String]()

//     while (true) {
//       println("Enter command (e.g. store key value, lookup key, delete key):")
//       val input = scala.io.StdIn.readLine()
//       val commandArgs = input.split(" ")
//       commandArgs.head match {
//         case "store" =>
//           if (commandArgs.length == 3) {
//             kvStore.put(commandArgs(1), commandArgs(2))
//             println(s"Stored value ${commandArgs(2)} for key ${commandArgs(1)}")
//           } else {
//             println("Invalid command. Usage: store key value")
//           }
//         case "lookup" =>
//           if (commandArgs.length == 2) {
//             val result = kvStore.getOrElse(commandArgs(1), s"No value found for key ${commandArgs(1)}")
//             println(result)
//           } else {
//             println("Invalid command. Usage: lookup key")
//           }
//         case "delete" =>
//           if (commandArgs.length == 2) {
//             kvStore.remove(commandArgs(1))
//             println(s"Deleted value for key ${commandArgs(1)}")
//           } else {
//             println("Invalid command. Usage: delete key")
//           }
//         case _ =>
//           println(s"Unknown command: ${commandArgs.head}")
//       }
//     }
//   }
// }

// object Main {
//   def main(args: Array[String]): Unit = {
//     DistributedStoreSystem.main(args)
//   }
// }
