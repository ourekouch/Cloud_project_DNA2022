// import akka.actor._

// import scala.collection.mutable

// case class Store(key: String, value: String)
// case class Lookup(key: String)
// case class Delete(key: String)

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