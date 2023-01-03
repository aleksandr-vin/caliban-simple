package example

import zio._
import sttp.tapir.server.http4s.ztapir.ZHttp4sServerInterpreter
import org.http4s.server.Router
import sttp.tapir.server.http4s.Http4sServerOptions
import org.http4s.blaze.server.BlazeServerBuilder
import zio.interop.catz._

object Server extends ZIOAppDefault {

  type F[A] = RIO[Any, A]

  override def run =
    ZIO
      .runtime[Any]
      .flatMap { implicit runtime =>
        val gql = new GraphQL()

        for {
          gqlE <- gql.createServerEndpoints
          server <- {
            val interpreter = ZHttp4sServerInterpreter[Any](Http4sServerOptions.customiseInterceptors[F].options)
            val routesMappings = List("/" -> gqlE).map { case (m, e) => (m, interpreter.from(e).toRoutes) }
            lazy val httpApp = Router(routesMappings: _*).orNotFound
            BlazeServerBuilder[F]
              .bindHttp(8080, "0.0.0.0")
              .withHttpApp(
                httpApp
              )
              .serve
              .compile
              .drain
          }
        } yield server
      }

}
