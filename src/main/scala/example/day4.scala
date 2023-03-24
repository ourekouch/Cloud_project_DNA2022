package example

import akka.actor.{Actor, ActorLogging, ActorRef, ActorSystem, PoisonPill, Props}
import akka.cluster.Cluster
import akka.cluster.singleton.{ClusterSingletonManager, ClusterSingletonManagerSettings}
import akka.pattern.ask
import akka.util.Timeout

import scala.concurrent.ExecutionContext.Implicits.global
import akka.util.Timeout
import scala.concurrent.duration._


import scala.concurrent.Future
import scala.concurrent.duration._
import scala.util.{Failure, Success}

case class Store(key: String, value: String)
case class Lookup(key: String)
case class Delete(key: String)
case class StoreResult(success: Boolean)
case class LookupResult(value: Option[String])
case class DeleteResult(success: Boolean)

class StorageActor extends Actor with ActorLogging {
  private var store = Map.empty[String, String]

  override def receive: Receive = {
    case Store(key, value) =>
      store += (key -> value)
      sender() ! StoreResult(success = true)

    case Lookup(key) =>
      sender() ! LookupResult(store.get(key))

    case Delete(key) =>
      if (store.contains(key)) {
        store -= key
        sender() ! DeleteResult(success = true)
      } else {
        sender() ! DeleteResult(success = false)
      }
  }
}

class LoggingActor extends Actor with ActorLogging {
  override def receive: Receive = {
    case Store(key, value) =>
      log.info(s"Storing key=$key with value=$value")
    case Lookup(key) =>
      log.info(s"Looking up key=$key")
    case Delete(key) =>
      log.info(s"Deleting key=$key")
  }
}

class CacheActor(storage: ActorRef) extends Actor with ActorLogging {
  import context.dispatcher
  implicit val timeout: Timeout = Timeout(5.seconds)

  private var cache = Map.empty[String, String]

  override def receive: Receive = {
    case Store(key, value) =>
      storage ! Store(key, value)
      cache += (key -> value)
      sender() ! StoreResult(success = true)

    case Lookup(key) =>
      if (cache.contains(key)) {
        sender() ! LookupResult(cache.get(key))
      } else {
        val futureValue: Future[LookupResult] = (storage ? Lookup(key)).mapTo[LookupResult]
        futureValue.onComplete {
          case Success(LookupResult(Some(value))) =>
            cache += (key -> value)
            sender() ! LookupResult(Some(value))
          case Success(LookupResult(None)) =>
            sender() ! LookupResult(None)
          case Failure(ex) =>
            log.error(ex, s"Failed to perform lookup for key=$key")
            sender() ! LookupResult(None)

        }
      }

    case Delete(key) =>
      storage ! Delete(key)
      cache -= key
      sender() ! DeleteResult(success = true)
  }
}

class ConsoleReaderActor(storage: ActorRef, cache: ActorRef) extends Actor with ActorLogging {
  import ConsoleReaderActor._

  override def receive: Receive = {
    case ReadLine =>
      val input = scala.io.StdIn.readLine()
      input.split(" ") match {
        case Array("store", key, value) =>
          cache ! Store(key, value)
        case Array("lookup", key) =>
          cache ! Lookup(key)
        case Array("delete", key) =>
          cache ! Delete(key)
        case _ =>
          log.warning(s"Invalid command: $input")
      }
      context.become(receive)
    }
}

object ConsoleReaderActor {
  case object ReadLine
}

object Main {
  def main(args: Array[String]): Unit = {
    val system = ActorSystem("StorageSystem")

    val storage = system.actorOf(Props[StorageActor], name = "storage")

    val logging = system.actorOf(Props[LoggingActor], name = "logging")

    val cache = system.actorOf(Props(new CacheActor(storage)), name = "cache")

    val consoleReader = system.actorOf(Props(new ConsoleReaderActor(storage, cache)), name = "consoleReader")

    consoleReader ! ConsoleReaderActor.ReadLine

    val cluster = Cluster(system)

    val singletonManager = system.actorOf(
      ClusterSingletonManager.props(
      singletonProps = Props[LoggingActor](),
      terminationMessage = PoisonPill,
      settings = ClusterSingletonManagerSettings(system)),
      name = "loggingSingleton")

    cluster.registerOnMemberUp {
    println("Cluster is ready!")
    }
   }
}