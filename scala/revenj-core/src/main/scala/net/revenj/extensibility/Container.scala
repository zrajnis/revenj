package net.revenj.extensibility

import java.lang.reflect.Type

import net.revenj.extensibility.InstanceScope._
import net.revenj.patterns.ServiceLocator

import scala.reflect.ClassTag
import scala.reflect.runtime.universe.TypeTag

trait Container extends ServiceLocator with AutoCloseable {

  private[revenj] def registerType[T](manifest: Type, implementation: Class[T], lifetime: InstanceScope = Transient): this.type

  @deprecated
  def register[T](singleton: Boolean)(implicit manifest: ClassTag[T]): this.type = {
    registerType(manifest.runtimeClass, manifest.runtimeClass, if (singleton) Singleton else Transient)
  }

  def register[T](lifetime: InstanceScope = Transient)(implicit manifest: ClassTag[T]): this.type = {
    registerType(manifest.runtimeClass, manifest.runtimeClass, lifetime)
  }

  @deprecated
  def registerAs[T, S <: T](singleton: Boolean)(implicit manifest: TypeTag[T], implementation: ClassTag[S]): this.type = {
    registerAs[T, S](if (singleton) Singleton else Transient)
  }

  def registerAs[T, S <: T](lifetime: InstanceScope = Transient)(implicit manifest: TypeTag[T], implementation: ClassTag[S]): this.type

  def registerInstance[T: TypeTag](service: T, handleClose: Boolean = false): this.type

  @deprecated
  def registerFactory[T: TypeTag](factory: Container => T, singleton: Boolean): this.type = {
    registerFunc[T](factory, if (singleton) Singleton else Transient)
  }

  def registerFunc[T: TypeTag](factory: Container => T, lifetime: InstanceScope = Transient): this.type

  def registerGenerics[T: TypeTag](factory: (Container, Array[Type]) => T, lifetime: InstanceScope = Transient): this.type

  def createScope(): Container
}