// import akka.actor.{ActorRef, ActorSystem, Props}
// import scala.concurrent.Await
// import scala.concurrent.duration.DurationInt
// import scala.util.Random

// object ClientProgram {
//   def main(args: Array[String]): Unit = {
//     val system = ActorSystem("KeyValueSystem")
//     val storageActor = system.actorOf(StorageActor.props, "StorageActor")
//     val cachingActor = system.actorOf(CachingActor.props, "CachingActor")

//     // Populating storage with values for keys from 1-500
//     for (i <- 1 to 500) {
//       storageActor ! Store(i.toString, Random.alphanumeric.take(10).mkString)
//     }

//     val random = new Random()
//     var lastKey = ""

//     // Making random requests to storage and caching actors
//     for (i <- 1 to 1000) {
//       val key = random.nextInt(1000) + 1 // Keys are from 1-1000

//       val operation = random.nextInt(3)
//       operation match {
//         case 0 => // Store
//           if (key > 500) {
//             storageActor ! Store(key.toString, Random.alphanumeric.take(10).mkString)
//           }
//         case 1 => // Delete
//           if (key > 500) {
//             storageActor ! Delete(key.toString)
//           }
//         case 2 => // Lookup
//           if (random.nextBoolean() && lastKey.nonEmpty) { // 50% chance to pick last key looked-up
//             if (random.nextBoolean()) {
//               storageActor ! Lookup(lastKey)
//             } else {
//               cachingActor ! Lookup(lastKey)
//             }
//           } else {
//             if (key > 500) {
//               storageActor ! Lookup(key.toString)
//             } else {
//               cachingActor ! Lookup(key.toString)
//             }
//           }
//           lastKey = key.toString
//       }

//       Thread.sleep(10) // Introduce a small delay before making the next request
//     }

//     system.terminate()
//   }
// }
