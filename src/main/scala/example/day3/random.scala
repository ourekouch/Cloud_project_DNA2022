// package example.day3

// import akka.actor._

// import scala.collection.mutable
// import scala.util.Random

// case class Store(key: String, value: String)
// case class Lookup(key: String)
// case class Delete(key: String)

// class CachingActor extends Actor {
//   private val cache = mutable.Map.empty[String, String]

//   override def receive: Receive = {
//     case Store(key, value) =>
//       cache += (key -> value)
//       println(s"Added key $key to cache")
//     case Lookup(key) if cache.contains(key) =>
//       println(s"Key $key found in cache")
//       sender() ! cache(key)
//     case Lookup(key) =>
//       println(s"Key $key not found in cache")
//       sender() ! "Not found"
//     case Delete(key) =>
//       cache -= key
//       println(s"Deleted key $key from cache")
//   }
// }

// object CachingActor {
//   def props: Props = Props[CachingActor]
// }

// object Client {
//   private val system = ActorSystem("KeyValueStoreSystem")
//   private val storageActor = system.actorOf(Props[StorageActor], "storageActor")
//   private val cachingActor = system.actorOf(CachingActor.props, "cachingActor")
//   private val random = new Random()

//   def run(): Unit = {
//     for (_ <- 1 to 10000) {
//       val key = random.nextInt(1000) + 1
//       val op = random.nextInt(3)

//       op match {
//         case 0 => storageActor ! Lookup(key.toString)
//         case 1 =>
//           if (key > 500) storageActor ! Delete(key.toString)
//           else storageActor ! Store(key.toString, s"value_$key")
//         case 2 =>
//           if (key > 500) storageActor ! Store(key.toString, s"value_$key")
//           else storageActor ! Lookup(key.toString)
//       }
//     }
//   }

//   def runWithCache(): Unit = {
//     for (_ <- 1 to 10000) {
//       val key = random.nextInt(1000) + 1
//       val op = random.nextInt(3)

//       op match {
//         case 0 => cachingActor ! Lookup(key.toString)
//         case 1 =>
//           if (key > 500) storageActor ! Delete(key.toString)
//           else {
//             storageActor ! Store(key.toString, s"value_$key")
//             cachingActor ! Store(key.toString, s"value_$key")
//           }
//         case 2 =>
//           if (key > 500) {
//             storageActor ! Store(key.toString, s"value_$key")
//             cachingActor ! Store(key.toString, s"value_$key")
//           } else {
//             val lastKey = random.nextInt(2)
//             if (lastKey == 0) cachingActor ! Lookup(key.toString)
//             else {
//               storageActor ! Lookup(key.toString)
//               cachingActor ! Store(key.toString, s"value_$key")
//             }
//           }
//       }
//     }
//   }
// }

// class StorageActor extends Actor {
//   private val store = mutable.Map.empty[String, String]

//   override def receive: Receive = {
//     case Store(key, value) =>
//       store += (key -> value)
//       println(s"Added key $key to store")
//     case Lookup(key) if store.contains(key) =>
//       println(s"Key $key found in store")
//       sender() ! store(key)
//     case Lookup(key) =>
//       println(s"Key $key not found in store")
//       sender() ! "Not found"
//     case Delete(key) =>
//       store -= key
//       println(s"Deleted key $key from store")
//   }
// }

// object StorageActor {
//   def props: Props = Props[StorageActor]
// }

// object Main {
//   def main(args: Array[String]): Unit = {
//     val startTime = System.nanoTime()
//     Client.run()
//     val endTime = System.nanoTime()
//     val durationWithoutCache = (endTime - startTime) / 1000000.0
//     println(s"Duration without cache: $durationWithoutCache ms")

//     val startTimeWithCache = System.nanoTime()
//     Client.runWithCache()
//     val endTimeWithCache = System.nanoTime()
//     val durationWithCache = (endTimeWithCache - startTimeWithCache) / 1000000.0
//     println(s"Duration with cache: $durationWithCache ms")
//   }
// }
